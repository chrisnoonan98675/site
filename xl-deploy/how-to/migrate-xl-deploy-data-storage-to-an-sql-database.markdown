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
- upgrade
since: XL Deploy 8.0.0
---

When you upgrade to XL Deploy 8.0.x, the data stored in XL Deploy must be converted from the JackRabbit (JCR) format to SQL format.

XL Deploy stores data and user-supplied artifacts such as scripts and deployment packages (`jar` and `war` files) in the database on the file system or on a database server. XL Deploy can only use one of these options at any given time, so you must configure the database correctly before using XL Deploy in a production setting. The setting can be configured in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file.

## Database overview

By default, XL Deploy uses an internal database that stores data on the file system. This configuration is intended for temporary use and is not recommended for production use.

### Database permissions

When you upgrade XL Deploy to a new version, XL Deploy creates and maintains the database schema. The database administrator requires the `REFERENCES`, `INDEX`, `CREATE`, and `DROP` permissions on the database; also, the `SELECT`, `INSERT`, `UPDATE`, and `DELETE` permissions are used in operation.

### Table definitions

Table definitions in XL Deploy use limited column sizes. You must configure this for all supported databases to prevent these limits from restricting users in how they can use XL Deploy.

For example, the ID of a configuration item (CI) is a path-like structure that consists of the concatenation of the names of all parent folders for the CI. A restriction is set on the length of this combined structure. For most Relational DataBase Management Systems (RDBMSes), the maximum length is 2000. For MySQL and MS SQL Server, the maximum length is 1024.

