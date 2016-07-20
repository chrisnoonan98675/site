---
title: XL Deploy key deployment indicators report
categories:
- xl-deploy
subject:
- Reporting
tags:
- deployment
- report
weight: 234
---

The XL Deploy key deployment indicators report shows the following indicators describing deployment performance:

{:.table .table-striped}
| Indicator | Description |
| --------- | ----------- |
| Number of successful deployments | Total number of deployments that ran successfully (that is, were fully automated) |
| Number of retried deployments | Total number of deployments that required manual intervention |
| Number of aborted deployments | Total number of deployments that was aborted |
| Number of rollbacks | Total number of rollbacks |
| Average deployment duration | Average duration of the shown deployments |

## Report without filtering

The report is shows all deployments in the date range in tabular format, without aggregation. This is an example of such a report:

![Key Deployment Indicators report](images/reports-key-deployment-indicators-grid.png "Key Deployment Indicators In A Date Range Report")

The report shows the following columns:

{:.table .table-striped}
| Column | Description |
| ------ | ----------- |
| Application | The application that was deployed |
| Environment | The environment on which the application was deployed |
| Success | Number of times the deployment was successful |
| Retried | Number of times the deployment succeeded with manual intervention |
| Aborted | Number of times the deployment aborted |
| Rollbacks | Total number of rollbacks |
| Average Duration | Average duration for the deployment |

## Filtered report

The report allows you to filter on application or environment. Simply select the appropriate filter, and then double-click the desired applications or environments to include them in the report.

## Aggregating results

Select **Aggregate results** to get a summary of the key deployment indicators over the selected applications or environments. The report can be shown in a grid or a chart view.

In grid view, the report looks like this:

![Key Deployment Indicators aggregated by selected applications grid report](images/reports-key-deployment-indicators-aggregated-by-environment-grid.png "Key Deployment Indicators aggregated by selected application grid report")

The report shows the following columns:

{:.table .table-striped}
| Column | Description |
| ------ | ----------- |
|  Application / Environment | The application being deployed or the environment being deployed to |
| Successful Deployments | Number of times the deployment has been successful |
| Rollbacks | This is the total number of rollbacks |
| Retried Deployments | Number of times the deployment succeeded with manual intervention |
| Aborted Deployments |  Number of times the deployment aborted |
| Deployment duration time | Average time for a deployment |

When selecting the chart view, the report is shown as a bar graph, for example:

![Key Deployment Indicators aggregated by selected applications chart Report](images/report-key-deployment-indicators-aggregated-by-environments-chart.png "Key Deployment Indicators aggregated by selected applications chart Report")

The chart shows the selected applications on the x-axis. The chart has two y-axis: the left y-axis shows the number of deployments (successful, retried and aborted) and the right y-axis shows the average deployment time.
