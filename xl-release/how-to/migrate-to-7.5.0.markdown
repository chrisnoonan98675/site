# Migrating data to XL Release 7.5.x

This document describes how to migrate data to an XL Release 7.5.x  server from a previous version. In-place upgrade is not supported for the upgrade to 7.5, since the storage solution is a completely different architecture. Version before 7.5 used JCR/JackRabbit as storage and in XLR 7.5 we made the transition to a relational database model.

## Prerequisites

* Upgrade source XL Release server to at least 7.0.x or 7.2.0.
* Set up the target XL Release 7.5.0 server in a _different_ installation directory. See **Setting up XL Release 7.5.0 with active releases in SQL database**.

## Set up and install

Download the [xl-release-sql-migrator-7.5.0-beta.1.zip](!!! MISSING LINK !!!) package and unzip it in a directory on the same server as the existing XL Release server you want to migrate date from (**source** server).

## Configuring the migrator

The configuration of the migrator is done in `conf/xl-release-sql-migrator.conf`.

Set the connection properties using the xlrelease database of the **target** XL Release 7.5.0 server.

For example:

    xl {
      database {
        db-driver-classname = "com.mysql.jdbc.Driver"
        db-url = "jdbc:mysql://mysql-server-host/xlrelease"
        db-username = "xlrelease"
        db-password = "xlrelease"
      }
    }

You can copy the relevant section of the `conf/xl-release.conf` file of the target XL Release 7.5.0. Make sure to use an **unencrypted** password.

The migrator application contains only JDBC drivers for the `H2` database. If you migrate to another database, you must include the JDBC driver jar file inside the `lib` folder of the migration tool.
	

You can set the page size used to fetch releases from the JCR repository or the archiving repository using the following configuration snippet:

    xl {
      migrator {
        pageSize = 100 # page size when fetching JCR or archived releases
      }
    }

This can be useful when you have large releases that contain a large number of comments for example. 

The migration tool uses 4Gb JVM heap, but if you get an `OutOfMemoryError` during migration then you can fix it by decreasing the page size.


## Running the migrator

You must run the application from the `XL_RELEASE_SERVER_HOME` folder of the **source** server. The migration tool will load your XL Release `conf`, `ext`, and `plugins` folder to load extra synthetic types. 

For example, if the **source** XL Release installation is under 

    /opt/xl-release-7.0.1-server
    
and the migration tool is under 

	/opt/xl-release-sql-migrator-7.5.0 

you should issue the following command:

```
/opt/xl-release-7.0.1-server/$ /opt/xl-release-sql-migrator-7.5.0/bin/xl-release-sql-migrator
```

The source server does not need to be running. The migrator does not alter the repository of the source server. However, we recommend to make a backup of the production server and run the migrator from the backup directory.

Note that the you don't need to specify the installation directory of the **target** server. Configuring the database location is sufficient.

The migrator supports incremental migration. If you stop it in the middle of the process and restart it later, it will skip the releases that were already migrated.


## Troubleshooting

* Make sure the configuration file to set up the database in XLR (`conf/xl-release.conf`) matches exactly with the database configuration in the migrator tool.

* If you need to adjust the configuration in XL Release, rerun the migrator afterwards and it will create the new tables and migrate the existing data with the new settings.

