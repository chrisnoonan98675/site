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

This document describes how to upgrade to XL Release 7.5.x server from a previous version. In-place upgrade is not supported for the upgrade to 7.5, since the storage solution is a completely different architecture. The versions before 7.5 used JackRabbit (JCR) as storage and, in XL Release 7.5, the transition to a relational database model was made.

<div class="alert alert-warning" style="width: 60%">
Please also refer to the general <a href="/xl-release/how-to/upgrade-xl-release.html">upgrade instructions</a> for more information.
</div>

## Prerequisites

* Upgrade source XL Release server to version 7.0.x, 7.1.x, or 7.2.x.
* External database for the storage of XL Release data. Supported databases:
    * PostgreSQL versions 9.3, 9.4, 9.5, 9.6, and 10.1

      **Note:** The archiving database and the normal database must point to different external databases.
    * MySQL versions 5.5, 5.6, and 5.7
    * Oracle 11g
    * Microsoft SQL Server 2012 and later
    * DB2 versions 10.5 and 11.1

      **Important:** To use DB2 as an external database, ensure you increase the `pagesize` to `32K`.
* The archive database is still required. The structure and functionality have not changed in this upgrade.

## Overview

The upgrade procedure to XL Release 7.5 is different than before. This is an overview of the steps that need to be taken.

1. Install XL Release 7.5.0
1. On the database, add a database schema and user that will contain the XL Release data
1. Install and configure the migrator tool
1. Shut down **source** XL Release server and run migrator tool
1. Copy files from **source** server to XL Release 7.5.0
1. Configure XL Release 7.5.0 to point to the new database
1. Start XL Release 7.5.0

## Step 1. Install XL Release 7.5.0

