---
title: Deploy your first application on JBoss Enterprise Application Platform (EAP) 6 and JBoss Application Server (AS)/WildFly 7.1+
subject:
- Deployment
categories:
- xl-deploy
tags:
- jboss
- wildfly
- middleware
- deployment
---

After you have installed your version of [XL Deploy](http://xebialabs.com/products/xl-deploy) and logged in for the first time, follow these instructions to:

1. Connect XL Deploy to a JBoss Enterprise Application Platform (EAP) 6 and JBoss Application Server (AS)/WildFly 7.1+ installation
1. Add JBoss/WildFly middleware containers to the XL Deploy Repository
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

If you haven't set up XL Deploy yet, visit our [Getting Started page](http://xebialabs.com/products/xl-deploy/#getting-started) to install the software and watch video tutorials about key XL Deploy concepts.

## Connect to your infrastructure

First, you need to connect XL Deploy to the host on which JBoss is running. Follow the instructions for the host's operating system and the connection protocol that you want XL Deploy to use:

* [Unix and SSH](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm)

## Add your middleware

Once XL Deploy can communicate with your host, it can scan for middleware containers and automatically add them to the Repository for you. You can:

* [Add containers in a JBoss Domain](#add-containers-in-a-jboss-domain)
* [Add a stand-alone JBoss server](#add-a-stand-alone-jboss-server)

### Add containers in a JBoss Domain

To add containers in a JBoss Domain:

1. Right-click the host that you created and select **Discover** > **jbossdm** > **Domain**.
1. In the **Name** box, enter a name for the domain.
1. In the **Home** box, enter the JBoss home directory; for example, `/opt/jbossdm-6eap/`
1. In the **Administrative username** and **Administrative password** boxes, enter the user name and password used to log in to your [JBoss administration](https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6/html-single/Administration_and_Configuration_Guide/index.html#chap-Management_Interfaces).
1. Click **Next**. A plan appears with the steps that XL Deploy will execute to discover the middleware on the host.

      ![JBoss EAP Domain discovery plan](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_domain_discovery_plan.png)

1. Click **Execute**. XL Deploy executes the plan. If it succeeds, the state of the steps will be DONE.
2. Click **Next** to see the middleware containers that XL Deploy discovered. You can click each item to view its properties.

      ![JBoss EAP Domain discovered items](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_domain_discovered_items.png)

1. Click **Save**. XL Deploy saves the items in the Repository.

### Add a stand-alone JBoss server

To add a stand-alone JBoss server:

1. Right-click the host that you created and select **Discover** > **jbossdm** > **StandaloneServer**.
1. In the **Name** box, enter a name for the server.
1. In the **Home** box, enter the JBoss home directory; for example, `/opt/jbossdm7/`
1. In the **Administrative username** and **Administrative password** boxes, enter the user name and password used to log in to JBoss Native Administration.
1. Click **Next**. A plan appears with the steps that XL Deploy will execute to discover the middleware on the host.

      ![JBoss EAP Standalone discovery plan](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_standalone_discovery_plan.png)

1. Click **Execute**. XL Deploy executes the plan. If it succeeds, the state of the steps will be DONE.
2. Click **Next** to see the middleware containers that XL Deploy discovered. You can click each item to view its properties.

      ![JBoss EAP Standalone discovered items](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_standalone_discovered_items.png)

1. Click **Save**. XL Deploy saves the items in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

Follow [these instructions](create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

To deploy to a JBoss Domain, you must add a *server group* to the environment. To deploy to a stand-alone JBoss server, you must add the *server* to the environment.

## Import the sample application

XL Deploy includes two versions of a sample application called *PetClinic-ear*, already packaged in XL Deploy's deployment package format (DAR).

**Tip:** To learn more about application packages, read *[Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)*.

To add the application to XL Deploy's Repository, you need to import it:

1. Click **Deployment** in XL Deploy.
2. Under **Packages**, click ![Import package button](/images/button_import_package.png). A new tab appears in the Deployment Workspace.
3. Select **Import deployment package from server**.
4. Next to **Select package**, select **PetClinic-ear/1.0**. This package contains version 1.0 of the PetClinic-ear application.

      ![Sample environment](images/xl-deploy-trial/xl_deploy_trial_import_sample_app.png)

5. Click **Import**. XL Deploy imports the application.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

**Tip:** To see an application being deployed for the first time, watch the *[Performing an initial deployment](http://vimeo.com/97815293)* video.

To deploy PetClinic-ear 1.0:

1. Under **Packages**, expand **PetClinic-ear**.
2. Select the **1.0** package and drag it to the left side of the Deployment Workspace.
3. Under **Deployed Applications**, select the environment and drag it to the right side of the Deployment Workspace.
4. Click ![Auto-map button](/images/button_auto-map.png) to automatically map the deployable in the package (which is an EAR file) in the package to the appropriate container in the environment.

      ![Sample mapping](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_domain_mapping_sample_app.png)

5. Click **Analyze** to preview the deployment plan in the Plan Analyzer.
6. Double-click the EAR file that XL Deploy mapped. Here, you can see properties that you can adjust at deployment time, like placeholder values.
5. Click **Next**. The deployment plan appears.
6. Click **Execute**. XL Deploy deploys the application. As each step is executed, you can click it to see real-time information about the deployment.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Successful deployment](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_domain_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again.

![Failed deployment](images/xl-deploy-trial/xl_deploy_trial_jboss-dm_domain_failed_deployment.png)

## Verify the deployment

To verify the deployment, go to `http://IP:PORT/petclinic`, where `IP` and `PORT` are the IP address and port of the server where the application was deployed.

![Sample deployed PetClinic-ear application](images/xl-deploy-trial/xl_deploy_trial_deployed_petclinic.png)

## Learn more

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [JBoss Application Server 7+ Plugin Manual](http://docs.xebialabs.com/releases/latest/jbossdm-plugin/jbossDomainPluginManual.html)
* [JBoss Application Server 5 and 6 Plugin Manual](http://docs.xebialabs.com/releases/latest/jbossas-plugin/jbossPluginManual.html)
* [XL Deploy for developers, in 5 minutes](../concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](http://vimeo.com/99837504)
* [Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](../concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/forums).
