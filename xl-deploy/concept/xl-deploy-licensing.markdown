---
title: XL Deploy licensing
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- license
---

If you are using XL Deploy Enterprise Edition, you can download your license at [https://dist.xebialabs.com](https://dist.xebialabs.com/).

**Important:** XL Deploy 5.0.0 or later requires a new license. If you are upgrading from an earlier version of XL Deploy, be sure to download and install your new license.

XL Deploy checks the validity of your license when you start the XL Deploy server and at certain times while the server is running.

The server will not start if a valid license is not installed.

If XL Deploy finds a license violation while the server is running, the server will not stop; however, some requests will be denied. For example:

* If the license usage time has expired, the server will deny all requests.
* If the license limits the number of configuration items (CIs) that you can create and that limit has been reached for a CI type, the server will not allow you to create new CIs of that type.

**Note:** After the server is stopped, it might not start again (for example, if the license expiry date has been reached).

## Licensed number of configuration items

If your license limits the number of configuration items (CIs) that you can create, XL Deploy validates it as follows:

* You cannot create more instances of a CI type than your license allows. Note that if you delete instances of a CI type, you can create new instances of that type.
* If a CI is a subtype of another type (its *supertype*), the instances of the subtype CI count toward the limit on the supertype. 
* You can always create instances of CI types that are not limited by your license.

## Checking license usage

Your license may pose restrictions one or more CI types, disallowing you to create more than the given number of CIs of that type. To see how many CIs you have created, click the arrow next to the **Help** menu, select **About**, and go to the **License** tab.
