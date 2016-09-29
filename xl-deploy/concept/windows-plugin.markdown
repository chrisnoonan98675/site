---
title: Introduction to the XL Deploy Windows plugin
categories:
- xl-deploy
subject:
- Bundled plugins
tags:
- windows
- plugin
weight: 364
---

The XL Deploy Windows plugin enables XL Deploy to perform common Microsoft Windows configuration and deployment tasks.

For information about the configuration items (CIs) that the Windows plugin provides, refer to the [Windows Plugin Reference](/xl-deploy/latest/windowsPluginManual.html).

## Features

* Install and uninstall MSI files
* Import Windows registry files
* Create and destroy Windows services
* Create and destroy Windows shares

## Use in deployment packages

This is a sample deployment package (DAR) manifest file (`deployit-manifest.xml`) that includes a few of the deployables provided by the Windows plugin:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage application="Windows" version="1.0">
      <deployables>
        <windows.Msi name="Apache HTTPD" file="httpd-2.2.22-win32-x86-no_ssl.msi" />
        <windows.RegFile name="My service registry key" file="myservice.reg" />
        <windows.ServiceSpec name="My service">
          <binaryPathName>C:\Program Files\Common Services\myservice.exe</binaryPathName>
          <dependsOn>
            <value>tcpip</value>
          </dependsOn>
        </windows.ServiceSpec>
        <windows.ShareSpec name="My share">
          <targetPath>C:\Users\deployit\Public</targetPath>
        </windows.ShareSpec>
      </deployables>
    </udm.DeploymentPackage>

## Important usage notes

* Windows registry files are imported when a package containing them is deployed or updated. However, no specific logic for removal of the registry keys is included. Ensure that your registry file removes the complete key used by your application before adding the values. See [Microsoft Knowledge Base article #310516](http://support.microsoft.com/kb/310516) for how to do this in a Windows registry file.

* Ensure that `services.msc` is not open when services are being deployed. Otherwise, the deployment may hang and/or the machine might need to be rebooted for the changes to take effect.

* Ensure that `regedit.exe` is not open when registry files are being imported. Otherwise, the deployment may hang and/or the machine might need to be rebooted for the changes to take effect.

## Extending the Windows plugin

The Windows plugin is based on the XL Deploy PowerShell plugin, which is a standard part of the XL Deploy server distribution. Please refer to the [documentation of the PowerShell plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-powershell-plugin.html) for information about how to extend PowerShell-based plugins.
