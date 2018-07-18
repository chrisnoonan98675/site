---
title: Declare custom REST endpoints
categories:
- xl-release
subject:
- Extending XL Release
tags:
- api
- script
- jython
- plugin
- customization
since:
- XL Release 4.8.0
---

You can extend XL Release by creating new endpoints backed by Jython scripts. You can use this feature, for example, to to integrate with other systems.

To declare new endpoints, add a file called `xl-rest-endpoints.xml` in the classpath of your XL Release server. This file can be in the JAR file of a custom plugin or in the `XL_RELEASE_SERVER_HOME/ext` directory. For example:

    <?xml version="1.0" encoding="UTF-8"?>
    <endpoints xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns="http://www.xebialabs.com/deployit/endpoints"
               xsi:schemaLocation="http://www.xebialabs.com/deployit/endpoints endpoints.xsd">
        <endpoint path="/test/demo" method="GET" script="demo.py" />
        <!-- ... more endpoints can be declared in the same way ... -->
    </endpoints>

After processing this file, XL Release creates a new REST endpoint, which is accessible via `http(s)://{xl-release-hostname}:{port}/{[context-path]}/api/extension/test/demo`.

Every endpoint should be represented by `<endpoint>` element, which can contain following attributes:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `path` | Yes | Relative REST path which will be exposed to run the Jython script |
| `method` | No | HTTP method type (GET, POST, DELETE, PUT); default value is GET |
| `script` | Yes | Relative path to the Jython script in the classpath |

## Jython scripts

Jython scripts should implement the logic of REST endpoints. Typically, every script will perform queries or actions in XL Release and produce a response.

### Objects available in the context

In a script, you have access to XL Release services and to the following objects:

* Request: [JythonRequest](https://docs.xebialabs.com/jython-docs/#!/xl-deploy/7.2.x/service/com.xebialabs.xlplatform.endpoints.JythonRequest)
* Response: [JythonResponse](https://docs.xebialabs.com/jython-docs/#!/xl-deploy/7.2.x/service/com.xebialabs.xlplatform.endpoints.JythonResponse)

Refer to the [XL Release Jython API](https://docs.xebialabs.com/jython-docs/#!/xl-release/7.2.x/) for the complete information.

### HTTP response

The server returns an HTTP response of type `application/json`, which contains a JSON object with the following fields:

{:.table .table-striped}
| Field | Description |
| ----- | ----------- |
| `entity` | Serialized value that is set in `response.entity` during script execution. XL Release handles the serialization of standard JSON data types: Number, String, Boolean, Date, Array, Dictionary, and `udm.ConfigurationItem`. |
| `stdout` | Text that was sent to standard output during the execution. |
| `stderr` | Text was sent to standard error during the execution. |
| `exception` | Textual representation of exception that was thrown during script execution. |

### HTTP status code

You can explicitly set an HTTP status code via `response.statusCode`. If it is not set explicitly and the script executes without error, the client will receive `200`. In the case of unhandled exceptions, the client will receive `500`.
