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

Troubleshooting:

* *Task is hanging on 'Send task to _satellite_' using satellite group*
If you deploy to an environment where some host(s) are attached to a satellite _group_*_ **satgrp** while some other hosts are attached to some satellite _instance_ **sat1** directly, the deployment task will fail when the **satgrp** contains **sat1** and picks **sat1** as the 'active' satellite.

Up until versions 7.0.2, 7.5.2 and 8.0.2, blocks assigned to the same satellite will be hanging on _EXECUTING_ state. The logs will show the following:
{code:java}
akka.actor.InvalidActorNameException: actor name [upload-supervisor-<something>] is not unique!
    ...
{code}

On later versions of XL-Deploy, one of the blocks will fail with the following output:
{code:java}
Error while sending file to satellite. (Task is already registered)
{code}
If you run into the situation described above, please cancel the task and ensure all hosts use **satgrp** not **sat1**.

Note that this issue could occur intermittently because of the random selection of a satellite from the satellite group.
