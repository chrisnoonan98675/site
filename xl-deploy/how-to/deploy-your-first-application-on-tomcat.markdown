---
title: Deploy your first application on Apache Tomcat
subject:
- Getting started
categories:
- xl-deploy
tags:
- tomcat
- middleware
- deployment
---

After you have installed your version of [XL Deploy](http://xebialabs.com/products/xl-deploy) and logged in for the first time, follow these instructions to:

1. Connect XL Deploy to an Apache Tomcat 5.5.x, 6.x, 7.x, or 8.x installation running on Unix or Windows
1. Add the Tomcat server to the XL Deploy Repository
1. Create an environment where you can deploy applications
1. Import a sample application into XL Deploy
1. Deploy the sample application to the environment that you created

If you haven't set up XL Deploy yet, visit our [Getting Started page](http://xebialabs.com/products/xl-deploy/#getting-started) to install the software and watch video tutorials about key XL Deploy concepts.

## Required Tomcat setup

Tomcat must be configured to run in the background. On Unix, Tomcat should be installed as a daemon or the Tomcat startup script should start Tomcat in the background. On Windows, you must install Tomcat as a service.

Refer to the [documentation for your version of Tomcat](http://tomcat.apache.org/) for information about running Tomcat in the background.

## Connect to your infrastructure

First, you need to connect XL Deploy to the host on which Tomcat is running. Follow the instructions for the host's operating system and the connection protocol that you want XL Deploy to use:

* [Unix and SSH](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](connect-xl-deploy-to-your-infrastructure.html#connect-to-a-windows-host-using-winrm)

## Add your middleware

Once XL Deploy can communicate with your host, you can add your Tomcat middleware:

* [Add the Tomcat server](#add-the-tomcat-server)
* [Add a Tomcat virtual host](#add-a-tomcat-virtual-host)

### Add the Tomcat server

To add the Tomcat server:

1. Right-click the host that you created and select **New** > **tomcat** > **Server**.
2. In the **Name** box, enter a name for the server.
3. In the 	**Home** box, enter Tomcat home directory; for example, `/opt/apache-tomcat-8.0.9/` on Unix or `C:\Program Files\Tomcat\` on Windows.
4. In the **Start Command** box, enter the home directory, followed by the OS-specific command to start Tomcat. For example:
      * `service tomcat8 start` on Unix
      * `net start tomcat8` on Windows

      **Note:** XL Deploy expects the Tomcat start command to return and to leave Tomcat running. If the start command does not return, XL Deploy will not be able to complete a deployment to Tomcat.

5. In the **Stop Command** box, enter the home directory, followed by the 0S-specific command to stop Tomcat. For example:
      * `service tomcat8 stop` on Unix
      * `net stop tomcat8` on Windows
6. In the **Start Wait Time** box, enter the number of seconds that XL Deploy should wait after executing the start command and before continuing with the deployment plan.
7. In the **Stop Wait Time** box, enter the number of seconds that XL Deploy should wait after executing the stop command and before continuing with the deployment plan.

      **Note:** If your startup and shutdown scripts only return after the server has fully started or stopped, you can set the wait time to 0.

      ![Sample Tomcat server configuration](images/xl-deploy-trial/xl_deploy_trial_tomcat_server.png)

8. Click **Save**. XL Deploy saves the server in the Repository.

### Add a Tomcat virtual host

To add a Tomcat [virtual host](http://tomcat.apache.org/tomcat-8.0-doc/virtual-hosting-howto.html):

1. Right-click the Tomcat server that you created and select **New** > **VirtualHost**.
2. In the **Name** box, enter a name for the virtual host.
3. Optionally change the **App Base** and **Host Name** (defaults are `webapps` and `localhost`).
 
      ![Sample Tomcat virtual host configuration](images/xl-deploy-trial/xl_deploy_trial_tomcat_virtual_host.png)

4. Click **Save**. XL Deploy saves the virtual host in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

Follow [these instructions](create-an-environment-in-xl-deploy.html) to create an environment where you can deploy a sample application.

To deploy to Tomcat, select a Tomcat virtual host from the **Containers** list when creating the environment.

## Import the sample application

XL Deploy includes two versions of a sample application called *PetClinic-war*, already packaged in XL Deploy's deployment package format (DAR).

Follow [these instructions](/xl-deploy/how-to/add-a-package-to-xl-deploy.html#import-a-package) to import **PetClinic-war/1.0**.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

**Tip:** To see an application being deployed for the first time, watch the *[Performing an initial deployment](http://vimeo.com/97815293)* video.

To deploy PetClinic-war 1.0:

1. Under **Packages**, expand **PetClinic-war**.
2. Select the **1.0** package and drag it to the left side of the Deployment Workspace.
3. Under **Deployed Applications**, select the environment and drag it to the right side of the Deployment Workspace.
4. Click ![Auto-map button](/images/button_auto-map.png) to automatically map the deployable in the package (which is a WAR file) in the package to the appropriate container in the environment.

      ![Sample mapping](images/xl-deploy-trial/xl_deploy_trial_tomcat_mapping_sample_app.png)

5. Click **Analyze** to preview the deployment plan in the Plan Analyzer.
6. Double-click the WAR file that XL Deploy mapped. Here, you can see properties that you can adjust at deployment time, like placeholder values.
5. Click **Next**. The deployment plan appears.
6. Click **Execute**. XL Deploy deploys the application. As each step is executed, you can click it to see real-time information about the deployment.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Successful deployment](images/xl-deploy-trial/xl_deploy_trial_tomcat_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again. For example, in the deployment shown below, XL Deploy could not stop the Tomcat server because the shutdown script is set to `/opt/apache-tomcat-8.0.1/bin/startup`. You should abort the deployment and correct the **Stop Command** in the Tomcat server configuration in the Repository, then try the deployment again.

![Failed deployment](images/xl-deploy-trial/xl_deploy_trial_tomcat_failed_deployment.png)

## Verify the deployment

To verify the deployment, go to `http://IP:8080/petclinic`, where `IP` is the IP address of the Tomcat server.

## Learn more

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [Tomcat Plugin Manual](http://docs.xebialabs.com/releases/latest/tomcat-plugin/tomcatPluginManual.html)
* [XL Deploy for developers, in 5 minutes](../concept/xl-deploy-for-developers.html)
* [Getting started with XL Deploy: Understanding packages](http://vimeo.com/99837504)
* [Preparing your application for XL Deploy](../concept/preparing-your-application-for-xl-deploy.html)
* [Understanding deployables and deployeds](../concept/understanding-deployables-and-deployeds.html)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/forums).
