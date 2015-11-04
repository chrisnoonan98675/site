---
title: Configure the reporting database
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- reporting
- archiving
- configuration
since:
- 4.7.0
---

Since XL Release 4.7.0, completed releases are exported to the internal reporting database, which is used to generate reports. By default, XL Release uses Apache Derby, with data stored at `XLRELEASE_HOME/archive/db`.

## Change the location of the Apache Derby database

You can move the Apache Derby database by changing the configuration in `XLRELEASE_HOME/conf/xl-release.conf` (if this file does not yet exist, create it). For example:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you are already using the reporting database, you must move it to the new location while XL Release is not running.

## Change the reporting database DBMS (XL Release 4.8.0 and later)

In XL Release 4.8.0 and later, you can use the following products as reporting database:

* Apache Derby (ebmedded)
* H2 (embedded)
* Oracle 11
* MySQL 4.6

To change the DBMS, do the following before initializing the XL Release repository:

1. Ensure that `XLRELEASE_HOME/conf/xl-release.conf` exists and contains the appropriate configuration. For example:

        xl {
          reporting {
            db-driver-classname = "com.mysql.jdbc.Driver"
            db-url = "jdbc:mysql://mysql-host.db:3306/archive?characterEncoding=UTF-8"
            db-username = "xlrelease"
            db-password = "s3cr3t"
          }
        }

2. Ensure that the JDBC driver is available. Drivers of embedded databases (H2 and Apache Derby) are provided with XL Release. Usage of MySQL of Oracle requires appropriate JDBC driver to be added to `XLRELEASE_HOME/plugins`. You can obtain the driver from the website of the vendor.

## Known limitations

You must configure the reporting database before setting up the repository; that is, before starting XL Release for the first time. XL Release does not support automatic migration to a different location or vendor after the schema and data are present in the database.

## Additional database configuration

### Increase MySQL maximal allowed packet size

XL Release supports attachments up to 100 MB. Increase the value of the `max_allowed_packet` configuration option in MySQL to be able to store large attachments in XL Release's reporting database. Otherwise, the MySQL server may respond with 'Packet Too Large' errors.

### Use UTF-8 collation and charset in MySQL

XL Release stores data in UTF-8. To allow the reporting database to work properly with multibyte characters, the MySQL server should be either configured with the setting `character_set_server=utf8`, or the JDBC connection URL should explicitly include the encoding as a URL parameter: `characterEncoding=UTF-8`.

More information at [dev.mysql.com](https://dev.mysql.com/doc/refman/5.5/en/charset-general.html).
