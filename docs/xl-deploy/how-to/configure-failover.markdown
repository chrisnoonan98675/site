---
title: Configure failover for XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- installation
- failover
- database
- active/passive
---

XL Deploy allows you to store the [repository](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database) in a relational database instead of on the filesystem. If you use a database, then you can set up failover handling by creating additional instances of XL Deploy that will use the same database as your master instance. Note that this is not an active/active setup; only one instance of XL Deploy can access the database at a time.

**Important:** Both nodes must use the same [Java version](/xl-deploy/concept/requirements-for-installing-xl-deploy.html#server-requirements).

**Tip:** If you are using XL Deploy 7.1.0 or later, you can use the failover approach described in this topic, or you can use an active/hot-standby configuration.

## Initial setup

To set up a master node (called `node1`) and a failover node (called `node2`):

1. Follow the instructions to configure the XL Deploy [repository](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database) on `node1`.
2. Add a `Cluster` section to the `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` file:

        <Cluster id="node1" syncDelay="2000">
            <Journal class="org.apache.jackrabbit.core.journal.MSSqlDatabaseJournal">
                <param name="revision" value="${rep.home}/revision" />
                <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
                <param name="url" value="jdbc:sqlserver://192.168.0.150:64088;DatabaseName=XLD" />
                <param name="user" value="sa" />
                <param name="password" value="pass123" />
                <param name="schemaObjectPrefix" value="JOURNAL_" />
            </Journal>
        </Cluster>

    Configure the properties for the type of database you are using (this example is based on Microsoft SQL Server).

3. [Start the XL Deploy server](/xl-deploy/how-to/start-xl-deploy.html) and verify that it starts without errors. Create at least one configuration item for testing purposes (you will check for this item on `node2`).
4. Stop the server.
5. Copy the entire installation folder (`XL_DEPLOY_SERVER_HOME`) to `node2`.
6. In `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml`, change the `Cluster id`. Do not change any other properties. For example:

        <Cluster id="node2" syncDelay="2000">
            <Journal class="org.apache.jackrabbit.core.journal.MSSqlDatabaseJournal">
                <param name="revision" value="${rep.home}/revision" />
                <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
                <param name="url" value="jdbc:sqlserver://192.168.0.150:64088;DatabaseName=XLD" />
                <param name="user" value="sa" />
                <param name="password" value="pass123" />
                <param name="schemaObjectPrefix" value="JOURNAL_" />
            </Journal>
        </Cluster>

7. Delete the contents of the `XL_DEPLOY_SERVER_HOME/repository` directory. **Important:** Only do this once, during the initial setup of the instance.
8. Start the XL Deploy server and verify that you can see the items that you created on `node1`.
9. Stop the server.
10. Start the server on `node1`.

## Switching to another node

When the master node (`node1`) fails, you must manually start the XL Deploy server on the failover node (`node2`). If there were pending or running tasks on the master node, first copy the contents of its `XL_DEPLOY_SERVER_HOME/work` directory to the failover node. The failover node will attempt to recover the tasks.

After you start XL Deploy on the failover node, it will retrieve the database journal during startup; note that if if has been shut down for a long time, this process may take several minutes.

If you want to switch back to the master node after it recovers, you must first shut down XL Deploy on the failover node. Multiple nodes cannot access the database at the same time.
