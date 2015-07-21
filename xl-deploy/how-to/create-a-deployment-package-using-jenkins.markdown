---
title: Create a deployment package using Jenkins
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- Jenkins
---

## Using the XL Deploy Jenkins plugin

To enable continuous integration, XL Deploy can be integrated with the Jenkins CI server. The XL Deploy Jenkins plugin enables you to integrate Maven and XL Deploy. Specifically, the plugin supports:

* Creating a deployment package containing artifacts from the build
* Publishing the package to an XL Deploy Server
* Performing a deployment of the package to a target environment


## Configuring the Jenkins plugin

Once you have installed the Jenkins plugin into your CI server you then need to configure it.  There are two areas to configure, first you need to configure the connection details to the XL Deploy server.

Navigate to Manage Jenkins > Configure System

You will now see an additional section for XL Deploy.  Simply fill in the details about your XL Deploy server and Test your connection

![image](images/jenkins-set-xld-server.png)

## Using the Jenkins plugin

Packaging an XL Deploy application is the core of successfully using XL Deploy. A package is often contains all of the components that form your application, for example webcontent, webserver configuration, compiled binaries (e.g. .NET applications, Java Enterprise Edition (JEE) Enterpris Archive(Ear) files), database scripts...the list is endless.

The plugin allows you to prescribe the contents of your deployment package (and therefore define your application) using the XL Deploy plugin.

This is done as a Post Build Action.  Simply select the Deploy with XL Deploy post Build Action

![image](images/jenkins-post-build-action.png)

This will reveal a standard Jenkins form which is specific to creating an XL Deployment Archive (DAR).

![image](images/jenkins-basic-information.png)

Basic Information can be entered here - click on the Question Marks to get specific information about a field. Fields can use Jenkins Variables (Version is a typical example often linked to $BUILD_TAG - e.g. 1.0.$BUILD_TAG)

At this point you add your deployables by checking the Package Application CheckBox.

![image](images/jenkins-package-application.png)

For Artifacts the Location field is used to indicate where that particular artifact resides.  This could be in the Jenkins workspace, a remote URI or some co-ordinates in a maven repository.  Check the Jenkins help for more details on the fields.

Add any properties as required by each of your artifacts/resources.

![image](images/jenkins-add-property.png) 

The plugin allows you to publish a package to XL Deploy.  By Selecting the checkbox you are able to choose from the Generated package, or one from another location (e.g. from the file system or an artifact repository)

The plugin allows you to deploy the package using XL Deploy (it must exist in the XL Deploy repository).  Select the Environment to target and choose the available options as shown below

![image](images/jenkins-deploy-application.png)





