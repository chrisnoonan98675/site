---
title: Configure XL Release user settings
categories:
- xl-release
subject:
- Settings
tags:
- settings
- users
- system administration
- ldap
- active directory
---

The **Users** page allows you to view and edit XL Release Users. This page is only available to users with 'admin' and 'edit security' permissions.

![Users](../images/users.png)

There are two kind of users in XL Release:

* Internal users are managed by XL Release and can be added and removed by an administrator.
* External users are maintained in an external system such as Active Directory. See [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html) in the System Administration manual for more information.

To create an internal user, click **New user**. This dialog appears:

![Users](../images/user-modal.png)

Enter the required details to create the user. Note that **Username** is the name that is needed to log in, and **Name** is the full name of the user, which is displayed in overviews and tasks.

On the overview, administrators can change the password or email address of an *internal* user by clicking on the user. You cannot change the password of an *external* user from XL Release because it is maintained in LDAP. 

You can also grant or revoke the login permission to a specific user by ticking or unticking the checkbox.
