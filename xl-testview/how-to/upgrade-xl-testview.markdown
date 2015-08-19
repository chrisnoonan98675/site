---
title: Upgrade XL TestView
categories:
- xl-testview
subject:
- Upgrade
tags:
- system administration
- upgrade
- installation
- migration
---

Briefly, the process of upgrading XL TestView is:

1. Obtain a new version of the XL TestView software and, if necessary, a new license from [XebiaLabs](https://dist.xebialabs.com/).
1. Read the [**release manual**](/xl-testview/latest/releasemanual.html) so you are aware of the new functionality and possible upgrade considerations.
1. Stop the current version of XL TestView if it is running and ensure that there are no running tasks active.
1. Create a new installation directory for the new version of XL TestView (so the previous version will still be available in case of problems).
1. Extract the new XL TestView release into the new installation directory.
1. Copy the data from the previous XL TestView installation directory to the new installation directory. This ensures that you have a backup of your repository.
1. Start the new version of XL TestView.

You can find release notes and version-specific upgrade notes on the page for each version; for example, [XL TestView 1.2.x](/xl-testview/1.2.x/).

## Skipping versions

When upgrading, you can skip XL TestView versions. XL TestView will sequentially apply upgrades for the intermediate versions. However, you may be required to take manual actions for the intermediate versions; you can find these in the [release manual](/xl-testview/latest/releasemanual.html).

## Upgrading and downgrading

After you upgrade to a new version of XL TestView, you cannot downgrade to an older version.

If you upgrade to a release candidate (RC), alpha, or beta version, you cannot upgrade to a newer version or downgrade to an older version.

Ensure that you always create a backup of your repository before you upgrade to a new version of XL TestView.

## Upgrading the repository

If a repository upgrade is required, XL TestView will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. Save this log for future reference.

## Deprecations

Each new version may deprecate some functionality or features in favor of newer ways of working. If functionality is marked as deprecated for a specific version, the old functionality is still available (so you can still upgrade hassle-free), but it will be removed in the next version.

In the [release manual](/xl-testview/latest/releasemanual.html), there is information about how to migrate to the new way of working. This gives you the time and opportunity to migrate to the new situation before upgrading to a still newer version that will no longer have the old functionality. Be sure to read the deprecation information for each release you're upgrading to, so you know what will change in upcoming versions.

## Performing the upgrade

Before upgrading, carefully read the [**release manual**](/xl-testview/latest/releasemanual.html). Note anything that may apply to your situation.

### Upgrading the server

To upgrade an XL TestView server installation:

1. Extract the server archive. It creates an installation directory called, for example, `xl-testview-1.2.0-server`.

1. Stop the XL TestView server.

1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.

1. If necessary, update the license (`conf/xl-testview-license.lic`). You can download your current license from [https://dist.xebialabs.com/](https://dist.xebialabs.com/).

1. Copy the `data` directory from the previous installation to the new installation directory.

1. Copy the contents of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL TestView version).

    **Tip:** Check the [upgrade manual](/xl-testview/latest/releasemanual.html) for information about plugin incompatibility.

1. Copy the contents of the `ext` directory from the previous installation to the new installation directory.

    **Note:** *Do not* copy the contents of the `hotfix` directory unless instructed to do so (because hotfixes are version-specific).

1. If you added libraries to XL TestView's `lib` directory (such as database drivers), copy the additional libraries from the previous installation to the new installation directory.

1. On unix based operating systems review `/etc/sysconfig/xl-testview` and/or `/etc/default/xl-testview` for changes in configuration settings. See also the [upgrade manual](/xl-testview/latest/releasemanual.html).

1. If you have made any changes to the XL TestView server startup scripts in the `bin` directory, manually re-do these changes in the new installation directory.

1. Verify that libraries in the `lib` directory do not *also* appear in the `plugins` directory, even if their versions are different.

    For example, if `lib` contains `guava-16.0.1.jar`, then the `plugins` directory should not contain any `guava-x.x.x.jar` file (such as `guava-13.0.jar`). In this case, you must remove the library from the plugins directory.

1. [Start the XL TestView server interactively](/xl-testview/how-to/start.html) to allow automatic repository upgraders to run.

1. If you normally run the XL TestView server [as a service](/xl-testview/how-to/install-xl-testview-as-a-service.html), shut it down and restart it as you normally do.
