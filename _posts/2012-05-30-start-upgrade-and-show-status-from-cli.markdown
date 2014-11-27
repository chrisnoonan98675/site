---
title: Start upgrade and show status from the XL Deploy CLI
categories:
- xl-deploy
tags:
- cli
- deployment
---

[This XL Deploy command-line interface (CLI) script](/sample-scripts/deployments.py) allows you to start an upgrade of an existing deployment via the CLI. It will show the current state of the deployment every 5 seconds and, if the CLI was started with `-expose-proxies`, the log output once the deployment has stopped or completed. This is similar to the action of the XL Deploy Maven plugin's [*deploy*](http://tech.xebialabs.com/deployit-maven-plugin/3.7.1/deploy-mojo.html) goal.

## Installation

Copy [this file](/sample-scripts/start-upgrade-and-show-status-from-cli/deployments.py) to `CLI_HOME/ext`.

## Usage

Call `startUpgrade(appName, newVersion, targetEnvironment)` to trigger an upgrade. This requires an existing deployment of the application (usually a different version) to the target environment. For example:

    startUpgrade("PetClinic", "2.5", "Test")
