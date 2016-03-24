---
title: Introduction to the XL Deploy WebSphere Application Server plugin
categories:
- xl-deploy
subject:
- WebSphere
tags:
- websphere
- middleware
- plugin
---

The IBM WebSphere Application Server (WAS) plugin adds capability for managing deployments and resources on an existing WebSphere application server. It offers out-of-the-box support for deploying and undeploying application artifacts, datasources, JMS resources, and other Java EE resources and WAS configurations.

The plugin can be extended to support more deployment options or management of new artifacts and resources on WAS.

For information about the configuration items (CIs) that the WAS plugin provides, refer to the [WebSphere Application Server Plugin Reference](/xl-deploy/latest/wasPluginManual.html).

## Features

* Deploy and undeploy Java EE application artifacts:
    * Enterprise applications (EAR)
    * Web applications (WAR)
    * Enterprise JavaBeans (EJB JAR)
* Update global web server plug-in configuration
* Create, update, and remove Java EE resources:
    * JMS resources
        * V5 default JMS resources: queues, topics and connection factories
        * WebSphere MQ JMS resources: queues, topics, connection factories and activation specifications
        * SIB JMS resources: queues, topics, connection factories and destinations
        * Custom JMS providers
    * JDBC resources
        * JDBC providers: Oracle, OracleXa or custom
        * Data sources: DB2 (type 2 and 4), Oracle, MsSql and Derby
    * J2C resources
        * Resource adapters, J2C connection factories, J2C activation specification and J2C admin objects
    * Asynchronous beans
        * Timer managers
        * Work managers
    * Schedulers
    * Cache instances
        * Servlet cache instances
    * Mail
        * Mail providers
        * Mail sessions
    * URL
        * URL providers
        * URLs
    * Resource environment
        * Resource environment providers
        * Resource environment entries
* Create, update, and remove WAS configuration elements:
    * Shared libraries
    * Virtual hosts
    * Name space bindings
    * Core groups
    * Service Integration Buses
    * WebSphere variables
    * Health policies
    * Service policies
* Configure application server components:
    * Session management
    * Application server settings
    * Web container settings
    * EJB container setting
    * Container services
        * Transaction service
        * ORB service
    * Message listener
        * Thread pools
        * Listener ports
    * Java and process management
    * Administration
        * Custom properties
        * Custom services
    * Ports
    + Performance Monitoring Infrastructure (PMI)
    * Logging and tracing
* Discover WAS topologies: cells, nodes, clusters, server, web servers
* Discover all Java EE and WAS configuration resources
* Control the state of cells, nodes, clusters and servers
* Create clusters and servers from template

## Use in deployment packages

The plugin works with the standard deployment package (DAR) format. The following is a sample `deployit-manifest.xml` file that can be used to create a WebSphere-specific deployment package. It contain declarations for a `jee.Ear`, a `was.OracleDatasourceSpec`, and JMS resources.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0-was" application="PetClinic">
        <deployables>
            <jee.Ear name="petClinic-1.0.ear" file="petClinic-1.0.ear">
            </jee.Ear>
            <was.OracleDatasourceSpec name="sampleOracleDatasource">
                <jndiName>jdbc/sampleOracleDatasource</jndiName>
                <jdbcProvider>Oracle JDBC Driver</jdbcProvider>
                <username>{% raw %}{{DATABASE_USERNAME}}{% endraw %}</username>
                <password>{% raw %}{{DATABASE_PASSWORD}}{% endraw %}</password>
                <datasourceHelperClassname>com.ibm.websphere.rsadapter.Oracle10gDataStoreHelper</datasourceHelperClassname>
            </was.OracleDatasourceSpec>
            <was.SibQueueDestinationSpec name="sampleSibQueueDestination">
                <busName>sampleSIBus</busName>
            </was.SibQueueDestinationSpec>
            <was.SibQueueSpec name="sampleSibQueue">
                <busName>sampleSIBus</busName>
                <description>sample sib queue</description>
            </was.SibQueueSpec>
        </deployables>
    </udm.DeploymentPackage>

## Use intermediate `wsadmin` instance to deploy applications

It is possible to use intermediate (proxy) `wsadmin` instance to deploy applications. This feature depends on `wsadmin` ability to serve as a proxy to a target host. To use this functionality, you must:

 * Define the deployment manager or unmanaged server on a proxy host - this instance will be used as a `wsadmin` proxy
 * Define the hostname property on the previously defined deployment manager or unmanaged server

Note that some functionalities might not work with this setup:

 * Control tasks that rely on shell scripts
 * Discovery of EAR, war, and EJB modules
 * Some CIs that rely on AdminTask commands
 * In the repository, CIs will be discovered/deployed under the "proxy" instance of dmgr or unmanaged server, not under the target instance

## Invoking control tasks

Some CIs have control tasks which can accept parameters. Here's an example how to create an application server:

    newServerId='Infrastructure/localhost/vagrantCell01/vagrantNode01/test3'
    newServer = factory.configurationItem(newServerId, 'was.ManagedServer', {'serverType': 'APPLICATION_SERVER', 'tags' : ['scope_jvm'] });
    repository.create(newServer)
    control = deployit.prepareControlTask(newServer, "create")
    control.parameters.values['template'] = 'default'
    taskId = deployit.createControlTask(control)
    deployit.startTaskAndWait(taskId)
