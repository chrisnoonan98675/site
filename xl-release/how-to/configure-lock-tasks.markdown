---
title: Configure lock tasks (XL Release 8.0)

categories:
-XL-release

subject:
-lock tasks

tags:
- Releases
- Lock tasks
- Locks
---

## Lock tasks
Lock tasks are mandatory. If a lock task occurs during a release, this task needs to be executed before the release can continue. For more information, see [Lock tasks](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html).

Configuring lock tasks involves the following procedures:
1. Granting permission to a user.
2. Adding locks to tasks.
3. Setting approvals.

### Grant lock permission to a user
Lock permission is role based and must be added to a user's profile before they can lock or unlock tasks. We recommend that this role is given to users who create release pipelines, or specific compliance people.

**Note:** Before adding lock tasks, specify, design, and test your complete pipeline. Adding locks before the pipeline is designed and tested will make the design process complex.

1. From the navigation bar, click **User management**.
2. Click **Permissions**.

### Adding locks to tasks
1. ...
2. ...
3. ...

### Setting approvals
A locked task can be either: automated - a release will continue once a locked task has completed, or, manual - a user will need to sign-off in XL Release or by remote approval.

#### Configuring an automated task
1. ...
2. ...
3. ...

#### Configuring a manual task
1. ...
2. ...
3. ...

### Lock task rules
Locking
