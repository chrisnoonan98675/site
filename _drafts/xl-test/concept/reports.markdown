---
title: Reports
categories:
- xl-test
subject:
- Reporting
tags:
- Reports
---


## Reports
A report is a representation of a set of test results in a graph or table. There are two types of test results: **Functional tests** and **Performance tests**. The following reports are available by default:

### Bar charts
* Type: Functional
* Span: Single testrun

Shows a bar chart with passed and failed tests for the most recent testrun of the selected date. Clicking on a bar will display the results in that section of the test results.

### Details
* Type: Functional
* Span: Single testrun

Shows passed and failed test runs, like the bar chart, but in a tabular format.

### Durations
* Type: Functional
* Span: Multiple testruns

Plots a graph of the test run durations over a specific time span.

### Flakiness overview
* Type: Functional
* Span: Multiple testruns

Shows how (un)stable tests are over time. Given a time range, the most flaky tests are shown.

### General statistics
* Type: Functional
* Span: Two testruns

Shows the improvement/degradation between two test runs.


### Health barometer
* Type:Functional
* Span: Mixed

This is a combined report with a flakiness overview, durations and a pie chart.

### Load test trends
* Type: Performance
* Span: Multiple testruns

Provides a duration chart for load test results.

### Pie chart
* Type: Functional
* Span: Single testrun

Shows passed and failed test runs, as a pie chart.