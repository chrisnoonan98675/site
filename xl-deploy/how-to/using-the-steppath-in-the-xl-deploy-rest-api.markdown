---
title: Using the stepPath in the XL Deploy REST API
categories:
- xl-deploy
subject:
- Customization
tags:
- api
- cli
- step
- task
since:
- 4.0.x
---

The `TaskBlockService` in the XL Deploy [REST API](/xl-deploy/latest/rest-api) allows you to retrieve information about a step in a deployment plan with [`GET /tasks/v2/{taskid}/step/{stepPath}`](https://docs.xebialabs.com/generated/xl-deploy/5.0.x/rest-api/com.xebialabs.deployit.engine.api.TaskBlockService.html#/tasks/v2/{taskid}/step/{stepPath}:GET). For example, in a CLI script, this can be called as:

    task2.step(String taskId, String stepPath)

The `stepPath` has three parts: the root, the number of the task block, and the number of the step in the block. These parts are separated by underscores.

## Examples

In the following example, the deployment task has one block. The `stepPath` for the first step is `0_1`, while the `stepPath` for the second step is `0_2`. The API call would be:

    task2.step("1582b3b8-096c-48ba-a1bd-85aed09b0ec9", "0_1")

![Deployment plan with no step blocks](images/plan-with-no-step-blocks.png)

In the following example, the deployment task has two blocks, and each block has one step. The `stepPath` for the first step is `0_1_1`, while the `stepPath` for the second step is `0_2_1`. The API calls would be:

    task2.step("4b672090-366e-4b37-b631-9f170f175610", "0_1_1")
    task2.step("4b672090-366e-4b37-b631-9f170f175610", "0_2_1")   

![Deployment plan with no step blocks](images/plan-with-two-step-blocks.png)
