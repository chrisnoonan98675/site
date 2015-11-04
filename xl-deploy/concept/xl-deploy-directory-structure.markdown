---
title: XL Deploy directory structure
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- cli
- installation
- hotfix
---

## XL Deploy server directory structure

When you extract the XL Deploy server archive, the following directory structure is created in the installation directory:

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files and the XL Deploy license
* `doc`: Contains the XL Deploy product documentation
* `ext`: Contains server extensions
* `hotfix`: Contains hotfixes (XL Deploy 4.5.x and earlier only)
* `hotfix/lib`: Contains hotfixes that fix issues with the server software (XL Deploy 5.0.0 and later)
* `hotfix/plugins`: Contains hotfixes that fix issues with the plugin software (XL Deploy 5.0.0 and later)
* `importablePackages`: Default location for importable deployment packages
* `lib`: Contains libraries that the server needs
* `log`: Contains server log files (this directory is only present once you have started XL Deploy server)
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
