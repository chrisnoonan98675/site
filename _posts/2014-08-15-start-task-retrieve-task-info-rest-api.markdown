---
title: How to start a task and retrieve task info using the XL Deploy REST API
author: amit_mohleji
categories:
- xl-deploy
tags:
- task
- api
---

One of the key aspects of XL Deploy's execution model is that most actions—whether [deployments](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deploying-an-application), [control tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#control-tasks), or running discovery—are ultimately [tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task) that run in XL Deploy's task engine. For example, if you [prepare a control task using XL Deploy's REST API](/prepare-invoke-control-task-rest-api), the output of the last API request is a task ID that you can use to execute the task.

Here, we'll give some examples of how to use [XL Deploy's REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html) to:

* [Start a task](#start-a-task)
* [Retrieve task info](#retrieve-task-info)

## Start a task

This is pretty straightforward. To start a task, you simply need to invoke [`POST /task/{taskid}/start`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html#/task/{taskid}/start:POST).

Input:

    curl -u<username>:<password> -X POST http://<xl-deploy-server>:<xl-deploy-port>/deployit/task/bd4aef1c-3a85-4d1a-a839-03ae619d01df/start

## Retrieve task info

Retrieving information about a task is also easy: just call [`GET /task/{taskid}`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html#/task/{taskid}:GET).

Input:

    curl -u<username>:<password> http://<xl-deploy-server>:<xl-deploy-port>/deployit/task/bd4aef1c-3a85-4d1a-a839-03ae619d01df/

Output:

	<task id="bd4aef1c-3a85-4d1a-a839-03ae619d01df" currentStep="12" totalSteps="12" failures="0" state="EXECUTED" state2="EXECUTED" owner="admin">
	  <description>Initial deployment of Environments/Dev/TEST/PetPortal</description>
	  <startDate>2014-08-08T19:51:17.993+0000</startDate>
	  <completionDate>2014-08-08T19:51:56.493+0000</completionDate>
	  <currentSteps>
		<current>12</current>
	  </currentSteps>
	  <metadata>
		<application>PetPortal</application>
		<environment>TEST</environment>
		<taskType>INITIAL</taskType>
		<environment_id>Environments/Dev/TEST</environment_id>
		<version>1.0</version>
	  </metadata>
	</task>

The return value of this call is a `TaskState` object, which is described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.TaskState.html).

For a list of the other operations that you can execute on tasks using XL Deploy's REST API, see the documentation for the [`TaskService`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html) and its extension, the [`TaskBlockService`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskBlockService.html).

The `TaskBlockService` also supports blocks, which are groups of steps within a task that are aggregated, such as all the steps executing for a particular deployment group. The [XL Deploy UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html) uses blocks to show the deployment plan in "collapsed" form, but you will generally only need to work with them if you are using complex [orchestration patterns](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#orchestrators).
