---
title: Understanding XL Test's architecture
categories:
- xl-test
subject:
- Architecture
tags:
- testrun
---

> 'I would have written a shorter letter, but I did not have the time.'  
>   -- Blaise Pascal

Before customizing XL Test functionality, some knowledge of the XL Test architecture is required. XL Test features a modular architecture that allows extension and modification of various components while maintaining a consistent system.

<!---
This diagram provides a high-level overview of the system architecture:

![XL Test Architecture](images/xl-test-architecture.png)
--->

The XL Test core is accessed using REST services. The product ships with one client of the REST service, a graphical user interface (GUI) that runs in browsers. Furthermore, the REST service can be accessed using various plugins and other tools that want to interact with XL Test.

Plugins add capabilities to XL Test and may be delivered by XebiaLabs or custom-built by users of XL Test. These capabilities include supporting test tools, integration with ALM tools, or custom reports.

## XL Test's event repository

XL Test uses events to represent facts. Examples of these events include the start of executing a test specification and the completion of an import of existing test results. Each individual test result is also stored as an event.

XL Test uses a central, scalable data storage solution as a repository to store these events.

### Event repository interface

The events in the repository can be accessed through a uniform interface. This interface is described in an API.

## XL Test and plugins

A XL Test plugin is a component that provides the XL Test server with extensions to support a specific type of test tool, provide an integration point, or enables a specific report.

To integrate with the XL Test core, the plugins adhere to a well-defined interface. This interface specifies the contract between the XL Test plugin and the XL Test core, making  clear what one can expect of the other. The XL Test core is the active party in this collaboration and invokes the plugin whenever needed. For its part, the XL Test plugin replies to requests it is sent. When the XL Test server starts, it scans the classpath and loads each XL Test plugin it finds, readying it for interaction with the XL Test core. The XL Test core does not change loaded plugins or load any new plugins after it has started.

At runtime, multiple plugins will be active at the same time. It is up to the XL Test core to integrate the various plugins and ensure they work together to perform its functionality.

Plugins can define the following items:

- _Report_: Configuration Items (CIs) that represent a report. See [create a custom report in XL Test](/xl-test/how-to/create-a-custom-report-in-xl-test.html).
- _Test Tool_: CIs that represent a test tool or family of test tools representing the same test result format. See [create a test tool plugin](/xl-test/how-to/create-a-test-tool-plugin.html).

To enhance the extensibility of XL Test, the product provides three APIs (Application Programming Interface):

- _Report and Repository API_: This API provides a uniform way to query XL Test's event repository to obtain test results that can be interpreted and represented.
- _Test Tool_: An API that can be used to extend XL Test in the supported test tools and test output formats.
- _Services_: The services of XL Test are exposed through a REST API. XL Test's graphical user interface uses this API.

