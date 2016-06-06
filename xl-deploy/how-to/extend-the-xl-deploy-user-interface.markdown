---
title: Extend the XL Deploy user interface
categories:
- xl-deploy
subject:
- Customization
tags:
- jython
- gui
---

You can extend XL Deploy by creating new endpoints backed by Jython scripts and new UI screens that use these endpoints.

Each extension must be packaged in a `jar` archive and saved in the `plugins` folder of the XL Deploy server. The typical file structure of UI extension is:

![Simple plugin structure](images/ui-extension-plugin-structure.png)

**Tip:** It is recommended that you create a folder under `web` with an unique name for each UI extension plugin, to avoid file name collisions.

The following XML files tell XL Deploy where to find and how to interpret the content of an extension:

* `xl-rest-endpoints.xml` for adding new REST endpoints
* `xl-ui-plugin.xml` for adding new top-menu items

**Note:** These files are both optional.

## Adding menu items in XL Deploy

The `xl-ui-plugin.xml` file contains information about the menu items to be added to the XL Deploy. They can be ordered using the `weight` attribute.

For example, it could contain:

{% highlight xml %}
<plugin xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="http://www.xebialabs.com/deployit/ui-plugin"
    xsi:schemaLocation="http://www.xebialabs.com/deployit/ui-plugin xl-ui-plugin.xsd">
    <menu id="test.demo" label="Demo" uri="demo.html" weight="12" />
</plugin>
{% endhighlight %}

Menus are enclosed in the `<plugin>` tag. The `xl-ui-plugin.xsd` schema verifies the way that menus are defined.

The attributes that are available for `<menu>` are:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `id` | Yes | Menu item ID, which must be unique among all menus items in XL Deploy; if there are duplicate IDs, XL Deploy will return a RuntimeException |
| `label` | Yes | Text to show on the menu button |
| `uri` | Yes | Link that will be used to fetch the content of the extension. The link needs to point exactly to the file that needs to be loaded by the browser. Default pages like `index.html` are not guaranteed to load automatically. |
| `weight` | Yes | Menu item order; the higher the weight, the further to the right the menu item will be placed |

**Tip:** Menu items created by extensions always appear _after_ all the 'native' XL Deploy menus.

### Calling XL Deploy REST services

You can call two types of XL Deploy REST services from HTML pages:

* XL Deploy REST API
* REST endpoints created by your extension (it is described below how to declare them)

For both types, your extension must authenticate itself with a basic HTTP authentication header appended to each request. You can retrieve the value of this header from the main XL Deploy application:

    var authHeader = parent.getAuthToken()

When you are logged in to XL Deploy with the user name and password `admin`, the `authHeader` variable will contain the string `'Basic YWRtaW46YWRtaW4'`.

**Tip:** If you have configured XL Deploy to run with a context path, don't forget to take it into account when building a path to the REST services.

## Declaring server endpoints

The `xl-rest-endpoints.xml` file contains declaration of endpoints which your extension adds to XL Deploy:

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<endpoints xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://www.xebialabs.com/deployit/endpoints"
           xsi:schemaLocation="http://www.xebialabs.com/deployit/endpoints endpoints.xsd">
    <endpoint path="/test/demo" method="GET" script="demo.py" />
    <!-- ... more endpoints can be declared in the same way ... -->
</endpoints>
{% endhighlight %}

**Note:** After processing this file, XL Deploy creates a new REST endpoint, which is accessible via `http://{xl-deploy-hostname}:{port}/{[context-path]}/api/extension/test/demo`.    

Every endpoint should be represented with `<endpoint>` element which can contain following attributes:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `path` | Yes | Relative REST path which be exposed to run the Jython script |
| `method` | No | HTTP method type (`GET`, `POST`, `DELETE`, `PUT`); default value is `GET` |
| `script` | Yes | Relative path to the Jython script in the classpath |

## Jython scripts

Jython scripts should implement the logic of REST endpoints. Typically, every script will perform queries or actions in XL Deploy and produce a response.

### Objects available in the context

In a script you have access to the following objects:

* Request: [JythonRequest](/jython-docs/#!/xl-deploy/5.0.x/service/com.xebialabs.xlplatform.endpoints.JythonRequest)
* Response: [JythonResponse](/jython-docs/#!/xl-deploy/5.0.x/service/com.xebialabs.xlplatform.endpoints.JythonResponse)
* XL Deploy services, described in the [Jython API documentation](/jython-docs/#!/xl-deploy/5.0.x/)

### HTTP response

The server returns a HTTP response of type `application/json` containing a JSON object with the following fields:

{:.table .table-striped}
| Field | Description |
| ----- | ----------- |
| `entity` | Serialized value that is set in `response.entity` during script execution. XL Deploy takes care about serialization of standard JSON data types: `Number`, `String`, `Boolean`, `Array`, `Dictionary`, and `udm.ConfigurationItem`. |
| `stdout` | Text that was sent to standard output during the execution |
| `stderr` | Text was sent to standard error during the execution |
| `Exception` | Textual representation of exception that was thrown during script execution |

### HTTP status code

You can explicitly set an HTTP status code via `response.statusCode`. If it is not set explicitly and the script executes smoothly, the client will receive `200`. In the case of unhandled exceptions, the client will receive `500`.

## Sample UI extension

You can find a sample UI extension plugin in `XLDEPLOY_SERVER_HOME/samples`.

## Troubleshooting

1. If you do not see your UI extension in XL Deploy, verify that the file paths in the plugin JAR do not start with `./`. You can check this with `jar tf yourfile.jar`. The output should look like:

        xl-rest-endpoints.xml
        xl-ui-plugin.xml
        web/

    It should <b>not</b> look like:

        ./xl-rest-endpoints.xml
        ./xl-ui-plugin
        .xml
        web/

2. For Jython extensions, if you import a module in a Jython script, the import must be relative to the root of the JAR and every package must have the `__init__.py` file. That is, for a file structure like this:

        test/
        test/__init__.py
        test/importing-script.py
        test/calc/test/calc/__init__.py
        test/calc/Calc.py

    The import must look like this:

        from test.calc.calc import Calc
