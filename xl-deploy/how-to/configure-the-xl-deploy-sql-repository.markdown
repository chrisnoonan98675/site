---
title: Configure the XL Deploy SQL repository (XL Deploy 8.0 and later)
categories:
- xl-deploy
subject:
- Repository
tags:
- system administration
- repository
- database
- sql
since:
- XL Deploy 8.0.0
---

XL Deploy uses a repository to store all of its data such as configuration items (CIs), deployment packages, logging, etc. XL Deploy can use the filesystem or a database for binary artifacts (deployment packages) and CIs and CI history.

By default, XL Deploy uses the filesystem to store all data in the repository. For production use, it is strongly recommended to use an industrial-grade external database server. The following databases are supported:

* PostgreSQL versions 9.3, 9.4, 9.5, 9.6, and 10.1
* MySQL versions 5.5, 5.6, and 5.7
* Oracle 11g
* Microsoft SQL Server 2012 and later
* DB2 versions 10.5 and 11.1

## Location of the repository

By default, the repository is located in `XL_DEPLOY_SERVER_HOME/repository`. You can change the location of the repository in the  `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file.

## Using a database server

The default setting in XL Deploy is to use the internal database that stores the data on the file system. This is intended for temporary use and is not recommended for production use.

* When the installation is upgraded to a new version, XL Deploy creates and maintains the database schema. The database administrator requires the following permissions on the database: REFERENCES, INDEX, CREATE, DROP, in addition to the permissions used in operation: SELECT, INSERT, UPDATE, DELETE.

* Table definitions in XL Deploy use limited column sizes. You must configure this for all the supported databases to prevent these limits from restricting users in how they can use XL Deploy.
Example: The ID of a Configuration Item (CI) is a path-like structure and consists of the concatenation of the names of all the parent folders for the CI. A restriction is set on the length of this combined structure. The maximum character length for all the supported databases is set to 850 chars.

XL Deploy can be configured to use two different database connections: one for primary XL Deploy data and one for the task archive. Both database connections can be configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file. The main database connection can be configured under the `repository` key and the database connection for the task archive can be configured under the `reporting` key. The default configuration for the `repository` database connection is also used for the `reporting` connection.

For information about:

* Backing up the database, refer to [Back up XL Deploy](/xl-deploy/how-to/back-up-xl-deploy.html)
* Active/hot standby mode: refer to [Configure active/hot-standby](/xl-deploy/how-to/configure-xl-deploy-active-hot-standby.html)

### Artifacts in XL Deploy

In addition to storing the data, XL Deploy stores user supplied artifacts in the database, such as scripts or deployment packages (`jar` or `war` files). These can be stored on the file system (this is the default setting) or in the database server. XL Deploy can use only one of these options at any time. That is why you must configure the database correctly before using XL Deploy in a production setting. This setting can be configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file.

### Preparing the database and repository

Before installing XL Deploy, create an empty database. XL Deploy will create the database schema during installation.

The account that accesses the database must be able to create tables during the initial installation and, later, it must be able to write to and delete from tables.

There are no requirements for the character set of the database.

**Important:** XL Deploy must initialize the repository before it can be used. You must run [XL Deploy's setup wizard](/xl-deploy/how-to/install-xl-deploy.html#run-the-server-setup-wizard) and initialize the repository after making any changes to the repository configuration.

The following set of SQL privileges are required.

**During installation / upgrade**:

* REFERENCES
* INDEX
* CREATE
* DROP

**During operation**:

* SELECT
* INSERT
* UPDATE
* DELETE

#### The configuration file: `xl-deploy.conf`

All the configuration is done in `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf`.

This file is in [HOCON](https://github.com/typesafehub/config/blob/master/HOCON.md) format.

After the first run, passwords in the configuration file will be encrypted and replaced with the base64-encoded encrypted values.

**Important:** You can configure the settings for the artifacts in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file.
Specify type of artifact storage to use. Valid values are:
* `file`: use the specified file system location for storing artifacts.
* `db`: use the database for storing artifacts.

Set the location for artifact storage on file system. Only active when `type = "file"`.

         artifacts {
            type = "file"
            root = ${xl.repository.root}"/artifacts"
        }

### Database-specific configurations in XL Deploy

#### Using XL Deploy with MySQL

To use XL Deploy with [MySQL](http://www.mysql.com/), ensure that the [JDBC driver for MySQL](http://dev.mysql.com/downloads/connector/j/) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

Make sure the userid accessing the MySQL database has been granted the following permissions:
* GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, REFERENCES, DROP, and INDEX on `dbname`.* to *dbuser*@*host* for database initialization and for XL Deploy version upgrades
* GRANT SELECT, INSERT, UPDATE, and DELETE on `dbname`.* to *dbuser*@*host* for ongoing usage

Configure the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` to point to the database schema.

