---
title: XL Deploy overview
---

## Key concepts

### Interacting with XL Deploy

You can interact with XL Deploy in two ways:

* The graphical user interface (GUI), which is a Flash application running in a browser. After logging in to the GUI, you can:
    * [Connect XL Deploy to your infrastructure](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html)
    * [Define your environments](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html)
    * [Import or create deployment packages](/xl-deploy/how-to/add-a-package-to-xl-deploy.html)
    * [Deploy applications to environments](/xl-deploy/how-to/deploy-an-application.html)
    * [Define permissions for users](/xl-deploy/how-to/set-up-xl-deploy-security-using-the-gui.html)
* The [command-line interface (CLI)](/xl-deploy/how-to/using-the-xl-deploy-cli.html), which is a Jython application that you can access [remotely](/xl-deploy/how-to/connect-to-xl-deploy-from-the-cli.html). Generally, the CLI is used to perform administrative tasks or to automate XL Deploy tasks.

#### Security

XL Deploy has a role-based access control scheme that ensures the security of your middleware and deployments. The security mechanism is based on the concepts of roles and [permissions](/xl-deploy/concept/overview-of-security-in-xl-deploy.html).

A role is a functional group of principals (security users or groups) that can be authenticated and assigned rights over resources in XL Deploy. These rights can be either:

* Global; that is, they apply to all of XL Deploy, such as permission to log in
* Relevant to a particular configuration item (CI) or set of CIs; for instance, the permission to read certain CIs in the repository

The security system uses the same permissions whether the system is accessed via the GUI or the CLI.

#### Plugins

A [plugin](/xl-deploy/how-to/install-or-remove-xl-deploy-plugins.html) is a self-contained piece of functionality that adds capabilities to the XL Deploy system. A plugin is packaged in a JAR or XLDP file and installed in XL Deploy's `plugins` directory. Plugins can contain:

* Functionality to connect to specific middleware
* Host connection methods
* Custom importers

### Configuration items (CIs)

A configuration item (CI) is a generic term that describes all objects that XL Deploy keeps track of. Applications, middleware, environments and deployments in XL Deploy are all represented in XL Deploy as CIs. A CI has a type that determines what information it contains and what it can be used for.

All XL Deploy CIs have an `id` property that is a unique identifier. The `id` determines the place of the CI in the repository.

For example, a CI of type `udm.DeploymentPackage` represents a deployment package. It has properties containing its version number. It contains child CIs for the artifacts and resource specifications it contains and has a link to a parent CI of type `udm.Application` which describes which application the package is a part of.

#### Directories

A directory is a CI used for grouping other CIs. Directories exist directly below the repository root nodes and may be nested. Directories are also used to group security settings.

For example, you could create directories called Administrative, Web, and Financial under the **Applications** node in the repository to group the available applications in these categories.

#### Embedded CIs

Embedded CIs are CIs that are part of another CI and can be used to model additional settings and configuration for the parent CI. Embedded CI types are identified by their source deployable type and their container (or parent) type.

Embedded CIs, like regular CIs, have a type and properties and are stored in the repository. Unlike regular CIs, they are not individually compared in the delta analysis phase of a deployment. This means that if an embedded CI is changed, this will be represented as a MODIFY delta on the parent CI.

#### Type system

XL Deploy features a configurable type system that allows [modification and addition of CI types](/xl-deploy/how-to/customizing-the-xl-deploy-type-system.html). This makes it possible to extend your installation of XL Deploy with new types or change existing types. Types defined in this manner are referred to as synthetic types. The type system is configured using XML files called `synthetic.xml`. All files containing synthetic types are read when the XL Deploy server starts and are available in the system afterward.

Synthetic types are first-class citizens in XL Deploy and can be used in the same way that the built-in types are used. This means they can be included in deployment packages, used to specify your middleware topology and used to define and execute deployments. Synthetic types can also be edited in the XL Deploy GUI, including new types and added properties.

### Deployment packages

In XL Deploy's Unified Deployment Model (UDM), a particular version of an application (consisting of both artifacts and resource specifications) is contained in a single deployment package. The package contains all deployables that the application needs. The package is environment-independent and can be deployed to any environment unchanged.

