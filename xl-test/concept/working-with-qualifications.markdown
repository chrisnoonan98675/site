---
layout: beta
title: Working with qualifications
categories:
- xl-test
subject:
- Qualification
tags:
- test results
- qualification
- test specification
---

XL Test's configurable qualification possibilities allow for seamless integration with a continuous delivery pipeline. XL Test automatically analyzes the test results that are available within XL Test using the specified qualification algorithm. In this way, XL Test supports tailored automated go/no go decision-making since the qualification can be used by continuous delivery management solutions.

There are 3 qualifiers shipped with XL Test:

* Default Functional Tests Qualifier - Fails if any test in the test run fails
* Default Performance Tests Qualifier - Fails if more than 10% of tests have a error response or the average response time is greater that 110% of the long term average
* Difference Functional Tests Qualifier - Fails if there are tests which changed from passed to failed between the previous run and the current run

New qualifictions can be created, this is is described in [Create a custom qualification in XL Test](xl-test/how-to/create-a-custom-qualification-in-xl-test.html)

The icon to the left of a test specification on the Test specifications screen shows whether the qualification of the most recent execution is passing or failing. In case of a failed qualification, a reason is displayed in the Test specifications screen.
