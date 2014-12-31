---
title: Ping a satellite from XL Deploy
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- remoting
- satellite
- connectivity
---

To check the connection between XL Deploy and a satellite, you can ping the satellite. This will test the entire network and application stack and check your parameters.

To ping a satellite server:

1. Click **Repository** in XL Deploy.
2. Right-click the satellite and select **Ping the satellite**.

    ![Ping a satellite](images/ping-a-satellite.png)

If the connection check succeeds, XL Deploy will return five ping values and the uptime of the satellite. The first ping value may be higher than the others if this is the first time XL Deploy is trying to connect to the satellite.

![Ping results](images/ping-a-satellite-result.png)

If the connection check fails, refer to the [information on troubleshooting](/xl-deploy/how-to/troubleshoot-with-satellite.html).
