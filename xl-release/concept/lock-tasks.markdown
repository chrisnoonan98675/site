---
title: Lock tasks (XL Release 8.0)

categories:
- xl-release
subject:
- Lock tasks
tags:
- Releases
- Lock tasks
- Locks
since:
- XL Release 8.0
---

**Questions:**
- Can lock permission be granted to a team?
- Can I add lock permissions from both 'User management > Permissions' and 'Design > Teams & Permissions' pages.

## Lock tasks
Lock tasks are mandatory. If a lock task occurs during a release, this task needs to be executed before the release can continue.

In highly regulated environments with strict compliance requirements, lock tasks ensure that standards are adhered to, and also that evidence along task details, are stored to an evidence database. Lock tasks make continuous delivery and compliance possible in regulated environments.

In an environment that is not highly regulated, and steps are open to interpretation, lock tasks ensure that mandatory activities are executed. In this case, you can give teams the freedom to compose their own pipeline and adjust it to their situation.

Locked tasks can be:
- Automated - a release will continue once a locked task has completed.

  Or

- Manual - for a release to continue, a user will need to sign-off in XL Release, or by remote approval.

### Lock permissions and pipeline design
Lock permission is role based and must be added to a user's profile before they can lock or unlock tasks. We recommend that this role is given to users who create release pipelines, or specific compliance people. Before adding lock tasks, specify, design, and test your complete pipeline. Adding locks before the pipeline is designed and tested will make the design process complex.

For more information, see [Configure global permissions ](https://docs.xebialabs.com/xl-release/how-to/configure-permissions.html).

### Audits
Lock tasks ensure your audit trail. If a team decides to skip certain activities, limited evidence is stored in the XL Release Evidence database. This can result in bad auditing results.
By locking tasks you ensure that they are executed and also that evidence, along with task details, and signee information, is stored in the evidence database.

### Lock tasks and groups
Using lock tasks in a group ensures that steps will be executed in a certain order. For example, a signoff can only be given after a static code scan.

For more information on lock tasks and how to use them, see [Configure lock tasks] holder](https://docs.xebialabs.com/xl-release/how-to/configure-lock_tasks.html).

### Lock task specification table
The following table defines what you can and can't do when a task is locked.

**When a task is locked, the following actions available:**
- Add or edit comments? :heavy_check_mark:   
- Add watchers   :heavy_check_mark:  
- Complete **complete what?**  :heavy_check_mark:

**When a task is locked, the following actions are not available:**
- Edit title, description, start/end dates, and tags     :x:  
- Edit something task specific (like scripts, remote servers, cc, bcc, etc) :x:    
- Toggle postponed during blackout :x:
- Remove attachments :x:    
- Edit preconditions :x:    
- Assign and owner, to a team, or to a person :x:  
- Remove watchers  :x:
- Move  :x:
- complete in advance
