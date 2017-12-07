---
title: Configure the XL Release JCR repository in a database
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
- database
- repository
removed:
- XL Release 7.5.0
weight: 493
---

XL Release stores its data in a repository. By default, the repository is stored in an embedded Derby database at `XL_RELEASE_SERVER_HOME/repository`. However, you can choose to store binary data (artifacts), configuration items (CIs), and CI history in an external database. This topic describes an approach to configuring the built-in Jackrabbit JCR implementation to use an external database.

**Note:** If you are installing XL Release for the first time and you are using XL Release 6.0.0 or later, it is recommended that you follow the approach described in [Configure active/hot-standby](/xl-release/how-to/configure-active-hot-standby.html#step-1-configure-external-databases) instead of the approach described here. You can choose to install a stand-alone external database; you are not required to use active/hot-standby clustering.

To use a database, you must configure the built-in Jackrabbit JCR implementation, depending on what you want to store in the database:

{:.table .table-striped}
| Type of data to store in the database | Properties to configure |
| ------------------------------------- | ----------------------- |
| Only binary artifacts | `DataStore`|
| Only CIs and CI history | `PersistenceManager` and `FileSystem` |
| All data (binary artifacts and CIs and CI history) | `DataStore`, `PersistenceManager` and `FileSystem` |

For information about:

* Using a database with Jackrabbit, see the [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore)
* Backing up and restoring the database, refer to [Back up XL Release](/xl-release/how-to/back-up-xl-release.html)
* XL Release's internal archive database, refer to [Configure the archive database](/xl-release/how-to/configure-the-archive-database.html)

## Preparing the database and repository

Before installing XL Release, create an empty database. XL Release will create the database schema during installation.

The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables.

There are no requirements for the character set of the database.

**Important:** XL Release must initialize the repository before it can be used. You must run [XL Release's setup wizard](/xl-release/how-to/install-xl-release.html#run-the-server-setup-wizard) and initialize the repository after making any changes to the repository configuration.

## External databases and failover

If you take the approach described in this topic, you can optionally [create a failover configuration](/xl-release/how-to/configure-failover.html) with multiple instances of XL Release that will use the same database as your master instance. However, this is a limited setup in which only one instance of XL Release can access the database at a time.

However, if you are using XL Release 6.0.0 or later, you can take advantage of clustering in an active/hot-standby configuration, which requires a different configuration for the external database. Refer to [Configure active/hot-standby](/xl-release/how-to/configure-active-hot-standby.html) for instructions.

## Use XL Release with MySQL

This is a sample `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for [MySQL](http://www.mysql.com/):

{% highlight xml %}
<Repository>
    <Security appName="Jackrabbit">
        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />
        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
            <param name="anonymousId" value="anonymous" />
            <param name="adminId" value="admin" />
        </LoginModule>
    </Security>

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

    <Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

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
</Repository>
{% endhighlight %}

## Use XL Release with DB2

This is a sample `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for [DB2](http://www-01.ibm.com/software/data/db2/):

{% highlight xml %}
<Repository>
    <Security appName="Jackrabbit">
        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />
        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
            <param name="anonymousId" value="anonymous" />
            <param name="adminId" value="admin" />
        </LoginModule>
    </Security>

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

    <Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

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
</Repository>
{% endhighlight %}

## Use XL Release with Oracle

This is a sample `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for [Oracle](http://www.oracle.com/us/products/database/index.html):

{% highlight xml %}
<Repository>
    <Security appName="Jackrabbit">
        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />
        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
            <param name="anonymousId" value="anonymous" />
            <param name="adminId" value="admin" />
        </LoginModule>
    </Security>

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

    <Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

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
</Repository>
{% endhighlight %}

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the `TNSNAMES` file. Refer to the Oracle documentation for more information.

## Use XL Release with SQL Server

To use XL Release with [Microsoft SQL Server](https://www.microsoft.com/en-us/server-cloud/products/sql-server/), ensure that the [Microsoft JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.

Make sure the userid accessing the MS SQL Server database is a member of one of the following roles:
* db_ddladmin, db_datareader, db_datawriter for database initialization and for XL Release version upgrades
* db_datareader, db_datawriter for ongoing usage

This is a sample `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for SQL Server:

{% highlight xml %}
<Repository>

	<Security appName="Jackrabbit">
		<SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
		<AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />
		<LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
			<param name="anonymousId" value="anonymous" />
			<param name="adminId" value="admin" />
		</LoginModule>
	</Security>

	<FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
		<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
		<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr"/>
		<param name="schemaObjectPrefix" value="default_" />
		<param name="schema" value="mssql" /> <!-- warning, this is not the schema name, it is the DB type -->
		<param name="user" value="sa" />
		<param name="password" value="password" />
	</FileSystem>

	<DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
		<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
		<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr"/>
		<param name="databaseType" value="mssql"/>
		<param name="user" value="sa" />
		<param name="password" value="password" />
	</DataStore>


	<Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

	<Workspace name="${wsp.name}">
		<FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
			<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
			<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr" />
			<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
			<param name="user" value="sa" />
			<param name="password" value="password" />
			<param name="schemaObjectPrefix" value="${wsp.name}_" />
		</FileSystem>

		<PersistenceManager class ="org.apache.jackrabbit.core.persistence.bundle.MSSqlPersistenceManager">
			<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
			<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr" />
			<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
			<param name="user" value="sa" />
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
			<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr" />
			<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
			<param name="user" value="sa" />
			<param name="password" value="password" />
			<param name="schemaObjectPrefix" value="version_"/>
		</FileSystem>

		<PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.MSSqlPersistenceManager">
			<param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
			<param name="url" value="jdbc:sqlserver://192.168.0.12:1433;DatabaseName=xlr" />
			<param name="schema" value="mssql" /><!-- warning, this is not the schema name, it is the DB type -->
			<param name="user" value="sa" />
			<param name="password" value="password" />
			<param name="schemaObjectPrefix" value="version_" />
		</PersistenceManager>
	</Versioning>
</Repository>
{% endhighlight %}

For more information about SQL Server configuration for Jackrabbit, refer to the [Jackrabbit wiki](http://wiki.apache.org/jackrabbit/DataStore#Database_Data_Store). For information about the `MSSqlPersistenceManager` class, refer to the [Jackrabbit documentation](http://jackrabbit.apache.org/api/2.2/org/apache/jackrabbit/core/persistence/db/MSSqlPersistenceManager.html).

## Use XL Release with PostgreSQL

This is a sample `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for [PostgreSQL](https://www.postgresql.org/) database:

{% highlight xml %}
<Repository>
    <DataSources>
        <DataSource name="ds1">
            <param name="driver" value="org.postgresql.Driver" />
            <param name="url" value="jdbc:postgresql://*host*:*port*/*database*" />
            <param name="user" value="xlr_user" />
            <param name="password" value="test" />
            <param name="databaseType" value="postgresql" />
            <param name="maxPoolSize" value="100" />
        </DataSource>
    </DataSources>

    <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
        <param name="path" value="${rep.home}/repository" />
    </FileSystem>

    <DataStore class="org.apache.jackrabbit.core.data.FileDataStore" />

    <Security appName="Jackrabbit">
        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />

        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
            <param name="anonymousId" value="anonymous" />
            <param name="adminId" value="jcr_admin" />
        </LoginModule>
    </Security>

    <Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default" />

    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
            <param name="path" value="${wsp.home}" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.PostgreSQLPersistenceManager">
            <param name="dataSourceName" value="ds1" />
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>
        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
        </SearchIndex>
    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.local.LocalFileSystem">
            <param name="path" value="${rep.home}/version" />
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.PostgreSQLPersistenceManager">
            <param name="dataSourceName" value="ds1" />
            <param name="schemaObjectPrefix" value="pm_ver_" />
        </PersistenceManager>
    </Versioning>

    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${rep.home}/repository/index" />
    </SearchIndex>
</Repository>
{% endhighlight %}

## Use Jackrabbit clustering mode

It is also possible to run XL Release server with its repository shared with other XL Release server instances. In this case, you must configure the Jackrabbit JCR to run in [clustered mode](http://wiki.apache.org/jackrabbit/Clustering#Overview). This requires a cluster configuration to be present in the `jackrabbit-repository.xml` file.

### File-based repository

Add the following snippet to `jackrabbit-repository.xml` to enable clustering:

{% highlight xml %}
<Cluster id="node1">
  <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
    <param name="revision" value="${rep.home}/revision.log" />
    <param name="directory" value="/nfs/myserver/myjournal" />
  </Journal>
</Cluster>
{% endhighlight %}

In this example, the property `directory` refers to the shared journal. Both XL Release instances must be able to write to the same journal.

### Database repository

The following XML snippet shows a sample clustering configuration for a JCR using Oracle as its repository:

{% highlight xml %}
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
{% endhighlight %}

Note that each Jackrabbit cluster node should have a unique value for `id`. For more information about JCR clustering or ways to configure clustering using other databases, refer to the Jackrabbit [clustering documentation](http://wiki.apache.org/jackrabbit/Clustering#Overview).

## Change the repository database settings

You may occasionally need to move the database or change settings such as the database username or password (for example, to test a new release against a non-production database).

To do so, you must *manually* update the following files with the new settings:

* `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml`
* `XL_RELEASE_REPOSITORY_HOME/workspaces/default/workspace.xml`
* `XL_RELEASE_REPOSITORY_HOME/workspaces/security/workspace.xml`

**Important:** If there are additional `workspace.xml` files in the repository directory, you must also update the settings in those files.
