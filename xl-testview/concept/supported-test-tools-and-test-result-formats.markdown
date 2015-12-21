---
no_mini_toc: true
title: Supported test tools and test result formats
categories:
- xl-testview
subject:
- Test results
tags:
- plugin
- test result parsers
- import
---

XL TestView supports the following test tools and test result formats:

{:.table .table-striped}
| Test tool | Build tool/Executor | Reporter format | Synthetic type | Type |
| --------- | ------------------- | --------------- | -------------- | ---- |
| Cucumber | N/A | N/A | `xlt.Cucumber` | Functional |
| Gatling | N/A | N/A | `xlt.Gatling` | Performance |
| [FitNesse](#fitnesse) | FitNesse | N/A | `xlt.FitNesse` | Functional | 
| Jasmine | Karma | JUnit | `xlt.KarmaXunit` | Functional |
| JMeter | N/A | CSV | `xlt.JMeterCSV` | Performance |
| JMeter | N/A | XML | `xlt.JMeterXML` | Performance |
| JUnit | Ant | N/A | `xlt.JUnit` | Functional |
| JUnit | Gradle | N/A | `xlt.JUnit` | Functional |
| JUnit | Maven Surefire | N/A | `xlt.SurefireJUnit` | Functional |
| TestNG | Ant | JUnit reporter | `xlt.TestNGJUnitReportReporter` | Functional |
| TestNG | Gradle | N/A | `xlt.TestNGJUnitReportReporter` | Functional |
| TestNG | Maven Surefire | N/A | `xlt.SurefireJUnit` | Functional |
| VSTest.console | N/A | TRX | `xlt.Trx` | Functional |
| [MSTest](#mstest) | N/A | TRX | `xlt.Trx` | Functional | 


## Notes

### FitNesse

* The test results must be in a folder named `FitNesseRoot`.

### MSTest

* Tests that result in a timeout will be reported by XL TestView as failed. This is inline with the behavior of VSTest.console.
* MSTest does not output ignored tests.

## Additional information

For information about the different test and build tools, see the following:

* [Cucumber](http://cukes.info/)
* [Gatling](http://gatling.io/)
* [Fitnesse](http://www.fitnesse.org/)
* [Jasmine](http://jasmine.github.io/)
* [Karma](http://karma-runner.github.io)
* [JMeter](http://jmeter.apache.org/)
* [JUnit](http://junit.org)
* [Ant](http://ant.apache.org/)
* [Gradle](http://gradle.org/)
* [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)
* [TestNG](http://testng.org/)
* [VSTest.console](http://msdn.microsoft.com/en-us/library/jj155800.aspx)
* [MSTest](http://msdn.microsoft.com/en-us/library/jj155804.aspx)
