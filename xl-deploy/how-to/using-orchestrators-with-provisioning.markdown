---
title: Using orchestrators with provisioning
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- cloud
- orchestrator
since:
- XL Deploy 5.5.0
weight: 156
---

In XL Deploy, an [orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) combines the steps for individual component changes into an overall deployment or provisioning workflow. Orchestrators are also responsible for deciding which parts of the deployment or provisioning plan are executed sequentially or in parallel. You can [combine multiple orchestrators](/xl-deploy/concept/combining-multiple-orchestrators.html) for more complex workflows.

XL Deploy supports several orchestrators for provisioning. To select orchestrator(s), click **Deployment Properties** when [setting up provisioning](/xl-deploy/how-to/provision-an-environment.html).

## `provisioning` orchestrator

The `provisioning` orchestrator is the default orchestrator for provisioning. This orchestrator interleaves all individual component changes by running all steps of a given order for all components. This results in an overall workflow in which all virtual instances are created, all virtual instances are provisioned, a new environment is created, and so on.

![provisioning orchestrator](images/provisioning/default-provisioning-orchestrator.png)

## `sequential-by-provisioned` orchestrator

The `sequential-by-provisioned` orchestrator provisions all virtual instances sequentially. For example, suppose you are provisioning an environment with Apache Tomcat and MySQL. The `sequential-by-provisioned` orchestrator will provision the Tomcat and MySQL provisionables sequentially as shown below.

![sequential-by-provisioned orchestrator](images/provisioning/sequential-by-provisioned-orchestrator.png)

## `parallel-by-provisioned` orchestrator

The `parallel-by-provisioned` orchestrator provisions all virtual instances in parallel.

![parallel-by-provisioned orchestrator](images/provisioning/parallel-by-provisioned-orchestrator.png)
