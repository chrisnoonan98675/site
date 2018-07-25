---
no_mini_toc: true
title: Types of tasks in XL Release
categories:
xl-release
subject:
Task types
tags:
task
weight: 440
---

XL Release includes many types of [tasks](/xl-release/how-to/working-with-tasks.html). Some task types represent actions taken by a person, while others are automated and provide integration with other tools.

**Tip:** In addition to the built-in task types, you can create your own [custom task types](/xl-release/how-to/create-custom-task-types.html).

## Human task types

In human tasks, a person performs an action and indicates when it is done.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------| ----------| -------------------------------|
| [Gate](/xl-release/how-to/create-a-gate-task.html) | Contains conditions that must be fulfilled before the release can continue | Red |
| [Manual](/xl-release/how-to/create-a-manual-task.html) | Represents a step in a template or release that must be completed by a person | Gray |
| [User Input](/xl-release/how-to/create-a-user-input-task.html) | Allows users to provide values for release variables | Gray |

## Automated task types

In automated tasks, the XL Release execution engine performs an automated script.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------| ----------| -------------------------------|
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
| --------| ----------| -------------------------------|
| [Parallel Group](/xl-release/how-to/create-a-parallel-group.html) | A container for tasks that are executed simultaneously | None |
| [Sequential Group](/xl-release/how-to/create-a-sequential-group.html) | A container for tasks that are executed in sequence |  None |
