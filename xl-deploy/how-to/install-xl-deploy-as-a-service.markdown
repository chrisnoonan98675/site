---
title: Install XL Deploy as a service
subject:
- Installation
categories:
- xl-deploy
tags:
- system administration
- installation
- service
- daemon
- setup
since:
- XL Deploy 5.0.0
---

To install XL Deploy 5.0.0 or later as a daemon or service:

1. Follow the installation procedure described in [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html).

    This procedure requires you to execute `<XLDEPLOY_HOME>/bin/run.sh` (on a Unix-based system) or `<XLDEPLOY_HOME>\bin\run.cmd` (on a Microsoft Windows-based system) to configure and initialize the server.
    
    On Unix, you should do this as the user under which you want XL Deploy to run. 

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `<XLDEPLOY_HOME>/conf/deployit.conf`.

1. As root (on Unix) or an administrator (on Windows), execute `<XLDEPLOY_HOME>/bin/install-service.sh` (on Unix) or `<XLDEPLOY_HOME>\bin\install-service.cmd` (on Windows). This command will install the service.

    On a Unix-based system, you will be asked for the user name under which you installed the XL Deploy server when you executed `run.sh`.

**Tip:** For information about starting the XL Deploy service, refer to [Start XL Deploy](/xl-deploy/how-to/start-xl-deploy.html).

## Troubleshoot the XL Deploy service

In XL Deploy 5.1.1 and later, if the XL Deploy service does not start, you can increase the startup timeout. In the `conf/xld-wrapper-linux.conf` (on Unix) or `conf\xld-wrapper-win.conf` (on Windows) file, add the following line:

    wrapper.startup.timeout=<timeout>

Where `<timeout>` is the startup timeout in seconds.

## Uninstall the XL Deploy service

To remove the installed service from the system, use the `<XLDEPLOY_HOME>/bin/uninstall-service.sh` (on Unix) or `<XLDEPLOY_HOME>\bin\uninstall-service.cmd` (on Windows) command.
