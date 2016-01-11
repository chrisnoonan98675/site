---
title: Create a custom report based on a built-in report
categories:
- xl-testview
subject:
- Reports
tags:
- report
- extension
---

XL TestView generates reports using:

* Python scripts for reports based on [Highcharts](http://www.highcharts.com/)
* Python or FreeMarker scripts for reports based on HTML 

The easiest way to create a custom XL TestView report is to copy a built-in report. This topic shows how to create a custom Highcharts report based on the built-in [bar chart](/xl-testview/concept/reports.html#bar-chart) report.

You can also create a custom report from scratch using [demos provided by Highcharts](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).

For detailed technical information about custom reports, refer to [Custom reports in XL TestView](/xl-testview/concept/custom-reports.html).

## Configure the report in `synthetic.xml`

First, add a custom report type to `<XLTESTVIEW_HOME>/ext/synthetic.xml`:

1. Copy a `type` element with the `type="xlt.Barchart"` attribute from `<XLTESTVIEW_HOME>/plugins/reports/synthetic.xml`:

        <type type="xlt.BarChart" extends="xlt.Report" label="Bar chart" description="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format">
            <property name="scriptLocation" default="reports/BarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

1. Change the `type` attribute to use your desired prefix and name; for example, `type="myCompany.myBarChart"`.
1. Change the `scriptLocation` to the report script that you will create; for example, `MyBarChart.py`.

    The result will look like:
    
        <type type="myCompany.myBarChart" extends="xlt.Report" label="My Bar Chart" description="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format">
            <property name="scriptLocation" default="reports/MyBarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

1. Save `synthetic.xml` and [restart](/xl-testview/how-to/start.html) XL TestView.
1. All changes made to `synthetic.xml` require you to restart.
1. To verify that your changes took effect, click **Projects** in the top menu and select the demo project. Next to *functionalTestsComponentA*, click **Show report**. It should show the *My Bar Chart* report:

    ![Report list with new custom report](images/report-list-with-custom-report.png)

## Write the report script

Next, copy `<XLTESTVIEW_HOME>/plugins/reports/reports/BarChart.py` to `<XLTESTVIEW_HOME>/ext/reports/MyBarChart.py`. You do not need to restart XL TestView after changing the script.

The Python script ends with `result_holder.result = <json>`. This is because Highcharts expects a JSON structure, so that is the output of the report script. The JSON structure is created as a Python dictionary.

Refer to the [Highcharts API](http://api.highcharts.com/highcharts) to see the options that are available for configuration.

### Sample script customization

This example shows how you can change the color of the bars in the bar chart. To do so, add a `colors` key to the JSON structure. Add this line after the closing curly bracket of `charts`:

{% highlight python %}
    'colors': {'#7cb5ec', '#434348', '#90ed7d'},
{% endhighlight %}

`colors` should be placed between `charts` and `title`, so it looks like this:

{% highlight python %}
    'chart': {
       'type': 'column'
    },
    'colors': ['#7cb5ec', '#434348', '#90ed7d'],
    'title': {
        'text': title
    },
{% endhighlight %}

**Note:** This structure looks similar to JSON, but because this is a Python script, it is actually a `Dictionary` (or `Map` in other languages). This means that the keys should be surrounded by quotation marks (`'` or `"`). If you copy examples from a Highcharts demo, you must adjust the keys accordingly. For an extended example, refer to [Create a custom report using Highcharts demos](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).

To see your changes in effect, open the `My Bar Chart` report for one of the sample test specifications.

This is the original bar chart:

![image](images/bar-chart-original.png)

This is the bar chart with the new colors:

![image](images/bar-chart-modified-color.png)
