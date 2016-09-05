---
title: Using templates
---

In XL Release, _templates_ are like blueprints for releases. You use templates to model the ideal process of a release flow. A template can describe a procedure that is used to deliver different applications, or it can describe a procedure that is used to release a particular application and that will be reused for different versions of the application.

Templates lack certain properties of actual releases, such as start and end dates. Also, you typically use placeholders such as variables and teams for information about a release that you do not know in advance.

A new release is based on a template. The release is a copy of the template and has the values for the variables filled in, the start and end dates assigned, and the teams populated.

If you change a template, the running releases that were created from it are not updated with the changes. Also, if you change the content or structure of a release, the changes are not automatically propagated to the template that was used to create it.

## View your templates

The template overview shows all templates that you have permission to see.

![Template Overview](/xl-release/images/template-overview.png)

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

    ![Create new template](/xl-release/images/create-new-template.png)

1. In the **Template Name** box, enter a name to identify the template.
1. In **Scheduled start date** and **Due date**, set the planned start and end dates of the release. XL Release uses these dates to show the release on the calendar. Only the start date is required.
1. If you want the release to be aborted when a task fails, select **Abort on failure**.
1. In the Description box, optionally enter detailed information about the release. This field allows [Markdown](/xl-release/how-to/use-markdown-in-xl-release.html).
1. In **Run release as user** and **Password for user that runs the release**, enter the user name and password of the XL Release user account that should be used to execute scripts.
1. Next to **Tags**, optionally add tags to the release to make it easier to find in the release overview.
1. Click **Create** to create the template.

To discard your changes without saving, click **Cancel**.

## Copy a template

To create a copy of a template, go to the template overview and click **Copy** under the desired template. Note that template names do not have to be unique because every template has a [unique ID](/xl-release/how-to/how-to-find-ids.html#releases-and-templates) such as `Release29994650`.

If you want to create a new version of a template, it is recommended that you do so by copying the original template and adding a version number to the name of the copy; for example, _Consumer Portal Release Process V4_.

## Import a template

To import a template:

1. On the template overview, click **Import**.
2. Click **Choose File** and browse to the ZIP file.

    **Tip:** If you have exported templates from a version of XL Release before 4.0.0, you can also select a JSON file.

3. Click **Import**.

![Import template](/xl-release/images/import-dialog-1.png)

After XL Release processes the file, the list of imported templates appears. If a template contains references to objects that do not exist on this server (for example, XL Deploy servers or gate dependencies), XL Release removes the references and shows a warning message.

If you import a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), all the triggers on the imported template will be disabled.

## Configure template properties

To specify metadata for a release or template, select **Properties** from the **Show** menu to go to the release properties page.

![Release properties](/xl-release/images/release-properties.png)

To configure release properties:

1. In the **Release Name** box, enter the name of the release. If the release is running, you can change its name.
1. In the **Description** box, optionally enter detailed information about the release. This field allows [Markdown](/xl-release/how-to/use-markdown-in-xl-release.html).

    Under the description, you can see links to the template that the release is based on and the release that [started it](/xl-release/how-to/create-a-create-release-task.html) (if applicable).

1. Next to **Flag status**, optionally select a yellow (attention needed) or red (release at risk) icon and enter a status message. Flagged releases are highlighted in overviews.
1. In **Start date** and **Due date**, set the *planned* start and end dates of the release. XL Release uses these dates to show the release on the calendar. These are not the dates that the release actually started or ended.
1. Select the person who is responsible for the release from the **Release Owner** list. This person will receive additional [notifications](/xl-release/concept/notifications-in-xl-release.html) if tasks fail or are flagged.

    Release owners are automatically added to the Release Admin team when the release is created. This team has all [permissions](/xl-release/how-to/configure-permissions-for-a-release.html) on the release.

1. In **Run automated tasks as user** and **Password**, enter the user name and password of the XL Release user account that should be used to execute scripts in this release.
1. If you want the release to be aborted when a task fails, select **Abort on failure**.
1. Next to **Tags**, optionally add tags to the release to make it easier to find in the release overview.
1. Optionally add attachments by clicking **Choose File** next to **Attachments**. To delete an attachment, click the **X** next to it.
1. To publish a link that you can add to a calendar application such as Microsoft Outlook or Apple iCal, select **Publish link**. If the release dates change, the event is automatically updated in your calendar.

    **Important:** Everyone who can reach the XL Deploy server can access this link, which may be a security concern.

1. To download an ICS file that you can open with a calendar application, click **Download calendar event**.
1. Under **Release Variables**:
    * If you are using XL Release 4.8.0 or later, click [**Variables Screen**](/xl-release/how-to/create-release-variables.html) to enter values for the variables that have been defined in the release.
    * If you are using XL Release 4.7.x or earlier, enter values for the variables that have been defined in the release. You must provide values for all variables, or any task that contains a variable without a value will fail.
