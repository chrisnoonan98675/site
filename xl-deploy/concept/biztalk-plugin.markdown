---
title: Introduction to the XL Deploy BizTalk plugin
categories:
- xl-deploy
subject:
- BizTalk
tags:
- biztalk
- plugin
---

The XL Deploy BizTalk plugin adds the capability to perform BizTalk configuration and deployment tasks.

For information about BizTalk requirements and the configuration items (CIs) that the plugin supports, refer to the [BizTalk Plugin Reference](/xl-deploy-biztalk-plugin/latest/biztalkPluginManual.html).

## Features

* Create and destroy BizTalk applications in the BizTalk database
    * Start and stop BizTalk applications as necessary
* Add and remove BizTalk and plain assemblies to/from the BizTalk database
* Import BizTalk bindings files into the BizTalk database
* Import BizTalk Business Rule Engine (BRE) vocabularies into the BizTalk database
* Import, deploy, and undeploy BRE rules into the BizTalk database
* Install BizTalk and plain assemblies in the Global Assembly Cache (GAC) on every BizTalk server (host)

## Use in deployment packages

The plugin works with the standard deployment package DAR format. The following is a sample `deployit-manifest.xml` file that includes a few of the deployables provided by the BizTalk plugin:

    <udm.DeploymentPackage version="1.0" application="BizTalkApp1">
      <orchestrator />
      <deployables>
        <biztalk.ApplicationSpec name="BizTalkApp1">
          <applicationName>BizTalkApp1</applicationName>
          <applicationReferences />
        </biztalk.ApplicationSpec>
        <biztalk.BindingsFile name="BizTalkApp1.BindingInfo.xml" file="BizTalkApp1/BizTalkApp1.BindingInfo.xml">
          <scanPlaceholders>true</scanPlaceholders>
          <applicationName>BizTalkApp1</applicationName>
        </biztalk.BindingsFile>
        <biztalk.RulesFile name="BizTalkApp1.Rule.1.0.xml" file="BizTalkApp1/BizTalkApp1.Rule.1.0.xml">
          <scanPlaceholders>true</scanPlaceholders>
          <rulesName>All policies</rulesName>
          <majorVersion>1</majorVersion>
          <minorVersion>0</minorVersion>
        </biztalk.RulesFile>
        <biztalk.BizTalkSchemaAssemblyFile name="BizTalkApp1.Schemas.dll" file="BizTalkApp1/BizTalkApp1.Schemas.dll">
          <scanPlaceholders>true</scanPlaceholders>
          <applicationName>BizTalkApp1</applicationName>
          <sourcePath>{% raw %}{{ASSEMBLY_SOURCE_DIR}}{% endraw %}\BizTalkApp1.Schemas.dll</sourcePath>
          <destinationPath>{% raw %}{{ASSEMBLY_DESTINATION_DIR}}{% endraw %}\BizTalkApp1.Schemas.dll</destinationPath>
        </biztalk.BizTalkSchemaAssemblyFile>
        <biztalk.BizTalkAssemblyFile name="BizTalkApp1.dll" file="BizTalkApp1/BizTalkApp1.dll">
          <scanPlaceholders>true</scanPlaceholders>
          <applicationName>BizTalkApp1</applicationName>
          <sourcePath>{% raw %}{{ASSEMBLY_SOURCE_DIR}}{% endraw %}\BizTalkApp1.dll</sourcePath>
          <destinationPath>{% raw %}{{ASSEMBLY_DESTINATION_DIR}}{% endraw %}\BizTalkApp1.dll</destinationPath>
        </biztalk.BizTalkAssemblyFile>
      </deployables>
    </udm.DeploymentPackage>

## Extend the BizTalk plugin

The BizTalk plugin has been written upon the foundations provided by the PowerShell plugin, which is a standard part of the XL Deploy server distribution. Please refer to the [documentation of the PowerShell plugin](/xl-deploy/concept/introduction-to-the-xl-deploy-powershell-plugin.html) for details on how to extend PowerShell-based plugins.
