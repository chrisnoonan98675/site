---
title: Deploy an application
categories:
xl-deploy
subject:
Deployment
tags:
deployment
application
package
since:
XL Deploy 5.0.0
weight: 184
---

After you have [defined your infrastructure](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html), [defined an environment](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html), and added an application to XL Deploy, you can perform the initial deployment of an application to an environment.

Notes:

* For information about using the Explorer to deploy an application, refer to [Using the Explorer](/xl-deploy/how-to/using-the-explorer.html).
* You can watch the *Performing an initial deployment* video [here](https://www.youtube.com/watch?v=pw17C9j60xY&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=4).
* A version of this topic is available for [XL Deploy 4.5.x and earlier](/xl-deploy/4.5.x/deploy-an-application-4.5.html).

## Deploy using the deployment wizard in the default GUI

As of version 6.2.0, the default GUI is HTML-based.

To deploy an application to an environment:

1. Expand **Applications**, and then expand the application that you want to deploy.
1. Hover over the desired deployment package or provisioning package, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deploy**. A new tab appears in the right pane.
1. In the new tab, select the target environment. You can filter the list of environments by typing in the **Search** box at the top.
1. If you are using XL Deploy 6.0.x or 6.1.0, click **Execute** to start executing the plan immediately. Otherwise, click **Continue**.

    ![Deployment mapping screen](images/explorer-deploy-02.png)

You can optionally:

* View or edit the properties of a deployed item by double-clicking it.
* Double-click an application to view the summary screen and click **Edit properties** to change the application properties.
* View the relationship between deployables and deployeds by clicking them.
* Click **Deployment Properties** to configure properties such as [orchestrators](/xl-deploy/concept/understanding-orchestrators.html).
* Click **Preview** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step. In preview mode, when you click a deployable, deployed, or a step, XL Deploy highlights all the related deployables, deployeds, and steps.
* Click the arrow icon on the **Deploy** button and select **Modify plan** to adjust the deployment plan by skipping steps or inserting pauses.

    ![Deployment pending plan](images/explorer-deploy-03.png)

1. Click **Deploy** to start executing the plan immediately.

    If the server does not have the capacity to immediately start executing the plan, it will be in a `QUEUED` state until the server has sufficient capacity.

    ![Deployment executed](images/explorer-deploy-04.png)

    If a step in the deployment fails, XL Deploy stops executing and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

### Use the deployment workspace   

As of XL Deploy version 7.2.0, you can open the deployment workspace by clicking the **Start a deployment** tile on the **Welcome** screen. A new **Deployment** tab is opened.
1. Locate the application under **Packages** in the left pane and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments** on the right pane.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it.
1. Click **Deploy** to start executing the plan immediately.

### Deploy latest version

As of XL Deploy 7.1.0, you can deploy the latest version of an application. Expand **Applications** in the left pane, hover over the desired application, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Deploy latest**.

**Note:** The deployment packages in XL Deploy are sorted using [Semantic Versioning (SemVer) 2.0.0](http://semver.org/) and lexicographically. The packages that are defined using SemVer are displayed first and other packages are sorted in lexicographical ordering.

The change in sorting deployment packages applies as of XL Deploy 7.1.0. After upgrading to XL Deploy version 7.1.0, the deployment packages will still be sorted lexicographically. You must create a new package for an application to apply the new method that includes both SemVer and lexicographical sorting.

When you want to deploy the [latest version of an application](/xl-deploy/latest/udmcireference.html#udmapplication), XL Deploy selects the last version of the deployment package from the list of sorted packages.

#### Example of deployment package sorting

* 1.0
* 2.0
* 2.0-alpha
* 2.0-alpha1
* 3.0
* 4.0
* 5.0
* 6.0
* 7.0
* 8.0
* 9.0
* 10.0
* 11.0

In this example, the latest version of the application is 11.0.

### Mapping deployables using the default GUI

* You can manually map a specific deployable by dragging it from the left side and dropping it on a specific container in the deployment execution screen. The cursor will indicate whether it is possible to map the deployable type to the container type.        

## Deploy using the legacy GUI

To deploy an application to an environment:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the deployment workspace.

    If the application has [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html) (supported in XL Deploy 5.1.0 and later), XL Deploy [analyzes them](/xl-deploy/concept/how-xl-deploy-checks-application-dependencies.html) and includes the deployables from the appropriate versions of the dependent applications.

    XL Deploy then automatically maps the deployables in the application to the appropriate containers in the environment.

1. Click **Execute** to immediately start the deployment.

You can also optionally:

* View or edit the properties of a deployed item by double-clicking it or by selecting it and clicking ![Edit deployed](/images/button_edit_deployed.png).
* Click **Deployment Properties** to select the [orchestrators](/xl-deploy/concept/understanding-orchestrators.html) that XL Deploy should use when generating the deployment plan.
* Click **Preview** to preview the deployment plan that XL Deploy generates. You can double-click each step to see the script that XL Deploy will use to execute the step. In preview mode, when you click a deployable, deployed, or a step, XL Deploy highlights all the related deployables, deployeds, and steps.
* Click **Advanced** if you want to adjust the deployment plan by skipping steps or inserting pauses.

**Note:** Double-clicking the **Advanced** button causes XL Deploy to generate two deployment tasks instead of one. This is a known issue. To remove the unneeded task, go to the [Task Monitor](/xl-deploy/how-to/monitor-and-reassign-deployment-tasks.html), open the pending task, and cancel it.

If the server does not have the capacity to immediately start executing the plan, the plan will be in a `QUEUED` state until the server has sufficient capacity.

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

### Mapping tips when using the legacy GUI

* Instead of dragging-and-dropping a deployment package on the environment, you can right-click the deployment package and select **Deploy**, then right-click the environment and select **Deploy to**.

* You can manually map deployables by dragging and dropping them on containers in the Workspace. The cursor will indicate whether it is possible to map the deployable type to the container type.

* To remove a deployable from all containers where it is mapped, select it in the left side of the Workspace and click ![Remove deployed from all containers](/images/remove_deployed.png).

* To remove one mapped deployable from a container, select it in the right side of the Workspace and click ![Remove deployed](/images/button_remove_deployed.png).

* Refer to [Configure XL Deploy client settings](/xl-deploy/how-to/configure-xl-deploy-client-settings.html) for information about changing the default mapping behavior.

## Skip a deployment step

If you have the [`task#skip_step` permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#local-permissions), you can adjust the deployment plan so that one or more steps are skipped. To do so, click the desired step and click **Skip**.

You can select multiple steps using the `CTRL/CMD` or `SHIFT` button and skip the steps by clicking **Skip selected steps**.

If you are using the legacy GUI and you want to skip a step, select the step, right-click, and select **Skip**.

## Add a pause step

You can insert pause steps in the deployment plan. To do so, hover over the step just above or below where you want to pause and click **Pause before** or **Pause after**.

If you are using the legacy GUI and you want to insert pause steps, select the step just above or below the point where you want to pause, right-click the step, and select **Pause before** or **Pause after**.

## Stop, abort, or cancel an executing deployment

You can stop or abort an executing deployment, then continue or cancel it. For information about doing so, refer to [Stopping, aborting, or canceling a deployment](/xl-deploy/how-to/stop-abort-or-cancel-a-deployment.html).

## Continue after a failed step

If a step in the deployment fails, XL Deploy stops executing the deployment and marks the step as `FAILED`.

In some cases, you can click **Continue** to retry the failed step. If the step is incorrect and should be skipped, select it and click **Skip**, and then click **Continue**.

## Rollback a deployment

To rollback a deployment that is in a `STOPPED`or `EXECUTED` state, click **Rollback** on the deployment plan.

You can perform one of three actions:
1. Select **Rollback** to open the rollback execution window and start executing the plan.
1. Select **Modify plan** if you want to make changes to the rollback plan. Click **Rollback** when you want to start the executing the plan.
1. Select **Schedule** to open the rollback schedule window. Select the date and time that you want to execute the rollback task. Specify the time using your local timezone. Click **Schedule**.

Executing the rollback plan will revert the deployment to the previous version of the deployed application (or applications, if the deployment involved multiple applications because of [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html)). It will also revert the deployeds created on execution.
