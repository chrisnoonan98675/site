---
title: Using the release flow editor
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- release summary
---

The **Release Flow** editor in the release summary shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

![Release Flow Editor](../images/release-flow-editor.png)

You use the editor for running releases and templates, though there are some minor differences between the two. In a running release, an orange arrow indicates the current task. Completed tasks appear in grey and you cannot move or edit them.

## Top bar

Next to the navigation button, there are action buttons that depend on the release type.

**Template**

![Add Phase - New release](../images/release-flow-header-template.png)

**Planned release**

![Add Phase - Start - Abort](../images/release-flow-header-planned.png)

**Active release**

![Add Phase - Abort - Restart Phase...](../images/release-flow-header-active.png)

The actions that are available are:
 
* **Add Phase**: Add a new phase to the release editor, after the last phase. You can drag the phase and drop it in the correct position.
* **New Release**: Create a new release from the template. This is a copy from the template. See [Create a release template](/xl-release/how-to/create-a-release-template.html) for more information.
* **Start**: Start a release that is in the 'planned' state.
* **Abort**: Stop a currently active release and discard it. 
* **Restart Phase...**: Abort the current phase (in an active release) and restarts execution from any phase in the past. See [Restart a phase in an active release](/xl-release/how-to/restart-a-phase-in-an-active-release.html) for more information.
* **Export to Excel**: Download the current release in Microsoft Excel format (.xlsx).
* **Export**: Download the current template in ZIP format. You can import a template in the Template Overview, so you can share templates across XL Release servers. See [Import a release template](/xl-release/how-to/import-a-release-template.html) for more information.

## Editor

The release flow editor shows the workflow of your release. This section describes how you use the editor to design a workflow by adding, moving, and deleting phases and tasks.

## Managing phases

Phases represent blocks of work that happen in succession. To add a phase, click **Add Phase** in the top bar. A new phase with title 'New Phase' appears.

![New Phase](../images/new-phase.png)

Click the title to change it. You can drag the new phase to the desired position and drop it. To collapse or expand a planned phase, click the arrow.

Hover over the phase header to:

* ![Phase color picker icon](../images/phase-color-picker-icon.png) Select the color of the phase's top bar.
* ![Duplicate icon](../images/duplicate-icon.png) Creates a copy of the current phase. This is useful during modeling when you have similar or identical phases. When you duplicate a phase that is active and has running and completed tasks, the tasks in the new phase are all in the 'planned' state.
* ![Phase details icon](../images/phase-details-icon.png) View a description of the phase and the start date, due dates, and duration. XL Release uses the dates and duration when displaying the phase in the planner and calendar; if they are not consistent with the release dates (as defined in the [Release properties](/xl-release/how-to/configure-release-properties.html) page), a warning appears.

    ![Phase details popup](../images/phase-details-popup.png)

* ![Delete icon](../images/delete-icon.png) Delete the phase from the release flow. Note that you cannot remove completed or active phases of a running release because this would corrupt the history reports and audit log of your release.

## Managing tasks

Phases by themselves do not do very much; they are logical groupings of all activities in a release. The activities are modeled as _tasks_. To add a task to a phase, click **Add task** at the bottom of the phase.

![New Phase](../images/add-task.png)

You can then specify the new task's title and type. Click **Add** to create the task and add it to the phase. The new task input field remains open so you can quickly add another task.

The task is added at the end of the release. Drag it to the desired position in the phase and drop it. You can also drop the task into another phase.

A [Parallel group](/xl-release/how-to/create-a-parallel-group.html) is a container for other tasks. All tasks inside the parallel group are started simultaneously and the parallel group task will finish when all of its children are complete. To add tasks to a parallel group, click **Add task** at the bottom of the group. To move a task into a parallel group, drag and drop it. To collapse or expand a parallel group, click its arrow.

![Add parallel task](../images/add-parallel-task.png)

A task appears as a box in the phase.

![Task in release flow editor](../images/task-in-release-flow-editor.png)

The elements of a task are:

* Orange triangle: Indicates that the task is currently active in the release. This only appears in an active release (not in templates or planned releases). In a parallel group, there can be more than one active task.
* The title appears in the upper left corner.
* The task assignee appears in the lower left corner. If the task is not assigned to a user, the team appears here. If the task is not assigned to a team, this area is blank. If the task is automated, the type of script appears here; for example, *Webhook: JSON webhook*.
* Status and action icons:
	* Icon indicating the type of task
	* The current state of the task (if the release is active)
	* Alert icon (if action is needed)
	* Buttons for duplicating and removing a task
		* In an active release, you can only remove planned tasks (tasks that are not in progress)
		* You can only duplicate tasks that are active or planned
* The due date appears in the lower right corner (if it is set).

Click anywhere on the task (besides the duplicate and remove buttons) to open the task details.
