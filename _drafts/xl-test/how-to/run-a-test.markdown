---
title: Execute tests from XL-Test
categories:
- xl-test
subject:
- Test specification
tags:
- test
- specification
---
If you have [import](import-test-results.html) a set of test results, it is possible to run the tests again. This will execute the tests on the local or a remote system, and import the new test results. 

* Click on `Test Specifications` in the navigation bar.
* Click on `Execute`.
* If this is the first time executing a test, XL-Test will require some additional information. This can be altered later by editing a Test Specification:
  * Command line: The command line command to execute the test set. The commands are relative to the working directory of the test specification.
  * Host: The host to run the command on. See [hosts](../concept/hosts.html)
* XL-Test will now execute the command and show the progress in a progress bar. Note that  the first time the progress bar will be very inaccurate, because XL-Test does not have any historical data on the run time yet.
* Once the execution is finished, if you click on `Show Report` you can view the results and generate reports over your imported results.
  
Some examples of command-line commands:

* **FitNesse:** `java -jar plugins/fitnesse-20140901-standalone.jar -p 1234 -c "DemoSuite?suite&debug&format=text"`
* **Gradle:** `gradle clean check`
* **Maven:** `mvn clean test`


