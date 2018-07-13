---
title: Dashboards in XL Release
categories:
- xl-release
subject:
- Dashboards
tags:
- reports
- release
- dashboards
- release value stream
---

XL Release reports show graphs and statistics based on historical release data. This data in the [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed or aborted but not yet archived do not appear in reports.

## Types of dashboards

The following default dashboards are available:

* [Reports](/xl-release/concept/reports-dashboard-tab.html)
* [Release value stream](/xl-release/concept/release-value-stream-dashboard.html)

If you are using XL Release version 7.5 or earlier you can view this type of report:

* [Release automation](/xl-release/concept/release-automation-report.html)

To access a report, select it from the **Reports** list in the top menu bar.

## Report permissions

Reports are available to users with the [*view reports* permission](/xl-release/how-to/configure-permissions.html). Also, each report only shows the releases that the user has permission to view.

## Report caching

To ensure the level of performance of the user interface, XL Release caches each time period view of each report for one hour. This means that you might experience a delay between the time that a release is [archived](/xl-release/concept/how-archiving-works.html) and the time that it appears in a report view.
