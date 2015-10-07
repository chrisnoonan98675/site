---
title: Using the XL Release planner
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- release summary
- planner
---

In the release details, select **Planner** from the **Show** menu to see an interactive Gantt chart. In the planner, you can view and edit the timing of phases and tasks on the level that you desire.

![Planner: phases overview](../images/planner-phases.png)

The Gantt chart is a combined timeline of the release, its phases, and the tasks within. You can optionally set a desired start time, due date, and duration on phases and tasks. If a task does not have due dates or a duration, it appears on that chart as having a default duration (one hour for manual tasks and one minute for automated tasks).

![Planner: default sequence](../images/planner-default-sequence.png)

In a phase, tasks are executed in sequential order. Therefore, in the example above, Task 2 will not start until Task 1 is finished. The grey line connecting the right end of Task 1 to the left side of Task 2 indicates this.

You can move and resize the tasks in the diagram for detailed time planning.

To set the duration of a task, drag its right edge. To set the scheduled start date of a task, drag its left edge.

**Note:** If you set a scheduled start date on a task, the task will not start before that date, even if the previous task is finished.

To move a task, drag it; this will update the scheduled start date and due date without changing the duration.

Alternatively, set the scheduled start date or due date by clicking the dates in the **Start date** and **End date** columns, or set the duration by clicking the date in the **Duration** column.

![Planner: sequence with start and dates](../images/planner-date-picker.png)

Moving a task and changing the due date will also affect the start dates of subsequent tasks in the same phase.

To remove a start or due date, click the cross next to the date.
