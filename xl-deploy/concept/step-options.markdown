---
title: Step options for generic-plugin, powershell-plugin, and python-plugin
categories: 
- xl-deploy
tags:
- generic-plugin
- powershell-plugin
- python-plugin
---

Plugin writers that write a plugin based on the [generic-plugin](/xl-deploy/latest/genericPluginManual.html) or the [powershell-plugin](/xl-deploy/latest/powershellPluginManual.html) have the option of specifying so-called *step options*. These control what data is sent along when performing a `CREATE`, `MODIFY`, `DESTROY` or `NOOP` deployment step defined by a CI type. Also they control which variables are available in templates or scripts.


## What is a step option?

A step option specifies what extra resources are available as part of performing a deployment step. Usually this is used whenever the step executes a script on a remote host. This script (or, more generally, the action to be performed) may have zero or more of the following requirements:
 
* the artifact associated with this step needed in the step's workdir, 
* external file(s) in the workdir,
* resolved Freemarker template(s) in the workdir,
* details of the previously deployed artifact in a variable in the script context, or
* details of the deployed application in a variable in the script context

Your type definition needs to specify the external files and templates involved by setting its `classpathResources` and `templateClasspathResources` properties; see e.g. the [Generic Model Plugin Manual](/xl-deploy/latest/genericPluginManual.html#shellscript-delegate). Details of the previously deployed artifact and deployed application will be available automatically when applicable.


## Why should I need to set a step option?

For some types (notably the generic-plugin based ones), the default behavior is that all classpath resources are uploaded and all Freemarker templates are resolved and uploaded, irrespective of whether the deployment step is a `CREATE` step, a `MODIFY` step, a `DESTROY` step, or even a `NOOP` step. However, together these resources may sometimes result in a large amount of data, especially if the artifact is large. For some steps it makes little sense to have all these resources uploaded. For example, creating the deployed on the target machine may involve executing a complex script that needs the artifact and some external files, and modifying it involves a template, but deleting the deployed is done by just removing a file from a fixed location. In that case it makes little sense to upload all the same stuff all the time, since not all of it is needed.
 
Step options allow you to specify what resources to upload before executing the step itself, by setting the `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` properties on the type.

Also you might have a deployment script that needs to refer to the previous deployed, or have information about the deployed application. These can be made available by setting the proper step options.


## What step options are available?

Depending on whether you use generic-plugin or powershell-plugin different step options are available. Regarding the python-plugin, [see below](#what-about-the-python-plugin) Following options are available for both:

* `none` - do not upload anything extra as part of this step. Can also be used to unset step options from a supertype.
* `uploadArtifactData` - upload the artifact associated with this deployed into the working directory before executing this step.
* `uploadClasspathResources` - upload the classpath resources, as specified by the deployed type, into the working directory when executing this step.

The following additional step option is only available in the generic-plugin:

* `uploadTemplateClasspathResources` - resolve the template classpath resources, as specified by the deployed type; then upload the result into the working directory when executing this step.

The following additional step options are only available in the powershell-plugin:

* `exposePreviousDeployed` - add the `previousDeployed` variable into the Powershell context. This variable points to the previous version of the deployed configuration item, which must not be null.
* `exposeDeployedApplication` - add the `deployedApplication` variable into Powershell context, which describes the version, environment and deployeds of the currently deployed application. You can check [udm.DeployedApplication](/xl-deploy/latest/udmcireference.html#udm.DeployedApplication) for more details.


## When can my plugin CI types use step options?

Your plugin CI types can use step options whenever they inherit from one of the following generic-plugin or powershell-plugin deployed types:

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

These types provide the hidden `SET_OF_STRING` properties `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` that your type then inherits.


## What are the default step option settings for existing types?

XL Deploy comes with various pre-defined CI types based on the generic-plugin and the powershell-plugin, and several plugins are built upon these as well. The default settings for each `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` can be looked up in the documentation of the various types in the [generic-plugin](/xl-deploy/latest/genericPluginManual.html) and the [powershell-plugin](/xl-deploy/latest/powershellPluginManual.html). You override the default settings in your type definitions in `synthetic.xml`, or - if you know what you're doing - change the defaults altogether in `conf/deployit-defaults.properties`.


## What about the Python plugin?

In the python-plugin there is no notion of step options. There is however a property of [python.PythonManagedDeployed](/xl-deploy/latest/pythonPluginManual.html#python.PythonManagedDeployed) similar to one of step options of the powershell-plugin:

* `exposeDeployedApplication` - add the `deployedApplication` object to the Python context ([udm.DeployedApplication](/xl-deploy/latest/udmcireference.html#udm.DeployedApplication)).

There are no additional classpath resources in the python-plugin, so only the current deployed is uploaded into a working directory before executing the Python script.