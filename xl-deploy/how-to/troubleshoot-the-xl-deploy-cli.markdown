---
title: Troubleshoot the XL Deploy CLI
subject:
- CLI
category:
- xl-deploy
tags:
- cli
- troubleshooting
- package
- ci
---

## I get the message: 'Error loading terminal, using fallback. Your terminal will have reduced functionality.'

The XL Deploy CLI was unable to load the correct Terminal. If you're running the CLI on Windows 2003 or Windows 2008, the solution for this is to install the [Microsoft Visual C++ 2008 Redistributable Package](https://www.microsoft.com/en-us/download/details.aspx?id=2092).

## Prevent XL Deploy from writing temporary files for imported packages

When uploading a package using the command-line interface (CLI), XL Deploy stores a temporary file on the server. This file is deleted only if you shut down the JVM. An alternative is to make XL Deploy read the archive in memory. To do this, use the following setting when starting the XL Deploy server:

	-Dorg.apache.james.mime4j.defaultStorageProvider=org.apache.james.mime4j.storage.MemoryStorageProvider

## Create the most common CIs in the CLI

The following snippet shows examples of creating common UDM configuration items (CIs).

    # Create a host
    host = factory.configurationItem('Infrastructure/sampleHost', 'overthere.SshHost', { 'os': 'UNIX', 'address': 'localhost', 'username': 'scott' })
    repository.create(host)                                                                                                                          
    deployit.print(host)
		
    # Create a dictionary
    dict = factory.configurationItem('Environments/myDict', 'udm.Dictionary')
    dict.entries = { 'a': '1', 'b': '2' }
    repository.create(dict)
    deployit.print(dict)

    # Create an environment
    env = factory.configurationItem('Environments/sampleEnv', 'udm.Environment')
    env.dictionaries = [ dict.id ]
    env.members = [ host.id ]                                                                                                                        
    repository.create(env)
    deployit.print(env)
