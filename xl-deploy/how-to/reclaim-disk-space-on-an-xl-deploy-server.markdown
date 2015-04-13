---
title: Reclaim disk space on an XL Deploy server
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- cli
- repository
---

Garbage collection can be used to optimize the disk space that is used for the XL Deploy repository. Running garbage collection is only relevant if you deleted a package. When deleting a package, XL Deploy at first only deletes the reference (for performance reasons) and later on you can delete the actual files by running a garbage collect. For imports, garbage collection is not needed.

To start garbage collection, you require admin permissions.

It is not required to run a garbage collection every delete of a package. The system will not break, it will only occupy a small amount of disk space that you can win back by running garbage collection at a later moment in time.

Start the garbage collector using the command-line interface (CLI) by running this command:

    deployit> deployit.runGarbageCollector()

The garbage collector stops automatically when the task is finished. The garbage collector is not accessible from the GUI, but the status of the task can be seen when running `listUnfinishedTasks`.

Garbage collection never runs automatically. The easiest way to schedule a garbage collection is to schedule a CLI script (for example with `cron` on linux) that triggers the garbage collection. How often you want to run the garbage collector depends on how many package you delete, and the buffer of free space on your disk, but in general it is not needed to run it more than once per day.
