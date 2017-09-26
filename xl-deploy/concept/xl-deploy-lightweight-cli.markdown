---
title: Introduction to the XL Deploy Lightweight CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- python
- lightweight
since:
- XL Deploy 7.2.0
---

The Lightweight CLI is a Python based command line interface that allows you to connect to an XL Deploy server, to generate a Deployfile, or to apply a Deployfile.

## Requirements

* The [pip](https://pypi.python.org/pypi/pip) tool for installing Python packages
* XL Deploy version 7.2.0

## Install the Lightweight CLI

The `xld-py-cli` Lightweight CLI package is available in the XL Deploy product bundle.

To install the lightweight CLI:
1. Create an installation directory such as `/opt/xebialabs/xld-py-cli` or `C:\Program Files\XL Deploy\Python CLI` (referred to as `XL_DEPLOY_PY_CLI_HOME` in this topic).
1. Copy the XL Deploy Lightweight CLI archive to the directory.
1. Extract the archive in the directory.
1. Open a terminal window or command prompt and execute this command:

    $ pip install xld-py-cli

## Sample command to apply a Deployfile

    $ xld --url *URL* --username *USERNAME* --password *PASSWORD* apply *PATH_TO_FILENAME*

## Sample commands to generate a Deployfile

* Deployfile containing multiple directories

    $ xld --url *URL* --username *USERNAME* --password *PASSWORD* generate *PATH_TO_DIRECTORIES*
