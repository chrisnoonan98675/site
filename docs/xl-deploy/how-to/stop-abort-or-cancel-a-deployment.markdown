---
title: Stopping, aborting, or canceling a deployment
subject:
- Deployment
categories:
- xl-deploy
tags:
- deployment
- gui
- rollback
weight: 188
---

## Stop a running deployment

To gracefully stop a running deployment, click **Stop** on the deployment plan. XL Deploy will wait until the step it is currently executing is finished, then it will stop the deployment.

After you stop a deployment, you can:

* Click **Continue** to continue the deployment from the next step
* Click **Rollback** to roll back the steps that XL Deploy has already executed

  You can perform one of three actions:
  1. Select **Rollback** to open the rollback execution window and start executing the plan.
  1. Select **Modify plan** if you want to make changes to the rollback plan. Click **Rollback** when you want to start the executing the plan.
  1. Select **Schedule** to open the rollback schedule window. Select the date and time that you want to execute the rollback task. Specify the time using your local timezone. Click **Schedule**.

* Click **Cancel** to cancel the deployment

For important information about canceling a deployment, refer to [Cancel a partially completed deployment](#cancel-a-partially-completed-deployment).

## Abort a running deployment

If you cannot gracefully stop a running deployment (for example, due to a hanging script), you can forcefully abort it. To do so, click **Abort** on the deployment plan. XL Deploy will attempt to abort the step it is currently executing. After the step is aborted, it is marked as `FAILED`.

After you abort a deployment, you can:

* Click **Continue** to continue the deployment from the aborted step
* Select **Skip** to skip the aborted step and then click **Continue** to continue the deployment from the next step
* Click **Rollback** to roll back the steps that XL Deploy has already executed

  You can perform one of three actions:
  1. Select **Rollback** to open the rollback execution window and start executing the plan.
  1. Select **Modify plan** if you want to make changes to the rollback plan. Click **Rollback** when you want to start the executing the plan.
  1. Select **Schedule** to open the rollback schedule window. Select the date and time that you want to execute the rollback task. Specify the time using your local timezone. Click **Schedule**.

* Click **Cancel** to cancel the deployment

## Cancel a partially completed deployment

If you stop or abort a deployment, or if a deployment fails, you can click **Cancel** to cancel it. However, this means that your application will be partially deployed. In XL Deploy, you will see the application as being deployed in the environment; however, the application may not work as expected.

Instead of canceling a deployment, it is recommended that you either:

* Click **Rollback** and execute the rollback plan

  You can perform one of three actions:
  1. Select **Rollback** to open the rollback execution window and start executing the plan.
  1. Select **Modify plan** if you want to make changes to the rollback plan. Click **Rollback** when you want to start the executing the plan.
  1. Select **Schedule** to open the rollback schedule window. Select the date and time that you want to execute the rollback task. Specify the time using your local timezone. Click **Schedule**.

* Correct the cause of the failed step, then click **Continue** to continue the deployment (click the failed step to see information about it)
