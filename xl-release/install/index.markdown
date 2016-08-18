---
title: Install XL Release
---

## Requirements for installing XL Release

### Server requirements

To install the XL Release server, the following prerequisites must be met:

* XL Release license: See XL Release licensing
* Operating system: Microsoft Windows or a Unix-family operating system
* Java SE Development Kit (JDK):
    * For XL Release 4.8.0 and later: JDK 8 (Oracle or IBM)
    * For XL Release 4.7.x and earlier: JDK 7 (Oracle or IBM)
* RAM: At least 2 GB of RAM available for XL Release
* Hard disk space: At least 2 GB of hard disk space to store the XL Release repository (this depends on your usage of XL Release)

Depending on the environment, the following may also be required:

* Database: The repository can optionally be stored in a database; see Configure the XL Release repository in a database
* LDAP: To enable group-based security, an LDAP x.509 compliant registry is required; see Configure LDAP security for XL Release

### Client requirements

The following web browsers are supported for the XL Release user interface:

* Firefox
* Chrome
* Safari
* Internet Explorer 9 or higher

**Note:** Internet Explorer Compatibility View is not supported.

## Installation options

You can install XL Release:

* Using the GUI installer
* Using the command-line installer
* As a service or daemon

## XL Release server directory structure

After the XL Release installation file is extracted, the following directory structure exists in the installation directory:

* `bin`: Contains the server binaries
* `conf`: Contains server configuration files
* `ext`: Contains server Java extensions
* `hotfix`: Contains hotfixes that correct issues with the server software
* `lib`: Contains libraries that the server needs
* `log`: contains server log files

The installation directory is referred to as `XL_RELEASE_SERVER_HOME`.

## Failover configuration

If you store the XL Release repository in a [database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html#using-a-database), you can set up a failover configuration as described in [Configure failover for XL Release](/xl-release/how-to/configure-failover.html).
