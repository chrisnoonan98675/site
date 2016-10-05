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
weight: 495
---

It is recommended that you regularly create a backup of your XL Release server. It is especially important to back up XL Release before [upgrading to a new version](/xl-release/how-to/upgrade-xl-release.html).

## Create a backup

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you are creating a backup.

To back up XL Release, it is recommended that you back up the complete installation folder (referred to here as `XL_RELEASE_SERVER_HOME`); for example, by compressing it in a ZIP file.

If you store the XL Release repository in a [database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html#using-a-database), back up the database using the tools provided by your database vendor. Note that you must also back up the `XL_RELEASE_SERVER_HOME/repository` directory, even if you use a database.

## Restore a backup

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you restore a backup.

### Restore the repository

If you use the built-in JCR repository, restore it by removing the `XL_RELEASE_SERVER_HOME/repository` directory and replacing it with the backup.

If you store the repository in a database, first remove the `XL_RELEASE_SERVER_HOME/repository` directory and replace it with the backup. Then, restore the backup of the database using the tools provided by your database vendor.

#### Restoring indexes

If you restore a database backup that is not in sync with the backup of the `XL_RELEASE_SERVER_HOME/repository` directory, the indexes will be incorrect and the repository will only show items (such as releases) created before the backup. To fix this issue, delete the indexes and allow XL Release to rebuild them when it starts. To do so, delete the contents of the following directories:

* `XL_RELEASE_SERVER_HOME/repository/repository/index`
* `XL_RELEASE_SERVER_HOME/repository/workspaces/default/index`
* `XL_RELEASE_SERVER_HOME/repository/workspaces/security/index`

### Restore the configuration

To restore your XL Release configuration, remove the `XL_RELEASE_SERVER_HOME/conf` directory and replace it with the backup.

### Restore your customizations

To restore your XL Release customizations, remove the `XL_RELEASE_SERVER_HOME/ext` and `XL_RELEASE_SERVER_HOME/plugins` directories and replace them with the backups.
