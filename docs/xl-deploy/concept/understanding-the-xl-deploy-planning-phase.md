---
title: Understanding the XL Deploy planning phase
subject:
Deployment
categories:
xl-deploy
tags:
planning
deployment
weight: 181
---

The [planning phase](/xl-deploy/concept/deployment-overview-and-unified-deployment-model.html#phase-4-planning) takes place when the global structure of the deployment has been determined, and XL Deploy needs to fill in the steps needed to deploy the application. The goal of planning is to generate a deployment plan. It uses the structured deployment generated by the orchestration phase. Then, the plugins and rules contribute steps to the plan.

Xl Deploy generates a unique plan for every deployment. For that reason, it is not possible to save the plan, nor change the plan structure or steps directly.

## What affects the final plan?

There are many factors that influence the plan:

* The application, environment, and mappings configured by the deployer during specification
* The structuring performed by the orchestrators selected by the deployer
* The plugins and rules installed in XL Deploy, including any user-created plugins or rules
* Staging and satellites will contribute steps to the plan depending on the configuration of the environment
* At the end of the planning phase, XL Deploy simplifies the plan so it is easier for you to visualize

Plugins and rules are at the center of the planning phase. While you cannot change plugins or rules during deployment, you can indirectly configure them to influence the deployment plan. For example, by defining new rules.

## Rules and plugins

During the planning phase, XL Deploy evaluates all plugins and rules to determine which steps should be added to the plan. XL Deploy has a structured way of evaluating rules and plugins. Evaluation is performed in sequentially executed stages.

## Stages in rules/plugin planning

### Preplanning contributors

During preplanning, steps can be contributed based on the entire deployment. As such, the preprocessor can make a decision based on the entire deployment. All preplan contributors will be evaluated just once, and the steps contributed will be put to a single subplan that is prepended to the final plan. Examples of such steps are sending an email before starting the deployment or performing pre-flight checks on CIs in that deployment.

### Subplan contributors

For every subplan, the subplan contributors are evaluated. The subplan contributor has access to all deltas in the subplan. For example a subplan contributor can contribute container stop and start steps to a subplan using the information from the deltas.

### Type contributors

A type contributor will be evaluated for every configuration item of a specific type in the deployment. It can contribute steps to the subplan it is part of. The type contributor has access to its delta and configuration item properties. For example a type contributor can upload a file and copy it to a specific location.

### Post-planning contributors

Post-processing is similar to preprocessing, but allows a plugin to add one or more steps to the very end of a plan. All post-plan contributors will be evaluated just once, and the steps contributed will be put to a single subplan that is appended to the final plan. A post-processor could for instance add a step to send a mail once the deployment has been completed.

## Step orders

The step order is determined by the plugin or rule that contributes the step. Within a subplan, steps are ordered by their step order. Notice that step orders do not affect steps that are not in the same subplan.
