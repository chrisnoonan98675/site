---
layout: beta
title: Supported test tools and test result formats
categories:
- xl-testview
subject:
- Test tool
tags:
- test tool
- plugin
- test results
---

XL TestView supports the following test tools and test result formats:

* [FitNesse](http://www.fitnesse.org/), for test suites executed by FitNesse itself, not through its jUnit runner
* [Cucumber](http://cukes.info/) report files
* JUnit compliant output files through the *xUnit* "tool", see below
* [Selenium](http://www.seleniumhq.org/), executed via xUnit or Cucumber
* [Gatling](http://gatling.io/) simulation logs
* [JMeter](http://jmeter.apache.org/) summary reports

*xUnit* output is known to work with the following setups:

* [JUnit](http://junit.org)
* [Maven](http://maven.apache.org) Surefire
* [Gradle](http://gradle.org), both [JUnit](http://junit.org/) and [TestNG](http://testng.org/)
* [Karma](http://karma-runner.github.io), with JUnit reporter
* [Appium](http://appium.io/)
