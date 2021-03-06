---
title: Create a Notification task
categories:
xl-release
subject:
Task types
tags:
task
notification
email
---

The Notification task type allows you to write emails that are sent automatically when a task becomes active. This is an automated task, so it will complete by itself (or fail if the email could not be sent) and XL Release will advance to the subsequent task.

![Notification Task Details](../images/notification-task-details.png)

The options for the notification task are:

{:.table .table-striped}
| Option | Description |
| -----| ----------|
| To | A list of email addresses where the message will be sent |
| Subject | The subject of the message |
| Body | The message body, in plain text |
| CC | A list of email addresses that receive the message as CC | 
| BCC | A list of email addresses that receive the message as BCC |
| Reply to | A email address of the reply message recipient |
| Mail priority | The priority of the email |
| From | The email address of the message sender. It overrides the default SMTP server sender |

Click a field to edit it. You can use [variables](/xl-release/concept/variables-in-xl-release.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Notification tasks have a gray border.
