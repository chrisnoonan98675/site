---
title: Using the Task Monitor
categories:
- xl-deploy
subject:
- Task Monitor
tags:
- deployment
- control
- gui
- task monitor
---

The XL Deploy user interface includes a Task Monitor that provides an overview of the tasks that are not archived. To access it using the default GUI, click **Task Monitor** in the left pane. The menu expands and you can select Deployment tasks or Control tasks.

By default, the Task Monitor only shows the tasks that are assigned to you. To see all tasks, click **All** in the Tasks field of the filters section.

## Filter tasks

You can use different filters to view specific tasks.
* Search tasks by package or environment
* Filter tasks by selecting the type or the user from a drop down list
* Filter tasks by specifying the start date and end date or by the task ID.

Click **Filter** to apply multiple filters on the list of tasks. To hide the filter section on the page, click **Hide filters**.

## Open a task

To open a task from the Task Monitor, double-click it. You can only open tasks that are assigned to you.

## Reassign a task

To assign a task to yourself, select it, click ![Edit task](/images/menu_three_dots.png), and select **Assign to me**. This requires the `task#takeover` [global permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#global-permissions).

To assign a task to another user, select it, click ![Edit task](/images/menu_three_dots.png), and select **Assign to user...**, and then select the user. This requires the `task#assign` global permission.

## Edit a task

Using the ![Edit task](/images/menu_three_dots.png) menu you can perform one these actions: Continue a paused task, Stop, Abort, Rollback, or Archive a task.
