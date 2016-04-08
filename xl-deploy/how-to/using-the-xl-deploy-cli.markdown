---
title: Using the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- jython
---

XL Deploy includes a command-line interface (CLI) that you can use to control and administer many features, such as discovering middleware topology, setting up environments, importing packages, and performing deployments. It connects to the XL Deploy server using the standard HTTP/HTTPS protocol, so it can be used remotely without firewall issues.

## Installing and setting up the CLI

For information about installing and setting up the CLI, refer to:

* [Install the XL Deploy CLI](/xl-deploy/how-to/install-the-xl-deploy-cli.html)
* [Configure the CLI to trust an XL Deploy server with a self-signed certificate](/xl-deploy/how-to/configure-the-cli-to-trust-the-xl-deploy-server-with-a-self-signed-certificate.html)
* [Connect to XL Deploy from the CLI](/xl-deploy/how-to/connect-to-xl-deploy-from-the-cli.html)

## The CLI and special (Unicode) characters

By default, the CLI uses the platform encoding. This may cause issues in a setting where uniform encoding is not used between systems, or where multibyte encodings may appear. Of special note is Jython's default behavior regarding strings and different encodings.

When strings need to support Unicode, it is recommended that you use proper Unicode strings in your scripts. That is, `u` prefix for a string and/or use the `unicode` type. For example, to create a dictionary with special characters in it:

    dic = factory.configurationItem('Environments/UnicodeExampleDict', 'udm.Dictionary')
    dic.entries = { 'micro' : u'µ', 'paragraph' : u'§', 'eaccute' : u'é' }
    repository.create(dic)
    print dic.entries

If values come from a database or other web service, you should be aware of the encoding and ensure that the values are properly converted to Unicode.

## Passing arguments to CLI commands or scripts

You can pass arguments from the command line to the CLI. You are not required to specify any options to pass arguments. This is an example of passing arguments, without specifying options:

    ./cli.sh these are four arguments

And with options:

    ./cli.sh -username User -port 8443 -secure again with four arguments

You can start an argument with the `-` character. To instruct the CLI to interpret it as an argument instead of an option, use the `--` separator between the option list and the argument list:

    ./cli.sh -username User -- -some-argument there are six arguments -one

This separator only needs to be used if one or more of the arguments begin with `-`.

You can use the arguments in commands given on the CLI or in a script passed with the `-f` option, by using the `sys.argv[index]` method, whereby the index runs from 0 to the number of arguments. Index 0 of the array contains the name of the passed script, or is empty when the CLI was started in interactive mode. The first argument has index 1, the second argument index 2, and so forth. Given the command line in the first example presented above, the following commands:

    import sys
    print sys.argv

Would yield as a result:

    ['', '-some-argument', 'there', 'are', 'six', 'arguments', '-one']

## Extending the CLI

You can extend the XL Deploy CLI by installing extensions that are loaded during CLI startup. Extensions are [Python](http://www.python.org/) scripts, for example with Python class definitions, that will be available in commands or scripts run from the CLI. This feature can be combined with arguments given on the command line when starting up the CLI.

To install CLI extensions:

1. Create a directory called `ext` in the directory from which you will start the CLI. During startup, the current directory will be searched for the existence of the `ext` directory.
2. Copy Python scripts into the `ext` directory.
3. Restart the CLI. During startup, the CLI will search for, load, and execute all scripts with the `py` or `cli` suffix found in the extension directory.

**Note:** The order in which scripts from the `ext` directory are executed is not guaranteed.

## Troubleshooting the CLI

### I get the message: 'Error loading terminal, using fallback. Your terminal will have reduced functionality.'

The XL Deploy CLI was unable to load the correct Terminal. If you are running the CLI on Windows 2003 or Windows 2008, the solution for this is to install the [Microsoft Visual C++ 2008 Redistributable Package](https://www.microsoft.com/en-us/download/details.aspx?id=2092).

### Prevent XL Deploy from writing temporary files for imported packages

When uploading a package using the CLI, XL Deploy stores a temporary file on the server. This file is deleted only if you shut down the JVM. An alternative is to make XL Deploy read the archive in memory. To do this, use the following setting when starting the XL Deploy server:

	-Dorg.apache.james.mime4j.defaultStorageProvider=org.apache.james.mime4j.storage.MemoryStorageProvider

## More information

For more information about using the CLI, refer to:

* [Objects](/xl-deploy/concept/objects-available-in-the-xl-deploy-cli.html) and [types](/xl-deploy/concept/types-used-in-the-xl-deploy-cli.html) available in the CLI
* [Set up roles and permissions](/xl-deploy/how-to/set-up-roles-and-permissions-using-the-cli.html)
* [Using the CLI with configuration items](/xl-deploy/how-to/using-the-xl-deploy-cli-with-configuration-items.html)
* [Create a deployment package](/xl-deploy/how-to/create-a-deployment-package-using-the-cli.html)
* [Deploy an application](/xl-deploy/how-to/deploy-an-application-using-the-cli.html)
* [Using the stepPath parameter](/xl-deploy/how-to/using-the-steppath-in-the-xl-deploy-rest-api.html)
* [Execute tasks](/xl-deploy/how-to/execute-tasks-from-the-xl-deploy-cli.html)
* [Discover middleware](/xl-deploy/how-to/discover-middleware-using-the-xl-deploy-cli.html)
* [Export items from or import items into the repository](/xl-deploy/how-to/export-items-from-or-import-items-into-the-repository.html)
