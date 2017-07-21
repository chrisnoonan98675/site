---
title: Introduction to the XL Deploy Script plugin
categories:
- xl-deploy
subject:
- Script
tags:
- provisioning
- plugin
since:
- XL Deploy 7.1.0
---

The XL Deploy Script plugin enables XL Deploy to install and provision scripts on hosts.

The plugin introduces a new *provisioner* that can run an arbitrary script file based on any `interpreter` after the environment is set up.

The `interpreter` (examples: shell, perl, awk, python) which will be run by the program loader, must exist on the host before executing it.

For more information about requirements and the configuration items (CIs) that the Script plugin provides, refer to the [Script Plugin Reference](/xl-deploy-xld-script-plugin/latest/scriptPluginManual.html).

## Features ##

* Apply script file for provisioning.
* Allow to define order in which scripts should be executed.
