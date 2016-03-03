---
title: Configure XL TestView 1.2.x
---

**Note:** This information is deprecated. For XL TestView 1.3.0 and later, refer to [Configure XL TestView](/xl-testview/how-to/configure-xl-testview.html).

Configuration can be done by:

* Setting the environment variables `XLT_SERVER_OPTS` and/or `XLT_SERVER_MEM_OPTS` before `server.sh` or `server.cmd` is executed.
* On unix `server.sh` will also source the scripts `/etc/sysconfig/xl-testview` and `/etc/default/xl-testview`, so you can export the `XLT_SERVER_*` variables from there. This has the advantage that settings will be persisted across reinstalls/upgrades.
* Modifying `server.sh` or `server.cmd` depending on your OS.

Configuration is split between memory settings for the JVM in `XLT_SERVER_MEM_OPTS` and other settings in `XLT_SERVER_OPTS`. `XLT_SERVER_MEM_OPTS` has a default of `-Xmx1024m -XX:MaxPermSize=256m` refer to your Java Runtime Documentation for details on these settings.

**Important:** The `xlt.*` system properties must be prefixed with `-D` to ensure they will be seen by the server process.

For example configuring the server port can be done by running server.sh like (assuming you have a shell/cmd prompt at `<XLTESTVIEW_HOME>/bin`):

    XLT_SERVER_OPTS="-Dxlt.server.port=8080" ./server.sh

On windows this would be something like:

    set XLT_SERVER_OPTS=-Dxlt.server.port=8080
    server.cmd

An example for a `/etc/sysconfig/xl-testview` or `/etc/default/xl-testview` file:

    export XLT_SERVER_OPTS="-Dxlt.server.port=8080"

## XL TestView 1.2.x Configuration properties

During startup, you can set several system properties on XL TestView.

{:.table .table-striped}
| Property | Default | Description |
| -------- | ------- | ----------- |
| `xlt.load.demo.data` | `true` | XL TestView includes a rich set of demonstration data to show all features. Set this property to `false` to start with a clean database. This feature only works on the first startup, to prevent overwriting existing data. |
| `xlt.server.host` | `0.0.0.0` | IP address of the interface the server should listen on. The value '0.0.0.0' means that XL TestView will listen on all network interfaces. |
| `xlt.server.port` | `6516` | Port to run XL TestView on. On Unix, the port must be greater than 1024 unless the server is running as root (which is not recommended). |
| `xlt.server.root` |`""` | Context root of the XL TestView service. Must start with a forward slash (`/`). |
| `xlt.server.session.timeout` | 30 | Time to allow browser sessions to live, in minutes. |
| `xlt.elasticsearch.data` | `data` | Relative path where Elasticsearch saves its data. |
| `xlt.elasticsearch.http` | false | Enable the Elasticsearch HTTP server. This allows access to the Elasticsearch database over the network. No access control is set up on Elasticsearch. **Do not enable this option on production instances.** |
| `xlt.elasticsearch.port.range` | 9200-9299 | Restrict which ports the Elasticsearch HTTP server uses. When a port in the range is unavailable, another port in the range is tried. |

The properties can be configured by placing them in the environment variable `XLT_SERVER_OPTS`, prefixed with `-D`. For example, to disable creation of the demo data, set `XLT_SERVER_OPTS` to `-Dxlt.load.demo.data=false`.

For more information about installing XL TestView please refer to [Install XL TestView](/xl-testview/how-to/install.html).
