---
title: Changing passwords in XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- security
- password
weight: 271
---

## Change the `admin` password

XL Deploy's built-in `admin` user has administrative permissions. You set the `admin` password when you use the setup wizard to [install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html). To change the `admin` password:

1. Ensure that the XL Deploy server is running.
2. Start the XL Deploy command-line interface (CLI) as the `admin` user or as another user with administrative permissions.
3. Execute the following commands:

        adminUser = security.readUser('admin')
        adminUser.password = 'newpassword'
        security.modifyUser(adminUser)

4. Stop the XL Deploy server.
5. In the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf` file, set `admin.password` to the new password. XL Deploy will encrypt this password when it starts.
6. [Start the XL Deploy server](/xl-deploy/how-to/start-xl-deploy.html).
7. Test the credentials by executing the following command in the CLI:

        security.login('admin', 'newpassword')

## Reset the `admin` password

If you've forgotten the password for the `admin` user and you do not know the password for another user with administrative permissions, you cannot log in to the server to change the `admin` password. For information about resetting the `admin` password in this situation, refer to [Reset the admin password for your XL Deploy server](/xl-deploy/how-to/reset-admin-password-xl-deploy-server.html).

## Change the encryption key password

Passwords that are stored in the repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `XL_DEPLOY_SERVER_HOME/conf/repository-keystore.jceks`. This keystore file is optionally protected with a password. If a password is set, you need to enter the password when the XL Deploy server starts up.

To change the keystore password, you can use the `keytool` utility that is part of the Java JDK distribution:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

There is one restriction: `keytool` refuses to read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, use the third-party application [KeyStore Explorer](http://www.keystore-explorer.org/).

**Note:** `repository-keystore.jceks` is one of two keystore concepts in XL Deploy. This keystore only contains the key used for encryption of passwords in the repository. If you use [HTTPS](/xl-deploy/how-to/install-xl-deploy.html#step-2-configure-secure-communication), XL Deploy will use a second keystore file to store the self-signed certificate.
