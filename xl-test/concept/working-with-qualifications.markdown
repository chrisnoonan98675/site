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

XL Test ships with two sample qualifications - one for performance tests and one for functional tests. Qualifications are referred to @@synthetic.xml. Editing the Python algorithms - see creating a report.

The icon to the left of a test specification on the Test specifications screen shows whether the qualification of the most recent execution is passing or failing. In case of a failed qualification, a reason is displayed in the Test specifications screen.
