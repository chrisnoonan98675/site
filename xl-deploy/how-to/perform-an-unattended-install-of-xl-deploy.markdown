---
title: Perform an unattended install of XL Deploy
subject:
- Installation
categories:
- xl-deploy
tags:
- system administration
- getting started
---

It is possible to do an unattended install of XL Deploy, for example, using Puppet. The server and command line interface (CLI) are distributed as separate ZIP archives. Installation for the CLI is as simple as extracting the ZIP file.

The server needs some more configuration. When the server is started, it looks for a file called `deployit.conf` in the `conf` directory in its home directory. If this does not exist, it enters an interactive setup wizard to create it.

An unattended install can be performed by including a `deployit.conf` file in the package and copying it to the `conf` directory after the installation ZIP file is extracted. You could do the installation manually once to obtain a `deployit.conf` file. After this installation, XL Deploy server can be started without entering the setup wizard.

If you are using a XL Deploy repository on disk, you do have to create the empty repository directory manually as this is normally done by the setup wizard.

Another option is to run through the setup wizard automatically, accepting all the defaults. This Unix command will do so:

    yes yes | bin/server.sh -setup
