---
title: Create a new XL Release admin user
---

XL Release includes a default `admin` user whose password is configured during the installation process. This is an internal user with special characteristics that is not intended to be used for regular administration of XL Release. However, you may want to create a separate admin user for day-to-day administration of XL Release.

## Step 1 Log in using the built-in `admin` account

Log in to XL Release using the built-in `admin` (lowercase) user and the password you set during installation.

## Step 2 Create a user account to use for day-to-day administration

If you have not connected XL Release to an external authentication source such as [LDAP](/xl-release/how-to/connect-xl-release-to-ldap-or-active-directory.html), add an internal user account. If you already have an external user account or group you use for administration, you can skip this step.

![Add new user](/xl-release/images/create-day2day-admin-user.png)

## Step 3 Create an XL Release role for administration

Create a new role called, for example, _Administrators_. Add the user account you created in the previous step, or your external user account or group, to the role. Click **Save** to apply the changes.

![Add new user](/xl-release/images/create-admins-role.png)

## Step 4 Grant all permissions to the new admin role

Now you can assign the new admin role all rights on the permissions screen. Click **Save** to apply the changes.

![Assign rights to new admin user](/xl-release/images/grant-all-rights.png)

Log out of the default `admin` account and log back in with the new account you created.

![Log in as new admin](/xl-release/images/login-as-day2day-admin.png)
