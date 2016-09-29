---
title: Steps and step lists in XL Deploy
subject:
- Deployment
categories:
- xl-deploy
tags:
- step
- deployment
- planning
weight: 182
---

A *step* is a concrete action to be performed to accomplish a task. All steps for a particular deployment are grouped together in a _steplist_.

XL Deploy includes many step implementations for common actions. Steps are contributed by plugins, based on the deployment that is being performed. Middleware-specific steps are contributed by the plugins.

The following are examples of steps:

* Copy file `/foo/bar` to `host1`, directory `/bar/baz`
* Install `petclinic.ear` on the WebSphere Application Server on `was1`
* Restart the Apache HTTP server on `web1`

You can perform actions on steps, but most interaction with the step will be done by the task itself.

You can mark a step to be skipped by the task. When the task is executing and the skipped step becomes the current step, the task will skip the step without executing it. The step will be marked _skipped_, and the next step in line will be executed.

**Note:** A step can only be skipped when the step is _pending_, _failed_, or _paused_.

![Step state](images/xl_deploy_step_state_diagram.png)

## Steplist

A steplist is a sequential list of _steps_ that are contributed by one or more _plugins_ when a deployment is being planned.

All steps in a steplist are ordered in a manner similar to `/etc/init.d` scripts in Unix, with low-order steps being executed before higher-order steps. XL Deploy predefines the following orders for ease of use:

* 0 = PRE_FLIGHT
* 10 = STOP_ARTIFACTS
* 20 = STOP_CONTAINERS
* 30 = UNDEPLOY_ARTIFACTS
* 40 = DESTROY_RESOURCES
* 60 = CREATE_RESOURCES
* 70 = DEPLOY_ARTIFACTS
* 80 = START_CONTAINERS
* 90 = START_ARTIFACTS
* 100 = POST_FLIGHT
