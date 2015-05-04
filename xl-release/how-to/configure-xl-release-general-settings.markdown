---
title: Configure XL Release general settings
categories:
- xl-release
subject:
- Settings
tags:
- settings
- system administration
---

Use the **General Settings** to configure some of XL Release features. This page is only accessible to users with 'admin' permission. Click **Save** to apply your changes.

## Reports

Use the **Reports** section to configure thresholds used in the [release value stream](/xl-release/concept/reports-in-xl-release.html#release-value-stream) screen.

![Reports Settings](../images/reports-settings.png)

## Polling

Use the **Polling** section to configure the polling interval to automatically refresh screens when task status change.

![Reports Settings](../images/polling-settings.png)

## Purging

You can configure XL Release to delete completed releases to prevent the database from growing. By default this is disabled.

When you enable purging, you can configure the time after which XL Release will delete a completed release. This an amount of time since the release was completed.

![Archiving Settings](../images/archiving-settings.png)
