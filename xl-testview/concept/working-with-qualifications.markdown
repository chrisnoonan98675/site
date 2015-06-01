---
layout: beta
title: Working with qualifications
categories:
- xl-testview
subject:
- Qualification
tags:
- test results
- qualification
- test specification
---

XL TestView's configurable qualification possibilities allow for seamless integration with a continuous delivery pipeline. XL TestView automatically analyzes the test results that are available within XL TestView using the specified qualification algorithm. In this way, XL TestView supports tailored automated go/no go decision-making since the qualification can be used by continuous delivery management solutions.

There are three qualifiers shipped with XL TestView:

* Default Functional Tests Qualifier - Fails if any test in the test run fails
* Default Performance Tests Qualifier - Fails if more than 10% of tests have a error response or the average response time is greater that 110% of the long term average
* Difference Functional Tests Qualifier - Fails if there are tests which changed from passed to failed between the previous run and the current run

XL TestView allows you to create custom qualifications in order to suit your specific needs. How to create a custom qualification is described in [Create a custom qualification in XL TestView](/xl-testview/how-to/create-a-custom-qualification-in-xl-test.html).

The icon to the left of a test specification on the test specifications screen shows whether the qualification of the most recent execution is passing (tick mark) or failing (exclamation mark). In case of a failed qualification, a reason for failure can be displayed (expand) in the test specifications screen.
