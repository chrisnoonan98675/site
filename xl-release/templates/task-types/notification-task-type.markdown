---
title: Notification task type
---

The Notification task type allows you to write emails that are sent automatically when a task becomes active. This is an automated task, so it will complete by itself (or fail if the email could not be sent) and XL Release will advance to the subsequent task.

![Notification Task Details](/xl-release/images/notification-task-details.png)

The options for the notification task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| To | A list of email addresses where the message will be sent |
| Subject | The subject of the message |
| Body | The message body, in plain text |

Click a field to edit it. You can use [variables](/xl-release/concept/variables-in-xl-release.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Notification tasks have a gray border.

## Configure an SMTP server

To configure the email server that is used to send notifications, select **Settings** > **SMTP server** from the top menu. The SMTP server page is only available to users who have the *Admin* [global permission](/xl-release/how-to/configure-permissions.html).

![SMTP server](/xl-release/images/smtp-server.png)

**Host** is used to specify the internet address of the mail server, followed by the **Port** it is listening on. Consult your system administrator on the values to use and whether to use **TLS** to secure the connection.

Most SMTP servers require authentication. Use the **Username** and **Password** fields to set the credentials of the user that will connect. The **From Address** is the sender address that is used in the emails. People will receive emails on behalf of this user. You must enter a valid email address, but you can add a name by using the following syntax: `Full name <name@example.com>`. Note that some mail servers will ignore this setting and set the authenticated user as the sender of the emails.

Click **Save** to apply your changes.
