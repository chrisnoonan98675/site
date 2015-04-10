---
title: Configure the XL Deploy repository
categories:
- xl-deploy
subject:
- Repository
tags:
- system administration
- repository
- database
---

XL Deploy uses a repository to store all of its data such as configuration items (CIs), deployment packages, logging, etc. XL Deploy can use the filesystem or a database for binary artifacts (deployment packages) and CIs and CI history.

Out of the box, XL Deploy uses the filesystem to store all data in the repository.

## Location of the repository

By default, the repository is located in `XLDEPLOY_SERVER_HOME/repository`. To change the location, change the value of `jcr.repository.path` in `XLDEPLOY_SERVER_HOME/conf/deployit.conf`. For example:

      jcr.repository.path=file://opt/xldeploy/repository

## Using a database

XL Deploy can also use a database to store its repository. The built-in Jackrabbit JCR implementation must be configured to make this possible.

There are several configuration options when setting up a database repository:

* Store **only binary artifacts** in a database. This requires configuring the `DataStore` property.
* Store **only CIs and CI history** in a database. This requires configuring the `PersistenceManager` and `FileSystem` properties.
* Store **all data** (binary artifacts and CIs and CI history) in a database. This requires configuring the `DataStore`, `PersistenceManager` and `FileSystem` must be configured.

Here are some examples of configuring XL Deploy to use a database for various database vendors. The XML snippets below must be put into the `conf/jackrabbit-repository.xml` file.

**Note:** XL Deploy **must** initialize the repository before it can be used. Run XL Deploy's setup wizard and initialize the repository after making any changes to the repository configuration.

For more information about using a database with Jackrabbit, see the [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore).

#### Using XL Deploy with MySQL

This is an example of configuring XL Deploy to use [MySQL](http://www.mysql.com/):

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="com.mysql.jdbc.Driver"/>
        <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
        <param name="databaseType" value="mysql"/>
        <param name="user" value="deployit" />
        <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="mysql" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>

    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/deployit"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="mysql" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="url" value="jdbc:mysql://localhost:3306/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

**Note:** The MySQL database is not suited for storage of large binary objects. See [the MySQL bug tracker](http://bugs.mysql.com/bug.php?id=10859).

### Using XL Deploy with DB2

This is an example of configuring XL Deploy to use [DB2](http://www-01.ibm.com/software/data/db2/):

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="databaseType" value="db2"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
                <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
                <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
                <param name="schemaObjectPrefix" value="${wsp.name}_" />
                <param name="schema" value="db2" />
                <param name="user" value="deployit" />
                <param name="password" value="deployit" />
        </FileSystem>

         <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
                <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
                <param name="url" value="jdbc:db2://localhost:50002/deployit" />
                <param name="user" value="deployit" />
                <param name="password" value="deployit" />
                <param name="databaseType" value="db2" />
                <param name="schemaObjectPrefix" value="${wsp.name}_" />
             </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>
    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="db2" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="db2" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

### Using XL Deploy with Oracle

This is an example of configuring XL Deploy to use [Oracle](http://www.oracle.com/us/products/database/index.html):

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="oracle.jdbc.OracleDriver"/>
        <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
        <param name="databaseType" value="oracle"/>
        <param name="user" value="deployit" />
        <param name="password" value="deployit" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_"/>
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.driver.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="oracle" />
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>
    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="schemaObjectPrefix" value="version_"/>
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.driver.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="databaseType" value="oracle" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the TNSNAMES file. See the Oracle documentation for more information.

## Clustering

It is also possible to run XL Deploy server with its repository shared with other XL Deploy server instances. For this to happen, the jackrabbit JCR must be configured to run in a [clustered mode](http://wiki.apache.org/jackrabbit/Clustering#Overview). This needs a cluster configuration to be present in the `jackrabbit-repository.xml` file.

### File-based repository

Add the following snippet to the `jackrabbit-repository.xml` to enable clustering:

    <Cluster id="node1">
      <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
        <param name="revision" value="${rep.home}/revision.log" />
        <param name="directory" value="/nfs/myserver/myjournal" />
      </Journal>
    </Cluster>

In the above example, the `directory` property refers to the shared journal. Both XL Deploy instances must be able to write to the same journal.

### Database repository

The following XML snippet shows a sample clustering configuration for a JCR using Oracle as its repository.

    <Cluster id="101" syncDelay="2000">
        <Journal class="org.apache.jackrabbit.core.journal.OracleDatabaseJournal">
            <param name="revision" value="${rep.home}/revision" />
            <param name="driver" value="oracle.jdbc.driver.OracleDriver" />
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="JOURNAL_" />
        </Journal>
    </Cluster>

Note that each Jackrabbit cluster node should have a unique value for `id`. For more information on JCR clustering, or ways to configure clustering using other databases, please refer to the Jackrabbit [clustering documentation](http://wiki.apache.org/jackrabbit/Clustering#Overview).
