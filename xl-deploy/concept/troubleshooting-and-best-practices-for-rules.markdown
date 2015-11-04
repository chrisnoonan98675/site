---
title: Troubleshooting and best practices for rules
categories:
- xl-deploy
subject:
- Rules
tags:
- rules
- step
- logging
- troubleshooting
since:
- XL Deploy 4.5.0
---

## Namespace

To avoid name clashes among plugins that you have created or acquired, it is recommended you use a namespace for your rules based on your company name. For example:

    <rule name="com.mycompany.xl-rules.createFooResource" scope="deployed">...</rule>

## Logging

To see more information during the planning phase, you can increase the logging output:

1. Open `<XLDEPLOY_HOME>/conf/logback.xml` for editing.
1. Add a statement such as one of the following:

        <logger name="com.xebialabs.deployit.deployment.rules" level="debug" />
        <logger name="com.xebialabs.deployit.deployment.rules" level="trace" />

1. Use the `logger` object in Jython scripts.

## Unique script names

Some step types allow you to refer to a script by name. XL Deploy will search for the script on the full classpath; this includes the `ext/` folder as well as the `conf/` folder, inside JAR files, and so on. Ensure that scripts are uniquely named across all of these locations.

**Note:** Some steps search for scripts with derived names. For example, the `os-script` step will search for `my script` as well as `myscript.sh` and `myscript.bat`.
