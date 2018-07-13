---
title: Add a satellite server to XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- configuration
- satellite
since:
- XL Deploy 5.0.0
weight: 305
---

Before you add a satellite server configuration item (CI) to the XL Deploy repository, ensure that you have [enabled communication with satellites](/xl-deploy/how-to/configure-xl-deploy-to-communicate-with-satellites.html).

To add a satellite server to XL Deploy:

1. In the left pane, click **Infrastructure**.
1. Click ![Menu button](images/menuBtn.png), then click **New** > **XL** > **Satellite**.
1. In the **Address** field, enter the IP address of the satellite.
1. In the **Port** field, enter the port number.

    **Important:** The IP address and port number must match the values configured in the `conf/satellite.conf` file on the satellite server.

 **Note:** If the **Encrypted** checkbox is selected, XL Deploy will use a secure connection to communicate with the satellite. For more information, see [Configure secure communication with a satellite](/xl-deploy/how-to/configure-secure-communication-with-a-satellite.html).

![image](images/satellite-ci-configuration-new-ui.png)

**Note:** Satellite is an add-on module for XL Deploy. Please [contact us](https://xebialabs.com/contact/) for pricing information.
