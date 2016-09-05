---
title: Using variables
since: XL Release 4.8.0
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

## Configure release variables

You can use [variables](/xl-release/concept/variables-in-xl-release.html) to manage information that you don't know in advance or that may change. Release variables can only be used in the template or release in which they are created (unlike [global variables](/xl-release/how-to/configure-global-variables.html)). There are two ways to create a release variable.

### Create a release variable in the release flow editor

A simple way to create a release variable is to type its name in a task in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), using the `${ }` syntax. For example, typing `${name}` creates a variable called `name`, which you can then edit on the Variables screen.

The variable's type depends on the type of the field where you created the variable. For example, typing `${name}` in a text field means `name` will be of the type *text*, while typing `${name}` in a password field means `name` will be of the type *password*.

Other properties, such as the variable's label and description, will be left blank. In XL Release 4.8.0 and later, you can edit them on the Variables screen.

**Note:** If you enter a password in the `${variable}` format, XL Release will treat it as a variable, so you cannot use this format for the text of the password itself.

### Create a release variable on the Variables screen

In XL Release 4.8.0 and later, you can also create release variables using the Variables screen:

1. In a release or template, select **Variables** from the **Show** menu.
1. Click **New variable**. The Create Variable screen appears.
1. In the **Variable name** box, enter a name for the variable.
1. In the **Label** box, optionally enter a user-friendly label for the variable. This will appear next to the fields where users can enter a value for the variable.
1. Select the variable type from the **Type** list:
    * **Text**: A string of letters or numbers
    * **Password**: A password
    * **Checkbox**: A true or false value (Boolean)
    * **Number**: An integer
    * **List**: A list of values that can be reordered and can contain duplicates
    * **Set**: A set of values that cannot be reordered and cannot contain duplicates
    * **Key-value map**: A set of keys and corresponding values
    * **XL Deploy environment**: An environment defined in an XL Deploy server
    * **XL Deploy package**: A deployment package defined in an XL Deploy server

    **Tip**: To prevent the display of passwords, password variables can only be used in password fields. Any other type of variable cannot be used in password fields.

1. Next to **Default value**, optionally enter a value for the variable. To add a value to a list or a set, type the value in the box and press ENTER.
1. In the **Description** box, optionally enter a user-friendly description of the variable. This will appear below the fields where users can enter a value for the variable.
1. If the variable must have a value, select **Required**.
1. To allow users to enter or change the variable's value when starting a release, select **Show on Create Release form**.

    ![Create release variable](/xl-release/images/create-release-variable.png)

1. Click **Create** to create the variable.

#### How required variables work

If a variable is required and you selected **Show on Create Release form**, then the variable must have a value before the release can start. This can be the default value that you set for the variable in the template, or a value that the user enters when [starting the release](/xl-release/how-to/start-a-release-from-a-template.html).

If a variable is required and it is used in a task, then the variable must have a value before the task can start. This can be the default value that you set for the variable in the template or release, or a value that the user enters before the task becomes active. If a required variable is missing a value when the task becomes active, then the task is not started, but remains in the *needs input* state until a user enters a value.

**Note:** In XL Release 4.7.x and earlier, all release variables must have a value before the release can start.

### Edit a release variable

To edit a variable in a template or a running release, select **Variables** from the **Show** menu, then click the desired variable. Note that you cannot change the variable's type.

