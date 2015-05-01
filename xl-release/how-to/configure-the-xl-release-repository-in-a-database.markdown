---
title: Configure the XL Release repository in a database
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
- database
---

XL Release uses a repository to store all of its data. XL Release can use the filesystem or a database to maintain the repository. By default, XL Release uses the filesystem to store all data in the repository. To use a database, the built-in Jackrabbit JCR implementation must be configured

There are several configuration options when setting up a database repository:

* Store only binary artifacts in a database. This requires you to configure the *DataStore* property.
* Store only CIs and CI history in a database. This requires you to configure the *PersistenceManager* and *FileSystem* properties.
* Store all data (binary artifacts and CIs and CI history) in a database. This requires you to configure the *DataStore*, *PersistenceManager*, and *FileSystem* properties.

Here are some examples of configuring XL Release to use a database for various database vendors. The XML snippets below must be placed in the `conf/jackrabbit-repository.xml` file.

**Note:** XL Release must initialize the repository before it can be used. Run XL Release's Setup Wizard and initialize the repository after making any changes to the repository configuration.

<!--**Note:** See **Configuring custom passwords** for information about encrypting the database password.-->

For more information about using a database with Jackrabbit, see its [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore).

## Using XL Release with [MySQL](http://www.mysql.com/)

    <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
        <param name="driver" value="com.mysql.jdbc.Driver"/>
        <param name="url" value="jdbc:mysql://localhost:3306/xlrelease"/>
        <param name="schemaObjectPrefix" value="rep_" />
        <param name="schema" value="mysql" />
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </FileSystem>

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="com.mysql.jdbc.Driver"/>
        <param name="url" value="jdbc:mysql://localhost:3306/xlrelease"/>
        <param name="databaseType" value="mysql"/>
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/xlrelease"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="mysql" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://localhost:3306/xlrelease" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
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
            <param name="url" value="jdbc:mysql://localhost:3306/xlrelease"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="mysql" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="url" value="jdbc:mysql://localhost:3306/xlrelease" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

**Note:** The MySQL database is not suited for storage of large binary objects; see [the MySQL bug tracker](http://bugs.mysql.com/bug.php?id=10859).

## Using XL Release with [DB2](http://www-01.ibm.com/software/data/db2/)

    <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
        <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
        <param name="url" value="jdbc:db2://localhost:50002/xlrelease"/>
        <param name="schemaObjectPrefix" value="rep_" />
        <param name="schema" value="db2" />
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </FileSystem>

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
        <param name="url" value="jdbc:db2://localhost:50002/xlrelease"/>
        <param name="databaseType" value="db2"/>
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/xlrelease"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="db2" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/xlrelease" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
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
            <param name="url" value="jdbc:db2://localhost:50002/xlrelease"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="db2" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/xlrelease" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
            <param name="databaseType" value="db2" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

## Using XL Release with [Oracle](http://www.oracle.com/us/products/database/index.html)

    <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
        <param name="driver" value="oracle.jdbc.OracleDriver"/>
        <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
        <param name="schemaObjectPrefix" value="rep_"/>
        <param name="schema" value="oracle" />
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </FileSystem>

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="oracle.jdbc.OracleDriver"/>
        <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
        <param name="databaseType" value="oracle"/>
        <param name="user" value="xlrelease" />
        <param name="password" value="XL Release" />
    </DataStore>

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_"/>
            <param name="schema" value="oracle" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
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
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
        </FileSystem>

        <PersistenceManager
            class="org.apache.jackrabbit.core.persistence.bundle.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/>
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
            <param name="databaseType" value="oracle" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the TNSNAMES file. See the Oracle documentation for more information.

## Clustering

It is also possible to run XL Release server with its repository shared with other XL Release server instances. In this case, you must configure the Jackrabbit JCR to run in [clustered mode](http://wiki.apache.org/jackrabbit/Clustering#Overview). This needs a cluster configuration to be present in the `jackrabbit-repository.xml` file.

### File-based repository

Add the following snippet to `jackrabbit-repository.xml` to enable clustering:

    <Cluster id="node1">
      <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
        <param name="revision" value="${rep.home}/revision.log" />
        <param name="directory" value="/nfs/myserver/myjournal" />
      </Journal>
    </Cluster>

In this example, the property `directory` refers to the shared journal. Both XL Release instances must be able to write to the same journal.

### Database repository

The following XML snippet shows a sample clustering configuration for a JCR using Oracle as its repository:

    <Cluster id="101" syncDelay="2000">
        <Journal class="org.apache.jackrabbit.core.journal.OracleDatabaseJournal">
            <param name="revision" value="${rep.home}/revision" />
            <param name="driver" value="oracle.jdbc.OracleDriver" />
            <param name="url" value="jdbc:oracle:thin:@localhost:1521:orcl" />
            <param name="user" value="xlrelease" />
            <param name="password" value="XL Release" />
            <param name="schemaObjectPrefix" value="JOURNAL_" />
        </Journal>
    </Cluster>

Note that each Jackrabbit cluster node should have a unique value for `id`. For more information about JCR clustering or ways to configure clustering using other databases, refer to the Jackrabbit [clustering documentation](http://wiki.apache.org/jackrabbit/Clustering#Overview).
