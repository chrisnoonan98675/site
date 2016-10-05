---
title: XL Deploy report dashboard
categories:
- xl-deploy
subject:
- Reporting
tags:
- deployment
- report
weight: 231
---

When opening the Reports section for the first time, XL Deploy will show a high-level overview of your deployment activity.

![Reports Dashboard](images/reports-dashboard.png)

The dashboard consists of three sections that each give a different view of your deployment history:

{:.table .table-striped}
| Section | Description |
| ------- | ----------- |
| Current Month | Information about the current month and gives insight into current deployment conditions |
| Last 6 Months | Trend information about the last 6 complete months |
| Last 30 Days | Information about the past 30 days of deployments |

The following graphs appear on the dashboard:

{:.table .table-striped}
| Graph | Description |
| ----- | ----------- |
| Status Overview | How many of the current month's deployments were successful, successful with manual intervention (retried), failed, or is a rollback |
| Average Duration of Successful Deployments | Average duration of successful deployments as a frequency diagram; shows the duration distribution over all deployments and identifies any outliers |
| Throughput | Throughput of deployments divided into successful, successful with manual intervention, failed deployments, and rollbacks over the last 6 months; the black line indicates the percentage of successful deployments per month |
| Deployment Duration over Time | Average deployment duration over the last 6 months |
| Top 5 Successful Deployments | Top 5 applications with most successful deployments over the last 30 days |
| Top 5 Re-tried Deployments | Top 5 applications with most retries (manual intervention) during deployments over the last 30 days |
| Top 5 Longest Running Deployments | Top 5 applications with longest running deployments over the last 30 days |

**Note:** Rollbacks never count towards successful deployments, even if the rollback is executed successfully.

To refresh the dashboard, press the reload button on the top right corner.
