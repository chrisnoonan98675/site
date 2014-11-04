---
title: Import and deploy deployment packages from the XL Deploy CLI
categories:
- xl-deploy
tags:
- package
- application
- cli
---

[This XL Deploy command-line interface (CLI) script](/sample-scripts/import-and-deploy.py) imports a given deployment package and starts an upgrade of the corresponding application to a target environment. This is similar to an invocation of the XL Deploy Maven plugin's [*import*](http://tech.xebialabs.com/deployit-maven-plugin/3.7.1/import-mojo.html) goal followed by an invocation of [*deploy*](http://tech.xebialabs.com/deployit-maven-plugin/3.7.1/deploy-mojo.html).

## Requirements

Requires `deployments.py` in `CLI_HOME/ext`.

## Installation

Copy [this file](/sample-scripts/import-and-deploy.py) to `CLI_HOME/ext`.

## Usage

Call `deployPackage(packagePath, targetEnvironment)` to import the package and trigger an upgrade to the given environment. This requires an existing deployment of the application (usually a different version) to the target environment. For example:

    deployPackage("path/to/my/package", "Dev")

You can combine this script with the [zip-importer](https://github.com/xebialabs/community-plugins/tree/master/deployit-server-plugins/importers/zip-importer) or [jee-archive-importer](https://github.com/xebialabs/community-plugins/tree/master/deployit-server-plugins/importers/jee-archive-importer) community plugin (on the server side) to support deployments of plain ZIP files, or even just EARs or WARs.
