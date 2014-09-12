---
title: Reference Manual
---

## Preface

This manual contains reference information for users of XL Deploy. For an overview of the UDM Configuration Items, please refer to [UDM CI Reference](udmcireference.html).

## Deployment Overview

XL Deploy is the first out-of-the-box deployment automation solution that allows non-experts to perform application deployments. A deployment consists of all the actions needed to install, configure and start an application on a target environment.

At a high level, deployments in XL Deploy are modeled using the _Unified Deployment Model (UDM)_. The following diagram depicts the main concepts in the UDM as they appear in the XL Deploy GUI:

![Unified Deployment Model](images/udm-highlevel-deployit.png "Unified Deployment Model")

Deployments are defined by:

* A **Package** containing _what_ is to be deployed (shown on the left side of the picture).
* An **Environment** defining _where_ the package is to be deployed (shown on the right side of the picture).
* Configuration of the **Deployment** specifying (possibly environment-specific) _customizations_ to the package to be deployed (the middle square).

Of course, packages and environments are made up of smaller parts:

* Packages consist of **deployables** or _things that can be deployed_.
* Environments consist of **containers** or _things that can be deployed *to*_.

Deployables come in two flavors: _artifacts_ are physical files (examples are an EAR file, a WAR file or a folder of static HTML) and _resource specifications_ are middleware resources that the application needs to run (examples are a queue, a topic or a datasource). These types of deployables are put together in a package.

Containers are actual middleware products that deployables are deployed to. Examples of containers are an application server such as Tomcat or WebSphere, a database server, or a WebSphere node or cell.

Let's say we have a package that consists of an EAR file (an artifact), a datasource (a resource specification) and some configuration files (artifacts) and we want to deploy this package to an environment containing an application server and a host (both containers). The exact deployment could look something like this:

![Unified Deployment Model Detail](images/udm-lowlevel-deployit.png "Unified Deployment Model Detail")

In the above picture, you can see that the EAR file and the datasource are deployed to the application server and the configuration files are deployed to the host.

As you can see above, the deployment also consists of smaller parts. The combination of a particular deployable and container is called a _deployed_. Deployeds represent _the deployable on the container_ and contain customizations for this specific deployable - container combination. For example, the _PetClinic-ds_ deployed in the picture represents the datasource from the deployment package as it will be deployed to the _was.Server_ container. The deployed allows a number of properties to be specified:

![Unified Deployment Model - Deployed](images/udm-lowlevel-deployed-deployit.png "Unified Deployment Model - Deployed")

For example, the deployed has a specific username and password that may be different when deploying the same datasource to another server.

Once a deployment is specified and configured using the concepts above (and the _what_, _where_ and _customizations_ are known), XL Deploy takes care of the _how_ by preparing a list of steps that need to be executed to perform the actual deployment. Each step specifies one action to be taken, such as copying of a file, modifying a configuration file or restarting a server.

When the deployment is started, XL Deploy creates a _task_ to perform the deployment. The task is an independent process running on the XL Deploy server. The steps are executed sequentially and the deployment is finished successfully when all steps have been executed. If an error occurs during deployment, the deployment stops and an operator is required to intervene.

The result of the deployment is stored in XL Deploy as a _deployed application_ and is shown in the Deployed Application Browser on the right hand side of the GUI. The deployed applications are organised by environment so it is clear where the application is deployed to. It is also possible to see which parts of the deployed package have been deployed to which environment member.

The final result of our sample deployment looks like this:

![Unified Deployment Model - Deployed Application](images/udm-lowlevel-deployedapplication-deployit.png "Unified Deployment Model - Deployed Application")

## Interacting with XL Deploy

There are two ways of interacting with XL Deploy and each is intended for a specific audience.

Performing tasks interactively is possible using the **Graphical User Interface (GUI)**. The GUI is a Flash application running inside a browser. After logging in, the user can configure and perform deployments; view the release pipeline of the different applications; view and edit the repository; view reports; and view or edit security settings. (Provided that the user has proper access to these components of course) See the **XL Deploy Graphical User Interface (GUI)** manual for more information.

The **Command Line Interface (CLI)** is used to automate XL Deploy tasks. The CLI is a Jython application that the user can access remotely. In addition to configuring and performing deployments, the user can also setup middleware in the CLI. In general, the CLI is used to perform administrative tasks or automate deployment tasks. See the **XL Deploy Command Line Interface (CLI)** manual for more information.

Please note that XL Deploy's security system is applied to all user actions, regardless of whether they are performed in the GUI or from the CLI.

