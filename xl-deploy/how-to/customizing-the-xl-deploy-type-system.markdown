---
title: Customizing the XL Deploy type system
categories:
- xl-deploy
subject:
- Customization
tags:
- ci
- synthetic
- type system
---

XL Deploy's type system allows you to customize any CI by changing its definition. Properties can be added, hidden or changed. These new properties are called _synthetic properties_ because they are not defined in a Java class. The properties and changes are defined in an XML file called `synthetic.xml` which is added to the XL Deploy classpath. Changes to the types are loaded when the XL Deploy server starts and can be used to perform deployments.

For information about the ways that you can customize the type system, refer to:

* [Customize an existing CI type](/xl-deploy/how-to/customize-an-existing-ci-type.html)
* [Define a new CI type](/xl-deploy/how-to/define-a-new-ci-type.html)
* [Define a synthetic method](/xl-deploy/how-to/define-a-synthetic-method.html)
