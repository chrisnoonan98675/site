---
title: Create custom configuration types
categories:
- xl-release
subject:
- Extending XL Release
tags:
- configuration object
- python
- plugin
- customization
---

XL Release allows you to add custom configuration types in XML. Custom configuration types appear in the configuration screens, and custom tasks can reference configuration instances. You can use custom configuration type tasks to reference third-party component settings. For example, XL Release includes with JIRA Server and Jenkins Server, which are custom configuration types.

To use a custom configuration type from a custom task, you need:

* The definition of the custom configuration type
* The reference to the configuration type in the custom task definition
* To create a configuration instance
* To reference the configuration instance from a custom task

## Configuration type definition

You define the custom configuration type in XML in `synthetic.xml`. For example, this is the definition of the "JIRA Server" and "Jenkins Server" types:

    <type type="jira.Server" extends="configuration.HttpConnection"/>
    <type type="jenkins.Server" extends="configuration.HttpConnection"/>

Each configuration type must extend the `xlrelease.Configuration` or `configuration.HttpConnection` root configuration type. The `xlrelease.Configuration` type can be used for simple configuration types, while the `configuration.HttpConnection` should be used if you need to define an HTTP endpoint. In the example, the "JIRA Server" and "Jenkins Server" types extend `configuration.HttpConnection`, which defines these properties:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `url` | Address where the server can be reached |
| `username` | Log-in user ID on the server |
| `password` | Log-in user password on the server |
| `proxyHost` | HTTP proxy host |
| `proxyPort` | HTTP proxy port |

The `virtual="true"` attribute means that this type will not appear in the UI and that we can not create an instance of this type.

## Reference configuration type from custom task definition

To reference a custom configuration type from a custom task, you must add a specific property to the custom task definition in `synthetic.xml`. For example, this is the "Create JIRA issue" definition:

    <type type="jira.CreateIssue" extends="xlrelease.PythonScript">
        <property name="jiraServer" category="input" label="Server" referenced-type="jira.Server" kind="ci"/>

        ...
    </type>

The required attributes to refer to a custom configuration type are:

* `kind="ci"`: Specifies that this property is a reference
* `referenced-type="your.Type"`: Specifies which configuration type can be referenced in this property

## Configuration page

Use the **Task configurations** page to configure objects that a custom task can reference (such as JIRA tasks or Jenkins tasks). This page is accessible to users with Admin permissions.

**Note:** Prior to XL Release 6.0.0, the page is called **Configuration**.

The page shows the configuration types that are currently available and allows you to create instances of the types.

![Configurations list](/xl-release/images/configurations-list.png)

XL Release includes two configuration types: JIRA Server and Jenkins Server.

To add a configuration instance, click **Add** under the type that you need. You can then set properties:

![Configuration details](/xl-release/images/jira-configuration-details.png)

Enter a symbolic name in the **Title** box. In the XL Release application, the configuration instance is referred to by this name.

To edit or delete an instance, click its name.

## Testing connectivity

In XL Release 6.0.1 and later, you can test the connectivity of the **Shared configuration** options. All types that extend `xlrelease.Configuration` or `configuration.HttpConnection` in `synthetic.xml` are eligible for testing.

To enable the testing feature, place a Python script in the plugin folder with the name of the type. For example, in the case of a JIRA server:

    <type type="jira.Server" extends="configuration.HttpConnection"/>

The script should be located in the `jira` folder and called `Server.py`. You can override the location and file name by adding the property `scriptLocation` in the `type` declaration:

    <type type="jira.Server" extends="configuration.HttpConnection">
      <property name="scriptLocation" default="jira/TestConnection.py" hidden="true" />
    </type>

The following properties are available in the script context:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| `HttpRequest` | Refer to the [Jython API](/jython-docs/#!/xl-release/6.0.x/service/HttpRequest.HttpRequest) for more information |
| `HttpResponse` | Refer to the [Jython API](/jython-docs/#!/xl-release/6.0.x/service/HttpRequest.HttpResponse) for more information |
| `configuration` | Container with all properties from the `type`; for example, if the `type` extends `configuration.HttpConnection`, you can access the user name, password, and so on |

The content of the script can be similar to:

{% highlight python %}
# get the configuration properties from the UI
params = { 'url': configuration.url, 'username' : configuration.username, 'password': configuration.password,  'proxyHost': configuration.proxyHost, 'proxyPort': configuration.proxyPort }

# do an http request to the server
response = HttpRequest(params).get('/', contentType = 'application/json')

# check response status code, if is different than 200 exit with error code
if reponse.status != 200:
  sys.exit(1)
{% endhighlight %}


If your type extends from `configuration.HttpConnection`, then you can use a standard simple HTTP connection test script available at path `configuration/HttpConnectionCheck.py`:

    <type type="nexus.Server" extends="configuration.HttpConnection">
        <property name="scriptLocation" hidden="true" default="configuration/HttpConnectionCheck.py"/>
        <property name="checkConfigurationPath" hidden="true" default="/service/local/authentication/login"/>
        <property name="checkConfigurationContentType" hidden="true" default="application/json"/>
    </type>

This script takes the URL configured in the UI, appends the value of the `checkConfigurationPath` property, and sends a HEAD request with the user name and password provided, also taking proxy settings into account. If the response status code is between 200 and 399, then the configuration is considered to be correct.

## Reference a configuration instance from a custom task

For information about referencing a configuration instance from a custom task, see [Create a Jenkins task](/xl-release/how-to/create-a-jenkins-task.html).
