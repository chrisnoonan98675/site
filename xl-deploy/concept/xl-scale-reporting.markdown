---
title: XL Scale reporting
categories:
- xl-deploy
subject:
- XL Scale
tags:
- xl scale
- plugin
- virtualization
- report
weight: 388
---

[XL Scale](/xl-deploy/concept/introduction-to-xl-scale.html) includes reporting functionality underneath the regular Reports tab that shows information about the instantiation and destruction of cloud environments. Enter a date range and press the 'Generate Report' button to generate the standard report. It is also possible to filter on the cloud environment template used to instantiate the environment.

**No filtering**. By default, the report shows all operations in the date range in tabular format.

![Cloud report](images/reports-cloud.png "Cloud activity in a date range report")

The report shows the following columns:

* **Template** - The template used to create the environment.
* **Environment** - The name of the created or destroyed environment.
* **Operation** - The operation executed: instantiate or destroy.
* **User** - The user that performed the operation.
* **Status** - The status of the operation.
* **Start Date** - The date on which the operation was started.
* **Completion Date** - The date on which the operation was completed.

Double-click on a row to show the instantiation/destruction steps and logs for the steps.

**Filter by**. The report allows you to filter on the cloud template used to create the environment.
