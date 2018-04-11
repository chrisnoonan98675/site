---
title: Migrate XL Deploy data storage to an SQL database
categories:
- xl-deploy
subject:
- Migrate
tags:
- SQL
- database
- migrate
since: XL Deploy 8.0.0
---

## Requirements

* A database connection configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file. This file is available in the installation with all the configuration settings commented out. When the configuration settings are commented out, XL Deploy uses the default settings.
* A JDBC `jar` library in the `XL_DEPLOY_SERVER_HOME/lib` folder compatible with your selected database.

## Database usage in XL Deploy

In addition to storing the data, XL Deploy stores user supplied artifacts in the database, such as scripts or deployment packages (`jar` or `war` files). These can be stored on the file system (this is the default setting) or in the database server. XL Deploy can use only one of these options at any time. That is why you must configure the database correctly before using XL Deploy in a production setting. This setting can be configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file.

The default settings is to use the internal database that stores the data on the file system. This is intended for temporary use and is not recommended for production use.

* When the installation is upgraded to a new version, XL Deploy creates and maintains the database schema. The database administrator requires the following permissions on the database: REFERENCES, INDEX, CREATE, DROP, in addition to the permissions used in operation: SELECT, INSERT, UPDATE, DELETE.

* Table definitions in XL Deploy use limited column sizes. You must configure this for all the supported databases to prevent these limits from restricting users in how they can use XL Deploy.
Example: The ID of a Configuration Item (CI) is a path-like structure and consists of the concatenation of the names of all the parent folders for the CI. A restriction is set on the length of this combined structure. For the majority of the Relational DataBase Management Systems (RDBMSes), the maximum length is 2000. For MySQL and MS SQL Server, the maximum length is 1024.

