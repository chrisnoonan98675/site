---
title: Persistence Configuration Manual
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

# Configuration

## Overview

There are now three entries in the `xl-release.conf` configuration file that needs to be defined:

* `xl.repository`: Defines the Jackrabbit repository, along with `jackrabbit-repository.xml` (unchanged);
* `xl.reporting`: Defines the SQL database to use for archived releases (unchanged);
* `xl.database`: Defines the SQL database for active releases, folders and permissions (new).


## Enabling SQL

To enable this new feature, you need to:

1. Set `xl.repository.sql` to `true`;
2. Configure `xl.database` to point to an existing SQL database;
3. Download the correct JDBC driver for your database and copy it under `XL_RELEASE_SERVER_HOME/lib`.

Detailed instructions for each supported database can be found in the "Configuration" section below.


## Supported databases

Currently we support the following SQL databases:

{:.table .table-striped}
| SQL Database  | Version   | Driver class name                        |  Driver download URL     |
| ------------- | --------- | ---------------------------------------- | -------------------------|
| Derby         |           | `org.apache.derby.jdbc.AutoloadedDriver` | _included in XL Release_ |
| H2            |           | `org.h2.Driver`                          | _included in XL Release_ |
| PostgreSQL    | 9.5       | `org.postgresql.Driver`                  | PostgreSQL JDBC Driver [https://jdbc.postgresql.org/download.html](https://jdbc.postgresql.org/download.html) |
| MySQL         | 5.7       | `com.mysql.jdbc.Driver`                  | MySQL Connector/J [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/) |
| Oracle        | xe-11g    | `oracle.jdbc.OracleDriver`               | OJDBC Driver [http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html](http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html)


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

## Basic setup

`xl-release.conf`:


{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "org.postgresql.Driver"
        db-url = "jdbc:derby:repository/db;create=true"
        db-username = ""
        db-password = ""
        max-pool-size = 10
    }
}
{% endhighlight %}


## Default Configuration

The default configuration has SQL mode disabled, but if enabled, it will use an embedded Derby database in `XL_RELEASE_SERVER_HOME/repository/db`.

For more information about configuring jackrabbit (`xl.repository`) and the archive (`xl.reporting`), please refer to the
"Configure the XL Release repository in a database" how-to:
[https://docs.xebialabs.com/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html](https://docs.xebialabs.com/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html).

These are the default settings in `xl-release.conf`:

{% highlight config %}
xl {
    repository {
        # location of the repository configuration file (jackrabbit-repository.xml by default)
        # if configuration file is not present on the filesystem, repository configuration will be taken from xl.repository.configuration profile
        configurationLocation = "jackrabbit-repository.xml"

        # configuration profile to load when configurationLocation does not exist (jackrabbit-repository-default.xml in this case)
        configuration = "default"

        # connection details for the repository database
        persistence {
          # default maxPoolSize
          maxPoolSize = 20
        }

        cluster {
          enabled = no
        }

        # jackrabbit configuration
        jackrabbit {
          # bundle cache size for the "default" workspace, default value -1 means the size is determined dynamically based on the heap size, one 10th is then used approx.
          bundleCacheSize = -1
          # settings related to jackrabbit datastore
          artifacts {
            # datastore location on filesystem, mandatory property in provided clustered configurations
            # location = ""
          }
        }

        # 'false' if passwords should be encrypted when retrieved from the repository, 'true' for backwards compatible behaviour
        decryptPasswords = false

        # SQL database mode (beta feature)
        sql = false
    }

    # SQL database for active releases, folders and permissions. Defaults to embedded Derby database in repository/db
    database {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:repository/db;create=true"
        db-username = ""
        db-password = ""
        max-pool-size = 10
    }

    # SQL database for archived releases. Defaults to embedded Derby database in archive/db
    reporting {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:archive/db;create=true"
        db-username = ""
        db-password = ""
    }

}
{% endhighlight %}

In the following sections, we will describe example configurations for each supported database.
You can of course do the same for the "reporting" database, but keep in mind that `repository`, `database` and `reporting` cannot share the same database.

## H2

Driver: // TODO: url to h2 driver here

{% highlight config %}
xl {
    repository {
        sql = true
    }
    database {
        db-driver-classname = "org.h2.Driver"
        db-url = "jdbc:h2:file:./repository/db
        db-username = "xlrelease"
        db-password = "secretpassword"
        max-pool-size = 10
    }
}
{% endhighlight %}

## Derby

No additional drivers needed.

{% highlight config %}
xl {
    database {
        db-driver-classname = "org.apache.derby.jdbc.AutoloadedDriver"
        db-url = "jdbc:derby:repository/db;create=true"
        db-username = "xlrelease"
        db-password = "secretpassword"
        max-pool-size = 10
    }
}
{% endhighlight %}

## PostgreSQL

Driver: [http://repo1.maven.org/maven2/org/postgresql/postgresql/9.4.1211/postgresql-9.4.1211.jar](http://repo1.maven.org/maven2/org/postgresql/postgresql/9.4.1211/postgresql-9.4.1211.jar)


## MySQL

Driver: [http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.39/mysql-connector-java-5.1.39.jar](http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.39/mysql-connector-java-5.1.39.jar)

## Oracle

Driver: // TODO