---
title: Users, roles, and permissions in XL Release
---

## Users

XL Release has a role-based security system with two types of users:

* _Internal users_ that are managed by XL Release and can be added and removed by an XL Release administrator
* [_External users_](/xl-release/how-to/configure-ldap-security-for-xl-release.html) that are maintained in an LDAP repository such as Active Directory

After you create internal users and/or retrieve external users, you can assign them to [roles](/xl-release/how-to/configure-roles.html). [Global permissions](/xl-release/how-to/configure-permissions.html) are assigned at the role level.

To view and edit XL Release users, select **Settings** > **Users** from the top menu. The Users page is only available to users who have the *Admin* or *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html).

![Users](/xl-release/images/users.png)

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

### Edit your user profile

To edit your XL Release user profile, select **Settings** > **Profile** from the top menu.

![User Profile](/xl-release/images/user-profile.png)

The email address is required to send [notifications](/xl-release/concept/notifications-in-xl-release.html), such as when a task that is assigned to you starts.

When the XL Release server is configured to use your company's [LDAP directory](/xl-release/how-to/configure-ldap-security-for-xl-release.html), it will attempt to automatically find your name and your email address. You can change the name and address that the server has found.

**Note:** *External* users cannot change their passwords from XL Release.

## Roles

XL Release has a role-based security system with two types of users:

* [_Internal users_](/xl-release/how-to/configure-user-settings.html) that are managed by XL Release
* [_External users_](/xl-release/how-to/configure-ldap-security-for-xl-release.html) that are maintained in an LDAP repository such as Active Directory

You assign internal users, external users, and external user groups to _roles_, which determine the [global permissions](/xl-release/how-to/configure-permissions.html) that they have. The technical term for a user or group that is assigned to a role is a _principal_.

To configure roles, select **Settings** > **Roles** from the top menu. The Roles page is only available to users who have the *Admin* or *Edit Security* global permission.

![Roles](/xl-release/images/roles.png)

In this example, the users *john* and *mary* have the *Administrators* role, while all users in the *all-it* group are members of the *Users* role.

You can give a role any name you want; there are no predefined role names.

To:

* Add a new role, click **New role**
* Delete a role, click the **X** next to it
* Change the name of a role, click it

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

## Global permissions

XL Release includes fine-grained access control that ensures the security of your releases. In XL Release, you assign internal and external users to [*roles*](/xl-release/how-to/configure-roles.html) that determine the global permissions that they have. Global permissions apply across the entire XL Release system.

To configure permissions for roles, select **Settings** > **Permissions** from the top menu. The Permissions page is only available to users who have the *Admin* or *Edit Security* global permission.

![Permissions](/xl-release/images/global-permissions.png)

The following global permissions are available:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Admin | All permissions |
| Login | Permission to log in to XL Release  |
| Edit Security | Access to the Roles and Permissions pages and permission to edit security on releases and templates |
| Create Template | Permission to create a new template |
| Create Release | Permission to create a release from any template; also see the [Create Release](/xl-release/how-to/create-a-release-template.html#template-permissions) template permission |
| View Reports | Permission to review reports |
| Edit Global Variables | Permission to edit [global variables](/xl-release/how-to/configure-global-variables.html) (available in XL Release 4.8.0 and later) |

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

### Template and release permissions

In addition to global security, you can enforce security on the template level and the release level. For more information, refer to:

* [Template permissions](/xl-release/how-to/create-a-release-template.html#template-permissions)
* [Release permissions](/xl-release/how-to/configure-release-teams-and-permissions.html#release-permissions)

### Access to task types

PLACEHOLDER
