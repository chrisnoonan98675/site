---
title: Using the remote completion task
categories:
- xl-release
subject:
-
tags:
- plugin
- remote completion task
since:
- XL Release 8.0.0
---

# Using the remote completion task
The remote completion task allows you to complete or fail a certain task without logging into XL Release from a remote place without access to a company’s network.

## How to use the remote completion task
A remote completion task represents a step in a template or release that must be completed by a person, remotely or from within XL Release.
Like other task types, you can assign remote completion tasks to a single user, a release team or both.

![Remote Completion Task](../images/remote-completion-plugin/remote-completion-task.png)

Remote completion tasks distinct themselves with a purple border.

When a Remote completion task starts, the users assigned to the task receive an email with a request to complete or fail a task remotely. This email contains two buttons **Complete task** and **Fail task**.

![Remote Completion Task Email](../images/remote-completion-plugin/remote-completion-email.png)

Each of the two buttons generates a new remote completion email upon click. This email can be sent to complete or fail a task.

![Remote Completion Task Email Request](../images/remote-completion-plugin/remote-completion-email-request.png)

## Auditing
The activity log shows everything that happens in a release. It provides an audit trail of who did what, and when. To open the activity log, select **Activity logs** from the **Show** menu. For a more detailed explanation of the activity logs, see: [Release activity logs](https://docs.xebialabs.com/xl-release/concept/release-activity-logs.html).
Click **Filter categories** and select **Comments** or **Task edits** to see who and when someone remotely completed or failed a task.

## Limitations

- When a Remote completion task has been started and becomes in progress, the users that are assigned to the task will receive an email with a request to remotely complete or fail a task. 
In case you want to re-assign a new user or team to a task that's in progress and want to make sure that they receive a new email with a remote completion request, you have to fail and restart the task after you reassigned a new user or team.
- The user completing or failing a task remotely has to send the email from the same email address the email was send to.



## Recommended mail clients
The **Complete task** and **Fail task** buttons will generate a new email based on _mailto_ links. Most email clients support this.
We recommend the following email clients:

- MacOS > Mail, Thunderbird
- Windows > Mail, Outlook, Thunderbird
- Android > Outlook, Gmail
- iOS > Mail, Gmail

There are some known issues with several webmail clients that have problems generating an email based on mailto links.