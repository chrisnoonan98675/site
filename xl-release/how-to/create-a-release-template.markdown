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
weight: 411
---

In XL Release, templates are like blueprints for releases. You use them to model the ideal process of a release flow. A template can describe a procedure that is used to deliver different applications, or it can describe a procedure that is used to release a particular application and that will be reused for different versions of it.

Templates lack certain properties of actual releases, such as start and end dates. Also, you typically use placeholders like variables and teams for information about a release that you do not know in advance.

A new release is based on a template. The release is a copy of the template and has the values for the variables filled in, the start and end dates assigned, and the teams populated.

If you change a template, the running releases that were created from it are not updated with the changes. Also, if you change the content or structure of a release, the changes are not automatically propagated to the template that was used to create it.

## Template overview

The template overview shows all templates that you have permission to see.

![Template Overview](../images/template-overview.png)

The following options are available (depending on your permissions):

{:.table .table-striped}
| Action | Description |
| ------ | ----------- |
| View | Open the template in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html) |
| New Release from Template | Create a new release based on the template |
| Copy | Create a new template that is a copy of this one; if you copy a template that contains [release triggers](/xl-release/how-to/create-a-release-trigger.html), all triggers on the copied template will be disabled.<br /><br />You will be added as an owner of this new template. However, if you did not have edit permissions on the original template, then all password fields and variables will be stripped. |
| Delete | Delete the template

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
1. Click **Create** to create the template

To discard your changes without saving, click **Cancel**.

## Teams in templates

When you create a template, the *Template Owner* team is automatically created. This team contains everyone who has owning rights on the template. Refer to [Configure teams for a release](/xl-release/how-to/configure-teams-for-a-release.html#predefined-teams) for more information.

## Template permissions

Templates have two sets of permissions. The first table shows permissions that apply to the template itself.

![Template Permissions](../images/template-permissions.png)

{:.table .table-striped}
| Permission | Description |
| ---------- | ----------- |
| Create Release | Users can create a release from the template |
| View Template | Users can see the template in the template overview |
| Edit Template | Users can change the template by adding tasks and phases and changing them |
| Edit Security | Users can edit teams and permissions on the template |

The second table contains permissions that are copied when creating a release. This allows you to define teams and permissions for the release beforehand. See [Configure release permissions](/xl-release/how-to/configure-permissions-for-a-release.html) for an overview of these permissions.

## Versioning templates

Template names do not have to be unique because every template has a [unique ID](/xl-release/how-to/how-to-find-ids.html#releases-and-templates) such as `Release29994650`. If you want to create a new version of a template, it is recommended that you do so by:

1. Going go the template overview
2. Clicking **Copy** next to the desired template
3. Adding a version number to the name; for example, _Consumer Portal Release Process V4_
