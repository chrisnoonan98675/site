---
title: Changing passwords in XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- security
---

## Change the `admin` password

XL Deploy's built-in `admin` user has administrative permissions. You set the `admin` password when you use the setup wizard to [install XL Deploy](install-xl-deploy.html). To change the `admin` password:

1. Ensure that the XL Deploy server is running.
2. Start the XL Deploy command-line interface (CLI) as the `admin` user or as another user with administrative permissions.
3. Execute the following commands:

        adminUser = security.readUser('admin')
        adminUser.password = 'newpassword'
        security.modifyUser(adminUser)

4. Stop the XL Deploy server.
5. Set the new `admin` password in the `conf/deployit.conf` file.
6. Restart XL Deploy.
7. Test the credentials in the CLI using the following command:

        security.login('admin', 'newpassword')

## Reset the `admin` password

If you've forgotten the password for the `admin` user and you do not know the password for another user with administrative permissions, you cannot log in to the server to change the `admin` password. For information about resetting the `admin` password in this situation, refer to [Reset the admin password for your XL Deploy server](/xl-deploy/how-to/reset-admin-password-xl-deploy-server.html).

## Change the encryption key password

Passwords that are stored in the repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `conf/repository-keystore.jceks`. This keystore file is optionally protected with a password. If a password is set, you need to enter the password when the XL Deploy Server starts up.

To change the keystore password, you can use the `keytool` utility that is part of the Java JDK distribution. Usage:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

There is one restriction: `keytool` refuses to read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, download the third-party application KeyStore Explorer.

**Note:** `repository-keystore.jceks` is one of the two keystore concepts in XL Deploy. This keystore only contains the key used for encryption of passwords in the repository. If you use HTTPS, XL Deploy will use a second keystore file to store the self-signed certificate.
