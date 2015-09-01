---
title: XL TestView boot properties
categories:
- xl-testview
subject:
- System administration
tags:
- system administration
- properties
- installation
- configuration
since:
- 1.3.0
---

You can set XL TestView system properties:

* In the `<XLTESTVIEW_HOME>/conf/xl-testview.conf` file
* In the `XLT_SERVER_OPTS` environment variable, prefixed with `-D`

    For example, to disable creation of demo data, set `XLT_SERVER_OPTS` to `-Dxlt.load-demo-data=false`.

The following properties are available:

{:.table .table-striped}
| Property | Default | Description |
| -------- | ------- | ----------- |
| `xlt.load-demo-data` | `true` | XL TestView includes a rich set of demonstration data to show its features. Set this property to `false` to start with a clean database. This feature only works on the first startup, to prevent overwriting existing data. |
| `xlt.server.host` | `0.0.0.0` | IP address of the interface the server should listen on. The value `0.0.0.0` means that XL TestView will listen on all network interfaces. |
| `xlt.server.port` | `6516` | Port to run XL TestView on. On Unix, the port must be greater than 1024 unless the server is running as root (which is not recommended). |
| `xlt.server.root` |`""` | Context root of the XL TestView service. Must start with a forward slash (`/`). |
| `xlt.server.session-timeout` | 30 | Time to allow browser sessions to live, in minutes. Users are logged out after this many minutes of inactivity. |
| `xlt.server.session-cookie-name` | `XLT_SESSION_ID` | Name that is used for the HTTP session cookie. |
| `xlt.server.secure-cookie` | `false` | Send only cookies if using a secure connection. Set this to `true` if you are running your server on HTTPS or if the XL TestView server is behind a proxy that provides SSL termination. |
| `xlt.elasticsearch.data` | `data` | Relative path where Elasticsearch saves its data. |
| `xlt.elasticsearch.http` | `false` | Enable the Elasticsearch HTTP server. This allows access to the Elasticsearch database over the network. No access control is set up on Elasticsearch. *Do not enable this option on production instances.* |
| `xlt.elasticsearch.port.range` | `9200-9300` | Restrict which ports the Elasticsearch HTTP server uses. When a port in the range is unavailable, another port in the range is tried. |

For more information about configuration, refer to [Install XL TestView](/xl-testview/how-to/install.html).
