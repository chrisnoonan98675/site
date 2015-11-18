---
title: Requirements for installing XL Release
categories:
- xl-release
subject:
- Installation
tags:
- system administration
- installation
- setup
- system requirements
---

## Server requirements

To [install](/xl-release/how-to/install-xl-release.html) the XL Release server, the following prerequisites must be met:

* **Operating system:** Microsoft Windows or a Unix-family operating system
* **Java SE Development Kit (JDK)**: JDK 7 (Oracle or IBM)
* **RAM:** At least 2 GB of RAM available for XL Release
* **Hard disk space:** At least 2 GB of hard disk space to store the XL Release repository (this depends on your usage of XL Release)

Depending on the environment, the following may also be required:

* **Database:** The repository can optionally be stored in a database; see [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html)
* **LDAP:** To enable group-based security, an LDAP x.509 compliant registry is required; see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html)

## Client requirements

The following web browsers are supported for the XL Release GUI:

* Firefox
* Chrome
* Safari
* Internet Explorer 9 or higher

**Note:** Internet Explorer Compatibility View is not supported.
