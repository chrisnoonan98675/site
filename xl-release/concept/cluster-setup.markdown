---
layout: beta-noindex
title: XL Release Cluster Milestone 1
---

XL Release has been enhanced to support cluster configurations. This document describes the setup procedure.

**Important:** XebiaLabs does not support the migration of production data from a previous non-clustered version of XL Release to the clustered version of XL Release. The cluster feature of XL Release should be used for testing or experimental purposes only.

## Infrastructural view

The initial cluster setup is:

* A load balancer configured to use sticky sessions
* A database server
* At least two XL Release servers

XebiaLabs' test setup uses [HAProxy](http://www.haproxy.org/) and a single Oracle 12c instance as a database. The final solution should use a clustered database.

### Supported software

XL Release only supports Oracle 12c as the back-end database for the beta release.

## Setup procedure for active/active

The setup procedure requires several manual configuration modifications before

1. Read about the [limitations of the cluster release](#limitations-of-cluster).
1. Unzip the XL Release distribution. Do not start XL Release yet.
1. Create the `conf/xl-release.conf` configuration file with necessary details as described in (#common-config-cluster).
1. Copy a valid license to the `conf` directory.
1. Install JDBC driver of the database of your choice to the `lib/` folder.
1. Run the server setup command as follows and follow on screen instructions:

        ./bin/server.sh -setup
or
        ./bin/server.cmd -setup

1. Use resulted distribution as a base for installation on separate nodes of the cluster.
1. Perform node specific configuration as described here (#node-config-cluster)

### Common cluster configuration

All cluster configuration must be provided in the file `conf/xl-release.conf`. The HOCON format is used for this configuration file. The following content describes bare minimum to be able to run a cluster:

    xl {

      reporting {
        db-url = "jdbc:oracle:thin:@oracle_hostname:1521:SID"
        db-driver-classname = "oracle.jdbc.driver.OracleDriver"
        db-username = "username"
        db-password = "password"
      }

      cluster {

        enabled = yes

        node {
          hostname = "127.0.0.1"
          clusterPort = 5531
          repositoryPort = 5541
          transactionManagerPort = 5551
        }

        members = [
          {hostname: "127.0.0.1", clusterPort: 5531, repositoryPort: 5541}
        ]
      }

      repository {

        persistence {
          jdbcUrl = "jdbc:h2:file:./repository/db"
          driverName = "org.h2.Driver"
          username = "sa"
          password = ""
        }
      }
    }

Let's describe each section separately

#### Archiving (reporting) database

Section `xl.reporting` must include following parameters:

* `db-driver-classname` must be set to class name of the database driver to be used (example, 'oracle.jdbc.driver.OracleDriver')
* `db-url` must be set to jdbc url that describes connection details to a database (example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`)
* `db-username` must be set with the username to be used to login to the database
* `db-password` must be set with the password to be used to login to the database (after performing setup, the password will be encrypted and stored in secured format)

*Note:* Place the JAR file containing the JDBC driver of the selected database in the XL Release `lib` directory.

#### Enabling cluster

The cluster is enabled with the switch `xl.cluster.enabled` set to `yes`.

#### Node connection details

Each node of the cluster will open ports for different types of incoming tcp connections. These are defined in section `xl.cluster.node`

* `hostname` must be set to the ip address or hostname of the machine where the node is running. Please note that loopback address like `127.0.0.1` or `localhost` does not make sense when running cluster nodes on different machines.
* `clusterPort` must be set to port used for cluster wide communications. Defaults to `5531`.
* `repositoryPort` must be set to port used in repository replication mechanism. Defaults to `5541`.
* `transactionManagerPort` must be set to port used for JTA transaction manager. Defaults to `5551`.

#### Cluster members connection details

Each node of the cluster must know about all other nodes in the cluster and this information must be same on each of the node. It is described in section `xl.cluster.members`.

This is a list of configurations values and is specified as follows

    members = [
        {hostname: "node-1", clusterPort: 5531, repositoryPort: 5541}
        {hostname: "node-2", clusterPort: 5531, repositoryPort: 5541}
        .....
        {hostname: "node-x", clusterPort: 5531, repositoryPort: 5541}
    ]

where:

* `hostname` must be set to the ip address or hostname of the machine where the node is running.
* `clusterPort` must be set to port used for cluster wide communications.
* `repositoryPort` must be set to port used in repository replication mechanism.

#### Shared repository connection details

1. Place the JAR file containing the Oracle JDBC driver `ojdbc6.jar` in the XL Release `plugins` directory. You can download the driver from [Oracle](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html).
1. Modify the `jdbc:connection-pool` element in the `conf/infinispan-cluster-oracle.xml` file as follows:
  * `connection-url` must be a valid JDBC URL; for example, `jdbc:oracle:thin:@oracle.hostname.com:1521:SID` (use the correct hostname, port, and SID)
  * `driver` must be a valid Oracle driver; for example, `oracle.jdbc.driver.OracleDriver`
  * `username` specifies the username to connect to the database
  * `password` specifies the password to connect to the database


## Limitations in the beta release

### Limitation on HTTP session sharing

XL Release does not yet share HTTP sessions among nodes in a cluster. This requires the user to hold an HTTP connection to a concrete node in a cluster. When a load balancer is used, a sticky session flag must be enabled. Session is identified by the `JSESSIONID` cookie.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.

* Any background tasks running on the node will be lost.
