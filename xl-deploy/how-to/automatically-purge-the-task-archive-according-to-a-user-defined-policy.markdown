---
title: Automatically purge the task archive according to a user defined policy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- policy
- task archive
- purge
---

XL Deploy records and archives information of all task execution in the system. This information is made available through the reporting sub-system in form of statistics, graphs and a searchable task archive.

By default all historical data is kept in the system indefinitely. Of course, this consumes disk space, so users who do not have a requirement for unlimited task history can specify a custom task retention policy.
This way we can specify the maximum number of days to keep the historical task data.

Please note that the records of all tasks started before the specified retention date will be removed from the archive and won't be available to the reporting sub-system.

To define a custom task retention policy, you need to create a new `policy.TaskRetentionPolicy`:

1. Click **Repository** in XL Deploy
2. Right-click on the **Configuration** root and select `policy.TaskRetentionPolicy`
3. Populate the required unique identifier and define the number of days to retain the tasks.
4. (Optional) By default automatic policy execution is enabled and will run according to the Crontab schedule defined under the Schedule tab.
5. (Optional) By default purged tasks are exported and stored inside a ZIP archive under the `<xld_root>/exports` directory. To specify a different directory, update the `archivePath` property.
The property accepts **${}** placeholders where valid keys are CI properties with addition of `execDate` and `execTime`.


![Task retention policy](images/system-admin-task-retention-policy.png)


**Note**: The policy can also be executed manually by right-clicking the policy CI and selecting **Execute job now**.

**Tip**: To run the policy without removing the tasks, enable the `dryRun` property.
