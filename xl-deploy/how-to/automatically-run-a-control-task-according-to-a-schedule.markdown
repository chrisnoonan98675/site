---
title: Automatically run a control task according to a schedule
categories:
- xl-deploy
subject:
- Control task
tags:
- system administration
- schedule
- control task
weight: 141
---

XL Deploy uses a scheduling mechanism to run various system administration jobs on top of the repository, such as garbage collection, purge policies, and so on. You can also use this mechanism to run specific control tasks on configuration items (CIs) stored in the repository.

To automatically run a control task according to a schedule, create a new `schedule.ControlTaskJob` CI:

1. Click **Explorer** in the top menu.
2. Hover over **Configuration** in the left sidebar, click ![Menu button](/images/menu_three_dots.png), and select **New** > **schedule** > **ControlTaskJob**.
3. Enter a unique name in the **Name** box.
4. In the **Crontab schedule** field, define a crontab pattern for executing the control task.

    The pattern is a list of six single space-separated fields representing second, minute, hour, day, month, and weekday. Month and weekday names can be entered as the first three letters of their English names.

5. In the **Configuration item Id** field, enter the ID of the target CI.
6. In the **Control task name** field, enter the name of the control task to invoke.
7. Under **Control task parameters**, provide any parameters that the control task requires, in the form of a `udm.Parameters` CI.
8. Click **Save**.

![Control task job](images/system-admin-control-task-job.png)
