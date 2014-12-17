---
title: Add externally stored artifact to the package
subject:
- Configuration
categories:
- xl-deploy
tags:
- artifacts
- packaging
---

Normally XL Deploy stores artifacts in the internal JCR repository, but is is also possible to set a remote URI which is lazily resolved once XL Deploy needs to access the file, e.g. when it needs to perform a deployment.

## Setting `fileUri` of deployable artifacts

When creating a deployable artifact you can choose: upload a file, which will be stored in JCR, or specify `fileUri` property, which will be used to resolve the artifact at runtime using provided URI with one of available artifact resolvers.

## Using maven repository URI

The URI of a maven artifact always start with `maven:` and follows by maven coordinates. For example: `maven:com.acme.applications:PetClinic:1.0`.

NOTE: To learn how to configure maven repositories which will be used for lookup please check this article: [Configure XL Deploy to fetch artifacts from a Maven repository](configure-xl-deploy-to-fetch-artifacts-from-a-maven-repository.html).


## Using HTTP URI

Using HTTP references in `fileUri` is really as simple as it seems. However, there are couple useful things to know about:
* XL Deploy tries to get filename from `Content-Disposition` header of `HEAD` request, then of `GET` request, and, if both are not available, it falls back to the last segment of the URI.
* You can specify Basic HTTP credentials in the URI (e.g. `http://admin:admin@example.com/artifact.jar`)
