---
title: Configure the reporting database
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- reporting
- configuration
since:
- 4.7.0
---

To configure the XL Release reporting database, add the following section to the `conf/xl-release.conf` file:

    xl {
      reporting {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:archive/db;create=true"
        db-username = ""
        db-password = ""
      }
    }

Where:

* `db-driver-classname` is the Java classname of the JDBC Driver to be used
* `db-url` is the database connection URL
* `db-username` is the database username
* `db-password` is the database password

Save the file and restart XL Release.
