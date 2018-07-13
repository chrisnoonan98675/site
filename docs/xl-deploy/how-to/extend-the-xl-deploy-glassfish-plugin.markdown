---
title: Extend the XL Deploy GlassFish plugin
categories:
- xl-deploy
subject:
- GlassFish
tags:
- glassfish
- middleware
- plugin
- jython
- extension
---

The [XL Deploy GlassFish plugin](/xl-deploy/concept/glassfish-plugin.html) is designed to be extended through XL Deploy's plugin API type system and Jython. The plugin wraps the GlassFish command-line interface (CLI) with a Jython runtime environment, thus allowing extenders to interact with GlassFish and XL Deploy from the script. Note that the Jython script is executed on the XL Deploy Server itself and has full access to the following XL Deploy objects:

* `deployed`: The current deployed object on which the operation has been triggered.
* `step`: The step object that the script is being executed from. Exposes an Overthere remote connection for file manipulation and a method to execute GlassFish CLI commands.
* `container`: The container object to which the deployed is targeted to.
* `delta`: The delta specification that lead to the script being executed.
* `deployedApplication`: The entire deployed application.

The plugin associates **Create**, **Modify**, **Destroy**, **Noop** and **Inspect** operations received from XL Deploy with jython scripts that need to be executed for the specific operation to be performed.

There also exists an advanced method to extend the plugin, but the implementation of this form of extension needs to be written in the Java programming language and consists of writing so-called `Deployed contributors`, `PlanPreProcessors` and `Contributors`.

## Add additional properties

GlassFish artifacts and resources support the concept of additional properties. These properties are normally specified by using the `--properties` argument of GlassFish CLI commands.

XL Deploy can be extended very easily to add one or more additional properties. You can add them by extending a type synthetically. You need to add the property into the category "Additional Properties".

For example:

	<type-modification type="glassfish.WarModule">
	    <property name="keepSessions" kind="boolean" category="Additional Properties" default="true"/>
	</type-modification>

This will result in adding the additional property `keepSessions` to be available on the CI, with a default value of `true`. This will result in deploying the application with the GlassFish CLI argument `--properties keepSessions=true`.

## Extend the plugin with a custom control task

The plugin has the capability to add control tasks to `glassfish.CliManagedDeployed` or `glassfish.CliManagedContainer`. The control task can be specified as a Jython script that will be executed on the XL Deploy server. The Jython script will execute `asadmin` commands on the remote host.

### Creating a Jython-based control task to list JDBC drivers in a StandaloneServer

`synthetic.xml` snippet:

	<type-modification type="glassfish.Domain">
	  <method name="listClusters" label="List clusters" delegate="asadmin" script="list-clusters.py" >
	</type-modification>

`list-clusters.py` snippet:

	logOutput("Listing clusters")
    result = executeCmd('list-clusters')
    logOutput(result.output)
    logOutput("Done.")

The script will execute the `list-clusters` command using `asadmin` on the remote host and print the result.
