---
title: Configure XL Release in Active/Hot-Standby cluster mode
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- failover
- database
- active/active
- clustering
weight: 496
---

XL Release 6.0.0 can be configured in a clustered active/hot-standby mode. This is the currently the only cluster mode that is supported. Running XL Release in this mode ensures that you have a Highly Available (HA) XL Release. This topic describes the required setup procedure to enable this cluster mode.

## Requirements

Running in a clustered mode adds some additional requirements to the regular set of requirements. Please review these before starting the setup.

* Clustering requires the use of an external database for the repository. If you're currently running XL Release against the 'out-of-the-box' configuration, which stores everything in an embedded Derby database, you cannot migrate, without first migrating all your data to an external DB
* The XL Release archiving database must also be configured as an external database
* Hot-Standby clustering requires the use of a loadbalancer which has support for this. This topic will describe the setup for the HAProxy loadbalancer.
* A shared Filesystem, such as NFS, reachable from both nodes

## Limitations in the clustering functionality preview

The preview of the XL Release clustering functionality has some limitations. Please review these before using clustering.

### Limitation on HTTP session sharing

XL Release does not share HTTP sessions among nodes in a cluster.
This requires the user to hold an HTTP connection to a concrete node in a cluster.
When a load balancer is used, a sticky session flag must NOT be enabled.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.
* Any background tasks running on the node will be lost and will be able to restart on the standby node.

## Clustering setup description

The initial cluster setup is:

* A load balancer configured so that it does not use sticky sessions
* A database server
* Two XL Release servers

## Active/Hot-standby cluster setup procedure

To set up an active/hot-standby cluster, you must do some manual configuration before starting XL Release.

### Step 1 Prerequisite setup

This how-to assumes you've already setup XL Release against an external database as described in [Configure an external database](/xl-release/how-to/configure-an-external-database.html)
and that you have shared filesystem, such as NFS, configured and ready to be used as a store for binary data by all the nodes in the cluster.

### Step 2 Setting up the cluster

1. Edit the `xl-release.conf` file and specify the common parts of the configuration, as described in:
    * [Enable clustering](#enable-clustering)
    * [Enable repository cluster mode](#enable-repository-cluster-mode)
    * [Configure a shared filesystem](#configure-a-shared-filesystem)
    * [Configure node connection details](#configure-node-connection-details)
    * [Configure cluster member connection details](#configure-cluster-member-connection-details)
1. Run the following server setup command and follow the on-screen instructions:

        ./bin/run.sh -setup

### Step 2 Prepare each node in the cluster

1. Archive the distribution which you've already set up in [Step 1 Prepare the common cluster configuration](#step-1-prepare-the-common-cluster-configuration).
1. Copy this archive to all the other nodes of the cluster and un-archive it there.
1. For each node, perform the node-specific configuration as described in [Configure node connection details](#configure-node-connection-details).

 Please note that you don't need to run setup again.

### Step 3 Start the nodes

The order in which you initially start each node of the cluster is important.

1. Begin starting the cluster with the node that is specified *first* in the [Cluster members connection details](#cluster-members-connection-details).
1. Proceed to the next node as soon as the previous node starts successfully.

## Cluster configuration settings

All cluster configuration settings must be provided in the `<XL_RELEASE_SERVER_HOME>/conf/xl-release.conf` file. The [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format is used for this configuration file.

### Enable clustering

The active/hot-standby clustering functionality is enabled when the `xl.cluster.enabled` switch is set to `hot-standby`.

### Enable repository cluster mode

Instead of the `<database>-standalone` configuration that you configured in the "[Configure an external database](/xl-release/how-to/configure-an-external-database.html)" how-to, you need to choose the clustered configuration.
Please set the correct `xl.repository.configuration` value from this table:

{:.table .table-striped}
| Database   | Configuration value  |
| ---------- | -------------------- |
| MySQL      | `mysql-cluster`      |
| Oracle     | `oracle-cluster`     |
| PostgreSQL | `postgresql-cluster` |

There is no need to change the other database configuration settings.

### Configure a shared filesystem

The active/hot-standby cluster configuration requires that artifacts are stored in a shared filesystem such as NFS.
Please ensure that the configuration value `xl.repository.jackrabbit.artifacts.location` points to such a shared filesystem and that it is accessible by all the nodes in the cluster.

### Configure node connection details

Each node of the cluster will open ports for different types of incoming TCP connections. These are defined in the `xl.cluster.node` section:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `id`  | Id used to uniquely describe node in the cluster. |
| `hostname` | IP address or host name of the machine where the node is running. Note that a loopback address such as `127.0.0.1` or `localhost` should not be used when running cluster nodes on different machines. |
| `clusterPort` | Port used for cluster-wide communications; defaults to `5531`. |

### Configure cluster member connection details

Each cluster node must know about all nodes in the cluster (including itself). This information *must* be the same on every node. It is defined in the `xl.cluster.members` section. For example:

    members = [
        {hostname: "node-1.example.com", clusterPort: 5531 }
        {hostname: "node-2.example.com", clusterPort: 5531 }
        .....
        {hostname: "node-x.example.com", clusterPort: 5531 }
    ]

Where:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `hostname` | IP address or hostname of the machine where the node is running |
| `clusterPort` | Port used for cluster-wide communications |
| `repositoryPort` | Port used in the repository replication mechanism |


### Sample configuration

For your convenience, here's the configuration for one of the nodes in a cluster that uses MySQL repository database:

    xl {
        cluster {
            # xl.cluster.mode: "default", "hot-standby"
            mode=hot-standby
            # xl.cluster.enabled - on or off
            enabled=on
            # xl.cluster.members - cluster seed nodes
            members=[
                {
                    clusterPort=5531
                    hostname=xlr-seed
                },
                {
                    clusterPort=5531
                    hostname=xlr-node
                }
            ]
            # xl.cluster.name - name of the cluster
            name="xlr_cluster"
            # xl.cluster.node - this cluster node specific parameters
            node {
                id=xlr-seed
                hostname=xlr-seed
                clusterPort=5531
            }
        }
        repository {
            # xl.repository.configuration - one of the predefined and supported jackrabbit repository configurations
            # available configurations: default, mysql-standalone, mysql-cluster, oracle-standalone, oracle-cluster, postgresql-standalone, postgresql-cluster
            configuration = "mysql-cluster"
            # xl.repository.persistence - repository database connection parameters
            persistence {
                jdbcUrl = "jdbc:mysql://db/xlrelease?useSSL=false"
                username = "xlrelease"
                password = "xlrelease"
                maxPoolSize = "20"
            }
            jackrabbit {
                # xl.repository.jackrabbit.artifacts.location - location for shared files - should be shared filesystem (e.g. NFS)
                artifacts.location = "repository"
                # xl.repository.jackrabbit.bunleCacheSize - bundle cache size - default is 8 MB, increase it to something more sensible
                bundleCacheSize = 128
            }
        }
        # xl.reporting - reporting/archive database connection parameters
        reporting {
            db-driver-classname="com.mysql.jdbc.Driver"
            db-url="jdbc:mysql://db/xlrarchive?useSSL=false"
            db-username="xlrarchive"
            db-password="xlrarchive"
        }
    }

Please note that passwords in configuration file will be encrypted and replaced by base64 encoded value after the first run.