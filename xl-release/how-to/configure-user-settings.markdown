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

XL Release has a role-based security system with two types of users:

* _Internal users_ that are managed by XL Release and can be added and removed by an XL Release administrator
* [_External users_](/xl-release/how-to/configure-ldap-security-for-xl-release.html) that are maintained in an LDAP repository such as Active Directory

After you create internal users and/or retrieve external users, you can assign them to [roles](/xl-release/how-to/configure-roles.html). [Global permissions](/xl-release/how-to/configure-permissions.html) are assigned at the role level.

To view and edit XL Release users, select **Settings** > **Users** from the top menu. The Users page is only available to users who have the *Admin* or *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html).

![Users](../images/users.png)

## Create an internal user

To create an internal user:

1. Click **New user**. The User dialog appears.
1. In the **Username** box, enter the name that the user will use when logging in.
1. In the **Name** box, enter the user's full name, as it should appear in overviews and tasks.
1. Enter a password for the user in the **Set password** and **Confirm password** boxes.
1. Click **Save**.

## Change internal user properties

To change the user name, name, email address, or password of an internal user, click the user's name on the Users page.

You cannot change the properties of external users from the XL Release interface because they are maintained in LDAP.

## Disable a user

You can disable a user by clearing the checkbox in the **Enabled** column on the Users page. Disabled users cannot log in to XL Release.
