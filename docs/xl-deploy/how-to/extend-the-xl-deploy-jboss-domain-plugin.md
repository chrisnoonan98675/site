---
title: Extend the XL Deploy JBoss Domain plugin
categories:
xl-deploy
subject:
JBoss EAP/WildFly
tags:
jboss
wildfly
middleware
plugin
extension
jython
control task
---

The [XL Deploy plugin for JBoss Enterprise Application Platform (EAP) 6 and JBoss Application Server (AS)/WildFly 7.1+](/xl-deploy/concept/jboss-domain-plugin.html) is designed to be extended through XL Deploy's plugin API type system and Jython. 

The plugin wraps the JBoss CLI with a Jython runtime environment, thus allowing extenders to interact with JBoss and XL Deploy from the script. The Jython script is executed on the XL Deploy server itself and has full access to the following XL Deploy objects:

* `deployed`: The current deployed object on which the operation has been triggered.
* `step`: The step object that the script is being executed from. Exposes an overthere remote connection for file manipulation and a method to execute JBoss CLI commands.
* `container`: The container object to which the deployed is targeted to.
* `delta`: The delta specification that lead to the script being executed.
* `deployedApplication`: The entire deployed application.

The plugin associates **Create**, **Modify**, **Destroy**, **Noop** and **Inspect** operations received from XL Deploy with Jython scripts that need to be executed for the specific operation to be performed.

There also exists an advanced method to extend the plugin, but the implementation of this form of extension needs to be written in the Java programming language and consists of writing so-called `Deployed contributors`, `PlanPreProcessors` and `Contributors`.

## Extend the plugin to support JDBC Driver deployment

In this example we will deploy a JDBC driver jar to a domain (`jbossdm.Domain`) or stand-alone server (`jbossdm.StandaloneServer`) as a module and register the driver with JBoss datasources subsystem.

### Define the deployed and deployable to represent a JDBC Driver

The following `synthetic.xml` snippet shows the definition of the JDBC Driver deployed. The deployed will be targeted to a domain (`jbossdm.Domain`) or a stand-alone server (`jbossdm.StandaloneServer`). Please refer to the [JBoss Application Server 7+ Plugin Reference](/xl-deploy/latest/jbossDomainPluginManual.html) to see the interfaces and class hierarchy of these types.

    <type type="jbossdm.JdbcDriverModule" extends="jbossdm.CliManagedDeployedArtifact"
          deployable-type="jbossdm.JdbcDriver" container-type="jbossdm.CliManagingContainer">
      <generate-deployable type="jbossdm.JdbcDriver" extends="udm.BaseDeployableArchiveArtifact">

      <property name="driverName"/>
      <property name="driverModuleName"/>
      <property name="driverXaDatasourceClassName/>

      <!-hidden properties to specify the jython scripts to execute for an operation -->
      <property name="createScript" default="jboss/dm/ds/create-jdbc-driver.py" hidden="true"/>
    </type>

`create-jdbc-driver.py` contains:

    from com.xebialabs.overthere.util import OverthereUtils

    #create module directory to copy jar and module.xml to
    driverModuleName = deployed.getProperty("driverModuleName")
    moduleRelPath = driverModuleName.replaceAll("\\.","/")
    moduleAbsolutePath = "%s/modules/%s" % (container.getProperty("home"), moduleRelPath)
    moduleDir = step.getRemoteConnection().getFile(moduleAbsolutePath);
    moduleDir.mkdirs();
    #upload jar
    moduleJar = moduleDir.getFile(deployed.file.getName())
    deployed.file.copyTo(moduleJar)

    moduleXmlContent = """
     <?xml version="1.0" encoding="UTF-8"?>
     <module xmlns="urn:jboss:module:1.0" name="%s">
        <resources>
            <resource-root path="%s"/>
        </resources>
        <dependencies>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
          </dependencies>
     </module>
     """ % (deployed.getProperty("driverModuleName"), deployed.file.getName())

    #create module.xml
    moduleXml = moduleDir.getFile("module.xml")
    OverthereUtils.write(moduleXlContent.getBytes(),moduleXml)

    #register driver with the datasource subsystem
    driverName = deployed.getProperty("driveName")
    xaClassName = deployed.getProperty("driverXaDatasourceClassName")
    cmd = '/subsystem=datasources/jdbc-driver=%s:add(driver-name="%s",driver-module-name="%s",driver-xa-datasource-class-name="%s")' 		% (driverName, driverName, driverModuleName, xaClasName)
    cmd = prependProfilePath(cmd) #prefix with profile if deploying to domain
    executeCmd(cmd)  #used to execute a JBoss Cli command.

## Extend the plugin with custom control task

The plugin has the capability to add control tasks to `jbossdm.CliManagedDeployed` or `jbossdm.CliManagedContainer`. The control task can be specified as a Jython script that will be executed on the XL Deploy Server or as an operating system shell script that will be run on the target host. The operating system shell script is first processed with FreeMarker before being executed.

### Create a Jython-based control task to list JDBC drivers in a stand-alone server

`synthetic.xml` snippet:

    <type-modification type="jbossdm.StandaloneServer">
      <property name="listJdbcDriversPythonTaskScript" hidden="true" default="jboss/dm/container/list-jdbc-drivers.py"/>
      <!-Note "PythonTaskScript" is appended to the method name to determine the script to run. -->
      <method name="listJdbcDrivers"/>
    </type-modification>

`list-jdbc-drivers.py` snippet:

    drivers = executeCmd("/subsystem=datasources:installed-drivers-list")
    logOutput(drivers)  #outputs to the step log

### Start the stand-alone server

`synthetic.xml` snippet:

    <type-modification type="jbossdm.StandaloneServer">
      <property name="startShellTaskScript" hidden="true" default="jboss/dm/container/start-standalone"/>
      <!-Note "ShellTaskScript" is appended to the method name to determine the script to run. -->
      <method name="start"/>
    </type-modification>

`start-standalone.sh` snippet:

    nohup ${container.home}/bin/standalone.sh >>nohup.out 2>&1 &
    sleep 2
    echo background process to start standalone server executed.
