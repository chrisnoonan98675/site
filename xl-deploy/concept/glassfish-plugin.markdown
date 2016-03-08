---
title: Introduction to the XL Deploy GlassFish plugin
categories:
- xl-deploy
subject:
- GlassFish plugin
tags:
- glassfish
- middleware
- plugin
---

The XL Deploy GlassFish plugin adds the capability to manage deployments and resources on GlassFish application server.

The plugin has the capability of managing application artifacts, datasource and JMS resources via the GlassFish CLI, and can easily be extended to support more deployment options or management of new artifacts/resources on GlassFish.

For information about the configuration items (CIs) that the GlassFish plugin provides, refer to the [GlassFish Plugin Reference](/xl-deploy/latest/glassfishPluginManual.html).

## Features

* Deploy to domains, standalone servers, or clusters
* Deploy application artifacts
	* Enterprise application (EAR)
	* Web application (WAR)
	* Enterprise Java beans (EJB)
	* Artifact references
* Deploy resources
    * JDBC Connection Pools
	* JDBC Resources
	* JMS Connection Factories
	* JMS Queues
	* JMS Topics
	* Resource references
* Use control tasks to create, destroy, start, and stop domains and standalone servers
* Discover domains, standalone servers, and clusters

## Use in Deployment Packages

The plugin works with the standard deployment package (DAR) format. The following is a sample `deployit-manifest.xml` file that can be used to create a GlassFish specific deployment package. It contains declarations for a `glassfish.War`, a connection pool (`glassfish.JdbcConnectionPoolSpec`), and a JDBC resources (`glassfish.JdbcResourceSpec`). It also contains references to be able to target the deployables to specific containers.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="MyApp">
      <deployables>

        <glassfish.War name="myWarFile" file="myWarFile/PetClinic-1.0.war">
          <scanPlaceholders>false</scanPlaceholders>
        </glassfish.War>
        <glassfish.ApplicationRefSpec name="myWarRef">
          <applicationName>myWarFile</applicationName>
        </glassfish.ApplicationRefSpec>

        <glassfish.JdbcConnectionPoolSpec name="connPool">
          <datasourceclassname>com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource</datasourceclassname>
          <restype>javax.sql.DataSource</restype>
        </glassfish.JdbcConnectionPoolSpec>

        <glassfish.JdbcResourceSpec name="myJDBCResource">
          <jndiName>myJDBCResource</jndiName>
          <poolName>connPool</poolName>
        </glassfish.JdbcResourceSpec>
        <glassfish.ResourceRefSpec name="MyJDBCResourceRef">
          <resourceName>myJDBCResource</resourceName>
        </glassfish.ResourceRefSpec>

      </deployables>
    </udm.DeploymentPackage>

## Deploying to GlassFish

Note that the plugin uses the GlassFish CLI to (un)install artifacts and resources. As such, the plugin assumes that the GlassFish Domain has already been started. The plugin does not support the starting of the domain prior to a deployment.

GlassFish manages all the artifacts and resources in the domain. Therefore all artifacts and resources must be deployed directly to the domain.
To target an application or resource to a specific container, you can use references. There are two types of deployables that can be used to deploy references:

* `ApplicationRefSpec` can be used to target applications to containers.
* `ResourceRefSpec` can be used to target resources to containers.

The CI name for all deployables will be used as identifier for the application or resource in GlassFish. The applications and resources are referenced by name.

An application can only be undeployed when there are no references to it. Be sure to undeploy all references to the application as well if you want to undeploy an application. The plugin checks if there are references, and if so it will give an error.

## Discovery in the GlassFish plugin

The plugin supports discovery of Domains, Clusters, and Standalone Servers.

The Domain can be discovered through the Host that runs the Domain. Notice that the name of the CI should match the name of the Domain, Cluster or Standalone Server. The name of the container CI is used for the `--target` parameter of the GlassFish CLI.

* XL Deploy will never discover cluster members. You are able to deploy any kind of deployable directly to the cluster, so XL Deploy does not need to know about the instances of a cluster.
* XL Deploy will always discover the default Standalone Server of the domain called server.
* XL Deploy will only discover infrastructure CIs. No deployed CIs will be discovered.
