---
title: Connect to XL Deploy from the CLI
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

To connect to the XL Deploy server using the command-line interface (CLI):

1. Ensure that the XL Deploy server is running.
2. Open a terminal window or command prompt and go to the `XLDEPLOY_CLI_HOME/bin` directory (where `XLDEPLOY_CLI_HOME` is the directory where the CLI is installed).
3. Execute the start command:
    * Unix-based operating systems: `./cli.sh`
    * Microsoft Windows: `cli.cmd`
4. Enter your username and password.

The CLI attempts to connect to the server on `localhost`, running on XL Deploy's standard port of `4516`.

## CLI startup options

Start the CLI with the `-h` flag to see the options that you can use during startup.

The options are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-configuration config_directory` | Pass an alternative configuration directory to the CLI. The CLI will look for a `deployit.conf` in this location. The configuration file supports the `cli.username` and `cli.password` options. |
| `-context newcontext` | If provided, the `context` value will be added to the XL Deploy server connection URL. For example, if `newcontext` is given, the CLI will attempt to connect to the XL Deploy server REST API at `http://host:port/newcontext/deployit`. The leading slash and REST API endpoint (`deployit`) will automatically be added if they are omitted from the parameter. **Tip:** If the XL Deploy context root is set to `deployit`, the `-context` value must be `/deployit/deployit`. |
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

One example of using options might be:

    ./cli.sh -username User -password UserPassword -host xl-deploy.local

This will connect the CLI as `User` with password `UserPassword` to the XL Deploy server running on the host `xl-deploy.local` and listening on port `4516`.

## Entering XL Deploy server credentials

To provide the user name and password for accessing the XL Deploy server, you can:

* Enter them interactively in the CLI
* Provide them with the `-username` and `-password` options
* Store them in the `cli.username` and `cli.password` properties in the `DEPLOYIT_CLI_HOME/conf/deployit.conf` file

## Entering passwords on the Windows command line

{% comment %} This section requires the following versions or later: 4.0.4, 4.5.5, 5.0.1 {% endcomment %}

You may use special characters such as `!`, `^`, or `"` in passwords. These characters have special meaning in the Windows command prompt, which means that passing them to the XL Deploy server as-is causes log-in to fail.

To prevent this issue, surround the password with quotation marks (`"`). If the password contains a quotation mark, you must triple it. For example, `My!pass^wo"rd` should be entered as `-password "My!pass^wo"""rd"`.

## Log out of the CLI

In interactive mode, you can log out of the CLI by executing the `quit` command.

In batch mode (when a script is provided), the CLI automatically terminates after finishing the script.
