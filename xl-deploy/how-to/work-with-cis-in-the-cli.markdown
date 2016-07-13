---
title: Work with configuration items in the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- ci
- script
- python
- jython
weight: 113
---

When using the [XL Deploy command-line interface (CLI)](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html) to work with configuration items (CIs), the main objects you will use are:

* The `factory` object, which is used to create a CI
* The `repository` object, which allows you to store a CI in the XL Deploy repository

## Explore CI types and their properties

To get the CI types that are available, execute:

    factory.types()

The CLI will show an overview of the CI types that are available, including types that are defined by plugins.

### Get information about a CI type

To get information about a type, such as its required properties, execute the `describe` method on the `deployit` object with the fully qualified type name as its parameter. For example:

    deployit.describe('udm.Dictionary')

An example of the output of this command is:

    ConfigurationItem udm.Dictionary:
    Description: A Dictionary contains key-value pairs that can be replaced
    Control tasks:
    Properties:
        - entries(MAP_STRING_STRING): The dictionary entries
        - encryptedEntries(MAP_STRING_STRING): The encrypted dictionary entries
        - restrictToContainers(Set<udm.Container>): Only apply this dictionary to the containers mentioned
        - restrictToApplications(Set<udm.Application>): Only apply this dictionary to the applications mentioned

    Properties marked with a '!' are required for discovery.
    Properties marked with a '*' are required.

## Get attributes of a specific CI

To get the attributes of a specific CI, such as when it was created or when it was last modified, execute the `read` method on the `repository` object. For example:

    ci=repository.read("Applications/Sample Apps/BookStore/1.0.0")
    ci._ci_attributes.createdAt

An example of the output of this command is:

    DateTime: 2016-05-13T10:07:25.312Z

## Create CIs

These examples show how to create some commonly used CIs.

### Create an SSH host CI

To create an SSH host CI, execute:

    sampleHost = factory.configurationItem('Infrastructure/sampleHost', 'overthere.SshHost', { 'os': 'UNIX', 'address': 'localhost', 'username': 'scott' })

To save the CI in the repository, execute:

    repository.create(sampleHost)

### Create a dictionary CI

To create a dictionary CI, execute:

    sampleDict = factory.configurationItem('Environments/myDict', 'udm.Dictionary')
    sampleDict.entries = { 'a': '1', 'b': '2' }

To save the CI in the repository, execute:

    repository.create(sampleDict)

### Create an environment CI

To create an environment CI and add a host and dictionary to it, execute:

    sampleEnv = factory.configurationItem('Environments/sampleEnv', 'udm.Environment')
    sampleEnv.dictionaries = [ sampleDict.id ]
    sampleEnv.members = [ sampleHost.id ]

To save the CI in the repository, execute:

    repository.create(sampleEnv)

## Move and rename CIs

You can use the CLI to move or rename CIs in the repository. A CI can only be moved within the root node in which it was created; for example, a CI under Applications can only be moved to another location in the Applications tree.

### Move a CI

This example shows how to create a directory CI under the Environments node and then move an environment CI into it.

To create the directory CI and save it to the repository, execute:

    directory = factory.configurationItem('Environments/ciGroup', 'core.Directory')
	repository.create(directory)

Then, move the environment CI into the directory:

    repository.move(sampleEnv, directory.id + '/sampleEnv')
    sampleEnv = repository.read('Environments/ciGroup/sampleEnv')

### Rename a CI

This example shows how to rename a directory CI:

    repository.rename(directory, 'renamedCiGroup')
    sampleEnv = repository.read('Environments/renamedCiGroup/sampleEnv')

References to renamed or moved CIs are kept up-to-date. For example, the *sampleHost* CI is a member of the *sampleEnv* environment. If you rename *sampleHost* to *renamedSampleHost*, then *sampleEnv* will refer to *renamedSampleHost*.

    sampleHost = repository.read('Infrastructure/sampleHost')
    repository.rename(sampleHost, 'renamedSampleHost')
    sampleEnv = repository.read('Environments/renamedCiGroup/sampleEnv')
    sampleHost = repository.read(sampleEnv.members[0])

**Note:** It recommended that you do not move or rename CIs while deployments are in progress or while the CIs are being used by the GUI or the CLI client.
