= Configure the SQL repository in a database (XL Release 7.5.x and later)
:sectnums:
:page-liquid:
:page-categories: [xl-release]
:page-subject: System administration
:page-tags: [system administration, setup, installation, database, repository, sql]
:page-since: [XL Release 7.5.0]
:page-weight: 493

== Introduction

IMPORTANT: This document describes the database configuration for XL Release 7.5.x and later versions. For previous versions that use the JackRabbit (JCR) repository, refer to link:configure-the-xl-release-repository-in-a-database.html[Configure the XL Release JCR repository in a database].

XL Release stores its data in a repository. By default, this repository is an embedded database stored in `XL_RELEASE_SERVER_HOME/repository`. Completed releases and reporting information are stored in another database called _archive_. By default, this is also an embedded database stored in `XL_RELEASE_SERVER_HOME/archive`. The embedded databases are automatically created when XL Release is started for the first time.

The embedded databases provide an easy way to set up XL Release for evaluation or in a test environment. However, for production use, it is strongly recommended that you use an industrial-grade external database server. Storing the repository in an external database server is also required to run XL Release in a cluster setup (active/active or active/hot standby).

The following databases are supported:

* PostgreSQL versions 9.3, 9.4, 9.5, 9.6, and 10.1
* MySQL versions 5.5, 5.6, and 5.7
* Oracle 11g
* Microsoft SQL Server 2012 and later
* DB2 versions 10.5 and 11.1

NOTE: The repository database and the archive database must not reside in the same database on the database server.

You cannot migrate the repository from an embedded database to an external database. Ensure that you configure production setup with an external database from the start. When upgrading from a JCR-based version of XL Release (version 7.2.x or earlier), ensure that you migrate to an external database. For information about upgrading from XL Release 7.2.x or earlier, refer to link:upgrade-to-7.5.0.html[Upgrade to XL Release 7.5.x] for detailed instructions.

More information:

* Active/hot standby mode: link:configure-active-hot-standby.html[Configure active/hot-standby]
* Cluster mode: link:configure-cluster.html[Configure cluster mode]
* Backing up and restore: link:back-up-xl-release.html[Back up XL Release]

== Preparing the external database

To use an external database, create the following empty database schemas:

* `xlrelease` Will contain active release data and configuration data.
* `xlarchive` Will contain completed releases and reporting data.

TIP: If you migrate from a previous version of XL Release with the archive configured in the `archive` directory as an embedded database, the data will remain in the embedded database. Do not create the `xlarchive` schema in the external database.

The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables. The following set of SQL privileges are required:

* **During installation and upgrade**:
** REFERENCES
** INDEX
** CREATE
** DROP
* **During operation**:
** SELECT, INSERT, UPDATE, DELETE

=== PostgreSQL
To create the XL Release database in PostgreSQL, you can execute the following script:

[source,sql]
----
CREATE USER xlrelease WITH
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  ENCRYPTED PASSWORD 'xlrelease';

CREATE USER xlarchive WITH
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  ENCRYPTED PASSWORD 'xlarchive';

CREATE DATABASE xlrelease OWNER xlrelease;
CREATE DATABASE xlarchive OWNER xlarchive;
----

=== MySQL
To create the XL Release database in MySQL, you can execute the following script:

[source,sql]
----
CREATE DATABASE xlrelease;
CREATE DATABASE xlarchive;

GRANT ALL PRIVILEGES ON xlrelease.* TO 'xlrelease'@'%' IDENTIFIED BY 'xlrelease';
GRANT ALL PRIVILEGES ON xlarchive.* TO 'xlarchive'@'%' IDENTIFIED BY 'xlarchive';

FLUSH PRIVILEGES;
----

For XL Release to function correctly when running on MySQL, change the following settings in the MySQL configuration file. To locate the file, refer to the link:https://dev.mysql.com/doc/refman/5.7/en/option-files.html[MySQL documentation].

[cols="^.<,<.<2",role="table table-bordered",options="header"]
|===
| Setting | Value
| `skip-character-set-client-handshake` |
| `collation_server` | `utf8_unicode_ci`
| `character_set_server` | `utf8`
|===

