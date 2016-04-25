---
title: Add a task to a phase in a release or template
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- task
- phase
- release flow
---

In XL Release, the activities in a template or release are modeled as tasks, which are logically grouped in phases. To add a task to phase to a template or release, use the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html); it shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

To add a task to a phase:

1. In the template or release, select **Release Flow** from the **Show** menu.
1. Click **Add task** at the bottom of the phase.

    ![New Phase](../images/add-task.png)

1. Click the task title to change it.
1. Select the task type.
1. Click **Add** to create the task and add it to the phase.
1. Optionally move the task by dragging it to the desired position. You can also drag the task to another phase.

    ![Task in release flow editor](../images/task-in-release-flow-editor.png)

1. Click the task to edit its details.

In a task:

* An orange triangle indicates that the task is currently active in the release (if the release is active).
* If the task is manual, the user or group to whom the task is assigned appears at the lower left. If the task is automated, the type of script appears at the lower left (for example, *Webhook: JSON webhook*).
* At the upper right, you can see:
    * An icon that indicates the task type
    * The current state of the task (if the release is active)
    * An alert icon, if action is needed
* The task due date appears at the lower right (if a date is set)

## Add a task to a parallel or sequential group

[Parallel Groups](/xl-release/how-to/create-a-parallel-group.html) and [Sequential Groups](/xl-release/how-to/create-a-sequential-group.html) are containers for other tasks.

Within a Parallel Group, all tasks are started simultaneously. The Parallel Group task finishes when all of its children are complete.

Within a Sequential Group, tasks are executed in order. The Sequential Group task finishes when its last child is complete. The Sequential Group task is available in XL Release 5.0.0 and later.

To add a task to a task group, click **Add task** at the bottom of the group. To move a task into a task group, drag and drop it. To collapse or expand a task group, click its arrow.

![Add Parallel Group task](../images/add-parallel-task.png)

## Copy a task

To copy a task, click the button in the upper right corner of the task. You cannot copy tasks that are completed.

## Delete a task

To delete a task, click the button in the upper right corner of the task. In an active release, you can only remove tasks that are planned (that is, tasks that are not in progress).
