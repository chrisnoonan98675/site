---
title: Synchronize plugins with a satellite server
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- remoting
- satellite
---

If you add, remove, or update a plugin in XL Deploy, you must synchronize it with your satellite before any new deployment. This is done throught a Control Task your Configuration Item instances of satellites.

1. In XL Deploy, click **Repository**.
1. Under **Infrastructure**, right-click the satellite that you want to synchronize and select **Synchronizing plugins on satellites**. Once transfered, the satellite will restart itself. 
1. Select the number of times XL Deploy should attempt to ping the satellite and the delay between attempts while waiting for the satellite to be reachable again.

While synchronizing, the satellite will wait for all executing tasks to complete, and will then restart.

**Note:** When a deployment plan is executed on a satellite, XL Deploy will check if any plugins are missing or out-of-date. If any are, XL Deploy stops the deployment. You must synchronize the satellite to continue.

You can check in the logs of the first step how many plugins have been synchronized.

![image](images/synchronize-a-satellite.png) 
![image](images/synchronize-a-satellite-result.png) 