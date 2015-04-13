---
title: Using the XL Deploy CLI with configuration items
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- ci
- repository
- script
---

These are some examples of ways you can use the XL Deploy command-line interface (CLI) to work with configuration items (CIs). The two main objects involved are the `factory` object and the `repository` object. The `factory` object is used to actually create the CI itself, while with the `repository` object it is possible to store the CI in the repository.

## Exploring CI types and their properties

The available CIs and their respective type need to be known before being able to create one. Using the command:

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

## Creating common UDM CIs

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

## Moving and renaming CIs

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
