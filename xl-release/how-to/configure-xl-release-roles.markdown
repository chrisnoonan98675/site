---
title: Configure XL Release roles
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

The **Roles** and **Permissions** screens allow you to set global permissions; that is, security settings that apply across the entire system. These pages are only available to users with 'admin' and 'edit security' permissions.

![Roles](../images/roles.png)

A *role* is a group of log-in names or LDAP groups. *Principal* is the technical term for an entity that is either a user or a group. You can assign permissions to roles in the **Permissions** screen.

To add a new role, click **New Role**. To delete a role, click the cross to its right. To change the name of a role, click it.

The role is defined by the principals that it contains. This is a comma-separated list of user IDs or groups from the LDAP repository. For example, in the screen shot above, 'john' and 'mary' are Administrators, and all users in the 'all-it' group are Users in XL Release.

The naming of the roles is not restricted. You can give a role any name you want, and there are no predefined role names.

Click **Save** to apply your changes. Click **Reset** to discard your changes and reload the current settings from the server.
