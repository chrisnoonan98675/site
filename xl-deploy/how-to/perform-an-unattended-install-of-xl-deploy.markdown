---
title: Perform an unattended install of XL Deploy
subject:
- Installation
categories:
- xl-deploy
tags:
- system administration
- installation
---

It is possible to perform an unattended install of the XL Deploy server, for example, using Puppet.

When the server is started, it looks for a file called `deployit.conf` in the `conf` directory in its home directory. If this file does not exist, the server starts an interactive setup wizard to create it.

You can perform an unattended install by including a `deployit.conf` file in the package and copying it to the `conf` directory after the installation ZIP file is extracted. You could do the installation manually once to obtain a `deployit.conf` file. After this installation, the XL Deploy server can be started without entering the setup wizard.

**Note:** If you are using a XL Deploy repository on disk, you do have to create the empty repository directory manually, as this is normally done by the setup wizard.

Another option is to run through the setup wizard automatically, accepting all the defaults. This Unix command will do so:

    yes yes | bin/server.sh -setup
