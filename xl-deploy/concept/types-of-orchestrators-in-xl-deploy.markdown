---
title: Types of orchestrators in XL Deploy
categories:
- xl-deploy
subject:
- Orchestration
tags:
- planning
- deployment
- orchestrator
---

An _orchestrator_ in XL Deploy combines the _steps_ for the individual component changes into an overall deployment workflow. 

If no orchestrator is selected, XL Deploy will use the default orchestrator. The default orchestrator "interleaves" all individual component changes by running all steps of a given order for all components. This results in an overall workflow that first stops all containers, then removes all old components, then adds the new ones etc.

Orchestrators are also responsible for deciding which parts of the plan can be executed in parallel. To enable parallelism, you must select an orchestrator for your deployment that supports parallelism. You can identify such orchestrator by its name that has a `parallel-` prefix. The effect of parallelism for every orchestrator can vary, please see the description below for details.

**Note:** For orchestrators that specify an order, the order is reversed for undeployment.

For illustrated examples of the way different orchestrators work, refer to [Understanding orchestrators](/xl-deploy/concept/understanding-orchestrators.html).

## *By container* orchestrators

*By container* orchestrators group steps that deal with the same container together, enabling deployments across a farm of middleware.

* `sequential-by-container` will deploy to all containers sequentially. The order of deployment is defined by alphabetic order of the containers' names.
* `parallel-by-container` will deploy to all containers in parallel.

## *By composite package* orchestrators

*By composite package* orchestrators group steps per contained package together.

* `sequential-by-composite-package` will deploy member packages of a composite package sequentially. The order of the member packages in the composite package is preserved.
* `parallel-by-composite-package` will deploy member packages of a composite package in parallel.

**Tip:** You can use the `sequential-by-composite-package` or `parallel-by-composite-package` orchestrator with a composite package that has other composite packages nested inside. When XL Deploy creates the interleaved sub-plans, it will flatten the composite packages and maintain the order of the deployment package members.

## *By deployment group* orchestrators

*By deployment group* orchestrators use the _deployment group_ property of a middleware *container* to group steps for all containers in the same group.

All component changes for a given container are put in the same group, and all groups are combined into a single (sequential or parallel) deployment workflow. This allows fine-grained control over which containers are deployed to together.

* `sequential-by-deployment-group` will deploy to each member of group sequentially. The order of deployment is defined by ascending order of the deployment group property. If the property is not specified, this group will be deployed first.
* `parallel-by-deployment-group` will deploy to each member of group in parallel.

The following *by deployment group* orchestrators are supported in XL Deploy 5.0.0 and later:

* `sequential-by-deployment-sub-group` will enforce a sequential deployment order based on the container's *deployment sub-group* property.
* `parallel-by-deployment-sub-group` will enforce a parallel deployment order based on the container's *deployment sub-group* property.
* `sequential-by-deployment-sub-sub-group` will enforce a sequential deployment order based on the container's *deployment sub-sub-group* property.
* `parallel-by-deployment-sub-sub-group` will enforce a parallel deployment order based on the container's *deployment sub-sub-group* property.

**Note:** The `group-based` orchestrator is the as `sequential-by-deployment-group`. It is kept for backward compatibility.

## Combining multiple orchestrators

You can specify multiple orchestrators per deployment to achieve complex use cases. When using multiple orchestrators:

* **Order matters.** The order in which multiple orchestrators are specified will affect final execution plan. The first orchestrator in the list will be applied first.

* **Recursion.** In general, orchestrators create execution plans represented as trees. For example, the `parallel-by-composite-package` orchestrator creates a parallel block with interleaved blocks per each member of the composite package as its leaves. The subsequent orchestrator uses the execution plan of the preceding orchestrator and scans it for interleaved blocks. As soon as it finds one, it will apply its rules independently of each interleaved block. As a consequence, the execution tree becomes deeper.

* **Two are enough.** Specifying just two orchestrators should cover almost all use cases.
