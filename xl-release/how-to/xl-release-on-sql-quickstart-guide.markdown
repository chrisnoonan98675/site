---
title: XL Release on SQL Quickstart Guide
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
- database
- repository
weight: 493
---

// TODO: Disclaimer: BETA feature...

Since version 7.2, XL Release can store active releases in an SQL database rather than in a jackrabbit repository.

Global settings, user profiles and shared configurations are still persisted in jackrabbit.

Like previous versions, XL Release 7.2 uses an SQL database to archive completed and aborted releases.

## Overview

There are now three entries in the `xl-release.conf` configuration file that needs to be defined:

* `xl.repository`: Defines the Jackrabbit repository, along with `jackrabbit-repository.xml` (unchanged);
* `xl.reporting`: Defines the SQL database to use for archived releases (unchanged);
* `xl.database`: Defines the SQL database for active releases, folders and permissions (new).


## Enabling SQL

To enable this new feature, you need to:

1. Start from a fresh installation of XL Release 7.2.0
2. Set `xl.repository.sql` to `true`;
3. Configure `xl.database` to point to an existing SQL database;
4. Download the correct JDBC driver for your database and copy it under `XL_RELEASE_SERVER_HOME/lib`.

Detailed instructions for each supported database can be found in the "Configuration" section below.


## Supported databases

Currently we support the following SQL databases:

{:.table .table-striped}
| SQL Database  | Version   |
| ------------- | --------- |
| Derby         |           |
| H2            |           |
| PostgreSQL    | 9.5       |
| MySQL         | 5.7       |
| Oracle        | xe-11g    |


## Minimal setup

The default configuration with SQL mode enabled will use an embedded Derby database in `XL_RELEASE_SERVER_HOME/repository/db`.

`xl-release.conf`:

{% highlight config %}
xl {
    repository {
        sql = true
    }
}
{% endhighlight %}


## SQL Database configuration

The `xl.database` section in `xl-release.conf` is composed by the following entries:

* `db-driver-classname`: the full class name of the driver, refer to the table above
* `db-url`: the jdbc url pointing to the database
* `db-username`: username used to access the database
* `db-password`: password for `db-username`
* `max-pool-size`: maximum number of database connections to use

Note: the `xl.reporting` section is similar except that `max-pool-size` cannot be configured and defaults to 10.

The following table shows the value for `db-driver-class-name` for each supported database and directions to obtain the corresponding jdbc driver.

{:.table .table-striped}
| SQL Database  | Driver class name                        |  Driver download URL     |
| ------------- | ---------------------------------------- | -------------------------|
| Derby         | `org.apache.derby.jdbc.AutoloadedDriver` | _included in XL Release_ |
| H2            | `org.h2.Driver`                          | _included in XL Release_ |
| PostgreSQL    | `org.postgresql.Driver`                  | [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download.html) |
| MySQL         | `com.mysql.jdbc.Driver`                  | [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) |
| Oracle        | `oracle.jdbc.OracleDriver`               | [OJDBC Driver](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html)


## Configuration examples

We assume the following if not otherwise stated:

* the database server is at `db1.example.com`
* the database uses its default port
* the name of the database is "xlreleasedb"
* the username used to connect to the database is "xlrelease"
* the password is "secret"

### PostgreSQL

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "org.postgresql.Driver"
        db-url = "jdbc:postgresql://db1.example.com:5432/xlreleasedb"
        db-username = "xlrelease"
        db-password = "secret"
        max-pool-size = 100
    }
}
{% endhighlight %}

### MySQL

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "com.mysql.jdbc.Driver"
        db-url = "jdbc:mysql://db1.example.com:3306/xlreleasedb"
        db-username = "xlrelease"
        db-password = "secret"
        max-pool-size = 100
    }
}
{% endhighlight %}


Note: MySQL server configuration should include the following entries:

`my.cnf`:

{% highlight config %}
[mysqld]
sql_mode="NO_AUTO_VALUE_ON_ZERO"
max_allowed_packet=16M
{% endhighlight %}

### Oracle

Please note that in this example there is no database name since Oracle uses the username instead.

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "oracle.jdbc.OracleDriver"
        db-url = "jdbc:oracle:thin:db1.example.com:1521/xe"
        db-username = "xlrelease"
        db-password = "secret"
        max-pool-size = 100
    }
}
{% endhighlight %}


### Derby

This example will use a local directory, namely `XL_RELEASE_SERVER_HOME/repository/db` for storage.

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:directory:repository/db"
        db-username = "xlrelease"
        db-password = "secret"
        max-pool-size = 100
    }
}
{% endhighlight %}

For more information about the jdbc url, please refer to Derby documentation: [Derby Database connection examples](https://db.apache.org/derby/docs/10.8/devguide/rdevdvlp22102.html#rdevdvlp22102)

### H2

This example will use a local file, namely `XL_RELEASE_SERVER_HOME/repository/db` for storage

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "org.h2.Driver"
        db-url = "jdbc:h2:file:repository/db"
        db-username = "xlrelease"
        db-password = "secret"
        max-pool-size = 100
    }
}
{% endhighlight %}

For more information about the jdbc url, please refer to H2 documentation: [H2 Database URL](http://www.h2database.com/html/features.html#database_url)
