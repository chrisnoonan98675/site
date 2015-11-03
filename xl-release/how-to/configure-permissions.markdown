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

* **Admin**: Has all permissions in XL Release.
* **Login**: Required to log in to XL Release.
* **Edit security**: Gives access to the Roles and Permissions screens and allows global permission to edit security on releases and templates.
* **Create Template**: Permission to create a new template
* **Create Release**: Permission to create a release from any template. See also the [Create release](/xl-release/how-to/configure-permissions-for-a-release.html) permission on a single release.
* **View Reports**: Permission to view reports.

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.

In addition to global permissions, security can be enforced on the release level. On releases and templates, other permissions apply and are granted to teams that are defined within the release. See [release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) and [template permissions](/xl-release/how-to/create-a-release-template.html#template-permissions) for an overview of these permissions.