* [Download xl-release-7.5.0.zip](https://dist.xebialabs.com/)
* Unzip xl-release-7.5.0.zip

Do not start the server at this moment.

## Step 2. Database setup

Create the following database and user in your SQL database:

* `xlrelease`: This database will contain the active releases and configuration data.

The user must have access to create tables. Tables are created during the upgrade procedure, not during operation.

The **archive database** remains as-is and is copied and configured in the final configuration steps.

## Step 3. Migrator tool setup

### Installation

Download the **xl-release-sql-migrator-7.5.0.zip** package from the [Customer download area](https://dist.xebialabs.com/) (requires customer log-in) and unzip it in a directory on the same machine as the **source** XL Release server.

### Database drivers

The migrator application contains only JDBC drivers for the embedded databases: Derby and H2. If you migrate to another database, you must download the appropriate JDBC driver jar file and place it inside the `lib` folder of the migration tool.

{:.table .table-striped}
| Database   | JDBC drivers | Notes   |
| ---------- | ------------ | ------- |
| Oracle     | [JDBC driver downloads](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html) | For Oracle 12c, use the 12.1.0.1 driver (`ojdbc7.jar`). It is recommended that you only use the thin drivers; refer to the [Oracle JDBC driver FAQ](http://www.oracle.com/technetwork/topics/jdbc-faq-090281.html) for more information. |
| PostgreSQL | [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)| Use the JDBC42 version, because XL Release 4.8.0 and later requires Java 1.8. |
| MySQL      | [Connector\J 5.1.30 driver download](http://dev.mysql.com/downloads/connector/j/)| |
| Microsoft SQL Server| [Microsoft JDBC Driver](https://www.microsoft.com/en-us/download/details.aspx?id=55539)| Also available on GitHub: [Microsoft/mssql-jdbc](https://github.com/Microsoft/mssql-jdbc) |
| DB2 | [DB2 JDBC driver](http://www-01.ibm.com/support/docview.wss?uid=swg21363866)| Download the JDBC 4.0 driver that matches your DB2 version |

### Migrator database configuration

The configuration of the migrator is done in `conf/xl-release-sql-migrator.conf`. This file is provided in the **xl-release-sql-migrator-7.5.0.zip** package.

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

### Set the page size

You can set the page size used to fetch releases from the JCR repository using the following configuration snippet in `conf/xl-release-sql-migrator.conf`.

    xl {
      database {
        ...
      }
      migrator {
        pageSize = 100 # page size when fetching JCR or archived releases
      }
    }

For example: this is recommended when you have large releases that contain a large number of comments.

The migration tool uses 4 GB JVM heap, but if you get an `OutOfMemoryError` during migration, you can fix it by decreasing the page size.

### Add custom classpath

If you modified your `xlr-wrapper-*.conf` from XL Release, you must add your custom classpath on `bin/xl-release-sql-migrator` like:

    CLASSPATH=$APP_HOME/conf:$APP_HOME/lib/*:./conf:./ext:./hotfix/*:./plugins/*

## Step 4. Running the migrator

You must run the application from the `XL_RELEASE_SERVER_HOME` folder of the **source** server. The migration tool will load your XL Release `conf`, `ext`, and `plugins` folder to load extra synthetic types.

For example, if the **source** XL Release installation is located at `/opt/xl-release-7.0.1-server` and the migration tool is located at `/opt/xl-release-sql-migrator-7.5.0`:

1. Go to `/opt/xl-release-7.0.1-server`.
2. Execute:

        /opt/xl-release-sql-migrator-7.5.0/bin/xl-release-sql-migrator

The **source** server does not need to be running. The migrator does not modify the repository of the source server. It is recommended that you create a backup of the production server and run the migrator from the backup directory.

**Note:** You do not need to specify the installation directory of the **target** server. Configuring the database location is sufficient.

The migrator supports incremental migration. If you stop it in the middle of the process and restart it later, it will skip the releases that were already migrated.

## Step 5. Copy the files from source to target server

1. If you have implemented any custom plugins, copy them from the `plugins` directory of the **source** XL Release installation to the `plugins` directory of the **target** installation.
1. Copy the content of the `ext` directory of the **source** installation to the `ext` directory of the **target** installation.
1. Copy the entire `archive` directory of the **source** installation to the **target** installation.
1. Copy the content of the `conf` directory of the **source** installation to the `conf` directory of the **target** installation.
1. If you have changed the XL Release server startup script(s) in the `bin` directory of the **source** installation, do not copy the changed script(s) to the **target** installation. Instead, manually reapply the changes to the files that were provided in the new version of XL Release.

## Step 6. Configure the database in XL Release 7.5.0

Install the database driver (JAR) file from Step 3 in the `plugins` folder of the **target** installation.

Edit `conf/xl-release.conf` and configure the database details to point to your database instance. This configuration must exactly match the configuration in the migrator tool.

    xl {
      cluster.mode = default
      database {
          db-driver-classname="org.postgresql.Driver"
          db-url="jdbc:postgresql://localhost:5432/xlrelease"
          db-username=xlrelease
          db-password="xlrelease"
      }
    }

If the reporting archive is configured to use an external database, you must also configure the connection settings in `conf/xl-release.conf`. If you are using the embedded archive database, you do not need to configure it.

<!--
           !!! Check details !!
-->
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

## Step 7. Start XL Release 7.5.0

[Start the XL Release server interactively](/xl-release/how-to/start-xl-release.html) to allow automatic repository upgraders to run.

**Note:** If you are running XL Release in cluster mode, you must start a single XL Release server instance and run the upgraders only on that instance. After the upgraders have successfully finished, you can boot up the rest of the cluster.

If you normally run the XL Release server [as a service](/xl-release/how-to/install-xl-release-as-a-service.html), shut it down and restart it as you normally do.

## Troubleshooting

* Make sure the configuration file to set up the database in XL Release (`conf/xl-release.conf`) exactly matches the database configuration in the migrator tool.

* If you need to adjust the configuration in XL Release, rerun the migrator afterwards and it will create the new tables and migrate the existing data with the new settings.
