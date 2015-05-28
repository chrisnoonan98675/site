---
title: Reclaim disk space on an XL Deploy server
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- cli
- schedule
- repository
---

Garbage collection can be used to optimize the disk space that is used for the XL Deploy repository. Running garbage collection is only relevant if you deleted a package. When deleting a package, XL Deploy at first only deletes the reference (for performance reasons) and later on you can delete the actual files by running a garbage collect. For imports, garbage collection is not needed.

It is not required to run a garbage collection every delete of a package. The system will not break, it will only occupy a small amount of disk space that you can win back by running garbage collection at a later moment in time.

How often you want to run the garbage collector depends on how many package you delete, and the buffer of free space on your disk, but in general it is not needed to run it more than once per day.

## Run garbage collection from the CLI

To start garbage collection from the command-line interface (CLI), run the following command:

    deployit> deployit.runGarbageCollector()

This requires requires admin permissions.

The garbage collector stops automatically when the task is finished. The garbage collector is not accessible from the GUI, but the status of the task can be seen when running `listUnfinishedTasks`.

## Automatically run garbage collection according to a schedule

The easiest way to schedule a garbage collection is to create a `schedule.GarbageCollectionJob` configuration item (CI) that triggers the garbage collection based on a crontab expression.

To create a `schedule.GarbageCollectionJob` CI:

1. Click **Repository** in the top menu.
2. Right-click **Configuration** and select **schedule** > **GarbageCollectionJob**.
3. Enter a unique name in the **Name** box.
4. In the **Crontab schedule** box, define a crontab pattern for executing the garbage collection job.

    The pattern is a list of six single space-separated fields representing second, minute, hour, day, month, and weekday. Month and weekday names can be entered as the first three letters of their English names. For example, to run the job every day at 20:00, enter `0 0 20 * * *`.

5. Click **Save**.

![Garbage collection job](images/system-admin-gc-job.png)

**Tip:** To execute a garbage collection job manually, right-click it and select **Execute job now**.

### Troubleshooting: `deployit.runGarbageCollector` doesn't free disk space

When you delete packages from XLD repository you expect that running `deployit.runGarbageCollector` will free space on the repository but you don't see any difference and it seems like the only way to free the space after deleting the package is to restart XL Deploy.

Explanation: the algorithm in the internal implementation of the Jackrabbit datastore will not remove files that were recently used from the datastore.
In order to force Jackrabbit algorithm to remove entries from datastore prior to executing `deployit.runGarbageCollector` JVM garbage collection has to be triggered.
If you don't want to restart XL Deploy, and you can't wait for JVM garbage collection to be automatically triggered you can manually trigger JVM garbage collection from a `jvisualvm` tool.


