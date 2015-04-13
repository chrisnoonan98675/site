---
title: Key XL Deploy concepts
subject:
- Architecture
categories:
- xl-deploy
tags:
- deployable
- package
- deployed
- security
- application
- plugin
- environment
---

## Interacting with XL Deploy

You can interact with XL Deploy in two ways. 

Performing tasks interactively is possible using the graphical user interface (GUI). The GUI is a Flash application running inside a browser. After logging in, you can configure and perform deployments, view the release pipeline of the different applications, view and edit the repository, view reports, and view or edit security settings (provided that you have permission to access to these components).

The command-line interface (CLI) is used to automate XL Deploy tasks. The CLI is a Jython application that you can access remotely. In addition to configuring and performing deployments, you can also set up middleware in the CLI. In general, the CLI is used to perform administrative tasks or automate deployment tasks.

### Security

XL Deploy supports a role-based access control scheme to ensure the security of your middleware and deployments. The security mechanism is based on the concepts of roles and permissions. 

A role is a functional group of principals (security users or groups) that can be authenticated and that can be assigned rights over resources in XL Deploy. These rights can be either:

* Global; that is, they apply to all of XL Deploy, such as login permission
* Relevant for a particular configuration item (CI) or set of CIs; for instance, the permission to read certain CIs in the repository

The security system uses the same permissions whether the system is accessed via the GUI or the CLI.

### Plugins

A plugin is a self-contained piece of functionality that adds capabilities to the XL Deploy system. A plugin is packaged in a JAR file and installed in XL Deploy's `plugins` directory. Plugins can contain:

* Functionality to connect to specific middleware
* Host connection methods
* Custom importers

## Configuration items (CIs)

A configuration item (CI) is a generic term that describes all objects that XL Deploy keeps track of. Applications, middleware, environments and deployments in XL Deploy are all represented in XL Deploy as CIs. A CI has a type that determines what information it contains and what it can be used for.

All XL Deploy CIs have an `id` property that is a unique identifier. The `id` determines the place of the CI in the repository.

For example, a CI of type `udm.DeploymentPackage` represents a deployment package. It has properties containing its version number. It contains child CIs for the artifacts and resource specifications it contains and has a link to a parent CI of type `udm.Application` which describes which application the package is a part of.

### Directories

A directory is a CI used for grouping other CIs. Directories exist directly below the repository root nodes and may be nested. Directories are also used to group security settings.

For example, you could create the directories Administrative, Web and Financial under the **Applications** node in the repository to group the available applications in these categories.

### Embedded CIs

Embedded CIs are CIs that are part of another CI and can be used to model additional settings and configuration for the parent CI. Embedded CI types are identified by their source deployable type and their container (or parent) type.

Embedded CIs, like regular CIs, have a type and properties and are stored in the repository. Unlike regular CIs, they are not individually compared in the delta analysis phase of a deployment. This means that if an embedded CI is changed, this will be represented as a MODIFY delta on the parent CI.

### Type system

XL Deploy features a configurable type system that allows modification and addition of CI types. This makes it possible to extend your installation of XL Deploy with new types or change existing types. Types defined in this manner are referred to as synthetic types. The type system is configured using XML files called `synthetic.xml`. All files containing synthetic types are read when the XL Deploy server starts and are available in the system afterwards.

Synthetic types are first-class citizens in XL Deploy and can be used in the same way that the built-in types are used. This means they can be included in deployment packages, used to specify your middleware topology and used to define and execute deployments. Synthetic types can also be edited in the XL Deploy GUI, including new types and added properties.

## Deployment packages

In XL Deploy's Unified Deployment Model (UDM), a particular version of an application (consisting of both artifacts and resource specifications) is contained in a single deployment package. The package contains all deployables that the application needs. The package is environment-independent and can be deployed to any environment unchanged.

Packages can be created outside of XL Deploy (and imported via the GUI or CLI) or, alternatively, you can use the XL Deploy GUI to create a package. Packages can also be exported from XL Deploy.

XL Deploy accepts packages in the deployment archive (DAR) format. A DAR file is a standard ZIP file with additional metadata information contained in a manifest.

### Deployables

Deployables are configuration items (CIs) that can be deployed to a container. Deployables are part of a deployment package. Deployables come in two forms: artifacts (for instance, EAR files) and specifications (for instance, a datasource).

#### Artifacts

Artifacts are files containing application resources such as code or images. The following are examples of artifacts:

* A WAR file
* An EAR file
* A folder containing static content such as HTML pages or images

An artifact has a property called checksum that can be overridden during or after import. If it is not given, XL Deploy will calculate a SHA-1 sum of the binary content of the artifact, which is used during deployments to determine whether the artifact's binary content has changed or not.

#### Resource specifications

Resource specifications are specifications of middleware resources that an application needs to run. The following are examples of these resources:

* A datasource
* A queue or topic
* A connection factory

### Deployeds

Deployeds are CIs that represent deployable CIs in their deployed form on the target container. The deployed CI specifies settings that are relevant for the CI on the container.

For example, a `wls.Ear` deployable is deployed to a `wls.Server` container, resulting in a `wls.EarModule` deployed.

Another example is a `wls.DataSourceSpec` that is deployed to a `wls.Server` container, resulting in a `wls.DataSource` deployed. The `wls.DataSource` is configured with the database username and password that are required to connect to the database from this particular server.

Deployeds go through the following lifecycle:

* The deployed is created on a target container for the first time in an initial deployment
* The deployed is upgraded to a new version in an upgrade deployment
* The deployed is removed from the target container when it is undeployed

