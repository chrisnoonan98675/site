---
title: Customize the test run overview
categories:
- xl-testview
subject:
- Reports
tags:
- report
- extension
weight: 734
---

The test run overview shows a table of all runs of a test specification. A lot of information is shown here, such as the qualification result, the start date of the test, and the duration of the test. You can customize this view with additional information.

## Open the test run overview

To open the test run overview:

1. Click **Projects** in the top navigation bar.
1. Click on a project.
2. Click **Show reports**.
3. In the pop-up window, select the test run overview.

## Customizing the columns

To customize the columns that appear on the test run overview, you use a type modification on the `xlt.TestRunOverview` synthetic type. Open the `<XLTESTVIEW_HOME>/dist/ext/synthetic.xml` file and add the following XML:

    <type-modification type="xlt.TestRunOverview">
        <property name="extraColumns" kind="list_of_string" default=""/>
    </type-modification>

Change the attribute `default` to a property of events you want to show. For example, if your build setup adds the hash of the artifact that is being tested to all test results in the field `artifactHash`, you would use the following type modification:

    <type-modification type="xlt.TestRunOverview">
        <property name="extraColumns" kind="list_of_string" default="artifactHash"/>
    </type-modification>

This name is case-sensitive and should appear exactly as it does in the test events. You can provide multiple names, separated by commas (`,`).
