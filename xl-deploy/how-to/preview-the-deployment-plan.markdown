---
title: Preview the deployment plan
categories:
- xl-deploy
subject:
- Deployment
tags:
- deployment
- planning
- gui
- preview
weight: 186
---

When you set up an initial deployment, an upgrade, or an undeployment, you can use the Plan Analyzer to preview the deployment plan that XL Deploy generated based on the deployment configuration. As you map deployables to containers in the deployment configuration, the Plan Analyzer will update and show changes to the plan.

**Note:** The Plan Analyzer is read-only.

## Open the Plan Analyzer

To open the Plan Analyzer from the Deployment Workspace:

* In XL Deploy 4.5.x or earlier, click **Analyze**
* In XL Deploy 5.0.0 or later, click **Preview**

## Match steps in the plan to deployeds

To see which steps in the deployment plan are related to a specific [deployed](/xl-deploy/concept/understanding-deployables-and-deployeds.html), click the deployed. To see which deployed is related to a specific step, click the step.

![Highlighted deployed and steps in Plan Analyzer](images/planalyzer-clickondeployed.png)

## Preview a step in the plan

For deployment plan steps that require a script, you can preview the script that XL Deploy will execute. To do so, double-click ![Step preview button](/images/button_step_preview.png) next to the step. Note that this requires the `task#preview_step` [global permission](/xl-deploy/concept/roles-and-permissions-in-xl-deploy.html#global-permissions).

The step preview also shows:

* The [order](/xl-deploy/concept/steps-and-steplists-in-xl-deploy.html#steplist) of the step. The step order determines the sequence of steps in the plan, with lower order numbers coming before higher ones. The selected [orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) can influence this.
 * The location of the script template, relative to XL Deploy's classpath; for example, relative to `SERVER_HOME/ext` or packaged in the relevant plugin.

This is an example of a step preview:

![Sample Plan Analyzer step preview](images/planalyzer-datasource.png)

If preview is not available, ![Step preview unavailable](/images/button_step_preview_unavailable.png) appears next to the step. To see the step order when preview is not available, hover the mouse pointer over the step and wait for the tooltip to appear. For example:

![Sample Plan Analyzer step tooltip](images/planalyzer-tooltip.png)

## Using orchestrators

The Plan Analyzer is useful when you are applying [orchestrators](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) to the deployment plan. Orchestrators are used to control the sequence of the generated plan. They are used mainly when dealing with more than one server.

For example, deploying an application to an environment that contains two JBoss servers results in a default deployment plan like this:

![Sample deployment plan with default orchestrator](images/planalyzer-twoservers.png)

In this plan, both servers are stopped simultaneously. This happens because the default orchestrator treats all target middleware as a single pool, so everything is started, stopped, and updated together.

You can change this by applying a different orchestrator. Click **Deployment Properties** to see the orchestrators are available.

This is an example of the deployment plan with the `sequential-by-container` orchestrator applied:

![image](images/planalyzer-onebyone.png)

## Start the deployment

When you are satisfied with the deployment plan as it appears in the Plan Analyzer, start the deployment by clicking:

* **Next** in XL Deploy 4.5.x or earlier
* **Execute** in XL Deploy 5.0.0 or later

![image](images/planalyzer-deploy.png)

This is the final plan that will be executed to perform the deployment. Here, you can adjust the plan by skipping steps or inserting pauses; right-click the plan to do so.
