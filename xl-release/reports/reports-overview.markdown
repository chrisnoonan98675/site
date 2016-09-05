---
title: Reports overview
---

XL Release reports show graphs and statistics based on historical release data. In XL Release 4.6.x and earlier, this data is stored in the XL Release repository. In XL Release 4.7.0 and later, it is stored in the [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed or aborted but not yet archived do not appear in reports.

**Note:** The data for the [**Releases at risk**](/xl-release/concept/dashboard-report.html#releases-at-risk) section of the dashboard report is an exception; because this section is about active releases, its data is stored in the XL Release repository.

To access a report, select it from the **Reports** list in the top menu bar.

## Report permissions

Reports are available to users with the [*view reports* permission](/xl-release/how-to/configure-permissions.html). Also, each report only shows the releases that the user has permission to view.

## Report caching

To ensure the level of performance of the user interface, XL Release caches each time period view of each report for one hour. This means that you might experience a delay between the time that a release is [archived](/xl-release/concept/how-archiving-works.html) and the time that it appears in a report view.

## Dashboard report

The XL Release dashboard report provides important information such as releases that have been flagged and which releases have the highest level of automation. The data for this report comes from XL Release's [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed but not yet archived do not appear in reports. The exception is the **Releases at risk** section; this section is about [active](/xl-release/concept/release-life-cycle.html) releases, so its data comes from XL Release's repository.

To access the report, select **Reports** > **Dashboard** from the top menu. Use the buttons at the top of the report to select a time period such as "last six months" or a specific date range.

### Releases at risk

The **Releases at risk** section shows [active](/xl-release/concept/release-life-cycle.html) releases that contain one or more tasks that are [flagged](/xl-release/concept/xl-release-task-overview.html#task-details). Click a release to open its summary.

![Releases at risk](/xl-release/images/dashboard-releases-at-risk.png)

### Release efficiency

The **Release efficiency** section provides an overview of your overall level of automation during the selected time period. It shows the following information about [completed](/xl-release/concept/release-life-cycle.html) releases in the selected time period:

* Total numbers of tasks and time spent
* A comparison of the percentage of automated tasks and manual tasks
* A comparison of the time spent on automated tasks and manual tasks

![Releases efficiency](/xl-release/images/dashboard-release-efficiency.png)

### Average release duration and automation

The **Average release duration and automation** section allows you to assess whether your level of automated improved over time during the selected time period. It shows the percentage of automated tasks in [completed](/xl-release/concept/release-life-cycle.html) releases during the selected time period, aggregated per month.

![Average release duration and automation](/xl-release/images/dashboard-release-duration.png)

### Releases per month

The **Releases per month** section shows how many releases were [completed](/xl-release/concept/release-life-cycle.html) in each month in the selected time period.

![Releases per month](/xl-release/images/dashboard-release-number.png)

### Top 10 longest releases and phases

The **Top-10 Longest Releases** section shows the ten releases that were [completed](/xl-release/concept/release-life-cycle.html) in the selected time period that had the longest duration. **Top-10 Longest Phases** shows the ten phases from these releases that had the longest duration. Both are shown in descending order.

Click a release name to open it in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).

![Top-10 Longest Releases and Phases](/xl-release/images/dashboard-longest-releases-phases.png)

### Top 10 longest tasks

The **Top-10 Longest Tasks** section shows the ten tasks with status *completed* or *completed in advance* in the selected time period that had the longest duration. It also shows the task owner (the most recent assignee).

![Top-10 Longest Tasks](/xl-release/images/dashboard-longest-tasks.png)

### Top 10 people most involved

The **Top-10 People Most Involved** section shows the users who spent the most time and handled the most tasks in the selected time period. To calculate this, XL Release groups the tasks with status *completed* or *completed in advance* by owner (that is, the most recent assignee), calculates their durations, and sorts them in descending order.

![Most involved people](/xl-release/images/dashboard-most-involved-people.png)

## Release automation report

The XL Release release automation report shows the level of automation used in your releases. The data for this report comes from XL Release's [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed but not yet archived do not appear in reports.

To access the report, select **Reports** > **Release automation** from the top menu. Use the buttons at the top of the report to select a time period such as "last six months" or a specific date range.

### Release duration and automation

The **Release duration** section shows the average duration of [completed](/xl-release/concept/release-life-cycle.html) releases by month, while the **Release automation** section shows the average percentage of automated tasks in releases that were completed in each month.

A release's completion date determines the month that it is included in; for example, a release that was started in April and completed in May is included in the averages for May.

![Release duration and automation](/xl-release/images/reports-release-duration-and-automation.png)

### Number of tasks and time spent

The **Number of tasks** section indicates whether your releases are becoming more or less automated. It shows a comparison of the number of automated and manual tasks in each of the last 20 [completed](/xl-release/concept/release-life-cycle.html) releases (sorted by completion date).

The **Time spent** section indicates where you are spending the most time during releases. It shows a comparison of the total time spent in automated and manual tasks in each of the last 20 completed releases (sorted by completion date).

![Number of tasks and time spent](/xl-release/images/reports-number-of-tasks-and-time-spent.png)

## Release value stream report

The XL Release release value stream report focuses on the quality of [completed and aborted](/xl-release/concept/release-life-cycle.html) releases. The data for this report comes from XL Release's [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed or aborted but not yet archived do not appear in reports.

Releases appear in this report in descending order. Under each phase of a release, you can see its duration and the proportion of the total duration that the phase took.

To access the XL Release release value stream report, select **Reports** > **Release value stream** from the top menu. Use the buttons at the top of the report to select a time period such as "last six months" or a specific date range.

![Release Value Stream](/xl-release/images/release-value-stream.png)

### Phase metrics and critical phases

Each release phase can show these criticality metrics:

* Flags: Total number of flags that were added to tasks in the phase
* Failures: Total number of task failures in the phase
* Delays: Total number of tasks in the phase that were completed after their due date

XL Release marks a phase as "critical" when the sum of these metrics is greater than 6; you can customize this value in the [report settings](/xl-release/how-to/configure-xl-release-general-settings.html#reports).

![Reports Settings](/xl-release/images/reports-settings.png)

The name of a critical phase appears on a red background. The total number of critical phases in a release is shown in the release header.

### Filter tasks

You can filter tasks by metric; use the **Show clean tasks** option to see tasks for which all metrics are zero.

![Completed release filter](/xl-release/images/completed-release-filter.png)

### Open a release

Click a release to open it in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).

![Completed release](/xl-release/images/completed-release.png)

Each task shows its individual value for the criticality metrics (if non-zero). The failure count appears in yellow if there were at least two failures and in red if there were at least four failures. You can customize these values in the [report settings](/xl-release/how-to/configure-xl-release-general-settings.html#reports).
