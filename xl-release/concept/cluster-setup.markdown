---
layout: beta-noindex
title: XL Release Cluster
---

XL Release has been enhanced to support cluster configurations. This document describes the setup procedure.

## Infrastructural view

The initial cluster setup is:

* A load balancer configured to use sticky sessions
* A database server
* At least two XL Release servers

## Tested setup
XebiaLabs' test setup used: 
* [HAProxy](http://www.haproxy.org/) as a load balancer.  
* An Oracle 12c as database.
* XLR Nodes were hosted on unix-based machines

### Supported software

XL Release Cluster have been tested with Oracle 12c and MySql databases only.

## Setup procedure for active/active

The setup procedure requires several manual configuration modifications before

Read about the [limitations of the cluster release](#limitations-of-cluster).

### Preparation of common clustered distribution

1. Unzip the XL Release distribution. Do not start XL Release yet.
1. Download file [xl-release.conf](cluster/xl-release.conf) and place it into `conf/` folder of XLR.
1. Specify common part of the configuration `conf/xl-release.conf` as described in sections
    * [Enabling Cluster](#enabling-cluster).
    * [Archiving database](#archiving-database).
    * [Repository database](#repository-database).
    * [Cluster members connection details](#cluster-members-connection-details).
1. Copy a valid license to the `conf` directory.
1. Install JDBC driver of the database of your choice to the `lib/` folder.
1. Run the server setup command as follows and follow on screen instructions:

        ./bin/server.sh -setup

### Per node setup

1. Use resulted distribution created in [Preparation of common clustered distribution](#preparation-of-common-clustered-distribution) 
1. Perform node specific configuration as described in [Node connection details](#node-connection-details)

### Starting up cluster

The sequence of initial start up of the cluster is important.

1. Begin start of the cluster with the node that is specified first in the [Cluster members connection details](#cluster-members-connection-details).   
1. Proceed to the next node as soon as previous node booted up successfully.       

### Cluster configuration

All cluster configuration must be provided in the file `conf/xl-release.conf`. The [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format is used for this configuration file. The following [xl-release.conf](cluster/xl-release.conf) describes bare minimum to be able to run a cluster.

Let's describe each section separately

#### Enabling cluster

The cluster is enabled with the switch `xl.cluster.enabled` set to `yes`. 

#### Archiving database

Archiving database must be shared between all nodes when cluster is enabled. Make sure that every node has access to the shared archiving database.  

Section `xl.reporting` must include following parameters:

* `db-driver-classname` must be set to class name of the database driver to be used (example, 'oracle.jdbc.driver.OracleDriver')
* `db-url` must be set to JDBC url that describes connection details to a database (example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`)
* `db-username` must be set with the username to be used to login to the database
* `db-password` must be set with the password to be used to login to the database (after performing setup, the password will be encrypted and stored in secured format)

*Note:* Place the JAR file containing the JDBC driver of the selected database in the XL Release `lib` directory.

#### Repository database

Repository database must be shared between all nodes when cluster is enabled. Make sure that every node has access to the shared repository database.

Section `xl.repository` must include following parameters:

* `driverName` must be set to class name of the database driver to be used (example, `oracle.jdbc.driver.OracleDriver`)
* `jdbcUrl` must be set to JDBC url that describes connection details to a database (example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`)
* `username` must be set with the username to be used to login to the database
* `password` must be set with the password to be used to login to the database (after performing setup, the password will be encrypted and stored in secured format)

#### Node connection details

Each node of the cluster will open ports for different types of incoming tcp connections. These are defined in section `xl.cluster.node`

* `hostname` must be set to the ip address or hostname of the machine where the node is running. Please note that loopback address like `127.0.0.1` or `localhost` does not make sense when running cluster nodes on different machines.
* `clusterPort` must be set to port used for cluster wide communications. Defaults to `5531`.
* `repositoryPort` must be set to port used in repository replication mechanism. Defaults to `5541`.
* `transactionManagerPort` must be set to port used for JTA transaction manager. Defaults to `5551`.

#### Cluster members connection details

Each node of the cluster must know about all nodes (including itself) in the cluster and this information must be same on each of the node. It is described in section `xl.cluster.members`.

This is a list of configurations values and is specified as follows

    members = [
        {hostname: "node-1.example.com", clusterPort: 5531, repositoryPort: 5541}
        {hostname: "node-2.example.com", clusterPort: 5531, repositoryPort: 5541}
        .....
        {hostname: "node-x.example.com", clusterPort: 5531, repositoryPort: 5541}
    ]

where:

* `hostname` must be set to the ip address or hostname of the machine where the node is running.
* `clusterPort` must be set to port used for cluster wide communications.
* `repositoryPort` must be set to port used in repository replication mechanism.


## Limitations in the cluster release

### Limitation on HTTP session sharing

XL Release does not yet share HTTP sessions among nodes in a cluster. This requires the user to hold an HTTP connection to a concrete node in a cluster. When a load balancer is used, a sticky session flag must be enabled. Session is identified by the `JSESSIONID` cookie.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.

* Any background tasks running on the node will be lost.

### Single mode vs Cluster mode repository

XL Release is relying on Modeshape JCR implementation when cluster is enabled and it relies on Jackrabbit JCR implementation when cluster is disabled. 
Because of that, switching from cluster mode to a single mode and vise versa will not migrate data from one JCR repository to another. 

### Limitations on data migration

XL Release Cluster does not support migration of previous production data nor migration from  single mode to cluster mode data (see Single mode vs Cluster mode repository)