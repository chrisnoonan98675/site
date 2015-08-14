---
layout: beta
title: XL Release Active/Active - First beta release
---

XL Release has been enhanced to support active/active configurations. This document describes the setup procedure.

The beta release mainly contains infrastructural changes instead of application-level changes; this means there are some limitations in the release, which are described below. These limitations will be addressed in a later release.

**Important:** XebiaLabs does not provide an upgrade path to the beta release and will not provide upgrade paths from the beta release to later releases.

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

1. Unzip the XL Release distribution file on each node. Do not start XL Release yet.
1. On each node, enable Infinispan clustered cache [as described below](#enable-infinispan-clustered-cache).
1. On each node, connect Infinispan to Oracle [as described below](#connect-infinispan-to-the-oracle-database).
1. Set up cluster connectivity as described under [Configure cluster connectivity](#configure-cluster-connectivity).
1. Copy a valid license to the `conf` directory on each node.
1. Ensure task archiving uses the Oracle database by configuring `conf/xl-release.conf` as described under [Configure task archiving](#configure-task-archiving).
1. On the first node *only*, follow the server setup procedure in the normal way (`bin/server.sh -setup`). Do not run the setup procedure on other nodes yet.
1. Start XL Release on the first node and log in to the user interface as the admin user.
1. After logging in successfully, copy `conf/xl-release-server.conf` and `conf/repository-keystore.jceks` (if it exists) to the other node(s).
1. On the other node(s), start the server without running the setup procedure (that is, without the `-setup` argument).
1. Read about the [limitations of this beta release](#limitations-in-the-beta-release).
1. Execute performance tests and fill out the form. [See below for instructions.](#running-stress-tests)  

### Enable Infinispan clustered cache

XL Release uses a clustered setup of [Infinispan](http://infinispan.org/). To enable the Infinispan cluster configuration, open `conf/repository.json` and change the value of property `cacheConfiguration` from `infinispan-local-h2.xml` to `infinispan-cluster-oracle.xml`.

### Connect Infinispan to the Oracle Database

Infinispan is responsible for data persistence. To connect Infinispan to the Oracle database:

1. Place the JAR file containing the Oracle JDBC driver `ojdbc6.jar` in the XL Release `plugins` directory. You can download the driver from [Oracle](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html).
1. Modify the `jdbc:connection-pool` element in the `conf/infinispan-cluster-oracle.xml` file as follows:
  * `connection-url` must be a valid JDBC URL; for example, `jdbc:oracle:thin:@oracle.hostname.com:1521:SID` (use the correct hostname, port, and SID)
  * `driver` must be a valid Oracle driver; for example, `oracle.jdbc.driver.OracleDriver`
  * `username` specifies the username to connect to the database
  * `password` specifies the password to connect to the database

### Configure task archiving

The task archive should use the central Oracle database, not multiple local H2 databases. Before the first run time task archiving is run on any node, change the `conf/xl-release.conf` file on each node as follows (see also [Connect Infinispan to the Oracle Database](#connect-infinispan-to-the-oracle-database) above):

* Set `db-driver-classname` to `"oracle.jdbc.driver.OracleDriver"`
* Set `db-url` to `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` (use the correct hostname, port, and SID)
* Set `db-username` to the proper value
* Set `db-password` to the proper value

### Configure cluster connectivity

The `conf/tcpping-jgroups-tcp.xml` file contains the [JGroups](http://www.jgroups.org/) configuration that is used for cluster connectivity. Each node of the cluster must be configured separately.

The following configuration settings are relevant:

* Element `TCP`, attribute `bind_addr`: Must contain the host name or IP address of the current XL Release node. Defaults to `127.0.0.1`. You can override this value using the JVM system property `jgroups.tcp.address`. 

* Element `TCP`, attribute `bind_port`: Must contain the current XL Release node port that JGroups opens for cluster communication. Note that this is different from the XL Release port (5516). Defaults to `7800`. You can override this value using the JVM system property `jgroups.tcp.port`.

    When running multiple XL Release instances on the same machine, each must have a different `bind_port` assigned.

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

To run a set of tests, follow these steps:

1. Consult the [form for required test sets TO DO](http://somewhere.todo.xebialabs.com) and pick up one test set.
1. Using Git, clone [https://github.com/xebialabs-community/xl-release-stress-tests.git](https://github.com/xebialabs-community/xl-release-stress-tests.git).
1. If the nodes in the cluster are running, stop them.
1. If the `ISPN_STRING_TABLE_REPO` table exists in the Oracle database, remove it.
1. Start a single node in the cluster and wait until it is fully initialized. 
1. Insert the test data by executing the following command in the directory of the cloned Git repository:

    On Microsoft Windows:
    
        gradlew :data-generator:run -Pserver-url=http://node1.hostname.com:5516 -Pactive-releases=NUMBER_OF_RELEASES -i

    On Linux:

        ./gradlew :data-generator:run -Pserver-url=http://node1.hostname.com:5516 -Pactive-releases=NUMBER_OF_RELEASES -i

    Where `node1.hostname.com:5516` points to a single running node in the cluster and `NUMBER_OF_RELEASES` is number of releases to insert into the repository. This number is defined by current test set and must be manually specified in the command.
1. Start the remaining nodes that participate in the test set and wait until they are fully initialized.
1. Run the test set by executing following command:

    On Microsoft Windows:

        gradlew :runner:run -PbaseUrl=http://loadbalancer.hostname.com -Psimulation=stress.RealisticSimulation

    On Linux

        ./gradlew :runner:run -PbaseUrl=http://loadbalancer.hostname.com -Psimulation=stress.RealisticSimulation

    Where `loadbalancer.hostname.com` points to a pre-configured load balancer that distributes requests to all active nodes in the cluster.
1. Copy the generated report from the `runner/reports` directory and upload it to [http://somewhere.todo.xebialabs.com](http://somewhere.todo.xebialabs.com).
1. Fill out the [form for the corresponding test set TODO](http://somewhere.todo.xebialabs.com).
