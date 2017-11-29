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

## Start XL Release in the background

To run the XL Release server as a background process on a Unix system, use:

    nohup bin/run.sh &

Alternatively, in XL Release 5.0.0 and later, you can [install XL Release as a service](/xl-release/how-to/install-xl-release-as-a-service.html) on a Unix-based or on a Microsoft Windows-based system.

If you have installed XL Release as a service, you must ensure that the XL Release server is configured so that it can start without user interaction. For example, the server should not [require a password](/xl-release/how-to/install-xl-release.html#step-4-provide-a-password-for-the-encryption-key) for the encryption key that protects passwords in the repository. Alternatively, you can store the password in the `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf file` as follows:

    repository.keystore.password=MY_PASSWORD

XL Release will encrypt the password when you start the server.

## Server options

Start the server with the `-h` flag to see the options it supports. They are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-repository-keystore-password VAL` | Identifies the password to use to access the repository keystore. If not specified and the repository keystore does require a password, XL Release will prompt you for it. |
| `-reinitialize` | Reinitialize the repository. Used only in conjunction with `-setup`.<br />**Note:** This flag only works for XL Release 7.2 or earlier and if XL Release is running on the filesystem repository. It does not work when you have configured XL Release to run against a database. |
| `-setup` | Runs the XL Release setup wizard. |
| `-setup-defaults VAL` | Specifies a file that contains default values for configuration properties set in the setup wizard.

**Tip:** Any options you want to give the XL Release server when it starts can be specified in the `XL_RELEASE_SERVER_OPTS` environment variable.
