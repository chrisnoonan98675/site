---
title: Install or remove XL Deploy plugins
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- plugin
- getting started
- installation
- setup
---

An XL Deploy plugin can have a `.jar` extension or, as of XL Deploy 5.0.0, an `.xldp` extension. The XLDP format is a ZIP archive that bundles the plugin with its dependencies.

## Install a plugin

To install a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Copy the plugin XLDP or JAR file to the `plugins` directory.
3. [Start](/how-to/start-xl-deploy.html) the XL Deploy server.

**Note:** Plugins that are installed or removed while XL Deploy is running will not take effect until the server is restarted.

## Remove a plugin

To remove a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Delete the plugin XLDP or JAR file from the `plugins` directory.
3. [Start](/how-to/start-xl-deploy.html) the XL Deploy server.

## XL Deploy server classloader

XL Deploy runs on the Java virtual machine (JVM) and has classloaders for the server itself and for plugins and extensions.

The XL Deploy server classpath typically contains resources, configuration files, and libraries that the server needs to work. The default XL Deploy server classloader will use the following classpath:

* `conf`: For configuration files
* `hotfix/*`: For server hotfixes (JAR files only)
* `lib/*`: For server libraries (JAR files only)

You can configure these folders in `xld-wrapper.conf`.

## XL Deploy plugin classloader

In addition to the server classloader, there is an XL Deploy plugin classloader. The plugin includes the classpath of the server classloader. It also includes these directories:

* `ext`: This directory is directly added to the classpath. It can contain classes and resources (without being stored in a JAR file).

The following directories are also scanned by the plugin classloader. All files with `*.jar` and `*.xldp` extension will be added to the classpath.

* `hotfix/plugins/*`: Can contain hotfix JARs for plugins
* `plugins/*`: Should contain installed plugins

These paths are not configurable. The directories are loaded in the order that they are mentioned. The order does mater. For example, hotfixes must be loaded before the actual code to be able to override its behavior.
