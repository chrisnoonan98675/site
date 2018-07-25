---
title: Changing passwords in XL Deploy
categories:
xl-deploy
subject:
System administration
tags:
system administration
security
password
weight: 271
---

## Change the encryption key password

Passwords that are stored in the repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `XL_DEPLOY_SERVER_HOME/conf/repository-keystore.jceks`. This keystore file is optionally protected with a password. If a password is set, you need to enter the password when the XL Deploy server starts.

**Note:** `repository-keystore.jceks` is one of two keystore concepts in XL Deploy. This keystore only contains the key used for encryption of passwords in the repository. If you use [HTTPS](/xl-deploy/how-to/install-xl-deploy.html#step-2-configure-secure-communication), XL Deploy will use a second keystore file to store the self-signed certificate.

To change the keystore password, you can use the `keytool` utility that is part of the Java JDK distribution:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

The `keytool` utility will not read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, use [KeyStore Explorer](http://www.keystore-explorer.org/).

## Change the admin user's password

XL Deploy's built-in admin user has administrative permissions. You set the admin password when you use the setup wizard to [install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html). To change the admin password:

1. Ensure that the XL Deploy server is running.
2. Start the XL Deploy command-line interface (CLI) as the admin user or as another user with the _admin_ global permission.
3. Execute the following commands:

        adminUser = security.readUser('admin')
        adminUser.password = 'newpassword'
        security.modifyUser(adminUser)

4. Stop the XL Deploy server.
5. In the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` file, set `admin.password` to the new password. XL Deploy will encrypt this password when it starts.
6. [Start the XL Deploy server](/xl-deploy/how-to/start-xl-deploy.html).
7. Test the credentials by executing the following command in the CLI:

        security.login('admin', 'newpassword')

## Reset the admin user's password

If you have forgotten the password for the built-in admin user and you do not have the password for another user with the _admin_ global permission, then you cannot authenticate with the XL Deploy server to change the admin password. It is strongly recommended that you create at least one additional user with the _admin_ permission to prevent this situation.

Prior to XL Deploy 5.1.0, you can install a hotfix to disable password checks and then use the user interface to set a new password for the admin user. Note that:

* This procedure is not supported for XL Deploy 5.1.0 and later.
* You must back up your XL Deploy instance before resetting the password.
* It is important that you remove the hotfix after you are finished.

To reset the password for the admin user:

1. [Contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) to request the hotfix. They will send you the required file.
1. After you have the hotfix, stop the XL Deploy server.
1. Stop the XL Deploy server.
1. Back up your XL Deploy configuration and repository.
1. If you are using XL Deploy 4.5.x or earlier, copy the hotfix to the `hotfix` directory of the XL Deploy server. If you are using XL Deploy 5.0.x, copy the hotfix to the `hotfix/lib` directory.
1. Start the XL Deploy server.
1. Log in to the XL Deploy interface as the admin user with any password.
1. Follow [the standard procedure](/xl-deploy/how-to/changing-passwords-in-xl-deploy.html#change-the-admin-password) to change the password for the admin user.
1. Stop the XL Deploy server.
1. Remove the hotfix from the `hotfix` directory.
1. Start the XL Deploy server.
