---
title: Configure global variables
categories:
- xl-release
subject:
- Settings
tags:
- settings
- system administration
- variable
---

On the **Global variables** screen under **Settings**, you can create, edit, and delete global variables. You must have the *Edit Global Variables* permission to do so.

![Global variables](../images/global-variables.png)

## Create a global variable

To create a global variable:

1. Click **New global variable**.
1. In the **Variable name** box, enter a name for the variable. The `global.` prefix is required.
1. In the **Label** box, optionally enter a user-friendly label for the variable.
1. Select the variable type from the **Type** list:
    * **Text**: A string of letters or numbers
    * **Password**: A password
    * **List**: A list of values that can be reordered and can contain duplicates
    * **Set**: A set of values that cannot be reordered and cannot contain duplicates
    * **Key-value map**: A set of keys and corresponding values
    * **XL Deploy environment**: An [environment](/xl-deploy/concept/key-xl-deploy-concepts.html#environments) defined in an [XL Deploy server](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html)
    * **XL Deploy package**: A [deployment package](/xl-deploy/concept/key-xl-deploy-concepts.html#deployment-packages) defined in an XL Deploy server

    **Tip:** Password variables can only be used in password fields, and other types of variables can only be used in non-password fields.

1. Next to **Value**, enter the value(s) for the variable. To add a value to a list or a set, type the value in the box and press ENTER.

    If a global variable does not have a value and it is used in a task, it will appear as a blank space in the task.

1. In the **Description** box, optionally enter a user-friendly description of the variable.

    ![Create global variable](../images/create-global-variable.png)

1. Click **Create** to create the variable.

## Edit a global variable

To edit a global variable, click it. Note that you cannot change the variable's name or type.

If you change the variable's value, planned tasks that use that variable will reflect the new value.
Completed, skipped, or failed tasks will reflect the old value (except in the case of user input tasks, which always show the variable's current value).

## Delete a global variable

To delete a global variable, click **Delete** under **Actions**.

If you delete a global variable that is still in use, tasks that use the variable will not be able to start.
