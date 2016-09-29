---
title: Examples of orchestrators in XL Deploy
categories:
- xl-deploy
subject:
- Orchestration
tags:
- planning
- deployment
- orchestrator
weight: 152
---

An [orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html#by-deployment-group-orchestrators) combines the steps for the individual component changes into an overall deployment workflow. This example shows how different orchestrators affect the deployment of a package containing an EAR file, a WAR file, and a datasource specification to an environment containing two JBoss Application Server server groups and one Apache Tomcat virtual host.

## Default orchestrator

When the [default orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html#default-orchestrator) is used, XL Deploy generates a deployment plan using the default [step order](/xl-deploy/concept/steps-and-steplists-in-xl-deploy.html#steplist).

![Default orchestrator](images/orchestrator-example-default.png)

## *By container* orchestrators

When the [`parallel-by-container`](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html#by-container-orchestrators) orchestrator is used, XL Deploy will deploy to each middleware container in parallel.

![Parallel by container orchestrator](images/orchestrator-example-parallel-by-container.png)

The ![Parallel deployment icon](/images/icon-deployment-plan-parallel.png) icon indicates the parts of the plan that will be executed in parallel. If the `sequential-by-container` orchestrator were used instead, the steps in the deployment plan would be the same, but the ![Sequential deployment icon](/images/icon-deployment-plan-sequential.png) icon would indicate the parts of the plan would be executed sequentially.

## *By deployment group* orchestrators

In this example of the [`parallel-by-deployment-group`](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html#by-deployment-group-orchestrators) orchestrator, the *JBoss-main-server-group* and *Tomcat8-virtualhost* containers are assigned deployment group number 1 and *JBoss-other-server-group* is assigned deployment group number 2.

![Parallel by deployment group](images/orchestrator-example-parallel-by-deploy-group.png)
