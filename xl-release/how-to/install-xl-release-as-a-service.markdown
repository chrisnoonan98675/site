---
title: Install XL Release as a service
subject:
- Installation
categories:
- xl-release
tags:
- system administration
- installation
- service
- daemon
- setup
since:
- XL Release 5.0.0
weight: 403
---

## Install XL Release on Unix

To install XL Release 5.0.0 or later on a Unix-based system:

1. Follow the installation procedure described in [Install XL Release](/xl-release/how-to/install-xl-release.html). This procedure requires you to execute `XL_RELEASE_SERVER_HOME/bin/run.sh` to configure and initialize the server. You should do this as the user under which you want XL Release to run.

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf`.

1. As root, execute `XL_RELEASE_SERVER_HOME/bin/install-service.sh`. This command will install the service with the name `xl-release` and the title `XL Release Server`.

    You will be asked for the user name under which you installed the XL Release server when you executed `run.sh`.

For information about starting the XL Release service, refer to [Start XL Release](/xl-release/how-to/start-xl-release.html).

## Install XL Release on Microsoft Windows

To install XL Release 5.0.0 or later on a Microsoft Windows-based system:

1. Follow the installation procedure described in [Install XL Release](/xl-release/how-to/install-xl-release.html). This procedure requires you to execute `XL_RELEASE_SERVER_HOME\bin\run.cmd` to configure and initialize the server.

    You must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `XL_RELEASE_SERVER_HOME\conf\xl-release-server.conf`.

1. As an administrator, execute `XL_RELEASE_SERVER_HOME\bin\install-service.cmd`. This command will install the service with the name `xl-release` and the title `XL Release Server`.

    Ensure that the JDK `java` process is available on the PATH or in the `JAVA_HOME` environment property.

For information about starting the XL Release service, refer to [Start XL Release](/xl-release/how-to/start-xl-release.html).

## Uninstall the XL Release service

To remove the installed service from the system, execute `XL_RELEASE_SERVER_HOME/bin/uninstall-service.sh` (on Unix) or `XL_RELEASE_SERVER_HOME\bin\uninstall-service.cmd` (on Windows).

## Troubleshoot the XL Release service

### XL Release service does not start

If the XL Release service does not start, you can increase the startup timeout:

1. Stop the service and uninstall it.
1. In the `XL_RELEASE_SERVER_HOME/conf/xlr-wrapper-linux.conf` (on Unix) or `XL_RELEASE_SERVER_HOME\conf\xlr-wrapper-win.conf` (on Windows) file, add the following line:

        wrapper.startup.timeout=<timeout>

    Where `<timeout>` is the startup timeout in seconds.

1. Save the file.
1. Install the service and start it.

### XL Release service occasionally fails to start or stop

On Unix-based systems, the XL Release service may occasionally fail to stop after a stop command is issued, or start after a start command is issued (as shown in the server log). If this is the case:

1. Stop the service and uninstall it.
1. In the `XL_RELEASE_SERVER_HOME/conf/xlr-wrapper-linux.conf` file, add the following line:

        wrapper.fork_hack = true

1. Save the file.
1. Install the service and start it.
