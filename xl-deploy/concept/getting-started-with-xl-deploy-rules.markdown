---
title: Getting started with XL Deploy rules
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- orchestrator
- planning
- step
since:
- XL Deploy 4.5.0
weight: 125
---

When preparing a deployment, XL Deploy must determine which actions, or steps, to take for the deployment, and in what order. This happens in three phases:

1. *Delta analysis* determines which deployables are to be deployed, resulting in a delta specification for the deployment.
2. *Orchestration* determines the order in which deployments of the deployables should happen (serially, in parallel, or interleaved).
3. *Planning* determines the specific steps that must be taken for the deployment of each deployable in the interleaved plan.

XL Deploy's *rules* system works with the [planning phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html). Rules allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured.

## Rules and orchestration

[Orchestration](/xl-deploy/concept/understanding-orchestrators.html) is important in the planning of a deployment. Orchestration is not part of the planning phase itself; rather, it happens immediately before the planning phase and after the delta analysis phase, and its output is used as input for how planning is done.

Delta analysis first determines which deployables need to be deployed, modified, deleted, or to remain unchanged. Each of these is called a `delta`. Orchestration determines the order in which the deltas should be processed. The result of orchestration is a tree-like structure of sub-plans, each of which is:

* A serial plan that contains other plans that will be executed one after another;
* A parallel plan that contains other plans that will be executed at the same time; or
* An interleaved plan that will contain the specific deployment steps after planning is done

The leaf nodes of the full deployment plan are always interleaved plans, and it is on these that the planning phase acts.

Planning provides steps for an interleaved plan, and this is done by invoking rules. Some rules will trigger depending on the delta under planning, while others may trigger independent of any delta. When a rule is triggered, it may or may not add one or more steps to the interleaved plan under consideration.

## Rules and steps

A step is a concrete action that XL Deploy performs to accomplish a task, such as *delete a file* or *execute a PowerShell script*. The plugins that are installed on the XL Deploy server define several step types and may also define rules that contribute steps to the plan. If you define your own rules, you can reuse the step types defined by the plugins.

You can also [disable rules](/xl-deploy/how-to/disable-a-rule.html) defined by the plugins.

Each step type is identified by a name. When you create a rule, you can add a step by referring to the step type's name.

Also every step has parameters, which are variable properties that can be determined during planning and passed to the step. The parameters that a step needs depend on the step type, but they all have at least an *order* and a *description*. The order determines when the step will run, and the description is how the step will be named when you inspect the plan.

## Rules and the planning context

Rules receive a reference to the XL Deploy planning context, which allows them to interact with the deployment plan. Rules use the planning context to contribute steps to the deployment plan or to add checkpoints that are needed for rollbacks.

The result of evaluating a rule is either that:

* The planning context is not affected, or
* Steps and side effects are added to the planning context

Typically, a rule only contributes steps to the plan in a few specific situations, when all of the conditions in its `conditions` section (if present) are met. Therefore, a rule will not always produce steps.

## How rules affect one another

Rules are applied one after another, depending on their scope. Rules operate in isolation, although they can share information through the [planning context](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html). The scope determines when and how often the rule is applied, as well as what data is available for the rule.

For example, a rule with the `deployed` scope is applied for every delta in the interleaved plan and has access to delta information such as the current operation (`CREATE`, `MODIFY`, `DESTROY`, or `NOOP`) and the current and previous instances of the deployed. The rule can use this information to determine whether it needs to add a step to the deployment plan.

**Important:** Be aware of the plan to which steps are contributed. Rules with the `deployed` and `plan` scope contribute to the same plan; therefore, the order of steps is important.

Rules cannot affect one another, but you can [disable rules](/xl-deploy/how-to/disable-a-rule.html). Every rule must have a name that is unique across the system.

### Pre-plan scope

A rule with the `pre-plan` scope is applied exactly once, at the start of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy pre-pends to the final deployment plan. A pre-plan-scoped rule is independent of deltas; however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.

### Deployed scope

A rule with the `deployed` scope is applied for each deployed in this interleaved plan; that is, for each delta. The steps that the rule contributes are added to the interleaved plan.

You must define a `type` and an `operation` in the `conditions` for each deployed-scoped rule. If a delta matches the type and operation, XL Deploy adds the steps to the plan for the deployed.

### Plan scope

A rule with the `plan` scope is applied once for every interleaved orchestration. It is independent of any single delta; however, it receives information about the deltas that are involved in the interleaved plan, which it can use to determine whether it should add steps to the plan.

The steps that the rule contributes are added to the interleaved plan related to the orchestration, along with the steps that are contributed by the deployeds in the orchestration.

### Post-plan scope

A rule with the `post-plan` scope is applied exactly once, at the end of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy appends to the final deployment plan. A post-plan-scoped rule is independent of deltas, however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.

## Types of rules

There are two types of rules:

* [XML rules](/xl-deploy/how-to/writing-xml-rules.html) allow you to define a rule using common conditions such as deployed types, operations, or the result of evaluating an expression. XML rules also allow you to define how a step must be instantiated by only writing XML.
* [Script rules](/xl-deploy/how-to/writing-script-rules.html) allow you to express rule logic in a Jython script. You can provide the same conditions as you can in XML rules. Depending on the scope of a script rule, it has access to the deltas or to the delta specification and the planning context.

The rule types are comparable in functionality. XML rules are more convenient because they define frequently used concepts in a simple way. Script steps are more powerful because they can include additional logic.

If you do not know which type of rule to use, try an XML rule first. If the XML rule is too restrictive, try a script rule.

For information about defining rules, refer to [How to define rules](/xl-deploy/how-to/how-to-define-rules.html).
