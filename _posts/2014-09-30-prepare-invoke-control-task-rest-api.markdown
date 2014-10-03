---
title: How to prepare and invoke a control task using the XL Deploy REST API
categories:
- xl-deploy
tags:
- control task
- repository
- api
---

In addition to its primary task of executing deployments, XL Deploy supports [control tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#control-tasks). These are useful maintenance or utility actions, such as checking a connection to a host or restarting a server, that you can associate with any [configuration item](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#configuration-items-cis) (ci) in XL Deploy's [repository](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#repository).

You can see which out-of-the-box control tasks are available in the documentation for the plugins you have installed (for example, documentation for the `checkConnection` control task on an `overthere.Host` [here](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherehost)). You can even define your own control tasks and attach them to existing ci types, if desired; for example, the [Generic Plugin Manual](http://docs.xebialabs.com/releases/latest/deployit/genericPluginManual.html#control-task-delegates) for documentation on adding tasks to any [`generic.Container`](http://docs.xebialabs.com/releases/latest/deployit/genericPluginManual.html#genericcontainer).

Here, we will demonstrate how to invoke a control task using the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html). This actually breaks down into two steps:

* [Preparing a control task](#preparing-a-control-task)—Creates a "task request" object which allows us to specify parameters that need to be passed to the control task
* [Invoking the control task](#invoking-the-control-task)—Passes the (potentially modified) "task request" to the server, which validates it and returns the ID of a task that is ready to run

## Preparing a control task

First, we will make a call to [`/control/prepare`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.ControlService.html#/control/prepare/{controlName}/{id:.*?}:GET) to retrieve a "task request" object (it's really called a [`Control` object](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.Control.html)) for the `checkConnection` task of a host in our repository. The format of the URL is `.../control/prepare/{controlTaskName}/{idOfTargetCi}`.

Input:

    curl -u<username>:<password> http://<xl-deploy-server>:<xl-deploy-port>/deployit/control/prepare/checkConnection/Infrastructure/my-host

Output:

    <control method="checkConnection">
        <controllable>
            <overthere.LocalHost id="Infrastructure/my-host" token="ce299116-ec1d-4426-a066-3ab9c3bb529b">
                ...
            </overthere.LocalHost>
        </controllable>
    </control>

We need to store the output of this "prepare" call in a temporary location so we can pass it on to the subsequent "invoke" call. A control task that requires parameters has an additional `<parameters>...</parameters>` block inside the `<control>` tag, which you may need to modify to specify the correct values of the desired input parameters.

## Invoking the control task

Let's assume we stored the output of the previous call in `/tmp/prepared.txt` and updated any required parameters as necessary. We can then ask the server to validate our control object and convert it to a task that can be run.

Input:

    curl -u<username>:<password> -X POST -H "Content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/control/ -d@/tmp/prepared.txt

Output:

    987f383f-36af-452a-a232-3c4bffc12389

The output is the ID of the resulting [task](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task), which can be started and inspected using the [`TaskService`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html). See [this article]({{ site.url }}/start-task-retrieve-task-info-rest-api) for an example of starting a task using the REST API.
