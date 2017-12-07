---
no_mini_toc: true
title: XL TestView reports
categories:
- xl-testview
subject:
- Reports
tags:
- report
- dashboard
weight: 730
---

A report is a representation of a set of test results in a graph or table. XL TestView supports *functional test results* and *performance test results*. These are the types of reports that XL TestView supports by default.

## Aggregated bar chart

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-bar-chart.svg" alt="XL TestView bar chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Latest test run in time span per test specification in a test specification set</p>
<p>This report aggregates the functional results of the test specifications in a test specification set. It shows the latest test run in the selected time window. Click the graph to see results on a different level of the test hierarchy.</p>
<p>Notes:
<ul>
    <li>If the set contains performance results these are ignored, so it is not necessary to introduce extra test specifications sets for reporting.</li>
    <li>This report is intended to be used with test specification sets that do not have overlapping test results (e.g. test results with the same hierarchy coming from different test specifications in the set).</li>
</ul>
</p>
</div>
</div>

## Bar chart

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-bar-chart.svg" alt="XL TestView bar chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Single test run</p>
<p>A bar chart report shows a bar chart with passed and failed tests for the most recent test run of the selected date. Click a bar to see the results in that section of the test results.</p>
</div>
</div>

## Details

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-details.svg" alt="XL TestView details report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Single test run</p>
<p>A details report shows passed and failed test runs in a tabular format.</p>
</div>
</div>

## Diff bar chart

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-bar-chart.svg" alt="XL TestView diff bar chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Two test runs</p>
<p>A bar chart report that shows the tests that changed state since the last execution; that is, passing tests shown failed during the previous execution and failing tests shown passed during the previous execution. Click a bar to see the results in that section of the test results.</p>
</div>
</div>

## Durations

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-duration.svg" alt="XL TestView durations report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Multiple test runs</p>
<p>A durations report plots a graph of the test run durations over a specific time span. Clicking an individual line of the duration reports drills down into the durations of its constituents.</p>
</div>
</div>

## Flakiness overview

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-flakiness.svg" alt="XL TestView flakiness icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Multiple test runs</p>
<p>A flakiness overview report shows how stable or unstable tests are over time. Given a time range, the most flaky tests are shown. Flakiness is calculated as the actual number of changes from passed to failed or from failed to passed versus the possible number of changes, given the number of test runs visible in the date/time range selected.</p>
</div>
</div>

## General statistics

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-general.svg" alt="XL TestView general statistics report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Two test runs</p>
<p>A general statistics report shows the improvement or degradation between two test runs.</p>
</div>
</div>

## Health barometer

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-health-barometer.svg" alt="XL TestView health barometer report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Mixed</p>
<p>The health barometer report combines a flakiness overview report, a duration report, and a pie chart report. Clicking the flakiness overview opens the detailed flakiness report. Clicking the duration opens the detailed durations report.</p>
</div>
</div>

## Historical bar chart

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-bar-chart.svg" alt="XL TestView bar chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Multiple test runs</p>
<p>This report presents the aggregated functional results of a test specification per execution of the test specification in the time window. The duration of the tests and the total build time is displayed as well.</p>
<p>Note: Build durations are only available when using Jenkins plugin 1.2.0 and higher combined with XL TestView 1.4.4 and higher.
</div>
</div>

## Historical comparison details

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-historical-comparison-details.svg" alt="XL TestView historical comparison details report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Two test runs</p>
<p>A details report shows test runs that changed state between the last two executions in a tabular format.</p>
</div>
</div>


## Load test trends

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-duration.svg" alt="XL TestView load test report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Performance</p>
<p>Span: Multiple test runs</p>
<p>A load test trends report provides a duration chart for load test results.</p>
</div>
</div>

## Pie chart

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-pie-chart.svg" alt="XL TestView pie chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Functional</p>
<p>Span: Single test run</p>
<p>A pie chart report shows passed and failed test runs as a pie chart.</p>
</div>
</div>

## Test run overview

<div class="row">
<div class="col-md-2">
<img src="images/icon-report-testruns-report.svg" alt="XL TestView pie chart report icon" width="100">
</div>
<div class="col-md-10">
<p>Type: Generic</p>
<p>Span: Multiple test runs</p>
<p>An overview showing the available test runs over a period of time. Clicking a test run provides a detailed overview of the test results in XL TestView's event structure.
</div>
</div>
