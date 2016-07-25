---
title: Configure permissions
categories:
- xl-release
subject:
- Settings
tags:
- settings
- role
- permission
- system administration
---

XL Release includes fine-grained access control that ensures the security of your releases. In XL Release, you assign users and/or LDAP groups a [*role*](/xl-release/how-to/configure-roles.html), which determines the global permissions that they have. Global permissions apply across the entire XL Release system.

To configure permissions for roles, select **Settings** > **Permissions** from the top menu. The Permissions page is only available to users with the *Admin* or *Edit Security* permission.

![Permissions](../images/global-permissions.png)

The following global permissions are available:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Admin | All permissions |
| Login | Permission to log in to XL Release  |
| Edit Security | Access to the Roles and Permissions pages and permission to edit security on releases and templates |
| Create Template | Permission to create a new template |
| Create Release | Permission to create a release from any template; also see the [Create Release](/xl-release/how-to/configure-permissions-for-a-release.html) permission on a single release |
| View Reports | Permission to review reports |
| Edit Global Variables | Permission to edit [global variables](/xl-release/how-to/configure-global-variables.html) (available in XL Release 4.8.0 and later) |

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

## Release permissions

In addition to global permissions, security can be enforced on the release level. On releases and templates, other permissions apply and are granted to teams that are defined in the release. See [release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) and [template permissions](/xl-release/how-to/create-a-release-template.html#template-permissions) for an overview of these permissions.
