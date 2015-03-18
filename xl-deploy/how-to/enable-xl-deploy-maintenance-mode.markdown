---
layout: beta
title: Enable XL Deploy maintenance mode
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- maintenance mode
since:
- 5.0.0
---

XL Deploy 5.0.0 and later includes a *maintenance mode* feature that allows admin users to prevent users from starting new deployments, so the XL Deploy server can safely be restarted.

When you enable maintenance mode, deployments that have already started will be allowed to finish. Use the Task Monitor to view deployments that are in progress.

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
