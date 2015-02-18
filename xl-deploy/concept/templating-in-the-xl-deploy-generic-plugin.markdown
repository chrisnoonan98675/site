---
title: Templating in the XL Deploy Generic plugin
categories:
- xl-deploy
subject:
- Generic plugin
tags:
- generic
- plugin
- template
---

When defining and using CIs with the Generic Model plugin, the need arises to use variables in certain CI properties and scripts. The most obvious use is to include properties from the deployment itself, such as the names or locations of files in the deployment package. XL Deploy uses the [FreeMarker](http://freemarker.sourceforge.net/) templating engine for this.

When performing a deployment using the Generic Model plugin, all CIs and scripts are processed in FreeMarker. This means that placeholders can be used in CI properties and scripts to make them more flexible. FreeMarker resolves placeholders using a _context_, a set of objects defining the template's environment. This context depends on the type of CI being deployed. 

For all CIs, the context variable `step` refers to the current step object and the context variable `statics` can be used to access static methods on any class. See [the section accessing static methods in the FreeMarker manual](http://freemarker.org/docs/pgui_misc_beanwrapper.html#autoid_55).

For deployed CIs, the context variable `deployed` refers to the current CI instance. For example:

	<type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
	      container-type="tc.Server">
		...
	    <property name="targetFile" default="${deployed.name}-ds.xml" hidden="true"/>
		...
	</type>

Additionally, when performing a `MODIFY` operation the context variable `previousDeployed` refers to the previous version of the current CI instance. For example:

    #!/bin/sh
    echo "Uninstalling ${previousDeployed.name}"
    rm ${deployed.container.home}/${previousDeployed.name}

    echo "Installing ${deployed.name}"
    cp ${deployed.file} ${deployed.container.home}

For container CIs, the context variable `container` refers to the current container instance. For example:

	<type type="tc.Server" extends="generic.Container">
	    <property name="home" default="/tmp/tomcat"/>
		<property name="targetDirectory" default="${container.home}/webapps" hidden="true"/>
	</type>

A special case is when referring to an artifact in the placeholder. For example, when deploying a CI representing a WAR file, the following placeholder can be used to refer to that file (assuming there is a `file` property on the deployable):

	${deployed.file}

In this case, XL Deploy will copy the referred artifact to the target container so that the file is available to the executing script. A script containing a command like the following would therefore copy the file represented by the deployable to it's installation path on the remote machine:

	cp ${deployed.file} /install/path

## Description of file-related placeholders

{:.table .table-striped}
| Placeholder | Description |
| ----------- | ----------- |
| `${deployed.file}` | Complete path of the uploaded file, e.g. `/tmp/ot-12345/generic_plugin.tmp/PetClinic-1.0.ear` |
| `${deployed.deployable.file}` | Complete path of the uploaded deployable file (no placeholder replacement), e.g. `/tmp/ot-12345/generic_plugin.tmp/PetClinic-1.0.ear` |
