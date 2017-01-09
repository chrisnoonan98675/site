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
weight: 401
---

## Server requirements

To [install](/xl-release/how-to/install-xl-release.html) the XL Release server, the following prerequisites must be met:

* **XL Release license:** See [XL Release licensing](/xl-release/concept/xl-release-licensing.html)
* **Operating system:** Microsoft Windows or a Unix-family operating system
* **Java SE Development Kit (JDK)**:
    * For XL Release 4.8.0 and later: JDK 8 (Oracle or IBM)
    * For XL Release 4.7.x and earlier: JDK 7 (Oracle or IBM)
* **RAM:** At least 2 GB of RAM available for XL Release
* **Hard disk space:** At least 2 GB of hard disk space to store the XL Release repository (this depends on your usage of XL Release)

Depending on the environment, the following may also be required:

* **Database:** The repository can optionally be stored in a database; see [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html)
* **LDAP:** To enable group-based security, an LDAP x.509 compliant registry is required; see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html)

### Hard disk space requirements

While it is possible to store the repository in an [external database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html), it should not be stored on NFS. Also, XL Release always requires that the disk space for the server be persistent. This is important for several reasons:

* Lucene indexes are stored in the repository directory; if the disk space is not persistent, these indexes will be rebuilt each time the server starts, which is very time-consuming
* Configuration files such as `xl-release.conf` and `deployit-defaults.properties` are updated by the running system
* Log files are also updated by the running system (unless configured otherwise)

## Client requirements

The following web browsers are supported for the XL Release user interface:

* Firefox
* Chrome
* Safari
* Internet Explorer 11 or later

**Note:** Internet Explorer Compatibility View is not supported.
