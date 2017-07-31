---
title: Migrating from the community XL Deploy plugin for XL Release to the officially supported plugin
categories:
- xl-release
subject:
- Bundled plugins
tags:
- plugin
- xl deploy
- xld
- xlr-xldeploy-plugin
- xlr-xld-plugin
---

The community supported `xlr-xldeploy-plugin` and the officially supported `xlr-xld-plugin` can both exist within the same XL Release instance without any conflicts. For more information about the officially supported `xlr-xld-plugin`, refer to [Using XL Deploy plugin for XL Release](/xl-release/how-to/xld-plugin.html).

When migrating from the community plugin to the officially supported plugin:

* You must define a new shared configuration in **Shared Configuration** > **XL Deploy Server** for the standard plugin. The XL Deploy server configuration defined for the community supported plugin does not apply for tasks created with the officially supported plugin.

* To migrate a deploy, undeploy, or control task defined using community plugin to the official plugin, you must use change task type option **Change Task Type** > **XL Deploy** > **Deploy** / **Undeploy** / **Control Task** and then re-enter the information in all the fields defined for the official plugin.

**Note:** The XL Deploy: Deploy task accepts the package name as *Application_name/package_name* (example: `PetClinic/1.0`) and the environment as *env_name* (example: `MyTomcatEnv`). The XL Deploy: Undeploy and Control tasks only accept the full name for the package name and environment.

## Mapping of CIs

{:.table table-striped}
| Community supported plugin | Officially supported plugin |
| ------ | ------ |
| `xldeploy.Server` | `xldeploy.XLDeployServer` |
| `xldeploy.Task` | `xldeploy.XldTask` |
| `xldeploy.TaskRunningTask` | Not present|
| `xldeploy.DeployTask` | `xldeploy.Deploy` |
| `xldeploy.UndeployTask` | `xldeploy.Undeploy` |
| `xldeploy.ControlTask` | `xldeploy.Controltask` |
| `xldeploy.MigrateTask` | Not present |
| `xldeploy.ImportTask` | Not present |
| `xldeploy.GetLatestVersionTask` | Not present |
| `xldeploy.GetAllVersionsTask` | Not present |
| `xldeploy.GetLastVersionDeployedTask` | Not present |
| `xldeploy.DoesCIExist` | Not present |
| `xldeploy.CreateCI` | Not present |
| `xldeploy.DeleteCI` | Not present |
| `xldeploy.DeleteInfrastructure` | Not present |
| `xldeploy.UpdateCIProperty` | Not present |
| `xldeploy.CliConfig` | Not present |
| `xldeploy.cliTask` | Not present |
| `xldeploy.cli` | Not present |
| `xldeploy.cliFile` | Not present |
| `xldeploy.cliUrl` | Not present |
| `xldeploy.XLDVersionsTile` | `xlrelease.XLDeployTile` |
