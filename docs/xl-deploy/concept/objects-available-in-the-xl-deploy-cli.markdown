---
title: Objects available in the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- ci
- script
weight: 248
---

The XL Deploy command-line interface (CLI) provides objects that you can use to interact with the XL Deploy server.

## The `deployit` object

The `deployit` object provides access to the main functions of XL Deploy itself. It allows you to import a package, work with tasks, and discover middleware.

## The `deployment` object

The `deployment` object provides access to the deployment engine of XL Deploy. Using this object, you can create a task for an initial deployment or upgrade. You execute the created tasks using the `deployit` object.

## The `factory` object

The `factory` object facilitates the creation of new configuration items (CIs) and artifacts (files and packages), to be saved in XL Deploy's repository.

## The `repository` object

The `repository` object allows you to access XL Deploy's repository. It provides Create, Read, Update, and Delete (CRUD) operations on CIs in the repository. It can also export task overviews to a local XML file, export CIs to a ZIP file, or import CIs from a ZIP file.

## The `security` object

The `security` object allows you to log into or out of XL Deploy and enables you to create and delete local users in the repository.

Users with administrative permissions can also grant, deny, or revoke security permissions to other users, even users who are managed in a credentials store other than XL Deploy's repository. By default, users with administrative permissions have *all* permissions that are available in XL Deploy.

## The `tasks` and `task2` objects

The `task2` object gives you access to XL Deploy's task block engine. You can use it to start, stop, abort, archive, or assign tasks. It replaces the deprecated `tasks` object.

## Get help with CLI objects

When you log in to the CLI, a welcome message with information about the objects appears. To see this message again, type `help` and press ENTER.

To get help with a specific object, invoke the `help()` function. This will list all methods on the object that are available for scripting. For example, executing:

    deployit> factory.help()

Returns:

    factory: Helper that can construct Configuration Items (CI) and Artifacts

    The methods available are:
    * factory.artifact(String id, String ciType, Map values, byte[] data) : ArtifactAndData - DEPRECATED
    * factory.artifactAsInputStream(String id, String ciType, Map values, InputStream data) : ArtifactAndData
    * factory.configurationItem(String id, String ciType) : ConfigurationItem
    * factory.configurationItem(String id, String ciType, Map values) : ConfigurationItem
    * factory.types() : void

To get help with a specific method, execute a command such as:

    deployit> security.help('getPermissions')

Note that the name of the method should be enclosed in quotation marks and without parentheses.