## XL Deploy Glossary

These are the main concepts in XL Deploy, in alphabetical order.

### Artifacts

Artifacts are files containing application resources such as code or images. The following are examples of artifacts:

* a WAR file.
* an EAR file.
* a folder containing static content such as HTML pages or images.

An artifact has a property called _checksum_ which can be overridden during or after import. If it is not given, XL Deploy will calculate a SHA-1 sum of the binary content of the artifact, which is used during deployments to determine whether the artifact's binary content has changed or not.

### Audit log

XL Deploy keeps a record of all actions that users perform in the system. This audit trail is written to a log file. For more information about the audit log, see the **System Administration Manual**.

### Command Line Interface (CLI)

The XL Deploy CLI provides a way to programmatically interact with XL Deploy. The CLI can be programmed using the **Python** programming language. For more detailed information, see the **XL Deploy Command Line Interface Manual**.

### Composite Packages

Composite packages are deployment packages that have other deployment packages as members. Composite packages can not be imported, but are created inside of XL Deploy using other packages that are also in the XL Deploy repository. Deploying a composite package works the same as deploying a regular package. Note that XL Deploy has a composite package _orchestrator_ that ensures that the deployment is carried out according to the ordering of the composite package members.

A typical use case for composite packages is to compose a release of an application that  consists of components that are delivered by separate teams.

### Control Tasks

Control tasks are actions that can be performed on middleware or middleware resources.

Control tasks are defined on a particular type and can be executed on a specific instance of that type. When a control task is invoked, XL Deploy starts a task that executes the steps associated with the control task. XL Deploy users can use them to interact directly with the underlying middleware. An example of a control task is starting or stopping of an Apache webserver.

Control tasks can be defined in Java or XML (see the **Customization Manual**) or using scripts (see the **Generic Model Plugin Manual**).

### Configuration Items (CIs)

A _configuration item_ (CI) is a generic term that describes all objects that XL Deploy keeps track of. Applications, middleware, environments and deployments in XL Deploy are all represented in XL Deploy as CIs. A CI has a certain _type_ that determines what information it contains and what it can be used for. CIs have properties to describe it and may have relations to other CIs.

XL Deploy CIs all share one property:

* **id**. The unique identifier for this CI. The id determines the place of the CI in the repository.

Some properties of a CI are mandatory. To determine which properties are available and which are mandatory or optional for a CI, see the **CI Reference** section in this document, the plugin manual for the plugin that provides the CI, or use the help facility in the XL Deploy CLI.

For example, a CI of type `udm.DeploymentPackage` represents a _deployment package_. It has properties containing its version number. It contains child CIs for the _artifacts_ and _resource specifications_ it contains and has a link to a parent CI of type `udm.Application` which describes which application the package is a part of.

### Containers

_Containers_ are configuration items (CIs) that _deployable_ CIs can be deployed to. Containers are grouped together in an _environment_. Examples of containers are a host, WebSphere server or WebLogic cluster.

### Deployables

_Deployables_ are configuration items (CIs) that can be deployed to a container. Deployables are part of a _deployment package_. Deployables come in two forms: _artifacts_ (for instance, EAR files) and _specifications_ (for instance, a datasource).

### Deployeds

_Deployeds_ are configuration items (CIs) that represent deployable CIs in their deployed form on the target container. The deployed CI specifies settings that are relevant for the CI on the container.

For example, a `wls.Ear` deployable is deployed to a `wls.Server` container, resulting in a `wls.EarModule` deployed.

Another example is a `wls.DataSourceSpec` that is deployed to a `wls.Server` container, resulting in a `wls.DataSource` deployed. The `wls.DataSource` is configured with the database username and password that are required to connect to the database from this particular server.

Deployeds go through the following life-cycle:

* The deployed is created on a target container for the first time in an _initial_ deployment
* The deployed is upgraded to a new version in an _upgrade_ deployment
* The deployed is removed from the target container when it is _undeployed_.

### Deploying an Application

This process installs a particular application version (represented by a _deployment package_) on an environment. XL Deploy copies all necessary files and makes all configuration changes to the target middleware that are necessary for the application to run.

### Deployment Archive (DAR) Format

The DAR format is the native format XL Deploy supports for _deployment packages_. A DAR file is a standard ZIP file with additional metadata information contained in a manifest. For a comprehensive description of the DAR format, see the **XL Deploy Packaging Manual**.

### Deployment Package

