---
title: Templating in the XL Deploy Generic plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- generic
- plugin
- template
- freemarker
---

When you define and use configuration items (CIs) with the Generic Model plugin, you may need to use variables in certain CI properties and scripts. For example, this is how you can include properties from the deployment itself, such as the names or locations of files in the deployment package. XL Deploy uses the [FreeMarker](http://freemarker.sourceforge.net/) templating engine for this.

When performing a deployment using the Generic Model plugin, all CIs and scripts are processed in FreeMarker. This means that you can use placeholders in CI properties and scripts to make them more flexible. FreeMarker resolves placeholders using a _context_, which is a set of objects defining the template's environment. This context depends on the type of CI being deployed.

For all CIs, the context variable `step` refers to the current step object. You can use the context variable `statics` to access static methods on any class. See [the section on accessing static methods in the FreeMarker manual](http://freemarker.org/docs/pgui_misc_beanwrapper.html#autoid_55).

## Deployed CIs

For deployed CIs, the context variable `deployed` refers to the current CI instance. For example:

	<type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
	      container-type="tc.Server">
		...
	    <property name="targetFile" default="${deployed.name}-ds.xml" hidden="true"/>
		...
	</type>

Additionally, when performing a `MODIFY` operation, the context variable `previousDeployed` refers to the previous version of the current CI instance. For example:

    #!/bin/sh
    echo "Uninstalling ${previousDeployed.name}"
    rm ${deployed.container.home}/${previousDeployed.name}

    echo "Installing ${deployed.name}"
    cp ${deployed.file} ${deployed.container.home}

## Container CIs

For container CIs, the context variable `container` refers to the current container instance. For example:

	<type type="tc.Server" extends="generic.Container">
	    <property name="home" default="/tmp/tomcat"/>
		<property name="targetDirectory" default="${container.home}/webapps" hidden="true"/>
	</type>

## Referring to an artifact

A special case is when referring to an artifact in a placeholder. For example, when deploying a CI representing a WAR file, the following placeholder can be used to refer to that file (assuming there is a `file` property on the deployable):

	${deployed.file}

In this case, XL Deploy will copy the referred artifact to the target container so that the file is available to the executing script. A script containing a command like the following would therefore copy the file represented by the deployable to its installation path on the remote machine:

	cp ${deployed.file} /install/path

### File-related placeholders

{:.table .table-striped}
| Placeholder | Description | Example |
| ----------- | ----------- | ------- |
| `${deployed.file}` | Complete path of the uploaded file | `/tmp/ot-12345/generic_plugin.tmp/PetClinic-1.0.ear` |
| `${deployed.deployable.file}` | Complete path of the uploaded deployable file (no placeholder replacement) | `/tmp/ot-12345/generic_plugin.tmp/PetClinic-1.0.ear` |

## Deployment plan steps

The following placeholders are available for deployment plan steps:

{:.table .table-striped}
| Placeholder | Description |
| ----------- | ----------- |
| `${step.uploadedArtifactPath}` | Path of the uploaded artifact |
| `${step.hostFileSeparator}` | File separator; depends on the operating system of the target machine |
| `${step.localConnection}` | Name of the local connection |
| `${step.retainRemoteWorkingDirOnCompletion}` | Whether to leave the working directory after the action is completed |
| `${step.hostLineSeparator}` | Line separator; depends on the operating system of the target machine |
| `${step.scriptTemplatePath}` | Path to the FreeMarker template |
| `${step.class}` | Step Java class |
| `${step.preview}` | Preview of the step |
| `${step.remoteWorkingDirPath}` | Path of the remote working directory |
| `${step.remoteConnection}` | Name of the remote connection |
| `${step.scriptPath}` | Path of the script |
| `${step.artifact}` | Artifact to be uploaded |
| `${step.remoteWorkingDirectory}` | Remote working directory name |

## Accessing the ExecutionContext

In XL Deploy 4.5.3 and later, the Generic plugin can access the [ExecutionContext](/xl-deploy/latest/javadoc/udm-plugin-api/index.html?com/xebialabs/deployit/plugin/api/flow/ExecutionContext.html) and use it in a FreeMarker template. For example:

    <type type="demo.DeployedStuff" extends="generic.ExecutedScript" deployable-type="demo.Stuff" container-type="overthere.SshHost">
      <generate-deployable type="demo.Stuff" extends="generic.Resource"/>
      <property name="P1" default="X"/>
      <property name="P2" default="Y"/>
      <property name="P3" default="Z"/>
      <property name="createScript" default="stuff/create" hidden="true"/>
    </type>

Sample FreeMarker template:

    echo "${deployed.P1}"
    echo "${deployed.P2}"
    echo "${deployed.P3}"
    echo "${context}"
    echo "${context.getClass()}"
    echo "${context.getTask().getId()}"
    echo "${context.getTask().getUsername()}"

    echo "display metadata"
    <#list context.getTask().getMetadata()?keys as k>
    echo "${k} = ${context.getTask().getMetadata()[k]}"
    </#list>
    echo "/display metadata"
