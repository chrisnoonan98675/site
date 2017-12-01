---
title: Upgrade to XL Release 7.5
categories:
- xl-release
subject:
- Installation
tags:
- upgrade
- system administration
- installation
- migration
weight: 408
---


This document describes how to upgrade XL Release 7.5.x  server from a previous version. In-place upgrade is not supported for the upgrade to 7.5, since the storage solution is a completely different architecture. Version before 7.5 used JCR/JackRabbit as storage and in XLR 7.5 we made the transition to a relational database model.

## Prerequisites

* Upgrade source XL Release server to version 7.0.x or 7.2.0.
* External database for the storage of XL Release data. Supported databases:
    * Oracle
    * PostgreSQL
    * MySQL
    * Microsoft SQL
    * DB2

* Note that the archive database is still needed. The structure and functionality is not changed in this upgrade.

## Steps

The upgrade procedure to XL Release 7.5 is different than before. Here's an overview of the steps that need to be taken.

1. Install XL Release 7.5.0
2. On the database, add a database schema / user that will contain the XL Release data.
3. Install and configure the migrator tool
4. Shutdown **source** XL Release server and run Migrator tool
5. Copy files from **source** server to XL Release 7.5.0
6. Configure XL Release 7.5.0 to point to the new database
7. Start XL Release 7.5.0


## 1. Install XL Release 7.5.0

* Download xl-release-7.5.0.zip
* Unzip xl-release-7.5.0.zip

Don't start the server just yet.

## 2. Database setup

Create the following database / user in your SQL database:

* 'xlrelease' -- this database will contain the active releases and configuration data.

The user must have access to create tables. Tables are created during the upgrade procedure, not during operation.

The **archive database** remains as-is and is copied / configured in the final configuration steps.

## 3. Migrator tool setup

### Installation

Download the **xl-release-sql-migrator-7.5.0.zip**
<!--
           !!! MISSING LINK !!
-->
package and unzip it in a directory on the same machine as the **source** XL Release server.

### Database drivers

The migrator application contains only JDBC drivers for the embedded databases, Derby and H2. If you migrate to another database, you must download the appropriate JDBC driver jar file and put it inside the `lib` folder of the migration tool.

