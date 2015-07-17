---
title: How archiving works with XL Release 4.7.0
categories:
- xl-release
subject:
- Archiving and data export
tags:
- archiving
- reporting
---

# How archiving works

A new feature in XL Release 4.7.0 is the archiving database. When a release is completed or aborted, it is moved from the repository to the archiving database. This improves performance and allows for custom export hooks to be defined. 

## Release life cycle

The life cycle of a release is as follows:

First, a release is created from a template and it is in **planned** state.
Then, the release is started and it is **in progress**. While it is in progress, tasks are completed, or tasks may fail and be restarted, etc. Finally either all tasks are completed and the release is **completed**, or after one or more failures the release is **aborted**.

See the [Release life cycle diagram](xl-release/concept/release-life-cycle.html) for more information.

**Completed** and **Aborted** are final states of a release, meaning that no work can be done in the scope of this release.

## Archiving

From XL Release 4.7.0 onwards, completed and aborted releases are archived. 

Archiving means that a release is removed from the repository and stored in another database, the **archive database**. 

What are the practical difference between an archived and unarchived release?

1. Archived releases are read-only. You can no longer add comments to tasks on an archived release.
2. Archived releases are in the reports. Releases that are not archived will not show up in the reports.
3. You can add a script to run at the moment a release is archived, for example to store it in a custom reporting database.

## Archiving job

By default, releases are archived right after they finish. 
There is an archiving job that runs every minute. It scans the repository for completed and reported releases, exports them to the archive database, runs custom export hooks and removes them from the repository.

There are two parameters that can be configured in this aspect.

1. The time a release needs to be finished before being moved to the archive. You can configure this in **Settings > General Settings > Archive**. By default this is 0 days, or in other words, right away. You can set this to a higher value if you would like to be able to add comments to a completed release, but keep in mind that it won't show up in the reports during that time.
2. The frequency that the archiving job is run. This is specified as the property `xlrelease.ArchivingSettings.archivingJobCronSchedule` in `deployit-defaults.properties`. It's specified in [cron syntax](http://www.cronmaker.com) and with it you can express frequencies like "every hour" or "every day at midnight".

## Export hooks

XL Release supports custom hooks that you can use to export information about completed releases. They are run when a release is archived. 

Export hooks are written in Jython and can be added to XL Release as a JAR file or by placing files in the XL Release classpath.

You can define export hooks in two ways: 

* [Generic export hooks](/xl-release/how-to/create-an-export-hook.html) that you can use to export information to any type of storage
* [JDBC export hooks](/xl-release/how-to/create-a-jdbc-export-hook.html) that can export data to an SQL database.

A sample export hook implementation is available on [GitHub](https://github.com/xebialabs/xl-release-samples/tree/master/elastic-search-export-hook).

