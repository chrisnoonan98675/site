---
title: Configure profile settings
categories:
xl-release
subject:
Settings
tags:
settings
profile
system administration
ldap
active directory
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

* **Month first**: Uses the `M/d/yy` format for short date, `MMM d, y` for medium date and `MMMM d, y` for long date representation.
* **Day first**: Uses the `dd/MM/yy`, `dd MMM y` and `dd MMMM y` formats.
* **Year first**: Uses the `yy/MM/dd`, `y MMM dd` and `y MMMM dd` formats.
* **Browser default**: Uses the date formats defined by your browser’s language.

### Time format

You can use the format from the language defined in your browser or choose a predefined time format:

* **24 hours**: Uses the 24-hour format.
* **12 hours**: Uses the 12-hour format with AM/PM.
* **Browser default**: Uses the time formats defined by you browser's language.

### First day of week

You can use the first day of the week as defined in your browser's settings or choose a predefined values:

* **Sunday**: The week starts on Sunday.
* **Monday**: The week starts on Monday.
* **Browser default**: The week starts according to your browser's language.
