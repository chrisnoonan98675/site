---
title: Types of tasks in XL Release
---

XL Release includes many types of [tasks](/xl-release/how-to/working-with-tasks.html). Some task types represent actions taken by a person, while others are automated and provide integration with other tools.

**Tip:** In addition to the built-in task types, you can create your own [custom task types](/xl-release/how-to/create-custom-task-types.html).

## Human task types

In human tasks, a person performs an action and indicates when it is done.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Gate](/xl-release/how-to/create-a-gate-task.html) | Contains conditions that must be fulfilled before the release can continue | Red |
| [Manual](/xl-release/how-to/create-a-manual-task.html) | Represents a step in a template or release that must be completed by a person | Gray |
| [User Input](/xl-release/how-to/create-a-user-input-task.html) | Allows users to provide values for release variables | Gray |

## Automated task types

In automated tasks, the XL Release execution engine performs an automated script.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Create Release](/xl-release/how-to/create-a-create-release-task.html) | Creates and starts a release based on a template | Green |
| [Jenkins](/xl-release/how-to/create-a-jenkins-task.html) | Runs a Jenkins job | Blue |
| [JIRA](/xl-release/how-to/jira-plugin.html) | Query and/or update issues on an Atlassian JIRA server | Blue |
| [Notification](/xl-release/how-to/create-a-notification-task.html) | Automatically sends an email when the task becomes active | Gray |
| [Remote Script](/xl-release/how-to/remote-script-plugin.html) | Executes commands on remote hosts | Blue |
| [Script](/xl-release/how-to/create-a-script-task.html) | Executes a Python script on the XL Release server | Gray |
| [Webhook](/xl-release/how-to/create-a-webhook-task.html) | Sends an HTTP query and parses the response (XML or JSON)| Blue |
| [XL Deploy](/xl-release/how-to/create-an-xl-deploy-task.html) | Tells [XL Deploy](https://docs.xebialabs.com/xl-deploy/) to deploy an application to an environment | Green |

## Container task types

You use container tasks to group other types of tasks. The container task types are:

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Parallel Group](/xl-release/how-to/create-a-parallel-group.html) | A container for tasks that are executed simultaneously | None |
| [Sequential Group](/xl-release/how-to/create-a-sequential-group.html) | A container for tasks that are executed in sequence |  None |

## Custom task types

[custom task types](/xl-release/customize-custom-task-types.html)
PLACEHOLDER

## Change a task's type

You can change the [type of a task](/xl-release/concept/types-of-tasks-in-xl-release.html) in:

* A template
* A planned release that has not started yet
* An active release, if the task that you want to change has not started yet

To change the type of a task in a template, you need the **Edit Template** permission on the template. To change the type of a task in a release, you need the **Edit Task** permission on the release.

To change a task's type:

1. Open the template or release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).
1. Click ![Task action menu](/images/menu_three_dots.png) on the task you want to change.
1. In the menu, hover over **Change type**.
1. Select the desired task type.

    XL Release will copy the values of properties that are shared between the task's previous type and the type that you selected.

Note that:

* You cannot change the type of an active, completed, failed, or skipped task.
* You can change a task to a Parallel or Sequential Group, but you cannot change a Parallel or Sequential Group to a different type of task.

## Restrict access to task types

In XL Release, you can restrict access to certain [task types](/xl-release/concept/types-of-tasks-in-xl-release.html) based on a user's [role](/xl-release/how-to/configure-roles.html). To configure task access, select **Settings** > **Task access** from the top menu.

![Task access](/xl-release/images/task-access.png)

For each **Task type**, you can either:

* Select **Available for all users** if all users should be able to create or edit tasks of that type
* Specify the roles that have access to the task type under **Restricted to roles**

If you clear the **Available for all users** option and you do not add any roles, the task type will be unavailable to all users except those who have the *admin* permission.

Click **Save** to apply your changes. To discard your changes without saving, click **Reset**.
