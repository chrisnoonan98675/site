---
title: Using the remote completion task
categories:
- xl-release
subject:
- Tasks
tags:
- plugin
- remote completion task
since:
- XL Release 8.0.0
---

With the remote completion task, you can complete or fail a specific task from a remote location without access to the company network and without logging in to XL Release.

## Set up the remote completion task

A remote completion task represents a step in a template or a release that must be completed by a user remotely or from XL Release.
Like other task types, you can assign remote completion tasks to a single user, a release team, or both.

![Remote Completion Task](../images/remote-completion-plugin/remote-completion-task.png)

The visual difference between remote completion tasks and other tasks is the purple border.

When a remote completion task starts, the users assigned to the task receive an email with a request to complete or fail a task remotely. This email contains two buttons: **Complete task** and **Fail task**.

![Remote Completion Task Email](../images/remote-completion-plugin/remote-completion-email.png)

When you click any of the two buttons, a new remote completion email is generated. This email can be sent to complete or fail a task.

![Remote Completion Task Email Request](../images/remote-completion-plugin/remote-completion-email-request.png)

## Auditing information

The activity log shows all the events that take place in a release. This provides an audit trail of every action and the user responsible for it. To open the activity log, select **Activity logs** from the **Show** menu. For a more detailed explanation of the activity logs, see: [Release activity logs](/xl-release/concept/release-activity-logs.html).
To see which user and time when that user remotely completed or failed a task, click **Filter categories** and select **Comments** or **Task edits**.

## Limitations

* When a remote completion task was started and is currently in progress, the users that are assigned to the task receive an email with a request to remotely complete or fail a task.
If you want to reassign a different user or team to a task that is in progress and you want to make sure that they receive a new email with a remote completion request, you must fail and restart the task after you reassigned the new user or team.
* The user completing or failing a task remotely must send the email from the same email address the email was send to.

## Recommended mail clients

The **Complete task** and **Fail task** buttons generate a new email based on the _mailto_ links. The majority of email clients support this setting.
These are the recommended email clients:

* For MacOS: Mail, Thunderbird
* For Windows: Mail, Outlook, Thunderbird
* For Android: Outlook, Gmail
* For iOS: Mail, Gmail

**Note:** There are known issues with several webmail clients that have problems generating an email based on mailto links.