=== Oracle 11g
To create the XL Release database in Oracle 11g, you can execute the following script:

[source,sql]
----
ALTER SYSTEM SET disk_asynch_io = FALSE SCOPE = SPFILE;

CREATE USER xlarchive IDENTIFIED BY xlarchive;
GRANT CONNECT,RESOURCE,DBA TO xlarchive;
GRANT CREATE SESSION TO xlarchive WITH ADMIN OPTION;

CREATE USER xlrelease IDENTIFIED BY xlrelease;
GRANT CONNECT,RESOURCE,DBA TO  xlrelease;
GRANT CREATE SESSION TO xlrelease WITH ADMIN OPTION;

save /dblibs/touch.log create;
----

=== Microsoft SQL Server
To create the XL Release database in Microsoft SQL Server, you can execute the following script:

[source,sql]
----
CREATE DATABASE xlrelease COLLATE SQL_Latin1_General_CP1_CI_AS;
GO
USE xlrelease;
GO
CREATE LOGIN xlrelease WITH PASSWORD = 'xlrelease', CHECK_EXPIRATION = OFF, CHECK_POLICY = OFF, DEFAULT_DATABASE = xlrelease;
GO
CREATE USER [xlrelease] FOR LOGIN [xlrelease];
EXEC sp_addrolemember N'db_owner', N'xlrelease';
GO

CREATE DATABASE xlarchive COLLATE SQL_Latin1_General_CP1_CI_AS;
GO
USE xlarchive;
GO
CREATE LOGIN xlarchive WITH PASSWORD = 'xlarchive', CHECK_EXPIRATION = OFF, CHECK_POLICY = OFF, DEFAULT_DATABASE = xlrelease;
GO
CREATE USER [xlarchive] FOR LOGIN [xlarchive];
EXEC sp_addrolemember N'db_owner', N'xlarchive';
GO
----

Unlike other supported databases, MS SQL Server does not have Multi Version Concurrency Control activated by default. XL Release requires this feature to function correctly. For more information on the settings described below, please refer to link:https://msdn.microsoft.com/en-us/library/ms189050.aspx[this MSDN article].

Enable snapshot isolation mode with the following commands executed against SQL Server:

[source,sql]
----
ALTER DATABASE xlrelease SET ALLOW_SNAPSHOT_ISOLATION ON;
ALTER DATABASE xlrelease SET READ_COMMITTED_SNAPSHOT ON;
ALTER DATABASE xlarchive SET ALLOW_SNAPSHOT_ISOLATION ON;
ALTER DATABASE xlarchive SET READ_COMMITTED_SNAPSHOT ON;
----

When Multi Version Concurrency Control is enabled, you must add a weekly maintenance task to MS SQL Server. This task will maintain the indexes and query statistics:

* Recompute statistics by running `EXEC sp_updatestats`
* Clear buffers by running `DBCC DROPCLEANBUFFERS`
* Clear cache by running `DBCC FREEPROCCACHE`
* Rebuild indexes that are fragmented more than 30%

=== IBM DB2
To create the XL Release database in DB2, you can execute the following script:

[source,sql]
----
create database xlr using codeset UTF8 territory us PAGESIZE 32K;
connect to xlr;

CREATE BUFFERPOOL TMP_BP SIZE AUTOMATIC PAGESIZE 32K;
connect reset;

connect to xlr;
CREATE SYSTEM TEMPORARY TABLESPACE TMP_TBSP PAGESIZE 32K MANAGED BY SYSTEM USING ("<PATH>") BUFFERPOOL TMP_BP;
CREATE SCHEMA xlrelease AUTHORIZATION xlrelease;
CREATE SCHEMA xlarchive AUTHORIZATION xlarchive;
connect reset;
----

CAUTION: To use DB2 as an external database, ensure that you increase the `pagesize` to `32K`.

XL Release requires that DB2 is set in MySQL compatible mode in order for it to support pagination queries. Run the following command on your DB2 database to enable MySQL compatible mode:

[source,console]
----
$ db2set DB2_COMPATIBILITY_VECTOR=MYS
$ db2stop
$ db2start
----

