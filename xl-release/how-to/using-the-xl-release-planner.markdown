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

The XL Release planner allows you to use an interactive Gantt chart to view and edit the timing of the phases and tasks in a release or template. The Gantt chart is a combined timeline of the template or release, its phases, and the tasks within.

To access in the planner in XL Release 5.0.0 or later, click **Planner** at the top of the release flow page. In earlier versions of XL Release, select **Planner** from the **Show** menu.

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
