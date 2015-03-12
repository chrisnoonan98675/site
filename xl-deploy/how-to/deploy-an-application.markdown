---
title: Deploy an application
categories:
- xl-deploy
subject:
- Getting started
tags:
- deployment
- application
- package
---

After you have [defined your infrastructure](connect-xl-deploy-to-your-infrastructure.html), [defined an environment](create-an-environment-in-xl-deploy.html), and added an application to XL Deploy, you can perform the initial deployment of an application to an environment.

**Tip:** Watch the *Performing an initial deployment* video [here](http://vimeo.com/97815293).

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages**.
1. Expand the application, select the deployment package, and drag it to the left side of the **Deployment Workspace**. Alternatively, you can right-click the deployment package and select **Deploy**.
1. Locate the environment under **Deployed Applications**.
1. Select the environment and drag it to the right side of the **Deployment Workspace**. Alternatively, you can right-click the environment and select **Deploy to**.
1. Map the deployables in the package to the containers in the environment. 
    * To automatically map all deployables, click ![Auto-map all deployables](/images/button_auto-map.png).
    * To automatically map one or more selected deployables, click ![Auto-map single deployable](/images/button_auto-map_single.png).
    * To manually map a deployable, drag it from the package to the environment. The cursor will indicate whether the file type can be mapped to the container type.
1. To view and/or edit the properties of a mapped deployable, select it and click ![Edit deployed](/images/button_edit_deployed.png), or double-click it.

    **Tip:** To remove a deployable from all containers where it is mapped, select it in the left frame and click ![Remove deployed from all containers](/images/remove_deployed.png). To remove one mapped deployable, select it in the right frame and click ![Remove deployed](/images/button_remove_deployed.png).

1. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
1. Click **Next**. The deployment plan appears.
1. Click **Execute** to start the deployment. If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity. 

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## Skip a deployment step

If you have the appropriate permission in XL Deploy, you can adjust the deployment plan so that one or more steps are skipped. To do so, select the step, right-click, and select **Skip**. 

## Add a pause step

If you have the appropriate permission in XL Deploy, you can insert pause steps in the deployment plan. To do so, select the step just below the point where you want to pause, right-click, and select **Pause**.

## Stop or abort an executing deployment

To gracefully stop a deployment that is executing, click **Stop**. XL Deploy will wait until the currently executing step is finished and then stop the deployment.

To forcefully abort a deployment that is executing, click **Abort**. XL Deploy will attempt to abort the currently executing step and mark it as `FAILED`.

After stopping or aborting a deployment, you can **Continue** or **Cancel** it.

## Continue after a failed step

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`.

In some cases, you can click **Continue** to retry the failed step. If the step is incorrect and should be skipped, select it and click **Skip**, then click **Continue**.

## Roll back a deployment

To roll back a deployment that is in a `STOPPED` or `EXECUTED` state, click **Rollback** on the deployment plan. Executing the rollback plan will revert the deployment back to the previous version of the deployed application. It will also revert the deployeds created on execution.
