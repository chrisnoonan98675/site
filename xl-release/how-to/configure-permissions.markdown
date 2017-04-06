---
title: Configure global permissions
categories:
- xl-release
subject:
- Security
tags:
- settings
- role
- permission
- system administration
---

XL Release includes fine-grained access control that ensures the security of your releases. In XL Release, you assign internal and external users to [*roles*](/xl-release/how-to/configure-roles.html) that determine the global permissions that they have. Global permissions apply across the entire XL Release system.

To configure permissions for roles, select **User management** > **Permissions** from the top bar. The Permissions page is only available to users who have the *Admin* or *Edit Security* global permission.

**Note:** Prior to XL Release 6.0.0, select **Settings** > **Permissions**.

![Permissions](../images/global-permissions.png)

The following global permissions are available:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Admin | All permissions |
| Edit Security | Access to the Roles and Permissions pages and permission to edit security on releases and templates |
| Create Template | Permission to create a new template |
| Create Release | Permission to create a release from any template; also see the [Create Release](/xl-release/how-to/create-a-release-template.html#template-permissions) template permission |
| View Reports | Permission to review reports |
| Edit Global Variables | Permission to edit [global variables](/xl-release/how-to/configure-global-variables.html) (available in XL Release 4.8.0 and later) |
| Edit Blackout Period | Permission to create, edit, or delete a blackout period |

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

## Folder, template, and release permissions

In addition to global security, you can enforce security on the folder, template, and release level. For more information, refer to [Configure release teams and permissions](/xl-release/how-to/configure-release-teams-and-permissions.html).
