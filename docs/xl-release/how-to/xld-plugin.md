---
title: Using XL Deploy plugin for XL Release
categories:
xl-release
subject:
Bundled plugins
tags:
plugin
xl deploy
task
xld
---

The XL Deploy plugin for XL Release allows you to start a control task or to start a deployment of an application on XL Deploy. It also provides the functionality to trigger undeployment of an application already deployed on XL Deploy.

If you are using the community supported `xlr-xldeploy-plugin` and want to migrate to the offically supported `xlr-xld-plugin`, refer to [Migrating from the community XL Deploy plugin for XL Release to the officially supported plugin](/xl-release/how-to/xld-plugin-community-to-official.html).

## Requirements

The XL Deploy plugin for XL Release requires XL Release version 6.2.x or later to be installed and is compatible with XL Deploy version 5.5.x or later.

## Installation

1. Download the XL Release XL Deploy plugin from https://xebialabs.com/products/xl-release/plugins/.
1. Unpack the plugin inside the `XL_RELEASE_SERVER_HOME/plugins/` directory.

## Configure XL Deploy Server (Shared configuration)

1. In XL Release menu bar, go to **Settings** > **Shared Configuration** > **XL Deploy Server** and click ![image](/xl-release/images/add-button.png).
1. Specify the required information:
  * `Title` [Required] Select a suitable title for the server configuration.
  * `Url` [Required] URL to connect to XL Deploy Server (Example: `http://localhost:4516`). If the port number is not specified in the URL, the plugin uses the default port number: 4517 for `https` protocol and 4516 for `http` protocol. If XL Deploy is running over ports 80 or 443, you must mention this explicitly in the URL.
  * `Username` Username for XL Deploy server. Can be provided/overridden at task level.
  * `Password` Password for XL Deploy server. Can be provided/overridden at task level.
  * `Proxy Host` HTTP proxy host if needed.
  * `Proxy Port` HTTP proxy Port if needed.
  * `Proxy User Name` HTTP proxy User name to connect to XL Deploy.
  * `Proxy Password` HTTP proxy Password to connect to XL Deploy.

## Add a control task

Control tasks are actions that you can perform on middleware or middleware resources. For example: checking the connection to a host is a control task. When you trigger a control task, XL Deploy starts a task that executes the steps associated with the control task.

You can create a control task in XL Release by adding a task of type `XL Deploy` -> `Control Task` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Configuration Item Id`: [Required] Full CI name (Example: `Infrastructure/ProductionBox`). Autocomplete is not supported.
  * `Control task name`: Name of the control task (Example: `checkConnection`). Autocomplete is not supported.
  * `Username`: Required if not provided in Shared Configuration.
  * `Password`: Required if not provided in Shared Configuration.
  * `Continue If Step Fails`: If it is set to `True` and a step fails, the task is retried. Default value is `False`.
  * `Number Of Continue Retrials`: Number of times to retry the task when the step fails and 'Continue If Step Fails' is set to `True`. Default value is 0.
  * `Polling Interval`: Polling interval in seconds to check the task status. Default value is 10.
  * `Polling Retry Count`: Number of times to retry checking for the task status. Default value is 0.
  * `Display Step Logs`: If it is set to `True`, the step logs will be printed. Defaults value is `True`.
  * `Parameters`: A dictionary with control task parameters.

## Add a deploy task

The deploy task is an automated task that tells XL Deploy to deploy a certain application to an environment. Both the application (Package below) and environment (Environment below) must be configured in XL Deploy. The task gives live updates of the deployment process and completes automatically when the deployment succeeds.

You can create a deploy task in XL Release by adding a task of type `XL Deploy` -> `Deploy` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Application`: ID of the application to be deployed. Autocomplete is supported.
  * `Version`: Version of the application to be deployed. Autocomplete is supported.
  * `Package`: [Required] Fully qualified ID of the package to deploy (Example: `Applications/PetClinic/1.0` or `PetClinic/1.0`). This field is auto-populated on completing `Application` and `Version` fields. Applications prefix is optional.
  * `Environment`: [Required] ID of the environment to deploy (Example: `SITEnv` or `Environments/SITEnv`). Autocomplete is supported. Environments prefix is optional.
  * `Username`: Required if not provided in Shared Configuration.
  * `Password`: Required if not provided in Shared Configuration.
  * `Continue If Step Fails`: If it is set to `True` and a step fails, the task is retried. Default value is `False`.
  * `Number Of Continue Retrials`:  Number of times to retry the task when the step fails and 'Continue If Step Fails' is set to `True`. Default value is 0.
  * `Polling Interval`: Polling interval in seconds to check the task status. Default value is 10.
  * `Polling Retry Count`: Number of times to retry checking for the task status. Default value is 0.
  * `Display Step Logs`: If it is set to `True`, the step logs will be printed. Defaults value is `True`.
  * `Orchestrators`: Comma separated list of orchestrators.
  * `Deployed Application Properties`: A dictionary with key value pairs to set deployed application properties.
  * `Deployed Properties`: A dictionary with key value pairs. The key is the ID of the deployable and the value in JSON format with the property name and values to be overridden.
  * `Rollback On Failure`: Enable this to perform rollback if the deployment fails.
  * `Cancel If Not Rollbacking`: Enable this to cancel the task if the deployment fails and `Rollback on Failure` is not selected.
  * `Fail On Pause`: Enable this to fail the task if it is paused.

  ![image](../images/xl-deploy-task.png)

