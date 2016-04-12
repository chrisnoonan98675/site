---
title: Create a Create Release task
categories:
- xl-release
subject:
- Task types
tags:
- task
- create release
- subrelease
since:
- XL Release 5.0.0
---

A Create Release task is an automatic task that creates and starts a release based on a configured template.

![Create release task details](../images/create-release-task-details.png)

The options for the Create Release task are:

{:.table .table-striped}
| Option                       | Description                                                                   |
| ---------------------------- | ----------------------------------------------------------------------------- |
| Release title                | The title to use for the newly created release                                |
| Template                     | The template from which to create a release                                   |
| Start release                | If checked release should also be started after it is created by this task    |
| Variables                    | Variables from the template that must be filled in (if applicable)            |
| Created release ID           | Output property bound to the variable that contains ID of the created release |

**Note:** To create a release, the release that contains the Create Release task must be configured with an automated tasks user who has the [*Create Release* permission](/xl-release/how-to/configure-permissions.html) on the template that is specified in the Create Release task. For information about configuring the automated tasks user, refer to [Configure release properties](/xl-release/how-to/configure-release-properties.html).

When the new release is created, XL Release stores its ID in the Create Release task's **Created release ID** output property. If you configure this property to be a variable, then you can use the variable in other tasks. For example, you can use the variable in a [Gate task](/xl-release/how-to/create-a-gate-task.html) so the initial release will wait at the Gate until the new release is complete.

If you do not set **Created release ID** to a variable, then the release will proceed normally, but you will not be able to refer to the created release in other tasks.

The example above shows a task that will create and start a new release based on the "Front office services release template" template. The new release's title will be "Release front office services 2.1", and the value of the template variable `version` will be bound to the release variable `frontOfficeVersion`. When the "Release front office services 2.1" release is created, XL Release will store its ID in the `frontOfficeServiceReleaseId` variable.
