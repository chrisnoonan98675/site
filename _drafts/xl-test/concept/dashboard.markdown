---
title: Dashboards
categories:
- xl-test
subject:
- System administration
tags:
- system administration
- Dashboard
- 
---

## Dashboards
A dashboard is the the primary way to quickly get an overview of the current status of your tests. You can create multiple dashboards, each with the information you want to see. The information on a dashboard is represented in tiles: small individual reports.

A dashboard has to modes: a view mode that shows the information, and an edit mode that enables you to alter the information shown.
### Dashboard list
There will be a list with all available dashboards. On a new setup, there will be one dashboard: `Demo Dashboard`. Click on the button with `Add a dashboard`. A new window will appear. You have the following options:

### View mode

All the controls for a dashboard are in the header bar. From left to right the following controls are available:

* Dashboard dropdown - In this drop down menu you can select the different dashboards on your system.
* Full Screen - Show only your dashboard
* Edit dashboard - Modify your dashboard
* Date selector - Select the time period this dashboard shows.

The date selector has two parts: A point in time, and a range.
The point in time is either `now` or a specific date. Now is the current moment, and is dynamic, it moves with the clock. A specific date is a fixed point in time.
The range is the amount of time before the date point that is shown.

For example, if the date is `now` and the range is 5 days, the test results are shown for the last 5 days. This time windows will shift with time.

If the date is `20 april 2015` and the range is two weeks, the test results are shown for the period of 6 april until 20 april 2015. This time window will stay the same forever.

The time period of the dashboard affects all the tiles on the dashboard. Note that there are reports that are only applicable for a single moment in time, not for a time range. Those reports will show the most recent results.

### Edit mode

If you are in dashboard edit mode, you have the following options for tiles:

In this mode it is possible to edit a dashboard, change its settings and add and remove tiles to the dashboard. If you click on `Save Dashboard` in the top right corner, the dashboard is saved and you are redirected to the view mode.

The dashboard is a grid of 12x12 rectangles. The tiles are rectangles and always occupy 1 or more pieces of the grid. Tiles will always snap to the grid.

#### Managing tiles
* Moving tiles - Click and hold the tile anywhere to drag it to another place. The grey shadow behind the tile will indicate where on the grid the tile is going to snap to.
* Deleting tiles - By clicking the trashcan a tile will be deleted. The system will ask for confirmation, but once a tile is deleted it is not possible to undelete it.
* Editing tile properties - By clicking on the gear a window will pop-up where you can alter the properties of this tile.
* Resizing - By dragging the stripted icon on the right-hand corner, you can resize the tile.


#### Adding tiles

You can add a tile by clicking on the `Add a tile` button in the top right-hand corner. A new window is shown where you can fill in the following options:

* Name - Fill in a name for this tile. This name will be in the header of the tile, so it should be a short representation of the concept this tile is going to present.
* Choose test specification - Select the test specification this tile gets its data from.
* Report type - Select the report this tile shows its data in.

The available reports depend on the type of test specification that is selected. Performance tests have different kind of reports than functional tests. See Reports for more information.

Click on `Add tile` to add the tile to the dashboard. You will return to the dashboard edit screen, where you can see your tile. You can add more tiles if you want. Caution! Your work is not saved yet, click the `Save dashboard` button to save the dashboard and view your dashboard.


