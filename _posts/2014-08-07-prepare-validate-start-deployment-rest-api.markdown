---
title: How to prepare, validate, and start a deployment using the XL Deploy REST API
author: amit_mohleji
categories:
- xl-deploy
tags:
- deployment
- api
---

A deployment in XL Deploy may look like a single action when triggered by [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin), [Bamboo](http://docs.xebialabs.com/releases/latest/bamboo-xl-deploy-plugin/bambooPluginManual.html), [XL Release](http://docs.xebialabs.com/releases/latest/xl-release/reference_manual.html#xl-deploy-task), or other plugins. However, preparing, validating, and executing a deployment is a sequence of individual steps that you may want to trigger from an integration point such as cloud management, provisioning, release management tools, and others. Here, we provide an example of walking through each of these steps with the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html).

Before we get started, we highly recommend that you read [this explanation of the deployment planning sequence](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#deployments-and-plugins), watch the [*Performing an initial deployment*](http://player.vimeo.com/video/97815293) and [*Performing an update deployment*](http://player.vimeo.com/video/99837505) videos, and carry out a few simple [deployments via the XL Deploy UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#deployments) yourself. This will give a much better understanding of the individual steps involved and how they related to the API calls we are going to use.

Here, we will describe the following sequence:

1. [Preparing an initial deployment](#preparing-an-initial-deployment)
1. [Previewing the deployment plan before mapping](#previewing-the-deployment-plan-before-mapping)
1. [Mapping deployables to the target environment](#mapping-deployables-to-the-target-environment)
1. [Previewing the deployment plan after mapping](#previewing-the-deployment-plan-after-mapping)
1. [Validating the deployment](#validating-the-deployment)
1. [Creating a deployment task](#creating-a-deployment-task)

## Preparing an initial deployment

Our first step is to call [`GET /deployment/prepare/initial`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/initial:GET) to retrieve a "deployment spec". For an initial deployment, the parameters we need to pass are the ID of the [target environment](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#environment) and the ID of the [deployment package](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployment-package) we are trying to deploy.

Input:

	curl -u<username>:<password> "http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/prepare/initial/?environment=Environments/Dev/TEST&version=Applications/PetPortal/1.0"

Output:

	<deployment id="deployment-be38a6b8-6743-40e7-bfcf-433083494097" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds/>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

The deployment spec that is returned (it's actually called a [`Deployment`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.Deployment.html) object) describes the components of our deployment package that we want deployed to [target containers](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#containers).

We're preparing an initial deployment here, which we can only do if there is no version of the application currently deployed to the target environment. That's also why we get a "blank" deployment spec, with an empty set of [deployed items](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployeds).

If there already is a version of the application running in the target environment, we need to call [`/deployment/prepare/update`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/update:GET) instead, passing the ID of the [`DeployedApplication`](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmdeployedapplication) representing the current version of the application running in the environment instead of the environment ID.

The deployment spec returned in that case will not be "blank", but will contain [`<deployeds>`](https://support.xebialabs.com/entries/30905535-Understanding-Deployables-and-Deployeds) describing the state of the currently deployed application version.

Whether we start with an initial or update deployment, the subsequent steps are the same: we need to store the deployment spec returned by the server, modify it to describe how we want the new application version to be deployed, and ask XL Deploy to prepare a deployment plan to make our desired state actually happen.

First, though, we will see the deployment plan that the XL Deploy server calculates based on our current deployment spec.

## Previewing the deployment plan before mapping

We can call [`POST /deployment/preview`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/preview:POST) to ask XL Deploy to calculate a deployment plan based on our current deployment spec and return the steps that would be executed. Here, we assume that we've stored the deployment spec returned by the previous call in `/tmp/deployment-spec.xml`.

Input:

	curl -u<username>:<password> -X POST -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/preview -d@/tmp/deployment-spec.xml

Content of `/tmp/deployment-spec.xml`:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds/>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

Output:

	<preview id="deployment-c90ec677-0be2-49bf-906f-650766740c36">
	  <steps>
		<step failures="0" state="PENDING">
		  <description>Logging release authorization for deployment of &apos;Applications/PetPortal/1.0&apos;</description>
		  <log></log>
		  <metadata>
			<order>9</order>
			<previewAvailable>false</previewAvailable>
		  </metadata>
		</step>
	  </steps>
	</preview>

The returned object is a `TaskPreview`, which is very similar to a [`TaskWithSteps`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.TaskWithSteps.html) object. Unsurprisingly, there's nothing really going on in this deployment plan, apart from the standard auditing step that ensures all release conditions that have been met are added to the task log. That's because our deployment spec is still "blank"; we're not asking XL Deploy to deploy any of the application components to the containers in our target environments.

That's what we'll do next, by asking XL Deploy to automatically map our deployables to the target containers.

## Mapping deployables to the target environment

We'll call [`POST /deployment/prepare/deployeds`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/deployeds:POST) to update our deployment spec by automatically mapping our deployables to the target containers in our environment.

Input:

	curl -u<username>:<password> -X POST -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/prepare/deployeds -d@/tmp/deployment-spec.xml

Content of /tmp/deployment-spec.xml:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds/>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

Output:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds>
		<sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql">
		  <deployable ref="Applications/PetPortal/1.0/sql"/>
		  <container ref="Infrastructure/Dev/Database-1/MySql"/>
		  <placeholders/>
		</sql.ExecutedSqlScripts>
		<jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear">
		  <deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/>
		  <container ref="Infrastructure/Dev/Appserver-1/JBoss"/>
		  <placeholders/>
		</jbossas.EarModule>
	  </deployeds>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment> 

That's more like it! The server has automatically mapped the [SQL scripts](https://support.xebialabs.com/entries/docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#sql-scripts) in the deployment package to the [MySQL client](http://docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#sql-client), and the [EAR file](http://docs.xebialabs.com/releases/latest/deployit/jeePluginManual.html#jeeear) to the [JBoss server](http://docs.xebialabs.com/releases/latest/jbossas-plugin/jbossPluginManual.html#containers), to create two deployed items. It has also replaced [placeholders](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#placeholders) in the deployable items with the specific values defined for this environment in [dictionaries](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#dictionaries). You can [use tags for more fine-grained control](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#tag-based-deployments) over the automated mapping process, by the way.

The call we made, which is equivalent to the "auto-map all" button in the UI, is not the only way to generate the deployed items for our deployment spec. You can also simply specify the desired deployeds directly in the XML, or use of the the `/deployment/generate` calls provided by the [`DeploymentService`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html), which are equivalent to mapping individual deployables via the UI.

In general, though, it's recommend best practice to use tags in your packages and environments so that this call just "does the right thing". This will make it very easy to use XL Deploy from other tools as part of your delivery pipeline.

Now that we are actually asking XL Deploy to create two new deployed items, let's see what the calculated deployment plan looks like.

## Previewing the deployment plan after mapping

We'll assume we've saved the updated deployment spec returned from the previous call back to /tmp/deployment-spec.xml. We can then call [`POST /deployment/preview`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/preview:POST) again to preview the deployment plan generated by XL Deploy for our updated spec:

Input:

	curl -u<username>:<password> -X POST -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/preview -d@/tmp/deployment-spec.xml

Content of `/tmp/deployment-spec.xml`:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds>
		<sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql">
		  <deployable ref="Applications/PetPortal/1.0/sql"/>
		  <container ref="Infrastructure/Dev/Database-1/MySql"/>
		  <placeholders/>
		</sql.ExecutedSqlScripts>
		<jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear">
		  <deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/>
		  <container ref="Infrastructure/Dev/Appserver-1/JBoss"/>
		  <placeholders/>
		</jbossas.EarModule>
	  </deployeds>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

Output:

	<preview id="deployment-c90ec677-0be2-49bf-906f-650766740c36">
	  <steps>
		<step failures="0" state="PENDING">
		  <description>Logging release authorization for deployment of &apos;Applications/PetPortal/1.0&apos;</description>
		  <log></log>
		  <metadata>
			<order>9</order>
			<previewAvailable>false</previewAvailable>
		  </metadata>
		</step>
		<step failures="0" state="PENDING">
		  <description>Stop JBoss</description>
		  <log></log>
		  <metadata>
			<order>10</order>
			<previewAvailable>true</previewAvailable>
		  </metadata>
		</step>
		<step failures="0" state="PENDING">
		  <description>Deploy PetClinic-ear on JBoss</description>
		  <log></log>
		  <metadata>
			<order>50</order>
			<deployed_0>Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear</deployed_0>
			<previewAvailable>false</previewAvailable>
		  </metadata>
		</step>
		<step failures="0" state="PENDING">
		  <description>Run 1-create-table.sql on MySql</description>
		  <log></log>
		  <metadata>
			<order>50</order>
			<deployed_0>Infrastructure/Dev/Database-1/MySql/sql</deployed_0>
			<previewAvailable>true</previewAvailable>
		  </metadata>
		</step>
		<step failures="0" state="PENDING">
		  <description>Start JBoss</description>
		  <log></log>
		  <metadata>
			<order>90</order>
			<previewAvailable>true</previewAvailable>
		  </metadata>
		</step>
	  </steps>
	</preview>  

This time, we have some "real" deployment steps to stop the JBoss server, deploy the EAR file, run the database update scripts, and restart the server. These steps have been contributed by the [Database plugin](http://docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#deployed-actions-table) and [JBoss plugin](http://docs.xebialabs.com/releases/latest/jbossas-plugin/jbossPluginManual.html#deployed-actions-table), respectively. See [here](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#the-planning-stage) for a more detailed description on how plugins interact to contribute steps to the overall deployment plan.

Since XL Deploy has successfully been able to generate a deployment plan based on our current deployment spec, we can reasonably assume that the spec is actually valid. We can also check that explicitly however and, if our current spec does not happen to be valid, get the server to return any validation errors.

## Validating the deployment

We can pass our deployment spec to [`POST /deployment/validate`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/validate:POST) to validate the deployment spec:

Input:

	curl -u<username>:<password> -X POST -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/validate -d@/tmp/deployment-spec.xml

Content of `/tmp/deployment-spec.xml`:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds>
		<sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql">
		  <deployable ref="Applications/PetPortal/1.0/sql"/>
		  <container ref="Infrastructure/Dev/Database-1/MySql"/>
		  <placeholders/>
		</sql.ExecutedSqlScripts>
		<jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear">
		  <deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/>
		  <container ref="Infrastructure/Dev/Appserver-1/JBoss"/>
		  <placeholders/>
		</jbossas.EarModule>
	  </deployeds>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

Output:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds>
		<sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql">
		  <deployable ref="Applications/PetPortal/1.0/sql"/>
		  <container ref="Infrastructure/Dev/Database-1/MySql"/>
		  <placeholders/>
		</sql.ExecutedSqlScripts>
		<jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear">
		  <deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/>
		  <container ref="Infrastructure/Dev/Appserver-1/JBoss"/>
		  <placeholders/>
		</jbossas.EarModule>
	  </deployeds>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

As described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.plugin.api.udm.ConfigurationItem.html#validation-messages), validation errors are indicated by a `<validation-messages>` block nested within the ci with errors. There are no validation errors in our case, as we anticipated.

Finally, then, we can ask XL Deploy to convert our validated deployment spec into a task that can be executed.

## Creating a deployment task

We can submit our deployment spec to the server using `POST /deployment/` to create a deployment task that we can run:

Input:

	curl -u<username>:<password> -X POST -H "content-type:application/xml" http://<xl-deploy-server>:<xl-deploy-port>/deployit/deployment/ -d@/tmp/deployment-spec.xml

Content of `/tmp/deployment-spec.xml`:

	<deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL">
	  <application>
		<udm.DeployedApplication id="Environments/Dev/TEST/PetPortal">
		  <version ref="Applications/PetPortal/1.0"/>
		  <environment ref="Environments/Dev/TEST"/>
		  <deployeds/>
		  <orchestrator/>
		  <optimizePlan>true</optimizePlan>
		</udm.DeployedApplication>
	  </application>
	  <deployeds>
		<sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql">
		  <deployable ref="Applications/PetPortal/1.0/sql"/>
		  <container ref="Infrastructure/Dev/Database-1/MySql"/>
		  <placeholders/>
		</sql.ExecutedSqlScripts>
		<jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear">
		  <deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/>
		  <container ref="Infrastructure/Dev/Appserver-1/JBoss"/>
		  <placeholders/>
		</jbossas.EarModule>
	  </deployeds>
	  <deployables>
		<ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/>
		<ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/>
	  </deployables>
	  <containers>
		<ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/>
		<ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/>
	  </containers>
	</deployment>

Output:

	bd4aef1c-3a85-4d1a-a839-03ae619d01df

The [task](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task) ID returned by the server can be used to start and inspect the deployment task using the [`TaskService`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html). See [this article](/start-task-retrieve-task-info-rest-api) for an example of starting a task using the REST API.
