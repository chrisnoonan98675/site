---
title: Introduction to the XL Deploy File plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- file
- archive
weight: 355
---

In many cases, an application depends on external resources for its configuration. The application accesses these resources from a predefined location or using a predefined mechanism. In the simplest of forms, a resource can be described as a file, an archive (`ZIP`), or a folder (collection of files).

The XL Deploy File plugin allows you to define these resources in a deployment package and manage them on a target host. It can deploy a `file.File`, `file.Folder`, or `file.Archive` configuration item (CI) on an `overthere.Host` CI.

The file, folder, or archive can contain placeholders that the plugin will replace when targeting to the specific host, thus allowing resources to be defined independent of their environment.

## Features

* Deploy a file-based resource on a host
* Upgrade a file-based resource on a host
* Undeploy a file-based resource on a host

## Use in deployment packages

This is a sample deployment package (DAR) manifest that defines a file, folder, and archive resource:

    <udm.DeploymentPackage version="1.0" application="FilePluginSample">
        <file.File name="sampleFile" file="sampleFile.txt"/>
        <file.Archive name="sampleArchive" file="sampleArchive.zip" />
        <file.Folder name="sampleFolder" file="sampleFolder" />
    </udm.DeploymentPackage>

## Customizing copy behavior

If the location on the host where the file, folder, or archive will be copied (the `targetPath`) is shared with other resources, you can set the `targetPathShared` property on the relevant CI type to "true". This means that XL Deploy will not delete the target path when updating or undeploying a deployed application; XL Deploy will only delete the artifacts that were copied to the target path.


For example, suppose you have a shared directory called `SharedDir`, which contains a directory that was not created by XL Deploy called `MyDir`. If `targetPathShared` is set to "true", then XL Deploy will not delete `/SharedDir/MyDir/` when updating or undeploying a deployed application. If `targetPathShared` is set to "false", XL Deploy will delete `/SharedDir/MyDir/`.

If `/SharedDir/MyDir/` exists *and* XL Deploy will deploy a folder named `MyDir`, then XL Deploy will not delete `/SharedDir/MyDir/` during the initial deployment (though files with the same name will be overwritten). However, XL Deploy would delete `/SharedDir/MyDir/` during an update or undeployment.

You can also customize the copy commands that the [Remoting plugin](/xl-platform/concept/remoting-plugin.html) uses for files and directories; refer to the [Overthere connection options](https://github.com/xebialabs/overthere#common-connection-options) for more information.
