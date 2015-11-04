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

For data that may change or that is not known in advance, XL Release provides a placeholder mechanism in the form of variables. You can use variables to manage information that:

* Is not known when designing a template, such as the name of the application
* Is used in several places in the release, such as the name of the application, which you might want to use in task descriptions and email notifications
* May change during the release, such as the version number of the release that is being pushed to production

Variables are snippets of text in a text field surrounded by `${ }`. For example, you can define a variable called `name` by typing `${name}` in a text field.

You can combine normal text with one or more variables, although in the case of XL Deploy, it is recommended that you use a dedicated variable for the entire field, because this will allow auto-completion later on.

## Variable support

Variables are supported in:

* Titles of phases and tasks
* Descriptions of tasks, phases and releases
* Task owners
* XL Deploy fields
* Notification task fields
* Gate conditions
* Scripts
* Password fields. When entering a password that uses the ${variable} syntax, it is treated as a variable, therefore you can not use such passwords in XL Release.

You are required to provide values for all variables when starting a release. Values are filled in the task when the task starts. You can change the values of variables during an active release, although doing so will only affect the tasks that are in a 'planned' state and have yet to be executed.

## Special variables

Special variables can be used to access release properties:

* `${release.id}`
* `${release.title}`
* `${release.status}`
* `${release.owner}`
* `${release.description}`
* `${release.flagStatus}`
* `${release.flagComment}`
* `${release.tags}`
