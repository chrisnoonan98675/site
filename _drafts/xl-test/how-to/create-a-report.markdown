---
title: Create a report
categories:
- xl-test
subject:
- Reports
tags:
- report
- script
- python
- Report
---
Currently xl-test generates reports using Python or Freemarker scripts. Pyton scripts are used for generating Highchart-based reports. Freemarker scrips are used for html based reports.

This how-to covers three topics:

1. [Creating a new customized report](#creating-a-new-customized-report)
2. [Trying out Highcharts examples](#trying-out-highcharts-examples)
3. [Troubleshooting](#troubleshooting)


## Creating a new customized report
Within these steps we take a Barchart as an example, but of course you can use any type of report.

Creating your own customized report is done in two steps:

1. [Configure your own report in synthetic.xml](#configure-your-own-report-in-synthetic.xml)
2. [Write your own script](#write-your-own-script)


### Configure your own report in synthetic.xml
You need to add your own Report type to the synthetic.xml. You can find this file in ```<XL-TEST-HOME>\plugins\demo\synthetic.xml```.

Copy a `type` element with attribute `type='xltest.Barchart'` and paste it, it will look like:

    <type type="xltest.BarChart" extends="generic.Report">
        <property name="title" default="Bar chart"/>
        <property name="scriptLocation" default="reports/BarChart.py"/>
        <property name="iconName" default="bar-report-icon"/>
        <property name="userFriendlyDescription" default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
        <property name="reportType" hidden="true" default="highchart"/>
    </type>
    
Change the attribute `type` into your own name. Use your own prefix for instance: `type='myCompany.myBarChart'`.

Now you can also change the other properties. Change the `scriptLocation` to point it to a new script file ([we will write that later](#write-your-own-script)). In this example we changed it to `MyBarChart.py`. For this example, the end result looks like:

    <type type="myCompany.myBarChart" extends="generic.Report">
        <property name="title" default="My Bar Chart"/>
        <property name="scriptLocation" default="reports/MyBarChart.py"/>
        <property name="iconName" default="bar-report-icon"/>
        <property name="userFriendlyDescription"
                  default="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format"/>
        <property name="reportType" hidden="true" default="highchart"/>
    </type>


After these changes, save the `synthetic.xml` file and restart xl-test. All changes made to the `synthetic.xml` require a restart.

To verify your changes took effect, click `Test Specifications` on the top menu. Then click `Show report` for `demoFitnesse`. It should have another Barchart report (in our case `My Bar Chart`):

![image](images/report-list-with-custom-report.png)


### Write your own script
Next we make a copy of the `BarChart.py` to `MyBarChart.py`. The path is relative to the `demo` folder so you can find it within `<XL-TEST-HOME>\plugins\demo\reports\BarChart.py`.

Any changes to the script do *not* require a restart of xl-test.

You will notice that the python script ends with `resultHolder.setResult( <json> )`. This is because highcharts expects a json structure so that is the output of the report script. Take a look at the [highcharts API](http://api.highcharts.com/highcharts) to know what you can configure.

In this example we change the color of the bars. To do this we need to add a `colors` key to the json structure. Add this line after the closing curly brace of `charts`:

```
'colors': {'#7cb5ec', '#434348', '#90ed7d'},
```

`colors` should be between `charts` and `title`, so it looks like this:

    'chart': {
       'type': 'column'
    },
    'colors': ['#7cb5ec', '#434348', '#90ed7d'],
    'title': {
        'text': title
    },
     
Note that the above may look like JSON but since we're writing a Python script this is actually a `Dictionary` (or Map for other languages). Meaning the keys should be within single quotes and you should change the demo examples from the Highcharts API to make them work. 

To see your changes in effect open your report for `demoFitnesse`, or refresh the report page if you already did that.

Original barchart:

![image](images/bar-chart-original.png)


The changed barchart:

![image](images/bar-chart-modified-color.png)


## Trying out Highcharts examples
When you're trying out the [Highcharts examples](http://www.highcharts.com/demo) you will get example code in JSON (click on 'View Options' while viewing a demo chart). Using the example is as simple as copying the applicable code in the Pyton script and surrounding all the keys with single quotes.

For example if you look at the [Line basic demo](http://www.highcharts.com/demo/line-basic) - it will produce the following code:

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

First we take out the JSON from the `.highcharts()` method (line 2). So we end up with:

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

Next:

 - surround all the keys with single quotes
 - replace any `//` by `#`
 - remove the `subtitle` key & value

You will end up with:

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

Within your report Python script, replace the method call of `resultHolder.setResult()` with the code we have above. So your call will look like this:

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

Your report should look like this:

![image](images/using-highchart-demo-line-example.png)


## Troubleshooting


### My report is not showing up (I see errors in the Browser Console)
You probably do not serve valid JSON to Highcharts.

- Look for any keys that are not surrounded by single quotes.
- If you see an error about `fontSize` then make sure you do not have a `subtitle` key/value pair in your JSON.
- Make sure comments are with `#` and **not** with `//`

### I get an error saying "failed to retrieve report..."
This basically means a syntax error in Python. Make sure you have all the keys in your dictionary surrounded with single quotes.

If you want to know what line caused this error then look at the xl-test server log in `<XL-TEST-HOME>/log/xltest-server.log`