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

If you assign a satellite group to a host and the host is part of a deployment, XL Deploy selects a random satellite from the group for deployment. This ensures that when a satellite from the group is down, other satellites can be used to perform future deployments.

For instructions about assigning satellites to a host, refer to [Assign satellites to a host](/xl-deploy/how-to/assign-a-satellite-to-a-host.html).

When a satellite from the group fails, the failed deployment task does not transfer over to another satellite. To recover the failed deployment task, you must bring back up the downed satellite.
