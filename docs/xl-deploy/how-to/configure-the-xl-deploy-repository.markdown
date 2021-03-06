---
title: Configure the XL Deploy JCR repository (XL Deploy 7.6 and earlier)
categories:
- xl-deploy
subject:
- Repository
tags:
- system administration
- repository
- database
weight: 156
removed:
- XL Deploy 8.0.0
---

<div class="alert alert-warning" style="width: 60%">
This document describes the database configuration for XL Deploy 7.6.x and earlier versions. For later versions that use the SQL repository, please refer to <a href="/xl-deploy/how-to/configure-the-xl-deploy-sql-repository.html">Configure the XL Deploy SQL repository</a> and <a href="/xl-deploy/how-to/migrate-xl-deploy-data-storage-to-an-sql-database.html">Migrate XL Deploy data storage to an SQL database</a>.
</div>

XL Deploy uses a repository to store all of its data such as configuration items (CIs), deployment packages, logging, etc. XL Deploy can use the filesystem or a database for binary artifacts (deployment packages) and CIs and CI history. By default, XL Deploy uses the filesystem to store all data in the repository.

## Location of the repository

By default, the repository is located in `XL_DEPLOY_SERVER_HOME/repository`. To change the location, change the value of `jcr.repository.path` in `XL_DEPLOY_SERVER_HOME/conf/deployit.conf`. For example:

    jcr.repository.path=file://opt/xldeploy/repository

## Using a database

XL Deploy can also use a database to store its repository. To use a database, you must configure the built-in Jackrabbit JCR implementation, depending on what you want to store in the database:

{:.table .table-striped}
| Type of data to store in the database | Properties to configure |
| ------------------------------------- | ----------------------- |
| Only binary artifacts | `DataStore`|
| Only CIs and CI history | `PersistenceManager` and `FileSystem` |
| All data (binary artifacts and CIs and CI history) | `DataStore`, `PersistenceManager` and `FileSystem` |

For information about:

