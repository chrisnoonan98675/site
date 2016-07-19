---
title: Best practices for rules
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- step
- logging
since:
- XL Deploy 4.5.0
weight: 134
---

This topic provides some best practices when writing [XL Deploy rules](/xl-deploy/concept/getting-started-with-xl-deploy-rules.html).

Before you start to write rules, it is recommended that you look at the open source plugins in the [XebiaLabs community](https://github.com/xebialabs-community/) to get an idea of naming conventions used in `synthetic.xml` and `xl-rules.xml` files.

## Types of operations that are needed

At a minimum, a rules-based plugin should contain `CREATE` rules, so that XL Deploy will take action during a deployment.

Additionally, you will typically need to include `DESTROY` rules to update and undeploy deployeds. Also, while you can perform an update by using a `DESTROY` rule followed by a `CREATE` rule, you can use `MODIFY` rules to support more intelligent update operations.

## Using a namespace

To avoid name clashes among plugins that you have created or acquired, it is recommended you use a namespace for your rules based on your company name. For example:

    <rule name="com.mycompany.xl-rules.createFooResource" scope="deployed">...</rule>

## Using unique script names

Some step types allow you to refer to a script by name. XL Deploy will search for the script on the full classpath; this includes the `ext/` folder as well as the `conf/` folder, inside JAR files, and so on. Ensure that scripts are uniquely named across all of these locations.

**Note:** Some steps search for scripts with derived names. For example, the `os-script` step will search for `my script` as well as `myscript.sh` and `myscript.bat`.

## Referring from a deployed

It is recommended that you do not refer from one deployed to another deployed or container. They are hard to set from a dictionary.

## Increasing logging output

To see more information during the [planning phase](/xl-deploy/concept/understanding-the-xl-deploy-planning-phase.html), you can increase the logging output:

1. Open `<XLDEPLOY_HOME>/conf/logback.xml` for editing.
1. Add a statement such as one of the following:

        <logger name="com.xebialabs.deployit.deployment.rules" level="debug" />
        <logger name="com.xebialabs.deployit.deployment.rules" level="trace" />

1. Use the `logger` object in Jython scripts.
