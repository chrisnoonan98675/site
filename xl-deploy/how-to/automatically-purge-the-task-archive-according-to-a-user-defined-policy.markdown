---
title: Automatically purge the task archive according to a user-defined policy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- policy
- task
- purge
- schedule
- retention
since:
- XL Deploy 5.0.0
---

XL Deploy records and archives information about all tasks that it executes. This information is available through the statistics, graphs, and task archives on the **Reports** screen.

By default, all historical data is kept in the system indefinitely. As of XL Deploy 5.0.0, you can define a custom task retention policy if you do not want to retain an unlimited task history and/or if you want to reclaim the disk space it requires.

**Note:** The record of all tasks that started before the specified retention date will be removed from the archive and will no longer be visible in XL Deploy reports.

## Automatically purge the task archive

To automatically purge old deployment packages according to a policy:

1. Click **Repository** in XL Deploy.
2. Right-click **Configuration** and select `policy.TaskRetentionPolicy`.
3. Enter a unique name in the **Name** box.
4. In the **Days to retain tasks** box, enter the number of days that XL Deploy should retain tasks.
5. By default, automatic policy execution is enabled and will run according to the crontab scheduled defined on the **Schedule** tab. You can optionally change the crontab schedule or disable the policy execution.
6. By default, purged tasks are exported to a ZIP file in `<XLDEPLOY_HOME>/exports`. You can optionally specify a different directory in the **Archive path** property on the **Export** tab.

    The property accepts `${}` placeholders, where valid keys are CI properties with addition of `execDate` and `execTime`.

    ![Task retention policy](images/system-admin-task-retention-policy.png)

**Tip**: You can manually execute a task retention policy by right-clicking it and selecting **Execute job now**. To test the policy by running it without removing tasks, select **Dry run policy**.
