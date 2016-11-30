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
weight: 497
since:
- XL Release 6.0.0
---

XL Release 6.0.0 includes an [active/hot-standby](/xl-release/how-to/configure-active-hot-standby.html) feature that requires you to store the XL Release repository database and archive database in external databases.

## Recommended databases

The following external databases are recommended:

* MySQL
* PostgreSQL
* Oracle 11g or 12c

## Step 1 Configure the archive database

The archive database must be shared among all nodes when active/hot-standby is enabled. Ensure that every node has access to the shared archive database.

To configure the archive database, first add the following parameters to the `xl.reporting` section of the `xl-release.conf` configuration file:

{:.table .table-striped}
| Parameter | Description |
| --------- | ----------- |
| `db-driver-classname` | Class name of the database driver to use; for example, `oracle.jdbc.driver.OracleDriver`. |
| `db-url` | JDBC URL that describes database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `db-username` | User name to use when logging into the database. |
| `db-password` | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |

Then, place the JAR file containing the JDBC driver of the selected database in the `XL_RELEASE_SERVER_HOME/lib` directory. To download the JDBC database drivers:

{:.table .table-striped}
| Database   | JDBC drivers | Notes   |
| ---------- | ------------ | ------- |
| MySQL      | [Connector\J 5.1.30 driver download](http://dev.mysql.com/downloads/connector/j/)| None. |
| Oracle     | [JDBC driver downloads](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html) | For Oracle 12c, use the 12.1.0.1 driver (`ojdbc7.jar`). It is recommended that you only use the thin drivers; refer to the [Oracle JDBC driver FAQ](http://www.oracle.com/technetwork/topics/jdbc-faq-090281.html) for more information. |
| PostgreSQL | [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)| Use the JDBC42 version, because XL Release 4.8.0 and later requires Java 1.8. |

## Step 2 Configure the repository database

The repository database must be shared among all nodes when when active/hot-standby is enabled. Ensure that every node has access to the shared repository database.

To configure the repository database, first add the `xl.repository.configuration` property to the `xl-release.conf` configuration file. This property identifies the predefined repository configuration that you want to use. Supported values are:

{:.table .table-striped}
| Parameter             | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| `default`               | Default configuration that uses an embedded Apache Derby database.  |
| `mysql-standalone`      | Single instance configuration that uses a MySQL database.           |
| `mysql-cluster`         | Cluster-ready configuration that uses a MySQL database.             |
| `oracle-standalone`     | Single instance configuration that uses an Oracle database.         |
| `oracle-cluster`        | Cluster-ready configuration that uses an Oracle database.          |
| `postgresql-standalone` | Single instance configuration that uses a PostgreSQL database.      |
| `postgresql-cluster`    | Cluster-ready configuration that uses a PostgreSQL database.        |

Next, add the following parameters to the `xl.repository.persistence` section of `xl-release.conf`:

{:.table .table-striped}
| Parameter     | Description |
| ---------     | ----------- |
| `jdbcUrl`     | JDBC URL that describes the database connection details; for example, `"jdbc:oracle:thin:@oracle.hostname.com:1521:SID"`. |
| `username`    | User name to use when logging into the database. |
| `password`    | Password to use when logging into the database (after setup is complete, the password will be encrypted and stored in secured format). |
| `maxPoolSize` | Database connection pool size; suggested value is 20. |

### Sample repository database configuration

This is an example of the `xl.repository` configuration related to the database:

    xl {
        repository {
            configuration = postgresql-standalone
            persistence {
                jdbcUrl = "jdbc:postgresql://db/xlrelease?ssl=false"
                username = "xlrelease"
                password = "xlrelease"
                maxPoolSize = 20
            }
            jackrabbit {
                artifacts.location="repository"
                bundleCacheSize = 128
            }
        }
        reporting {
            db-driver-classname=org.postgresql.Driver
            db-url="jdbc:postgresql://db/xlrarchive?ssl=false"
            db-password="xlrarchive"
            db-username="xlrarchive"
        }
    }
