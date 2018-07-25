---
title: Back up XL Deploy
categories:
xl-deploy
subject:
System administration
tags:
system administration
repository
database
backup
upgrade
weight: 268
---

It is recommended that you regularly create a backup of your XL Deploy server. It is especially important to back up XL Deploy before [upgrading to a new version](/xl-deploy/how-to/upgrade-xl-deploy.html).

## Create a backup

**Important:** XL Deploy [must not be running](/xl-deploy/how-to/shut-down-xl-deploy.html) when you are creating a backup. Schedule backups outside planned deployment hours to ensure that the server is not being used.

To back up XL Deploy, it is recommended that you back up the complete installation folder (referred to here as `XL_DEPLOY_SERVER_HOME`); for example, by compressing it in a ZIP file.

If you store the XL Deploy repository in a [database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database), back up the database using the tools provided by your database vendor. Note that you must also back up the `XL_DEPLOY_SERVER_HOME/repository` directory, even if you use a database.

## Restore a backup

**Important:** XL Deploy [must not be running](/xl-deploy/how-to/shut-down-xl-deploy.html) when you restore a backup.

### Restore the repository

If you use the built-in JCR repository, restore it by removing the `XL_DEPLOY_SERVER_HOME/repository` directory and replacing it with the backup.

If you store the repository in a database, first remove the `XL_DEPLOY_SERVER_HOME/repository` directory and replace it with the backup. Then, restore the backup of the database using the tools provided by your database vendor.

#### Restoring indexes

If you restore a database backup that is not in sync with the backup of the `XL_DEPLOY_SERVER_HOME/repository` directory, the indexes will be incorrect and the repository will only show configuration items (CIs) created before the backup. To fix this issue, delete the indexes and allow XL Deploy to rebuild them when it starts. To do so, delete the contents of the following directories:

* `XL_DEPLOY_SERVER_HOME/repository/repository/index`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/default/index`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/security/index`

### Restore the configuration

To restore your XL Deploy configuration, remove the `XL_DEPLOY_SERVER_HOME/conf` directory and replace it with the backup.

### Restore your customizations

To restore your XL Deploy customizations, remove the `XL_DEPLOY_SERVER_HOME/ext` and `XL_DEPLOY_SERVER_HOME/plugins` directories and replace them with the backups. If you are restoring customizations after upgrading XL Deploy, first review the [upgrade procedure](/xl-deploy/how-to/upgrade-xl-deploy.html#upgrade-the-server). Some customizations must be redone manually because some files may change between versions of XL Deploy.
