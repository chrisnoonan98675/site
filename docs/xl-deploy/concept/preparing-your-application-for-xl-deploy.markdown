---
title: Preparing your application for XL Deploy
subject:
- Packaging
categories:
- xl-deploy
tags:
- application
- package
- deployment
weight: 200
---

XL Deploy uses the Unified Deployment Model (UDM) to structure deployments. In this model, deployment packages are containers for complete application distribution. They include application artifacts (EAR files, static content) as well as resource specifications (datasources, topics, queues, and so on) that the application needs to run.

A Deployment ARchive, or DAR file, is a ZIP file that contains application files and a manifest file that describes the package content. In addition to packages in a compressed archive format, XL Deploy can also import _exploded DARs_ or archives that have been extracted.

Packages should be independent of the target environment and contain customization points (for example, placeholders in configuration files) that supply environment-specific values to the deployed application. This enables a single artifact to make the entire journey from development to production.

## What's in an application deployment package?

An application deployment package contains *deployables*, which are:

* The physical files (artifacts) that make up a specific version of the application; for example, an application binary, configuration files, or web content
* The middleware resource specifications that are required for the application; for example, a datasource, queue, or timer configuration

The deployment package should contain everything that your application needs to run and that should be removed if your application is undeployed (that is, not resources that are shared among multiple applications).

### Deployment commands and scripts

Generally, the deployment package for an application should not contain deployment commands or scripts. When you prepare a deployment in XL Deploy, a *deployment plan* is automatically generated. This plan contains all of the steps that are needed to deploy your application to a target environment.

### Environment-specific values

An *environment* is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

A deployment package should be independent of the environment in which it will be deployed. This means that the deployables in the package should not contain environment-specific values. You can think of the deployment package as a template for the deployed application. XL Deploy supports placeholders for environment-specific values; these are discussed later in this article.

## Deploying shared resources

You may have resources that are shared by more than one application. You should package these resources so that XL Deploy can deploy them; however, you should not include them in the deployment package for an individual application that uses them. Instead, you should create a deployment package that contains shared resources and use placeholders to refer to these shared resources from your application packages.

## Understanding deployable types

Every deployable in a package has a *configuration item (CI) type* that:

* Describes the deployable, and
* Determines the steps that XL Deploy will add to the deployment plan when you map the item to a target *container*

The plugins that are included in your XL Deploy installation determine the CI types that are available for you to use.

### Exploring CI types

Before you create a deployment package, you should explore the CI types that are available. To do so in the XL Deploy interface, first import a sample deployment package:

1. Go to **Explorer**.
1. Hover over **Applications**, click ![Menu button](images/menuBtn.png), and select **Import** > **From XL Deploy server**.
1. Select the **PetClinic-ear/1.0** sample package.
1. Click **Import**. XL Deploy imports the package.
1. Click **Close**.
1. Click ![image](images/refresh_button.png) to refresh the CI Library.
1. Expand an application, hover over a deployment package, click ![Menu button](images/menuBtn.png), and select **New** to see the CI types that are available.

### How do I know which type to use?

In most cases, the CI types that you need to use are straightforwardly determined by the components of your application and by the target middleware. XL Deploy also includes types for common application components such as files that simply need to be moved to target servers.

For each type, you can specify properties that represent attributes of the artifact or resource to be deployed, such as the target location for a file or a JDBC connection URL for a datasource. If the value of a property is the same for all target environments, you can set the value in the deployment package itself.

If the value of a property varies across your target environments, you should use a placeholder for it. XL Deploy automatically resolves placeholders based on the environment to which you are deploying the package.

## Create a deployment package

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
1. Hover over **Environments**, click ![Menu button](images/menuBtn.png), and select **New** > **Dictionary**. The dictionary properties appear.
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

## Create and verify the deployment plan

Every plugin that is installed can contribute steps to the deployment plan. When XL Deploy creates the plan, it integrates these steps to ensure that the plugins work together correctly and the steps are in the right order.

To preview the deployment plan that XL Deploy will generate for your application, create a deployment plan and verify the steps.

