---
title: Extend the XL Release user interface
categories:
- xl-release
subject:
- Extension
tags:
- api
- script
- jython
- plugin
- customization
---

You can extend XL Release by adding user interface (UI) screens that call REST services from the XL Release REST API or from custom endpoints, backed by Jython scripts that you write.

## Structuring a UI extension

You install a UI extension by packaging it in a JAR file and saving it in the `XL_RELEASE_SERVER_HOME/plugins` folder. The typical file structure of a UI extension is:

    ui-extension-demo-plugin
        src
            main
                python
                    demo.py
                resources
                    xl-rest-endpoints.xml
                    xl-ui-plugin.xml
                web
                    demo-plugin
                        demo.html
                        main.css


It is recommended that you create a folder under `web` with an unique name for each UI extension plugin, to avoid file name collisions.

The following XML files tell XL Release where to find and how to interpret the content of an extension:

* `xl-ui-plugin.xml` adds items to the top menu bar in XL Release
* `xl-rest-endpoints.xml` adds custom REST endpoints

Both files are optional.

**Tip:** You can also store the `xl-rest-endpoints.xml` file in the `XL_RELEASE_SERVER_HOME/ext` directory.

## Adding menu items

The `xl-ui-plugin.xml` file contains information about the menu items that you want to add to the top menu bar. You can order individual menu items using the `weight` attribute.

Menus are defined by the `menu` tag and enclosed in the `plugin` tag. The `xl-ui-plugin.xsd` schema verifies the way that menus are defined.

The attributes that are available for the `menu` tag are:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `id` | Yes | Menu item ID, which must be unique among all menus items in XL Release. If there are duplicate IDs, XL Release will return a `RuntimeException`. |
| `label` | Yes | Text to show on the menu button. |
| `uri` | Yes | Link that will be used to fetch the content of the extension. The link must point exactly to the file that the browser should load; default pages such as `index.html` are not guaranteed to load automatically. |
| `weight` | Yes | Menu item order; the higher the weight, the further to the right the menu item will be placed. Menu items created by extensions always appear _after_ the native XL Release menu items. |

### Example menu item definition

This is an example of an `xl-ui-plugin.xml` file that adds a menu item called **Demo**:

{% highlight xml %}
<plugin xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="http://www.xebialabs.com/xl-release/ui-plugin"
    xsi:schemaLocation="http://www.xebialabs.com/xl-release/ui-plugin xl-ui-plugin.xsd">
    <menu id="test.demo" label="Demo" uri="demo.html" weight="12" />
</plugin>
{% endhighlight %}

### Calling XL Release REST services

You can call the following services from an HTML page created by a UI extension:

* [XL Release REST API services](https://docs.xebialabs.com/xl-release/latest/rest-api)
* [REST endpoints created by the extension](#declaring-server-endpoints)

In both cases, the extension must authenticate itself with a basic HTTP authentication header appended to each request. You can retrieve the value of this header from the main XL Release application:

    var authHeader = parent.getAuthToken()

## Declaring server endpoints

The `xl-rest-endpoints.xml` file declares the endpoints that your extension adds to XL Release.

Every endpoint should be represented by an `endpoint` element that can contain following attributes:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `path` | Yes | Relative REST path which be exposed to run the Jython script. |
| `method` | No | HTTP method type (`GET`, `POST`, `DELETE`, `PUT`); default value is `GET`. |
| `script` | Yes | Relative path to the Jython script in the classpath. |


For example, this `xl-rest-endpoints.xml` file adds a `GET` endpoint at `/test/demo`:

{% highlight xml %}
<?xml version="1.0" encoding="UTF-8"?>
<endpoints xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://www.xebialabs.com/xl-release/endpoints"
           xsi:schemaLocation="http://www.xebialabs.com/xl-release/endpoints endpoints.xsd">
    <endpoint path="/test/demo" method="GET" script="demo.py" />
    <!-- ... more endpoints can be declared in the same way ... -->
</endpoints>
{% endhighlight %}

After processing this file, XL Release creates a new REST endpoint that is accessible via `http://{xl-deploy-hostname}:{port}/{[context-path]}/api/extension/test/demo`.    

## Writing Jython scripts

You implement the logic of REST endpoints in Jython scripts. Typically, every script will perform queries or actions in XL Release and produce a response.

### Objects available in the context

In a Jython script, you have access to the following objects:

* Request: [JythonRequest](/jython-docs/#!/xl-deploy/5.0.x/service/com.xebialabs.xlplatform.endpoints.JythonRequest)
* Response: [JythonResponse](/jython-docs/#!/xl-deploy/5.5.x/service/com.xebialabs.xlplatform.endpoints.JythonResponse)
* XL Release services, described in the [Jython API documentation](/jython-docs/#!/xl-release/5.0.x/)

### HTTP response

The XL Release server returns a HTTP response of type `application/json`, which contains a JSON object with the following fields:

{:.table .table-striped}
| Field | Description |
| ----- | ----------- |
| `entity` | Serialized value that is set in `response.entity` during script execution. XL Release takes care about serialization of standard JSON data types: `Number`, `String`, `Boolean`, `Array`, `Dictionary`, and `udm.ConfigurationItem`. |
| `stdout` | Text that was sent to standard output during the execution. |
| `stderr` | Text was sent to standard error during the execution. |
| `Exception` | Textual representation of any exception that was thrown during script execution. |

### HTTP status code

You can explicitly set an HTTP status code via `response.statusCode`. If a status code is not set explicitly and the script executes smoothly, the client will receive code `200`. In the case of unhandled exceptions, the client will receive code `500`.

## Troubleshooting

### Menu item does not appear in UI

If you do not see your UI extension in XL Release, verify that the file paths in the extension JAR do not start with `./`. You can check this with the `jar tf yourfile.jar` command. The output should look like:

    xl-rest-endpoints.xml
    xl-ui-plugin.xml
    web/

It should *not* look like:

    ./xl-rest-endpoints.xml
    ./xl-ui-plugin
    .xml
    web/

#### Importing Jython modules

For Jython extensions, if you import a module in a Jython script, the import must be relative to the root of the JAR and every package must have the `__init__.py` file. That is, for a file structure like this:

    test/
    test/__init__.py
    test/importing-script.py
    test/calc/test/calc/__init__.py
    test/calc/Calc.py

The import must look like this:

    from test.calc.calc import Calc
