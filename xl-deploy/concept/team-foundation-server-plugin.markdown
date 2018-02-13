---
title: Introduction to the Team Foundation Server XL Deploy plugin
categories:
- xl-deploy
subject:
- Team Foundation Server
tags:
- tfs
- microsoft
---

The XL Deploy plugin for Microsoft Team Foundation Server (TFS) plugin provides automated deployment functionality for TFS 2010, TFS 2012, and TFS 2013.

If you are using VSTS, TFS 2015 Update 2 or TFS 2017, please refer to [Visual Studio Team Services (VSTS) XL Deploy plugin](/xl-deploy/concept/team-foundation-server-2015-plugin.html).

To support the use of XL Deploy from a TFS installation, this plugin provides custom build activities to interface with XL Deploy, a sample build template, and an editor to help you modify the build script with custom XL Deploy build actions.

The build template extends the default build template with an XL Deploy-specific part that packages your software into an XL Deploy DAR archive, uploads it to a specified XL Deploy server, and lets XL Deploy deploy the software to a specified environment. This is achieved using custom activities that you can reuse in your own variants of the build template.

For version-specific information about the TFS plugin, refer to the [Team Foundation Server Plugin Reference](/xl-deploy-tfs-plugin/latest/tfsPluginManual.html).

## Features

* DAR packaging    
* Importing a DAR package into XL Deploy
* Having the imported DAR package deployed to a specified environment

## Background information about TFS

TFS combines a source control system, a build service, a bug tracking system, and more. The XL Deploy TFS plugin deals with the build system exclusively.

In TFS, you can have multiple project collections; however, the examples here assume that there is only a single one, as different project collections don't talk to each other.

In TFS, everything that is version-controlled is reference by a TFS path that starts with `$/`. For example, the *MvcMusicStore* project root would be `$/MvcMusicStore`.

### TFS workflows

A TFS build workflow is defined by an XAML file (XAML is an XML dialect). New TFS team projects receive a default set of XAML files, and the XL Deploy TFS plugin provides another set. These XAML files are templates for a TFS build and contain parameters to be set when a build is started.

You can set a build definition from the Team Explorer window by indicating an XAML file to use and entering default values for a specific build. It can be any XAML file anywhere in the project collection. For example, for *MvcMusicStore*, you may indicate that a build should use `$/NerdDinner/BuildDefinitions/NerdDinner_deployit.xaml`.

Therefore, an enterprise may have a kind of "common build infrastructure" project that contains XAML files. The XL Deploy-enabled workflow should be placed in such a project, so other projects can use it or clone from it.

### Custom activities

The XL Deploy-enabled build workflow uses custom activities to drive XL Deploy. These activities are executable code provided by a number of DLL files, which build agents must be able to find.

The Build Controller defines a TFS path where it looks for DLLs that contain custom activities. Usually that location is also in the common build project. Because it is a TFS location, the build agents know how to get the DLLs. You only have to place the DLLs in that location.

This also makes the DLLs available to Visual Studio, which requires them for the XL Deploy credentials. You may need to restart Visual Studio to load new DLLs. Visual Studio may not recognize new DLLs without being restarted; if you are unable to enter default credentials for TFS to use, restart Visual Studio.

After the XAML and DLLs are in place, you can use the custom activities in your customized build flows. Each activity takes some input parameters, and some have output parameters as well.

**Note:** The Build Controller is used for all projects in the project collection. Therefore, you should not allow it to point to sample projects such as *NerdDinner* or *MvcMusicStore* for custom activities, because this would make all other projects dependent on the sample projects.

## Configure TFS

The XL Deploy-enabling custom activities and their dependencies are contained in the DLL directory of the plugin. TFS must be able to find them in order to use them, so you must add these DLLs to the custom activities collection.

1. Open the TFS Administration console (Start Menu > All Programs > Microsoft Visual Studio Team Foundation Server 2013 > Team Foundation Server Administration Console).
1. On the Build Configuration screen, find the Build Controller for the project that is going to use XL Deploy, and click the 'Properties' link.
1. The Build Controller Properties screen will show a text box called 'Version control path to custom assemblies'. This points to a directory inside a TFS-managed project.
1. "Get" that project, go to the indicated directory, add the XL Deploy TFS plugin DLLs, and check them in.

In the BuildProcessTemplates directory of your team project, add the provided XL Deploy-enabled build template (`TfvcTemplate_XLDeploy.12.xaml`, `DefaultTemplate-WithDeployit.11.1.xaml` or `...10.xaml`). Then, set your team project's build process to use it.

## Create your own XL Deploy-enabled build template

If you want to write your own XL Deploy-enabled build template, you must use the custom activities that come with the TFS plugin. The following activities are available:

