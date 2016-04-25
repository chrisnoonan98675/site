---
title: Configure SMTP server
categories:
- xl-release
subject:
- Settings
tags:
- settings
- system administration
- email
- smtp
- notification
---

XL Release sends [notifications](/xl-release/concept/notifications-in-xl-release.html) to users of the system by email. To configure the email server that is used to send notifications, select **Settings** > **SMTP server** from the top menu. The SMTP server page is only available to users with the [*Admin*](/xl-release/how-to/configure-permissions.html) permission.

![SMTP server](../images/smtp-server.png)

**Host** is used to specify the internet address of the mail server, followed by the **Port** it is listening on. Consult your system administrator on the values to use and whether to use **TLS** to secure the connection.

Most SMTP servers require authentication. Use the **Username** and **Password** fields to set the credentials of the user that will connect. The **From Address** is the sender address that is used in the emails. People will receive emails on behalf of this user. You must enter a valid email address, but you can add a name to by using the following syntax: `Full name <name@example.com>`. Note that some mail servers will ignore this setting and set the authenticated user as the sender of the emails.

Click **Save** to apply your changes.
