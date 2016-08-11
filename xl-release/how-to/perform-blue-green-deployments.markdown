---
title: Perform blue green deployments
categories:
- xl-release
- xl-deploy
subject:
- Deployment
tags:
- blue green
- deployment pattern
---

This guide explains how to perform ["blue-green" deployments](http://martinfowler.com/bliki/BlueGreenDeployment.html) using XL Release and XL Deploy. Blue-green deployment is a pattern in which identical production environments known as Blue and Green are maintained, one of which is live at all times. Applications or features are deployed to and tested on the non-live environment (Green) before user traffic is diverted to it.

After users are using the new features in the Green environment, the Blue environment can be updated with more changes. If it is necessary to rollback the release in the Green environment, you can simply route user traffic back to the Blue environment.

In XL Release, you can implement a blue-green deployment by:

1. Selecting the environment in a User Input task
1. Using a precondition to determine which release tasks to execute

## Step 1 Add preconditions to tasks

[Preconditions](/xl-release/how-to/set-a-precondition-on-a-task.html) allow you to specify which tasks are performed during a release.

This is a an example of a [User Input](/xl-release/how-to/create-a-user-input-task.html) task (highlighted) that allows you to choose the Blue or Green environment. The selected environment is stored in a [release variable](/xl-release/how-to/create-release-variables.html) that is then used in preconditions on the subsequent Blue and Green [Sequential Blocks](/xl-release/how-to/create-a-sequential-group.html). The precondition determines whether XL Release executes the Blue or Green block. It will skip the block for the environment that was not chosen.

![Sample blue-green release](../images/bluegreen-sample-release.png)

## Step 2 Create and execute the release

When you create a release, leave the release variable for the environment choice empty. This allows it to be specified when the release reaches the User Input task.

![Sample blue-green release](../images/bluegreen-create-release.png)

When the release reaches the task, you enter the environment to use:

![Sample blue-green release](../images/bluegreen-choose-target.png)

The selected Sequential Block is executed, and the other block is skipped:

![Sample blue-green release](../images/bluegreen-complete.png)

**Tip:** XL Release also supports [global variables](/xl-release/how-to/configure-global-variables.html) that you could use to indicate whether the current live environment is Blue or Green across all of your releases. 
