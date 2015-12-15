---
title: Reset the admin password for your XL Release server
subject:
- System administration
categories:
- xl-release
tags:
- system administration
- password
- security
---

If you have forgotten the password for the built-in XL Release `admin` user and you do not have the password for another user with administrative permissions, then you cannot log in to the XL Release server to change the `admin` password.

To resolve this situation, you can use a hotfix that disables all password checks. This allows you to use the REST API and `xl-release-server.conf` file to set a new password for the `admin` user.

Note that:

* You must back up your XL Release instance before resetting the password.
* It is important that you remove the hotfix after you are finished.

To reset the password for the `admin` user in XL Release:

1. Stop the XL Release server.
1. Back up your XL Release instance.
1. Download [this JAR file](sample-scripts/hotfix-disable-password-check.jar) and copy it to the `hotfix` directory of the XL Release server.
1. Start the XL Release server. Note that security is now disabled.
1. Create a temporary file called `user.xml` with the following content. Replace `NEW_PASSWORD` with your desired password. Note that the new password cannot be `admin`.

        <user admin="true">
            <username>admin</username>
            <password>NEW_PASSWORD</password>
        </user>

1. From a command prompt or terminal, execute the following REST API call:

        curl -X PUT -H "content-type:application/XML" "http://IP:PORT/security/user/admin" -d@/tmp/user.xml

    Replace `IP` and `PORT` with the IP address and port number where your XL Release server is running. Replace `/tmp/user.xml` with the path to your XML file.

1. Stop the XL Release server.
1. Open `<XL_RELEASE_HOME>/conf/xl-release-server.conf` and add the new `admin` password as follows:

        admin.password=NEW_PASSWORD

1. Save the file and start the XL Release server. This registers and encrypts the new password.
1. Remove the JAR file from the `hotfix` directory.
1. Restart the XL Release server.
