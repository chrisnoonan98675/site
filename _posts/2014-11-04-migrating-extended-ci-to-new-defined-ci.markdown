---
title: Migrating an extended deployable and deployed CI to a new defined deployable and deployed CI
author: levent_tutar
categories:
- xl-deploy
- tips-and-tricks
tags:
- ci
- python
- deployable
- deployed
- jboss
---

[This Python script](/sample-scripts/migrating-deployable-deployed.py) will loop through the XL Deploy repository and migrate the deployable [`jbossdm.QueueSpec`](http://docs.xebialabs.com/releases/latest/jbossdm-plugin/jbossDomainPluginManual.html#jbossdmqueuespec) and deployed [`jbossdm.Queue`](http://docs.xebialabs.com/releases/latest/jbossdm-plugin/jbossDomainPluginManual.html#jbossdmqueuespec) to the new CI deployable type `jbossdmx.QueueSpec` and new CI deployed type `jbossdmx.Queue`. This migration means that you do not have to support both CIs after introducing new deployable CIs.

This `synthetic.xml` example defines the new deployable and deployed:


    <type type="jbossdmx.Queue" extends="jbossdm.CliManagedDeployed" deployable-type="jbossdmx.QueueSpec" container-type="jbossdm.ResourceContainer" description="A JbossX Queue">
        <generate-deployable type="jbossdmx.QueueSpec" extends="jee.QueueSpec" description="A QueueX"/>
        <!--required-->
        <property name="jndiName" description="(Comma separated list) The jndi names the queue will be bound to."/>

        <!--optional-->
        <property name="selector" required="false" description="The queue selector"/>
        <property name="durable" kind="boolean" required="false" default="true" description="Whether the queue is durable or not"/>

        <!--hidden-->
        <property name="createScript" hidden="true" default="jboss/dm/jms/create-queue.py"/>
        <property name="destroyScript" hidden="true" default="jboss/dm/jms/destroy-queue.py"/>
        <property name="inspectScript" hidden="true" default="jboss/dm/jms/inspect-queue.py"/>
    </type>
 

The output after executing the script is:

	The deployed applications are: ['Environments/leleacc/PetClinic-war']

	The following are of old deployed type jbossdm.Queue:
	set([])

	The following are of old deployable type jbossdm.QueueSpec:
	set(['Applications/levent-war/4.0/queueleventspec2', 'Applications/levent-war/4.0/queueleventspec'])


	Looping for old deployable type: Applications/levent-war/4.0/queueleventspec2
	  Looping for deployed application: Environments/leleacc/PetClinic-war

	Looping for old deployable type: Applications/levent-war/4.0/queueleventspec
	  Looping for deployed application: Environments/leleacc/PetClinic-war
	We are outside the for loop with deployables
	targeted deployedBuffer: []
	untargeted deployedNewDeployedsBuffer: []

	Let's delete the targeted old deployeds under the infrastructure: []

	Let's delete the untargeted old deployeds under the infrastructure: []

	Let's delete old deployable types:
	Applications/levent-war/4.0/queueleventspec2 is removed
	Applications/levent-war/4.0/queueleventspec is removed

	Let's create the new deployable types:
	Applications/levent-war/4.0/queueleventspec2 is created and has the type jbossdmx.QueueSpec
	Applications/levent-war/4.0/queueleventspec is created and has the type jbossdmx.QueueSpec

	Let's update the deployeds of the deployed application of the environment:
	[]

	Let's create the deployeds that are not targeted to an environment but exist
	[]

	The following are of old deployed type jbossdm.Queue:
	set([])


	The following are of old deployable type jbossdm.QueueSpec:
	set([])
