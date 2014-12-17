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

Normally XL Deploy stores artifacts in the internal JCR repository, but is is also possible to set a remote URI which is lazily resolved once XL Deploy needs to access the file, e.g. when it needs to perform a deployment. Maven repositories and HTTP/HTTPS locations are supported by default, and it is also possible to implement support for any store, which can be accessed from Java.

Let's imagine there is an service, called "Acme Cloud", which can store artifacts and uses the following schema to identify them:

    acme:{cloud-id}/{file-name}

In the example we assume that Acme Cloud provides `acme-cloud` library to access data in its storage.

## Step 1 Implement a `ArtifactResolver` interface

TIP: Check javadocs of [ArtifactResolver](http://docs.xebialabs.com/releases/latest/xl-deploy/javadoc/engine-spi/com/xebialabs/deployit/engine/spi/artifact/resolution/ArtifactResolver.html)

By implementing this interface you "teach" XL Deploy to retrieve artifacts using URIs with `acme` protocol. A single resolver can support multiple protocols.

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

NOTE: It is important to put `@Resolver` annotation on your class, which will take XL Deploy that this resolver needs to be picked up and registered. Note that protocol name must be compatible with URI specification and for that reason it can not contain dash (`-`) characters.

## Step 2 Add the resolver to the classpath of XL Deploy

In order to make XL Deploy aware of this resolver you have to compile the class and put it on the classpath of the server. Don't forget to put 3rd-party libs on the classpath as well. The server must be restarted after this.

## Step 3 Specify fileUri in `udm.SourceArtifact`

Now, when you create a deployable CI of any type which extends `udm.SourceArtifact`, it is possible to specify `fileUri`  property using the protocol described in your resolver. So, after we've added our `AcmeCloudArtifactResolver` resolver, we can create an artifact pointing to `acme:cloud42/artifact.jar` and it will be successfully deployed.
