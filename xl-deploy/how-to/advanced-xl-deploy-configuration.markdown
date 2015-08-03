---
title: Advanced XL Deploy configuration
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- security
- configuration
---

## XL Deploy properties file

XL Deploy automatically generates the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file and populates it with the default values provided by all installed plugins.

Lines in the file that start with a number sign (`#`) are provided for reference; XL Deploy ignores these lines. To change the value of a property, remove the number sign from that line and set the property to the desired value.

## XL Deploy configuration file

The `conf/deployit.conf` file allows you to set a number of advanced configuration settings. You must restart the server after changing this file.

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `taskThreadPool.corePoolSize` | Minimum number of threads allocated to execute tasks | 10 |
| `taskThreadPool.maxPoolSize` | Maximum number of threads allocated to execute tasks | 2147483647 |
| `taskThreadPool.keepAliveSeconds` | Number of seconds a task thread is kept alive before destroying it if the number of allocated threads exceeds | |
| `taskThreadPool.queueCapacity` | Capacity of the queue that holds tasks to be executed if no threads are available | 2147483647 |

## Client security configuration

The `conf/deployit.conf` file allows you to set a number of client security configuration settings. You must restart the server and reload the GUI after changing these settings.

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `client.session.timeout.minutes` | Number of minutes before a user's session is locked when the GUI is idle | 0 (no timeout) |
| `client.session.remember.enabled` | Show or hide the **Keep me logged in** option on the log-in screen | true (option is shown) |

## Client configuration

The `conf/deployit.conf` file allows you to set a number of client configuration settings. You must restart the server and reload the GUI after changing these settings.

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `client.automatically.map.all.deployables` | Enable or disable automatic mapping of all deployables when you set up a deployment in the GUI | true (all deployables will be auto-mapped) |
