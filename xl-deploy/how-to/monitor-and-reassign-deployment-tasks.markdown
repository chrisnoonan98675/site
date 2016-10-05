---
title: Monitor and reassign deployment tasks
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- gui
- security
weight: 190
---

The XL Deploy GUI incorporates a task monitor from which you can get an overview of the deployment tasks in the system. The task monitor is opened from the help menu in the far top right. By default it shows only tasks belonging to the current user, but by selecting 'All tasks' from the dropdown at the top, a full overview can be obtained.

![Task Monitor](images/task-monitor.png)

From the task monitor it is possible to reassign tasks. Security-wise there are two different permissions, `task#assign` and `task#takeover`, governing the rules who can do what: if a user has task#takeover, he can reassign tasks to himself. This is done by selecting a task and pressing the 'Assign to me' button at the bottom of the task monitor.

The `task#assign` permission is a stronger permission, allowing the user to reassign any task from anyone to anyone. By pressing the 'Assign to...' button, a popup will appear in which the user name can be filled in to whom the task should be reassigned.
