---
layout: satellite
title: Add a satellite server to XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- configuration
- satellite
since:
- 5.0.0
---

To add a satellite server to XL Deploy:

1. Click **Repository** in XL Deploy.
2. Right-click **Infrastructure** and select **New** > **xl** > **satellite**. A new tab appears.
3. In the **Address** box, enter the IP address of the satellite in the **Address** box.
4. Enter the port number in the **Port** box.

    **Important:** The IP address and port number must *exactly* match the values configured in the `conf/satellite.conf` file on the satellite server.

5. Optionally select **Encrypted**. If you select it, XL Deploy will try to communicate with the satellite over a secure connection. See [Configure secure communication with a satellite](/xl-deploy/how-to/configure-secure-communication-with-a-satellite.html) for more information.

    ![image](images/satellite-ci-configuration.png) 
