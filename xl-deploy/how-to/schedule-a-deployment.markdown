---
title: Schedule a deployment
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- gui
- task
- scheduling
---

XL Deploy allows you to schedule deployment tasks for execution at a specified later moment in time. Scheduled deployment tasks work just like other scheduled tasks; refer to [Understanding tasks in XL Deploy](https://docs.xebialabs.com/xl-deploy/concept/understanding-tasks-in-xl-deploy.html#scheduling) for important information about the way XL Deploy handles scheduled tasks.

To schedule a deployment using the XL Deploy GUI:

1. Go to the Deployment Workspace.
1. Select a deployment package and environment.
1. Click **Advanced**.
1. Click **Schedule**.

    In the Schedule window, select the date and time that you want to execute the deployment task. Specify the time using your local timezone.

    ![Schedule Window](images/schedule-window.png)

1. Click **OK** to schedule the task.

## View scheduled tasks

To view scheduled tasks, click the gear icon and select **Task Monitor**. Note that you can only see tasks that you have [permission](/xl-deploy/concept/overview-of-security-in-xl-deploy.html#permissions) to view.

![Schedule Task Monitor](images/schedule-task-monitor.png)

**Note:** Although control tasks and discover tasks can also be scheduled, you will not see them in the Task Monitor. They will appear in the Repository.

## Reschedule a task

To reschedule a scheduled task:

1. Double the task in the Task Monitor to open its execution plan.
1. Click **Schedule**.
1. In the Schedule window, select a new date and time. 
