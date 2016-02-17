---
title: Using the release summary
categories:
- xl-release
subject:
- Releases
tags:
- release
- release summary
---

In a release or template, select **Release summary** from the **Show** menu to go to the release summary page, where you can see an overview of important information about the release or template.

## Timeline

**Timeline** shows a timeline of the release. The complete timeline appears in orange, with lines indicating the phases within it. The current phase and currently active tasks appear below the timeline.

![Release Summary Timeline](../images/summary-timeline.png)

The timeline start and end dates appear as follows:

* If an item has not started, its *scheduled start date* and *due date* appear.
* If an item is complete, the *actual start and end dates* appear.
* If an item is in progress, the start date that appears is the *actual start date*. If the item is on schedule and the scheduled start date is after the current date, then the end date is the *scheduled start date*. Otherwise, the end date is the *current date*.

### Release dependencies

If the release depends on other releases (as specified on a [gate task](/xl-release/how-to/create-a-gate-task.html)), then they appear above the release in the timeline, under **Depends On**.

If other releases depend on this release, then they appear below the release in the timeline, under **Blocks**.

## Task overview

**Task overview** shows upcoming tasks, tasks that are currently active, and tasks that need to be done. You can view the task overview for teams or for users.

![Release Summary Task overview](../images/summary-tasks.png)

## Alerts

**Alerts** shows release warnings.

![Release Summary Task overview](../images/summary-alerts.png)

There are several types of warnings:

* **Status flags** shows flags that have been manually set on the release or on a task to indicate that the release needs attention or is at risk
* **Dependencies** shows alerts for dependent releases that are not finished
* **Delays** shows all active and planned tasks that have a due date in the past

## Customize the release summary (XL Release 5.0.0 and later)

In XL Release 5.0.0 and later, you can customize the release summary by moving, resizing, adding, and removing tiles. By default, the Timeline, Task overview, and Alerts tiles are placed on the release summary.

### Arrange tiles on the release summary

To arrange tiles on the release summary:

1. Click **Configure**.
1. Left-click and hold the title bar of a tile, then drag it to the desired position.
1. To resize a tile, drag its lower right corner.
1. Click **Save changes**.

### Add a tile to the release summary

To add a tile to the release summary:

1. Click **Configure**.
1. Click **Add tile**.
1. Select the tile from the list and click **Add tile**.
1. Optionally resize and move the tile as desired.
1. Click **Save changes**.

### Remove a tile from the release summary

To remove a tile from the release summary:

1. Click **Configure**.
1. On the tile that you want to remove, click ![Delete icon](../images/xlr-tile-delete-icon.png). Note that you cannot undo removing a tile.
1. Click **Save changes**.
