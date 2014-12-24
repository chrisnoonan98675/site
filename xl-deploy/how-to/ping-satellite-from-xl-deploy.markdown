---
title: Ping a satellite
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- remoting
- satellite
---

To check the configuration of a satellite, you can try to ping it thanks to an XL Deploy Control Task. This will test the whole network and applicative stack and check your parameters.

In case of success, you'll see the 5 ping values and the uptime of the satellite. The first ping value can be higher than the others in case it's the first time to try to connect to it.

In case of failure, please refer to the [troubleshooting](/xl-deploy/how-to/troubleshooting-with-satellite.html) section.

![image](images/ping-a-satellite.png) 
![image](images/ping-a-satellite-result.png)