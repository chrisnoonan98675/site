---
title: Install or remove XL Release plugins
categories:
- xl-release
subject:
- Installation
tags:
- system administration
- plugin
- installation
- hotfix
---

An XL Release plugin has a `.jar` extension. To install or remove a plugin, you must stop the XL Release server; plugins that are installed or removed while the server is running will not take effect until it is restarted.

## Install a plugin

### XL Release version 7.6.x and earlier:

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.
2. Copy the plugin JAR file to the `XL_RELEASE_SERVER_HOME/plugins` directory.
3. [Start](/xl-release/how-to/start-xl-release.html) the XL Release server.
4. Refresh the XL Release GUI in your browser.

### XL Release version 8.0 and later

1. Use the [Plugin Manager](/xl-release/how-to/using-the-plugins-manager.html)
2. Restart XL Release

## Remove a plugin

### XL Release version 7.6.x and earlier

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.
2. Delete the plugin JAR file from the `XL_RELEASE_SERVER_HOME/plugins` directory.
3. [Start](/xl-release/how-to/start-xl-release.html) the XL Release server.
4. Refresh the XL Release GUI in your browser.


### XL Release version 8.0 or later

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.
2. If the plugin was installed manually, delete the plugin JAR file from the `XL_RELEASE_SERVER_HOME/plugins/__local__/` directory.
3. If it is an official plugin, delete the plugin JAR file from the `XL_RELEASE_SERVER_HOME/plugins/xlr-official/` directory.
**Note:** Do not delete the `xlr-official` folder. The XebiaLabs supported plugins are downloaded to this folder from the plugin manager.
4. [Start](/xl-release/how-to/start-xl-release.html) the XL Release server.
5. Refresh the XL Release GUI in your browser.

**Note:** Various plugins add new types to XL Release. Removing a plugin can potentially prevent XL Release to read your existing data correctly because of the missing types.
