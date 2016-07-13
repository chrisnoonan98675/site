---
title: Best practices for customizing XL Deploy
categories:
- xl-deploy
subject:
- Customization
tags:
- plugin
- type system
---

When customizing XL Deploy, it is recommended that you start by [extending configuration item (CI) types](/xl-deploy/how-to/customize-an-existing-ci-type.html) and [writing rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html), both of which are done in the `<XLDEPLOY_HOME>/ext` directory.

If you cannot achieve the desired behavior through rules, you can build [custom server plugpoints or plugins](/xl-deploy/how-to/create-an-xl-deploy-plugin.html) using Java. When building a plugin in Java, create a build project that includes the `<XLDEPLOY_HOME>/lib` directory on its classpath.

## Configuration item type modifications

When extending a CI type, it is recommended that you first copy the existing CI type to a custom namespace for your organization, and then make the desired changes. Similarly, if you want to modify a script that is used in a plugin, first copy it to a different classpath namespace, then make the desired changes.

This approach will ensure that you can easily roll back problematic changes by replacing the CI types or scripts with the originals.

## Handling passwords in plugins

If you develop a custom plugin in Java, ensure that you do not log passwords in plain text while the plugin is executing; it is recommended that you replace passwords with a string such as `******`.

Also, ensure that you do not include passwords in the command line when executing an external tool, because this will cause them to appear in the output of the `ps` command.
