---
title: Installing and removing XL Deploy plugins
categories:
- xl-deploy
subject:
- System administration
tags:
- system administation
- plugin
---

XL Deploy Server supports various plugins that add functionality to the system. When it starts, the XL Deploy server scans the plugins directory and loads all plugins it finds. The additional functionality they provide is immediately available. Any plugins added or removed when XL Deploy server is running will not take effect until the server is restarted.

To install a new plugin, stop the XL Deploy server and copy the plugin JAR archive into the plugins directory, then restart the XL Deploy server.

To uninstall a plugin, stop the XL Deploy server and remove the plugin JAR archive from the plugins directory, then restart the XL Deploy server.
