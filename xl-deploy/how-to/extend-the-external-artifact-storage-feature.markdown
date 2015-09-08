---
title: Extend the external artifact storage feature
subject:
- Packaging
categories:
- xl-deploy
tags:
- artifacts
- package
- application
- configuration
since:
- XL Deploy 5.0.0
---

Artifacts are the physical files that make up a specific version of an application; for example, an application binary, configuration files, or web content. When adding an artifact to a deployment package, you can either:

* Upload an artifact that will be stored in XL Deploy’s internal JCR repository, or
* Specify the uniform resource identifier (URI) of an externally stored artifact, which XL Deploy will resolve when it needs to access the file (supported in XL Deploy 5.0.0 and later)

By default, XL Deploy supports externally stored artifacts in Maven repositories (including Artifactory and Nexus) and HTTP/HTTPS locations. You can also implement support for any store that can be accessed with Java.

For example, there is an service called "Acme Cloud" that can store artifacts. It uses the following schema to identify artifacts:

    acme:{cloud-id}/{file-name}

In this example, Acme Cloud provides `acme-cloud` library to access data in its storage.

## Step 1 Implement an `ArtifactResolver` interface

An `ArtifactResolver` interface instructs XL Deploy to retrieve artifacts using URIs with the `acme` protocol. A single resolver can support multiple protocols.

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

**Important:** It is important to put the `@Resolver` annotation on your class. This indicates that the resolver must be picked up and registered. The protocol name must be compatible with the URI specification; for that reason, it can not contain the dash (`-`) character.

## Step 2 Add the resolver to the XL Deploy classpath

To make XL Deploy aware of the resolver, you must compile the class and put it on the classpath of the server, along with third-party libraries. You must then restart the server.

## Step 3 Specify `fileUri` in `udm.SourceArtifact`

Now, when you create a deployable configuration item (CI) of any type that extends `udm.SourceArtifact`, you can specify the `fileUri` property using the protocol described in your resolver.

So, after adding the `AcmeCloudArtifactResolver` resolver, you can create an artifact pointing to `acme:cloud42/artifact.jar`, and XL Deploy can deploy it.
