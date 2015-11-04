---
title: Understanding XL TestView's architecture
categories:
- xl-testview
subject:
- Architecture
tags:
- plugin
- api
- extension
---

Before you customize XL TestView's functionality, some knowledge of the XL TestView architecture is required. XL TestView features a modular architecture that allows extension and modification of various components while maintaining a consistent system.

You can access the XL TestView core using REST services. The product ships with one client of the REST service, a graphical user interface (GUI) that runs in browsers.

Plugins add capabilities to XL TestView and may be delivered by XebiaLabs or custom built by users of XL TestView. These capabilities include supporting test tools, integration with ALM tools, or custom reports.

## Events

Test results are stored in XL TestView's database. A single test result is called an [event](/xl-testview/concept/events.html). Examples of events are:

* The result of the execution of a single test case (the most granular level)
* The start of an import of a test specification

XL TestView uses a central, scalable data storage solution as a repository to store events.

## Plugins

Plugins provide the XL TestView server with extensions to support a specific type of test tool, provide an integration point, or enable a specific report.

To integrate with the XL TestView core, plugins must adhere to a well-defined interface that specifies the contract between the XL TestView plugin and the XL TestView core, making  clear what one can expect of the other. The XL TestView core is the active party in this collaboration and invokes the plugin whenever needed. The XL TestView plugin replies to requests it is sent. 

When the XL TestView server starts, it scans the classpath and loads each XL TestView plugin it finds, readying it for interaction with the XL TestView core. The XL TestView core does not change loaded plugins or load any new plugins after it has started.

At runtime, multiple plugins will be active at the same time. It is up to the XL TestView core to integrate the various plugins and ensure they work together to perform its functionality.

You can use plugins to extend XL TestView in the following ways:

* [Reports](/xl-testview/how-to/create-a-custom-report.html) that display test result data
* [Qualifications](/xl-testview/how-to/create-a-custom-qualification.html) that define if test specification results are passed or failed, based on a predefined qualifier
* [Test results parsers](/xl-testview/how-to/create-a-custom-test-results-parser.html) that process test results produced by a test tool
