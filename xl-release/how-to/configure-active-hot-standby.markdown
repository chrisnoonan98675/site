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

As of XL Release 6.0.0, you can configure XL Release in a clustered active/hot-standby mode. Running XL Release in this mode ensures that you have a Highly Available (HA) XL Release. Currently, active/hot-standby is the only cluster mode that is recommended.

This topic describes the procedure to enable active/hot-standby mode.

## Requirements

Using XL Release in active/hot-standby mode adds requirements to the [normal system requirements for XL Release](/xl-release/concept/requirements-for-installing-xl-release.html). Please review these requirements before starting the setup.

* You must use an [external database](/xl-release/how-to/configure-an-external-database.html) for the repository. If you currently run XL Release with its default configuration (which stores everything in an embedded Derby database), you must migrate all of your data to an external database before you can start to use active/hot-standby.
* The XL Release [archive database](/xl-release/how-to/configure-the-archive-database.html) must also be configured as an external database.
* Hot standby mode requires you to use a load balancer that supports hot standby. This topic describes how to set up the [HAProxy](http://www.haproxy.org/) load balancer.
* You must have a shared filesystem (such as NFS) that both the active and the standby XL Release nodes can reach.

## Limitation on HTTP session sharing and resiliency

In active/hot-standby mode, there is always at most one "active" XL Release node. The nodes use a health REST endpoint (`/ha/health`) to tell the load balancer which node is the active one. The load balancer will always route users to the active node; calling a standby node directly will result in incorrect behavior.

However, XL Release does not share HTTP sessions among nodes. If the active XL Release node becomes unavailable:

* All users will effectively be logged out and will lose any work that was not yet persisted to the database.
* Any script tasks that were running on the previously active node will have the `failed` status. After another node has become the new active node (which will happen automatically), these tasks can be restarted.

## Active/Hot-standby setup procedure

The initial active/hot-standby setup is:

* A load balancer
* A database server
* Two XL Release servers

To set up an active/hot-standby cluster, you must do some manual configuration before starting XL Release.

### Step 1 Prerequisite setup

This procedure assumes that:

* You have already configured XL Release to use an external database as described in [Configure an external database](/xl-release/how-to/configure-an-external-database.html)
* You have configured a shared filesystem (such as NFS) that is ready to be used as a store for binary data by all nodes

### Step 2 Setting up the cluster

1. Edit the `xl-release.conf` file and specify the common parts of the configuration, as described in:
    * [Enable clustering](#enable-clustering)
    * [Enable repository cluster mode](#enable-repository-cluster-mode)
    * [Configure a shared filesystem](#configure-a-shared-filesystem)
    * [Configure node connection details](#configure-node-connection-details)
1. At a command prompt, run the following server setup command and follow the on-screen instructions:

        ./bin/run.sh -setup

### Step 3 Prepare each node in the cluster

1. Zip the distribution that you created in [Step 2 Setting up the cluster](#step-2-setting-up-the-cluster).
1. Copy this zip file to all other nodes and unzip it there.
1. For each node, perform the node-specific configuration as described in [Configure node connection details](#configure-node-connection-details).

**Note:** You do not need to run the setup command again.

### Step 4 Set up the load balancer

To use active/hot-standby, you must front the XL Release servers with a load balancer. The load balancer must check the `/ha/health` endpoint with a `HEAD` or `GET` request to verify that the node is up. This endpoint will return:

* A non-success status code if it is running in hot-standby mode
* A `200 OK` HTTP status code if it is the currently active node

**Note:** Performing a simple TCP check or `GET` operation on `/` is not sufficient, as that will only determine whether the node is running; it will not indicate whether the node is in standby mode.

For instance, for HAProxy, you can add the following configuration:

    backend default_service
      option httpchk head /ha/health HTTP/1.0
      server docker_xlr-node_1 docker_xlr-node_1:5516 check inter 2000 rise 2 fall 3

### Step 5 Start the nodes

Start each of the nodes 1 by 1, making sure that at least the first one is fully up, before starting the backup nodes.

## Active/hot-standby configuration settings

All active/hot-standby configuration settings must be provided in the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file, which uses the [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format.

### Enable clustering

The active/hot-standby functionality is enabled when the following settings are configured in `xl-release.conf`:

* `xl.cluster.enabled` is set to `true`
* `xl.cluster.mode` is set to `hot-standby`

### Enable repository cluster mode

Instead of the `<database>-standalone` configuration that you configured in the [Configure an external database](/xl-release/how-to/configure-an-external-database.html) procedure, you must choose a clustered configuration. Set the correct `xl.repository.configuration` value from this table:

{:.table .table-striped}
| Database   | Configuration value  |
| ---------- | -------------------- |
| MySQL      | `mysql-cluster`      |
| Oracle     | `oracle-cluster`     |
| PostgreSQL | `postgresql-cluster` |

There is no need to change the other database configuration settings.

### Configure a shared filesystem

The active/hot-standby configuration requires that artifacts are stored in a shared filesystem such as NFS. Ensure that the `xl.repository.jackrabbit.artifacts.location` configuration value points to such a shared filesystem and that all nodes can access it.

### Configure node connection details

Each node will open ports for different types of incoming TCP connections. These are defined in the `xl.cluster.node` section:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `id`  | ID used to uniquely describe a node in the cluster. |
| `hostname` | IP address or host name of the machine where the node is running. Note that a loopback address such as `127.0.0.1` or `localhost` should not be used when running cluster nodes on different machines. |
| `clusterPort` | Port used for cluster-wide communications; defaults to `5531`. |

## Sample `xl-release.conf` configuration

This is a sample `xl-release.conf` configuration for one node that uses a MySQL repository database.

    xl {
        cluster {
            # xl.cluster.mode: "default", "hot-standby"
            mode=hot-standby
            # xl.cluster.enabled - true or false
            enabled=true
            # xl.cluster.name - name of the cluster
            name="xlr_cluster"
            # xl.cluster.node - this cluster node specific parameters
            node {
                id=xlr-node-1
                hostname=xlr-node-1
                clusterPort=5531
            }
        }
        repository {
            # xl.repository.configuration - one of the predefined and recommended jackrabbit repository configurations
            # available configurations: default, mysql-standalone, mysql-cluster, oracle-standalone, oracle-cluster, postgresql-standalone, postgresql-cluster
            configuration = "mysql-cluster"
            # xl.repository.persistence - repository database connection parameters
            persistence {
                jdbcUrl = "jdbc:mysql://db/xlrelease"
                username = "xlrelease"
                password = "xlrelease"
                maxPoolSize = "20"
            }
            jackrabbit {
                # xl.repository.jackrabbit.artifacts.location - location for shared files - should be shared filesystem (e.g. NFS)
                artifacts.location = "repository"
                # xl.repository.jackrabbit.bunleCacheSize - bundle cache size in MB - default is 8 MB
                bundleCacheSize = 128
            }
        }
        # xl.reporting - reporting/archive database connection parameters
        reporting {
            db-driver-classname="com.mysql.jdbc.Driver"
            db-url="jdbc:mysql://db/xlrarchive"
            db-username="xlrarchive"
            db-password="xlrarchive"
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
