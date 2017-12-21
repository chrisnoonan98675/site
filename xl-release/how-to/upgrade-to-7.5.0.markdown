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

* Upgrade source XL Release server to version 7.0.x or 7.2.0.
* External database for the storage of XL Release data. Supported databases:
    * Oracle
    * PostgreSQL
    * MySQL
    * Microsoft SQL
    * DB2

* Note that the archive database is still needed. The structure and functionality have not changed in this upgrade.

## Overview

The upgrade procedure to XL Release 7.5 is different than before. This is an overview of the steps that need to be taken.

1. Install XL Release 7.5.0
1. On the database, add a database schema / user that will contain the XL Release data
1. Install and configure the migrator tool
1. Shutdown **source** XL Release server and run Migrator tool
1. Copy files from **source** server to XL Release 7.5.0
1. Configure XL Release 7.5.0 to point to the new database
1. Start XL Release 7.5.0

## Step 1. Install XL Release 7.5.0

* [Download xl-release-7.5.0.zip](https://dist.xebialabs.com/)
* Unzip xl-release-7.5.0.zip

Do not start the server at this moment.

## Step 2. Database setup

Create the following database / user in your SQL database:

* `xlrelease` -- this database will contain the active releases and configuration data.

The user must have access to create tables. Tables are created during the upgrade procedure, not during operation.

The **archive database** remains as-is and is copied / configured in the final configuration steps.

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

The migration tool uses 4Gb JVM heap, but if you get an `OutOfMemoryError` during migration, you can fix it by decreasing the page size.

### Add custom classpath

If you modified your `xlr-wrapper-*.conf` from XL Release, you must add your custom classpath on `bin/xl-release-sql-migrator` like:

      CLASSPATH=$APP_HOME/conf:$APP_HOME/lib/*:./conf:./ext:./hotfix/*:./plugins/*

## Step 4. Running the migrator

You must run the application from the `XL_RELEASE_SERVER_HOME` folder of the **source** server. The migration tool will load your XL Release `conf`, `ext`, and `plugins` folder to load extra synthetic types.

For example:

If the **source** XL Release installation is at the location:

    /opt/xl-release-7.0.1-server

And the migration tool is at the location:

	  /opt/xl-release-sql-migrator-7.5.0

Execute the following command:

    /opt/xl-release-7.0.1-server/$ /opt/xl-release-sql-migrator-7.5.0/bin/xl-release-sql-migrator

The **source** server does not require to be running. The migrator does not modify the repository of the source server. It is recommended to create a backup of the production server and run the migrator from the backup directory.

**Note:** You do not need to specify the installation directory of the **target** server. Configuring the database location is sufficient.

The migrator supports incremental migration. If you stop it in the middle of the process and restart it later, it will skip the releases that were already migrated.

## Step 5. Copy the files from source to target server

1. If you have implemented any custom plugins, copy them from the `plugins` directory of the previous installation directory to the new installation directory.

1. Copy the contents of the `ext` directory from the old installation directory to the new installation directory.

1. Copy the entire `archive` directory from the previous installation directory to the new installation directory.

1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.

1. If you have changed the XL Release server startup script(s) in the `bin` directory, do not copy the changed script(s) to the new installation directory. Instead, manually reapply the changes to the files that were provided in the new version of XL Release.

## Step 6. Configure the database in XL Release 7.5.0

Get the database driver file (see Step 3) and install the jar file in the `plugins` folder.

Edit `conf/xl-release.conf` and configure database details to point to your database instance.

      xl {
          cluster.mode = default
          database {
              db-driver-classname="org.postgresql.Driver"
              db-url="jdbc:postgresql://localhost:5432/xlrelease"
              db-username=xlrelease
              db-password="xlrelease"
          }
      }

If the reporting archive is configured to use an external database, also configure the connection settings in `conf/xl-release.conf`. If you are using the embedded archive database, you do not need to configure it.

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

_The passwords will be encrypted in this file by XL Release_

## Step 7. Starting XL Release 7.5.0

[Start the XL Release server interactively](/xl-release/how-to/start-xl-release.html) to allow automatic repository upgraders to run.

**Note:** If you are running XL Release in cluster mode, you must start a single XL Release server instance and run the upgraders only on that instance. After the upgraders have successfully finished, you can boot up the rest of the cluster.

If you normally run the XL Release server [as a service](/xl-release/how-to/install-xl-release-as-a-service.html), shut it down and restart it as you normally do.

## Troubleshooting

* Make sure the configuration file to set up the database in XL Release (`conf/xl-release.conf`) matches exactly the database configuration in the migrator tool.

* If you need to adjust the configuration in XL Release, rerun the migrator afterwards and it will create the new tables and migrate the existing data with the new settings.
