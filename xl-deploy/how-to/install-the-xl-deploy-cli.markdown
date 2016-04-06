---
title: Install the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- installation
- system administration
- cli
---

You can install the XL Deploy command-line interface (CLI) on any remote computer and connect to the XL Deploy server.

To install the CLI:

1. Download the XL Deploy CLI archive (ZIP file) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com) (requires customer log-in) or from the link provided when you sign up for a [free trial](https://xebialabs.com/products/xl-deploy/trial/). 
1. Ensure that XL Deploy is running.
1. Log in to the computer where you want to install the XL Deploy CLI.
1. Create an installation directory such as `/opt/xebialabs/xl-deploy-cli` or `C:\Program Files\XL Deploy\CLI`.
1. Copy the XL Deploy CLI archive to the directory.
1. Extract the archive in the directory.
1. Follow the instructions in [Connect to XL Deploy from the CLI](/xl-deploy/how-to/connect-to-xl-deploy-from-the-cli.html).

## Set environment variables

After you install the XL Deploy CLI, it is recommended that you set the `DEPLOYIT_CLI_HOME` environment variable to the root directory where the CLI is installed.

A second environment variable, `DEPLOYIT_CLI_OPTS`, can be used to provide JVM options for the XL Deploy CLI process. For example, to set the initial Java heap size to 512 megabytes of memory and the maximum Java heap size to 2 GB of memory, the environment variable would be set as follows:

* Unix: `export DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`
* Microsoft Windows: `set DEPLOYIT_CLI_OPTIONS="-Xms512m -Xmx2g"`

If `DEPLOYIT_CLI_OPTS` is not set, the CLI startup scripts provide sensible defaults.
