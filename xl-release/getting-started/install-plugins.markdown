---
title: Install XL Release plugins
---

An XL Release plugin has a `.jar` extension. To install or remove a plugin, you must stop the XL Release server; plugins that are installed or removed while the server is running will not take effect until it is restarted.

## Install a plugin

To install a plugin:

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.
2. Copy the plugin JAR file to the `XL_RELEASE_SERVER_HOME/plugins` directory.
3. [Start](/xl-release/how-to/start-xl-release.html) the XL Release server.
4. Refresh the XL Release GUI in your browser.

## Remove a plugin

To remove a plugin:

1. [Shut down](/xl-release/how-to/shut-down-xl-release.html) the XL Release server.
2. Delete the plugin JAR file from the `XL_RELEASE_SERVER_HOME/plugins` directory.
3. [Start](/xl-release/how-to/start-xl-release.html) the XL Release server.
4. Refresh the XL Release GUI in your browser.
