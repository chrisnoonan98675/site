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

For information about configuring an older version of XL TestView, refer to [Configure XL TestView 1.2.x](/xl-testview/1.2.x/configuration.html).

## Sample configuration file

This is an example of the `<XLTESTVIEW_HOME>/conf/xl-testview.conf` file:

{% highlight sh %}
# The format of this file is HOCON. Please read
# https://github.com/typesafehub/config/blob/master/HOCON.md for details about
# this format System properties on the JVM will override these properties.
# See https://github.com/typesafehub/config for details.

xlt {

  # Configuration for authentication of users in XL TestView
  authentication {
    # Method used to authenticate users. Possible values are "file", "ldap" and "none"
    method = "file"

    # Properties for connecting to an ldap server. Required if
    # xlt.authentication.method = "ldap". If secure ldap is desired with a self
    # signed certificate the properties in xlt.truststore are also required.
    # NOTE: This does not cover Active Directory authentication.
    ldap {
      # Complete url to the ldap server including port number.
      # For example: ldaps://localhost:636
      url = ""

      # NOTE: Either use the user-dn property or user-search-base + user-search-filter.

      # The placeholder {0} is used to substitute the common name of the user.
      # For example, cn={0},ou=people,dc=xebialabs,dc=com
      user-dn = ""

      # Base DN to search for users
      user-search-base = ""

      # user search filter
      user-search-filter = ""
    }
  }

  # Configuration for Elastic Search, used to store test events.
  elasticsearch {
    # Relative path where Elasticsearch saves its data.
    data = "data"

    # Enable the Elasticsearch HTTP server. This allows access to the
    # Elasticsearch database over the network. No access control is set up on
    # Elasticsearch.
    # DO NOT USE ON UNTRUSTED NETWORKS.
    http = false

    # The ports that Elastic Search will attempt to bind to. It will use
    # 9200 unless it is occupied, then it will use 9201, etc.
    port-range = "9200-9300"
  }

  server {
    # IP address of the interface the server should listen on.
    # The value '0.0.0.0' means that XL TestView will listen on all network interfaces.
    host = "0.0.0.0"
    # Port to run XL TestView on. On Unix, the port must be greater than 1024
    # unless the server is running as root (which is not recommended).
    port = 6516
    # Context root of the XL TestView service. Must start with a forward slash (/).
    root = ""
    # Time to allow browser sessions to live. See
    # https://github.com/typesafehub/config/blob/master/HOCON.md#duration-format for the format
    session-timeout = 30 minutes
    # Name for the session cookie. Should be unique among all applications on
    # the server to prevent affecting each others sessions.
    session-cookie-name = "XLT_SESSION_ID"
    # Send only cookies if using a secure connection. Set this to true if you
    # are running your server on https
    secure-cookie = false

    # Configure the HTTP server thread pool
    threadpool {
      min = 10
      max = 20
    }
  }

  # XL TestView includes a rich set of demonstration data to show all features.
  # Set this property to false to start with a clean database. This feature
  # only works on the first startup, to prevent overwriting existing data.
  load-demo-data = true

  truststore {
    # Absolute path to the trust store. To prevent errors, this must be empty
    # or a valid java key store file.
    location = ""
    # Password to the trust store.
    password = ""
  }
}
{% endhighlight %}

## Configure LDAP

For information about configuring `xl-testview.conf` settings related to LDAP, refer to [Configure LDAP authentication for XL TestView](/xl-testview/how-to/configure-ldap.html).

## Configure advanced JVM settings

You can configure the Java virtual machine (JVM) as follows:

* Set the `XLT_SERVER_OPTS` and/or `XLT_SERVER_MEM_OPTS` environment variables before `server.sh` or `server.cmd` is executed.

* On Unix, `server.sh` will also source the `/etc/sysconfig/xl-testview` and `/etc/default/xl-testview` scripts, so you can export `XLT_SERVER_*` variables from there. This has the advantage that settings will be persisted across re-installations and upgrades.

* Modifying `server.sh` or `server.cmd`, depending on your operating system.

Configuration is split between memory settings for the JVM in `XLT_SERVER_MEM_OPTS` and other settings in `XLT_SERVER_OPTS`.

`XLT_SERVER_MEM_OPTS` has a default of `-Xmx1024m -XX:MaxPermSize=256m`; refer to your Java Runtime Documentation for details on these settings.

### Memory configuration examples

For example, increasing the amount of memory the server has available can be done by running `server.sh` as follows (assuming you have a shell/cmd prompt at `<XLTESTVIEW_HOME>/bin`):

    XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m" ./server.sh

On Windows, you can do this as follows:

    set XLT_SERVER_MEM_OPTS=-Xmx4096m -XX:MaxPermSize=256m
    server.cmd

This is an example for a `/etc/sysconfig/xl-testview` or `/etc/default/xl-testview` file:

    export XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m"
