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
- database
---

It is recommended that you regularly create a backup of your XL Release server. It is especially important to back up XL Release before [upgrading to a new version](/xl-release/how-to/upgrade-xl-release.html).

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you are making a backup.

## Creating a backup

The components that should be included in your backup depend on your XL Release configuration.

### Back up the repository

If you use the built-in JCR repository (the default), back up the files in the `XLRELEASE_HOME/repository` directory.

If you store the repository in a [database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html#using-a-database), back up the files in the `XLRELEASE_HOME/repository` directory, and back up the database itself using the tools provided by your database vendor.

#### Back up the archive database

In XL Release 4.7.0 and later, completed releases are stored in an internal [archive database](/xl-release/how-to/configure-the-archive-database.html). To back up the archive database, back up the files in the `XLRELEASEHOME_archive` directory.

### Back up the configuration

To back up your XL Release configuration, back up the files in the `XLRELEASE_HOME/conf` directory.

### Back up your customizations

To back up your XL Release customizations, back up the files in the `XLRELEASE_HOME/ext` and `XLRELEASE_HOME/plugins` directories.

## Restore a backup

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you restore a backup.

### Restore the repository

If you use the built-in JCR repository, restore it by removing the `XLRELEASE_HOME/repository` directory and replacing it with the backup.

If you store the repository in a database, first remove the `XLRELEASE_HOME/repository` directory and replace it with the backup. Then, restore the backup of the database using the tools provided by your database vendor.

#### Restoring indexes

If you restore a database backup that is not in sync with the backup of the `XLRELEASE_HOME/repository` directory, the indexes will be incorrect and the repository will only show items (such as releases) created before the backup. To fix this issue, delete the indexes and allow XL Release to rebuild them when it starts. To do so, delete the contents of the following directories:

* `XLRELEASE_HOME/repository/repository/index`
* `XLRELEASE_HOME/repository/workspaces/default/index`
* `XLRELEASE_HOME/repository/workspaces/security/index`

### Restore the configuration

To restore your XL Release configuration, remove the `XLRELEASE_HOME/conf` directory and replace it with the backup.

### Restore your customizations

To restore your XL Release customizations, remove the `XLRELEASE_HOME/ext` and `XLRELEASE_HOME/plugins` directories and replace them with the backups.
