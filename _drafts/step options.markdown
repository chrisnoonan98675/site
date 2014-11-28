---
title: Step options for generic-plugin
categories: 
- generic-plugin
tags:
- generic-plugin
---

Plugin writers that write a plugin based on the generic-plugin or the powershell-plugin have the option of specifying so-called *step options*. These control what data is sent along when performing a `CREATE`, `MODIFY`, `DESTROY` or `NOOP` deployment step defined by a CI type.


## What is a step option?

A step option specifies what extra resources are uploaded as part of performing a deployment step. Usually this is used whenever the step executes a script on a remote host. This script (or, more generally, the action to be performed) may need to have access to zero or more of the following additional resources:
 
* the artifact associated with this step, 
* external file(s), or
* resolved template(s).

Your type definition needs to specify the external files and templates involved by setting its `classpathResources` and `templateClasspathResources` properties. By default all classpath resources are uploaded and all templates are resolved and uploaded, irrespective of whether the deployment step is a `CREATE` step, a `MODIFY` step, a `DESTROY` step, or even a `NOOP` step.

However, together these resources may sometimes result in a large amount of data, especially if the artifact is large. For some steps it makes little sense to have all these resources uploaded. For example, creating the deployed on the target machine may involve executing a complex script that needs the artifact and some external files, and modifying it involves a template, but deleting the deployed is done by just removing a file from a fixed location. In that case it makes little sense to upload all the same stuff all the time, since not all of it is needed.

Step options allow you to specify what resources to upload before executing the step itself.


## What step options are available?

You can use the following step options:

* `none` - do not upload anything extra as part of this step
* `uploadArtifactData` - upload the artifact associated with this deployed into the working directory before executing this step
* `uploadClasspathResources` - upload the classpath resources, as specified by the deployed type, into the working directory when executing this step
* `uploadTemplateClasspathResources` - resolve the template classpath resources, as specified by the deployed type; and upload the result into the working directory when executing this step.


## When can my plugin CI types use step options?

Your plugin CI types can use step options whenever they inherit from one of the following generic-plugin or powershell-plugin deployed types:

* [generic.AbstractDeployed](xl-deploy/genericPluginManual.html#generic.AbstractDeployed)
* [generic.AbstractDeployedArtifact](xl-deploy/genericPluginManual.html#generic.AbstractDeployedArtifact)
* [generic.CopiedArtifact](xl-deploy/genericPluginManual.html#generic.CopiedArtifact)
* [generic.ExecutedFolder](xl-deploy/genericPluginManual.html#generic.ExecutedFolder)
* [generic.ExecutedScript](xl-deploy/genericPluginManual.html#generic.ExecutedScript)
* [generic.ExecutedScriptWithDerivedArtifact](xl-deploy/genericPluginManual.html#generic.ExecutedScriptWithDerivedArtifact)
* [generic.ManualProcess](xl-deploy/genericPluginManual.html#generic.ManualProcess)
* [generic.ProcessedTemplate](xl-deploy/genericPluginManual.html#generic.ProcessedTemplate)
* [powershell.BaseExtensiblePowerShellDeployed](xl-deploy/genericPluginManual.html#powershell.BaseExtensiblePowerShellDeployed)
* [powershell.ExtensiblePowerShellDeployed](xl-deploy/genericPluginManual.html#powershell.ExtensiblePowerShellDeployed)
* [powershell.ExtensiblePowerShellDeployedArtifact](xl-deploy/genericPluginManual.html#powershell.ExtensiblePowerShellDeployedArtifact)

These types provide the hidden `SET_OF_STRING` properties `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` that your type then inherits.


## What are the default step option settings for existing types?

XL Deploy comes with various pre-defined CI types based on the generic-plugin and the powershell-plugin, and several plugins a built upon these as well. The default settings for each `createOptions`, `modifyOptions`, `destroyOptions` and `noopOptions` are specified inside `conf/deployit-defaults.properties`. By default, each of these have all three of `uploadArtifactData`, `uploadClasspathResources` and `uploadTemplateClasspathResources` enabled.