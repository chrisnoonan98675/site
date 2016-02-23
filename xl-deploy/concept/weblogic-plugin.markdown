---
title: Introduction to the XL Deploy WebLogic plugin
categories:
- xl-deploy
subject:
- WebLogic plugin
tags:
- weblogic
- oracle
- middleware
- plugin
---

The XL Deploy Oracle WebLogic server (WLS) plugin allows you to manage deployments and resources on a WebLogic server. It can deploy and undeploy application artifacts, as well as datasources and other JMS resources. You can easily [extend](/xl-deploy/how-to/extend-the-xl-deploy-weblogic-plugin.html) the plugin to support more deployment options or management of new artifacts or resources on WebLogic.

For information about WebLogic requirements and the configuration items (CIs) that the plugin supports, refer to the [WebLogic Plugin Reference](/xl-deploy/latest/wlsPluginManual.html).

## Features

* Deployment units
	* Enterprise application (EAR)
	* Web application (WAR)
	* Enterprise JavaBean (EJB)
	* J2EE Shared library
* Staging modes
	* Stage 
	* NoStage
* Deployment strategies
	* Classical
	* Versioned
	* Side by side
* Resources
	* Datasource
	* JMS Queue
	* JMS Topic
	* JMS uniform distributed Queue
	* JMS uniform distributed Topic
	* JMS connection factory
	* Mail Session
	* Persistence Store (file and JDBC)
* Files and folders
* Discovery

## Use in deployment packages

The WebLogic plugin works with the standard deployment package (DAR) format. The following is a sample `deployit-manifest.xml` file that can be used to create a WebLogic-specific deployment package. It contain declarations for an EAR file (`wls.Ear`), a datasource (`wls.DataSourceSpec`), and two JMS resources.

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<udm.DeploymentPackage version="1.0" application="SampleApp">
    <deployables>
        <jee.Ear name="sampleApp.ear" file="sampleApp.ear">
        </jee.Ear>
        <wls.DataSourceSpec name="testDatasource">
            <jndiNames>jdbc/sampleDatasource</jndiNames>
            <url>jdbc:mysql://localhost/test</url>
            <driverName>com.mysql.jdbc.Driver</driverName>
            <username>{% raw %}{{DATABASE_USERNAME}}{% endraw %}</username>
            <password>{% raw %}{{DATABASE_PASSWORD}}{% endraw %}</password>
        </wls.DataSourceSpec>
        <wls.QueueSpec name="sampleQueue">
            <jmsModuleName>{% raw %}{{JMS_MODULE_NAME}}{% endraw %}</jmsModuleName>
            <jndiName>jms/testQueue</jndiName>
        </wls.QueueSpec>
        <wls.ConnectionFactorySpec name="sampleCf">
            <jmsModuleName>{% raw %}{{JMS_MODULE_NAME}}{% endraw %}</jmsModuleName>
            <jndiName>jms/sampleCf</jndiName>
        </wls.ConnectionFactorySpec>
    </deployables>
</udm.DeploymentPackage>
{% endhighlight %}

## Deploying applications

You can influence the way an application is deployed to a container by modifying the properties of the corresponding deployed. For example, if an EAR file is to be deployed as a *versioned* application using *nostage* mode, you can specify these properties in the deployed EAR module (`wls.EarModule`):

* `versioned` = True (in the XL Deploy user interface, check the checkbox)
* `stage mode` = `NoStage` (in the XL Deploy user interface, choose NoStage from the drop-down list)
* `staging directory` = Absolute path of the directory where the EAR file should be uploaded

Similarly, if the deployed application needs to be upgraded using the *side by side* deployment strategy, modify these properties in the deployed EAR module (`wls.EarModule`):

* `staging directory` = New path of the directory where the new version of EAR will be uploaded
* `deployment strategy` = `SIDE_BY_SIDE` (in the XL Deploy user interface, choose SIDE_BY_SIDE from the drop-down list)
* `retire timeout` = Interval (in seconds) if a timeout period is needed to retire the previous version of the application

## Updating shared libraries

When updating a shared library (`wls.SharedLibraryWarModule` or `wls.SharedLibraryJarModule`), the plugin will query the repository to find all the applications (`wls.Ear` and `wls.War`, property `sharedLibraries`) that depends on it. For each of them, the plugins will add stop, undeploy, deploy, and start steps before and after the library update.

The `wls.War` and `wls.Ear` types have a `SharedLibraries` property whose default value could be set to {% raw %}`{{SHARED_LIBRARIES}}`{% endraw %} in the deployment package. So a dictionary may contain the value and this value can be a list of configuration item IDs.

For example, a deployed EAR module wants to link two deployed shared libraries, `libfoo` and `libbar`. The target is a `wls.Cluster` (`/Infrastructure/wlsDomain-103/cluster-1`). The dictionary can be set up as follows:

