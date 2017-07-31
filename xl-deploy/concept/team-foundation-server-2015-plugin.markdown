---
title: Introduction to the Visual Studio Team Services (VSTS) / Team Foundation Server (TFS) XL Deploy plugin
categories:
- xl-deploy
subject:
- Team Foundation Server
tags:
- tfs
- vsts
- microsoft
since:
- XL Deploy 5.1.0
---

As of version 7.0.0 the Team Foundation Server 2015 XL Deploy plugin has been renamed to the Visual Studio Team Services (VSTS) XL Deploy plugin.

The XL Deploy extension for Visual Studio Team Services (VSTS) provides automated deployment functionality through an XL Deploy build task for [Microsoft TFS 2015](https://msdn.microsoft.com/en-us/Library/vs/alm/Build/feature-overview), [Microsoft TFS 2017](https://www.visualstudio.com/en-us/news/releasenotes/tfs2017-update1), and [Visual Studio Team Services](https://www.visualstudio.com/en-us/products/visual-studio-team-services-vs.aspx) (VSTS), which is also known as Visual Studio Online.

The plugin provides custom build activities to interface with XL Deploy, a sample build template, and an editor to help you modify the build script with custom build actions.

The build template extends a default build template with an XL Deploy-specific part that packages your software in an XL Deploy deployment package (DAR file), uploads the DAR file to an XL Deploy server, and allows XL Deploy to deploy the software to an environment. You can reuse these custom activities in your own variants of the build template.

Also refer to:

* [Visual Studio Team Services Plugin Reference](/xl-deploy-vsts-xld-plugin/latest/tfs2015PluginManual.html)
* [The XL Deploy plugin on the Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=xebialabs.tfs2015-xl-deploy-plugin)
* [Introduction to the Team Foundation Server plugin](/xl-deploy/concept/team-foundation-server-plugin.html) for the XL Deploy plugin for TFS 2010, TFS 2012, and TFS 2013

## Requirements

* Team Foundation Server 2015 Update 2 or later, or Visual Studio Team Services
* XL Deploy version 5.1.0 and later

**Note:** If you are using TFS 2015 or TFS 2015 Update 1, the Team Foundation Server 2015 version of the plugin is still available.

## Supported versions

{:.table .table-striped}
| VSTS/TFS versions | Plugin |
| ----------------- | ------ |
| TFS 2010, 2012, 2013 | [Team Foundation Server plugin](/xl-deploy/concept/team-foundation-server-plugin.html) |
| TFS 2015 or TFS 2015 Update 1 | Team Foundation Server 2015 version of the plugin |
| TFS 2015 Update 2, 2017, VSTS | Visual Studio Team Services (VSTS) plugin |

## Features

- Create a deployment package (DAR file)
- Import a deployment package into XL Deploy
- Upload a deployment package to TFS
- Version a deployment package
- Deploy an imported package to a specified environment

## Install the XL Deploy build task

Before using the XL Deploy build task for TFS 2015, TFS 2017 or Visual Studio Team Services, ensure that you have installed the build task as described in [Install a build task in TFS](/xl-deploy/how-to/install-a-build-task-in-tfs-2015.html).

Also, ensure that you are not searching for the custom activities used for XAML builds. For information about custom activities, refer to the [documentation on the XL Deploy plugin for earlier versions of TFS](/xl-deploy/concept/team-foundation-server-plugin.html).

**Tip:** You can use the [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=xebialabs.tfs2015-xl-deploy-plugin) to install the XL Deploy extension in VSTS.

## Using the XL Deploy build task

After you install the XL Deploy build task, it is available in the **Deploy** category. In your build definition, add a new task and search for the **XL Deploy** task.

![Add XL Deploy task](images/tfs_2015_plugin_add_task.png)

Ensure that the task is added after the build steps that create the artifacts that you want to package.

The task has the following options:

![XL Deploy task options](images/tfs_2015_plugin_task_options.png)

### Manifest path

Set the **Manifest Path** to the TFS path of the manifest to be included; for example, `$/MyApplication/src/deployit-manifest.xml`. The deployment package will be created based on the information that is present in the manifest file indicated in this option.

The manifest path is a TFS path, not a local path, so that any TFS Build Agent can download and package the manifest and perform an automated deployment. Click **...** to use the file explorer to select the manifest file.

### XL Deploy endpoint

An endpoint is a pointer to an XL Deploy instance. Select the instance that you want to use from the **XL Deploy Endpoint** list.

If this is the first task you are configuring, you must add a new endpoint. If you have other build definitions that use XL Deploy tasks, you can select an endpoint that is already defined.

For information about adding an XL Deploy endpoint, refer to [Add an endpoint in Team Foundation Server 2015](/xl-deploy/how-to/add-an-endpoint-in-tfs-2015.html).

### Publish artifacts to TFS

To upload the artifact after a DAR file is generated, select **Publish the artifacts to TFS**. This allows Microsoft Release Management to pick up the artifact for further processing.

### Version

If **Version** is selected, the version in the manifest file will be updated. You do not need to set a placeholder in the manifest; the build task will process the manifest file, search for the version attribute, and set its value to the build number.

You can define the build number format on the **General** tab of the build definition. If you would like to assign a different value as the package version, set the **Version value override** under [Advanced options](#advanced-options).

### Deploy

Select **Deploy** to configure the build task to trigger a deployment for the imported package. Additional options will appear.

When selected extra options are shown. By selecting it, you are setting your build task to trigger a deployment for the just imported package.

![Deploy options](images/tfs_2015_plugin_deploy_options.png)

In **Target Environment** box, enter the name of the environment to use for deployment, without the *Environments* root; for example, *Production/PROD01*.

If you want XL Deploy to roll back the deployment if it fails, select **Rollback on deployment failure**.

## Advanced options

The **Advanced options** selection allows you to override the default values of certain parameters.

![Advanced options](images/tfs_2015_plugin_advanced_options.png)

### Output Folder

By default, the folder where the DAR file is created is the build staging folder. If you want the DAR file to be placed in a different location, specify the path in **Output Folder**.

### Package root folder

Each time a package is created, relative paths are collected from the manifest file. The build task assumes that each relative path has a build staging folder as a root. If your deployment files are located elsewhere, specify the root path to be used in the **Package root folder** box.

### Version value override

If **Version** is selected, the version in the manifest file will be updated. By default, the build number is used. To override this, set the **Version value override** to a custom value.
