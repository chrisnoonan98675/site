---
title: Using the release planner
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- planner
- release summary
---

In a release or template, select **Planner** from the **Show** menu to go to the planner page, where you can use an interactive Gantt chart to view and edit the timing of phases and tasks on the level that you desire. The Gantt chart is a combined timeline of the release, its phases, and the tasks within.

![Planner: phases overview](../images/planner-phases.png)

Tasks are executed in sequential order within a phase. Therefore, in the example below, Task 2 will not start until Task 1 is complete, as indicated by the line that connects them.

![Planner: default sequence](../images/planner-default-sequence.png)

You can optionally set a desired start time, due date, and duration on phases and tasks. If a task does not have a due date or a duration, it appears here with a default duration: one hour for manual tasks and one minute for automated tasks.

To adjust a task:

* Move it by dragging it to a new position
* Set its duration by dragging its right edge
* Set its scheduled start date by dragging its left edge
* Alternatively, set the dates and duration by clicking **Show dates**, then setting dates and durations explicitly

![Planner: sequence with start and dates](../images/planner-date-picker.png)

**Note:** Moving tasks and changing their due dates will affect the start dates of subsequent tasks in the same phase. Also, if you set a scheduled start date on a task, it will not start before that date, even if the task that precedes it is completed.
