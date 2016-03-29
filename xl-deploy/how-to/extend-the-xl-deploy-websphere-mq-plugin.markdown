---
title: Extend the XL Deploy WebSphere MQ plugin
categories:
- xl-deploy
subject:
- WebSphere
tags:
- websphere
- middleware
- plugin
- extension
---

The WebSphere MQ (WMQ) plugin is designed to be extended through XL Deploy's plugin API type system. Also, because the WMQ plugin is built on top of the Generic plugin, support for new types can be added using the 
Generic plugin patterns. 

## Make an existing property hidden or visible or change the default value

The following synthetic.xml snippet shows how the `maxDepth` property can be made hidden with a default value set on it in the `wmq.LocalQueue` type:

	<type-modification type="wmq.LocalQueue">
      <!-- make it hidden, and give a default value if all the local queues are always created with the maxDepth value of 3-->
	  <property name="maxDepth" kind="integer" default="3" hidden="true"/>
	</type-modification>

## Add a new property to a deployed or deployable

The following synthetic.xml snippet shows how a new property `DEFPRTY` (for specifying the default priority) can be added to the `wmq.LocalQueue` type:

	<type-modification type="wmq.LocalQueue">
        <!-- adding new property for setting the default priority-->
        <property name="defprty" kind="integer" default="3" label="default priority" 
        description="The default priority of messages put on the queue. The value must be in the range zero 
        (the lowest priority) through to the MAXPRTY queue manager parameter. (MAXPRTY is 9.)"/>
	</type-modification>	

**Important:** When you add a new property in the WMQ plugin, the property name should match exactly with the the WMQ command parameter name. Hence the property has been called `defprty` and not `defaultProperty` or `defPriority`. Also, a label can be specified for the property to give it a user-friendly name on the UI. In the example above, the property `defprty` will appear as `default priority` on the UI. 

## Add a new type

New types can be added in WMQ plugin using the Generic plugin patterns. For example, the following `synthetic.xml` snippet defines a new deployed type `wmq.ModelQueue` (and the corresponding deployable type `wmq.ModelQueueSpec`, which will be automatically generated from the deployed definition):

	<type type="wmq.ModelQueue" extends="wmq.Resource" deployable-type="wmq.ModelQueueSpec" 
															container-type="wmq.QueueManager">
		<generate-deployable type="wmq.ModelQueueSpec" extends="generic.Resource"/>
		<property name="createScript" hidden="true" default="wmq/create-qmodel" />
		<property name="modifyScript" hidden="true" default="wmq/modify-qmodel" />
		<property name="destroyScript" hidden="true" default="wmq/destroy-qmodel" />
		<property name="maxDepth" kind="integer" description="The maximum number of messages allowed on the queue"/>
	</type>
	
Once this new type has been added in the synthetic.xml, the new types `wmq.ModelQueueSpec` and `wmq.ModelQueue` is readily available to the XL Deploy type system. But to make it usable completely, the corresponding scripts must be added at the specified path. This is how the create script `wmq/create-qmodel.sh` might look:

	#!/bin/sh
	echo "DEFINE QMODEL(${deployed.name}) ${deployed.parameters}" 
			| ${deployed.container.executablesDirectory}/runmqsc ${deployed.container.name}
	
Similarly, below is an example destroy script `wmq/destroy-qmodel.sh` for the newly defined type:

	#!/bin/sh
	echo "DELETE QMODEL(${deployed.name})" | ${deployed.container.executablesDirectory}/runmqsc ${deployed.container.name}
	
On similar lines, the modify script can be specified containing the alter command for altering the model queue.