== Database-specific configuration in XL Release

=== The configuration file

All the configuration is done in `XL_RELEASE_SERVER_HOME/conf/xl-release.conf`, which is in link:https://github.com/typesafehub/config/blob/master/HOCON.md[HOCON] format.

When you start the XL Release server for the first time, it will encrypt passwords in the configuration file and replace them with Base64-encoded encrypted values.

=== PostgreSQL

Download the link:https://jdbc.postgresql.org/download.html[PostgreSQL JDBC driver] JAR file and place it in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema. This is a sample configuration for PostgreSQL:

[source]
----
xl {
  ...
  database {
      db-driver-classname = "org.postgresql.Driver"
      db-url = "jdbc:postgresql://localhost:5432/xlrelease"
      db-username = "xlrelease"
      db-password = "xlrelease"
  }
  reporting {
      db-driver-classname = "org.postgresql.Driver"
      db-url = "jdbc:postgresql://localhost:5432/xlarchive"
      db-username = "xlarchive"
      db-password = "xlarchive"
  }
  ...
}
----

=== MySQL

Download the link:http://dev.mysql.com/downloads/connector/j/[MySQL JDBC driver] JAR file and place it in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema. This is a sample configuration for MySQL:

[source]
----
xl {
  ...
  database {
    db-driver-classname = "com.mysql.jdbc.Driver"
    db-url = "jdbc:mysql://localhost:3306/xlrelease?useSSL=false&nullNamePatternMatchesAll=true"
    db-username = "xlrelease"
    db-password = "xlrelease"
  }
  reporting {
    db-driver-classname = "com.mysql.jdbc.Driver"
    db-url = "jdbc:mysql://localhost:3306/xlarchive?useSSL=false&nullNamePatternMatchesAll=true"
    db-username = "xlarchive"
    db-password = "xlarchive"
  }
  ..
}
----

=== Oracle

Download the link:http://www.oracle.com/technetwork/database/features/jdbc/default-2280470.html[Oracle JDBC driver] JAR file and place it in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema. This is a sample configuration for Oracle:

[source]
----
xl {
  ...
  database {
    db-driver-classname="oracle.jdbc.driver.OracleDriver"
    db-url="jdbc:oracle:thin:@localhost:1521:XE"
    db-username = "xlrelease"
    db-password = "xlrelease"
  }
  reporting {
    db-driver-classname="oracle.jdbc.driver.OracleDriver"
    db-url="jdbc:oracle:thin:@localhost:1521:XE"
    db-username = "xlarchive"
    db-password = "xlarchive"
  }
  ...
}
----

If you use the TNSNames Alias syntax to connect to Oracle, you must specify where the driver can find the `TNSNAMES` file. For more information, refer to the Oracle documentation.

=== DB2

Download the link:http://www-01.ibm.com/support/docview.wss?uid=swg21363866[DB2 JDBC driver] JAR file and place it in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema. This is a sample configuration for DB2:

[source]
----
xl {
  ...
  database {
    db-driver-classname="com.ibm.db2.jcc.DB2Driver"
    db-url="jdbc:db2://127.0.0.1:50000/xlr"
    db-username = "xlrelease"
    db-password = "xlrelease"
  }
  reporting {
    db-driver-classname="com.ibm.db2.jcc.DB2Driver"
    db-url="jdbc:db2://127.0.0.1:50000/xlr"
    db-username = "xlarchive"
    db-password = "xlarchive"
  }
  ...
}
----

=== Microsoft SQL Server

Download the [Microsoft JDBC driver for SQL Server] JAR file and place it in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema. This is a sample configuration for SQL Server:

[source]
----
xl {
  ...
  database {
    db-driver-classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    db-url = "jdbc:sqlserver://localhost:1433;databaseName=xlrelease"
    db-username = "xlrelease"
    db-password = "xlrelease"
  }
  reporting {
    db-driver-classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    db-url = "jdbc:sqlserver://localhost:1433;databaseName=xlarchive"
    db-username = "xlarchive"
    db-password = "xlarchive"
  }
  ...
}
----
