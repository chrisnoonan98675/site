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

In XL Release 4.6.0 you can configure XL Release to delete completed releases to prevent the database from growing. By default this is disabled.

When you enable purging, you can configure the time after which XL Release will delete a completed release. This is an amount of time since the release was completed or aborted.

![Archiving Settings](../images/archiving-settings-purging.png)

In XL Release 4.7.0 and later, the [archiving feature](/xl-release/how-to/configure-xl-release-general-settings.html#archiving) replaces the purging feature.

## Archiving

In XL Release 4.7.0 and later, you can configure XL Release to archive finished (that is, completed and aborted) releases. These releases are moved to a separate "reporting" database so that the active repository can be kept relatively small. 

Archived releases are available in reports and you can search for them from them Release Value Stream Mapping screen. However, you cannot search for them in the Release Overview screen.

You can configure the time after which XL Release will archive a completed release. This is an amount of time since the release was completed or aborted.

![Archiving Settings](../images/archiving-settings.png)

**Note:** XL Release reports related to finished releases only use data from the reporting database. Therefore, if you configure a very long period before releases are archived, then your reports will not be up-to-date. Also, having a large number of releases that are not archived negatively affects performance. For more information about the configuration of the reporting database, refer to [Configure the reporting database](/xl-release/how-to/configure-the-reporting-database.html).
