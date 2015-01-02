---
title: Understanding orchestrators
categories:
- xl-deploy
subject:
- Deployment
tags:
- planning
- deployment
- orchestrator
---

An _orchestrator_ in XL Deploy combines the _steps_ for the individual component changes into an overall deployment workflow. If no orchestrator is selected, XL Deploy will use the default orchestrator. The default orchestrator "interleaves" all individual component changes by running all steps of a given order for all components (see _steplist_ for an explanation of step order). This results in an overall workflow that first stops all containers, then removes all old components, then adds the new ones etc.

The following plan shows an example of an interleaved plan. The first number is the step number, the second number is the order of the step:

![Default orchestrator](images/orchestrators-default.png "Default orchestrator")

As you can see, all the steps from both containers are put together in one interleaved plan. The steps with the same weight have been ordered together. This will cause the "interleaving" of all the steps.

Orchestrators are also responsible for deciding which parts of the plan can be executed in parallel. To enable parallelism, you must select an orchestrator for your deployment that supports parallelism. One can identify such orchestrator by its name that has a `parallel-` prefix. The effect of parallelism for every orchestrator can vary, please see the description below for details.

## Types of orchestrators

XL Deploy ships with the following main types of orchestrators :

* _By Container_ orchestrators. This type of orchestrators groups steps that deal with the same container together, enabling deployments across a farm of middleware.
    * `sequential-by-container` will deploy to all containers sequentially. The order of deployment is defined by alphabetic order of the containers' names.
    * `parallel-by-container` will deploy to all containers in parallel.
* _By Composite Package_ orchestrators. This type of orchestrators groups steps per contained package together.
    * `sequential-by-composite-package` will deploy member packages of a composite package sequentially. The order of the member packages in the composite package is preserved.
    * `parallel-by-composite-package` will deploy member packages of a composite package in parallel.
* _By Deployment Group_ orchestrators (separate plugin). This type of orchestrators uses the _deployment group_ synthetic property on a container to group steps for all containers with the same deployment group.
    * `sequential-by-deployment-group` will deploy to each of group sequentially. The order of deployment is defined by ascending order of the _deployment group_ property. If the property is not specified, this group will be deployed first.
    * `parallel-by-deployment-group` will deploy to each of group in parallel.

Note that for orchestrators that specify an order, the order will be reversed for the undeployment.

To get a better understanding of how the orchestrators will put together a plan, we will give a few examples.

![Sequential by container](images/orchestrators-container.png "Sequential by container")

The `sequential-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed sequentially.

![Parallel by container](images/orchestrators-container-p.png "Parallel by container")

The `parallel-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed in parallel.

![Sequential by Composite Package](images/orchestrators-composite.png "Sequential by Composite Package")

The `sequential-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. These plans will be executed sequentially.

![Parallel by Composite Package](images/orchestrators-composite-p.png "Parallel by Composite Package")

The `parallel-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. These plans will be executed in parallel.

**Note:** You can use the `sequential-by-composite-package` or `parallel-by-composite-package` orchestrator with a composite package that has other composite packages nested inside. When XL Deploy creates the interleaved subplans, it will flatten the composite packages and maintain the order of the deployment package members.

![Sequential by Deployment Group](images/orchestrators-group.png "Sequential by Deployment Group")

The `sequential-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed sequentially.

![Parallel by Deployment Group](images/orchestrators-group-p.png "Parallel by Deployment Group")

The `parallel-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed in parallel.

It is also possible to create your own orchestrator. <!--See the **Customization Manual** for more information on creating a custom orchestrator.-->

## Combining multiple orchestrators

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

### Example

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
