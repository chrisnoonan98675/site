---
title: Change a task's type
categories:
- xl-release
subject:
- Task types
tags:
- release
- template
- task
since:
- XL Release 4.8.0
---

You can change the type of a task in:

* A template
* A planned release that has not started yet)
* An active release, if the task that you want to change has not started yet

To change the type of a task in a template, you need the **Edit Template** permission on the template. To change the type of a task in a release, you need the **Edit Task** permission on the release.

To change a task's type:

1. Open the template or release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).
1. Click ![Task action menu](/images/menu_three_dots.png) on the task you want to change.
1. In the menu, hover over **Change type**.
1. Select the desired task type.

    XL Release will copy the values of properties that are shared between the task's previous type and the type that you selected.

**Note:** You cannot change the type of an active, completed, failed, or skipped task.
