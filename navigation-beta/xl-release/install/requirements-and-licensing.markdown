---
no_index: true
title: Requirements and licensing
---

## Requirements for installing XL Release

### Client requirements

The following web browsers are supported for the XL Release user interface:

* Chrome
* Firefox
* Internet Explorer 11 or later

**Note:** Internet Explorer Compatibility View is not supported.

### Server requirements

To [install](/xl-release/how-to/install-xl-release.html) the XL Release server, the following prerequisites must be met:

* **XL Release license:** See [XL Release licensing](/xl-release/concept/xl-release-licensing.html)
* **Operating system:** Microsoft Windows or a Unix-family operating system
* **Java SE Development Kit (JDK):**
    * For XL Release 4.8.0 and later: JDK 8 (Oracle or IBM)
    * For XL Release 4.7.x and earlier: JDK 7 (Oracle or IBM)

#### Server hardware requirements

* **Multicore CPU**
* **RAM:** 4-6 GB of RAM available for the XL Release process. **Note:** Allocating more than 6 GB to the XL Release process does not show significant performance improvements.
* **Hard disk:** XL Release must be installed on a hard drive that is persistent, but not NFS. Hard disk usage depends on configuration of the product. At least 100 GB of storage is recommended for the default installation that runs an embedded database. Use a fast hard drive.

#### External systems

* **Database:** By default, XL Release is installed with an embedded database, but it can be configured to use an external relational database. See [Configure the XL Release repository in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html) for details.
* **LDAP:** To connect XL Release to your corporate Active Directory or LDAP server, see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html).

### Reference setup

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

## XL Release licensing

If you have an Enterprise Edition of XL Release, you can download your license file at the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/) (requires enterprise customer log-in). If you have a Trial Edition of XL Release, you will receive a license key by email.

### License types

When you log in to the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), you will see all of your current licenses. There are several versions of the XL Release license:

* **V1:** Supported for XL Release 4.0.10 and earlier
* **V2:** Supported for XL Release 4.0.11 through 4.7.1
* **V3:** Required for XL Release 4.7.2 and later

You can also find this information in the `README` file located with your licenses on the XebiaLabs Software Distribution site.

**Tip:** To check the version of a license (`.lic`) file, open it in a text editor.

### License installation

For information about installing the license, refer to [Install XL Release](/xl-release/how-to/install-xl-release.html#install-the-license).

### License validation

XL Release checks the validity of your license when you start the XL Release server and at certain times while the server is running.

The server will not start if a valid license is not installed.

### License extension and renewal

To renew an Enterprise Edition license or extend a Trial Edition license, [contact XebiaLabs](https://xebialabs.com/contact).

To renew your license using a license file that you download from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), replace the license file in the `XL_RELEASE_SERVER_HOME/conf` directory with the new file.

To renew your license using a license key, go to **Help** > **About** in XL Release, click **Renew license**, and enter the new license key.
