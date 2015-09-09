---
title: Install XL TestView as a service
Subject: Installation
---

XL TestView provides no features out of the box to configure it as a service.

For Windows, a service runner can be used.

# Linux

For Linux, there exist a number of configuration systems.

## Systemd

Create user and a group named `xlt`. The directory containing XL TestView (`/opt/xl-testview` in the example below) should be owned by this user/group, along with all the files it contains.

Create a file `/lib/systemd/system/xl-testview.service` with the following content:

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


