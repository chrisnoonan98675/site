---
title: Create Release task type
---

A Create Release task is an automatic task that creates and starts a release based on a configured template.

![Create release task details](/xl-release/images/create-release-task-details.png)

The options for the Create Release task are:

{:.table .table-striped}
| Option                       | Description                                                                   |
| ---------------------------- | ----------------------------------------------------------------------------- |
| Release title                | The title to use for the newly created release                                |
| Template                     | The template from which to create a release                                   |
| Start release                | If checked release should also be started after it is created by this task    |
| Variables                    | Variables from the template that must be filled in (if applicable)            |
| Created release ID           | Output property bound to the variable that contains ID of the created release |

**Important:** To create a release, the release that contains the Create Release task must be configured with an automated tasks user who has the [*Create Release* permission](/xl-release/how-to/configure-permissions.html) on the template that you specify in the Create Release task. For information about configuring the automated tasks user, refer to [Configure release properties](/xl-release/how-to/configure-release-properties.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Create Release tasks have a green border.

## Using the new release ID

When the new release is created, XL Release stores its unique ID in the Create Release task's **Created release ID** output property. If you configure this property to be a [variable](/xl-release/how-to/create-release-variables.html), then you can use the variable in other tasks. For example, you can use the variable in a [Gate task](/xl-release/how-to/create-a-gate-task.html) so the initial release will wait at the Gate until the new release is complete.

If you do not save the **Created release ID** in a variable, then the release will proceed normally, but you will not be able to refer to the created release in other tasks.

The example above shows a task that will create and start a new release based on the "Front office services release template" template. The new release's title will be "Release front office services 2.1", and the value of the `${version}` template variable will be set to "2.1". When the "Release front office services 2.1" release is created, XL Release will store its unique ID in the `${frontOfficeServiceReleaseId}` variable.

## Limiting concurrent releases

To prevent performance issues, XL Release limits the number of concurrently running releases that can be created by Create Release tasks to 100. You can adjust this limit by uncommenting and changing the `xlrelease.Release.maxConcurrentReleases` property in the `XL_RELEASE_SERVER_HOME/conf/deployit-defaults.properties` file. You must restart the XL Release server for the change to take effect.
