---
title: Introduction to the XL Deploy Lightweight CLI
categories:
xl-deploy
subject:
Command-line interface
tags:
cli
script
python
lightweight
Deployfile
since:
XL Deploy 7.2.0
---

The Lightweight CLI is a Python-based command-line interface that allows you to connect to an XL Deploy server and generate or apply a  [Deployfile](/xl-deploy/concept/environment-as-code.html).

## Requirements

* The [`pip`](https://pypi.python.org/pypi/pip) tool for installing Python packages
* XL Deploy 7.2.0 or later

## Install the Lightweight CLI

To install the XL Deploy Lightweight CLI, use `pip` to install [xld-py-cli](https://pypi.python.org/pypi/xld-py-cli/).

## Commands and options

The Lightweight CLI supports the following commands:

{:.table .table-striped}
| Command | Description |
| ------| ----------|
| `xld` | Identifies a Lightweight CLI command |
| `apply` | Apply a Deployfile to the XL Deploy repository |
| `generate` | Generate a Deployfile from a directory in the XL Deploy repository |

The Lightweight CLI supports the following options:

{:.table .table-striped}
| Option | Description |
| -----| ----------|
| `--url` | Location of the XL Deploy server (including port number) |
| `--username` | User name to use when authenticate with the server |
| `--password` | Password for authenticating with the server |

### Environment variables

You can store connection data for the Lightweight CLI in the following environment variables:

{:.table .table-striped}
| Option | Description |
| -----| ----------|
| `XL_DEPLOY_URL` | Location of the XL Deploy server (including port number) |
| `XL_DEPLOY_USERNAME` | User name to use when authenticate with the server |
| `XL_DEPLOY_PASSWORD` | Password for authenticating with the server |

If command-line options are specified, they take precedence over environment variable values.

## Apply a Deployfile

To apply a Deployfile:

    xld --url http://xl-deploy.mycompany.com:4516 --username john --password secret01 apply /Users/john/Deployfile

You can only apply one Deployfile at a time.

## Generate a Deployfile

This example shows how to generate a Deployfile from a directory in the XL Deploy repository:

    xld --url http://xl-deploy.mycompany.com:4516 --username john --password secret01 generate /Infrastructure/MyTeam/TestInfra

To generate multiple Deployfiles:

    xld --url http://xl-deploy.mycompany.com:4516 --username john --password secret01 generate /Infrastructure/AWS Environments/Cloud

By default, the output of the `generate` command is written to `stdout`. To redirect the output to a file, use the `>` character:

    xld --url http://xl-deploy.mycompany.com:4516 --username john --password secret01 generate /Infrastructure/AWS Environments/Cloud > Deployfile
