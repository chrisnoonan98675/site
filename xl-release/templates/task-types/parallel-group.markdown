---
title: Parallel Group
---

A Parallel Group is a container for tasks that are executed simultaneously.

**Tip:** To group tasks that should be executed in sequence, use the [Sequential Group](/xl-release/how-to/create-a-sequential-group.html) task type (available in XL Release 5.0.0 and later).

To add a Parallel Group to a template or release:

1. Select [**Release Flow Editor**](/xl-release/how-to/using-the-release-flow-editor.html) from the **Show** menu.
1. [Add a task to a phase](/xl-release/how-to/add-a-task-to-a-phase.html), selecting the Parallel Group type.
1. Add tasks to the group by clicking **Add task** in the group or by dragging existing tasks into the group.

![Parallel Group](/xl-release/images/parallel-group.png)

In this example, the XL Deploy tasks and the "Divide test cases" task will start simultaneously. The "Deploy to ACC" group will not finish until all of its subtasks are complete. Then, XL Release will continue with the next task, "Notify QA installation".

## Adjust Parallel Groups in the planner

By default, all tasks in a Parallel Group start when the group starts. The [planner](/xl-release/how-to/using-the-xl-release-planner.html) allows you to do planning on a detailed level and explicitly express dependencies between tasks.

This is an example of a Parallel Group with three tasks. Task 1 already has a due date.

![Parallel Group: Task 1](/xl-release/images/planner-parallel-dependency-1.png)

Connect Task 1 to Task 2 by dragging an arrow from the right edge of Task 1 to the left edge of Task 2.

![Parallel Group: Connect to Task 2](/xl-release/images/planner-parallel-dependency-2.png)

As a result, Task 2 will start at the due date of Task 1.

![Parallel Group: Task 1 and 2 connected](/xl-release/images/planner-parallel-dependency-3.png)

Task 3 is not connected and because it is inside a Parallel Group, it will start at the same time as Task 1 (when the Parallel Group starts).

## Execute tasks sequentially within Parallel Groups

The [parallel group task type](/xl-release/how-to/create-a-parallel-group.html) allows you to execute tasks in parallel, instead of in sequence. However, by using dependencies among tasks, you can "chain" some or all of the tasks in a parallel group in a sequence.

**Note:** In XL Release 5.0.0 and later, you can use the [sequential group task type](/xl-release/how-to/create-a-sequential-group.html) to group tasks that should be executed in order.

### Execute tasks using multiple parallel groups

This example shows how XL Release can execute deployments to multiple servers in parallel, with the tasks on each server executing sequentially.

In the "Deploy" phase below, there is an outer parallel group called "Concurrent Server Deployments", which groups the tasks for the server. There are two sequential tasks per server: "Deploy code" and "Check results".

![Parallel setup](/xl-release/images/deploy-servers-parallel_setup.png)

In this setup, the default behavior of the "Server 1" and "Server 2" parallel groups is to run the "Deploy code" and "Check results" tasks in parallel. To change this so that the tasks execute sequentially, switch to the planner view.

![Planner view](/xl-release/images/deploy-servers-parallel-planner_view.png)

Here, you can link the tasks to ensure they execute sequentially.

![Linking tasks](/xl-release/images/linking-tasks-in-xlr-planner.png)

Now both server deployment blocks execute in parallel, but the tasks within each deployment are sequential:

![Sequential tasks](/xl-release/images/deploy-servers-parallel_running.png)

### Execute tasks using a single parallel group

The intermediate parallel groups "Server 1" and "Server 2" are not strictly necessary as shown above. You can achieve the same result with a single parallel group. In the release flow:

![Single parallel group](/xl-release/images/seq-task-groups-in-parallel-block-no-wrapper.PNG)

And in the planner:

![Single parallel group in planner view](/xl-release/images/seq-task-groups-in-parallel-block-no-wrapper-in-planner.PNG)

This approach saves one level of nesting of parallel groups; however, you lose some visibility into the logical grouping of tasks for server 1 and server 2. For example, you could no longer collapse only the tasks for server 1; either you see all tasks in "Concurrent Server Deployments", or none. Therefore, the approach with multiple parallel groups is recommended.
