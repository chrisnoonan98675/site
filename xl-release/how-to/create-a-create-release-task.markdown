---
title: Create a Create Release task
categories:
- xl-release
subject:
- Task types
tags:
- task
- create release
- start release
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

**Note:** In order to successfully create a release, the release which contains the task needs to be configured with an automated tasks user. 
The user needs to have `Create Release` permissions on the template which is specified in the Create Release task. 
See [how to configure release properties](/xl-release/how-to/configure-release-properties.html) on how to configure this user.

When the new release is created, XL Release stores the ID of the created release into the `Created release ID` output property. 
This allows you for example to create and start a release from the main release and then wait for the started release to finish in a 
[gate task](/xl-release/how-to/create-a-gate-task.html).

If you do not select variable in the Created release ID field, then the main release will proceed normally but you will not be able to reference it later 
in the release.

The example above shows a task that will create and start a new release based on the "Front office services release template" template. 
The new release's title will be "Release front office services 2.1", and the value of the template variable `version` will be bound 
to the release variable `frontOfficeVersion`.

When the "Release front office services 2.1" release is created, XL Release will put value of the created releaes ID into variable `frontOfficeVersion`.
You can configure a [gate task](/xl-release/how-to/create-a-gate-task.html) to wait until this release is finished or use it like any other text variable.
