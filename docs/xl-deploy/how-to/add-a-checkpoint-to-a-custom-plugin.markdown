---
pre_rules: true
title: Add a checkpoint to a custom plugin
categories:
- xl-deploy
subject:
- Plugins
tags:
- java
- plugin
- ci
- checkpoint
weight: 223
---

As a plugin author, you typically execute multiple steps when your CI is created, destroyed or modified. You can let XL Deploy know when the action performed on your CI is complete, so that XL Deploy can store the results of the action in its repository. If the deployment plan fails halfway through, XL Deploy can generate a customized rollback plan that contains steps to rollback only those changes that are already committed.

XL Deploy must be told to add a checkpoint after a step that completes the operation on the CI. Once the step completes successfully, XL Deploy will checkpoint (commit to the repository) the operation on the CI (the `destroy` operation will remove it, the `create` operation will create it and the `modify` operation updates it) and generate rollback steps for it if needed.

Here's an example of adding a checkpoint:

    @Create
    public void executeCreateCommand(DeploymentPlanningContext ctx, Delta delta) {
        ctx.addStepWithCheckpoint(new ExecuteCommandStep(order, this), delta);
    }

This informs XL Deploy to add the specified step and to add a `create` checkpoint. Here's another example:

    @Destroy
    public void destroyCommand(DeploymentPlanningContext ctx, Delta delta) {
        if (undoCommand != null) {
            DeployedCommand deployedUndoCommand = createDeployedUndoCommand();
            ctx.addStepWithCheckpoint(new ExecuteCommandStep(undoCommand.getOrder(), deployedUndoCommand), delta);
        } else {
            ctx.addStepWithCheckpoint(new NoCommandStep(order, this), delta);
        }
    }

XL Deploy will add a `destroy` checkpoint after the created step.

Checkpoints with the `modify` action on CIs are a bit more complicated since a `modify` operation is frequently implemented as a combination of `destroy` (remove the old version of the CI) and a `create` (create the new version). In this case, we need to tell XL Deploy to add a checkpoint after the step removing the old version as well as a checkpoint after creating the new one. More specifically, we need to tell XL Deploy that the first checkpoint of the `modify` operation is really a `destroy` checkpoint. This is how that looks:


    @Modify
    public void executeModifyCommand(DeploymentPlanningContext ctx, Delta delta) {
        if (undoCommand != null && runUndoCommandOnUpgrade) {
            DeployedCommand deployedUndoCommand = createDeployedUndoCommand();
            ctx.addStepWithCheckpoint(new ExecuteCommandStep(undoCommand.getOrder(), deployedUndoCommand), delta, Operation.DESTROY);
        }

        ctx.addStepWithCheckpoint(new ExecuteCommandStep(order, this), delta);
    }

Note that additional parameter `Operation.DESTROY` in the `addStepWithCheckpoint` invocation that lets XL Deploy know the checkpoint is a `destroy` checkpoint even though the delta passed in represents a `modify` operation.

The final step uses the `modify` operation from the delta to indicate the CI is now present and.

## Implicit checkpoints

If you do not specify any checkpoints for a delta, XL Deploy will add a checkpoint to the last step of the delta. Let's see how it works based on an example.

We perform the initial deployment of a package that contains an SQL script and a WAR file. The deployment plan looks like:

1. Execute the SQL script.
1. Upload the WAR file to the host where the servlet container is present.   
1. Register the WAR file with the servlet container.

Without checkpoints, XL Deploy does not know how to roll back this plan if it fails on a step. XL Deploy adds implicit checkpoints based on the two delta in the plan: a new SQL script and a new WAR file. Step 1 is related to the SQL script, while steps 2 and 3 are related to the WAR file. XL Deploy adds a checkpoint to the last step of each delta. The resulting plan looks like:

1. Execute the SQL script and checkpoint the SQL script.
1. Upload the WAR file to the host where the servlet container is present.   
1. Register the WAR file with the servlet container and checkpoint the WAR file.

If step 1 was executed successfully but step 2 or 3 failed, XL Deploy knows it must roll back the executed SQL script, but not the WAR file.
