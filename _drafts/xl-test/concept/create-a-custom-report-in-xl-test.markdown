---
layout: beta
title: Create a custom report in XL Test
categories:
- xl-test
subject:
- Report
tags:
- test results
- reports
- test specification
---

In XL Test, you can easily create custom reports. This document describes the steps to be taken to create a custom report.

A report has three parts:

* Add a report entry in the `synthetic.xml` file
* A report generation script
* An HTML component responsible for rendering the report in the web browser

Refer to [Create a custom report in XL Test](/xl-test/how-to/create-a-custom-report-in-xl-test.html) for information about creating a custom report.

## Add a report entry

Custom reports should be registered in `<XLTESTVIEW_HOME>/ext/synthetic.xml`.

This is the type definition of the default bar chart:

    <type type="xltest.BarChart" extends="xltest.Report">
        <property name="title" default="Bar chart"/>
        <property name="userFriendlyDescription" default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
        <property name="iconName" default="bar-report-icon"/>
        <property name="scriptLocation" default="reports/BarChart.py"/>
        <property name="reportType" hidden="true" default="highchart"/>
    </type>

The `type` attribute (`xltest.BarChart`) can be changed to a custom name. A type name consists of a name space (`xltest`) and a type name (`BarChart`). The namespace `xltest` is reserved for XL Test internal reports. It is recommended to use a custom namespace; for example, `custom` or your company name. All reports should extend the `xltest.Report` type.

