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
1. Locate the application under **Packages**.
1. Expand the application name, select the deployment package, and drag it to the left side of the **Deployment Workspace**. Alternatively, you can right-click the deployment package and select **Deploy**.
1. Locate the environment under **Deployed Applications**.
1. Expand the environment name, select the deployed application, and drag it to the right side of the **Deployment Workspace**. Alternatively, you can right-click the deployed application and select **Update**.
1. If the updated application includes new deployables that you want to deploy, click ![Auto-map button](/images/button_auto-map.png) to automatically map them to the containers in the environment.

    If the updated application is missing a deployable that was included in the previous deployment, the corresponding deployed item will appear in red.

1. To view and/or edit the properties of a mapped deployable, select it and click ![Edit deployed](/images/button_edit_deployed.png), or double-click it.
1. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
1. Click **Next**. The deployment plan appears.
1. Click **Execute** to execute the plan.

If the update fails, click the failed step to see information about the cause of the failure.
