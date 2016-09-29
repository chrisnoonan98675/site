---
title: Use a predefined step in a rule
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- step
- deployment
- planning
- plugin
since:
- XL Deploy 4.5.0
weight: 130
---

XL Deploy [rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured. Several XL Deploy plugins include predefined rules that you can use when writing rules.

## Predefined steps in standard plugins

The standard XL Deploy plugins contain the following predefined steps:

* `delete`: Deletes a file or directory on a remote host
* `jython`: Executes a Python script locally on the XL Deploy server
* `manual`: Allows you to incorporate a manual process as part of a deployment
* `noop`: A "dummy" step that does not perform any actions
* `os-script`: Executes a script on a remote host
* `powershell`: Executes a PowerShell script on the remote Microsoft Windows host
* `template`: Generates a file based on a FreeMarker template and uploads the file to a remote host
* `upload`: Copies a `udm.Artifact` to an `overthere.Host`
* `wait`: Freezes the deployment plan execution for a specified number of seconds

For information about step parameters and examples, refer to the [Steps Reference](/xl-deploy/latest/referencesteps.html).

## Predefined steps in other plugins

Other XL Deploy plugins can contain predefined steps; for example, the IBM WebSphere Application Server (WAS) plugin contains a [`wsadmin`](/xl-deploy-was-plugin/5.1.x/wasPluginManual.html#wsadmin) step that can execute a Python script via the Python terminal of a `was.Cell`.

For information about predefined steps that are included with other XL Deploy plugins, refer to the [reference manual](/xl-deploy/latest/) for the plugin that you are interested in.

## Calculated step parameters

For some predefined steps, XL Deploy calculates the values of parameters so you do not have to specify them (even for parameters that are required).

### Order of a step

The `order` parameter of a step is calculated as follows:

* If the scope is `pre-plan`, `post-plan`, or `plan`, the `order` is 50
* If the scope is `deployed` and:
    * The operation is `CREATE`, `MODIFY`, or `NOOP` and:
        * The deployed is a `udm.Artifact` configuration item (CI), the `order` is 70
        * The deployed is **not** a `udm.Artifact` CI, the `order` is 60
    * The operation is `DESTROY` and:
        * The deployed is a `udm.Artifact` CI, the `order` is 30
        * The deployed is **not** a `udm.Artifact` CI, the `order` is 40

The XL Deploy order convention is described in [Steps and steplists in XL Deploy](/xl-deploy/concept/steps-and-steplists-in-xl-deploy.html#steplist).

### Description of a step

The `description` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `description` is calculated based on the `operation`, the name of the `deployed`, and the name of the `container`.
* If the scope is not `deployed`, the `description` cannot be calculated automatically and must be specified manually.

### Target host

The `target-host` parameter of a step is calculated as follows:

* If the scope is `deployed` and:
    * `deployed.container` is of type `overthere.Host`, the `target-host` is set to `deployed.container`.
    * `deployed.container` is of type `overthere.HostContainer`, the `target-host` is set to `deployed.container.host`.
    * `deployed.container` has a property called `host`, the value of which is of type `overthere.Host`; then `target-host` is set to this value.
* In other cases, `target-host` cannot be calculated automatically and must be specified manually.

For more information about `overthere` CIs, refer to the [Remoting Plugin Reference](/xl-deploy/latest/remotingPluginManual.html).

### Artifact

In XL Deploy 4.5.3, XL Deploy 5.0.0, and later, the `artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type `udm.Artifact`, the `artifact` is set to `deployed`.
* In other cases, `artifact` cannot be calculated automatically and must be specified manually.

### Source artifact

In XL Deploy 4.5.2 and earlier, the `source-artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type `udm.Artifact`, the `source-artifact` is set to `deployed`.
* In other cases, `source-artifact` cannot be calculated automatically and must be specified manually.

### Contexts

Some steps have contexts such as `freemarker-context`, `jython-context` or `powershell-context`.

In XL Deploy 4.5.3, XL Deploy 5.0.0, and later, the context of a step is enriched with calculated variables as follows:

* If the scope is `deployed`, the context is enriched with a <a href="/xl-deploy/5.1.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html#getDeployed%28%29">deployed</a> instance that is accessible in a FreeMarker template by name `deployed`.
* If the scope is `deployed`, the context is enriched with a <a href="/xl-deploy/5.1.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html#getPrevious%28%29">previousDeployed</a> instance that is accessible in a FreeMarker template by name `previousDeployed`.
* In other cases, the context is not calculated automatically.

Note that depending on the operation, the `deployed` or `previousDeployed` might not be initialized. For example, if the operation is `CREATE`, the `deployed` is set, but `previousDeployed` is not set.

### FreeMarker context

In XL Deploy 4.5.2 and earlier, the `freemarker-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `freemarker-context` is enriched with a deployed instance that is accessible in a FreeMarker template by name deployed.
* In other cases, `freemarker-context` is not calculated automatically.

### Jython context

In XL Deploy 4.5.2 and earlier, the `jython-context` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `jython-context` is enriched with a deployed instance that is accessible in a python script by binding deployed.
* In other cases, `jython-context` is not automatically calculated.

## Create a custom step

If the predefined step types in XL Deploy do not provide the functionality that you need, you can define custom step types and create rules that refer to them. Refer to [Create a custom step for rules](/xl-deploy/how-to/create-a-custom-step-for-rules.html) for more information.
