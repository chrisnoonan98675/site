# Setting up active/active mode for XL Release 7.5.0

## Things to consider

* We assume you have set up the server following the instructions in **Setting up XL Release 7.5.0 with active releases in SQL database**


## Cluster set up

Instructions are based on [Configure active/hot-standby mode](https://docs.xebialabs.com/xl-release/how-to/configure-active-hot-standby.html). 

Follow the instructions in the above document, but do things differently according to the steps outlined below:

### Step 1. (Configure external databases)

Follow the instructions in  **Setting up XL Release 7.5.0 with active releases in SQL database** 

### Step 2. (Set up the cluster)

* The cluster setting for active/active is `xl.cluster.mode=full`
* Attachments are stored in SQL now, so we don't have to worry about `xl.repository.jackrabbit.artifacts.location`
* We need to specify an additional property `xl.cluster.akka.cluster.seed-nodes`. This property contains a list of servers that server as 'seed nodes'. At least one of these servers must be running at all times in order of the cluster to be alive. Additional servers can be added to the cluster that are not seed nodes. 

In my example setup there are two nodes, both running on the host `HesBook.local`, but on different ports. Note that apart from the http port, each server also has a clusterPort which is different.

This gives us the following example setup for the cluster section in the `xl-release.conf` file:

```
xl {
    cluster {
        mode = full
        name = "xlr_cluster"
        node {
            id = server1
            hostname = HesBook.local
            clusterPort = 5531		
        }
        akka.cluster.seed-nodes = [
            "akka.tcp://xlr_cluster@HesBook.local:5531",
            "akka.tcp://xlr_cluster@HesBook.local:5532"
        ]
    }
    database {
        ...
    }
    reporting {
        ...
    }
}
```



### Step 3. (Set up the first node)

Not clear if this step is needed when the database is already created.

### Step 4. (Prepare each node in the cluster)

Here's the example configuration for the second node:

```
xl {
    cluster {
        mode = full
        name = "xlr_cluster"
        node {
            id = server2
            hostname = HesBook.local
            clusterPort = 5532
        }
        akka.cluster.seed-nodes = [
            "akka.tcp://xlr_cluster@HesBook.local:5531",
            "akka.tcp://xlr_cluster@HesBook.local:5532"
        ]
    }
    database {
        ...
    }
    reporting {
        ...
    }
    repository {
        ...
    }
}
```

In active/active mode you can reach the individual servers directly, so you can start them up already to see if they work.

### Step 5. (Set up the load balancer)

The load balancer configuration is simpler because there is no failover scenario involved. The load balancer just needs to divide the traffic between the different nodes. One thins to take into account though is to use 'sticky sessions'; wihtin a user session, the user should always be directed to the same node.

The example HA PRoxy configuration is largely the same, except for the final section.

*Active / Hot stand-by snippet*:

```
backend default_service
  option httpchk head /ha/health HTTP/1.0
  server docker_xlr-node_1 docker_xlr-node_1:5516 check inter 2000 rise 2 fall 3
  server docker_xlr-seed_1 docker_xlr-seed_1:5516 check inter 2000 rise 2 fall 3
```

is replaced by

*Active / Active snippet*:

```
backend default_service
  cookie JSESSIONID prefix
  server server1 HesBook.local:5516 cookie server1 check port 5516
  server server2 HesBook.local:5517 cookie server2 check port 5517
```

### Step 6. (Start the nodes)

Here's a snippet of the log file of a node starting up successfully in the cluster (with a seed node alread running).

```
[INFO] [10/06/2017 16:01:56.770] [main] [akka.cluster.Cluster(akka://xlr_cluster)] Cluster Node [akka.tcp://xlr_cluster@HesBook.local:5532] - Starting up...
[INFO] [10/06/2017 16:01:56.830] [main] [akka.cluster.Cluster(akka://xlr_cluster)] Cluster Node [akka.tcp://xlr_cluster@HesBook.local:5532] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [10/06/2017 16:01:56.830] [main] [akka.cluster.Cluster(akka://xlr_cluster)] Cluster Node [akka.tcp://xlr_cluster@HesBook.local:5532] - Started up successfully
[WARN] [10/06/2017 16:01:56.853] [xlr_cluster-akka.actor.default-dispatcher-3] [akka.tcp://xlr_cluster@HesBook.local:5532/system/cluster/core/daemon/downingProvider] Don't use auto-down feature of Akka Cluster in production. See 'Auto-downing (DO NOT USE)' section of Akka Cluster documentation.
2017-10-06 16:01:57.018 [xlr_cluster-akka.actor.default-dispatcher-18] {} INFO  c.x.x.a.sharding.ReleaseWatcherActor - Starting ReleaseWatcherActor on akka.tcp://xlr_cluster@HesBook.local:5532 at akka://xlr_cluster/user/release-watcher
[INFO] [10/06/2017 16:01:57.302] [xlr_cluster-akka.actor.default-dispatcher-2] [akka.cluster.Cluster(akka://xlr_cluster)] Cluster Node [akka.tcp://xlr_cluster@HesBook.local:5532] - Welcome from [akka.tcp://xlr_cluster@HesBook.local:5531]
[INFO] [10/06/2017 16:01:57.907] [xlr_cluster-akka.actor.default-dispatcher-23] [akka.tcp://xlr_cluster@HesBook.local:5532/system/sharding/releaseCoordinator] ClusterSingletonManager state change [Start -> Younger]
[INFO] [10/06/2017 16:01:57.907] [xlr_cluster-akka.actor.default-dispatcher-28] [akka.tcp://xlr_cluster@HesBook.local:5532/user/archiving] ClusterSingletonManager state change [Start -> Younger]
```

