---
title: Working with qualifications
categories:
- xl-testview
subject:
- Qualifications
tags:
- qualification
- test specification
weight: 725
---

XL TestView's configurable qualifications allow for seamless integration with a continuous delivery pipeline. XL TestView automatically analyzes the test results using a specified qualification algorithm. In this way, XL TestView supports tailored automated go/no-go decision making, because continuous delivery management solutions can use the qualification.

XL TestView includes the following default qualifiers:

* Default functional test qualifier: Fails if any test in the test run fails
* Default performance test qualifier: Fails if more than 10% of tests contain an error response or the average response time is greater that 110% of the long-term average
* Regression qualifier: Fails if there are tests that changed from passed to failed between the previous run and the latest run

XL TestView allows you to create custom qualifications to suit your specific needs. Refer to [Create a custom qualification in XL TestView](/xl-testview/how-to/create-a-custom-qualification.html) for more information.

The icon next to a test specification on the test specifications screen shows whether the qualification of the most recent execution passed (check mark) or failed (exclamation mark).
