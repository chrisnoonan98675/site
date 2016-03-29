---
title: Configure user settings
categories:
- xl-release
subject:
- Settings
tags:
- settings
- user management
- system administration
- ldap
- active directory
- security
---

There are two kind of users in XL Release:

* Internal users are managed by XL Release and can be added and removed by an administrator.
* External users are maintained in an external system such as Active Directory; see [Configure LDAP security for XL Release](/xl-release/how-to/configure-ldap-security-for-xl-release.html) for more information.

To view and edit XL Release users, select **Settings** > **Users** from the top menu. The Users page is only available to users with the *Admin* and *Edit Security* permissions.

![Users](../images/users.png)

## Create an internal user

To create an internal user:

1. Click **New user**. The User dialog appears.
2. In the **Name** box, enter the full name of the user as it should appear in overviews and tasks.
3. In the **Username** box, enter the name that the user will use to log in.
4. Enter a password for the user in **Set password** and **Confirm password**.

## Change a user password

On the **Users** page, you can change the password or email address of an *internal* user by clicking the user.

You cannot change the password of an *external* user from XL Release, because the user is maintained in LDAP. 

## Allow or prevent login

On the **Users** page, you can grant or revoke a user's permission to log in by selecting or clearing the **Allow login** box.