A report has the following properties:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `title` | Report title that appears in the **Show report** dialog on the project page |
| `userFriendlyDescription` | Description that appears next to the chart on the project page |
| `iconName` | An icon name that refers to an SVG icon in `<XLTESTVIEW_HOME>/ext/web/images/sprite-icons/<iconName>.svg` |
| `scriptLocation` | Location of the Python script containing the report generation logic; see [Report generation script](#report-generation-type) |
| `reportType` | Report type that instructs the web front end how to render the report; see [HTML component](#html-component) |

## Report generation script

Report generation scripts are created in Python. Custom scripts are placed in the folder `<XLTESTVIEW_HOME>/ext/<scriptLocation>`.

A report can be any data structure, as long as it can serialize to JSON. This includes dictionaries (hash-map), lists, string, Boolean, integers and floating point numbers.

The general structure of a script is:

1. Calculate data to report, based on test run events.
2. Pass the generated data to the `resultHolder`.

In code:
 
    n = len(testRun.events) # let's say there are 42 events in this test run
    resultHolder.setResult({ 'numberOfTests': n })

This short example will determine the number of test run events associated with this test run and return it in a JSON object as:

    { numberOfTests: 42 }
    
In a Python script, the following properties are available:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `testRun` | object | The current test run / the most recent test run given a time span |
| `testRuns` | object | The test runs repository, used to obtain other test runs |
| `queryParameters` | dictionary | All query (URL) parameters provided
| `startDate` | integer | Value of the URL parameter `startDate`; defaults to two weeks ago |
| `endDate` | integer | Value of the URL parameter `endDate`; defaults to the current time |
| `tags` | list of strings | Value of the URL parameter `tags`; defaults to an empty list |

### TestRun

The TestRun object has the following public properties:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `id` | string | Unique ID of the test run |
| `testSpecificationName` | string | ID of the associated test specification |
| `finished` | boolean | `True` if the test run has finished |
| `startTime` | date | Start time of the test run |
| `finishedTime` | date | Finish time of the test run |
| `qualificationResult` | boolean | Qualification (passed/failed) for this test run |
| `failureReason` | string | Reason for failure |
| `events` | list of events | Test run events associated with this test run |
| `getEvents(filterParameters)` | list of events | Test run events associated with this test run, only the events that match filter `filterParameters` are returned |
| `hasParameter(parameterName)` | string | Returns `True` if a parameter exists |
| `getParameter(parameterName)` | string | Obtain the parameter value, or `None` if it does not exist |
| `getParameters()` | dictionary | Get all parameters as a dictionary |

The test run parameters are restricted to any parameter that was provided as part of the execution or import process. Parameters of individual test results are not taken into account. 

**Note:** The date objects are actually `java.util.Date` objects.

### TestRuns

The `testRuns` object supports the methods below. Those methods range from finding a particular event up to getting a list of test runs.

APIs related to test runs:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `getTestRun(testRunId)` | test run | Get a specific test run based on a run ID, which can be a string or a `java.util.UUID` |
| `getLatestTestRun(testSpecificationName, startTime, endTime)` | test run | Get the most recent test run given a test specification ID and in a time slot |
| `getLatestTestRun(testSpecificationName, startTime)` | test run | Get the most recent test run given a test specification and a start date (until now) |
| `getTestRunsBetween(specificationName, startTime, endTime)` | list of test runs | Get all test runs for a specific test specification ID in a particular time period |
| `getTestRuns(eventProperties, startTime, endTime)` | list of test runs |  Find all test runs that match `eventsProperties` in a given time period |
| `getPreviousRuns(testRun, max)` | list of test runs | Get up to `max` test runs performed before `testRun` |
| `getPreviousRuns(testSpecificationName, max)` | list of test runs | Get up to `max` latest test runs for a test specification |
| `getLaterRuns(testRun, max)` | list of test runs | Get up to `max` test runs performed after `testRun` |

APIs related to test result events:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `getLatestEventOfType(testSpecificationName, eventType, startTime, endTime)` | event | Get the most recent event of a particular time, given a time period |
| `getLatestEventOfTypeAndRunId(testSpecificationName, eventType, runId, startTime, endTime)` | event | Get the most recent event of a particular event type, given a test run and a time period; `runId` must be a `java.util.UUID` |
| `getLatestEventWithProperties(eventProperties)` | events | Get the most recent event given a set of properties; this is a more generic version of the APIs above |
| `getEventsBetween(startTime, endTime, eventProperties)` | list of events | Obtain a list of events given a time period; note that the events can be queried over test specifications |

## HTML component

The HTML component is responsible for rendering the generated report in the web browser. The following report types are available by default:

 * rendering a Highchart chart (reportType = highchart)
 * tabular data (reportType = table)
 * rendering plain HTML (reportType = html)

### Charts

For charts (bar, line, pie), XL Test is using [Highcharts](http://highcharts.com). Report like the Bar chart and Pie chart use this library to visualize th data. For those reports, the Report (server side) script produces a Highcharts data structure. This data structure is basically passed on 1:1 to Highcharts for rendering. This allows the report to set the data series, chart type, and even color. The how-to document on [creating a custom report](/xl-test/how-to/create-a-custom-report-in-xl-test.html) shows an example of how to get started.

For documentation on configuring charts please refer to the [Highcharts API documentation](http://api.highcharts.com/highcharts).

By default, charts have a `reportType` `highchart`.

### Tabular data

Tabular data is passed to the front-end as a table format, including header and body contents. The following code is a simplified version of the test runs report.

	REF_TMPL = '#/testspecifications/%s/report/xltest.TestRunEvents?runId=%s'
	
	runs = testRuns.getTestRunsBetween(testRun.testSpecificationName, startDate.getTime(), endDate.getTime())

	def rowValues(run):
	    return [ run.qualificationResult,
	             { 'v': run.startTime.time,
	               'ref': REF_TMPL % (run.testSpecificationName, run.testRunId) },   ## (1)
	             run.finished and (run.finishedTime.time - run.startTime.time) or 'in progress']

	resultHolder.setResult({
	    'title':'Test runs',
	    'description': 'The test runs performed in the period defined.',
	    'header': [                                                                  ## (2)
	        { 'name': '', 'kind': 'qualification'},
	        { 'name': 'Start date', 'kind': 'date'},
	        { 'name': 'Duration (s)', 'kind': 'duration'}
	    ],
	    'body': [                                                                    ## (3)
	        rowValues(run) for run in runs
	    ]
	})

This table has 3 colums as described in the header field (_2_): a qualification field (will show a qualification icon), a start date and a duration field. These are the different types that are supported. Any other type will just be rendered as is.

The Start date field is rendered as a link (_1_). The qualification result ad duration is rendered as normal field values.

The body contents (_3_) is rendered with a single [list comprehension](https://docs.python.org/2/tutorial/datastructures.html#list-comprehensions).

### Plain HTML

Plain HTML reports simply produce a string of HTML. A trivial example would look like this:

    resultHolder.setResult('''
    <p>Look mom! A report!</p>
    ''')
    
### Custom HTML templates

Any (AngularJS) formatted HTML snippet can be used as render template. Templates have to be located in a folder `<XLTESTVIEW_HOME>/ext/web/reports/<reportType>.html`. `reportType` matches the report type property defined in the report definition.

To show a report in a tile on a dashboard, a similar approach is used, only the report template is named `<XLTESTVIEW_HOME>/ext/web/reports/tiles/<reportType>.html`.

A report template has the following properties:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
|`report`  | object | The report as provided to the `resultHolder` in the Python script |
