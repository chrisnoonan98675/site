---
title: Deploy your first application on Microsoft Internet Information Services (IIS)
subject:
- Deployment
categories:
- xl-deploy
tags:
- iis
- middleware
- deployment
---

After you have installed [XL Deploy](http://xebialabs.com/products/xl-deploy) and logged in for the first time, follow these instructions to:

1. Connect XL Deploy to your Microsoft Internet Information Services (IIS) installation
1. Add IIS middleware containers to the XL Deploy Repository
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

If you haven't set up XL Deploy yet, visit our [Getting Started page](http://xebialabs.com/products/xl-deploy/#getting-started) to install the software and watch video tutorials about key XL Deploy concepts.

## Connect to your infrastructure

First, follow [these instructions](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm) to use WinRM to connect XL Deploy to the host on which IIS is running.

## Add your middleware

Once XL Deploy can communicate with your host, you can add the IIS server:

* [Add an IIS 7.0+ server](#add-an-iis-7-0-server)
* [Add an IIS 6.0 server](#add-an-iis-6-0-server)

### Add an IIS server

To add an IIS 7.0+ server:

1. Right-click the host that you created and select **New** > **iis** > **Server**.
2. In the **Name** box, enter a name for the server.

      ![Sample IIS 7.0+ server configuration](images/xl-deploy-trial/xl_deploy_trial_iis_server.png)

3. Click **Save**. XL Deploy saves the server in the Repository.

### Add an IIS 6.0 server

To add an IIS 6.0 server:

1. Right-click the host that you created and select **New** > **iis6** > **Server**.
2. In the **Name** box, enter a name for the server.
3. Optionally set the path to the .NET Framework installation in **.NET Framework Installation Path** (defaults to `C:\WINDOWS\Microsoft.NET\Framework64`).

      ![Sample IIS 6.0 server configuration](images/xl-deploy-trial/xl_deploy_trial_iis6_server.png)

3. Click **Save**. XL Deploy saves the server in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

Follow [these instructions](create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

To deploy to IIS, select the IIS server from the **Containers** list when creating the environment.

## Import the sample application

XL Deploy includes two versions of a sample application called *NerdDinner*, already packaged in XL Deploy's deployment package format (DAR).

**Tip:** To learn more about application packages, read *[Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)*.

To add the application to XL Deploy's Repository, you need to import it:

1. Click **Deployment** in XL Deploy.
2. Under **Packages**, click ![Import package button](/images/button_import_package.png). A new tab appears in the Deployment Workspace.
3. Select **Import deployment package from server**.
4. Next to **Select package**, select **NerdDinner/2.0**. This package contains version 2.0 of the NerdDinner application.

      ![Sample application](images/xl-deploy-trial/xl_deploy_trial_import_sample_NerdDinner.png)

5. Click **Import**. XL Deploy imports the application.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

**Tip:** To see an application being deployed for the first time, watch the *[Performing an initial deployment](http://vimeo.com/97815293)* video.

To deploy NerdDinner 2.0:

1. Under **Packages**, expand **NerdDinner**.
2. Select the **2.0** package and drag it to the left side of the Deployment Workspace.
3. Under **Deployed Applications**, select the environment and drag it to the right side of the Deployment Workspace.
4. Click ![Auto-map button](/images/button_auto-map.png) to automatically map the deployables in the package to the appropriate container in the environment.

      ![Sample mapping](images/xl-deploy-trial/xl_deploy_trial_iis_mapping_sample_app.png)

5. Click **Analyze** to preview the deployment plan in the Plan Analyzer.
6. Double-click the deployables that XL Deploy mapped. Here, you can see various properties that you can adjust at deployment time, like the website name.
5. Click **Next**. The deployment plan appears.
6. Click **Execute**. XL Deploy deploys the application. As each step is executed, you can click it to see real-time information about the deployment.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Sample successful deployment](images/xl-deploy-trial/xl_deploy_trial_iis_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again. For example, in the deployment shown below, XL Deploy could not copy files to the IIS server because remote management is not enabled. If we enable remote management on the server and click **Continue**, XL Deploy will try to start the application again.

![Sample failed deployment](images/xl-deploy-trial/xl_deploy_trial_iis_failed_deployment.png)

## Verify the deployment

To verify the deployment, use the <a href="http://msdn.microsoft.com/en-us/library/vstudio/bb763170(v=vs.100).aspx" target="_blank">IIS Manager</a> to connect to the IP address that you provided in [Connect to your infrastructure](#connect-to-your-infrastructure). NerdDinner will appear as a new site on the server. Click the link under **Browse Website** to visit the site.

![Deployed application in IIS 8](images/xl-deploy-trial/xl_deploy_trial_iis_deployed_website.png)

## What's next

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [IIS Plugin Manual](http://docs.xebialabs.com/releases/latest/iis-plugin/iisPluginManual.html)
* [XL Deploy for developers, in 5 minutes](../concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](http://vimeo.com/99837504)
* [Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](../concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/forums).
