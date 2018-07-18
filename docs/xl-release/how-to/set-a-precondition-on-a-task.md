---
title: Set a precondition on a task
categories:
- xl-release
subject:
- Tasks
tags:
- task
- script
- jython
weight: 463
---

You can control the execution flow by setting a precondition on a task. A precondition is an `if` statement for tasks, written in Jython script code.

If the precondition evaluates to **True**, the task is started. If the precondition evaluates to **False**, the task is skipped. If an exception is raised or a compilation error occurs when XL Release is evaluating the precondition script, the task is **failed**. You can fix the script and **retry** the task.

![Manual Task With Precondition](../images/manual-task-details.png)

There are two ways of writing preconditions.

## Boolean expression

A Boolean expression is restricted to single statement script. For example:

    releaseVariables['jobStatus'] == 'Success'

The task will only be started if the variable `jobStatus` is equal to 'Success'.

## Multi-line script

If you need a more complicated script, you must set the **result** variable. For example:

    if releaseVariables['OS'] == 'Linux' and releaseVariables['pingResult'] == '0':
        result = True
    else:
        result = False

## Disabling preconditions

You can disable precondition on certain task types. For example, to disable precondition on parallel groups, modify `synthetic.xml` as follows:

    <type-modification type="xlrelease.ParallelGroup">
        <property name="preconditionEnabled" kind="boolean" required="true" default="false" hidden="true"
            description="Whether preconditions should be enabled"/>
    </type-modification>
