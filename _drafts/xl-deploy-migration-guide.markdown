---
title: XL Deploy migration guide
subject:
- Upgrading
categories:
- xl-deploy
tags:
- migration
---

## Introduction
With the release of XL Deploy 5.0 a number of deprecated features will be removed. This document lists those features and will explain how to migrate to the new way of using XL Deploy. You can already start moving to the new functionality because the new features are already released.

## Dar manifest format
Support for the non-xml manifest format for Dar files that uses a `MANIFEST.MF` file, has been deprecated. You should now use the XML based format and use `deployit-manifest.xml` in your packages. The `deployit-manifest.xml` should be placed in the root folder of the Dar, not in `META-INF`.

### How to convert deprecated manifest to the XML manifest format
We do not provide a tool to convert deprecated manifests to the XML format. We do support the XML manifest format in our integration plugins like the maven or the jenkins plugin, so it is possible to generate packages in the XML format with these tools already.

There is another trick to convert a package to the XML format. XL Deploy will always export packages with the XML format. So you can import the package with an deprecated manifest file in XL Deploy, and then export the package to get. This trick only works with older versions of XL Deploy prior to version 5.0 that still support the deprecated format.

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

How this looks like in the XML format:

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
* The deployment package is the root XML element and includes the application name and version that used to be in the preamble in the deprecated format.
* The types of the CI's are encoded in the xml tag
* CI names are specified with a `name` attribute
* References to artifacts are specified with `file` attributes
* The `settings` property that is of kind `MAP_STRING_STRING` is much easier to define with XML because it allows more structure in the format.

This is just a simple example, there are more differences. You can find the complete reference documentation of the XML format [in the packaging manual](xl-deploy/latest/packagingmanual.html).


## Orchestrators

With the release of XL Deploy 5.0 a number of deprecated orchestrators have been removed. The functionality has been replaced by new orchestrators. The following list will help you to choose any of the new orchestrators:

* `container-by-container-serial`: Now use `sequential-by-container` instead.
* `composite-package`: Now use `sequential-by-composite-package` instead.
* `group-based`: Now use `sequential-by-deployment-group` instead.
* `default`: Do not set any orchestrator anymore. This is default behavior.

Note that there is an upgrader that will automatically convert any usages of the old orchestrators to the new ones when XL Deploy 5.0 boots first time, you do not have to do anything. The list above will help you to make the correct choice in the future.


## The `repository` CLI object

In the CLI `repository` object, two methods have disappeared: `repository.getArchivedTasks()` and `repository.getArchivedTasks(String from, String to)`. Instead there are now the methods `repository.getArchivedTasksList()` and `repository.getArchivedTasksList(String from, String to)`. Since also the `TaskInfo` object disappears, instead each item in the resulting list will be a wrapper implementing the `TaskWithBlock` interface, augmented with the method `get_step_blocks()`. On a `TaskWithBlock` you retrieve the application by calling `t.metadata['application']`.

Consider this example which outputs state of each step of the task. Instead of:
    
    tasks = repository.getArchivedTasks().getTasks()
    for task in tasks:
        steps = task.getSteps()
        for step in steps:
            print str(step.getState())

now you can write:

    tasks = repository.getArchivedTaskList()
    for task in tasks:
        step_blocks = task.get_step_blocks()
        for step_block in step_blocks:
            for step in step_block.getSteps():
                print str(step.getState())

Also note that since 4.0 the structure of tasks was changed from a flat list of steps to a tree-like structure of composite blocks containing other blocks, or step blocks containing steps. A step list for a step block can be separately retrieved using the method call `task2.steps(task.getId(), step_block.getId())`

For more details, see the [relevant section of the CLI manual](xl-deploy/latest/climanual.html#retrieving-archived-tasks-from-the-repository)



































































