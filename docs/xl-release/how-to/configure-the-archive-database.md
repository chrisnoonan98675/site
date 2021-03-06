---
title: Configure the archive database
categories:
xl-release
subject:
Archiving and data export
tags:
system administration
reporting
archiving
database
since:
XL Release 4.7.0
weight: 437
---

XL Release stores completed releases in a database that is separate from the repository: the archived database. Besides the completed releases, metadata for reporting is stored in this database.

For more information about the archiving process, see [How archiving works](/xl-release/concept/how-archiving-works.html).

In the default setup, an embedded database is used and the data is stored in the
`XL_RELEASE_SERVER_HOME/archive/` directory.

You must configure the archive database before setting up the repository; that is, before starting XL Release for the first time. XL Release does not support automatic migration to a different location or vendor after the schema and data are present in the database.

## Configure the archive database (XL Release 4.8.0 7.2.0)

For XL Release versions 4.8.0 to 7.2.x, you can use the following products as archive database:

* Apache Derby (embedded)
* H2 (embedded)
* Oracle 11
* MySQL 4.6

To change the database, do the following before initializing the XL Release repository:

1. Ensure that `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` exists and contains the appropriate configuration. For example:

        xl {
          reporting {
            db-driver-classname = "com.mysql.jdbc.Driver"
            db-url = "jdbc:mysql://mysql-host.db:3306/archive?characterEncoding=UTF-8"
            db-username = "xlrelease"
            db-password = "s3cr3t"
          }
        }

2. Ensure that the JDBC driver is available. Drivers of embedded databases (H2 and Apache Derby) are provided with XL Release. Usage of MySQL of Oracle requires appropriate JDBC driver to be added to `XL_RELEASE_SERVER_HOME/plugins`. You can obtain the driver from the website of the vendor.

### Additional database configuration

#### Increase maximum allowed packet size in MySQL

XL Release supports attachments up to 100 MB. To store large attachments in the archive database, increase the `max_allowed_packet` configuration option in MySQL. Otherwise, the MySQL server may return "Packet Too Large" errors.

#### Use UTF-8 collation and charset in MySQL

XL Release stores data in UTF-8. To enable the archive database to work with multibyte characters:

* The MySQL server should be configured with the setting `character_set_server=utf8`, or
* The JDBC connection URL should explicitly include the encoding as a URL parameter (`characterEncoding=UTF-8`)

For more information, refer to [Character Sets and Collations in General](https://dev.mysql.com/doc/refman/5.5/en/charset-general.html).

## Configure the archive database (XL Release 7.5.0 and later)

For setting up the archive database in **XL Release 7.5.0 and later**, refer to [Configure the XL Release SQL repository in a database](/xl-release/how-to/configure-the-xl-release-sql-repository-in-a-database.html).

## Change the location of the embedded archive database

You can move the Apache Derby database by changing the configuration in `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` (if this file does not exist, create it). For example:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you are already using the archive database, you must move it to the new location while XL Release is not running.