**Note:** For MySQL, XL Deploy requires the Barracuda file format for [InnoDB](https://dev.mysql.com/doc/refman/5.7/en/innodb-file-format.html). This is the default format in MySQL 5.7 or later and can be configured in earlier versions.

### Repository and reporting database connections

In the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file, you can configure XL Deploy to use different database connections:

* One for primary XL Deploy data (under the `repository` key)
* One for the [task archive](/xl-deploy/concept/understanding-tasks-in-xl-deploy.html) (under the `reporting` key)

The default configuration for the repository database connection is also used for the reporting connection.

When you upgrade from XL Deploy 7.5.x to 8.0.x, XL Deploy uses a database connection for the task archive. To use the same configuration for the repository and reporting connections, rename the `reporting` key to `repository`. If you do not modify the configuration, XL Deploy will use the `reporting` connection configuration for the task archive and will use the default configuration (the internal database) for the primary connection. This situation is not correct and may cause issues for any installation.

## Requirements

To migrate XL Deploy data storage to an SQL database, you must:

* Configure a database connection in the `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf` file. A sample file is available at `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf.example`. If you do not provide a custom database configuration in `xl-deploy.conf`, XL Deploy uses the default configuration.
* Place a JDBC `jar` library that is compatible with your selected database in the `XL_DEPLOY_SERVER_HOME/lib` folder.

## Upgrade to XL Deploy 8.0.0

The upgrade process has two stages:

1. **Stage 1** happens during the upgrade. During this stage, basic data (metadata, security related data, and CI data) is migrated to SQL format. This stage must be completed successfully before you can use XL Deploy.
1. **Stage 2** happens during normal operation of XL Deploy. During this stage, CI change history data (which is primary used to [compare CIs](/xl-deploy/how-to/working-with-configuration-items.html#compare-cis)) is migrated to SQL format. This operation is executed slowly, in small batches, to minimize the impact on XL Deploy's performance. During the migration, CI change history data will become available to the system. Functionality that relies on CI change history data will not be able to access that data until the migration is complete; however, all other functionality will operate normally.

### Upgrade instructions

The upgrade process first applies required upgraders to the JCR repository, and then migrates data from JCR to SQL format. If you have not already migrated archived tasks (part of the upgrade to XL Deploy 7.5.x), that migration will also run during the upgrade process.

**Important:** Ensure that you always [create a backup of your repository](/xl-deploy/how-to/back-up-xl-deploy.html) before you upgrade to a new version of XL Deploy.

To perform the upgrade:

1. Follow the normal [upgrade procedure](/xl-deploy/how-to/upgrade-xl-deploy.html#upgrade-the-server) through step 13. Do not start the XL Deploy server.

2. Download the XL Deploy JCR-to-SQL Migrator (`xl-deploy-x.x.x-jcr-to-sql-migrator.zip`) from the [XebiaLabs Software Distribution site](https://dist.xebialabs.com/). Extract the Migrator ZIP file into the XL Deploy installation folder, so that its directories are merged with those of the XL Deploy installation. For example:

        cd xl-deploy-8.0.0-server
        unzip ~/Downloads/xl-deploy-8.0.0-jcr-to-sql-migrator.zip

3. Ensure that the settings used to configure the JCR repository in the previous installation is copied to the new installation. There are no changes in how JCR is configured. After the data is completely migrated (that is, after stages 1 and 2 are complete), the JCR configuration will no longer be used.

4. Ensure that the database connection(s) are configured correctly in `XL_DEPLOY_SERVER_HOME/conf/xl-deploy.conf`. It is recommended that you use a different database or user (possibly on the same database instance) for the new SQL storage.

5. Follow the normal upgrade procedure from step 14.

### Monitoring progress during stage 1

During the migration, progress will be reported on the command line and in the log file. This is an example of the command-line logging from stage 1:

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

The log file will contain more detailed logging, such as logging for data that is based on types that are no longer available in the type system.

### Monitoring progress during stage 2

During stage 2, you can monitor progress using a JMX client such as [JConsole](https://docs.oracle.com/javase/8/docs/technotes/guides/management/jconsole.html), [JVisualVM](https://docs.oracle.com/javase/8/docs/technotes/guides/visualvm/intro.html), or [JmxTerm](http://wiki.cyclopsgroup.org/jmxterm/) (a command-line JMX client).

The following JMX beans are available:

* `com.xebialabs.xldeploy.migration:name=HistoryMigrationStatistics`: This bean can be used to track the progress on the migration of change history. The properties on the bean show 4 different counts for the items to migrate:

    1. ToProcess: The number of items remaining to process.
    1. Processed: The number of items successfully processed.
    1. InError: The number of items that failed during migration. More detailed information can of the failure can be found in the log file.
    1. Ignored: The number of items that were ignored. Items under the “Applications” root in the system or that are of a type for which versioning is switched off will be ignored.

    The process is complete when the number of items “ToProcess” has reached 0.

* `com.xebialabs.xldeploy.migration:name=HistoryMigrationManager`: This bean can be used to manage the process. The reset operations will reset the migration flag on the items (all items or only the ones “InError”) in the JCR repository, to make them eligible for re-processing. The restart migration operation will restart the process. These operations can only be applied when the migration process is not running (the previous run is finished).

* `com.xebialabs.xldeploy.migration:name=ArchiveMigrationStatistics`: This bean shows counts on the migration status of items in the task archive. The process is complete when the number of items “ToProcess” and the number of items “Migrated” have reached 0.

## Removing the Migrator after database migration

After migration is complete (including stage 1, stage 2, and task archive migration) you can remove the XL Deploy JCR-to-SQL Migrator from the server. To remove it:

1. Shut down XL Deploy.
1. Run the `XL_DEPLOY_SERVER_HOME/bin/uninstall-jcr-to-sql-migrator.sh` or `XL_DEPLOY_SERVER_HOME/bin/uninstall-jcr-to-sql-migrator.cmd` script. This will remove the Migrator.
1. Restart XL Deploy.

If the system does not start correctly at this stage, contact [XebiaLabs Support](https://support.xebialabs.com/). The issue may be caused by a plugin that depends on the JCR packages. You can add these packages to the server by reinstalling the Migrator; the server will start, but it is unlikely that the plugin that caused the issue will work correctly.

## Removing remaining JCR data

After you remove the XL Deploy JCR-to-SQL Migrator, some data will remain in JCR format. XL Deploy does not use this data. Depending on the configuration of your server, you can remove this data using different methods:

* If you were using an internal database, you can delete the JCR repository from the file system. In the default XL Deploy configuration, this includes everything in the `XL_DEPLOY_SERVER_HOME/repository` directory except the `database` and `artifacts` folders.
* If you were using an external database, you can drop the JCR tables, depending on your RDBMS and configuration.

It is not required that you delete the data after removing the migrator software. It will not impact the performance of XL Deploy.
