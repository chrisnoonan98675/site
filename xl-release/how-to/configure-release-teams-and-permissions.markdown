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

In XL Release, you use _release teams_ to group users who have the same role and assign them folder-level, template-level, and release-level permissions. You can then assign tasks in the template or release release to teams.

In XL Release 6.0.0 and later, you configure release teams and their permissions on the folder level. In earlier versions of XL Release, you configure template and release permissions separately (folder permissions are not available).

## Configure release teams

To configure release teams in XL Release 6.0.0 and later:

1. Select **Templates** > **Folders** from the top bar.
2. Select a folder.
3. Go to the **Permissions** tab.
4. Click **Add team** to add a new team.
5. Add [roles](/xl-release/how-to/configure-roles.html) by typing in the **Global roles** column. Add individual users by typing in the **Users** column.
6. Click **Save** to save your changes.

![Folder teams](../images/folder-teams.png)

In XL Release 5.0.x and earlier, you add teams by opening the template or release and going to **Show** > **Teams**.

### Predefined teams

XL Release automatically creates the following predefined teams, which you cannot remove:

{:.table .table-striped}
| Team | Description | Permissions |
| ---- | ----------- | ----------- |
| Folder Owner | Contains everyone who has owning rights on the folder (available in XL Release 6.0.0 and later). | Has all folder permissions by default.
| Template Owner | Contains everyone who has owning rights on the template. | Has all template permissions by default.
| Release Admin | Contains the release owner. Members of this team receive extra notifications when, for example, a task fails and the release is halted. | Has all release permissions by default. |

## Configure permissions

You can configure permissions if you have the *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html) or an _Edit Security_ permission on a folder, template, or release.

To configure permissions in XL Release 6.0.0 and later:

1. Select **Templates** > **Folders** from the top bar.
2. Select a folder.
3. Go to the **Permissions** tab.

    If the folder is the child of another folder, the **Inherit permissions from parent folder** option is selected by default. If you want the child folder to have different permissions, clear this option.

4. Under **Folder permissions**, add release teams to each permission.
5. Click **Save** to save your changes.

![Folder permissions](../images/folder-permissions.png)

In XL Release 5.0.x and earlier, you configure permissions by opening the template or release and going to **Show** > **Permissions**. In XL Release 6.0.0 and later, the Permissions screen provides a read-only view of the permissions on the template or release.

### Folder permissions

Folder permissions are available in XL Release 6.0.0 and later. The following permissions apply to folders:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Folder | Users can see the folder in the Folders screen |
| Edit Folder | Users can edit the folder (for example, by renaming it) |
| Edit Folder Security | Users can edit the teams and permissions on a folder |

### Template permissions

The following permissions apply to templates:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Create Release | Users can create a release from the template. |
| View Template | Users can see the template in the template overview. |
| Edit Template | Users can change the template by adding tasks and phases and changing them. |
| Edit Template Security | Users can edit teams and permissions on the template (this permission is named _Edit Security_ prior to XL Release 6.0.0) |
| Edit Triggers | Users can view, edit, and delete triggers on the template (to create a trigger, you also need the *Create Release* permission) |

### Release permissions

The following permissions apply to releases:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Release | Users have view access to this release. It will appear in the Release Overview. In the release details, users have read-only access to the release flow, properties, and activity log. |
| Edit Release | Users can alter the structure of a release by adding and moving tasks and phases. Release properties and teams are editable. |
| Edit Release Security | Users can edit teams and permissions in a release (this permission is named _Edit Security_ prior to XL Release 6.0.0) |
| Start Release | Users can start a planned release. |
| Abort Release | Users can abort an active or planned release. |
| Reassign Task | Users can assign tasks to other people. Team assignment is also enabled. |
| Edit Task | Users can edit individual tasks. |
