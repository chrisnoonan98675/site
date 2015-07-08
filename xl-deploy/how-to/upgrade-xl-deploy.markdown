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
- migration
---

Briefly, the process of upgrading XL Deploy is:

1. Obtain a new version of the XL Deploy software and, if necessary, a new license from [XebiaLabs](https://dist.xebialabs.com/).
1. Read the [**release notes**](/xl-deploy/latest/releasenotes.html) so you are aware of the new functionality and possible upgrade considerations.
1. Read the [**version-specific upgrade notes**](/xl-deploy/latest/upgrademanual.html) so you are aware of all special upgrade considerations.
1. Stop the current version of XL Deploy if it is running and ensure that there are no running tasks active.
1. Create a new installation directory for the new version of XL Deploy (so the previous version will still be available in case of problems).
1. Extract the new XL Deploy release into the new installation directory.
1. Copy the data from the previous XL Deploy installation directory to the new installation directory.
1. Start the new version of XL Deploy.

You can find release notes and version-specific upgrade notes on the page for each version; for example, [XL Deploy 4.5.x](/xl-deploy/4.5.x/).

## Skipping versions

When upgrading, you can skip XL Deploy versions when upgrading. XL Deploy will sequentially apply upgrades for the intermediate versions.

However, you may be required to take manual actions for the intermediate versions; you can find these in the [version-specific upgrade notes](/xl-deploy/latest/upgrademanual.html).

## Upgrading the repository

If a repository upgrade is required, XL Deploy will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. Save this log for future reference.

<div class="alert alert-warning" role="alert">
    <p><strong>Note:</strong> After a component is upgraded, it cannot be downgraded to an older version.</p>
</div>

## Upgrading plugins

Plugin versions are related to the version of XL Deploy (or Deployit) that they are compatible with. For example, the WebSphere Application Server plugin version 4.0.0 requires XL Deploy 4.0.0 or later, unless otherwise specified in the [version-specific upgrade notes](/xl-deploy/latest/upgrademanual.html).

The new version of XL Deploy may not be compatible with the current version of your plugins. If this is the case, you must download and install updated versions of the plugins.

Upgrading to a new plugin version may require you to take manual actions; you can find these in the [version-specific upgrade notes](/xl-deploy/latest/upgrademanual.html).

## Deprecations

Each new version may deprecate some functionality or features in favor of newer ways of working. If functionality is marked as deprecated for a specific version, the old functionality is still available (so you can still upgrade hassle-free), but it will be removed in the next version.

In the [version-specific upgrade notes](/xl-deploy/latest/upgrademanual.html), there is information about how to migrate to the new way of working. This gives you the time and opportunity to migrate to the new situation before upgrading to a still newer version that will no longer have the old functionality. Be sure to read the deprecation information for each release you're upgrading to, so you know what will change in upcoming versions.

## Performing the upgrade

Before upgrading, carefully read the [**release notes**](/xl-deploy/latest/releasenotes.html) and the [**version-specific upgrade notes**](/xl-deploy/latest/upgrademanual.html). Note anything that may apply to your situation.

### Upgrading the server

To upgrade an XL Deploy server installation:

1. Extract the server archive. It creates an installation directory called, for example, `xl-deploy-4.0.0-server`.

1. Stop the Deployit/XL Deploy server.

1. When upgrading to XL Deploy 4.5.x or earlier, copy the contents of the `conf` directory from the previous installation to the new installation directory.

    <div class="alert alert-warning" role="alert">
    <p>When upgrading to XL Deploy 5.0.0 or later, do not copy the full contents of the <code>conf</code> directory. Only copy the following files from <code>conf</code>:</p>
    <ul>
    <li><code>deployit.conf</code></li>
    <li><code>jackrabbit-repository.xml</code></li>
    <li><code>repository-keystore.jceks</code></li>
    <li><code>deployit-defaults.properties</code></li>
    </ul>
    <p><strong>Note:</strong> If you have customized the <code>conf/tasker.conf</code> file and you are upgrading to XL Deploy 5.0.0 or later, you must reapply your customizations in <code>conf/system.conf</code>.</p>
    </div>

1. If necessary, update the license (`conf/deployit-license.lic`). **This is required when upgrading to XL Deploy 5.0.0 or later.** You can download your current license from [https://dist.xebialabs.com/](https://dist.xebialabs.com/).

1. Copy the `repository` directory from the previous installation to the new installation directory.

1. Copy the contents of the `importablePackages` directory from the previous installation to the new installation directory.

1. Copy the contents of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version). 

    **Tip:** Check the [version-specific upgrade notes](/xl-deploy/latest/upgrademanual.html) for information about plugin incompatibility.

1. Copy the contents of the `ext` directory from the previous installation to the new installation directory.

    **Note:** *Do not* copy the contents of the `hotfix` directory unless instructed to do so (because hotfixes are version-specific).

1. If you added libraries to XL Deploy's `lib` directory (such as database drivers), copy the additional libraries from the previous installation to the new installation directory.
 
1. If you have made any changes to the XL Deploy server startup scripts in the `bin` directory, manually re-do these changes in the new installation directory.

    In XL Deploy 4.5.x and earlier, the startup scripts are `server.sh` and `server.cmd`. In XL Deploy 5.0.0 and later, they are `run.sh` and `run.cmd`.

1. Verify that libraries in the `lib` directory do not *also* appear in the `plugins` directory, even if their versions are different.

    For example, if `lib` contains `guava-16.0.1.jar`, then the `plugins` directory should not contain any `guava-x.x.x.jar` file (such as `guava-13.0.jar`). In this case, you must remove the library from the plugins directory. 

1. [Start the XL Deploy server interactively](/xl-deploy/how-to/start-xl-deploy.html) to allow automatic repository upgraders to run.

1. If you normally run the XL Deploy server [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html), shut it down and restart it as you normally do.

### Upgrading the CLI

To upgrade an XL Deploy command-line interface (CLI) installation:

1. Create a directory for the new XL Deploy CLI installation, including the new XL Deploy CLI version number in the directory name.
1. Extract the CLI archive in this directory.
1. Copy the contents of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version).
1. Copy the contents of the `ext` directory from the previous installation to the new installation directory.

    **Note:** *Do not* copy the content of the `hotfix` directory unless instructed to do so (because hotfixes are version-specific).

1. If you have made any changes to the XL Deploy CLI startup scripts (`cli.sh` or `cli.cmd`), copy these from the `bin` directory in the previous installation to the new installation directory.
