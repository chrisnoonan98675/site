---
title: Configuring lock tasks
categories:
- xl-release
subject:
- Tasks
tags:
- Releases
- Lock tasks
- Locks
since:
- XL Release 8.0.0
---

In XL Release 8.0 and later, the lock task feature is available. Lock tasks are mandatory. If a lock task occurs during a release, this task needs to be executed before the release can continue.

In highly regulated environments with strict compliance requirements, lock tasks ensure that standards are adhered to, and also that evidence along task details, are stored in activity logs. Lock tasks make continuous delivery and compliance possible in regulated environments.

In an environment that is not highly regulated, and steps are open to interpretation, lock tasks ensure that mandatory activities are executed. In this case, you can give teams the freedom to compose their own pipeline and adjust it to their situation.

Configuring lock tasks involves the following procedures:
1. Granting permission to a user or a team.
1. Adding locks to tasks.

## Audits
Lock tasks ensure your audit trail. If a team decides to skip certain activities, limited evidence is stored in activity logs. This can result in bad auditing results.
By locking tasks you ensure that they are executed and also that evidence, along with task details, and signee information, is stored in the evidence database.

## Grant lock permission to user or a team
Lock permission is role based and must be added to a user or team before they can lock or unlock tasks. We recommend that this role is given to users who create release pipelines, or specific compliance people.

**Important:** Before adding lock tasks, specify, design, and test your complete pipeline. Adding locks before the pipeline is designed and tested will make the design process complex.

  1. On the navigation bar, click **Design**.
  2. Click **Folders**.
  3. Select a folder.
  4. Click the **Teams & Permissions** tab.
  5. In the **Folder permissions** section, add the team name to **Lock release task** or **Lock release template task** or both.

  ![Lock permissions](../images/lock-permissions.png)

## Lock task actions

### Add a lock to task

1. Click the **Release** tab.
2. From the **Show** dropdown, select **Release flow**
3. Click ![menu button](../images/menuBtn.png) on the right side of a task or grouping.
2. Click **Lock**.

When a task is locked, a lock is displayed on the left side of the task and the task appears striped.       

![Locked task example](../images/locked-task.png)


### Remove a lock from a tasks
1. Click the **Release** tab.
2. From the **Show** dropdown, select **Release flow**
3. Click ![menu button](../images/menuBtn.png) on the right side of a task or grouping.
2. Click **Unlock**.

## Lock task specification
The following section defines what actions are available when a task is locked.

**When a task is locked, the following actions are available:**
- Add or edit comments
- Add watchers  
- Complete a task  
- Retry a task
- Abort a task
- Fail a task
- Add attachments
- Toggle conditions
- Set the variable values that will be passed to the created release

**When a task is locked, the following actions are not available:**
- Edit title, description, start and end dates, and tags    
- Edit something that is task specific (like scripts, remote servers, cc, bcc, etc.)    
- Toggle postponed during blackout
- Remove attachments   
- Remove watchers
- Edit preconditions  
- Assign and owner, to a team, or to a person
- Remove watchers  
- Move  
- Complete a task in advance
- Skip a task
- Duplicate a task
- Assign a task to an owner, a team, or to me
- Edit the subtasks from within a locked task grouping
- Add or remove the subtasks from within a locked task grouping
- Move a task from inside a locked task grouping
- Add or remove tasks from a parallel group
- Link or unlink a task
- Add or remove dependencies
- Add or remove conditions
- Toggle conditions
- Edit input or output properties
- Edit title, template, risk profile, or release ID
- Edit the variable list
