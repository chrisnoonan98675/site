---
title: Introduction to the Bamboo XL Deploy plugin
categories:
- xl-deploy
subject:
- Bamboo plugin
tags:
- bamboo
- plugin
---

The XL Deploy plugin for [Atlassian Bamboo](https://www.atlassian.com/software/bamboo) enables two tasks:

* Publish to XL Deploy
* Deploy with XL Deploy

These tasks can be executed separately or combined sequentially.

For information about Bamboo requirements and the configuration items (CIs) that the plugin supports, refer to the [Bamboo Plugin Reference](/xl-deploy/latest/bamboo-plugin/index.html). 

In order for the Bamboo server to be in sync with the XL Deploy server, please don't forget to restart the Bamboo server after each upgrade of the XL Deploy server.

## Features

* Publish DAR package to XL Deploy
* Triggering deployment in XL Deploy
    * Updating mappings on upgrade
* Execution on Windows/UNIX Slave nodes

## Publish to XL Deploy

The publish task can be used to publish a DAR package to XL Deploy.

* **Server URL (required)**
* **XL Deploy Username (required)**
* **XL Deploy Password (required)**
* **DAR file pattern (required)** File pattern where the DAR file could be found. The result should be exactly one file. Example: `**/*.dar` searches for any file in any subfolder that has `.dar` extension.
* **Work directory** This optional parameter changes the work directory location. By default it is the work directory of the task.

## Deploy with XL Deploy

The deploy task can be used to deploy an application with XL Deploy. The application must already be published to XL Deploy. You can use the "Publish to XL Deploy" step for this.

The following properties can be configured:

* **Server URL (required)**
* **XL Deploy Username (required)**
* **XL Deploy Password (required)**
* **Environment (required)** Specify the environment the application will be deployed to.
* **Application (required)** Specify the Deployment Package.
* **Version (required)** Specify the Version of the Deployment Package.
* **Orchestrators** With this optional parameter you can change the orchestrators. Default orchestrator is `default`. Use comma (,) as a separator to specify multiple orchestrators.
* **Update deployeds** Enabling this will update the deployeds and mappings on an update. This will keep any previous deployeds present in the deployment object that are already present, unless they cannot be deployed with regards to their tags. It will add all the deployeds that are still missing.
* **Action on failure** Select the action to perform on failure. You can choose to cancel the task (this is the default), to rollback the task, or to do nothing. If you do nothing, the task will stay in XL Deploy, and you can manually review, cancel or rollback the task from XL Deploy.
