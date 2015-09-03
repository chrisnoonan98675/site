---
title: Supported test tools and test result formats
categories:
- xl-testview
subject:
- Test parsers
tags:
- test tool
- plugin
- test results
---

XL TestView supports the following test tools and test result formats:

{:.table .table-striped}
|Test Tool |Build tool / Executor|Reporter format|Synthetic type|Type|
|------|----------|--------|--------------|----|-------|
|[Cucumber](http://cukes.info/)|     |   |      xlt.Cucumber     |Functional|
|[Gatling](http://gatling.io/)|||xlt.Gatling|Performance||
|[FitNesse](http://www.fitnesse.org/)| FitNesse   | |    xlt.FitNesse          |Functional| 
|[JMeter](http://jmeter.apache.org/)||CSV|xlt.JMeterCSV|Performance||
|[JMeter](http://jmeter.apache.org/)||XML|xlt.JMeterXML|Performance||
|[JUnit](http://junit.org) |Ant	     |        |     xlt.JUnit         |Functional|
|JUnit |Gradle	     |        |       xlt.JUnit       |Functional|
|JUnit |Maven Surefire	     |        |     xlt.SurefireJUnit         |Funcational|
|[Jasmine](http://jasmine.github.io/)|[Karma](http://karma-runner.github.io) |  JUnit  |    xlt.KarmaXunit          |Functional|
|[TestNG](http://testng.org/)|Ant       |   JUnit reporter     |    xlt.TestNGJUnitReportReporter          |Functional|
|TestNG|Gradle       |        |     xlt.TestNGJUnitReportReporter         |Functional|
|TestNG|Maven Surefire       |        |  xlt.SurefireJUnit            |Functional|
