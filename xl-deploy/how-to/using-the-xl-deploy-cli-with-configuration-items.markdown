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

These are some examples of ways you can use the XL Deploy command-line interface (CLI) to work with configuration items (CIs). The two main objects involved are:

* The `factory`, which is used to create the CI itself
* The `repository` object, which allows you to store the CI in the repository

## Exploring CI types and their properties

To get the CI types that are available, execute:

    deployit> factory.types()

An overview will be shown on standard output of all the available types that are shipped with XL Deploy. If at some point more plugins are added to XL Deploy, types defined therein will be added to XL Deploy's type registry and will then also be available in addition to the types initially shipped with XL Deploy. The new types should also show up in the output of this command.

To obtain more information about a type (for example, its required properties), execute the `describe` method on the `deployit` object with the fully qualified type name as its parameter:

    deployit> deployit.describe('udm.Dictionary')

An example of the output of this command is:

    ConfigurationItem udm.Dictionary:
    Description: A Dictionary contains key-value pairs that can be replaced
    Control tasks:
    Properties:
    	- entries(MAP_STRING_STRING): The dictionary entries
    
    Properties marked with a '!' are required for discovery.
    Properties marked with a '*' are required.

You can also get attributes of a CI, such as when it was created and when it was last modified. For example:

    ci=repository.read("Applications/forFile/1.0")
    ci._ci_attributes.createdAt

## Creating common UDM CIs

This is an example that shows the creation of commonly used CIs:

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

You can use the CLI to move or rename CIs in the repository. A CI can only be moved in the root node in which it was created; for example, a CI under **Applications** can only be moved to another location in the Applications tree.

This is an example that shows how to move and rename CIs:

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

**Note:** It recommended that you do not move or rename CIs while deployments are in progress or while the CIs concerned are being used by an XL Deploy GUI or CLI client.
