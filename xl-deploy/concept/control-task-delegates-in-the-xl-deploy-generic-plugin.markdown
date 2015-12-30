---
title: Control task delegates in the XL Deploy Generic plugin
categories:
- xl-deploy
subject:
- Generic plugin
tags:
- generic
- plugin
- control task
- freemarker
weight: 113
---

The Generic Model plugin has predefined control task delegates that have the ability to execute scripts on a target host. The delegates can be used to define control tasks on any configuration item (CI) defined in XL Deploy's type system.

## `shellScript` delegate

The `shellScript` delegate has the capability of executing a single script on a target host. 

{:.table .table-striped}
| Argument | Type | Required | Description |
| -------- | ---- | -------- | ----------- |
| `script` | STRING | Yes | The classpath to the FreeMarker template that will generate the script |
| `host` | STRING | No | The target host on which to execute the script. This argument takes an expression in the form `${..}` which indicates the property to use as the host. For example, `${thisCi.parent.host}`, `${thisCi.delegateToHost}`. In the absence of this argument, the delegate will try to resolve the host. For `udm.Deployed`-derived configuration items, the container property is used as the target host if it is an `overthere.HostContainer`. For `udm.Container`-derived CIs, the CI itself is used as the target host if it is an `overthere.HostContainer`. In all other cases, this argument is required. |
| `classpathResources` | LIST_OF_STRING | No | Comma-separated string of additional classpath resources that should be uploaded to the working directory before executing the script. |
| `templateClasspathResources` | LIST_OF_STRING | No | Comma-separated string of additional template classpath resources that should be uploaded to the working directory before executing the script. The template is first rendered and the rendered content copied to a file, with the same name as the template, in the working directory. |

Example:

	<type type="tc.DeployedDataSource" extends="generic.ProcessedTemplate" deployable-type="tc.DataSource"
	      container-type="tc.Server">
	    <generate-deployable type="tc.DataSource" extends="generic.Resource"/>
		...
	    <method name="ping" delegate="shellScript"
	        script="tc/ping.sh"
	        classpathResources="tc/ping.py"/>
	</type>

## `localShellScript` delegate

The `localShellScript` delegate has the capability of executing a single script on a the XL Deploy host.

{:.table .table-striped}
| Argument | Type | Required | Description |
| -------- | ---- | -------- | ----------- |
| `script` | STRING | Yes | The classpath to the FreeMarker template that will generate the script |
| `classpathResources` | LIST_OF_STRING | No | Comma-separated string of additional classpath resources that should be uploaded to the working directory before executing the script. |
| `templateClasspathResources` | LIST_OF_STRING | No | Comma-separated string of additional template classpath resources that should be uploaded to the working directory before executing the script. The template is first rendered and the rendered content copied to a file, with the same name as the template, in the working directory. |

Example:

	<type-modification type="udm.DeployedApplication" >
	    <method name="updateVersionDatabase" delegate="localShellScript"
	        script="cmdb/updateVersionDatabase.sh.ftl"/>
	</type>

## `shellScripts` delegate

The `shellScripts` delegate has the capability of executing multiple scripts on a target host.

{:.table .table-striped}
| Argument | Type | Required | Description |
| -------- | ---- | -------- | ----------- |
| `scripts` | LIST_OF_STRING | Yes | Comma-separated string of the classpath to the FreeMarker templates that will generate the scripts. In addition, each template can be prefixed with an alias. The format of the alias is `alias:path`. The alias can be used to define `classpathResources` and `templateClasspathResources` attributes that should be uploaded for the specific script. Example: `aliasClasspathResources` and `aliasTemplateClasspathResources` |
| `host` | STRING | No | The target host on which to execute the script. This argument takes an expression in the form `${..}` which indicates the property to use as the host. For example, `${thisCi.parent.host}`, `${thisCi.delegateToHost}`. In the absence of this argument, the delegate will try to resolve the host. For `udm.Deployed`-derived configuration items, the container property is used as the target host if it is an `overthere.HostContainer`. For `udm.Container` derived CIs, the CI itself is used as the target host if it is an `overthere.HostContainer`. In all other cases, this argument is required. |
| `classpathResources` | LIST_OF_STRING | No | Comma-separated string of additional classpath resources that should be uploaded to the working directory before executing the script. These resources are uploaded for all scripts. |
| `templateClasspathResources` | LIST_OF_STRING | No | Comma-separated string of additional template classpath resources that should be uploaded to the working directory before executing the script.The template is first rendered and the rendered content copied to a file, with the same name as the template, in the working directory. These resources are uploaded for all scripts. |

Example:

	<type type="tc.Server" extends="generic.Container">
		...
	    <method name="startAndWait" delegate="shellScripts"
	        scripts="start:tc/start.sh,tc/tailLog.sh"
	        startClasspathResources="tc/start.jar"
	        startTemplateClasspathResources="tc/password.xml"
	        classpathResources="common.jar"/>
	</type>

## `localShellScripts` delegate

The `localShellScripts` delegate has the capability of executing multiple scripts on the XL Deploy host.

{:.table .table-striped}
| Argument | Type | Required | Description |
| -------- | ---- | -------- | ----------- |
| `scripts` | LIST_OF_STRING | Yes | Comma separated string of the classpath to the FreeMarker templates that will generate the scripts. In addition, each template can be prefixed with an alias. The format of the alias is `alias:path`. The alias can be used to define `classpathResources` and `templateClasspathResources` attributes that should be uploaded for the specific script. Example: `aliasClasspathResources` and `aliasTemplateClasspathResources` |
| `classpathResources` | LIST_OF_STRING | No | Comma-separated string of additional classpath resources that should be uploaded to the working directory before executing the script. These resources are uploaded for all scripts. |
| `templateClasspathResources` | LIST_OF_STRING | No | Comma-separated string of additional template classpath resources that should be uploaded to the working directory before executing the script. The template is first rendered and the rendered content copied to a file, with the same name as the template, in the working directory. These resources are uploaded for all scripts. |

Example:

	<type-modification type="udm.Version">
	    <method name="udpateSCMandCMDB" delegate="localShellScripts"
            scripts="updateSCM:scm/update,updateCMDB:cmdb/update"
            updateSCMClasspathResources="scm/scm-connector.jar"
            updateCMDBTemplateClasspathResources="cmdb/request.xml.ftl"
            classpathResources="common.jar"/>
	</type>
