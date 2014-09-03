---
title: How to prepare, validate and start a deployment using the XL Deploy REST API
categories:
 
- xl-deploy
tags:
- API
- deployment
---

A deployment in XL Deploy may look like a single action when triggered by the [Jenkins](https://wiki.jenkins-ci.org/display/JENKINS/XL+Deploy+Plugin), [Bamboo](http://docs.xebialabs.com/releases/latest/bamboo-xl-deploy-plugin/bambooPluginManual.html), [XL Release](http://docs.xebialabs.com/releases/latest/xl-release/reference_manual.html#xl-deploy-task) or other plugins. Actually, though, preparing, validating and executing a deployment is a sequence of individual steps. Here, we'll give an example of walking through each of these steps via the [XL Deploy REST API](http://docs.xebialabs.com/releases/latest/deployit/rest-api/index.html).

Before we get started, we highly recommend that you read [this explanation of the deployment planning sequence](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#deployments-and-plugins), watch the&nbsp;_[Performing an initial deployment](http://player.vimeo.com/video/97815293)_ and&nbsp;_[Performing an update deployment](http://player.vimeo.com/video/99837505)_ introductory videos and carry out a few simple [deployments via the XL Deploy UI](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#deployments) yourself. This will give a much better understanding of the individual steps involved, and how they related to the API calls we are going to use here.

Here, we will describe the following sequence:

*   [Preparing an initial deployment](#prepare-for-initial-deployment)
*   [Previewing the deployment plan before mapping](#preview-steps-before-mapping)
*   [Mapping deployables to the target environment](#map-the-deployeds)
*   [Previewing the deployment plan after mapping](#preview-steps-after-mapping)
*   [Validating the deployment](#validate-updated-deployment)
*   [Creating a deployment task](#create-a-task)

### <a name="prepare-for-initial-deployment"></a>Preparing an initial deployment

Our first step is to call [<tt>GET /deployment/prepare/initial</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/initial:GET) to retrieve a "deployment spec". For an initial deployment, the parameters we need to pass are the ID of the [target environment](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#environment) and the ID of the [deployment package](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployment-package) we are trying to deploy:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; "http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/prepare/initial/?environment=Environments/Dev/TEST&amp;version=Applications/PetPortal/1.0"
> 
> **OUTPUT:**
> 
> &lt;deployment id="deployment-be38a6b8-6743-40e7-bfcf-433083494097" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds/&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;

The deployment spec that is returned (it's actually called a [<tt>Deployment</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.dto.Deployment.html) object) describes which components of our deployment package we want deployed to which [target containers](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#containers).

We're preparing an initial deployment here, which we can only do if there is no version of the application currently deployed to the target environment. That's also why we get a "blank" deployment spec, with an empty set of [deployed items](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#deployeds), here.

If there already is a version of the application running in the target environment, we need to call [<tt>/deployment/prepare/update</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/update:GET) instead, passing the ID of the [<tt>DeployedApplication</tt>](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmdeployedapplication) representing the current version of the application running in the environment instead of the environment ID.

The deployment spec returned in that case will not be "blank", but will contain [<tt>&lt;deployeds&gt;</tt>](https://support.xebialabs.com/entries/30905535-Understanding-Deployables-and-Deployeds) describing the state of the currently deployed application version.

Whether we start with an initial or update deployment, the subsequent steps are the same: we need to store the deployment spec returned by the server, modify it to describe how we want the new application version to be deployed, and ask XL Deploy to prepare a deployment plan to make our desired state actually happen.

First, though, we will see which deployment plan the XL Deploy server calculates based on our _current_ deployment spec.

### <a name="preview-steps-before-mapping"></a>Previewing the deployment plan before mapping

We can call [<tt>POST /deployment/preview</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/preview:POST) to ask XL Deploy to calculate a deployment plan based on our current deployment spec and return the steps that would be executed. Here, we assume that we've stored the deployment spec returned by the previous call in /tmp/deployment-spec.xml

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt;&nbsp;-X POST -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/preview -d@/tmp/deployment-spec.xml
> 
> **Contents of /tmp/deployment-spec.xml:
> **
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds/&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;
> 
> **OUTPUT:**
> 
> &lt;preview id="deployment-c90ec677-0be2-49bf-906f-650766740c36"&gt;
> &nbsp; &lt;steps&gt;
> &nbsp; &nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Logging release authorization for deployment of &amp;apos;Applications/PetPortal/1.0&amp;apos;&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;9&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;false&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp; &lt;/steps&gt;
> &lt;/preview&gt;

The returned object is a <tt>TaskPreview</tt>, which is very similar to a [<tt>TaskWithSteps</tt>](http://docs.xebialabs.com/releases/4.0/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.TaskWithSteps.html) object. Unsurprisingly, there's nothing really going on in this deployment plan, apart from the standard auditing step that ensures all release conditions that have been met are added to the task log. That's because our deployment spec is still "blank" - we're not asking XL Deploy to deploy any of the application components to the containers in our target environments.

That's what we'll do next, by asking XL Deploy to automatically map our deployables to the target containers.

### <a name="map-the-deployeds"></a>Mapping deployables to the target environment

We'll call [<tt>POST /deployment/prepare/deployeds</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/prepare/deployeds:POST) to update our deployment spec by automatically mapping our deployables to the target containers in our environment:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; -X POST -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/prepare/deployeds -d@/tmp/deployment-spec.xml
> 
> **<strong>Contents of /tmp/deployment-spec.xml:**</strong>
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds/&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;
> 
> **OUTPUT:**
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds&gt;
> &nbsp; &nbsp; &lt;sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/sql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Database-1/MySql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/sql.ExecutedSqlScripts&gt;
> &nbsp; &nbsp; &lt;jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Appserver-1/JBoss"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/jbossas.EarModule&gt;
> &nbsp; &lt;/deployeds&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;&nbsp;

That's more like it! The server has automatically mapped the [SQL scripts](docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#sql-scripts) in the deployment package to the [MySQL client](http://docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#sql-client), and the [EAR file](http://docs.xebialabs.com/releases/latest/deployit/jeePluginManual.html#jeeear) to the [JBoss server](http://docs.xebialabs.com/releases/latest/jbossas-plugin/jbossPluginManual.html#containers), to create two deployed items. It has also replaced [placeholders](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#placeholders) in the deployable items with the specific values defined for this environment in [dictionaries](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#dictionaries). You can [use tags for more fine-grained control](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#tag-based-deployments) over the automated mapping process, by the way.

The call we made, which is equivalent to the "auto-map all" button in the UI, is not the only way to generate the deployed items for our deployment spec. You can also simply specify the desired deployeds directly in the XML, or use of the the <tt>/deployment/generate</tt> calls provided by the [DeploymentService](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html), which are equivalent to mapping individual deployables via the UI.

In general, though, it's recommend best practice to use tags in your packages and environments so that this call just "does the right thing". This will make it very easy to use XL Deploy from other tools as part of your delivery pipeline.

Now that we are actually asking XL Deploy to create two new deployed items, let's see what the calculated deployment plan looks like.

### <a name="preview-steps-after-mapping"></a>Previewing the deployment plan after mapping

We'll assume we've saved the updated deployment spec returned from the previous call back to <tt>/tmp/deployment-spec.xml</tt>. We can then call [<tt>POST /deployment/preview </tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/preview:POST)again to preview the deployment plan generated by XL Deploy for our updated spec:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; -X POST -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/preview -d@/tmp/deployment-spec.xml
> 
> **<strong><strong>Contents of /tmp/deployment-spec.xml:**</strong></strong>
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds&gt;
> &nbsp; &nbsp; &lt;sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/sql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Database-1/MySql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/sql.ExecutedSqlScripts&gt;
> &nbsp; &nbsp; &lt;jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Appserver-1/JBoss"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/jbossas.EarModule&gt;
> &nbsp; &lt;/deployeds&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;
> 
> **OUTPUT:**
> 
> &lt;preview id="deployment-c90ec677-0be2-49bf-906f-650766740c36"&gt;
> &nbsp; &lt;steps&gt;
> &nbsp; &nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Logging release authorization for deployment of &amp;apos;Applications/PetPortal/1.0&amp;apos;&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;9&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;false&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp; &nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Stop JBoss&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;10&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;true&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp; &nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Deploy PetClinic-ear on JBoss&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;50&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;deployed_0&gt;Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear&lt;/deployed_0&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;false&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp;&nbsp;&nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Run 1-create-table.sql on MySql&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;50&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;deployed_0&gt;Infrastructure/Dev/Database-1/MySql/sql&lt;/deployed_0&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;true&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp;&nbsp;&nbsp; &lt;step failures="0" state="PENDING"&gt;
> &nbsp; &nbsp; &nbsp; &lt;description&gt;Start JBoss&lt;/description&gt;
> &nbsp; &nbsp; &nbsp; &lt;log&gt;&lt;/log&gt;
> &nbsp; &nbsp; &nbsp; &lt;metadata&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;order&gt;90&lt;/order&gt;
> &nbsp; &nbsp; &nbsp; &nbsp; &lt;previewAvailable&gt;true&lt;/previewAvailable&gt;
> &nbsp; &nbsp; &nbsp; &lt;/metadata&gt;
> &nbsp; &nbsp; &lt;/step&gt;
> &nbsp; &lt;/steps&gt;
> &lt;/preview&gt;&nbsp;&nbsp;

This time, we have some "real" deployment steps to stop the JBoss server, deploy the EAR file, run the database update scripts, and restart the server. These steps have been contributed by the [Database plugin](http://docs.xebialabs.com/releases/latest/deployit/databasePluginManual.html#deployed-actions-table) and [JBoss plugin](http://docs.xebialabs.com/releases/latest/jbossas-plugin/jbossPluginManual.html#deployed-actions-table), respectively. See [here](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#the-planning-stage) for a more detailed description on how plugins interact to contribute steps to the overall deployment plan.

Since XL Deploy has successfully been able to generate a deployment plan based on our current deployment spec, we can reasonably assume that the spec is actually valid. We can also check that explicitly however and, if our current spec does _not_ happen to be valid, get the server to return any validation errors.

### <a name="validate-updated-deployment"></a>Validating the deployment

We can pass our deployment spec to [<tt>POST /deployment/validate</tt>](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.DeploymentService.html#/deployment/validate:POST) to validate the deployment spec:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; -X POST -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/validate -d@/tmp/deployment-spec.xml
> 
> **<strong><strong><strong>Contents of /tmp/deployment-spec.xml:**</strong></strong></strong>
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds&gt;
> &nbsp; &nbsp; &lt;sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/sql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Database-1/MySql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/sql.ExecutedSqlScripts&gt;
> &nbsp; &nbsp; &lt;jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Appserver-1/JBoss"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/jbossas.EarModule&gt;
> &nbsp; &lt;/deployeds&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;
> 
> **OUTPUT:**
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds&gt;
> &nbsp; &nbsp; &lt;sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/sql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Database-1/MySql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/sql.ExecutedSqlScripts&gt;
> &nbsp; &nbsp; &lt;jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Appserver-1/JBoss"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/jbossas.EarModule&gt;
> &nbsp; &lt;/deployeds&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;

As described [here](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.plugin.api.udm.ConfigurationItem.html#validation-messages), validation errors are indicated by a <tt>&lt;validation-messages&gt;</tt> block nested within the CI with errors. In our case, there are no validation errors in our case, as we anticipated.

Finally, then, we can ask XL Deploy to convert our validated deployment spec into a task that can be executed.

### <a name="create-a-task"></a>Creating a deployment task

We can submit our deployment spec to the server using <tt>POST /deployment/</tt> to create a deployment task that we can run:

> **INPUT:**
> 
> curl -u&lt;username&gt;:&lt;password&gt; -X POST -H "content-type:application/xml" http://&lt;xl-deploy-server&gt;:&lt;xl-deploy-port&gt;/deployit/deployment/ -d@/tmp/deployment-spec.xml
> 
> **<strong><strong><strong>Contents of /tmp/deployment-spec.xml:**</strong></strong></strong>
> 
> &lt;deployment id="deployment-c90ec677-0be2-49bf-906f-650766740c36" type="INITIAL"&gt;
> &nbsp; &lt;application&gt;
> &nbsp; &nbsp; &lt;udm.DeployedApplication id="Environments/Dev/TEST/PetPortal"&gt;
> &nbsp; &nbsp; &nbsp; &lt;version ref="Applications/PetPortal/1.0"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;environment ref="Environments/Dev/TEST"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployeds/&gt;
> &nbsp; &nbsp; &nbsp; &lt;orchestrator/&gt;
> &nbsp; &nbsp; &nbsp; &lt;optimizePlan&gt;true&lt;/optimizePlan&gt;
> &nbsp; &nbsp; &lt;/udm.DeployedApplication&gt;
> &nbsp; &lt;/application&gt;
> &nbsp; &lt;deployeds&gt;
> &nbsp; &nbsp; &lt;sql.ExecutedSqlScripts id="Infrastructure/Dev/Database-1/MySql/sql"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/sql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Database-1/MySql"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/sql.ExecutedSqlScripts&gt;
> &nbsp; &nbsp; &lt;jbossas.EarModule id="Infrastructure/Dev/Appserver-1/JBoss/PetClinic-ear"&gt;
> &nbsp; &nbsp; &nbsp; &lt;deployable ref="Applications/PetPortal/1.0/PetClinic-ear"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;container ref="Infrastructure/Dev/Appserver-1/JBoss"/&gt;
> &nbsp; &nbsp; &nbsp; &lt;placeholders/&gt;
> &nbsp; &nbsp; &lt;/jbossas.EarModule&gt;
> &nbsp; &lt;/deployeds&gt;
> &nbsp; &lt;deployables&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/PetClinic-ear" type="jee.Ear"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Applications/PetPortal/1.0/sql" type="sql.SqlScripts"/&gt;
> &nbsp; &lt;/deployables&gt;
> &nbsp; &lt;containers&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Appserver-1/JBoss" type="jbossas.ServerV5"/&gt;
> &nbsp; &nbsp; &lt;ci ref="Infrastructure/Dev/Database-1/MySql" type="sql.MySqlClient"/&gt;
> &nbsp; &lt;/containers&gt;
> &lt;/deployment&gt;
> 
> **OUTPUT:**
> 
> bd4aef1c-3a85-4d1a-a839-03ae619d01df

The [task](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#task) ID returned by the server can be used to start, inspect etc. the deployment task using the [TaskService](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.TaskService.html). See [this article](https://support.xebialabs.com/entries/48023205-How-to-start-a-task-and-retrieve-task-info-using-the-XL-Deploy-REST-API) for an example of starting a task using the REST API.
