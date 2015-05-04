---
title: Create a parallel group
categories:
- xl-release
subject:
- Task types
tags:
- task
- parallel group
---

A parallel group is a container for tasks that should be executed simultaneously. 

## Add a parallel group in the release editor

In the release editor, click **Add task** to add a parallel group. You can then drag existing tasks into the parallel group or click **Add task** in the group to create a new task.

![Parallel group](../images/parallel-group.png)

In this example, the XL Deploy tasks and the "Divide test cases" task will start simultaneously and the parallel group "Deploy to ACC" will not complete until all tasks are complete. Then will XL Release start the next task ("Notify QA installation").

## Adjust parallel groups in the planner

By default, all tasks in a parallel are started when the group is started and are executed in parallel. The planner tool allows you to do planning on a detailed level and explicitly express dependencies between tasks.

This is an example of a ParallelGroup with three tasks. Task 1 already has a due date.

![Task 1](../images/planner-parallel-dependency-1.png) 

Connect Task 1 to Task 2 by dragging an arrow from the right edge of Task 1 to the left edge of Task 2.

![Connect to Task 2](../images/planner-parallel-dependency-2.png) 

As a result, Task 2 will start at the due date of Task 1.

![Task 1 and 2 connected](../images/planner-parallel-dependency-3.png)

Task 3 is not connected and because it is inside a parallel group, it will start at the same time as Task 1 (when the parallel group starts).
