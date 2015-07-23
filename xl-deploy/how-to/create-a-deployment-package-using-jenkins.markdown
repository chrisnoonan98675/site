---
title: Create a deployment package using Jenkins
categories:
- xl-deploy
subject:
- Packaging
tags:
- package
- application
- jenkins
---

To enable continuous integration, XL Deploy can be integrated with [Jenkins CI server](https://jenkins-ci.org/). The [XL Deploy Jenkins plugin](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin) enables you to integrate Maven and XL Deploy. Specifically, the plugin supports:

* Creating a deployment package containing artifacts from a build
* Publishing the package to an XL Deploy server
* Performing a deployment of the package to a target environment

## Configuring the Jenkins plugin

After you have installed the Jenkins plugin in your CI server, you must configure it:

1. In Jenkins, go to **Manage Jenkins** > **Cofigure System**.
2. In the **XL Deploy** section, enter information about your XL Deploy server and test the connection.

![image](images/jenkins-set-xld-server.png)

## Using the Jenkins plugin

In XL Deploy, a [deployment package](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html#whats-in-an-application-deployment-package) contains the components that form your application; for example, web content, webserver configuration, database scripts, compiled binaries such as .NET applications and Java Enterprise Edition (JEE) Enterprise Archive (EAR) files, and so on.

The XL Deploy Jenkins plugin allows you to provide the contents of your deployment package, and therefore define your application. This is done as a post-build action. Select the **Deploy with XL Deploy** post-build action:

![image](images/jenkins-post-build-action.png)

This allows you to create an XL Deploy Deployment ARchive (DAR file). First, provide basic information about the application. You can use Jenkins variables in the fields; for example, the version is typically linked to the Jenkins `$BUILD_TAG` variable, as in `1.0.$BUILD_TAG`.

To add deployables to the package, select **Package Application**.

![image](images/jenkins-basic-information.png)

For artifacts, the **Location** field indicates where the artifact resides. For example, this can be the Jenkins workspace, a remote URI, or coordinates in a Maven repository.

![image](images/jenkins-package-application.png)

You can add additional properties that are required for each artifact or resource.

![image](images/jenkins-add-property.png) 

To publish the package to XL Deploy, select **Deploy application**. You can can select the generated package or a package from another location (that is, from the file system or from an artifact repository). Note that the application must exist in XL Deploy before you can publish a package.

Select the target environment where you want to deploy the package.

![image](images/jenkins-deploy-application.png)
