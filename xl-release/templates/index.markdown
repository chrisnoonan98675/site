---
title: Template overview
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

## Copy a template

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

## Add phases and tasks to a template

[Work with phases and tasks](phases-and-tasks.html)

## Use variables in a template

[Work with release variables](release-variables.html)

## Using the table view

The XL Release table view provides an alternative view of a template or release that is optimized for working with tasks. In table view, you can:

* Filter phases and tasks based on name, start date, end date, duration, type, and/or assignee
* Directly open a task by clicking its name
* Use the task action menu (![Task action menu](/images/menu_three_dots.png)) to quickly assign a task to yourself, change its type, duplicate it, or delete it

To access the table view, click **Table** at the top of the release flow page.

![XL Release table view](../images/release-table-view.png)

## Using the planner view
