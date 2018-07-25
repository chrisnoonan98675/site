---
title: Sample use of the XL Deploy Generic plugin
categories:
xl-deploy
subject:
Bundled plugins
tags:
generic
plugin
weight: 359
---

This is an example of using the Generic Model plugin to implement support for a simple middleware platform. Deployment to this platform is done by simply copying a WAR archive to the right directory on the container. Resources are created by copying configuration files into the container's configuration directory. The Tomcat application server works in a very similar manner.

By defining a container and several other CIs based on CIs from the Generic Model plugin, it is possible to add support for deploying to this platform to XL Deploy.

## Defining the container

To use any of the CIs in the Generic Model plugin, they need to be targeted to a `generic.Container`. This snippet shows how to define a generic container as a synthetic type:

	<type type="tc.Server" extends="generic.Container">
	    <property name="home" default="/tmp/tomcat"/>
	</type>

	<type type="tc.UnmanagedServer" extends="tc.Server">
	    <property name="startScript" default="tc/start.sh" hidden="true"/>
	    <property name="stopScript" default="tc/stop.sh" hidden="true"/>
	    <property name="restartScript" default="tc/restart.sh" hidden="true"/>
	</type>

Note that the `tc.UnmanagedServer` CI defines a start, stop and restart script. The XL Deploy Server reads these scripts from the classpath. When targeting a deployment to the `tc.UnmanagedServer`, XL Deploy will include steps executing the start, stop and restart scripts in appropriate places in the deployment plan.

## Defining a configuration file

The following snippet defines a CI based on the `generic.CopiedArtifact`. The `tc.DeployedFile` CI can be targeted to the `tc.Server`. The target directory is specified as a hidden property. Note the placeholder syntax used here.

	<type type="tc.DeployedFile" extends="generic.CopiedArtifact" deployable-type="tc.File"
	      container-type="tc.Server">
	    <generate-deployable type="tc.File" extends="generic.File"/>
	    <property name="targetDirectory" default="${deployed.container.home}/conf" hidden="true"/>
	</type>

Using the above snippet, it is possible to create a package with a `tc.File` deployable and deploy it to an environment containing a `tc.UnmanagedServer`. This will result in a `tc.DeployedFile` deployed.

## Defining a WAR

To deploy a WAR file to the `tc.Server`, one possibility is to define a `tc.DeployedWar` CI that extends the `generic.ExecutedScript`. The `tc.DeployedWar` CI is generated when deploying a `jee.War` to the `tc.Server` CI. This is what the XML looks like:

	<type type="tc.DeployedWar" extends="generic.ExecutedScript" deployable-type="jee.War"
	      container-type="tc.Server">
	    <generate-deployable type="tc.War" extends="jee.War"/>
	    <property name="createScript" default="tc/install-war" hidden="true"/>
	    <property name="modifyScript" default="tc/reinstall-war" hidden="true" required="false"/>
	    <property name="destroyScript" default="tc/uninstall-war" hidden="true"/>
	</type>

When performing an initial deployment, the create script, `tc/install-war` is executed on the target container. Inside the script, a reference to the `file` property is replaced by the actual archive. Note that the script files do not have an extension. Depending on the target platform, the extension `sh` (Unix) or `bat` (Windows) is used.

The WAR file is referenced from the script as follows:

    echo Installing WAR ${deployed.deployable.file} in ${deployed.container.home}

## Defining a datasource

Configuration files can be deployed by creating a CI based on the `generic.ProcessedTemplate`. By including a `generic.Resource` in the package that is a FreeMarker template, a configuration file can be generated during the deployment and copied to the container. This snippet defines such a CI, `tc.DeployedDataSource`:

	<type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
	      container-type="tc.Server">
	    <generate-deployable type="tc.DataSource" extends="generic.Resource"/>

	    <property name="jdbcUrl"/>
	    <property name="port" kind="integer"/>
	    <property name="targetDirectory" default="${deployed.container.home}/webapps" hidden="true"/>
	    <property name="targetFile" default="${deployed.name}-ds.xml" hidden="true"/>
	    <property name="template" default="tc/datasource.ftl" hidden="true"/>
	</type>

The `template` property specifies the FreeMarker template file that the XL Deploy Server reads from the classpath. The `targetDirectory` controls where the template is copied to. Inside the template, properties like `jdbcUrl` on the datasource can be used to produce a proper configuration file.
