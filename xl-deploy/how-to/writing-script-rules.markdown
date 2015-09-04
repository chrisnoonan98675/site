---
title: Writing script rules
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
---

A script rule adds steps and checkpoints to a plan by running a Jython script that calculates which steps and checkpoints to add.

**Important:** The script in a script rule only runs during the *planning* phase. The purpose of the script is to provide steps for the final plan to execute, *not* to take deployment actions. Script rules do not interact with XL Deploy's execution phase, although some of the steps executed in that phase may involve executing scripts (such as a `jython` step).

## Define steps in script rules

A script rule uses the following format in `xl-rules.xml`:

* A `rule` tag with `name` and `scope` attributes, both of which are required.
* An optional `conditions` tag with:
    * One or more `type` tags that identify the UDM types that the rule is restricted to. `type` is required if the scope is `deployed`; otherwise, you must omit it. The UDM type name must refer to a *deployed* type (not a *deployable*, *container*, or other UDM type).
    * One or more `operation` tags that identify the operations that the rule is restricted to. The operation can be `CREATE`, `MODIFY`, `DESTROY`, or `NOOP`. `operation` is required if the scope is `deployed`; otherwise, you must omit it.
    * An optional `expression` tag with an expression in Jython that defines a condition upon which the rule will be triggered. This tag is optional for all scopes. If you specify an `expression`, it must evaluate to a Boolean value. 
* A `planning-script-path` child tag that identifies a script file that is available on the class path (in the `<XLDEPLOY_HOME>/ext/` directory).
 
Every script is run in isolation; that is, you cannot pass values directly from one script to another.

## Sample script rule: Successfully created artifact

This is an example of a script that is executed for every deployed that is involved in the deployment. The step of type `noop` will only be added for new deployeds (operation is `CREATE`) that derive from the type `udm.BaseDeployedArtifact`, as defined by the `type` element. Creating a step is done through the factory object `steps`. Addition of the step is performed through `context`, which represents the planning context (*not* the execution context).

    <rules xmlns="http://www.xebialabs.com/xl-deploy/rules">
        <rule name="SuccessBaseDeployedArtifact" scope="deployed">
            <conditions>
                <type>udm.BaseDeployedArtifact</type>
                <operation>CREATE</operation>
            </conditions>
            <planning-script-path>planning/SuccessBaseDeployedArtifact.py</planning-script-path>
        </rule>    

Where `planning/SuccessBaseDeployedArtifact.py`, which is stored in the `<XLDEPLOY_HOME>/ext/` directory, has following content:

    step = steps.noop(description = "A dummy step to indicate that some new artifact was created on the target environment", order = 100)
    context.addStep(step)
