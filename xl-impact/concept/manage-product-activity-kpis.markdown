---
title: Manage Product Activity KPIs
categories:
- xl-impact
subject:
- KPI
tags:
- kpi
- goals
- tiles
---

## KPI: Project Activity

Understanding overall project activity provides key knowledge about current status, enabling managers to make data-supported decisions about changes to promote process improvement.

1.	Project Activity Trend
  *	Calculates overall activity by taking a sum of all events (across tools) that occurred during the current period. This value is compared to the same total from the previous period. If the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, then the summary text will display “stable” instead of a direction.

1.	Weekend Activity Trend
  *	The total list of events for the current period is sorted into weekday and weekend events based on the user’s browser time zone settings. The weekend events are tallied and the sum is compared to the previous period. This value is compared to the same total from the previous period. If the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, then the summary text will display “stable” instead of a direction.
  *	Note that the characteristics of the event are considered in this calculation, in order to prevent any weekend-run automated events from over-inflating the total number of weekend events. For example, a release that starts during the week but automatically ends on the weekend will not be counted towards the weekend total, but a Jira issue created during the week and manually closed over the weekend will be.

1.	Project Activity Anomalies
  *	Based on total activity for all days during the period, abnormally high or low days of activity are determined using a triple-exponential algorithm that considers overall activity level, overall activity change trends, and weekly fluctuations. Then from that list the top 5 most anomalous days are displayed in the tile.

1.	Busiest Day
  *	All events for each day during the current period are tallied to create the total amount of activity on each day. The day with the highest total amount of activity is displayed as the busiest day.

1.	Busiest Day of the Week
  *	Daily activity totals for the current period are organized by day of the week (e.g., Monday, Tuesday) based on the user’s browser time zone settings, and the sum is taken of all daily totals for each week day. The weekday (excluding weekend) with the highest total is displayed as the busiest weekday.

1.	Longest Gap in Activity

  *	First, any days with no events (a total activity of 0) are identified, and any consecutive 0-activity days are added together. The largest total of 0-activity days is the value displayed in the tile.
  *	For companies with no weekend activity, this gap will usually be 2 days.

1.	Release Count
  *	All XLR releases started during the period are counted, and the total is displayed in the tile.

1.	Commit Count
  *	All commits made during the period are counted, and the total is displayed in the tile.

1.	Pull Request Count
  *	All pull requests opened during the period are counted, and the total is displayed in the tile.

1.	Build Count
  *	All builds started during the period are counted, and the total is displayed in the tile.

1.	Project Activity Chart
  *	Each bar represents one day of activity, with the stacked colors each representing an activity type.
  *	X-axis is time, in days.
  *	Y-axis is count of activity items. The axis will scale based on the highest count for the displayed period.

## KPI: Task Failures

Creating targeted improvements requires an understanding of the biggest pain points currently in the process. Seeing failures and delays can support that understanding, giving you a foundation to begin targeted changes.

1.	Tasks or Jenkins Jobs with worst failure rate change
  *	First, failure rates are calculated for each XL Release task (by originating template) and Jenkins build (by job name) for the current period and for the previous period. Then the rate of change from the previous to current period is calculated for each item. Those with the highest rate of change between the two periods are listed in the tile.
  *	Because high rates of change always indicate that the number of failures increased (a large decrease would result in a negative difference), this insight highlights tasks or jobs that are getting worse (and thus potentially trouble spots).  

1.	Jobs Failed
  *	All jobs that failed during the current period are counted and this total is displayed in the tile. The smaller “previously” value is the same total for failures in the previous period, and the % change is the difference between the two.

1.	Tasks Failed
  *	All XLR tasks that failed during the current period are counted and this total is displayed in the tile. The smaller “previously” value is the same total for failures in the previous period, and the % change is the difference between the two.

1.	Job Failure Rate
  *	The total number of jobs that failed during the current period is divided by the number of jobs started during the current period, and this value is displayed in the tile. The smaller “previously” value is the same calculation for the previous period, and the % change is the difference between the two.

1.	Tasks Failure Rate
  *	The total number of XLR tasks that failed during the current period is divided by the number of tasks started during the current period, and this value is displayed in the tile. The smaller “previously” value is the same calculation for the previous period, and the % change is the difference between the two.
  
1.	Task Failures-originating template
  *	Dark blue dots represent XLR tasks. In the default display, tasks are grouped by originating template (so all gate tasks in Template A will be 1 dot, and gate tasks in Template B will be another dot); when the toggle is switched to “types,” tasks are grouped by overall task type (so all gate tasks are in 1 dot). Light blue dots represent Jenkins builds grouped by Jenkins job name. The position of each dot represents the number of times that task/build as run versus the number of runs that failed.
  *	X-axis is number of runs.
  *	Y-axis is number of fails.
