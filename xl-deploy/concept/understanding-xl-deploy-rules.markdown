---
title: Understanding XL Deploy rules
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
- 4.5.0
---

When preparing a deployment, XL Deploy must determine which actions, or steps, to take for the deployment, and in what order. This happens in three phases:

1. *Delta analysis* determines which deployables are to be deployed, resulting in a delta specification for the deployment.
2. *Orchestration* determines the order in which deployments of the deployables should happen (serially, in parallel, or interleaved).
3. *Planning* determines the specific steps that must be taken for the deployment of each deployable in the interleaved plan.

XL Deploy's *rules* system works with the planning phase. Rules allow you to use XML or Jython to specify the steps that belong in a deployment plan and how the steps are configured.

This manual describes how the rules system works and how you can influence the steps that will be included in the deployment plan. 

## Orchestration

Orchestration is important in the planning of a deployment. Orchestration is not part of the planning phase itself; rather, it happens immediately before the planning phase and after the delta analysis phase, and its output is used as input for how planning is done.
 
Delta analysis first determines which deployables need to be deployed, modified, deleted, or to remain unchanged. Each of these is called a `delta`. Orchestration determines the order in which the deltas should be processed. The result of orchestration is a tree-like structure of sub-plans, each of which is:

* A serial plan that contains other plans that will be executed one after another;
* A parallel plan that contains other plans that will be executed at the same time; or
* An interleaved plan that will contain the specific deployment steps after planning is done

The leaf nodes of the full deployment plan are always interleaved plans, and it is on these that the planning phase acts.

Planning provides steps for an interleaved plan, and this is done by invoking rules. Some rules will trigger depending on the delta under planning, while others may trigger independent of any delta. When a rule is triggered, it may or may not add one or more steps to the interleaved plan under consideration.

## Where to define rules

You define and disable rules in `xl-rules.xml`, which is stored in the `<XLDEPLOY_HOME>/ext` directory. XL Deploy plugin JARs can also contain `xl-rules.xml`. When the XL Deploy server starts, it scans all `xl-rules.xml` files and registers their rules.

## How to define rules

Each rule:
 
* Must have a name that is unique across the whole system
* Must have a scope
* Must define the conditions under which it will run
* Can use the planning context to influence the resulting plan
 
The `xl-rules.xml` file has the default namespace `xmlns="http://www.xebialabs.com/deployit/xl-rules"`. The root element must be `rules`, under which `rule` and `disable-rule` elements are located.

## Rescan the rules file

You can configure XL Deploy to rescan all rules on the server whenever you change the `<XLDEPLOY_HOME>/ext/xl-rules.xml` file. To do so, edit the `file-watch` setting in the `<XLDEPLOY_HOME>/conf/planner.conf` file. For example, to check every 5 seconds if `xl-rules.xml` has been modified:
    
    xl {
        file-watch {
            interval = 5 seconds
        }
    }

By default, the interval is set to 0 seconds. This means that XL Deploy will not automatically rescan the rules when `<XLDEPLOY_HOME>/ext/xl-rules.xml` changes.

If XL Deploy is configured to automatically rescan the rules and it finds that `xl-rules.xml` has been modified, it will rescan all rules in the system. By auto-reloading the rules, it is easy to do some experimenting until you are satisfied with your set of rules.

**Note:** If you modify `planner.conf`, you must restart the XL Deploy server.

## Rules and steps

A step is a concrete action that XL Deploy performs to accomplish a task, such as *delete a file* or *execute a PowerShell script*. The plugins that are installed on the XL Deploy server define several step types and may also define rules that contribute steps to the plan. If you define your own rules, you can reuse the step types defined by the plugins.

You can also disable rules defined by the plugins.

Each step type is identified by a name. When you create a rule, you can add a step by referring to the step type's name. 

Also every step has parameters, which are variable properties that can be determined during planning and passed to the step. The parameters that a step needs depend on the step type, but they all have at least an *order* and a *description*. The order determines when the step will run, and the description is how the step will be named when you inspect the plan.

## Rules and the planning context

Rules receive a reference to the XL Deploy planning context, which allows them to interact with the deployment plan. Rules use the planning context to contribute steps to the deployment plan or to add checkpoints that are needed for rollbacks.

The result of evaluating a rule is either that:

* The planning context is not affected, or 
* Steps and side effects are added to the planning context

Typically, a rule only contributes steps to the plan in a few specific situations, when all of the conditions in its `conditions` section (if present) are met. Therefore, a rule will not always produce steps.

## How rules affect one another

Rules are applied one after another, depending on the scope. Rules operate in isolation, although they can share information through the planning context. Rules can not affect one another, but you can disable rules. Every rule must have a name that is unique across the system.

## Rule scope

The input that a rule receives depends on the planning context and on the rule's scope. The scope determines when and how often the rule is applied, as well as what data is available for the rule.

For example, a rule with the `deployed` scope is applied for every delta in the interleaved plan and has access to delta information such as the current operation (`CREATE`, `MODIFY`, `DESTROY`, or `NOOP`) and the current and previous instances of the deployed. The rule can use this information to determine whether it needs to add a step to the deployment plan.

**Important:** Be aware of the plan that steps are contributed to. Deployed rules and plan rules contribute to the same plan; therefore, the order of steps is important.

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

* **XML rules** allow you to define a rule using common conditions such as deployed types, operations, or the result of evaluating an expression. XML rules also allow you to define how a step must be instantiated by only writing XML.
* **Script rules** allow you to express rule logic in a Jython script. You can provide the same conditions as you can in XML rules. Depending on the scope of a script rule, it has access to the deltas or to the delta specification and the planning context.

The rule types are comparable in functionality. XML rules are more convenient because they define frequently used concepts in a simple way. Script steps are more powerful because they can include additional logic.
 
If you do not know which type of rule to use, try an XML rule first. If the XML rule is too restrictive, try a script rule.