In the **Unified Deployment Model**, a particular version of an application (consisting of both artifacts and resource specifications) is contained in a single _deployment package_. The package contains all _deployables_ that the application needs. The package is environment-independent and can be deployed to any environment unchanged (see the **Unified Deployment Model**).

Packages can be created outside of XL Deploy (and imported via the GUI or CLI) or, alternatively, you can use the XL Deploy GUI to create a package. Packages can also be exported from XL Deploy.

XL Deploy accepts packages in the Deployment ARchive (DAR) format. For a comprehensive description of the DAR format, see the **XL Deploy Packaging Manual**.

### Dictionaries

A dictionary is a CI that contains environment-specific entries for placeholder resolution. Entries can be added in the GUI or using the CLI. This allows the deployment package to remain environment-independent so it can be deployed unchanged to multiple environments.

A dictionary value can refer to another dictionary entry. This is accomplished by using the <code>&#123;&#123;..}}</code> placeholder syntax.

For example:
<table border="1">
<tr><td>APP_NAME</td><td>XL Deploy</td></tr>
<tr><td>MESSAGE</td><td>Welcome to &#123;&#123;APP_NAME}}!</td></tr>
</table>

The value belonging to key MESSAGE will be "Welcome to XL Deploy!". Placeholders may refer to keys from any dictionary in the same environment.

If a dictionary is associated with an environment this means that, by default, values from the dictionary are applied to all deployments targeting the environment. It is also possible to restrict the dictionary values to deployments to specific _containers_ within the environment or to deployments of specific _applications_ to the environment. These restrictions can be specified on the dictionary's _Restrictions_ tab. A deployment must meet **all** restrictions for the dictionary values to be applied.

**Note:** An unrestricted dictionary cannot refer to entries in a restricted dictionary.

Dictionaries are evaluated in the order in which they appear in the GUI. The first dictionary that defines a value for a placeholder is the one that XL Deploy uses for that placeholder.

For an example of using placeholders in CI properties, see the **XL Deploy Packaging Manual**.

To use dictionaries for storing sensitive information like passwords, see **Encrypted Dictionary**.

### Directories

A directory is a CI used for grouping other CIs. Directories exist directly below the repository root nodes and may be nested. Directories are also used to group security settings.

For example, you could create the directories _Administrative_, _Web_ and _Financial_ under the **Applications** node to group the available applications in these categories.

### Embedded CIs

Embedded CIs are CIs that are part of another CI and can be used to model additional settings and configuration for the parent CI. Embedded CI types are identified by their source deployable type and their container (or parent) type.

Embedded CIs, like regular CIs, have a type and properties and are stored in the repository. Unlike regular CIs, they are not individually compared in the delta analysis phase of a deployment. This means that if an embedded CI is changed, this will be represented as a MODIFY delta on the parent CI.

See the **Customization Manual** for more information.

### Encrypted Dictionaries

An encrypted dictionary is a dictionary that stores sensitive information and for which all contained values are encrypted by XL Deploy. Encrypted dictionaries store key - value pairs and are associated with environments just like regular dictionaries. When a value from an encrypted dictionary is used in a CI property or placeholder, the XL Deploy CLI and GUI will only show the encrypted values. Once the value is used in a deployment, the value is decrypted and can be used by XL Deploy and the plugins. For security reasons the value of an encrypted dictionary will be blank when used in a CI property that is not password enabled.

### Environment

An _environment_ is a grouping of infrastructure items, such as hosts, servers, clusters, etc. Environments can contain any combination of infrastructure items that are used in your situation. An environment is used as the target of a deployment, allowing _deployables_ to be mapped to members of the environment.

In addition to physical environments, XL Deploy also allows definition of a _cloud environment_ -- an environment containing members that run on a cloud platform. Cloud environments are defined using an _environment template_. Environment templates can be instantiated causing one or more instances to be launched on the target cloud platform and resulting in a cloud environment available as a deployment target. See the cloud platform specific manuals for details.

**Note**: cloud environments require _XL Scale_ to be installed.

### File encoding

Different files in artifacts or deployment packages might have different encodings / character sets. In order to make sure that these files are kept in their correct encoding while running them through the placeholder replacement, you can specify the encoding in the artifact property called `fileEncodings`. This property is a map that will map regular expressions matching the full path of the file in the artifact to a target encoding. For instance if you have the following files in a `file.Folder` artifact:

* en-US/index.html
* zh-CN/index.html

You can set the following values in the `fileEncodings` property to make sure the Chinese files are interpreted as UTF-16:
<table border=1>
<tr><th>key</th><th>value</th></tr>
<tr><td>zh-CN.*</td><td>UTF-16BE</td></tr>
</table>