{:.table .table-striped}
| Database   | JDBC drivers | Notes   |
| ---------- | ------------ | ------- |
| Oracle     | [JDBC driver downloads](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html) | For Oracle 12c, use the 12.1.0.1 driver (`ojdbc7.jar`). It is recommended that you only use the thin drivers; refer to the [Oracle JDBC driver FAQ](http://www.oracle.com/technetwork/topics/jdbc-faq-090281.html) for more information. |
| PostgreSQL | [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)| Use the JDBC42 version, because XL Release 4.8.0 and later requires Java 1.8. |
| MySQL      | [Connector\J 5.1.30 driver download](http://dev.mysql.com/downloads/connector/j/)| |
| Microsoft SQL Server| [Microsoft JDBC Driver](https://www.microsoft.com/en-us/download/details.aspx?id=55539)| Also available on GitHub: [Microsoft/mssql-jdbc](https://github.com/Microsoft/mssql-jdbc) |
| DB2 | [DB2 JDBC driver](http://www-01.ibm.com/support/docview.wss?uid=swg21363866)| Download the JDBC 4.0 driver that matches your DB2 version |

### Migrator database configuration

The configuration of the migrator is done in `conf/xl-release-sql-migrator.conf`.

Configure the `xlrelease` database that will be used by the **target** XL Release 7.5.0 server.

For example:

    xl {
      database {
        db-driver-classname = "com.mysql.jdbc.Driver"
        db-url = "jdbc:mysql://mysql-server-host/xlrelease"
        db-username = "xlrelease"
        db-password = "xlrelease"
      }
    }

### Page size

You can set the page size used to fetch releases from the JCR repository using the following configuration snippet in `conf/xl-release-sql-migrator.conf`.

    xl {
      database {
        ...
      }
      migrator {
        pageSize = 100 # page size when fetching JCR or archived releases
      }
    }

This can be useful when you have large releases that contain a large number of comments for example.

The migration tool uses 4Gb JVM heap, but if you get an `OutOfMemoryError` during migration then you can fix it by decreasing the page size.


### Custom classpath

If you have modified your `xlr-wrapper-*.conf` from XL Release, you have to add your custom classpath on `bin/xl-release-sql-migrator` like:

      CLASSPATH=$APP_HOME/conf:$APP_HOME/lib/*:./conf:./ext:./hotfix/*:./plugins/*

## 4. Running the migrator

You must run the application from the `XL_RELEASE_SERVER_HOME` folder of the **source** server. The migration tool will load your XL Release `conf`, `ext`, and `plugins` folder to load extra synthetic types.

For example, if the **source** XL Release installation is under

    /opt/xl-release-7.0.1-server

and the migration tool is under

	/opt/xl-release-sql-migrator-7.5.0

you should issue the following command:

```
/opt/xl-release-7.0.1-server/$ /opt/xl-release-sql-migrator-7.5.0/bin/xl-release-sql-migrator
```

The source server must not need to be running. The migrator does not alter the repository of the source server. However, we recommend to make a backup of the production server and run the migrator from the backup directory.

Note that the you don't need to specify the installation directory of the **target** server. Configuring the database location is sufficient.

The migrator supports incremental migration. If you stop it in the middle of the process and restart it later, it will skip the releases that were already migrated.

## 5. Copy files from source to target server

1. If you have implemented any custom plugins, copy them from the `plugins` directory from the previous installation directory to the new installation directory.

1. Copy the contents of the `ext` directory from the old installation directory to the new installation directory.

1. Copy the entire `archive` directory from the previous installation to the new installation directory.

1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.

1. If you have changed the XL Release server startup script(s) in the `bin` directory, do not copy the changed script(s) to the new installation directory. Instead, manually reapply the changes to the files that were provided in the new version of XL Release.

## 6. Configure database in XL Release 7.5.0

Get the database driver file (see Step 3) and install the jar file in the `plugins` folder.

Edit `conf/xl-release.conf` and configure database details to point to your database instance.

```
xl {
    cluster.mode = default
    database {
        db-driver-classname="org.postgresql.Driver"
        db-url="jdbc:postgresql://localhost:5432/xlrelease"
        db-username=xlrelease
        db-password="xlrelease"
    }
}
```

If the reporting archive is configured to use an external database, also configure the connection settings in `conf/xl-release.conf`.

```
xl {
    cluster.mode = default
    database {
        db-driver-classname="org.postgresql.Driver"
        db-url="jdbc:postgresql://localhost:5432/xlrelease"
        db-username=xlrelease
        db-password="xlrelease"
    }
    reporting {
        db-driver-classname="org.postgresql.Driver"
        db-url="jdbc:postgresql://localhost:5432/xlarchive"
        db-username=xlarchive
        db-password="xlarchive"
    }
}
```


If you are using the embedded derby archived database, you need to upgrade it, this can be done by just adding at the end of the connection url `;upgrade=true`.

```
xl {
    reporting {
        db-driver-classname=...
        db-url="jdbc:.......;upgrade=true"
        db-username=...
        db-password=...
    }
}
```

<!--
           !!! Check details !!
-->


_Please note that passwords will be encrypted in this file by XL Release_


## 7. Starting XL Release 7.5.0

[Start the XL Release server interactively](/xl-release/how-to/start-xl-release.html) to allow automatic repository upgraders to run.

	**Note:** If you are running XL Release in cluster mode, you must start a single XL Release server instance and run the upgraders only on that instance. After the upgraders have sucessfully finished you can boot up the rest of the cluster.

If you normally run the XL Release server [as a service](/xl-release/how-to/install-xl-release-as-a-service.html), shut it down and restart it as you normally do.


## Troubleshooting

* Make sure the configuration file to set up the database in XLR (`conf/xl-release.conf`) matches exactly with the database configuration in the migrator tool.

* If you need to adjust the configuration in XL Release, rerun the migrator afterwards and it will create the new tables and migrate the existing data with the new settings.
