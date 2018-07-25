---
title: Install Xl Satellite as a service
categories:
xl-deploy
subject:
Satellite
tags:
satellite
service
wrappers
system administration
installation
---

You can install and run XL-Satellite as a service on your Unix or Microsoft Windows machine. The satellite service wrapper requires Java Development Kit 8 or higher to be installed on the target Unix or Windows machine.

## Install XL Satellite on a Unix system

To install XL Satellite as a service on a Unix-based system:

1. Extract the satellite distribution ZIP file in the location on the server where you want to install the software.

    You can change the settings or configuration in the `SATELLITE_HOME/conf/satellite.conf` file with the appropriate values.  

1. As root, execute `SATELLITE_HOME/bin/install-service.sh`. This command will install and start the service.

    You will be asked for the user under which you want XL Satellite to run.

To check the status of your XL-Satellite service, you can use the following command:

    $ service xl-satellite status

To start, stop, and restart the xl-satellite service, you can use the following commands:

    $ service xl-satellite start

    $ service xl-satellite stop

    $ service xl-satellite restart

## Install XL Satellite on Microsoft Windows

To install XL Satellite as a service on a Windows-based system:

1. Extract the satellite distribution ZIP file in the location on the server where you want to install the software. You should do this as the user under which you want XL Satellite to run.

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `SATELLITE_HOME/conf/satellite.conf`.

1. As administrator, execute `SATELLITE_HOME/bin/install-service.cmd`. This command will install and start the service.

To manage the service, open the run dialog and execute the `services.msc` command or open the **Control Panel**, click **Administrative Tools** and double click the **Services** shortcut.

You can see the xl-satellite service in the list of installed services. You can start, stop, and restart it from the GUI or change the type from automatic to manual.

## Uninstall XL Satellite service

To remove the installed service from the system, execute `SATELLITE_HOME/bin/uninstall-service.sh` (as root, on Unix) or `SATELLITE_HOME\bin\uninstall-service.cmd` (as administrator, on Windows). This will stop and remove the service from the machine.
