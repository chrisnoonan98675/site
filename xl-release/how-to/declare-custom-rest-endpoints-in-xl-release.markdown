---
title: Declare custom REST endpoints in XL Release
categories:
- xl-release
subject:
- XL Release API
tags:
- api
- script
- jython
- plugin
---

You can extend XL Release by creating new endpoints backed by Jython scripts. This feature can be useful for example to integrate with other systems.

To declare new endpoints you need to put a file `xl-rest-endpoints.xml` in the classpath of your XL Release server: either in a `jar` file of your custom plugin or in `SERVER_INSTALLATION/ext` folder:

    <?xml version="1.0" encoding="UTF-8"?>
    <endpoints xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns="http://www.xebialabs.com/deployit/endpoints"
               xsi:schemaLocation="http://www.xebialabs.com/deployit/endpoints endpoints.xsd">
        <endpoint path="/test/demo" method="GET" script="demo.py" />
        <!-- ... more endpoints can be declared in the same way ... -->
    </endpoints>


**Note:** After processing this file, XL Release creates a new REST endpoint, which is accessible via `http(s)://{xl-release-hostname}:{port}/{[context-path]}/api/extension/test/demo`.

Every endpoint should be represented with `<endpoint>` element which can contain following attributes:

{:.table .table-striped}
| Attribute | Required | Description |
| --------- | -------- | ----------- |
| `path` | Yes | Relative REST path which be exposed to run the Jython script |
| `method` | No | HTTP method type (GET, POST, DELETE, PUT); default value is GET |
| `script` | Yes | Relative path to the Jython script in the classpath |

## Jython scripts

Jython scripts should implement the logic of REST endpoints. Typically, every script will perform queries or actions in XL Release and produce a response.

### Objects available in the context

In a script you have access to the following objects:

* request: <a href="/jython-docs/#!/xl-deploy/4.5.x/service/com.xebialabs.xlplatform.endpoints.JythonRequest">JythonRequest</a>
* response: <a href="https://docs.xebialabs.com/jython-docs/#!/xl-deploy/4.5.x/service/com.xebialabs.xlplatform.endpoints.JythonResponse">JythonResponse</a>

and XL Release services. Please check <a href="/jython-docs/#!/xl-release/4.6.x/">XL Release Jython API Documentation</a> for the complete information.

### HTTP response

The server returns a HTTP response of type `application/json` containing a JSON object with the following fields:

{:.table .table-striped}
| Field | Description |
| ----- | ----------- |
| `entity` | Serialized value that is set in <code>response.entity</code> during script execution. XL Release takes care about serialization of standard JSON data types: Number, String, Boolean, Date, Array, Dictionary, and udm.ConfigurationItem. |
| `stdout` | Text that was sent to standard output during the execution. |
| `stderr` | Text was sent to standard error during the execution. |
| `exception` | Textual representation of exception that was thrown during script execution. |

### HTTP status code

You can explicitly set an HTTP status code via `response.statusCode`. If it is not set explicitly and the script executes smoothly, the client will receive `200`. In the case of unhandled exceptions, the client will receive `500`.
