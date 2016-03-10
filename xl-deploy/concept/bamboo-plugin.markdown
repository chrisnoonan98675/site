---
title: Introduction to the Bamboo XL Deploy plugin
categories:
- xl-deploy
subject:
- Bamboo
tags:
- bamboo
- plugin
---

The XL Deploy plugin for [Atlassian Bamboo](https://www.atlassian.com/software/bamboo) enables two tasks:

* Publish to XL Deploy
* Deploy with XL Deploy

These tasks can be executed separately or combined sequentially.

For information about Bamboo requirements and the configuration items (CIs) that the plugin supports, refer to the [Bamboo Plugin Reference](/xl-deploy/latest/bamboo-plugin/index.html). 

To download the plugin, visit the [Atlassian Marketplace](https://marketplace.atlassian.com/plugins/com.xebialabs.deployit.plugin.bamboo-deployit-plugin/server/overview).

**Tip:** To ensure that the Bamboo server stays in sync with the XL Deploy server, restart the Bamboo server after each upgrade of the XL Deploy server.

**Note:** The Bamboo XL Deploy plugin cannot set values for hidden CI properties.

## Features

* Publish DAR package to XL Deploy
* Triggering deployment in XL Deploy
    * Updating mappings on upgrade
* Execution on Windows/UNIX Slave nodes

## Publish to XL Deploy

You can use the publish task to publish a deployment package (DAR file) to XL Deploy. The following properties can be configured:

* Server URL (required): Address of the XL Deploy server.
* XL Deploy Username (required): User ID to use when logging in to the XL Deploy server.
* XL Deploy Password (required): Password for the XL Deploy user.
* DAR file pattern (required): File pattern where the DAR file could be found. The result should be exactly one file. Example: `**/*.dar` searches for any file in any subfolder that has the `.dar` extension.
* Work directory (optional): Changes the work directory location (by default, the work directory of the task is used).

## Deploy with XL Deploy

You can use the deploy task to deploy an application with XL Deploy. The application must already be published to XL Deploy (you can do so with the Publish to XL Deploy task).

The following properties can be configured:

* Server URL (required): Address of the XL Deploy server.
* XL Deploy Username (required): User ID to use when logging in to the XL Deploy server.
* XL Deploy Password (required): Password for the XL Deploy user.
* Environment (required): The environment to which the application will be deployed.
* Application (required): The deployment package (DAR file).
* Version (required): The version of the deployment package.
* Orchestrators (optional): [Orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html) to use (by default, XL Deploy's default orchestrator is used). Use a comma (`,`) as a separator when specifying multiple orchestrators.
* Update deployeds (optional): Update the deployeds and mappings on an update. This will keep any previous deployeds present in the deployment object that are already present, unless they cannot be deployed with regards to their tags. It will add all deployeds that are still missing.
* Action on failure (optional): The action to perform on failure. You can choose to cancel the task (this is the default), to rollback the task, or to do nothing. If you do nothing, the task will stay in XL Deploy, and you can manually review, cancel, or roll back the task from XL Deploy.
