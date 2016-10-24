---
title: Configure external databases (XL Release 6.0.0 and later)
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- database
since:
- XL Release 6.0.0
---

XL Release 6.0.0 includes an [active/hot-standby](/xl-release/how-to/configure-active-failover.html) feature that requires you to store the XL Release repository and archive database in external databases.

## Configure the archive database

The archive database must be shared among all nodes when active/hot-standby is enabled. Ensure that every node has access to the shared archive database.

The `xl.reporting` section of the `xl-release.conf` configuration file must include the following parameters:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `db-driver-classname` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver`. |
| `db-url` | JDBC URL that describes database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `db-username` | User name to use when logging into the database. |
| `db-password` | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |

Place the JAR file containing the JDBC driver of the selected database in the `XL_RELEASE_SERVER_HOME/lib` directory.

## Configure the repository database

The repository database must be shared among all nodes when when active/hot-standby is enabled. Ensure that every node has access to the shared repository database.

The `xl.repository.configuration` property in the `xl-release.conf` configuration file identifies the predefined repository configuration that you want to use. Possible values are:

{:.table .table-striped}
| Parameter             | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| `default`               | Default configuration that uses an embedded Apache Derby database  |
| `mysql-standalone`      | Single instance configuration that uses a MySQL database           |
| `mysql-cluster`         | Cluster-ready configuration that uses a MySQL database             |
| `oracle-standalone`     | Single instance configuration that uses an Oracle database         |
| `oracle-cluster`        | Cluster-ready configuration that uses an Oracle database           |
| `postgresql-standalone` | Single instance configuration that uses a PostgreSQL database      |
| `postgresql-cluster`    | Cluster-ready configuration that uses a PostgreSQL database        |

The `xl.repository.persistence` section of `xl-release.conf` must include the following parameters:

{:.table .table-striped}
| Parameter     | Description |
| ---------     | ----------- |
| `jdbcUrl`     | JDBC URL that describes the database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `username`    | User name to use when logging into the database |
| `password`    | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |
| `maxPoolSize` | Database connection pool size; suggested value is 20. |

### Sample repository database configuration

This is an example of the `xl.repository` configuration related to the database:

    xl {
      // ...
      repository {
        configuration = "mysql-cluster"
        persistence {
          jdbcUrl = "jdbc:mysql://db/xlrelease"
          username = "xlrelease"
          password = "xlrelease"
          maxPoolSize = "20"
        }
        // ...
      }
      // ...
    }
