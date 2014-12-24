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

To declare a satellite instance in XL Deploy, you need to create an "Xl.Satellite" Configuration Item from the Repository view of XL Deploy.

1. Click **Repository** in XL Deploy.
2. Right-click **Infrastructure** and select **New** > **xl** > **satellite**. A new tab appears.
3. In the **Address** box, enter the IP address of the satellite and enter the port number in the **Port** box. Please ensure that they **extactly** match the value configured in the file `conf/application.conf` of the satellite server.

You can optionally selected the **Encrypted** checkbox. XL Deploy will then	 try to communicate throught a secure communication with the satellite. Please refer to the section [about setting up a secure communicte with a satellite](/xl-deploy/how-to/configure-secure-communication-with-a-satellite.html) for more information on that topic.

![image](images/add-a-satellite-ci.png) 
![image](images/satellite-ci-configuration.png) 

