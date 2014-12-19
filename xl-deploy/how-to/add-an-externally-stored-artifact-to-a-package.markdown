---
title: Add an externally stored artifact to a package
subject:
- Configuration
categories:
- xl-deploy
tags:
- artifacts
- packaging
---

Normally, XL Deploy stores artifacts in its internal JCR repository. As of XL Deploy 5.0.0, you can also set a remote uniform resource identifier (URI) that is resolved when XL Deploy needs to access the file; for example, when it needs to perform a deployment.

By default, XL Deploy supports Maven repositories and HTTP/HTTPS locations.

## Setting `fileUri` of deployable artifacts

When creating a deployable artifact, you can choose to either:

* Upload a file that will be stored in JCR.
* Specify the `fileUri` property, which XL Deploy will use to resolve the artifact at runtime, using the URI with one of available artifact resolvers.

Note that URI validation, checksum calculation and placeholders scanning are happens only once, after the creation of the CI, and those are not re-calculated and scanned when you change `fileUri` property. So it is not recommended to change `fileUri` after it was created in XL Deploy.

## Using Maven repository URI

The URI of a Maven artifact start with `maven:`, followed by [Maven coordinates](http://maven.apache.org/pom.html#Maven_Coordinates). For example: `maven:com.acme.applications:PetClinic:1.0`.

**Note:** For information about configuring the Maven repositories that will be used, refer to [Configure XL Deploy to fetch artifacts from a Maven repository](configure-xl-deploy-to-fetch-artifacts-from-a-maven-repository.html).

## Using HTTP URI

You can use an HTTP reference in the `fileUri` property. Note that:

* XL Deploy tries to get the file name from the `Content-Disposition` header of the `HEAD` request, then of the `GET` request. If neither is available, it falls back to the last segment of the URI.
* You can specify basic HTTP credentials in the URI; for example, `http://admin:admin@example.com/artifact.jar`.
* To connect using HTTPS with a self-signed SSL certificate you need to configure JVM parameters of XL Deploy to trust your certificate.

## CLI example

This is how you can create a deployment package using XL Deploy CLI:

    admin > myApp = factory.configurationItem('Applications/myApp', 'udm.Application')
    admin > repository.create(myApp)
    admin > myApp1_0 = factory.configurationItem('Applications/myApp/1.0', 'udm.DeploymentPackage')
    admin > repository.create(myApp1_0)
    admin > myFile = factory.configurationItem('Applications/myApp/1.0/myFile', 'file.File', {'fileUri': 'http://example.com.com/artifact.war'})
    admin > repository.create(myFile)
