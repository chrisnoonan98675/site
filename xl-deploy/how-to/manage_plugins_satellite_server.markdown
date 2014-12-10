---
title: Managing plugins to an XL Satellite server
subject:
- Satellite
categories:
- xl-satellite
tags:
- plugins
- remoting
---

## Checking plugins

When some plugins are added or updated to XL Deploy, they have to be added or updated also to satellites server. When a deployment plan is executed on a satellite, a step will check if plugins are missing or have to be updated. In case of missing plugins, the deployment will stop itself and you have to synchronize plugins on your satellite.

## Synchronize plugins

When you synchronize XL Deploy and XL Satellite plugins, XL Satellite will wait that all current executing tasks are done and then will restart.

To synchronize plugins on satellite :

1. Click **Repository** in XL Deploy.
2. Click **Infrastructure** and select the satellite you want to update
3. Right-click on the satellite and select **Synchronizing plugins on satellites**
4. A window will appear letting you choose the attempt number to ping the satellite and the delay between attempts. It allows to know when the satellite has restarted. 
