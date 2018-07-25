---
title: Create and verify the deployment plan
subject:
Packaging
categories:
xl-deploy
tags:
application
package
deployment
weight: 200
no_index: true
---

<html>
<div id="userMap">

            <div class="content"><a href="preparing-your-application-for-xl-deploy.html"><div class="box box1">Preparing your application for XL Deploy</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="whats-in-a-deployment-package.html"><div class="box box2">What's in an application deployment package?</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-a-deployment-package.html"><div class="box box3">Create a deployment package</div></a></div>

            <div class="arrow">→</div>

            <div class="content" id="activeBox"><a href="create-and-verify-the-deployment-plan.html"><div class="box box3">Create and verify the deployment plan</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="troubleshoot-the-deployment-plan.html"><div class="box box5">Troubleshoot the deployment plan</div></a></div>

<div class="clearfix"></div>
</div>
</html>


Every plugin that is installed can contribute steps to the deployment plan. When XL Deploy creates the plan, it integrates these steps to ensure that the plugins work together correctly and the steps are in the right order.

To preview the deployment plan that XL Deploy will generate for your application, create a deployment plan and verify the steps.

### Check the target environment

Before you can create a deployment plan, the target environment for the deployment must be configured. To see the environments that have been defined in XL Deploy, go to **Repository** and expand **Environments**.

To verify the members of your target environment, double-click it and review its properties. The **Members** list shows the infrastructure items that make up the environment. If your target environment is not yet defined in XL Deploy, you can create it by right-clicking **Environments** and selecting **New**.

If the infrastructure members that make up your target environment are not available in the Repository, you can add them by either:

* Using the XL Deploy [Discovery feature](/xl-deploy/how-to/discover-middleware.html)
* Manually [adding the required configuration items](/xl-deploy/how-to/working-with-configuration-items.html#create-a-new-ci)

### Create the deployment plan

To create the deployment plan:

1. Click **Start a deployment**.
2. Under **Applications**, expand your application.
3. Select the desired version of your application and drag it to the left side of the Deployment Workspace.
4. Under **Environments**, select the environment where your application should be deployed and drag it to the right side of the Deployment Workspace.
5. Click ![image](../images/button_auto-map-new.png) to automatically map your application’s deployables to containers in the environment.
6. Double-click each mapped deployable to verify that its properties are configured as expected. Here, you can see the placeholders that XL Deploy found in your deployment package and the values that it will assign to them during the deployment process.

    ![Deployed properties](../images/deployment-workspace-deployed-properties-new.png)

7. Click **Preview** at to [Preview the deployment plan](/xl-deploy/how-to/preview-the-deployment-plan.html).
8. Review the steps in the Preview pane.
9. Optionally double click the step to preview the commands that XL Deploy will use to execute the step.
10. Click **Close preview** to return to the Deployment Workspace.
