---
title: Key XL Test concepts
categories:
- xl-test
subject:
- Getting started
tags:
- tests
- test result
- test tool
- specification
---

XL Test lets you define, manage, execute, visualize, and analyze tests across all your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

## Test tools

XL Test integrates with a plethora of test tools. It does so by supporting well-known output formats produced by these test tools. XL Test translates these output formats into its uniform event structure allowing unified, cross-tool reporting. A list of supported test tools can be found [here](test-tools.html).

## Test specifications

Jobs or tasks that include automated tests are shown in XL Test by means of test specifications. Results of test specifications can be shown via dashboards, reports, and qualifications. XL Tests supports creating a test specification through the [user interface](/xl-test/howto/add-a-test-specification.html), by [importing  existing test results](/xl-test/import-test-results.html), or by push results to XL Test via [Jenkins](/xl-test/howto/bla).

## Dashboarding

XL Test allows you to create a custom dashboard that contains all relevant test results on a single screen.

## Reporting

XL Test provides numerous out-of-the-box reports that visualize test results. XL Test supports the following graphs and tables:

* Bar chart.
* Pie chart.
* Durations chart. Shows the trend in the time required to execute the tests.
* Details table. Shows a list of failed and passed tests.
* Flakiness Overview. Identifies the tests with the most inconsistent results over time.
* General statistics. Compares the given run with the previous run on a number of aspects.
* Health barometer. Shows clickable placeholders to the flakiness overview, durations chart, and details table.

## Qualification

XL Test's qualification function allows for seamless integration with a continuous delivery pipeline. It allows for automatic analysis of the by test results that are available within XL Test. In this way, XL Test supports automated go/no go decision-making.

## Test job execution

XL Test also enables you to execute tests.
