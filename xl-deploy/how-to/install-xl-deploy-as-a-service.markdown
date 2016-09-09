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
weight: 104
---

**Note:** A version of this topic is available for [XL Deploy 4.5.x](/xl-deploy/4.5.x/install-xl-deploy-as-a-service-4.5.html).

## Install XL Deploy on Unix

To install XL Deploy 5.0.0 or later on a Unix-based system:

1. Follow the installation procedure described in [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html). This procedure requires you to execute `XL_DEPLOY_SERVER_HOME/bin/run.sh` to configure and initialize the server. You should do this as the user under which you want XL Deploy to run.

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `XL_DEPLOY_SERVER_HOME/conf/deployit.conf`.

1. As root, execute `XL_DEPLOY_SERVER_HOME/bin/install-service.sh`. This command will install the service.

    You will be asked for the user name under which you installed the XL Deploy server when you executed `run.sh`.

For information about starting the XL Deploy service, refer to [Start XL Deploy](/xl-deploy/how-to/start-xl-deploy.html).

## Install XL Deploy on Microsoft Windows

To install XL Deploy 5.0.0 or later on a Microsoft Windows-based system:

1. Follow the installation procedure described in [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html). This procedure requires you to execute `XL_DEPLOY_SERVER_HOME\bin\run.cmd` to configure and initialize the server.

    You must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `XL_DEPLOY_SERVER_HOME\conf\deployit.conf`.

1. As an administrator, execute `XL_DEPLOY_SERVER_HOME\bin\install-service.cmd`. This command will install the service.

For information about starting the XL Deploy service, refer to [Start XL Deploy](/xl-deploy/how-to/start-xl-deploy.html).

## Uninstall the XL Deploy service

To remove the installed service from the system, execute `XL_DEPLOY_SERVER_HOME/bin/uninstall-service.sh` (on Unix) or `XL_DEPLOY_SERVER_HOME\bin\uninstall-service.cmd` (on Windows).

## Troubleshoot the XL Deploy service

### XL Deploy service does not start

If the XL Deploy service does not start, you can increase the startup timeout (supported in XL Deploy 5.1.1 and later):

1. Stop the service and uninstall it.
1. In `XL_DEPLOY_SERVER_HOME/conf/xld-wrapper-linux.conf` (on Unix) or `XL_DEPLOY_SERVER_HOME\conf\xld-wrapper-win.conf` (on Windows), add the following line:

        wrapper.startup.timeout=<timeout>

    Where `<timeout>` is the startup timeout in seconds.

1. Save the file.
1. Install the service and start it.

### XL Deploy service occasionally fails to start or stop

On Unix-based systems, the XL Deploy service may occasionally fail to stop after a stop command is issued, or start after a start command is issued (as shown in the server log). If this is the case:

1. Stop the service and uninstall it.
1. In `XL_DEPLOY_SERVER_HOME/conf/xld-wrapper-linux.conf`, add the following line:

        wrapper.fork_hack = true

1. Save the file.
1. Install the service and start it.