If you change the variable's value, planned tasks that use that variable will reflect the new value.
Completed, skipped, or failed tasks will reflect the old value (except in the case of user input tasks, which always show the variable's current value).

### Delete or replace a release variable

To delete or replace a variable in a template or a running release, select **Variables** from the **Show** menu, then click **Delete/Replace** next to the desired variable.

If the variable is not being used in the template or release, simply confirm that you want to delete it.

If the variable is still in use, XL Release allows you to choose how it will replace the variable wherever it appears in the template or release. You can replace the variable with:

* A static value
* A different variable (in `${ }` format)
* A blank space

![Delete and replace variable](/xl-release/images/variable-delete-and-replace.png)

After XL Release replaces all occurrences of the variable, it deletes the variable.

## Configure global variables

You can use [variables](/xl-release/concept/variables-in-xl-release.html) to manage information that you don't know in advance or that may change. Global variables can be used in all templates and releases to manage shared information (unlike [release variables](/xl-release/how-to/create-release-variables.html), which are limited to a single release or template). Global variables are available in XL Release 4.8.0 and later.

To create, edit, and delete global variables, select **Settings** > **Global variables** from the top menu. The Global variables page is only available to users who have the *Edit Global Variables* [global permission](/xl-release/how-to/configure-permissions.html).

![Global variables](/xl-release/images/global-variables.png)

### Create a global variable

To create a global variable:

1. Click **New global variable**.
1. In the **Variable name** box, enter a name for the variable. The `global.` prefix is required.
1. In the **Label** box, optionally enter a user-friendly label for the variable.
1. Select the variable type from the **Type** list:
    * **Text**: A string of letters or numbers
    * **Password**: A password
    * **Checkbox**: A true or false value (Boolean)
    * **Number**: An integer
    * **List**: A list of values that can be reordered and can contain duplicates
    * **Set**: A set of values that cannot be reordered and cannot contain duplicates
    * **Key-value map**: A set of keys and corresponding values
    * **XL Deploy environment**: An [environment](/xl-deploy/concept/key-xl-deploy-concepts.html#environments) defined in an [XL Deploy server](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html)
    * **XL Deploy package**: A [deployment package](/xl-deploy/concept/key-xl-deploy-concepts.html#deployment-packages) defined in an XL Deploy server

    **Tip:** Password variables can only be used in password fields, and other types of variables can only be used in non-password fields.

1. Next to **Value**, enter the value(s) for the variable. To add a value to a list or a set, type the value in the box and press ENTER.

    If a global variable does not have a value and it is used in a task, it will appear as a blank space in the task.

1. In the **Description** box, optionally enter a user-friendly description of the variable.

    ![Create global variable](/xl-release/images/create-global-variable.png)

1. Click **Create** to create the variable.

### Edit a global variable

To edit a global variable, click it. Note that you cannot change the variable's name or type.

If you change the variable's value, planned tasks that use that variable will reflect the new value.
Completed, skipped, or failed tasks will reflect the old value (except in the case of user input tasks, which always show the variable's current value).

### Delete a global variable

To delete a global variable, click **Delete** under **Actions**.

If you delete a global variable that is still in use, tasks that use the variable will not be able to start.

### Variable usage example

For data that may change or that is not known in advance, XL Release provides a placeholder mechanism in the form of [variables](/xl-release/concept/variables-in-xl-release.html).

This example shows a template that deploys an application to a test environment and assigns testing to QA. When testing succeeds, XL Release sends an email notification. If testing fails, we try again with the next version of the application.

This is the template:

![Template with variables](/xl-release/images/template-with-variables.png)

The variable `${package}` is used in the phase title and in the titles of various tasks. This variable is also used to instruct XL Deploy to deploy this package:

![Variables in XL Deploy task](/xl-release/images/variables-in-deployit-task.png)

Click **New Release** to create a release from the template. XL Release scans the template for variables and asks the user to provide values for all of them.

![Setting variables when creating a release](/xl-release/images/setting-variables-when-creating-a-release.png)

XL Deploy variables are marked with special icons, and auto-completion is available for those variables. XL Release queries the XL Deploy server for the packages and environments that are available and display them in a drop-down menu.

After the release is created, the release flow appears with the values of the variables filled in.

Note that you can still change variables by editing the fields on the Release properties page.

![Variables in release](/xl-release/images/variables-in-release.png)

Now suppose that QA testing for BillingApp 1.0 failed and we need to repeat the procedure for the next version delivered by the Development team.

Click **Restart Phase** to restart the QA phase. Before the release flow resumes, you can change the variables:

![Variables when restarting a release](/xl-release/images/variables-in-release-restart.png)

When the release resumes, the phase is duplicated with the new variable values in place (the old phase still has the old values).

![Variables in restarted release](/xl-release/images/variables-in-restarted-release.png)