{:.table}
| Key | Value |
| --- | ----- |
| `SHARED_LIBRARIES` | `{% raw %}{{TARGET}}{% endraw %}/libfoo,{% raw %}{{TARGET}}{% endraw %}/libbar` |
| `TARGET`| `/Infrastructure/wlsDomain-103/cluster-1` |

## Deploying non-versioned artifacts

The plugin allows you to deploy non-versioned artifacts (such as EAR, WAR, and EJBJAR files) as versioned artifacts. In this case, the plugin automatically computes the version using the pattern `Application-VersionOfThePackage`. If the artifact is packaged with a version (for example, a shared library), the version will be read from the manifest file.

These CI properties are related to versioning:

* `automaticVersioning`: If set to "true" (its default), the plugin creates a unique version for the deployed. This means that any value that you explicitly set for the `versionIdentifier` property will be ignored.
* `versioned`: If set to "true" (its default), the artifact will be deployed as a versioned artifact.
* `versionExpression`: This is the [FreeMarker](http://freemarker.incubator.apache.org/) expression that the plugin uses to build the artifact version based on the manifest file. It applies if both `automaticVersioning` and `versioned` are set to "true".

    You can override `versionExpression` in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file; for example, `wls.WarModule.versionExpression=${manifestAttributes['Weblogic-Application-Version']}`.

For more information about artifact CI properties, refer to the [WebLogic Plugin Reference](/xl-deploy/latest/wlsPluginManual.html).

## Targeting multiple containers

Certain deployables can be targeted to multiple containers. For example, an EAR file can be targeted to two clusters. Similarly, a datasource can be targeted to two clusters.

### Create steps

To handle multiple targets, the WebLogic plugin generates steps for each targeting. For example, if a datasource is targeted to Cluster-1 and Cluster-2, XL Deploy will create two datasource creation steps:

* The first step will create the datasource on Cluster-1, with all the properties of the deployed datasource. 
* The second step will add Cluster-2 to the target list of the datasource created in first step. If there are difference in the datasource values of this deployed, it *overrides* the previous value.

Because the second targeting overrides the properties of the first targeting, ensure that the properties of the deployeds (of the same deployable) are uniform across each other.

### Destroy steps

Similar to creation, the following sequence of steps occurs if destroy operation takes place for such a multiple targeted datasource:

* The first step will remove Cluster-1 from datasource target's list
* The second step will remove Cluster-2 from datasource target's list, and since the datasource has no target set on it, it destroys the datasource

Note that the actual datasource destruction takes place in the second step, and the first step simply removes the first container from datasource targets.

### Deploy steps

When deploying `wls.Ear`, `wls.War`, `wls.EjbJar`, and shared library artifacts to multiple target servers, the WebLogic plugin will create a separate deployment step for each target server (by default). For example:

1. Deploy PetClinic-1.0.ear on wlserver-1
1. Deploy PetClinic-1.0.ear on wlserver-2

### Multi-target deployment mode

The WebLogic also supports targeting multiple managed servers in one command by setting the dDeployment mode on the `wls.Domain` to `MULTI_TARGET`. In this case, the plugin will generate a deployment plan containing only one deployment step for all target servers in the domain. For example:

1. Deploy PetClinic-1.0.ear on wldomain: wlserver-1, wlserver-2

## Creating resources

XL Deploy handles the creation of resources in the same way it handles deploying an application. If needed, you can trigger a restart by setting the `restartTarget` parameter to "true".

## Copying files

XL Deploy handles the copy of files targeted on a `wls.Server` or `wls.Cluster`. If needed, you can trigger a restart by setting the `restartTarget` parameter to true.

## Managing JMS resources

The WebLogic plugin greatly simplifies the management of JMS resources by automatically managing JMS modules and sub-deployments needed for JMS resources, allowing you to focus on the actual JMS resource you need to manage.

For example, the followings is the sequence of steps that happens behind the scene when a JMS resource such as a queue is created:

1. You specify the JMS module name in a deployed resource (for example, the `jmsModuleName` property of `wls.Queue`) 
1. The WebLogic plugin automatically creates the *module* if it is not present; otherwise, the plugin adds the deployed container to existing module targets
1. The WebLogic plugin automatically creates a *subdeployment* if is not present; otherwise the plugin adds the deployed container to existing sub-deployment targets
1. The WebLogic plugin creates/updates the JMS resource and assigns the sub-deployment created in the previous step as the resource sub-deployment

Similarly, the destruction of a JMS resource is handled as follows:

1. The resource container is removed from its sub-deployment targets
1. Destroy the JMS resource only if its sub-deployment targets list is empty (if it is the last one) 
1. Destroy the sub-deployment automatically if it contains no targets
1. Destroy JMS module _if no other JMS resources are using it_