1. Click **Save** to save your changes to the release properties. Changes are not saved automatically.

To discard your changes without saving, click **Reset**.

## Add phases and tasks to a template

[Work with phases and tasks](phases-and-tasks.html)

## Use variables in a template

[Work with release variables](release-variables.html)

## Using the release flow view

In XL Release, the release flow editor shows the phases and tasks in the release and allows you to add, move, edit, and delete them. To go to the release flow editor, open a template or release and select **Release Flow** from the **Show** menu.

![Release Flow Editor](/xl-release/images/release-flow-editor.png)

### Working with phases

In the release flow editor, you can work with phases:

* Add a phase by clicking **Add phase**
* Move a phase by dragging and dropping it
* Edit a phase by clicking ![Phase edit button](/images/button_edit_phase.png) on the phase header
* Delete a phase by clicking ![Phase delete button](/images/button_delete_phase.png) on the phase header

Note that you cannot move, edit, or delete a phase that has already been completed.

### Working with tasks

In the release flow editor, you can also work with tasks:

* Add a task by clicking **Add task** in the desired phase
* Move a task by dragging and dropping it (unless it is complete)
* [Edit a task's details](/xl-release/how-to/working-with-tasks.html) by clicking it
* Assign a task to yourself by clicking ![Task action menu](/images/menu_three_dots.png)
* [Change a task's type](/xl-release/how-to/change-a-task-type.html) by clicking ![Task action menu](/images/menu_three_dots.png)
* Skip, fail, duplicate, or delete a task by clicking ![Task action menu](/images/menu_three_dots.png)

In an active release, ![Active task indicator](/images/active_task_arrow.png) indicates the task that is currently active. Note that you cannot move, edit, or delete a task that has already been completed.

### Differences between templates and releases

You use the release flow editor for templates, planned releases, and active releases, but there are some differences in the actions that are available:

{:.table .table-striped}
| Action | Description | Available in... |
| ------ | ----------- | --------------- |
| Add Phase | Add a new phase after the last phase; you can drag and drop phases to rearrange them | Template<br />Planned release<br />Active release |
| New Release | Create a new release from the [template](/xl-release/how-to/create-a-release-template.html) | Template |
| Start Release | Start a release that is in the planned state. |  Planned release |
| Abort Release | Stop a release that is active, and abort it. | Planned release<br />Active release |
| Restart Phase | Abort a phase in a release that is active and [restart the release](/xl-release/how-to/restart-a-phase-in-an-active-release.html) from any past phase | Active release |
| Export to Excel | Download the current release in Microsoft Excel format (.xlsx) | Planned release<br />Active release |
| Export | Download the template in ZIP format; you can [import a template](/xl-release/how-to/import-a-release-template.html) in the Template Overview | Template |


## Using the table view

The XL Release table view provides an alternative view of a template or release that is optimized for working with tasks. In table view, you can:

* Filter phases and tasks based on name, start date, end date, duration, type, and/or assignee
* Directly open a task by clicking its name
* Use the task action menu (![Task action menu](/images/menu_three_dots.png)) to quickly assign a task to yourself, change its type, duplicate it, or delete it

To access the table view, click **Table** at the top of the release flow page.

![XL Release table view](/xl-release/images/release-table-view.png)

## Using the planner view

The XL Release planner allows you to use an interactive Gantt chart to view and edit the timing of the phases and tasks in a release or template. The Gantt chart is a combined timeline of the template or release, its phases, and the tasks within.

To access in the planner in XL Release 5.0.0 or later, click **Planner** at the top of the release flow page. In earlier versions of XL Release, select **Planner** from the **Show** menu.

![Planner: phases overview](/xl-release/images/planner-phases.png)

Tasks are executed in sequential order within a phase. Therefore, in the example below, Task 2 will not start until Task 1 is complete, as indicated by the line that connects them.

![Planner: default sequence](/xl-release/images/planner-default-sequence.png)

### Editing tasks and phases in the planner

When editing a task or a phase in the planner, you can:

* Move it by dragging it to a new position
* Set its duration by dragging its right edge
* Set its scheduled start date by dragging its left edge
* Alternatively, set the dates and duration by clicking **Show dates** and then setting them explicitly

![Planner: sequence with start and dates](/xl-release/images/planner-date-picker.png)

When you set the scheduled start date or duration of a task, the planner will automatically adjust subsequent tasks in the same phase. For more information about dates and durations, refer to [Scheduling releases](/xl-release/how-to/scheduling-releases.html)

**Tip:** Double-click a task in the planner to open its detail view.
