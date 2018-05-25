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
| Edit security | Access to the Roles and Permissions pages and permission to edit security on releases and templates |
| Create template | Permission to create a new template |
| Create release | Permission to create a release from any template; also see the [Create Release](/xl-release/how-to/configure-release-teams-and-permissions.html#template-permissions) template permission |
| View reports | Permission to review reports |
| Edit global variables | Permission to edit [global variables](/xl-release/how-to/configure-global-variables.html) (available in XL Release 4.8.0 and later) |
| Create top level folders | Permission to create folders |
| Edit blackout period | Permission to create, edit, or delete a blackout period |
| Edit risk profile | Permission to create, edit, or delete a risk profile |  

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

## Folder, template, and release permissions

In addition to global security, you can enforce security on the folder, template, and release level. For more information, refer to [Configure release teams and permissions](/xl-release/how-to/configure-release-teams-and-permissions.html).
