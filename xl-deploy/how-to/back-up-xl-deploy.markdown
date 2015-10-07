---
title: Back up XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- repository
---

## Create a backup

To create a backup of XL Deploy, several components may need to be backed up depending on your configuration:

* **Repository**.
    * Built-in repository: Create a backup of the built-in JCR repository by backing up the files in the `repository` directory.
    * Database repository: Create a backup of both the files in the `repository` directory, as well as the database (using the tools provided by your database vendor).
* **Configuration**. Create a backup of the XL Deploy configuration by backing up the files in the `conf` directory in the installation directory.
* **Customization**. Create a backup of the XL Deploy customizations by backing up the files in the `ext` and `plugins` directory in the installation directory.

**Note:** XL Deploy **must not** be running when you are making a backup. Schedule backups outside planned deployment hours to ensure the server is not being used.

## Restore a backup

To restore a backup of XL Deploy, restore one of the following components:

* **JCR repository**.
    * Built-in repository: Remove the `repository` directory and replace it with the backup.
    * Database repository: Remove the `repository` directory and replace it with the backup. Next, restore a backup of the database using the tools provided by your vendor.
* **Configuration**. Remove the `conf` directory in the `XLDEPLOY_SERVER_HOME` directory and replace it with the backup.
* **Customization**. Remove the `ext` and `plugins` directories in the `XLDEPLOY_SERVER_HOME` directory and replace them with the backups.

**Note:** XL Deploy **must not** be running when you are restoring a backup.