### Composite packages

Composite packages are deployment packages that have other deployment packages as members. A typical use case for composite packages is to compose a release of an application that consists of components that are delivered by separate teams.

Composite packages can not be imported, but are created inside of XL Deploy using other packages that are also in the XL Deploy repository. You can create composite packages that contain other composite packages.

Deploying a composite package works the same as deploying a regular package. Note that XL Deploy has a composite package orchestrator that ensures that the deployment is carried out according to the ordering of the composite package members.

## Dictionaries

A dictionary is a CI that contains environment-specific entries for placeholder resolution. Entries can be added in the GUI or using the CLI. This allows the deployment package to remain environment-independent so it can be deployed unchanged to multiple environments.

A dictionary value can refer to another dictionary entry. This is accomplished by using the {% raw %}`{{..}}`{% endraw %} placeholder syntax.

For example:

{:.table}
| APPNAME | XL Deploy|
| MESSAGE |Welcome to {% raw %}{{APPNAME}}{% endraw %}!

The value belonging to key MESSAGE will be "Welcome to XL Deploy!". Placeholders may refer to keys from any dictionary in the same environment.

If a dictionary is associated with an environment this means that, by default, values from the dictionary are applied to all deployments targeting the environment. It is also possible to restrict the dictionary values to deployments to specific containers within the environment or to deployments of specific applications to the environment. These restrictions can be specified on the dictionary's Restrictions tab. A deployment must meet **all** restrictions for the dictionary values to be applied.

**Note:** An unrestricted dictionary cannot refer to entries in a restricted dictionary.

Dictionaries are evaluated in the order in which they appear in the GUI. The first dictionary that defines a value for a placeholder is the one that XL Deploy uses for that placeholder.

### Encrypted dictionaries

An encrypted dictionary is a dictionary that stores sensitive information and for which all contained values are encrypted by XL Deploy. Encrypted dictionaries store key-value pairs and are associated with environments just like regular dictionaries. When a value from an encrypted dictionary is used in a CI property or placeholder, the XL Deploy CLI and GUI will only show the encrypted values. Once the value is used in a deployment, the value is decrypted and can be used by XL Deploy and the plugins. For security reasons the value of an encrypted dictionary will be blank when used in a CI property that is not password enabled.

## Containers

Containers are CIs that deployable CIs can be deployed to. Containers are grouped together in an environment. Examples of containers are a host, WebSphere server or WebLogic cluster.

## Environments

An environment is a grouping of infrastructure items, such as hosts, servers, clusters, and so on. Environments can contain any combination of infrastructure items that are used in your situation. An environment is used as the target of a deployment, allowing deployables to be mapped to members of the environment.

In addition to physical environments, XL Deploy also allows definition of a cloud environment, which is an environment containing members that run on a cloud platform. Cloud environments are defined using an environment template. Environment templates can be instantiated causing one or more instances to be launched on the target cloud platform and resulting in a cloud environment available as a deployment target. See the cloud platform specific manuals for details.

**Note**: Cloud environments require XL Scale to be installed.

## Deploying an application

The process of deploying an application installs a particular application version (represented by a deployment package) on an environment. XL Deploy copies all necessary files and makes all configuration changes to the target middleware that are necessary for the application to run.

### Plan optimisation

During planning, XL Deploy will try to simplify and optimise the plan. The simplifications and optimisations will be performed after the ordinary planning phase.
Simplification is needed to remove intermediate plans that are not necessary. Optimisation is performed to split large step plans into smaller plans. This will give a better overview of how many steps there are, and decreases the amount of network traffic needed to transfer the task state during execution.

Simplification can be switched on and off by switching the `optimizePlan` property of the deployed application. Turning this property off will only disable the simplification, but not the splitting of large plans.

* Simplification will only remove intermediate plans, and never steps. An example: If a parallel plan contains only one sub plan, the intermediate parallel plan is removed because there will not be anything running in parallel.
* XL Deploy will scan all step plans and if any step plan contains more than 30 steps, it will be split up into serial plans that contain all steps from a given order group.
* After splitting the step by order, the plan will again be scanned for step plans that contain more than 100 steps. Those plans will be split into serial plans containing 100 steps each.

### Parallel deployment

XL Deploy is able to run specific parts of the deployment plan in parallel. XL Deploy decides which parts of the plan will be executed in parallel during orchestration. By default, no plan will be executed in parallel. You can enable parallel execution by selecting an orchestrator that supports parallel execution.

### Rollback

XL Deploy supports customized rollbacks of deployments that revert changes made by a (failed) deployment to the exact state before the deployment was started. Rollbacks are triggered manually via the GUI or CLI when a task is active (not yet archived). Changes to deployeds and dictionaries are also rolled back.

### Undeploying an application

The process of underlying an application removes a deployed application from an environment. XL Deploy stops the application and undeploys all its components from the target middleware.

### Upgrading an application

The process of upgrading an application replaces an application deployed to an environment with another version of the same application. When performing an upgrade, most deployeds can be inherited from the initial deployment. XL Deploy recognizes which artifacts in the deployment package have changed and deploys only the changed artifacts.

## Control tasks

Control tasks are actions that can be performed on middleware or middleware resources.

Control tasks are defined on a particular type and can be executed on a specific instance of that type. When a control task is invoked, XL Deploy starts a task that executes the steps associated with the control task. XL Deploy users can use them to interact directly with the underlying middleware. An example of a control task is starting or stopping of an Apache web server.

Control tasks can be defined in Java or XML, or using scripts.
