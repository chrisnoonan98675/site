---
title: XL Release dashboard report
categories:
- xl-release
subject:
- Reports
tags:
- report
- release
- dashboard
---

The XL Release dashboard report provides important information such as releases that have been flagged and which releases have the highest level of automation. The data for this report comes from XL Release's [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed but not yet archived do not appear in reports. The exception is the **Releases at risk** section; this section is about [active](/xl-release/concept/release-life-cycle.html) releases, so its data comes from XL Release's repository.

To access the report, select **Reports** > **Dashboard** from the top menu. Use the buttons at the top of the report to select a time period such as "last six months" or a specific date range.

## Releases at risk

The **Releases at risk** section shows [active](/xl-release/concept/release-life-cycle.html) releases that contain one or more tasks that are [flagged](/xl-release/concept/xl-release-task-overview.html#task-details). Click a release to open its summary.

![Releases at risk](../images/dashboard-releases-at-risk.png)

## Release efficiency

The **Release efficiency** section provides an overview of your overall level of automation during the selected time period. It shows the following information about [completed](/xl-release/concept/release-life-cycle.html) releases in the selected time period:

* Total numbers of tasks and time spent
* A comparison of the percentage of automated tasks and manual tasks
* A comparison of the time spent on automated tasks and manual tasks

![Releases efficiency](../images/dashboard-release-efficiency.png)

## Average release duration and automation

The **Average release duration and automation** section allows you to assess whether your level of automated improved over time during the selected time period. It shows the percentage of automated tasks in [completed](/xl-release/concept/release-life-cycle.html) releases during the selected time period, aggregated per month.

![Average release duration and automation](../images/dashboard-release-duration.png)

## Releases per month

The **Releases per month** section shows how many releases were [completed](/xl-release/concept/release-life-cycle.html) in each month in the selected time period.

![Releases per month](../images/dashboard-release-number.png)

## Top 10 longest releases and phases

The **Top-10 Longest Releases** section shows the ten releases that were [completed](/xl-release/concept/release-life-cycle.html) in the selected time period that had the longest duration. **Top-10 Longest Phases** shows the ten phases from these releases that had the longest duration. Both are shown in descending order.

Click a release name to open it in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).

![Top-10 Longest Releases and Phases](../images/dashboard-longest-releases-phases.png)

## Top 10 longest tasks

The **Top-10 Longest Tasks** section shows the ten tasks with status *completed* or *completed in advance* in the selected time period that had the longest duration. It also shows the task owner (the most recent assignee).

![Top-10 Longest Tasks](../images/dashboard-longest-tasks.png)

## Top 10 people most involved

The **Top-10 People Most Involved** section shows the users who spent the most time and handled the most tasks in the selected time period. To calculate this, XL Release groups the tasks with status *completed* or *completed in advance* by owner (that is, the most recent assignee), calculates their durations, and sorts them in descending order.

![Most involved people](../images/dashboard-most-involved-people.png)
