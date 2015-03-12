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

An _orchestrator_ in XL Deploy combines the _steps_ for the individual component changes into an overall deployment workflow. Orchestrators are also responsible for deciding which parts of the plan can be executed in parallel.

For a list of orchestrators that are included with XL Deploy, refer to [Types of orchestrators in XL Deploy](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html).

The following examples illustrate the way different orchestrators work.

## Default orchestrator

This is an example of an interleaved plan. The first number is the step number, the second number is the order of the step:

![Default orchestrator](images/orchestrators-default.png "Default orchestrator")

As you can see, all the steps from both containers are combined in one interleaved plan. The steps with the same weight have been ordered together. This will cause the "interleaving" of all the steps.

## `sequential-by-container` orchestrator

![Sequential by container](images/orchestrators-container.png "Sequential by container")

The `sequential-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed sequentially.

## `parallel-by-container` orchestrator

![Parallel by container](images/orchestrators-container-p.png "Parallel by container")

The `parallel-by-container` orchestrator will put all steps that belong to a specific container into their own interleaved sub plan. These sub plans will be executed in parallel.

## `sequential-by-composite-package` orchestrator

![Sequential by composite package](images/orchestrators-composite.png "Sequential by composite package")

The `sequential-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. These plans will be executed sequentially.

## `parallel-by-composite-package` orchestrator

![Parallel by composite package](images/orchestrators-composite-p.png "Parallel by composite package")

The `parallel-by-composite-package` orchestrator puts all steps belonging to a member of the composite package into a separate interleaved plan. These plans will be executed in parallel.

## `sequential-by-deployment-group` orchestrator

![Sequential by deployment group](images/orchestrators-group.png "Sequential by deployment group")

The `sequential-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed sequentially.

## `parallel-by-deployment-group` orchestrator

![Parallel by deployment group](images/orchestrators-group-p.png "Parallel by deployment group")

The `parallel-by-deployment-group` orchestrator works by dividing all containers in groups, and run every group in isolation. So all the steps belonging to a specific group will end up in the same interleaved plan. All interleaved plans are executed in parallel.

## Example with multiple orchestrators

In this example, a composite package must be deployed to an environment that consists of many multiple containers. Also, each member of the package must only be deployed when the previous member has been deployed. To decrease the deployment time, each member has to be deployed in parallel to the containers.

The solution is to use two orchestrators: `sequential-by-composite-package` and `parallel-by-container`.

Let's show step by step how the orchestrators are being applied and how the execution plan changes on the way.

Deploying a composite package to an environment with multiple containers will require steps that might look like this:

![Steps needed for composite package](images/orchestrators-composed-1.png "Steps needed for composite package")

As soon as the `sequential-by-composite-package` orchestrator is applied to that list the execution plan will look like this:

![Sequential by composite package](images/orchestrators-composed-2.png "Sequential by composite package")

As a final stage of orchestration, the `parallel-by-container` orchestrator is applied to all interleaved blocks separately and the final result will be like this:

![Parallel by composite package](images/orchestrators-composed-3.png "Parallel by composite package")
