---
title: Licensing in XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- license
- ci
- installation
- upgrade
---

## License types

When you log in to the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/), you will see all of your current licenses. There are three versions of the XL Deploy license:

* V1: Required for XL Deploy 4.0.x and earlier
* V2: Required for XL Deploy 4.5.x
* V3: Required for XL Deploy 5.0.0 and later

To check the version of a license, open it in a text editor. The version is identified in the license file.

## License validation

XL Deploy checks the validity of your license when you start the server and at certain times while the server is running.

The server will not start if a valid license is not installed.

If XL Deploy finds a license violation while the server is running, the server will not stop; however, some requests will be denied. For example:

* If the license usage time has expired, the server will deny all requests.
* If the license limits the number of configuration items (CIs) that you can create, and that limit has been reached for a CI type, the server will not allow you to create new CIs of that type.

**Note:** After the server is stopped, it might not start again (for example, if the license expiry date has been reached).

## Licensed number of configuration items

If your license limits the number of CIs that you can create, XL Deploy validates it as follows:

* You cannot create more instances of a CI type than your license allows. Note that if you delete instances of a CI type, you can create new instances of that type.
* If a CI is a subtype of another type (its *supertype*), the instances of the subtype CI count toward the limit on the supertype. 
* You can always create instances of CI types that are not limited by your license.

To see how many CI types you have created, go to **Administration** > **About** > **License** in the XL Deploy user interface.
