---
title: Create a new admin user in XL Release
categories:
- xl-release
subject: System administration
tags:
- user management
---

If you have installed XL Release recently, you will know that it ships with a default `admin` user whose password is set as part of the installation process. This is an internal user with special characteristics (for example, the password cannot be changed) that is not intended to be used for day-to-day administration of XL Release.

Of course, you will want an account with which you can administer XL Release on a day-to-day basis. So, as the first step after you've installed XL Release, you can use these instructions to create a new admin user.

## 1. Log in to the UI using the built-in admin account

Log in to XL Release using the built-in `admin` (lowercase) user and the password you set during installation. This will probably be the last time you need to use that account to access XL Release.

## 2. Create a user account to use for day-to-day administration

If you haven't connected XL Release to an external authentication source such as LDAP, add an internal user account. If you already have an external user account or group you use for administration, you can skip this step.

![Add new user](images/create-day2day-admin-user.png)

## 3. Create an XL Release role for administration

Create a new role called `Administrators` (or whatever) for your admins. Add the user account you created in the previous step, or your external user account or group, to the role. Don't forget to click **Save** to apply the changes!

![Add new user](images/create-admins-role.png)

## 4. Grant all permissions to the new admin role

Once you've created the new admin role, you can assign it all rights on the permissions screen. Again, click **Save** to apply the changes.

![Assign rights to new admin user](images/grant-all-rights.png)

You can now log out of the `admin` account and log back in with the user account you've just assigned to the admin role and use it for day-to-day administration of XL Release.

![Log in as new admin](images/login-as-day2day-admin.png)
