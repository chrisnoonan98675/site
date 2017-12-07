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
- XL Release 7.5.0
---

This document describes how to run XL Release 7.5 and higher as a cluster. Running XL Release in this mode enables you to have a Highly Available (HA) XL Release setup. Two modes are supported:

* **Active/active**: Two or more nodes are running simultaneously to process all requests. A load balancer is needed to distribute requests.
* **Active/hot standby**: Two nodes are running at the same time, but only one serves requests. The other node will take over when the main node goes down. The load balancer detects which node is active and redirects requests accordingly.

![Cluster configuration](../images/diagram-cluster.png)



## Requirements

Using XL Release in cluster mode requires the following:

* You must meet the [standard system requirements for XL Release](/xl-release/concept/requirements-for-installing-xl-release.html).

* The XL Release repository and archive must be stored in an external database, using the instructions in [this topic](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html). Cluster mode is not supported for the default configuration with an embedded database.

* A load balancer. This topic describes how to set up the [HAProxy](http://www.haproxy.org/) load balancer.

* The time on both XL Release nodes must be synchronized through an NTP server.

* The servers running XL Release must run on the same operating system.

* XL Release servers and load balancers must be on the same network.



## Active/Hot-standby setup procedure

The initial active/hot-standby setup is:

* A load balancer
* A database server
* Two XL Release servers

To set up the cluster, perform the following configuration steps before starting XL Release.

### Step 1 Set up external databases

Follow the instructions in [Configure the XL Release SQL repository in a database](/xl-release/howto/configure-the-xl-release-sql-repository-in-a-database.markdown).

For the cluster setup, both the xlrelease repostiory as the reporting archive must be configured in an external database.

### Step 2 Set up the cluster in the XL Release configuration

All active/hot-standby configuration settings are specified in the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file, which uses the [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format. 

1. Enable clustering by:
    * Setting `xl.cluster.mode` to `full` (active/active) or `hot-standby`.
1. Define ports for different types of incoming TCP connections in the `xl.cluster.node` section:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `xl.cluster.mode` | Possible values: `default` (single node, no cluster); `full` (active/active); `hot-standby`. Use this property top turn on the cluster by setting it to either 'full' or 'hot-standby'.|
| `xl.cluster.name` | A label to identify the cluster. |
| `xl.cluster.node.id`  | Unique ID that identifies this node in the cluster. |
| `xl.cluster.node.hostname` | IP address or host name of the machine where the node is running. Note that a loopback address such as `127.0.0.1` or `localhost` should not be used. |
| `xl.cluster.node.clusterPort` | Port used for cluster-wide communications; defaults to `5531`. |

#### Sample configuration

This is an example of the `xl-release.conf` configuration for an active/active setup:

    xl {
        cluster {
            mode = full
            name = "xlr-cluster"
            node {
                clusterPort = 5531
                hostname = "xlrelease-1.example.com"
                id = "xlrelease-1"
            }
        }
        database {
            ...
        }
    }


### Step 3 Set up the first node

At a command prompt, run the following server setup command and follow the on-screen instructions:

    ./bin/run.sh -setup

### Step 4 Prepare another node in the cluster

1. Zip the distribution that you created in [Step 2](#step-2-set-up-the-cluster-in-the-xl-release-configuration).
1. Copy the ZIP file to another nodes and unzip it.
1. Edit the `xl.cluster.node` section of the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file. Update the values for the specific node.

**Note:** You do not need to run the server setup command on each node.

### Step 5 Set up the load balancer

#### Active / active
When running in active/active mode, you should configure a load balancer to divide the requests between the available servers.

This is a sample `haproxy.cfg` configuration for HAProxy. Ensure that your configuration is hardened before using it in a production environment.

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
      cookie JSESSIONID prefix
      server server1 xlrelease.local:5516 cookie server1 check port 5516
      server server2 xlrelease.local:5517 cookie server2 check port 5517


#### Active / hot standby 

To use active/hot-standby, you must front the XL Release servers with a load balancer. The load balancer checks the `/ha/health` endpoint with a `HEAD` or `GET` request to verify that the node is up. This endpoint will return:

* A non-success status code if it is running in hot-standby mode
* A `200 OK` HTTP status code if it is the currently active node

For HAProxy, replace the `` section with something like the following to enable hot standby failover:

	backend default_service
	  option httpchk head /ha/health HTTP/1.0
	  server node_1 node_1:5516 check inter 2000 rise 2 fall 3
	  server node_2 node_2:5516 check inter 2000 rise 2 fall 3

#### Limitation on HTTP session sharing and resiliency in active / hot standby mode

In active/hot-standby mode, there is always at most one "active" XL Release node. The nodes use a health REST endpoint (`/ha/health`) to tell the load balancer which node is the active one. The load balancer will always route users to the active node; calling a standby node directly will result in incorrect behavior.

However, XL Release does not share HTTP sessions among nodes. If the active XL Release node becomes unavailable:

* All users will effectively be logged out and will lose any work that was not yet persisted to the database.
* Any script tasks that were running on the previously active node will have the `failed` status. After another node has become the new active node (which will happen automatically), you can restart these tasks.

**Note:** Performing a simple TCP check or `GET` operation on `/` is not sufficient, as that will only determine whether the node is running; it will not indicate whether the node is in standby mode.


### Step 6 Start the nodes

Start XL Release on each node, beginning with the first node that you configured. Ensure that each node is fully up and running before starting the next one.


