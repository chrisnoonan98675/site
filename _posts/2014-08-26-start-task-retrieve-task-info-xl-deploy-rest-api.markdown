---
title: How to start a task and retrieve task info using the XL Deploy REST API
categories:
 
- xl-deploy
tags:
- API
- tasks
---

One of the key aspects of XL Deploy's execution model is that pretty much all actions - whether [deployments](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deploying-an-application), [control tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#control-tasks) or running discovery - are ultimately [tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task) that run in XL Deploy's task engine. For example, if you [prepare a control task using XL Deploy's REST API](https://support.xebialabs.com/entries/46231275-How-to-prepare-and-invoke-a-control-task-using-the-XL-Deploy-REST-API), the output of the last API request is a task ID that can be used to execute the task.

Here, we'll give some short examples on how to use [XL Deploy's REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html) to:

*   [Start a task](#task-start)
*   [Retrieve task info](#task-get)

### <a name="task-start"></a>Start a task

This is pretty straightforward. To start a task, you simply need to invoke [<tt>POST /task/{taskid}/start</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html#/task/{taskid}/start:POST):

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt;&nbsp;-X POST http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/task/bd4aef1c-3a85-4d1a-a839-03ae619d01df/start

### <a name="task-get"></a>Retrieve task info

Retrieving information about a task is also easy: just call [<tt>GET /task/{taskid}</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html#/task/{taskid}:GET):

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/task/bd4aef1c-3a85-4d1a-a839-03ae619d01df/
> 
> **OUTPUT:**
> 
> &lt;task id="bd4aef1c-3a85-4d1a-a839-03ae619d01df" currentStep="12" totalSteps="12" failures="0" state="EXECUTED" state2="EXECUTED" owner="admin"&gt;
> &nbsp; &lt;description&gt;Initial deployment of Environments/Dev/TEST/PetPortal&lt;/description&gt;
> &nbsp; &lt;startDate&gt;2014-08-08T19:51:17.993+0000&lt;/startDate&gt;
> &nbsp; &lt;completionDate&gt;2014-08-08T19:51:56.493+0000&lt;/completionDate&gt;
> &nbsp; &lt;currentSteps&gt;
> &nbsp; &nbsp; &lt;current&gt;12&lt;/current&gt;
> &nbsp; &lt;/currentSteps&gt;
> &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &lt;application&gt;PetPortal&lt;/application&gt;
> &nbsp; &nbsp; &lt;environment&gt;TEST&lt;/environment&gt;
> &nbsp; &nbsp; &lt;taskType&gt;INITIAL&lt;/taskType&gt;
> &nbsp; &nbsp; &lt;environment_id&gt;Environments/Dev/TEST&lt;/environment_id&gt;
> &nbsp; &nbsp; &lt;version&gt;1.0&lt;/version&gt;
> &nbsp; &lt;/metadata&gt;
> &lt;/task&gt;

The return value of this call is a <tt>TaskState</tt> object, which is described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.TaskState.html).

For a list of other operations you can execute on tasks using XL Deploy's REST API, see the documentation for the [TaskService](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html) and its extension, the [TaskBlockService](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskBlockService.html).

The TaskBlockService also supports _blocks_, which are groups of steps within a task that are aggregated, such as all the steps executing for a particular deployment group. The [XL Deploy UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html) uses blocks to show the deployment plan in "collapsed" form, but you will generally only need to work with them if you are using complex [orchestration patterns](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#orchestrators).
