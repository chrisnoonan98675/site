---
title: Key XL TestView concepts
categories:
- xl-testview
subject:
- Architecture
tags:
- test specification
- dashboard
- report
- project
weight: 708
---

XL TestView lets you manage, visualize, and analyze tests across all of your test tools to give you a single source of quality truth. XL TestView acts as the Fitbit of your software.

## Test specifications

A *test specification* represents a coherent set of tests. These can be jobs or tasks that include  that include automated tests. You can see the results of these tests in dashboards, reports, and qualifications.

There are four types of test specification:

* Active test specification: A test specification that can retrieve test results
* Executable test specification: A test specification that can execute test runs and import the results
* Passive test specification: A test specification to which an external process such as Jenkins can push results
* Test specification set: A group that aggregates the results of multiple test specifications

You can create a test specification [in the XL TestView interface](/xl-testview/how-to/create-a-test-specification.html), by [importing existing test results](/xl-testview/how-to/import-test-results.html), or by [pushing results to XL TestView via Jenkins](/xl-testview/how-to/connect-to-a-jenkins-job.html).

## Projects

*Projects* combine test specifications into logical groups.

## Test runs

A *test run* is the set of results associated with a single execution of the tests in a test specification.

## Reports and dashboards

XL TestView includes a variety of [*reports*](/xl-testview/concept/reports.html) for functional test results and performance test results. Some reports represent a single test run, while others aggregate data from multiple test runs. You can add reports to *dashboards* for a quick visual overview of test results.

## Qualification

A *qualification* is XL TestView's determination of whether a particular test run passed or failed, based on a configurable analysis of the test results. Qualifications allow XL TestView to seamlessly integrate with a continuous delivery pipeline.

## Test tools

Test tools are the programs used to test software, such as JUnit, FitNesse or Gatling. XL TestView interprets their results.

## Extensibility

You can [extend XL TestView's functionality](/xl-testview/how-to/extending.html) in several ways.

### Custom test result parsers

Test result parsers are small programs that interpret the results produced by test tools. XL TestView includes several default test result parsers, and you can create [custom parsers](/xl-testview/how-to/create-a-custom-test-results-parser.html) if XL TestView does not support your preferred test tool or test result format out-of-the-box.

### Custom reports

Using Python and HTML, you can create custom reports [based on XL TestView's built-in reports](/xl-testview/how-to/create-a-custom-report.html) or [using Highcharts demo materials](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html). Custom reports can be used in [dashboards](/xl-testview/how-to/create-a-dashboard.html).

### Custom qualifications

You can create [your own qualifications](/xl-testview/how-to/create-a-custom-qualification.html), which can be applied to all test specifications, even those that group results from multiple test tools.
