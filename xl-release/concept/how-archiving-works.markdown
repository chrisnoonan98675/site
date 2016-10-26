---
title: How archiving works
categories:
- xl-release
subject:
- Archiving and data export
tags:
- archiving
- reporting
- export
- system administration
since:
- XL Release 4.7.0
weight: 436
---

In XL Release 4.7.0 and later, *completed* and *aborted* releases are archived, which means that they are removed from the XL Release repository (JCR database) and stored in a different internal database, which is called the *archive database*. This improves performance and allows you to create custom hooks that export release information to external databases or reporting tools.

![How archiving works](../images/diagram-databases-export-hooks.png)

The differences between archived and unarchived releases are:

* Archived releases are read-only. You cannot add comments to tasks in an archived release.
* Archived releases appear in reports. Releases that are not archived do not appear in reports.
* You can create a custom hook that runs when a release is archived; for example, to store the release in an external reporting database.

## Archiving job

By default, releases are archived right after they finish. Every minute, XL Release runs an archiving job that scans the repository for completed and reported releases, exports them to the archive database, runs custom export hooks, and removes the releases from the repository.

You can configure two parameters for the archiving job:

* The time a release must be completed or aborted before it is moved to the archive. You can configure this in **Settings** > **General Settings** > **Archiving**.

    By default, this is 0 days; in other words, releases are archived right after they are completed or aborted. If you would like to be able to add comments to a completed release, increase this value; however, keep in mind that releases will not appear in report until they are archived.

* The frequency that the archiving job is run. You can change this in the `xlrelease.ArchivingSettings.archivingJobCronSchedule` property in the `deployit-defaults.properties` file. You must specify the frequency in [cron syntax](http://www.cronmaker.com), which allows you to set frequencies such as "every hour" or "every day at midnight".

## Export hooks

XL Release supports custom export hooks that you can use to export information about completed and aborted releases. They are run when a release is archived.

Export hooks are written in [Jython](http://www.jython.org/). You can add them to XL Release as JAR files or by placing files in the XL Release classpath.

You can define export hooks in two ways:

* [Generic export hooks](/xl-release/how-to/create-an-export-hook.html) that you can use to export information to any type of storage
* [JDBC export hooks](/xl-release/how-to/create-a-jdbc-export-hook.html) that can export data to an SQL database

A sample export hook implementation is available on [GitHub](https://github.com/xebialabs/xl-release-samples/tree/master/elastic-search-export-hook).
