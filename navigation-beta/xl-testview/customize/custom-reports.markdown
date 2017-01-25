---
no_index: true
title: Create custom reports
---

XL TestView generates reports using:

* Python scripts for reports based on [Highcharts](http://www.highcharts.com/)
* Python or FreeMarker scripts for reports based on HTML

## Create a custom report based on a built-in report

The easiest way to create a custom XL TestView report is to copy a built-in report. This topic shows how to create a custom Highcharts report based on the built-in [bar chart](/xl-testview/concept/reports.html#bar-chart) report.

You can also create a custom report from scratch using [demos provided by Highcharts](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).

For detailed technical information about custom reports, refer to [Custom reports in XL TestView](/xl-testview/concept/custom-reports.html).

### Configure the report in `synthetic.xml`

First, add a custom report type to `XL_TESTVIEW_HOME/ext/synthetic.xml`:

1. Copy a `type` element with the `type="xlt.Barchart"` attribute from `XL_TESTVIEW_HOME/plugins/reports/synthetic.xml`:

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

    ![Report list with new custom report](/xl-testview/how-to/images/report-list-with-custom-report.png)

### Write the report script

Next, copy `XL_TESTVIEW_HOME/plugins/reports/reports/BarChart.py` to `XL_TESTVIEW_HOME/ext/reports/MyBarChart.py`. You do not need to restart XL TestView after changing the script.

The Python script ends with `result_holder.result = <json>`. This is because Highcharts expects a JSON structure, so that is the output of the report script. The JSON structure is created as a Python dictionary.

Refer to the [Highcharts API](http://api.highcharts.com/highcharts) to see the options that are available for configuration.

#### Sample script customization

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

![image](/xl-testview/how-to/images/bar-chart-original.png)

This is the bar chart with the new colors:

![image](/xl-testview/how-to/images/bar-chart-modified-color.png)

## Create a custom report based on a Highcharts demo

For reports based on the [Highcharts](http://www.highcharts.com/) charting library, XL TestView uses Python scripts. You can use the [demos provided by Highcharts](http://www.highcharts.com/demo) as a basis for Python scripts for custom XL TestView reports.

Click **View Options** when viewing a Highcharts demo to get sample code in JSON. Copy this sample code to the Python script and add single quotation marks around all keys.

For example, this is the sample code from the [basic line chart demo](http://www.highcharts.com/demo/line-basic):

{% highlight JavaScript %}
$(function () {
    $('#container').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
});
{% endhighlight %}

### Copy JSON

First, copy the JSON from the `.highcharts()` method (line 2):

{% highlight python %}
{
    title: {
        text: 'Monthly Average Temperature',
        x: -20 //center
    },
    subtitle: {
        text: 'Source: WorldClimate.com',
        x: -20
    },
    xAxis: {
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
    yAxis: {
        title: {
            text: 'Temperature (°C)'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        valueSuffix: '°C'
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    series: [{
        name: 'Tokyo',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }, {
        name: 'New York',
        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
    }, {
        name: 'Berlin',
        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
    }, {
        name: 'London',
        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
}
{% endhighlight %}

### Change JSON to Python syntax

Then change the JSON to Python syntax:

* Place single quotation marks around all keys
* Replace any double forward slashes (`//`) with a number sign (`#`)
* Remove the `subtitle` key and value

This produces:

{% highlight python %}
{
    'title': {
        'text': 'Monthly Average Temperature',
        'x': -20 #center
    },
    'xAxis': {
        'categories': ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
    'yAxis': {
        'title': {
            'text': 'Temperature (°C)'
        },
        'plotLines': [{
            'value': 0,
            'width': 1,
            'color': '#808080'
        }]
    },
    'tooltip': {
        'valueSuffix': '°C'
    },
    'legend': {
        'layout': 'vertical',
        'align': 'right',
        'verticalAlign': 'middle',
        'borderWidth': 0
    },
    'series': [{
        'name': 'Tokyo',
        'data': [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }, {
        'name': 'New York',
        'data': [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
    }, {
        'name': 'Berlin',
        'data': [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
    }, {
        'name': 'London',
        'data': [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
}
{% endhighlight %}

### Update the report script

In your report Python script, replace the method call of `resultHolder.setResult()` with the code above. Your call will look like this:

{% highlight python %}
resultHolder.setResult({
    'title': {
        'text': 'Monthly Average Temperature',
        'x': -20 #center
    },
    'xAxis': {
        'categories': ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    },
    'yAxis': {
        'title': {
            'text': 'Temperature (°C)'
        },
        'plotLines': [{
            'value': 0,
            'width': 1,
            'color': '#808080'
        }]
    },
    'tooltip': {
        'valueSuffix': '°C'
    },
    'legend': {
        'layout': 'vertical',
        'align': 'right',
        'verticalAlign': 'middle',
        'borderWidth': 0
    },
    'series': [{
        'name': 'Tokyo',
        'data': [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }, {
        'name': 'New York',
        'data': [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
    }, {
        'name': 'Berlin',
        'data': [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
    }, {
        'name': 'London',
        'data': [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
})
{% endhighlight %}

The corresponding report will look like this:

![image](/xl-testview/how-to/images/using-highchart-demo-line-example.png)

Refer to [Create a custom report in XL TestView](/xl-testview/how-to/create-a-custom-report.html) for more information about creating and troubleshooting custom reports.

## Create a custom report for a test specification set

This topic describes how to create a custom report for test specification sets. The process is very similar to the process for [other custom reports](/xl-testview/how-to/create-a-custom-report.html).

For detailed technical information about custom reports, refer to [Custom reports in XL TestView](/xl-testview/concept/custom-reports.html).

### Configure the report in `synthetic.xml`

Add a custom report type to `XL_TESTVIEW_HOME/ext/synthetic.xml`:

1. Copy the entry for an existing report that is similar to the one you want to create. For example, for a bar chart:

        <type type="xlt.BarChart" extends="xlt.Report" label="Bar chart">
            <property name="title" default="Bar chart"/>
            <property name="scriptLocation" default="reports/BarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

2. Change the `type` attribute to your desired prefix and name; for example, `type="myCompany.myCustomSet"`.

3. Change the `scriptLocation` to the report script that you will create; for example, `MyCustomSetReport.py`.

4. Change the following properties:

    * `title`: Title that appears on the report screen
    * `label` (in the main type element): Label that appears in the dialog where you select the report
    * `description` (in the main type element): Description of the report

4. Add a property named `applicableCategories` with a `kind` of `set_of_string` and a value of `set`. This indicates that the report contains data about specification *sets*. For example:

        <property name="applicableCategories" kind="set_of_string" default="set"/>

    The result will look like:

        <type type="myCompany.myCustomSet" extends="xlt.Report" label="My Set Report" description="My set report that does things differently">
            <property name="title" default="My Set Report"/>
            <property name="scriptLocation" default="reports/MyCustomSetReport.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
	        <property name="applicableCategories" kind="set_of_string" default="set"/>
        </type>

5. Save the `synthetic.xml` file and [restart XL TestView](/xl-testview/how-to/start.html).

6. To verify that your changes took effect, click **Projects** in the top menu and select a project. On a *test specification*, click **Show report**. It should show the *My Set Report* report:

    ![Report list with new custom report](/xl-testview/how-to/images/create-a-custom-report-testset-reports.png)

### Write the report script

To write the report script, follow the process described in [Create a custom report](/xl-testview/how-to/create-a-custom-report.html). Note the following differences:

* Because you are going to report on a *test specification set*, there is no such thing as the *latest run* (`test_run`). Instead, you can access the `test_specification_set` you are reporting on.

* You can get *qualification* information from a test run. You can query a a `test_runs_repository` (which is the same as `test_runs` in non-set reports) and a `qualification_repository`.

#### Sample script

This sample script takes all test specifications of a set and counts the passed and failed qualifications. Then, it represents the values in a pie chart. To use this sample, save it as `MyCustomSetReport.py` in `ext/reports` (create this directory if it doesn't exist).

{% highlight python %}
from modules.hierarchy import *

# get all test specifications within this test spec
# note: this does not go recursively
testSpecs = test_specification_set.testSpecifications

# qualification statuses!
passed = 0
failed = 0

# iterate over all test specifications
for testSpec in testSpecs:
    qualification = qualification_repository.getLatestQualificationResult(testSpec.name)
    if qualification.get("result") == "PASSED":
        passed = passed + 1
    else:
        failed = failed + 1

result_holder.result = {
    'chart': {
        'type': 'pie',
        'plotBackgroundColor': None,
        'plotBorderWidth': None,
        'plotShadow': False
    },
    'title': 'Passed versus failed test specifications from their latest qualification',
    'description': 'This report presents the tests that passed and failed during the last execution of the test specification.',
    'tooltip': {
        'enabled': False
    },
    'legend': {
        'borderColor': None,
        'layout': 'vertical',
        'verticalAlign': 'middle',
        'symbolHeight': 12,
        'symbolWidth': 12,
        'symbolRadius': 6,
        'itemMarginBottom': 4
    },
    'plotOptions': {
        'series': {
            'slicedOffset': 0,
            'point': {
                'events': {
                    'click': 'url'
                }
            }
        },
        'pie': {
            'allowPointSelect': True,
            'dataLabels': {
                'enabled': True,
                'distance': -15,
                'format': '{y}'
            },
            'innerSize': '70%',
            'showInLegend': 'True',
            'animation': False,
            'states': {
                'hover': {
                    'enabled': False
                }
            }
        }
    },
    'series': [{
        'data': [{
            'name': "Passed ({0})".format(passed),
            'y': passed
        },
            {
                'name': "Failed ({0})".format(failed),
                'y': failed
            }
        ]
    }]
}
{% endhighlight %}

**Note:** While the value assigned to `result_holder.result` looks similar to JSON, this is in fact a Python `Dictionary` (or `Map` in other languages). This means that the keys should be surrounded by quotation marks (`'` or `"`). If you copy examples from a Highcharts demo, you must adjust the keys accordingly. For an extended example, refer to [Create a custom report using Highcharts demos](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).

The end result should look like:

![Report example](/xl-testview/how-to/images/create-a-custom-report-testset-reports_report_example.png)
