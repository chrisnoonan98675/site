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

XL Release sends emails when certain events happen in a release. The emails are: 

* **Task assignment**: An active task has been assigned to somebody else. The new assignee receives a message telling them they are responsible for completion of this task.

* **Task failed**: When a task fails, the release owner is notified so they can take action. A manual task fails when its owner indicates that he or she cannot proceed and clicks **Fail**. Automated tasks may fail when they cannot be executed correctly. The release owner must then resolve the issue.

* **Task started**: XL Release started a task which is now in progress. XL Release sends a notification to the task owner.
	* If the task does not have an assignee but it has been assigned to a team, all team members receive a message.
	* If there is no owner or team assigned, the release owner receives a warning message that a task is in progress but no one is responsible for it. In the case of automated tasks, messages are sent to individual owners or team owners so they can track automated procedures they are responsible for. However, a warning message for unassigned tasks is not sent to release owners.

* **Comment added**: When a user adds a comment to a task, a message is sent to the task owner and all team members of the team assigned to the task.

* **Release flagged**: The release owner is notified when a user adds a flag status message to a task or the release to indicate that attention is needed or that the release is at risk.

See [Configure SMTP servers in XL Release](/xl-release/how-to/configure-smtp-server.html) for information about configuring the email server and sender for these messages.
