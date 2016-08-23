---
title: Model a release process
---

In XL Release, _templates_ are like blueprints for releases. You use templates to model the ideal process of a release flow. A template can describe a procedure that is used to deliver different applications, or it can describe a procedure that is used to release a particular application and that will be reused for different versions of the application.

Templates lack certain properties of actual releases, such as start and end dates. Also, you typically use placeholders such as variables and teams for information about a release that you do not know in advance.

A new release is based on a template. The release is a copy of the template and has the values for the variables filled in, the start and end dates assigned, and the teams populated.

If you change a template, the running releases that were created from it are not updated with the changes. Also, if you change the content or structure of a release, the changes are not automatically propagated to the template that was used to create it.

## View your templates

The template overview shows all templates that you have permission to see.

![Template Overview](../images/template-overview.png)

The following options are available (depending on your permissions):

{:.table .table-striped}
| Action | Description |
| ------ | ----------- |
| View | Open the template in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html). |
| New release from template | Create a new release based on the template. |
| Copy | Create a new template that is a copy of this one; if you copy a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), all triggers on the copied template will be disabled.<br /><br />You will be added as an owner of the new template. However, if you did not have edit permissions on the original template, then all password fields and variables will be stripped. |
| Delete | Delete the template. |

## Create a template

To create a template:

1. On the template overview, click **New template**.

    ![Create new template](../images/create-new-template.png)

1. In the **Template Name** box, enter a name to identify the template.
1. In **Scheduled start date** and **Due date**, set the planned start and end dates of the release. XL Release uses these dates to show the release on the calendar. Only the start date is required.
1. If you want the release to be aborted when a task fails, select **Abort on failure**.
1. In the Description box, optionally enter detailed information about the release. This field allows [Markdown](/xl-release/how-to/use-markdown-in-xl-release.html).
1. In **Run release as user** and **Password for user that runs the release**, enter the user name and password of the XL Release user account that should be used to execute scripts.
1. Next to **Tags**, optionally add tags to the release to make it easier to find in the release overview.
1. Click **Create** to create the template.

To discard your changes without saving, click **Cancel**.

### Copy a template

To create a copy of a template, go to the template overview and click **Copy** under the desired template. Note that template names do not have to be unique because every template has a [unique ID](/xl-release/how-to/how-to-find-ids.html#releases-and-templates) such as `Release29994650`.

If you want to create a new version of a template, it is recommended that you do so by copying the original template and adding a version number to the name of the copy; for example, _Consumer Portal Release Process V4_.

## Import a template

To import a template:

1. On the template overview, click **Import**.
2. Click **Choose File** and browse to the ZIP file.

    **Tip:** If you have exported templates from a version of XL Release before 4.0.0, you can also select a JSON file.

3. Click **Import**.

![Import template](../images/import-dialog-1.png)

After XL Release processes the file, the list of imported templates appears. If a template contains references to objects that do not exist on this server (for example, XL Deploy servers or gate dependencies), XL Release removes the references and shows a warning message.

If you import a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), all the triggers on the imported template will be disabled.

## Configure template security

### Template teams

In XL Release, a *release team* allows you to group users with the same role, so you can assign tasks in the release to the team. You can also configure template-level and release-level permissions on the team level. For information about teams, refer to [Configure release teams and permissions](/xl-release/how-to/configure-release-teams-and-permissions.html).

### Template permissions

_Template permissions_ determine who can do what in a release template.

To set template permissions, open a template and select **Permissions** from the **Show** menu. The Permissions page is only available to users who have the *Edit Security* permission on the template or who have the *Edit Security* [global permission](/xl-release/how-to/configure-permissions.html).

![Template Permissions](../images/template-permissions.png)

The following permissions are available for templates:

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Create Release | Users can create a release from the template. |
| View Template | Users can see the template in the template overview. |
| Edit Template | Users can change the template by adding tasks and phases and changing them. |
| Edit Security | Users can edit teams and permissions on the template. |

_Release permissions_ determine who can do what in a release. For information about these permissions, refer to [Configure release teams and permissions](/xl-release/how-to/configure-release-teams-and-permissions.html).

