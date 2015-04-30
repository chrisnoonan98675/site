---
title: Automatically run a control task according to a schedule
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- schedule
- control task
---

XL Deploy uses a scheduling mechanism to run various system administration jobs on top of the repository (e.g. garbage collection, purge policies etc.).

This mechanism can also be used to run arbitrary control tasks on configuration items stored in the repository.

To automatically run a control task according to a schedule, you need to create a new `schedule.ControlTaskJob`:

1. Click **Repository** in XL Deploy
2. Right-click on the **Configuration** root and select `schedule.ControlTaskJob`
3. Populate the required unique identifer and define a crontab pattern when the control task should execute.
The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday. Month and weekday names can be given as the first three letters of the English names.

4. Enter the Id of the target configuration item and the name of the control task to invoke
5. If the control task expects paremeters in form of an `udm.Parameters` CI, populate the **Control task paramaters** property

![Control task job](images/system-admin-control-task-job.png)
