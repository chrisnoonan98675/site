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

Depending on the version of XL TestView the configuration is different.

Configuration for older versions:

* [XL TestView 1.2.x](/xl-testview/1.2.x/configuration.html)

## Configuring JVM settings

Configuration can be done by:

* Setting the environment variables `XLT_SERVER_OPTS` and/or `XLT_SERVER_MEM_OPTS` before `server.sh` or `server.cmd` is executed.
* On unix `server.sh` will also source the scripts `/etc/sysconfig/xl-testview` and `/etc/default/xl-testview`, so you can export the `XLT_SERVER_*` variables from there. This has the advantage that settings will be persisted across reinstalls/upgrades.
* Modifying `server.sh` or `server.cmd` depending on your OS.

Configuration is split between memory settings for the JVM in `XLT_SERVER_MEM_OPTS` and other settings in `XLT_SERVER_OPTS`. `XLT_SERVER_MEM_OPTS` has a default of `-Xmx1024m -XX:MaxPermSize=256m` refer to your Java Runtime Documentation for details on these settings.

For example increasing the amount of memory the server has available can be done by running server.sh like (assuming you have a shell/cmd prompt at `<XLTESTVIEW_HOME>/bin`):

    XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m" ./server.sh

On windows this would be something like:

    set XLT_SERVER_MEM_OPTS=-Xmx4096m -XX:MaxPermSize=256m
    server.cmd

An example for a `/etc/sysconfig/xl-testview` or `/etc/default/xl-testview` file:

    export XLT_SERVER_MEM_OPTS="-Xmx4096m -XX:MaxPermSize=256m"

## Configuring XL TestView 1.3.x

Starting with XL TestView 1.3.x configuration is now done via a configuration file: `<XLTESTVIEW_HOME>/conf/xl-testview.conf`. The format of this file is HOCON, refer to [https://github.com/typesafehub/config/blob/master/HOCON.md](https://github.com/typesafehub/config/blob/master/HOCON.md) for syntax.

### LDAP

LDAP configuration is described in more detail in: [Configure LDAP authentication for XL TestView](/xl-testview/how-to/configure-ldap.html).

### Example configuration file

This is the default configuration file.

{% highlight sh %}
xlt {
  # Configuration for authentication of users in XL TestView
  authentication {
    # Method used to authenticate users.
    # Possible values are "file", "ldap" and "none"
    method = "file"

    # Properties for connecting to an ldap server.
    # Required if xlt.authentication.method = "ldap". If secure ldap is desired
    # with a self signed certificate the properties in xlt.truststore are
    # also required.
    ldap {
      # Complete url to the ldap server including port number.
      # For example: ldaps://localhost:636
      url = ""

      # NOTE: Either use the user-dn property or
      # user-search-base and user-search-filter.

      # The placeholder {0} is used to substitute the common name of the user.
      # For example, cn={0},ou=people,dc=xebialabs,dc=com
      user-dn = ""

      # Base DN to search for users
      user-search-base = ""

      # user search filter
      user-search-filter = ""
    }
  }

  # Configuration for the embedded Elastic Search, used to store test events.
  elasticsearch {
    # Relative path where Elasticsearch saves its data.
    data = "data"

    # Enable the Elasticsearch HTTP server. This allows access to the
    # Elasticsearch database over the network. No access control is set up
    # on Elasticsearch.
    # DO NOT USE ON UNTRUSTED NETWORKS.
    http = false

    # The ports that Elastic Search will attempt to bind to. It will use 9200
    # unless it is occupied, then it will use 9201, etc.
    port-range = "9200-9300"
  }

  server {
    # IP address of the interface the server should listen on. The value
    # '0.0.0.0' means that XL TestView will listen on all network interfaces.
    host = "0.0.0.0"
    # Port to run XL TestView on. On Unix, the port must be greater than 1024
    # unless the server is running as root (which is not recommended).
    port = 6516
    # Context root of the XL TestView service. Must start with a
    # forward slash (/).
    root = ""
    # Time to allow browser sessions to live. See
    # https://github.com/typesafehub/config/blob/master/HOCON.md#duration-format
    # for the format
    session-timeout = 30 minutes
    # Name for the session cookie. Should be unique among all applications on
    # the server to prevent affecting each others sessions.
    session-cookie-name = "XLT_SESSION_ID"
    # Send only cookies if using a secure connection. Set this to true if you
    # are running your server on https
    secure-cookie = false
  }

  # XL TestView includes a rich set of demonstration data to show all features.
  # Set this property to false to start with a clean database. This setting
  # only works on the first startup, to prevent overwriting existing data.
  load-demo-data = true

  truststore {
    # Absolute path to the trust store. To prevent errors, this must be empty
    # or a valid java key store file.
    location = ""
    # Password to the trust store.
    password = ""
  }

  # Configure the JVM Thread pool
  threadpool {
    min = 10
    max = 20
  }
}
{% endhighlight %}
