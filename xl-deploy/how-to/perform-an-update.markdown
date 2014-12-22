---
title: Perform an update
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
2. Locate the application under **Packages**.
3. Expand the application name, select the deployment package, and drag it to the left side of the **Deployment Workspace**.
4. Locate the environment under **Deployed Applications**.
5. Expand the environment name, select the deployed application, and drag it to the right side of the **Deployment Workspace**.
6. If the updated application includes new deployables, click ![Auto-map button](/images/button_auto-map.png) to automatically map them to the containers in the environment.
7. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
8. Click **Next**. The deployment plan appears.
9. Click **Execute** to execute the plan.

If the update fails, click the failed step to see information about the cause of the failure.
