---
title: Upgrade XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- upgrade
---

## Upgrade process overview

The process of upgrading XL Deploy is:

1. Obtain a new version of the XL Deploy software (the main product and/or plugins) from XebiaLabs.
1. Read the new version's release notes so you are aware of the new functionality and possible upgrade considerations.
1. Read the new version's upgrade manual so you are aware of possible upgrade considerations.
1. Stop the current version of XL Deploy if it is running and ensure that there are no running tasks active.
1. Create a new installation directory for the new version of XL Deploy, so the current version is still available in case of problems.
1. Extract the new XL Deploy release into the new installation directory.
1. Copy the data from the previous XL Deploy installation directory to the new installation directory.
1. Start the new version of XL Deploy.

## Upgrade notes

* You can skip XL Deploy versions when upgrading. XL Deploy will sequentially apply any upgrades for the intermediate (skipped) versions. Please read the specific upgrade notes for each version carefully.

* If a repository upgrade is required, XL Deploy will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. You may want to save this log for future reference.

* The new version of XL Deploy may not be compatible with the current version of your plugins. If this is the case, you must download an updated version of your plugins as well. Please read the specific upgrade notes for each version carefully.

* Plugin versions are related to the version of XL Deploy (or Deployit) that they are compatible with. For example, WAS plugin version 3.6.0 requires at least Deployit server version 3.6.0. This version of the WAS plugin should also work in Deployit 3.7.0 unless stated otherwise in the specific upgrade notes or release notes.

## Deprecations

Each new version may deprecate some old functionality or features in favor of newer ways of working. If functionality is marked as deprecated for a specific version, the old functionality is still available (so you can still upgrade hassle-free), but it will be removed in the next version.

Every deprecation is accompanied by a description of how to migrate to the new way of working. This gives you the time and opportunity to migrate to the new situation before upgrading to a still newer version that will no longer have the old functionality. Be sure to read the deprecation information for each release you're upgrading to, so you know what will change in upcoming versions.

## Performing the upgrade

Before upgrading, carefully read the release notes and note anything that may apply to your situation.

To begin upgrading XL Deploy, first unpack the distribution archive, which contains:

* A server archive
* A CLI archive

### Upgrading the server

To upgrade an XL Deploy server installation:

1. Extract the server archive. It creates an installation directory called, for example, `xl-deploy-4.0.0-server`.
1. Stop the Deployit/XL Deploy server.
1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.
1. If necessary, update the product license (`conf/deployit-license.lic`). You can download your current licenses from [https://dist.xebialabs.com/](https://dist.xebialabs.com/).
1. Copy the `repository` directory from the previous installation to the new installation directory.
1. Copy the content of the `importablePackages` directory from the previous installation to the new installation directory.
1. Copy the content of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version). Also refer to the version-specific upgrade notes for plugin incompatibilities.
1. Copy the content of the `ext` directory from the previous installation to the new installation directory.
1. *Do not* copy the content of the hotfix directory (unless instructed) because hotfixes are version-specific.
1. If you added libraries to XL Deploy's `lib` directory (such as database drivers), copy the additional libraries from the previous installation to the new installation directory.
1. If you have made any changes to the XL Deploy server startup scripts (`server.sh` or `server.cmd`), manually re-do these changes in the new installation directory.
1. Verify that libraries in the `lib` directory do not also appear in the `plugins` directory, even if their versions are different.

    For example, if `lib` contains a `guava-16.0.1.jar`, then the `plugins` directory should not contain any `guava-x.x.x.jar` file (such as `guava-13.0.jar`). In this case, you must remove the library from the plugins directory. For example: 

    ![Sample duplicate libraries](images/duplicate-libs-example.png)

1. Start the XL Deploy server interactively to allow automatic repository upgraders to run.
1. If you normally run the XL Deploy server as a service, stop it and start it as you normally do.

**Note:** Ensure that the plugins and extensions in the previous XL Deploy installation are compatible with the new XL Deploy server version.

### Upgrading the CLI

To upgrade an existing XL Deploy CLI installation:

1. Create a directory for the new XL Deploy CLI installation, including the new XL Deploy CLI version number in the directory name.
1. Extract the CLI archive in this directory.
1. Copy the content of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version).
1. Copy the content of the `ext` directory from the previous installation to the new installation directory.
1. *Do not* copy the content of the hotfix directory (unless instructed) because hotfixes are version-specific.
1. If you have made any changes to the XL Deploy CLI startup scripts (`cli.sh` or `cli.cmd`), copy these from the `bin` directory in the previous installation to the new installation directory.

## Specific upgrade notes

For information that is specific to each version of XL Deploy, refer to the upgrade notes in that version.
