---
title: Understanding XL Deploy's architecture
categories:
- xl-deploy
subject:
- Architecture
tags:
- udm
- plugin
- ci
- rules
- planning
---

XL Deploy features a modular architecture that allows you to change and extend components while maintaining a consistent system. This is a high-level overview of the system architecture:

![XL Deploy architecture](images/xl-deploy-architecture.png)

## The XL Deploy core

XL Deploy's central component is called the *core*. It contains the following functionality:

* The Unified Deployment Engine, which determines what is required to perform a deployment
* Storage and retrieval of deployment packages
* Execution and storage of deployment tasks
* Security
* Reporting

The XL Deploy core is accessed using a REST service. XL Deploy includes two REST service clients: a graphical user interface (GUI) that runs in browsers and a command-line interface (CLI) that interprets Jython.

XL Deploy plugins provide support for various middleware platforms. These plugins add capabilities to XL Deploy and may be delivered by XebiaLabs or custom-built by XL Deploy users.

## XL Deploy and plugins

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

## Preparing and performing deployments in XL Deploy

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

## Deployments and plugins

The following diagram depicts the way in which a plugin is involved in a deployment:

![Deployment](images/Deployment-full.png)

The transitions that are covered by a puzzle-piece are the ones that interact with the plugins, while the XL Deploy logo indicates that the transition is handled by the XL Deploy core.

The following sections describe how plugins are involved in the aforementioned activities.

### The Specification stage

In the Specification stage, the deployment to be executed is specified. This includes selecting the deployment package and members to be deployed, as well as mapping each package member to the environment members that they should be deployed to.

#### Specifying CIs

The XL Deploy plugin defines which CIs the XL Deploy core can use to create deployments. When a plugin is loaded into the core, XL Deploy scans the plugin for CIs and adds these to its CI registry. Based on the CI information in the plugin, XL Deploy will categorize each CI as either a deployable CI (defining the *what* of the deployment) or a container CI (defining the *where* of the deployment).

#### Specifying relationships

While the deployable CI represents the passive resource or artifact, the deployed CI represents the *active* version of the deployable CI when it has been deployed in a container. By defining deployed CIs, the plugin indicates which combinations of deployable and container are supported.

#### Configuration

Each deployed CI represents a combination of a deployable CI and a container CI. It is important to note that one deployable CI can be deployed to multiple container CIs. For example, an EAR file can be deployed to two application servers. In a deployment, this is modeled as multiple deployed CIs.

You may want to configure a deployable CI differently depending on the container CI or environment to which it is deployed. This can be done by configuring the properties of the deployed CI differently.

Configuration of deployed CIs is handled in the XL Deploy core. Users perform this task either via the GUI or via the CLI. An XL Deploy plugin can influence this process by providing default values for its properties.

#### Result

The result of the Specification stage is a deployment specification that contains deployed CIs that describe which deployable CIs are mapped to which container CIs with the needed configuration.

### The Planning stage

In the Planning stage, the deployment specification and the subplans that were created in the Orchestration stage are processed. During this stage, the XL Deploy core performs the following procedure:

1. Preprocessing
1. Contributor processing
1. Postprocessing

During each part of this procedure, the XL Deploy plugin is invoked so it can add required deployment steps to the subplan.

#### Preprocessing

Preprocessing allows the plugin to contribute steps to the very beginning of the plan. During preprocessing, all preprocessors defined in the plugin are invoked in turn. Each preprocessor has full access to the delta
specification. As such, the preprocessor can contribute steps based on the entire deployment. Examples of such steps are sending an email before starting the deployment or performing pre-flight checks on CIs in that deployment.

#### Deployed CI processing

Deployed CIs contain both the data and the behavior to make a deployment happen. Each deployed CI that is part of the deployment can contribute steps to ensure that it is deployed or configured correctly.

Steps in a deployment plan must be specified in the correct order for the deployment to succeed. Furthermore, the order of these steps must be coordinated among an unknown number of plugins. To achieve this, XL Deploy weaves all the separate resulting steps from all the plugins together by looking at the order property (a number) they specify.

For example, suppose you have a container CI representing a WebSphere Application Server (WAS) called WasServer. This CI contains the data describing a WAS server (things like host, application directory, and so on) as well as the behavior to manage it. During a deployment to this server, the WasServer CI contributes steps with order 10 to stop the server. Also, it would contribute steps with order 90 to restart it. In the same deployment, a deployable CI called WasEar (representing a WAS EAR file) contributes steps to install itself with order 40. The resulting plan would weave the installation of the EAR file (40) in between the stop (10) and start (90) steps.

This mechanism allows steps (behavior) to be packaged together with the CIs that contribute them. Also, CIs defined by separate plugins can work together to produce a well-ordered plan.

##### Default step orders

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

#### Postprocessing

Postprocessing is similar to preprocessing, but it allows a plugin to add one or more steps to the very end of a plan. A postprocessor could, for example, add a step to send an email after the deployment is complete.

#### Result

The Planning stage results in a deployment plan that contains all steps required to perform the deployment. The deployment plan is ready to be executed.
