---
title: Using XL Deploy plugin for XL Release
categories:
- xl-release
subject:
- Bundled plugins
tags:
- plugin
- xl deploy
- task
- xld
---

The XL Deploy plugin for XL Release allows you to start a control task or to start a deployment of an application on XL Deploy. It also provides the functionality to trigger undeployment of an application already deployed on XL Deploy.

If you are using the community supported `xlr-xldeploy-plugin` and want to migrate to the offically supported `xlr-xld-plugin`, refer to [Migrating from the community XL Deploy plugin for XL Release to the officially supported plugin](/xl-release/how-to/xld-plugin-community-to-official.html).

## Requirements

The XL Deploy plugin for XL Release requires XL Release version 6.2.x or later to be installed and is compatible with XL Deploy version 5.5.x or later.

## Installation

1. Download the XL Release XL Deploy plugin from https://xebialabs.com/products/xl-release/plugins/.
1. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.

## Configure XL Deploy Server (Shared configuration)

1. In XL Release menu bar, go to **Settings** > **Shared Configuration** > **XL Deploy Server** > **Add XL Deploy Server**.
1. Specify the required information:
  * `Title` - [Required] Select a suitable title for the server configuration.
  * `Url` - [Required] Url to connect to XL Deploy Server (Example: http://localhost:4516).
  * `Username` - Username for XL Deploy server. Can be provided/overridden at task level.
  * `Password` - Password for XL Deploy server. Can be provided/overridden at task level.
  * `Proxy Host` - HTTP proxy host if needed.
  * `Proxy Port` - HTTP proxy Port if needed.
  * `Proxy User Name` - HTTP proxy User name to connect to XL Deploy.
  * `Proxy Password` - HTTP proxy Password to connect to XL Deploy.

## Add a control task

Control tasks are actions that you can perform on middleware or middleware resources. For example: checking the connection to a host is a control task. When you trigger a control task, XL Deploy starts a task that executes the steps associated with the control task.

You can create a control task in XL Release by adding a task of type XL Deploy -> Control Task with following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Configuration Item Id`: [Required] Full CI name (Example: Infrastructure/ProductionBox). Autocomplete is not supported.
  * `Control task name`: Name of the control task (Example: `checkConnection`). Autocomplete is not supported.
  * `Username`: Required if not provided in Shared Configuration.
  * `Password`: Required if not provided in Shared Configuration.

## Add a deploy task

The deploy task is an automated task that tells XL Deploy to deploy a certain application to an environment. Both the application (Package below) and environment (Environment below) must be configured in XL Deploy. The task gives live updates of the deployment process and completes automatically when the deployment succeeds.

You can create a deploy task in XL Release by adding a task of type XL Deploy -> Deploy Task with following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Package`: [Required] ID of the package to deploy (Example: `PetClinic/1.0`). Autocomplete supported.
  * `Environment`: [Required] ID of the environment to deploy (Example: SITEnv). Autocomplete supported.
  * `Username`: Required if not provided in Shared Configuration.
  * `Password`: Required if not provided in Shared Configuration.
  * `Rollback On Failure`: Whether rollback should be done if the deployment fails.

## Add an undeploy task

The undeploy task is an automated task that tells XL Deploy to undeploy an application from an environment. The name of the deployed application holds the complete information about the environment and the package to be undeployed. For example: Undeploy `Environments/SITEnv/PetClinic` tells that package PetClinic must be undeployed from Environment SITEnv.

You can create an undeploy task in XL Release by adding a task of type XL Deploy -> Undeploy Task with following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Deployed Application`: [Required] Name of the deployed application you want to undeploy (Example: `Environments/SITEnv/PetClinic`). Autocomplete supported.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Rollback On Failure`: Whether rollback should be done if the undeployment fails.
