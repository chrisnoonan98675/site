---
title: Upgrade to XL Release 7.5.x or later
categories:
xl-release
subject:
Installation
tags:
upgrade
system administration
installation
migration
weight: 408
---

This document describes how to upgrade to XL Release 7.5.x or later from an earlier version. In-place upgrade is not supported for 7.5.x and later because the storage solution is a completely different architecture. Versions prior to XL Release 7.5.x used JackRabbit (JCR) as storage and, in XL Release 7.5.x, the transition to a relational database model was made.

<div class="alert alert-warning" style="width: 60%">
Please also refer to the general <a href="/xl-release/how-to/upgrade-xl-release.html">upgrade instructions</a> for more information.
</div>

## Prerequisites

* Upgrade the **source** XL Release server to version 7.0.x, 7.1.x, or 7.2.x.
* Download the SQL Migrator Tool version 7.5.3 or later
* External database servers for the storage of XL Release data. Supported databases:
    * PostgreSQL versions 9.3, 9.4, 9.5, 9.6, and 10.1

      **Note:** The archiving database and the normal database must point to different database servers.

    * MySQL versions 5.5, 5.6, and 5.7
    * Oracle 11g
    * Microsoft SQL Server 2012 and later
    * DB2 versions 10.5 and 11.1

      **Important:** To use DB2 as an external database, ensure you increase the `pagesize` to `32K`.

* The archive database is still required. The structure and functionality have not changed in this upgrade.

**Important:**
1. As of version 7.5.3, the SQL migrator allows you to migrate the archive database to a different database server.
1. If you want to upgrade from XL Release versions 7.0.x, 7.5.0, 7.5.1, or 7.5.2 to version 8.0.0, you must first upgrade to version 7.5.3 and run XL Release on version 7.5.3. To upgrade from version 7.5.3 to version 8.0.0, copy the contents of your `XL_RELEASE_SERVER_HOME/conf` directory from the previous installation to the new installation directory.

## Overview

The upgrade procedure to XL Release 7.5.x or later is different than before. This is an overview of the steps that need to be taken.

1. Install XL Release 7.5.x or later
1. On the database, add a database schema and user that will contain the XL Release data
1. Install and configure the migrator tool
1. Shut down **source** XL Release server and run migrator tool
1. Copy files from **source** server to the **target** server
1. Configure the **target** server to point to the new database
1. Start the new version of XL Release

## Step 1. Download the new version of XL Release

1. Download the desired version of XL Release from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/) (requires customer log-in).
1. Unzip the software distribution ZIP file.
1. Do not start the server at this time.

## Step 2. Set up the database

Create an `xlrelease` database and an `xlrelease` user in your SQL database.

The user must have access to create tables. Tables are created during the upgrade procedure, not during operation.

## Step 3. Set up the SQL Migrator Tool

### Download the SQL Migrator Tool

1. Download the latest version of the XL Release SQL Migrator Tool from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/) (requires customer log-in).
1. Unzip the SQL Migrator Tool ZIP file in a directory on the same machine as the **source** XL Release server.

#### Database drivers

The SQL Migrator Tool only contains JDBC drivers for the Derby and H2 embedded databases. If you migrate to another database, you must download the appropriate JDBC driver JAR file and place it in the `lib` folder of the SQL Migrator Tool.

