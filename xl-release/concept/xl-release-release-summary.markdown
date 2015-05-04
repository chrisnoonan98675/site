---
title: XL Release release summary
categories:
- xl-release
subject:
- Releases
tags:
- release
- user interface
---

The Release Summary shows an overview of the current release.

## Timeline

The first section shows a timeline of the release. 

![Release Summary Timeline](../images/summary-timeline.png)

The complete timeline of the current release appears in orange.

The timeline start and end dates appear as follows:

* If an item has not started, its **scheduled start date** and **due date** appear.
* If an item is complete, the **actual start and end dates** appear.
* If an item is in progress, the start date that appears is the **actual start date**. If the item is on schedule and the scheduled start date is after the current date, then the end date is the **scheduled start date**. Otherwise, the end date is the **current date**.

**Note:** The release phases (thin lines at the top of the main release) are cropped to fit inside the release.

A release may depend on other releases; you can specify this on a [gate](/xl-release/how-to/create-a-gate-task.html). If the release has any dependencies on other releases, then those releases appear above it, under **Depends On**. The releases that depend on this release appear below the release in the timeline (if visible) and are summarized in the **Blocks** header.

The current phase and currently active tasks appear below the timeline.

## Task overview

The next section is the task overview.

![Release Summary Task overview](../images/summary-tasks.png)

This widget shows the tasks that are coming up, that are currently active, and that need to be done. The tasks are split per team or per user so you can easily see how teams and people are allocated throughout the release.

## Alerts

The **Alerts** section shows release warnings.

![Release Summary Task overview](../images/summary-alerts.png)

**Status Flags** are manually set on the release or on a task to indicate that the release needs attention or is at risk.

The **Dependencies** section shows alerts for dependent releases that are not finished.

Finally, **Delays** shows all tasks (active and planned) that have a due date in the past.
