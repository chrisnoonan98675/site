---
title: Start XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
---

Open a terminal window and change to the `XL_RELEASE_SERVER_HOME` directory. To start the XL Release server on a Unix system, enter:

    bin/run.sh

To start the XL Release server on a Window system, enter:

    bin\run.cmd

Start the server with the `-h` flag to see the options that are available:

    Supported options:
     -help                  : Prints this usage message
     -repository-keystore-password VAL : The password to open the repository keystore file, if not given, the server will prompt you.
     -reinitialize          : Reinitialize the repository, only useful with -setup
     -setup                 : (Re-)run the setup 
     -setup-defaults VAL    : Use the given file for defaults during setup run.sh arguments...

The command line options are:

* `-repository-keystore-password VAL`: Identifies the password to use to access the repository keystore. If not specified and the repository keystore does require a password, XL Release will prompt you for it.
* `-reinitialize`: Reinitialize the repository. Used only in conjunction with `-setup`.
	**Note:** This flag only works if XL Release is running on the filesystem repository. It does not work when you have configured XL Release to run against a database.
* `-setup`: Runs the XL Release Setup Wizard.
* `-setup-defaults VAL`: Specifies a file that contains default values for configuration properties set in the Setup Wizard.

**Tip:** Any options you want to give the XL Release server when it starts can be specified in the `XL_RELEASE_SERVER_OPTS` environment variable.
