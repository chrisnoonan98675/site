---
title: Release automation report (XL Release versions 7.5 and earlier)
categories:
- xl-release
subject:
- Reports
tags:
- report
- release
- release automation
removed: XL Release 7.6.0
---

As of XL Release version 7.6.0, the **Release automation report** page from the **Reports** section has been removed.

The XL Release release automation report shows the level of automation used in your releases. The data for this report comes from XL Release's [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed but not yet archived do not appear in reports.

To access the report, select **Reports** > **Release automation** from the top menu. Use the buttons at the top of the report to select a time period such as "Last 6 months" or a specific date range. Type a part of a release title or release tags in the **Filter by title...** and **Add a tag...** boxes. When you start typing the release tag name, the tag autocompletes from a database of a maximum of 500 tags.

## Release duration and automation

The **Release duration** section shows the average duration of [completed](/xl-release/concept/release-life-cycle.html) releases by month, while the **Release automation** section shows the average percentage of automated tasks in releases that were completed in each month.

A release's completion date determines the month that it is included in; for example, a release that was started in June and completed in August is included in the averages for August.

![Release duration and automation](../images/reports-release-duration-and-automation.png)

## Number of tasks and time spent

The **Number of tasks** section indicates whether your releases are becoming more or less automated. It shows a comparison of the number of automated and manual tasks in each of the last 20 [completed](/xl-release/concept/release-life-cycle.html) releases (sorted by completion date).

The **Time spent** section indicates where you are spending the most time during releases. It shows a comparison of the total time spent in automated and manual tasks in each of the last 20 completed releases (sorted by completion date).

![Number of tasks and time spent](../images/reports-number-of-tasks-and-time-spent.png)
