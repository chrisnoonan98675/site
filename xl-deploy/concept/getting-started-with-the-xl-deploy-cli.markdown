---
title: Getting started with the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- jython
weight: 110
---

XL Deploy includes a command-line interface (CLI) that you can use to control and administer many features, such as discovering middleware topology, setting up environments, importing packages, and performing deployments. It connects to the XL Deploy server using the standard HTTP/HTTPS protocol, so it can be used remotely without firewall issues.

## Install the CLI

For installation instructions, refer to [Install the XL Deploy CLI](/xl-deploy/how-to/install-the-xl-deploy-cli.html).

If you have configured your XL Deploy server to use a [self-signed certificate](/xl-deploy/how-to/install-xl-deploy.html#step-3-generate-a-self-signed-certificate), you must also [configure the CLI to trust the server](/xl-deploy/how-to/configure-the-cli-to-trust-the-xl-deploy-server-with-a-self-signed-certificate.html).

## Connect to XL Deploy from the CLI

After you install the CLI, you can connect to the XL Deploy server as follows:

1. Ensure that the XL Deploy server is running.
2. Open a terminal window or command prompt and go to the `XLDEPLOY_CLI_HOME/bin` directory (where `XLDEPLOY_CLI_HOME` is the directory where the CLI is installed).
3. Execute the start command:
    * Unix-based operating systems: `./cli.sh`
    * Microsoft Windows: `cli.cmd`
4. Enter your user name and password.

    The CLI attempts to connect to the server on `localhost`, running on XL Deploy's standard port of `4516`.

### Entering XL Deploy server credentials

To provide the user name and password for accessing the XL Deploy server, you can:

* Enter them interactively in the CLI
* Provide them with the `-username` and `-password` options
* Store them in the `cli.username` and `cli.password` properties in the `XLDEPLOY_CLI_HOME/conf/deployit.conf` file

### Entering passwords on the Windows command line

{% comment %} This section requires the following versions or later: 4.0.4, 4.5.5, 5.0.1 {% endcomment %}

You can use special characters such as `!`, `^`, or `"` in passwords. These characters have special meaning in the Microsoft Windows command prompt, which means that passing them to the XL Deploy server as-is causes log-in to fail.

To prevent this issue, surround the password with quotation marks (`"`). If the password contains a quotation mark, you must triple it. For example, `My!pass^wo"rd` should be entered as `-password "My!pass^wo"""rd"`.

## CLI startup options

The CLI can be started with the following options:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-configuration config_directory` | Pass an alternative configuration directory to the CLI. The CLI will look for a `deployit.conf` in this location. The configuration file supports the `cli.username` and `cli.password` options. |
| `-context newcontext` | If provided, the `context` value will be added to the XL Deploy server connection URL. For example, if `newcontext` is given, the CLI will attempt to connect to the XL Deploy server REST API at `http://host:port/newcontext/deployit`. The leading slash and REST API endpoint (`deployit`) will automatically be added if they are omitted from the parameter.<br /><br /> **Tip:** If the XL Deploy context root is set to `deployit`, the `-context` value must be `/deployit/deployit`. |
| `-f Python_script_file` | Starts the CLI in batch mode to run the provided Python file. After the script completes, the CLI will terminate. The XL Deploy CLI can load and run Python script files of up to 100 KB in size. |
| `-source Python_script_file` | Alternative for the `-f` option. |
| `-host myhost.domain.com` | Specifies the host the XL Deploy server is running on. The default host is `127.0.0.1` (that is, `localhost`). |
| `-port 1234` | Specifies the port at which to connect to the XL Deploy server. If the port is not specified, it will default to XL Deploy's default port, `4516`. |
| `-proxyHost VAL` | Specifies the HTTP proxy host if XL Deploy needs to be accessed via an HTTP proxy. |
| `-proxyPort N` | Specifies the HTTP proxy port if XL Deploy needs to be accessed via an HTTP proxy. |
| `-secure` | Instruct the CLI connect to the XL Deploy server using HTTPS. By default it will connect to the secure port 4517, unless a different port is specified with the `-port` option. To connect, the XL Deploy server must have been started using this secured port (enabled by the default). |
| `-username myusername` | The username for logging in. If the username is not specified, the CLI will enter interactive mode and prompt the user. |
| `-password mypassword` | The password for logging in. If the password is not specified, the CLI will enter interactive mode and prompt the user. |
| `-q` | Suppresses display of the welcome banner. |
| `-quiet` | Alternative for the `-q` option. |
| `-h` | List the startup options. |

## Accessing help in the CLI

To access help in the CLI, execute the `help` command.

To get information about a specific [object](/xl-deploy/concept/objects-available-in-the-xl-deploy-cli.html), execute `<objectname>.help()`. To get information about a specific method, execute `<objectname>.help("<methodname>")`.

### CLI startup example

An example of the CLI startup options is:

    ./cli.sh -username User -password UserPassword -host xl-deploy.local

This will connect the CLI as `User` with password `UserPassword` to the XL Deploy server running on the host `xl-deploy.local` and listening on port `4516`.

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

## Sample CLI scripts

This is an example of a simple CLI script that deploys the BookStore 1.0.0 application to an environment called TEST01:

    # Load package
    package = repository.read('Applications/Sample Apps/BookStore/1.0.0')

    # Load environment
    environment = repository.read('Environments/Testing/TEST01')

    # Start deployment
    deploymentRef = deployment.prepareInitial(package.id, environment.id)
    depl = deployment.prepareAutoDeployeds(deploymentRef)
    task = deployment.createDeployTask(depl)
    deployit.startTaskAndWait(task.id)

This is an example of the same deployment with an [orchestrator](/xl-deploy/concept/types-of-orchestrators-in-xl-deploy.html):

    # Load package
    package = repository.read('Applications/Sample Apps/BookStore/1.0.0')

    # Load environment
    environment = repository.read('Environments/Testing/TEST01')

    # Start deployment
    depl = deployment.prepareInitial(package.id, environment.id)
    depl2 = deployment.prepareAutoDeployeds(depl)
    depl2.deployedApplication.values['orchestrator'] = 'parallel-by-container'
    task = deployment.createDeployTask(depl2)
    deployit.startTaskAndWait(task.id)

This is an example of a script that undeploys BookStore 1.0.0 from the TEST01 environment:

    taskID = deployment.createUndeployTask('Environments/Testing/TEST01/BookStore').id
    deployit.startTaskAndWait(taskID)

## Extending the CLI

You can extend the XL Deploy CLI by installing extensions that are loaded during CLI startup. Extensions are [Python](http://www.python.org/) scripts, for example with Python class definitions, that will be available in commands or scripts run from the CLI. This feature can be combined with arguments given on the command line when starting up the CLI.

To install a CLI extension:

1. Create a directory called `ext` in the directory from which you will start the CLI. During startup, the current directory will be searched for the existence of the `ext` directory.
2. Copy Python scripts into the `ext` directory.
3. Restart the CLI. During startup, the CLI will search for, load, and execute all scripts with the `py` or `cli` suffix found in the `ext` directory.

**Note:** The order in which scripts from the `ext` directory are executed is not guaranteed.

## Log out of the CLI

In interactive mode, you can log out of the CLI by executing the `quit` command.

In batch mode (when a script is provided), the CLI automatically terminates after finishing the script.

## More information

For more information about using the CLI, refer to:

* [Objects](/xl-deploy/concept/objects-available-in-the-xl-deploy-cli.html) that are available in the CLI
* [Types](/xl-deploy/concept/types-used-in-the-xl-deploy-cli.html) that are available in the CLI
* [Set up roles and permissions](/xl-deploy/how-to/set-up-roles-and-permissions-using-the-cli.html)
* [Working with configuration items](/xl-deploy/how-to/work-with-cis-in-the-cli.html)
* [Execute tasks](/xl-deploy/how-to/execute-tasks-from-the-xl-deploy-cli.html)
* [Export items from or import items into the repository](/xl-deploy/how-to/export-items-from-or-import-items-into-the-repository.html)
