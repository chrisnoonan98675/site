---
title: Update a deployed application
categories:
xl-deploy
subject:
Deployment
tags:
deployment
application
package
update
weight: 191
---

XL Deploy always works with complete [deployment packages](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html#whats-in-an-application-deployment-package) that contain everything your applications need. You don't have to manually create a delta package to perform an update; instead, XL Deploy’s auto-flow engine calculates the delta between two packages automatically.

When updating a deployed application, XL Deploy identifies the configuration items in each package that differ between the two versions. It then generates an optimized deployment plan that only contains the steps that are needed to change these items.

When you want to update a deployed application, the process is the same whether you’re upgrading to a new version or downgrading to a previous version.

**Tip:** Watch the *Performing an update* video [here](https://www.youtube.com/watch?v=S8HuaxCJA00&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=6) and the *Reverting to a previous version* video [here](https://www.youtube.com/watch?v=zfmu75XocCg&list=PLIIv46GEoJ7ZvQd4BbzdMLaH0tc-gYyA1&index=7).

## Update an application using the default GUI

As of version 6.2.0, the default GUI is HTML-based.

To update a deployed application:

1. Expand **Environments**, and then expand the environment where the application is deployed.
1. Hover over the application, click ![Explorer action menu](/images/menu_three_dots.png), and then select **Update**. A new tab appears in the right pane.
1. In the new tab, select the desired version. You can filter the list of versions by typing in the **Search** box at the top.
1. Click **Continue**.
1. You can optionally:

    * View or edit the properties of a deployed item by double-clicking it.
    * Click **Deployment Properties** to configure properties such as [orchestrators](/xl-deploy/concept/understanding-orchestrators.html).
    * Click the arrow icon on the **Deploy** button and select **Modify plan** if you want to adjust the deployment plan by skipping steps or inserting pauses.

1. Click **Deploy** to start executing the plan immediately.

    If the server does not have the capacity to immediately start executing the plan, it will be in a `QUEUED` state until the server has sufficient capacity.

    If a step in the update fails, XL Deploy stops executing and marks the step as `FAILED`. Click the step to see information about the failure in the output log.

### Mapping deployables using the default GUI

* You can manually map a specific deployable by dragging it from the left side and dropping it on a specific container in the deployment execution screen. The cursor will indicate whether it is possible to map the deployable type to the container type.        

## Update an application using the XL Deploy legacy GUI

To update a deployed application:

1. Click **Deployment** in the top bar.
1. Locate the application under **Packages** and expand it to see the versions (deployment packages).
1. Locate the environment under **Environments**.
1. Drag the version of the application that you want to deploy and drop it on the environment where you want to deploy it. The application and environment appear in the deployment workspace.

    XL Deploy analyzes the application's [dependencies](/xl-deploy/concept/application-dependencies-in-xl-deploy.html) and the dependencies of the applications in the environment (supported in XL Deploy 5.1.0 and later). If the new version [does not satisfy](/xl-deploy/concept/how-xl-deploy-checks-application-dependencies.html) the dependencies of the applications that are already deployed, then XL Deploy will not deploy it.

    XL Deploy then automatically maps the deployables in the application to the appropriate containers in the environment.

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

For information about skipping steps or stopping an update, refer to [Deploy an application](/xl-deploy/how-to/deploy-an-application.html).
