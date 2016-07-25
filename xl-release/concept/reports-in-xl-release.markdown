---
title: Reports in XL Release
categories:
- xl-release
subject:
- Reports
tags:
- report
- release
- dashboard
- release value stream
- release automation
weight: 485
---

XL Release reports show graphs and statistics based on historical release data. In XL Release 4.6.x and earlier, this data is stored in the XL Release repository. In XL Release 4.7.0 and later, it is stored in the [archive database](/xl-release/concept/how-archiving-works.html). Releases that are completed or aborted but not yet archived do not appear in reports.

**Note:** The data for the [**Releases at risk**](/xl-release/concept/dashboard-report.html#releases-at-risk) section of the dashboard report is an exception; because this section is about active releases, its data is stored in the XL Release repository.

## Types of reports

The following types of reports are available:

* [Dashboard](/xl-release/concept/dashboard-report.html)
* [Release automation](/xl-release/concept/release-automation-report.html)
* [Release value stream](/xl-release/concept/release-value-stream-report.html)

To access a report, select it from the **Reports** list in the top menu bar.

## Report permissions

Reports are available to users with the [*view reports* permission](/xl-release/how-to/configure-permissions.html). Also, each report only shows the releases that the user has permission to view.

## Report caching

To ensure the level of performance of the user interface, XL Release caches each time period view of each report for one hour. This means that you might experience a delay between the time that a release is [archived](/xl-release/concept/how-archiving-works.html) and the time that it appears in a report view.
