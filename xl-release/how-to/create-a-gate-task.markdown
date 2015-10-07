---
title: Create a gate task
categories:
- xl-release
subject:
- Task types
tags:
- task
- gate
---

A **gate** is a type of task that contains conditions that must be fulfilled before the release can continue. There are two types of conditions: simple checkboxes and dependencies on other releases.

![Gate Details](../images/gate-details.png)

## Checkbox conditions

The gate details window shows the checkbox conditions. If you have the [edit task](/xl-release/how-to/configure-permissions-for-a-release.html) permission, you can add a checkbox by clicking **Add condition**. To remove a condition, click the cross icon.

Checkboxes must be completed by users who are involved in the release. When a gate task is active, it can only be completed with all of its conditions are met; that is, when all checkboxes have been ticked. The gate task will not automatically complete when the conditions are met; the task assignee must mark it as complete.

## Dependencies

The gate details window also shows dependencies on other releases. A gate task can wait on other releases, on the level of release, phase, or task. In the screenshot above, there is a dependency on the "Deploy version to QA" task in the QA phase of the "Back office services 3.2" release.

Click **Add dependency** to create a new dependency or click an existing dependency to edit it.

![Dependency Editor](../images/dependency-editor.png)

Use the dependency editor to select the conditions for the dependency:

* The release the gate will wait on (only current releases appear)
* The release phase the gate will wait on (optional)
* The release task the gate will wait on (optional)

When the gate contains dependencies and no conditions, it completes _automatically_ when all dependent releases, phases, or tasks are complete.

When a dependent task or release fails, the gate does not fail. It waits until the release is restarted and the task is completed or skipped. A gate fails if a release it depends on is aborted.
