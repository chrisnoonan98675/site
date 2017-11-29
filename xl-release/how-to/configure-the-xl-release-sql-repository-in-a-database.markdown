---
title: Configure the XL Release SQL repository in a database
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
---

XL Release stores its data in a repository. By default, the repository is stored in an embedded Derby database at `XL_RELEASE_SERVER_HOME/repository`. However, you can choose to store binary data (artifacts), configuration items (CIs), and CI history in an external database. This topic describes an approach to configuring the built-in Derby implementation to use an external database.

For information about:
* Backing up and restoring the database, refer to [Back up XL Release](/xl-release/how-to/back-up-xl-release.html)
* XL Release's internal archive database, refer to [Configure the archive database](/xl-release/how-to/configure-the-archive-database.html)

## Preparing the database and repository

Before installing XL Release, create an empty database. XL Release will create the database schema during installation.
The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables.

## External databases and failover

If you take the approach described in this topic, you can optionally [create a failover configuration](/xl-release/how-to/configure-failover.html) with multiple instances of XL Release that will use the same database as your master instance. However, this is a limited setup in which only one instance of XL Release can access the database at a time.
However, if you are using XL Release 6.0.0 or later, you can take advantage of clustering in an active/hot-standby configuration, which requires a different configuration for the external database. Refer to [Configure active/hot-standby](/xl-release/how-to/configure-active-hot-standby.html) for instructions.

**note**: For XL Release 7.5.0 you can use an [active/active](/xl-release/how-to/configure-active-active.html) setup.

## Use XL Release with MySQL

This is a sample `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration for [MySQL](http://www.mysql.com/). Ensure that the [Mysql JDBC driver](http://dev.mysql.com/downloads/connector/j/) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.:

    xl {
        ...
        database {
            db-driver-classname="com.mysql.jdbc.Driver"
            db-password=xlrelease
            db-url="jdbc:mysql://localhost:3306/xlrelease?useSSL=false"
            db-username=xlrelease
            max-pool-size=20
        }
        ...
    }

Your MySql instance should be configured in your `cnf` file as:

    sql_mode="NO_AUTO_VALUE_ON_ZERO"
    skip-character-set-client-handshake
    collation_server=utf8_unicode_ci
    character_set_server=utf8


## Use XL Release with DB2

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

## Use XL Release with Oracle

This is a sample `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration for [Oracle](http://www.oracle.com/us/products/database/index.html), ensure that the [Oracle JDBC driver](http://www.oracle.com/technetwork/database/features/jdbc/index- 091264.html) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.:

       xl {
         ...
         database {
           db-driver-classname="oracle.jdbc.driver.OracleDriver"
           db-password=xlrelease
           db-url="jdbc:oracle:thin:@localhost:1521:XE"
           db-username=xlrelease
           max-pool-size=20
         }
         ...
       }

If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the `TNSNAMES` file. Refer to the Oracle documentation for more information.

## Use XL Release with SQL Server

To use XL Release with [Microsoft SQL Server](https://www.microsoft.com/en-us/server-cloud/products/sql-server/), ensure that the [Microsoft JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.
This is a sample `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration for SQL Server:
    xl {
        ...
        database {
            db-driver-classname = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
            db-url = "jdbc:sqlserver://localhost:1433;databaseName=xlrelease"
            db-username = "xlrelease"
            db-password = "xlrelease"
            max-pool-size = 20
        }
        ...
    }

## Use XL Release with Postgresql

To use XL Release with [Postgresql](https://www.postgresql.org/docs/), ensure that the [Postgresl JDBC driver](https://jdbc.postgresql.org/download.html) JAR file is located in `XL_RELEASE_SERVER_HOME/lib` or on the Java classpath.
This is a sample `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` configuration for SQL Server:

    xl {
        ...
        database {
            db-driver-classname="org.postgresql.Driver"
            db-password="{b64}wyCfV+HXKRAo9GT9QWeqDw=="
            db-url="jdbc:postgresql://localhost:5432/xlrelease"
            db-username=xlrelease
            max-pool-size=20
        }
        ...
    }
