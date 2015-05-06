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

Create or update the file `conf/xl-release.conf` and add the following section:

    xl {
      reporting {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:archive/db;create=true"
        db-username = ""
        db-password = ""
      }
    }

Where:

* **db-driver-classname** is the Java classname of the JDBC Driver to be used
* **db-url** is the database's connection URL
* **db-username** is the database's username
* **db-password** is the database's password