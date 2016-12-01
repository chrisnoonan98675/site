---
title: Changing passwords in XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- security
- repository
- password
weight: 500
---

## Change the encryption key password

Passwords in the XL Release repository are encrypted with a secret key. This password encryption key is stored in a keystore file called `XL_RELEASE_SERVER_HOME/conf/repository-keystore.jceks`, which is optionally protected with a password. If a password is set, you must enter it when the XL Release Server starts.

**Note:** `repository-keystore.jceks` is one of the two keystore concepts in XL Release. This keystore only contains the key used for encryption of passwords in the repository. If you use HTTPS, XL Release will use a second keystore file to store the (self-signed) certificate.

To change the keystore password, use the `keytool` utility that is part of the Java JDK distribution:

    keytool -storepasswd -keystore conf/repository-keystore.jceks -storetype jceks

The `keytool` utility will not read or set passwords that are shorter than 6 characters. If you want to change a keystore with an empty or short password, use [KeyStore Explorer](http://www.keystore-explorer.org/).

## Change the admin user's password

In XL Release 6.0.0 and later, you can change the password of the built-in admin user in the same way that you change other user passwords:

1. Log in to XL Release as admin or as another user with the _Admin_ global permission.
2. Go to **User management** > **Users** and click the admin user.
3. Enter a new password and save the change.

In earlier versions of XL Release, you must use the REST API to change the admin user's password:

1. Create a temporary file called `user.xml` with the following content:

        <user admin="true">
            <username>admin</username>
            <password>NEW_PASSWORD</password>
        </user>

     Replace `NEW_PASSWORD` with your desired password. Note that the new password cannot be admin.

1. From a command prompt or terminal, execute the following REST API call:

        curl -uadmin:CURRENT_PASSWORD -X PUT -H "content-type:application/XML" "http://IP:PORT/security/user/admin" -d@/tmp/user.xml

    Replace `CURRENT_PASSWORD` with the admin user's current password, replace `IP` and `PORT` with the IP address and port number where your XL Release server is running, and replace `/tmp/user.xml` with the path to the XML file.

1. Stop the XL Release server.
1. Open `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf` and add the new admin password as follows:

        admin.password=NEW_PASSWORD

1. Save the file.
1. Start the XL Release server. This registers and encrypts the new password.

## Reset the admin user's password

If you have forgotten the password for the built-in admin user and you do not have the password for another user with the _Admin_ global permission, then you cannot authenticate with the XL Release server to change the admin password. It is strongly recommended that you create at least one additional user with the _Admin_ permission to prevent this situation.

Prior to XL Release 6.0.0, you can install a hotfix to disable password checks and then use the REST API to set a new password for the admin user. Note that:

* This procedure is not supported for XL Release 6.0.0 and later.
* You must back up your XL Release instance before resetting the password.
* It is important that you remove the hotfix after you are finished.

To reset the password for the admin user:

1. [Contact the XebiaLabs support team](https://support.xebialabs.com/hc/en-us/requests/new) to request the hotfix. They will send you the required file.
1. After you have the hotfix file, stop the XL Release server.
1. [Back up your XL Release instance.](/xl-release/how-to/back-up-xl-release.html)
1. Copy the hotfix file to the `hotfix` directory of the XL Release server.
1. Start the XL Release server. Note that security is now disabled.
1. Create a temporary file called `user.xml` with the following content:

        <user admin="true">
            <username>admin</username>
            <password>NEW_PASSWORD</password>
        </user>

    Replace `NEW_PASSWORD` with your desired password. Note that the new password cannot be admin.

1. From a command prompt or terminal, execute the following REST API call:

        curl -X PUT -H "content-type:application/XML" "http://IP:PORT/security/user/admin" -d@/tmp/user.xml

    Replace `IP` and `PORT` with the IP address and port number where your XL Release server is running. Replace `/tmp/user.xml` with the path to your XML file.

1. Stop the XL Release server.
1. Open `XL_RELEASE_SERVER_HOME/conf/xl-release-server.conf` and add the new admin password as follows:

        admin.password=NEW_PASSWORD

1. Save the file.
1. Start the XL Release server. This registers and encrypts the new password.
1. Remove the hotfix file from the `hotfix` directory.
1. Restart the XL Release server.
