---
title: Archive releases
since: XL Release 4.7.0
---

In XL Release 4.7.0 and later, *completed* and *aborted* releases are archived, which means that they are removed from the XL Release repository (JCR database) and stored in a different internal database, which is called the *archive database*. This improves performance and allows you to create custom hooks that export release information to external databases or reporting tools.

![How archiving works](/xl-release/images/diagram-databases-export-hooks.png)

The differences between archived and unarchived releases are:

* Archived releases are read-only. You cannot add comments to tasks in an archived release.
* Archived releases appear in reports. Releases that are not archived do not appear in reports.
* You can create a custom hook that runs when a release is archived; for example, to store the release in an external reporting database.

## Archiving job

By default, releases are archived right after they finish. Every minute, XL Release runs an archiving job that scans the repository for completed and reported releases, exports them to the archive database, runs custom export hooks, and removes the releases from the repository.

You can configure two parameters for the archiving job:

* The time a release must be completed or aborted before it is moved to the archive. You can configure this in **Settings** > **General Settings** > **Archive**.

    By default, this is 0 days; in other words, releases are archived right after they are completed or aborted. If you would like to be able to add comments to a completed release, increase this value; however, keep in mind that releases will not appear in report until they are archived.

    ![Archiving Settings](/xl-release/images/archiving-settings.png)

* The frequency that the archiving job is run. You can change this in the `xlrelease.ArchivingSettings.archivingJobCronSchedule` property in the `deployit-defaults.properties` file. You must specify the frequency in [cron syntax](http://www.cronmaker.com), which allows you to set frequencies such as "every hour" or "every day at midnight".

**Note:** XL Release reports related to finished releases only use data from the archive database. Therefore, if you configure a very long period before releases are archived, then your reports will not be up-to-date. Also, having a large number of releases that are not archived negatively affects performance. For more information about the configuration of the database, refer to [Configure the archive database](/xl-release/how-to/configure-the-archive-database.html).

## Configure the archive database

Since XL Release 4.7.0, completed releases are exported to the internal [archive database](/xl-release/concept/how-archiving-works.html), which is used to generate reports. By default, XL Release uses Apache Derby, with data stored at `XL_RELEASE_SERVER_HOME/archive/db`.

### Change the location of the Apache Derby database

You can move the Apache Derby database by changing the configuration in `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` (if this file does not exist, create it). For example:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you are already using the archive database, you must move it to the new location while XL Release is not running.

### Change the archive database DBMS (XL Release 4.8.0 and later)

In XL Release 4.8.0 and later, you can use the following products as archive database:

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

### Known limitations

You must configure the archive database before setting up the repository; that is, before starting XL Release for the first time. XL Release does not support automatic migration to a different location or vendor after the schema and data are present in the database.

### Additional database configuration

#### Increase MySQL maximal allowed packet size

XL Release supports attachments up to 100 MB. To store large attachments in the archive database, increase the `max_allowed_packet` configuration option in MySQL. Otherwise, the MySQL server may return "Packet Too Large" errors.

#### Use UTF-8 collation and charset in MySQL

XL Release stores data in UTF-8. To enable the archive database to work with multibyte characters:

* The MySQL server should be configured with the setting `character_set_server=utf8`, or
* The JDBC connection URL should explicitly include the encoding as a URL parameter (`characterEncoding=UTF-8`)

For more information, refer to [Character Sets and Collations in General](https://dev.mysql.com/doc/refman/5.5/en/charset-general.html).

## Export hooks

XL Release supports custom export hooks that you can use to export information about completed and aborted releases. They are run when a release is archived.

Export hooks are written in [Jython](http://www.jython.org/). You can add them to XL Release as JAR files or by placing files in the XL Release classpath.

You can define export hooks in two ways:

* [Generic export hooks](/xl-release/how-to/create-an-export-hook.html) that you can use to export information to any type of storage
* [JDBC export hooks](/xl-release/how-to/create-a-jdbc-export-hook.html) that can export data to an SQL database

A sample export hook implementation is available on [GitHub](https://github.com/xebialabs/xl-release-samples/tree/master/elastic-search-export-hook).

### Failover configuration

If you store the XL Release repository in a [database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html#using-a-database), you can set up a failover configuration as described in [Configure failover for XL Release](/xl-release/how-to/configure-failover.html).