{:.table .table-striped}
| Database   | JDBC drivers | Notes   |
| ---------| -----------| ------|
| Oracle     | [JDBC driver downloads](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html) | For Oracle 12c, use the 12.1.0.1 driver (`ojdbc7.jar`). It is recommended that you only use the thin drivers; refer to the [Oracle JDBC driver FAQ](http://www.oracle.com/technetwork/topics/jdbc-faq-090281.html) for more information. |
| PostgreSQL | [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)| Use the JDBC42 version, because XL Release 4.8.0 and later requires Java 1.8. |
| MySQL      | [Connector\J 5.1.30 driver download](http://dev.mysql.com/downloads/connector/j/)| |
| Microsoft SQL Server| [Microsoft JDBC Driver](https://www.microsoft.com/en-us/download/details.aspx?id=55539)| Also available on GitHub: [Microsoft/mssql-jdbc](https://github.com/Microsoft/mssql-jdbc). |
| DB2 | [DB2 JDBC driver](http://www-01.ibm.com/support/docview.wss?uid=swg21363866)| Download the JDBC 4.0 driver that matches your DB2 version. |

### Configure the database

Configure the `conf/xl-release-sql-migrator.conf` in the SQL Migrator Tool directory to identify the `xlrelease` database that the **target** XL Release server will use.

For example, a MySQL configuration would be:

      xl {
          database {
            db-driver-classname = "com.mysql.jdbc.Driver"
            db-url = "jdbc:mysql://mysql-server-host/xlrelease"
            db-username = "xlrelease"
            db-password = "xlrelease"
          }
          archive {
          db-driver-classname = "org.h2.Driver"
          db-url = "jdbc:h2:mem:test"
          db-username = "sa"
          db-password = ""
        }
    }

### Set the page size

You can set the page size used to fetch releases from the JCR repository by adding the following configuration snippet to `conf/xl-release-sql-migrator.conf`:

    xl {
      database {
        ...
      }
      migrator {
        pageSize = 100 # page size when fetching JCR or archived releases
      }
    }

Setting the page size is recommended when you have large releases that contain a large number of comments.

The SQL Migrator Tool uses 4 GB JVM heap; if you see an `OutOfMemoryError` during migration, decrease the page size.

### Add custom classpath

If you have modified the `xlr-wrapper-*.conf` file in your **source** XL Release installation, you must add your custom classpath to `bin/xl-release-sql-migrator` in the SQL Migrator Tool directory. For example:

    CLASSPATH=$APP_HOME/conf:$APP_HOME/lib/*:./conf:./ext:./hotfix/*:./plugins/*

## Step 4. Run the SQL Migrator Tool

Run the SQL Migrator Tool from the `XL_RELEASE_SERVER_HOME` folder of the **source** server. The SQL Migrator Tool will load your XL Release `conf`, `ext`, and `plugins` folder to load extra synthetic types.

For example, if the **source** XL Release installation is located at `/opt/xl-release-7.0.1-server` and the SQL Migrator Tool is located at `/opt/xl-release-sql-migrator-7.5.2`:

1. Go to `/opt/xl-release-7.0.1-server`.
2. Execute:

        /opt/xl-release-sql-migrator-7.5.2/bin/xl-release-sql-migrator

The source server does not need to be running. The SQL Migrator Tool does not modify the repository of the source server. It is recommended that you create a backup of the source server and run the SQL Migrator Tool from the backup directory.

**Note:** You do not need to specify the installation directory of the target server. Configuring the database location is sufficient.

The SQL Migrator Tool supports incremental migration. If you stop it in the middle of the process and restart it later, it will skip the releases that were already migrated.

### Running options

You can run the migrator with the following environment variables:

{:.table .table-striped}
| Option | Description | Default value |
| ACCEPT_MIGRATION=true | The confirmation screen is not displayed | false |
| MIGRATE_REPOSITORY=true | The JCR repository will be migrated | true |
| MIGRATE_ARCHIVE=true | The archive database will be migrated | false |
| SKIP_ERRORS=true | Errors on writing configuration items will be skipped and logged in error log | false |

## Step 5. Copy files from source to target server

1. If you have implemented any custom plugins, copy them from the `plugins` directory of the **source** XL Release installation to the `plugins` directory of the **target** installation.
1. Copy the content of the `ext` directory of the **source** installation to the `ext` directory of the **target** installation.
1. Copy the content of the `conf` directory of the **source** installation to the `conf` directory of the **target** installation.

    **Important:** As of version 7.5.0, the content of the `script.policy` file has been modified. When upgrading to XL Release version 7.5.x or later, do not overwrite the `script.policy` file, you must use the new version of the file.

1. If you have changed the XL Release server startup script(s) in the `bin` directory of the **source** installation, do not copy the changed script(s) to the **target** installation. Instead, manually reapply the changes to the files that were provided in the new version of XL Release.

## Step 6. Configure the target XL Release server

1. Install the database driver (JAR) file from Step 3 in the `plugins` folder of the **target** installation.
1. Open `conf/xl-release-sql-migrator.conf` and copy the contents of the file.
1. Open `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` and paste the content.
1. Change the word `archive` to `reporting`.

 **Note:** Database configuration in `xl-release.conf` must match `xl-release-sql-migrator.conf`.


For example, a PostgreSQL configuration would be:

    xl {
      cluster.mode = default
      database {
          db-driver-classname="org.postgresql.Driver"
          db-url="jdbc:postgresql://localhost:5432/xlrelease"
          db-username=xlrelease
          db-password="xlrelease"
      }
    }

If the reporting archive is configured to use an external database, you must also configure the connection settings in `XL_RELEASE_SERVER_HOME/conf/xl-release.conf`. If you are using the embedded archive database, you do not need to configure it.

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

**Note:** XL Release will encrypt the passwords in the `xl-release.conf` file upon startup.

## Step 7. Start the target XL Release server

[Start the target XL Release server interactively](/xl-release/how-to/start-xl-release.html) to allow automatic repository upgraders to run.

**Note:** If you are running XL Release in cluster mode, you must start a single XL Release server instance and run the upgraders only on that instance. After the upgraders have successfully finished, you can boot up the rest of the cluster.

If you normally run the XL Release server [as a service](/xl-release/how-to/install-xl-release-as-a-service.html), shut it down and restart it as you normally do.

## Troubleshooting

* Ensure the configuration file to set up the database in XL Release (`conf/xl-release.conf`) matches the database configuration in the SQL Migrator Tool exactly.

* If you need to adjust the configuration in XL Release, rerun the migrator afterwards and it will create the new tables and migrate the existing data with the new settings.
