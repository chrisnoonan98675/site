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
---

The XL Deploy command-line interface (CLI) provides five objects to interact with the XL Deploy Server: `deployit`, `deployment`, `repository`, `factory` and `security`.

It's also possible to define custom helper objects. A brief description of these four objects and how to define custom helper objects is given below. The next chapters will present examples on how to use these objects for scripting purposes.

## The `deployit` object

The `deployit` object provides access to the main functions of XL Deploy itself. It allows the user to import a package, work with tasks and executing discovery.

## The `deployment` object

The `deployment` object provides access to the deployment engine of XL Deploy. Using this object it is possible to create a task for an initial deployment or upgrade. The created tasks can be executed by using the `deployit` object.

## The `factory` object

The `factory` object facilitates the creation of new configuration items (CIs) and artifacts (files and packages), to be saved in XL Deploy's repository.

## The `repository` object

The `repository` object allows the user to access XL Deploy's repository. It provides Create, Read, Update and Delete (CRUD) operations on Configuration Items (CI) in the repository. It can also export task overviews to a local XML file or export/import configuration items to a ZIP file.

## The `security` object

The `security` object facilitates the logging in or out of XL Deploy and the creation or deletion of users in the XL Deploy repository. Users of XL Deploy may also be administered using another credentials store like a LDAP directory, but creation and deletion of users on these specific stores is not within XL Deploy's scope.

Users with administrative permissions my also grant, deny or revoke security permissions to other users, even those users that are not administered in XL Deploy's repository but in some other credentials store. By default, users with administrative permissions own *all* permissions available in XL Deploy.

## The `tasks` object

The `tasks` object makes it possible to interact with tasks in XL Deploy, such as deployments or discovery tasks. Tasks can be started, stopped or aborted or can be assigned to another user.

## Get help with CLI objects

When you log in to the CLI, a welcome message with information about the objects appears. To see this message again, type `help` and press ENTER.

It's easy to obtain information about a specific XL Deploy object by invoking the `help()` function. This will list all methods on the object that are available for scripting:

    deployit> factory.help()

    factory: Helper that can construct Configuration Items (CI) and Artifacts

    The methods available are:
    * factory.artifact(String id, String ciType, Map values, byte[] data) : ArtifactAndData
    * factory.configurationItem(String id, String ciType) : ConfigurationItemDto
    * factory.configurationItem(String id, String ciType, Map values) : ConfigurationItemDto
    * factory.types() : void
 
Extensive help about the usage of a specific method can be obtained by issuing a command like:

    deployit> security.help('getPermissions')

On the object in question. Notice that the name of the method is given enclosed in quote marks and without parentheses.