{:.table .table-striped}
| Activity | Description |
| -------- | ----------- |
| `GenerateDeployitContext` | Accepts the `DeployitURL`, `UserName` and `Password` as input parameters and verifies that the combination is correct. If it is, `GenerateDeployitContext` outputs a `DeployitContext` that holds a reference to the server. If the combination is not correct, `GenerateDeployitContect` fails the build. The `DeployitContext` is used in all other XL Deploy custom activities except `CreatePackage`. |
| `CreatePackage` | Creates a DAR archive from the generated artifacts and optionally outputs the `ApplicationName` as specified in the manifest. The DAR archive has the filename `PackagePath`. You can specify the location of the generated artifacts by setting `PackageDataRootPath`. The manifest will be retrieved from `ManifestPath`. Values for placeholder replacement can be provided via the `Dictionary` parameter. |
| `UploadPackage` | Uploads a package with filename `PackagePath` to XL Deploy, putting the XL Deploy-generated package ID into `PackageID`. |
| `CreateDeploymentTask` | Instructs XL Deploy to initiate a deployment of a given application version onto a given `EnvironmentName`. Both the `ApplicationName` and the `VersionId` must be given; the plugin checks whether the `ApplicationName` is already deployed on `EnvironmentName` and thus may result in either an initial or an upgrade deployment of the `VersionId`. The resulting XL Deploy task ID is output to `TaskId`. |
| `TaskController` | Is the general access route to task handling. It needs a `DeployitContext` and a `TaskId`, together with an `Action`. The `Action` must be specified as `[TaskAction.xxx]` where `xxx` is *Start*, *Abort*, *Archive*, *Stop*, or *Cancel*. |
| `WaitForTask` | Waits for task `TaskId` until its state is something other than *Queued*, *Executing*, *Aborting*, *Failing*, or *Stopping*. The final state is output to `FinalState`. You can specify how often TFS checks the task state by setting `RefreshInterval` to a .NET Timespan; for example, `[TimeSpan.FromSeconds(10)]`. |
| `GenerateRollbackTask` | Starts a rollback for task `DeploymentTaskId`. The ID of the resulting rollback is output to `RollbackTaskId`. |
| `GetTaskState` | Gets the task state for a given `TaskId`. |
| `LogSteps` | Takes a `TaskInfo` input parameter and logs the steps according to the specification in the additional parameters `ErrorStates`, `WarningStates`, `NormalStates`. Each of these takes a String containing a comma-separated list of TaskStates; each step in the TaskInfo will be logged at the appropriate level. If not present, `ErrorStates` will fallback to the single value "FAILED", `WarningStates` to "PENDING", and `NormalStates` to "" (i.e. no states will be logged at this level). |

These activities live in this XML namespace for TFS 2010:

    xmlns:xdw="clr-namespace:XebiaLabs.Deployit.Workflow40;assembly=XebiaLabs.Deployit.Workflow40"

This namespace for TFS 2012:

    xmlns:xdw="clr-namespace:XebiaLabs.Deployit.Workflow45;assembly=XebiaLabs.Deployit.Workflow45"

And for TFS 2013:

    xmlns:xdw="clr-namespace:XebiaLabs.Deployit.Workflow;assembly=XebiaLabs.XLDeploy.Workflow"

## Automatically deploy your application

After the TFS plugin is properly configured for your TFS build collection, you can auto-deploy built software projects by selecting an XL Deploy-enabled build template.

The XL Deploy TFS plugin contains a build template that is derived from the standard TFS default build template, and augments it with an XL Deploy piece that will combine the build artifacts with a manifest and upload the package to an XL Deploy instance. This template can be used out of the box or can be used as a basis to set up your own build process.

1. In Visual Studio, go to your project's build definitions (Team Explorer > Connect to your team project > select Builds).
1. Click New Build Definition or right-click one of the current build definitions and select 'Edit Build definition'.
1. Go to the 'Process' screen, find the box saying 'Build process template' and click Show Details. The location of the currently used build definition is shown.
1. This location is inside a TFS-controlled repository. Get the latest version, add the XL Deploy build template to the same directory, and check it in.
1. Now it should be possible to select the XL Deploy-enabled build definition for your project.
1. Enter the XL Deploy-specific build parameters under the new XL Deploy and XL Deploy Configuration headers (and others as required).

These XL Deploy-related settings have the following meanings:

* **Manifest Path**: This should be set to the TFS path of the manifest to be included; for example, `$/MyApplication/src/deployit-manifest.xml`. It is a TFS path rather than a local path so any TFS Build Agent can download and package the manifest and do an automated deployment.
* **Rollback on deployment failure**: Whether to have XL Deploy try to roll back a failed deployment.
* **Target Environment**: Where to deploy to if the build was successful. This must be a predefined XL Deploy environment specified by its name, without the 'Environment/' prefix.
* **XL Deploy credentials**: The username and password to use by default when trying to connect to XL Deploy.
* **XL Deploy Server URL**: Where XL Deploy can be found.

The settings you enter here will be used by all builds of the project, except the XL Deploy credentials, which can be overridden when queuing a build manually.

## Hide XL Deploy user credentials

The build agent must have the credentials to connect to XL Deploy. Security dictates that these credentials must not be exposed on casual inspection; therefore, they must not be hard-coded in the build template (this would also lead to inflexibility). However, setting them as `string`s in build parameters exposes the default values to anyone viewing the build definition, which may not be desirable.

The XL Deploy-enabled build template that is included with the TFS plugin solves this issue by using a build process parameter of custom type `DeployitCredentials` that holds the credentials, and by using a custom editor called `DeployitCredentialsEditor`. The editor uses a password text box for the password, so the value is never exposed.

This is achieved by setting an attribute on the process parameter metadata. On TFS 2010:

    Editor="XebiaLabs.Deployit.Workflow40.DeployitCredentialsEditor, XebiaLabs.Deployit.Workflow40"

On TFS 2012:

    Editor="XebiaLabs.Deployit.Workflow45.DeployitCredentialsEditor, XebiaLabs.Deployit.Workflow45"

And TFS 2013:

    Editor="XebiaLabs.Deployit.Workflow.DeployitCredentialsEditor, XebiaLabs.XLDeploy.Workflow"

If you do not set the editor, the password remains hidden, but you cannot change the value.
