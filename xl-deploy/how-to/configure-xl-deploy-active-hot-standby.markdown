---
title: Configure active/hot-standby mode
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- installation
- failover
- database
- active/hot-standby
- clustering
since:
- XL Deploy 7.1.0
---

As of XL Deploy 7.1.0, you can configure XL Deploy in a clustered active/hot-standby mode. Running XL Deploy in this mode ensures that you have a Highly Available (HA) XL Deploy. This topic describes the procedure to enable active/hot-standby mode.

![Active/hot-standby configuration](images/XL-Deploy-active-hot-standby-configuration.png)

**Tip:** If you do not want to use active/hot-standby mode, you can set up failover handling as described in [Configure failover for XL Deploy](/xl-deploy/how-to/configure-failover.html).

## Requirements

Using XL Deploy in active/hot-standby mode requires the following:

* You must meet the [standard system requirements for XL Deploy](/xl-deploy/concept/requirements-for-installing-xl-deploy.html).

    **Important:** Active/hot-standby is not supported on Microsoft Windows.

* The XL Deploy repository must be stored in an external database, using the instructions in this topic. If you are using XL Deploy's default configuration (which stores the repository in an embedded Derby database), you must migrate all of your data to an external database before you can start to use active/hot-standby.

* You must use a load balancer that supports hot-standby. This topic describes how to set up the [HAProxy](http://www.haproxy.org/) load balancer.

* You must have a shared filesystem (such as NFS) that both the active and standby XL Deploy nodes can reach. This will be used to store binary data (artifacts).

* The time on both XL Deploy nodes must be synchronized through an NTP server.

## Limitation on HTTP session sharing and resiliency

In active/hot-standby mode, there is always at most one "active" XL Deploy node. The nodes use a health REST endpoint (`/ha/health`) to tell the load balancer which node is the active one. The load balancer should always route users to the active node; calling a standby node directly will result in incorrect behavior.

However, XL Deploy does not share HTTP sessions among nodes. If the active XL Deploy node becomes unavailable:

* All users will effectively be logged out and will lose any work that was not yet persisted to the database.
* Any deployment or control tasks that were running on the previously active node must be manually recovered. Tasks that were previously running will not automatically be visible from the newly active node because this may lead to data corruption in split-brain scenarios.

## Limitation on satellite usage

Use of the [satellite module](/xl-deploy/concept/getting-started-with-the-satellite-module.html) with an active/hot-standby installation of XL Deploy is not supported.

## Active/Hot-standby setup procedure

The initial active/hot-standby setup is:

* A load balancer
* A database server
* Two XL Deploy servers

To set up an active/hot-standby cluster, you must do some manual configuration before starting XL Deploy.

### Step 1 Configure external databases

The following external databases are recommended:

* MySQL
* PostgreSQL
* Oracle 11g or 12c

The following set of SQL privileges are required (where applicable):

* REFERENCES
* INDEX
* CREATE
* DROP
* SELECT, INSERT, UPDATE, DELETE

#### Provide JDBC drivers

Place the JAR file containing the JDBC driver of the selected database in the `XL_DEPLOY_SERVER_HOME/lib` directory. To download the JDBC database drivers:

{:.table .table-striped}
| Database   | JDBC drivers | Notes   |
| ---------- | ------------ | ------- |
| MySQL      | [Connector\J 5.1.30 driver download](http://dev.mysql.com/downloads/connector/j/) | None. |
| Oracle     | [JDBC driver downloads](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html) | For Oracle 12c, use the 12.1.0.1 driver (`ojdbc7.jar`). It is recommended that you only use the thin drivers; refer to the [Oracle JDBC driver FAQ](http://www.oracle.com/technetwork/topics/jdbc-faq-090281.html) for more information. |
| PostgreSQL | [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)| None. |

#### Configure the repository database

The repository database must be shared among all nodes when active/hot-standby is enabled. Ensure that every node has access to the shared repository database.

To configure the repository database, first add the `xl.repository.configuration` property to the `repository.conf` configuration file, which uses the [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format. This property identifies the predefined repository configuration that you want to use. Supported values are:

{:.table .table-striped}
| Parameter             | Description                                                                             |
| --------------------- | --------------------------------------------------------------------------------------- |
| `default`               | Default (single instance) configuration that uses an embedded Apache Derby database.  |
| `mysql-standalone`      | Single instance configuration that uses a MySQL database.                             |
| `mysql-cluster`         | Cluster-ready configuration that uses a MySQL database.                               |
| `oracle-standalone`     | Single instance configuration that uses an Oracle database.                           |
| `oracle-cluster`        | Cluster-ready configuration that uses an Oracle database.                             |
| `postgresql-standalone` | Single instance configuration that uses a PostgreSQL database.                        |
| `postgresql-cluster`    | Cluster-ready configuration that uses a PostgreSQL database.                          |

Next, add the following parameters to the `xl.repository.persistence` section of `repository.conf`:

{:.table .table-striped}
| Parameter     | Description |
| ---------     | ----------- |
| `jdbcUrl`     | JDBC URL that describes the database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `username`    | User name to use when logging into the database. |
| `password`    | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |
| `maxPoolSize` | Database connection pool size; suggested value is 20. |

Next, set `xl.repository.jackrabbit.artifacts.location` to a shared filesystem (such as NFS) location that all nodes can access. This is required for storage of binary data (artifacts).

Finally, set `xl.repository.cluster.nodeId` to a unique value on each node. The value of `xl.repository.cluster.nodeID` is used to distinguish entries in the database for each running `jackrabbit` instance.

#### Sample database configuration

This is an example of the `xl.repository` configuration for a stand-alone database:

    xl {
      repository {
        # placeholder for repository configuration overrides
        configuration = XLD_CONFIGURATION
        jackrabbit.artifacts.location = XLD_SHARED_LOCATION
        cluster.nodeId = XLD_HOSTNAME

        persistence {
          jdbcUrl = XLD_DB_REPOSITORY_URL
          username = XLD_DB_USER
          password = XLD_DB_PASS
          maxPoolSize = 20
        }
      }
    }

### Step 2 Set up the cluster

Additional active/hot-standby configuration settings must be provided in the `XL_DEPLOY_SERVER_HOME/conf/system.conf` file, which also uses the [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format. In this file on each node:

1. Enable clustering by setting `cluster.mode` to `hot-standby`.
1. Provide database access for registering active nodes to a membership table by adding a `cluster.membership` configuration containing the following keys:

{:.table .table-striped}
| Parameter        | Description |
| ---------        | ----------- |
| `jdbc.url`       | JDBC URL that describes the database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `jdbc.username`  | User name to use when logging into the database. |
| `jdbc.password`  | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |

An minimal example section looks like this:

    cluster {
      mode = hot-standby

      membership {
        jdbc {
          url = "jdbc:mysql://db/xldrepo?useSSL=false"
          username = xld
          password = t0pS3creT
        }
      }
    }

Refer to [Optional cluster settings](#optional-cluster-settings) for additional settings that are available for `system.conf`.

### Step 3 Set up the first node

At a command prompt, run the following server setup command and follow the on-screen instructions:

    ./bin/run.sh -setup

### Step 4 Prepare each node in the cluster

1. Compress the distribution that you created in [Step 2 Set up the cluster](#step-2-set-up-the-cluster) in a ZIP file.
1. Copy the ZIP file to all other nodes and unzip each one.
1. On each node, edit the `xl.repository.cluster.nodeId` setting of the `XL_DEPLOY_SERVER_HOME/conf/repository.conf` file. Update the values for the specific node.

**Note:** You do not need to run the server setup command on each node.

### Step 5 Set up the load balancer

To use active/hot-standby, you must use a load balancer in front of the XL Deploy servers. The load balancer must check the `/ha/health` endpoint with a `GET` request to verify that the node is up. This endpoint will return:

* A `503` HTTP status code if this node is running as standby (non-active) node.
* A `204` HTTP status code if this is the active node. All user traffic should be sent to this node.

**Note:** Performing a simple TCP check or `GET` operation on `/` is not sufficient, as that will only determine whether the node is running; it will not indicate whether the node is in standby mode.

For example, for HAProxy, you can add the following configuration:

    backend default_service
      option httpchk get /deployit/ha/health HTTP/1.0
      server docker_xld-node_1 docker_xld-node_1:4516 check inter 2000 rise 2 fall 3

### Step 6 Start the nodes

Start XL Deploy on each node, starting with the first node that you configured. Ensure that each node is fully up and running before starting the next one.

## Sample `system.conf` configuration

This is a sample `system.conf` configuration for one node that uses a MySQL repository database.

    task {
      recovery-dir = work
      step {
        retry-delay = 5 seconds
        execution-threads = 32
      }
    }

    satellite {

      enabled = no

      port = 8180
      #hostname = "" #Host name or ip address to bind to

      ssl {
        enabled = yes
        port = 8280

        key-store = "keystore"
        key-password = "changeme"
        key-store-password = "changeme"

        trust-store = "truststore"
        trust-store-password = "changeme"

        # Protocol to use for SSL encryption, choose from:
        # Java 7:
        #   'TLSv1.1', 'TLSv1.2'
        protocol = "TLSv1.2"

        # Example: ["TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"]
        # You need to install the JCE Unlimited Strength Jurisdiction Policy
        # Files to use AES 256.
        # More info here:
        # http://docs.oracle.com/javase/7/docs/technotes/guides/security/SunProviders.html#SunJCEP
        enabled-algorithms = ["TLS_RSA_WITH_AES_128_CBC_SHA"]

        # There are three options, in increasing order of security:
        # "" or SecureRandom => (default)
        # "SHA1PRNG" => Can be slow because of blocking issues on Linux
        # "AES128CounterSecureRNG" => fastest startup and based on AES encryption
        # "AES256CounterSecureRNG"
        # The following use one of 3 possible seed sources, depending on
        # availability: /dev/random, random.org and SecureRandom (provided by Java)
        # "AES128CounterInetRNG"
        # "AES256CounterInetRNG" (Install JCE Unlimited Strength Jurisdiction
        # Policy Files first)
        # Setting a value here may require you to supply the appropriate cipher
        # suite (see enabled-algorithms section above)
        random-number-generator = "AES128CounterSecureRNG"
      }

      timeout {
        ping = "5 seconds"
        upload.idle = "30 seconds"
        streaming = "10 seconds"
      }

      streaming {
        # Maximum amount of concurrent uploads per task
        max-uploads = 10
      }

    }

    cluster {
      mode = hot-standby

      membership {
        jdbc {
          url = "jdbc:mysql://db/xldrepo?useSSL=false"
          username = xld
          password = t0pS3creT
        }
      }
    }


#### Optional cluster settings

You can optionally configure the following additional settings in the `cluster` section of `system.conf`:

{:.table .table-striped}
| Parameter             | Description                                                | Default value          |
| --------------------- | ----------------------------------------------------------------------------------- |
| `name`                  | The hot-standby management Akka cluster name.               | `xld-hotstandby-cluster` |
| `membership.jdbc.driver` | The database driver class name. For example, `oracle.jdbc.OracleDriver`. | Determined from the database URL |
| `membership.heartbeat`  | How often a node should write liveness information into the database. | 10 seconds |
| `membership.ttl`        | How long liveness information remains valid.                       | 60 seconds |
| `akka.cluster.auto-down-unreachable-after` | The amount of time that passes before the Akka cluster determines that a node has gone down. | 15 seconds |

The `heartbeat` and `ttl` settings are relevant for cluster bootstrapping. A newly starting node will look in the database to find live nodes and try to join the cluster with the given `name` running on those nodes.

The `auto-down-unreachable-after` setting determines how fast the cluster decides that a node has gone down and, in case of the active node, whether a standby node must be activated. Changing this setting to a lower value means that hot-standby takeover takes place faster; but in the case of transient network issues, it may cause a takeover while the original node is still alive. Using a longer value does the opposite; the cluster is more resilient against transient network failures, but takeover takes more time when a real crash occurs.

**Note:** After the first run, passwords in the configuration file will be encrypted and replaced with Base64-encoded values.

## Sample `haproxy.cfg` configuration

This is a sample `haproxy.cfg` configuration. Ensure that your configuration is hardened before using it in a production environment.

    global
      log 127.0.0.1 local0
      log 127.0.0.1 local1 notice
      log-send-hostname
      maxconn 4096
      pidfile /var/run/haproxy.pid
      user haproxy
      group haproxy
      daemon
      stats socket /var/run/haproxy.stats level admin
      ssl-default-bind-options no-sslv3
      ssl-default-bind-ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES128-SHA:DHE-RSA-AES128-SHA:ECDHE-ECDSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:AES128-GCM-SHA256:AES128-SHA256:AES128-SHA:AES256-GCM-SHA384:AES256-SHA256:AES256-SHA:DHE-DSS-AES128-SHA
    defaults
      balance roundrobin
      log global
      mode http
      option redispatch
      option httplog
      option dontlognull
      option forwardfor
      timeout connect 5000
      timeout client 50000
      timeout server 50000
    listen stats
      bind :1936
      mode http
      stats enable
      timeout connect 10s
      timeout client 1m
      timeout server 1m
      stats hide-version
      stats realm Haproxy\ Statistics
      stats uri /
      stats auth stats:stats
    frontend default_port_80
      bind :80
      reqadd X-Forwarded-Proto:\ http
      maxconn 4096
      default_backend default_service
    backend default_service
      option httpchk head /deployit/ha/health HTTP/1.0
      server docker_xlr-node_1 docker_xlr-node_1:5516 check inter 2000 rise 2 fall 3
      server docker_xlr-seed_1 docker_xlr-seed_1:5516 check inter 2000 rise 2 fall 3
