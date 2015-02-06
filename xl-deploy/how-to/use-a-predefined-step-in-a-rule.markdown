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
- 4.5.0
---

The plugins that are bundled with XL Deploy contain predefined steps that you can use in rules..

## Calculated step parameters

For some predefined steps, XL Deploy calculates the values of parameters so you do not have to specify them (even for parameters that are required).

### Order of a step

The `order` parameter of a step is calculated as follows:

* If the scope is `pre-plan`, `post-plan`, or `plan`, the `order` is 50
* If the scope is `deployed` and:
    * The operation is `CREATE`, `MODIFY`, or `NOOP` and:
        * The deployed is an [`udm.Artifact`](/xl-deploy/latest/udmcireference.html), the `order` is 70
        * The deployed is **not** an `udm.Artifact`, the `order` is 60
    * The operation is `DESTROY` and:
        * The deployed is an `udm.Artifact`, the `order` is 30
        * The deployed is **not** an `udm.Artifact`, the `order` is 40

The XL Deploy order convention is described in the [Customization Manual](/xl-deploy/latest/customizationmanual.html#deployed-ci-processing).

### Description of a step

The `description` parameter of a step is calculated as follows:

* If the scope is `deployed`, the `description` is calculated based on the `operation`, the name of the `deployed`, and the name of the `container`.
* If the scope is not `deployed`, the `description` cannot be calculated automatically and must be specified manually.

### Target host

The `target-host` parameter of a step is calculated as follows:

* If the scope is `deployed` and:
    * `deployed.container` is of type [`overthere.Host`](/xl-deploy/latest/remotingPluginManual.html#overthere.Host), the `target-host` is set to `deployed.container`.
    * `deployed.container` is of type [`overthere.HostContainer`](/xl-deploy/latest/remotingPluginManual.html), the `target-host` is set to `deployed.container.host`.
    * `deployed.container` has a property called `host`, the value of which is of type `overthere.Host`; then `target-host` is set to this value.
* In other cases, `target-host` cannot be calculated automatically and must be specified manually.

### Artifact

<div class="alert alert-danger" role="alert">The information in this section is in beta and is subject to change.</div>

In XL Deploy 4.5.3, 5.0.0, and later, the `artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type `udm.Artifact`, the `artifact` is set to `deployed`.
* In other cases, `artifact` cannot be calculated automatically and must be specified manually.

### Source artifact

In XL Deploy 4.5.2 and earlier, the `source-artifact` parameter of a step is calculated as follows:

* If the scope is `deployed` and `deployed` is of type `udm.Artifact`, the `source-artifact` is set to `deployed`.
* In other cases, `source-artifact` cannot be calculated automatically and must be specified manually.

### Contexts

<div class="alert alert-danger" role="alert">The information in this section is in beta and is subject to change.</div>

Some steps have contexts such as `freemarker-context`, `jython-context` or `powershell-context`.

In XL Deploy 4.5.3, 5.0.0, and later, the context of a step is enriched with calculated variables as follows:

* If the scope is `deployed`, the context is enriched with a <a href="/xl-deploy/4.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html#getDeployed%28%29">deployed</a> instance that is accessible in a FreeMarker template by name `deployed`.
* If the scope is `deployed`, the context is enriched with a <a href="/xl-deploy/4.5.x/javadoc/udm-plugin-api/com/xebialabs/deployit/plugin/api/deployment/specification/Delta.html#getPrevious%28%29">previousDeployed</a> instance that is accessible in a FreeMarker template by name `previousDeployed`.
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

## Define a custom step

If the predefined step types in XL Deploy do not provide the functionality that you need, you can define custom step types and create rules that refer to them. Refer to [Define a custom step](/xl-deploy/latest/xldeployjavaapimanual.html#define-a-custom-step-for-rules) for more information.
