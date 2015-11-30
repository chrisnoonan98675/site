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

Variables are snippets of text surrounded by `${ }`.

<!--You can combine normal text with one or more variables, although in the case of XL Deploy, it is recommended that you use a dedicated variable for the entire field, because this will allow auto-completion later on.-->

## Types of variables

There are two types of variables in XL Release:

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

You can use variables in:

* Titles of phases and tasks
* Descriptions of tasks, phases, and releases
* Task owners
* XL Deploy fields
* Notification task fields
* Gate conditions
* Scripts
* Password fields

**Note:** If you enter a password in the `${variable}` syntax, XL Release treats it as a variable.

You are required to provide values for all variables when starting a release. Values are filled in the task when the task starts. You can change the values of variables during an active release, although doing so will only affect the tasks that are in a 'planned' state and have yet to be executed.

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
