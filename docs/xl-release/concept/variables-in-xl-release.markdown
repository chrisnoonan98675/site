---
title: Variables in XL Release
categories:
- xl-release
subject:
- Variables
tags:
- variable
- release
weight: 475
---

When creating release templates, you will probably create tasks that contain information that varies based on the release. For example, you might have one generic release template that is used for the release process of several applications. Different releases based on this template would require different application names.

XL Release allows you to use variables for this kind of information. You can use variables to manage information that:

* Is not known when designing a template, such as the name of the application
* Is used in several places in the release, such as the name of the application, which you might want to use in task descriptions and email notifications
* May change during the release, such as the version number of the release that is being pushed to production

Variables are identified by the `${ }` syntax. XL Release supports several types of variable; for example, *text*, *password*, *number*, and *list*.

## Variable scope

In XL Release, you can create variables with different scopes:

* *Release variables* can only be used in a specific template or release
* *Global variables* can be used in all templates and releases (available in XL Release 4.8.0 and later)

## How to create a global variable

If you have the *Edit Global Variables* permission, you can create global variables in **Settings** > **Global variables** (available in XL Release 4.8.0 and later). For information about creating, editing, and deleting global variables, refer to [Configure global variables](/xl-release/how-to/configure-global-variables.html).

## How to create a release variable

If you have the *Edit Template* or *Edit Release* permission on a template or a release, respectively, you can create a release variable by:

* Typing the variable name in a field in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) (using the `${ }` syntax)
* Using the [Variables screen](/xl-release/how-to/create-release-variables.html) (available in XL Release 4.8.0 and later)

For more information about creating, editing, and deleting release variables, refer to [Create release variables](/xl-release/how-to/create-release-variables.html).

## Where variables can be used

You can use variables in almost any field in XL Release; for example, in the titles of phases and tasks, in descriptions of phases, tasks, and releases, and in conditions and scripts.

You can create release variables that must be filled in before a release or task can start; refer to [Create release variables](/xl-release/how-to/create-release-variables.html#how-required-variables-work) for more information.

You can change the values of variables in an active release, although doing so will only affect tasks that are in a *planned* state.

### Using a List variable as a value in the List box variable type

As of XL Release 7.0.0 you can create a List variable and use it as a possible value for a List box variable.

1. Create a [global variable](/xl-release/how-to/create-release-variables.html#create-a-global-variable) or a [release variable](/xl-release/how-to/create-release-variables.html#create-a-release-variable-on-the-variables-screen) with the type **List**.
1. When you create a new variable and select the type **List box**, click the button next to the **Possible values** to switch between a list of normal values or a variable of type **List**.

    ![List box variable](../images/variable-list-box.png)

1. Select the second option and choose a **List** type variable.
1. Click **Save**.

You can use the **List box** variable in templates, releases, or tasks allowing users to select the values from predefined **List** variable.

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
