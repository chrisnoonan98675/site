# Setting up XL Release 7.2.0 with active releases in SQL database (PostgreSQL)

## Things to consider

* XL Release 7.2.0 active/active beta still has parts of the data in JCR. The JCR part will be removed for XL Release 7.5, but for now we still have to deal with it.
* We do not support migrating data out of the beta installation into XL Release 7.5
* Migrating data from an existing installation into XL Release 7.2.0 active/active beta is possible, but we start with a clean slate approach in this document. An external migrator application is needed and will be provided later.


## Download and install

* Download [xl-release-7.2.0-server.zip](https://dist.xebialabs.com/xl-release-trial.zip)
* Unzip xl-release-7.2.0

## Configure PostgreSQL

Create three databases / users in PostgreSQL:

1. "repository" (for JCR, still needed in this beta, will be gone in 7.5)
2. "xlarchive" (for reporting)
3. "xlrelease" to use for xl.database

## Configure XL Release

* [Download PostgreSQL driver for Java 8](https://jdbc.postgresql.org/download.html) and install the jar file in the `plugins` folder.

* Edit `conf/xl-release.conf`. This example assumes a separate database for each user. Please configure database details to point to your PostgreSQL instance.

```
xl {
    cluster.mode = default
    database {
        db-driver-classname="org.postgresql.Driver"
        db-password="xlrelease"
        db-url="jdbc:postgresql://localhost:5432/xlrelease"
        db-username=xlrelease
    }
    reporting {
        db-driver-classname="org.postgresql.Driver"
        db-password="xlarchive"
        db-url="jdbc:postgresql://localhost:5432/xlarchive"
        db-username=xlarchive
    }
    repository {
        configuration=postgresql-standalone
        persistence {
            jdbcUrl="jdbc:postgresql://localhost:5432/xlrepository"
            maxPoolSize=20
            password="xlrepository"
            username=xlrepository
        }
        sql=true
    }
}
```

_Please note that passwords will be encrypted in this file by XL Release_

## Initialize XL Release

We start with a clean database. (Migration will be dealt with in a separate topic)

* Run `bin/run.sh`
* Follow instructions to setup Jackrabbit repository

After answering the configuration questions, all databases will be initialized and XL Release will start up in single-node setup.
