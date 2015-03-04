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

To start garbage collection, you require admin permissions.

Start the garbage collector using the command-line interface (CLI) by running this command:

    deployit> deployit.runGarbageCollector()

The garbage collector stops automatically when the task is finished. The garbage collector is not accessible from the GUI, but the status of the task can be seen when running `listUnfinishedTasks`.


## Automatically run garbage collection according to a schedule

The easiest way to schedule a garbage collection is to create a `schedule.GarbageCollectionJob` that triggers the garbage collection based on a crontab expression.

To create the job CI:

1. Click **Repository** in XL Deploy
2. Right-click on the **Configuration** root and select `schedule.GarbageCollectionJob`
3. Populate the required fields and define a crontab pattern for when the collection job should run

The pattern is a list of six single space-separated fields: representing second, minute, hour, day, month, weekday. Month and weekday names can be given as the first three letters of the English names. E.g. Enter 0 0 20 * * * = to run each day at 20:00 o'clock.

![Garbage collection job](images/system-admin-gc-job.png)

The newly added garbage collection job can also be executed manually by right-clicking the job CI and selecting **Execute job now**.