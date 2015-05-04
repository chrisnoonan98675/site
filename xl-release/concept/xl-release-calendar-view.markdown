---
title: XL Release calendar view
categories:
- xl-release
subject:
- Calendar
tags:
- pipeline
- user interface
---

The **Calendar** shows an overview of all releases on a month-by-month basis. 

![Calendar](../images/calendar.png)

The navigation bar offers a convenient way to browse months. By default, it is hidden, you can open it by clicking the gray vertical bar with triangle on the left side of the calendar.

To see the current month, click **Today** in the top bar. 

The **Filter options** give you control over which releases are shown on the calendar.

![Calendar filter options](../images/calendar-filter-options.png)

* **All active releases**: Show releases that are currently in progress.
* **All planned releases**: Show planned releases that have not started yet.
* **All completed releases**: Show releases that have completed or that were aborted.
* **Only my releases**: Show releases that you own.
* **Only flagged releases**: Show releases that are flagged with a status message to indicate that they need attention or are at risk.

You can also filter by release title or tag by using the text box. This will work in addition to the filters already selected.

Releases appear as light blue bars on the calendar. The rules governing the release start and end dates are the same as in the release summary.

Warning icons may appear on the releases to indicate that some attention is needed. A flag appears when a status message is set on the task.

A red icon with exclamation mark appears when there is a dependency conflict, which occurs when a gate task in a release has a dependency on another release, but this release will end later than the start date of the current release. This helps you identify "schedule shift" when one ore more releases require the completion of other releases.

To see more information about a release, click it. A small window with information appears.

![Calendar info](../images/calendar-info.png)

It contains:

* The release title
* The currently active phases and tasks
* A summary of the dependencies on other releases:
	* **Depends on**: Releases that must be complete before a gate task in this release can complete
	* **Depending on this release**: Releases that are waiting on this release

**Tip:** Hover over a dependent release to see it highlighted in the calendar.
