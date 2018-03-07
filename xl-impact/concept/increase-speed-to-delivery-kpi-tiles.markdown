---
title: Increase Speed to Delivery KPI Tiles
categories:
- xl-impact
subject:
- KPI
tags:
- kpi
- goals
- tiles
---

## KPI: Frequency and Duration

The frequency and duration of releases demonstrates if content is being released faster and with more frequency; this is a central concept for increasing speed to delivery.

<!--
SCREENSHOT
-->

### Tile descriptions

1.	Release Frequency Trend:
  * The mean release frequency is calculated (see number 2 below) and compared to the previous period. If the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, the summary text displays *stable*.

1.	Release Duration Trend:
  * The mean release duration is calculated using the start times of the first and last event related to the release (events in XL Release, Jenkins, GitHub, etc.) and compared to the previous period. If the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, the summary text displays *stable*.

1.	Release Duration Anomalies:
  * Release duration is calculated for each release including all related events (see number 2 above), and then an exponential algorithm calculates the anomalies based on the array of durations for the period.

1.	Average Release Duration:
  * Release length is calculated from the first object (examples: a pull request or a commit) to the last (examples: a release or a ticket update) and then the mean release length is calculated. The current mean is compared to the previous mean to get the percent change.

1.	Average Release Frequency:
  * The release period is calculated using the start time of each release within the metric period and then the release frequency is 1/(release period). The mean frequency for all releases is taken, and that number is compared to the mean from the previous period to get the percent change.

1.	Release Duration and Frequency Chart:

  *	Each bar represents 1 release, from the first event to the last event (including data from across tools—commits, pull requests, XL Release releases), calculated based on the Metric from number 1 above.
  *	X-axis is the starting time of the release (based on the start time and date of first event).
  *	Y-axis is total time of the release. Units on axis will adjust based on visible data.
  *	Hover includes bar chart that breaks out duration by type of each event: XLR releases, Jenkins builds, and GitHub commits.

## KPI: Batch Size

Increased speed to release is directly tied to how much is accomplished in each release. Moving to CI/CD requires finding a balance between fast, frequent releases and a manageable load of meaningful changes in each release. Additionally, faster releases are only valuable if the contents of each release adds value. Thus, understanding how much work is included, and what the work contains, is key to measuring the success of increased speed.

<!--
SCREENSHOT
-->

1.	High Performers: Batch Size
  *	All releases are compared by duration for the period, and then the mean ticket and story point data for only the top 10% is calculated for this tile.

1.	Average Tickets in Release
  *	Tickets related to the release are found using the Jira issue key via commits in the “releases to customer” started during the metric period. The mean of the total identified issues for the period is the main number in this metric.

1.	Average Story Points in Release
  *	Tickets related to the release are found using the Jira issue key via commits in the “releases to customer” started during the metric period, and the story points are summed from these tickets. The mean of the total story points for issues in the period is the main number in this metric.

1.	Story Points by Release Chart
  *	Each dot represents 1 release (including all events).
  *	X-axis is starting time of the release (based on start time/date of first event).
  *	Y-axis is number of story points included in the release, which is calculated by taking the sum of the number of tickets referenced by commits associated with that release.
  *	Related best practice: do not re-use tickets, as they will be counted for each release they are used on

1.	Issue Severities/Types per Release Chart
  *	Each bar represents the sum of all tickets in 1 release (including all events), with the stacked colors representing categories of tickets included in that release. Category data is pulled from Jira tickets associated with that release by commits.
  *	X-axis labels include dates of release starts, but the placement of bars is even across the axis and not calculated based on a time scale.
  *	Y-axis is number of tickets included in the release, with height of each color/stack based on the individual sums for the categories.

## KPI: Parallel Work

Part of the balancing act to increase speed is determining how much work can be reasonably done in parallel. This will vary by team, organization, and project, so it’s important to understand how parallel tasks relate to the length of releases—different contexts will change how much is reasonable to do at the same time.

1.	High Performers: Parallel Work
  *	All releases are compared by duration for the period, and then the mean branch and Pull Request data for only the top 10% is calculated for this tile.
  *	For branches, the number of branches is calculated using the diversions inside the Git version tree to capture parallelization rather than merges (more details are available in the code repo for XL Impact).
  *	For pull requests, any pull request listed on a commit related to the release is counted towards the total pull requests for that given release, and then the number open at the same time is calculated based on that set.
  *	Number of releases in the metric period is summed and divided by the number of days in the period (“period size”). For releases that start or end outside of the period, only the portion inside the period will be counted in the total.

1.	Average Open Pull Requests per Day
  *	Number of pull requests in the metric period is summed and divided by the number of days in the period (“period size”). For PRs that start or end outside of the period, only the portion inside the period will be counted in the total.

1.	Running Releases
  *	Each bar represents one running XLR release, which is different than the overall releases described above—this includes all XLR releases in the period, even if multiple are tied to the same “release to customer.”
  *	X-axis is time.
  *	Y-axis is number of parallel running releases. Y-axis is not labeled, but all releases with simultaneous run time are stacked vertically (demonstrating the same x-axis values).

1.	Open Pull Requests
  *	Each bar represents one open pull request.
  *	X-axis is time.

  Y-axis is number of parallel open pull requests. Y-axis is not labeled, but all pull requests with simultaneous open time are stacked vertically (demonstrating the same x-axis values).
