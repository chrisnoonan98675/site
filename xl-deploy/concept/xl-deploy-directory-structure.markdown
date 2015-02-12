---
title: XL Deploy directory structure
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- cli
---

## XL Deploy server directory structure

When you extract the XL Deploy server archive, the following directory structure is created in the installation directory:

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files and the XL Deploy license
* `doc`: Contains the XL Deploy product documentation
* `ext`: Contains server extensions
* `hotfix`: Contains hotfixes that fix issues with the server software
* `hotfix/plugins`: Contains hotfixes that fix issues with the plugin software
* `importablePackages`: Default location for importable deployment packages
* `lib`: Contains libraries that the Server needs
* `log`: Contains Server log files (this directory is only present once you have started XL Deploy Server)
* `plugins`: Contains the XL Deploy middleware plugins
* `recovery.dat`: Stores tasks that are in progress for recovery purposes (this file is only present after you have started XL Deploy server)
* `samples`: Contains sample plugins and configuration snippets

## XL Deploy CLI directory structure

When you extract the XL Deploy command-line interface (CLI) archive, the following directory structure is created in the installation directory:

* `bin`: Contains the CLI binaries
* `ext`: Contains CLI Python extension scripts
* `hotfix`: Contains hotfixes that fix issues with the CLI software
* `lib`: Contains necessary libraries
* `plugins`: Contains the CLI plugins
