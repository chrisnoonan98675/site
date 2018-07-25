---
title: Introduction to the XL Deploy Tomcat plugin
categories:
- xl-deploy
subject:
- Tomcat
tags:
- plugin
- tomcat
---

The XL Deploy Apache Tomcat plugin allows you to manage deployments on a Tomcat server. Standard support includes deploying and undeploying web applications, datasources, mail sessions, resource links, ActiveMQ, and WebSphere MQ resources. The plugin can easily be extended to support more deployment options or management of new artifacts and resources on Tomcat.

For information about Tomcat requirements and the configuration items (CIs) it supports, refer to the [Tomcat Plugin Manual](/xl-deploy-tomcat-plugin/latest/tomcatPluginManual.html).

## Features

* Deploy web applications (WAR) to a Tomcat virtual host
* Deploy resources to an application context on a Tomcat virtual host
* Deploy resources to the common Tomcat context (that is, `$TOMCAT_HOME/conf/context.xml`)
* Deploy resource links
* Supported JEE resources:
	* Datasource
	* JMS Queue
	* JMS Queue Connection Factory
	* JMS Topic
	* JMS Topic Connection Factory
	* Mail Session
* Supported Messaging Middleware:
    * ActiveMQ
    * WebSphere MQ
* Support for Tomcat Database Connection Pool (DBCP) configurations
* Support for stopping and starting a Tomcat server via control tasks
* Deploy configuration files to a Tomcat server
* Deploy libraries files to a Tomcat server

## Tomcat topology

The Tomcat plugin allows for the modeling of a Tomcat installation into the XL Deploy infrastructure as a hierarchical set of containers. The Tomcat server (`tomcat.Server`), Tomcat common context (`tomcat.CommonContext`), and Tomcat virtual host (`tomcat.VirtualHost`) containers can then be included in XL Deploy environment definitions, to which you can deploy deployables.

### Tomcat server (`tomcat.Server`)

`tomcat.Server` models a Tomcat installation running on a host. This container must be defined under any `overthere.Host` in the XL Deploy infrastructure. The container supports operating system-specific stop and start commands used to control the stopping and starting of the Tomcat server. In addition, stop and start wait times can be specified. XL Deploy will wait for the specified amount of time to elapse after the execution of a stop/start command.

### Tomcat common context (`tomcat.CommonContext`)

`tomcat.CommonContext` models the `context.xml` file present in the `$TOMCAT_HOME/conf` directory. This container must be defined under a `tomcat.Server` container. Only a single instance should be defined. Any Tomcat resource can be deployed to this container.

### Tomcat virtual host (`tomcat.VirtualHost`)

`tomcat.VirtualHost` models a Tomcat virtual host definition (that is, `$TOMCAT_HOME/conf/[enginename]/[hostname]`). This container must be defined under a `tomcat.Server` container. Multiple instances can be defined. Web applications and tomcat resources can be deployed to this container.

## Deploying web applications

Web applications (`jee.War`) can be deployed to a Tomcat virtual host (`tomcat.VirtualHost`). The context root must be specified. The context root is used to create the corresponding application specific context XML in the virtual host. If you want to deploy the WAR file in the exploded mode, you need to package the WAR file using `tomcat.ExplodedWar` instead of `jee.War`.

## Deploying resources

A Tomcat resource (`tomcat.JndiContextElement`) can be deployed to a Tomcat virtual host (`tomcat.VirtualHost`) or Tomcat common context (`tomcat.CommonContext`). Every resource has an optional context property. This property is always set to _context_ when the resource is deployed to Tomcat common context. When the resource is deployed to a Tomcat virtual host, the user can specify the name of the context to which the resource must be defined.

When left blank, the plugin can default the value to that of the WAR in the current deployment. There must only be a single WAR in the current deployment for the defaulting to work. For example, deploying a WAR with context root set to _sample_, and a datasource to a virtual host will automatically result in the _context_ property of the datasource been set to _sample_.

## Use in deployment packages

The plugin works with the standard deployment package of DAR format.

The following is a sample `deployit-manifest.xml` file that can be used to create a Tomcat specific deployment package. It contain declarations for a WAR (`jee.War`), a datasource (`tomcat.DataSourceSpec`), and a couple of JMS resources.

	<?xml version="1.0" encoding="UTF-8"?>
	<udm.DeploymentPackage version="1.0" application="SampleApp">
	  <deployables>
	    <jee.War name="SampleApp-1.0.war" file="SampleApp-1.0.war">
	      <tags />
	      <scanPlaceholders>true</scanPlaceholders>
	      <checksum>b4759ef62eaf3a89db260f51ad0d1d749d22f7e4</checksum>
	    </jee.War>
	    <tomcat.DataSourceSpec name="testDatasource">
	      <tags />
	      <jndiName>jdbc/sampleDatasource</jndiName>
	      <driverClassName>com.mysql.jdbc.Driver</driverClassName>
	      <url>jdbc:mysql://localhost/test</url>
	      <username>{% raw %}{{DATABASE_USERNAME}}{% endraw %}</username>
	      <password>{% raw %}{{DATABASE_PASSWORD}}{% endraw %}</password>
	      <connectionProperties />
	    </tomcat.DataSourceSpec>
	    <jee.QueueSpec name="sampleQueue">
	      <tags />
	      <jndiName>jms/testQueue</jndiName>
	    </jee.QueueSpec>
	    <jee.QueueConnectionFactorySpec name="sampleQCf">
	      <tags />
	      <jndiName>jms/sampleQCf</jndiName>
	    </jee.QueueConnectionFactorySpec>
	  </deployables>
	</udm.DeploymentPackage>