Packages can be created outside of XL Deploy (and imported via the GUI or CLI) or, alternatively, you can use the XL Deploy GUI to create a package. Packages can also be exported from XL Deploy.

XL Deploy accepts packages in the deployment archive (DAR) format. A DAR file is a standard ZIP file with additional metadata information contained in a manifest.

#### Deployables

Deployables are configuration items (CIs) that can be deployed to a container. Deployables are part of a deployment package. Deployables come in two forms: artifacts (for instance, EAR files) and specifications (for instance, a datasource).

##### Artifacts

Artifacts are files containing application resources such as code or images. The following are examples of artifacts:

* A WAR file
* An EAR file
* A folder containing static content such as HTML pages or images

An artifact has a property called `checksum` that can be overridden during or after import. If it is not given, XL Deploy will calculate a SHA-1 sum of the binary content of the artifact, which is used during deployments to determine whether the artifact's binary content has changed or not.

##### Resource specifications

Resource specifications are specifications of middleware resources that an application needs to run. The following are examples of these resources:

* A datasource
* A queue or topic
* A connection factory

#### Deployeds

Deployeds are CIs that represent deployable CIs in their deployed form on the target container. The deployed CI specifies settings that are relevant for the CI on the container.

For example, a `wls.Ear` deployable is deployed to a `wls.Server` container, resulting in a `wls.EarModule` deployed.

Another example is a `wls.DataSourceSpec` that is deployed to a `wls.Server` container, resulting in a `wls.DataSource` deployed. The `wls.DataSource` is configured with the database username and password that are required to connect to the database from this particular server.

Deployeds go through the following lifecycle:

* The deployed is created on a target container for the first time in an initial deployment
* The deployed is upgraded to a new version in an upgrade deployment
* The deployed is removed from the target container when it is undeployed

#### Composite packages

Composite packages are deployment packages that have other deployment packages as members. A typical use case for composite packages is to compose a release of an application that consists of components that are delivered by separate teams.

Composite packages can not be imported, but are created inside of XL Deploy using other packages that are also in the XL Deploy repository. You can create composite packages that contain other composite packages.

Deploying a composite package works the same as deploying a regular package. Note that XL Deploy has a composite package orchestrator that ensures that the deployment is carried out according to the ordering of the composite package members.

### Dictionaries

A [dictionary](/xl-deploy/how-to/create-a-dictionary.html) is a CI that contains environment-specific entries for placeholder resolution. Entries can be added in the GUI or using the CLI. This allows the deployment package to remain environment-independent so it can be deployed unchanged to multiple environments.

A dictionary value can refer to another dictionary entry. This is accomplished by using the {% raw %}`{{..}}`{% endraw %} placeholder syntax.

For example:

{:.table}
| Key     | Value     |
| ------- | --------- |
| APPNAME | XL Deploy |
| MESSAGE | Welcome to {% raw %}{{APPNAME}}{% endraw %}! |

The value belonging to key `MESSAGE` will be "Welcome to XL Deploy!". Placeholders may refer to keys from any dictionary in the same environment.