## Add an undeploy task

The undeploy task is an automated task that tells XL Deploy to undeploy an application from an environment. The name of the deployed application holds the complete information about the environment and the package to be undeployed. For example: Undeploy `Environments/SITEnv/PetClinic` tells that package PetClinic must be undeployed from Environment SITEnv.

You can create an undeploy task in XL Release by adding a task of type `XL Deploy` -> `Undeploy` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Deployed Application`: [Required] Name of the deployed application you want to undeploy (Example: `Environments/SITEnv/PetClinic`). Autocomplete supported.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Continue If Step Fails`: If it is set to `True` and a step fails, the task is retried. Default value is `False`.
  * `Number Of Continue Retrials`:  Number of times to retry the task when the step fails and 'Continue If Step Fails' is set to `True`. Default value is 0.
  * `Polling Interval`: Polling interval in seconds to check the task status. Default value is 10.
  * `Polling Retry Count`: Number of times to retry checking for the task status. Default value is 0.
  * `Display Step Logs`: If it is set to `True`, the step logs will be printed. Defaults value is `True`.
  * `Orchestrators`: Comma separated list of orchestrators.
  * `Deployed Application Properties`: A dictionary with key value pairs to set deployed application properties.
  * `Rollback On Failure`: Enable this to perform rollback if the undeployment fails.
  * `Cancel If Not Rollbacking`: Enable this to cancel the task if the undeployment fails and `Rollback on Failure` is not selected.
  * `Fail On Pause`: Enable this to fail the task if it is paused.
  * `Fail If Application Does Not Exist`: Enable this to fail the task if the application does not exist.

For more information about using the deployment rollback option, refer to [Perform deployment rollback](/xl-release/how-to/perform-deployment-rollback.html).  

## Add an **Add CI tag** task

The **Add CI tag** task is an automated task that adds a new tag to an existing CI in XL Deploy.

You can create an **Add CI tag** task in XL Release by adding a task of type `XL Deploy` -> `Add CI Tag` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `XL Deploy CI`: [Required] Full Configuration Item ID where tags have to be added (Example: `Infrastructure/ProductionBox`).
  * `XL Deploy New Tag`: [Required] A new tag to add to the CI.

## Add a **Get CI tags** task

The **Get CI tags** task is an automated task that retrieves all the tags of a CI from XL Deploy. The tags are displayed in an output property.

You can create a **Get CI tags** task in XL Release by adding a task of type `XL Deploy` -> `Get CI Tags` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `XL Deploy CI`: [Required] Full Configuration Item ID for which tags are required (Example: `Infrastructure/ProductionBox`).
  * `XL Deploy Tags`: The output property with the list of tags of the above mentioned XL Deploy CI.

