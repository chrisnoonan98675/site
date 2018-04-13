---
title: Using the plugins manager
categories:
- xl-release
subject:
- Settings
tags:
- plugins
- manager
- system administration
---

As of XL Release version 7.6.0, you can manage your XL Release plugins directly from the XL Release user interface. The plugin manager displays the list of installed plugins and their current version. With the plugin manager you can upload a new plugin or a new version of an installed plugin directly from the user interface.

To use the plugin manager, click **Plugins** in the top menu bar.

You can view a list of all the currently plugins installed in XL Release.

![Plugins](../images/plugins-manager.png)

To upload a new plugin or a new version of an existing plugin, click **Upload new plugin file** and select a file. The plugin files are saved in `XL_RELEASE_SERVER_HOME/plugins`.

**Note:** After you upload a plugin, ensure you restart your XL Release instance.

As of XL Release version 8.0.0, the **Browse** tab in the **Plugins** section from the menu bar shows all the official plugins available for XL Release. You can install any plugin by clicking **Install**.
You must restart XL Release to enable the newly installed plugins.
