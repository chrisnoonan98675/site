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

The XL Deploy Oracle WebLogic server (WLS) plugin adds capability for managing deployments and resources on WebLogic server. It works out of the box for deploying/undeploying application artifacts, datasource and other JMS resources, and can easily be extended to support more deployment options or management of new artifacts/resources on WLS.

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

The plugin works with the standard deployment package of DAR format. The following is a sample `deployit-manifest.xml` file that can be used to create a WebLogic specific deployment package. It contain declarations for an EAR file (`wls.Ear`), a datasource (`wls.DataSourceSpec`) and two JMS resources.

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="1.0" application="SampleApp">
        <deployables>
            <jee.Ear name="sampleApp.ear" file="sampleApp.ear">
            </jee.Ear>
            <wls.DataSourceSpec name="testDatasource">
                <jndiNames>jdbc/sampleDatasource</jndiNames>
                <url>jdbc:mysql://localhost/test</url>
                <driverName>com.mysql.jdbc.Driver</driverName>
                <username>{{DATABASE_USERNAME}}</username>
                <password>{{DATABASE_PASSWORD}}</password>
            </wls.DataSourceSpec>
            <wls.QueueSpec name="sampleQueue">
                <jmsModuleName>{{JMS_MODULE_NAME}}</jmsModuleName>
                <jndiName>jms/testQueue</jndiName>
            </wls.QueueSpec>
            <wls.ConnectionFactorySpec name="sampleCf">
                <jmsModuleName>{{JMS_MODULE_NAME}}</jmsModuleName>
                <jndiName>jms/sampleCf</jndiName>
            </wls.ConnectionFactorySpec>
        </deployables>
    </udm.DeploymentPackage>

## Deploying applications

The way an application is deployed to a container can be influenced by modifying properties of the corresponding deployed. 

For example, if an EAR is to be deployed as a *versioned* application, using *nostage* mode, specify these properties in the deployed EAR module (`wls.EarModule`):

* `versioned` = true (or on UI, check the checkbox)
* `stage mode` = NoStage (or on UI, choose NoStage from the dropdown)
* `staging directory` = absolute path of the directory where EAR is to be uploaded

Similarly, if the deployed application needs to be upgraded using the *side by side* deployment strategy, modify these properties in the deployed EAR module (`wls.EarModule`):

* `staging directory` = new path of the directory where the new version of EAR will be uploaded
* `deployment strategy` = *SIDE_BY_SIDE* (or on the UI, choose SIDE_BY_SIDE from the dropdown)
* `retire timeout` = Time interval (seconds) if a timeout period is needed for retiring the previous version of the application

## Shared libraries

When updating a shared library (`wls.SharedLibraryWarModule` or `wls.SharedLibraryJarModule`), the plugin will query the repository to find all the applications (`wls.Ear` and `wls.War`, property `sharedLibraries`) that depends on it. For each of them, the plugins will add stop/undeploy/deploy/start steps before and after the library update.

The `wls.War` and `wls.Ear` types have a `SharedLibraries` property whose default value could be set to {% raw %}`{{SHARED_LIBRARIES}}`{% endraw %} in the deployment package. So a dictionary may contain the value and this value can be a list of configuration item IDs.

For example, a deployed EAR module wants to link two deployed shared libraries, `libfoo` and `libbar`. The target is a `wls.Cluster` (`/Infrastructure/wlsDomain-103/cluster-1`). The dictionary can be set up as follows:

* `SHARED_LIBRARIES`=`{% raw %}{{TARGET}}{% endraw %}/libfoo,{% raw %}{{TARGET}}{% endraw %}/libbar`
* `TARGET`=`/Infrastructure/wlsDomain-103/cluster-1`

## Non-versioned and versioned artifacts

The plugin allows to deploy non versioned artifacts (EAR, WAR, EJBJAR) as a versioned artifact. In this case, the plugin computes automatically the version using this pattern: `Application-VersionOfThePackage`. Of course, if you artifact is packaged with a version (for example, a shared library), the version will be read from the manifest file.

## Targeting to multiple containers

Certain deployables can be targeted to multiple containers. For example, an Ear can be targeted to two clusters. Similarly a datasource can be targeted to two clusters.

Note that the way WLS plugin handles this multiple targeting is by generating steps for each targeting. So for example, if a datasource is targeted to two cluster (say Cluster-1, Cluster-2), XL Deploy will create two datasource creation steps, wherein:

* The first step will create the datasource on Cluser-1, with all the properties of the deployed datasource. 
* The second step will add Cluster-2 to the target list of the datasource created in first step. If there are difference in the datasource values of this deployed, it *overrides* the previous value.

Since the second targeting overrides the properties of the first targeting, take utmost care to keep the properties of the deployeds (of the same deployable)  uniform across each other.

Similar to creation, the following sequence of steps occurs if destroy operation takes place for such a multiple targeted datasource:

* The first step will remove Cluster-1 from datasource target's list
* The second step will remove Cluster-2 from datasource target's list, and since the datasource has no target set on it, it destroys the datasource

Note that the actual datasource destruction takes place in the second step, and the first step simply removes the first container from datasource targets.

When deploying `wls.Ear`, `wls.War`, `wls.EjbJar` and shared library artifacts to multiple target servers, WLS plugin will, by default, create a separate deployment step for each target server, e.g.:

* Deploy PetClinic-1.0.ear on wlserver-1
* Deploy PetClinic-1.0.ear on wlserver-2, etc.

The WLS plugin also supports targeting multiple managed servers in one command by setting the `Deployment mode` on the `wls.Domain` to `MULTI_TARGET`. In this case it will generate a deployment plan containing only one deployment step for all target servers in the domain:

* Deploy PetClinic-1.0.ear on wldomain: wlserver-1, wlserver-2

## Creating resources

XL Deploy handles the creation of resources in the same way it handles deploying an application. If needed, you can trigger a restart by setting the `restartTarget` parameter to true.

## Copying files

XL Deploy handles the copy of files targeted on a `wls.Server` or `wls.Cluster`. If needed, you can trigger a restart by setting the `restartTarget` parameter to true.

## Managing JMS resources

The WLS plugin greatly simplifies the management of JMS resources. It does this by automatically managing the JMS modules and sub-deployments needed for JMS resources, letting the user to focus on the actual JMS resource he needs to manage.

For example, the followings is the sequence of steps that happens behind the scene when a JMS resource like Queue is created:

* The JMS module name is specified by user in deployed resource (for example, the `jmsModuleName` property of `wls.Queue`) 
* The WLS plugin automatically creates the *module* if it is not present, otherwise adds the deployed container to existing module targets
* The WLS plugin automatically creates a *subdeployment* if is not present, otherwise adds the deployed container to existing subdeployment targets
* The WLS plugin creates/updates the JMS resource and assign the subdeployment created in previous step as the resource subdeployment

Similarly, the destruction of a JMS resource is handled in the following way:

* The resource container is removed from its subdeployment targets.
* Destroy the JMS resource only if its subdeployment targets list is empty (if it is the last one) 
* Destroy the subdeployment automatically if it contains no targets
* Destroy JMS module _if no other JMS resources are using it_.

The thing to note is that the WLS plugin manage modules intelligently unless you want to use your own.
