---
title: Install XL TestView as a service
categories:
- xl-testview
subject:
- Installation
tags:
- system administration
- installation
weight: 701
---

## Install as a service after XL TestView 1.4.0

In XL TestView 1.4.1 and later, scripts are included to install XL TestView as a service on both Microsoft Windows and Unix systems using `upstart` or `systemd`.

To install XL TestView as a service:

1. Follow the installation procedure described in [Install XL Deploy](/xl-testview/how-to/install.html). You can skip the step to run `server.sh` or `server.cmd`.

2. As root (on Unix) or an administrator (on Windows), execute:
    * `XL_TESTVIEW_HOME/bin/install-service.sh` (on Unix)
    * `XL_TESTVIEW_HOME\bin\install-service.cmd` (on Windows)

    This command will install the service.

    On Windows, this installs a service wrapper and a service called `XL TestView`, configured to run on the local system account. For production use it is recommended that you create a dedicated user and configure the service to log on as this dedicated user.

    The Unix `install-service.sh` script will install a service user called `xl-testview`, tighten the permissions on the installation, and install an `upstart` or `systemd` service script.

**Note:** A System V init script is not provided with XL TestView.

### Uninstall the XL TestView service

To remove the installed service from Windows, run the `XL_TESTVIEW_HOME\bin\uninstall-service.cmd` command. On Unix, run `XL_TESTVIEW_HOME\bin\uninstall-service.sh`.

## Install as a service before XL TestView 1.4.1

## Microsoft Windows

To run XL TestView as a service on Microsoft Windows, use a service wrapper.

## Linux

To run XL TestView as a service on Linux, use `systemd`. Create user and a group named `xlt`. The directory containing XL TestView (`/opt/xl-testview` in the example below) should be owned by this user/group, along with all of the files it contains.

Create a `/lib/systemd/system/xl-testview.service` file with the following content:

    [Unit]
    Description=XL TestView
    After=network.target

    [Service]
    EnvironmentFile=-/etc/sysconfig/xl-testview
    User=xlt
    Group=xlt
    ExecStart=/opt/xl-testview/bin/server.sh
    Restart=on-failure
    KillMode=control-group

    [Install]
    WantedBy=multi-user.target

Start the service with `systemctl start xl-testview.service`.