If a dictionary is associated with an environment this means that, by default, values from the dictionary are applied to all deployments targeting the environment. It is also possible to [restrict](/xl-deploy/how-to/create-a-dictionary.html#restrict-a-dictionary-to-containers-or-applications) the dictionary values to deployments to specific containers within the environment or to deployments of specific applications to the environment. These restrictions can be specified on the dictionary's Restrictions tab. A deployment must meet **all** restrictions for the dictionary values to be applied.

**Note:** An unrestricted dictionary cannot refer to entries in a restricted dictionary.

Dictionaries are evaluated in the order in which they appear in the GUI. The first dictionary that defines a value for a placeholder is the one that XL Deploy uses for that placeholder.

#### Encrypted dictionaries

An encrypted dictionary is a dictionary that stores sensitive information and for which all contained values are encrypted by XL Deploy. Encrypted dictionaries store key-value pairs and are associated with environments just like regular dictionaries. When a value from an encrypted dictionary is used in a CI property or placeholder, the XL Deploy CLI and GUI will only show the encrypted values. Once the value is used in a deployment, the value is decrypted and can be used by XL Deploy and the plugins. For security reasons the value of an encrypted dictionary will be blank when used in a CI property that is not password enabled.

**Note:** Encrypted dictionaries are deprecated as of XL Deploy 5.0.0. In XL Deploy 5.0.0 and later, encrypted key-value pairs are stored in normal dictionaries.

### Containers

Containers are CIs that deployable CIs can be deployed to. Containers are grouped together in an environment. Examples of containers are a host, WebSphere server or WebLogic cluster.

### Environments

An environment is a grouping of infrastructure items, such as hosts, servers, clusters, and so on. Environments can contain any combination of infrastructure items that are used in your situation. An environment is used as the target of a deployment, allowing deployables to be mapped to members of the environment.

In addition to physical environments, XL Deploy also allows definition of a cloud environment, which is an environment containing members that run on a cloud platform. Cloud environments are defined using an environment template. Environment templates can be instantiated causing one or more instances to be launched on the target cloud platform and resulting in a cloud environment available as a deployment target. See the cloud platform specific manuals for details.

**Note:** Cloud environments require XL Scale to be installed.

### Deploying an application

The process of deploying an application installs a particular application version (represented by a deployment package) on an environment. XL Deploy copies all necessary files and makes all configuration changes to the target middleware that are necessary for the application to run.

#### Plan optimization

During planning, XL Deploy will try to simplify and optimize the plan. The simplifications and optimizations will be performed after the ordinary planning phase.

Simplification is needed to remove intermediate plans that are not necessary. Optimization is performed to split large step plans into smaller plans. This will give a better overview of how many steps there are, and decreases the amount of network traffic needed to transfer the task state during execution.

Simplification can be switched on and off by switching the `optimizePlan` property of the deployed application. Turning this property off will only disable the simplification, but not the splitting of large plans.

* Simplification will only remove intermediate plans, and never steps. An example: If a parallel plan contains only one sub plan, the intermediate parallel plan is removed because there will not be anything running in parallel.
* XL Deploy will scan all step plans and if any step plan contains more than 30 steps, it will be split up into serial plans that contain all steps from a given order group.
* After splitting the step by order, the plan will again be scanned for step plans that contain more than 100 steps. Those plans will be split into serial plans containing 100 steps each.

#### Parallel deployment

XL Deploy is able to run specific parts of the deployment plan in parallel. XL Deploy decides which parts of the plan will be executed in parallel during orchestration. By default, no plan will be executed in parallel. You can enable parallel execution by selecting an orchestrator that supports parallel execution.

#### Rollback

XL Deploy supports customized rollbacks of deployments that revert changes made by a (failed) deployment to the exact state before the deployment was started. Rollbacks are triggered manually via the GUI or CLI when a task is active (not yet archived). Changes to deployeds and dictionaries are also rolled back.

#### Undeploying an application

The process of underlying an application removes a deployed application from an environment. XL Deploy stops the application and undeploys all its components from the target middleware.

#### Upgrading an application

The process of upgrading an application replaces an application deployed to an environment with another version of the same application. When performing an upgrade, most deployeds can be inherited from the initial deployment. XL Deploy recognizes which artifacts in the deployment package have changed and deploys only the changed artifacts.

### Control tasks

[Control tasks](/xl-deploy/how-to/using-control-tasks-in-xl-deploy.html) are actions that you can perform on middleware or middleware resources. For example, a control task could start or stop an Apache web server.

A control task is defined on a particular CI type and can be executed on a specific instance of that type. When you invoke a control task, XL Deploy starts a task that executes the steps that are associated with the control task.

You can define control tasks in Java or XML, or by using scripts.

## Architecture

XL Deploy features a modular architecture that allows you to change and extend components while maintaining a consistent system. This is a high-level overview of the system architecture:

![XL Deploy architecture](/xl-deploy/images/xl-deploy-architecture.png)

### The XL Deploy core

XL Deploy's central component is called the *core*. It contains the following functionality:

* The Unified Deployment Engine, which determines what is required to perform a deployment
* Storage and retrieval of deployment packages
* Execution and storage of deployment tasks
* Security
* Reporting

The XL Deploy core is accessed using a REST service. XL Deploy includes two REST service clients: a graphical user interface (GUI) that runs in browsers and a command-line interface (CLI) that interprets Jython.

XL Deploy plugins provide support for various middleware platforms. These plugins add capabilities to XL Deploy and may be delivered by XebiaLabs or custom-built by XL Deploy users.

### XL Deploy and plugins

A XL Deploy plugin is a component that provides the XL Deploy server with a way to interact with a specific piece of middleware. This allows the XL Deploy core to remain independent of the middleware to which it connects. At the same time, this allows plugin writers to extend XL Deploy in a way that seamlessly integrates with the rest of XL Deploy's functionality. Existing XL Deploy plugins can be extended to customize XL Deploy for your environment. You can even write a new XL Deploy plugin from scratch.

To integrate with the XL Deploy core, the plugins adhere to a well-defined interface that specifies the contract between the XL Deploy plugin and the XL Deploy core, making it clear what each can expect of the other. The XL Deploy core is the active party in this collaboration and invokes the plugin whenever needed. The plugin replies to requests it is sent.

When the XL Deploy server starts, it scans the classpath and loads each XL Deploy plugin it finds, readying it for interaction with the XL Deploy core. The XL Deploy core does not change loaded plugins or load any new plugins after it has started.

At runtime, multiple plugins will be active at the same time. It is up to the XL Deploy core to integrate the various plugins and ensure they work together to perform deployments. There is a well-defined process (described below) that invokes all plugins involved in a deployment and turns their contributions into one consistent deployment plan. The execution of the deployment plan is handled by the XL Deploy core.

Plugins can define the following items:

* Deployable: Configuration items (CIs) that are part of a package and that can be deployed
* Container: CIs that are part of an environment and that can be deployed to
* Deployed: CIs that represent the end result of the deployment of a deployable CI to a container CI
* A recipe describing how to deploy deployable CIs to container CIs
* Validation rules to validate CIs or properties of CIs

### Preparing and performing deployments in XL Deploy

Performing a deployment in XL Deploy consists of a number of stages that, together, ensure that the deployment package is deployed and configured on the environment. Some of these activities are performed by the XL Deploy core, while others are performed by the plugins.

The stages are:

* **Specification:** Creates a *deployment specification* that defines which deployables (deployment package members) are to be deployed to which containers (environment members) and how they should be configured.

* **Delta Analysis:** Analyzes the differences between the deployment specification and the current state of the middleware, resulting in a *delta specification*, which is a list of changes to the middleware state that transforms the current situation into the situation described by the deployment specification. The deltas represent operations needed on the deployed items in the deployment. There are four defined operations:
    * `CREATE` when deploying an item for the first time
    * `MODIFY` when upgrading an item
    * `DESTROY` when undeploying an item
    * `NOOP` when there is no change

* **Orchestration:** Splits the delta specification into independent subspecifications that can be planned and executed in isolation. Creates a *deployment plan* containing nested *subplans*.

* **Planning:** Adds *steps* to each subplan that, when executed, perform the actions needed to execute the actual deployment.

* **Execution:** Executes the complete deployment plan to perform the deployment.

### Deployments and plugins

The following diagram depicts the way in which a plugin is involved in a deployment:

![Deployment](/xl-deploy/images/Deployment-full.png)

The transitions that are covered by a puzzle-piece are the ones that interact with the plugins, while the XL Deploy logo indicates that the transition is handled by the XL Deploy core.

The following sections describe how plugins are involved in the aforementioned activities.

#### The Specification stage

In the Specification stage, the deployment to be executed is specified. This includes selecting the deployment package and members to be deployed, as well as mapping each package member to the environment members that they should be deployed to.

##### Specifying CIs

The XL Deploy plugin defines which CIs the XL Deploy core can use to create deployments. When a plugin is loaded into the core, XL Deploy scans the plugin for CIs and adds these to its CI registry. Based on the CI information in the plugin, XL Deploy will categorize each CI as either a deployable CI (defining the *what* of the deployment) or a container CI (defining the *where* of the deployment).

##### Specifying relationships

While the deployable CI represents the passive resource or artifact, the deployed CI represents the *active* version of the deployable CI when it has been deployed in a container. By defining deployed CIs, the plugin indicates which combinations of deployable and container are supported.

##### Configuration

Each deployed CI represents a combination of a deployable CI and a container CI. It is important to note that one deployable CI can be deployed to multiple container CIs. For example, an EAR file can be deployed to two application servers. In a deployment, this is modeled as multiple deployed CIs.

You may want to configure a deployable CI differently depending on the container CI or environment to which it is deployed. This can be done by configuring the properties of the deployed CI differently.

Configuration of deployed CIs is handled in the XL Deploy core. Users perform this task either via the GUI or via the CLI. An XL Deploy plugin can influence this process by providing default values for its properties.

##### Result

The result of the Specification stage is a deployment specification that contains deployed CIs that describe which deployable CIs are mapped to which container CIs with the needed configuration.

#### The Planning stage

In the Planning stage, the deployment specification and the subplans that were created in the Orchestration stage are processed. During this stage, the XL Deploy core performs the following procedure:

1. Preprocessing
1. Contributor processing
1. Postprocessing

During each part of this procedure, the XL Deploy plugin is invoked so it can add required deployment steps to the subplan.

##### Preprocessing

Preprocessing allows the plugin to contribute steps to the very beginning of the plan. During preprocessing, all preprocessors defined in the plugin are invoked in turn. Each preprocessor has full access to the delta
specification. As such, the preprocessor can contribute steps based on the entire deployment. Examples of such steps are sending an email before starting the deployment or performing pre-flight checks on CIs in that deployment.

##### Deployed CI processing

Deployed CIs contain both the data and the behavior to make a deployment happen. Each deployed CI that is part of the deployment can contribute steps to ensure that it is deployed or configured correctly.

Steps in a deployment plan must be specified in the correct order for the deployment to succeed. Furthermore, the order of these steps must be coordinated among an unknown number of plugins. To achieve this, XL Deploy weaves all the separate resulting steps from all the plugins together by looking at the order property (a number) they specify.

For example, suppose you have a container CI representing a WebSphere Application Server (WAS) called WasServer. This CI contains the data describing a WAS server (things like host, application directory, and so on) as well as the behavior to manage it. During a deployment to this server, the WasServer CI contributes steps with order 10 to stop the server. Also, it would contribute steps with order 90 to restart it. In the same deployment, a deployable CI called WasEar (representing a WAS EAR file) contributes steps to install itself with order 40. The resulting plan would weave the installation of the EAR file (40) in between the stop (10) and start (90) steps.

This mechanism allows steps (behavior) to be packaged together with the CIs that contribute them. Also, CIs defined by separate plugins can work together to produce a well-ordered plan.

###### Default step orders

XL Deploy uses the following default orders:

{:.table .table-striped}
| Step | Default order |
| ---- | ------------- |
| `PRE_FLIGHT` | 0 |
| `STOP_ARTIFACTS` | 10 |
| `STOP_CONTAINERS` | 20 |
| `UNDEPLOY_ARTIFACTS` | 30 |
| `DESTROY_RESOURCES` | 40 |
| `CREATE_RESOURCES` | 60 |
| `DEPLOY_ARTIFACTS` | 70 |
| `START_CONTAINERS` | 80 |
| `START_ARTIFACTS` | 90 |
| `POST_FLIGHT` | 100 |

To review the order values of the steps in a plan, set up the deployment, preview the plan, and then check the server log. The step order value appears at the beginning of each step in the log.

To change the order of steps in a plan, you can customize XL Deploy's behavior by:

* Creating rules that XL Deploy applies during the planning phase; see [Getting started with XL Deploy rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html) for more information
* Developing a server plugin; see [Create an XL Deploy plugin](/xl-deploy/how-to/create-an-xl-deploy-plugin.html) and [Introduction to the Generic plugin](/xl-deploy/concept/generic-plugin.html) for more information

##### Postprocessing

Postprocessing is similar to preprocessing, but it allows a plugin to add one or more steps to the very end of a plan. A postprocessor could, for example, add a step to send an email after the deployment is complete.

##### Result

The Planning stage results in a deployment plan that contains all steps required to perform the deployment. The deployment plan is ready to be executed.
