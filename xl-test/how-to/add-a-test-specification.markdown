---
layout: beta
title: Add a test specification
categories:
- xl-test
subject:
- Test specification
tags:
- test specification
- project
---

To add a test specification or a set of test specifications to XL Test:

1. Click **Projects** in the top menu bar.
2. Click on the project you want to add the test specification to.
1. Click **Add a test specification to project**. 

## Add a single test specification

To add a single test specification:

1. Select **Single test specification**.
1. Select the type of test specification that you want to create:
    * Executable test specification
    * Test specification
1. Enter a unique title in the **Title** box.
2. Select the test tool for the specification from the **Test Tool Name** list.
1. In the **Search Pattern** box, enter the regular expression that XL Test should use to identify the files that contain the results of test runs. More information on the regular expression pattern can be found [here](/xl-test/concept/xl-test-file-selection-patterns.html).
1. In the **Working Directory** box, enter the location where the command to run the test specification should be executed.
1. Select the host where the specification will from from the **Host** list.
1. Select the algorithm that determines whether the specification as a whole passed or failed from the **Qualification Type** list.
1. If you want to import existing test results into XL Test, select **Import Test Results**.
1. If you are creating an executable test specification:
    a. Enter the command to execute the tests in the **Command Line** box.
    b. In the **Timeout** box, enter the maximum number of minutes that the tests should be allowed to run.

    ![New test specification](images/create-a-test-specification.png)

1. Click **Add Test Specification** to save the specification.

## Create a test specification set

To create a set of test specifications:

1. Select **Test specification set**.
1. Enter a unique name in the **Test specification name** box.
1. Select a test specification in the **Available test specifications** list and click the arrow to move it to the **To be added to super set** list. Do this for each specification that you want to add to the set.

    ![New test specification set](images/create-a-test-specification-set.png)

1. Click **Add test specification** to save the set.
