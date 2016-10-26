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

In XL Release, you use **teams** to group users who have the same role.  You can assign permissions to teams on the folder-level, template-level, and release-level. When designing a release flow of a template or a release, you can assign tasks to teams. Team members will then be responsible for completing the task and will get notified when the task starts.


## Teams and Permissions in folders

With XL Release 6.0.0 folders were introduced. Now templates and releases may live either inside some folder, or not in any folder at all. 

In the latter case, Teams and Permissions are set directly on templates and releases, like previous versions of XL Release.

In XL Release 6.0.0 and later, templates and releases that live in a folder _always_ take the security settings of the folder they are in. So if you create a template in a folder or move a template in a folder, there are no individual settings on the template; you must define teams and permissions on the folder level.

Folders may inherit teams and permissions from the parent folder. The inheritance model is either all or nothing: you either inherit all setting from the parent folder (or the parent's parent), or you define all settings on the folder itself. This means that if you uncheck the box 'inherit from parent' all settings are copied and are no longer in synch with the parent folder. 

## Configure teams

To configure release teams in XL Release 6.0.0 and later:

1. Select **Design** > **Folders** from the top bar.
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
| Team | Description | Default Permissions |
| ---- | ----------- | ----------- |
| Folder Owner | People that manage the folders, subfolder and folder security | All folder permissions |
| Template Owner | People that desing the templates. | All template permissions. |
| Release Admin | Contains the release owners, the people responsible for executing the release. Members of this team receive extra notifications when, for example, a task fails and the release is halted. | All release permissions. |

Note that you are free to add and remove people to these teams, and to assign permissions to them.

## Configure permissions

You can configure permissions on templates and releases if you have the _Edit Template Security_ or _Edit Release Security_ permission on a specific template or release, or if you have the _Edit Security_ [global permission](/xl-release/how-to/configure-permissions.html). This applies to all releases and templates in XL Release 5.0 and previous versions. In XL Release 6.0.0 and later versions it only applies for templates and releases that are not stored in a folder.

When using folders, you can only configure permissions on a folder and you need to have the _Edit Folder Security_ permission on the folder to do so. The global _Edit Security_ permission does not apply to folders.

To configure permissions in XL Release 6.0.0 and later:

1. Select **Design** > **Folders** from the top bar.
2. Select a folder.
3. Go to the **Permissions** tab.

    If the folder is the child of another folder, the **Inherit permissions from parent folder** option is selected by default. If you want this folder to have different permissions, clear this option.

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
