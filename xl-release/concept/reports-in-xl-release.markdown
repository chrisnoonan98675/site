---
title: Reports in XL Release
categories:
- xl-release
subject:
- Variables
tags:
- report
- release
- dashboard
- release value stream
- release automation
---

The reports screen shows graphs and statistics based on the historical release data present in the XL Release repository. It is accessible to users with the [view reports permission](/xl-release/how-to/configure-xl-release-permissions.html).

At the top of the page you can select the time period you are interested in: last 30 days, last 3 months, last 6 months, or range. In the range selection, you can also filter on dates using the **From** and **To** date selectors. The choice is reflected on all graphs and tables on the page.

## Dashboard

The **Releases at risk** section shows the active releases that have been flagged. Click a release to open its summary.

![Releases at risk](../images/dashboard-releases-at-risk.png)

The **Release efficiency** section breaks down the task count and total duration between manual and automated tasks. It provides an overview of your overall level of automation during the selected time period.

![Releases efficiency](../images/dashboard-release-efficiency.png)

The **Average release duration and automation** chart also shows the percentage of automation, but aggregated per month. Use it to assess whether automation improves over time.

![Average release duration and automation](../images/dashboard-release-duration.png)

The **Releases per month** bar chart shows how many releases were finished in a particular month.

![Releases per month](../images/dashboard-release-number.png)

**Top 10 longest releases** shows which release took most time to be completed. For an overview of which parts of the release took most time, there is the **Top 10 longest phases** table. The release names in these tables are hyperlinks to the releases' [flow editor](/xl-release/how-to/using-the-release-flow-editor.html).

![Top-10 Longest Releases and Phases](../images/dashboard-longest-releases-phases.png)

**Top 10 longest tasks** is an overview of which tasks took most time to be completed and who was responsible for each task.

![Top-10 Longest Tasks](../images/dashboard-longest-tasks.png)

The table **Top 10 people most involved** shows which users have spent the most time and handled the most tasks in the selected period.

![Most involved people](../images/dashboard-most-involved-people.png)

## Release automation

**Release duration** shows the average release duration by month.

**Release automation** shows the percentage of automated releases (that is, those that contain at least one automated task) by month.

![Release duration and automation](../images/reports-release-duration-and-automation.png)

The **Number of tasks** chart shows the last 20 releases, with number of tasks, split between automated tasks and manual tasks. It shows whether your releases are becoming more or less automated.

The **Time spent** chart shows the last 20 releases, with task duration split between automated tasks and manual tasks. It shows you where you are spending time in the release.

![Number of tasks and time spent](../images/reports-number-of-tasks-and-time-spent.png)

## Release value stream

This report focuses on the quality of *completed* releases.

![Release Value Stream](../images/release-value-stream.png)

For each phase, an indicator is built based on these metrics:

* Flag count: Incremented each time a task is flagged
* Failure count: Incremented each time a task fails
* Delayed count: The number of tasks that completed after their due date

A phase is considered "critical" when the sum of these three counts is strictly greater than 6 (this value can be customized in the [Reports Settings](/xl-release/how-to/configure-xl-release-general-settings.html#reports) screen). The phase is colored in red and the number of critical phases is displayed in the release header. Each phase also displays its total number of tasks and the value of each criticality metric (if non-zero). At the bottom of the release, the time proportion of each phase is shown, both as a duration and as a percentage of the total release time.

Clicking on a release opens its flow editor, which is slightly different for completed releases.

![Completed release](../images/completed-release.png)

Instead of the assignee, each task shows its individual values for the criticality metrics (if non-zero). The failure count is color-coded: yellow if there were at least two failures, and red if there were at least four (these values can be customized in the Reports Settings screen).

Tasks can be filtered by criticality metric (with the fourth option "clean tasks" referring to tasks that have all metrics to zero):

![Completed release filter](../images/completed-release-filter.png)
