---
title: Create a release template
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- permission
---

Templates are like blueprints for releases. You use them to model the ideal process of a release flow. A template can describe a procedure that is used to deliver different applications, or it can describe a procedure that is used to release a particular application and that will be reused for different versions of it.

Templates lack certain properties of actual releases, such as start and end dates. Also, you typically use placeholders like variables and teams for information about a release that you do not know in advance.

A new release is based on a template. The release is a copy of the template and has the values for the variables filled in, the start and end dates assigned, and the teams populated. 

If you change a template, the running releases that were created from it are not updated with the changes. Also, if you change the content or structure of a release, the changes are not automatically propagated to the template that was used to create it.

## Template overview

The template overview shows all templates that you have permission to see.

![Template Overview](../images/template-overview.png)

The following options are available (depending on your permissions):

* **View**: Open the template in the Release Flow editor.
* **New Release from template**: Create a new release based on this template.
* **Copy**: Create a new template that is a copy of this one. When copying a template that contains release triggers, all the triggers on the copied template will be disabled.
* **Delete**: Delete this template.

## Creating a template

To create a new, empty template, click **New template**.

![Create new template](../images/create-new-template.png)

**Template name** is the name that identifies the template in the overview.

**Scheduled start date and time**, **Due date and time**, and **Duration** set the _planned_ start date, end date and duration of a template. These dates will be used to display the template in the planner. Only the scheduled start date is mandatory, if not set, the due date and the duration will be inferred.

Tick the **Abort on failure** checkbox if you want the release created from this template to be aborted when a task fails.

You can enter a **Description** to provide more information about the template. Click the text to edit it. The text editor supports [Markdown syntax](/xl-release/how-to/use-markdown-in-xl-release.html), so you can put styled text and hyperlinks here.

**Run Release As User** and **Password for user that runs the release**: The username and password of the XL Release user account that will be used to execute scripts.

**Tags** can be set on the template. The tags are copied to the releases that are created from this template, to make it easier to search for them in the release overview.

## Template details

Editing a template is very similar to editing an actual release. However, there are some differences.

### Teams in templates

When you create a template, a team called **Template owners** is automatically created and populated with the user who created the template. This team has the permissions that are required to edit a template and create a release.

The **Release Admin** team is not available for templates. It is created when a release is created from the template.

### Template permissions

Templates have two sets of permissions. The first table shows permissions that apply to the template itself.

![Template Permissions](../images/template-permissions.png)

* **Create release**: Users can create a release from this template.
* **View template**: Users can see the template in the template overview.
* **Edit template**: Users can change the template by adding tasks and phases and changing them.
* **Edit security**: Users can edit teams and permissions on the template.

The second table contains permissions that are copied when creating a release. This allows you to define teams and permissions for the release beforehand. See [Release Permissions](/xl-release/how-to/configure-release-permissions.html) for an overview of these permissions.

## Starting a release from a template

When you create a release from a template, it is first in a 'planned' state. This is an important state, because before the release starts and people are notified by email that tasks are assigned to them, the release owner must do some configuration:

* **Variables**: When starting a release, the Create Release page will ask the release owner to assign values to all variables. 
* **Teams**: In a planned release, the release owner should populate the teams or revise the members of each team.
* **Start dates and due dates**: The release owner can optionally set scheduled start dates and due dates on crucial tasks or on all tasks.
* **Dependencies**: The release owner should revise dependencies on other releases. Dependencies can only be set on active releases, so they should not be specified in the template.
