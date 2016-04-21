---
title: Upgrade XL Release
categories:
- xl-release
subject:
- Upgrade
tags:
- upgrade
- system administration
- installation
- migration
---

Briefly, the process of upgrading XL Release is:

1. Obtain a new version of the XL Release software and, if necessary, a new license from [XebiaLabs](https://dist.xebialabs.com/).
1. Read the [release manual](/xl-release/latest/releasemanual.html) so you are aware of the new functionality and possible upgrade considerations.
1. Stop the current version of XL Release if it is running and ensure that there are no active tasks.
1. Create a new installation directory for the new version of XL Release (so the existing version is still available in case of problems).
1. Extract the new XL Release software release into the new installation directory.
1. Copy the data from the previous XL Release installation directory into the new installation directory.
1. Start the new version of XL Release.

## About upgrading

### Skipping versions

It is possible to skip XL Release versions when upgrading. XL Release will sequentially apply any upgrades for the intermediate (skipped) versions. However, you may be required to take manual actions for the intermediate versions; you can find these in the [version-specific upgrade notes](/xl-release/latest/upgrademanual.html).

### Upgrading and downgrading

After you upgrade to a new version of XL Release, you cannot downgrade to an older version. If you upgrade to a release candidate (RC), alpha, or beta version, you cannot upgrade to a newer version or downgrade to an older version. Ensure that you always create a backup of your repository before you upgrade to a new version of XL Release.

### Upgrading the repository

If a repository upgrade is required, XL Release will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. Save this log for future reference.

## Before you upgrade

Before you upgrade:

* Carefully read the [release manual](/xl-release/latest/releasemanual.html) and note any changes that may apply to your situation.
* Check whether there are any hotfixes installed in the `hotfix` directory. If hotfixes are installed, [contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) before upgrading.

## Upgrade the server

To upgrade an existing XL Release server installation:

1. Create a directory for the new XL Release server installation, including the new XL Release server version number in the directory name.
2. Extract the XL Release software in this directory.
3. Copy the contents of the `conf` directory from the previous installation to the new installation directory.
4. Copy the contents of the `ext` directory from the previous installation to the new installation directory.
4. Copy the entire `repository` directory from the previous installation to the new installation directory.
4. Copy the entire `archive` directory from the previous installation to the new installation directory.
6. Copy the contents of the `plugins` directory from the previous installation to the new installation directory.
7. If you have made any changes to the XL Release server startup scripts, manually re-do these changes in the new installation directory.

This completes the upgrade of the XL Release server.
