---
title: Propagate the XL Deploy WebSphere plugin to unmanaged web servers
categories:
- xl-deploy
tags:
- websphere
- middleware
- plugin
---

To propagate XL Deploy's [IBM WebSphere plugin](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html) to [unmanaged web servers](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html#wasunmanagedwebserver) in your WebSphere installation:

1. In the **Infrastructure** section of the XL Deploy repository, [create](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#creating-a-new-ci) a [host](http://docs.xebialabs.com/releases/latest/deployit/remotingPluginManual.html#overtherehost) for the server on which your web server (such as an Apache web server) is running.
2. Under the host, create a [`was.UnmanagedWebServer`](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html#wasunmanagedwebserver) and set its **Plugin Configuration Path** property, including the file name; for example, `/mypath/plugin-cfg.xml`.
3. Update the `was.DeploymentManager` in XL Deploy:
    * Select the **Update Global Plugin Configuration** option.
    * Add the web server you created to the deployment manager's list of **Unmanaged Web Servers**.
4. To verify the functionality, set up a deployment to the cell and use the [Plan Analyzer](http://docs.xebialabs.com/releases/latest/deployit/guimanual.html#using-the-plan-analyzer) to review the deployment plan. There should be a step to *Copy global WebSphere plugin configuration file to '...' for unmanaged web server '...'* (with the values specific to your system). 

For more information about the plugin, refer to the [IBM WebSphere Plugin Manual](http://docs.xebialabs.com/releases/latest/was-plugin/wasPluginManual.html).
