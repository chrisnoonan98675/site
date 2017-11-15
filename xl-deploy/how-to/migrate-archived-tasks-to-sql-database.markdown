---
title: Migrate archived tasks to SQL database
categories:
- xl-deploy
subject:
- Migrate
tags:
- SQL
- migrate
since: XL Deploy 7.5.0
---

As of XL Deploy version 7.5, the archived tasks are no longer stored as part of the regular Jackrabbit (JCR) repository and are offloaded into a separate SQL database. All the reports starting with version 7.5 are running on top of the SQL database. The purpose of this change is to reduce the overall size of the JCR repository containing active tasks. This also reduces the recovery time after a crash, provides more efficient reporting, and additionally induces a smaller load on the *live* JCR database.

Except from configuring the database connection, this migration is a fully transparent operation running as a background process that takes place during a normal XL Deploy operation. The XL Deploy data cannot be moved all at once. During the migration period, the reports on past tasks may be incomplete.

### Migration process steps

The migration consists of four phases:
1. Prepare for insertion
1. Insert data into the SQL database
1. Export failed archived tasks to XML
1. Delete migrated tasks from JCR

#### First phase: Prepare for insertion

The releases marked as *Failed to insert* on a previous migration attempt will be unmarked and XL Deploy will retry to migrate these in the second phase. If the migration fails due to an archived task for an external issue and this issue has been lifted, this is the process to make another migration attempt.

#### Second phase

All the tasks not marked as *Migrated* or *Failed to delete* during a previous run are transferred to the SQL database. When successful, the task will be marked as *Migrated*; when unsuccessful, it will be marked as *Failed to insert*.

#### Third phase

As a fallback, all tasks marked as *Failed to insert* will be exported as XML.

#### Fourth phase

Each task marked as *Migrated* is removed from the JCR repository. If deletion fails, it will be marked as *Failed to delete*.

### Handling of each phase

Each phase is performed in batches that are allowed to run for a specified amount of time. By default, a new batch is triggered every minute and allowed to run for 30 seconds. For a better performance of the JCR and SQL databases, each batch is divided in sub-batches of a default size 10 and a pause with the default value of 200ms is inserted between the sub-batches. Each sub-batch queries the JCR database for archived tasks with a particular migration status (for example: *Migrated* or *Failed to insert*). The handling of these migration statuses is designed to ensure progress and prevent duplicate work or unnecessary retries.

### Operational controls

The migration process can be monitored and optimized through JMX, using tools such as JConsole, VisualVM, Java Flight Recorder, or others. XL Deploy provides an `MBean` called MigrationSettings under the namespace `com.xebialabs.xldeploy.migration`.

For each phase, the batching schedule can be set using a valid `Cron` expression. The timeout for each batch, the sub-batch size, and the inter-sub-batch interval can be modified for each phase separately. The changes are immediate providing you with multiple options to reduce pressure on the JCR and SQL databases on a running XL Deploy system, or to shorten the total migration time.

There is a JMX operation available that allows you to restart the migration without shutting down and restarting the XL Deploy server (for example: use this when tablespace has run out and the DBA has now added more).
