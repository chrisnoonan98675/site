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
- classpath
weight: 107
---

XL Deploy runs on the Java Virtual Machine (JVM) and has two classloaders: one for the server itself, and one for the plugins and extensions. A plugin can have a `.jar` extension or, as of XL Deploy 5.0.0, an `.xldp` extension. The XLDP format is a ZIP archive that bundles a plugin with all of its dependencies.

To install or remove a plugin, you must stop the XL Deploy server; plugins that are installed or removed while the server is running will not take effect until it is restarted.

## Server classloader

The XL Deploy server classpath typically contains resources, configuration files, and libraries that the server needs to work. The default XL Deploy server classloader will use the following classpath:

{:.table}
| Directory | Description |
| --------- | ----------- |
| `XL_DEPLOY_SERVER_HOME/conf` | For configuration files |
| `XL_DEPLOY_SERVER_HOME/hotfix/lib/*` | For server hotfix JARs (XL Deploy 4.5.x and earlier) |
| `XL_DEPLOY_SERVER_HOME/hotfix/lib/*` | For server hotfix JARs (XL Deploy 5.0.0 and later) |
| `XL_DEPLOY_SERVER_HOME/lib/*` | For server library JARs |

You can configure these directories in `XL_DEPLOY_SERVER_HOME/conf/xld-wrapper.conf`.

## Plugin classloader

In addition to the XL Deploy server classloader, there is a plugin classloader. The plugin includes the classpath of the server classloader. It also includes:

{:.table}
| Directory | Description |
| --------- | ----------- |
| `ext` | Directly added to the classpath and can contain classes and resources that are not in a JAR file |

The plugin classloader also scans the following directories and adds all `*.jar` and `*.xldp` files to the classpath:

{:.table}
| Directory | Description |
| --------- | ----------- |
| `XL_DEPLOY_SERVER_HOME/hotfix/plugins/*` | Can contain hotfix JARs for plugins (XL Deploy 4.5.x and earlier) |
| `XL_DEPLOY_SERVER_HOME/hotfix/plugins/*` | Can contain hotfix JARs for plugins (XL Deploy 5.0.0 and later) |
| `XL_DEPLOY_SERVER_HOME/plugins/*` | Contains installed plugins |

These paths are not configurable. The directories are loaded in order that they are listed, and this order does matter. For example, hotfixes must be loaded before the actual code so it can override the server behavior.

## Install a plugin

To install a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Copy the plugin XLDP or JAR file to the `XL_DEPLOY_SERVER_HOME/plugins` directory.
3. [Start](/xl-deploy/how-to/start-xl-deploy.html) the XL Deploy server.
4. Refresh the XL Deploy GUI in your browser.

## Remove a plugin

To remove a plugin:

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.
2. Delete the plugin XLDP or JAR file from the `XL_DEPLOY_SERVER_HOME/plugins` directory.
3. [Start](/xl-deploy/how-to/start-xl-deploy.html) the XL Deploy server.
4. Refresh the XL Deploy GUI in your browser.
