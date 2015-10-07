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

Special variables can be used to access release properties:

* `${release.id}`
* `${release.title}`
* `${release.status}`
* `${release.owner}`
* `${release.description}`
* `${release.flagStatus}`
* `${release.flagComment}`
* `${release.tags}`

## Variable usage example

This example shows a template that deploys an application to a test environment and assigns testing to QA. When testing succeeds, XL Release sends an email notification. If testing fails, we try again with the next version of the application. 

This is the template:

![Template with variables](../images/template-with-variables.png)

The variable `${package}` is used in the phase title and in the titles of various tasks. This variable is also used to instruct XL Deploy to deploy this package:

![Variables in XL Deploy task](../images/variables-in-deployit-task.png)

Click **New Release** to create a release from the template. XL Release scans the template for variables and asks the user to provide values for all of them.

![Setting variables when creating a release](../images/setting-variables-when-creating-a-release.png)

XL Deploy variables are marked with special icons, and auto-completion is available for those variables. XL Release queries the XL Deploy server for the packages and environments that are available and display them in a drop-down menu.

After the release is created, the release flow appears with the values of the variables filled in.

Note that you can still change variables by editing the fields on the Release properties page.

![Variables in release](../images/variables-in-release.png)

Now suppose that QA testing for BillingApp 1.0 failed and we need to repeat the procedure for the next version delivered by the Development team.

Click **Restart Phase** to restart the QA phase. Before the release flow resumes, you can change the variables: 

![Variables when restarting a release](../images/variables-in-release-restart.png)

When the release resumes, the phase is duplicated with the new variable values in place (the old phase still has the old values).

![Variables in restarted release](../images/variables-in-restarted-release.png)

