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

The XL Deploy [IBM WebSphere MQ (WMQ) plugin](/xl-deploy/concept/websphere-mq-plugin.html) can be extended through XL Deploy's plugin API type system. Also, because the WMQ plugin is built on top of the [Generic plugin](/xl-deploy/concept/generic-plugin.html), support for new types can be added using Generic plugin patterns.

## Make an existing property hidden or visible or change the default value

The following `synthetic.xml` example shows how to hide the `maxDepth` property on the `wmq.LocalQueue` configuration item (CI) type and set its default value:

{% highlight xml %}
<type-modification type="wmq.LocalQueue">
    <!-- make it hidden, and give a default value if all the local queues are always created with the maxDepth value of 3-->
    <property name="maxDepth" kind="integer" default="3" hidden="true"/>
</type-modification>
{% endhighlight %}

## Add a new property to a deployed or deployable

The following `synthetic.xml` example shows how to add a new property called `DEFPRTY` (for specifying the default priority) to the `wmq.LocalQueue` CI type:

{% highlight xml %}
<type-modification type="wmq.LocalQueue">
    <!-- adding new property for setting the default priority-->
    <property name="defprty" kind="integer" default="3" label="default priority" description="The default priority of messages put on the queue. The value must be in the range zero (the lowest priority) through to the MAXPRTY queue manager parameter. (MAXPRTY is 9.)"/>
</type-modification>
{% endhighlight %}

**Important:** When you add a new property in the WebSphere MQ plugin, the property name should exactly match the WebSphere MQ command parameter name; this is why, in the above example, the property is called `defprty` instead of `defaultProperty` or `defPriority`. You can specify a label for the property so it has a user-friendly name in the GUI; in the above example, the `defprty` property will appear as `default priority` in the GUI.

## Add a new CI type

You can add new configuration item (CI) types using Generic plugin patterns. The following `synthetic.xml` example defines a new deployed type called `wmq.ModelQueue`; the corresponding deployable type `wmq.ModelQueueSpec` will automatically be generated from the deployed definition.

{% highlight xml %}
<type type="wmq.ModelQueue" extends="wmq.Resource" deployable-type="wmq.ModelQueueSpec" container-type="wmq.QueueManager">
    <generate-deployable type="wmq.ModelQueueSpec" extends="generic.Resource"/>
	<property name="createScript" hidden="true" default="wmq/create-qmodel" />
	<property name="modifyScript" hidden="true" default="wmq/modify-qmodel" />
	<property name="destroyScript" hidden="true" default="wmq/destroy-qmodel" />
	<property name="maxDepth" kind="integer" description="The maximum number of messages allowed on the queue"/>
</type>
{% endhighlight %}

After the new type is added to `synthetic.xml`, the new `wmq.ModelQueueSpec` and `wmq.ModelQueue` types will be available to the XL Deploy type system. However, to make them fully usable, the corresponding scripts must be added at the specified path. This is an example of a CREATE script called `wmq/create-qmodel.sh`:

{% highlight sh %}
#!/bin/sh
echo "DEFINE QMODEL(${deployed.name}) ${deployed.parameters}" | ${deployed.container.executablesDirectory}/runmqsc ${deployed.container.name}
{% endhighlight %}

This is an example of a DESTROY script called `wmq/destroy-qmodel.sh`:

{% highlight sh %}
#!/bin/sh
echo "DELETE QMODEL(${deployed.name})" | ${deployed.container.executablesDirectory}/runmqsc ${deployed.container.name}
{% endhighlight %}

Similarly, you can specify a MODIFY script that contains commands for changing the model queue.
