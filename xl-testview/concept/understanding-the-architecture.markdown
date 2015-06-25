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

{% comment %}
This diagram provides a high-level overview of the system architecture:

![XL TestView Architecture](images/architecture.png)
{% endcomment %}

You can access the XL TestView core using REST services. The product ships with one client of the REST service, a graphical user interface (GUI) that runs in browsers. Furthermore, the REST service can be accessed using various plugins and other tools that want to interact with XL TestView.

Plugins add capabilities to XL TestView and may be delivered by XebiaLabs or custom-built by users of XL TestView. These capabilities include supporting test tools, integration with ALM tools, or custom reports.

## Event repository

XL TestView uses events to represent facts. Examples of these events include the start of executing a test specification and the completion of an import of existing test results. Each individual test result is also stored as an event.

XL TestView uses a central, scalable data storage solution as a repository to store these events.

### Event repository interface

The events in the repository can be accessed through a uniform interface. This interface is described in an API.

## XL TestView and plugins

A XL TestView plugin is a component that provides the XL TestView server with extensions to support a specific type of test tool, provide an integration point, or enables a specific report.

To integrate with the XL TestView core, the plugins adhere to a well-defined interface. This interface specifies the contract between the XL TestView plugin and the XL TestView core, making  clear what one can expect of the other. The XL TestView core is the active party in this collaboration and invokes the plugin whenever needed. For its part, the XL TestView plugin replies to requests it is sent. 

When the XL TestView server starts, it scans the classpath and loads each XL TestView plugin it finds, readying it for interaction with the XL TestView core. The XL TestView core does not change loaded plugins or load any new plugins after it has started.

At runtime, multiple plugins will be active at the same time. It is up to the XL TestView core to integrate the various plugins and ensure they work together to perform its functionality.

{% comment %}
Plugins can define the following items:

- Report: Configuration items (CIs) that represent a report. See [create a custom report in XL TestView](/xl-testview/how-to/create-a-custom-report.html).
- Test Tool: CIs that represent a test tool or family of test tools representing the same test result format. See [create a test tool plugin](/xl-testview/how-to/create-a-test-tool-plugin.html).
{% endcomment %}

To enhance the extensibility of XL TestView, the product provides three APIs:

- Report and Repository API: This API provides a uniform way to query XL TestView's event repository to obtain test results that can be interpreted and represented.
- Test Tool: An API that can be used to extend XL TestView in the supported test tools and test output formats.
- Services: The services of XL TestView are exposed through a REST API. XL TestView's graphical user interface uses this API.
