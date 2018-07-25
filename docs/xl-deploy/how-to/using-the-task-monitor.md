---
title: Using the Task Monitor
categories:
xl-deploy
subject:
Task Monitor
tags:
deployment
control
gui
task monitor
---

The XL Deploy user interface includes a task monitor that provides an overview of the tasks that are not archived. To access it using the default GUI, click **Task Monitor** in the left pane. The menu expands and you can select **Deployment tasks** or **Control tasks**.

By default, the **Task Monitor** only shows the tasks that are assigned to you. To see all tasks, click **All tasks** in the **Tasks** field of the filters section.

## Filter tasks

You can use different filters to view specific tasks.
* Search tasks by specifying the package, the environment, or the task ID
* Filter tasks by selecting the type or the state from a drop down list

To hide the filter section on the page, click **Hide filters**.

## Open a task

To open a task from the task monitor, double-click it. You can only open tasks that are assigned to you.

## Reassign a task

To assign a task to yourself, select it, click ![Edit task](/images/menu_three_dots.png), and select **Assign to me**. This requires the `task#takeover` [global permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#global-permissions).

To assign a task to another user, select it, click ![Edit task](/images/menu_three_dots.png), and select **Assign to user...**, and then select the user. This requires the `task#assign` global permission.

## Edit a task

Using the ![Edit task](/images/menu_three_dots.png) menu you can perform one these actions: Continue a paused task, Stop, Abort, Rollback, or Archive a task.
