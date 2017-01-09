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

## Minimal server requirements

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

## Recommended server requirements

If you expect to have 20 or fewer concurrent active releases and 25 or fewer concurrent users, the following specifications are sufficient:

* A recent server with a multicore CPU
* 2 GB of RAM allocated for XL Release
* A hard drive that is 7200 RPM or faster

If you expect that your system will have a larger number of concurrent releases, it is recommended that you run XL Release on a server with a multicore CPU and between 4 GB and 6 GB of allocated heap memory. This should decrease the time spent performing garbage collection and therefore speed up the response time for XL Release users.

**Note:** Increasing the heap size over 6 GB does not show significant performance improvements.

### Configuration used for testing

The server that XebiaLabs uses for reference testing has:

* Two Quad Intel(R) Xeon(R) CPU E5450 @ 3.00GHz
* 16 GB of RAM
* Two 300GB 10K SAS 2.5" disks

The server runs CentOS Linux 7.2 with monitoring systems and a single XL Release instance running as a service. It is configured as follows:

{:.table .table-striped}
| XL Release version | 6.1.0 |
| XL Release mode | Standalone, non-clustered |
| XL Release persistence | Embedded Derby, local file system (out-of-the-box settings) |
| Java version | Oracle JDK 1.8.0_74 |

The relevant configuration settings are:

{:.table .table-striped}
| Parameter | Value | Location  | Description |
| -------- |-------- | -------- | ---------------- |
| `wrapper.java.additional.1` | `-Xms1024m -Xmx4096m` | `conf/xlr-wrapper-linux.conf` | Sets the heap size to minimum 1 GB and maximum 4 GB |
| `threads.max` | `125` | `conf/xl-release-server.conf` | Sets the maximum number of HTTP threads to 125  |
| `xl.executors.scheduler.maxThreadsCount` | `50` | `conf/xl-release.conf` | Sets the maximum number of threads for asynchronous operations to 50  |
| `xl.repository.jackrabbit.bundleCacheSize` | `256` | `conf/xl-release.conf` | Sets the JCR cache size to 256 MB  |

This configuration supports up to 100 concurrent users running a test set with 200 active releases, 200 templates and 500 completed releases, with a mean response time less then 3 seconds.

**Note:** The configuration settings above are optimized for the specific installation and load profile exercised by XebiaLabs' stress tests. Other configuration settings may be optimal for your environment (external database, shared filesystem, hot-standby, and so on) and load profile (number of concurrent users, release structure, and so on).

## Client requirements

The following web browsers are supported for the XL Release user interface:

* Firefox
* Chrome
* Safari
* Internet Explorer 11 or later

**Note:** Internet Explorer Compatibility View is not supported.
