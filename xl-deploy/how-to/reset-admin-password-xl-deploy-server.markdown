---
title: Reset the admin password for your XL Deploy server
subject:
- System administration
categories:
- xl-deploy
tags:
- system administration
- password
- security
since:
- XL Deploy 3.9.5
---

[Changing passwords in XL Deploy](/xl-deploy/how-to/changing-passwords-in-xl-deploy.html#change-the-admin-password) describes how to change the password for the built-in `admin` user. However, if you have forgotten the password for the `admin` user and you do not have the password for another user with administrative permissions, then you cannot log in to the XL Deploy server to change the `admin` password.

To resolve this situation, you can use a hotfix that disables all password checks. This allows you to log in as the `admin` user with any password and follow the standard procedure to change it to a known password. You could compare it to rebooting a Unix server in single user mode when you forget the password for the root user.

Note that:

* The hotfix has been tested with Deployit 3.9.5, XL Deploy 4.0.2, XL Deploy 4.5.1, and XL Deploy 5.0.7. It is not supported for XL Deploy 5.1.x or later.
* You must back up your XL Deploy instance before resetting the password.
* It is important that you remove the hotfix after you are finished.

To reset the password for the `admin` user:

1. [Contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) to request the hotfix. They will send you the required file.
1. After you have the hotfix, stop the XL Deploy server.
1. Stop the XL Deploy server.
1. Back up your XL Deploy configuration and repository.
1. If you are using XL Deploy 4.5.x or earlier, copy the hotfix to the `hotfix` directory of the XL Deploy server. If you are using XL Deploy 5.0.x, copy the hotfix to the `hotfix/lib` directory.
1. Start the XL Deploy server.
1. Log in to the XL Deploy interface as the `admin` user with any password.
1. Follow [the standard procedure](/xl-deploy/how-to/changing-passwords-in-xl-deploy.html#change-the-admin-password) to change the password for the `admin` user.
1. Stop the XL Deploy server.
1. Remove the hotfix from the `hotfix` directory.
1. Start the XL Deploy server.
