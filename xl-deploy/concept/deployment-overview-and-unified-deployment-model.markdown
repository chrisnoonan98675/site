---
title: Deployment overview and Unified Deployment Model
subject:
- Deployment
categories:
- xl-deploy
tags:
- application
- package
- deployment
- environment
---

XL Deploy is the first out-of-the-box deployment automation solution that allows non-experts to perform application deployments. A deployment consists of all the actions needed to install, configure and start an application on a target environment.

## Unified Deployment Model (UDM)

At a high level, deployments in XL Deploy are modeled using the _Unified Deployment Model (UDM)_. The UDM consists of the following components:

* **Deployment package**. This is an environment-independent package containing _deployable_ configuration items (CIs) that form a complete application.
* **Environment**. This is an environment containing _containers_ (deployment targets; that is, servers that applications can be deployed on). An example is a test environment containing a cluster of WebSphere servers.
* **Deployment**. This is the process of configuring and installing a _deployment package_ onto a specific _environment_. It results in _deployeds_ describing the coupling of a _deployable_ and a _container_.

## UDM in the GUI

The following diagram depicts the main concepts in the UDM as they appear in the XL Deploy GUI:

![Unified Deployment Model](images/udm-highlevel-deployit.png)

Deployments are defined by:

* A package containing _what_ is to be deployed (shown on the left side of the picture)
* An environment defining _where_ the package is to be deployed (shown on the right side of the picture)
* Configuration of the deployment specifying (possibly environment-specific) _customizations_ to the package to be deployed (the middle square).

Of course, packages and environments are made up of smaller parts:

* Packages consist of deployables or _things that can be deployed_.
* Environments consist of containers or _things that can be deployed to_.

Deployables come in two flavors: _artifacts_ are physical files (examples are an EAR file, a WAR file or a folder of static HTML) and _resource specifications_ are middleware resources that the application needs to run (examples are a queue, a topic or a datasource). These types of deployables are put together in a package.

Containers are actual middleware products that deployables are deployed to. Examples of containers are an application server such as Tomcat or WebSphere, a database server, or a WebSphere node or cell.

## Example

Let's say we have a package that consists of an EAR file (an artifact), a datasource (a resource specification) and some configuration files (artifacts) and we want to deploy this package to an environment containing an application server and a host (both containers). The exact deployment could look something like this:

![Unified Deployment Model detail](images/udm-lowlevel-deployit.png)

In the above picture, you can see that the EAR file and the datasource are deployed to the application server and the configuration files are deployed to the host.

As you can see above, the deployment also consists of smaller parts. The combination of a particular deployable and container is called a _deployed_. Deployeds represent _the deployable on the container_ and contain customizations for this specific deployable - container combination. For example, the `PetClinic-ds` deployed in the picture represents the datasource from the deployment package as it will be deployed to the `was.Server` container. The deployed allows a number of properties to be specified:

![Unified Deployment Model - deployed](images/udm-lowlevel-deployed-deployit.png)

For example, the deployed has a specific username and password that may be different when deploying the same datasource to another server.

After a deployment is specified and configured using the concepts above (and the _what_, _where_ and _customizations_ are known), XL Deploy takes care of the _how_ by preparing a list of steps that need to be executed to perform the actual deployment. Each step specifies one action to be taken, such as copying of a file, modifying a configuration file or restarting a server.

When the deployment is started, XL Deploy creates a _task_ to perform the deployment. The task is an independent process running on the XL Deploy server. The steps are executed sequentially and the deployment is finished successfully when all steps have been executed. If an error occurs during deployment, the deployment stops and an operator is required to intervene.

The result of the deployment is stored in XL Deploy as a _deployed application_ and is shown in the Deployed Application Browser on the right hand side of the GUI. The deployed applications are organised by environment so it is clear where the application is deployed to. It is also possible to see which parts of the deployed package have been deployed to which environment member.

The final result of our sample deployment looks like this:

![Unified Deployment Model - deployed application](images/udm-lowlevel-deployedapplication-deployit.png)
