---
title: Requirements and licensing
---

## Server requirements

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

## Client requirements

The following web browsers are supported for the XL Release user interface:

* Firefox
* Chrome
* Safari
* Internet Explorer 9 or higher

**Note:** Internet Explorer Compatibility View is not supported.

## Licensing

If you have an Enterprise Edition of XL Release, you can download your license file at the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/) (requires enterprise customer log-in). If you have a Trial Edition of XL Release, you will receive a license key by email.

## License types

When you log in to the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), you will see all of your current licenses. There are several versions of the XL Release license:

* **V1:** Supported for XL Release 4.0.10 and earlier
* **V2:** Supported for XL Release 4.0.11 through 4.7.1
* **V3:** Required for XL Release 4.7.2 and later

You can also find this information in the `README` file located with your licenses on the XebiaLabs Software Distribution site.

**Tip:** To check the version of a license (`.lic`) file, open it in a text editor.

## License installation

For information about installing the license, refer to [Install XL Release](/xl-release/how-to/install-xl-release.html#install-the-license).

### License validation

XL Release checks the validity of your license when you start the XL Release server and at certain times while the server is running.

The server will not start if a valid license is not installed.

### License extension and renewal

To renew an Enterprise Edition license or extend a Trial Edition license, [contact XebiaLabs](https://xebialabs.com/contact).

To renew your license using a license file that you download from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), replace the license file in the `XL_RELEASE_HOME/conf` directory with the new file.

To renew your license using a license key, go to **Help** > **About** in XL Release, click **Renew license**, and enter the new license key.
