---
title: Create custom configuration types in XL Release
categories:
- xl-release
subject:
- Configuration
tags:
- configuration
- python
---

XL Release allows you to add custom configuration types in XML. Custom configuration types appear in the configuration screens, and custom tasks can reference configuration instances. You can use custom configuration type tasks to reference third-party component settings.

For example, XL Release includes with JIRA Server and Jenkins Server, which are custom configuration types.

To use a custom configuration type from a custom task, you need:

* The definition of the custom configuration type
* The reference to the configuration type in the custom task definition
* To create a configuration instance
* To reference the configuration instance from a custom task

## Configuration type definition

You define the custom configuration type in XML in `synthetic.xml`. For example, this is the definition of the "Jira Server" and "Jenkins Server" types:

    <type type="jira.Server" extends="configuration.HttpConnection"/>
    <type type="jenkins.Server" extends="configuration.HttpConnection"/>

Each configuration type must extend the root configuration type `xlrelease.Configuration` or `configuration.HttpConnection`.

In the example, the "Jira Server" and "Jenkins Server" types extend `configuration.HttpConnection`, which defines these properties:

* `url`: The address where the server is reachable.
* `username`: The login user ID on the server.
* `password`: The login user password on the server.
* `proxyHost`: The http proxy host
* `proxyPort` the http proxy port

The `virtual="true"` attribute means that this type will not appear in the UI and that we can not create an instance of this type.

## Reference configuration type from custom task definition

To reference a custom configuration type from a custom task, you must add a specific property to the custom task definition in `synthetic.xml`. For example, this is the "Create Jira issue" definition:

    <type type="jira.CreateIssue" extends="xlrelease.PythonScript">
        <property name="jiraServer" category="input" label="Server" referenced-type="jira.Server" kind="ci"/>

        ...
    </type>

The required attributes to refer to a custom configuration type are:

* `kind="ci"`: Specifies that this property is a reference.
* `referenced-type="your.Type"`: Specifies which configuration type can be referenced in this property.

## Configuration page

Use the **Configuration** page to configure objects that a custom task can reference (such as Jira tasks or Jenkins tasks). This page is accessible to users with Admin permissions.

The page shows the configuration types that are currently available and allows you to create instances of the types.

![Configurations list](plugin_manual/configurations-list.png)

XL Release includes two configuration types: Jira Server and Jenkins Server.

To add a configuration instance, click **Add** under the type that you need. You can then set properties:

![Configuration details](plugin_manual/jira-configuration-details.png)

Enter a symbolic name in the **Title** box. In the XL Release application, the configuration instance is referred to by this name.

To edit or delete an instance, click its name.

## Reference a configuration instance from a custom task

For information about referencing a configuration instance from a custom task, see the [Jenkins task documentation](reference_manual.html#jenkins-task).
