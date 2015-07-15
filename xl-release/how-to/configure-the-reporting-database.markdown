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

Since XL Release 4.7.0, completed releases are exported to the internal reporting database, which is used to generate reports. XL Release only supports the default reporting database, Apache Derby. You can change the database location if needed.

To do so, create an `XLRELEASE_HOME/conf/xl-release.conf` file with following content:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you already have the reporting database in the default location (`XLRELEASE_HOME/archive/db`), then you must move it to the new location while XL Release is not running. After moving the database and changing the configuration, you can start XL Release again.
