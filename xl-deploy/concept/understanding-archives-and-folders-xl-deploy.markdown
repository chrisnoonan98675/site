---
title: Understanding archives and folders in XL Deploy
subject:
- Packaging
categories:
- xl-deploy
tags:
- deployable
- package
---

There are several special things about the way that XL Deploy handles archive artifacts (such as ZIP files) and folders. In XL Deploy's [Unified Deployment Model](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#unified-deployment-model-udm) (UDM) type hierarchy, there are two base types for deployable artifacts:

* [`udm.BaseDeployableFileArtifact`](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmbasedeployablefileartifact) for files
* [`udm.BaseDeployableFolderArtifact`](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmbasedeployablefolderartifact) for folders

Every deployable artifact type in XL Deploy is a subtype of one of these two base types. Therefore,  [`udm.BaseDeployableArchiveArtifact`](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmbasedeployablearchiveartifact) is a subtype of `udm.BaseDeployableFileArtifact` and is used as the base type for deployable archives such as `jee.Ear`. 

For the most part, XL Deploy treats archives like regular files; however, in the case of archives, the default value for the `scanPlaceholdersproperty` is false. This prevents the costly scanning of placeholders when you import an archive into XL Deploy's repository.

Archives are not automatically decompressed when you deploy them. In fact, you wouldn't want this to happen to an EAR file, because the application server will handle the decompression.

XL Deploy actually stores folder artifacts in its repository as ZIP files because this is more efficient; however, this isn't visible to the user.

When you import a deployment package (DAR file), you can specify the content of a folder artifact as a plain folder or as an archive (ZIP file) inside the DAR.

Continuous integration tools such as [Maven](http://maven.apache.org/), [Jenkins](http://jenkins-ci.org/), [Hudson](http://hudson-ci.org/), [Bamboo](https://www.atlassian.com/software/bamboo), and [Team Foundation Server](http://www.visualstudio.com/products/tfs-overview-vs) should support the ability to refer to an archive in the build output as the source for a folder artifact.
