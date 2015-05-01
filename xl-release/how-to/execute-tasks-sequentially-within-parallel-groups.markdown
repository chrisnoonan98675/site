---
title: Execute tasks sequentially within parallel groups
categories:
- xl-release
subject: Tasks
tags:
- task
- parallel group
---

XL Release supports parallel groups of tasks in phases, which allow you to execute tasks in parallel, rather then sequentially as standard.

Actually, though, parallel groups can do more than that! By defining dependencies between tasks in a parallel group, you can "chain" some or all of the tasks in a parallel group into a sequence. A parallel group, in effect, is actually a "task group" that supports fully parallel and fully sequential execution, as well as any combination in between.

For example, a question was asked how XL Release could execute deployments to multiple servers in parallel, with the tasks on each server executing sequentially. Here's how:

Inside the Deploy phase below, I have an outer parallel group Concurrent Server Deployments, which groups the tasks for the server. I have two sequential tasks per server: deploy and then check results.

![Parallel setup](../images/deploy-servers-parallel_setup.png)

When set up like this, the default behavior of the parallel groups Server 1 and Server 2 will be to run their Deploy code and Check results tasks in parallel - not what we want. To ensure that these tasks execute sequentially, I switch to the Planner view.  This allows me to link tasks together.

![Planner view](../images/deploy-servers-parallel-planner_view.png)

Once I'm looking at the template in the planner, I can now link the tasks to ensure they execute sequentially:

![Linking tasks](../images/linking-tasks-in-xlr-planner.png)

Now both server deployment blocks execute in parallel, but the tasks within each deployment are sequential:

![Sequential tasks](../images/deploy-servers-parallel_running.png)

The "intermediate" parallel groups Server 1 and Server 2 are not strictly necessary here, by the way. You can achieve the same result with a single parallel group:

![Single parallel group](../images/seq-task-groups-in-parallel-block-no-wrapper.PNG)

![Single parallel group in planner view](../images/seq-task-groups-in-parallel-block-no-wrapper-in-planner.PNG)

With this approach, you save yourself one level of nesting of parallel groups, but also lose some visibility into the "logical groups" of tasks for server 1 and server 2. For example, you can no longer collapse only the tasks for server 1 - either you can see all tasks in Concurrent Server Deployments, or none.

Overall, we recommend the first approach, with intermediate groups, for clarity, especially if there is only one additional level of nesting. But the choice is up to you!
