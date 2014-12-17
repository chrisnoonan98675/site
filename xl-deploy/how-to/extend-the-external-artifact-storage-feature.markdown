---
title: Extend the external artifact storage feature
subject:
- Configuration
categories:
- xl-deploy
tags:
- artifacts
- packaging
---

Normally, XL Deploy stores artifacts in its internal JCR repository. As of XL Deploy 5.0.0, you can also set a remote uniform resource identifier (URI) that is resolved when XL Deploy needs to access the file; for example, when it needs to perform a deployment.

By default, XL Deploy supports Maven repositories and HTTP/HTTPS locations. You can also implement support for any store, which can be accessed from Java.

Imagine there is an service called "Acme Cloud" that can store artifacts. It uses the following schema to identify them:

    acme:{cloud-id}/{file-name}

In the example, Acme Cloud provides `acme-cloud` library to access data in its storage.

## Step 1 Implement a `ArtifactResolver` interface

By implementing an `ArtifactResolver` interface, you instruct XL Deploy to retrieve artifacts using URIs with the `acme` protocol. A single resolver can support multiple protocols.

Refer to the [ArtifactResolver documentation](http://docs.xebialabs.com/releases/latest/xl-deploy/javadoc/engine-spi/com/xebialabs/deployit/engine/spi/artifact/resolution/ArtifactResolver.html) for more information.

    import com.xebialabs.deployit.engine.spi.artifact.resolution.ArtifactResolver;
    import com.xebialabs.deployit.engine.spi.artifact.resolution.ArtifactResolver.Resolver;
    import com.xebialabs.deployit.engine.spi.artifact.resolution.ResolvedArtifactFile;
    import com.xebialabs.deployit.plugin.api.udm.artifact.SourceArtifact;

    import java.io.IOException;
    import java.io.InputStream;
    import java.net.URI;
    import java.net.URISyntaxException;

    import com.acme.cloud.AcmeCloudClient;
    import com.acme.cloud.AcmeCloudFile;


    @Resolver(protocols = {"acme"})
    public class AcmeCloudArtifactResolver implements ArtifactResolver {

      @Override
      public ResolvedArtifactFile resolveLocation(SourceArtifact artifact) {

        AcmeCloudClient acmeCloudClient = new AcmeCloudClient();
        AcmeCloudFile acmeCloudFile = acmeCloudClient.fetch(artifact.getFileUri());

        return new ResolvedArtifactFile() {
          @Override
          public String getFileName() {
            return acmeCloudFile.getFilename();
          }

          @Override
          public InputStream openStream() throws IOException {
            return acmeCloudFile.getInputStream();
          }

          @Override
          public void close() throws IOException {
            acmeCloudClient.cleanTempDirs();
          }
        };
      }

      @Override
      public boolean validateCorrectness(SourceArtifact artifact) {
        try {
          return new URI(artifact.getFileUri()).getScheme().equals("acme");
          } catch (URISyntaxException e) {
            return false;
          }
        }
      }
    }

**Important:** It is important to put the `@Resolver` annotation on your class. This will indicate that this resolver needs to be picked up and registered. The protocol name must be compatible with the URI specification; for that reason, it can not contain the dash (`-`) character.

## Step 2 Add the resolver to the XL Deploy classpath

To make XL Deploy aware of the resolver, you must compile the class and put it on the classpath of the server, along with the third-party libraries. You must then restart the server.

## Step 3 Specify `fileUri` in `udm.SourceArtifact`

Now, when you create a deployable CI of any type that extends `udm.SourceArtifact`, you can specify the `fileUri` property using the protocol described in your resolver. So, after you've added the `AcmeCloudArtifactResolver` resolver, you can create an artifact pointing to `acme:cloud42/artifact.jar` and it will be successfully deployed.
