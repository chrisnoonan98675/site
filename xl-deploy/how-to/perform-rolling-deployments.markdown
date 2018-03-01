---
title: Perform rolling deployments
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment pattern
- orchestration
---

This guide explains how to perform "rolling" deployments using XL Deploy. Rolling deployments are generally used to implement changes to infrastructure in an incremental fashion. Rolling deployments often take advantage of load balancers to minimize disruption to the running application. XL Deploy can orchestrate deployment plans to accommodate this requirement.

In XL Deploy, you can implement a rolling deployment by:

1. Applying an orchestrator to deploy to each middleware container sequentially
1. Inserting pauses between deployment to each container

## Step 1 Set up the deployment with an orchestrator

In XL Deploy, the [orchestration](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) feature allows a deployment plan to be generated in different ways to satisfy requirements such as rolling deployments, canary deployments, and blue/green deployments.

You can apply one or more orchestrators to an application, and you can parameterize them so you have ultimate flexibility in how a deployment to your environments is performed.

To specify an orchestrator at deployment time:

1. [Set up the deployment](/xl-deploy/how-to/deploy-an-application.html) in the XL Deploy GUI by selecting a deployment package and an environment.
1. Click **Preview** to see a live preview of the generated deployment plan.
1. Click **Deployment Properties** and select the `sequential-by-container` orchestrator.
1. Click **OK**.

![Sequential-by-container orchestrator](images/rolling-select-orchestrator.png)

## Step 2 Review the plan

After you select an orchestrator, XL Deploy updates the preview of the deployment plan. While reviewing the plan, you will see that the application will be deployed to one container, the next container, and so on.

![Sample rolling deployment plan](images/rolling-preview.png)

## Step 3 Add pauses to the plan

XL Deploy allows you to insert [pause steps](/xl-deploy/how-to/deploy-an-application.html#add-a-pause-step) in the deployment plan, so you can progress through the deployment at your own pace. Each pause step halts the deployment process, and you must click **Continue** to resume it.

To add pause steps to the deployment plan:

1. Click **Advanced**.
1. Right-click the step before which or after which you want to insert a pause (you may need to first expand the blocks of steps in the plan).
1. Select **Pause Before** or **Pause After**.

![Adding a pause step to a deployment plan](images/rolling-pause.png)

## Step 5 Execute the plan

To start the deployment, click **Execute**. Each time XL Deploy reaches a pause step, it will stop execution, giving you time to verify the results of that part of the deployment. When you are ready to resume deployment execution, click **Continue**.

![Executing a rolling deployment](images/rolling-execution.png)

## Specifying orchestrators in advance

Instead of specifying orchestrators when you set up the deployment, you can specify them as a property of the deployment package:

1. Click **Explorer** in the top bar of the XL Deploy GUI.
1. Expand **Applications**, then expand the desired application, and double-click the version you want to update.
1. Enter the exact name (case-sensitive) of an orchestrator in the **Orchestrator** box on the **Common** section. Alternatively, you can enter a placeholder that will be filled by a dictionary; for example, `{% raw %}{{ orchestrator }}{% endraw %}`.

![Specifying an orchestrator on a deployment package](images/rolling-application-property.png)

**Tip:** You can see the names of the available orchestrators by clicking **Deployment Properties** when setting up a deployment.
