---
title: How to prepare and invoke a control task using the XL Deploy REST API
categories:
- xl-deploy
tags:
- API
- control-task
- xl-deploy-4.0
- xl-deploy-4.5
---

Next to its primary task of executing deployments, XL Deploy also supports [Control Tasks](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#control-tasks). These are useful maintenance or utility actions, such as checking a connection to a host, or restarting a server, that can be associated with any [CI](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#configuration-items-cis) in XL Deploy's [repository](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#repository).

You can see which out-of-the-box control tasks are available from the documentation for the plugins you have installed ([here](http://docs.xebialabs.com/releases/4.0/deployit/remotingPluginManual.html#overtherehost) you can see the documentation for the _checkConnection_ control task on an [`overthere.Host`](http://docs.xebialabs.com/releases/4.0/deployit/remotingPluginManual.html#overtherehost), for example). You can even define your own control tasks and attach them to existing CI types, if desired - see e.g. [here](http://docs.xebialabs.com/releases/latest/deployit/genericPluginManual.html#control-task-delegates) for documentation on how to add tasks to any [`generic.Container`](http://docs.xebialabs.com/releases/latest/deployit/genericPluginManual.html#genericcontainer).

Here, we will demonstrate how to invoke a control task using the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html). This actually breaks down into two steps:

*   [Preparing a control task.](https://xebialabs.zendesk.com/entries/45128245-XL-Deploy-REST-API-Examples#prepare-control-task) This creates a "task request" object which allows us to e.g. specify parameters that need to be passed to the control task.
*   [Invoking the control task.](https://xebialabs.zendesk.com/entries/45128245-XL-Deploy-REST-API-Examples#trigger-control-tasks) This passes the (potentially modified) "task request" to the server, which validates it and returns the ID of a task that is ready to run.

### Preparing a control task

First, we will make a call to [`/control/prepare`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.ControlService.html#/control/prepare/{controlName}/{id:.*?}:GET) to retrieve a "task request" object (it's really called a "[`Control` object](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.Control.html)") for the _checkConnection_ task of a host in our repository. The format of the URL is `.../control/prepare/{controlTaskName}/{idOfTargetCi}`:

**INPUT:**

      curl -u<username>:<passwordhttp://<xl-deploy-server>:<xl-deploy-port>/deployit/control/prepare/checkConnection/Infrastructure/my-host

**OUTPUT:**

      <control method="checkConnection">
            <controllable>
                  <overthere.LocalHost id="Infrastructure/my-host" token="ce299116-ec1d-4426-a066-3ab9c3bb529b">
                        ...
            </overthere.LocalHost>
            </controllable>
      </control>

We need to store the output of this "prepare" call in a temporary location so we can pass it on to the subsequent "invoke" call. A control task that requires parameters has an additional `<parameters>...</parameters>` block inside the `<control>` tag, which you may need to modify to specify the correct values of the desired input parameters.

### Invoking the control task

Let's assume we stored the output of the previous call in `/tmp/prepared.txt`, and updated any required parameters as necessary. We can then ask the server to validate our control object and convert it to a task that can be run:

**INPUT:**

      curl -u<username>:<password-X POST -H "Content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/control/ -d@/tmp/prepared.txt

OUTPUT:

      987f383f-36af-452a-a232-3c4bffc12389

The output is the ID of the resulting [task](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task), which can be started, inspected etc. using the [TaskService](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html). See [this article](https://support.xebialabs.com/entries/48023205-How-to-start-a-task-and-retrieve-task-info-using-the-XL-Deploy-REST-API) for an example of starting a task using the REST API.
