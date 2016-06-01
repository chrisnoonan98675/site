---
title: Using the XL Release cluster functionality
beta: true
no_index: true
---

XL Release 4.8.0 includes a preview of support for cluster configurations. You can use this preview to test the clustering functionality. This topic describes the required setup procedure for XL Release clustering.

## Limitations in the clustering functionality preview

The preview of the XL Release clustering functionality has some limitations. Please review these before using clustering.

### Limitations on repository data migration

The clustering functionality preview should only be used for testing purposes. XL Release does not support the migration of repository data:

* From prior versions of XL Release to the cluster preview version
* From single-node mode to cluster mode
* From the cluster preview version to the final cluster functionality release

The final cluster functionality release will include upgraders that will migrate repository data from your prior production version of XL Release.

### Limitations on repository sharing

XL Release uses a ModeShape JCR implementation when the cluster functionality is enabled, and uses a Jackrabbit JCR implementation when the cluster functionality is disabled. Therefore, switching from cluster mode to single-node mode and vice versa will not migrate data from one JCR repository to the other.

### Limitation on HTTP session sharing

XL Release does not yet share HTTP sessions among nodes in a cluster. This requires the user to hold an HTTP connection to a concrete node in a cluster. When a load balancer is used, a sticky session flag must be enabled. A session is identified by the `JSESSIONID` cookie.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.
* Any background tasks running on the node will be lost.

## Clustering setup description

The initial cluster setup is:

* A load balancer configured to use sticky sessions
* A database server
* At least two XL Release servers

### Testing setup

In XebiaLabs' test setup:

* [HAProxy](http://www.haproxy.org/) was used as a load balancer
* An Oracle 12c database was used
* XL Release nodes were hosted on Unix-based machines

### Supported software

The XL Release clustering functionality has only been tested with Oracle 12c and MySQL databases.

## Active/active cluster setup procedure

To set up an active/active cluster, you must do some manual configuration before starting XL Release.

### Step 1 Prepare the common cluster configuration

1. Unzip the XL Release distribution ZIP file. Do not start XL Release yet.
1. Download [this `xl-release.conf` file](cluster/xl-release.conf) and copy it to the `<XL_RELEASE_HOME>/conf` directory.
1. Edit the `xl-release.conf` file and specify the common parts of the configuration, as described in:
    * [Enable clustering](#enable-clustering)
    * [Configure the archive database](#configure-the-archive-database)
    * [Configure the repository database](#configure-the-repository-database)
    * [Configure cluster member connection details](#configure-cluster-member-connection-details)
1. Copy a valid license (`.lic`) file to the `<XL_RELEASE_HOME>/conf` directory.
1. Install the JDBC driver of the database of your choice in the `<XL_RELEASE_HOME>/lib` directory.
1. Run the following server setup command and follow the on-screen instructions:

        ./bin/run.sh -setup

### Step 2 Prepare each node in the cluster

1. Archive the distribution which you've already set up in [Step 1 Prepare the common cluster configuration](#step-1-prepare-the-common-cluster-configuration).
1. Copy this archive to all the other nodes of the cluster and unarchive it there.
1. For each node, perform the node-specific configuration as described in [Configure node connection details](#configure-node-connection-details).

 Please note that you don't need to run setup again.

### Step 3 Start the nodes

The order in which you initially start each node of the cluster is important.

1. Begin starting the cluster with the node that is specified *first* in the [Cluster members connection details](#cluster-members-connection-details).
1. Proceed to the next node as soon as the previous node started successfully.       

## Cluster configuration settings

All cluster configuration settings must be provided in the `<XL_RELEASE_HOME>/conf/xl-release.conf` file. The [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format is used for this configuration file. [This `xl-release.conf` file](cluster/xl-release.conf) describes the minimum configuration needed to run a cluster.

### Enable clustering

The clustering functionality is enabled when the `xl.cluster.enabled` switch is set to `yes`.

### Configure the archive database

The archive database must be shared among all nodes when the clustering functionality is enabled. Ensure that every node has access to the shared archive database.

The `xl.reporting` section must include the following parameters:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `db-driver-classname` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver` |
| `db-url` | JDBC URL that describes connection details to a database; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` |
| `db-username` | User name to use to log in to the database |
| `db-password` | Password to use to log in to the database (after setup is complete, the password will be encrypted and stored in secured format) |

**Note:** Place the JAR file containing the JDBC driver of the selected database in the `<XL_RELEASE_HOME>/lib` directory.

### Configure the repository database

The repository database must be shared among all nodes when the clustering functionality is enabled. Ensure that every node has access to the shared repository database.

The `xl.repository` section must include the following parameters:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `driverName` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver` |
| `jdbcUrl` | JDBC URL that describes connection details to a database; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` |
| `username` | User name to use to log in to the database |
| `password` | Password to use to log in to the database (after setup is complete, the password will be encrypted and stored in secured format) |

### Configure node connection details

Each node of the cluster will open ports for different types of incoming TCP connections. These are defined in the `xl.cluster.node` section:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `hostname` | IP address or host name of the machine where the node is running. Note that a loopback address such as `127.0.0.1` or `localhost` should not be used when running cluster nodes on different machines. |
| `clusterPort` | Port used for cluster-wide communications; defaults to `5531`. |
| `repositoryPort` | Port used in the repository replication mechanism; defaults to `5541`. |
| `transactionManagerPort` | Port used for the JTA transaction manager; defaults to `5551`. |

### Configure cluster member connection details

Each cluster node must know about all nodes in the cluster (including itself). This information *must* be the same on every node. It is defined in the `xl.cluster.members` section. For example:

    members = [
        {hostname: "node-1.example.com", clusterPort: 5531, repositoryPort: 5541}
        {hostname: "node-2.example.com", clusterPort: 5531, repositoryPort: 5541}
        .....
        {hostname: "node-x.example.com", clusterPort: 5531, repositoryPort: 5541}
    ]

Where:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `hostname` | IP address or hostname of the machine where the node is running |
| `clusterPort` | Port used for cluster-wide communications |
| `repositoryPort` | Port used in the repository replication mechanism |
