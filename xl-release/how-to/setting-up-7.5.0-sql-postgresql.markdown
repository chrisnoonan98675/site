# Setting up XL Release 7.5.0 with active releases in SQL database (PostgreSQL)

## Things to consider


* We start with a clean slate approach in this document. An external migrator application is needed to move data from a previous version of XL Release. This is discussed in a separate document.
* Note that you can't migrate data to into an XL Release 7.5.0 version that already has already been started. When upgrading, the use another database schema.


## Download and install

* Download xl-release-7.5.0-beta-1.zip
* Unzip xl-release-7.5.0-beta-1

## Configure PostgreSQL

Create two databases / users in PostgreSQL:

1. "xlrelease" for active releases
2. "xlarchive" for archived releases / reporting

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
}
```

_Please note that passwords will be encrypted in this file by XL Release_

## Initialize XL Release

We start with a clean database. (Migration will be dealt with in a separate topic)

* Run `bin/run.sh`
* Follow instructions to setup the server

After answering the configuration questions, the databases will be initialized and XL Release will start up in single-node setup.
