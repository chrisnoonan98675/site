---
layout: beta
title: Getting Started with XL Test
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

Great! You've downloaded and installed XL Test. Please take a moment and see what you can do with XL Test.

XL Test lets you define, manage, execute, visualize, and analyze tests across all your test tools to give you a single source of quality truth. XL Test acts as the Fitbit of your software.

Below, please find some tips you may regard as useful for your first-time XL Test experience.

## 1. Exploring XL Test

- Read the [essential background information](/xl-test/concept/key-xl-test-concepts.html) on XL Test.

   - XL Test comes with several showcase test specifications and, for one of those test specifications, some showcase (random) test runs with test results. The test specification concerns a set with a substantial number of functional tests with ten test runs. The hierarchy of the tests allows for drill-down functionality in some reports, e.g. the bar chart and the durations chart.
   - Explore the showcase dashboard or try to open some of the reports for the test specifications that are available.ease see the [complete list of available reports](/xl-test/concept/xl-test-reports.html).
   - So, XL Test comes with some showcase test specifications. Now it is time to try to executing one of these, e.g. the calculatorTestsComponentB.

## 2. Import your own test results

- Set up XL Test to use your own data (please skip to step 3 if you would like to directly integrate XL Test with your existing Jenkins setup).

   - Run the import wizard. Please refer to the [documentation](/xl-test/how-to/import-test-results.html).

## 3. Hook up Jenkins and XL Test

A typical use case for XL Test is to get the results from test jobs run via Jenkins available in XL Test. To this end, install the XL Test plugin for Jenkins and hook it up to an existing test job as described [here](/xl-test/how-to/connect-xl-test-to-a-jenkins-job.html).

## 4. Hook up XL Test with a real-life Jenkins install

- Pick an application that makes sense (not 30 test jobs, but, say, 5 for starters)
- Add the Jenkins plugin as a post build action to each of the test jobs as described [here](/xl-test/how-to
/connect-xl-test-to-a-jenkins-job.html).
- Verify that when you run these jobs, the test results become available within XL Test (test specifications should appear)

## 5. Qualification of test results

XL Test can help you in taking go/no go decisions by automated qualfications. This can help you in answering questions like “Am I okay to go live with the application"?

- Develop you own qualification algorithm using the [documentation](/xl-test/how-to/create-a-custom-qualification-in-xl-test.html).

- [Create a superset](/xl-test/how-to/add-a-test-specification.html) on the side of XL Test to which you add each of the test specifications that are filled with the results from the Jenkins jobs. Verify these qualifications roll up to the super set.

The qualification of the super set allows you to garner the automated go/no go decisions.
