---
title: Configure XL TestView
categories:
- xl-testview
subject:
- Configuration
tags:
- system administration
- installation
- configuration
since:
- XL TestView 1.3.0
---

In XL TestView 1.3.0 and later, configuration is done in the `<XLTESTVIEW_HOME>/conf/xl-testview.conf` file. This file uses the [Human-Optimized Config Object Notation (HOCON) format](https://github.com/typesafehub/config/blob/master/HOCON.md).

For information about configuring an older version of XL TestView, refer to [Configure XL TestView 1.2.x](/xl-testview/1.2.x/archive/configuration.html).

The following table lists the possible configuration options.

{:.table .table-striped}
| Property | Default | Description |
| -------- | ------- | ----------- |
| `xlt.authentication.ldap.url` | `""` | Complete url to the LDAP server including port number. For example: `ldaps://localhost:636`. |
| `xlt.authentication.ldap.user-dn` | `""` | The placeholder `{0}` is used to substitute the common name of the user. For example, `cn={0},ou=people,dc=xebialabs,dc=com`. <br/>**Note**: Either use the `user-dn` property or `user-search-base` and `user-search-filter`. |
| `xlt.authentication.ldap.user-search-base` | `""` | Base DN to search for users. <br/>**Note**: Either use the `user-dn` property or `user-search-base` and `user-search-filter`. |
| `xlt.authentication.ldap.user-search-filter` | `""` | User search filter. <br/>**Note**: Either use the `user-dn` property or `user-search-base` and `user-search-filter`. |
| `xlt.authentication.method` | `file` | Method used to authenticate users. Possible values are `file`, `ldap` and `none`. |
| `xlt.data` | `data` | Allows relocating the location where Elasticsearch and JCR create their databases. This can be convenient for backup and or upgrade scenarios. <br/>This is supported in XL TestView 1.4.0 and later. |
| `xlt.elasticsearch.data` | `data` | Relative path where Elasticsearch saves its data. <br/>This is deprecated and ignored as of XL TestView 1.4.0. Use the `xlt.data` property instead. |
| `xlt.elasticsearch.http` | `false` | Enable the Elasticsearch HTTP server. This allows access to the Elasticsearch database over the network. No access control is set up on Elasticsearch. <br/>**Important**: Do not enable this option on production instances. |
| `xlt.elasticsearch.port-range` | `9200`-`9300` | Restrict the ports that the Elasticsearch HTTP server uses. When a port in the range is unavailable, another port in the range is tried. |
| `xlt.load-demo-data` | `true` | XL TestView includes a rich set of demonstration data to show all features. Set this property to `false` to start with a clean database. This feature only works on the first startup, to prevent overwriting existing data. |
| `xlt.server.host` | `0.0.0.0` | IP address of the interface the server should listen on. The value `0.0.0.0` means that XL TestView will listen on all network interfaces. |
| `xlt.server.port` | `6516` | Port to run XL TestView on. On Unix, the port must be greater than 1024 unless the server is running as root (which is not recommended). |
| `xlt.server.root` |`""` | Context root of the XL TestView service. Must start with a forward slash (`/`). |
| `xlt.server.secure-cookie` | `false` | Send only cookies if using a secure connection. Set this to `true` if you are running your server on HTTPS. |
| `xlt.server.session-cookie-name` | `XLT_SESSION_ID` | Name for the session cookie. Should be unique among all applications on a server or reverse proxy to prevent affecting each others sessions. |
| `xlt.server.session-timeout` | `30 minutes` | Time to allow browser sessions to live. See [the HOCON format](https://github.com/typesafehub/config/blob/master/HOCON.md#duration-format) for more information. |
| `xlt.server.threadpool.max` | `20` | Configure the maximum number of worker threads for handling web requests. |
| `xlt.server.threadpool.min` | `10` | Configure the minimum number of worker threads for handling web requests. |
| `xlt.truststore.location` | `""` | Absolute path to the trust store. To prevent errors, this must be empty or a valid Java keystore file. |

## Configure LDAP

For information about configuring `xl-testview.conf` settings related to LDAP, refer to [Configure LDAP authentication for XL TestView](/xl-testview/how-to/configure-ldap.html).

## Configure advanced JVM settings

You can configure the Java virtual machine (JVM) as follows:

* Set the `XLT_SERVER_OPTS` and/or `XLT_SERVER_MEM_OPTS` environment variables before `server.sh` or `server.cmd` is executed.

* On Unix, `server.sh` will also source the `/etc/sysconfig/xl-testview` and `/etc/default/xl-testview` scripts, so you can export `XLT_SERVER_*` variables from there. This has the advantage that settings will be persisted across re-installations and upgrades.

* Modifying `server.sh` or `server.cmd`, depending on your operating system.

Configuration is split between memory settings for the JVM in `XLT_SERVER_MEM_OPTS` and other settings in `XLT_SERVER_OPTS`.

`XLT_SERVER_MEM_OPTS` has a default of `-Xmx1024m -XX:MaxPermSize=256m`; refer to your Java Runtime Documentation for information about these settings. XL TestView 1.4.0 and later uses Java 8, which means that the `MaxPermSize` setting is no longer required and will result in a warning when starting the server.

### Memory configuration examples

For example, increasing the amount of memory the server has available can be done by running `server.sh` as follows (assuming you have a shell/cmd prompt at `<XLTESTVIEW_HOME>/bin`):

    XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m" ./server.sh

On Microsoft Windows, you can do this as follows:

    set XLT_SERVER_MEM_OPTS=-Xmx4096m -XX:MaxPermSize=256m
    server.cmd

To make the memory change persistent you can add the setting to the `/etc/sysconfig/xl-testview` or `/etc/default/xl-testview` file. This would look like:

    export XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m"
