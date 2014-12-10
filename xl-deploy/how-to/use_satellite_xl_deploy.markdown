---
title: Configuring ang using a satellite on XL Deploy
subject:
- Satellite
categories:
- xl-satellite
- xl-deploy
tags:
---

## Configuring a satellite on XL Deploy

1. Click **Repository** in XL Deploy.
2. Right-click **Infrastructure** and select **New** > **xl** > **satellite**. A new tab appears.
3. In the **Host** box enter an IP address of the satellite
4. In the **Port** box you can modify the port. If so you will have to modify it in the `conf/application.conf` of your satellite server.

## Using a satellite on XL Deploy

All XL Deploy components can be attached to a satellite :

1. Select a component you want to execute on a satellite (ex : Tomcat, JBoss)
2. In the **Infrastructure** configuration, you will find a satellite list
3. Select a satellite and your component will be executed on that satellite