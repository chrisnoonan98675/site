---
title: Create a sequential group
categories:
- xl-release
subject:
- Task types
tags:
- task
- sequential group
since:
- XL Release 5.0.0
---

A sequential group is a container for tasks that should be executed in sequence. A sequential group works in the same way as a series of tasks in a phase; however, sequential groups are a useful way to group related tasks within a phase. For example, you could configure a precondition on the group so that all of its sub-tasks will be skipped under certain conditions. You can also collapse sequential groups for better readability of the release.

## Add a sequential group in the release editor

In the release editor, click **Add task** to add a sequential group. You can then drag existing tasks into the sequential group or click **Add task** in the group to create a new task.

![Sequential group](../images/sequential-group.png)

In this example, the "Prepare release notes" task has already started; when it finishes, then the "QA prerequisites" task will start. The "Manual checks" sequential group will finish with its last sub-task, "Check JIRA version".
