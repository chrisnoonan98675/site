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

XL Test lets you define, manage, execute, visualize, and analyze tests across all your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

## Test specifications

In XL Test, a *test specification* represents a coherent set of tests. These can be jobs or tasks that include  that include automated tests. You can see the results of these tests in dashboards, reports, and qualifications.

You can create a test specification [in the XL Test interface](/xl-test/how-to/add-a-test-specification.html), by [importing existing test results](/xl-test/how-to/import-test-results.html), or by [pushing results to XL Test via Jenkins](/xl-test/how-to/connect-xl-test-to-a-jenkins-job.html).

Also, XL Test can [execute test specifications](/xl-test/how-to/execute-tests-from-xl-test.html). XL Test orchestrates the process and delegates execution to the appropriate test tools.

## Test runs

A *test run* is a single execution of a test specification. Some reports represent a single test run, while others aggregate data from multiple test runs.

## Reports

XL Test includes a variety of *reports* for functional test results and performance test results. You can add reports to *dashboards* for a quick visual overview of test results. You can also create [custom reports](/xl-test/how-to/create-a-custom-report-in-xl-test.html).

## Qualification

*Qualification* is a configurable feature that allows XL Test to seamlessly integrate with a continuous delivery pipeline. XL Test automatically analyzes test results using a qualification algorithm that you choose and returns a go/no-go result. You can also create [custom qualification algorithms](/xl-test/how-to/create-a-custom-qualification-in-xl-test.html).

## Plugins

XL Test supports integration with other tools through *plugins* that search for test results and import results into XL Test. You can create a [custom test tool plugin](/xl-test/how-to/create-a-test-tool-plugin.html).
