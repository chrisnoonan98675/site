---
title: Using the calendar view
categories:
- xl-release
subject:
- Calendar
tags:
- release
- calendar
---

To see an overview of all releases in XL Release on a month-by-month basis, select **Releases** > **Calendar** from the top bar (in XL Release 5.0.x and earlier, click **Calendar** in the top bar).

On the calendar, releases appear as light blue bars. A red exclamation mark icon indicates that there is a dependency conflict; that is, a gate task in a release has a dependency on another release, but the release will end after the start date of the dependent one. This helps you identify "schedule shift", where one or more releases require the completion of other releases.

![Calendar](../images/calendar.png)

To see more information about a release, click it.

**Tip:** Hover over a dependent release to see it highlighted in the calendar.

![Calendar info](../images/calendar-info.png)

## Filtering the calendar

To filter the calendar, click **Filter options** and select:

* **All active releases** to show releases that are currently in progress
* **All planned releases** to show planned releases that have not started yet
* **All completed and aborted releases** to show releases that are completed or aborted
* **Only my releases** to show releases that you own
* **Only flagged releases** to show releases that are flagged with a status message to indicate that they need attention or are at risk

To filter on the release title or tag, use the **By title or tag** box.

## Marking special days

You can mark special days such as holidays by clicking ![Edit calendar day](../images/icon_edit_calendar_day.png) in the day on the calendar. You can give special days a custom label and background color.

![Special day in the calendar](../images/calendar-special-day.png)
