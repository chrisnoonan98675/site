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

Since XL Release 4.7.0 completed releases are exported to the internal reporting database, which is then used to generate reports. Currently only the default database is supported: Apache Derby. But you can change its location if needed.

To do that create a file `XLR_HOME/conf/xl-release.conf` with following content:

    xl {
      reporting {
        db-url = "jdbc:derby:/path/to/archive/db;create=true"
      }
    }

If you already have the reporting database in the default location (`XLR_HOME/archive/db`), then you also need to move it to the new one. Please do that while XL Release is not running. After moving the database and changing the configuration, you can start XL Release again.
