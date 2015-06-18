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
---

During startup, you can set several system properties on XL TestView.

{:.table .table-striped}
| Property | Default | Description |
| -------- | ------- | ----------- |
| `xlt.load.demo.data` | `true` | XL TestView includes a rich set of demonstration data to show all features. Set this property to `false` to start with a clean database. This feature only works on the first startup, to prevent overwriting existing data. |
| `xlt.server.host` | 0.0.0.0 |  Host where XL TestView runs. |
| `xlt.server.port` | `6516` | Port to run XL TestView on. On Unix, the port must be greater than 1024 unless the server is running as root (which is not recommended). |
| `xlt.server.root` |`""` | Context root of the XL TestView service. Must start with a forward slash (`/`). |
| `xlt.server.session.timeout` | 30 | Time to allow browser sessions to live, in minutes. |
| `xlt.elasticsearch.data` | `data` | Relative path where Elasticsearch saves its data. |
| `xlt.elasticsearch.http` | false | Enable the Elasticsearch HTTP server. This allows access to the Elasticsearch database over the network. No access control is set up on Elasticsearch. **Do not enable this option on production instances.** |
| `xlt.elasticsearch.port.range` | 9200-9299 | Restrict which ports the Elasticsearch HTTP server uses. When a port in the range is unavailable, another port in the range is tried. |

The properties can be configured by placing them in the environment variable `XLT_SERVER_OPTS`, prefixed with `-D`. For example, to disable creation of the demo data, set `XLT_SERVER_OPTS` to `-Dxlt.load.demo.data=false`.

For more information about configuration, refer to [Install XL TestView](/xl-testview/how-to/install.html).
