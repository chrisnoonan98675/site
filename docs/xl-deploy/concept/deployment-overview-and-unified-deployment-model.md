---
title: Deployment overview and Unified Deployment Model
subject:
Deployment
categories:
xl-deploy
tags:
application
package
deployment
environment
weight: 180
---

XL Deploy is the first out-of-the-box deployment automation solution that allows non-experts to perform application deployments. A deployment consists of all the actions needed to install, configure and start an application on a target environment.

## Unified Deployment Model (UDM)

In XL Deploy, deployments are modeled using the Unified Deployment Model (UDM), which consists of:

* **Deployment package:** An environment-independent *package* that contains deployable configuration items (CIs) that form a complete application.
* **Environment:** A group of infrastructure and middleware *containers*, which are deployment targets; for example, hosts, servers, clusters, and so on.
* **Deployment:** The process of configuring and installing a deployment package in a specific environment. Deployment results in *deployeds*, which describe the combination of a deployable and a container.

Deployment packages represent versions of an application; for example, the application MyWebsite could have deployment packages for version 1.0.0, 2.0.0, and so on. You can define [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html) among application versions; this ensures that when you try to deploy a specific deployment package while its dependencies are not already present in the target environment, the dependent packages will automatically be deployed, or the deployment will fail.

Additionally, deployment packages and all other configuration items (CIs) stored in XL Deploy's Repository are version-controlled; refer to [The XL Deploy Repository](/xl-deploy/concept/the-xl-deploy-repository.html) for more information.

## UDM in the GUI

This screenshot depicts the main UDM concepts as they appear in the Deployment Workspace of the XL Deploy User Interface:

![Unified Deployment Model](images/udm-highlevel-deployit.png)

Deployments are defined by:

* A package containing *what* is to be deployed (shown on the left)
* An environment defining *where* the package is to be deployed (shown on the right)
* Configuration of the deployment specifying *customizations* (possibly environment-specific) to the package to be deployed (the middle)

Packages and environments are made up of smaller parts:

* Packages consist of _deployables_, which are things that can be deployed
* Environments consist of _containers_, which are things that can be deployed to

There are two types of deployables:

* Artifacts, which are physical files such as an EAR file, a WAR file, or a folder of HTML files
* Resource specifications, which are middleware resources that an application requires to run, such as a queue, a topic, or a datasource

Containers are the actual middleware products to which deployables are deployed. Examples of containers are an application server such as Tomcat or WebSphere, a database server, and a WebSphere node or cell.

## The deployment process

The deployment process consists of several phases:

1. Specification
1. Delta analysis
1. Orchestration
1. Planning
1. Execution

The process is the same whether you are deploying an application, upgrading an application to a new version, downgrading an application to an older version, or undeploying an application.

### Phase 1 Specification

Deploying an application starts with specification. During specification, you select the application that you want to deploy and the environment to which you want to deploy. Then the deployables are mapped to the containers in the environment. XL Deploy helps you create correct mappings, manually or automatically. Given the application, environment, and mappings, XL Deploy can perform delta analysis.

### Phase 2 Delta analysis

XL Deploy uses the resulting mappings to perform delta analysis. A delta is the difference between the specification and the actual state. During delta analysis, XL Deploy calculates what needs to be done to deploy the application. It does so by comparing the specification against the current state of the application. This will result in a delta specification.

### Phase 3 Orchestration

Orchestration uses the delta specification to structure your deployment; for example, the order in which parts of the deployment will be executed, and which parts will be executed sequentially or in parallel. Depending on how you want the deployment to be structured, you choose a combination of orchestrators.

### Phase 4 Planning

In the [planning phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html), XL Deploy uses the orchestrated deployment to determine the final plan. The plan contains the steps to deploy the application. A step is a individual action that is taken during execution. The plugins and rules determine which steps are added to the plan. The result is the plan that can be executed to perform the deployment.

### Phase 5 Execution

During execution of the plan, XL Deploy executes the steps. After all steps have been executed successfully, the application is deployed.

## Example

Assume you have a package that consists of an EAR file (an artifact), a datasource (a resource specification), and some configuration files (artifacts). You want to deploy this package to an environment containing an application server and a host (both containers). The deployment could look like this:

![Unified Deployment Model detail](images/udm-lowlevel-deployit.png)

The EAR file and the datasource are deployed to the application server and the configuration files are deployed to the host.

As you can see above, the deployment also contains smaller parts. The combination of a particular deployable and container is called a *deployed*. Deployeds represent the deployable on the container and contain customizations for the specific deployable/container combination. For example, the `PetClinic-ds` deployed represents the datasource from the deployment package as it will be deployed to the `was.Server` container. You can specify a number of properties on the deployed:

![Unified Deployment Model deployed](images/udm-lowlevel-deployed-deployit.png)

For example, the deployed has a specific username and password that may be different when deploying the same datasource to another server.

After a deployment is specified and configured using the concepts above (and the *what*, *where* and *customizations* are known), XL Deploy takes care of the *how* by preparing a list of steps that need to be executed to perform the actual deployment. Each step specifies one action to take, such as copying a file, modifying a configuration file, or restarting a server.

When the deployment is started, XL Deploy creates a *task* to perform the deployment. The task is an independent process running on the XL Deploy server. The steps are executed sequentially and the deployment is finished successfully when all steps have been executed. If an error occurs during deployment, the deployment stops and you must manually intervene.

The result of the deployment is stored in XL Deploy as a *deployed application* and appears on the right side of the Deployment Workspace. Deployed applications are organized by environment so it is clear where each application is deployed. You can also see which parts of the deployed package are deployed to each environment member.

The final result of the sample deployment looks like this:

![Unified Deployment Model deployed application](images/udm-lowlevel-deployedapplication-deployit.png)