## Add a **Set CI tags** task

The **Set CI tags** task is an automated task that adds new tags to a CI in XL Deploy and replaces the old tags. The tags are displayed in an output property.

You can create an **Set CI tags** task in XL Release by adding a task of type `XL Deploy` -> `Set CI Tags` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `XL Deploy CI`: [Required] Full Configuration Item ID for which tags are required (Example: `Infrastructure/ProductionBox`).
  * `XL Deploy New Tags`: [Required] The list of new tags to be added to the above mentioned XL Deploy CI.
  * `XL Deploy Tags`: The output property with the list of tags of the above mentioned XL Deploy CI.

## Add a **Create CI** task

The **Create CI** task is an automated task that creates a CI and optionally adds it to an environment.

You can create a **Create CI** task in XL Release by adding a task of type `XL Deploy` -> `Create CI` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `CI ID`: [Required] Full Configuration Item ID (Example: `Applications/PetClinic/1.0/CIName`).
  * `CI Type`: [Required] The type of the Configuration Item (Example: `udm.Application`).
  * `Xml Descriptor`: XML with the fields to set on the CI.
  * `Add To Environment`: Switch this to add the CI to an environment.
  * `Environment ID`: Environment where to add the CI.

## Add a **Delete CI** task

The **Delete CI** task is an automated task that deletes a CI after optionally removing it from an environment.

You can create a **Delete CI** task in XL Release by adding a task of type `XL Deploy` -> `Delete CI` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `CI ID`: [Required] Full Configuration Item ID (Example: `Applications/PetClinic/1.0/CIName`).
  * `Environment ID`: Environment where you remove the CI.
  * `Throw on Fail`: Switch this to fail the task if the CI does not get deleted or if one of the provided CIs does not exist.

## Add a **Get CI** task

The **Get CI** task is an automated task that retrieves a CI from XL Deploy in either XML or JSON format.

You can create a **Get CI** task in XL Release by adding a task of type `XL Deploy` -> `Get CI` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `CI ID`: [Required] Fully qualified Configuration Item ID to get (Example: `Applications/PetClinic/1.0/CIName`).
  * `Format`: [Required] The format in which the CI will be returned: XML or JSON.
  * `Throw on Fail`: Switch this to fail the if the CI does not exist.

## Add an **Update CI property** task

The **Update CI property** task is an automated task used to update a property from a CI on XL Deploy.

You can create an **Update CI property** task in XL Release by adding a task of type `XL Deploy` -> `Update CI property` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `CI ID`: [Required] Fully qualified Configuration Item ID to update (Example: `Applications/PetClinic/1.0/CIName`).
  * `Property name`: [Required] Name of the property to update on the CI.
  * `Property value`: [Required] Value of the property to update.

## Add a **Does CI exist** task

The **Does CI exist** task is an automated task to check if a CI exists.

You can create an **Does CI exist** task in XL Release by adding a task of type `XL Deploy` -> `Does CI exist` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `CI ID`: [Required] Fully qualified Configuration Item ID to check (Example: `Infrastructure/localhost`).
  * `Throw on Fail`: Switch this to fail the task if the provided CI does not exist.

## Add a **Create folder (tree)** task

The **Create folder (tree)** task is an automated task that creates a folder or the folder tree (if required).

You can create a **Create folder (tree)** task in XL Release by adding a task of type `XL Deploy` -> `Create Folder (Tree)` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Folders`: [Required] Comma separated list of the folder names to create (Example: directory, directory/subdirectory).
  * `Folder Type`: [Required] The type of folder to create: Applications, Environment, Infrastructure, Configuration.

## Add a **Get latest version** task

The **Get latest version** task is an automated task to find the latest deployment package version for an application in XL Deploy.

You can create a **Get latest version** task in XL Release by adding a task of type `XL Deploy` -> `Get latest version` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Application ID`: [Required] Application ID for which the version needs to be fetched (Example: `Applications/PetClinic`).
  * `Strip Applications`: Switch this to strip the prefix `Applications` from the application ID.
  * `Throw on Fail`: Switch this to fail the task if the provided CI does not exist.

