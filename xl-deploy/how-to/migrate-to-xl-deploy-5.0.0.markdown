---
title: Migrate to XL Deploy 5.0.0
categories:
- xl-deploy
subject:
- Upgrade
tags:
- upgrade
- installation
- system administration
since:
- XL Deploy 5.0.0
---

With the release of XL Deploy 5.0.0, a number of [deprecated features](/xl-deploy/5.0.x/upgrademanual.html) will be removed. This article will help you migrate to the new way of using XL Deploy.

## Deployment package manifest format

XL Deploy no longer supports the non-XML manifest format (`MANIFEST.MF`) for deployment packages (DAR files). You should now use the XML-based format (`deployit-manifest.xml`) in packages. The `deployit-manifest.xml` file should be placed in the root folder of the DAR, not in `META-INF`.

### How to convert deprecated manifest to the XML manifest format

XL Deploy does not include a tool to convert deprecated manifests to the XML-based format. However, the XML-based format is supported in integration plugins such as the Maven and Jenkins plugins, so it is possible to generate packages using the XML-based format with these tools.

XL Deploy will always export packages with the XML-based format. This means that you can import a package containing a deprecated manifest file in XL Deploy, and then [export the package](/xl-deploy/how-to/export-a-deployment-package.html) to get an XML-based manifest. However, note that this only works with versions of XL Deploy prior to 5.0.0, because they support the deprecated format.

### Example of a converted manifest file

Example of deprecated format:

    Manifest-Version: 1.0
    Deployit-Package-Format-Version: 1.3
    CI-Application: AnimalZoo-ear
    CI-Version: 4.0

    Name: AnimalZooBE-1.0.ear
    CI-Type: jee.Ear
    CI-Name: AnimalZooBE

    Name: petclinicDS
    CI-Type: example.Datasource
    CI-driver: com.mysql.jdbc.Driver
    CI-settings-EntryKey-1: autoCommit
    CI-settings-EntryValue-1: true

How this looks in the XML-based format:

    <?xml version="1.0" encoding="UTF-8"?>
    <udm.DeploymentPackage version="4.0" application="AnimalZoo-ear">
      <deployables>

        <jee.Ear name="AnimalZooBE" file="AnimalZooBE-1.0.ear" />

        <example.Datasource name="petclinicDS">
          <driver>com.mysql.jdbc.Driver</driver>
          <settings>
            <entry key="autoCommit">true</entry>
          </settings>
        </example.Datasource>

      </deployables>
    </udm.DeploymentPackage>

Notice the following differences in the XML format:

* The deployment package is the root XML element and it includes the application name and version, which are in the preamble in the deprecated format.
* The types of the CIs are encoded in the XML tag.
* CI names are specified with a `name` attribute.
* References to artifacts are specified with `file` attributes.
* The `settings` property that is of kind `MAP_STRING_STRING` is much easier to define with XML because it allows more structure in the format.

For more information about the XML-based format, refer to [XL Deploy manifest format](/xl-deploy/concept/xl-deploy-manifest-format.html).

## Orchestrators

With the release of XL Deploy 5.0.0, a number of deprecated orchestrators have been removed. The functionality has been replaced by new orchestrators:

* `container-by-container-serial`: Use `sequential-by-container` instead
* `composite-package`: Use `sequential-by-composite-package` instead
* `group-based`: Use `sequential-by-deployment-group` instead
* `default`: This is default behavior, so you don't need to set an orchestrator

The [upgrade process](/xl-deploy/how-to/upgrade-xl-deploy.html) will automatically convert any usage of the old orchestrators to the new ones when XL Deploy 5.0.0 starts for the first time. You do not have to manually switch to the new orchestrators.

## The `repository` CLI object

In the CLI `repository` object, two methods have been removed:

* `repository.getArchivedTasks()`
* `repository.getArchivedTasks(String from, String to)`

And two methods have been introduced:

* `repository.getArchivedTasksList()`
* `repository.getArchivedTasksList(String from, String to)`

Because the `TaskInfo` object was also removed, each item in the resulting list will be a wrapper implementing the `TaskWithBlock` interface, augmented with the method `get_step_blocks()`. On a `TaskWithBlock`, you retrieve the application by calling `t.metadata['application']`.

Consider this example, which outputs state of each step of the task. Instead of:
    
    tasks = repository.getArchivedTasks().getTasks()
    for task in tasks:
        steps = task.getSteps()
        for step in steps:
            print str(step.getState())

Now you can write:

    tasks = repository.getArchivedTaskList()
    for task in tasks:
        step_blocks = task.get_step_blocks()
        for step_block in step_blocks:
            for step in step_block.getSteps():
                print str(step.getState())

Also note that in XL Deploy 4.0.0, the structure of tasks was changed from a flat list of steps to a tree-like structure of composite blocks containing other blocks, or step blocks containing steps. You can retrieve a step list for a step block separately using the method call `task2.steps(task.getId(), step_block.getId())`.

For more information, refer to [Retrieving archived tasks from the repository](/xl-deploy/how-to/execute-tasks-from-the-xl-deploy-cli.html#retrieving-archived-tasks-from-the-repository).
