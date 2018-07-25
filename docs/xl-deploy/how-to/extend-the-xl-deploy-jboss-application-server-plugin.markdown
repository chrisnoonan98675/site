---
title: Extend the XL Deploy JBoss Application Server plugin
categories:
- xl-deploy
subject:
- JBoss Application Server
tags:
- jboss
- middleware
- plugin
- extension
---

The JBoss Application Server plugin is designed to be extended through XL Deploy's plugin API type system. Also, because the JBoss plugin is built on the XL Deploy [Generic plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-generic-plugin.html), you can add support for new types using the Generic plugin patterns. 

## Change the visibility or default value of an existing property

The following `synthetic.xml` snippet shows how the `restartRequired` property can be made visible and the `targetDirectory` property can be given a default value of `/home/deployer/install-files` for `jbossas.EarModule`.

	<type-modification type="jbossas.EarModule">
      <!-- make it visible so that I can control whether to restart a Server or not from UI-->
	  <property name="restartRequired" kind="boolean" default="true" hidden="false"/>
	  
	  <!-- custom deploy directory for my jboss applications -->
	  <property name="targetDirectory" default="/home/deployer/install-files" hidden="true"/>
	</type-modification>

## Add a new property to a deployed or deployable

The following `synthetic.xml` snippet shows how a new property `blocking-timeout-millis` can be added to `jbossas.TransactionalDatasource`.

	<type-modification type="jbossas.TransactionalDatasource">
        <!-- adding new property -->
        <property name="blockingTimeoutMillis" kind="integer" default="3000" description="maximum time in milliseconds to block 
        while waiting for a connection before throwing an exception"/>
	</type-modification>	

**Important:** When you add a new property to the JBoss Application Server plugin, _the configuration property must be specified in lower camel-case with the hyphens removed from it_. Thus the property `blocking-timeout-millis` must be specified as `blockingTimeoutMillis`. Similarly, `idle-timeout-minutes` becomes `idleTimeoutMinutes` in `synthetic.xml`.

## Add a new type

You can add new types to the JBoss Application Server plugin using the `Generic Plugin` patterns. For example, the following `synthetic.xml` snippet defines a new type, `jbossas.EjbJarModule`:

	<type type="jbossas.EjbJarModule" extends="generic.CopiedArtifact" deployable-type="jee.EjbJar" container-type="jbossas.BaseServer">
		<generate-deployable type="jbossas.EjbJar" extends="jee.EjbJar"/>
		<property name="targetDirectory" default="${deployed.container.home}/server/${deployed.container.serverName}/deploy" hidden="true"/>
		<property name="targetFile" default="${deployed.deployable.name}.jar" hidden="true"/>
		<property name="createOrder" kind="integer" default="50" hidden="true"/>
		<property name="destroyOrder" kind="integer" default="40" hidden="true"/>
		<property name="restartRequired" kind="boolean" default="true" hidden="true"/>
	</type>
