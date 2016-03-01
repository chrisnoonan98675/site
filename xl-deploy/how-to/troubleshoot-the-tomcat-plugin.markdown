---
title: Troubleshoot the XL Deploy Tomcat plugin
categories:
- xl-deploy
subject:
- Tomcat plugin
tags:
- tomcat
- middleware
- troubleshooting
- plugin
---

## Starting Tomcat

XL Deploy expects the Tomcat start command to return and leave Tomcat running. This means that the default Tomcat start script will cause XL Deploy to hang during deployment. You can specify start and stop commands on the `tomcat.Server` configuration item (CI).

For example, to start and stop Tomcat 7 when it is installed as a daemon on Unix, use `service tomcat7 start` or `/etc/init.d/tomcat7 start`. On Microsoft Windows, use `net start tomcat7`.

**Tip:** If your start and stop scripts only return when Tomcat is actually started or stopped, set the wait times on the `tomcat.Server` CI to 0.

## Restarting Tomcat after deployment

If you are running Tomcat on Unix and you find that the Tomcat server does not restart after application deployment, you may need to use the `nohup` command with the `sleep` option. This ensures that the SSH connection is not closed before the restart is processed. For example:

    nohup /usr/local/apache-tomcat-7.0.42/bin/catalina.sh start && sleep 2

## Disabling management of `context.xml`

If you find that the Tomcat plugin deploys additional files that cause conflicts, you may need to disable its management of the `context.xml` file, as described in the [deployed actions table](/xl-deploy-tomcat-plugin/5.0.x/tomcatPluginManual.html#deployed-actions-table). To disable management, locate the following line in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file:

    #tomcat.manageContextXml=true

Change the line to:

    tomcat.manageContextXml=false

For more information about the Tomcat plugin, refer to:

* [Introduction to the XL Deploy Tomcat plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-tomcat-plugin.html)
* [Tomcat Plugin Reference](/xl-deploy/latest/tomcatPluginManual.html)
