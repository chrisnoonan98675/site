---
title: Create a custom report for test specification sets
categories:
- xl-testview
subject:
- Reports
tags:
- report
- extension
- test specification set
---

Producing reports for Test Specification Sets is mostly the same as you'd do for a normal report. You can create highchart reports for sets as well, there is no difference in that respect.

For detailed technical information about custom reports, refer to [Custom reports in XL TestView](/xl-testview/concept/custom-reports.html).

This how-to will explain how to create a custom specification set report. Since the workings are practically the same as for custom reports on test specifications, this how-to will emphasize the differences between them.

## Configure the report in `synthetic.xml`

Add a custom report type to `<XLTESTVIEW_HOME>/ext/synthetic.xml`:

1. Like with custom reports for specifications, copy an existing entry of a report. Here we take the bar chart again.

        <type type="xlt.BarChart" extends="xlt.Report" label="Bar chart" description="Presents the tests that passed and failed in the latest execution of the test specification, in bar chart format">
            <property name="scriptLocation" default="reports/BarChart.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
        </type>

2. Change the `type` attribute to use your desired prefix and name; for example, `type="myCompany.myCustomSet"`.

3. Change the `scriptLocation` to the report script that you will create; for example, `MyCustomSetReport.py`.

4. Change the `label` and `description` attributes.

5. Make sure you add a property named `applicableCategories`, the kind should be `set_of_string` and the value should be `set`. This indicates the report is for test specification *sets*. Example:

        <property name="applicableCategories" kind="set_of_string" default="set"/>

    The result will look like:
    
        <type type="myCompany.myCustomSet" extends="xlt.Report" label="My Set Report" description="My set report that does things different">
            <property name="scriptLocation" default="reports/MyCustomSetReport.py"/>
            <property name="iconName" default="bar-report-icon"/>
            <property name="reportType" hidden="true" default="highchart"/>
	        <property name="applicableCategories" kind="set_of_string" default="set"/>
        </type>


All changes made to a `synthetic.xml` require a [restart](/xl-testview/how-to/start.html) of XL TestView.

To verify that your changes took effect, click **Projects** in the top menu and select a project. On a *test specification*, click **Show report**. It should show the *My Set Report* report:

![Report list with new custom report](images/create-a-custom-report-testset-reports.png)

## Write the report script

Writing a report remains the same as with a 'normal' custom report. However, there are a few differences you have to keep in mind:

1. Since you're reporting about a *specification set* there is no such thing as the *latest run* (`test_run`) available. Instead you can access the `test_specification_set` you are reporting about.

2. You can get *qualification* information from a test run. To make things easier for querying we provide a `test_runs_repository` (which is the same as `test_runs` in non-set reports) and a `qualification_repository`. 


### Sample script
This shows a script that takes all test specifications of a set and counts the passed and failed qualifications. Then, it represents the values in a Pie chart. Store
this file as `MyCustomSetReport.py` under `ext/reports` (You may need to create the reports folder if it doesn't exist):

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

The end result should look like this:

![Report example](images/create-a-custom-report-testset-reports_report_example.png)



**Note:** While the report syntax looks similar to JSON, this is in fact a Python script, and it is actually a `Dictionary` (or `Map` in other languages). This means that the keys should be surrounded by quotation marks (`'` or `"`). If you copy examples from a Highcharts demo, you must adjust the keys accordingly. For an extended example, refer to [Create a custom report using Highcharts demos](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).
---
title: Create a custom report for test specification sets
categories:
- xl-testview
subject:
- Reports
tags:
- report
- extension
- test specification
since:
 - XL TestView 1.4.0
---

This topic describes how to create a custom report for test specification sets. The process is very similar to the process for [other custom reports](/xl-testview/how-to/create-a-custom-report.html).

For detailed technical information about custom reports, refer to [Custom reports in XL TestView](/xl-testview/concept/custom-reports.html).

## Configure the report in `synthetic.xml`

Add a custom report type to `<XLTESTVIEW_HOME>/ext/synthetic.xml`:

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

    ![Report list with new custom report](images/create-a-custom-report-testset-reports.png)

## Write the report script

To write the report script, follow the process described in [Create a custom report](/xl-testview/how-to/create-a-custom-report.html). Note the following differences:

* Because you are going to report on a *test specification set*, there is no such thing as the *latest run* (`test_run`). Instead, you can access the `test_specification_set` you are reporting on.

* You can get *qualification* information from a test run. You can query a a `test_runs_repository` (which is the same as `test_runs` in non-set reports) and a `qualification_repository`. 

### Sample script

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

The end result should look like:

![Report example](images/create-a-custom-report-testset-reports_report_example.png)



**Note:** While the report syntax looks similar to JSON, this is in fact a Python script, and it is actually a `Dictionary` (or `Map` in other languages). This means that the keys should be surrounded by quotation marks (`'` or `"`). If you copy examples from a Highcharts demo, you must adjust the keys accordingly. For an extended example, refer to [Create a custom report using Highcharts demos](/xl-testview/how-to/create-a-custom-report-using-highcharts-demos.html).
