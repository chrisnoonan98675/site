---
title: Upgrade Manual
---

## Upgrade process ##

Overall, the upgrade process consists of the following steps:

1. Obtain a new version of the XL Deploy software (the main product and/or plugins) from XebiaLabs.
1. Read the new version's **release notes** so you are aware of the new functionality and possible upgrade considerations.
1. Read the new version's **upgrade manual** (this document) so you are aware of possible upgrade considerations.
1. Stop the current version of XL Deploy if it is running and ensure that there are no running tasks active.
1. Create a new installation directory for the new version of XL Deploy, so the current version is still available in case of problems.
1. Extract the new XL Deploy release into the new installation directory.
1. Copy the data from the previous XL Deploy installation directory to the new installation directory.
1. Start the new version of XL Deploy.

See [**Performing the Upgrade**](#performing-the-upgrade) below for a detailed explanation of these steps.

## Upgrade notes ##

* You can skip XL Deploy versions when upgrading. XL Deploy will sequentially apply any upgrades for the intermediate (skipped) versions. **Please read the specific upgrade instructions for each version carefully.**
* If a repository upgrade is required, XL Deploy will detect that it is running against an old repository and will automatically execute an upgrade when it is first started. The server log will contain extensive logging of the repository upgrade process. You may want to save this log for future reference.
* The new version of XL Deploy may not be compatible with the current version of your plugins. If this is the case, you must download an updated version of your plugins as well. **Please read the specific upgrade instructions for each version carefully.**
* Plugin versions are related to the version of XL Deploy (or Deployit) that they are compatible with. For example, WAS plugin version 3.6.0 requires **at least** Deployit server version 3.6.0. This version of the WAS plugin should also work in Deployit 3.7.0 unless stated otherwise in this document or in the release notes.

## Deprecations ##

Each new version may *deprecate* some old functionality or features in favor of newer ways of working. If functionality is marked as deprecated for a specific version, the old functionality is still available (so you can still upgrade hassle-free), but it will be removed in the *next* version.

Every deprecation will be accompanied by a description of how to migrate to the new way of working. This gives you the time and opportunity to migrate to the new situation before upgrading to a still newer version that will no longer have the old functionality. Be sure to read the deprecation sections for each release you're upgrading to, so you will know what will change in upcoming versions.

## Performing the upgrade ##

Before upgrading, carefully read the [Release Notes](releasenotes.html) and note anything that may apply to your situation.

To begin upgrading XL Deploy, first unpack the distribution archive, which contains:

* A server archive
* A CLI archive

### Upgrading the server ###

To upgrade an XL Deploy server installation:

1. Extract the server archive. It creates an installation directory called, for example, `xl-deploy-4.0.0-server`.
1. Stop the Deployit/XL Deploy server.
1. Copy the contents of the `conf` directory from the previous installation to the new installation directory.
1. If necessary, update the product license (`conf/deployit-license.lic`). You can download your current licenses from [https://tech.xebialabs.com/download/license](https://tech.xebialabs.com/download/license).
1. Copy the `repository` directory from the previous installation to the new installation directory.
1. Copy the content of the `importablePackages` directory from the previous installation to the new installation directory.
1. Copy the content of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version). Also refer to the version-specific upgrade notes for plugin incompatibilities.
1. Copy the content of the `ext` directory from the previous installation to the new installation directory.
1. **DO NOT** copy the content of the `hotfix` directory (unless instructed) because hotfixes are version-specific.
1. If you added libraries to XL Deploy's `lib` directory (such as database drivers), copy the additional libraries from the previous installation to the new installation directory.
1. If you have made any changes to the XL Deploy server startup scripts (`server.sh` or `server.cmd`), manually re-do these changes in the new installation directory.
1. **Verify that libraries in the `lib` folder do not also appear in the `plugins` directory, even if their versions are different.** For example, if `lib` contains a `guava-16.0.1.jar` then the `plugins` directory should not contain any `guava-x.x.x.jar` file (such as `guava-13.0.jar`). In this case, you must remove the library from the `plugins` directory. For example: <br/> ![example](images/duplicate-libs-example.png)
1. **Start the XL Deploy server interactively to allow any automatic repository upgraders to run.**
1. If you normally run the XL Deploy server as a service, stop it again and start it as you normally do.

**Note:** Ensure that the plugins and extensions in the previous XL Deploy installation are compatible with the new XL Deploy server version.

This completes the upgrade of the XL Deploy server.

### Upgrading the CLI ###

To upgrade an existing XL Deploy CLI installation:

1. Create a directory for the new XL Deploy CLI installation, including the new XL Deploy CLI version number in the directory name.
2. Extract the CLI archive in this directory.
2. Copy the content of the `plugins` directory from the previous installation to the new installation directory (unless new versions of your plugins were provided with the new XL Deploy version).
3. Copy the content of the `ext` directory from the previous installation to the new installation directory.
4. **DO NOT** copy the content of the hotfix directory (unless instructed) because hotfixes are version-specific.
5. If you have made any changes to the XL Deploy CLI startup scripts (`cli.sh` or `cli.cmd`), copy these from the `bin` directory in the previous installation to the new installation directory.

This completes the upgrade of the XL Deploy CLI.

## Specific upgrade notes ##

This section describes specific considerations for migrating from or to a particular XL Deploy/Deployit version:

* [Upgrading to XL Deploy 4.5.0](#upgrade_to_450)
* [Upgrading to XL Deploy 4.0.0](#upgrade_to_400)
* [Upgrading to Deployit 3.9.90](#upgrade_to_3990)
* [Upgrading to Deployit 3.9.4](#upgrade_to_394)
* [Upgrading to Deployit 3.9.3](#upgrade_to_393)
* [Upgrading to Deployit 3.9.2](#upgrade_to_392)
* [Upgrading to Deployit 3.9.0](#upgrade_to_39)
* [Upgrading to Deployit 3.8.4](#upgrade_to_384)
* [Upgrading to Deployit 3.8.3](#upgrade_to_383)
* [Upgrading to Deployit 3.8.0](#upgrade_to_38)
* [Upgrading to Deployit 3.7.0](#upgrade_to_37)
* [Upgrading to Deployit 3.6.0](#upgrade_to_36)
* [Upgrading from Deployit 3.0.x or earlier](#upgrade_from_30_or_earlier)

<a name="upgrade_to_450"></a>
### Upgrading to XL Deploy 4.5.0 ###

#### Increased PermGen space

The Permanent Generation memory (`PermGen`) allocation for XL Deploy has been increased. The default value of `PermGen` was increased from 128 MB to 256 MB. The value is specified in `bin/server.sh` and `bin/server.cmd`.

#### Exposing password properties in exported package

Before XL Deploy 4.5.0, a package export showed all password properties of deployables in an unencrypted and unobfuscated form.
This is no longer the case. Currently, the default is only to export password properties whose values looks like a placeholder
(e.g. `{{PASSWORD}}`) and to remove the value for the passwords that are not using placeholders.

To control whether password properties should be exported, you can use the following two properties:

* `udm.Version.exportAllPasswords`: If set to `true`, all passwords will be exported. Its default value is `false`,
that means no passwords are exported by default.
* `udm.Version.exportOnlyPasswordPlaceholders`: If set to `true`, only passwords that looks like placeholders (i.e. starting and
ending with double curly braces) will be exported, and the rest will be exported as empty string. Its default value is `true`.

You can change the default values of these properties using `conf/deployit-defaults.properties` file.

If passwords were removed from the exported DAR archive, you will not be able to import it again, as password properties are by default required properties.
To fix this you can choose one of the following solutions:

* Use placeholders instead of actual values for the passwords, as placeholders are by default kept as they are in the exported package. This is the recommended practice.
* Set `udm.Version.exportAllPasswords` to `true` before doing the export. This way, all the passwords are exported to the package manifest.
* Add the missing passwords by manually editing the manifest file from the exported DAR archive.

#### Deprecations in XL Deploy 4.5.0 ####

<style>
.deprecated {
    background-color: #ffdddd;
    border: 1px dotted black;
}

.replacement {
    background-color: #ddffdd;
    border: 1px dotted black;
}

table {
    border: 1px solid black;
}
</style>

This is a list of things that are marked as deprecated and will be removed in the next major version of XL Deploy. 

Legend:

<table>
    <tr><th>Context</th></tr>
    <tr><td class="deprecated">Deprecated item 1</td></tr>
    <tr><td class="replacement">Replacement for deprecated item 1</td></tr>
    <tr><td class="deprecated">Deprecated item 2</td></tr>
    <tr><td class="replacement">Replacement for deprecated item 2</td></tr>
    <tr><td>etc.</td></tr>
</table>

Or

<table>
    <tr><th colspan="2">Context</th></tr>
    <tr>
        <td class="deprecated">Deprecated item 1</td>
        <td class="replacement">Replacement 1</td>
    </tr>
    <tr>
        <td class="deprecated">Deprecated item 2</td>
        <td class="replacement">Replacement 2</td>
    </tr>
    <tr>
        <td>etc.</td>
        <td>etc.</td>
    </tr>
</table>

##### General deprecations

* Support for IE8 and IE9 will be dropped in XL Deploy 5.0.0.
* Support for the <span class="deprecated">`MANIFEST.MF` format</span> for XL Deploy manifests is deprecated. Please migrate to the <span class="replacement">[`deployit-manifest.xml` format](packagingmanual.html#xml-manifest-format)</span>. 
* The following orchestrators are deprecated:
<table>
    <tr>
        <td class="deprecated"><code>default</code></td>
        <td class="replacement">(no orchestrator)</td>
    </tr>
    <tr>
        <td class="deprecated"><code>container-by-container-serial</code></td>
        <td class="replacement"><code>sequential-by-container</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>composite-package</code></td>
        <td class="replacement"><code>sequential-by-composite-package</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>group-based</code></td>
        <td class="replacement"><code>sequential-by-deployment-group</code></td>
    </tr>
</table>

##### Deprecations in REST services

* The REST endpoint `/security/check/{permission}/{ciID}` will no longer support <span class="deprecated">HEAD</span> requests. Instead, use a <span class="replacement">GET</span> request, which returns a Boolean value instead of an HTTP status.
* Endpoint <span class="deprecated">`/task`</span> will be removed. Use the <span class="replacement">`/task/v2`</span> endpoint instead.
<!-- TODO: -->
* The related object types <span class="deprecated">`TaskState` and `TaskWithSteps`</span> will be removed and superseded by <span class="replacement">`TaskWithBlock`, `BlockState`, and `StepBlockState`</span>.
* From the `/deployment` endpoint:
<table>
    <tr>
        <td class="deprecated"><code>/deployment/preview</code></td>
        <td class="replacement"><code>/deployment/previewblock</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>/deployment/preview/{stepNumber}</code></td>
        <td class="replacement"><code>/deployment/previewblock/{blockId}/{stepNr}</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>/deployment/generate/all</code></td>
        <td class="replacement"><code>/deployment/prepare/deployeds</code> (note that this has slightly different semantics)</td>
    </tr>
</table>

##### Deprecations in the CLI

* The <span class="deprecated">`tasks`</span> object is deprecated. Use <span class="replacement">`task2`</span> instead.
* The related object types <span class="deprecated">`TaskState` and `TaskWithSteps`</span> will be removed and superseded by <span class="replacement">`TaskWithBlock`, `BlockState`, and `StepBlockState`</span>.
* From the `deployment` object:
<table>
    <tr>
        <td class="deprecated"><code>deployment.taskPreview(Deployment deployment)</code></td>
        <td class="replacement"><code>deployment.taskPreviewBlock(Deployment deployment)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.stepPreview(Deployment deployment, Integer stepNumber)</code></td>
        <td class="replacement"><code>deployment.taskPreviewBlock(Deployment deployment, String blockId, Integer stepNumber)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.generateAllDeployments(Deployment deployment)</code></td>
        <td class="replacement"><code>deployment.prepareAutoDeployeds(Deployment deployment) (note that this has slightly different semantics)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.deploy(Deployment deployment)</code></td>
        <td class="replacement"><code>deployment.getDeployTask(Deployment deployment)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.undeploy(String deployedApplication)</code></td>
        <td class="replacement"><code>deployment.getUndeployTask(String deployedApplication)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.rollbackTask(String taskId)</code></td>
        <td class="replacement"><code>deployment.getRollbackTask(String taskId)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployment.createTask(Deployment deployment)</code></td>
        <td class="replacement"><code>deployment.getDeployTask(Deployment deployment).getId()</code></td>
    </tr>
</table>

* From the `repository` object:
<table>
    <tr>
        <td class="deprecated"><code>repository.getArchivedTasks()</code></td>
        <td class="replacement"><code>repository.getArchivedTasksList()</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>repository.getArchivedTasks(String beginDate, String endDate)</code></td>
        <td class="replacement"><code>repository.getArchivedTasksList(String beginDate, String endDate)</code></td>
    </tr>
</table>

* From the `deployit` object, the following calls will be deprecated:
<table>
    <tr>
        <td class="deprecated"><code>deployit.retrieveTaskInfo(String taskId)</code></td>
        <td class="replacement"><code>task2.get(String taskId)</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployit.listUnfinishedTasks()</code></td>
        <td class="replacement"><code>task2.getMyCurrentTasks()</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployit.listAllUnfinishedTasks()</code></td>
        <td class="replacement"><code>task2.getAllCurrentTasks()</code></td>
    </tr>
    <tr>
        <td class="deprecated"><code>deployit.discover(ConfigurationItem ci)</code></td>
        <td class="replacement"><code>deployit.createDiscoveryTask()</code> together with <code>deployit.retrieveDiscoveryResults()</code></td>
    </tr>
</table>

##### Deprecations in plugins

* The following FreeMarker-related methods will be deprecated from CIs in the Generic plugin:
<table>
    <tr><th>Namespace <code>com.xebialabs.deployit.plugin.generic.freemarker</code></th></tr>
    <tr><td class="deprecated">constructor <code>CiAwareObjectWrapper(ArtifactUploader uploader)</code></td></tr>
    <tr><td class="replacement">constructor <code>CiAwareObjectWrapper(ArtifactUploader uploader, boolean maskPasswords)</code></td></tr>
    <tr><td class="deprecated">constructor <code>CiTemplateModel(ConfigurationItem ci, CiAwareObjectWrapper wrapper)</code></td></tr>
    <tr><td class="replacement">constructor <code>CiTemplateModel(ConfigurationItem ci, CiAwareObjectWrapper wrapper, boolean maskPasswords)</code></td></tr>
</table>

* From the Remoting plugin:
<table>
    <tr><th>Namespace `com.xebialabs.deployit.plugin.overthere</code></th></tr>
    <tr><td class="deprecated">class <code>AutoFlushingExecutionContextOverthereProcessOutputHandler</code></td></tr>
    <tr><td class="replacement">class <code>DefaultExecutionOutputHandler</code></td></tr>
    <tr><td class="deprecated">class <code>ExecutionContextOverthereProcessOutputHandler</code></td></tr>
    <tr><td class="replacement">class <code>DefaultExecutionOutputHandler</code> (also)</td></tr>
</table>

##### Deprecations relevant for extenders

* If you have written Java Contributors, PrePlanProcessors, or PostPlanProcessors, these need to be static methods.
* For control tasks, the `generic` delegate type is deprecated. Use `shellScript` instead.
* All Java-backed control task delegates need to be static methods.
* You can no longer use <span class="deprecated">`getSyntheticProperties(...)`, `putSyntheticProperties(...)`, or `setSyntheticProperties(...)`</span> on CIs. Use <span class="replacement">`getProperty()`</span>, etc.
* Every custom CI will be required to extend `BaseConfigurationItem` (or a subclass thereof).
* If you are writing your own Steps, note that in the Generic plugin's `BaseStep` class:
<table>
    <tr><td class="deprecated">method `evaluateTemplate(String templatePath, Map<String, Object> vars)`</td></tr>
    <tr><td class="replacement">method `evaluateTemplate(String templatePath, Map<String, Object> vars, boolean maskPasswords`</td></tr>
</table>
* The namespace `com.xebialabs.deployit.plugin.api.execution` is deprecated:
<table>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.execution.Step`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.plugin.api.flow.Step`</td></tr>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.execution.ExecutionContext`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.plugin.api.flow.ExecutionContext`</td></tr>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.execution.ExecutionListener`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.engine.spi.execution.ExecutionStateListener`</td></tr>
</table>
* The namespace `com.xebialabs.deployit.plugin.api.deployment.execution` is deprecated:
<table>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.deployment.execution.DeploymentStep`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.plugin.api.flow.Step`</td></tr>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.deployment.execution.DeploymentExecutionContext`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.plugin.api.flow.ExecutionContext`</td></tr>
</table>    
* If you have implemented your own orchestrators, note that all constructors without the `description` argument are deprecated. Use the variants that include them as their first arguments. This holds for these classes:
    * `CompositeOrchestration`
    * `InterleavedOrchestration`
    * `ParallelOrchestration`
    * `SerialOrchestration`
    * ... and their factory methods in the `Orchestrations` class
* If you have written your own importer, in the `ImportedPackage` class, note that:
<table>
    <tr><th>In class `com.xebialabs.deployit.server.api.importer.Importer`</th></tr>
    <tr><td class="deprecated">constructor `ImportedPackage(PackageInfo packageInfo)`</td></tr>
    <tr><td class="replacement">constructor `ImportedPackage(PackageInfo packageInfo, Application application, Version version)`</td></tr>
    <tr><td class="deprecated">method `getDeploymentPackage()`</td></tr>
    <tr><td class="replacement">method `getVersion()`</td></tr>
</table>
* When creating new CIs, note that:
<table>
    <tr><td class="deprecated">`com.xebialabs.deployit.plugin.api.reflect.Descriptor.newInstance()`</td></tr>
    <tr><td class="replacement">`com.xebialabs.deployit.plugin.api.reflect.Descriptor.newInstance(String id)`</td></tr>
</table>    
* The following Java classes have been deprecated: <!-- TODO alternatives pending decision -->
    * <span class="deprecated">`com.xebialabs.deployit.cli.api.legacy.FullTaskInfo`</span>
    * <span class="deprecated">`com.xebialabs.deployit.cli.api.legacy.FullTaskInfos`</span>
    * <span class="deprecated">`com.xebialabs.deployit.cli.api.legacy.StepInfo`</span>
    * <span class="deprecated">`com.xebialabs.deployit.cli.api.legacy.TaskInfo`</span>

<!-- TODO inspection subsystem -->
<!-- TODO execution.Step's Result enum still seems to be used for inspection -->

<a name="upgrade_to_400"></a>
### Upgrading to XL Deploy 4.0.0 ###

#### New license necessary

We made changes to the license system as a result a new license file is needed to run XL Deploy. You can download your license from [https://tech.xebialabs.com/download/license](https://tech.xebialabs.com/download/license). The license needs to be installed into the `conf` directory.

**Note**: Ensure the license file is not changed in any way, that will corrupt the license. Some mail programs may corrupt the license file unless wrapped in a ZIP.

#### Java 7

XL Deploy requires Java 7 to run. 

#### Plugin compatibility

The following old plugins are **not** compatible with XL Deploy 4.0.0:

* glassfish-plugin 3.9.x
* ec2-plugin 3.9.x
* cloud-plugin 3.9.x
* wls-plugin 3.9.x
* jbossdm-plugin 3.9.x
* wp-plugin 3.9.x
* was-plugin-extensions 3.9.x

#### Use of `cp`, `tar`, `copy` and `xcopy`

XL Deploy 4.0.0 uses version 2.4.2 of the remoting framework [Overthere](https://github.com/xebialabs/overthere) that introduces a new feature to copy files on a remote machine in an efficient manner. Overthere 2.4.2 does this by using the following commands to copy file and directories (where `{0}` is replaced with the path of the source file or directory and `{1}` is replaced with the path of the target file or directory):

* File copy on Unix and z/OS: `cp -p {0} {1}`
* Directory copy on Unix and z/OS:
    * XL Deploy 4.0.0: `tar cC {0} . | tar xmC {1} .`
    * XL Deploy 4.0.1 and up: `cd {1} ; tar -cf - -C {0} . | tar xpf -` 
* File copy on Windows: `copy {0} {1} /y`
* Directory copy on Windows: `xcopy {0} {1} /i /y /s /e /h /q`

This functionality is used in three places in XL Deploy:

* When a file or directory is staged, it will be  uploaded to the staging directory at the start of the deployment and then, when a step uses it, it will be copied to the target directory or the temporary directory (depending on the plugin) using one the commands specified above.
* The behavior of the [File Plugin](filePluginManual.html) has been modified to more atomic:
    1. If staging is enabled, the file or directory will be uploaded to the staging directory at the beginning of the deployment and then copied from the staging directory to the target directory (using one the commands specified above) during the actual deployment.
    1. If staging is not enabled, the file or directory will be uploaded to the temporary directory and immediately copied from there to the target directory (using one the commands specified above) in a single step.
* The behavior of the [generic.CopiedArtifact type of the Generic Model Plugin](genericPluginManual.html#copied-artifact) has been modified in a similar manner. This means all plugins that extend this behavior, e.g. the Tomcat Plugin or the JBossAS plugin for 5.x and 6.x, are also affected by this.

Prior to XL Deploy 4.0.0, the behavior of the File Plugin and of generic.CopiedArtifact was to copy the file or directory directly to the target path. From XL Deploy 4.0.1 and up, this behavior can be re-enabled by setting the hidden property `copyDirectlyToTargetPath` to `true`

For example, to revert the File Plugin to its pre-4.0.0 behavior, add the following line to the `conf/deployit-defaults.properties` file and restart the XL Deploy server:

```
file.DeployedArtifactOnHost.copyDirectlyToTargetPath=true
```

#### Orchestrators

The kind of the `orchestrator` property on the `udm.DeployedApplication` type has been changed from STRING to LIST_OF_STRING, because it is now possible to compose orchestrators. If you have CLI
scripts that assign a single String value to this property this will still work, because it will be put into the list as the only element.
If you have scripts that depend on reading the value, the script must be changed to expect a list of string when reading the property.

#### UI functionality has been moved

The pause button and the skip button in task execution screen were moved into a context menu on the steps.

<a name="deprecations_in_400"></a>
#### Deprecations in XL Deploy 4.0.0 ####

The following orchestrators have been deprecated and will be removed in an upcoming (major) release.

* container-by-container-serial, replaced by the sequential-by-container orchestrator.
* composite-package, replaced by the sequential-by-composite-package orchestrator.
* group-based, replaced by the sequential-by-deployment-group orchestrator.

<a name="upgrade_to_3990"></a>
### Upgrading to Deployit 3.9.90 ###

#### Java 7 ####

From this version onwards Deployit will require Java 7 to run. All pre-existing plugins are compatible with this version of Java and it should not be needed to rebuild them.

<a name="upgrade_to_394"></a>
### Upgrading to Deployit 3.9.4 ###

#### Support for deployment package level properties ####

Support for deployment package level properties has been added in Deployit 3.9.4 to make it possible to set values for deployment properties from the `udm.DeploymentPackage`.

#### Support for native Windows WinRM ####

Support for the native Windows WinRM implementation, i.e. the `winrs` command, has been implemented in Deployit 3.9.4 to make the setup of WinRM easier for users running the Deployit server on a Windows platform. The existing Java implementation that is internal to Deployit is still available for users running the Deployit server on Unix or Windows.

Because of this, the `WINRM` connection type has been renamed to `WINRM_INTERNAL` and the new `WINRM_NATIVE` connection type can be used to select the native Windows WinRM implementation. The `WINRM_HTTP` and `WINRM_HTTPS` connection types that were duplicates of the `WINRM` connection type have been removed.

Please update any scripts or procedure that create **overthere.CifsHost** CIs will have to be updated to reflect the new connection types:

* Replace `WINRM` with `WINRM_INTERNAL`.
* Replace `WINRM_HTTP` with `WINRM_INTERNAL` and **winrmEnableHttps** set to `false`.
* Replace `WINRM_HTTPS` with `WINRM_INTERNAL` and **winrmEnableHttps** set to `true`.

You do not have to upgrade the contents of your repository. The `UpgradeToRemotingPlugin394` upgrader mentioned below will take care of that.

#### Automatic repository upgrade ####

The first time this version of Deployit is started with a repository created with an older version of Deployit, the following upgraders will automatically upgrade the repository:

* `Deployit394Checksums`: Deployit 3.9 introduced the checksum property which stores SHA-1 checksums of artifacts. The SHA-1 algorithm in some cases generated too short checksum strings. This upgrader will recalculate all incorrect checksums.
* `UpgradeToRemotingPlugin394`: This upgrader converts the connection type properties of all **overthere.CifsHost** properties in the repository as follows:
    * Replaces `WINRM` with `WINRM_INTERNAL`.
    * Replaces `WINRM_HTTP` with `WINRM_INTERNAL` and sets **winrmEnableHttps** to `false`.
    * Replaces `WINRM_HTTPS` with `WINRM_INTERNAL` and sets **winrmEnableHttps** to `true`.
    * Replaces `WINRS` (only available in hotfix versions of Deployit) with `WINRM_NATIVE`.


<a name="upgrade_to_393"></a>
### Upgrading to Deployit 3.9.3 ###

#### Automatic repository upgrade ####

The first time this version of Deployit is started with a repository created with an older version of Deployit, the following upgraders will automatically upgrade the repository:

* `Deployit393ReportsView`: Deployit 3.9.3 introduces the new global `report#view` permission to control how is allowed to view the reports tab. This upgrader will grant all roles that have the `login` permission the `report#view` permission.

<a name="upgrade_to_392"></a>
### Upgrading to Deployit 3.9.2 ###

#### DEPL-4652 ####

An `autoPrepareDeployeds` method has been added to the `deployment` object in the CLI and the corresponding REST API that works as follows:

* For an initial deployment without any deployeds, it is identical to a call to `generateAllDeployeds`.
* For an initial deployment with at least one deployed, the behavior is as below.
* For an update deployment, keep existing mappings but:
    * Add mappings for new deployables, if allowed by types and tags.
    * Add mappings for new containers, if allowed by types and tags.
    * Remove mappings for old deployables, without a warning.

<a name="upgrade_to_39"></a>
### Upgrading to Deployit 3.9.0 ###

#### Upgrading plugins ####

Plugins provided by XebiaLabs for Deployit version 3.8 will work in Deployit 3.9. There is no need to upgrade plugins when you are upgrading to Deployit 3.9.

Custom plugins built in Java against the Deployit plugin API will need to take the following change into account:

* class `ReadOnlyRepository` returned from the `DeploymentPlanningContext.getRepository()` call has been replaced by class `Repository`. The new `Repository` class provides an extension on the original `ReadOnlyRepository` class.

Recompiling the plugin Java source code against the new plugin API should bring your plugin up-to-date.

#### Checksum property ####

In Deployit 3.9, a new system property called `checksum` has been introduced on configuration items of type `udm.BaseDeployableArtifact`. This property influences how Deployit will calculate upgrades of deployments (See: [Packaging Manual](packagingmanual.html#artifact_comparison)). If you've previously defined a property called `checksum` on your configuration items, please rename it to avoid clashes with the newly introduced `checksum` property.

#### Importing (old style manifests) ####

On importing it is now possible to pass in a `CI-Name` for non-artifact configuration items. The behavior for both Artifacts and non-Artifacts is now the same, both will be stored under the `CI-Name` value if it is given, and fallback to the Manifest entry name `Name`.

<a name="upgrade_to_384"></a>
### Upgrading to Deployit 3.8.4 ###

#### DEPLOYITPB-4188 ####

In versions of Deployit prior to 3.8.4, the [envVars](http://docs.xebialabs.com/releases/3.8/deployit/genericPluginManual.html#genericcontainer) property on the `generic.Container` type of the `generic-plugin` (and therefore also on the `sql.SqlClient` type of the `database-plugin` and its subtypes) was resolved using FreeMarker. From Deployit 3.8.4 onwards this is no longer done as their values should refer to Unix or Windows environment variables which will be resolved by the shell.

<a name="upgrade_to_383"></a>
### Upgrading to Deployit 3.8.3 ###

#### Automatic repository upgrade ####

The first time this version of Deployit is started with a repository created with an older version of Deployit, the following upgraders will automatically upgrade the repository:

* `Deployit383AclClear`: The upgrader that runs when upgrading from a pre-3.7.0 version of Deployit to a version below Deployit 3.9.3 (Deployit37Security, see below) left behind incorrect `rep:policy` nodes in the repository (DEPLOYITPB-4141). This upgrader removes those nodes.

<a name="upgrade_to_38"></a>
### Upgrading to Deployit 3.8.0 ###

#### Automatic repository upgrade ####

The first time this version of Deployit is started with a repository created with an older version of Deployit, the following upgraders will automatically upgrade the repository:

* `Deployit38ConfigurationRoot`: This upgrader creates the new `Configuration` root that is supported by Deployit 3.8.0 and up.

#### Release Dashboard ####

The Release Dashboard feature has changed to make it easy to reuse a deployment pipeline for many applications. A new CI has been introduced, _release.DeploymentPipeline_ which is stored under the new _Configuration_ root node. Each application that should be used in the Release Dashboard can link to the required deployment pipeline.

When starting Deployit 3.8 for the first time, any deployment pipelines configured in 3.7 will be converted to 3.8 deployment pipeline CIs.

#### Placeholder scanning ####

Placeholder scanning in archives (EAR, WAR, etc.) is now **disabled** by default. To enable it, edit `deployit-defaults.properties` and add the following line:

    udm.BaseDeployableArchiveArtifact.scanPlaceholders=true

#### Custom Java plugins ####

If you have custom Java plugins written against the Deployit 3.7 API, you may need to make some changes to take advantage of the new features in Deployit 3.8.

Deployit 3.8 has changed the API to manage deployments and tasks. Java plugins that use the UDM Plugin-API from Deployit 3.7 will continue to work using a legacy layer. However, if your Java plugin extends classes from the Generic plugin or Python plugin, you may need to update your Java code, since these plugins now use the new API. Here's a table of replacements (read 'c.x.d.p' as 'com.xebialabs.deployit.plugin')

<table class="deployed-matrix">
<tr>
  <th>Deployit 3.7</th>
  <th>Deployit 3.8</th>
</tr>
<tr>
  <td>c.x.d.p.api.deployment.execution.Step</td>
  <td>c.x.d.p.api.flow.Step</td>
</tr>
<tr>
  <td>c.x.d.p.api.deployment.execution.Step.Result</td>
  <td>c.x.d.p.api.flow.StepExitCode</td>
</tr>
<tr>
  <td>c.x.d.p.api.deployment.execution.DeploymentStep</td>
  <td>c.x.d.p.api.flow.Step</td>
</tr>
<tr>
  <td>c.x.d.p.api.deployment.execution.DeploymentExecutionContext</td>
  <td>c.x.d.p.api.flow.ExecutionContext</td>
</tr>
<tr>
  <td>c.x.d.p.api.inspection.InspectionExecutionContext</td>
  <td>c.x.d.p.api.flow.ExecutionContext</td>
</tr>
<tr>
  <td>c.x.d.p.api.inspection.InspectionPlanningContext</td>
  <td>c.x.d.p.api.inspection.InspectionContext</td>
</tr>
<tr>
  <td>c.x.d.p.overthere.ExecutionContextOverthereProcessOutputHandler</td>
  <td>c.x.d.p.overthere.DefaultProcessOutputHandler</td>
</tr>
</table>

<a name="upgrade_to_37"></a>
### Upgrading to Deployit 3.7.0 ###

* Deployit 3.7 contains an updated security implementation. Data from a pre-3.7 repository will automatically be converted to the new security model. See the section below for a complete description of the security upgrade.
* New versions of the following plugins are needed:
	* WAS
	* WLS
	* Tomcat
	* JBoss

#### Security upgrade ####

**Permissions and repository**

Starting with Deployit 3.7, Deployit needs to know the admin user's password to access the repository. This must be entered in the `deployit.conf` file prior to starting the new Deployit server. Note that the password in the configuration file is encrypted by the Deployit server when it starts.

The main change in the Deployit 3.7 security model is that local security permissions are stored only on root nodes and _core.Directory_ CIs. During the upgrade, Deployit will convert pre-3.7-style permissions to the new permission system and automatically create directories to attach the relevant permissions to. The server log contains a description of the old security information that was found and how this was translated to the new permission system.

Permissions in Deployit are now assigned to **roles** instead of directly to principals. Deployit itself contains the definitions of roles and the principals they map to. Deployit will create a role for every user it finds during the upgrade.

It is also no longer possible to globally grant certain permissions. Permissions _deploy#initial_, _deploy#upgrade_, _deploy#undeploy_, _import#initial_, _import#upgrade_, _task#skip\_step_, _task#move\_step_ can now only be granted on a directory CI. Deployit will convert these global permissions into directory permissions during the upgrade.

Deployit 3.7 no longer allows _deny_ permissions. Instead, permissions should be segregated using the new _core.Directory_ grouping CIs. The _deny_ permissions are not migrated by Deployit and an equivalent configuration must be created manually.

Permission _repo#edit_ no longer allows deleting of packages. From 3.7 onwards, new permission _import#remove_ is required to be allowed to delete packages.

In summary, these are the security changes in Deployit 3.7:

<table class="deployed-matrix">
<tr>
	<th>Feature</th>
	<th>Pre-3.7</th>
	<th>3.7</th>
	<th>Conversion</th>
</tr>
<tr>
	<td>Admin password</td>
	<td>Deployit did not need the administrator password.</td>
	<td>Deployit needs the administrator password.</td>
	<td>The administrator password must be set manually in the deployit.conf file before starting Deployit 3.7.</td>
</tr>
<tr>
	<td>Local permissions defined per CI</td>
	<td>Local permissions can be defined on any CI, if the permission is applicable.</td>
	<td>Local permissions can only be defined on <b>root nodes</b> or <b>core.Directory</b> CIs.</td>
	<td>Deployit automatically migrates permissions from pre-3.7 repositories to 3.7, creating new directory CIs as needed.</td>
</tr>
<tr>
	<td>Permission assignment</td>
	<td>Permissions are assigned to users (when using the repository as user store) or LDAP principals (users or groups).</td>
	<td>Permissions are assigned to <b>roles</b> managed within Deployit.</td>
	<td>Deployit automatically migrates principals from pre-3.7 repositories to 3.7, creating new roles as needed.</td>
</tr>
<tr>
	<td>Permissions that are both global as well as local</td>
	<td>Several permissions (deploy#initial, deploy#upgrade, deploy#undeploy, import#initial, import#upgrade, task#skip_step, task#move_step) can be assigned both globally as well as locally.</td>
	<td>These permissions can <b>only</b> be assigned locally on root nodes or directory CIs.</td>
	<td>Deployit automatically migrates these permissions from pre-3.7 repositories to 3.7.</td>
</tr>
<tr>
	<td><b>deny</b> permissions</td>
	<td>Permissions can explicitly be denied on any level.</td>
	<td>Denying permissions is no longer possible.</td>
	<td>Deployit <b>does not</b> migrate deny permissions. An equivalent security configuration must be created manually after the upgrade.</td>
</tr>
<tr>
	<td>Permission to delete packages</td>
	<td>repo#edit permission allowed deleting of packages.</td>
	<td>New permission <b>import#remove</b> is introduced to allow deleting of packages. Permission repo#edit is still required to edit CIs.</td>
	<td>Deployit automatically assigns import#remove to roles that had repo#edit permission in the pre-3.7 repository.</td>
</tr>
</table>


**LDAP**

Configuration of LDAP security has also changed. The configuration in 3.7 is specified in a file called _deployit-security.xml_. The 3.6-style LDAP configuration in the _jackrabbit-jaas.config_ file can be migrated easily to the 3.7 format. The following table describes the mapping of the 3.6-style LDAP setup to the new 3.7 setup:

<table class="deployed-matrix">
<tr>
	<th>3.6 example</th>
	<th>3.7 XML element</th>
	<th>3.7 XML properties</th>
	<th>3.7 example</th>
</tr>
<tr>
	<td>userProvider="ldap://localhost:389/ou=system"</td>
	<td>security:ldap-server</td>
	<td>url and root properties</td>
	<td>&lt;security:ldap-server id="ldapServer" url="ldap://localhost:389/" root="ou=system"/&gt;</td>
</tr>
<tr>
	<td>userFilter="(&amp;(uid={USERNAME})(objectClass=inetOrgPerson))"</td>
	<td>security:ldap-authentication-provider</td>
	<td>user-search-filter</td>
	<td>user-search-filter="(&amp;(uid={0})(objectClass=inetOrgPerson))"</td>
</tr>
<tr>
	<td>userSearchBase="dc=example,dc=com"</td>
	<td>security:ldap-authentication-provider</td>
	<td>user-search-base</td>
	<td>user-search-base="dc=example,dc=com"</td>
</tr>
<tr>
	<td>groupFilter="(memberUid={USERNAME})"</td>
	<td>security:ldap-authentication-provider</td>
	<td>group-search-filter</td>
	<td>group-search-filter="(memberUid={0})"</td>
</tr>
<tr>
	<td>groupSearchBase="ou=groups,dc=example,dc=com"</td>
	<td>security:ldap-authentication-provider</td>
	<td>group-search-base</td>
	<td>group-search-base="ou=groups,dc=example,dc=com"</td>
</tr>
<tr>
	<td>java.naming.security.principal="cn=admin,dc=example,dc=com"</td>
	<td>security:ldap-server</td>
	<td>manager-dn</td>
	<td>&lt;security:ldap-server id="ldapServer" url="ldap://localhost:389/" manager-dn="cn=admin,dc=example,dc=com" manager-password="secret"/&gt;</td>
</tr>
<tr>
	<td>java.naming.security.credentials="secret"</td>
	<td>security:ldap-server</td>
	<td>manager-password</td>
	<td>&lt;security:ldap-server id="ldapServer" url="ldap://localhost:389/" manager-dn="cn=admin,dc=example,dc=com" manager-password="secret"/&gt;</td>
</tr>
<tr>
	<td>useSSL</td>
	<td>security:ldap-server</td>
	<td>url</td>
	<td>Use the LDAPS protocol in the url, for example: &lt;security:ldap-server id="ldapServer" url="ldaps://localhost:686/"/&gt;</td>
</tr>
<tr>
	<td>principalProvider=com.xebialabs.deployit.security.LdapPrincipalProvider</td>
	<td>n/a</td>
	<td>n/a</td>
	<td>This element is no longer required.</td>
</tr>
</table>

See the **System Administration Manual** for more details on the available LDAP configuration options.

If you used Deployit 3.6 with LDAP, you will have modified the **conf/jackrabbit-repository.xml** file. For 3.7, the _Security_ snippet must be:

	<Security appName="Jackrabbit">
	        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security" />
	        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager" />

	        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
	                <param name="anonymousId" value="anonymous" />
	                <param name="adminId" value="admin" />
	        </LoginModule>
	</Security>

#### Additional upgrade steps ####

After completing the upgrade procedure described in **Upgrading the Server** above, perform the following additional steps:

1. Edit the `deployit.conf` file and enter the admin password to property `admin.password`.
2. If you use LDAP, migrate the LDAP configuration to the _deployit-security.xml_ configuration file.
3. Create a backup of your repository.
4. Start the Deployit server. When the server starts, Deployit will migrate the security information from the 3.6 repository to the 3.7 structure. In this process, Deployit will automatically create a role for each user in Deployit as well as special _core.Directory_ CIs to assign permissions to.
5. Log into Deployit as the admin user and inspect the changes to the repository.
6. Open the Admin tab to create meaningful role assignments.
7. Open the Repository tab to create meaningful directory names.

#### Completing the migration ####

After the Deployit automated upgrade completes, log in to the Deployit GUI with various credentials to ensure that the security setup was correctly migrated. It is now possible to see the security permissions on _directory_ CIs in the Repository Browser.

<a name="upgrade_to_36"></a>
### Upgrading to Deployit 3.6.0 ###

* Deployit 3.6 is a drop-in replacement for Deployit 3.5. No repository migration is needed.
* New versions of the following plugins are needed:
	* WAS plugin
	* WLS plugin

<a name="upgrade_from_30_or_earlier"></a>
### Upgrading from Deployit 3.0.x or earlier ###

Deployit and the plugins were changed extensively in version 3.5. As a consequence, it is **not** possible to automatically migrate from a 3.0 or earlier version to version 3.5 or later. If you want to perform this type of upgrade, please contact the XebiaLabs support team.
