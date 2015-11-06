---
title: Introduction to the XL Deploy JBoss Application Server plugin
categories:
- xl-deploy
subject:
- JBoss Application Server plugin
tags:
- jboss
- middleware
- plugin
---

The XL Deploy JBoss Application Server (AS) plugin adds the capability to manage deployments and resources on a JBoss Application Server. It works out of the box for deploying and undeploying application artifacts, datasources, and other JMS resources. You can extend the plugin to support more deployment options or management of new artifacts and resources on JBoss Application Server.

For information about the configuration items (CIs) that the JBoss Application Server plugin provides, refer to the [JBoss Application Server Plugin Reference](/xl-deploy/latest/jbossPluginManual.html).

## Features

* Application artifacts:
	* Enterprise application (EAR)
	* Web application (WAR)
* JBoss-specific artifacts:
	* Service Archive (SAR)
	* Resource Archive (RAR)
	* Hibernate Archive (HAR)
	* Aspect archive (AOP)
* Resources:
	* Datasource
	* JMS Queue**
	* JMS Topic**
* Discovery

**Note:** When creating JMS resources such as JMS queues and JMS topics for JBoss Application Server 6, only the JNDI name is used. Other properties such as `RedeliveryDelay`, `MaxDeliveryAttempts`, and so on are not used, even if they are defined and set on the configuration item (CI) in `synthetic.xml`. You can define these properties by editing the global server configuration at `%JBOSS_HOME%/server/<configuration/deploy/hornetq/hornetq-jms.xml`.

## Use in deployment packages

This is a sample `deployit-manifest.xml` file that can be used to create a deployment package. It contains declarations for a `jbossas.Ear`, a `jbossas.DataSourceSpec`, and JMS resources.

	<?xml version="1.0" encoding="UTF-8"?>
	<udm.DeploymentPackage version="1.0" application="SampleApp">
	  <deployables>
	    <jbossas.QueueSpec name="testQueue">
	      <jndiName>jms/testQueue</jndiName>
	    </jbossas.QueueSpec>
	    <jbossas.ConfigurationFolder name="testConfigFolder" file="testConfigFolder/">
	      <scanPlaceholders>true</scanPlaceholders>
	    </jbossas.ConfigurationFolder>
	    <jbossas.TopicSpec name="testTopic">
	      <jndiName>jms/testTopic</jndiName>
	    </jbossas.TopicSpec>
	    <jbossas.TransactionalDatasourceSpec name="testDatasource">
	      <jndiName>jdbc/sampleDatasource</jndiName>
	      <userName>{{DATABASE_USERNAME}}</userName>
	      <password>{{DATABASE_PASSWORD}}</password>
	      <connectionUrl>jdbc:mysql://localhost/test</connectionUrl>
	      <driverClass>com.mysql.jdbc.Driver</driverClass>
	      <connectionProperties />
	    </jbossas.TransactionalDatasourceSpec>
	    <jbossas.ConfigurationFile name="testConfigFiles" file="testConfigFiles/testConfigFile.xml">
	      <scanPlaceholders>true</scanPlaceholders>
	    </jbossas.ConfigurationFile>
	    <jee.Ear name="PetClinic" file="PetClinic/PetClinic.ear">
	      <scanPlaceholders>false</scanPlaceholders>
	    </jee.Ear>
	  </deployables>
	</udm.DeploymentPackage>

## Deploying applications

By default, XL Deploy deploys the application artifacts and resource specifications (datasource, queues, topics etc) to the `deploy` directory in the server's configuration. Therefore, if the server configuration is set to `default` (which is the default value of the server name), the artifact is copied to `${JBOSS_HOME}/server/default/deploy`. Also, the server is stopped before copying the artifact and then started again. These configurations are customisable to suit specific scenarios.
