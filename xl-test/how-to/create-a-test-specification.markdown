---
layout: beta
title: Create a test specification
categories:
- xl-test
subject:
- Test specification
tags:
- test specification
- project
---

To create a test specification in XL Test:

1. Click **Projects** in the top menu bar.
1. Next to the project in which you want to create the test specification, click **Manage**.
1. Click **Add a test specification to project**. The New test specification screen appears.
1. Select the type of test specification you want to create:
    * **Active Test Specification**: A test specification that can retrieve test results.
    * **Executable Test Specification**: A test specification that can execute test runs.
    * **Passive Test Specification**: A test specification to which an external process such as Jenkins can push results.
    * **Test Specification Set**: A group that aggregates the results of multiple test specifications. For information about creating a set, refer to [Create a test specification set](/xl-test/how-to/create-a-test-specification-set.html).
1. Enter a unique title in the **Title** box.
1. Select a qualifier from the **Qualification** list.
1. Select the test tool for the specification from the **Test Tool Name** list.

    If you are creating a passive test specification, click **Create test specification** to finish. Otherwise, continue with the remaining steps.
    
1. In the **Search Pattern** box, enter the regular expression that XL Test should use to identify the files that contain the results of test runs. More information on the regular expression pattern can be found [here](/xl-test/concept/xl-test-file-selection-patterns.html).
1. In the **Working Directory** box, enter the location where the command to run the test specification should be executed.
1. Select the host where the specification will from from the **Host** list.

    If you are creating an active test specification, click **Create test specification** to finish. Otherwise, continue with the remaining steps.

1. Enter the command to execute the tests in the **Command Line** box. The command is relative to the working directory. Some sample commands are:
    * FitNesse: `java -jar plugins/fitnesse-20140901-standalone.jar -p 1234 -c "DemoSuite?suite&debug&format=text"`
    * Gradle: `gradle clean check`
    * Maven: `mvn clean test`
1. In the **Timeout** box, enter the maximum number of minutes that the tests should be allowed to run.

    ![New test specification](images/create-a-test-specification.png)

1. Click **Create test specification** to finish.
