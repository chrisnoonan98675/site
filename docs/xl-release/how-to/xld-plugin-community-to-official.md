---
title: Migrating from the community XL Deploy plugin for XL Release to the officially supported plugin
categories:
xl-release
subject:
Bundled plugins
tags:
plugin
xl deploy
xld
xlr-xldeploy-plugin
xlr-xld-plugin
---

The community supported `xlr-xldeploy-plugin` and the officially supported `xlr-xld-plugin` can both exist within the same XL Release instance without any conflicts. For more information about the officially supported `xlr-xld-plugin`, refer to [Using XL Deploy plugin for XL Release](/xl-release/how-to/xld-plugin.html).

When migrating from the community plugin to the officially supported plugin:

* You must define a new shared configuration in **Shared Configuration** > **XL Deploy Server** for the standard plugin. The XL Deploy server configuration defined for the community supported plugin does not apply for tasks created with the officially supported plugin.

* To migrate a task from the community plugin to the official plugin, you must use change task type option **Change Task Type** > **XL Deploy** and then choose the appropriate task type using the mapping table below. For example: To migrate the deploy task defined from community plugin to the official plugin, you must use change task type option **Change Task Type** > **XL Deploy** > **Deploy** and then enter the missing information in the fields defined for the official plugin.

**Note:** The XL Deploy: Deploy task accepts the package name as *Application_name/package_name* (example: `PetClinic/1.0`) or *Applications/Application_name/package_name* (example: `Applications/PetClinic/1.0`) and the environment as *env_name* (example: `MyTomcatEnv`) or *Environments/env_name* (example: `Environments/MyTomcatEnv`). The prefixes `Applications` and `Environments` are optional. The XL Deploy: Undeploy and Control tasks only accept the full name for the package name and environment.

## Mapping of CIs

{:.table table-striped}
| Community supported plugin | Officially supported plugin | Properties not migrated from Community task |
| -----| -----| -----|
| `xldeploy.Server` | `xldeploy.XLDeployServer` | None |
| `xldeploy.Task` | `xldeploy.XldTask` | None |
| `xldeploy.TaskRunningTask` | `xldeploy.RetryTask`| None |
| `xldeploy.DeployTask` | `xldeploy.Deploy` | `Server`, `Environment`, `Deployed Properties` |
| `xldeploy.UndeployTask` | `xldeploy.Undeploy` | `Server` |
| `xldeploy.ControlTask` | `xldeploy.Controltask` | `Server`, `Control task name`, `Parameters` |
| `xldeploy.MigrateTask` | `xld.Migrate` | `Server` |
| `xldeploy.ImportTask` | `xld.ImportTask` | `Server` |
| `xldeploy.GetLatestVersionTask` | `xld.GetLatestVersion` | `Server` |
| `xldeploy.GetAllVersionsTask` | `xld.GetAllVersions` | `Server` |
| `xldeploy.GetLastVersionDeployedTask` | `xld.GetLastVersionDeployed` | `Server` |
| `xldeploy.DoesCIExist` | `xld.DoesCIExist` | `Server` |
| `xldeploy.CreateCI` | `xld.CreateCI` | `Server` |
| `xldeploy.DeleteCI` | `xld.DeleteCI` | `Server` |
| `xldeploy.DeleteInfrastructure` | `xld.DeleteInfrastructure` | `Server` |
| `xldeploy.UpdateCIProperty` | `xld.UpdateCIProperty` | `Server` |
| `xldeploy.CreateFolderTree` | `xld.CreateFolderTree` |`Server`, `Folders` |
| `xldeploy.GetCITask` | `xld.GetCITask` | `Server` |
| `xldeploy.GetCITags` | `xld.GetCITags` | `Server` |
| `xldeploy.AddCITag` | `xld.AddCITag` | `Server` |
| `xldeploy.SetCITags` | `xld.SetCITags` | `Server` |
| `xldeploy.CliConfig` | `xld.CliConfig` | None |
| `xldeploy.cliTask` | `xld.cliTask` | None |
| `xldeploy.cli` | `xld.cli` | `XL Deploy CLI` |
| `xldeploy.cliFile` | `xld.cliFile` | `XL Deploy CLI` |
| `xldeploy.cliUrl` | `xld.cliUrl` | `XL Deploy CLI` |
| `xldeploy.XLDVersionsTile` | `xlrelease.XLDeployTile` | None |

**Note:** The `Deployed Properties` in the `XL Deploy: Deploy` task does not support xpath expression in the official plugin. This property now is a dictionary with key value pairs in the official plugin. The key being the ID of the deployable and the value in json format with the property name and values to be overridden.
