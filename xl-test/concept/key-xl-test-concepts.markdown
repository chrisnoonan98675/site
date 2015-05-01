---
layout: beta
title: Key XL Test concepts
categories:
- xl-test
subject:
- Architecture
tags:
- test results
- test tool
- test specification
- dashboard
- report
---

XL Test lets you manage, visualize, and analyze tests across all of your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

## Test specifications

In XL Test, a *test specification* represents a coherent set of tests. These can be jobs or tasks that include  that include automated tests. You can see the results of these tests in dashboards, reports, and qualifications.

You can create a test specification [in the XL Test interface](/xl-test/how-to/add-a-test-specification.html), by [importing existing test results](/xl-test/how-to/import-test-results.html), or by [pushing results to XL Test via Jenkins](/xl-test/how-to/connect-xl-test-to-a-jenkins-job.html).

XL Test also allows you to create [executable test specifications](/xl-test/how-to/execute-tests-from-xl-test.html). XL Test delegates execution to the appropriate test tool(s) and takes care of importing the results.

## Test runs

A *test run* is the set of results associated with a single execution of the tests in a test specification.

## Reports and dashboards

XL Test includes a variety of *reports* for functional test results and performance test results. Some reports represent a single test run, while others aggregate data from multiple test runs.

You can add reports to *dashboards* for a quick visual overview of test results. You can also create [custom reports](/xl-test/how-to/create-a-custom-report-in-xl-test.html).

## Qualification

A *qualification* is XL Test's determination of whether a particular test run passed or failed, based on a configurable analysis of the test results. You can create [your own qualifications](/xl-test/how-to/create-a-custom-qualification-in-xl-test.html), and they can be applied to all test specifications, even those that group results from multiple test tools. This allows XL Test to seamlessly integrate with a continuous delivery pipeline.

## Plugins

XL Test integrates with your test tools through *plugins* that search for test results and import results into XL Test. You can create a [custom plugin](/xl-test/how-to/create-a-test-tool-plugin.html).
