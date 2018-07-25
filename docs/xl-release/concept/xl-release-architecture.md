---
title: Understanding XL Release's architecture
categories:
xl-release
subject:
Architecture
tags:
system administration
customization
weight: 406
---

XL Release features a modular architecture that allows you to change and extend components while maintaining a consistent system. This is a high-level overview of the system architecture:

![XL Release architecture](../images/xl-release-architecture.png)

XL Release's central component is called the *core*. It contains the following functionality:

* Templates and releases
* Release archiving
* Security
* Reporting

The XL Release core is accessed using a REST service. XL Release includes a REST service client in the form of its graphical user interface (GUI) that runs in browsers.

XL Release plugins provide support for various third-party tools. These plugins add capabilities to XL Release and may be delivered by XebiaLabs or custom-built by XL Release users.
