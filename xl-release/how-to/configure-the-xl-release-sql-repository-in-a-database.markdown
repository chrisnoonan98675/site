---
title: Configure the XL Release SQL repository in a database (XL Release 7.5 and later)
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
- sql
since:
- XL Release 7.5.0
weight: 493
---

<div class="alert alert-warning" style="width: 60%">
This document describes the database configuration for XL Release 7.5 and later versions. For previous versions that use the JackRabbit (JCR) repository, refer to <a href="/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html">Configure the XL Release JCR repository in a database</a>.
</div>

XL Release stores its data in a repository. By default, this repository is an embedded database stored in `XL_RELEASE_SERVER_HOME/repository`.

Completed releases and reporting information are stored in another database called 'archive'. By default, this is also an embedded database stored in `XL_RELEASE_SERVER_HOME/archive`.

The embedded databases are automatically created when XL Release is started for the first time.

The purpose for the embedded databases is the easy setup in evaluation and test environments. For production use, it is strongly recommended to use an industrial-grade external database server. The following databases are supported:

* PostgreSQL versions 9.3, 9.4, 9.5, 9.6, and 10.1

  **Note:** The archiving database and the normal database must point to different external databases.
* MySQL versions 5.5, 5.6, and 5.7
* Oracle 11g
* Microsoft SQL Server 2012 and later
* DB2 versions 10.5 and 11.1

  **Important:** To use DB2 as an external database, ensure you increase the `pagesize` to `32K`.

To run XL Release in a cluster setup (Active/active or active/hot standby) it is required to have the repository stored in an external database.

**Note:** It is currently not possible to migrate the repository from an embedded database to an external database. Ensure that you configure production setup with an external database from the start. When migrating from a previous JCR version of XL Release (version 7.2 and earlier) make sure to migrate to an external database.

**Important:** When migrating from XL Release version 7.2 and earlier, refer to [Upgrade to XL Release 7.5](/xl-release/how-to/upgrade-to-7.5.0.html) for detailed migration instructions.

More information:

* Active/hot standby mode: [Configure active/hot-standby](/xl-release/how-to/configure-active-hot-standby.html)
* Cluster mode: [Configure cluster mode](/xl-release/how-to/configure-cluster.html)
* Backing up and restore: [Back up XL Release](/xl-release/how-to/back-up-xl-release.html)

## Preparing the external database

To use an external database, two empty database schemas should be created.

1. **xlrelease** - active release data and configuration data.
2. **xlarchive** - completed releases and reporting data.

**Note:** When migrating from a previous version of XL Release with the archive configured in the `archive` directory as an embedded database, the data will remain in the embedded database and this schema should not be created in the external database.

The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables.

The following set of SQL privileges are required.

**During installation / upgrade**:

* REFERENCES
* INDEX
* CREATE
* DROP

**During operation**:

* SELECT, INSERT, UPDATE, DELETE

## The configuration file: `xl-release.conf`

All the configuration is done in `XL_RELEASE_SERVER_HOME/conf/xl-release.conf`.

This file is in [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format.

After the first run, passwords in the configuration file will be encrypted and replaced with the base64-encoded encrypted values.

## Database-specific configuration in XL Release

### PostgreSQL

Driver:

 * [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema.

This is a sample configuration for PostgreSQL:

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

### MySQL

Driver:

 * [MySQL JDBC driver](http://dev.mysql.com/downloads/connector/j/)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema.

This is a sample for MySQL:

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
	        db-url = "jdbc:mysql://localhost:3306/xlrelease?useSSL=false&nullNamePatternMatchesAll=true"
	        db-username = "xlarchive"
	        db-password = "xlarchive"
	     }
	    ...
    }

Your MySQL instance should be configured in your `cnf` file as:

    skip-character-set-client-handshake
    collation_server=utf8_unicode_ci
    character_set_server=utf8

### Oracle

Driver:

 * [Oracle JDBC driver](http://www.oracle.com/technetwork/database/features/jdbc/index- 091264.html)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema.

This is a sample for Oracle:

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


If you use the TNSNames Alias syntax to connect to Oracle, you must specify where the driver can find the `TNSNAMES` file. For more information, refer to the Oracle documentation.

### DB2

Driver:

 * [DB2 JDBC driver](http://www-01.ibm.com/support/docview.wss?uid=swg21363866)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema.

This is a sample for DB2:

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

This is a sample `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration for [DB2](http://www-01.ibm.com/software/data/db2/). Ensure that the [DB2 JDBC driver](http://www-01.ibm.com/support/docview.wss?uid=swg21363866) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.

       xl {
          ...
          database {
             db-driver-classname="com.ibm.db2.jcc.DB2Driver"
             db-password="{b64}wyCfV+HXKRAo9GT9QWeqDw=="
             db-url="jdbc:db2://127.0.0.1:50000/xlr"
             db-username=xlrelease
             max-pool-size=20
          }
          ...
       }

**Note:** If you are using DB2 version 9.7.2 or later, you can enable support for `LIMIT x` using the `DB2_COMPATIBILITY_VECTOR` registry variable:

        db2set DB2_COMPATIBILITY_VECTOR=MYS
        db2stop
        db2start      

### SQL Server

Driver:

 * [Microsoft JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Next, configure `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` to point to the database schema.

This is a sample for SQL Server:

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

Enable snapshot isolation mode by executing the following commands against the SQL Server:

    ALTER DATABASE xlrelease SET ALLOW_SNAPSHOT_ISOLATION ON;
    ALTER DATABASE xlrelease SET READ_COMMITTED_SNAPSHOT ON;
    ALTER DATABASE xlarchive SET ALLOW_SNAPSHOT_ISOLATION ON;
    ALTER DATABASE xlarchive SET READ_COMMITTED_SNAPSHOT ON;

To maintain the performance of the database, make sure you execute the following maintenance tasks on the QL Server once a week:

 * Recompute statistics by running `EXEC sp_updatestats`
 * Clear buffers by running `DBCC DROPCLEANBUFFERS`
 * Clear cache by running `DBCC FREEPROCCACHE`
 * Rebuild indexes that are fragmented more than 30%
