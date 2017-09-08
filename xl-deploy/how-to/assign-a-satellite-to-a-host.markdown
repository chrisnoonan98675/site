---
title: Assign satellites to a host
categories:
- xl-deploy
subject:
- Satellite
tags:
- configuration
- satellite
- host
since:
- XL Deploy 5.0.0
weight: 308
---

To assign satellites to a host in XL Deploy:

1. Click **Repository** in XL Deploy.
2. Double-click the host CI to which you want to assign a satellite.
3. Under **Advanced**, select the satellite server or satellite server group from the list.

    ![image](images/attach-a-satellite.png)

**Note:** If you assign a satellite group to a host and the host is part of a deployment, XL Deploy selects a random satellite from the group for deployment. This ensures that when a satellite from the group is down, other satellites can be used to perform future deployments.
