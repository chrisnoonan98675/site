---
title: The deployment process from package to deployment
tags:
- deployment
---

Deploying applications is one of the main features of XL Deploy. It all starts with selecting the deployment package and an environment. If everything goes well, the result of your deployment is the application running in its target environment.

## Phases
The deployment process consists of several phases:

1. Specification
1. Delta analysis
1. Orchestration
1. Planning
1. Execution

### 1. Specification
When you want to deploy an application it starts with specification. During specification, you select the application that you want to deploy and the environment you want to deploy to. Then the deployables are mapped to the containers in the environment. XL Deploy helps you create correct mappings, manually or automatically. Given the application, environment, and mappings, XL Deploy can perform Delta Analysis.

### 2. Delta Analysis
XL Deploy will use the resulting mappings to perform Delta analysis. A Delta is the difference between the specification and the actual state. During Delta analysis XL Deploy calculates what needs to be done to deploy the application. It does that by comparing the specification against the current state of the application. This will result in a delta specification.

### 3. Orchestration
Orchestration will use the delta specification to structure your deployment. For example the order in which parts of the deployment will be executed, and which parts will be executed sequentially or in parallel. Depending on how you want the deployment to be structured, you choose a combination of orchestrators.

### 4. Planning
In the [planning](understanding-the-xl-deploy-planning-algorithm.html) phase XL Deploy uses the orchestrated deployment to determine the final plan. The plan contains the steps to deploy the application. A step is a individual action that is taken during execution. The plugins and rules determine which steps are added to the plan. The result is the plan that can be executed to perform the deployment.

### 5. Execution
During execution of the plan XL Deploy will execute the steps. After all steps have been executed successfully, the application is deployed.

## Deployments, Updates and Undeployments
The process is the same for deploying an application, upgrading an application to a new version, downgrading an application to an older version, or undeploying an application.
