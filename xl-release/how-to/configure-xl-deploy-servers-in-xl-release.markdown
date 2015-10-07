---
title: Configure XL Deploy servers in XL Release
categories:
- xl-release
subject:
- Settings
tags:
- settings
- system administration
- xl deploy
- server
---

Use the **XL Deploy Servers** page to configure connections to XebiaLabs XL Deploy servers. This page is accessible to users with 'admin' permission. It shows a list of currently known servers and allows you to configure them.

![XL Deploy Servers](../images/deployit-servers.png)

The overview shows the XL Deploy server name, as used by the [XL Deploy Task](/xl-release/how-to/create-an-xl-deploy-task.html), followed by the connection URL and the name of the XL Deploy user that is used to make the remote connection.

To add a server, click **New server**. This dialog appears:

![XL Deploy Server Details](../images/deployit-server-details.png)

Enter a symbolic name in the **Server Name** box. In the XL Release application, the XL Deploy Server is referred to by this name.

The connection **URL** is the internet address at which the XL Deploy Server is reachable. It is the same address you use to access the XL Deploy UI.

The **Username** is the login ID of the user in XL Deploy that XL Release will use, followed by the **Password**.

XL Releases uses this user to query XL Deploy for its available applications and environments, to support code completion in the XL Deploy task and variables section.

Click **Test** to test if XL Release can log in to the XL Deploy server with the configured URL and credentials.

It is recommended that you configure a user in XL Deploy that has read-only rights for this. To do deployments, specify a user and password on the XL Deploy task directly. This provides fine-grained access control from XL Release to XL Deploy. If you do not specify a user on the XL Deploy task, then XL Release will use the user configured on the XL Deploy Server to perform deployments, provided that it has deployment rights on the XL Deploy Server.
