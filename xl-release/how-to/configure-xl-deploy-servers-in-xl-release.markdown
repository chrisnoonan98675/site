---
title: Configure XL Deploy servers
categories:
- xl-release
subject:
- Settings
tags:
- settings
- system administration
- xl deploy
---

To configure connections between XL Release and [XL Deploy](/xl-deploy/) servers, select **Settings** > **XL Deploy servers** from the top menu. The XL Deploy servers page is only available to users with the *Admin* permission. 

![XL Deploy Servers](../images/deployit-servers.png)

To add a server:

1. Click **New server**.
2. In the **Server Name** box, enter a name for the server. This is how the server will be identified in XL Release.
3. In the **URL** box, enter the address at which the server is reachable. This is the same address you use to access the XL Deploy user interface.
4. In the **Username** and **Password** boxes, enter the credentials of the XL Deploy user that XL Release will use to log in. XL Releases uses this user to query XL Deploy for its available applications and environments, to support code completion in the XL Deploy task and variables section.

    It is recommended that you create an XL Deploy user with read-only rights for XL Release to use. To perform deployments, specify a user and password directly in the XL Deploy task. This provides fine-grained access control from XL Release to XL Deploy. If you do not specify a user in the XL Deploy task, then XL Release will use the user configured on the XL Deploy server to perform deployments (provided that user has deployment rights in XL Deploy).

5. Click **Test** to test if XL Release can log in to the XL Deploy server with the configured address and credentials.
