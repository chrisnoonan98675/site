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

By default, the Task Monitor only shows the tasks that are assigned to you. To see all tasks, click **All** in the Tasks field of the filters section.

To access the Task Monitor using the legacy GUI, select **Task Monitor** from the gear icon menu.

![Task Monitor](images/task-monitor-new-ui.png)

## Open a task

To open a task from the Task Monitor, double-click it. You can only open tasks that are assigned to you.

## Reassign a task

To assign a task to yourself: On the right of the task, click ![Menu button](../../images/menu_three_dots.png), and click **Assign to me**. This requires the `task#takeover` [global permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#global-permissions).

To assign a task to another user: On the right of the task, click ![Menu button](../../images/menu_three_dots.png), and click **Assign to user**. This requires the `task#assign` global permission.
