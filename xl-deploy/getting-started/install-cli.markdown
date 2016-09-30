---
title: Install the XL Deploy CLI
---

You can install the XL Deploy command-line interface (CLI) on any remote computer and connect to the XL Deploy server.

To install the CLI and connect to the XL Deploy server:

1. Download the XL Deploy CLI archive (ZIP file) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) (requires customer log-in) or from the link provided when you sign up for a [free trial](https://xebialabs.com/products/xl-deploy/trial/).
1. Ensure that XL Deploy is running.
1. Log in to the computer where you want to install the XL Deploy CLI.
1. Create an installation directory such as `/opt/xebialabs/xl-deploy-cli` or `C:\Program Files\XL Deploy\CLI` (referred to as `XL_DEPLOY_CLI_HOME` in this topic).
1. Copy the XL Deploy CLI archive to the directory.
1. Extract the archive in the directory.
2. Open a terminal window or command prompt and go to the `XL_DEPLOY_CLI_HOME/bin` directory.
3. Execute the start command:
    * Unix-based operating systems: `./cli.sh`
    * Microsoft Windows: `cli.cmd`
4. Enter your user name and password.

    The CLI attempts to connect to the server on `localhost`, running on XL Deploy's standard port of `4516`.

## Set environment variables

After you install the XL Deploy CLI, it is recommended that you set the `DEPLOYIT_CLI_HOME` environment variable to the root directory where the CLI is installed.

A second environment variable, `DEPLOYIT_CLI_OPTS`, can be used to provide Java Virtual Machine (JVM) options for the XL Deploy CLI process. For example, to set the initial Java heap size to 512 MB and the maximum Java heap size to 2 GB, set the environment variable as follows:

* Unix-based operating systems: `export DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`
* Microsoft Windows: `set DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`

If `DEPLOYIT_CLI_OPTS` is not set, the CLI startup scripts provide sensible defaults.

## XL Deploy CLI directory structure

After you install the XL Deploy CLI, it will have the following directory structure:

{:.table}
| Directory | Contains... |
| --------- | ----------- |
| `bin` | CLI binaries |
| `ext` | CLI Python extension scripts |
| `hotfix` | Hotfixes that fix issues with the CLI software |
| `lib` | Required libraries |
| `plugins` | CLI plugins |