### Garbage Collection

Garbage collection can be used to optimise the disk space that is used for the repository. Running garbage collection is only relevant if you deleted a package. When deleting a package, XL Deploy at first only deletes the reference (for performance reasons) and later on you can delete the actual files by running a garbage collect. For imports, garbage collection is not needed. In order to start a Garbage Collection you require admin permissions.

It is certainly not needed to run a garbage collection every delete of a package. The system will not break, it will only occupy a small amount of disk space that you can win back by running garbage collection at a later moment in time.

The Garbage Collector can be started using the CLI by running this command:

    deployit> deployit.runGarbageCollector()

The Garbage Collector stops automatically when the task is finished. The garbage collector is not accessible from the GUI, but the status of the task can be seen when running `listUnfinishedTasks`.

Garbage collection never runs automatically. The easiest way to schedule a garbage collection is to schedule a CLI script (for example with cron on linux) that triggers the garbage collection. How often you want to run the Garbage Collector depends on how many package you delete, and the buffer of free space on your disk, but in general it is not needed to run it more than once per day.


### Orchestrators

#### Types of Orchestrators

An _orchestrator_ in XL Deploy combines the _steps_ for the individual component changes into an overall deployment workflow. The default orchestrator "interleaves" all individual component changes by running all steps of a given order for all components (see _steplist_ for an explanation of step order). This results in an overall workflow that first stops all containers, then removes all old components, then adds the new ones etc.

The following plan shows an example of an interleaved plan. The first number is the step number, the second number is the order of the step:

![Default orchestrator](images/orchestrators-default.png "Default orchestrator")

As you can see, all the steps from both containers are put together in one interleaved plan. The steps with the same weight have been ordered together. This will cause the "interleaving" of all the steps.

Orchestrators are also responsible for deciding which parts of the plan can be executed in parallel. To enable parallelism, you must select an orchestrator for your deployment that supports parallelism. One can identify such orchestrator by its name that has a `parallel-` prefix. The effect of parallelism for every orchestrator can vary, please see the description below for details.

In addition to the default orchestrator, XL Deploy also ships with three main types of orchestrators :

* _By Container_ orchestrators. This type of orchestrators groups steps that deal with the same container together, enabling deployments across a farm of middleware.
    * `sequential-by-container` will deploy to all containers sequentially. The order of deployment is defined by alphabetic order of the containers' names.
    * `parallel-by-container` will deploy to all containers in parallel.
    * `container-by-container-serial` is a **deprecated** orchestrator and is kept for backward compatibility. If specified, behaves as `sequential-by-container`.
* _By Composite Package_ orchestrators. This type of orchestrators groups steps per contained package together.
    * `sequential-by-composite-package` will deploy member packages of a composite package sequentially. The order of the member packages in the composite package is preserved.
    * `parallel-by-composite-package` will deploy member packages of a composite package in parallel.
    * `composite-package` is a **deprecated** orchestrator and is kept for backward compatibility. If specified, behaves as `sequential-by-composite-package`.
* _By Deployment Group_ orchestrators (separate plugin). This type of orchestrators uses the _deployment group_ synthetic property on a container to group steps for all containers with the same deployment group.
    * `sequential-by-deployment-group` will deploy to each of group sequentially. The order of deployment is defined by ascending order of the _deployment group_ property. If the property is not specified, this group will be deployed first.
    * `parallel-by-deployment-group` will deploy to each of group in parallel.
    * `group-based` is a **deprecated** orchestrator and is kept for backward compatibility. If specified, behaves as `sequential-by-deployment-group`.

Note that for orchestrators that specify an order, the order will be reversed for the undeployment.

To get a better understanding of how the orchestrators will put together a plan, we will give a few examples.


![Sequential by container](images/orchestrators-container.png "Sequential by container")


The `sequential-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed sequentially.

![Parallel by container](images/orchestrators-container-p.png "Parallel by container")

The `parallel-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed in parallel.

![Sequential by Composite Package](images/orchestrators-composite.png "Sequential by Composite Package")

The `sequential-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. The these plans will be executed sequentially.

![Parallel by Composite Package](images/orchestrators-composite-p.png "Parallel by Composite Package")

The `parallel-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. The these plans will be executed in parallel.

![Sequential by Deployment Group](images/orchestrators-group.png "Sequential by Deployment Group")

The `sequential-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed sequentially.

![Parallel by Deployment Group](images/orchestrators-group-p.png "Parallel by Deployment Group")

The `parallel-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed in parallel.

