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

In XL Release, a release team groups users with the same role. You can assign a task to a team to indicate that someone from that team must perform the task when it becomes active. Also, you assign [release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) on the team level.

To edit a release team, open the template or release and select **Teams** from the **Show** menu.

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
