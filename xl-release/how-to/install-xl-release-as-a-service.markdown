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
---

To install XL Release 5.0.0 or later as a daemon or service:

1. Follow the installation procedure described in [Install XL Release](/xl-release/how-to/install-xl-release.html).

    This procedure requires you to execute `<XLRELEASE_HOME>/bin/run.sh` (on a Unix-based system) or `<XLRELEASE_HOME>\bin\run.cmd` (on a Microsoft Windows-based system) to configure and initialize the server.
    
    On Unix, you should do this as the user under which you want XL Release to run. 

    Also, you must ensure that the server is configured so that it can start without input from the user; for example, if a repository keystore password is required, then it should be provided in `<XLRELEASE_HOME>/conf/xl-release.conf`.

1. As root (on Unix) or an administrator (on Windows), execute `<XLRELEASE_HOME>/bin/install-service.sh` (on Unix) or `<XLRELEASE_HOME>\bin\install-service.cmd` (on Windows). This command will install the service.

    On a Unix-based system, you will be asked for the user name under which you installed the XL Release server when you executed `run.sh`.

## Troubleshoot the XL Release service

If the XL Release service does not start, you can increase the startup timeout. In the `conf/xlr-wrapper-linux.conf` (on Unix) or `conf\xlr-wrapper-win.conf` (on Windows) file, add the following line:

    wrapper.startup.timeout=<timeout>

Where `<timeout>` is the startup timeout in seconds.

## Uninstall the XL Release service

To remove the installed service from the system, use the `<XLRELEASE_HOME>/bin/uninstall-service.sh` (on Unix) or `<XLRELEASE_HOME>\bin\uninstall-service.cmd` (on Windows) command.
