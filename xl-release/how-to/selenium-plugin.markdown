---
title: Using the XL Release Selenium plugin
categories:
- xl-release
subject:
- Bundled plugins
tags:
- plugin
- selenium
- task
since:
- XL Release 5.0.0
---

The XL Release [Selenium](http://www.seleniumhq.org/projects/webdriver/) plugin allows XL Release to run test cases using Selenium WebDriver API. The plugin includes the following task types:

* **Selenium: Run TestCase**

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Selenium tasks have a dark olive green border.

## Features

* Run a test case using Selenium WebDriver

## Requirements

The Selenium plugin requires XL Release access to [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/).  

## Create Selenium task type

The **Selenium: Execute Test Script** task type runs Selenium test scripts. It requires you to specify the following information:

* URL for WebDriver Hub 
* The web browser that you want to test with

![XL Release Selenium Task](../images/selenium-task.png)

Additionally, for testing with Selenium Test Suites, you can specify:

* Starting URL
* Location of Test Suite Files
* Integer representing a threshold for failed test cases

## XL Release Scenario

Customers generally invoke Selenium testing during the Continuous Integration phase of building code, using their CI tool to run Selenium testing for Developers to validate their code changes.

These same Selenium tests can be run in different environments.  A sample XL Release template shows how QA can leverage the automated Selenium tests as part of their overall testing effort 

![XL Release Template using Selenium Testing in Parallel test group](../images/selenium-testing-example.png)