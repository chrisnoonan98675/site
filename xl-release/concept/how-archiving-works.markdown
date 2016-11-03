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

## Configure the archiving job

By default, releases are archived right after they finish. Every minute, XL Release runs an archiving job that scans the repository for completed and reported releases, exports them to the archive database, runs custom export hooks, and removes the releases from the repository.

### Configure general properties

You can configure the following parameters for the archiving job:

* In **Settings** > **General settings** > **Archiving**, you can configure the amount of time a release must be completed or aborted before it is moved to the archive. By default, this is 0 days; in other words, releases are archived right after they are completed or aborted. If you would like to be able to add comments to a completed release, increase this value; however, keep in mind that releases will not appear in reports until they are archived.

* In the `xlrelease.ArchivingSettings.archivingJobCronSchedule` property in the `deployit-defaults.properties` file, you can configure how frequently the archiving job runs. You must specify the frequency in [cron syntax](http://www.cronmaker.com), which allows you to set frequencies such as "every hour" or "every day at midnight".

Ensure that you configure the archiving job settings so that the overall number of releases that can be archived per day is not less than the number of releases being completed or aborted. For example, do not configure the cron schedule to run once per week without changing the [throttling properties](#configure-throttling-properties), because this will mean that only approximately 18 releases would be archived per week, while many more releases may be completed or aborted during that time.

### Configure throttling properties

In XL Release 6.0.0 and later, you can throttle the archiving job. This is useful if you have many releases to archive, as it ensures that the archiving job does not use a large amount of system resources and impede XL Release's performance.

The following throttling properties are available in the `deployit-defaults.properties` file:

{:.table .table-striped}
| Property | Description | Default |
| -------- | ----------- | ------- |
| `xlrelease.ArchivingSettings.maxSecondsPerRun` | Maximum amount of time that one execution of the archiving job is allowed to take (in seconds). With the default setting, approximately 18 releases will be archived, and then the job will stop. The next job execution will trigger after 1 minute. Set to `0` or `-1` to remove the limitation. | `20` |
| `xlrelease.ArchivingSettings.sleepSecondsBetweenReleases` | Time to wait between archiving each release (in seconds). With the default setting, the job will not archive more than 1 release per second. Set to any negative number to remove the wait time. | `1` |
| `xlrelease.ArchivingSettings.searchPageSize` | Search page size when searching for releases to archive. | `20` |
| `xlrelease.ArchivingSettings.enabled` | Enables the archiving job. Use this setting to disable the job while you configure or troubleshoot the job.<br />**Important:** Do not permanently disable the archiving job. This will cause a negative performance impact. | `true` |

#### Adjusting properties at runtime

You can configure the throttling properties at runtime using the [JMX](https://blogs.oracle.com/java-platform-group/entry/deep_monitoring_with_jmx)-managed bean by path `com.xebialabs.xlrelease:name=Archiving`. Note that changing properties using JMX does not change the default values that are stored in the `deployit-defaults.properties` file; therefore, after the XL Release server is restarted, the configuration is reset to what is set in the file.

#### Adjusting the search page size

The `searchPageSize` property is a low-level setting that should not be changed in most cases. It limits the number of releases that the archiving job finds before it archives the set. For example, if the property is set to `5`, XL Release will find five completed releases, archive them, search for the next five completed releases, and so on until it archives all required releases or the `maxSecondsPerRun` limit has been reached.

This property can be used, for example, if the repository contains thousands of releases that must be archived and you want XL Release to find the releases as quickly as possible. For example, if the `searchPageSize` is `500` and `maxSecondsPerRun` and `sleepSecondsBetweenReleases` are both `-1`, then the next archiving job will work as fast as possible to archive all releases. However, the CPU usage of XL Release will be very high for the whole time that the archiving job runs.

## Create an export hook

XL Release supports custom export hooks that you can use to export information about completed and aborted releases. They are run when a release is archived.

Export hooks are written in [Jython](http://www.jython.org/). You can add them to XL Release as JAR files or by placing files in the XL Release classpath.

You can define export hooks in two ways:

* [Generic export hooks](/xl-release/how-to/create-an-export-hook.html) that you can use to export information to any type of storage
* [JDBC export hooks](/xl-release/how-to/create-a-jdbc-export-hook.html) that can export data to an SQL database

A sample export hook implementation is available on [GitHub](https://github.com/xebialabs/xl-release-samples/tree/master/elastic-search-export-hook).
