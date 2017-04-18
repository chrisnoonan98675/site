---
title: Install Xl Deploy satellite as a service
categories:
- xl-deploy
subject:
- Satellite
tags:
- satellite
- service
- wrappers
- system administration
- installation
---

## Install XL Deploy satellite on a Unix system

To install XL Deploy satellite as a service on a Unix-based system:

1. Follow the installation procedure described in [Install and configure a satellite server](/xl-deploy/how-to/install-and-configure-a-satellite-server.html). This procedure requires you to execute `SATELLITE_HOME/bin/run.sh` to configure and initialize the satellite server. You should do this as the user under which you want XL Deploy satellite to run.

    Also, you must ensure that the satellite server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `SATELLITE_HOME/conf/satellite.conf`.

1. As root, execute `SATELLITE_HOME/bin/install-service.sh`. This command will install the service.

    You will be asked for the user name under which you installed the XL Deploy satellite server when you executed `run.sh`.

For information about starting the XL Deploy satellite service, refer to [Start the satellite](/xl-deploy/how-to/install-and-configure-a-satellite-server.html#start-the-satellite).

## Install XL Deploy satellite on Microsoft Windows

To install XL Deploy satellite as a service on a Windows-based system:

1. Follow the installation procedure described in [Install and configure a satellite server](/xl-deploy/how-to/install-and-configure-a-satellite-server.html). This procedure requires you to execute `SATELLITE_HOME/bin/run.cmd` to configure and initialize the server. You should do this as the user under which you want XL Deploy satellite to run.

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `SATELLITE_HOME/conf/satellite.conf`.

1. As root, execute `SATELLITE_HOME/bin/install-service.cmd`. This command will install the service.

For information about starting the XL Deploy satellite service, refer to [Start the satellite](/xl-deploy/how-to/install-and-configure-a-satellite-server.html#start-the-satellite).

## Uninstall XL Deploy satellite service

To remove the installed service from the system, execute `SATELLITE_HOME/bin/uninstall-service.sh` (on Unix) or `SATELLITE_HOME\bin\uninstall-service.cmd` (on Windows).
