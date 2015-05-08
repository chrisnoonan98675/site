---
layout: beta
title: Customize Test Run Overview
categories:
- xl-test
subject:
- Reports
tags:
- host
- ssh
- cifs
- jenkins
---

The test runs overview shows a table of all test runs of a test specification. A lot of information is shown here, such as the qualification result, the start date of the test and the duration of the test. It is possible to customize this view with additional information.

## How to view the test runs overview:

1. Click on a project
2. Click on the `Show reports` button.
3. In the modal, select the Test run overview

## Customizing the columns

Modifications can be done by using a type modification on the `xltest.TestRunOverview` synthetic type. Add the following xml to the `synthetic.xml` in the `dist/ext` directory: 

    <type-modification type="xltest.TestRunOverview">
        <property name="extraColumns" kind="list_of_string" default=""/>
    </type-modification>

Change the attribute `default` to an property of events you want to show. For example, if your build setup adds the hash of the artifact that is being tested to all test results in the field `artifactHash` you use the following type modification:

    <type-modification type="xltest.TestRunOverview">
        <property name="extraColumns" kind="list_of_string" default="artifactHash"/>
    </type-modification>
    
This name is case sensitive and should appear exactly like that in the test events.