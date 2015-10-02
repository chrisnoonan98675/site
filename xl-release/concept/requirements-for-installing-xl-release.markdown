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
---

## Server requirements

To install the XL Release server, the following requirements must be met:

* **XL Release license:** See [XL Release licensing](/xl-release/concept/xl-release-licensing.html)
* **Operating system:** Microsoft Windows or a Unix-family operating system
* **Java SE Development Kit (JDK)**:
    * For XL Release 4.8.0 and later: JDK 8 (Oracle or IBM)
    * For XL Release 4.7.x. and earlier: JDK 7 (Oracle or IBM)
* **RAM:** At least 2 GB of RAM available for XL Release
* **Hard disk space:** At least 2 GB of hard disk space to store the XL Release repository (this depends on your usage of XL Release)

Depending on the environment, the following may also be required:

* **Database:** The repository can optionally be stored in a database; see [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html)
* **LDAP:** To enable group-based security, an LDAP x.509 compliant registry is required; see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html)

## Client requirements

The following web browsers are supported for the XL Release user interface:

* Internet Explorer 9 or higher
* Firefox
* Chrome
* Safari
