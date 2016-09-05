---
title: Working with running releases
---

## Release views

XL Release includes several release planning and management views to meet the needs of everyone who participates in the release process:

* The [pipeline view](/xl-release/how-to/using-the-pipeline-view.html) provides an overview of all active releases. It indicates the phase that each release is in and the tasks that are currently active.
* The [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) shows the phases and tasks in a template or release. This is the primary interface for adding, editing, moving, and grouping tasks.
* [Table view](/xl-release/how-to/using-the-table-view.html) shows the phases and tasks in a template or release in a tabular format that you can sort by properties such as task type, due date, and assignee (available in XL Release 5.0.0 and later).
* The [release planner](/xl-release/how-to/using-the-xl-release-planner.html) is an interactive Gantt chart that shows the timing of phases and tasks in a release.

## Using the release overview

To see the list of releases that you have permission to view and that are currently active (including releases that are planned, in progress, or failed), select **Releases** > **Overview** from the top bar. The release overview shows the [status](/xl-release/concept/release-life-cycle.html) of each release, as well as any status flags that have been set.

![Release Overview](/xl-release/images/release-overview.png)

### Release overview actions

Next to each release, click:

* **View** to open the release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html)
* **Start** to start a release that is in the *planned* state
* **Abort** to abort the release

### Dates in the release overview

The **Start date** column shows:

* The planned start date if the release is scheduled in the future
* The actual start date if the release has already started

The **End date** column shows:

* The planned end date if the release is not complete
* The actual end date otherwise

Overdue dates appear in red.

The **Duration** column shows the duration of the release.

### Filtering the release overview

To filter the release overview, click **Filter options** and select:

* **All active releases** to show releases that are busy (in the *in progress*, *failing*, or *failed* state)
* **All planned releases** to show releases that have been created but that have not started yet (in the *planned* state)
* **All completed and aborted releases** to show releases that are completed or aborted (in the *completed* or *aborted* state)
* **Only my releases** to show releases for which you are the release owner
* **Only flagged releases** to show releases that are flagged with a warning message; use this option to see releases that are currently at risk

To filter on the release title, use the **Filter by title or tag** box.

To filter on dates, use the **From** and **To** options.

## Using the pipeline view

To see an overview of all [active](/xl-release/concept/release-life-cycle.html) releases in XL Release, select **Releases** > **Pipeline** from the top bar. This page provides a pipeline view of each active release; it shows how much of each release has been completed and any flags that have been set on the release or tasks within it.

![Pipeline](/xl-release/images/pipeline.png)

### Filter releases

To filter the releases, click **Filter options** and select **Only my releases** and/or **Only flagged releases**. You can also filter by typing part of a release title or release tag in the box at the top of the page.

![Filtered pipeleine view](/xl-release/images/pipeline-filtered.png)

### Drill down

To drill down into a specific release, click its pipeline.

![Pipeline drill down](/xl-release/images/pipeline-drill-down.png)

## Restart a phase in an active release

In an active release, you can abort the current phase and restart the execution from any phase in the past. This can be required if some parts of the release procedure must be repeated. For example, QA rejects a version of the application for release and the test phase must be repeated with an updated version.

When you restart the release from a previous phase:

1. The current phase is interrupted
1. All remaining tasks are skipped
3. The release is paused
4. XL Release makes a copy of all previous phases that need to be repeated

The release owner can change variable values and task details before reinitiating the release flow.

**Note:** If you want to specify the *task* from which to restart the phase, you must have permission to skip all tasks before the current one; that is, you either have the `edit#task` permission on the release, or you or your team own the tasks before the current one. If you do not have permission, you can only restart the phase from the first task.

### Example

This is an example of how restarting a phase works in practice. Suppose you have a release with three phases: QA, UAT, and Production.

![Restart: first phase failed](/xl-release/images/restart-first-phase-failed.png)

The QA phase was started with version 1.0 of the product, but bugs were found and QA could not sign off. So the **Sign off by QA** task failed. The Dev team is notified and produces a fix: version 1.0.1. You can now start the QA phase again for version 1.0.1.

Do so by clicking **Restart Phase...** in the top bar and selecting the phase and task from which the release should be restarted.

![Restart confirmation dialog](/xl-release/images/restart-dialog-1.png)

Click **Continue** to confirm the restart. Alternatively, click **Cancel** to discard the restart.

After you continue, the release is paused and the new phases are created. Before the release flow is resumed, you can make some changes. First, you confirm whether to resume now or later.

![Restart confirmation dialog](/xl-release/images/restart-dialog-2.png)

You can change the package version variable from 1.0 to 1.0.1 and click **Resume now** to proceed right away.

If you click **Resume later**, the release remains in the 'paused' state. You can change task assignees, due dates, and so on. You can even decide to delete tasks that are no longer relevant.

XL Release creates a phase called **QA (2)**. You can still modify its content. For example, suppose that the task **Update test scenarios** is no longer relevant; you can remove it.

![Restart confirmation dialog](/xl-release/images/phase-restarted.png)

To resume a paused release, click **Resume Release** in the top bar.

## Notifications

XL Release sends emails when certain events happen in a release. The emails are:

* **Task assignment**: An active task has been assigned to somebody else. The new assignee receives a message telling them they are responsible for completion of this task.

* **Task failed**: When a task fails, the release owner is notified so they can take action. A manual task fails when its owner indicates that he or she cannot proceed and clicks **Fail**. Automated tasks may fail when they cannot be executed correctly. The release owner must then resolve the issue.

* **Task started**: XL Release started a task which is now in progress. XL Release sends a notification to the task owner.
	* If the task does not have an assignee but it has been assigned to a team, all team members receive a message.
	* If there is no owner or team assigned, the release owner receives a warning message that a task is in progress but no one is responsible for it. In the case of automated tasks, messages are sent to individual owners or team owners so they can track automated procedures they are responsible for. However, a warning message for unassigned tasks is not sent to release owners.

* **Comment added**: When a user adds a comment to a task, a message is sent to the task owner and all team members of the team assigned to the task.

* **Release flagged**: The release owner is notified when a user adds a flag status message to a task or the release to indicate that attention is needed or that the release is at risk.

See [Configure SMTP servers in XL Release](/xl-release/how-to/configure-smtp-server.html) for information about configuring the email server and sender for these messages.

## The audit log

The activity log shows everything that happens in a release. It provides an audit trail of who did what, and when. To open the activity log, select **Activity logs** from the **Show** menu.

This is an example of an activity log:

![Activity Log](/xl-release/images/activity-logs.png)

## Filtering the activity logs

To filter the activity logs, click **Filter categories** and select:

* **Important** to show the most important events of all other categories (such as *release started* and *task failed*); by default, only this category is selected
* **Release life cycle** to show events for the start and end of a release, phases, and tasks
* **Release edits** to show changes that were made to a release or a template
* **Task edits** to show changes that were made to a single task
* **Task assignment** to show events where a task was assigned to a user
* **Comments** to show events where a comment was added to a task
* **Security** to show changes that were made to the release security settings

To filter on a user or action, use the **Filter by user or action** box.

To filter on dates, use the **From** and **To** options.

**Tip:** You can sort the activity logs by clicking the **Date** column header.