## Use release variables

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

    ![Create release variable](../images/create-release-variable.png)

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

![Delete and replace variable](../images/variable-delete-and-replace.png)

After XL Release replaces all occurrences of the variable, it deletes the variable.

## Work with phases and tasks

In XL Release, the phases in a template or release represent blocks of work that happen in succession. To add a phase to a template or release, use the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html); it shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

In XL Release, the activities in a template or release are modeled as tasks, which are logically grouped in phases. To add a task to phase to a template or release, use the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html); it shows the phases and tasks in the release and allows you to add, move, edit, and delete them.

### Add a phase

To add a phase:

1. In the template or release, select **Release Flow** from the **Show** menu.
1. Click **Add Phase**. A new phase with title 'New Phase' appears.

    ![New Phase](../images/new-phase.png)

1. Click the phase title to change it.
1. Optionally move the phase by dragging it to the desired position.

    **Tip:** You can expand or collapse a phase by clicking the arrow in the header.

1. Optionally select a different color for the phase.
1. Click ![Phase details icon](../images/phase-details-icon.png) to view a description of the phase and its start date, due date, and duration.

     XL Release uses the dates and duration when displaying the phase in the planner and calendar; if they are not consistent with the release dates (as defined in the [release properties](/xl-release/how-to/configure-release-properties.html)), a warning appears.

    ![Phase details popup](../images/phase-details-popup.png)

### Copy a phase

To copy a phase, click ![Duplicate icon](../images/duplicate-icon.png). This is useful during modeling when you have similar or identical phases. When you duplicate a phase that is active and has running and completed tasks, the tasks in the new phase are all in the *planned* state.

### Delete a phase

To delete a phase, click ![Delete icon](../images/delete-icon.png). Note that you cannot delete a completed or active phase of a running release.

### Add a task to a phase

To add a task to a phase:

1. In the template or release, select **Release Flow** from the **Show** menu.
1. Click **Add task** at the bottom of the phase.

    ![New Phase](../images/add-task.png)

1. Click the task title to change it.
1. Select the task type.
1. Click **Add** to create the task and add it to the phase.
1. Optionally move the task by dragging it to the desired position. You can also drag the task to another phase.

    ![Task in release flow editor](../images/task-in-release-flow-editor.png)

1. Click the task to edit its details.

In a task:

* An orange triangle indicates that the task is currently active in the release (if the release is active).
* If the task is manual, the user or group to whom the task is assigned appears at the lower left. If the task is automated, the type of script appears at the lower left (for example, *Webhook: JSON webhook*).
* At the upper right, you can see:
    * An icon that indicates the task type
    * The current state of the task (if the release is active)
    * An alert icon, if action is needed
* The task due date appears at the lower right (if a date is set)

### Add a task to a parallel or sequential group

[Parallel Groups](/xl-release/how-to/create-a-parallel-group.html) and [Sequential Groups](/xl-release/how-to/create-a-sequential-group.html) are containers for other tasks.

Within a Parallel Group, all tasks are started simultaneously. The Parallel Group task finishes when all of its children are complete.

Within a Sequential Group, tasks are executed in order. The Sequential Group task finishes when its last child is complete. The Sequential Group task is available in XL Release 5.0.0 and later.

To add a task to a task group, click **Add task** at the bottom of the group. To move a task into a task group, drag and drop it. To collapse or expand a task group, click its arrow.

![Add Parallel Group task](../images/add-parallel-task.png)

### Copy a task

To copy a task, click the button in the upper right corner of the task. You cannot copy tasks that are completed.

### Delete a task

To delete a task, click the button in the upper right corner of the task. In an active release, you can only remove tasks that are planned (that is, tasks that are not in progress).

## Using the table view

The XL Release table view provides an alternative view of a template or release that is optimized for working with tasks. In table view, you can:

* Filter phases and tasks based on name, start date, end date, duration, type, and/or assignee
* Directly open a task by clicking its name
* Use the task action menu (![Task action menu](/images/menu_three_dots.png)) to quickly assign a task to yourself, change its type, duplicate it, or delete it

To access the table view, click **Table** at the top of the release flow page.

![XL Release table view](../images/release-table-view.png)
