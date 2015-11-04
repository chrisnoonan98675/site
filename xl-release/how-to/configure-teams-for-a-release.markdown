---
title: Configure release teams
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- team
- release summary
- permission
- users
---

In a release or template, select **Teams** from the **Show** menu to go to the permissions page, where you specify the teams that are defined for the release.

Teams group people with the same role together. You can assign a task to a team to indicate that someone from the team must pick it up when it becomes active. Release security permissions are also expressed on the team level; see [Configure release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) for more information.

![Release teams](../images/release-team-overview.png)

To add a new team to the release:

1. Click **New team**.
2. Type each team member's name and press ENTER to add the user to the team.

To remove a team, click the **X** next to it.

## Predefined teams

There are two predefined teams that you cannot remove:

{:.table .table-striped}
| Team | Description |
| ---- | ----------- |
| Template Owner | Contains everyone who has owning rights on the template. |
| Release Admin | Contains everyone who is responsible for a running release that is created from the template. These are the people who receive extra notifications when, for example, a task fails and the release is halted. |
