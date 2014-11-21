---
title: Reset the admin password for your XL Deploy server
categories:
- xl-deploy
tags:
- system administration
---

The XL Deploy [System Administration Manual](http://docs.xebialabs.com/releases/latest/xl-deploy/systemadminmanual.html#changing-the-admin-password) describes how to change the password for the built-in admin user.

However, if you've forgotten the password for that user and you don't know the password for another user with the admin permission, you cannot log in to the system to change that password.

To help you get out of this situation, we have developed a hotfix that disables all password checks. This allows you to log in as the admin user with any password and the follow the standard procedure to change it to a known password. You could compare it to rebooting a Unix server in single user mode when you forget the password for the root user.

Please follow the instructions below to reset the password for the admin user in XL Deploy:

1. Stop the XL Deploy server.
1. Back up your XL Deploy configuration and repository.
1. Copy [this JAR file](/sample-scripts/hotfix-disable-password-check.jar) to the `hotfix` directory of the XL Deploy server.
1. Start the XL Deploy server.
1. Log in to the XL Deploy interface as the admin user with any password.
1. Follow [the standard procedure](http://docs.xebialabs.com/releases/latest/xl-deploy/systemadminmanual.html#changing-the-admin-password) to change the password for the admin user.
1. Stop the XL Deploy server.
1. Remove the hotfix.
1. Start the XL Deploy server.

Some important caveats:

* This hotfix has been tested with Deployit 3.9.5, XL Deploy 4.0.2 and XL Deploy 4.5.1.
* Do not forget to back up your XL Deploy configuration and repository.
* Most importantly: Do not forget to remove the hotfix after you are done! It disables all password checks, which means that anyone can access the system.
