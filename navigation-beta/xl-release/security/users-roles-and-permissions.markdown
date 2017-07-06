---
no_index: true
title: Users, roles, and permissions
---

## Configure user settings

XL Release has a role-based security system with two types of users:

* _Internal users_ that are created by an XL Release administrator and managed by XL Release
* _External users_ that are maintained in an LDAP repository such as Active Directory

After you [configure LDAP security](/xl-release/how-to/configure-ldap-security-for-xl-release.html), external users can log in, at which point they are stored in XL Release as external users.

You can assign both internal and external users to [roles](/xl-release/how-to/configure-roles.html), to which you assign [global permissions](/xl-release/how-to/configure-permissions.html).

To view and edit XL Release users, select **User management** > **Users**  from the top menu. The Users page is only available to users who have the *Admin* or *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html).

**Note:** Prior to XL Release 6.0.0, select **Settings** > **Users**.

![Users](../images/users.png)

### Create an internal user

To create an internal user:

1. Click **New user**. The User dialog appears.
1. In the **Username** box, enter the name that the user will use when logging in.
1. In the **Name** box, enter the user's full name, as it should appear in overviews and tasks.
1. Enter a password for the user in the **Set password** and **Confirm password** boxes.
1. Click **Save**.

### Change internal user properties

To change the user name, name, email address, or password of an internal user, click the user's name on the Users page.

You cannot change the properties of external users from the XL Release interface because they are maintained in LDAP.

### Disable a user

You can disable a user by clearing the checkbox in the **Enabled** column on the Users page. Disabled users cannot log in to XL Release.

## Configure roles

XL Release has a role-based security system with two types of users:

* [_Internal users_](/xl-release/how-to/configure-user-settings.html) that are managed by XL Release
* [_External users_](/xl-release/how-to/configure-ldap-security-for-xl-release.html) that are maintained in an LDAP repository such as Active Directory

You assign internal users, external users, and external user groups to _roles_, which determine the [global permissions](/xl-release/how-to/configure-permissions.html) that they have. The technical term for a user or group that is assigned to a role is a _principal_.

To configure roles, select **User management** > **Roles** from the top menu. The Roles page is only available to users who have the *Admin* or *Edit Security* global permission.

**Note:** Prior to XL Release 6.0.0, select **Settings** > **Roles**.

![Roles](../images/roles.png)

In this example, the users *john* and *mary* have the *Administrators* role, while all users in the *all-it* group are members of the *Users* role.

You can give a role any name you want; there are no predefined role names.

To:

* Add a new role, click **New role**
* Delete a role, click the **X** next to it
* Change the name of a role, click it
* Add a principal to a role, type the name and press ENTER

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

## Configure release teams and permissions

In XL Release, you use **teams** to group users who have the same role.  You can assign permissions to teams on the folder level, template level, and release level. When designing the release flow of a template or a release, you can assign tasks to teams. Team members will then be responsible for completing the task and will be notified when the task starts.

### Teams and permissions in folders

With XL Release 6.0.0, folders were introduced. Now templates and releases can be stored in a folder, or not in any folder at all.

In the latter case, teams and permissions are set directly on templates and releases, like previous versions of XL Release.

In XL Release 6.0.0 and later, templates and releases that are stored in a folder _always_ take the security settings of the folder they are in. So if you create a template in a folder or move a template to a folder, there are no individual settings on the template; you must define teams and permissions on the folder level.

A folder may inherit teams and permissions from its parent folder. The inheritance model is all or nothing: you either inherit all settings from the parent folder (or the parent's parent), or you define all settings on the folder itself. This means that if you clear the **Inherit permissions from parent folder** option on the folder, all settings are copied and are no longer in sync with the parent folder.

### Configure teams

To configure release teams in XL Release 6.0.0 and later:

1. Select **Design** > **Folders** from the top bar.
2. Select a folder.
3. Go to the **Teams & Permissions** tab.
4. Click **Add team** to add a new team.
5. Add [roles](/xl-release/how-to/configure-roles.html) by typing in the **Global roles** column. Add individual users by typing in the **Users** column.
6. Click **Save** to save your changes.

![Folder teams](../images/folder-teams.png)

In XL Release 5.0.x and earlier, you add teams by opening the template or release and going to **Show** > **Teams**.

#### Predefined teams

XL Release automatically creates the following predefined teams, which you cannot remove:

{:.table .table-striped}
| Team | Description | Default Permissions |
| ---- | ----------- | ----------- |
| Folder Owner | People that manage the folders, subfolder, and folder security. | All folder permissions. |
| Template Owner | People that design the templates. | All template permissions. |
| Release Admin | Contains the release owners, the people responsible for executing the release. Members of this team receive extra notifications when, for example, a task fails and the release is halted. | All release permissions. |

Note that you are free to add and remove people to these teams, and to assign permissions to them.

### Configure permissions

You can configure permissions on templates and releases if you have the _Edit Template Security_ or _Edit Release Security_ permission on a specific template or release, or if you have the _Edit Security_ [global permission](/xl-release/how-to/configure-permissions.html). This applies to all releases and templates in XL Release 5.0 and previous versions. In XL Release 6.0.0 and later versions, it only applies for templates and releases that are not stored in a folder.

When using folders, you can only configure permissions on a folder and you need to have the _Edit Folder Security_ permission on the folder to do so. The global _Edit Security_ permission does not apply to folders.

To configure permissions in XL Release 6.0.0 and later:

1. Select **Design** > **Folders** from the top bar.
2. Select a folder.
3. Go to the **Teams & Permissions** tab.

    If the folder is the child of another folder, the **Inherit permissions from parent folder** option is selected by default. If you want this folder to have different permissions, clear this option.

4. Under **Folder permissions**, add release teams to each permission.
5. Click **Save** to save your changes.

![Folder permissions](../images/folder-permissions.png)

In XL Release 5.0.x and earlier, you configure permissions by opening the template or release and going to **Show** > **Permissions**. In XL Release 6.0.0 and later, the Permissions screen provides a read-only view of the permissions on the template or release.

#### Folder permissions

Folder permissions are available in XL Release 6.0.0 and later. The following permissions apply to folders:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Folder | Users can see the folder in the Folders screen. |
| Edit Folder | Users can edit the folder (for example, by renaming it). |
| Edit Folder Security | Users can edit the teams and permissions on a folder. |

#### Template permissions

The following permissions apply to templates:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Create Release | Users can create a release from the template. |
| View Template | Users can see the template in the template overview. |
| Edit Template | Users can change the template by adding tasks and phases and changing them. |
| Edit Template Security | Users can edit teams and permissions on the template; this permission is named _Edit Security_ prior to XL Release 6.0.0. |
| Edit Triggers | Users can view, edit, and delete triggers on the template; this permission is available in XL Release 6.0.0 and later. To create a trigger, you also need the *Create Release* permission. |

#### Release permissions

The following permissions apply to releases:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| View Release | Users have view access to this release. It will appear in the Release Overview. In the release details, users have read-only access to the release flow, properties, and activity log. |
| Edit Release | Users can alter the structure of a release by adding and moving tasks and phases. Release properties and teams are editable. |
| Edit Release Security | Users can edit teams and permissions in a release; this permission is named _Edit Security_ prior to XL Release 6.0.0. |
| Start Release | Users can start a planned release. |
| Abort Release | Users can abort an active or planned release. |
| Reassign Task | Users can assign tasks to other people. Team assignment is also enabled. |
| Edit Task | Users can edit individual tasks. |
| Edit Task Blackout| Users can enable or disable the Postpone during blackout period setting at task level. |