It is also possible to create your own orchestrator. See the **Customization Manual** for more information on creating a custom orchestrator.

#### Combining multiple orchestrators

XL Deploy provides possibility to specify multiple orchestrators per deployment to achieve more complex use cases.
Certain rules have to be taken into consideration when using multiple orchestrators:

* **Order does matter**.
        The order in which multiple orchestrators are specified will effect final execution plan. The first orchestrator in the list will be applied first.
* **Recursion**.
        In general, orchestrators create execution plans represented as trees, for example `parallel-by-composite-package`
        orchestrator creates a parallel block with interleaved blocks per each member of the composite package as its leaves.
        The following orchestrator uses execution plan of the preceding orchestrator and scans it for interleaved blocks.
        As soon as it finds one, it will apply its rules independently to each of interleaved blocks.
        As a consequence, the execution tree becomes deeper.
* **Two are enough**.
        Specifying just two orchestrators should cover almost all use cases.

The following examples display how multiple orchestrators must be used:

##### Example

**Problem**: A composite package has to be deployed to an environment that consists of many multiple containers.
And it's required that each member of the package is deployed only when the previous member has been deployed.
To decrease the deployment time, each member has to be deployed in parallel to the containers.

**Solution**: Use two orchestrators `sequential-by-composite-package` and `parallel-by-container`.

Let's show step by step how the orchestrators are being applied and how the execution plan changes on the way.

Deploying a composite package to an environment with multiple containers will require steps that might look like this:

![Steps needed for composite package](images/orchestrators-composed-1.png "Steps needed for composite package")

As soon as the `sequential-by-composite-package` orchestrator is applied to that list the execution plan will look like this:

![Sequential by Composite Package](images/orchestrators-composed-2.png "Sequential by Composite Package")

As a final stage of orchestration, the `parallel-by-container` orchestrator is applied to all interleaved blocks separately and the final result will be like this:

![Parallel by Composite Package](images/orchestrators-composed-3.png "Parallel by Composite Package")

### Staging artifacts

XL Deploy is able to stage artifacts to target hosts before the deployment commences. In order for it to do so, two requirements have to be met:

- The host(s) need to have their `stagingDirectory` set.
- The plugin being used needs to support staging.

If these conditions are met, XL Deploy will copy all artifacts that are being deployed to this host before executing the real deployment. This will ensure that the downtime of your application is minimized. After the deployment has completed successfully XL Deploy will clear out the staging directories.

### Parallel Deployment

XL Deploy is able to run specific parts of the deployment plan in parallel.

