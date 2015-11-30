---
title: Variables in XL Release
categories:
- xl-release
subject:
- Variables
tags:
- variable
- release
---

XL Release allows you to define variables for information that you don't know in advance or that may change. You can use variables to manage information that:

* Is not known when designing a template, such as the name of the application
* Is used in several places in the release, such as the name of the application, which you might want to use in task descriptions and email notifications
* May change during the release, such as the version number of the release that is being pushed to production

Variables are identified by the `${ }` syntax. XL Release supports several types of variable, such as *text*, *password*, *list*, *set*, and *key-value map*.

## Variable scope

In XL Release, you can create variables with different scopes:

* *Release variables* can only be used in a specific template or release
* *Global variables* can be used in all templates and releases (available in XL Release 4.8.0 and later)

## How to create a global variable

If you have the *Edit Global Variables* permission, you can create global variables in **Settings** > **Global variables**. For information about creating, editing, and deleting global variables, refer to [Configure global variables](/xl-release/how-to/configure-global-variables.html).

## How to create a release variable

If you have the *Edit Template* or *Edit Release* permission on a template or a release, respectively, you can create a release variable by:

* Typing the variable name in a field in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) (using the `${ }` syntax)
* Using the [Variables screen](/xl-release/how-to/create-release-variables.html)

For more information about creating, editing, and deleting release variables, refer to [Create release variables](/xl-release/how-to/create-release-variables.html).

## Where variables can be used

You can use variables in almost any field in XL Release; for example, in the titles of phases and tasks, in descriptions of phases, tasks, and releases, and in conditions and scripts.

You can create release variables that must be filled in before a release or task can start; refer to [Create release variables](/xl-release/how-to/create-release-variables.html#how-required-variables-work) for more information.

You can change the values of variables in an active release, although doing so will only affect tasks that are in a *planned* state.

## Special release variables

You can use the following special release variables to access the properties of a release:

* `${release.id}`
* `${release.title}`
* `${release.status}`
* `${release.owner}`
* `${release.description}`
* `${release.flagStatus}`
* `${release.flagComment}`
* `${release.tags}`
