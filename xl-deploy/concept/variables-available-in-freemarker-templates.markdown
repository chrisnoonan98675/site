---
title: Variables available in FreeMarker templates
subject:
- Rules
categories:
- xl-deploy
tags:
- plugin
- deployment
- step
- freemarker
- rules
---

XL Deploy uses the [FreeMarker](http://freemarker.sourceforge.net/) templating engine to allow you to access deployment properties such as such as the names or locations of files in the deployment package. 

For example, when using rules to customize a deployment plan, you can invoke a FreeMarker template from an [`os-script`](/xl-deploy/5.0.x/referencesteps.html#os-script) or [`template`](/xl-deploy/5.0.x/referencesteps.html#template) step. Also, you can use FreeMarker templates with the Java-based [Generic plugin](/xl-deploy/latest/genericPluginManual.html#templating), or with a custom plugin that is based on the Generic plugin.

The data that is available for you to use in a FreeMarker template depends on when and where the template will be used.

* [Objects and properties available to rules](/xl-deploy/concept/objects-and-properties-available-to-rules.html) describes the objects that are available for you to use in rules with different [scopes](/xl-deploy/concept/understanding-xl-deploy-rule-scope.html)
* The [Steps Reference](/xl-deploy/latest/referencesteps.html) describes the predefined steps that you can invoke using rules
* The [UDM CI reference](/xl-deploy/latest/udmcireference.html) describes the properties of the objects that you can access
* The [Jython API documentation](/jython-docs/#!/xl-deploy/5.0.x/) describes the services that you can access
