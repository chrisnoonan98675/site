---
title: Add an externally stored artifact to a package
subject:
- Packaging
categories:
- xl-deploy
tags:
- artifacts
- package
- application
since:
- XL Deploy 5.0.0
weight: 207
---

Artifacts are the physical files that make up a specific version of an application; for example, an application binary, configuration files, or web content. When adding an artifact to a deployment package, you can either:

* Upload an artifact that will be stored in XL Deploy's internal JCR repository, or
* Specify the uniform resource identifier (URI) of an externally stored artifact, which XL Deploy will resolve when it needs to access the file (supported in XL Deploy 5.0.0 and later)

## Set the URI of a deployable artifact

If you set the file URI (`fileUri`) property of an artifact configuration item (CI) to a URI, XL Deploy uses an artifact resolver to resolve the URI when it needs access to the artifact (for example, when you set up a deployment). XL Deploy will download the artifact temporarily to perform certain actions on it, such as placeholder replacement; after deployment is complete, XL Deploy will delete its temporary copy of the artifact.

By default, XL Deploy supports Maven repositories (including Artifactory and Nexus) and HTTP/HTTPS locations. You can also add your own [custom artifact resolver](/xl-deploy/how-to/extend-the-external-artifact-storage-feature.html).

**Important:** The value of the `fileUri` property must be a *stable* reference; that is, it must point to the *same* file whenever it is referenced. "Symlink"-style references, such as a link to the "latest" version, are not supported.

## Changing the URI of a deployable artifact

**Important:** Do not change the file URI property after saving the artifact CI.

XL Deploy performs URI validation, checksum calculation, and placeholder scanning once, after the creation of the artifact configuration item (CI). It does not perform these actions again if the `fileUri` property is changed.

Also, if you are using XL Deploy's internal JCR repository, changing the URI of a saved CI can result in orphaned artifact files that cannot be removed by the garbage collection mechanism.

If you want to change the file URI, create a new CI for the artifact.

## Use a Maven repository URI

The URI of a Maven artifact must start with `maven:`, followed by [Maven coordinates](http://maven.apache.org/pom.html#Maven_Coordinates). For example:

    maven:com.acme.applications:PetClinic:1.0

For information about configuring your Maven repository, refer to [Configure XL Deploy to fetch artifacts from a Maven repository](/xl-deploy/how-to/configure-xl-deploy-to-fetch-artifacts-from-a-maven-repository.html).

**Important:** References to SNAPSHOT versions are *not* supported because these are not stable references.

XL Deploy looks up the artifact during initial deployments and update deployments. If the artifact is missing from the repository, the lookup will return an error. In XL Deploy 5.5.2 and later, you can configure XL Deploy to serve an empty artifact so the deployment can continue anyway; however, note that this option is *not* recommended, as it can cause issues that are hard to debug. To enable this option, set `xl-platform.extensions.resolver.maven.ignoreMissingArtifact` in the `conf/maven.conf` file:

    xl.repository.artifact.resolver.maven.ignoreMissingArtifact = true

## Use an HTTP or HTTPS URI

You can use an HTTP or HTTPS reference in the `fileUri` property. XL Deploy tries to get the file name from the `Content-Disposition` header of the `HEAD` request, then from the `Content-Disposition` header of the `GET` request. If neither is available, XL Deploy gets the file name from the last segment of the URI.

You can specify basic HTTP credentials in the URI. For example:

    http://admin:admin@example.com/artifact.jar

To connect using HTTPS with a self-signed SSL certificate, you must configure the JVM parameters of XL Deploy to trust your certificate.

XL Deploy looks up the artifact during initial deployments and update deployments. If the URL returns a 404 error, the lookup will return an error. In XL Deploy 5.5.3 and later, you can configure XL Deploy to serve an empty artifact so the deployment can continue anyway; however, note that this option is *not* recommended, as it can cause issues that are hard to debug. To enable this option, set `xl-platform.extensions.resolver.http.ignoreMissingArtifact` in the `conf/extensions.conf` file:

    xl.repository.artifact.resolver.http.ignoreMissingArtifact = true

## Create a deployment package using the CLI

This example shows how you can create a deployment package with an externally stored artifact using the XL Deploy command-line interface (CLI):

    myApp = factory.configurationItem('Applications/myApp', 'udm.Application')
    repository.create(myApp)
    myApp1_0 = factory.configurationItem('Applications/myApp/1.0', 'udm.DeploymentPackage')
    repository.create(myApp1_0)
    myFile = factory.configurationItem('Applications/myApp/1.0/myFile', 'file.File', {'fileUri': 'http://example.com.com/artifact.war'})
    repository.create(myFile)
