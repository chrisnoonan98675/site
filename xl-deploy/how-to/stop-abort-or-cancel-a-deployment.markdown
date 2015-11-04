---
title: Stopping, aborting, or canceling a deployment
subject:
- Deployment
categories:
- xl-deploy
tags:
- deployment
- gui
---

## Stop a running deployment

To gracefully stop a running deployment, click **Stop** under the deployment plan. XL Deploy will wait until the step it is currently executing is finished, then it will stop the deployment.

After you stop a deployment, you can:

* Click **Continue** to continue the deployment from the next step
* Click **Rollback** to roll back the steps that XL Deploy has already executed
* Click **Cancel** to cancel the deployment

For important information about canceling a deployment, refer to [Cancel a partially completed deployment](#cancel-a-partially-completed-deployment).

## Abort a running deployment

If you cannot gracefully stop a running deployment (for example, due to a hanging script), you can forcefully abort it. To do so, click **Abort** under the deployment plan. XL Deploy will attempt to abort the step it is currently executing. After the step is aborted, it is marked as *failed*.

After you abort a deployment, you can:

* Click **Continue** to continue the deployment from the aborted step
* Right-click the aborted step and select **Skip step** to skip it, then click **Continue** to continue the deployment from the next step
* Click **Rollback** to roll back the steps that XL Deploy has already executed
* Click **Cancel** to cancel the deployment

For important information about canceling a deployment, refer to [Cancel a partially completed deployment](#cancel-a-partially-completed-deployment).

## Cancel a partially completed deployment

If you stop or abort a deployment, or if a deployment fails, you can click **Cancel** to cancel it. However, this means that your application will be partially deployed. In XL Deploy, you will see the application as being deployed in the environment; however, the application may not work as expected.

Instead of canceling a deployment, it is recommended that you either:

* Click **Rollback** and execute the rollback plan
* Correct the cause of the failed step, then click **Continue** to continue the deployment (click the failed step to see information about it)
