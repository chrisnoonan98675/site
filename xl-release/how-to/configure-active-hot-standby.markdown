---
title: Configure active/hot-standby mode
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- failover
- database
- active/hot-standby
- clustering
weight: 496
since:
- XL Release 6.0.0
---

As of XL Release 6.0.0, you can configure XL Release in a clustered active/hot-standby mode. Running XL Release in this mode ensures that you have a Highly Available (HA) XL Release. This topic describes the procedure to enable active/hot-standby mode.

![Active/hot-standby configuration](../images/diagram-active-hot-standby.png)

**Tip:** If you do not want to use active/hot-standby mode, you can set up failover handling as described n [Configure failover for XL Release](/xl-release/how-to/configure-failover.html).

## Requirements

Using XL Release in active/hot-standby mode requires the following:

* You must meet the [standard system requirements for XL Release](/xl-release/concept/requirements-for-installing-xl-release.html).

    **Important:** Active/hot-standby is not supported on Microsoft Windows.

* The XL Release repository must be stored in an external database, using the instructions in this topic. If you are using XL Release's default configuration (which stores the repository in an embedded Derby database), you must migrate all of your data to an external database before you can start to use active/hot-standby.

* The XL Release [archive database](/xl-release/concept/how-archiving-works.html) must also be stored in an external database, using the instructions in this topic.

* You must use a load balancer that supports hot-standby. This topic describes how to set up the [HAProxy](http://www.haproxy.org/) load balancer.

* You must have a shared filesystem (such as NFS) that both the active and standby XL Release nodes can reach. This will be used to store binary data (artifacts).

* The time on both XL Release nodes must be synchronized through an NTP server.

## Limitation on HTTP session sharing and resiliency

In active/hot-standby mode, there is always at most one "active" XL Release node. The nodes use a health REST endpoint (`/ha/health`) to tell the load balancer which node is the active one. The load balancer will always route users to the active node; calling a standby node directly will result in incorrect behavior.

However, XL Release does not share HTTP sessions among nodes. If the active XL Release node becomes unavailable:

* All users will effectively be logged out and will lose any work that was not yet persisted to the database.
* Any script tasks that were running on the previously active node will have the `failed` status. After another node has become the new active node (which will happen automatically), you can restart these tasks.

## Active/Hot-standby setup procedure

The initial active/hot-standby setup is:

* A load balancer
* A database server
* Two XL Release servers

To set up an active/hot-standby cluster, you must do some manual configuration before starting XL Release.

### Step 1 Configure external databases

The following external databases are recommended:

* MySQL
* PostgreSQL
* Oracle 11g or 12c
* DB2
* MsSql
* H2 (For testing only)
* Derby (For testing only)

The following set of sql privileges are required (where appliccable):

* REFERENCES
* INDEX
* CREATE
* DROP
* SELECT, INSERT, UPDATE, DELETE

#### Configure the archive database

The archive database must be shared among all nodes when active/hot-standby is enabled. Ensure that every node has access to the shared archive database.

To configure the archive database, first add the following parameters to the `xl.reporting` section of the `xl-release.conf` configuration file:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `db-driver-classname` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver`. |
| `db-url` | JDBC URL that describes database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `db-username` | User name to use when logging into the database. |
| `db-password` | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |

Then, place the JAR file containing the JDBC driver of the selected database in the `XL_RELEASE_SERVER_HOME/lib` directory. To download the JDBC database drivers see [Configure the XL Release SQL repository in a database](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html)


#### Sample database configuration

This is an example of the `xl-release.conf` configuration for a stand-alone database:

    xl {
        akka {
            jvm-exit-on-fatal-error=on
        }
        cluster {
            enabled=no
            node {
                clusterPort=5531
                hostname="127.0.0.1"
                id=node1
            }
        }
        database {
            db-driver-classname="org.postgresql.Driver"
            db-password="xlrelease"
            db-url="jdbc:postgresql://localhost:5432/xlrelease"
            db-username=xlrelease
            max-pool-size=20
        }
    }
    


### Step 2 Set up the cluster

All active/hot-standby configuration settings must be provided in the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file, which uses the [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format. In this file on one node:

1. Enable clustering by:
    * Setting `xl.cluster.enabled` to `true`
    * Setting `xl.cluster.mode` to `hot-standby`
1. Define ports for different types of incoming TCP connections in the `xl.cluster.node` section:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `id`  | Unique ID that identifies a node in the cluster. |
| `hostname` | IP address or host name of the machine where the node is running. Note that a loopback address such as `127.0.0.1` or `localhost` should not be used when running cluster nodes on different machines. |
| `clusterPort` | Port used for cluster-wide communications; defaults to `5531`. |

### Step 3 Set up the first node

At a command prompt, run the following server setup command and follow the on-screen instructions:

    ./bin/run.sh -setup

### Step 4 Prepare each node in the cluster

1. Zip the distribution that you created in [Step 2 Set up the cluster](#step-2-set-up-the-cluster).
1. Copy the ZIP file to all other nodes and unzip each one.
1. On each node, edit the `xl.cluster.node` section of the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file. Update the values for the specific node.

**Note:** You do not need to run the server setup command on each node.

### Step 5 Set up the load balancer

To use active/hot-standby, you must front the XL Release servers with a load balancer. The load balancer must check the `/ha/health` endpoint with a `HEAD` or `GET` request to verify that the node is up. This endpoint will return:

* A non-success status code if it is running in hot-standby mode
* A `200 OK` HTTP status code if it is the currently active node

**Note:** Performing a simple TCP check or `GET` operation on `/` is not sufficient, as that will only determine whether the node is running; it will not indicate whether the node is in standby mode.

For instance, for HAProxy, you can add the following configuration:

    backend default_service
      option httpchk head /ha/health HTTP/1.0
      server docker_xlr-node_1 docker_xlr-node_1:5516 check inter 2000 rise 2 fall 3

### Step 6 Start the nodes

Start XL Release on each node, beginning with the first node that you configured. Ensure that each node is fully up and running before starting the next one.

## Sample `xl-release.conf` configuration

This is a sample `xl-release.conf` configuration for one node that uses a MySQL repository database.

    xl {
        akka {
            jvm-exit-on-fatal-error=on
        }
        cluster {
            mode="hot-standby
            enabled=true
            name = "xlr_cluster"
            node {
                clusterPort=5531
                hostname="127.0.0.1"
                id=node1
            }
        }
        database {
            db-driver-classname="com.mysql.jdbc.Driver"
            db-password="xlrelease"
            db-url="jdbc:mysql://localhost:3306/xlrelease?useSSL=false"
            db-username=xlrelease
            max-pool-size=20
        }
    }
    


**Note:** After the first run, passwords in the configuration file will be encrypted and replaced with base64-encoded values.

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
      option httpchk head /ha/health HTTP/1.0
      server docker_xlr-node_1 docker_xlr-node_1:5516 check inter 2000 rise 2 fall 3
      server docker_xlr-seed_1 docker_xlr-seed_1:5516 check inter 2000 rise 2 fall 3