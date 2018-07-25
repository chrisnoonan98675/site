---
title: Introduction to the XL Deploy Generic plugin
categories:
xl-deploy
subject:
Bundled plugins
tags:
plugin
generic
weight: 356
---

XL Deploy supports a number of middleware platforms. Sometimes, though, it is necessary to extend XL Deploy with new middleware support. The Generic Model plugin provides a way to do this, without having to write Java code. Instead, using XL Deploy's flexible type system and the base CIs from the Generic Model plugin, new CIs can be defined by writing XML and providing scripts for functionality.

Several of XL Deploy's standard plugins are also built on top of the Generic Model plugin.

## Features

* Define custom containers
    * Stop, start, restart capabilities
* Define and copy custom artifacts to a custom container
* Define, copy and execute custom scripts and folders on a custom container
* Define resources to be processed by a template and copied to a custom container
* Define and execute control tasks on containers and deployeds
* Flexible templating engine

## Plugin concepts

The Generic Model plugin provides several CIs that can be used as base classes for creating XL Deploy extensions. There are base CIs for each of XL Deploy's CI types (deployables, deployeds and containers). A typical usage scenario is to create custom, synthetic CIs (based on one of the provided CIs) and using it to invoke the required behavior (scripts) in a deployment plan.

**Note:** Since Deployit 3.6, the deployeds in the Generic Model Plugin can target containers that implement the `overthere.HostContainer` interface. In addition to the `generic.Container` and derived CIs, this means they can also be targeted to CIs derived from `overthere.Host`.

### Container

A `generic.Container` is a topology CI and models middleware in your infrastructure. This would typically be used to model middleware within XL Deploy that does not have out of the box support or is custom to your environment. The other CIs in the plugin can be deployed to (subclasses of) the container. The behavior of the container in a deployment is configured by specifying scripts to be executed when it is started, stopped or restarted. XL Deploy will invoke these scripts as needed.

### Nested container

A `generic.NestedContainer` is a topology CI and models middleware in your infrastructure. The nested container allows for the modelling of abstract middleware concepts as containers to which items can be deployed.

### Copied artifact

A `generic.CopiedArtifact` is  an artifact as copied over to a `generic.Container`. It manages the copying of any generic artifact (`generic.File`, `generic.Folder`, `generic.Archive`, `#generic.Resource`) in the deployment package to the container. It is possible to indicate that this copied artifact requires a container restart.

### Executed script

An `generic.ExecutedScript` encapsulates a script that is executed on a `generic.Container`. The script is processed by the templating engine (see below) before being copied to the target container. The behavior of the script is configured by specifying scripts to be executed when it is deployed, upgraded or undeployed.

### Manual process

A `generic.ManualProcess` entails a script containing manual instructions for the operator to perform before the deployment can continue. The script is processed by the templating engine (see below) and is displayed to the operator in the step logs. Once the instructions have been carried out, the operator can continue the deployment.  The instructions can also be automatically emailed.

### Executed folder

An `generic.ExecutedFolder` encapsulates a folder containing installation and rollback scripts that are executed on a `generic.Container`. Installation scripts are executed when the folder is deployed or updated, rollback scripts are executed when it is undeployed. Execution of the scripts happens in order. Scripts are processed by the templating engine before being copied to the target container.

### Processed template

A `generic.ProcessedTemplate` is a [FreeMarker](http://freemarker.sourceforge.net/) template that is processed by the templating engine and then copied to a `generic.Container`. For information about the templating engine, refer to [Templating in the XL Deploy Generic plugin](/xl-deploy/concept/templating-in-the-xl-deploy-generic-plugin.html).

### Control task delegates

For information about control task delegates, refer to [Control task delegates in the XL Deploy Generic plugin](/xl-deploy/concept/control-task-delegates-in-the-xl-deploy-generic-plugin.html).
