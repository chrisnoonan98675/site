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

On the **Permissions** screen under **Settings**, you can assign permissions to roles.

![Permissions](../images/global-permissions.png)

For each role, there is a list of permissions that can be enabled or disabled:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Admin | All permissions |
| Login | Permission to log in to XL Release  |
| Edit security | Access to the Roles and Permissions screens and permission to edit security on releases and templates |
| Create Template | Permission to create a new template |
| Create Release | Permission to create a release from any template; also see the [Create Release](/xl-release/how-to/configure-permissions-for-a-release.html) permission on a single release |
| View Reports | Permission to review reports |
| Edit Global Variables | Permission to edit [global variables](/xl-release/how-to/configure-global-variables.html) (available in XL Release 4.8.0 and later) |

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

In addition to global permissions, security can be enforced on the release level. On releases and templates, other permissions apply and are granted to teams that are defined within the release. See [release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) and [template permissions](/xl-release/how-to/create-a-release-template.html#template-permissions) for an overview of these permissions.
