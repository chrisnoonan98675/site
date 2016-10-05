---
title: XL Deploy licensing
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- license
- installation
- setup
weight: 101
---

If you have an Enterprise Edition of XL Deploy, you can download your license file at the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/) (requires enterprise customer log-in). If you have a Trial Edition of XL Deploy, you will receive a license key by email.

## License types

When you log in to the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), you will see all of your current licenses. There are several versions of the XL Deploy license:

* **V1:** Required for XL Deploy 4.0.x and earlier, XL Deploy 4.5.1, and XL Deploy 4.5.2
* **V2:** Required for XL Deploy 4.5.3 and later
* **V3:** Required for XL Deploy 5.0.0 and later

You can also find this information in the `README` file located with your licenses on the XebiaLabs Software Distribution site.

**Tip:** To check the version of a license (`.lic`) file, open it in a text editor.

## License installation

For information about installing the license, refer to [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html#install-the-license).

## License validation

XL Deploy checks the validity of your license when you start the XL Deploy server and at certain times while the server is running.

The server will not start if a valid license is not installed.

If XL Deploy finds a license violation while the server is running, the server will not stop; however, some requests will be denied. For example:

* If the license usage time has expired, the server will deny all requests.
* If the license limits the number of configuration items (CIs) that you can create, and that limit has been reached for a CI type, the server will not allow you to create new CIs of that type.

**Note:** After the server is stopped, it might not start again (for example, if the license expiry date has been reached).

### Licensed number of configuration items

If your license limits the number of CIs that you can create, XL Deploy validates it as follows:

* You cannot create more instances of a CI type than your license allows. Note that if you delete instances of a CI type, you can create new instances of that type.
* If a CI is a subtype of another type (its *supertype*), the instances of the subtype CI count toward the limit on the supertype.
* You can always create instances of CI types that are not limited by your license.

## License extension and renewal

To renew an Enterprise Edition license or extend a Trial Edition license, [contact XebiaLabs](https://xebialabs.com/contact).

To renew your license using a license file that you download from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), replace the license file in the `XL_DEPLOY_SERVER_HOME/conf` directory with the new file.

To renew your license using a license key, click the gear icon in XL Deploy, select **Renew license**, and enter the new license key.
