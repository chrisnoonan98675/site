---
title: Introduction to the XL Deploy File plugin
categories: 
- xl-deploy
subject:
- File plugin
tags:
- plugin
- file
- archive
---

In many cases, an application depends on external resources for its configuration. The application accesses these resources from a predefined location or using a predefined mechanism. In the simplest of forms, a resource can be described as a file, an archive (`ZIP`), or a folder (collection of files). The XL Deploy File plugin allows you to define these resources in a deployment package and manage them on a target host.

The resources can contain placeholders that the plugin will replace when targeting to the specific host, thus allowing resources to be defined independent of their environment.

## Features

Deploy, upgrade, and undeploy a file based resource on an `overthere.Host` configuration item (CI).

* `file.File`
* `file.Folder`
* `file.Archive`

## Use in deployment packages

This is a sample deployment package (DAR) manifest that defines a file, folder, and archive resource:

    <udm.DeploymentPackage version="1.0" application="FilePluginSample">
        <file.File name="sampleFile" file="sampleFile.txt"/>
        <file.Archive name="sampleArchive" file="sampleArchive.zip" />
        <file.Folder name="sampleFolder" file="sampleFolder" />
    </udm.DeploymentPackage>
