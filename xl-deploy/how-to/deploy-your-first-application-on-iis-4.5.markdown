---
title: Deploy your first application on Microsoft IIS (XL Deploy 4.5.x or earlier)
subject:
- Getting started
categories:
- xl-deploy
tags:
- iis
- middleware
- deployment
deprecated:
- 4.5.x
---

After you have installed [XL Deploy](http://xebialabs.com/products/xl-deploy) and logged in for the first time, follow these instructions to:

1. Connect XL Deploy to your Microsoft Internet Information Services (IIS) installation
1. Add IIS middleware containers to the XL Deploy Repository
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

If you haven't set up XL Deploy yet, refer to [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html) to install the software.

## Connect to your infrastructure

First, follow [these instructions](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm) to use WinRM to connect XL Deploy to the host on which IIS is running.

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

Follow [these instructions](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

To deploy to IIS, select the IIS server from the **Containers** list when creating the environment.

## Import the sample application

XL Deploy includes two versions of a sample application called *NerdDinner*, already packaged in XL Deploy's deployment package format (DAR).

Follow [these instructions](/xl-deploy/how-to/add-a-package-to-xl-deploy.html#import-a-package) to import **NerdDinner/2.0**.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

Follow [these instructions](/xl-deploy/how-to/deploy-an-application.html) to deploy the application.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Sample successful deployment](images/xl-deploy-trial/xl_deploy_trial_iis_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again. For example, in the deployment shown below, XL Deploy could not copy files to the IIS server because remote management is not enabled. If we enable remote management on the server and click **Continue**, XL Deploy will try to start the application again.

![Sample failed deployment](images/xl-deploy-trial/xl_deploy_trial_iis_failed_deployment.png)

## Verify the deployment

To verify the deployment, use the <a href="http://msdn.microsoft.com/en-us/library/vstudio/bb763170(v=vs.100).aspx" target="_blank">IIS Manager</a> to connect to the IP address that you provided in [Connect to your infrastructure](#connect-to-your-infrastructure). NerdDinner will appear as a new site on the server. Click the link under **Browse Website** to visit the site.

![Deployed application in IIS 8](images/xl-deploy-trial/xl_deploy_trial_iis_deployed_website.png)

## What's next

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [Introduction to the IIS plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-iis-plugin.html)
* [XL Deploy for developers](/xl-deploy/concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](https://www.youtube.com/watch?v=dqeL45WGcKU)
* [Preparing your application for XL Deploy](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](/xl-deploy/concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/).
