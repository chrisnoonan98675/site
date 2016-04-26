---
title: Execute tasks sequentially in a parallel group
categories:
- xl-release
subject:
- Tasks
tags:
- task
- parallel group
---

The [parallel group task type](/xl-release/how-to/create-a-parallel-group.html) allows you to execute tasks in parallel, instead of in sequence. However, by using dependencies among tasks, you can "chain" some or all of the tasks in a parallel group in a sequence.

**Note:** In XL Release 5.0.0 and later, you can use the [sequential group task type](/xl-release/how-to/create-a-sequential-group.html) to group tasks that should be executed in order.

## Execute tasks using multiple parallel groups

This example shows how XL Release can execute deployments to multiple servers in parallel, with the tasks on each server executing sequentially.

In the "Deploy" phase below, there is an outer parallel group called "Concurrent Server Deployments", which groups the tasks for the server. There are two sequential tasks per server: "Deploy code" and "Check results".

![Parallel setup](../images/deploy-servers-parallel_setup.png)

In this setup, the default behavior of the "Server 1" and "Server 2" parallel groups is to run the "Deploy code" and "Check results" tasks in parallel. To change this so that the tasks execute sequentially, switch to the planner view.

![Planner view](../images/deploy-servers-parallel-planner_view.png)

Here, you can link the tasks to ensure they execute sequentially.

![Linking tasks](../images/linking-tasks-in-xlr-planner.png)

Now both server deployment blocks execute in parallel, but the tasks within each deployment are sequential:

![Sequential tasks](../images/deploy-servers-parallel_running.png)

## Execute tasks using a single parallel group

The intermediate parallel groups "Server 1" and "Server 2" are not strictly necessary as shown above. You can achieve the same result with a single parallel group. In the release flow:

![Single parallel group](../images/seq-task-groups-in-parallel-block-no-wrapper.PNG)

And in the planner:

![Single parallel group in planner view](../images/seq-task-groups-in-parallel-block-no-wrapper-in-planner.PNG)

This approach saves one level of nesting of parallel groups; however, you lose some visibility into the logical grouping of tasks for server 1 and server 2. For example, you could no longer collapse only the tasks for server 1; either you see all tasks in "Concurrent Server Deployments", or none. Therefore, the approach with multiple parallel groups is recommended.
