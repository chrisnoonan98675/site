---
title: Create a deployment package
subject:
Packaging
categories:
xl-deploy
tags:
application
package
deployment
weight: 200
no_index: true
---

<html>
<div id="userMap">

            <div class="content"><a href="preparing-your-application-for-xl-deploy.html"><div class="box box1">Preparing your application for XL Deploy</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="whats-in-a-deployment-package.html"><div class="box box2">What's in an application deployment package?</div></a></div>

            <div class="arrow">→</div>

            <div class="content" id="activeBox"><a href="create-a-deployment-package.html"><div class="box box3">Create a deployment package</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-and-verify-the-deployment-plan.html"><div class="box box3">Create and verify the deployment plan</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="troubleshoot-the-deployment-plan.html"><div class="box box5">Troubleshoot the deployment plan</div></a></div>

<div class="clearfix"></div>
</div>
</html>





There are several ways that you can create a deployment package:

* Using the XL Deploy interface
* Using a plugin for a tool such as Maven or Jenkins
* Using a command line tool such as `zip`

### Environment-independent packages

To make the deployables in your package environment-independent:

1. Use placeholders for values that are specific to a certain environment, such as database credentials.
2. Create sets of key-value pairs called *dictionaries*, which contain environment-specific values and associate them with the appropriate environments.

When you import the deployment package or create it in the XL Deploy interface, XL Deploy scans the deployables for placeholders. When you execute the deployment, XL Deploy replaces the placeholders with the values in the dictionary.

#### Add placeholders to deployables

Review the components of your application for values that are environment-specific and replace them with placeholders. A placeholder is surrounded by two sets of curly brackets. For example:

    jdbc.url=jdbc:oracle:thin:{% raw %}{{DB_USERNAME}}{% endraw %}/{% raw %}{{DB_PASSWORD}}{% endraw %}@dbhost:1521:orcl

#### Create a dictionary

To create a dictionary that defines values that are specific to an environment:

1. In the XL Deploy interface, go to the **Explorer**.
1. Hover over **Environments**, click ![Menu button](../images/menuBtn.png), and select **New** > **Dictionary**. The dictionary properties appear.
1. Enter a name for the dictionary in the **Name** field.
1. On the **Common** tab, click **Add new row** to add entries to the dictionary.
1. Under **Key**, enter a placeholder that you are using in the application, without brackets (DB_USERNAME and DB_PASSWORD from the example above).
1. Under **Value**, enter the value that XL Deploy should replace the placeholder with when you deploy the application to the target environment.
1. Click **Save**.
1. Double-click the environment that will use the newly created dictionary. The environment properties appear.
1. On the **Common** tab, select the dictionary you created.
1. Click **Save**.

When you execute a deployment to this environment, XL Deploy replaces the placeholders with the values that you defined. For example:

    jdbc.url=jdbc:oracle:thin:scott/tiger@dbhost:1521:orcl

### Create a deployment package in the XL Deploy interface

Creating a deployment package in the XL Deploy interface is an easy way to see what makes up a DAR file and what a manifest file looks like. To create a deployment package, refer to [Create a deployment package using the XL Deploy GUI](/xl-deploy/how-to/create-a-deployment-package-using-the-ui.html).

#### Export the deployment package

After you create a deployment package in the XL Deploy interface, hover over the package, click ![Menu button](images/menuBtn.png), and select **Export**. The DAR file is downloaded to your computer.

To open the DAR file, change the file extension to ZIP, then open it with a file archiving program. In the package, you will see the artifacts that you uploaded when creating the package and a manifest file called `deployit-manifest.xml`. The manifest file contains:

* General information about the package, such as the application name and version
* References to all artifacts and resource definitions in the deployment package

For more information, refer to [XL Deploy manifest format](/xl-deploy/concept/xl-deploy-manifest-format.html).

For Windows environments, there is a Manifest Editor that can help you create and edit `deployit-manifest.xml` files. For information about using this tool, refer to [GitHub](https://github.com/xebialabs-community/xld-manifest-editor).

### Create a deployment package using an XL Deploy plugin

XL Deploy includes plugins that enable you to automatically build packages as part of your delivery pipeline. Some of the plugins that are available are:

* [Maven](/xl-deploy/latest/maven-plugin/index.html)
* [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin)
* [Bamboo](/xl-deploy/latest/bamboo-plugin/index.html)
* [Team Foundation Server (TFS)](/xl-deploy/latest/tfs-plugin/index.html)

### Create a deployment package using a command line tool

Even if you aren’t using a build tool or CI tool, you can create DARs automatically as part of your build process. Because a DAR is simply a ZIP file that contains an XL Deploy manifest file in the root folder, you can use a command line tool to build it. Examples of such tools are:

* `zip`
* Java `jar` utility
* Maven `jar` plugin
* Ant `jar` task

### Import a deployment package

To deploy a package that you have created to a target environment, you must first make the package available to the XL Deploy server. You can do so by publishing the package from a build tool or by manually importing the package.

The tools listed above can automatically publish deployment packages to an XL Deploy server. You can also publish packages through the XL Deploy user interface, the command line, or a Web request to the XL Deploy HTTP API.

#### Import a deployment package using the XL Deploy interface

You can import deployment packages from the XL Deploy server or from a location that is accessible via a URL, such as a CI server or an artifact repository such as Archiva, Artifactory, or Nexus. For information about importing a deployment package, refer to [Add a package to XL Deploy](/xl-deploy/how-to/add-a-package-to-xl-deploy.html).
