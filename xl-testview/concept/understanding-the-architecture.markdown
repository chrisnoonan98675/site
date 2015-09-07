---
title: Understanding XL TestView's architecture
categories:
- xl-testview
subject:
- Architecture
tags:
- plugin
- api
---

Before you customize XL TestView functionality, some knowledge of the XL TestView architecture is required. XL TestView features a modular architecture that allows extension and modification of various components while maintaining a consistent system.

You can access the XL TestView core using REST services. The product ships with one client of the REST service, a graphical user interface (GUI) that runs in browsers.

Plugins add capabilities to XL TestView and may be delivered by XebiaLabs or custom built by users of XL TestView. These capabilities include supporting test tools, integration with ALM tools, or custom reports.

## Events

Test results are stored in the database of XL TestView. In XL TestView a single test result is called an [Event](events.html).
Examples of these events include the result of the execution of a single test case (the most granular level) and the start of an import of a test specification.

XL TestView uses a central, scalable data storage solution as a repository to store these events.

## XL TestView and plugins

A XL TestView plugin is a component that provides the XL TestView server with extensions to support a specific type of test tool, provide an integration point, or enables a specific report.

To integrate with the XL TestView core, the plugins adhere to a well-defined interface. This interface specifies the contract between the XL TestView plugin and the XL TestView core, making  clear what one can expect of the other. The XL TestView core is the active party in this collaboration and invokes the plugin whenever needed. For its part, the XL TestView plugin replies to requests it is sent. 

When the XL TestView server starts, it scans the classpath and loads each XL TestView plugin it finds, readying it for interaction with the XL TestView core. The XL TestView core does not change loaded plugins or load any new plugins after it has started.

At runtime, multiple plugins will be active at the same time. It is up to the XL TestView core to integrate the various plugins and ensure they work together to perform its functionality.

Plugins can be used to extend XL TestView in the following ways:

- Report - configuration items (CIs) that represent a report.
  
  See [create a custom report in XL TestView](/xl-testview/how-to/create-a-custom-report.html).
- Qualifications - define if test specification results are either passed or failed, based on a (pre-)defined qualifier.

  See [create a custom qualification](/xl-testview/how-to/create-a-custom-qualification.html).
- Test tool configuration - CIs that represent a test tool or family of test tools representing the same test result format.

  See [create a custom test result parser](/xl-testview/how-to/create-a-custom-test-results-parser.html).
