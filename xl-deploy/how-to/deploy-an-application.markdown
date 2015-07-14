---
title: Deploy an application
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- application
- package
since:
- 5.0.0
---

After you have [defined your infrastructure](connect-xl-deploy-to-your-infrastructure.html), [defined an environment](create-an-environment-in-xl-deploy.html), and added an application to XL Deploy, you can perform the initial deployment of an application to an environment.

**Tip:** Watch the *Performing an initial deployment* video [here](http://vimeo.com/97815293).

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the deployment workspace.

    If the application has [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html) (supported in XL Deploy 5.1.0 and later), XL Deploy [analyzes them](/xl-deploy/concept/how-xl-deploy-checks-application-dependencies.html) and includes the deployables from the appropriate versions of the dependent applications. Applications will be deployed in reverse topological order to ensure that dependent applications are deployed first.

    XL Deploy then automatically maps the deployables in the application to the appropriate containers in the environment.
    
1. Click **Execute** to immediately start the deployment.

You can also optionally:

* View or edit the properties of a mapped deployable by double-clicking it or by selecting it and clicking ![Edit deployed](/images/button_edit_deployed.png).
* Click **Deployment Properties** to select the [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) that XL Deploy should use when generating the deployment plan.
* Click **Preview** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step.
* Click **Advanced** if you want to adjust the deployment plan by skipping steps or inserting pauses.

If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity. 

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

## Mapping tips

* Instead of dragging-and-dropping a deployment package on the environment, you can right-click the deployment package and select **Deploy**, then right-click the environment and select **Deploy to**.
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

To roll back a deployment that is in a `STOPPED` or `EXECUTED` state, click **Rollback** on the deployment plan. Executing the rollback plan will revert the deployment to the previous version of the deployed application (or applications, if the deployment involved multiple applications because of [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html)). It will also revert the deployeds created on execution.
