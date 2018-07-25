---
title: Introduction to the XL Deploy PowerShell plugin
categories:
xl-deploy
subject:
Bundled plugins
tags:
powershell
plugin
weight: 361
---

You can use the XL Deploy PowerShell plugin to create extensions and plugins that require PowerShell scripts to be executed on the target platform. For example, the XL Deploy plugins for Windows, Internet Information Services (IIS), and BizTalk were built on top of this plugin.

## PowerShell step batching

The PowerShell plugin allows one to enable batching of multiple PowerShell steps into a single step. This will improve the throughput of large deployments at the cost of less granular steps.

By default batching is disabled, but it can be enabled to setting the hidden property `powershell.BaseExtensiblePowerShellDeployed.batchSteps` (or the `batchSteps` property on one its subtypes) to `true`.

The maximum number of steps that will be included in one batch can be controlled with the hidden property `powershell.BaseExtensiblePowerShellDeployed.maxBatchSize` (or the `maxBatchSize` property on one of its subtypes).

In addition to these configurable options, the following restrictions are applied when batching steps:

1. Only PowerShell steps generated by the type `powershell.BaseExtensiblePowerShellDeployed` or one of its subtypes are batched.
1. Only steps that deploy to the same target container are batched.
1. Only steps with identical orders are batched.
1. Only steps that have identical 'verbs' are batched, e.g. 'Create appPool1 on iis' and 'Deploy website1 on iis' would not be batched, while 'Create appPool1 on iis' and 'Create website1 on iis' would be batched into 'Create appPool1, website1 on iis', provided they had the same order.
1. Steps that have `classpathResources` are never batched.
1. Even though at most `maxBatchSize` steps are batched together, the step description will never be longer than roughly 50 characters plus the name of the container.

## Hidden configuration item properties

Some configuration items in the PowerShell plugin includes hidden properties such as `uploadArtifactData`, `uploadClasspathResources`, `exposeDeployedApplication`, and `exposePreviousDeployed`. Normally, you cannot access hidden properties in a PowerShell script.

When creating a custom CI type that is based on a PowerShell CI, you can use the `createOptions` property to expose hidden properties. For example:

    <property name="createOptions" kind="set_of_string" default="uploadArtifactData, uploadClasspathResources, exposeDeployedApplication" hidden="true"/>

For a list of hidden properties for each CI, refer to the [PowerShell Plugin Manual](/xl-deploy/latest/powershellPluginManual.html).