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

* **Active task assigned/reassigned**: An active task has been assigned to somebody else. The new assignee receives an email telling them they are responsible for completion of this task. The task team is also notified of this change.

* **Active task unassigned**: The task team and release admin team receive an email telling them that the task must be assign to someone.

* **Manual task started**: XL Release started a task which is now in progress. XL Release sends a notification to the task owner.
	* If the task does not have an assignee but it has been assigned to a team, the task team members receive an email.
	* If there is no owner or team assigned, the release admin team receives an email that a task is in progress but no one is responsible for it. In the case of automated tasks, messages are sent to individual owners or team owners so they can track automated procedures they are responsible for. However, a warning message for unassigned tasks is not sent to release owners.

* **Task failed (auto or manual)**: When a task fails, the release admin team is notified so they can take action. A manual task fails when its owner indicates that he or she cannot proceed and clicks **Fail**. Automated tasks may fail when they cannot be executed correctly. The release admin team must then resolve the issue.

* **Task overdue**: The task owner and release admin receive an email that the task scheduled end date has passed and the task is overdue. If the task does not have an assigned owner, the task team receives the email notification.

* **Comment added**: When a user adds a comment to a task, an email is sent to the task owner, the task team members of the team assigned to the task, and to the release admin team.

* **Task waiting for input**: The task owner receives an email that the task is waiting for input. If the task does not have an assigned owner, the task team receives the email notification.

* **Task flagged**: The task owner and release admin receive an email when a user adds a flag status message to a task. If the task does not have an assigned owner, the task team receives the email notification.

* **Manual task started without owner**: The release admin team receives and email that a manual task has started and there is no assigned task owner.

* **Release started**: The release admin team is notified when a release has started.

* **Release completed**: The release admin team is notified when a release is completed.

* **Release failed**: The release admin team is notified when a release has failed.

* **Release failing**: The release admin team is notified when a release is failing.

* **Release aborted**: The release admin team is notified when a release was aborted.

* **Release flagged**: The release admin team is notified when a user adds a flag status message to a task or the release to indicate that attention is needed or that the release is at risk.

For more information about how to change the default

See [Configure SMTP servers in XL Release](/xl-release/how-to/configure-smtp-server.html) for information about configuring the email server and sender for these messages.
