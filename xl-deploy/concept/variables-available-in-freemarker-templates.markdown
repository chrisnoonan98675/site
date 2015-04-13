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

For example, when using rules to customize a deployment plan, you can invoke a FreeMarker template from an [`os-script`](https://docs.xebialabs.com/xl-deploy/4.5.x/referencesteps.html#os-script) or [`template`](https://docs.xebialabs.com/xl-deploy/4.5.x/referencesteps.html#template) step. Also, you can use FreeMarker templates with the Java-based [Generic plugin](https://docs.xebialabs.com/xl-deploy/4.5.x/genericPluginManual.html#templating), or with a custom plugin that is based on the Generic plugin.

The data that is available for you to use in a FreeMarker template depends on when and where the template will be used.

* The [Rules Manual](https://docs.xebialabs.com/xl-deploy/4.5.x/rulesmanual.html#define-rule-behavior) describes the objects that are available for you to use in rules with different scopes
* The [Steps Reference](https://docs.xebialabs.com/xl-deploy/4.5.x/referencesteps.html) describes the predefined steps that you can invoke using rules
* The [UDM CI reference](https://docs.xebialabs.com/xl-deploy/4.5.x/udmcireference.html) describes the properties of the objects that you can access
* The [Jython API documentation](https://docs.xebialabs.com/jython-docs/#!/xl-deploy/4.5.x/) describes the services that you can access

