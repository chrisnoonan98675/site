---
layout: beta
title: Execute tests from XL Test
categories:
- xl-test
subject:
- Test specification
tags:
- test specification
- test execution
---

If you have imported a set of test results, you can run the tests again. This will execute the tests on the local or a remote system and import the new test results.

1. Click **Test specifications** in the menu bar.
1. Next to the test specification that you want to execute, click **Execute**.
1. If this is the first time that XL Test is executing this specification, you must provide the command-line command that XL Test should use to execute the tests. The command is relative to the working directory defined in the test specification. Some sample commands are:
    * FitNesse: `java -jar plugins/fitnesse-20140901-standalone.jar -p 1234 -c "DemoSuite?suite&debug&format=text"`
    * Gradle: `gradle clean check`
   * Maven: `mvn clean test`

1. If this is the first time that XL Test is executing this specification, you must identify the host where XL Test should run the command. See [Add a host to XL Test](/xl-test/how-to/add-a-host-to-xl-test.html) for more information.
1. XL Test executes the command and shows the progress in a progress bar. 

    **Note:** The first time you execute the specification, the progress bar will not be accurate because XL Test does not have the necessary historical data about the run time.

1. After execution is complete, click **Show report** to see the results.
