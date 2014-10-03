---
title: Getting started with XL Deploy&#58; Deploy your first application on Apache Tomcat
categories:
- xl-deploy
tags:
- tomcat
- middleware
- deployment
---

After you have installed your version of [XL Deploy](http://go.xebialabs.com/XL-Deploy-Trial.html) and logged in for the first time, follow these instructions to:

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

* [Unix and SSH](#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](#connect-to-a-windows-host-using-winrm)

If you would like to use SSH on Windows through WinSSHD or OpenSSH, please refer to the [Remoting Plugin Manual](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#host-setup-for-ssh).

**Tip:** To see a host setup and connection check in action, watch the *[Defining infrastructure](http://vimeo.com/97815291)* video.

### Connect to a Unix host using SSH

To connect to a Unix host using SSH:

1. Click **Repository** in XL Deploy.
1. Right-click **Infrastructure** and select **New** > **overthere** > **SshHost**. A new tab appears.
2. In the **Name** box, enter a name for the host.
3. Select **UNIX** from the **Operating system** list.
4. Select the **Connection Type**:
    * Select **SCP** if the user that will connect to the host has privileges to manipulate files and execute commands.
    * Select **SU** if the user that will connect to the host can use `su` to log in as one user and execute commands as a different user.
    * Select **SUDO** or **INTERACTIVE_SUDO** if the user that will connect to the host can use `sudo` to execute commands as a different user. Refer to the [SSH information in the Remoting Plugin Manual](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#sudo-and-interactivesudo) if you don't know which connection type to choose.
5. In the **Address** box, enter the IP address of the host.
6. In the **Port** box, enter the port on which XL Deploy should connect to the host (default is 22).
7. In the **Username** box, enter the user name that XL Deploy should use when connecting to the host.
8. In the **Password** box, enter the user's password.
9. If you chose the connection type SU, SUDO, or INTERACTIVE_SUDO, click the **Advanced** tab and enter the user name and password (in the case of SU) that XL Deploy should use.

      ![Sample Unix host with SSH]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_unix_host_ssh.png)

10. Click **Save**. XL Deploy saves the host in the Repository.

### Connect to a Windows host using WinRM

To check if WinRM is installed on the host, follow <a href="http://technet.microsoft.com/en-us/library/ff520073(WS.10).aspx" target="_blank">the appropriate instructions</a> for the host's version of Windows. If it is not installed, follow [these instructions](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#winrm) to install it, then follow the steps below to connect XL Deploy to the host.

To connect to a Windows host using WinRM:

1. Click **Repository** in XL Deploy.
1. Right-click **Infrastructure** and select **New** > **overthere** > **CifsHost**. A new tab appears.
2. In the **Name** box, enter a name for the host.
3. Select **WINDOWS** from the **Operating system** list.
4. Select the **Connection Type**:
    * If the computer where you installed XL Deploy does not run Windows, select **WINRM_INTERNAL**.
    * If the computer where you installed XL Deploy runs Windows, select **WINRM_NATIVE**.
    
    **Note:** The WINRM_NATIVE option requires [Winrs](http://technet.microsoft.com/en-us/library/hh875630.aspx) to be installed on the computer where you installed XL Deploy. This is only supported for Windows 7, Windows 8, Windows Server 2008 R2, and Windows Server 2012.

5. In the **Address** box, enter the IP address of the host.
6. In the **Port** box, optionally enter the port on which Telnet or WinRM runs.

      **Note:** You can change the port on which the CIFS server runs on the **CIFS** tab (defaults to 445).

7. In the **Username** box, enter the user name that XL Deploy should use when connecting to the host.
8. In the **Password** box, enter the user's password.

      **Tip:** For information about the permissions that the user must have, see our documentation on [WinRM connections](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#cifs-winrm-and-telnet).

      ![Sample Windows host with WinRM]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_windows_host_winrm.png)

10. Click **Save**. XL Deploy saves the host in the Repository.

## Verify the connection

After you configure the host, verify that XL Deploy can connect to it:

1. Under **Infrastructure**, right-click the host and select **Check connection**. A new tab appears with the steps that XL Deploy will execute to check the connection.
2. Click **Execute**. XL Deploy verifies that it can transfer files to the host and execute commands on it.

If the connection check succeeds, the state of the steps will be **DONE**.

![Sample successful connection check]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_successful_connection_check_tomcat.png)

If the connection check fails, please refer to our tips for troubleshooting [SSH](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#troubleshooting-ssh) and [WinRM](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#cifs-winrm-and-telnet) connections.

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

      ![Sample Tomcat server configuration]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_server.png)

8. Click **Save**. XL Deploy saves the server in the Repository.

### Add a Tomcat virtual host

To add a Tomcat [virtual host](http://tomcat.apache.org/tomcat-8.0-doc/virtual-hosting-howto.html):

1. Right-click the Tomcat server that you created and select **New** > **VirtualHost**.
2. In the **Name** box, enter a name for the virtual host.
3. Optionally change the **App Base** and **Host Name** (defaults are `webapps` and `localhost`).
 
      ![Sample Tomcat virtual host configuration]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_virtual_host.png)

4. Click **Save**. XL Deploy saves the virtual host in the Repository.

## Create an environment

An environment is a grouping of infrastructure and middleware items such as hosts, servers, clusters, and so on. An environment is used as the target of a deployment, allowing you to map deployables to members of the environment.

**Tip:** To see a sample environment being created, watch the *[Defining environments](http://vimeo.com/97815292)* video.

To create an environment where you can deploy a sample application:

1. Right-click **Environments** and select **New** > **Environment**.
2. In the **Name** box, enter a name for this environment.
3. On the **Common** tab, select the Tomcat virtual host from the **Containers** list and click ![Right arrow button]({{ site.url }}/images/button_add_container.png) to move it to the **Members** list.

      ![Sample environment]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_environment.png)

4. Click **Save**. XL Deploy saves the environment in the Repository.

## Import the sample application

Your trial version of XL Deploy includes two versions of a sample application called *PetClinic-war*, already packaged in XL Deploy's deployment package format (DAR).

**Tip:** To learn more about application packages, read *[Preparing your application for XL Deploy](http://docs.xebialabs.com/general/preparing_your_application_for_XL_Deploy.html)*.

To add the application to XL Deploy's Repository, you need to import it:

1. Click **Deployment** in XL Deploy.
2. Under **Packages**, click ![Import package button]({{ site.url }}/images/button_import_package.png). A new tab appears in the Deployment Workspace.
3. Select **Import deployment package from server**.
4. Next to **Select package**, select **PetClinic-war/1.0**. This package contains version 1.0 of the PetClinic-war application.

      ![Sample environment]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_import_sample_PetClinic-war.png)

5. Click **Import**. XL Deploy imports the application.

## Deploy the sample application

Now you can deploy the sample application to the environment that you created.

**Tip:** To see an application being deployed for the first time, watch the *[Performing an initial deployment](http://vimeo.com/97815293)* video.

To deploy PetClinic-war 1.0:

1. Under **Packages**, expand **PetClinic-war**.
2. Select the **1.0** package and drag it to the left side of the Deployment Workspace.
3. Under **Deployed Applications**, select the environment and drag it to the right side of the Deployment Workspace.
4. Click ![Auto-map button]({{ site.url }}/images/button_auto-map.png) to automatically map the deployable in the package (which is a WAR file) in the package to the appropriate container in the environment.

      ![Sample mapping]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_mapping_sample_app.png)

5. Click **Analyze** to preview the deployment plan in the Plan Analyzer.
6. Double-click the WAR file that XL Deploy mapped. Here, you can see properties that you can adjust at deployment time, like placeholder values.
5. Click **Next**. The deployment plan appears.
6. Click **Execute**. XL Deploy deploys the application. As each step is executed, you can click it to see real-time information about the deployment.

If the deployment succeeds, the state of the deployment plan is **EXECUTED**.

![Successful deployment]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_successful_deployment.png)

If the deployment fails, click the failed step to see information about the failure. In some cases, you can correct the error and try again. For example, in the deployment shown below, XL Deploy could not stop the Tomcat server because the shutdown script is set to `/opt/apache-tomcat-8.0.1/bin/startup`. You should abort the deployment and correct the **Stop Command** in the Tomcat server configuration in the Repository, then try the deployment again.

![Failed deployment]({{ site.url }}/images/xl-deploy-trial/xl_deploy_trial_tomcat_failed_deployment.png)

## Verify the deployment

To verify the deployment, go to `http://IP:8080/petclinic`, where `IP` is the IP address of the Tomcat server.

## Learn more

After you've connected XL Deploy to your middleware and deployed a sample application, you can start thinking about how to package and deploy your own applications with XL Deploy. To learn more, see:

* [Tomcat Plugin Manual](http://docs.xebialabs.com/releases/latest/tomcat-plugin/tomcatPluginManual.html)
* [XL Deploy for developers, in 5 minutes](http://docs.xebialabs.com/general/xl_deploy_for_developers.html)
* [Getting started with XL Deploy: Understanding packages](http://vimeo.com/99837504)
* [Preparing your application for XL Deploy](http://docs.xebialabs.com/general/preparing_your_application_for_XL_Deploy.html)
* [Understanding deployables and deployeds](https://support.xebialabs.com/entries/30905535-Understanding-Deployables-and-Deployeds)

## Get help

You can always ask questions and connect with other users at [our forums](https://support.xebialabs.com/forums/21189855-XL-Trials-Workshops).
