---
title: Back up XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- backup
- maintenance
---

## Creating backups

To create a backup of XL Release, several components may need to be backed up depending on your configuration:

* Repository:
    * Built-in repository: Create a backup of the built-in JCR repository by backing up the files in the `repository` directory.
    * Database repository: Create a backup of the files in the `repository` directory, and the database (using the tools provided by your database vendor).
* Archive (XL Release 4.7 and higher): Create a backup of the local archive database by backing up the files in the `archive` directory.
* Configuration: Create a backup of the XL Release configuration by backing up the files in the `conf` directory in the installation directory.

**Important:** XL Release must not be running when you are making a backup. Schedule backups outside planned deployment hours to ensure the server is not being used.

## Restoring backups

To restore a backup of XL Release, restore one of the following components:

* JCR repository:
    * Built-in repository: Remove the `repository` directory and replace it with the backup.
    * Database repository: Remove the `repository` directory and replace it with the backup. Then, restore a backup of the database using the tools provided by your vendor.
* Archive (XL Release 4.7 and higher): Remove the `archive` directory and replace it with the backup.
* Configuration: Remove the `conf` directory in the `XL_RELEASE_SERVER_HOME` directory and replace it with the backup.

**Important:** XL Release must not be running when you are restoring a backup.
