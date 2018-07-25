---
title: Upgrade XL Release
categories:
xl-release
subject:
Installation
tags:
upgrade
system administration
installation
migration
weight: 407
---

<div class="alert alert-warning" style="width: 60%">
If you are upgrading to XL Release 7.5.x or later, you need to execute a different migration procedure. Please refer to <a href="/xl-release/how-to/upgrade-to-7.5.0.html">these instructions</a>.
</div>

Briefly, the process of upgrading XL Release is:

1. Obtain a new version of the XL Release software and, if necessary, a new license from [XebiaLabs](https://dist.xebialabs.com/).
1. Read the [release manual](/xl-release/latest/releasemanual.html) so you are aware of the new functionality and possible upgrade considerations.
1. Stop the current version of XL Release if it is running and ensure that there are no active tasks. In a hot-standby configuration, all nodes must be stopped.
1. Make a backup of your current repository and archive database.
1. Create a new installation directory for the new version of XL Release (so the existing version is still available in case of problems).
1. Extract the new XL Release software release into the new installation directory.
1. Copy the data from the previous XL Release installation directory into the new installation directory.
1. Start the new version of XL Release interactively so that automatic upgraders can run. In a hot-standby configuration, you must start a single node first until all upgraders are executed, and only then proceed with starting other nodes.

## About upgrading

### Skipping versions

It is possible to skip XL Release versions when upgrading. XL Release will sequentially apply any upgrades for the intermediate (skipped) versions. However, you may be required to take manual actions for the intermediate versions; you can find these in the [version-specific upgrade notes](/xl-release/latest/upgrademanual.html).

### Upgrading and downgrading

After you upgrade to a new version of XL Release, you cannot downgrade to an older version. If you upgrade to a release candidate (RC), alpha, or beta version, you cannot upgrade to a newer version or downgrade to an older version. Ensure that you always [create a backup of your repository](/xl-release/how-to/back-up-xl-release.html) before you upgrade to a new version of XL Release.

### Upgrading the repository

If a repository upgrade is required, XL Release will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. Save this log for future reference.

### Upgrading an existing hot-standby configuration to a new version

When upgrading a [hot-standby configuration](/xl-release/how-to/configure-active-hot-standby.html) or an [active/active configuration](/xl-release/how-to/configure-active-active.html), all nodes must be stopped. You can copy the configuration data from the existing nodes. The node ID in the configuration must be unique for each node. You do not have to copy the data that is shared among all the nodes (this is normally stored on NFS), but ensure that you back up the data.

You can then proceed by starting a single node in the cluster. After starting the node, wait until all upgraders are executed and the node is fully started before starting the other nodes, one by one.

## Before you upgrade

Before you upgrade:

* Carefully read the [release manual](/xl-release/latest/releasemanual.html) and note any changes that may apply to your situation.
* Check if there are any hotfixes installed in the `hotfix` directory. If hotfixes are installed, [contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) before upgrading.

## Upgrade the server

<div class="alert alert-warning" style="width: 60%">
When upgrading to XL Release 7.5, a database migration must be performed using a separate Migrator tool. Please follow the instructions in <a href="/xl-release/how-to/upgrade-to-7.5.0.html">Upgrade to XL Release 7.5</a>.
</div>

To upgrade an XL Release server installation:

1. Extract the server ZIP file. It creates an installation directory called, for example, `xl-release-7.0.0-server`.

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.

1. If you are using an embedded repository, copy the `repository` directory and the entire `archive` directory from the old installation directory to the new installation directory.

1. If you have implemented any custom plugins, copy them from the `plugins` directory from the previous installation directory to the new installation directory. If you are upgrading from a previous version to XL Release version 8.0.0 or later, use the  `plugins/__local__/` folder for your custom plugins.

1. Copy the contents of the `ext` directory from the old installation directory to the new installation directory.

1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.

    **Important:**
    1. As of version 8.0.0, the plugins are placed in a different location. When upgrading from version 7.5.3 to version 8.0.0, do not copy the `xlr-wrapper-linux.conf` or `xlr-wrapper-win.conf` files. You can compare the older version against the new version of the files and modify only the necessary lines.

    1. Some versions of XL Release require a new version of the license file. Refer to [XL Release licensing](/xl-release/concept/xl-release-licensing.html#license-types) to see if you need a new license. You can download all of your licenses from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/).

    1. As of version 7.5.0, the content of the `script.policy` file has been modified. When upgrading to XL Release version 7.5.x or later, do not overwrite the `script.policy` file, you must use the new version of the file.

1. If you have changed the XL Release server startup script(s) in the `bin` directory, do not copy the changed script(s) to the new installation directory. Instead, manually reapply the changes to the files that were provided in the new version of XL Release.

    **Note:** In XL Release 4.8.x and earlier, the startup scripts are called `server.sh` and `server.cmd`. In XL Release 5.0.0 and later, they are called `run.sh` and `run.cmd`; there are also `install-service.sh` and `install-service.cmd` scripts for running XL Release [as a service](/xl-release/how-to/install-xl-release-as-a-service.html). If you customized `server.sh` or `server.cmd`, you must redo these changes in `install-service.sh` or `install-service.cmd`.

1. [Start the XL Release server interactively](/xl-release/how-to/start-xl-release.html) to allow automatic repository upgraders to run.

	**Note:** If you are running XL Release in cluster mode, you must start a single XL Release server instance and run the upgraders only on that instance. After the upgraders have sucessfully finished you can boot up the rest of the cluster.

1. If you normally run the XL Release server [as a service](/xl-release/how-to/install-xl-release-as-a-service.html), shut it down and restart it as you normally do.
