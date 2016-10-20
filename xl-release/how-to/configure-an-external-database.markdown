#---
title: Configure an external database for use with XL Release 6.0.0+
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- database
weight: 496
since:
- XL Release 6.0.0
---

## Configure the archive database

The archive database must be shared among all nodes when the clustering functionality is enabled. Ensure that every node has access to the shared archive database.

The `xl.reporting` section must include the following parameters:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `db-driver-classname` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver` |
| `db-url` | JDBC URL that describes connection details to a database; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` |
| `db-username` | User name to use to log in to the database |
| `db-password` | Password to use to log in to the database (after setup is complete, the password will be encrypted and stored in secured format) |

**Note:** Place the JAR file containing the JDBC driver of the selected database in the `<XL_RELEASE_SERVER_HOME>/lib` directory.

### Configure the repository database

The repository database must be shared among all nodes when the clustering functionality is enabled.
Ensure that every node has access to the shared repository database.

The `xl.repository.configuration` property contains the name of the predefined repository configuration.
Possible values are:

{:.table .table-striped}
| Parameter             | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| default               | default configuration that uses an embedded Apache Derby database  |
| mysql-standalone      | single instance configuration that uses a MySQL database           |
| mysql-cluster         | cluster ready configuration that uses a MySQL database             |
| oracle-standalone     | single instance configuration that uses an Oracle database         |
| oracle-cluster        | cluster ready configuration that uses an Oracle database           |
| postgresql-standalone | single instance configuration that uses a PostgreSQL database      |
| postgresql-cluster    | cluster ready configuration that uses a PostgreSQL database        |


The `xl.repository.persistence` section must include the following parameters:

{:.table .table-striped}
| Parameter     | Description |
| ---------     | ----------- |
| `jdbcUrl`     | JDBC URL that describes connection details to a database; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"` |
| `username`    | User name to use to log in to the database |
| `password`    | Password to use to log in to the database (after setup is complete, the password will be encrypted and stored in secured format) |
| `maxPoolSize` | Database connection pool size. Suggested value: 20. |


For your convenience here's the example snippet of xl.repository database related configuration:

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

