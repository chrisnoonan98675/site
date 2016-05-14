---
title: Install or remove XL Deploy plugins
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- plugin
- installation
- hotfix
---

An XL Deploy plugin can have a `.jar` extension or, as of XL Deploy 5.0.0, an `.xldp` extension. The XLDP format is a ZIP archive that bundles the plugin with its dependencies.

To install or remove a plugin, you must stop the XL Deploy server; plugins that are installed or removed while the server is running will not take effect until it is restarted.

## Install a plugin

To install a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Copy the plugin XLDP or JAR file to the `XLDEPLOY_HOME/plugins` directory.
3. [Start](/xl-deploy/how-to/start-xl-deploy.html) the XL Deploy server.

## Remove a plugin

To remove a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Delete the plugin XLDP or JAR file from the `XLDEPLOY_HOME/plugins` directory.
3. [Start](/xl-deploy/how-to/start-xl-deploy.html) the XL Deploy server.

## XL Deploy server classloader

XL Deploy runs on the Java virtual machine (JVM) and has classloaders for the server itself and for plugins and extensions.

The XL Deploy server classpath typically contains resources, configuration files, and libraries that the server needs to work. The default XL Deploy server classloader will use the following classpath:

* `XLDEPLOY_HOME/conf`: For configuration files
* `XLDEPLOY_HOME/hotfix/*`: For server hotfix JARs (XL Deploy 4.5.x and earlier)
* `XLDEPLOY_HOME/hotfix/lib/*`: For server hotfix JARs (XL Deploy 5.0.0 and later)
* `XLDEPLOY_HOME/lib/*`: For server library JARs

You can configure these folders in `XLDEPLOY_HOME/conf/xld-wrapper.conf`.

## XL Deploy plugin classloader

In addition to the server classloader, there is an XL Deploy plugin classloader. The plugin includes the classpath of the server classloader. It also includes these directories:

* `XLDEPLOY_HOME/ext`: This directory is directly added to the classpath. It can contain classes and resources (without being stored in a JAR file).

The following directories are also scanned by the plugin classloader. All files with `*.jar` and `*.xldp` extension will be added to the classpath.

* `XLDEPLOY_HOME/hotfix/*`: Can contain hotfix JARs for plugins (XL Deploy 4.5.x and earlier)
* `XLDEPLOY_HOME/hotfix/plugins/*`: Can contain hotfix JARs for plugins (XL Deploy 5.0.0 and later)
* `XLDEPLOY_HOME/plugins/*`: Should contain installed plugins

These paths are not configurable. The directories are loaded in the order that they are mentioned. The order does matter; for example, hotfixes must be loaded before the actual code so that they can override its behavior.
