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

## Client requirements

The following web browsers are supported for the XL Release user interface:

* Chrome
* Firefox
* Internet Explorer 11 or later

**Note:** Internet Explorer Compatibility View is not supported.

## Server requirements

To [install](/xl-release/how-to/install-xl-release.html) the XL Release server, the following prerequisites must be met:

* **XL Release license:** See [XL Release licensing](/xl-release/concept/xl-release-licensing.html)
* **Operating system:** Microsoft Windows or a Unix-family operating system
* **Java SE Development Kit (JDK):**
    * For XL Release 4.8.0 and later: JDK 1.8.0_25 or later

    **Important:** XL Release is not compatible with Java Development Kit 9 (JDK 9).

    * For XL Release 4.7.x and earlier: JDK 7 (Oracle or IBM)

### Server hardware requirements

* **Multicore CPU**
* **RAM:** 4-6 GB of RAM available for the XL Release process. **Note:** Allocating more than 6 GB to the XL Release process does not show significant performance improvements.
* **Hard disk:** XL Release must be installed on a hard drive that is persistent, but not NFS. Hard disk usage depends on configuration of the product. At least 100 GB of storage is recommended for the default installation that runs an embedded database. Use a fast hard drive.

### External systems

* **Database:** By default, XL Release is installed with an embedded database, but it can be configured to use an external relational database. See [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) for details.
* **LDAP:** To connect XL Release to your corporate Active Directory or LDAP server, see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html).

## Reference setup

The server that XebiaLabs uses for reference has:

* Two Quad Intel(R) Xeon(R) CPU E5450 @ 3.00GHz
* 16 GB of RAM
* Two 300 GB 10K SAS 2.5" disks

It is configured as follows:

{:.table .table-bordered}
| Operating system | CentOS Linux 7.2 |
| Installed software | A single XL Release instance running as a service, and monitoring systems |
| XL Release version | 6.1.0 |
| XL Release mode | Standalone, non-clustered |
| XL Release persistence | Embedded Derby, local file system (out-of-the-box settings) |
| Java version | Oracle JDK 1.8.0_74 |

The relevant configuration settings are:

{:.table .table-striped}
| Parameter | Value | Location  | Description |
| -------- |-------- | -------- | ---------------- |
| `wrapper.java.additional.1` | `-Xms1024m -Xmx4096m` | `XL_RELEASE_SERVER_HOME/conf/xlr-wrapper-linux.conf` | Sets the heap size to minimum 1 GB and maximum 4 GB |
| `threads.max` | `125` | `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf` | Sets the maximum number of HTTP threads to 125  |
| `xl.executors.scheduler.maxThreadsCount` | `50` | `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` | Sets the maximum number of threads for asynchronous operations to 50  |
| `xl.repository.jackrabbit.bundleCacheSize` | `256` | `XL_RELEASE_SERVER_HOME/conf/xl-release.conf` | Sets the JCR cache size to 256 MB  |

This configuration supports up to 100 concurrent users running a test set with 200 active releases, 200 templates and 500 completed releases, with a mean response time less then 3 seconds.

**Note:** Other configuration settings may be optimal for your environment (external database, shared filesystem, hot-standby) and load profile (number of concurrent users, release structure).
