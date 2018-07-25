---
title: Troubleshoot the deployment plan
subject:
Packaging
categories:
xl-deploy
tags:
application
package
deployment
weight: 200
no_index: true
---

<html>
<div id="userMap">

            <div class="content"><a href="preparing-your-application-for-xl-deploy.html"><div class="box box1">Preparing your application for XL Deploy</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="understanding-deployable-types.html"><div class="box box1">Understanding deployable types</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="whats-in-a-deployment-package.html"><div class="box box2">What's in an application deployment package?</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-a-deployment-package.html"><div class="box box3">Create a deployment package</div></a></div>

            <div class="arrow">→</div>

            <div class="content"><a href="create-and-verify-the-deployment-plan.html"><div class="box box3">Create and verify the deployment plan</div></a></div>

            <div class="arrow">→</div>

            <div class="content" id="activeBox"><a href="troubleshoot-the-deployment-plan.html"><div class="box box5">Troubleshoot the deployment plan</div></a></div>

<div class="clearfix"></div>
</div>
</html>


    

When XL Deploy creates the deployment plan, it analyzes and integrates the steps that each plugin contributes to the plan. If the deployment plan that XL Deploy generates for you does not contain the steps that are needed to deploy your application correctly, you can troubleshoot it using several different features.

### Adjust the deployment plan

You may be able to achieve the desired deployment behavior by:

* Adjusting the properties of the CI types that you are using
* Using different CI types
* Creating a new CI type

To check the types that are available and their properties, follow the instructions provided in *Exploring CI types*. The documentation for each plugin describes the actions that are linked to each CI type.

If you cannot find the CI type that you need for a component of your application, you can add types by creating a new plugin.

### Configure an existing plugin

You can configure your plugins to change the deployment steps that it adds to the plan or to add new steps as needed.

For example, if you deploy an application to a JBoss or Tomcat server that you have configured for hot deployments, you are not required to stop the server before the application is deployed or start it afterward. In the [JBoss Application Server plugin reference documentation](/xl-deploy/latest/jbossPluginManual.html) and [Tomcat plugin reference documentation](/xl-deploy/latest/tomcatPluginManual.html), you can find the `restartRequired` property for `jbossas.EarModule`, `tomcat.WarModule`, and other deployable types. The default value of this property is `true`. To change the value:

1. Set `restartRequired` to `false` in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.
2. Restart the XL Deploy server to load the new configuration setting.
3. Create a deployment that will deploy your application to the target environment. You will see that the server stop and start steps do not appear in the deployment plan that is generated.

For more detailed information about how XL Deploy creates deployment plans, refer to the [Understanding the packaging phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html). For information about configuring the plugin you are using, refer to its manual in the XL Deploy documentation.

### Create a new plugin

To deploy an application to middleware for which XL Deploy does not already offer content, you can create a plugin by defining the CI types, rules, and actions that you need for your environment. In a plugin, you can define:

* New container types, which are types of middleware that can be added to a target environment
* New artifact and resources types that you can add to deployment packages and deploy to new or existing container types
* Rules that indicate the steps that XL Deploy should execute when you deploy the new artifact and resource types
* [Control tasks](/xl-deploy/how-to/using-control-tasks-in-xl-deploy.html) that define housekeeping actions you can perform on new or existing container types

You can define rules and control tasks in an XML file. Implementations of new steps use your preferred automation for your target systems. No specialized scripting language is required.
