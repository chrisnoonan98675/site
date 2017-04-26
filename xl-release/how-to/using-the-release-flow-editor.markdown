---
title: Using the release flow editor
categories:
- xl-release
subject:
- Releases
tags:
- release
- template
- release flow
weight: 415
---

In XL Release, the release flow editor shows the phases and tasks in the release and allows you to add, move, edit, and delete them. To go to the release flow editor, open a template or release and select **Release Flow** from the **Show** menu.

![Release Flow Editor](../images/release-flow-editor.png)

## Working with phases

In the release flow editor, you can work with phases:

* Add a phase by clicking **Add phase**
* Move a phase by dragging and dropping it
* Edit a phase by clicking ![Phase edit button](/images/button_edit_phase.png) on the phase header
* Delete a phase by clicking ![Phase delete button](/images/button_delete_phase.png) on the phase header

Note that you cannot move, edit, or delete a phase that has already been completed.

## Working with tasks

In the release flow editor, you can also work with tasks:

* Add a task by clicking **Add task** in the desired phase
* Move a task by dragging and dropping it (unless it is complete)
* [Edit a task's details](/xl-release/how-to/working-with-tasks.html) by clicking it
* Assign a task to yourself by clicking ![Task action menu](/images/menu_three_dots.png)
* [Change a task's type](/xl-release/how-to/change-a-task-type.html) by clicking ![Task action menu](/images/menu_three_dots.png)
* Skip, fail, duplicate, or delete a task by clicking ![Task action menu](/images/menu_three_dots.png)

In an active release, ![Active task indicator](/images/active_task_arrow.png) indicates the task that is currently active. Note that you cannot move, edit, or delete a task that has already been completed.

## Differences between templates and releases

You use the release flow editor for templates, planned releases, and active releases, but there are some differences in the actions that are available:

{:.table .table-striped}
| Action | Description | Available in... |
| ------ | ----------- | --------------- |
| Add Phase | Add a new phase after the last phase; you can drag and drop phases to rearrange them | Template<br />Planned release<br />Active release |
| New Release | Create a new release from the [template](/xl-release/how-to/create-a-release-template.html) | Template |
| Start Release | Start a release that is in the planned state. |  Planned release |
| Abort Release | Stop a release that is active, and abort it. | Planned release<br />Active release |
| Restart Phase | Abort a phase in a release that is active and [restart the release](/xl-release/how-to/restart-a-phase-in-an-active-release.html) from any past phase | Active release |
| Export Excel | Download the current release in Microsoft Excel format (.xlsx) | Planned release<br />Active release |
| Export XLR | Download the template in ZIP format; you can [import a template](/xl-release/how-to/import-a-release-template.html) in the Template Overview | Template |
| Export XFile| Download the template in [XFile](/xl-release/concept/release-as-code.html) format | Template |
