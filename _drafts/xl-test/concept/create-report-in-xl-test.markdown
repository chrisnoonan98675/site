---
layout: beta
title: Creating reports
categories:
- xl-test
subject:
- Report
tags:
- test results
- reports
- test specification
---

In XL Test it's easy to create custom reports. This document describes the steps to be taken to create a custom report.

A report consists of 3 parts:

 1. Add a report entry in the `synthetic.xml` file
 2. A report generation script
 3. A HTML component responsible for rendering the report in the web browser

Refer to [Create a custom report in XL Test](/xl-test/how-to/create-a-custom-report-in-xl-test.html) to learn on how to create a custom report.

## Add a report entry

Custom reports should be registered in `<XLTEST_HOME>/ext/synthetic.xml`.

This is the type definition of the default bar chart:

        <type type="xltest.BarChart" extends="xltest.Report">
            <property name="title" default="Bar chart"/>
            <property name="userFriendlyDescription" default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="scriptLocation" default="reports/BarChart.py"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

The `type` attribute (`xltest.BarChart`) can be changed to a custom name. A type name consists of a name space (`xltest`) and a type name (`BarChart`). The namespace `xltest` is reserved for XL Test internal reports. It is recommended to use a custom namespace, e.g. `custom` or the company name. All reports should extend the `xltest.Report` type.

A report has the following properties:

 * `title`: report title, displayed in the Show report dialog on the project page
 * `userFriendlyDescription`: a description shown alongside the chart in the project page.
 * `iconName`: an icon name. The name should refer to an SVG icon `<XLTEST_HOME>/ext/web/images/sprite-icons/<iconName>.svg`.
 * `scriptLocation`: location of the Python script containing the report generation logic. See section [Report generation script](#report-generation-type).
 *   * `reportType`: The report type gives the web frontend a hint on how to render the report. See section [HTML component](#html-component).

## Report generation script

Report generation scripts are created in Python. Custom scripts are placed in the folder `<XLTEST_HOME>ext/<scriptLocation>`.

A report can be any data structure, as long as it can serialize to JSON. This includes dictionaries (hash-map), lists, string, boolean, integers and floating point numbers.

The general structure of a script is:

 1. Calculate data to report, based on test run events.
 2. Pass the generated data to the `resultHolder`.

In code:
 
    n = len(testRun.events) # let's say there are 42 events in this test run
    resultHolder.setResult({ 'numberOfTests': n })

This short example will determine the number of test run events associated with this test run and return it in a JSON object as:

    { numberOfTests: 42 }
    
In a Python script, the following properties are available:

 * `testRun` (object): the current test run / the most recent test run given a time span.
 * `testRuns` (object): the test runs repository, used to obtain other test runs.
 * `queryParameters` (dictionary): All query (URL) parameters provided.
 * `startDate` (integer): value of the URL parameter "startDate". Defaults to two weeks ago.
 * `endDate` (integer): value of the URL parameter "endDate". Defaults to the current time.
 * `tags`(list of strings): value of the URL parameter "tags". Defaults to an empty list. 

### TestRun

The TestRun object has the following public properties:

 * `id` (string): unique id of the test run.
 * `testSpecificationName` (string): ID of the associated test specification.
 * `finished` (boolean): `True` if the test run has finished.
 * `startTime` (date): start time of the test run.
 * `finishedTime` (date): finish time of the test run.
 * `qualificationResult` (boolean): qualification (passed/failed) for this test run.
 * `failureReason` (string): reason for failure.
 * `events` (list of events): Test run events associated with this test run.
 * `getEvents(filterParameters)` (list of events): Test run events associated with this test run, only the events that match filter `filterParameters` are returned.
 * `hasParameter(parameterName)` (string): return `True` if a parameter exists.
 * `getParameter(parameterName)` (string): obtain the parameter value, or `None` if it does not exist.
 * `getParameters()` (dictionary): get all parameters as a dictionary.

The test run parameters are restricted to any parameter that was provided as part of the execution or import process. Parameters of individual test results are not taken into account. 

NB.: The date objects are actually `java.util.Date` objects.

### TestRuns

The `testRuns` object supports the following methods. Those methods range from finding a particular event up to getting a list of test runs.

Test run related API's:

 * `getTestRun(testRunId)` (test run): get a specific test run based on a run id. The run id can be either a string or a `java.util.UUID`.
 * `getLatestTestRun(testSpecificationName, startTime, endTime)` (test run): get the most recent test run given a test specification id and in a time slot.
 * `getLatestTestRun(testSpecificationName, startTime)` (test run): Get the most recent test run given a test specification and a start date (till now).

 * `getTestRunsBetween(specificationName, startTime, endTime)` (list of test runs): Get all test runs for a specific test specification id in a particular time period.
 * `getTestRuns(eventProperties, startTime, endTime)` (list of test runs): Find all test runs that match `eventsProperties` in a given time period.
 * `getPreviousRuns(testRun, max)` (list of test runs): Get up to `max` test runs performed before `testRun`.
 * `getPreviousRuns(testSpecificationName, max)` (list of test runs): Get up to `max` latest test runs for a test specification.
 * `getLaterRuns(testRun, max)` (list of test runs): Get up to `max` test runs performed after `testRun`.

Test result event related API's:

 * `getLatestEventOfType(testSpecificationName, eventType, startTime, endTime)` (Event): get the most recent event of a particular time, given a time period.
 * `getLatestEventOfTypeAndRunId(testSpecificationName, eventType, runId, startTime, endTime)` (event): get the most recent event of a particular event type, given a test run and a time period. `runId` must be a `java.util.UUID`.
 * `getLatestEventWithProperties(eventProperties)` (events): get the most recent event given a set of properties. This is a more generic version of the before mentioned API's.

 * `getEventsBetween(startTime, endTime, eventProperties)` (list of events): obtain a list of events given a time period. Note that the events can be queried over test specifications.


## HTML component

The HTML component is responsible for rendering the generated report in the web browser. Standard examples include:

 * rendering a Highchart chart (reportType = highchart)
 * rendering plain HTML (reportType = html)

Any (AngularJS) formatted HTML snippet can be used as render template. Templates have to be located in a folder `<XLTEST_HOME>/ext/web/reports/<reportType>.html`. `reportType` matches the report type property defined in the report definition.

To show a report in a tile on a dashboard, a similar approach is used, only the report template is named `<XLTEST_HOME>/ext/web/reports/tiles/<reportType>.html`.

A report template has the following properties:

 * `report`: the report as provided to the `resultHolder` in the Python script.

