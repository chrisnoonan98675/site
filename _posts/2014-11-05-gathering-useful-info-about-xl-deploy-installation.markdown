---
title: Gathering useful information about your XL Deploy installation
categories:
- xl-deploy
tags:
- system administration
---

When you run into problems with XL Deploy and you ask for help it, is useful to share details about the installation and configuration of your environment. To help you gather this kind of information, we have written a special command-line interface (CLI) script.

**Note:** This script works with XL Deploy/Deployit 4.0.0 and earlier. In later versions of XL Deploy, this information is partially available in **Help** > **About**.

The script exposes details about:

* XL Deploy version
* Repositories statistics about CI usage, hosts, and deployments
* Installed hotfixes, plugins, and scripts
* Synthetic and server configuration

It does not expose sensitive information about your environment, unless there is sensitive information in the `synthetic.xml` configuration. Always review the output before sharing it with other people.

## Usage

The script can be viewed [here](https://github.com/xebialabs/community-plugins/blob/master/cli-scripts/usageinfo/usageinfo.py) or downloaded directly [here](https://raw.github.com/xebialabs/community-plugins/master/cli-scripts/usageinfo/usageinfo.py).

Run the script on the system where XL Deploy is installed. The script requires Python to be installed on the system.

Install the script by putting it in the `ext` folder of the XL Deploy CLI installation folder.

To run the script, run this command from the root folder of the XL Deploy CLI installation folder:

    bin/cli.sh -expose-proxies -username user -password pass -f usageinfo.py -- -server-home /opt/deployit-3.8.3-server

You must replace the username, password, and path to server installation directory with your own values.

The output of the script will be printed to the standard output of the command line. To be able to share the output, you can write it to a file called `output.log` by appending:

    > output.log
