---
title: Monitor and reassign deployment tasks
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- gui
- task monitor
weight: 190
---

The XL Deploy user interface includes a Task Monitor that provides an overview of deployment tasks that are not archived. To access it using the default GUI, click **Task Monitor** in the left pane.

To access the Task Monitor using the legacy GUI, select **Task Monitor** from the gear icon menu.

By default, the Task Monitor only shows the tasks that are assigned to you. To see all tasks, select **All tasks** at the top of the Task Monitor.

![Task Monitor](images/task-monitor.png)

## Open a task

To open a task from the Task Monitor, double-click it. You can only open tasks that are assigned to you.

## Reassign a task

To assign a task to yourself, select it and click **Assign to me**. This requires the `task#takeover` [global permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#global-permissions).

To assign a task to another user, select it and click **Assign to...**, and then select the user. This requires the `task#assign` global permission.
