---
title: Provision an environment
categories:
- xl-deploy
subject:
- Provisioning
tags:
- provisioning
- environment
- cloud
since:
- XL Deploy 6.0.0
weight: 157
---

You can use XL Deploy's [provisioning feature](/xl-deploy/concept/provisioning-through-xl-deploy.html) to create cloud-based environments in a single action. The process of provisioning an environment using XL Deploy is very similar to the process of [deploying an application](/xl-deploy/how-to/deploy-an-application.html).

**Note:** A version of this topic is available for [XL Deploy 5.5.x](/xl-deploy/5.5.x/provision-an-environment-5.5.html).

## Provision an environment using the GUI

To provision an environment using the XL Deploy GUI:

1. Click **Deployment** in the top bar.
1. Under **Packages**, locate the provisioning package and expand it to see its versions.
1. Drag the desired version to the left side of the Deployment Workspace.
1. Under **Environments**, locate the desired environment and drag it to the right side of the Deployment Workspace.

    XL Deploy automatically maps the provisionables in the package to the providers in the environment.

1. Click **Execute** to immediately start the provisioning.

You can also optionally:

* View or edit the properties of a mapped provisioned by double-clicking it.
* Click **Deployment Properties** to select orchestrators or enter placeholder values.
* Click **Advanced** if you want to adjust the plan by skipping steps or inserting pauses.

If the server does not have the capacity to immediately start executing the plan, the plan will be in a QUEUED state until the server has sufficient capacity.

If a step in the provisioning fails, XL Deploy stops executing the provisioning and marks the step as FAILED. Click the step to see information about the failure in the output log.

## Provision an environment using the CLI

For information about provisioning an environment using the XL Deploy command-line interface (CLI), refer to [Using the XL Deploy CLI provisioning extension](/xl-deploy/how-to/using-the-xl-deploy-cli-provisioning-extension.html).

## The unique provisioning ID

To prevent name collisions, a unique provisioning ID is added to most items that are created during provisioning. This ID is a random string of characters such as `AOAFbrIEq`. In the GUI, you can see the ID by clicking **Deployment Properties** and going to the **Provisioning** tab.

On the package level, you can specify an XL Deploy environment to which the CIs that are created based on bound templates will be added. The unique ID will be appended to this environment name; for example, if you specify the environment name *TEST*, XL Deploy will create an environment called *TEST-AOAFbrIEq*.

The unique ID is also added to the CIs that are created. For example, if you create an `aws.ec2.InstanceSpec` provisionable called *apache-spec*, XL Deploy will created a provisioned called *AOAFbrIEq-apache-spec*.

If the cardinality set on the provisionable CI is greater than 1, then XL Deploy will append a number to the name of the provisioneds that are generated. For example, if *apache-spec* has a cardinality of 3, then XL Deploy will create provisioneds called *AOAFbrIEq-apache-spec*, *AOAFbrIEq-apache-spec-2*, and *AOAFbrIEq-apache-spec-3*.