### Check the target environment

Before you can create a deployment plan, the target environment for the deployment must be configured. To see the environments that have been defined in XL Deploy, go to **Repository** and expand **Environments**.

To verify the members of your target environment, double-click it and review its properties. The **Members** list shows the infrastructure items that make up the environment. If your target environment is not yet defined in XL Deploy, you can create it by right-clicking **Environments** and selecting **New**.

If the infrastructure members that make up your target environment are not available in the Repository, you can add them by either:

* Using the XL Deploy [Discovery feature](/xl-deploy/how-to/discover-middleware.html)
* Manually [adding the required configuration items](/xl-deploy/how-to/working-with-configuration-items.html#create-a-new-ci)

### Create the deployment plan

To create the deployment plan:

1. Click **Start a deployment**.
2. Under **Applications**, expand your application.
3. Select the desired version of your application and drag it to the left side of the Deployment Workspace.
4. Under **Environments**, select the environment where your application should be deployed and drag it to the right side of the Deployment Workspace.
5. Click ![image](images/button_auto-map-new.png) to automatically map your application’s deployables to containers in the environment.
6. Double-click each mapped deployable to verify that its properties are configured as expected. Here, you can see the placeholders that XL Deploy found in your deployment package and the values that it will assign to them during the deployment process.

    ![Deployed properties](images/deployment-workspace-deployed-properties-new.png)

7. Click **Preview** at to [Preview the deployment plan](/xl-deploy/how-to/preview-the-deployment-plan.html).
8. Review the steps in the Preview pane.
9. Optionally double click the step to preview the commands that XL Deploy will use to execute the step.
10. Click **Close preview** to return to the Deployment Workspace.

## Troubleshoot the deployment plan

When XL Deploy creates the deployment plan, it analyzes and integrates the steps that each plugin contributes to the plan. If the deployment plan that XL Deploy generates for you does not contain the steps that are needed to deploy your application correctly, you can troubleshoot it using several different features.

### Adjust the deployment plan

You may be able to achieve the desired deployment behavior by:

* Adjusting the properties of the CI types that you are using
* Using different CI types
* Creating a new CI type

To check the types that are available and their properties, follow the instructions provided in *Exploring CI types*. The documentation for each plugin describes the actions that are linked to each CI type.

If you cannot find the CI type that you need for a component of your application, you can add types by creating a new plugin.

### Configure an existing plugin

You can configure your plugins to change the deployment steps that it adds to the plan or to add new steps as needed.

For example, if you deploy an application to a JBoss or Tomcat server that you have configured for hot deployments, you are not required to stop the server before the application is deployed or start it afterward. In the [JBoss Application Server plugin reference documentation](/xl-deploy/latest/jbossPluginManual.html) and [Tomcat plugin reference documentation](/xl-deploy/latest/tomcatPluginManual.html), you can find the `restartRequired` property for `jbossas.EarModule`, `tomcat.WarModule`, and other deployable types. The default value of this property is `true`. To change the value:

1. Set `restartRequired` to `false` in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.
2. Restart the XL Deploy server to load the new configuration setting.
3. Create a deployment that will deploy your application to the target environment. You will see that the server stop and start steps do not appear in the deployment plan that is generated.

For more detailed information about how XL Deploy creates deployment plans, refer to the [Understanding the packaging phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html). For information about configuring the plugin you are using, refer to its manual in the XL Deploy documentation.

### Create a new plugin

To deploy an application to middleware for which XL Deploy does not already offer content, you can create a plugin by defining the CI types, rules, and actions that you need for your environment. In a plugin, you can define:

* New container types, which are types of middleware that can be added to a target environment
* New artifact and resources types that you can add to deployment packages and deploy to new or existing container types
* Rules that indicate the steps that XL Deploy should execute when you deploy the new artifact and resource types
* [Control tasks](/xl-deploy/how-to/using-control-tasks-in-xl-deploy.html) that define housekeeping actions you can perform on new or existing container types

You can define rules and control tasks in an XML file. Implementations of new steps use your preferred automation for your target systems. No specialized scripting language is required.
