---
title: Installing and removing XL Deploy plugins
categories:
- xl-deploy
subject:
- System administration
tags:
- system administation
- plugin
---

To install a new plugin, stop the XL Deploy server and copy the plugin XLDP or JAR archive into the `plugins` directory, then restart the XL Deploy server.

To uninstall a plugin, stop the XL Deploy server and remove the plugin XLDP or jar archive from the `plugins` directory, then restart the XL Deploy server.

Plugins can have `.jar` and `.xldp` extensions. Any plugins added or removed when XL Deploy server is running will not take effect until the server is restarted.

## Installing extensions and hotfixes

XL Deploy runs on the JVM and has two classloaders. One for the server itself, and one for the plugins and extensions. The plugin classloader has support for our XLDP plugin format. The XLDP format is a ZIP archive that bundles the plugin with all its dependencies.

The default XL Deploy server classloader will use the following classpath:

* `conf`: For configuration files.
* `hotfix/*`: For server hotfixes, `jar` files only.
* `lib/*`: For server libraries, `jar` files only.

These folders can be configured by changing `xld-wrapper.conf`. The XL Deploy server classpath typically contains resources, configuration files and libraries needed by the server itself to work.

Additionally to the server classloader, there is also the XL Deploy plugin class loader. The plugin includes the classpath of the server classloader. Additionally it includes the following directories:

* `ext`: This folder is directly added to the classpath and can contain classes and resources without putting them in a `jar` file.

The following folders are also scanned by the plugin classloader. All files with `*.jar` and `*.xldp` extension will be added to the classpath.

* `hotfix/plugins/*`: This folder can contain hotfix jars for plugins
* `plugins/*`: This folder should contain installed plugins

These paths are not configurable. The folders are loaded in order they are mentioned. The order does mater. For example, hot fixes must be loaded before the actual code to be able to override their behavior.