XL Deploy decides which parts of the plan will be executed in parallel during Orchestration. By default, no plan will be executed in parallel. Users can enable parallel execution by selecting an Orchestrator that supports parallel execution. More information about configuring parallel deployment using orchestrators can be found in the section about [Orchestrators](referencemanual.html#orchestrators).

### Placeholders

Placeholders are configurable entries in your application that will be set to an actual value at deployment time. This allows the deployment package to be environment-independent and thus reusable.

There are two types of placeholders. _File_ placeholders occur inside of artifacts in the deployment package. XL Deploy scans packages that it imports for text files and searches these text files for file placeholders. The following items are scanned:

* File-type CIs
* Folder-type CIs
* Archive-type CIs

Before a deployment can be performed, a value must be specified for **all** _file_ placeholders in the deployment.

_Property_ placeholders are used in CI properties by specifying them in the package's manifest. In contrast to file placeholders, _property_ placeholders do not necessarily need to have a value from a dictionary. If the placeholder can not be resolved from a dictionary, the placeholder is left as-is.

XL Deploy recognizes placeholders using the following format:

	{{ PLACEHOLDER_KEY }}

Values for placeholders can be provided manually or filled in from a _dictionary_.

There are two special placeholder values for _File_ placeholders:

* _&lt;empty>_ which replaces the placeholder key with an empty string
* _&lt;ignore>_ which ignores the placeholder key, leaving it as-is

Notice that when you upgrade an application, XL Deploy will resolve the values for the placeholders again from the dictionary. If a previously known key is removed from the dictionary, XL Deploy will use the last known value.

### Plan optimisation
During planning, XL Deploy will try to simplify and optimise the plan. The simplifications and optimisations will be performed after the ordinary planning phase.
Simplification is needed to remove intermediate plans that are not necessary. Optimisation is performed to split large step plans into smaller plans. This will give a better overview of how many steps there are, and decreases the amount of network traffic needed to transfer the task state during execution.

Simplification can be switched on and off by switching the `optimizePlan` property of the deployed application. Turning this property off will only disable the simplification, but not the splitting of large plans.

* **Simplification** Simplification will only remove intermediate plans, and never steps. An example: If a parallel plan contains only one sub plan, the intermediate parallel plan is removed because there will not be anything running in parallel.
* **Splitting step plans by step order**  XL Deploy will scan all step plans and if any step plan contains more than 30 steps, it will be split up into serial plans that contain all steps from a given order group.
* **Splitting step plans by size** After splitting the step by order, the plan will again be scanned for step plans that contain more than 100 steps. Those plans will be split into serial plans containing 100 steps each.

### Plugin

A _plugin_ is a self-contained piece of functionality that adds capabilities to the XL Deploy system. Plugins are packaged in a JAR file and installed in XL Deploy's plugins directory. Plugins can contain:

* Functionality to connect to specific middleware
* Host connection methods
* Custom importers

### Repository

XL Deploy's database is called the _repository_. It stores all configuration items (CIs), binary files (such as _deployment packages_) and XL Deploy's security configuration (user accounts and rights). The repository can be stored on disk (default) or in a relational database (see **Configuring Database Storage** in the  **System Administration Manual**).

Each CI in XL Deploy has an id that uniquely identifies the CI. This id is a path that determines the place of the CI in the repository. For instance, a CI with id "Applications/PetClinic/1.0" will appear in the **PetClinic** subfolder under the **/Applications** root folder.

The repository has a hierarchical layout and a version history. All CIs of all types are stored here. The top-level folders indicate the type of CI stored below it. Depending on the type of CI, the repository stores it under a particular folder:

* Application CIs are stored under the **/Applications** folder.
* Environment and Dictionary CIs are stored under the **/Environments** folder.
* Middleware CIs (representing hosts, servers, etc.) are stored under the **/Infrastructure** folder.

The repository acts as a version control system, that is, every change to every object in the repository
is logged and stored. This makes it possible to compare a history of all changes to every CI in the repository. XL Deploy also retains
the history of all changes to _deleted_ CIs. Even if a CI is deleted, the storage it uses will not be freed up so that
it is possible to retrieve the CI as it existed before the deletion. See the **System Administration Manual** for more information about managing the repository.

#### Containment and References

XL Deploy's repository contains CIs that refer to other CIs. There are two ways in which CIs can refer to each other:

* **Containment**. In this case, one CI _contains_ another CI. If the parent CI is removed, so is the child CI. An example of this type of reference is an Environment CI and its deployed applications.
* **Reference**. In this case, one CI _refers_ to another CI. If the referring CI is removed, the referred CI is unchanged. Removing a CI when it is still being referred to is not allowed. An example of this type of reference is an Environment CI and its middleware. The middleware exists in the **/Infrastructure** folder independently of the environments the middleware is in.

#### Deployed Applications

A deployed application is the result of deploying a _deployment package_ to an _environment_. Deployed applications have a special structure in the repository. While performing the deployment, package members are installed as _deployed items_ on individual environment members. In the repository, the _deployed application_ CI is stored under the _Environment_ node. Each of the _deployed items_ are stored under the infrastructure members in the _Infrastructure_ node.

So, deployed applications exist in both the **/Environment** as well as **/Infrastructure** folder. This has some consequences for the security setup. See the **XL Deploy System Administration Manual** for details.

### Resource Specifications

Resource specifications are specifications of middleware resources that an application needs to run. The following are examples of these resources:

* a datasource.
* a queue or topic.
* a connection factory.

### Rollback

XL Deploy supports customized rollbacks of deployments that revert changes made by a (failed) deployment to the exact state before the deployment was started. Rollbacks are triggered manually via the GUI or CLI when a task is active (not yet archived). Changes to deployeds and dictionaries are also rolled back.

### Roles

Roles are functional groups of _principals_ (security users or groups) that are assigned a common set of permissions. In XL Deploy, permissions can _only_ be assigned to roles. Roles are created and mapped to principals in XL Deploy itself, offering an additional layer of abstraction to the backend user store.

### Security

XL Deploy supports a role-based access control scheme to ensure the security of your middleware and deployments. The security mechanism is based on the concepts of _roles_ and _permissions_. A (security) role is a group of entities that can be authenticated and that can be assigned rights over resources in XL Deploy.  These rights can be either global (that is, they apply to all of XL Deploy, such as login permission) or relevant for a particular CI or set of CIs (for instance, the permission to read certain CIs in the repository).

The security system uses the same permissions whether the system is accessed via the GUI or the CLI.

For more information about XL Deploy's security system, see the **System Administration Manual**.

### Step

A step is a concrete action to be performed to accomplish a _task_. Steps are contributed by _plug-ins_ based on the deployment that is being performed. All steps for a particular deployment are grouped together in a _steplist_. XL Deploy ships with many step implementations for common actions. Other, middleware-specific steps are contributed by the plugins.

The following are examples of steps:

* Copy file /foo/bar to host1, directory /bar/baz.
* Install petclinic.ear on the WAS server on was1.
* Restart the Apache HTTP server on web1.

The user can perform actions on steps, but most interaction with the step will be done by the task itself.

The user can:

* **Skip the step**. Mark the step to be skipped by the task. When the task is executing and the skipped step becomes the current step, the task will skip the step without executing it. The step will be marked _skipped_, and the next step in line will be executed. The step can only be skipped when the step is _pending_, _failed_, or _paused_.
* **Unskip the step**. Undo the marking for skipping of the step.

![Step State](images/step-state-diagram.png "Step State Diagram")

### Steplist

A steplist is a sequential list of _steps_ that are contributed by one or more _plugins_ when a deployment is being planned.

All steps in a steplist are ordered in a manner similar to /etc/init.d scripts in Unix, with low-order steps being executed before higher-order steps. XL Deploy predefines the following orders for ease of use:

* 0 = PRE_FLIGHT
* 10 = STOP_ARTIFACTS
* 20 = STOP_CONTAINERS
* 30 = UNDEPLOY_ARTIFACTS
* 40 = DESTROY_RESOURCES
* 60 = CREATE_RESOURCES
* 70 = DEPLOY_ARTIFACTS
* 80 = START_CONTAINERS
* 90 = START_ARTIFACTS
* 100 = POST_FLIGHT

### Tag-based Deployments

Tags make it easier to configure deployments by marking which deployables should be mapped to which containers.

To perform a deployment using tags, specify tags on the deployables and containers. Tags can be specified either in the imported package or by using the repository browser. When deploying a package to an environment, XL Deploy will match the deployables and containers based on the following rules:

* Both of them have **no** tags.
* Either of them has a tag called '\*'.
* Either of them has a tag called '+' and the other has at least one tag.
* Both have at least one tag in common.

If none of these rules apply, XL Deploy will not generate a deployed for the deployable-container combination.

The following diagram shows which tags match when:

![Tag Matching](images/tag-matching.png "Tag Matching")

An example scenario is deploying a front-end and back-end application to two application servers. By tagging the front-end EAR and front-end application server both with 'FE' and the back-end EAR and back-end server with 'BE', XL Deploy will automatically create the correct deployeds.

### Task

A task is an activity in XL Deploy. When starting a deployment, XL Deploy will create and start a task. The task contains a list of _steps_ that must be executed to successfully complete the task. XL Deploy will execute each of the steps in turn. When all of the steps are successfully executed, the task itself is successfully executed. If one of the steps fails, the task itself is marked failed.

XL Deploy supports the following types of tasks:

* **Deploy application**. This task deploys a package onto an environment.
* **Update application**. This task updates an existing deployment of an application.
* **Undeploy application**. This task undeploys a package from an environment.
* **Rollback**. This task rolls back a deployment.
* **Discovery**. This task discovers middleware on a host.
* **Control Task**. This task interacts with middleware on demand.

#### Task Recovery

XL Deploy periodically stores a snapshot of the tasks in the system to be able to recover tasks if the server is stopped abruptly. XL Deploy will reload the tasks from the recovery file when it restarts. The tasks, deployed item configurations and generated steps will all be recovered. Tasks that were executing (or failing, stopping or aborting) in XL Deploy when the server stopped will be put in _failed_ state so the user can decide whether to rerun or cancel it. Only tasks that have been _pending_, _scheduled_ or _started_ will be recovered.

#### Scheduling

XL Deploy allows you to schedule a task for execution at a specified later moment in time. All task types can be scheduled, this includes deployment tasks, control tasks and discovery tasks. You should prepare your task like you would normally do. Then instead of starting it, you can schedule it to any given date and time in the future. To prevent mistakes, you are not able to schedule tasks in the past. Notice that the amount of time you can schedule a task in the future is limited by a system specific value. Although you are guaranteed to be able to schedule a task at least 3 weeks ahead.
When a task is scheduled, the task is created and the status is set to scheduled. It will automatically start executing when the scheduled time has passed. Notice that the task will be _QUEUED_ if there is no executor available.

A scheduled task will never be archived automatically after it has been executed, so you need to do that manually when the execution has finished. When a scheduled task encounters an failure during execution, the task will be left with a _FAILED_ state in the system. A user should manually correct the problem and is then able to continue the task, or reschedule it.

It is possible to start a scheduled task immediately when required. The task will then start executing directly and the task is not scheduled anymore. It is also possible to reschedule a task to any other given moment in the future. A scheduled task can also be cancelled. When a scheduled task is cancelled it will be removed from the system, and the status will be stored in the task history. To perform these operations, take a look at the [GUI](guimanual.html) or [CLI](climanual.html) manual.

Notice that the server will store the scheduled date and time using the UTC timezone, so for example any log statement will mention the UTC time. When a task is scheduled in relation to your local timezone, you should pass the correct timezone with the call, then XL Deploy will convert it to UTC for you. For you convenience, the UI allows you to enter the scheduled time in you local time zone, and it will automatically pass the correct timezone to the server.

When the server is restarted, either after a manual stop or a forced shutdown, XL Deploy will automatically reschedule all scheduled tasks that are not executed yet. If the task was scheduled for execution during the downtime, it will start immediately.

#### Task State

XL Deploy allows a user to interact with the task. A user can:

* **Start the task**. XL Deploy will try to start executing the steps associated with the task. If there is no executor available, the task will be queued. The task can be started when the task is _pending_, _failed_, _stopped_ or _aborted_. Starting a task when _scheduled_ will also unschedule the task.
* **Schedule the task**. XL Deploy will schedule the task to execute it automatically at the specified time. A task can be scheduled when the task is _pending_, _failed_, _stopped_ or _aborted_.
* **Stop the task**. XL Deploy will wait for the currently executing step(s) to finish and will then cleanly stop the task. The state of the task will become _stopping_. Note that, due to the nature of some steps, this is not always possible. For example, a step that calls an external script may hang indefinitely. A task can only be stopped when _executing_.
* **Abort the task**. XL Deploy will attempt to interrupt the currently executing step(s). The state of the task will become _aborting_. If successful, the task is marked _aborted_ and the step is marked _failed_. The task can be aborted when _executing_, _failing_ or _stopping_.
* **Cancel the task**. XL Deploy will cancel the task execution. If the task was _executing_ before, the task will be stored since it may have made changes to the middleware. If the task was _pending_ and never started, it will be removed but not stored. The task can only be cancelled when it is _pending_, _scheduled_, _failed_, _stopped_ or _aborted_.
* **Archive the task**. XL Deploy will finalise the task and store it. Manually archiving is needed to be able to review a task when it is executed, and to decide whether or not a rollback is needed. Archiving the task can only be done when the task is _executed_.

![Task State](images/task-state-diagram.png "Task State Diagram")

### Type System

XL Deploy features a configurable type system that allows modification and addition of CI types. This makes it possible to extend your installation of XL Deploy with new types or change existing types. Types defined in this manner are referred to as _synthetic_ types. The type system is configured using XML files called _synthetic.xml_. All files containing synthetic types are read when the XL Deploy server starts and are available in the system afterwards.

Synthetic types are first-class citizens in XL Deploy and can be used in the same way that the built-in types are used. This means they can be included in deployment packages, used to specify your middleware topology and used to define and execute deployments. Synthetic types can also be edited in the XL Deploy GUI, including new types and added properties.

For more information about extending XL Deploy, see the **Customization Manual**.

### Undeploying an Application

This process removes a _deployed application_ from an _environment_. XL Deploy stops the application and undeploys all its components from the target middleware.

### Unified Deployment Model (UDM)

The UDM is XebiaLabs' model for describing deployments and is used in XL Deploy. The UDM consists of the following components:

* **Deployment Package**. This is an environment-independent package containing _deployable_ CIs that together form a complete application.
* **Environment**. This is an environment containing _containers_ (deployment targets, i.e. servers that applications can be deployed on). An example is a test environment containing a cluster of WebSphere servers.
* **Deployment**. This is the process of configuring and installing a _deployment package_ onto a specific _environment_. It results in _deployeds_ describing the coupling of a _deployable_ and a _container_.

### Upgrading an Application

This process replaces an application deployed to an _environment_ with another version of the same application. When performing an upgrade, most _deployeds_ can be inherited from the initial deployment. XL Deploy recognizes which artifacts in the deployment package have changed and deploys only the changed artifacts.
