---
title: Using release dashboards
since: XL Release 5.0.0
---

To see an overview of important information about a template or release, select **Release dashboard** from the **Show** menu. The release dashboard is a customizable view where you can add, configure, move, and remove tiles that show planning information and details about release status. For information about the tiles that are available, refer to [Overview of release dashboard tiles](/xl-release/concept/release-dashboard-tiles.html).

![Release dashboard](/xl-release/images/release-dashboard.png)

**Tip:** In XL Release 4.8.x and earlier, the [release summary](/xl-release/how-to/using-the-release-summary.html) is available instead of the release dashboard.

## Add a tile to the release dashboard

To add a tile to the release dashboard:

1. Click **Configure**.
1. Click **Add tile**.
1. Select the tile from the list and click **Add tile**.
1. Optionally resize and move the tile as desired.
1. Click **View mode** to save your changes.

## Configure release dashboard tiles

To configure the tiles on the release dashboard:

1. Click **Configure**.
1. Click ![Gear icon](/images/button_configure_tile.png) on the tile you want to configure.
1. Make your changes and click **Save**.
1. Click **Back to view mode** to save your changes.

## Arrange tiles on the release dashboard

To arrange tiles on the release dashboard:

1. Click **Configure**.
1. Left-click and hold the title bar of a tile, then drag it to the desired position.
1. To resize a tile, drag its lower right corner.
1. Click **Back to view mode** to save your changes.

## View detailed information for a tile

Many tiles have a detail view that offers additional information. To go to the detail view, click the tile. To return to the release dashboard, click **Back to view mode**.

## Remove a tile from the release dashboard

To remove a tile from the release dashboard:

1. Click **Configure**.
1. On the tile that you want to remove, click ![Delete icon](/xl-release/images/xlr-tile-delete-icon.png). Note that you cannot undo removing a tile.
1. Click **Back to view mode** to save your changes.

## Release dashboard tiles

In XL Release 5.0.0 and later, the [release dashboard](/xl-release/how-to/using-the-release-dashboard.html) is a customizable view where you can add, configure, move, and remove tiles that show planning information and details about release status.

![Release dashboard](/xl-release/images/release-dashboard.png)

The topic provides an overview of the tiles that are available.

### JIRA issues

The JIRA issues tile retrieves information from an [Atlassian JIRA server](/xl-release/how-to/jira-plugin.html#set-up-a-jira-server) based on a [JQL query](https://confluence.atlassian.com/jira/advanced-searching-179442050.html) that you specify in the tile configuration. The tile summarizes issues by their status in JIRA.

To see the list of JIRA issues that are shown, click the tile to go to its detail view.

### Jenkins builds

The Jenkins build tile provides an overview of the status of Jenkins jobs started by the release.

To see the list of Jenkins builds that are shown, click the tile to go to its detail view.

**Note:** If the template or release does not contain any [Jenkins tasks](/xl-release/how-to/create-a-jenkins-task.html), this tile will not show any data.

### Release alerts

The release alerts tile shows different types of release warnings:

* **Status flags**: Flags that have been manually set on the release or on a task to indicate that the release needs attention or is at risk
* **Dependencies**: Alerts for dependent releases that are not completed
* **Delays**: All active and planned tasks that have a due date in the past

### Release health

The release health tile shows a traffic light that indicates the health of the release in a single glance. Its colors indicate:

{:.table table-striped}
| Color | Description |
| ----- | ----------- |
| Red | The release is in a failed state, or there are one or more red ("release at risk") status flags on the release |
| Yellow | The calculated end date of the release is after its configured due date, or there are one or more yellow ("attention needed") flags on the release |
| Green | There are no issues with the release |

To see detailed information about release status flags, click the tile to go to its detail view.

### Release progress

The release progress tile shows how many tasks have been completed and indicates whether the release is proceeding on time.

### Release summary

The release summary tile shows the template or release's start and due dates, duration, current phase, and owner.

### Release timeline

The release timeline tile shows a timeline of the release and its phases.

The timeline start and end dates appear as follows:

* If an item has not started, its *scheduled start date* and *due date* appear.
* If an item is complete, the *actual start and end dates* appear.
* If an item is in progress, the start date that appears is the *actual start date*. If the item is on schedule and the scheduled start date is after the current date, then the end date is the *scheduled start date*. Otherwise, the end date is the *current date*.

To see detailed information, including releases that depend on or block this release, click the tile to go to its detail view.

### Users and teams

The users and teams tile (available in XL Release 5.0.1 and later) shows an overview of the number of tasks assigned to users in the template or release.

To see detailed information about task assignment across all users and teams, click the tile to go to its default view.

### XL Deploy deployments

The XL Deploy deployments tile shows information about deployments that were started from the release.

To see a list of the deployments, click the tile to go its detail view.

**Note:** If the template or release does not contain any [XL Deploy tasks](/xl-release/how-to/create-an-xl-deploy-task.html), this tile will not show any data.
