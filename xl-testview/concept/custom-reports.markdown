---
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

You can easily create custom reports in XL TestView. This topic provides background information about custom reports. For a step-by-step example, refer to [Create a custom report based on a built-in report](/xl-testview/how-to/create-a-custom-report.html).

This document describes the steps to be taken to create a custom report.

A report has three parts:

* A report entry in the `synthetic.xml` file
* A report generation script written in Python
* An HTML component responsible for rendering the report in the web browser

## Report entry in `synthetic.xml`

Custom reports are registered in `<XLTESTVIEW_HOME>/ext/synthetic.xml`.

For example, this is the type definition of the default bar chart in `synthetic.xml`:

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
| `iconName` | A name that refers to an SVG icon in `<XLTESTVIEW_HOME>/ext/web/images/sprite-icons/<iconName>.svg` |
| `scriptLocation` | Location of the Python script containing the report generation logic |
| `reportType` | Report type that instructs the front end how to render the report |

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
| `test_run` | object | The current test run / the most recent test run given a time span (see [TestRun object](#testrun-object)) |
| `test_runs` | object | The test runs repository, used to obtain other test runs (see [TestRuns object](#testruns-object)) |
| `query_parameters` | dictionary | All query (URL) parameters provided
| `start_date` | integer | Value of the URL parameter `startDate`; defaults to two weeks ago |
| `end_date` | integer | Value of the URL parameter `endDate`; defaults to the current time |
| `tags` | list of strings | Value of the URL parameter `tags`; defaults to an empty list |

### TestRun object

The `test_run` object refers to the test run on which the report should be based. 

The methods available in the `test_run` object are available in the [Java API documentation](https://docs.xebialabs.com/generated/xl-testview/1.3.x/javadoc/com/xebialabs/xlt/plugin/api/testrun/TestRun.html).

### TestRuns object

The `test_runs` object is used to retrieve (older) test runs from the repository. Its methods range from finding a particular event up to getting a list of test runs.

The methods available in the `test_runs` object are available in the [Java API documentation](https://docs.xebialabs.com/generated/xl-testview/1.3.x/javadoc/com/xebialabs/xlt/plugin/api/testrun/TestRunsRepository.html).

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
	
	runs = test_runs.getTestRunsBetween(testRun.testSpecificationName, startDate.getTime(), endDate.getTime())

	def rowValues(run):
	    return [ run.qualificationResult,
	             { 'v': run.startTime.time,
	               'ref': REF_TMPL % (run.testSpecificationName, run.testRunId) },   ## (1)
	             run.finished and (run.finishedTime.time - run.startTime.time) or 'in progress']

	result_holder.result = {
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
	}

This table has three columns, as described in the header field (note `2`):

* Qualification field (will show a qualification icon)
* Start date
* Duration field

XL TestView supports these three types. Any other type will be rendered as-is.

The start date field is rendered as a link (note `1`). The qualification result and duration are rendered as normal field values. The body contents (note `3`) are rendered with a single [list comprehension](https://docs.python.org/2/tutorial/datastructures.html#list-comprehensions).

### Custom HTML

The `html` type produces a string of HTML. A trivial example is:

    result_holder.result = '''
    <p>This is my custom report.</p>
    '''

As an advanced feature, you can use any [AngularJS](https://angularjs.org/)-formatted HTML snippet as a render template. Templates must be located in `<XLTESTVIEW_HOME>/ext/web/reports/<reportType>.html`, where `<reportType>` matches the `reportType` defined in `synthetic.xml`.

XL TestView includes the following default templates:

* `highchart.html`
* `html.html`
* `link.html`
* `qualification.html`

To show a report in a tile on a dashboard, a similar approach is used, but the report template is named `<XLTESTVIEW_HOME>/ext/web/reports/tiles/<reportType>.html`.

A report template has the following properties:

{:.table .table-striped}
| Property | Type | Description |
| -------- | ---- | ----------- |
|`report`  | object | The report as provided to the `result_holder` in the Python script |
