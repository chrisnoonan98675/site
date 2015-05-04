---
title: Restart a phase in an active release
categories:
- xl-release
subject:
- Releases
tags:
- release
- phase
---

In an active release, you can abort the current phase and restart the execution from any phase in the past. This can be required if some parts of the release procedure must be repeated. For example, QA rejects a version of the application for release and the test phase must be repeated with an updated version.

When you restart the release from a previous phase, the current phase is interrupted and all remaining tasks are skipped. The release is paused and XL Release makes a copy of all previous phases that need to be repeated. The release owner can change variable values and task details before the release flow is reinitiated.

This is an example of how restarting a phase works in practice. Suppose you have a release with three phases: QA, UAT and Production.

![Restart: first phase failed](../images/restart-first-phase-failed.png)

The QA phase was started with version 1.0 of the product, but bugs were found and QA cannot sign off. So the **Sign off by QA** task has failed. The Dev team is notified and produces a fix: version 1.0.1. You can now start the QA phase again for version 1.0.1.

Do so by clicking **Restart Phase...** in the top bar. XL Release then asks from which phase and task the release should be restarted. Please note, if you want to specify the task from which to restart the phase, you must have permission to skip all tasks before the current (that is, you either have the `edit#task` permission on the release, or you or your team own them). If you do not have permission, you can only restart the phase from the first task.

![Restart confirmation dialog](../images/restart-dialog-1.png)

Press **Continue** to confirm the restart. Alternatively, click **Cancel** to discard the restart.

After you continue, the release is paused and the new phases are created. Before the release flow is resumed, you can make some changes. First, you confirm whether to resume now or later.

![Restart confirmation dialog](../images/restart-dialog-2.png)

You can change the package version variable from 1.0 to 1.0.1 and click **Resume now** to proceed right away.

If you click **Resume later**, the release remains in the 'paused' state. You can change task assignees, due dates, and so on. You can even decide to delete tasks that are no longer relevant.

XL Release creates a phase called **QA (2)**. You can still modify its contents. For example, suppose that the task **Update test scenarios** is no longer relevant; you can remove it.

![Restart confirmation dialog](../images/phase-restarted.png)

To resume a paused release, click **Resume Release** in the top bar.
