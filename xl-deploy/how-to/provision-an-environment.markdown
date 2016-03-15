---
layout: beta-noindex
title: Provision an environment
since:
- XL Deploy 5.5.0
---

In XL Deploy, you use the [provisioning feature](/xl-deploy/concept/provisioning-environments-with-xl-deploy.html) to create cloud-based environments in a single action. To provision an environment through XL Deploy:

1. Click **Provision** in the top bar.
1. Under **Provisioning Packages**, locate the blueprint and expand it to see its versions (provisioning packages).
1. Drag the desired provisioning package to the left side of the Provisioning Workspace.
1. Under **Ecosystems**, locate the desired ecosystem and drag it to the right side of the Provisioning Workspace.

    XL Deploy automatically maps the provisionables in the package to the providers in the ecosystem.

1. Click **Execute** to immediately start the provisioning.

You can also optionally:

* View or edit the properties of a mapped provisioned by double-clicking it.
* Click **Provisioning Properties** to select orchestrators, change the provisioned environment, or enter placeholder values.
* Click Advanced if you want to adjust the provisioning plan by skipping steps or inserting pauses.

If the server does not have the capacity to immediately start executing the plan, the plan will be in a QUEUED state until the server has sufficient capacity.

If a step in the provisioning fails, XL Deploy stops executing the provisioning and marks the step as FAILED. Click the step to see information about the failure in the output log. 

## Provision an environment using the CLI

***To be written***

## Deploy to a provisioned environment

***To be written***

## Deprovision an environment

***To be written***
