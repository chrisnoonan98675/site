---
layout: beta
title: Key XL Test concepts
categories:
- xl-test
subject:
- Getting started
tags:
- test
- test result
- test tool
- specification
---

XL Test lets you define, manage, execute, visualize, and analyze tests across all your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

## Test tools

XL Test integrates with a plethora of test tools. It does so by supporting well-known output formats produced by these test tools. XL Test translates these output formats into its uniform event structure allowing unified, cross-tool reporting. A list of supported test tools can be found [here](supported-test-tools-and-test-result-formats.html). XL Test's main focus is on the extensibility of test tools

## Test specifications

Jobs or tasks that include automated tests are shown in XL Test by means of test specifications. Results of test specifications can be shown via dashboards, reports, and qualifications. XL Tests supports creating a test specification through the [user interface](/xl-test/how-to/add-a-test-specification.html), by [importing  existing test results](/xl-test/how-to/import-test-results.html), or by [pushing results to XL Test via Jenkins](/xl-test/how-to/connect-xl-test-to-a-jenkins-job.html).

## Dashboards

XL Test allows you to create a custom dashboard that contains all relevant test results on a single screen.

## Reporting

XL Test provides numerous out-of-the-box reports that visualize test results. XL Test supports the following graphs and tables:

* Bar chart
* Pie chart
* Durations chart that shows the trend in the time required to execute the tests
* Details table that shows a list of failed and passed tests
* Flakiness overview that identifies the tests with the most inconsistent results over time
* General statistics that compare the given run to the previous run on a number of aspects
* Health barometer that shows clickable placeholders to the flakiness overview, durations chart, and details table

It is possible to [create customized reports](/xl-test/how-to/create-a-custom-report-in-xl-test.html).


## Qualification

XL Test's configurable qualification possibilities allow for seamless integration with a continuous delivery pipeline. XL Test automatically analyzes the test results that are available within XL Test using the specified configuration algorithm. In this way, XL Test supports tailored automated go/no go decision-making.

## Test specification execution

XL Test also enables you to [execute test specifications](/xl-test/how-to/execute-tests-from-xl-test.html). XL Test orchestrates the process and delegates execution to the appropriate automated test tools.
