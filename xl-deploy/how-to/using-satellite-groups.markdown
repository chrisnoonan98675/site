---
title: Using satellite groups
categories:
- xl-deploy
subject:
- Satellite
tags:
- configuration
- satellite
- group
since:
- XL Deploy 7.2.0
---

You can define groups of satellites that are logically connected together, for example: several satellites that are installed in the same datacenter.

To create a satellite group, hover over **Infrastructure** in the left pane, click ![Explorer action menu](/images/menu_three_dots.png), select **New** > **xl** > **SatelliteGroup**. Specify the name and enter the required information for each satellite you want to add to the satellite group.

If you assign a satellite group to a host and the host is part of a deployment, XL Deploy selects a random satellite from the group as the 'active' satellite for this deployment. This ensures that when a satellite from the group is down, other satellites can be used to perform future deployments.

For instructions about assigning satellites to a host, refer to [Assign satellites to a host](/xl-deploy/how-to/assign-a-satellite-to-a-host.html).

When a satellite from the group fails, the failed deployment task does not transfer over to another satellite. To recover the failed deployment task, you must bring back up the downed satellite.

## Troubleshooting

### Task is hanging on `Send task to satellite` using satellite group

If you deploy to an environment where one or more hosts are attached to a **satgrp** satellite group and other hosts are attached to a **sat1** satellite instance directly, the deployment task fails when the **satgrp** contains **sat1** and picks **sat1** as the active satellite.

For versions 7.0.2, 7.5.2, and 8.0.2, the blocks assigned to the same satellite hang on the _EXECUTING_ state. The logs shows the following:

        akka.actor.InvalidActorNameException: actor name [upload-supervisor-<something>] is not unique!

For XL Deploy version 8.0.3 and later, one of the blocks fails with the following output:

        Error while sending file to satellite. (Task is already registered)

If any of these situations occur, cancel the task and ensure all the hosts use **satgrp** and not **sat1**.

**Note:** This issue can occur intermittently due to the random selection of a satellite from the satellite group.
