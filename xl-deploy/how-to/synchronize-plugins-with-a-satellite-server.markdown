---
layout: satellite
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
- XL Deploy 5.0.0
---

If you add, remove, or update an XL Deploy plugin or extension, you must synchronize XL Deploy with satellite servers before performing a deployment.

Before XL Deploy executes a deployment plan on a satellite, it checks if any plugins are missing or out-of-date. If any are, XL Deploy stops the deployment, and you must synchronize the satellite before continuing.

## What is synchronized?

The following folders will be synchronized:

* Plugins and other files in the `plugins` folder
* Extensions and other files in the `ext` folder
* Hotfixes and other files in the `hotfix/plugins` folder

When determining the difference between the satellite and the XL Deploy server, name changes and content change are also taken into consideration. When XL Deploy detects a change, it re-uploads the entire folder.

Note that:

* The `hotfix/lib` folder is not synchronized.
* Manually placing files on the satellite is not recommended. XL Deploy will delete such files during the next synchronization.

## Synchronize a satellite server

To synchronize a satellite server:

1. Ensure that the XL Deploy server can boot with the currently installed plugins and extensions. Synchronizing a broken setup can result in a broken satellite.
1. Ensure that there is at least one file to synchronize in each of the folders. If there are no files on the XL Deploy server, the synchronization of that folder is skipped.
1. Click **Repository** in XL Deploy.
1. Under **Infrastructure**, right-click the satellite and select **Synchronize plugins satellite**.

    Before synchronizing, the satellite will wait for all executing tasks to complete. It will then synchronize and restart.

1. In the **Max Attempts** box, enter the number of times that XL Deploy should attempt to ping the satellite.
1. In the **Delay** box, enter the number of seconds that XL Deploy should wait between attempts to ping the satellite.
1. Click **Next**, then click **Execute**.

    ![image](images/synchronize-a-satellite-result.png)

    If XL Deploy finds files that need to be synchronized, it will synchronize them and then restart the satellite server. This is required to prevent issues with file locking. If nothing needs to be synchronized, the satellite will not be restarted.

The log of the first task shows how many plugins were synchronized.
