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

You use the release flow editor for templates, planned releases, and active releases, though there are some differences in the actions that are available:

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

In an active release, an orange arrow indicates the current task. Also, completed tasks appear in grey, and you cannot move or edit them.

**Tip:** In XL Release 4.8.0 and later, you can take action on a task in the release flow editor without opening it by clicking ![Task action menu](/images/menu_three_dots.png) on the task. For example, you can assign a task to yourself, change its type, or complete, skip, fail, duplicate, or delete the task.
