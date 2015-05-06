---
layout: beta
title: Import test results into XL Test
categories:
- xl-test
subject:
- Test specification
tags:
- test specification
- test results
- import
- project
---

You can import results from past test executions into XL Test. This will automatically create a test specification that you can use to execute future tests.

To import results into XL Test, click **Get started** on the welcome screen or click **Import** in the top menu bar.

## Step 1 Select test tool

1. Select the tool that was used to generate the results you want to import. Please refer to [the list of supported tools and test result formats](/xl-test/concept/supported-test-tools-and-test-result-formats.html).
1. Click **Next**.

## Step 2 Locate test results

1. Select the location where XL Test can find the results:
    * The XL Test server
    * A remote computer running a Unix-based operating system
    * A remote computer running Microsoft Windows
    * A Jenkins server
1. If you select a remote computer or Jenkins server, provide the connection information that XL Test should use to retrieve the results.
1. In the **Location of the test results** box, enter the directory where the test results are located; for example, `/Users/johndoe/fitnesse/results/` or `C:\Program Files\Cucumber\results\`. XL Test will search this directory and any subdirectories.
1. In the **Test result file name pattern** box, enter the regular expression that XL Test should use to match test result files. For more information about the regular expression pattern, refer to [XL Test file selection patterns](/xl-test/concept/xl-test-file-selection-patterns.html).
1. Click **Next**.

## Step 3 Verify import

XL Test shows the test result files that it found. Verify that they are correct, then click **Next** to import the results.

## Step 4 Import

1. Select an existing project where the test specification should be added, or enter a unique name to create a new project.
2. Enter an unique name for the test specification. 
3. Click **Next** to import the results and save the specification.

## Step 5 Finish

XL Test imports the test results. After it is finished, click **Finish and go to specifications** to go to the project page.