* Using a database with Jackrabbit, see the [PersistenceManager FAQ](http://wiki.apache.org/jackrabbit/PersistenceManagerFAQ) and [DataStore FAQ](http://wiki.apache.org/jackrabbit/DataStore)
* Backing up the database, refer to [Back up XL Deploy](/xl-deploy/how-to/back-up-xl-deploy.html)

**Note:** If you are installing XL Deploy for the first time and you are using XL Deploy 7.1.0 or later, it is recommended that you follow the approach described in [Configure active/hot-standby](/xl-deploy/how-to/configure-xl-deploy-active-hot-standby.html) instead of the approach described here. You can choose to install a stand-alone external database; you are not required to use active/hot-standby clustering.

### Preparing the database and repository

Before installing XL Deploy, create an empty database. XL Deploy will create the database schema during installation.

The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables.

There are no requirements for the character set of the database.

**Important:** XL Deploy must initialize the repository before it can be used. You must run [XL Deploy's setup wizard](/xl-deploy/how-to/install-xl-deploy.html#run-the-server-setup-wizard) and initialize the repository after making any changes to the repository configuration.

### Using XL Deploy with MySQL

To use XL Deploy with [MySQL](http://www.mysql.com/), ensure that the [JDBC driver for MySQL](http://dev.mysql.com/downloads/connector/j/) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

Make sure the userid accessing the MySQL database has been granted the following permissions:
* GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, and INDEX on `dbname`.* to *dbuser*@*host* for database initialization and for XL Deploy version upgrades
* GRANT SELECT, INSERT, UPDATE, and DELETE on `dbname`.* to *dbuser*@*host* for ongoing usage

This is a sample `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for MySQL:

{% highlight xml %}
<?xml version="1.0"?>
<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>

    <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
           <param name="driver" value="com.mysql.jdbc.Driver"/>
           <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld"/>
           <param name="schemaObjectPrefix" value="fs_" />
           <param name="schema" value="mysql" />
           <param name="user" value="xld_user" />
           <param name="password" value="test" />
        </FileSystem>

        <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
         <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld"/>
            <param name="databaseType" value="mysql"/>
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </DataStore>

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
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="mysql" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="driver" value="com.mysql.jdbc.Driver"/>
            <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
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
            <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="mysql" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MySqlPersistenceManager">
            <param name="url" value="jdbc:mysql://192.168.0.50:3306/xld" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${rep.home}/repository/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>

</Repository>
{% endhighlight %}

{% comment %}
**Note:** The MySQL database is not suited for storage of large binary objects; see [the MySQL bug tracker](http://bugs.mysql.com/bug.php?id=10859).
{% endcomment %}

### Using XL Deploy with DB2

To use XL Deploy with [IBM DB2](http://www-01.ibm.com/software/data/db2/), ensure that the [JDBC driver for DB2](http://www-01.ibm.com/support/docview.wss?uid=swg21363866) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

This is a sample `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for DB2:

{% highlight xml %}
<?xml version="1.0"?>
<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>          

    <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
           <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
           <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
           <param name="schemaObjectPrefix" value="${wsp.name}_" />
           <param name="schema" value="db2" />
           <param name="user" value="xld_user" />
           <param name="password" value="test" />
        </FileSystem>

        <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
         <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="databaseType" value="db2"/>
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </DataStore>

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
        <FileSystem class="org.apache.jackrabbit.core.fs.db.DbFileSystem">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="db2" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="driver" value="com.ibm.db2.jcc.DB2Driver"/>
            <param name="url" value="jdbc:db2://localhost:50002/deployit" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
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
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.BundleDbPersistenceManager">
            <param name="url" value="jdbc:db2://localhost:50002/deployit" />
            <param name="user" value="xld_user" />
            <param name="password" value="test" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${rep.home}/repository/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>

</Repository>
{% endhighlight %}

### Using XL Deploy with Oracle

To use XL Deploy with [Oracle](http://www.oracle.com/us/products/database/index.html), ensure that the [JDBC driver for Oracle](http://www.oracle.com/technetwork/database/features/jdbc/default-2280470.html) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

This is a sample `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for Oracle:

{% highlight xml %}
<?xml version="1.0"?>
<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>
    <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
       <param name="driver" value="oracle.jdbc.OracleDriver"/>
       <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522:1521/XLD"/>
       <param name="schemaObjectPrefix" value="fs_" />
       <param name="schema" value="oracle" />
       <param name="user" value="deployit" />
       <param name="password" value="deployit" />
    </FileSystem>

    <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
        <param name="driver" value="oracle.jdbc.OracleDriver"/>
        <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522/XLD"/>
        <param name="databaseType" value="oracle"/>
        <param name="user" value="deployit" />
        <param name="password" value="deployit" />
    </DataStore>

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
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522/XLD"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522/XLD"/>
            <param name="databaseType" value="oracle" />
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
        <FileSystem class="org.apache.jackrabbit.core.fs.db.OracleFileSystem">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522/XLD"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.OraclePersistenceManager">
            <param name="driver" value="oracle.jdbc.OracleDriver"/>
            <param name="url" value="jdbc:oracle:thin:@ABCD1234:1522/XLD"/>
            <param name="databaseType" value="oracle" />
            <param name="user" value="deployit" />
            <param name="password" value="deployit" />
            <param name="schemaObjectPrefix" value="version_" />
        </PersistenceManager>
    </Versioning>

    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${rep.home}/repository/index" />
        <param name="supportHighlighting" value="true" />
    </SearchIndex>
</Repository>
{% endhighlight %}

**Note:** If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the `TNSNAMES` file. Refer to the Oracle documentation for more information.

### Using XL Deploy with SQL Server

To use XL Deploy with [Microsoft SQL Server](https://www.microsoft.com/en-us/server-cloud/products/sql-server/), ensure that the [JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

This is a sample `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for SQL Server:

{% highlight xml %}
<?xml version="1.0"?>
<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>
   <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
           <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
           <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD"/>
           <param name="schemaObjectPrefix" value="fs_" />
           <param name="schema" value="mssql" /> <!-- warning, this is not the schema name, it is the DB type -->
           <param name="user" value="sa" />
           <param name="password" value="PASS" />
        </FileSystem>

        <DataStore class="org.apache.jackrabbit.core.data.db.DbDataStore">
         <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
            <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD"/>
            <param name="databaseType" value="mssql"/>
            <param name="user" value="sa" />
            <param name="password" value="PASS" />
        </DataStore>

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
            <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
            <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
            <param name="schema" value="mssql" />
            <param name="user" value="sa" />
            <param name="password" value="PASS" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MSSqlPersistenceManager">
            <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
            <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD" />
            <param name="user" value="sa" />
            <param name="password" value="PASS" />
			<param name="databaseType" value="mssql"/>
            <param name="schemaObjectPrefix" value="${wsp.name}_" />
        </PersistenceManager>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>

    </Workspace>

    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.db.MSSqlFileSystem">
            <param name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver"/>
            <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD"/>
            <param name="schemaObjectPrefix" value="version_" />
            <param name="schema" value="mssql" />
            <param name="user" value="sa" />
            <param name="password" value="PASS" />
        </FileSystem>

        <PersistenceManager class="org.apache.jackrabbit.core.persistence.pool.MSSqlPersistenceManager">
            <param name="url" value="jdbc:sqlserver://15.51.45.218:4516;DatabaseName=XLD" />
            <param name="user" value="sa" />
            <param name="password" value="PASS" />
            <param name="schemaObjectPrefix" value="version_" />
			<param name="databaseType" value="mssql"/>
        </PersistenceManager>
    </Versioning>

        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${rep.home}/repository/index" />
            <param name="supportHighlighting" value="true" />
        </SearchIndex>
</Repository>
{% endhighlight %}

For more information about SQL Server configuration for Jackrabbit, refer to the [Jackrabbit wiki](http://wiki.apache.org/jackrabbit/DataStore#Database_Data_Store). For information about the `MSSqlPersistenceManager` class, refer to the [Jackrabbit documentation](http://jackrabbit.apache.org/api/2.2/org/apache/jackrabbit/core/persistence/db/MSSqlPersistenceManager.html).

## Use XL Deploy with PostgreSQL

This is a sample `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml` configuration for [PostgreSQL](https://www.postgresql.org/) database:

{% highlight xml %}
<?xml version="1.0"?>
<!DOCTYPE Repository
          PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
          "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>
    <DataSources>
        <DataSource name="ds1">
            <param name="driver" value="org.postgresql.Driver" />
            <param name="url" value="jdbc:postgresql://*host*:*port*/*database*" />
            <param name="user" value="xld_user" />
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

### Moving the database or changing settings

If you store the XL Deploy repository in a database, you may occasionally need to move the database or change settings such as the database username or password (for example, to test a new release against a non-production database).

To do so, you must *manually* update the following files with the new settings:

* `XL_DEPLOY_SERVER_HOME/conf/jackrabbit-repository.xml`
* `XL_DEPLOY_REPOSITORY_HOME/workspaces/default/workspace.xml`
* `XL_DEPLOY_REPOSITORY_HOME/workspaces/security/workspace.xml`

**Important:** If there are additional `workspace.xml` files in the repository directory, you must also update the settings in those files.
