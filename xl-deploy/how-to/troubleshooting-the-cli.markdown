---
title: Troubleshooting the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- jython
- troubleshooting
weight: 120
---

## Error when loading terminal

If you see the message `Error loading terminal, using fallback. Your terminal will have reduced functionality`, then the XL Deploy CLI was unable to load the correct terminal. If you are running the CLI on Windows 2003 or Windows 2008, the solution for this is to install the [Microsoft Visual C++ 2008 Redistributable Package](https://www.microsoft.com/en-us/download/details.aspx?id=2092).

## Connecting fails with "Unable to find valid certification path to requested target"

If you see the message `Unable to find valid certification path to requested target` when trying to connect to the XL Deploy server from the CLI, you need to [configure the CLI to trust XL Deploy's self-signed certificate](/xl-deploy/how-to/configure-the-cli-to-trust-the-xl-deploy-server-with-a-self-signed-certificate.html).

## Using the CLI with special (Unicode) characters

By default, the CLI uses the platform encoding. This may cause issues in a setting where uniform encoding is not used between systems, or where multibyte encodings may appear. Of special note is Jython's default behavior regarding strings and different encodings.

When strings need to support Unicode, it is recommended that you use proper Unicode strings in your scripts. That is, use the `u` prefix for a string and/or use the `unicode` type. For example, to create a dictionary with special characters in it:

    dic = factory.configurationItem('Environments/UnicodeExampleDict', 'udm.Dictionary')
    dic.entries = { 'micro' : u'µ', 'paragraph' : u'§', 'eaccute' : u'é' }
    repository.create(dic)
    print dic.entries

If values come from a database or other web service, you should be aware of the encoding and ensure that the values are properly converted to Unicode.

## Preventing XL Deploy from writing temporary files for imported packages

When uploading a package using the CLI, XL Deploy stores a temporary file on the server. This file is only deleted if you shut down the Java Virtual Machine (JVM). An alternative is to make XL Deploy read the archive in memory. To do this, use the following setting when starting the XL Deploy server:

	-Dorg.apache.james.mime4j.defaultStorageProvider=org.apache.james.mime4j.storage.MemoryStorageProvider
