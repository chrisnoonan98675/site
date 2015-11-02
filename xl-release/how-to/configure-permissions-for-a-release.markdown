---
title: Configure release permissions
categories:
- xl-release
subject:
- Releases
tags:
- release
- properties
- permission
---

In a release or template, select **Permissions** from the **Show** menu to go to the permissions page, where you specify who can do what on a release. This page is only available to users who have the Edit Security permission, either on the release or as a [global permission](/xl-release/how-to/configure-xl-release-permissions.html).

![Release Permissions](../images/release-permissions.png)

The following permissions apply to a release:

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

**Tip:** The Release Admin team is automatically created when the release is created. It contains the release owner and has all permissions by default.