**Note:** For MySQL, XL Deploy requires the Barracuda file format for [InnoDB](https://dev.mysql.com/doc/refman/5.7/en/innodb-file-format.html). This is the default format in MySQL 5.7 or later and can be configured in earlier versions.

XL Deploy can be configured to use two different database connections: one for primary XL Deploy data and one for the task archive. Both database connections can be configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file. The main database connection can be configured under the repository key and the database connection for the task archive can be configured under the reporting key. The default configuration for the repository database connection is also used for the reporting connection.

## Upgrade from XL Deploy version 7.5.0 to version 8.0.0

**Important:** When you upgrade from version 7.5.0 to 8.0.0, XL Deploy uses a database connection for the task archive that can be configured under the reporting key. To use this same configuration for both the repository connection and the reporting connection, rename the key to repository. If you do not modify the configuration, XL Deploy will use the reporting connection configuration for the task archive and will use the default configuration (the internal database) for the primary connection. This situation is not correct and may cause issues for any installation.

## Upgrade from XL Deploy version 7.2.0 or earlier to version 8.0.0

To upgrade an existing XL Deploy installation to version 8.0.0, the data must to be converted from the JackRabbit (JCR) format to the new SQL format. The process has two stages:

1. The first stage is during the upgrade. The basic data (metadata, security related data, and CI data) is migrated to the new format. This stage must be completed successfully before XL Deploy can be used.

1. The second stage is during the (normal) operation of XL Deploy. The change history of CIs (data that is mainly used for the “Comparison” function) is migrated to the new format. This operation is configured to run slowly in small batches, to keep the impact on the normal operation to a minimum.
During the migration, the change history data will become available to the system. All functions will operate correctly. The functions that rely on the change history data will not have access to all data until the migration is complete.

### Upgrade instructions

The upgrade process applies any upgraders that have not been applied to the data in the JCR repository. Then it migrates the data from the JCR format to the new SQL format.

If the migration of the archived tasks (part of the upgrade to version 7.5.0) has not been completed, it will also start running during this operation.

To perform the upgrade:

1. Follow the normal [upgrade steps](/xl-deploy/how-to/upgrade-xl-deploy.html#upgrade-the-server) to step 14 “Start the XL Deploy server interactively”.

2. Download the upgrader distribution from the download site: `xl-deploy-x.x.x-jcr-to-sql-migrator.zip` and extract this into the XL Deploy installation folder. For version 8.0.0:

        cd xl-deploy-8.0.0-server
        unzip ~/Downloads/xl-deploy-8.0.0-jcr-to-sql-migrator.zip

3. Make sure the configuration used in the previous installation to configure the JCR repository is copied to the new installation. There are no changes in how that is configured. Once the data is completely migrated (after completing stages 1 and 2) it will no longer be used.

4. Make sure the database connection(s) are configured correctly in `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf`.
It is recommended to use a different database or user (possibly on the same database instance) for the new SQL storage.

5. Follow the normal upgrade steps from step 14.

During the migration, progress will be reported on the command line and in the log file.

Sample from the command line logging for the first stage:

        ...
        2018-03-12 10:26:10.576 [main] {} WARN  c.x.deployit.upgrade.Upgrader - Ensure that you're running in 'interactive' mode and look at your console to continue the upgrade process.
        2018-03-12 10:26:10.576 [main] {} INFO  c.x.deployit.upgrade.Upgrader - Upgraders need to be run, asking user to confirm.

        *** WARNING ***
        We detected that we need to upgrade your repository
        Before continuing we suggest you backup your repository in case the upgrade fails.
        Please ensure you have 'INFO' level logging configured.
        Please enter 'yes' if you want to continue [no]: yes
        2018-03-12 10:26:14.569 [main] {} INFO  c.x.deployit.upgrade.Upgrader - User response was: yes
        2018-03-12 10:26:14.572 [main] {} INFO  c.x.deployit.upgrade.Upgrader - Upgrading to version [deployit 7.5.0]
        2018-03-12 10:26:14.706 [main] {} INFO  c.x.deployit.upgrade.Upgrader - Upgrading to version [deployit 8.0.0]
        2018-03-12 10:26:14.707 [main] {} INFO  c.x.d.c.u.Deployit800CiMigrationUpgrader - Found migration of CI data. Asking user confirmation.

        *** WARNING ***
        This upgrade will migrate the CI data from the previous repository format (JCR) to the new repository format (SQL).
        The target database is configured to be jdbc:derby:repository/db;create=true.
        Some parts of this database (TableName(XLD_CIS) and TableName(XL_USERS)) tables will be cleared before migration.
        Please enter 'yes' if you want to continue [no]: yes
        2018-03-12 10:26:18.298 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Starting CI Migration.
        2018-03-12 10:26:18.298 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Deleting CIs and Users.
        2018-03-12 10:26:18.455 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Migrating metadata.
        2018-03-12 10:26:18.458 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Migrating version info.
        2018-03-12 10:26:18.521 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Migrating security info.
        2018-03-12 10:26:18.803 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Migrating CIs.
        2018-03-12 10:26:19.624 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Running migration phase: create CI records on 2309 nodes in batches of 1000.
        2018-03-12 10:26:20.115 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 1 / total: 3.
        2018-03-12 10:26:20.413 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 2 / total: 3.
        2018-03-12 10:26:20.565 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 3 / total: 3.
        2018-03-12 10:26:20.689 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Running migration phase: process properties on 2309 nodes in batches of 1000.
        2018-03-12 10:26:20.914 [main] {} INFO  c.x.deployit.util.JavaCryptoUtils - BouncyCastle already registered as a JCE provider
        2018-03-12 10:26:22.424 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 1 / total: 3.
        2018-03-12 10:26:23.603 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 2 / total: 3.
        2018-03-12 10:26:24.003 [main] {} INFO  c.x.d.migration.CiJcrToSqlMigrator - Completed batch 3 / total: 3.
        2018-03-12 10:26:24.068 [main] {} INFO  c.x.d.migration.MoveFilesInterceptor - Migrating artifacts from file system. Moving from /Users/mwinkels/8.0.0/xl-deploy-8.0.0-SNAPSHOT-server/repository/repository/datastore to repository/repository/datastore.
        2018-03-12 10:26:24.083 [main] {} INFO  c.x.d.migration.MoveFilesInterceptor - Migrated artifacts.
        2018-03-12 10:26:24.083 [main] {} INFO  c.x.d.migration.JcrToSqlMigrator - Done.
        2018-03-12 10:26:27.332 [task-sys-akka.actor.default-dispatcher-3] {} INFO  akka.event.slf4j.Slf4jLogger - Slf4jLogger started
        ...

The log file will contain more detailed logging, for example: data based on types that are no longer available in the type system.

During the second stage, progress can be monitored using a JMX client, such as [JConsole](https://docs.oracle.com/javase/8/docs/technotes/guides/management/jconsole.html), [JVisualVM](https://docs.oracle.com/javase/8/docs/technotes/guides/visualvm/intro.html), or [JmxTerm](http://wiki.cyclopsgroup.org/jmxterm/) (a command line JMX client).

The following JMX beans are available:

* `com.xebialabs.xldeploy.migration:name=HistoryMigrationStatistics`: This bean can be used to track the progress on the migration of change history.

The properties on the bean show 4 different counts for the items to migrate:

1. ToProcess: The number of items remaining to process.
1. Processed: The number of items successfully processed.
1. InError: The number of items that failed during migration. More detailed information can of the failure can be found in the log file.
1. Ignored: The number of items that were ignored. Items under the “Applications” root in the system or that are of a type for which versioning is switched off will be ignored.

The process is complete when the number of items “ToProcess” has reached 0.

* `com.xebialabs.xldeploy.migration:name=HistoryMigrationManager`: This bean can be used to manage the process.

The reset operations will reset the migration flag on the items (all items or only the ones “InError”) in the JCR repository, to make them eligible for re-processing.
The restart migration operation will restart the process.
These operations can only be applied when the migration process is not running (the previous run is finished).

* `com.xebialabs.xldeploy.migration:name=ArchiveMigrationStatistics`: This bean shows counts on the migration status of items in the task archive.

The process is complete when the number of items “ToProcess” and the number of items “Migrated” have reached 0.

### Remove migrator software after database migration

After the migration is complete (stage 1, stage 2, and task archive migration that was part of the 7.5.0 upgrade) the migrator software can be removed from the system.

Follow these steps to remove it:

1. Shutdown XL Deploy.
1. Run the `XL_DEPLOY_SERVER_HOME/bin/uninstall-jcr-to-sql-migrator.sh` or `XL_DEPLOY_SERVER_HOME/bin/uninstall-jcr-to-sql-migrator.cmd` script. This will remove the migration software.
1. Restart XL Deploy.

If the system does not start correctly at this stage, contact [XebiaLabs support](https://support.xebialabs.com/). This issue could be caused by a plugin that depends on the JCR packages. These packages can be added to the system by re-installing the migrator software as described above. The system will start, but it is very unlikely that the plugin that caused the issue will work correctly.

After removing the software, some of the data is still in the JCR format and it is not used by XL Deploy. Depending on the configuration of your system, you can remove this data using different methods.

* If you were using the internal database, the JCR repository can be removed from the file system. In the default configuration, this includes everything from `XL_DEPLOY_SERVER_HOME/repository` except the database and artifacts folders.

* If you were using an external database, the JCR tables can be dropped depending on your RDBMS and configuration.

It is not mandatory to delete the data after removing the migrator software. This will not impact the performance of XL Deploy.