This is a sample configuration for MySQL:

        xl {
            repository {
                database {
                    db-driver-classname="com.mysql.jdbc.Driver"
                    db-password="samplepassword"
                    db-url="jdbc:mysql://localhost:3306/xldrepo?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false"
                    db-username=sample-user
                    max-pool-size=10
                }
            }
        }

**Notes:**
1. The MySQL database does not support full unicode character set. For more information, see [MySQL documentation](https://dev.mysql.com/doc/refman/5.7/en/charset-unicode-utf8mb3.html).    
1. For MySQL, XL Deploy requires the [`innodb_large_prefix`](https://dev.mysql.com/doc/refman/5.7/en/innodb-parameters.html#sysvar_innodb_large_prefix) option to be `ON`. For more information, see the MySQL version specific documentation.

#### Using XL Deploy with DB2

To use XL Deploy with [IBM DB2](http://www-01.ibm.com/software/data/db2/), ensure that the [JDBC driver for DB2](http://www-01.ibm.com/support/docview.wss?uid=swg21363866) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

Configure the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` to point to the database schema.

This is a sample configuration for DB2:

        xl {
            repository {
                database {
                    db-driver-classname="com.ibm.db2.jcc.DB2Driver"
                    db-password="sample-password"
                    db-url="jdbc:db2://localhost:50000/xldrepo"
                    db-username=sample-user
                }
            }
        }

#### Using XL Deploy with Oracle

To use XL Deploy with [Oracle](http://www.oracle.com/us/products/database/index.html), ensure that the [JDBC driver for Oracle](http://www.oracle.com/technetwork/database/features/jdbc/default-2280470.html) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

Configure the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` to point to the database schema.

This is a sample configuration for Oracle:

        xl {
            repository {
                database {
                    db-driver-classname="oracle.jdbc.OracleDriver"
                    db-password="samplepassword"
                    db-url="jdbc:oracle:thin:@//localhost:1521/xe"
                    db-username=sample-user
                    max-pool-size=10
                }
            }
        }

**Note:** If you use the TNSNames Alias syntax to connect to Oracle, you may need to inform the driver where to find the `TNSNAMES` file. Refer to the Oracle documentation for more information.

#### Using XL Deploy with SQL Server

To use XL Deploy with [Microsoft SQL Server](https://www.microsoft.com/en-us/server-cloud/products/sql-server/), ensure that the [JDBC driver for SQL Server](https://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx) JAR file is located in `XL_DEPLOY_SERVER_HOME/lib` or on the Java classpath.

Configure the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` to point to the database schema.

This is a sample configuration for SQL Server:

        xl {
            repository {
                database {
                    db-driver-classname="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                    db-password="samplepassword"
                    db-url="jdbc:sqlserver://localhost:1433"
                    db-username=sample-user
                    max-pool-size=10
                }
            }
        }

#### Use XL Deploy with PostgreSQL

Driver:

 * [PostgreSQL JDBC driver](https://jdbc.postgresql.org/download.html)

Place the driver JAR file in the `XL_RELEASE_SERVER_HOME/lib` folder.

Configure the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` to point to the database schema.

This is a sample configuration for PostgreSQL:

        xl {
            repository {
                database {
                    db-driver-classname="org.postgresql.Driver"
                    db-password="samplepassword"
                    db-url="jdbc:postgresql://localhost/postgres"
                    db-username=sample-user
                    max-pool-size=10
                }
            }
        }
