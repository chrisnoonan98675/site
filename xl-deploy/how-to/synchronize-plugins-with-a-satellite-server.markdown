---
title: Synchronize plugins with a satellite server
categories:
- xl-deploy
subject:
- Satellite
tags:
- plugin
- extension
- hotfix
- remoting
- satellite
since:
- 5.0.0
---

If you add, remove, or update an XL Deploy plugin or extension, you must synchronize XL Deploy with satellite servers before performing a deployment.

What exactly will be synchronized?

* Plugins and other files in the `plugins` folder
* Extensions and other files in the `ext` folder
* Hotfixes and other files in the `hotfix/plugins` folder

When determining the difference between the satellite and the XL Deploy server, name changes and content change are also taken into consideration. When XL Deploy detects a change, it re-uploads the entire folder.

To synchronize a satellite server:

1. Click **Repository** in XL Deploy.
2. Right-click the satellite and select **Synchronize plugins satellite**.

    Before synchronizing, the satellite will wait for all executing tasks to complete. It will then synchronize and restart.

3. Select the number of times that XL Deploy should attempt to ping the satellite after it restarts and the delay between attempts to ping it.

    ![image](images/synchronize-a-satellite-result.png)

The log of the first task shows how many plugins have been synchronized.

**Note** Ensure there is at least one file to synchronize in one of the folders. If there are no files on the XL Deploy server, the synchronization of that folder is skipped.

Before XL Deploy executes a deployment plan on a satellite, it checks if any plugins are missing or out-of-date. If any are, XL Deploy stops the deployment, and you must synchronize the satellite before continuing.
