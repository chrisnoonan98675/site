---
title: Release Notes
---

### Introduction

The release notes list the changes, improvements, and bug fixes included in each release of XL Deploy. **For upgrade instructions and important information about API deprecation, refer to the [Upgrade Manual](upgrademanual.html).**

###    Version 4.5.0

#### Major new features

* Configuration compare
    * New configuration comparison option based on XL Deploy's extensible discovery mechanism.
    * Compare multiple live environments, as well as existing items in XL Deploy's repository with live state.

* Rules DSL
    * Rules define the behavior of a plugin.
    * Rules can be used to add behavior to an existing plugin.
    * Rules can be used to change or disable behavior of an existing plugin.
    * Rules can be defined in XML or in Jython. No Java required.
    * XL Deploy ships with a set of standard steps that can be used by rules such as:
        * Upload an artifact to a remote machine.
        * Execute a script on a remote machine.
    * Existing plugins are fully backwards-compatible.

* UI extensions
    * Modular extension capability for the UI.
    * Add new menu items to create your own screens and dashboards.
    * Fully HTML5 based.
    * Also define additional HTTP endpoints to easily add functionality.
    
#### Improvements

* [DEPL-1231] - Delete multiple items at once from the GUI and the CLI
* [DEPL-2586] - Add "close all tabs" button to deployment and repository browsers
* [DEPL-4889] - Allow repository keystore password to be specified without appearing on the command line
* [DEPL-4943] - Expose createdAt, createdBy, lastModifiedAt and lastModifiedBy attributes of configuration items
* [DEPL-5170] - Add support for PostgreSQL
* [DEPL-6004] - Expose limited task information to ExecutionContext
* [DEPL-6030] - Automatically encrypt keystore.password and keystore.keypassword properties in server configuration file
* [DEPL-6382] - Stop cli.cmd on windows from closing the CMD window on error
* [DEPL-6402] - Add tab completion to the XLD CLI
* [DEPL-6423] - Support file placeholders in .tar, .tar.gz, .tgz archives and similar formats
    
#### Bug fixes

* [DEPL-5444] - Tasks do not respond to 'Stop' or 'Abort' commands when STOPPING
* [DEPL-5906] - DAR export exposes passwords
* [DEPL-5908] - Remote booter does not work when XL Deploy is proxied by a server using Server Name Identification
* [DEPL-6011] - In database plugin when using dependencies for rollback scripts the sub-folder are not uploaded to the target host
* [DEPL-6057] - Running the garbage collector results in dead letter
* [DEPL-6089] - Not possible to use placeholder ${deployed.deployable.file} when extending generic.ExecutedScript
* [DEPL-6246] - ConcurrentModificationException when using uploadArtifactData and exposePreviousDeployed
* [DEPL-6285] - Regression in UI on authentication failure
* [DEPL-6363] - Don't allow user to copy passwords from CI properties in the GUI
* [DEPL-6381] - Error setting CI reference on the deployed user interface
* [DEPL-6417] - Freemarker resolver fails to resolve CI property of type list
* [DEPL-6435] - Staging step does not close Overthere connections
* [DEPL-6579] - Tasks do not respond to 'Stop' or 'Abort' commands when FAILING

###    Version 4.0.1


#### Improvements

* [DEPL-5340] - Improve filtering of possible values for a CI, SET_OF_CI or LIST_OF_CI property shown in UI
* [DEPL-5517] - Use filename in Content-Disposition when downloading a package for import
* [DEPL-5668] - Previous button disabled when discovery fails (REGRESSION)
* [DEPL-5735] - Allow file copy buffer size to be configured in Overthere
* [DEPL-5800] - Add a method __contains__ in CI.py to be able to use "IN"
* [DEPL-5856] - Add HTTP access logs
* [DEPL-5730] - Allow user to enable pre-4.0.0 file/folder copy behaviour

#### Bug fixes

