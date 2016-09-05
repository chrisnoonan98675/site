---
title: Using releases
---

## The release life cycle

A release can go through various stages. First a blueprint of a release is defined as a *template*. From the template, a *planned release* is created. The release is a copy of the template, but it has not started yet. When it is started, the release becomes *active* and the phases and the tasks are executed. When all is done, the release is *completed*.

This is a detailed breakdown of the states that a release can go through.

![Release life cycle](/xl-release/images/release_lifecycle.png)

All releases are derived from a template (it can be an empty template), so the first state of a release is *template*.

When a release is created from a template, a copy is made from the template and the release is *planned*.

When the release starts, it enters the *in progress* state. Phases and tasks are started and notifications are sent to task owners to pick up their tasks.

The state of an *active* release is reflected in the state of the current task. In the case of multiple tasks running in a parallel group, the state of the topmost parallel group is used.

* *In progress*: The current task is in progress.
* *Failed*: The current task has failed. The release is halted until the task is retried.
* *Failing*: Some tasks in a parallel group have failed, but other tasks are still in progress.

You can restart a phase when the release is any active state ('in progress', 'failed', or 'failing'). After a phase is restarted, the release is in 'paused' state. This is a state in which no tasks are active. It is similar to the 'planned' state, and allows the release owner to change due dates and other task variables before the release is resumed.

There are two end states: *completed* and *aborted*. When the last task in a release finishes, the release enters the 'completed' state. A release can be manually aborted at any point, at which point it will be in the 'aborted' state.

### Running release

In a running release, XL Release starts planned tasks in the correct order and executes them if they are automated, or sends a notification to the users who are responsible for their completion if they are not automated.

Generally, one or more tasks will be active at any given time. Users who have tasks assigned to them will find these tasks in their task overview. Users are expected to mark tasks as complete in XL Release when done.

If an active task is failed, the release is paused. The release owner must then decide whether to assign the task to another user or to skip it.

Also, the release may be stalled after a phase is restarted. In this situation, tasks are copied and may have erroneous start and due dates, or may be assigned to the wrong users. The release owner should configure the tasks correctly before carrying on.

## Using the planner

The XL Release planner allows you to use an interactive Gantt chart to view and edit the timing of the phases and tasks in a release or template. The Gantt chart is a combined timeline of the template or release, its phases, and the tasks within.

To access in the planner in XL Release 5.0.0 or later, click **Planner** at the top of the release flow page. In earlier versions of XL Release, select **Planner** from the **Show** menu.

![Planner: phases overview](/xl-release/images/planner-phases.png)

Tasks are executed in sequential order within a phase. Therefore, in the example below, Task 2 will not start until Task 1 is complete, as indicated by the line that connects them.

![Planner: default sequence](/xl-release/images/planner-default-sequence.png)

### Editing tasks and phases in the planner

When editing a task or a phase in the planner, you can:

* Move it by dragging it to a new position
* Set its duration by dragging its right edge
* Set its scheduled start date by dragging its left edge
* Alternatively, set the dates and duration by clicking **Show dates** and then setting them explicitly

![Planner: sequence with start and dates](/xl-release/images/planner-date-picker.png)

When you set the scheduled start date or duration of a task, the planner will automatically adjust subsequent tasks in the same phase. For more information about dates and durations, refer to [Scheduling releases](/xl-release/how-to/scheduling-releases.html)

**Tip:** Double-click a task in the planner to open its detail view.

## Release security

Release permissions determine who can do what in a release. These permissions are copied from the template when you create a release based on it. This allows you to define teams and permissions for the release before running it.

To set release permissions, open a template or release and select **Permissions** from the **Show** menu. The Permissions page is only available to users who have the *Edit Security* permission on the template or release or who have the *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html).

![Release Permissions](/xl-release/images/release-permissions.png)

The following permissions are available for releases:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Release | Users have view access to this release. It will appear in the Release Overview. In the release details, users have read-only access to the release flow, properties, and activity log. |
| Edit Release | Users can alter the structure of a release by adding and moving tasks and phases. Release properties and teams are editable. |
| Edit Security | Users can edit teams and permissions in a release |
| Start Release | Users can start a planned release. |
| Abort Release | Users can abort an active or planned release. |
| Edit Task | Users can edit individual tasks. |
| Reassign Task | Users can assign tasks to other people. Team assignment is also enabled. |

Click **Save** to save your changes to the release permissions. Changes are not saved automatically. To discard your changes without saving, click **Reset**.
