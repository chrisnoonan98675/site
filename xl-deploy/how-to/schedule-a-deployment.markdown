---
title: Schedule a deployment
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- gui
---

XL Deploy allows you to schedule a task for execution at a specified later moment in time. You should prepare your deployment task like you would normally do. See [Understanding tasks in XL Deploy](https://docs.xebialabs.com/xl-deploy/concept/understanding-tasks-in-xl-deploy.html#scheduling) for all of the possibilities and details of scheduling.

In the XL Deploy GUI, you would perform the following actions to schedule a deployment:

1. Select a deployment package and environment.
2. Create the desired mappings.
3. Click Next.
4. Now instead of clicking Execute, click Schedule.

![Schedule Button](images/schedule-button.png)

The XL Deploy GUI will now show the scheduling window. Here you can configure the date and time you want this task to be scheduled. Notice that you need to specify the time using your local timezone.

![Schedule Window](images/schedule-window.png)

After pressing OK, the task is scheduled and will close. If you want to see all scheduled tasks, you need to open the task monitor. The task monitor will show which tasks are scheduled and when they are scheduled. To reschedule a task, double-click on the task to open it. Now you can change the time and date by using the Schedule button.

![Schedule Task Monitor](images/schedule-task-monitor.png)

Control tasks and discovery tasks can also be scheduled in the same way as deployment tasks. The task monitor will not list control tasks and discovery tasks. These tasks will show up in the repository workspace.
