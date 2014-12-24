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

If you add, remove, or update an XL Deploy plugin, you must synchronize it with your satellite servers:

1. In XL Deploy, click **Repository**.
1. Under **Infrastructure**, right-click the satellite that you want to synchronize and select **Synchronizing plugins on satellites**.
1. Select the number of times XL Deploy should attempt to ping the satellite and the delay between attempts.

While synchronizing, the satellite will wait for all executing tasks to complete, and will then restart.

**Note:** When a deployment plan is executed on a satellite, XL Deploy will check if any plugins are missing or out-of-date. If any are, XL Deploy stops the deployment. You must synchronize the satellite to continue.
