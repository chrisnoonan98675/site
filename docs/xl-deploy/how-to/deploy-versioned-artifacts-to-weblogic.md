---
title: Deploy versioned artifacts to WebLogic
categories:
xl-deploy
subject:
WebLogic
tags:
weblogic
oracle
middleware
plugin
---

The [XL Deploy WebLogic server (WLS) plugin](/xl-deploy/concept/weblogic-plugin.html) allows you to manage deployments and resources on an Oracle WebLogic server. With this plugin, you can deploy multiple versions of the same application at the same time, which is known as a side-by-side deployment or versioning.

## Artifact versioning properties

Artifact versioning involves several properties on the artifact configuration item (CIs):

* `versioned`: Indicates that the artifact is versioned
* `versionIdentifier`: The artifact version (not needed if automatic versioning is enabled)
* `automaticVersioning`: Indicates that the plugin should automatically version the artifact
* `manifestVersionProperty`: The property in the artifact's manifest file that the plugin can use to automatically derive its version
* `versionExpression`: A [FreeMarker](http://freemarker.org/) expression that the plugin uses to derive the version

## Explicitly set the version

To explicitly set the version, use the `versionIdentifier` property. The plugin will use this value when deploying, updating, or undeploying the application.

## Use automatic versioning

If you do not want to set the version explicitly, you can use automatic versioning, in which case the plugin derives update version numbers automatically. If `automaticVersioning` is set to "true" *or* if `versionIdentifier` is blank, the plugin will automatically version the artifact.

There are two methods for deriving the version:

* The plugin uses the version as defined in XL Deploy; for example, for `Applications/MyApp/1.0`, the version would be `MyApp-1.0`.

* The plugin gets the version from the manifest file.

    * `manifestVersionProperty` identifies the variable in the artifact's manifest file that defines its version; by default, this is `Extension-Name`. If this variable has a value, then the plugin uses the CI's `versionExpression` property to derive the version.

    * `versionExpression` is a FreeMarker expression that the plugin uses to derive the version. By default, it produces a version as follows:

        * If the `Specification-Version` or `Implementation-Version` variable is present, that variable is used
        * If both `Specification-Version` and `Implementation-Version` are present, `Specification-Version@Implementation-Version` is used

**Tip:** If the artifact's manifest uses the `Extension-Name` variable, the name of the artifact CI should match its value.

You can set CI properties in the manifest of a deployment package (DAR file) before importing it, on the CI in the Repository, or in the properties of a mapped deployed. For more information about CI properties, see the [WebLogic Plugin Reference](/xl-deploy/latest/wlsPluginManual.html).

## Changing CI property defaults

The `manifestVersionProperty` CI property defaults to "Extension-Name". You can change the default in the `wls.WarModule.manifestVersionProperty` property in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.

By default, `versionExpression` uses the following FreeMarker expression to calculate the version:

     [#ftl]${manifestAttributes["Specification-Version"]}[#if manifestAttributes["Implementation-Version"]??]@${manifestAttributes["Implementation-Version"]}[/#if]

You can change the default in the `wls.WarModule.versionExpression` property in the `XL_DEPLOY_SERVER_HOME/conf/deployit-defaults.properties` file.
