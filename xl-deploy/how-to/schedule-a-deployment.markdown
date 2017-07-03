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
- schedule
weight: 189
---

XL Deploy allows you to schedule deployment tasks for execution at a specified later moment in time. Scheduled deployment tasks work just like other scheduled tasks; refer to [Understanding tasks in XL Deploy](https://docs.xebialabs.com/xl-deploy/concept/understanding-tasks-in-xl-deploy.html#scheduling-tasks) for information about the way XL Deploy handles scheduled tasks, including important information about archiving and failures.

To schedule a deployment using the default GUI:

1. Expand **Applications**, and then expand the application that for which you want to schedule a deployment.
1. Hover over the desired deployment package or provisioning package, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deploy**. A new tab appears in the right pane.
1. Select the target environment and click **Continue**.
1. Click the arrow icon on the **Deploy** button and select **Schedule**.
1. In the Schedule window, select the date and time that you want to execute the deployment task. Specify the time using your local timezone.
1. Click **Schedule**.

To schedule a deployment using the legacy GUI:

1. Go to the Deployment Workspace.
1. Select a deployment package and environment.
1. Click **Advanced**.
1. Click **Schedule**.

    In the Schedule window, select the date and time that you want to execute the deployment task. Specify the time using your local timezone.

    ![Schedule Window](images/schedule-window.png)

1. Click **OK** to schedule the task.

## View scheduled deployments

To view scheduled deployment tasks using the default GUI, click the **Task Monitor** in the left pane. Note that you can only see deployment tasks that you have [permission](/xl-deploy/concept/overview-of-security-in-xl-deploy.html#permissions) to view.

To view scheduled deployment tasks using the legacy GUI, click the gear icon and select **Task Monitor**.

![Schedule Task Monitor](images/schedule-task-monitor.png)

**Note:** Although control tasks and discover tasks can also be scheduled, you will not see them in the Task Monitor when using the legacy GUI. They appear in the Repository.

## Reschedule a deployment

{% comment %}
To reschedule a scheduled deployment task using the default GUI:

1. Expand **Applications**, and then expand the application that for which you want to reschedule a deployment.
1. Hover over the desired deployment package or provisioning package, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deploy**. A new tab appears in the right pane.
1. Select the target environment and click **Continue**.
1. Click the arrow icon on the **Deploy** button and select **Schedule**.
1. In the Schedule window, select a new date and time.
1. Click **Schedule**.
{% endcomment %}

To reschedule a scheduled deployment task using the legacy GUI:

1. Double-click the deployment task in the Task Monitor to open its deployment plan.
1. Click **Schedule**.
1. In the Schedule window, select a new date and time.
1. Click **OK**.
