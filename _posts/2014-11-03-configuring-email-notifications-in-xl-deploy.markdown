---
title: Configuring email notifications in XL Deploy
categories:
- xl-deploy
tags:
- notification
- trigger
---

These are sample scenarios for sending email notifications from XL Deploy to different project teams.

## Mail notification audience groups

In a typical enterprise scenario, there are likely to be different target audiences requiring different notification schemes. In this example, we'll work with the following groups:

* Managers: Need more knowledge on the high level status of when deployment started, completed and how it went through
* Dev + Ops: More focused on resolving any issues in case of deployment failures

So based on the audience groups, we define three types of notifications:

* Deployment start: Primarily notifying managerial group that a certain deployment has started
* Deployment failed: Notifies the Dev + Ops groups that a certain deployment has failed on a certain step
* Deployment complete: Notifies all groups that deployment is complete, with a brief summary of the steps 

## Triggering states

While setting up a [task trigger](http://docs.xebialabs.com/releases/latest/deployit/triggerPluginManual.html#triggertasktrigger), you would need to carefully set the [state transitions](http://docs.xebialabs.com/releases/latest/deployit/triggerPluginManual.html#task-state-transitions) for the mail scenarios:

* For deployment start: State should be from PENDING to QUEUED
* For deployment failed: State should be from EXECUTING to STOPPED
* For deployment complete: State should be from EXECUTING to EXECUTED

## Important notes

* For deployment failure: When a deployment fails, certain steps may not have been completed. The  [`completionDate` property](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.StepState.html) for will not be set for such steps. If you're using that property in your deployment failure email template, be sure to handle that case appropriately.
* The mail server should be set up and configured with emails.
* You can generalize the ["to addresses" section](http://docs.xebialabs.com/releases/latest/deployit/triggerPluginManual.html#triggeremailnotification) in the email configuration with a property to be picked up from the environment so that you don't create multiple copies of email template for every environment just to allow for different recipient lists.

## Here's how it's done

To create the email recipient properties:

    <type-modification type="udm.Environment">
        <!-- Property under Mail Tab for management and devops recipients -->
        <property name="managerialRecepients" default="admin@yourcompany.com" description="Recepients for Start/Stop Deployment Emails" kind="string" required="false" category="Mail" />
        <property name="devOpsRecepients" default="admin@yourcompany.com" description="Recepients for Deployment Failure Emails" kind="string" required="false" category="Mail" />
    </type-modification>

You can now use the new properties in your email configuration for "to addresses":

* `${deployedApplication.environment.devOpsRecepients}`
* `${deployedApplication.environment.managerialRecepients}`

## Template for deployment start/complete email

### Subject

    Deployment | ${deployedApplication.version.id} | ${deployedApplication.environment.name} | <#if task.state == "EXECUTING"> STARTED<#else > SUCCESSFUL</#if>.

### Body

    Application ${deployedApplication.version.application.name} deployment to ${deployedApplication.environment.name} <#if task.state == "EXECUTING"> has started<#else > is successful</#if>.

    <#if task.state == "EXECUTED">
    Deployment started at ${task.startDate} and completed at ${task.completionDate}.

    Task comprises of following steps:

    <#list task.steps as stepInfo>
    ------------------------------------------------------------------------------------------

    Step "${stepInfo.description}" started at ${stepInfo.startDate} and ended at ${stepInfo.completionDate} with status ${stepInfo.state}.

    ------------------------------------------------------------------------------------------
    </#list>
    </#if>

    Have a nice day.

    XL Deploy Notification System.

## Template for deployment failed email

### Subject

    Deployment | ${deployedApplication.version.id} | ${deployedApplication.environment.name} | FAILED

### Body

    Application ${deployedApplication.version.id} deployment to ${deployedApplication.environment.name} has Failed.

    Deployment started at ${task.startDate} <#if (task.completionDate)?? > and completed at ${task.completionDate}<#else> and didn't complete</#if>.

    <#list task.steps as stepInfo>
    ------------------------------------------------------------------------------------------

    Step "${stepInfo.description}" <#if (stepInfo.startDate)??> started at ${stepInfo.startDate} and ended at ${stepInfo.completionDate} with status ${stepInfo.state}.
    Logs:
    ${stepInfo.log}
    <#else> yet to start.
    </#if>

    ------------------------------------------------------------------------------------------
    </#list>

    Please look into the build failure immediately.

    XL Deploy Notification System.

## Configuring your XL Deploy installation
 
1. Stop the XL Deploy server.
1. Add the [type modification](http://docs.xebialabs.com/releases/latest/deployit/customizationmanual.html#synthetic-properties) to `SERVER_HOME/ext/synthetic.xml`.
1. Restart the XL Deploy server.
1. Go to [**Repository** > **Configuration**](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#editing-the-repository).
1. [suggestion] Add a new [directory](http://docs.xebialabs.com/releases/latest/deployit/referencemanual.html#directories) under **Configuration** called *Shared Configuration*.
1. [suggestion] Under that, add two more directories: *Triggers* and *Email Templates*.
1. Under **Email Templates**, add two new **Email Notification** items.
1. Name the first *Deployment Start/Complete email* and copy the above template and values to the "to address", "subject" and "body" sections.
1. Name the second *Deployment Failed email* and copy the failure template and values to the "to address", "subject" and "body" sections.
1. Under **Trigger**, create three [task triggers](http://docs.xebialabs.com/releases/latest/deployit/triggerPluginManual.html#triggeremailnotification): *Deployment Start trigger*, *Deployment Failed trigger*,  and *Deployment Complete trigger*.
1. Add the corresponding email notifications to each trigger (note that the "start" and "complete" triggers will use the same template).
1. Configure the "from" and "to" task states as listed above for the three triggers.
1. Now you can add these triggers to any environment to trigger email notifications.

## Information about variables used

Deployment email templates have access to the following variables:

* [`task`](http://docs.xebialabs.com/releases/latest/xl-deploy/triggerPluginManual.html#actionsl)
* [`step`](http://docs.xebialabs.com/releases/latest/deployit/rest-api/com.xebialabs.deployit.engine.api.execution.StepState.html)
* [`deployedApplication`](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmdeployedapplication)
* `action`

`deployedApplication` deserves a closer look, as it allows reference to the following other objects:

* `deployedApplication.version` refers to the [deployment package](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmdeploymentpackage) being deployed
* `deployedApplication.version.application` refers to the [application](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmapplication) to which the deployment package belongs
* `deployedApplication.environment` refers to the [environment](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmenvironment) to which the package is being deployed
* `deployedApplication.environment.dictionaries[0].entries['<key>']` refers to a [dictionary](http://docs.xebialabs.com/releases/latest/deployit/udmcireference.html#udmdictionary) entry within the first dictionary in the target environment

With knowledge of these objects, you can access further properties and use them to make a comprehensive email template.

See the [Trigger Plugin Manual](http://docs.xebialabs.com/releases/latest/deployit/triggerPluginManual.html) for more information.
