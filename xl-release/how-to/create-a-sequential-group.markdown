---
title: Create a sequential group
categories:
- xl-release
subject:
- Task types
tags:
- task
- sequential group
---

A sequential group is a container for tasks that should be executed in sequence. 

## Add a sequential group in the release editor

In the release editor, click **Add task** to add a sequential group. You can then drag existing tasks into the sequential group or click **Add task** in the group to create a new task.

![Sequential group](../images/sequential-group.png)

In this example, the "Prepare release notes" task has already started; when it finishes then the next task "QA prerequisites" will start. The sequential group "Manual checks" will finish together with its last sub-task: "Check JIRA version".

Sequential group works in the same way as if its sub-tasks would just appear on the phase level. But it is a good way to group related tasks together in a phase. For example, you could configure a precondition on the group so that all sub-tasks would be skipped in certain conditions. Or you could collapse a sequential group for better readability of the release.
