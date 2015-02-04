---
title: Advanced XL Deploy configuration
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- security
- configuration
---

## XL Deploy properties file

XL Deploy automatically generates the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file and populates it with the default values provided by all installed plugins.

Lines in the file that start with a number sign (`#`) are provided for reference; XL Deploy ignores these lines. To change the value of a property, remove the number sign from that line and set the property to the desired value.

## XL Deploy configuration file

A number of advanced configuration options are available by editing the `deployit.conf` file in the `conf` directory. Restart the server after making the necessary changes.

* `taskThreadPool.corePoolSize`: The minimum number of threads allocated to execute tasks. Default value: 10.
* `taskThreadPool.maxPoolSize`: The maximum number of threads allocated to execute tasks. Default value: 2147483647.
* `taskThreadPool.keepAliveSeconds`: The number of seconds a time is kept alive before destroying it if the number of allocated threads exceeds.
* `taskThreadPool.queueCapacity`: The capacity of the queue that holds tasks to be executed if no threads are available. Default value: 2147483647.

## Client security configuration

A number of client security configuration options are available by editing the `deployit.conf` file in the `conf` directory. Restart the server after making the necessary changes.

* `client.session.timeout.minutes`: The number of minutes it takes before the session of the user is locked when he is not using the XL Deploy GUI. The default value of '0' means that no time-out is configured.
* `client.session.remember.enabled`: Setting this value to false disables the "Keep me logged in" option on the login screen. Default value: true.
