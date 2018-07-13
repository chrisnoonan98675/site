---
title: Task life cycle (XL Release 4.7.x and earlier)
categories:
- xl-release
subject:
- Tasks
tags:
- task
deprecated:
- XL Release 4.7.x
weight: 460
---

In an active release, tasks transition through different states. The following diagram shows the lifecycle of a task.

![Task life cycle](../images/task-lifecycle-4.7.png)

Tasks start in **planned** state. This means that they are not active yet. All properties can still be edited.

When the release flow reaches a task and it becomes active:

* If a scheduled start date is set and this date has not passed yet, the task is **pending**
* Otherwise, the task [precondition](/xl-release/how-to/set-a-precondition-on-a-task.html) is evaluated (if a precondition has been set on the task)

The next state depends on the result of the precondition script. If it evaluates to:

* **True**: The next state is **in progress**. At this point, notifications are sent to the task owner, and you can trigger the next transition by clicking **Complete**, **Skip**, or **Fail**
* **False**: The task is **skipped**.

Normally, when you complete a task or when an automated task is performed without errors, the task enters the **completed** state. This is an end state that means that the task has completed successfully. The release flow continues with the next task in the flow.

You can also skip a task that was **in progress** or **failed**. In that case, the task goes into the **skipped** state. The 'skipped' state is functionally equivalent to **completed**. The only difference is that it implies that no work was done on the task.

Tasks may also be completed or skipped in advance; that is, before the execution flow has reached the task. In that case, the task appears as completed (or skipped) in the release flow editor. Before the release flow has reached this task, it is still possible to **reopen** the task, moving it back in the 'planned' state. When the release flow reaches a task that was completed or skipped in advance, the state is made definite and the task cannot be reopened.

## Fail and abort

If a user fails a task or an automated task encounters an error, the task goes into the **failed** state. This triggers a notification to the release owner. The task can then be either restarted ('in progress' state) or skipped if no more work will be done ('skipped' state).

## Failing state

There is also a **failing** state. This state only applies to a parallel group task that contains subtasks. It indicates that one of the subtasks is in a **failed** state, but that other tasks are still running.

Transitions to and from the 'failing' state are:

1. In progress &#8594; failing: A subtask fails and other subtasks are still in progress.
2. Failing &#8594; in progress: A failed subtask restarts (and was the only failed) or is skipped, and there are other subtasks still pending or in progress.
3. Failing &#8594; failing: A failed subtask is skipped, but of the remaining subtasks, some are failed and some are pending or in progress.
4. Failing &#8594; failed: There are failed subtasks, and the last subtask that was still pending or in progress completes or fails.

## Completion of gate tasks

Gate tasks with no conditions and only dependencies will complete immediately when their dependencies are complete. If all dependencies are complete but at least one of the dependencies is an aborted release, the gate goes into a 'failed' state. If any conditions are set, the owner of the task must complete the gate task manually.
