---
title: High availability setup for MySQL, with clustering
author: mark_ravech
categories:
- xl-deploy
tags:
- middleware
- database
---

XL Deploy clusters cannot run in an active/active setup. Only one node can be running at a time.

Database repositories and clustering are configured in `jackrabbit-repository.xml`, under `<XLDEPLOY_HOME>/conf`.

You can download an open-source version of MySQL Community Server [here](http://dev.mysql.com/downloads/mysql/).

## Scenario 1: Single-node XL Deploy

1. Extract the XL Deploy ZIP file.
1. Copy [the `jackrabbit-repository_mySqlNoCluster.xml` file](/sample-scripts/high-availability-setup-for-xl-deploy/jackrabbit-repository_mySqlNoCluster.xml) to `jackrabbit-repository.xml` under `<XLDEPLOY_HOME>/conf`. Modify it as needed.
1. Copy the MySQL database driver (for example, `mysql-connector-java-5.1.33-bin.jar`) under `<XLDEPLOY_HOME>/lib`.
1. Open the MySQL Workbench or use the command-line tool `mysql`.
1. Create a new schema/database named` deployit` (the name can be anything, but it must match `jackrabbit-repository.xml`). For example: `<param name="url" value="jdbc:mysql://localhost:3306/deployit"/>`, database name = `deployit`.
1. Run the setup. XL Deploy creates tables in the MySQL database.

## Scenario 2: Two-node XL Deploy

1. Extract the XL Deploy ZIP file on both nodes.
1. Copy [the `jackrabbit-repository_mySqlClusterNode1.xml` file](/sample-scripts/high-availability-setup-for-xl-deploy/jackrabbit-repository_mySqlClusterNode1.xml) and [the `jackrabbit-repository_mySqlClusterNode2.xml` file](/sample-scripts/high-availability-setup-for-xl-deploy/jackrabbit-repository_mySqlClusterNode2.xml) to `jackrabbit-repository.xml` under `<XLDEPLOY_HOME>/conf` to each node respectively. Modify them as needed.
1. Copy the MySQL database driver (for example, `mysql-connector-java-5.1.33-bin.jar`) under `<XLDEPLOY_HOME>/lib` on both nodes.
1. Open the MySQL Workbench or use the command-line tool `mysql`.
1. Create new schemas/databases named `deployit`, `deployit_node1`, and `deployit_node2` (the names can be anything, but they must match `jackrabbit-repository.xml`).
1. Run the setup. XL Deploy creates tables in the MySQL database on node1 only.
1. Shut down XL Deploy on node1.
1. Run `select * from deployit.journal_local_revisions` to get the current revision number of node1 from your database.
1. Copy `<XLDEPLOY_HOME>/repository` from node1 to node2.
1. Add node2 name to your database in `JOURNAL_LOCAL_REVISIONS`, including the revision number from node1 (`insert into deployit.journal_local_revisions (JOURNAL_ID,REVISION_ID) VALUES ("node2",44)`).
1. Start node1 and create a new package.
1. Stop node1.
1. Start node2 and verify that the newly created package on node1 appears correctly.