## Add a **Get all versions** task

The **Get all versions** task is an automated task to find all deployment package versions for an application in XL Deploy.

You can create a **Get all versions** task in XL Release by adding a task of type `XL Deploy` -> `Get all versions` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Application ID`: [Required] Application ID for which the versions need to be fetched (Example: `Applications/PetClinic`).
  * `Throw on Fail`: Switch this to fail the task if the provided CI does not exist.

## Add a **Get last deployed version** task

The **Get last deployed version** task is an automated task to find the latest version of the package deployed in XL Deploy.

You can create a **Get last deployed version** task in XL Release by adding a task of type `XL Deploy` -> `Get last deployed version` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Environment ID`: [Required] Environment ID where the application is deployed.
  * `Application Name`: [Required] Application ID for which the version needs to be fetched.

## Add an **Import package** task

The **Import package** task is an automated task that imports a package from a remote URL.

You can create an **Import package** task in XL Release by adding a task of type `XL Deploy` -> `Import package` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Repository URL`: [Required] URL of the artifact repository from where the package needs to be imported.
  * `Repository username`: Username of the artifact repository.
  * `Repository password`: Password of the artifact repository.

## Add a **Migrate package** task

The **Migrate package** task is an automated task that migrates a package from one XL Deploy server to another XL Deploy server.

You can create a **Migrate package** task in XL Release by adding a task of type `XL Deploy` -> `Migrate package` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Destination server`: [Required] XL Deploy Server where to migrate the package.
  * `Destination username`: Required if not provided in Shared configuration.
  * `Destination password`: Required if not provided in Shared configuration.
  * `Deployment Package`: [Required] Fully qualified ID of the package to deploy (Example: `Applications/PetClinic/1.0`).
  * `Auto Create Path`: Switch this to auto create the path.
  * `Overwrite on Destination`: Switch this to overwrite a package with the same ID that already exists on the destination server. If this is set to `False`, it will throw an error.

## Add a **Delete infrastructure** task

The **Delete infrastructure** task is an automated task that deletes specified infrastructure and all deployed dependencies.

You can create a **Delete infrastructure** task in XL Release by adding a task of type `XL Deploy` -> `Delete Infrastructure` with the following properties:
  * `Server`: [Required] XL Deploy Server to connect to.
  * `Username`: Required if not provided in Shared configuration.
  * `Password`: Required if not provided in Shared configuration.
  * `Infrastructure ID `: [Required] Name of the infrastructure you want to delete (Example: `Infrastructure/ProductionBox`). Autocomplete is supported.

## Configure XL Deploy CLI (Shared configuration)

1. In XL Release menu bar, go to **Settings** > **Shared Configuration** > **XL Deploy CLI config** and click ![image](/xl-release/images/add-button.png).
1. Specify the required information:
  * `Title` [Required] Select a suitable title for the server configuration.
  * `Cli Home` [Required] The home directory where XL Deploy CLI is installed.
  * `XLD Host` The XL Deploy server host where the CLI should connect. DEFAULT will work if it is on the same server as XL Deploy.
  * `XLD Port` The XL Deploy server port where the CLI should connect. DEFAULT will work if it is using the default XL Deploy port.
  * `XLD Secure` Switch to specify if the connection to XL Deploy will be secure. If set to `True`, the port will default to 4517.
  * `XLD Context` The context for XL Deploy CLI. DEFAULT will work if no context is needed.
  * `XLD Proxy Host` HTTP proxy host if needed.
  * `XLD Proxy Port` HTTP proxy port if needed.
  * `XLD Socket Timeout` Connection timeout to XL Deploy.
  * `XLD Username` Username to connect to XL Deploy. Defaults to admin.
  * `XLD Password` Password to connect to XL Deploy. Defaults to admin.

