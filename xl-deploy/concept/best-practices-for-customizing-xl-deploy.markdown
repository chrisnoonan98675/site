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

When customizing XL Deploy, it is recommended that you start by [extending configuration item (CI) types](/xl-deploy/how-to/customize-an-existing-ci-type.html) and [writing rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html).

If you cannot achieve the desired behavior through rules, you can build [custom server plugpoints or plugins](/xl-deploy/how-to/create-an-xl-deploy-plugin.html) using Java. When building a plugin in Java, create a build project that includes the `XL_DEPLOY_SERVER_HOME/lib` directory on its classpath.

## Getting started with customization

For examples of CI type modifications (`synthetic.xml`) and rules (`xl-rules.xml`), look at the open source plugins in the [XebiaLabs community plugins repository](https://github.com/xebialabs-community).  

## Configuration item type modifications

When extending a CI type, it is recommended that you first copy the existing CI type to a custom namespace for your organization, and then make the desired changes. Similarly, if you want to modify a script that is used in a plugin, first copy it to a different classpath namespace, then make the desired changes.

This approach will ensure that you can easily roll back problematic changes by replacing the CI types or scripts with the originals.

### Managing `synthetic.xml` customizations

XL Deploy will load all `synthetic.xml` files that it finds on the classpath. This means that you can store `synthetic.xml` files (and associated scripts and other resources) in:

* The `XL_DEPLOY_SERVER_HOME/ext` directory. This is recommended for small, local customizations.

* A JAR file in the `XL_DEPLOY_SERVER_HOME/plugins` directory. This is recommended for larger customizations. It also makes it easier to version-control customizations by storing them in a source control management system (such as Git or SVN) from which you build JAR files.

* A subdirectory of the `XL_DEPLOY_SERVER_HOME/plugins` directory. This is similar to storing customizations in the `ext` directory or in an exploded JAR file. In this case, you can also easily version-control customizations.

{% comment %}
## Referring from a deployed to another CI

While you can [refer from one CI to another](/xl-deploy/concept/xl-deploy-manifest-format.html#refer-from-one-ci-to-another), it is recommended that you avoid referring from one deployed to another deployed or to a container.
{% endcomment %}

## Plugin idempotency

It is recommended that you try to make plugins idempotent; this makes the plugin more robust in the case of rollbacks.

## Using operations in rules

A rule's `operation` property identifies the operations it is restricted to: `CREATE`, `MODIFY`, `DESTROY`, or `NOOP`. Generally, a plugin that uses rules should contain one or more rules with the `CREATE` operation, to ensure that the plugin can deploy artifacts and/or resources. The plugin should also contain `DESTROY` rules, so that it can update and undeploy deployed applications.

You may also want to include `MODIFY` rules that will update deployed applications in a more intelligent way; however, in some cases, a simple `DESTROY` operation followed by a `CREATE` operation is more appropriate for application updates.

## Handling passwords in plugins

If you develop a custom plugin in Java, ensure that you do not log passwords in plain text while the plugin is executing; it is recommended that you replace passwords with a string such as `******`.

Also, ensure that you do not include passwords in the command line when executing an external tool, because this will cause them to appear in the output of the `ps` command.
