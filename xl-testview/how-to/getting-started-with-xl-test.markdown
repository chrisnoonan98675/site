---
layout: beta
title: Getting started with XL TestView
categories:
- xl-testview
subject:
- Getting started
tags:
- test results
- test tool
- test specification
- dashboard
- report
---

XL TestView lets you define, manage, execute, visualize, and analyze tests across all of your test tools to give you a single source of quality truth. XL TestView acts as the Fitbit of your software.

Below, please find some tips for your first-time XL TestView experience.

## Step 1 Explore XL TestView

First, read the [essential background information](/xl-testview/concept/key-xl-test-concepts.html) about XL TestView's key concepts.

Then, explore [dashboards](/xl-testview/how-to/using-xl-test-dashboards.html) and [reports](/xl-testview/concept/xl-test-reports.html) with the included sample test specifications and results. You can drill down the hierarchy of tests in reports such as the bar chart and duration chart.

![Dashboard with sample data](images/getting-started-demo-dashboard.png)

Finally, try [executing](/xl-testview/how-to/execute-tests-from-xl-test.html) a sample test specification, such as *functionalTestsComponentB*.

![Sample test specification](images/getting-started-sample-test-specification.png)

## Step 2 Import your test results

Set up XL TestView to use your existing test results by running the [import wizard](/xl-testview/how-to/import-test-results.html).

![XL TestView import wizard](images/getting-started-import-wizard-step-1.png)

If you would like to integrate XL TestView with an existing Jenkins setup, skip to step 3.

## Step 3 Install the XL TestView plugin in Jenkins

XL TestView can retrieve test results from test jobs that are run with Jenkins. To integrate XL TestView with Jenkins, [install the XL TestView plugin](/xl-testview/how-to/connect-xl-test-to-a-jenkins-job.html) and add an XL TestView post-build step to an existing job.

![XL TestView post-build step in Jenkins](images/getting-started-jenkins-post-build-step.png)

## Step 4 Push test results from Jenkins to XL TestView

To see XL TestView and Jenkins in action:

1. Start with an application that has a low number of test jobs; for example, five jobs.
2. Add the XL TestView post-build action to each test job.
3. Verify that, when you run these jobs, new test specifications appear in XL TestView for the results.

## Step 5 Qualification of test results

XL TestView can help you take go/no-go decisions through automated qualifications, so you can answer questions such as "Am I okay to go live with the application?".

Create a [test specification set](/xl-testview/how-to/create-a-test-specification-set.html) that contains specifications that get results from your Jenkins jobs. Verify that the qualifications of these specifications roll up into the set.

![Test specification set with qualifications](images/getting-started-test-spec-set-qualification.png)

You can also develop your own qualification algorithm using [XML and Python](/xl-testview/how-to/create-a-custom-qualification-in-xl-test.html).
