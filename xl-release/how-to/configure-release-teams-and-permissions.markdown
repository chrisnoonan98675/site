---
title: Configure release teams and permissions
categories:
- xl-release
subject:
- Releases
tags:
- release
- properties
- permission
weight: 414
---

In XL Release, a *release team* allows you to group users with the same role, so you can assign tasks in the release to the team. You can also configure template-level and release-level permissions on the team level.

To add or change a release team, open a template or release and select **Teams** from the **Show** menu.

![Release teams](../images/release-team-overview.png)

To add a new team:

1. Click **New team**. A team called _New team_ is added.
1. Click the team name and change it to the desired name.
1. Click **Add a member** next to the team. Type the name of a user or role that you want to add to the team and press ENTER. Repeat this for each user or role that you want to add.

To remove a team, click the **X** next to it.

## Template permissions

Template permissions determine who can do what in a release template. For information about these permissions, refer to [Create a release template](/xl-release/how-to/create-a-release-template.html#template-permissions).

## Release permissions

Release permissions determine who can do what in a release. These permissions are copied from the template when you create a release based on it. This allows you to define teams and permissions for the release before running it.

To set release permissions, open a template or release and select **Permissions** from the **Show** menu. The Permissions page is only available to users who have the *Edit Security* permission on the template or release or who have the *Edit Security* [global permission](/xl-release/how-to/configure-xl-release-permissions.html).

![Release Permissions](../images/release-permissions.png)

The following permissions are available for releases:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Release | Users have view access to this release. It will appear in the Release Overview. In the release details, users have read-only access to the release flow, properties, and activity log. |
| Edit Release | Users can alter the structure of a release by adding and moving tasks and phases. Release properties and teams are editable. |
| Edit Security | Users can edit teams and permissions in a release |
| Start Release | Users can start a planned release. |
| Abort Release | Users can abort an active or planned release. |
| Edit Task | Users can edit individual tasks. |
| Reassign Task | Users can assign tasks to other people. Team assignment is also enabled. |

Click **Save** to save your changes to the release permissions. Changes are not saved automatically. To discard your changes without saving, click **Reset**.

## Predefined teams

XL Release automatically creates the following predefined teams, which you cannot remove:

{:.table .table-striped}
| Team | Description | Permissions |
| ---- | ----------- | ----------- |
| Template Owner | Contains everyone who has owning rights on the template. | Has all template permissions by default.
| Release Admin | Contains the release owner. Members of this team receive extra notifications when, for example, a task fails and the release is halted. | Has all release permissions by default. |
