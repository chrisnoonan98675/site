---
title: XL Deploy deployment report
categories:
- xl-deploy
subject:
- Reporting
tags:
- deployment
- report
---

The deployments report shows deployments done in a given date range. It also allows user to aggregate deployments by selected applications and environments. There are three ways to aggregate the data in the report.

**No filtering**. By default, the report shows all deployments in the date range in tabular format.

![Deployments report](images/reports-deployments.png)

The report shows the following columns:

* Package - The package and version that was deployed.
* Environment - The environment to which it was deployed.
* Deployment Type - The type of the deployment (*Initial*, *Upgrade*, *Undeployment* or *Rollback*).
* User - The user that performed the deployment.
* Status - The status of the deployment. 
* Start Date - The date on which the deployment was started.
* Completion Date - The date on which the deployment was completed.

Double-click on a row to show the deployment steps and logs for that particular deployment.

**Filter by**. The report allows you to filter on application or environment. Simply select the appropriate filter, and then double-click the desired applications or environments to include them in the report.

![Deployments filtered report](images/reports-deployments-filtered.png)

**Aggregate results**. Select the 'Aggregate results' checkbox to get the total number of deployments for the selected applications or environments. This report can be shown in a grid and a chart view. 

In grid view, the report looks like this:

![Deployments aggregated by selected applications grid report](images/report-deployments-aggregated-by-applications-grid.png "Deployments aggregated by selected applications grid report")

When selecting the chart view, the report is shown as a bar graph, for example:

![Deployments aggregated by selected applications chart report](images/report-deployments-aggregated-by-applications-chart.png "Deployments aggregated by selected applications chart report")

**Note:** If you change the name of an application that was previously deployed, you will not be able to access detailed reports about that application.
