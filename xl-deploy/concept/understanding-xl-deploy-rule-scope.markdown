---
title: Understanding XL Deploy rule scope
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- planning
- step
since:
- XL Deploy 4.5.0
---

XL Deploy [rules](/xl-deploy/concept/understanding-xl-deploy-rules.html) allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured.

The input that a rule receives depends on the [planning context](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html) and on the rule's *scope*. The scope determines when and how often the rule is applied, as well as what data is available for the rule.

For example, a rule with the `deployed` scope is applied for every delta in the interleaved plan and has access to delta information such as the current operation (`CREATE`, `MODIFY`, `DESTROY`, or `NOOP`) and the current and previous instances of the deployed. The rule can use this information to determine whether it needs to add a step to the deployment plan.

**Important:** Be aware of the plan that steps are contributed to. Deployed rules and plan rules contribute to the same plan; therefore, the order of steps is important.

## Pre-plan scope

A rule with the `pre-plan` scope is applied exactly once, at the start of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy pre-pends to the final deployment plan. A pre-plan-scoped rule is independent of deltas; however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.

## Deployed scope

A rule with the `deployed` scope is applied for each deployed in this interleaved plan; that is, for each delta. The steps that the rule contributes are added to the interleaved plan.

You must define a `type` and an `operation` in the `conditions` for each deployed-scoped rule. If a delta matches the type and operation, XL Deploy adds the steps to the plan for the deployed.

## Plan scope

A rule with the `plan` scope is applied once for every interleaved orchestration. It is independent of any single delta; however, it receives information about the deltas that are involved in the interleaved plan, which it can use to determine whether it should add steps to the plan.

The steps that the rule contributes are added to the interleaved plan related to the orchestration, along with the steps that are contributed by the deployeds in the orchestration.

## Post-plan scope

A rule with the `post-plan` scope is applied exactly once, at the end of the planning stage. The steps that the rule contributes are added to a single plan that XL Deploy appends to the final deployment plan. A post-plan-scoped rule is independent of deltas, however, it receives a reference to the complete delta specification of the plan, which it can use to determine whether it should add steps to the plan.
