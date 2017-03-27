---
title: Configure profile settings
categories:
- xl-release
subject:
- Settings
tags:
- settings
- profile
- system administration
- ldap
- active directory
---

To edit your XL Release user profile, select **User management** > **User profile** from the top menu.

**Note:** Prior to XL Release 6.0.0, select **Settings** > **Profile**.

![User Profile](../images/user-profile.png)

## Personal settings for user

The email address is required to send [notifications](/xl-release/concept/notifications-in-xl-release.html), such as when a task that is assigned to you starts.

When the XL Release server is configured to use your company's [LDAP directory](/xl-release/how-to/configure-ldap-security-for-xl-release.html), it will attempt to automatically find your name and your email address. You can change the name and address that the server has found.

*External* users cannot change their passwords from XL Release.

## Personal regional settings

**Note:** Users can configure their personal regional settings in XL Release 6.2.0 and later.

### Date format

You can use the format from the language defined in your browser or choose a predefined date format:

* **M/d/yy**: Month first, then day, then two-digit year.
* **dd/MM/yy**: Day first, then month, then two-digit year.
* **yy/MM/dd**: Two-digit year first, then month, then day.
* **Browser default**: The format defined by your browser's language.

### Time format

You can use the format from the language defined in your browser or choose a predefined time format:

* **HH:mm**: 24-hour format.
* **hh:mm a**: 12-hour format with AM/PM.
* **Browser default**: The format defined by you browser's language.

### First day of week

You can use the first day of the week as defined in your browser's settings or choose a predefined values:

* **Sunday**: The week starts on Sunday.
* **Monday**: The week starts on Monday.
* **Browser default**: The week starts according to your browser's language.
