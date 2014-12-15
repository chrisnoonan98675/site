---
title: Step options for the Generic, PowerShell, and Python plugins
categories: 
- xl-deploy
subject:
- Plugins
tags:
- plugin
- powershell
- python
---

If you create a plugin based on the [Generic](/xl-deploy/latest/genericPluginManual.html) or [PowerShell](/xl-deploy/latest/powershellPluginManual.html) plugin, you can specify *step options* that control the data that is sent when performing a `CREATE`, `MODIFY`, `DESTROY` or `NOOP` deployment step defined by a configuration item (CI) type. Step options also control the variables that are available in templates or scripts.

## What is a step option?

A step option specifies the extra resources that are available when performing a deployment step. A step option is typically used when the step executes a script on a remote host. This script (or, more generally, the action to be performed) may have zero or more of the following requirements:
 
* The artifact associated with this step needed in the step's `workdir`
* External file(s) in the `workdir`
* Resolved FreeMarker template(s) in the `workdir`
* Details of the previously deployed artifact in a variable in the script context
* Details of the deployed application in a variable in the script context

The type definition must specify the external files and templates involved by setting its `classpathResources` and `templateClasspathResources` properties; for example, see the [`shellScript` delegate in the Generic plugin](/xl-deploy/latest/genericPluginManual.html#shellscript-delegate). Information about the previously deployed artifact and deployed application are automatically available when applicable.

## When are step options needed?

For some types, especially types based on the Generic plugin, the default behavior is that all classpath resources are uploaded and all FreeMarker templates are resolved and uploaded, regardless of the deployment step type. However, these resources may result in a large amount of data, especially if the artifact is large. For some steps, you may not need to upload all resources.

For example, creating the deployed on the target machine may involve executing a complex script that needs the artifact and some external files, and modifying it involves a template, but deleting the deployed is done by just removing a file from a fixed location. In that case, it is not necessary to upload everything each time, because it is not all needed.

Step options allow you to use the `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` properties on the type to specify the resources to upload before executing the step itself.

Also, you might have a deployment script that needs to refer to the previous deployed, or needs to have information about the deployed application. You can make this information available by setting the proper step options.

## What step options are available?

### Generic plugin and PowerShell plugin options

The following step options are available for the Generic plugin and PowerShell plugin:

* `none`: Do not upload anything extra as part of this step. You can also use this option to unset step options from a supertype.
* `uploadArtifactData`: Upload the artifact associated with this deployed to the working directory before executing this step.
* `uploadClasspathResources`: Upload the classpath resources, as specified by the deployed type, to the working directory when executing this step.

## Generic plugin options

The following additional step option is available in the Generic plugin:

* `uploadTemplateClasspathResources`: Resolve the template classpath resources, as specified by the deployed type, then upload the result into the working directory when executing this step.

## PowerShell plugin options

The following additional step option is available in the PowerShell plugin:

* `exposePreviousDeployed`: Add the `previousDeployed` variable to the PowerShell context. This variable points to the previous version of the deployed configuration item, which must not be null.
* `exposeDeployedApplication`: Add the `deployedApplication` variable to PowerShell context, which describes the version, environment, and deployeds of the currently deployed application. Refer to the [`udm.DeployedApplication`](/xl-deploy/latest/udmcireference.html#udm.DeployedApplication) CI for more information.

## When can my plugin CI types use step options?

Your plugin CI types can use step options whenever they inherit from one of the following Generic plugin or PowerShell plugin deployed types:

* [generic.AbstractDeployed](/xl-deploy/latest/genericPluginManual.html#generic.AbstractDeployed)
* [generic.AbstractDeployedArtifact](/xl-deploy/latest/genericPluginManual.html#generic.AbstractDeployedArtifact)
* [generic.CopiedArtifact](/xl-deploy/latest/genericPluginManual.html#generic.CopiedArtifact)
* [generic.ExecutedFolder](/xl-deploy/latest/genericPluginManual.html#generic.ExecutedFolder)
* [generic.ExecutedScript](/xl-deploy/latest/genericPluginManual.html#generic.ExecutedScript)
* [generic.ExecutedScriptWithDerivedArtifact](/xl-deploy/latest/genericPluginManual.html#generic.ExecutedScriptWithDerivedArtifact)
* [generic.ManualProcess](/xl-deploy/latest/genericPluginManual.html#generic.ManualProcess)
* [generic.ProcessedTemplate](/xl-deploy/latest/genericPluginManual.html#generic.ProcessedTemplate)
* [powershell.BasePowerShellDeployed](/xl-deploy/latest/powershellPluginManual.html#powershell.BasePowerShellDeployed)
* [powershell.BaseExtensiblePowerShellDeployed](/xl-deploy/latest/powershellPluginManual.html#powershell.BaseExtensiblePowerShellDeployed)
* [powershell.ExtensiblePowerShellDeployed](/xl-deploy/latest/powershellPluginManual.html#powershell.ExtensiblePowerShellDeployed)
* [powershell.ExtensiblePowerShellDeployedArtifact](/xl-deploy/latest/powershellPluginManual.html#powershell.ExtensiblePowerShellDeployedArtifact)

These types provide the hidden `SET_OF_STRING` properties `createOptions`, `modifyOptions`, `destroyOptions`, and `noopOptions` that your type inherits.

## What are the default step option settings for existing types?

XL Deploy comes with various predefined CI types based on the Generic plugin and the PowerShell plugin. Several plugins are built upon these, as well. For the default settings for `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions`, refer to the CI documentation in the [Generic Plugin Manual](/xl-deploy/latest/genericPluginManual.html) and the [PowerShell Plugin Manual](/xl-deploy/latest/powershellPluginManual.html).

You can override the default settings in the type definitions in the `synthetic.xml` file. Also, you can change the defaults in the `conf/deployit-defaults.properties` file.

## Step options in the Python plugin?

The Python plugin does not have step options. However, the [`python.PythonManagedDeployed`](/xl-deploy/latest/pythonPluginManual.html#python.PythonManagedDeployed) CI has a property that is similar to one of the PowerShell step options:

* `exposeDeployedApplication`: Add the `deployedApplication` object to the Python context ([`udm.DeployedApplication`](/xl-deploy/latest/udmcireference.html#udm.DeployedApplication)).

There are no additional classpath resources in the Python plugin, so only the current deployed is uploaded to a working directory the Python script is executed.
