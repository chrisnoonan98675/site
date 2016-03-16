---
title: Using variables and expressions in FreeMarker templates
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

For example, when using rules to customize a deployment plan, you can invoke a FreeMarker template from an `os-script` or `template` [step](/xl-deploy/latest/referencesteps.html). Also, you can use FreeMarker templates with the Java-based [Generic plugin](/xl-deploy/concept/templating-in-the-xl-deploy-generic-plugin.html), or with a custom plugin that is based on the Generic plugin.

## Variables that are available

The data that is available for you to use in a FreeMarker template depends on when and where the template will be used.

* [Objects and properties available to rules](/xl-deploy/concept/objects-and-properties-available-to-rules.html) describes the objects that are available for you to use in rules with different [scopes](/xl-deploy/concept/understanding-xl-deploy-rule-scope.html)
* The [Steps Reference](/xl-deploy/latest/referencesteps.html) describes the predefined steps that you can invoke using rules
* The [UDM CI reference](/xl-deploy/latest/udmcireference.html) describes the properties of the objects that you can access
* The [Jython API documentation](/jython-docs/#!/xl-deploy/5.1.x/) describes the services that you can access

## Expressions that are available

The XL Deploy FreeMarker processor can handle special characters in variable values by sanitizing them for Microsoft Windows and Unix.

The processor will automatically detect and sanitize variables for each operating system if the FreeMarker template ends with the correct extension:

* For Windows: `.bat.ftl`, `.cmd.ftl`, `.bat`, `.cmd`
* For Unix: `.sh.ftl`, `.sh`

It uses the `${sanitize(password)}` expression to do so (where `password` is an example of a variable name). If the extension is not matched, then the processor will not modify the variable.

When auto-detection based on the file extension is not possible, you can use the following expressions to sanitize variables for each operating system:

* `${sanitizeForWindows(password)}`
* `${sanitizeForUnix(password)}`

Where `password` is an example of a variable name.
