---
title: Configure failover for XL Release
removed:
XL Release 7.5.0
---

<div class="alert alert-warning" style="width: 60%">This topic describes procedures that are valid only for XL Release versions pre 6.0.0. For XL Release versions 6.0.0 to 7.2.x, you can find the related topic <a href="/xl-release/how-to/configure-active-hot-standby.html">here</a>. For XL Release versions 7.5.0 and later, you can find the related topic <a href="/xl-release/how-to/configure-cluster.html">here</a>.</div>

XL Release allows you to store its [repository](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) and [archive database](/xl-release/how-to/configure-the-archive-database.html) in an external database instead of on the filesystem. If you use a database, then you can set up failover handling by creating additional instances of XL Release that will use the same database as your master instance. Note that this is not an active/active setup; only one instance of XL Release can access the database at a time.

**Tip:** If you are using XL Release 6.0.0 or later, you can use the failover approach described in this topic, or you can use an [active/hot-standby configuration](/xl-release/how-to/configure-active-hot-standby.html).

## Initial setup

To set up a master node (called `node1`) and a failover node (called `node2`):

1. Follow the instructions to configure the XL Release [repository](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) and [archive database](/xl-release/how-to/configure-the-archive-database.html#change-the-archive-database-dbms-xl-release-480-and-later) on `node1`.
2. Add a `Cluster` section to the `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` file:

        <Cluster id="node1" syncDelay="2000">
            <Journal class="org.apache.jackrabbit.core.journal.MSSqlDatabaseJournal">
                <param name="revision" value="${rep.home}/revision" />
                <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
                <param name="url" value="jdbc:sqlserver://192.168.0.150:64088;DatabaseName=XLR" />
                <param name="user" value="sa" />
                <param name="password" value="pass123" />
                <param name="schemaObjectPrefix" value="JOURNAL_" />
            </Journal>
        </Cluster>

    Configure the properties for the type of database you are using (this example is based on Microsoft SQL Server).

3. [Start the XL Release server](/xl-release/how-to/start-xl-release.html) and verify that it starts without errors. Create at least one new template or release for testing purposes (you will check for this item on `node2`).
4. Stop the server.
5. Copy the entire installation folder (`XL_RELEASE_SERVER_HOME`) to `node2`.

    **Important:** Both nodes must use the same [Java version](/xl-release/concept/requirements-for-installing-xl-release.html#server-requirements).

6. In `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml`, change the `Cluster id`. Do not change any other properties. For example:

        <Cluster id="node2" syncDelay="2000">
            <Journal class="org.apache.jackrabbit.core.journal.MSSqlDatabaseJournal">
                <param name="revision" value="${rep.home}/revision" />
                <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
                <param name="url" value="jdbc:sqlserver://192.168.0.150:64088;DatabaseName=XLR" />
                <param name="user" value="sa" />
                <param name="password" value="pass123" />
                <param name="schemaObjectPrefix" value="JOURNAL_" />
            </Journal>
        </Cluster>

    Do not change the archive database configuration in the `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` file.

7. Delete the contents of the `XL_RELEASE_SERVER_HOME/repository` directory. **Important:** Only do this once, during the initial setup of the instance.
8. Start the XL Release server and verify that you can see the items that you created on `node1`.
9. Stop the server.
10. Start the server on `node1`.

## Switching to another node

When the master node (`node1`) fails, manually start the XL Release server on the failover node (`node2`). The server will retrieve the database journal during startup; note that if it has been shut down for a long time, this process may take several minutes.

If you want to switch back to the master node after it recovers, you must first shut down XL Release on the failover node. Multiple nodes cannot access the database at the same time.
