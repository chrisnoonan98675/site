---
layout: pre-rules
title: Create an XL Deploy plugin
categories: 
- xl-deploy
subject:
- Plugins
tags:
- plugin
- java
- generic
---

XL Deploy allows customization using the Java programming language. By implementing a server _plugpoint_, certain XL Deploy server functionality can be changed to adapt the product to your needs. And if you want to use XL Deploy with new middleware, you can implement a custom _plugin_. 

Before you customize XL Deploy functionality, you should understand the XL Deploy architecture. Refer to the [Customization Manual](/xl-deploy/5.0.x/customizationmanual.html#understanding-xl-deploy-architecture) for more information.

Writing a custom plugin is the most powerful way to extend XL Deploy. It uses XL Deploy's Java plugin API which is also used by all of the plugins provided by XebiaLabs. The plugin API specifies a contract between XL Deploy core and a plugin that ensures that a plugin can safely contribute to the calculated deployment plan. To understand the plugin API, it is helpful to learn about the XL Deploy system architecture and how the plugins are involved in performing a deployment. The following sections assume the read has this background information.

## UDM and Java

The UDM concepts are represented in Java by interfaces:

- _Deployable_ classes represent deployable CIs
- _Container_ classes represent container CIs
- _Deployed_ classes represent deployed CIs

In addition to these types, plugins also specify the behavior required to perform the deployment. That is, which actions (steps) are needed to ensure that a deployable ends up in the container as a deployed. In good OO-fashion, this behavior is part of the Deployed class.

Let's look at the mechanisms available to plugin writers in each of the two deployment phases, Specification and Planning.

## Specifying a namespace

All of the CIs in XL Deploy are part of a namespace to distinguish them from other, similarly named CIs. For instance, CIs that are part of the UDM plugin all use the `udm` namespace (such as `udm.Deployable`).

Plugins implemented in Java must specify their namespace in a source file called `package-info.java`. This file provides package-level annotations and is required to be in the same package as your CIs.

This is an example package-info file:

    @Prefix("yak")
    package com.xebialabs.deployit.plugin.test.yak.ci;

    import com.xebialabs.deployit.plugin.api.annotation.Prefix;

## Specification

This section describes Java classes used in defining CIs that are used in the Specification stage.

{:.table .table-striped}
| Classes | Description |
| `udm.ConfigurationItem` and `udm.BaseConfigurationItem` | The `udm.BaseConfigurationItem` is the base class for all the standard CIs in XL Deploy. It provides the `syntheticProperties` map and a default implementation for the name of a CI.
| `udm.Deployable` and `udm.BaseDeployable` | The `udm.BaseDeployable` is the default base class for types that are deployable to `udm.Container` CIs. It does not add any additional behavior
| `udm.EmbeddedDeployable` and `udm.BaseEmbeddedDeployable` | The udm.BaseEmbeddedDeployable is the default base class for types that can be nested under a `udm.Deployable` CI, and which participate in the deployment of the `udm.Deployable` to a `udm.Container`. It does not add any additional behavior.
| `udm.Container` and `udm.BaseContainer` | The udm.BaseContainer is the default base class for types that can contain `udm.Deployable` CIs. It does not add any additional behavior
| `udm.Deployed` and `udm.BaseDeployed` | The udm.BaseDeployed is the default base class for types that specify which `udm.Deployable` CI can be deployed onto which `udm.Container` CI
| `udm.EmbeddedDeployed` and `udm.BaseEmbeddedDeployed` | The `udm.BaseEmbeddedDeployed` is the default base class for types that are nested under a `udm.Deployed` CI. It specifies which `udm.EmbeddedDeployable` can be nested under which `udm.Deployed` or `udm.EmbeddedDeployed` CI. |

### Additional UDM concepts

In addition to the base types, the UDM defines a number of implementations with higher level concepts that facilitate deployments.

{:.table .table-striped}
| Classes | Description |
| `udm.Environment` | The environment is the target for a deployment in XL Deploy. It has members of type `udm.Container`. |
| `udm.Application` | The application is a grouping of multiple `udm.DeploymentPackage` CIs that can each be the source of a deployment (for example: application = PetClinic; version = 1.0, 2.0, ...) |
| `udm.DeploymentPackage` | A deployment package has a set of `udm.Deployable` CIs, and it is the source for a deployment in XL Deploy. |
| `udm.DeployedApplication` | The DeployedApplication resembles the deployment of a `udm.DeploymentPackage` to a `udm.Environment` with a number of specific `udm.Deployed` CIs. |
| `udm.Artifact` | An implementation of a `udm.Deployable` which resembles a 'physical' artifact on disk (or memory). |
| `udm.FileArtifact` | A `udm.Artifact` which points to a single file. |
| `udm.FolderArtifact` | A `udm.Artifact` which points to a directory structure. |

## Mapping deployables to containers

When creating a deployment, the deployables in the package are targeted to one or more containers. The deployable on the container is represented as a deployed. Deployeds are defined by the deployable CI type and container CI type they support. Registering a deployed CI in XL Deploy informs the system that the combination of the deployable and container is possible and how it is to be configured. Once such a CI exists, XL Deploy users can create them in the GUI by dragging the deployable to the container.

When you drag a deployable that contains embedded-deployables to a container, XL Deploy will create an deployed with embedded-deployeds.

## Deployment-level properties

It is also possible to set properties on the deployment (or undeployment) operation itself rather than on the individual deployed. The properties are specified by modifying `udm.DeployedApplication` in the synthetic.xml.

Here's an example:

    <type-modification type="udm.DeployedApplication">
       <property name="username" transient="true"/>
       <property name="password" transient="true" password="true"/>
       <property name="nontransient" required="false" category="SomeThing"/>
    </type-modification>

Here, `username` and `password` are _required_ properties and need to be set before deployment plan is generated. This can be done in the UI by clicking on the **Deployment Properties** button before starting a deployment.

In the CLI, properties are set on the `deployment.deployedApplication`:

    d = deployment.prepareInitial('Applications/AnimalZoo-ear/1.0', 'Environments/myEnv')
    d.deployedApplication.username = 'scott'
    d.deployedApplication.password = 'tiger'

Deployment-level properties may be defined as _transient_, in which case the value will not be stored after deployment. This is useful for user names and password for example. On the other hand, non-transient properties will be available afterwards when doing an update or undeployment.

Analogous to the copying of values of properties from the deployable to the deployed, XL Deploy will copy properties from the `udm.DeploymentPackage` to the deployment level properties of the `udm.DeployedApplication`.

## Planning

During planning a Deployment plugin can contribute steps to the deployment plan. Each of the mechanisms that can be used is described below.

### `@PrePlanProcessor` and `@PostPlanProcessor`

The `@PrePlanProcessor` and `@PostPlanProcessor` annotations can be specified on a method to define a pre- or postprocessor. The pre- or postprocessor takes an optional order attribute which defaults to '100'; lower order means it is earlier, higher order means it is later in the processor chain. The method should take a `DeltaSpecification` and return either a `Step`, `List of Step` or `null`, the name can be anything, so you can define multiple pre- and postprocessors in one class. See these examples:

    @PrePlanProcessor
    public Step preProcess(DeltaSpecification specification) { ... }

    @PrePlanProcessor
    public List<Step> foo(DeltaSpecification specification) { ... }

    @PostPlanProcessor
    public Step postProcess(DeltaSpecification specification) { ... }

    @PostPlanProcessor
    public List<Step> bar(DeltaSpecification specification) { ... }

As a pre- or postprocessor is instantiated when it is needed, it should have a default constructor. Any fields on the class are not set, so the annotated method should not rely on them being set.

### `@Create`, `@Modify`, `@Destroy`, `@Noop`

Deployeds can contribute steps to a deployment in which it is present. The methods that are invoked should also be specified in the `udm.Deployed` CI. It should take a `DeploymentPlanningContext` (to which one or more Steps can be added with specific ordering) and a `Delta` (specifying the operation that is being executed on the CI). The return type of the method should be `void`.

The method is annotated with the operation that is currently being performed on the deployed CI. The following operations are available:

* `@Create` when deploying a member for the first time
* `@Modify` when upgrading a member
* `@Destroy` when undeploying a member
* `@Noop` when there is no change

In the following example, the method `createEar()` is called for both a `create` and `modify` operation of the DeployedWasEar.

    public class DeployedWasEar extends BaseDeployed<Ear, WasServer> {
        ...

        @Create @Modify
        public void createEar(DeploymentPlanningContext context, Delta delta) {
            // do something with my field and add my steps to the result
            // for a particular order
            context.addStep(new CreateEarStep(this));
        }
    }

**Note:** These methods cannot occur on `udm.EmbeddedDeployed` CIs. The `EmbeddedDeployed` CIs do not add any additional behavior, but can be checked by the owning `udm.Deployed` and that can generate steps for the `EmbeddedDeployed` CIs.

### `@Contributor`

A `@Contributor` contributes steps for the set of `Deltas` in the current subplan being evaluated. The methods annotated with `@Contributor` can be present on any Java class which has a default constructor. The generated steps should be added to the collector argument `context`.

    @Contributor
    public void contribute(Deltas deltas, DeploymentPlanningContext context) { ... }

### The `DeploymentPlanningContext`

Both a contributor and specific contribution methods receive a `DeploymentPlanningContext` object as a parameter. The context is used to add steps to the deployment plan, but it also provides some additional functionality the plugin can use:

* `getAttribute()` / `setAttribute()`: contributors can add information to the planning context during planning. This information will be available during the entire planning phase and can be used to communicate between contributors or with the core.
* `getDeployedApplication()`: this allows contributors to access the deployed application that the deployeds are a part of.
* `getRepository()`: contributors can access the XL Deploy repository to determine additional information they may need to contribute steps.  The repository can be read from and written to during the planning stage.

## Repository upgrades

Sometimes, when changes occur in a plugin's structure (properties added, removed or renamed, the structure of CI trees updated), the XL Deploy repository must be migrated from the old to the new structure. Plugins contain _upgrade_ classes for this.

Upgrade classes are Java classes that upgrade data in the repository that was produced by a previous version of the plugin to the current version of the plugin. XL Deploy scans the plugin JAR file for upgrade classes when it loads the plugin. When found, the current plugin version is compared with the plugin version registered in the XL Deploy repository. If the current version is higher than the previous version, the upgrade is executed. If the plugin was never installed before, the upgrade is not run.

An upgrade class extends the following base class:

    public abstract class Upgrade implements Comparable<Upgrade> {

        public abstract boolean doUpgrade(RawRepository repository) throws UpgradeException;
        public abstract Version upgradeVersion();

        ...
    }

The two methods each upgrade must implement are:

    public abstract Version upgradeVersion();

This method returns the version of the upgrade. This is the version the upgrade migrates _to_. That is, after it has run, XL Deploy registers that this is the new current version.

Method

    public abstract boolean doUpgrade(RawRepository repository) throws UpgradeException;

is the workhorse of the upgrade. Here, the class has access to the repository to perform any rewrites necessary.

When XL Deploy boots, it scans for upgrades to run. If it detects any, the boot process is stopped to report this fact to the user and to prompt them to make a backup of the repository first in case of problems. The user has the option to stop XL Deploy at this time if he does not want to perform the upgrade now. Otherwise, XL Deploy continues to boot and executes all upgrades sequentially.

## Packaging your plugin

Plugins are distributed as standard Java archives (JAR files). Plugin JARs are put in the XL Deploy server `plugins` directory, which is added to the XL Deploy server classpath when it boots. XL Deploy will scan its classpath for plugin CIs and plugpoint classes and load these into its registry. These classes **must** be in the `com.xebialabs` or `ext.deployit` packages. The CIs are used and invoked during a deployment when appropriate.

Synthetic extension files packaged in the JAR file will be found and read. If there are multiple extension files present, they will be combined and the changes from all files will be combined.

## Plugin versioning

Plugins, like all software, change. To support plugin changes, it is important to keep track of each plugin version as it is installed in XL Deploy. This makes it possible to detect when a plugin version changes and allows XL Deploy to take specific action, if required. XL Deploy keeps track of plugin versions by scanning each plugin jar for a file called `plugin-version.properties`. This file contains the plugin name and its current version.

For example:

    plugin=sample-plugin
    version=3.7.0

This declares the plugin to be the `sample-plugin`, version `3.7.0`.
