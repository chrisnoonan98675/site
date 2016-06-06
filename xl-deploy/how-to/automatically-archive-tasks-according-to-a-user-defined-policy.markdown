---
title: Automatically archive tasks according to a user-defined policy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- policy
- task
- archive
- purge
- cancel
- schedule
- retention
since:
- XL Deploy 6.0.0
---

XL Deploy keeps all active tasks in the Task Monitor, which is available under the gear button.

Executed tasks are archived when you manually click **Close** or **Cancel** on the task. As of XL Deploy 6.0.0, you can define a custom task archive policy that will automatically archive tasks that are visible in the **Task Monitor**.

## Automatically archive active tasks

To automatically archive active tasks according to a policy:

1. Click **Repository** in XL Deploy.
2. Right-click **Configuration** and select `policy.TaskArchivePolicy`.
3. Enter a unique name in the **Name** box.
4. In the **Days to retain tasks** box, enter the number of days that XL Deploy should retain tasks. If 0 days is specified, all active tasks are subject to archiving.
5. By default, automatic policy execution is enabled and will run according to the crontab schedule defined on the **Schedule** tab. You can optionally change the crontab schedule or disable policy execution.
6. By default, successfully executed active tasks and failed tasks are archived. This can be changed in the **includeExecutedTasks** and **includeFailedTasks** properties.

**Tip**: You can manually execute a task archive policy by right-clicking it and selecting **Execute job now**. To test the policy by running it without removing tasks, select **Dry run policy** on the **Schedule** tab.
