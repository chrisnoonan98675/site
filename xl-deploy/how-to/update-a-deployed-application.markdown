---
title: Update a deployed application
categories:
- xl-deploy
subject:
- Getting started
tags:
- deployment
- application
- package
---

XL Deploy always works with complete [deployment packages](../concept/preparing-your-application-for-xl-deploy.html#whats-in-an-application-deployment-package) that contain everything your applications need. You don't have to manually create a delta package to perform an update; instead, XL Deploy’s auto-flow engine calculates the delta between two packages automatically.

When updating a deployed application, XL Deploy identifies the configuration items in each package that differ between the two versions. It then generates an optimized deployment plan that only contains the steps that are needed to change these items.

When you want to update a deployed application, the process is the same whether you’re upgrading to a new version or downgrading to a previous version.

**Tip:** Watch the *Performing an update* video [here](http://vimeo.com/99837505) and the *Reverting to a previous version* video [here](http://vimeo.com/100130875).

To update a deployed application:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the **Workspace**. XL Deploy automatically maps the deployables in the application to the appropriate containers in the environment.

    **Note:** If the updated application is missing a deployable that was included in the previous deployment, the corresponding deployed item will appear in red.

1. Optionally view or edit the properties of a mapped deployable by double-clicking it or by selecting it and clicking ![Edit deployed](/images/button_edit_deployed.png).
1. Optionally click **Deployment Properties** to select the [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) that XL Deploy should use when generating the deployment plan.
1. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
1. Click **Next**. The deployment plan appears.
1. Click **Execute** to start the update. If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity. 

If a step in the update fails, XL Deploy stops executing the update and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## Mapping tips

* Instead of dragging-and-dropping the application version on the environment, you can right-click the application version and select **Deploy** and right-click the deployed application and select **Update**.
* You can manually map deployables by dragging and dropping them on containers in the Workspace. The cursor will indicate whether the deployable type can be mapped to the container type.
* To remove a deployable from all containers where it is mapped, select it in the left side of the Workspace and click ![Remove deployed from all containers](/images/remove_deployed.png).
* To remove one mapped deployable from a container, select it in the right side of the Workspace and click ![Remove deployed](/images/button_remove_deployed.png).

For information about skipping steps or stopping an update, refer to [link](/xl-deploy/how-to/perform-an-initial-deployment.html).
