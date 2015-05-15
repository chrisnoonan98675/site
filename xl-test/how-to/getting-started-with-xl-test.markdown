---
layout: beta
title: Getting started with XL Test
categories:
- xl-test
subject:
- Getting started
tags:
- test results
- test tool
- test specification
- dashboard
- report
---

XL Test lets you define, manage, execute, visualize, and analyze tests across all of your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

Below, please find some tips for your first-time XL Test experience.

## Step 1 Explore XL Test

First, read the [essential background information](/xl-test/concept/key-xl-test-concepts.html) about XL Test's key concepts.

Then, explore [dashboards](/xl-test/how-to/using-xl-test-dashboards.html) and [reports](/xl-test/concept/xl-test-reports.html) with the included sample test specifications and results. You can drill down the hierarchy of tests in reports such as the bar chart and duration chart.

![Dashboard with sample data](images/getting-started-demo-dashboard.png)

Finally, try [executing](/xl-test/how-to/execute-tests-from-xl-test.html) a sample test specification, such as *calculatorTestsComponentB*.

![Sample test specification](images/getting-started-sample-test-specification.png)

## Step 2 Import your test results

Set up XL Test to use your existing test results by running the [import wizard](/xl-test/how-to/import-test-results.html).

![XL Test import wizard](images/getting-started-import-wizard-step-1.png)

If you would like to integrate XL Test with an existing Jenkins setup, skip to step 3.

## Step 3 Install the XL Test plugin in Jenkins

XL Test can retrieve test results from test jobs that are run with Jenkins. To integrate XL Test with Jenkins, [install the XL Test plugin](/xl-test/how-to/connect-xl-test-to-a-jenkins-job.html) and add an XL Test post-build step to an existing job.

![XL Test post-build step in Jenkins](images/getting-started-jenkins-post-build-step.png)

## Step 4 Push test results from Jenkins to XL Test

To see XL Test and Jenkins in action:

1. Start with an application that has a low number of test jobs; for example, five jobs.
2. Add the XL Test post-build action to each test job.
3. Verify that, when you run these jobs, new test specifications appear in XL Test for the results.

## Step 5 Qualification of test results

XL Test can help you take go/no-go decisions through automated qualifications, so you can answer questions such as "Am I okay to go live with the application?".

Create a [test specification set](/xl-test/how-to/create-a-test-specification-set.html) that contains specifications that get results from your Jenkins jobs. Verify that the qualifications of these specifications roll up into the set.

![Test specification set with qualifications](images/getting-started-test-spec-set-qualification.png)

You can also develop your own qualification algorithm using [XML and Python](/xl-test/how-to/create-a-custom-qualification-in-xl-test.html).
