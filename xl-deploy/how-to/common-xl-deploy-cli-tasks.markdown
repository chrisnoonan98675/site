---
title: Common XL Deploy CLI tasks
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- ci
- control task
- repository
- script
---

This topic describes common tasks that you can perform with the XL Deploy command-line interface (CLI).

## Working with configuration items

This section shows some examples of how to work with configuration items (CIs). The two main objects involved are the `factory` object and the `repository` object. The `factory` object is used to actually create the CI itself, while with the `repository` object it is possible to store the CI in the repository.

### Finding out types of available CIs and their properties

The available CIs and their respective type need to be known before being able to create one. Using the command

    deployit> factory.types()

An overview will be shown on standard output of all the available types that are shipped with XL Deploy. If at some point more plugins are added to XL Deploy, types defined therein will be added to XL Deploy's type registry and will then also be available in addition to the types initially shipped with XL Deploy. The new types should also show up in the output of this command.

In order to obtain some more details of a specific type, for instance its required properties, execute the `describe` method on the `deployit` object with the fully qualified type name as its parameter:

    deployit> deployit.describe('udm.Dictionary')

The output of this command will show something like:

    ConfigurationItem udm.Dictionary:
    Description: A Dictionary contains key-value pairs that can be replaced
    Control tasks:
    Properties:
    	- entries(MAP_STRING_STRING): The dictionary entries
    
    Properties marked with a '!' are required for discovery.
    Properties marked with a '*' are required.

### Creating common UDM CIs

The following snippet shows examples of creating common UDM CIs.

	# Create a host
	deployit> sampleHost = factory.configurationItem('Infrastructure/sampleHost', 'overthere.SshHost',
		{ 'os': 'UNIX', 'address': 'localhost', 'username': 'scott' })
	deployit> repository.create(sampleHost)
	deployit> deployit.print(sampleHost)

	# Create a dictionary
	deployit> sampleDict = factory.configurationItem('Environments/myDict', 'udm.Dictionary')
	deployit> sampleDict.entries = { 'a': '1', 'b': '2' }
	deployit> repository.create(sampleDict)
	deployit> deployit.print(sampleDict)

	# Create an environment
	deployit> sampleEnv = factory.configurationItem('Environments/sampleEnv', 'udm.Environment')
	deployit> sampleEnv.dictionaries = [ sampleDict.id ]
	deployit> sampleEnv.members = [ sampleHost.id ]
	deployit> repository.create(sampleEnv)
	deployit> deployit.print(sampleEnv)

### Moving and renaming CIs

The repository allows you to move or rename CIs as well. Note that a CI can only be moved within the root node it was created in. That is, a CI under the _Application_ root node can only be moved to another place in this tree.

The following snippet shows examples of moving and renaming CIs:

	# Create a directory to store environments
	deployit> directory = factory.configurationItem('Environments/ciGroup', 'core.Directory')
	deployit> repository.create(directory)

	# Move the sample environment in the new directory
	deployit> repository.move(sampleEnv, directory.id + '/sampleEnv')
	deployit> sampleEnv = repository.read('Environments/ciGroup/sampleEnv')

	# Rename the directory
	deployit> repository.rename(directory, 'renamedCiGroup')
	deployit> sampleEnv = repository.read('Environments/renamedCiGroup/sampleEnv')

	# References to renamed or moved CIs are kept up-to-date
	deployit> repository.rename(sampleHost, 'renamedSampleHost')
	deployit> sampleEnv = repository.read('Environments/renamedCiGroup/sampleEnv')
	deployit> sampleHost = repository.read(sampleEnv.members[0])

**Note**: Moving or renaming CIs when deployments are in progress or when the CIs concerned are used by XL Deploy clients (GUI or CLI) is discouraged.

## Executing a control task

Control tasks can be executed from the CLI. Take, for example, the _start_ control task on a GlassFish server in the `glassfish-plugin` (available as a community plugin, see the introduction of this manual). It can be executed as follows:

	deployit> server = repository.read('Infrastructure/demoHost/demoServer')
	deployit> deployit.executeControlTask('start', server)

## Executing a control task manually with parameters

Control tasks can also be executed manually. This allows you to set parameters for the control task, and to have more control over the execution of the task. For example:

	deployit> server = repository.read('Infrastructure/demoHost/demoServer')
	deployit> control = deployit.prepareControlTask(server, 'methodWithParams')
    deployit> control.parameters.values['paramA'] = 'value'
    deployit> taskId = deployit.createControlTask(control)
    deployit> deployit.startTaskAndWait(taskId)

## Shutting down the server

The CLI can also be used to shutdown the XL Deploy server as follows:

	deployit.shutdown()

Note that you must have administrative permissions for this.

## Exporting and importing the repository

The `repository` object allows you to export XL Deploy repository tree into a ZIP file, so that it can be imported into the same or another XL Deploy server. This feature can be used for backup or migration purposes. For example following code will export all applications into a ZIP file `DEPLOYIT_SERVER_HOME/export/Applications-<date>.zip`:

	deployit> fileName = repository.exportCisAndWait('Applications')

You can monitor server log files to see the progress. The resulting ZIP file contains all configuration item properties, including artifact files.

To export all configuration items (Applications, Environments etc.) use `'/'` or `None` as the argument: `repository.exportCis('/')`.

More notes about the export:

* Only the current versions of configuration items are exported. Previous versions are not.
* During the export process, do not change the repository items that are being exported. Doing so may interrupt the export or corrupt the output.
* Exporting large number of configuration items can take a long time, so it is recommended to do it when no deployments are running.

Export process is a task, same as deployments, so you can control it using the same objects. If you want to have more control over the export task, you can use it like this:

	deployit> taskId = repository.exportCis('Applications')
	deployit> task2.start(taskId)
	deployit> print(str(task2.get(taskId).state))
	deployit> deployit.waitForTask(taskId)

For importing previously exported content you can use either:

	deployit> repository.importCisAndWait(fileName)

or:

	deployit> taskId = repository.importCis(fileName)
	deployit> task2.start(taskId)
	deployit> print(str(task2.get(taskId).state))
	deployit> deployit.waitForTask(taskId)

Please note that the content of the exported location will be removed before import. For example, if you exported a folder `Applications/myApps`, then this folder will be removed before import of the archive. If you exported contents of the whole repository by passing `/` as a root id, then all the CIs except internal roots `Applications`, `Environments`, `Infrastructure` and `Configuration` will be removed from the repository.

You need administrative permissions to perform repository export or import.
