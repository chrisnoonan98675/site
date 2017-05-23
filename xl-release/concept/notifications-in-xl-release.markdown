---
title: Notifications in XL Release
categories:
- xl-release
subject:
- Notifications
tags:
- notification
- email
---

XL Release sends emails when certain events happen in a release. The default events are:

## Task level events

* **Active task assigned**: An active task has been assigned to somebody else. The new assignee receives an email telling them they are responsible for completion of this task. The task team members also receive an email notification.

* **Active task unassigned**: The task owner and the task team receive an email telling them that the task must be assign to someone.

* **Comment added**: When a user adds a comment to a task, an email is sent to the task owner and the task team members of the team assigned to the task. The author of the comment will not be notified.

* **Manual task started**: XL Release started a task which is now in progress. XL Release sends a notification to the task owner.
	* If the task does not have an assignee but it has been assigned to a team, the task team members receive an email.
	* If there is no owner or team assigned, the release admin team receives an email that a task is in progress but no one is responsible for it. In the case of automated tasks, messages are sent to individual owners or team owners so they can track automated procedures they are responsible for. However, a warning message for unassigned tasks is not sent to release owners.

* **Manual task started without assignee**: The release admin team receives and email that a manual task has started and there is no assigned task owner.

* **Task due soon**: The release admin and the task owner receive and email when a task is approaching the set due date. The notification event triggers when only 25% of the initial task duration time remains.

* **Task failed**: When a task fails, the task owner and the task team are notified so they can take action. A manual task fails when its owner indicates that he or she cannot proceed and clicks **Fail**. Automated tasks may fail when they cannot be executed correctly. If this results in a release failure, the release admin will receive an email.

* **Task flagged**: The task owner and release admin receive an email when a user adds a flag status message to a task. If the task does not have an assigned owner, the task team receives the email notification. The author of the flag status message will not be notified.

* **Task overdue**: The task owner and release admin receive an email that the task scheduled end date has passed and the task is overdue. If the task does not have an assigned owner, the task team receives the email notification.

* **Task waiting for input**: The task owner and the task team receive an email that the task is waiting for input.

## Release level events

* **Release started**: The release admin team is notified when a release has started.

* **Release completed**: The release admin team is notified when a release is completed.

* **Release failed**: The release admin team is notified when a release has failed.

* **Release failing**: The release admin team is notified when a release is failing.

* **Release aborted**: The release admin team is notified when a release was aborted.

* **Release flagged**: The release admin team is notified when a user adds a flag status message to a task or the release to indicate that attention is needed or that the release is at risk. The author of the flag status message will not be notified.

For more information about how to change the default notification settings, refer to [Configure notification settings](/xl-release/how-to/configure-notification-settings.html).

See [Configure SMTP servers in XL Release](/xl-release/how-to/configure-smtp-server.html) for information about configuring the email server and sender for these messages.
