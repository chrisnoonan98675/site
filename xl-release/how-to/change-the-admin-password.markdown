---
title: Change the admin password on your XL Release server
subject:
- System administration
categories:
- xl-release
tags:
- system administration
- password
- security
weight: 501
---

To change the password of the built-in XL Release `admin` user:

1. Create a temporary file called `user.xml` with the following content:

        <user admin="true">
            <username>admin</username>
            <password>NEW_PASSWORD</password>
        </user>

     Replace `NEW_PASSWORD` with your desired password. Note that the new password cannot be `admin`.

1. From a command prompt or terminal, execute the following REST API call:

        curl -uadmin:CURRENT_PASSWORD -X PUT -H "content-type:application/XML" "http://IP:PORT/security/user/admin" -d@/tmp/user.xml

    Replace `CURRENT_PASSWORD` with the `admin` user's current password, replace `IP` and `PORT` with the IP address and port number where your XL Release server is running, and replace `/tmp/user.xml` with the path to the XML file.

1. Stop the XL Release server.
1. Open `<XL_RELEASE_SERVER_HOME>/conf/xl-release-server.conf` and add the new `admin` password as follows:

        admin.password=NEW_PASSWORD

1. Save the file.
1. Start the XL Release server. This registers and encrypts the new password.

**Tip:** If you have forgotten the password for the `admin` user and you do not have the password for another user with administrative permissions, refer to [Reset the admin password for your XL Release server](/xl-release/how-to/reset-admin-password-xl-release-server.html).
