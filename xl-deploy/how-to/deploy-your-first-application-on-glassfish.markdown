---
title: Deploy your first application on GlassFish
subject:
- Deployment
categories:
- xl-deploy
tags:
- glassfish
- middleware
- deployment
---

After you have installed [XL Deploy](http://xebialabs.com/products/xl-deploy) and logged in for the first time, follow these instructions to:

1. Connect XL Deploy to a GlassFish installation running on Unix
1. Add GlassFish middleware containers to the XL Deploy Repository
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

If you haven't set up XL Deploy yet, visit our [Getting Started page](http://xebialabs.com/products/xl-deploy/#getting-started) to install the software and watch video tutorials about key XL Deploy concepts.

## Connect to your infrastructure

First, you need to connect XL Deploy to the host on which GlassFish is running. Follow the instructions for the host's operating system and the connection protocol that you want XL Deploy to use:

* [Unix and SSH](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm)

## Add your middleware

Once XL Deploy can communicate with your host, it can scan for middleware containers and automatically add them to the Repository for you.

To add a GlassFish domain:

1. Right-click the host that you created and select **Discover** > **glassfish** > **Domain**.
1. In the **Name** box, enter the name of the domain. This must match the domain name in your GlassFish installation.
1. In the **Home** box, enter the path to `bin/asadmin`; for example, `/opt/glassfish4`.
1. In the **Administrative port** and **Administrative Host** boxes, optionally set the port and host used to log in to the Domain Administration Server (defaults to 4848 and `localhost`).
1. In the **Administrative username** box, enter the user name that XL Deploy should use to log in to the DAS.
1. In the **Administrative password** box, enter the password for the user.
1. If the connection to the DAS should use HTTPS, select **Secure**.
1. Click **Next**. A plan appears with the steps that XL Deploy will execute to discover the middleware on the host.

      ![Sample discovery steps](images/xl-deploy-trial/xl_deploy_trial_glassfish_discovery_steps.png)

1. Click **Execute**. XL Deploy executes the plan. If it succeeds, the state of the steps will be **DONE**.
1. Click **Next**. XL Deploy shows the items that it discovered.

      ![Sample discovered infrastructure items](images/xl-deploy-trial/xl_deploy_trial_glassfish_discovered_items.png)

1. You can click each item to view its properties. If an item is missing a property value that is required, a red triangle appears next to it. Provide the missing value and click **Apply** to save your changes.
1. When you are finished, click **Save**. XL Deploy saves the items in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

Follow [these instructions](create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

To deploy to GlassFish, select a GlassFish domain from the **Containers** list when creating the environment.

## Import the sample application

XL Deploy includes two versions of a sample application called *PetClinic-war*, already packaged in XL Deploy's deployment package format (DAR).

**Tip:** To learn more about application packages, read [Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html).

To add the application to XL Deploy's Repository, you need to import it:

1. Click **Deployment** in XL Deploy.
2. Under **Packages**, click ![Import package button](/images/button_import_package.png). A new tab appears in the Deployment Workspace.
3. Select **Import deployment package from server**.
4. Next to **Select package**, select **PetClinic-war/1.0**. This package contains version 1.0 of the PetClinic-war application.

      ![Sample environment](images/xl-deploy-trial/xl_deploy_trial_import_sample_PetClinic-war.png)

5. Click **Import**. XL Deploy imports the application.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

**Tip:** To see an application being deployed for the first time, watch the *[Performing an initial deployment](http://vimeo.com/97815293)* video.

To deploy PetClinic-war 1.0:

1. Under **Packages**, expand **PetClinic-war**.
2. Select the **1.0** package and drag it to the left side of the Deployment Workspace.
3. Under **Deployed Applications**, select the environment and drag it to the right side of the Deployment Workspace.
4. Click ![Auto-map button](/images/button_auto-map.png) to automatically map the deployable in the package (which is a WAR file) in the package to the appropriate container in the environment.

      ![Sample mapping](images/xl-deploy-trial/xl_deploy_trial_glassfish_mapping_sample_app.png)

5. Click **Analyze** to preview the deployment plan in the Plan Analyzer.
6. Double-click the WAR file that XL Deploy mapped. Here, you can see properties that you can adjust at deployment time.
5. Click **Next**. The deployment plan appears.
6. Click **Execute**. XL Deploy deploys the application. As each step is executed, you can click it to see real-time information about the deployment.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Successful deployment](images/xl-deploy-trial/xl_deploy_trial_glassfish_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again. For example, in the deployment shown below, XL Deploy could not deploy the application because the GlassFish domain is not running. After starting the domain, click **Continue** and XL Deploy will try the deployment again.

![Failed deployment](images/xl-deploy-trial/xl_deploy_trial_glassfish_failed_deployment.png)

## Verify the deployment

To verify the deployment, log in to the GlassFish Administration Console and check the list of applications for the PetClinic application.

![Sample deployed application](images/xl-deploy-trial/xl_deploy_trial_glassfish_deployed_app_in_admin_console.png)

## Learn more

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [GlassFish Plugin Manual](http://docs.xebialabs.com/releases/latest/glassfish-plugin/glassfishPluginManual.html)
* [XL Deploy for developers, in 5 minutes](../concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](http://vimeo.com/99837504)
* [Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](../concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/forums).
