---
title: Understanding tasks in XL Deploy
categories:
- xl-deploy
subject:
- Deployment
tags:
- task
---

A task is an activity in XL Deploy. When starting a deployment, XL Deploy will create and start a task. The task contains a list of _steps_ that must be executed to successfully complete the task. XL Deploy will execute each of the steps in turn. When all of the steps are successfully executed, the task itself is successfully executed. If one of the steps fails, the task itself is marked failed.

XL Deploy supports the following types of tasks:

* Deploy application: Deploys a package onto an environment
* Update application: Updates an existing deployment of an application
* Undeploy application: Undeploys a package from an environment
* Rollback: Rolls back a deployment
* Discovery: Discovers middleware on a host
* Control task: Interacts with middleware on demand

## Task Recovery

XL Deploy periodically stores a snapshot of the tasks in the system to be able to recover tasks if the server is stopped abruptly. XL Deploy will reload the tasks from the recovery file when it restarts. The tasks, deployed item configurations and generated steps will all be recovered. Tasks that were executing (or failing, stopping or aborting) in XL Deploy when the server stopped will be put in _failed_ state so the user can decide whether to rerun or cancel it. Only tasks that have been _pending_, _scheduled_ or _started_ will be recovered.

## Scheduling

XL Deploy allows you to schedule a task for execution at a specified later moment in time. All task types can be scheduled, this includes deployment tasks, control tasks and discovery tasks. You should prepare your task like you would normally do. Then instead of starting it, you can schedule it to any given date and time in the future. To prevent mistakes, you are not able to schedule tasks in the past. Notice that the amount of time you can schedule a task in the future is limited by a system specific value. Although you are guaranteed to be able to schedule a task at least 3 weeks ahead.
When a task is scheduled, the task is created and the status is set to scheduled. It will automatically start executing when the scheduled time has passed. Notice that the task will be _QUEUED_ if there is no executor available.

A scheduled task will never be archived automatically after it has been executed, so you need to do that manually when the execution has finished. When a scheduled task encounters an failure during execution, the task will be left with a _FAILED_ state in the system. A user should manually correct the problem and is then able to continue the task, or reschedule it.

It is possible to start a scheduled task immediately when required. The task will then start executing directly and the task is not scheduled anymore. It is also possible to reschedule a task to any other given moment in the future. A scheduled task can also be cancelled. When a scheduled task is cancelled it will be removed from the system, and the status will be stored in the task history. To perform these operations, take a look at the [GUI Manual](/xl-deploy/4.5.x/guimanual.html) or [CLI Manual](/xl-deploy/4.5.x/climanual.html).

Notice that the server will store the scheduled date and time using the UTC timezone, so for example any log statement will mention the UTC time. When a task is scheduled in relation to your local timezone, you should pass the correct timezone with the call, then XL Deploy will convert it to UTC for you. For you convenience, the UI allows you to enter the scheduled time in you local time zone, and it will automatically pass the correct timezone to the server.

When the server is restarted, either after a manual stop or a forced shutdown, XL Deploy will automatically reschedule all scheduled tasks that are not executed yet. If the task was scheduled for execution during the downtime, it will start immediately.

## Task State

XL Deploy allows a user to interact with the task. A user can:

* **Start the task**. XL Deploy will try to start executing the steps associated with the task. If there is no executor available, the task will be queued. The task can be started when the task is _pending_, _failed_, _stopped_ or _aborted_. Starting a task when _scheduled_ will also unschedule the task.

* **Schedule the task**. XL Deploy will schedule the task to execute it automatically at the specified time. A task can be scheduled when the task is _pending_, _failed_, _stopped_ or _aborted_.

* **Stop the task**. XL Deploy will wait for the currently executing step(s) to finish and will then cleanly stop the task. The state of the task will become _stopping_. Note that, due to the nature of some steps, this is not always possible. For example, a step that calls an external script may hang indefinitely. A task can only be stopped when _executing_.

* **Abort the task**. XL Deploy will attempt to interrupt the currently executing step(s). The state of the task will become _aborting_. If successful, the task is marked _aborted_ and the step is marked _failed_. The task can be aborted when _executing_, _failing_ or _stopping_.

* **Cancel the task**. XL Deploy will cancel the task execution. If the task was _executing_ before, the task will be stored since it may have made changes to the middleware. If the task was _pending_ and never started, it will be removed but not stored. The task can only be cancelled when it is _pending_, _scheduled_, _failed_, _stopped_ or _aborted_.

* **Archive the task**. XL Deploy will finalise the task and store it. Manually archiving is needed to be able to review a task when it is executed, and to decide whether or not a rollback is needed. Archiving the task can only be done when the task is _executed_.

![Task state](images/task-state-diagram.png)
