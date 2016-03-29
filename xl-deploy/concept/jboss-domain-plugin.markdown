---
title: Introduction to the XL Deploy JBoss Domain plugin
categories:
- xl-deploy
subject:
- JBoss EAP/WildFly
tags:
- jboss
- wildfly
- middleware
- plugin
- discovery
---

The JBoss Domain (jbossdm) plugin for XL Deploy allows you to manage deployments and resources on:

* JBoss Enterprise Application Platform (EAP) 6
* JBoss Application Server (AS)/WildFly 7.1+

The plugin can manage application artifacts, datasources, and other JMS resources using the JBoss command-line interface (CLI). You can extend the plugin to support more deployment options or manage new artifacts and resources on JBoss/WildFly.

For information about JBoss/WildFly requirements and the configuration items (CIs) that the plugin supports, refer to the [JBoss Application Server 7+ Plugin Reference](/xl-deploy/latest/jbossDomainPluginManual.html).

**Tip:** If you're using JBoss Application Server (AS) 4.x, 5.x, or 6.x, refer to the [JBoss Application Server plugin](/xl-deploy/concept/jboss-application-server-plugin.html).

## Features

* Supports domain and stand-alone mode
* Deploy application artifacts:
    * Enterprise application (EAR)
    * Web application (WAR)
* Deploy resources:
    * Datasource including XA Datasource
    * JMS Queue
    * JMS Topic
* Discover profiles and server groups in domain

## Use in deployment packages

The JBoss Domain plugin works with XL Deploy's standard deployment package (DAR) format. The following is a sample `deployit-manifest.xml` file that can be used to create a deployment package for JBoss AS. It contains declarations for a `jbossdm.Ear` CI, a `jbossdm.DataSourceSpec` CI, and two JMS resources.

{% highlight xml %}
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
{% endhighlight %}

## Deploying applications

The JBoss Domain plugin uses the JBoss/WildFly command-line interface (CLI) to install and uninstall artifacts and resources. Therefore, the plugin assumes that the JBoss/WildFly domain or stand-alone server is already started. The plugin does not support starting the domain or stand-alone server before deployment.

### Stand-alone mode

Artifacts such as WAR and EAR files and resources such as datasources, queues, topics, and so on can be deployed to a stand-alone server (`jbossdm.StandaloneServer`).

### Domain Mode

Artifacts such as WAR and EAR files can be deployed to a domain (`jbossdm.Domain`) or a server group (`jbossdm.ServerGroup`). When targeted to a domain, artifacts are installed or uninstalled on all server groups defined for the domain. To deploy artifacts to certain server groups, you can define server groups in your environment.

Resources such as datasources, queues, topics, and so on can be deployed to a domain (`jbossdm.Domain`) or a profile (`jbossdm.Profile`). When targeted to a domain, resources are installed or uninstalled in the "default" profile. To deploy resources to certain profiles, you can define profiles in your environment.

## Using WildFly 8 with Microsoft Windows

WildFly 8 scripts for Microsoft Windows end with "Press any key to continue ..." and require user interaction to dismiss the message. This causes XL Deploy to hang while it waits on a response from the WildFly CLI.

To prevent the CLI from waiting for user interaction, set the NOPAUSE variable as described in [the WildFly documentation](https://docs.jboss.org/author/display/WFLY8/CLI+Recipes?_sscc=t#CLIRecipes-Windowsand%22Pressanykeytocontinue...%22issue).

## Discovery

The JBoss Domain plugin supports [discovery](/xl-deploy/how-to/discover-middleware.html) of profiles and server groups in a domain. This is a sample XL Deploy CLI script that discovers a sample domain:

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
