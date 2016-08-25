---
title: Introduction to the XL Deploy WebSphere MQ plugin
categories:
- xl-deploy
subject:
- WebSphere
tags:
- websphere
- plugin
---

The XL Deploy IBM WebSphere MQ (WMQ) plugin allows you to manage resources on a WebSphere MQ environment. The plugin can deploy and undeploy local queues and alias queues on a queue manager, and can be extended to support management of other resources in a WebSphere MQ environment.

For information about the configuration items (CIs) that the plugin provides, refer to the [IBM WebSphere MQ Plugin Reference](/xl-deploy/latest/wmqPluginManual.html).

## Features

* Deploy resources:
	* Local queue
	* Alias queue
* Control task to start and stop queue managers

## Use in deployment packages

The plugin works with the standard deployment package DAR format. The following is a sample `deployit-manifest.xml` file that can be used to create a WMQ specific deployment package. It contain declarations for a local queue(`wmq.LocalQueueSpec`) and an alias queue (`wmq.AliasQueueSpec`).

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="WebsphereMQApp">
      <orchestrator>default</orchestrator>
      <parallelByContainer>false</parallelByContainer>
      <deployables>
       <wmq.AliasQueueSpec name="testAliasQueue">
          <target>testTargetQueue</target>
          <cluster>QUEUE_CLUSTER</cluster>
        </wmq.AliasQueueSpec>
        <wmq.LocalQueueSpec name="testLocalQueue">
          <maxDepth>3</maxDepth>
          <boqname>testBackoutQueue</boqname>
          <bothresh>10</bothresh>
        </wmq.LocalQueueSpec>
      </deployables>
    </udm.DeploymentPackage>

## Queue manager

`wmq.QueueManager` is a container type which represents an existing queue manager running in the MQ environment, and the MQ resources such as local queue or alias queue can be targeted to it. It has a containment relationship with host, which means that it can only be created under a host.

Also, because a `wmq.QueueManager` is meant to represent an existing queue manager, the name of the CI should reflect the existing queue manager name. For example, if the existing queue manager running in the MQ environment is called `VENUS`, the `wmq.QueueManager` CI should be called `VENUS`.

`wmq.QueueManager` also supports control tasks for starting and stopping the queue manager.
