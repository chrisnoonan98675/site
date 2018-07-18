---
title: Back up XL Release (XL Release 7.5 and later)
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- backup
- maintenance
- database
since:
 - XL Release 7.5.0
weight: 489 
---

<div class="alert alert-warning" style="width: 60%">
This document describes how to perform a backup for XL Release version 7.5 and later. For XL Release version 7.2 and earlier, refer to <a href="/xl-release/how-to/back-up-xl-release-jcr.html">Back up XL Release (XL Release 7.2 and previous versions)</a>.
</div>

It is recommended that you regularly create a backup of your XL Release server. It is especially important to back up XL Release before [upgrading to a new version](/xl-release/how-to/upgrade-xl-release.html).

## Create a backup

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you are creating a backup.

To back up XL Release, it is recommended that you back up the complete installation folder (referred to here as `XL_RELEASE_SERVER_HOME`); for example, by compressing it in a ZIP file.

If you store the XL Release repository in an external database, back up the database using the tools provided by your database vendor. Make sure you back up both `xlrelease` and `archive` databases.

## Restore a backup

**Important:** XL Release [must not be running](/xl-release/how-to/shut-down-xl-release.html) when you restore a backup.

### Restore the database

If you store the repository in a database, restore the backup of the database using the tools provided by your database vendor.

### Restore XL Release

To restore your XL Release configuration, replace the installation directory with the contents of the backup.
