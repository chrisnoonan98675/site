---
title: Release value stream report
categories:
- xl-release
subject:
- Reports
tags:
- report
- release
- release value stream
---

To access the XL Release release value stream report, select **Reports** > **Release value stream** from the top menu.

The release value stream report focuses on the quality of *completed* releases.

![Release Value Stream](../images/release-value-stream.png)

For each phase, an indicator is built based on these metrics:

* Flag count: Incremented each time a task is flagged
* Failure count: Incremented each time a task fails
* Delayed count: The number of tasks that completed after their due date

A phase is considered "critical" when the sum of these three counts is strictly greater than 6 (this value can be customized in the [Reports Settings](/xl-release/how-to/configure-xl-release-general-settings.html#reports) screen). The phase is colored in red and the number of critical phases is displayed in the release header. Each phase also displays its total number of tasks and the value of each criticality metric (if non-zero). At the bottom of the release, the time proportion of each phase is shown, both as a duration and as a percentage of the total release time.

## Completed releases

Clicking on a release opens its flow editor, which is slightly different for completed releases.

![Completed release](../images/completed-release.png)

Instead of the assignee, each task shows its individual values for the criticality metrics (if non-zero). The failure count is color-coded: yellow if there were at least two failures, and red if there were at least four (these values can be customized in the Reports Settings screen).

## Filter tasks

Tasks can be filtered by criticality metric (with the fourth option "clean tasks" referring to tasks that have all metrics to zero):

![Completed release filter](../images/completed-release-filter.png)
