---
title: Create a Start Release task
categories:
- xl-release
subject:
- Task types
tags:
- task
- start release
- subrelease
since:
- XL Release 5.0.0
---

A Start Release task is an automatic task that creates and starts a release based on a configured template.

![Start release task details](../images/start-release-task-details.png)

The options for the Start Release task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Release title | The title to use for the newly created release |
| Template | The template from which to start a release |
| Wait at Gate task | An upcoming Gate task in the current release (optional) |
| Variables | Variables from the template that must be filled in (if applicable) |

When the new release starts, XL Release adds a [dependency](/xl-release/how-to/create-a-gate-task.html#dependencies) to the Gate task selected in **Wait at Gate task**. This allows you for example to start a subrelease from the main release and then have the main release wait for the subrelease to finish. If you do not select a Gate task, then the main release will proceed normally while the subrelease runs.

The example above shows a task that will create and start a new release based on the "Front office services release template" template. The new release's title will be "Release front office services 2.1", and the value of the template variable `version` will be set to 2.1.

When the "Release front office services 2.1" release is created, XL Release will add it as a dependency on the "Wait for front office services 2.1 to be released" Gate task. The main release will wait at this task until "Release front office services 2.1" finishes.
