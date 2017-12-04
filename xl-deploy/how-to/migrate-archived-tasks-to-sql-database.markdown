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

As of XL Deploy version 7.5.0, the archived tasks are no longer stored as part of the regular Jackrabbit (JCR) repository and are offloaded into a separate SQL database. All the reports starting with version 7.5 are running on top of the SQL database. The purpose of this change is to improve the overall performance and to reduce size of the JCR repository containing active tasks. This also reduces the recovery time after a crash, provides more efficient reporting, and additionally induces a smaller load on the *live* JCR database.

**Important:** Make sure you set up the database configuration correctly before starting the migration.

Except from configuring the database connection, this migration is a fully transparent operation running as a background process that takes place during a normal XL Deploy operation. The XL Deploy data cannot be moved all at once. During the migration period, the reports on past tasks may be incomplete. Data is migrated from newest to oldest and the reports on recent data will be available first.

The migration process starts automatically when you launch XL Deploy. The system remains available to use during the migration with a possible small impact on performance. It is recommended to perform the migration during a low activity period (example: night, weekend, or holiday).

Depending on the size of the data you want to migrate, the process can take from minutes to a few days to fully complete.  Example: approximately 6000 records in 45 minutes and approximately 180.000 records in 20 hours. The duration of the entire process depends on the sizing of the machine or environment, the usage of the system, etc.

**Notes:**
1. During migration some messages will be shown in the log.
1. Tasks that cannot be migrated, will be exported to XML files.
1. If XL Deploy is stopped during migration, the process continues after restart.

### Migration process steps

The migration consists of four phases:
1. **Prepare for insertion:**
The releases marked as *Failed to insert* on a previous migration attempt will be unmarked and XL Deploy will retry to migrate these in the second phase. If the migration fails due to an archived task for an external issue and this issue has been lifted, this is the process to make another migration attempt.

1. **Insert data into the SQL database:**
All the tasks not marked as *Migrated* or *Failed to delete* during a previous run are transferred to the SQL database. When successful, the task will be marked as *Migrated*; when unsuccessful, it will be marked as *Failed to insert*.

1. **Export failed archived tasks to XML:**
As a fallback, all tasks marked as *Failed to insert* will be exported as XML.

1. **Delete migrated tasks from JCR:**
Each task marked as *Migrated* is removed from the JCR repository. If deletion fails, it will be marked as *Failed to delete*.

### Handling of each phase

Each phase is performed in batches that are allowed to run for a specified amount of time. By default, a new batch is triggered every 15 minutes and allowed to run for 9 minutes. For a better performance of the JCR and SQL databases, each batch is divided in sub-batches of a default size 500 and a pause with the default value of 1 minute is inserted between the sub-batches. Each sub-batch queries the JCR database for archived tasks with a particular migration status (for example: *Migrated* or *Failed to insert*). The handling of these migration statuses is designed to ensure progress and prevent duplicate work or unnecessary retries.

### Operational controls

The migration process can be optimized through JMX, using tools such as JConsole, VisualVM, Java Flight Recorder, or others. XL Deploy provides an `MBean` called MigrationSettings under the namespace `com.xebialabs.xldeploy.migration`.

For each phase, the batching schedule can be set using a valid `Cron` expression. The timeout for each batch, the sub-batch size, and the inter-sub-batch interval can be modified for each phase separately. The changes are immediate providing you with multiple options to reduce pressure on the JCR and SQL databases on a running XL Deploy system, or to shorten the total migration time.

There is a JMX operation available that allows you to restart the migration without shutting down and restarting the XL Deploy server (for example: use this when tablespace has run out and the DBA has now added more).

### Settings when upgrading XL Deploy

In the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file (not included in the installation), add a section with the configuration of the reporting database:

        xl {
            reporting {
                database {
                    db-driver-classname="org.postgresql.Driver"
                    db-password="DB_PASSWORD"
                    db-url="jdbc:postgresql://DB_URL"
                    db-username="DB_USER"
                }
            }
        }

If you are using MySQL, a configuration property is required:

      xl {
          reporting {
              database {
                  db-driver-classname="com.mysql.jdbc.Driver"
                  db-password="DB_PASSWORD"
                  db-url="jdbc:mysql://DB_URL?useLegacyDatetimeCode=false"
                  db-username="DB_USER"
              }
          }
      }

You might require other configuration properties, depending on your setup.

For more information about upgrading XL Deploy, refer to [Upgrade XL Deploy](/xl-deploy/how-to/upgrade-xl-deploy.html).

### Known issues

In some cases the migration process can report an error during the deletion phase. These errors can be safely ignored:

        2017-11-28 21:35:11.716 [xl-scheduler-system-akka.actor.default-dispatcher-18] {sourceThread=scala-execution-context-global-267, akkaTimestamp=20:35:11.709UTC, akkaSource=akka://xl-scheduler-system/user/$a/JCR-to-SQL-migration-job-delete-3/$b, sourceActorSystem=xl-scheduler-system} ERROR c.x.d.m.RepeatedBatchProcessor - Exception while processing archived tasks
        com.xebialabs.deployit.jcr.RuntimeRepositoryException: /tasks/.....
                at com.xebialabs.deployit.jcr.JcrTemplate.execute(JcrTemplate.java:48)
                at com.xebialabs.deployit.jcr.JcrTemplate.execute(JcrTemplate.java:26)
               .....
        Caused by: javax.jcr.PathNotFoundException: /tasks/.....
                at org.apache.jackrabbit.core.ItemManager.getNode(ItemManager.java:577)
                at org.apache.jackrabbit.core.session.SessionItemOperation$6.perform(SessionItemOperation.java:129)
               .....
