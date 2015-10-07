---
title: XL Deploy report dashboard
categories:
- xl-deploy
subject:
- Reporting
tags:
- deployment
- report
---

## Dashboard

When opening the Reports section for the first time, XL Deploy will show a high-level overview of your deployment activity.

![Reports Dashboard](images/reports-dashboard.png)

The dashboard consists of three sections that each give a different view of your deployment history:

* **Current Month** shows information about the current month and gives insight into current deployment conditions.
* **Last 6 Months** shows trend information about the last 6 complete months.
* **Last 30 Days** shows information about the past 30 days of deployments.

The following graphs are displayed on the dashboard:

* **Status Overview** shows how many of the current month's deployments were successful, successful with manual intervention (Retried), failed or is a rollback.
* **Average Duration of Successful Deployments** shows the average duration of successful deployments as a frequency diagram. It show the duration distribution over all deployments and identifies any outliers.
* **Throughput** shows the throughput of deployments divided into successful, successful with manual intervention, failed deployments and rollbacks over the last 6 months. The black line indicates the percentage of successful deployments per month.
* **Deployment Duration over Time** shows the average deployment duration over the last 6 months.
* **Top 5 Successful Deployments** shows the top 5 applications with most successful deployments over the last 30 days.
* **Top 5 Re-tried Deployments** shows the top 5 applications with most retries (manual intervention) during deployments over the last 30 days.
* **Top 5 Longest Running Deployments** shows the top 5 applications with longest running deployments over the last 30 days.

Rollbacks never count towards successful deployments, even if the rollback is executed successfully.

To refresh the dashboard, press the reload button on the top right corner.
