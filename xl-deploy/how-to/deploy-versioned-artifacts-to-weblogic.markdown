---
title: Deploy versioned artifacts to WebLogic
categories:
- xl-deploy
subject:
- WebLogic
tags:
- weblogic
- oracle
- middleware
- plugin
---

The [XL Deploy WebLogic server (WLS) plugin](/xl-deploy/concept/weblogic-plugin.html) allows you to manage deployments and resources on an Oracle WebLogic server. With this plugin, you can deploy non-versioned artifacts such as EAR, WAR, and EJBJAR files as versioned artifacts.

Artifact versioning involves several properties on the artifact configuration item (CIs):

* `versioned`: Indicates that the artifact is versioned
* `automaticVersioning`: Indicates that the plugin should automatically version the artifact
* `versionIdentifier`: The artifact version (not needed if automatic versioning is enabled)
* `manifestVersionProperty`: The property in the artifact's manifest file that defines its version
* `versionExpression`: A [FreeMarker](http://freemarker.org/) expression that the plugin uses to calculate the version

The way that you configure the artifact CI properties depends on the version setting that the artifact's manifest file (usually called `MANIFEST.MF`) uses. For example:

* If the manifest uses the `Extension-Name` property:
    * The name of the CI must match the value of the `Extension-Name` property
    * Set `automaticVersioning` and `versioned` to "true"
    * Set `manifestVersionProperty` to "Extension-Name"

* If the manifest uses the `Specification-Version` property:
    * Set `automaticVersioning` and `versioned` to "false"
    * Leave `versionIdentifier` blank
    * Set `manifestVersionProperty` to "Specification-Version" 

You can configure the CI properties in the manifest of a deployment package (DAR file) before importing it, on the CI in the Repository, or in the properties of a mapped deployed. For more information about CI properties, see the [WebLogic Plugin Reference](/xl-deploy/latest/wlsPluginManual.html).

## Changing CI property defaults

The `manifestVersionProperty` CI property defaults to "Extension-Name". You can change the default in the `wls.WarModule.manifestVersionProperty` property in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file.

By default, `versionExpression` uses the following FreeMarker expression to calculate the version:

     [#ftl]${manifestAttributes["Specification-Version"]}[#if manifestAttributes["Implementation-Version"]??]@${manifestAttributes["Implementation-Version"]}[/#if]

You can change the default in the `wls.WarModule.versionExpression` property in the `<XLDEPLOY_HOME>/conf/deployit-defaults.properties` file.
