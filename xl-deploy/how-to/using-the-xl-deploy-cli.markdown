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

XL Deploy includes a command-line interface (CLI) that you can use to control and administer many features, such as discovering middleware topology, setting up environments, importing packages, and performing deployments.

The CLI can be programmed with [Python](http://www.python.org/). It connects to the XL Deploy server using the standard HTTP / HTTPS protocol. This makes it possible to use the CLI remotely without firewall issues.

Before you start using the XL Deploy CLI, ensure that the XL Deploy server is running.

## Environment variables

After installing the XL Deploy CLI, it's good practice to set an environment variable named `DEPLOYIT_CLI_HOME` which points to the root directory where the CLI has been installed. In the remainder of this manual, the `DEPLOYIT_CLI_HOME` environment variable will be assumed to be set and to refer to the XL Deploy CLI installation directory.

A second environment variable, `DEPLOYIT_CLI_OPTS`, can be used to provide JVM options for the XL Deploy CLI process. For example, to set the initial Java heap size to 512 megabytes of memory, and the maximum Java heap size to 2 gigabytes of memory, the environment variable would be set as follows:

* Unix: `export DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`
* Windows: `set DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`

If not set, the CLI startup scripts provide sensible defaults.

## Starting the XL Deploy CLI

To access the XL Deploy CLI, open a terminal window or command shell and change to the `DEPLOYIT_CLI_HOME/bin` directory.

Start the CLI with the command `./cli.sh` on Unix or `cli.cmd` on Windows.

When the CLI starts, it will prompt you for a `username` and `password` combination. After you provide them, the CLI will attempt to connect to the XL Deploy server on `localhost` running on XL Deploy's standard port of `4516`.

After you are connected to the XL Deploy server, you can start sending commands.

## CLI startup options

Start the CLI with the `-h` flag to see the options that you can use during startup.

**Note:** Use `cli.cmd` instead of `./cli.sh` on Windows.

The options are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-configuration config_directory` | Pass an alternative configuration directory to the CLI. The CLI will look for a `deployit.conf` in this location. The configuration file supports the `cli.username` and `cli.password` options. |
| `-context newcontext` | If provided, the `context` value will be added to the XL Deploy server connection URL. For example, if `newcontext` is given, the CLI will attempt to connect to the XL Deploy server REST API at `http://host:port/newcontext/deployit`. Note that the leading slash and REST API endpoint (`deployit`) will automatically be added if they are omitted from the parameter. |
| `-f Python_script_file` | Starts the CLI in batch mode to run the provided Python file. After the script completes, the CLI will terminate. **Note**: The XL Deploy CLI can load and run Python script files of up to 100K in size. |
| `-source Python_script_file` | Alternative for the `-f` option. |
| `-host myhost.domain.com` | Specifies the host the XL Deploy server is running on. The default host is `127.0.0.1` (that is, `localhost`). |
| `-port 1234` | Specifies the port at which to connect to the XL Deploy server. If the port is not specified, it will default to XL Deploy's default port, `4516`. |
| `-proxyHost VAL` | Specifies the HTTP proxy host if XL Deploy needs to be accessed via an HTTP proxy. |
| `-proxyPort N` | Specifies the HTTP proxy port if XL Deploy needs to be accessed via an HTTP proxy. |
| `-secure` | Instruct the CLI connect to the XL Deploy server using HTTPS. By default it will connect to the secure port 4517, unless a different port is specified with the `-port` option. To connect, the XL Deploy server must have been started using this secured port (on by the default). |
| `-username myusername` | The username to use for login. If the username is not specified, the CLI will enter interactive mode and prompt the user. |
| `-password mypassword` | The password to use for login. If the password is not specified, the CLI will enter interactive mode and prompt the user. |
| `-q` | Suppresses display of the welcome banner. |
| `-quiet` | Alternative for the `-q` option. |

One example of using options might be:

    ./cli.sh -username User -password UserPassword -host xl-deploy.local

This will connect the CLI as `User` with password `UserPassword` to the XL Deploy server running on the host `xl-deploy.local` and listening on port `4516`.

## Entering XL Deploy server credentials

To provide the user name and password for accessing the XL Deploy server, you can:

* Enter them interactively in the CLI
* Provide them with the `-username` and `password` options
* Store them in the `cli.username` and `cli.password` properties in the `DEPLOYIT_CLI_HOME/conf/deployit.conf` file

## Passing arguments to CLI commands or script

You can pass arguments from the command line to the CLI. You are not required to specify any options in order to pass arguments. 

This is an example of passing arguments, without specifying options:

    ./cli.sh these are four arguments

And with options:

    ./cli.sh -username User -port 8443 -secure again with four arguments

You can start an argument with the character \'`-`\'. To instruct the CLI to not interpret it as an option instead of an argument, use the \'`--`\' separator between the option list and the argument list:

    ./cli.sh -username User -- -some-argument there are six arguments -one

This separator only needs to be used in case one or more of the arguments begin with \'`-`\'.

The arguments may now be used in commands given on the CLI or be used in a script passed with the `-f` option, by using the `sys.argv[index]` method, whereby the index runs from 0 to the number of arguments. Index 0 of the array contains the name of the passed script, or is empty when the CLI was started in interactive mode. The first argument has index 1, the second argument index 2, and so forth. Given the command line in the first example presented above, the following commands:

    import sys
    print sys.argv

Would yield as a result:

    ['', '-some-argument', 'there', 'are', 'six', 'arguments', '-one']

## Logging in and logging out

The CLI will attempt to connect to the XL Deploy server with the provided credentials and connection settings. If a successful connection can not be established, an error message will be displayed and the CLI will terminate.

If a successful connection with the XL Deploy server is established, a welcome message will be printed and the CLI is ready to accept commands.

In interactive mode, the XL Deploy CLI can be ended by typing `quit` at the prompt. This will log out the user and terminate the CLI process. In batch mode (when a script is provided), the CLI terminates automatically after finishing the script.

## CLI extensions

It is possible to install CLI extensions that are loaded during CLI startup. Extensions are Python scripts, for example with Python class definitions, that will be available in commands or scripts run from the CLI. This feature can be combined with arguments given on the command line when starting up the CLI.

To install CLI extensions follow these steps:

1. Create a directory called `ext`. This directory should be created in the same directory from which you will start the CLI; during startup the current directory will be searched for the existence of the `ext` directory.
2. Copy Python scripts into the `ext` directory.
3. Restart the CLI. During startup, the CLI will search for, load and execute all scripts with the `py` or `cli` suffix found in the extension directory.

Please note that the order in which scripts from the ext directory are executed is not guaranteed.

## The CLI and special (Unicode) characters

Per default the CLI uses the platform encoding, this may cause issues in a setting were no uniform encoding is used between systems. Or where multibyte encodings may appear. Of special note is Jython's default behaviour regarding strings and different encodings. When strings need to support unicode it is recommended to make proper unicode strings in your scripts. E.g. use the 'u' prefix for a string and/or use the `unicode` type. So for instance to create a dictionary with special characters in it you would have to use something similar to this:

    dic = factory.configurationItem('Environments/UnicodeExampleDict', 'udm.Dictionary')
    dic.entries = { 'micro' : u'µ', 'paragraph' : u'§', 'eaccute' : u'é' }
    repository.create(dic)
    print dic.entries

In the case were values come from a database or other web services, you should be aware of the encoding they are in and take care of properly converting the values to Unicode.

## CLI examples

For CLI examples, download the XL Deploy demo plugin from [https://github.com/xebialabs/community-plugins/](https://github.com/xebialabs/community-plugins/).
