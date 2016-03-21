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
- release summary
- release flow
---

In a release or template, select **Release Flow** from the **Show** menu to go to the release flow editor. The release flow editor shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

Phases by themselves do not do very much; they are simply logical groupings of all activities in a release. The activities are modeled as _tasks_. To add a task to a phase:

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

[Parallel group](/xl-release/how-to/create-a-parallel-group.html) and [sequential group](/xl-release/how-to/create-a-sequential-group.html) are containers for other tasks. All tasks inside the parallel group are started simultaneously and the parallel group task will finish when all of its children are complete. In a sequential group the tasks would be executed in order, and sequential group will finish when its last child task is complete. To add a task to a task group, click **Add task** at the bottom of the group. To move a task into a task group, drag and drop it. To collapse or expand a task group, click its arrow.

![Add parallel task](../images/add-parallel-task.png)

## Copy a task

To copy a task, click the button in the upper right corner of the task. You cannot copy tasks that are completed.

## Delete a task

To delete a task, click the button in the upper right corner of the task. In an active release, you can only remove tasks that are planned (that is, tasks that are not in progress).
