---
layout: beta
title: Create a custom report in XL Test
categories:
- xl-test
subject:
- Reports
tags:
- report
- script
- python
- json
- freemarker
---

XL Test generates reports using:

* Python scripts for reports based on [Highcharts](http://www.highcharts.com/)
* FreeMarker scripts for reports based on HTML 

## Create a new custom report

Creating a custom report involves two steps:

1. Configuring the report in `synthetic.xml`
1. Writing a script for the report

The easiest way to start creating a custom report is to copy a built-in report. This example shows how to create a custom Highcharts report based on the built-in bar chart report.

### Configure the report in `synthetic.xml`

First, add a custom report type to `<XLTEST_HOME>\ext\synthetic.xml`:

1. Copy a `type` element with attribute `type="xltest.Barchart"` from `<XLTEST_HOME>\plugins\demo\synthetic.xml`:

        <type type="xltest.BarChart" extends="generic.Report">
            <property name="title" default="Bar chart"/>
            <property name="scriptLocation" default="reports/BarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="userFriendlyDescription" default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>
    
1. Change the `type` attribute to use your desired prefix and name; for example, `type="myCompany.myBarChart"`.
1. Change the `scriptLocation` to the report script that you will create; for example, `MyBarChart.py`.

    The result will look like:

        <type type="myCompany.myBarChart" extends="generic.Report">
            <property name="title" default="My Bar Chart"/>
            <property name="scriptLocation" default="reports/MyBarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="userFriendlyDescription"
                  default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

1. Save `synthetic.xml` and restart XL Test. All changes made to `synthetic.xml` require a restart.
1. To verify that your changes took effect, click `Test specifications` in the top menu, then click `Show report` for `demoFitnesse`. It should show another bar chart report (in our case, `My Bar Chart`):

    ![demoFitnesse reports with new custom report](images/report-list-with-custom-report.png)

## Write the report script

Next, copy `<XLTEST_HOME>\plugins\demo\reports\BarChart.py` to `reports/MyBarChart.py` in `ext`. Changes to the script do not require you to restart XL Test.

The Python script ends with `resultHolder.setResult( <json> )`. This is because Highcharts expects a JSON structure, so that is the output of the report script. The JSON structure is created as a Python dictionary.

Refer to the [Highcharts API](http://api.highcharts.com/highcharts) to see the options that are available for configuration.

This example shows how you can change the color of the bars in the bar chart. To do so, add a `colors` key to the JSON structure. Add this line after the closing curly bracket of `charts`:

    'colors': {'#7cb5ec', '#434348', '#90ed7d'},

`colors` should be placed between `charts` and `title`, so it looks like this:

    'chart': {
       'type': 'column'
    },
    'colors': ['#7cb5ec', '#434348', '#90ed7d'],
    'title': {
        'text': title
    },

**Note:** This structure looks similar to JSON, but because this is a Python script, it is actually a `Dictionary` (or `Map` in other languages). This means that the keys should be surrounded by single quotation marks (`'`). If you copy examples from a Highcharts demo, you must adjust the keys accordingly. For an extended example, refer to [Create a custom report using Highcharts demos](create-a-custom-report-using-highcharts-demos.html).

To see your changes in effect, open the `My Bar Chart` report for `demoFitnesse`.

This is the original bar chart:

![image](images/bar-chart-original.png)

This is the bar chart with the new colors:

![image](images/bar-chart-modified-color.png)