* [DEPL-2934] - Wrong times displayed for deployments in "Deployments in date range report"
* [DEPL-3625] - Removed unneeded 'create directory' step in upgrade process
* [DEPL-4936] - Cannot import package that has a CI with an embedded LIST_OF_CI property
* [DEPL-5325] - Python daemon does not work over CIFS/WINRM_NATIVE connections
* [DEPL-5355] - Validate that all application names are unique
* [DEPL-5652] - Unable to downloads DARs from a URL for import
* [DEPL-5669] - Type-modifications are applied in the wrong order
* [DEPL-5731] - Cannot use WINRM_NATIVE with a password containing special characters (&#$!*)
* [DEPL-5789] - Short-circuit copy of directories does not work on AIX
* [DEPL-5845] - Property suPassword on overthere.SshHost is not a password property
* [DEPL-5847] - Python daemon does not work over SSH/SU connections
* [DEPL-5858] - Hidden properties should be ignored by the importer
* [DEPL-5870] - Cannot copy step log in hierarchical task view
* [DEPL-5895] - The db2Home property is not used in the Db2Client script files
* [DEPL-5931] - Staging can leave artifact files in the temporary directory
* [DEPL-5939] - Race-condition match-error in par-tasker
* [DEPL-6003] - taskId, stepDescription and username not added to MDC

###    Version 4.0.0

#### Major new features

* Declarative parallel deployment
    * Deploy to many servers in parallel with the push of a button using your existing packages, environments and plugins.
    * Support for different parallel orchestrators, including "parallel by container", "parallel by composite package" and "parallel by deployment group".
    * Support composition of orchestrators.

* Declarative staging support
    * Check a box to stage files to target servers before the deployment starts.
    * Works out of the box for all of your plugins and deployments.

* Schedule deployments for execution at a later time
    * Prepare a deployment ahead of time and have it executed when load on your application is low.
    * See and interact with scheduled tasks in the task monitor.

#### Changes

* Improved performance and resource utilisation
    * Deployment performance: by parallel deployment and staging, resulting in a decrease of downtime.
    * UI performance

* Removed the ability to manually reorder steps in the deployment plan

* Deployit was rebranded to XL Deploy

* Improved insight by adding control tasks to the reports

* A new connection type, SU, which uses SCP to transfer files. Commands are executed via the `su` command.

* Adding pause steps and skipping steps in the task execution screen moved to a context menu. The old buttons were removed.

* Introduced a new licensing scheme. Old licenses are not compatible. A new license must be installed in `DEPLOYIT_HOME/conf/deployit-license.lic`. Your license can be downloaded from the product download site at [https://tech.xebialabs.com/download/license](https://tech.xebialabs.com/download/license).

* This version of XL Deploy requires Java 7 to run

* Documentation improvements

#### Plugin compatibility

Due to changes in a third-party library, the following plug-ins are not compatible with XL Deploy 4.0.0:

* cloud-plugin 3.9.x
* ec2-plugin 3.9.x
* glassfish-plugin 3.9.x
* jbossdm-plugin 3.9.x
* netscaler-plugin 3.9.x
* was-plugin-extensions 3.9.x
* wls-plugin 3.9.x
* wp-plugin 3.9.x

Updated 4.0.0 versions will be released shortly.

#### Improvements

* [DEPL-4951] - Improve performance of deployment pipeline
* [DEPL-5115] - Don't checkpoint a JCR changeset when doing a dry run
* [DEPL-5116] - Only read CI trees as deeply as needed
* [DEPL-5151] - Make keeping of version history for a type configurable
* [DEPL-5209] - Implement CLI support for scheduling
* [DEPL-5228] - Add REST API to get the effective dictionary
* [DEPL-5284] - Restrict deployment and cloud reports to users that have applicable read permissions
* [DEPL-5341] - Add ability to attach control tasks to "roots", e.g. Applications, Environments, Infrastructure and Configuration
* [DEPL-5372] - Support tar archives for "file.Archive"
* [DEPL-5376] - Support local copies on remote file systems
* [DEPL-5430] - Improve filtering of possible values for a CI, SET_OF_CI or LIST_OF_CI property shown in UI
* [DEPL-5492] - Allow DOMAIN\USERNAME syntax to be used for WINRM_NATIVE connections
* [DEPL-5525] - Allow user to disable/enable optimization of the generated plan

#### Bug fixes

* [DEPL-5152] - LIST_OF_STRING is stored without u{..}
* [DEPL-5206] - Packages in importablePackages directory are locked and cannot be deleted (in windows).
* [DEPL-5319] - CLI cannot upload dar with spaces in file name
* [DEPL-5368] - Cannot upload a directory that is specified in the ClasspathResources property
* [DEPL-5375] - Comma's cannot be escaped in set_of_string resulting in incorrect sequence in freemarker template
* [DEPL-5392] - A failed upload disables the save button
* [DEPL-5395] - Fix backwards compatibility of engine-api with pre 3.9.90 versions
* [DEPL-5400] - CIFS over SSH jumpstation does not work in quick succession
* [DEPL-5418] - When importing folder CI's files are left in temp directory
* [DEPL-5443] - Fix a file descriptor leak when importing packages
* [DEPL-5583] - Race conditions in overthere when running parallel deployment on same host
* [DEPL-5616] - Group orchestrators are installed per default

###    Version 3.9.90

Use of a version in the 4.0.x range is recommended over using this version.

####    Changes
* Support for parallel deployments
* Support for scheduling tasks

####    Improvements
* [DEPL-4951] - Displaying a deployment pipeline is very slow

####    Bug fixes
* [DEPL-5281] - Passwords are displayed in the preview window

###    Version 3.9.4

####    Changes
* [DEPLOYITPB-3828] - Use different credentials to start/stop a container
* [DEPLOYITPB-4662] - Add property to DeployableArtifact that specifies character encoding of the file(s)
* [DEPLOYITPB-4940] - Add winrs-backed implementation of WinRM 

####    Improvements
* [DEPLOYITPB-2157] - Include empty synthetic.xml in the ext directory
* [DEPLOYITPB-3487] - Inject previousDeployed instance in the freemarker context in the generic plugin
* [DEPLOYITPB-3570] - Document that the CLI configuration file must be called "deployit.conf"
* [DEPLOYITPB-3792] - Include overthere documentation in the remote plugin documentation
* [DEPLOYITPB-4256] - Ignore empty tags
* [DEPLOYITPB-4315] - Add support for z/OS
* [DEPLOYITPB-4893] - Set properties of a deployed application from matching properties on a udm.Version (deployment package)
* [DEPLOYITPB-4911] - Give warning for required asContainment properties of SET_OF_CI, LIST_OF_CI
* [DEPLOYITPB-5008] - Run multiple PowerShell scripts in a single PowerShell session
* [DEPLOYITPB-5011] - Improve description of generic containers in generic plugin documentation
* [DEPLOYITPB-5012] - Description of Manual Process has a typo (inststructions)
* [DEPLOYITPB-5050] - Document that values from EncryptedDictionary are not resolved in plaintext properties
* [DEPLOYITPB-5053] - Allow symlinking of cli.sh and server.sh scripts to other locations
* [DEPLOYITPB-5057] - Not possible to specify a proxy for the cli
* [DEPLOYITPB-5059] - Provide more fine-grained control over stopping and starting containers
* [DEPLOYITPB-5072] - Allow static Java methods to be invoked from FreeMarker templates
* [DEPLOYITPB-5073] - Add localShellScript and localShellScripts delegates

####    Bug fixes
* [DEPLOYITPB-4891] - Long names not well visible in Release Dashboard pipelines
* [DEPLOYITPB-4904] - Timer already cancelled
* [DEPLOYITPB-4949] - Username and password not used when deploying to sql.Db2Client
* [DEPLOYITPB-4969] - Do not call validator when optional property is not filled in
* [DEPLOYITPB-4998] - Add ability to connect to DB2 with a specific user
* [DEPLOYITPB-5001] - Update packaging manual with embedded CIs in XML manifest format
* [DEPLOYITPB-5009] - Python daemon fails in some setups since Deployit 3.9.3
* [DEPLOYITPB-5055] - Checksums are written excluding leading zeroes



###    Version 3.9.3

####    Changes
* [DEPLOYITPB-4667] - Removed PDF documentation from the release
* [DEPLOYITPB-4775] - int properties defined in CI's in java cannot be optional. Optional integer properties defined in the synthetic.xml are now properly optional.

####    Improvements
* [DEPLOYITPB-3359] - Allow user to define the repository location during DAR import
* [DEPLOYITPB-3569] - Obfuscate cli.password property in CLI configuration file
* [DEPLOYITPB-4354] - Ask user for confirmation when moving a CI
* [DEPLOYITPB-4800] - Set of CI kind in DIP does not remove item when selected
* [DEPLOYITPB-4808] - Add global permission report#view so access to reports can be restricted
* [DEPLOYITPB-4811] - Add support for "classpathResources" to BaseExtensiblePowerShellDeployed
* [DEPLOYITPB-4859] - Add "executeModifiedScripts" and "executeRollbackForModifiedScripts" properties to ExecutedFolder CI in to support running modified SQL files.
* [DEPLOYITPB-4881] - Set checkpoint after first SQL script executed
* [DEPLOYITPB-4837] - Update bundled-plugin documentation to include examples in XML manifest format

####    Bug fixes
* [DEPLOYITPB-4159] - Python daemon (wsadmin/wlst) does not work with WinRM. Upgraded to Overthere 2.2.2.
* [DEPLOYITPB-4656] - Check connection fails for overthere.SshHost with SUDO connection type
* [DEPLOYITPB-4701] - Tighten permission checks in Task Web Service
* [DEPLOYITPB-4717] - Closing a control task window does not cancel the task
* [DEPLOYITPB-4776] - Deployit does not shut down properly via REST call
* [DEPLOYITPB-4778] - Default admin password is not set in complete setup mode
* [DEPLOYITPB-4799] - User cannot authenticate against LDAP if member of a group containing a special character
* [DEPLOYITPB-4812] - Python daemon does not work over TELNET connections when connecting from a Windows machine
* [DEPLOYITPB-4828] - When no BOM is detected, the default charset is used
* [DEPLOYITPB-4853] - Fix overlapping 'Full Screen' and 'Close Window' icons
* [DEPLOYITPB-4861] - Check connection task succeeds even for non-zero exit code of the command
* [DEPLOYITPB-4883] - Exceptions are not properly printed when a CLI Python script is run using the -f flag
* [DEPLOYITPB-4886] - Fix placeholder replacement in folder artifacts named as x.zip



###    Version 3.9.2

####    Improvements
* [DEPLOYITPB-2306] - Added ability to update a discovered topology
* [DEPLOYITPB-4039] - Added ability to generate a virtual deployable type
* [DEPLOYITPB-4383] - Added ability for a packager to specify the placeholder format in the manifest
* [DEPLOYITPB-4473] - Added a .NET/IIS application in the deployit-server distribution
* [DEPLOYITPB-4530] - Ported "options" approach from powershell-plugin to generic-plugin
* [DEPLOYITPB-4652] - Added autoPrepareDeployeds to CLI, REST API and GUI
* [DEPLOYITPB-4660] - Added detection of UTF-8 and UTF-16 text files with BOMs in deployable artifacts and preserve their encoding during placeholder replacement
* [DEPLOYITPB-4661] - Added detection of UTF-8 and UTF-16 text files with BOMs in the plugin templates and scripts and read them with the correct encoding
* [DEPLOYITPB-4733] - Added a way to find DeployedApplication given Environment and package to REST
* [DEPLOYITPB-4742] - Added ability to resize/maximize balloon window and other popup windows

####    Bug fixes
* [DEPLOYITPB-3381] - Empty tags (tags containing only spaces that are stripped) should not be allowed
* [DEPLOYITPB-4147] - Validations on Embedded deployeds do not work properly in the UI
* [DEPLOYITPB-4619] - NPE when generating a deployed with embedded deployeds for a deployable that has no embedded deployables
* [DEPLOYITPB-4627] - Opening history comparison for deployed throws PathNotFoundException: $configuration.item.type
* [DEPLOYITPB-4633] - Special characters ($ and `) are not escaped in PowerShell strings
* [DEPLOYITPB-4635] - Error or incorrect behaviour on update to package that adds or removes embeddeds
* [DEPLOYITPB-4642] - Old-style 'DeploymentStep' steps causes exception during planning
* [DEPLOYITPB-4664] - Tooltip mentions magic placeholder values <EMPTY> and <IGNORE> but should be <empty> and <ignore>
* [DEPLOYITPB-4705] - Rollback of an update deployment is incorrect
* [DEPLOYITPB-4706] - Duplicate type definition accepted
* [DEPLOYITPB-4718] - MSSQL wrapper script should not add transaction
* [DEPLOYITPB-4725] - Repository Garbage sometimes can not be started (again)
* [DEPLOYITPB-4734] - ItemConflictException when rolling back a deployment with an embedded CI
* [DEPLOYITPB-4739] - RuntimeException: java.lang.ClassCastException [/deployment/validate]: com.xebialabs.deployit.engine.api.dto.ValidatedConfigurationItem cannot be cast to com.xebialabs.deployit.plugin.api.udm.Deployed
* [DEPLOYITPB-4743] - Import breaks with large file.Folder artifacts
* [DEPLOYITPB-4753] - Workdirs are not cleaned up after a task has finished



###    Version 3.9.1

####    Improvements
* [DEPLOYITPB-4603] - Make services.Repository returns artifacts when invoked from a contributor or a step
* [DEPLOYITPB-4604] - Allow nested container to be started, stopped and restarted
* [DEPLOYITPB-4620] - Add ability to send HTML email notifications from trigger plugin
* [DEPLOYITPB-4621] - Add ability to generic Containers to upload classpath and templated resources for stop/start actions
* [DEPLOYITPB-4622] - Add ability to generic control task delegate to manage classpath resources, resolving host and executing multiple steps. 
* [DEPLOYITPB-4625] - Username field does not have focus on login screen

####    Bug fixes
* [DEPLOYITPB-3558] - Execution of PowerShell steps on localhost hangs
* [DEPLOYITPB-4347] - The "Deployments done in a date range" report only shows names
* [DEPLOYITPB-4411] - Import package button not visible when package name is long
* [DEPLOYITPB-4449] - Unnecessary unsaved changes warning when opening editors with large text input fields
* [DEPLOYITPB-4456] - When (de)selecting tabs with long titles, the tab positions jump around a lot
* [DEPLOYITPB-4494] - Error message "Key must have a value" badly worded
* [DEPLOYITPB-4503] - Task reassignment completion message is confusing
* [DEPLOYITPB-4505] - Task monitor is not resized when the browser window is resized
* [DEPLOYITPB-4507] - "Blue arrows" are drawn starting from virtual right hand side of the list of deployables
* [DEPLOYITPB-4509] - No progress indicator shown while task preview is generated
* [DEPLOYITPB-4545] - Buttons on step list (skip,stop,abort, etc.) should be disabled once pressed and processing is busy.
* [DEPLOYITPB-4569] - NPE when using a host template without marker file connection options
* [DEPLOYITPB-4572] - REST API is not backwards compatible with 3.8.x clients
* [DEPLOYITPB-4575] - Task recovery file not removed when task is cancelled
* [DEPLOYITPB-4589] - The CreateOptions & ModifyOptions properties should be hidden
* [DEPLOYITPB-4593] - If excludeFileNamesRegex matches the name of the uploaded ZIP for a folder, nothing is scanned
* [DEPLOYITPB-4598] - Subpermission names (e.g. deploy undeploy) do not render on narrow screens
* [DEPLOYITPB-4602] - sql.SqlScripts doesn't take the destroyOrder into account when destroying
* [DEPLOYITPB-4613] - Generic Plugin - Add ability to resolve set_of_string values to values stored in other properties.
* [DEPLOYITPB-4616] - Pending tasks should be archived to prevent to lose them if the deployit server restarts
* [DEPLOYITPB-4624] - Fixed storing of embeddeds



###    Version 3.9.0

####    Major changes
* Provision new deployment environment in the cloud
* Integration with Team Foundation Server via the TFS plugin
* Plugins for deploying to Windows, IIS and BizTalk
* See how deployment plans are put together with the Plan Analyzer
* Show scripts to be executed in the deployment plan
* Restyled Deployit GUI

####    Improvements
* Model embedded CIs in deployments
* Compare CI revisions in the GUI
* Use placeholders to control naming of deployed CIs
* Duplicate CIs in the Repository
* Specify parameters to control tasks
* Restrict dictionaries to particular containers and applications
* New XML-based manifest format
* Explicitly control artifact versioning by specifying checksums



###    Version 3.8.5

####    Improvements
* [DEPLOYITPB-4231] - Set default of "os" property to "WINDOWS" for overthere.CifsHost
* [DEPLOYITPB-4233] - Set label on property "stopStartOnNoop" to "Stop and start on every deployment"
* [DEPLOYITPB-4235] - Improve PowerShell error handling
* [DEPLOYITPB-4388] - Add ability to deploy Apache configuration files that are in the package

####    Bug fixes
* [DEPLOYITPB-3912] - Add test mail control task to mail.SmtpServer
* [DEPLOYITPB-4067] - MsSqlClient.bat.ftl returns 0 on an incorrect login
* [DEPLOYITPB-4180] - The Deployed Applications in an Environment report queries for the environment name instead of its id
* [DEPLOYITPB-4193] - Property commandLine on DeployedCommand should be required
* [DEPLOYITPB-4262] - Cannot access properties of TaskState objects in the CLI
* [DEPLOYITPB-4284] - UI stores username and password at multiple places
* [DEPLOYITPB-4310] - CLI does not expose enough information when encountering an exception
* [DEPLOYITPB-4330] - Deployit won't start when certain leftovers are present in work
* [DEPLOYITPB-4342] - GUI mishandles empty lists-of-string (#1058)
* [DEPLOYITPB-4360] - CLI import does not work behind reverse proxy
* [DEPLOYITPB-4376] - Garbage collection tasks are not archived
* [DEPLOYITPB-4381] - "Plus" character is lost when creating CIs



###    Version 3.8.4

####    Improvements
* [DEPLOYITPB-3623] - Add "username" and "password" properties to sql.SqlScripts
* [DEPLOYITPB-4058] - Package server.cmd and cli.cmd files as Windows text files
* [DEPLOYITPB-4064] - Add properties for command lines options for sqlcmd
* [DEPLOYITPB-4094] - Allow RemoteBooter clients to specify converters for XStreamReaderWriter
* [DEPLOYITPB-4102] - The UI should support empty values for dictionary keys.
* [DEPLOYITPB-4144] - Deployment object reference and deployment/validate documentation do not indicate where to get validation errors
* [DEPLOYITPB-4211] - Accessing name property on CI from the CLI 
* [DEPLOYITPB-4215] - Make the commands executed for SSH+SCP, SSH+SUDO and SSH+INTERACTIVE_SUDO connections configurable
* [DEPLOYITPB-4216] - Allow stderr to be handled per character (like stdout can)
* [DEPLOYITPB-4230] - Split "check connection" task into two steps

####    Bug fixes
* [DEPLOYITPB-3501] - Default Values for set_of_string are not displayed
* [DEPLOYITPB-3839] - Cannot rename ci when the new name is the end part of the old name
* [DEPLOYITPB-4036] - Boolean property powershell.BaseExtensiblePowerShellDeployed.stopStartOnNoop should not be required.
* [DEPLOYITPB-4048] - Cannot start the CLI on Windows 2003
* [DEPLOYITPB-4051] - The generated description for an UPDATE deployment task says "UPGRADE deployment of ..."
* [DEPLOYITPB-4057] - Error message "PlaceholderS […] doesn't have a value" has one S too many.
* [DEPLOYITPB-4069] - Documentation for database-plugin does not mention support for MS SQL
* [DEPLOYITPB-4112] - EmailNotifications do not require either a body or template path
* [DEPLOYITPB-4143] - /deployment/validate documentation is not correctly indented
* [DEPLOYITPB-4152] - Discovery from CLI fails with error 500: "Task not found"
* [DEPLOYITPB-4157] - Server doesn't start when jbossdm plugin is loaded
* [DEPLOYITPB-4161] - Old style ExecutionContextListener implementations aren't destroyed on context stop
* [DEPLOYITPB-4164] - Placeholder replacement: Skipped JAR files eat up time
* [DEPLOYITPB-4176] - Syntax used to export environment variables does not work in all shells
* [DEPLOYITPB-4181] - LIST_OF_CI & LIST_OF_STRING does not keep the order
* [DEPLOYITPB-4188] - FreeMarker resolution can cause an infinite loop (that cannot be aborted)
* [DEPLOYITPB-4197] - No autodeploy possible after unmapping app with a single deployable
* [DEPLOYITPB-4212] - Entering a new dictionary key/value has leading spaces
* [DEPLOYITPB-4213] - Copying files over CIFS and SFTP is sometimes very slow
* [DEPLOYITPB-4214] - Disable startProcess for WinRM because it does not support streaming stdin and therefore does not support wsadmin/wlst daemons
* [DEPLOYITPB-4259] - MANIFEST.MF in exported DAR should not include placeholders property
* [DEPLOYITPB-4261] - Artifact data is not uploaded for a PowerShell destroy step
* [DEPLOYITPB-4277] - 3.8.3: print ci.name returns 'has no property name'
* [DEPLOYITPB-4278] - help() kills the cli if started with -q option



###    Version 3.8.3

####    Improvements
* [DEPLOYITPB-3374] - Do not trigger MODIFY operation on a CI for changes to tags
* [DEPLOYITPB-3962] - Support an 'everyone' granted-authority assigned to all authenticated users.
* [DEPLOYITPB-4041] - Add support for TLS communication to the SMTP server
* [DEPLOYITPB-4066] - Unhide "username" and "password" on sql.MsSqlClient
* [DEPLOYITPB-4072] - Make the username and password prompts from the CLI look nicer
* [DEPLOYITPB-4025] - Add logging for password failures during startup

####    Bug fixes
* [DEPLOYITPB-3271] - Deployit server starts even though JCR repository cannot be started
* [DEPLOYITPB-3388] - Restart control task on www.ApacheHttpdServer is broken
* [DEPLOYITPB-3910] - Cannot downgrade ExecutedFolder (e.g. SqlScripts) without rollback scripts
* [DEPLOYITPB-3933] - Trigger plugin manual not referenced from doc page
* [DEPLOYITPB-3938] - Clean Deployit installation starts with a WARN message in the log
* [DEPLOYITPB-3971] - Trigger plugin manual refers to 'mail.SmptServer' instead of 'mail.SmtpServer'
* [DEPLOYITPB-3993] - Trigger plugin is not aware of SKIP step state.
* [DEPLOYITPB-4008] - Deleting referenced CI throws: java.io.IOException: Stream closed
* [DEPLOYITPB-4028] - Deployit doesn't start on IBM JDK
* [DEPLOYITPB-4065] - Username and password on sql.ExecutedSqlScripts are not used by MsSqlClient.bat.ftl
* [DEPLOYITPB-4070] - Property databaseName on sql.MsSqlClient should not be required
* [DEPLOYITPB-4085] - "ls -ld ... returned unparseable output" error on SELinux
* [DEPLOYITPB-4088] - Placeholder in properties of kind SET_OF_STRING are not being replaced on upgrade
* [DEPLOYITPB-4091] - Server can not parse XML with date in ISO8601 format
* [DEPLOYITPB-4105] - User can rename the JCR roots
* [DEPLOYITPB-4108] - Querying by modification does not actually restrict returned CIs
* [DEPLOYITPB-4118] - Rollback of update throws NotFoundException for DeployedApplication
* [DEPLOYITPB-4134] - REST API documentation missed "/deployit" prefix.
* [DEPLOYITPB-4141] - Upgrade to 3.7/8 leaves rep:policy nodes in the repository



###    Version 3.8.2

####    Bug fixes
* [DEPLOYITPB-4014] - Bug in UI is requestion for non existing "admim" (typo) user causing errors in server log



###    Version 3.8.1

####    API Changes
* [DEPLOYITPB-3976] - REST API: Replace HEAD /security/check/{permission}/{id:.*?} with GET

####    Improvements
* [DEPLOYITPB-3760] - Add option to disable "keep me logged in" checkbox
* [DEPLOYITPB-3761] - Add option to automatically log out users
* [DEPLOYITPB-3961] - Support '*' and '+' as universal tags in deployables/containers.
* [DEPLOYITPB-3979] - CLI: Add searchByName method that searches the repository
* [DEPLOYITPB-3927] - Remove commons-logging from Deployit server distribution

####    Bug fixes
* [DEPLOYITPB-2222] - Clicking close on tab after initial/upgrade/undeploy does not refresh the deployed applications browser
* [DEPLOYITPB-3779] - Repeated WARN "transient property [jee.War.scanPlaceholders] should not have been persisted."
* [DEPLOYITPB-3844] - file.Folder CIs uploaded as ZIPs via the UI are not expanded during deployment
* [DEPLOYITPB-3845] - Tasks that are restored when the GUI is restarted show up in the wrong workspace
* [DEPLOYITPB-3875] - Recovered task hangs and cannot be aborted
* [DEPLOYITPB-3894] - New values in deployables do not override values on prior deployeds
* [DEPLOYITPB-3906] - NPE when CI reference in MANIFEST.MF point to non-existent entry
* [DEPLOYITPB-3911] - UI shows enabled Rollback, Skip and Pause buttons for recovered control task
* [DEPLOYITPB-3928] - SKIP steps should be gray in UI
* [DEPLOYITPB-3934] - CLI does not display reason for NotFoundException (404)
* [DEPLOYITPB-3935] - Exception while upgrading from 3.6.4 to 3.8
* [DEPLOYITPB-3936] - Subtypes of Overthere.Host do not show the new "terminal" icon
* [DEPLOYITPB-3937] - Cannot find /Configuration when upgrading from 3.6.4 to 3.8
* [DEPLOYITPB-3939] - "compare" discloses values for items in udm.EncryptedDictionary
* [DEPLOYITPB-3943] - new security API does not have a getPermissions function
* [DEPLOYITPB-3945] - Security exception in log when logging in as non-admin
* [DEPLOYITPB-3952] - Username and principal name in different caps are incorrectly remembered
* [DEPLOYITPB-3963] - No more than 25 applications are shown in the application filter select box on the deployment-done-in-a-date-range report parameters screen
* [DEPLOYITPB-3967] - Completed tasks are not archived when you close them with the [X] button
* [DEPLOYITPB-3969] - Application name returned in PackageInfo contains directory information
* [DEPLOYITPB-3975] - Discovery menu in UI gives wrong suggestions for discovery
* [DEPLOYITPB-3977] - CLI should have script name in argv[0] even when arguments absent
* [DEPLOYITPB-3997] - Rollback should use current DeployedApplication's orchestrator and transient properties.



###    Version 3.8.0

####    Major changes
* Automated Rollback functionality for deployment tasks
* Audit log and auditing API
* Encrypted dictionaries
* Discovery of middleware is now available in the Deployit GUI
* Simplify setup of Deployit by discovering WebSphere resources and artifacts in existing environments
* Define and build packages in the Deployit GUI
* Support for JBoss 6 EAP Domain Managers
* Support for WebSphere Process Server
* Authenticate against Windows domains using Kerberos
* Extensive public REST API that exposes the functionality of the Deployit Server 
* Triggers for task or step state changes
* Email notifications
    
####    Improvements
* Dictionaries may refer to other dictionaries
* Deployment pipelines can be shared between applications
* Added support for pause steps / manual steps
* Fine-grained deployment checklist permissions
* Placeholder scanning on archives is disabled by default
* Password encryption key can be initialized when creating a repository
* Task id is shown in Deployit GUI, so tasks started in GUI can be finished in CLI
* Reports can now be filtered on application / environment
* Logs of historic deployments can be shown from the Deployit GUI
* About box in Deployit GUI shows detailed installation information



###    Version 3.7.1

####    Improvements
* [DEPLOYITPB-3292] - Log DictionaryValueExceptions as WARN
* [DEPLOYITPB-3320] - Improve import error handling when placeholder scanning fails

####    Bug fixes
* [DEPLOYITPB-3115] - AD-934: Password revealed during step "Waiting for AFS replication"
* [DEPLOYITPB-3171] - Logger (PermissionEnforcer) is too much verbose 
* [DEPLOYITPB-3241] - unable to delete CI's after migrating to 3.7.0
* [DEPLOYITPB-3242] - Right-click menus do not scroll if the screen is too small
* [DEPLOYITPB-3253] - User with "security edit" and "admin" permissions can't set permissions
* [DEPLOYITPB-3259] - Deployables are not ordered alphabetically in the deployment editor
* [DEPLOYITPB-3260] - Server includes spring-security-ldap version 3.0.5.RELEASE instead of 3.1.0.RELEASE
* [DEPLOYITPB-3261] - Server includes spring-tx version 3.0.6.RELEASE instead of 3.1.1.RELEASE
* [DEPLOYITPB-3263] - Package includes spurious __MACOSX files
* [DEPLOYITPB-3265] - Security permissions printed in the CLI after an upgrade should correctly list local and global permissions
* [DEPLOYITPB-3321] - Admin tab not shown in UI when user has "admin" right because he is a member of a group that has those privs instead of a user
* [DEPLOYITPB-3322] - Release Dashboard: shows saved pipeline of other users of the same browser
* [DEPLOYITPB-3323] - When logging out in the CLI, the previous credentials are remembered and a user can login with the same username and any credentials
* [DEPLOYITPB-3324] - Double clicking on a CompositePackage in release dashboard does not show pipeline for version.
* [DEPLOYITPB-3326] - Deployment pipelines boxes too small to fit timestamped versions generated by Deployit Maven plugin
* [DEPLOYITPB-3338] - Cannot save udm.DeployedApplication entity



###    Version 3.7.0

####    Major changes
* Use the .NET plugin to deploy .NET applications to Microsoft middleware
* The Release Dashboard gives real-time insight into applications’ progress through the release pipeline 
* Combine project components into business service releases using composite packages
* Fine-tune deployment strategy to new middleware stacks and customer environments by defining orchestrators 
* View and edit security permissions in the GUI

####    Bug fixes
* [DEPLOYITPB-792] - Removing repository directory gives errors in server.
* [DEPLOYITPB-1166] - Clicking on buttons or tabs is already possible, even when the GUI is not yet fully loaded
* [DEPLOYITPB-1177] - Remove INFO logging of authorization on each request
* [DEPLOYITPB-1180] - Allow pretty printing of all DTOs in the CLI
* [DEPLOYITPB-1368] - Calling TaskProxy.abort on a stopped task results in an NPE in the core
* [DEPLOYITPB-1402] - Cannot set the Deployit server's bind address via the configuration
* [DEPLOYITPB-1433] - Boolean values are marked as different in compare even if they are the same
* [DEPLOYITPB-1446] - Impossible to *clear* a CI reference via the UI
* [DEPLOYITPB-1601] - In an upgraded mapping , the placeholders are not upgraded if they are not detected 
* [DEPLOYITPB-1612] - SSH SUDO the permissions between the user and the sudo users may not match
* [DEPLOYITPB-1661] - recovery.dat already exists
* [DEPLOYITPB-2123] - Pressing tab in the last cell of the placeholder component should add a new row
* [DEPLOYITPB-2149] - Set-valued properties can contain no members even if "required"
* [DEPLOYITPB-2183] - CLI: invoking print on a instance of com.xebialabs.deployit.core.api.dto.Deployment displays nothing
* [DEPLOYITPB-2192] - OutOfMemory Error when importing packages with large (300MB) file(s) in it
* [DEPLOYITPB-2194] - Cannot import package that has a CI or SET_OF_CIS reference where the referencedType is abstract or an interface
* [DEPLOYITPB-2195] - Cannot import package that has an optional CI reference property that is not filled
* [DEPLOYITPB-2203] - Unable to set value of non-required property to empty (i.e. "")  if it has a default value
* [DEPLOYITPB-2228] - Auto-generated Deployables contain fields of type 'string' for booleans of the corresponding Deployeds
* [DEPLOYITPB-2233] - Retrying a ScriptExecutionStep after initial failure causes an NPE in SshSftpFile:60
* [DEPLOYITPB-2235] - Support a "file" FreeMarker property (generic-plugin) that uploads the artifact file and returns the remote path for all Artifacts, not just BaseDeployableArtifact
* [DEPLOYITPB-2257] - The application should be on read only 
* [DEPLOYITPB-2294] - Missing value in Deployit server startup messages
* [DEPLOYITPB-2383] - When uploading the package, the resteasy framework leaves temporary files
* [DEPLOYITPB-2559] - The -help option is not recognized
* [DEPLOYITPB-2570] - When importing a package that contains an entry with an incorrect name, the error message is not very helpful
* [DEPLOYITPB-2582] - Properties are not shown in the expected order
* [DEPLOYITPB-2626] - Encoding problems with French caracters
* [DEPLOYITPB-2703] - Getting a property's value via PropertyDescriptor.get() method should not return null for Map_String_String and Set_of_? types, but empty Map or Set instead.
* [DEPLOYITPB-2726] - java.lang.IllegalArgumentException: Prefix string too short
* [DEPLOYITPB-2755] - The copied python script doesn't work on Windows host configured with WinRM since the run time is not copied.
* [DEPLOYITPB-2758] - Validation error when saving a CI on the repository browser leads to an XML popup
* [DEPLOYITPB-2804] - The spinners on the Reports tab are active and hogging the CPU.
* [DEPLOYITPB-2813] - Clean script : provide a way to select packages older than 3 months
* [DEPLOYITPB-2820] - When the JCR repository contains nodes of unknown types, no node are returned from a search
* [DEPLOYITPB-2822] - Hidden properties are persisted and persisted values override the values from deployit-defaults.properties
* [DEPLOYITPB-2834] - Spurious NPE when shutting down the server by sending a POST request to the /deployit/server/shutdown resource
* [DEPLOYITPB-2843] - NPE during import
* [DEPLOYITPB-2856] - Generated upgrade deployeds still contains the old deployable data in some cases. 
* [DEPLOYITPB-2857] - Problem using getArchivedTasks(begin,end) in the CLI
* [DEPLOYITPB-2865] - Ghost window with dead buttons appears if there are unresolved placeholders
* [DEPLOYITPB-2872] - Deployment fails with error message: org.apache.lucene.search.BooleanQuery$TooManyClauses: maxClauseCount is set to 1024
* [DEPLOYITPB-2883] - Required hidden values can be set to '' (empty string) using deployit-defaults.properties
* [DEPLOYITPB-2885] - Property values added to deployit-defaults.properties are not inherited
* [DEPLOYITPB-2894] - Archives in generic.Folders processed by TrueZIP for placeholders are not copied to target machine
* [DEPLOYITPB-2902] - Tag-based deployment : unmatched tag forbids manual mapping
* [DEPLOYITPB-2904] - No valid error message on import / no message in GUI
* [DEPLOYITPB-2907] - Uploaded classpathResources for ExecutedScript are not released and cannot be executed
* [DEPLOYITPB-2909] - Type modification that sets a set_of_string property to an empty value (overriding a non-empty parent value) is ignored
* [DEPLOYITPB-2910] - NPE when saving an artifact using CLI
* [DEPLOYITPB-2918] - Unable to import DAR file
* [DEPLOYITPB-2924] - File-not-found during drag-and-drop
* [DEPLOYITPB-2940] - Packages downloaded from a URL are saved as temporary files with missing/invalid extensions
* [DEPLOYITPB-2941] - Faulty repository directory after inital setup on windows
* [DEPLOYITPB-2949] - Jetty Session fails to be invalidated. IllegalException is thrown. As seen during demo.
* [DEPLOYITPB-2956] - When the DN of a user entry contains a comma, it will not receive the permissions granted to the groups it is a member of
* [DEPLOYITPB-2966] - CLI changes control characters
* [DEPLOYITPB-2976] - HTML encoding of dictionary values is wrong
* [DEPLOYITPB-2985] - Dictionary substitution with <empty> does not work
* [DEPLOYITPB-3006] - Inserting a new entry in a resourcebundle after sorting gives problems
* [DEPLOYITPB-3013] - Default deployed property value defined in dictionary
* [DEPLOYITPB-3015] - Modification checking of deployed with a map_string_string property
* [DEPLOYITPB-3017] - Deployed is regarded as modified when a property goes from 'no value' to 'default value'
* [DEPLOYITPB-3067] - Cannot import package with cli if the location is an URL
* [DEPLOYITPB-3074] - Starting the Deployit server takes 40 seconds
* [DEPLOYITPB-3076] - recovery.dat should be removed when no tasks are executing
* [DEPLOYITPB-3084] - Creating two CIs referring to each other (circular dependency) leads to a stackoverflow exception on the server.
* [DEPLOYITPB-3095] - Ensure XML in user input is encoded and not interpreted (WEB-002)
* [DEPLOYITPB-3096] - Ensure user input to queries is filtered (WEB-003)
* [DEPLOYITPB-3099] - Can <generate-deployable> the same type twice
* [DEPLOYITPB-3120] - A subcontext menu is impossible to reach when the context menu is so low that the subcontext menu is shown higher
* [DEPLOYITPB-3124] - Don't include HTML snippets in error messages to prevent XSS attacks (WEB-001)
* [DEPLOYITPB-3125] - Show as root fails with empty environments in Deployed Applications
* [DEPLOYITPB-3128] - Delta-analysis does not see different when adding a STRING to a SET_OF_STRING
* [DEPLOYITPB-3146] - Repository not shut down properly when the server process is terminated
* [DEPLOYITPB-3184] - Actual error not shown when wsadmin/wlst won't start
* [DEPLOYITPB-3185] - Output sent to stderr is sometimes lost when running with the Python daemon
* [DEPLOYITPB-3186] - Spurious "None" printed when an exception is thrown by a script running in the Python daemon
* [DEPLOYITPB-1402] - Cannot set the Deployit server's bind address via the configuration
* [DEPLOYITPB-1731] - When granting the deploy#upgrade permission to a principal, you also need to grant him the read permission on the corresponding Infrastructure.
* [DEPLOYITPB-2149] - Set-valued properties can contain no members even if "required"
* [DEPLOYITPB-2233] - Retrying a ScriptExecutionStep after initial failure causes an NPE in SshSftpFile:60
* [DEPLOYITPB-2294] - Missing value in Deployit server startup messages
* [DEPLOYITPB-2296] - LDAP configuration ignores the 'principalProvider' argument
* [DEPLOYITPB-2302] - LdapPrincipalProvider caches the result of a "get groups for user" query even if the user credentials are invalid
* [DEPLOYITPB-2383] - When uploading the package, the resteasy framework leaves temporary files
* [DEPLOYITPB-2559] - The -help option is not recognized
* [DEPLOYITPB-2570] - When importing a package that contains an entry with an incorrect name, the error message is not very helpful
* [DEPLOYITPB-2626] - Encoding problems with French caracters
* [DEPLOYITPB-2703] - Getting a property's value via PropertyDescriptor.get() method should not return null for Map_String_String and Set_of_? types, but empty Map or Set instead.
* [DEPLOYITPB-2755] - The copied python script doesn't work on Windows host configured with WinRM since the run time is not copied.
* [DEPLOYITPB-2758] - Validation error when saving a CI on the repository browser leads to an XML popup
* [DEPLOYITPB-2804] - The spinners on the Reports tab are active and hogging the CPU.
* [DEPLOYITPB-2820] - When the JCR repository contains nodes of unknown types, no node are returned from a search
* [DEPLOYITPB-2822] - Hidden properties are persisted and persisted values override the values from deployit-defaults.properties
* [DEPLOYITPB-2834] - Spurious NPE when shutting down the server by sending a POST request to the /deployit/server/shutdown resource
* [DEPLOYITPB-2843] - NPE during import
* [DEPLOYITPB-2856] - Generated upgrade deployeds still contains the old deployable data in some cases. 
* [DEPLOYITPB-2857] - Problem using getArchivedTasks(begin,end) in the CLI
* [DEPLOYITPB-2883] - Required hidden values can be set to '' (empty string) using deployit-defaults.properties
* [DEPLOYITPB-2885] - Property values added to deployit-defaults.properties are not inherited
* [DEPLOYITPB-2894] - Archives in generic.Folders processed by TrueZIP for placeholders are not copied to target machine
* [DEPLOYITPB-2907] - Uploaded classpathResources for ExecutedScript are not released and cannot be executed
* [DEPLOYITPB-2909] - Type modification that sets a set_of_string property to an empty value (overriding a non-empty parent value) is ignored
* [DEPLOYITPB-2910] - NPE when saving an artifact using CLI
* [DEPLOYITPB-2940] - Packages downloaded from a URL are saved as temporary files with missing/invalid extensions
* [DEPLOYITPB-2941] - Faulty repository directory after inital setup on windows
* [DEPLOYITPB-2949] - Jetty Session fails to be invalidated. IllegalException is thrown. As seen during demo.
* [DEPLOYITPB-2956] - When the DN of a user entry contains a comma, it will not receive the permissions granted to the groups it is a member of
* [DEPLOYITPB-2966] - CLI changes control characters
* [DEPLOYITPB-2972] - When saving a CI, errors in the server don't show up in the GUI
* [DEPLOYITPB-2976] - HTML encoding of dictionary values is wrong
* [DEPLOYITPB-3006] - Inserting a new entry in a resourcebundle after sorting gives problems
* [DEPLOYITPB-3011] - Security permissions are not enforced when reading multiple CIs
* [DEPLOYITPB-3015] - Modification checking of deployed with a map_string_string property
* [DEPLOYITPB-3017] - Deployed is regarded as modified when a property goes from 'no value' to 'default value'
* [DEPLOYITPB-3026] - NamespaceException: deployit: is not a registered namespace prefix. in \<script\> at line number 1



###    Version 3.6.4

####    Major functional changes
* Upgraded to Overthere 1.0.16:
    * Default value of overthere.SshHost.allocateDefaultPty is now false to prevent problems with OpenSSH on AIX or WinSSHD. Set to true 
    * Added hidden property overthere.SshHost.sudoOverrideUmask that, when set, causes permissions to be explicitly changed with chmod -R go+rX after uploading a file or directory with scp.

####    Improvements
* [DEPLOYITPB-2900] - Surpress the CLI banner when executing a script.
* [DEPLOYITPB-2961] - Make it possible to change the server's context root.

####    Bug fixes
* [DEPLOYITPB-2233] - Retrying a ScriptExecutionStep after initial failure causes an NPE in SshSftpFile:60.
* [DEPLOYITPB-2302] - LdapPrincipalProvider caches the result of a "get groups for user" query even if the user credentials are invalid.
* [DEPLOYITPB-2686] - LDAP user/search base documentation is missing.
* [DEPLOYITPB-2758] - Validation error when saving a CI on the repository browser leads to an XML popup.
* [DEPLOYITPB-2804] - The spinners on the Reports tab are active and hogging the CPU.
* [DEPLOYITPB-2894] - Archives in generic.Folders processed by TrueZIP for placeholders are not copied to target machine.
* [DEPLOYITPB-2902] - Tag-based deployment : unmatched tag forbids manual mapping.
* [DEPLOYITPB-2904] - No valid error message on import / no message in GUI.
* [DEPLOYITPB-2907] - [generic-plugin] Uploaded classpathResources for ExecutedScript are not released and cannot be executed.
* [DEPLOYITPB-2918] - Unable to import DAR file.
* [DEPLOYITPB-2939] - Save packages downloaded from a URL for import under their original filename.
* [DEPLOYITPB-2940] - Packages downloaded from a URL are saved as temporary files with missing/invalid extensions.
* [DEPLOYITPB-2941] - Cannot use absolute directory to store the JCR repository on Windows.
* [DEPLOYITPB-2956] - Fixed bug where a user DN with spaces caused its group membership check to not return anything.
* [DEPLOYITPB-2959] - When the DN of a user entry contains a comma, it will not receive the permissions granted to the groups it is a member of.
* [DEPLOYITPB-2976] - Placeholder are incorrectly replaced with HTML encoded values.
* [DEPLOYITPB-2985] - Dictionary substitution with <empty> does not work.
* [DEPLOYITPB-3010] - Cannot execute existing files (.bat, .exe) on Windows using SSH/Cygwin (e.g. needed by the WAS and WLS plugins).



###    Version 3.6.3

####    Major functional changes

* Repository garbage collection - when Deployit server is shutdown, the repository garbage collector is invoked to reclaim disk space freed up by deleting artifacts from the repository.

####    Improvements
* [DEPLOYITPB-2809] - Garbage collect JCR DataStore

####    Bug fixes
* [DEPLOYITPB-2856] - Generated upgrade deployeds still contains the old deployable data in some cases. 
* [DEPLOYITPB-2857] - Problem using getArchivedTasks(begin,end) in the CLI


###    Version 3.6.2

####    Major functional changes
* Default tty allocation - the setting overthere.SshHost.allocateDefaultPty now defaults to _false_ instead of _true_ since this is the more common case. 

####    Improvements
* [DEPLOYITPB-2301] - Make the LdapPrincipalProvider's group cache timeout configurable
* [DEPLOYITPB-2686] - Support 'userSearchBase' and 'groupSearchBase' options in LdapLoginModule/LdapPrincipalProvider
* [DEPLOYITPB-2836] - Allow user to filter/query CI's based on the lastModified date
* [DEPLOYITPB-2867] - Exclude files from placeholder scanning and replacing using a regular expression

####    Bug fixes
* [DEPLOYITPB-1402] - Cannot set the Deployit server's bind address via the configuration
* [DEPLOYITPB-2282] - deployit dictionary plugin: boolean values are not saved
* [DEPLOYITPB-2296] - LDAP configuration ignores the 'principalProvider' argument
* [DEPLOYITPB-2383] - When uploading the package, the resteasy framework leaves temporary files
* [DEPLOYITPB-2626] - Encoding problems with French caracters
* [DEPLOYITPB-2755] - The copied python script doesn't work on Windows host configured with WinRM since the run time is not copied.
* [DEPLOYITPB-2843] - NPE during import
* [DEPLOYITPB-2865] - Ghost window with dead buttons appears if there are unresolved placeholders
* [DEPLOYITPB-2872] - Deployment fails with error message: org.apache.lucene.search.BooleanQuery$TooManyClauses: maxClauseCount is set to 1024
* [DEPLOYITPB-2873] - Deployit repository retains history of deleted CIs



###    Version 3.6.1

####    Major functional changes
* Upgraded to Overthere 1.0.11



###    Version 3.6.0

####    Bug fixes
* [DEPLOYITPB-601] - The letters in the Deployit logo are not correctly aligned
* [DEPLOYITPB-1137] - upgrade: delete/edit mapping button should be disabled incase of upgrade till the new package is dropped
* [DEPLOYITPB-1164] - Change final server startup message to mention the actual host the server is running on instead of 'localhost'
* [DEPLOYITPB-1178] - Dummy extension file is included in the distribution
* [DEPLOYITPB-1179] - Must be able to issue a command in the CLI to display initial help messages again
* [DEPLOYITPB-1187] - Include CLI command to shutdown server
* [DEPLOYITPB-1197] - Distribution includes a .gitignore file
* [DEPLOYITPB-1223] - repository: Location field should be grayed out in Editing a package artifact
* [DEPLOYITPB-1244] - repository: tab titles should show distinguishing information
* [DEPLOYITPB-1698] - SCP directory copy does not support Windows-to-Unix copies
* [DEPLOYITPB-2209] - Generated CI Reference should indicate which properties are required for discovery (@InspectionProperty)
* [DEPLOYITPB-2258] - Placeholder scanning and replacement only supported in archives with 'correct' (undocumented) extensions
* [DEPLOYITPB-2262] - Cannot undeploy file.File artifact
* [DEPLOYITPB-2267] - SSH/SFTP connectionType : errors when Overthere try to clean the temporary directory
* [DEPLOYITPB-2278] - Bug in the documentation about importer
* [DEPLOYITPB-2282] - deployit dictionary plugin: boolean values are not saved
* [DEPLOYITPB-2293] - Typos in Deployit server startup messages
* [DEPLOYITPB-2297] - Missing documentation for LDAP configuration option 'userAttribute'
* [DEPLOYITPB-2303] - Multiple concurrent login requests at UI startup can lock out a user if invalid credentials are presented
* [DEPLOYITPB-2305] - Unable to import package with a file.Folder CI that contains TrueZIP-readable archives (e.g. JARs)
* [DEPLOYITPB-2550] - Problem with Security Permission : given read access to an environment give full read access
* [DEPLOYITPB-2553] - Missing CLI help documentation for public List<String> search(String ciType, String parent) in RepositoryClient
* [DEPLOYITPB-2569] - Cannot undeploy a application if not steps are generated
* [DEPLOYITPB-2594] - Cannot perform upgrade deployment in UI
* [DEPLOYITPB-2597] - Required validation for SET_OF_STRING broken
* [DEPLOYITPB-2598] - Property tags on udm.Deployable and udm.Container should not be required
* [DEPLOYITPB-2601] - Property schema on sql.OracleClient should be renamed to SID
* [DEPLOYITPB-2606] - Cannot undeploy a package with only SQL files if the user does not provide rollback SQL files
* [DEPLOYITPB-2618] - Execution of control tasks not covered by security permission
* [DEPLOYITPB-2621] - CI property that is transient (@Property(isTransient=true) ) is still stored to the repository.
* [DEPLOYITPB-2637] - "server.sh -setup -reinitialize" should mention it only works for the default configuration.
* [DEPLOYITPB-2640] - Keys and/or values in map_string_string properties are not resolved against the environment dictionaries
* [DEPLOYITPB-2641] - ci and set_of_ci properties are not resolved against the environment dictionaries
* [DEPLOYITPB-2643] - LDAP security is broken: group membership incorrectly determined
* [DEPLOYITPB-2648] - Cannot generate a deployable that extends another generated deployable
* [DEPLOYITPB-2651] - The packaging format for set_of_string contains a superfluous -EntryValue
* [DEPLOYITPB-2655] - Placeholders not being validated on next. Validation errors for placeholders key with no values entered appear as an
alert box. It should appear as validation errors label.Example:  9 error(s) found
* [DEPLOYITPB-2679] - Password are not stored encrypted in the repository
* [DEPLOYITPB-2683] - file property not set on Deployed on undeployment
* [DEPLOYITPB-2684] - Spurious ActionScript error (TypeError) when dragging a deployable to a container
* [DEPLOYITPB-2685] - Jboss plugin start.sh script hangs when executed on localhost
* [DEPLOYITPB-2690] - Deployit 3.5.2 : regression on propagation of default values to sub-types
* [DEPLOYITPB-2693] - Non-Deployment tasks should not be written to the recovery file.
* [DEPLOYITPB-2694] - Placeholder validation throwing exception. See stack trace
* [DEPLOYITPB-2699] - index.html for riatest is not templated to point to the latest swf
* [DEPLOYITPB-2701] - Recovery fails with class not found exception
* [DEPLOYITPB-2704] - Able to define duplicate properties 'id', 'name', 'type', etc
* [DEPLOYITPB-2709] - Top 5 dasboard reports do not show data on last day for month with 31 days.
* [DEPLOYITPB-2711] - Nested objects are no longer found using the base functions in python-plugin
* [DEPLOYITPB-2720] - Creating a deployed by drag and drop should choose the most specific one and if more than one choices are available then only should show the selection popup
* [DEPLOYITPB-2733] - In deployments done in date range report deployments from one day before selected begin date show up on ui.
* [DEPLOYITPB-2734] - "Key Deployment Indicators in a date range" report title should be "Key deployment indicators in a date range"
* [DEPLOYITPB-2747] - Placeholder are not replaced when the deployed is a generic.ExecutedScript

####    Improvements
* [DEPLOYITPB-1527] - CLI : command to display all the available CI's type
* [DEPLOYITPB-2204] - as a deployer, I want to manage the attributes values of the Deployed by the dictionary
* [DEPLOYITPB-2255] - Preserve order in which steps are added to the planning context by a contributor
* [DEPLOYITPB-2285] - Use the ci.py CLI extension to simplify writing integration tests
* [DEPLOYITPB-2632] - Show only the keys of visible properties in the comparison screen
* [DEPLOYITPB-2635] - All packages downloaded from a URL are saved as ".dar" files which confuses additional importers
* [DEPLOYITPB-2636] - Allow placeholder scanning in artifacts to be disabled
* [DEPLOYITPB-2644] - Load Python script when a PythonStep is executed, not when it is created
* [DEPLOYITPB-2670] - Add targetPath property to file.File, file.Folder, file.Archive
* [DEPLOYITPB-2707] - Render checkboxes _in front of_ their labels
* [DEPLOYITPB-2708] - Show full ID and type when hovering over an item in any of the browsers or selection widgets

