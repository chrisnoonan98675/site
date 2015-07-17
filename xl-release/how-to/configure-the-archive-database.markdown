---
title: Configure the archive database
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- reporting
- archiving
- archive database
- configuration
since:
- 4.7.0
---

Since XL Release 4.7.0, completed releases are exported to the internal archive database, which is used to generate reports. By default, XL Release uses Apache Derby, with data stored at `XLRELEASE_HOME/archive/db`.

## Change the location of the Apache Derby database

Youcan move the Apache Derby database by changing the configuration in `XLRELEASE_HOME/conf/xl-release.conf` (if this file does not yet exist, create it). For example:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you are already using the archive database, you must move it to the new location while XL Release is not running.

## Change the archive database DBMS

You can use the following products as archive database:

* Apache Derby (ebmedded)
* H2 (embedded)
* Oracle 11
* MySQL 4.6

To change the DBMS, do the following before initializing the XL Release repository:

1. Ensure that `XLRELEASE_HOME/conf/xl-release.conf` exists and contains the appropriate configuration. For example:

        xl {
          reporting {
            db-driver-classname = "com.mysql.jdbc.Driver"
            db-url = "jdbc:mysql://mysql-host.db:3306/archive"
            db-username = "xlrelease"
            db-password = "s3cr3t"
          }
        }

2. Ensure that the JDBC driver is available. Drivers of embedded databases (H2 and Apache Derby) are provided with XL Release. Usage of MySQL of Oracle requires appropriate JDBC driver to be added to `XLRELEASE_HOME/plugins`. You can obtain the driver from the website of the vendor.

## Known limitations

You must configure the archive database before setting up the repository; that is, before starting XL Release for the first time. XL Release does not support automatic migration to a different location or vendor after the schema and data are present in the database.
