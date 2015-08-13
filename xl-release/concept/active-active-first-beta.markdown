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

## Setup procedure for active/active

The beta release includes a number of components that are not included in other XL Release versions. Therefore, the setup procedure requires several manual configuration modifications. 

1. To begin with, unzip the XL Release distribution file on each node. Do not start XL Release yet.
1. On each node, enable Infinispan clustered cache [as described below](#enable-infinispan-clustered-cache)
1. On each node, connect Infinispan to Oracle [as described below](#connecting-infinispan-to-the-oracle-database)
1. Setup cluster connectivity as described under [Configure cluster connectivity](#configure-cluster-connectivity)
1. Put a valid license in the `conf` directory on each node
1. Ensure task archivation uses the Oracle database by configuring `conf/xl-release.conf` as described under [Configure task archiving](#configure-task-archiving)
1. On the first node only, run through the server setup procedure in the normal way (`bin/server.sh -setup`). Do not run on other nodes yet.
1. Start XL Release on this node and log in as admin into the UI.
1. Only after logging in successfully, copy `conf/xl-release-server.conf` and `conf/repository-keystore.jceks` (if it exists) to the other node(s).
1. On the other node(s), start the server without running the setup procedure (i.e. without the `-setup` argument).
1. Read about [limitations of this beta release](#limitations-in-the-beta-release)
1. Execute performance tests and fill out the form. [See below for instructions.](#running-stress-tests)  

### Enable Infinispan clustered cache

XL Release uses a clustered setup of [Infinispan](http://infinispan.org/). To enable Infinispan cluster configuration, open `conf/repository.json` and change the value of property `cacheConfiguration` from `infinispan-local-h2.xml` to `infinispan-cluster-oracle.xml`.

### Connecting Infinispan to the Oracle Database

Infinispan is responsible for data persistence. To connect Infinispan to the Oracle database:

1. Place the JAR file containing the Oracle JDBC driver `ojdbc6.jar` in the XL Release `plugins` directory. You can download the driver from [Oracle](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html).
1. Modify the `jdbc:connection-pool` element in the `conf/infinispan-cluster-oracle.xml` file as follows:
  * `connection-url` must be a valid JDBC URL; for example, `jdbc:oracle:thin:@oracle.hostname.com:1521:SID` (use correct hostname, port, and SID)
  * `driver` must be a valid Oracle driver; for example, `oracle.jdbc.driver.OracleDriver`
  * `username` specifies the username to connect to the database
  * `password` specifies the password to connect to the database

### Configure task archiving

The task archive should use the central Oracle database not multiple local H2 databases. Before the first run on any node, change the `conf/xl-release.conf` file on each node as follows (cf. [Connecting Infinispan to the Oracle Database](#connecting-infinispan-to-the-oracle-database) above):

* set `db-driver-classname` to `"oracle.jdbc.driver.OracleDriver"`
* set `db-url` to `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` (use correct hostname, port, and SID)
* set `db-username` to the proper value
* set `db-password` to the proper value

### Configure cluster connectivity

The `conf/tcpping-jgroups-tcp.xml` file contains the [JGroups](http://www.jgroups.org/) configuration that is used for cluster connectivity. Each node of the cluster must be configured separately.

The following configuration settings are relevant:

* Element `TCP`, attribute `bind_addr`: Must contain the host name or IP address of the current XL Release node. Defaults to `127.0.0.1`. You can override this value using the JVM system property `jgroups.tcp.address`. 

* Element `TCP`, attribute `bind_port`: Must contain the current XL Release node port that JGroups opens for cluster communication. Note that this is different from the XL Release port (5516). Defaults to `7800`. You can override this value using the JVM system property `jgroups.tcp.port`. When running multiple XL Release instances on the same machine, each must have a different `bind_port` assigned.

* Element `TCPPING`, attribute `initial_hosts`: Must contain addresses and ports of all nodes in a cluster. Multiple values are comma-separated. Defaults to `127.0.0.1[7800]`. You can override this value using the JVM system property `jgroups.tcp.cluster.addresses`.


## Limitations in the beta release

### Limitations on functionality

* XL Release does not yet support concurrent operations on a release on two nodes. The current XL Release server uses a node-wide lock to prevent concurrent changes. This lock will be changed to a cluster-friendly solution later.

* Triggers are not cluster-aware yet. That is, race conditions may occur when multiple nodes poll for a condition and act on it at the same time.

### Limitations on resiliency

If an XL Release node becomes unavailable:

* All users that are on that node will be logged out and will lose any work that was not yet persisted to the database.

* Any background tasks running on the node will be lost.


## Running stress tests

**-- UNDER CONSTRUCTION --**

To run the stress tests, follow these steps:

1. Do a git clone of https://github.com/xebialabs-community/xl-release-stress-tests.git
1. Go into that directory and execute the following commands:
    * (On windows):
        * gradlew `:data-generator:run`
        * gradlew `:runner:run`
    * (On Linux):
        * ./gradlew `:data-generator:run`
        * ./gradlew `:runner:run`
1. Reports can be found in the `runner/reports` directory
1. Fill out the form at [http://somewhere.todo.xebialabs.com](http://somewhere.todo.xebialabs.com)