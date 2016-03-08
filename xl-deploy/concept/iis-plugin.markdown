---
title: Introduction to the XL Deploy IIS plugin
categories:
- xl-deploy
subject:
- IIS plugin
tags:
- iis
- microsoft
- middleware
- plugin
---

The XL Deploy Microsoft Internet Information Services (IIS) plugin adds the capability to perform IIS deployments and configuration tasks.

For IIS requirements and configuration items (CIs) that the plugin supports, refer to the [IIS Plugin Reference](/xl-deploy/latest/iisPluginManual.html).

## Features

* Supports Internet Information Services version 6.0 or higher:
    * IIS 6.0 (Windows Server 2003 R2)
    * IIS 7.0 (Windows Server 2008)
    * IIS 7.5 (Windows Server 2008 R2)
    * IIS 8.0 (Windows Server 2012)
    * IIS 8.5 (Windows Server 2012 R2)
* Deploy and undeploy IIS web content
* Create, modify, and destroy IIS configuration elements:
    * Websites
    * Application pools
    * Applications
    * Virtual directories
* Set options on IIS configuration elements:
    * Authentication modes
    * Directory browsing
* Stop and start websites and application pools as necessary

## Use in deployment packages

The plugin works with the standard deployment package DAR format. The following is a sample `deployit-manifest.xml` file that includes a few of the deployables provided by the IIS plugin:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage application="NerdDinner" version="2.0">
      <deployables>
        <iis.WebContent name="NerdDinner-files" file="NerdDinner-files/NerdDinner-Files">
          <targetPath>C:\inetpub\nerddinner</targetPath>
        </iis.WebContent>
        <iis.ApplicationPoolSpec name="NerdDinner-applicationPool">
          <managedRuntimeVersion>v4.0</managedRuntimeVersion>
        </iis.ApplicationPoolSpec>
        <iis.WebsiteSpec name="NerdDinner-website">
          <websiteName>NerdDinner</websiteName>
          <physicalPath>C:\inetpub\nerddinner</physicalPath>
          <applicationPoolName>NerdDinner-applicationPool</applicationPoolName>
          <bindings>
            <iis.WebsiteBindingSpec name="NerdDinner-website/88">
              <port>88</port>
            </iis.WebsiteBindingSpec>
          </bindings>
        </iis.WebsiteSpec>
      </deployables>
    </udm.DeploymentPackage>

## Extend the IIS plugin ##

The IIS plugin has been written upon the foundations provided by the PowerShell plugin, which is a standard part of the XL Deploy server distribution. Please refer to the [documentation of the PowerShell plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-powershell-plugin.html) for details on how to extend PowerShell-based plugins.
