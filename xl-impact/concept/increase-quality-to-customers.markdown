---
title: Increase Quality to Customers
categories:
- xl-impact
subject:
- KPI
tags:
- kpi
- goals
- tiles
---

## KPI: Costumer-Facing Defects

The customer experience can be directly related to how well everything works for your customers—encountering defects can diminish the overall experience. Defects in production can indicate larger process problems, as defects were not caught before going live. Response time is also a key part of understanding the customer experience, as longer response times or resolution rates indicate issues in defect correction or how well teams can adjust work plans as problems arise.

1.	Time to Issue Resolution Trend
  *	Issue resolution time is calculated by taking the mean issue age of all issues resolved during the current period. This mean is compared to the previous period mean, and if the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, then the summary text will display “stable” instead of a direction.

1.	Resolution Rate Trend
  *	Issue resolution rate is calculated by dividing the number of issues resolved during the current period by the number of issues reported during the same period. This rate is compared to the previous period rate, and if the difference is greater than +/- 10%, the direction is displayed in the summary text. If the difference is less than +/-10%, then the summary text will display “stable” instead of a direction.

1.	Issue Trends: Features (waffle)
  *	The grid represents 100% of issues reported during the current period. The proportion of grid squares colored from the total represents the proportion of issues in the period that are related to the “leader” component. The “leader” component is calculated using a market share algorithm to determine the most popular values in the "Component(s)" Jira issue field for production issues reported during this period.
  *	Only Jira issues that mention a component are used for the calculation, so if out of all of the issues reported only 1 mentions a component, that component is automatically the “leader” with 100%.
  *	The proportion of colored grid squares shows the number attributed to the “leader” compared against the total number of issues reported. So the total number of issues will be the total number of issues reported (not the total number that mention components), and the filled grid squares will reflect the number of mentions as a percentage.

1.	Issue Trends: Issue Type (waffle)
  *	The grid represents 100% of issues reported during the current period. The proportion of grid squares colored from the total represents the proportion of issues in the period that are related to the “leader” component. The “leader” component is calculated using a market share algorithm to determine the most popular values in the "Issue Type" Jira field for production issues reported during this period.

1.	Issues Reported in Production
  *	Number of reported issues is the sum of all issues reported during this period for production. The smaller “previously” number is the sum of all issues reported during the previous period, and the % change is the difference between the two.

1.	Longest Gap Between Issues
  *	First, all issues reported for production during the current period are ordered by creation time and date. Then, gaps are identified as the difference between the start times of each item in the list. From the complete list of difference values, the maximum is presented as the longest gap.

1.	Reported Severity Breakdown: Issues in Production Chart
  *	Top bar represents issues reported during current period, with the whole bar representing all issues (100%) and sections stacked based on number of issues reported for each level of severity.
  *	Bottom bar represents the same for issues reported during the previous period.

1.	Open Severity Breakdown: Issues in Production Chart
  *	Top bar represents issues open during current period, with the whole bar representing all issues (100%) and sections stacked based on number of issues reported for each level of severity.
  *	Bottom bar represents the same for issues open during the previous period.
  *	An issue is considered open during the period if any of the issue’s "open" periods overlap with the report period. An “open” period is either: (1) time between issue is created and issue was moved to Done; (2) time between issue was reopened and issue was moved to Done; (3) "Open" period may be still in progress (issue is still open) - such issues count as if their Done time is somewhere in the future.
  *	Issues open through multiple periods may be counted in both the current and previous periods.

1.	Average Time to Resolution Chart
  *	Each point is the set of issues resolved on a particular day rated with the color-coded level of severity. This means that if 1 issue was resolved on that date, the point represents 1 issue; if 6 issues were closed on that date, the point represents all 6 (assuming they are all the same level of severity).
  *	X-axis is time, representing date of resolution.
  *	Y-axis is time, representing time to resolution. Units will scale based on visible data.

  Each set of points and connecting lines are color-coded based on the severity rating of the issues. There is a separate set of points/line for each level of severity. If multiple issues with different levels of severity are all resolved on the same day, multiple points will appear on the chart to reflect the different severity levels represented.
