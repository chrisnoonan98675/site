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

XL Deploy can also use a database to store its repository. To use a database, you must configure the built-in Jackrabbit JCR implementation, depending on what you want to store in the database:

{:.table .table-striped}
| Type of data to store in the database | Properties to configure |
| ------------------------------------- | ----------------------- |
| Only binary artifacts | `DataStore`|
| Only CIs and CI history | `PersistenceManager` and `FileSystem` |
| All data (binary artifacts and CIs and CI history) | `DataStore`, `PersistenceManager` and `FileSystem` |

**Note:** XL Deploy must initialize the repository before it can be used. Run XL Deploy's setup wizard and initialize the repository after making any changes to the repository configuration.

For more information about using a database with Jackrabbit, see the [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore).

For information about changing the configuration, refer to [Change the repository database settings](/xl-deploy/how-to/change-the-repository-database-settings.html).

### Using XL Deploy with MySQL

This is a sample `<XLDEPLOY_HOME>/conf/jackrabbit-repository.xml` configuration for [MySQL](http://www.mysql.com/):

{% highlight xml %}
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
{% endhighlight %}

**Note:** The MySQL database is not suited for storage of large binary objects; see [the MySQL bug tracker](http://bugs.mysql.com/bug.php?id=10859).

### Using XL Deploy with DB2

This is a sample `<XLDEPLOY_HOME>/conf/jackrabbit-repository.xml` configuration for [DB2](http://www-01.ibm.com/software/data/db2/):

{% highlight xml %}
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
{% endhighlight %}

### Using XL Deploy with Oracle

This is a sample `<XLDEPLOY_HOME>/conf/jackrabbit-repository.xml` configuration for [Oracle](http://www.oracle.com/us/products/database/index.html):

{% highlight xml %}
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
{% endhighlight %}

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the `TNSNAMES` file. Refer to the Oracle documentation for more information.

### Using XL Deploy with SQL Server

To use XL Deploy with [Microsoft SQL Server](https://www.microsoft.com/en-us/server-cloud/products/sql-server/), ensure that the [Microsoft JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx) JAR file is located in `<XLDEPLOY_HOME>/lib` or on the Java classpath.

This is a sample `<XLDEPLOY_HOME>/conf/jackrabbit-repository.xml` configuration for SQL Server:

{% highlight xml %}
<DataStore class="org.apache.jackrabbit.core.data.FileDataStore" />

<Security appName="Jackrabbit">
    <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
    <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />
    <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
        <param name="anonymousId" value="anonymous" />
        <param name="adminId" value="admin" />
    </LoginModule>
</Security>

<Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

<Workspace name="${wsp.name}">
    <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
      <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
		<param name="url" value="jdbc:sqlserver://sqlservername:1433;DatabaseName=XLDeploy" />
		<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
		<param name="user" value="username" />
		<param name="password" value="password" />
		<param name="schemaObjectPrefix" value="${wsp.name}_" />
    </FileSystem>

    <PersistenceManager class ="org.apache.jackrabbit.core.persistence.bundle.MSSqlPersistenceManager">
		<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
		<param name="url" value="jdbc:sqlserver://sqlservername:1433;DatabaseName=XLDeploy" />
		<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
		<param name="user" value="username" />
		<param name="password" value="password" />
		<param name="schemaObjectPrefix" value="${wsp.name}_" />
    </PersistenceManager>

    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${wsp.home}/index" />
        <param name="supportHighlighting" value="true" />
    </SearchIndex>
</Workspace>

<Versioning rootPath="${rep.home}/version">
    <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
      <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
		<param name="url" value="jdbc:sqlserver://sqlservername:1433;DatabaseName=XLDeploy" />
		<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
		<param name="user" value="username" />
		<param name="password" value="password" />
		<param name="schemaObjectPrefix" value="version_"/>
    </FileSystem>

    <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.MSSqlPersistenceManager">
      <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
		<param name="url" value="jdbc:sqlserver://sqlservername:1433;DatabaseName=XLDeploy" />
		<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
		<param name="user" value="username" />
		<param name="password" value="password" />
		<param name="schemaObjectPrefix" value="version_" />
    </PersistenceManager>
</Versioning>
{% endhighlight %}

For more information about SQL Server configuration for Jackrabbit, refer to the [Jackrabbit wiki](http://wiki.apache.org/jackrabbit/DataStore#Database_Data_Store). For information about the `MSSqlPersistenceManager` class, refer to the [Jackrabbit documentation](http://jackrabbit.apache.org/api/2.2/org/apache/jackrabbit/core/persistence/db/MSSqlPersistenceManager.html).

## Clustering

It is also possible to run XL Deploy server with its repository shared with other XL Deploy server instances. For this to happen, the jackrabbit JCR must be configured to run in a [clustered mode](http://wiki.apache.org/jackrabbit/Clustering#Overview). This needs a cluster configuration to be present in the `jackrabbit-repository.xml` file.

### File-based repository

Add the following code to the `jackrabbit-repository.xml` to enable clustering:

{% highlight xml %}
<Cluster id="node1">
  <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
    <param name="revision" value="${rep.home}/revision.log" />
    <param name="directory" value="/nfs/myserver/myjournal" />
  </Journal>
</Cluster>
{% endhighlight %}

In the above example, the `directory` property refers to the shared journal. Both XL Deploy instances must be able to write to the same journal.

### Database repository

The following example shows a sample clustering configuration for a JCR using Oracle as its repository.

{% highlight xml %}
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
{% endhighlight %}

Note that each Jackrabbit cluster node should have a unique value for `id`. For more information on JCR clustering, or ways to configure clustering using other databases, please refer to the Jackrabbit [clustering documentation](http://wiki.apache.org/jackrabbit/Clustering#Overview).
