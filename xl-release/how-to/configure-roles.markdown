---
title: Configure roles
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

XL Release includes fine-grained access control that ensures the security of your releases. In XL Release, you assign users and/or LDAP groups a role, which determines the [global permissions](/xl-release/how-to/configure-permissions.html) that they have. A user or a group that is part of a role is called a principal.

To configure roles, select **Settings** > **Roles** from the top menu. The Roles page is only available to users with the *Admin* or *Edit Security* permission.

![Roles](../images/roles.png)

A role is defined by the principals it contains. This is a comma-separated list of user IDs or groups from an [LDAP repository](/xl-release/how-to/configure-ldap-security-for-xl-release.html). For example, in the screenshot above, users *john* and *mary* have the *Administrators* role, while all users in the *all-it* group are members of the *Users* role.

You can give a role any name you want; there are no predefined role names.

To:

* Add a new role, click **New role**
* Delete a role, click the **X** next to it
* Change the name of a role, click it

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.
