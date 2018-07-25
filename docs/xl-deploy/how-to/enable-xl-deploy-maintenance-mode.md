---
title: Enable XL Deploy maintenance mode
categories:
xl-deploy
subject:
System administration
tags:
system administration
maintenance mode
since:
XL Deploy 5.0.0
weight: 265
---

XL Deploy 5.0.0 and later includes a *maintenance mode* feature that allows administrators to prevent users from starting new deployments and other tasks, so the XL Deploy server can safely be restarted.

Note that:

* When you enable maintenance mode, deployments that have already started will be allowed to finish. You can use the [Task Monitor](/xl-deploy/how-to/monitor-and-reassign-deployment-tasks.html) to view deployments that are in progress.
* While maintenance mode is enabled, the `admin` user can continue to start new tasks.
* Maintenance mode does not prevent scheduled tasks from starting.

## Enable maintenance mode

To enable maintenance mode:

1. Click **Admin** in the top bar.
2. Go to the **Maintenance** tab.
3. Click **Enable maintenance mode**.

## Disable maintenance mode

To disable maintenance mode and allow users to start new deployments:

1. Click **Admin** in the top bar.
2. Go to the **Maintenance** tab.
3. Click **Disable maintenance mode**.
