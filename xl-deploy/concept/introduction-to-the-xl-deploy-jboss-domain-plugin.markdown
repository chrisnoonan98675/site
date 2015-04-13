---
title: Introduction to the XL Deploy JBoss Domain plugin
categories:
- xl-deploy
subject:
- JBoss Domain plugin
tags:
- jboss
- wildfly
- middleware
- plugin
- discovery
---

The XL Deploy JBoss Domain plugin adds the capability to manage deployments and resources on JBoss Enterprise Application Platform (EAP) 6 and JBoss Application Server (AS)/WildFly 7.1+.

The plugin has the capability of managing application artifacts, datasource and other JMS resources via the JBoss Cli, and can easily be extended to support more deployment options or management of new artifacts/resources on JBoss AS.

For information about JBoss/WildFly requirements and the configuration items (CIs) that the plugin supports, refer to the [JBoss Application Server 7+ Plugin Reference](/xl-deploy/latest/jbossDomainPluginManual.html).

## Features

* Domain and stand-alone mode support
* Deployment of application artifacts
    * Enterprise application (EAR)
    * Web application (WAR)
* Deployment of resources
    * Datasource including XA Datasource
    * JMS Queue
    * JMS Topic
* Discovery of profiles and server groups in domain

## Use in deployment packages

The plugin works with XL Deploy's standard deployment package DAR format. The following is a sample `deployit-manifest.xml` file that can be used to create a JBoss AS specific deployment package. It contains declarations for a `jbossdm.Ear`, a `jbossdm.DataSourceSpec`, and two of JMS resources.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="SampleApp">
      <deployables>
        <jbossdm.QueueSpec name="testQueue">
          <jndiName>java:jboss/jms/testQueue</jndiName>
        </jbossdm.QueueSpec>
        <jbossdm.TopicSpec name="testTopic">
          <jndiName>jms/testTopic</jndiName>
        </jbossdm.TopicSpec>
        <jbossdm.DataSourceSpec name="testDatasource">
          <jndiName>java:jboss/jdbc/sampleDatasource</jndiName>
          <driverName>mysql</driverName>
          <username>{% raw %}{{DATABASE_USERNAME}}{% endraw %}</username>
          <password>{% raw %}{{DATABASE_PASSWORD}}{% endraw %}</password>
          <connectionUrl>jdbc:mysql://localhost/test</connectionUrl>
          <connectionProperties />
        </jbossdm.DataSourceSpec>
        <jee.Ear name="PetClinic" file="PetClinic/PetClinic.ear">
          <scanPlaceholders>false</scanPlaceholders>
        </jee.Ear>
      </deployables>
    </udm.DeploymentPackage>

## Deploying applications

Note that the plugin uses the JBoss CLI to (un)install artifacts and resources. As such, the plugin assumes that the JBoss Domain or Standalone server has already been started.  The plugin does not support the starting of the domain or standalone server prior to a deployment.

### Stand-alone mode

Artifacts (WAR, EAR) and resources (datasources, queues, topics, and so on) can be targeted to a stand-alone server (`jbossdm.StandaloneServer`).

### Domain Mode

Artifacts (WAR, EAR) can be targeted to either a domain (`jbossdm.Domain`) or server group (`jbossdm.ServerGroup`). When targeted to a domain, the artifacts are (un)installed on all server groups defined for the domain. For specific targeting of artifacts to certain server groups, you can define the server groups in your environment.

Resources (datasources, queues, topics, and so on) can be targeted to either a domain (`jbossdm.Domain`) or profile (`jbossdm.Profile`). When targeted to a domain, the resources are (un)installed in the "default" profile. For specific targeting of resources to certain profiles, you can define the profiles in your environment.

## Discovery

The plugin supports the discovery of profiles and server groups in a domain.

Here is an example command-line interface (CLI) script which discovers a sample domain:

    host = repository.create(factory.configurationItem('Infrastructure/jboss-host', 'overthere.SshHost',
        {'connectionType':'SFTP','address': 'jboss-7','username': 'root','password':'centos','os':'UNIX'}))
    jboss = factory.configurationItem('Infrastructure/jboss-host/jboss-domain', 'jbossdm.Domain',
        {'home':'/opt/jboss/7', 'host':'Infrastructure/jboss-host', 'username':"jbossAdmin", "password":"jboss"})

    taskId = deployit.createDiscoveryTask(jboss)
    deployit.startTaskAndWait(taskId)
    cis = deployit.retrieveDiscoveryResults(taskId)
    deployit.print(cis)

    #discovery just discovers the topology and keeps the configuration items in memory. Save them in the XL Deploy repository
    repository.create(cis)

Note that JBoss Domain has a containment relation with a host (created under a host), so the server ID has been kept as `Infrastructure/jboss-host/jboss-domain`.
