---
title: Install XL TestView as a service
categories:
- xl-testview
subject:
- Installation
tags:
- system administration
- installation
---

## Microsoft Windows

To run XL TestView as a service on Microsoft Windows, use a service runner.

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
