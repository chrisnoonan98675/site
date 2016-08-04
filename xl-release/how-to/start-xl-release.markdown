---
title: Start XL Release
categories:
- xl-release
subject:
- Installation
tags:
- system administration
- installation
- setup
weight: 404
---

To start the XL Release server, open a command prompt or terminal, go to the `XL_RELEASE_SERVER_HOME/bin` directory, and execute the appropriate command:

{:.table .table-striped}
| Operating system | Command |
| ---------------- | ------- |
| Microsoft Windows | `run.cmd` |
| Unix-based systems | `run.sh` |

## Server options

Start the server with the `-h` flag to see the options it supports. They are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-repository-keystore-password VAL` | Identifies the password to use to access the repository keystore. If not specified and the repository keystore does require a password, XL Release will prompt you for it. |
| `-reinitialize` | Reinitialize the repository. Used only in conjunction with `-setup`.<br />**Note:** This flag only works if XL Release is running on the filesystem repository. It does not work when you have configured XL Release to run against a database. |
| `-setup` | Runs the XL Release setup wizard. |
| `-setup-defaults VAL` | Specifies a file that contains default values for configuration properties set in the setup wizard.

**Tip:** Any options you want to give the XL Release server when it starts can be specified in the `XL_RELEASE_SERVER_OPTS` environment variable.

## Start XL Release in the background

To run the XL Release server as a background process on a Unix system, use:

    nohup bin/run.sh &

**Tip:** You can also [Install XL Release as a service](/xl-release/how-to/install-xl-release-as-a-service.html).
