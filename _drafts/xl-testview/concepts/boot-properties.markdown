---
layout: beta
title: XL TestView boot properties
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- properties
- installation
---

XL TestView uses [Typesafe Config](https://github.com/typesafehub/config) for the configuration. Typesafe Config provides a priority system for property files.

* The highest priority have system properties, set using `-D...=...` on the Java Virtual Machine during startup.
* Second highest are the properties defined in `xl-testview.conf` in the conf directory. This will be the place where most custom properties will go.
* Finally there is an internal file that supplies defaults for any values omitted in `xl-testview.conf`. 

The property files have internal documentation. Please look at `xl-testview.conf` for all the properties and their effects.

Any changes in properties require restarting the server.

The format of the files is in [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md). HOCON is a superset of JSON, which means that normal JSON is valid.

For more information about configuration, refer to [Install XL TestView](/xl-testview/how-to/install.html).
