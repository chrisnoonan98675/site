---
layout: beta
title: XL Release Active/Active - First beta release
---

XL Release has been enhanced to support active/active configurations. This document describes the setup procedure.

The beta release mainly contains infrastructural changes instead of application-level changes; this means there are some limitations in the release, which are described below. These limitations will be addressed in a later release.

**Note:** XebiaLabs does not provide an upgrade path to the beta release and will not provide upgrade paths from the beta release to later releases.

## Infrastructural view

The initial active/active setup is:

* A load balancer configured to use sticky sessions
* A database server
* At least two XL Release servers

XebiaLabs' test setup uses [HAProxy](http://www.haproxy.org/) and a single Oracle 12c instance as a database. The final solution should use a clustered database.

### Supported software

XL Release only supports Oracle 12c as the back-end database for the beta release.

## Setup procedure

The beta release includes a number of components that are not included in other XL Release versions. Therefore, the setup procedure requires several manual configuration modifications.

To start, unzip the XL Release distribution file on each node. Before starting XL Release, modify the configuration as described below.

### Configure task archiving

The task that archives releases must only run on a single node in the cluster at a time. In the `xlrelease.ArchivingSettings.archivingJobCronSchedule` setting in the `conf/deployit-defaults.properties` file, configure the cron schedule on each node so that it will only run on one node at a time. For example, set the offset from the hour a few minutes apart.

### Configure cluster connectivity

The `conf/tcpping-jgroups-tcp.xml` file contains the [JGroups](http://www.jgroups.org/) configuration that is used for cluster connectivity. Each node of the cluster must be configured separately.

The following configuration settings are important:

* Element `TCP`, attribute `bind_addr`: Must contain the host name or IP address of the current XL Release node. Defaults to `127.0.0.1`. You can change this value using the JVM system property `jgroups.tcp.address`. 

* Element `TCP`, attribute `bind_port`: Must contain the current XL Release node port that JGroups opens for cluster communication. Defaults to `7800`. You can change this value using the JVM system property `jgroups.tcp.port`.

* Element `TCPPING`, attribute `initial_hosts`: Must contain addresses and ports of all nodes in a cluster. Multiple values are comma-separated. Defaults to `127.0.0.1[7800]`. You can change this value using the JVM system property `jgroups.tcp.cluster.addresses`.

### Enabling infinispan clustered cache

XL Release uses a clustered setup of [Infinispan](http://infinispan.org/). To enable infinispan cluster configuration, open file `conf/repository.json` and replace value of property `cacheConfiguration` from `infinispan-local-h2.xml` to `infinispan-cluster-oracle.xml`.

### Connection to Oracle Database

[Infinispan](http://infinispan.org/) is responsible for data persistence. To connect Infinispan to the Oracle database, make the following changes in the `conf/infinispan-cluster-oracle.xml` file:

* Modify element `jdbc:connection-pool`:
    * `connection-url` must be a valid JDBC URL; for example, `jdbc:oracle:thin:@oracle.hostname.com:1521:SID`
    * `driver` must be a valid Oracle driver; for example, `oracle.jdbc.driver.OracleDriver`
    * `username` specifies the username to connect to the database
    * `password` specifies the password to connect to the database

Also, place the JAR file containing the Oracle JDBC driver `ojdbc6.jar` in the XL Release `plugins` directory. You can download the driver from [Oracle](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html).

### First run

For the first run in a multi-node setup, the following sequence must be followed:

1. On the first node, run through the server setup procedure in the normal way (`bin/server.sh -setup`). Do not run on other nodes yet.
1. Log in into the XL Release GUI as the admin user.
1. Only after logging in successfully, copy `conf/xl-release-server.conf` and `conf/repository-keystore.jceks` (if it exists) to the other node(s).
1. On the other node(s), start the server without running the setup procedure.

## Limitations in the beta release

### Limitations on functionality

* XL Release does not yet support concurrent operations on a release on two nodes. The current XL Release server uses a node-wide lock to prevent concurrent changes. This lock will be changed to a cluster-friendly solution later.

* Triggers are not cluster-aware yet. That is, race conditions may occur when multiple nodes poll for a condition and act on it at the same time.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.

* Any background tasks running on the node will be lost.

### Limitations on replication

If a node leaves the cluster (because it is stopped, there are networking problems, or another reason) and then re-joins the cluster, new releases that were added in the meantime will *not* appear in search results or in the release overview, despite the fact that they are replicated to the node.

This is caused by a Modeshape issue that will be fixed in the next Modeshape release; refer to the [JBossDeveloper forum](https://developer.jboss.org/message/936937) and [MODE-1903](https://issues.jboss.org/browse/MODE-1903) for more information.

A workaround is to remove all indexes on the node before it re-joins the cluster. The indexes will then be rebuilt with new data. Partial indexes rebuilt based on [journaling](https://docs.jboss.org/author/display/MODE40/Journaling) is [scheduled](https://issues.jboss.org/browse/MODE-1903) for Modeshape 4.4.
