---
title: Upgrade XL Deploy
categories:
- xl-deploy
subject:
- Upgrade
tags:
- system administration
- upgrade
- installation
weight: 115
---

<div class="alert alert-warning" style="width: 60%">
If you are upgrading to XL Deploy 8.0.x or later, you need to execute a different migration procedure. Please refer to <a href="/xl-deploy/how-to/migrate-xl-deploy-data-storage-to-an-sql-database.html">these instructions</a>.
</div>

Briefly, the process of upgrading XL Deploy is:

1. Obtain a new version of the XL Deploy software and, if necessary, a new license from [XebiaLabs](https://dist.xebialabs.com/).
1. Read the [release manual](/xl-deploy/latest/releasemanual.html) so you are aware of the new functionality and possible upgrade considerations.
1. Stop the current version of XL Deploy if it is running and ensure that there are no active tasks.
1. Extract the new XL Deploy release into a directory for the new version of XL Deploy (so the old version will still be available in case of problems).
1. Copy data from the old installation directory to the new installation directory.
1. Redo custom changes that you made to configuration files and startup scripts.
1. Start the new version of XL Deploy so that automatic upgraders can run.

You can find release notes and version-specific upgrade notes on the page for each version; for example, [XL Deploy 6.0.x](/xl-deploy/6.0.x/).

## About upgrading

### Upgrading and downgrading

After you upgrade to a new version of XL Deploy, you cannot downgrade to an older version. If you upgrade to a release candidate (RC), alpha, or beta version, you cannot upgrade to a newer version or downgrade to an older version. Ensure that you always [create a backup of your repository](/xl-deploy/how-to/back-up-xl-deploy.html) before you upgrade to a new version of XL Deploy.

### Skipping versions

When upgrading, you can skip XL Deploy versions. XL Deploy will sequentially apply upgrades for the intermediate versions. However, you may be required to take manual actions for the intermediate versions; you can find these in the [release manual](/xl-deploy/latest/releasemanual.html).

### Upgrading the repository

If a repository upgrade is required, XL Deploy will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. Save this log for future reference.

**Important:** If you are upgrading to version 7.5.0: during the upgrading process, the application requires additional privileges to perform the required changes to the schema. These privileges include:
* ALTER, CREATE, and DROP privileges for tables, views, indexes, and triggers
* REFERENCES privilege (if applicable)
* INSERT, SELECT, UPDATE, and DELETE privileges (like in a normal operation)

### Upgrading plugins

Plugin versions are related to the version of XL Deploy (or Deployit) that they are compatible with. For example, the WebSphere Application Server plugin version 4.0.0 requires XL Deploy 4.0.0 or later, unless otherwise specified in the [release manual](/xl-deploy/latest/releasemanual.html).

The new version of XL Deploy may not be compatible with the current version of your plugins. If this is the case, you must download and install updated versions of the plugins. Upgrading to a new plugin version may require you to take manual actions; you can find these in the [release manual](/xl-deploy/latest/releasemanual.html).

XL Deploy will not prevent you from downgrading a plugin to an older version, but doing so is not recommended.

### Deprecations

Each new version may deprecate some functionality or features in favor of newer ways of working. If functionality is marked as deprecated for a specific version, the old functionality is still available (so you can still upgrade hassle-free), but it will be removed in the next version.

In the [release manual](/xl-deploy/latest/releasemanual.html), there is information about how to migrate to the new way of working. This gives you the time and opportunity to migrate to the new situation before upgrading to a still newer version that will no longer have the old functionality. Be sure to read the deprecation information for each release you're upgrading to, so you know what will change in upcoming versions.

## Before you upgrade

Before you upgrade:

* Carefully read the [release manual](/xl-deploy/latest/releasemanual.html) and note any changes that may apply to your situation.
* Review any files that you have customized, such as `conf/deployit-security.xml` or `conf/logback.xml`. You will need to redo these changes in the new files.
* Check whether there are any hotfixes installed in the `hotfix` directory. If hotfixes are installed, [contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) before upgrading.

## Upgrade the server

To upgrade an XL Deploy server installation:

1. Extract the server archive. It creates an installation directory called, for example, `xl-deploy-6.0.0-server`.

1. Log in to XL Deploy as an administrator, click the gear icon and select **Task Monitor**, then select **All Tasks**. If there are any failing or failed tasks, cancel them. If there are any executing tasks, wait for them to complete or cancel them. To open a task from the Task Monitor, double-click it.

1. [Shut down](/xl-deploy/how-to/shut-down-xl-deploy.html) the XL Deploy server.

1. Copy the `repository` directory from the old installation directory to the new installation directory.

1. Copy the contents of the `importablePackages` directory from the old installation directory to the new installation directory.

1. Copy the contents of the `plugins` directory from the old installation directory to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version).

    **Tip:** Check the [release manual](/xl-deploy/latest/releasemanual.html) for information about plugin incompatibility.

