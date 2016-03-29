---
title: Import test results into XL TestView
categories:
- xl-testview
subject:
- Test specifications
tags:
- test specification
- import
- project
---

You can import results from past test executions into XL TestView. The import wizard will guide you through the process.

To import results into XL TestView, click **Get started** on the welcome screen or click **Import** in the top menu bar.

## Step 1 Select test tool

1. Select the tool that was used to generate the results you want to import. Please refer to [the list of supported tools and test result formats](/xl-testview/concept/supported-test-tools-and-test-result-formats.html).
1. Click **Next**.

**Tip:** Do you want to import results from a test tool or in a format that XL TestView does not support by default? You can do so with a [custom test results parser](/xl-testview/how-to/create-a-custom-test-results-parser.html).

## Step 2 Locate test results

1. Select the location where XL TestView can find the results:
    * The XL TestView server
    * A remote computer running a Unix-based operating system
    * A remote computer running Microsoft Windows
1. If you select a remote computer provide the connection information that XL TestView should use to retrieve the results.
1. In the **Location of the test results** box, enter the directory where the test results are located; for example, `/Users/johndoe/fitnesse/results/` or `C:\Program Files\Cucumber\results\`. XL TestView will search this directory and any subdirectories.
1. In the **Test result file name pattern** box, enter the ANT pattern that XL TestView should use to match test result files. For more information about the pattern, refer to [XL TestView file selection patterns](/xl-testview/concept/file-selection-patterns.html).
1. Click **Next**.

## Step 3 Verify import

XL TestView shows the test result files that it found. Verify that they are correct, then click **Next** to import the results.

## Step 4 Import

1. Select an existing project where the test specification should be added, or enter a unique name to create a new project.
2. Enter an unique name for the test specification.
3. Click **Next** to import the results and save the specification.

## Step 5 Finish

XL TestView imports the test results. After it is finished, click **Finish and go to specifications** to go to the project page.
