---
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
| [Cucumber](http://cukes.info/) | N/A | N/A | `xlt.Cucumber` | Functional |
| [Gatling](http://gatling.io/) | N/A | N/A | `xlt.Gatling` | Performance |
| [FitNesse](http://www.fitnesse.org/) | FitNesse | N/A | `xlt.FitNesse` | Functional | 
| [Jasmine](http://jasmine.github.io/) | [Karma](http://karma-runner.github.io) | JUnit | `xlt.KarmaXunit` | Functional |
| [JMeter](http://jmeter.apache.org/) | N/A | CSV | `xlt.JMeterCSV` | Performance |
| JMeter | N/A | XML | `xlt.JMeterXML` | Performance |
| [JUnit](http://junit.org) | [Ant](http://ant.apache.org/) | N/A | `xlt.JUnit` | Functional |
| JUnit | [Gradle](http://gradle.org/)	| N/A | `xlt.JUnit` | Functional |
| JUnit | [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) | N/A | `xlt.SurefireJUnit` | Functional |
| [TestNG](http://testng.org/) | Ant | JUnit reporter | `xlt.TestNGJUnitReportReporter` | Functional |
| TestNG | Gradle | N/A | `xlt.TestNGJUnitReportReporter` | Functional |
| TestNG | Maven Surefire | N/A | `xlt.SurefireJUnit` | Functional |
