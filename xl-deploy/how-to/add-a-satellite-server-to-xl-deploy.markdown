---
title: Add a satellite server to XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- configuration
- satellite
---

To add a satellite server to XL Deploy:

1. Click **Repository** in XL Deploy.
2. Right-click **Infrastructure** and select **New** > **xl** > **satellite**. A new tab appears.
3. In the **Host** box, enter the IP address of the satellite.
4. Optionally enter a port number in the **Port** box. If you enter a port number here, ensure that it is also configured in the `conf/application.conf` of the satellite server.
