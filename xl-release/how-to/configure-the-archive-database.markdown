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

Since XL Release 4.7.0, completed releases are exported to the internal archive database, which is used to generate reports. By default XL Release uses Apache Derby with data stored at `XLRELEASE_HOME/archive/db`.


## Changing location of Apache Derby database.

This can be done by changing configuration at `XLRELEASE_HOME/conf/xl-release.conf`. Create the file if it doesn't exist. For example:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you already have the archive database, then you must move it to the new location while XL Release is not running.

## Configuring archive database with DBMS of different vendor

It is possible to use the following products as archive database:

* Apache Derby (ebmedded);
* H2 (embedded);
* Oracle 11;
* MySQL 4.6.

The following configuration changes need to be made before initializing XL Release repository:

### Change xl-release.conf

In order to do so you have to make sure `XLRELEASE_HOME/conf/xl-release.conf` exists and contains appropriate configuration snippet. For instance:

    xl {
      reporting {
        db-driver-classname = "com.mysql.jdbc.Driver"
        db-url = "jdbc:mysql://mysql-host.db:3306/archive"
        db-username = "xlrelease"
        db-password = "s3cr3t"
      }
    }

### Make sure JDBC driver is available

Drivers of embedded databases (H2 and Apache Derby) are provided with XL Release. Usage of MySQL of Oracle requires appropriate JDBC driver to be added into `XLRELEASE_HOME/plugins`. Such driver can be downloaded from website of the vendor.

## Known limitations

Reporting database needs to be configured before setting up the repository (i.e. starting XL Release server first time). Once the schema and data is present in some database, XL Release doesn't support automation migration into different location/vendor.