1. Copy the contents of the `ext` directory from the old installation directory to the new installation directory.

1. If you added libraries to XL Deploy's `lib` directory (such as database drivers), copy the additional libraries from the old installation directory to the new installation directory.

1. Verify that libraries in the `lib` directory do not *also* appear in the `plugins` directory, even if their versions are different.

    For example, if `lib` contains `guava-16.0.1.jar`, then the `plugins` directory should not contain any `guava-x.x.x.jar` file (such as `guava-13.0.jar`). In this case, you must remove the library from the `plugins` directory.

1. Copy the `conf/deployit-license.lic` file from the old installation directory to the new installation directory.

    Some versions of XL Deploy require a new version of the license file. Refer to [XL Deploy licensing](/xl-deploy/concept/xl-deploy-licensing.html#license-types) to see if you need a new license. You can download all of your licenses from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/).

1. Copy the `conf/deployit.conf` file, the `conf/deployit-defaults.properties` file, and the `conf/repository-keystore.jceks` file (if it exists) from the old installation directory to the new installation directory.

1. If you have changed other files in the `conf` directory, such as [`deployit-security.xml`](/xl-deploy/how-to/connect-xl-deploy-to-ldap-or-active-directory.html) or [`logback.xml`](/xl-deploy/how-to/using-xl-deploy-logging.html#configure-logging), do not copy the changed files to the new installation directory. Instead, manually reapply the changes to the files that were provided in the new version of XL Deploy.

1. If you have changed the XL Deploy server startup script(s) in the `bin` directory, do not copy the changed script(s) to the new installation directory. Instead, manually reapply the changes to the files that were provided in the new version of XL Deploy.

    **Note:** In XL Deploy 4.5.x and earlier, the startup scripts are called `server.sh` and `server.cmd`. In XL Deploy 5.0.0 and later, they are called `run.sh` and `run.cmd`; there are also `install-service.sh` and `install-service.cmd` scripts for running XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html). If you customized `server.sh` or `server.cmd`, you must redo these changes in `install-service.sh` or `install-service.cmd`.

1. [Start the XL Deploy server interactively](/xl-deploy/how-to/start-xl-deploy.html) to allow automatic repository upgraders to run.

1. If you normally run the XL Deploy server [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html), shut it down and restart it as you normally do.

## Upgrade the CLI

To upgrade an XL Deploy command-line interface (CLI) installation:

1. Create a directory for the new XL Deploy CLI installation, including the new XL Deploy CLI version number in the directory name.
1. Extract the CLI archive in this directory.
1. Copy the contents of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version).
1. Copy the contents of the `ext` directory from the previous installation to the new installation directory.

    **Note:** *Do not* copy the content of the `hotfix` directory unless instructed to do so (because hotfixes are version-specific).

1. If you have made any changes to the XL Deploy CLI startup scripts (`cli.sh` or `cli.cmd`), copy these from the `bin` directory in the previous installation to the new installation directory.
