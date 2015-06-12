---
layout: beta
title: Custom reports in XL TestView
categories:
- xl-test
subject:
- Reports
tags:
- test results
- report
- test specification
---

You can easily create custom reports in XL TestView. This topic provides background information about custom reports. For a step-by-step example, refer to [Create a custom report](/xl-testview/how-to/create-a-custom-report.html).

This document describes the steps to be taken to create a custom report.

A report has three parts:

* A report entry in the `synthetic.xml` file
* A report generation script written in Python
* An HTML component responsible for rendering the report in the web browser

## Report entry in `synthetic.xml`

Custom reports are registered in `<XLTESTVIEW_HOME>/ext/synthetic.xml`.

For example, this is the type definition of the default bar chart:

    <type type="xlt.BarChart" extends="xlt.Report">
        <property name="title" default="Bar chart"/>
        <property name="scriptLocation" default="reports/BarChart.py"/>
        <property name="iconName" default="bar-report-icon"/>
        <property name="userFriendlyDescription" default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
        <property name="reportType" hidden="true" default="highchart"/>
    </type>

The `type` attribute (`xlt.BarChart`) should be changed to a custom name. A type name consists of a namespace (`xlt`) and a type name (`BarChart`). The namespace `xlt` is reserved for XL TestView internal reports. It is recommended to use a custom namespace; for example, `custom` or your company name. All reports should extend the `xlt.Report` type.

### Report properties

A report has the following properties in `synthetic.xml`:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `title` | Report title that appears in the **Show report** dialog on the project page |
| `userFriendlyDescription` | Description that appears next to the chart on the project page |
| `iconName` | An icon name that refers to an SVG icon in `<XLTESTVIEW_HOME>/ext/web/images/sprite-icons/<iconName>.svg` |
| `scriptLocation` | Location of the Python script containing the report generation logic |
| `reportType` | Report type that instructs the web front end how to render the report |

## Report script

Report generation scripts are created in Python. Custom scripts are placed in `<XLTESTVIEW_HOME>/ext/<scriptLocation>`.

A report can have any data structure that can serialize to JSON. This includes dictionaries (hash-map), lists, string, Boolean, integers, and floating point numbers.

The general structure of a script is:

1. Retrieve data and/or events
1. Calculate data to report, based on test run events
1. Pass the generated data to the `resultHolder`

### Sample script

This is a short example of a script:
 
    n = len(testRun.events) # let's say there are 42 events in this test run
    resultHolder.setResult({ 'numberOfTests': n })

This short example will determine the number of test run events associated with this test run and return it in a JSON object as:

    { numberOfTests: 42 }

### Python script properties

In a Python script, the following properties are available:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `testRun` | object | The current test run / the most recent test run given a time span (see [TestRun object properties](#testrun-object-properties)) |
| `testRuns` | object | The test runs repository, used to obtain other test runs (see [TestRuns object properties](#testruns-object-properties)) |
| `queryParameters` | dictionary | All query (URL) parameters provided
| `startDate` | integer | Value of the URL parameter `startDate`; defaults to two weeks ago |
| `endDate` | integer | Value of the URL parameter `endDate`; defaults to the current time |
| `tags` | list of strings | Value of the URL parameter `tags`; defaults to an empty list |

### TestRun object properties

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

### TestRuns object properties

The `testRuns` object supports the methods below. Those methods range from finding a particular event up to getting a list of test runs.

#### APIs related to test runs

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

#### APIs related to test result events 

APIs related to test result events:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
| `getLatestEventOfType(testSpecificationName, eventType, startTime, endTime)` | event | Get the most recent event of a particular time, given a time period |
| `getLatestEventOfTypeAndRunId(testSpecificationName, eventType, runId, startTime, endTime)` | event | Get the most recent event of a particular event type, given a test run and a time period; `runId` must be a `java.util.UUID` |
| `getLatestEventWithProperties(eventProperties)` | events | Get the most recent event given a set of properties; this is a more generic version of the APIs above |
| `getEventsBetween(startTime, endTime, eventProperties)` | list of events | Obtain a list of events given a time period; note that the events can be queried over test specifications |

## HTML component

The HTML component is responsible for rendering the generated report in the web browser. The `reportType` in `synthetic.xml` identifies the report type:

* `highchart`: Renders a [Highcharts](http://highcharts.com) chart
* `table`: Renders tabular data
* `html`: Renders HTML, allowing you to publish to a custom template

### Charts

The `highchart` type renders a [Highcharts](http://highcharts.com) chart. XL TestView uses Highcharts to visualize data in the bar, line, and pie charts.

For those reports, the server-side report script produces a Highcharts data structure. This data structure is passed directly to Highcharts for rendering. This allows the report to set the data series, chart type, and even color.

For information about creating charts, refer to the [Highcharts API documentation](http://api.highcharts.com/highcharts). For a step-by-step example of using Highcharts demos to create a report in XL TestView, refer to [Create a custom report using Highcharts demos](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html)

### Tabular data

The `table` type passes tabular data to the front end in table format, including header and body contents.

The following example of tabular data is a simplified version of the [test run overview](/xl-testview/concept/reports.html#test-run-overview). The numbers in parentheses refer to notes below the example.

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

This table has three columns, as described in the header field (note `2`):

* Qualification field (will show a qualification icon)
* Start date
* Duration field

XL TestView supports these three types. Any other type will be rendered as-is.

The start date field is rendered as a link (note `1`). The qualification result and duration are rendered as normal field values. The body contents (note `3`) are rendered with a single [list comprehension](https://docs.python.org/2/tutorial/datastructures.html#list-comprehensions).

### Custom HTML

The `html` type produces a string of HTML. A trivial example is:

    resultHolder.setResult('''
    <p>This is my custom report.</p>
    ''')

As an advanced feature, you can use any [AngularJS](https://angularjs.org/)-formatted HTML snippet as a render template. Templates must be located in `<XLTESTVIEW_HOME>/ext/web/reports/<reportType>.html`, where `<reportType>` matches the `reportType` defined in `synthetic.xml`.

XL TestView includes the following default templates:

* `highchart.html`
* `html.html`
* `link.html`
*  `noreport.html`
*  `qualification.html`

To show a report in a tile on a dashboard, a similar approach is used, but the report template is named `<XLTESTVIEW_HOME>/ext/web/reports/tiles/<reportType>.html`.

A report template has the following properties:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
|`report`  | object | The report as provided to the `resultHolder` in the Python script |