## Add a **Run script** task

The **Run script** task is an automated task that runs a specified script on XL Deploy CLI.

You can create a **Run script** task in XL Release by adding a task of type `XL Deploy CLI` -> `Run script` with the following properties:
  * `XL Deploy CLI`: [Required] XL Deploy CLI to connect to.
  * `Script`: [Required] The script that runs on XL Deploy CLI.
  * `Options`: The command line options used with the script.
  * `Console output`: The output property which displays the console output of the process.
  * `Error`: The output property which displays the error stream of the process.
  
## Add a **Run script from file** task

The **Run script from file** task is an automated task that runs a specified script from a file on XL Deploy CLI.

You can create a **Run script from file** task in XL Release by adding a task of type `XL Deploy CLI` -> `Run script from file` with the following properties:
  * `XL Deploy CLI`: [Required] XL Deploy CLI to connect to.
  * `Script`: [Required] The location of the script file that runs on the XL Deploy CLI.
  * `Options`: The command line options used with the script.
  * `Console output`: The output property which displays the console output of the process.
  * `Error`: The output property which displays the error stream of the process.
  
## Add a **Run script from url** task

The **Run script from url** task is an automated task that runs a specified script from a URL on XL Deploy CLI.

You can create a **Run script from url** task in XL Release by adding a task of type `XL Deploy CLI` -> `Run script from url` with the following properties:
  * `XL Deploy CLI`: [Required] XL Deploy CLI to connect to.
  * `Script Url`: [Required] The URL of the script file that runs on the XL Deploy CLI.
  * `URL Username`: Username used when accessing the URL.
  * `URL Password`: Password used when accessing the URL.
  * `Options`: The command line options used with the script.
  * `Console output`: The output property which displays the console output of the process.
  * `Error`: The output property which displays the error stream of the process.
  
## Release notes

### Version 8.1.0

#### Features

* [REL-6571] Add a task to delete infrastructure
* [REL-4473] Add a task to migrate a package
* [REL-4474] Add a task to import a package from remote URL
* [REL-4479] Add a task to create a CI
* [REL-4480] Add a task to update a CI property
* [REL-4481] Add a task to delete a CI
* [REL-4478] Add a task to check whether a CI exists
* [REL-6573] Add a task to get a CI in json or xml format
* [REL-6569] Add a task to add a tag to CI
* [REL-6574] Add a task to get tags of a CI
* [REL-6575] Add a task to set tags for a CI
* [REL-4475] Add a task to get all versions of an applications
* [REL-4476] Add a task to get latest version of an application
* [REL-4477] Add a task to get latest deployed version of an application
* [REL-6570] Add a task to create a folder
* [REL-6974] Add tasks to run XL Deploy CLI scripts

#### Improvements

* [REL-6572] Improve Deploy task to add more support
* [REL-6576] Improve UnDeploy task to add more support
* [REL-6182] Make Deploy and Undeploy tasks consistent
* [REL-6745] Add support to update deployed properties through Deploy task
* [REL-6747] Support all properties in Control task as community plugin
* [REL-6333] Split the package into Application and version during input selection with restricted results
* [REL-6795] Changing labels and property descriptions of tasks

#### Bug Fixes

* [REL-3781] Cannot connect to XL Deploy server when URL ends with a slash
* [REL-3126] XL Deploy server configuration breaks correct URL
* [DEPL-13361] Send http over port 4516 and https over port 4517 when no port is specified
* [DEPL-13395] Deploy task should not fail on warning

### Version 8.0.0

#### Improvements

* Compatibility with XL Release 8.0.0

### Version 7.5.2

#### Bug Fixes

* [REL-6222] Bug fix for Jython: auto detect encoding issue

### Version 7.5.1

#### Improvements

* [REL-4944] Support for Control task parameters in xlr-xld-plugin
* [REL-5877] Add ability to pass parameters while invoking a control task in XL Deploy plugin for XL Release
* [REL-5969] Show exception raised for a failed XL Deploy deployment in XL Release
