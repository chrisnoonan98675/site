---
title: Deploy an application (XL Deploy 4.5.x or earlier)
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- application
- package
deprecated:
- 4.5.x
---

After you have [defined your infrastructure](connect-xl-deploy-to-your-infrastructure.html), [defined an environment](create-an-environment-in-xl-deploy.html), and added an application to XL Deploy, you can perform the initial deployment of an application to an environment.

**Tip:** Watch the *Performing an initial deployment* video [here](https://www.youtube.com/watch?v=pw17C9j60xY&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=4).

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Deployed Applications**.
1. Drag the version of the application that you want to deploy to the left side of the Workspace.
1. Drag the environment where you want to deploy the application to the right side of the Workspace.
2. Click ![Auto-map button](/images/button_auto-map.png) to map the deployables in the deployment package to the containers in the environment.
1. Optionally view or edit the properties of a mapped deployable by double-clicking it or by selecting it and clicking ![Edit deployed](/images/button_edit_deployed.png).
1. Optionally click **Deployment Properties** to select the [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) that XL Deploy should use when generating the deployment plan.
1. Optionally click **Analyze** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
1. Click **Next**. The deployment plan appears.
1. Click **Execute** to start the deployment. If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity. 

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## Mapping tips

* Instead of dragging-and-dropping the application version on the environment, you can right-click the application version and select **Deploy** and right-click the environment and select **Deploy to**.
* You can manually map deployables by dragging and dropping them on containers in the Workspace. The cursor will indicate whether the deployable type can be mapped to the container type.
* To remove a deployable from all containers where it is mapped, select it in the left side of the Workspace and click ![Remove deployed from all containers](/images/remove_deployed.png).
* To remove one mapped deployable from a container, select it in the right side of the Workspace and click ![Remove deployed](/images/button_remove_deployed.png).

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
