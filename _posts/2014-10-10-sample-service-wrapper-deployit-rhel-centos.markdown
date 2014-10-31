---
title: Sample service wrapper for Deployit on RHEL/CentOS
categories:
- xl-deploy
tags:
- service
---

[This wrapper shell script](/sample-scripts/deployitd) will run the Deployit server as a service in RHEL/CentOS environments, which can be further tweaked according to project need and environments.

Note: This script has been tested with [Deployit 3.9.0](http://docs.xebialabs.com/product-version.html#/deployit/3.9.x).

## Usage

    ./deployitd [start|stop|restart|status]

Before running the script, update the following variable in the script file to point to your Deployit `SERVER_HOME`:

    DEPLOYIT_HOME=/apps/deployit/server

## Invoking Remotely

If you want to invoke this script remotely, you may need to redirect `stdin`, otherwise the daemon may hang due to pending file descriptors:

    ssh username@server 'cd | nohup ./deployitd start > out 2> err < /dev/null &'
