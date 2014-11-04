---
title: Troubleshooting WebSphere connectivity
categories:
- xl-deploy
tags:
- websphere
- middleware
- connectivity
---

You may encounter the following error when using XL Deploy to deploy an application to IBM WebSphere:

    Deploying application sometest.war with args: ['-appname', sometest.war', '-contextroot', 'jee6sandboxdemo', '-MapModulesToServers', [['.*', '.*', 'WebSphere:cell=ontCell,cluster=Jee6SandboxDemo']], '-MapWebModToVH', [['.*', '.*', 'default_host']], '-usedefaultbindings']
     Traceback (most recent call last):
        File "", line 1, in ?
        File "", line 116, in runScriptFromDaemon
        File "E:\temp\deployit\overthere-20121211T075233005.tmp\deploy-application.py", line 194, in ?
          AdminApp.install(deployed.file, installArgs)
      com.ibm.ws.scripting.ScriptingException: com.ibm.ws.scripting.ScriptingException:            
       com.ibm.websphere.management.application.client.AppDeploymentException:      
      com.ibm.websphere.management.application.client.AppDeploymentException: java.lang.NoClassDefFoundError: javax.annotation.sql.DataSourceDefinition [Root      
      exception is java.lang.NoClassDefFoundError: javax.annotation.sql.DataSourceDefinition]
      
      java.lang.NoClassDefFoundError: java.lang.NoClassDefFoundError: javax.annotation.sql.DataSourceDefinition

If you look at `console_output.txt`, you can see that XL Deploy tries to run the WebSphere administrative console command on the WebSphere host:

    AdminApp.install(deployed.file, installArgs)
    where deployed.file is the location of the file you want to deploy, and installArgs are the printed arguments on the first line of the log file.

See the documentation for [AdminApp.install](http://www-01.ibm.com/support/knowledgecenter/SS7JFU_6.1.0/com.ibm.websphere.express.doc/info/exp/ae/txml_callappinstall.html) tool for more information.

Run this command directly on the server and see what happens. If the problems also exist on the server, than you might need to fix your WebSphere installation. XL Deploy depends on such commands to be executed correctly. If the problems with the WebSphere server have been solved, XL Deploy will also work correctly.
