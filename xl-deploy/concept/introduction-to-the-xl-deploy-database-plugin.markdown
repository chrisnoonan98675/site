---
title: Introduction to the XL Deploy Database plugin
categories:
- xl-deploy
subject:
- Database plugin
tags:
- plugin
- database
---

The XL Deploy Database plugin supports deployment of SQL files and folders to a database client.

## Features

* Runs on XL Deploy 4.0 and up.
* Supports deployment to MySQL, PostgreSQL, Oracle, MS SQL and DB/2.
* Deploys and undeploys SQL files and folders.

## SQL scripts

The `sql.SqlScripts` configuration item (CI) identifies a ZIP file that contains SQL scripts that are to be executed on a database. The scripts must be located at the root of the ZIP file.

SQL scripts can be installation scripts or rollback scripts. Installation scripts are used to execute changes on the database, such as creation of a table or inserting data. Each installation script is associated with a rollback script that undoes the actions performed by its companion installation script.

Executing an installation script, followed by the accompanying rollback script, should leave the database in an unchanged state.

A rollback script **must** have the same name as the installation script it is associated with, and must have the moniker `-rollback` attached to it.

**Note:** If a script fails and you perform a rollback, XL Deploy executes **all** rollback scripts, not only the rollback scripts that correspond to the installation scripts that were successfully executed.

## Order of SQL scripts

SQL scripts are ordered lexicographically based on their filename. This is a sample ordering of several installation scripts:

* `1-create-user-table.sql`
* `1-create-user-table-rollback.sql`
* `10-drop-user-index.sql`
* `10-drop-user-index-rollback.sql`
* `2-insert-user.sql`
* `2-insert-user-rollback.sql`
* ...
* `9-create-user-index.sql`
* `9-create-user-index-rollback.sql`

Note that in this example, the tenth script, `10-drop-user-index.sql` would be incorrectly executed after the first script, `1-create-user-table.sql`.

## Upgrading SQL scripts

When upgrading a SqlScripts CI, only the scripts that were not present in the previous package version are executed. For example, if the previous SqlScripts folder contained `script1.sql` and `script2.sql` and the new version of SqlScripts folder contains `script2.sql` and `script3.sql`, then only `script3.sql` will be executed as part of the upgrade.

## Undeploying SQL scripts

When you undeploy an SqlScripts CI, all rollback scripts are executed in reverse lexicographical order.

Additionally, since Deployit 3.9.3, scripts with content that has been modified are also executed. To re-enable the old behavior, in which only the names of the scripts were taken into consideration, set the hidden property `sql.ExecutedSqlScripts.executeModifiedScripts` to `false`. If a rollback script is provided for that script, it will be run before the new script is run. To disable this behavior, set the hidden property `sql.ExecutedSqlScripts.executeRollbackForModifiedScripts` to `false`.

## Dependencies

You can include dependencies with SQL scripts. Dependencies are included in the package using sub-folders. Sub-folders that have the same name as the script (without the file extension) are uploaded to the target machine with the scripts in the sub-folder. The main script can then execute the dependent scripts in the same connection.

Common dependencies that are placed in a sub-folder called `common` are available to all scripts.

For example, this is a ZIP file containing Oracle scripts:

	mysqlfolder
	|
	|__ 01-CreateTable.sql
	|
	|__ 02-CreateUser.sql
	|
	|__ 02-CreateUser
	|   |  
	|   |__ create_admin_users.sql
	|   |
	|   |__ create_power_users.sql
	|
	|__ common
    	|
    	|__ some_other_util.sql
    	|
    	|__ some_resource.properties

The `02-CreateUser.sql` script can use its dependencies or common dependencies as follows:

	--
	-- 02-CreateUser.sql
	--

	INSERT INTO person2 (id, firstname, lastname) VALUES (1, 'xebialabs1', 'user1');
	-- Execute a common dependency
	@common/some_other_util.sql
	-- Execute script-specific dependency: Create Admin Users
	@02-CreateUser/create_admin_users.sql
	-- Execute script-specific dependency: Create Power Users
	@02-CreateUser/create_power_users.sql
	COMMIT;
	
*Note:* The syntax for including the dependant scripts varies between databases. For example, MS SQL databases use `include <script file name>`.

## SQL client

`sql.SqlClient` CIs are containers to which `sql.SqlScripts` can be deployed. The plugin ships with SqlClient for the following databases:

* MySQL
* PostgreSQL
* Oracle
* MS SQL
* DB/2

When SQL scripts are deployed to an SQL client, each script to be executed is run against the SQL client in turn. The SQL client can be configured with a username and password that is used to connect to the database. The credentials can be overridden on each SQL script if required.

## Use in deployment packages

The following is a manifest snippet that shows how SQL file and folder CIs can be included in a deployment package. The SQL scripts CI refers to a folder, `sql`, in the deployment package.

    <udm.DeploymentPackage version="2.0" application="PetClinic-ear">
    	<jee.Ear name="PetClinic" file="PetClinic-2.0.ear"/>
    	<sql.SqlScripts name="sql" file="sql" />
    </udm.DeploymentPackage>
