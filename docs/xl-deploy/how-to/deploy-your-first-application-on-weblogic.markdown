---
title: Deploy your first application on Oracle WebLogic
subject:
- Getting started
categories:
- xl-deploy
tags:
- weblogic
- middleware
- deployment
since:
- XL Deploy 5.0.0
---

After you have installed [XL Deploy](http://xebialabs.com/products/xl-deploy) and the [Oracle WebLogic plugin](/xl-deploy/latest/wlsPluginManual.html), log in to XL Deploy and follow these instructions to:

1. Connect XL Deploy to your Oracle WebLogic installation
1. Discover your WebLogic middleware containers
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

## Connect to your infrastructure

First, you need to connect XL Deploy to the host on which the WebLogic Administration Server is running.

Follow the instructions for the host's operating system and the connection protocol that you want XL Deploy to use:

* [Unix and SSH](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](/xl-deploy/how-to/connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm)

## Add your middleware

Once XL Deploy can communicate with your host, it can scan for middleware containers and automatically add them to the Repository for you.

To add the WebLogic domain:

1. Right-click **Infrastructure** and select **Discover** > **Domain**.

    **Tip:** If you do not see the **Domain** option in the menu, verify that the WebLogic plugin is installed.

1. In the **Name** box, enter a name for the domain.
1. Select the host that you created from the **Host** list.
1. Select your WebLogic version from the **Version** list.
1. In the **WebLogic home** box, enter the path to the WebLogic server installation; for example, `/opt/weblogic/wlserver_12c`
1. In the **WLST path** box, optionally enter the path to the WebLogic Scripting Tool (WLST) binary, relative to the WebLogic home directory (defaults to `<WebLogic_home>/common/bin/wlst.sh` on Unix hosts and `<WebLogic_home>\common\bin\wlst.cmd` on Windows hosts).
1. Optionally select a protocol from the **Administrative server protocol** list.
1. In the **Administrative server host** and **Administrative server port** boxes, optionally set the host and port to use for the administration server.
1. In the **Administrative username** box, enter the user name that XL Deploy should use to log in to the WebLogic Domain.
1. In the **Administrative password** box, enter the password for the user.
1. Click **Next**. A plan appears with the steps that XL Deploy will execute to discover the middleware on the host.

      ![Sample discovery steps](images/xl-deploy-trial/xl_deploy_trial_wls_discovery_steps.png)

1. Click **Execute**. XL Deploy executes the plan. If it succeeds, the state of the steps will be **DONE**.
1. Click **Next**. XL Deploy shows the items that it discovered.

      ![Sample discovered infrastructure items](images/xl-deploy-trial/xl_deploy_trial_wls_discovered_items.png)

1. You can click each item to view its properties. If an item is missing a property value that is required, a red triangle appears next to it. Provide the missing value and click **Apply** to save your changes.
1. When you are finished, click **Save**. XL Deploy saves the items in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

Follow [these instructions](/xl-deploy/how-to/create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

## Import the sample application

XL Deploy includes two versions of a sample application called *PetClinic-ear*, already packaged in XL Deploy's deployment package format (DAR).

Follow [these instructions](/xl-deploy/how-to/add-a-package-to-xl-deploy.html#import-a-package) to import **PetClinic-ear/1.0**.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

Follow [these instructions](/xl-deploy/how-to/deploy-an-application.html) to deploy the application.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Sample failed deployment](images/xl-deploy-trial/xl_deploy_trial_wls_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again.

![Sample failed deployment](images/xl-deploy-trial/xl_deploy_trial_wls_failed_deployment.png)

## Verify the deployment

To verify the deployment, log in to the WebLogic Administration Console and check the list of deployments for the PetClinic application.

![Sample deployed application](images/xl-deploy-trial/xl_deploy_trial_wls_deployed_app_in_admin_console.png)

## What's next

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [Introduction to the WebLogic plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-weblogic-plugin.html)
* [XL Deploy for developers](/xl-deploy/concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](https://www.youtube.com/watch?v=dqeL45WGcKU)
* [Preparing your application for XL Deploy](/xl-deploy/concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](/xl-deploy/concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/).
