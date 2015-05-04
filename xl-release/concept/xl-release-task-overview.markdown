---
title: XL Release task overview
categories:
- xl-release
subject:
- Tasks
tags:
- task
- user interface
---

When you log in, you see the task overview, which shows the active tasks that are assigned to you or to a team that you are in.

![Task Overview](../images/task-overview.png)

Tasks are grouped by release. To hide the tasks of a release, click the triangle to the left of it.

## Task details

![Task Details](../images/task-overview-details.png)

The following information appears for each task:

* **Status icon**:

	* Orange icon—The task is currently active.
	* Clock icon—The task is waiting to start at a predefined time in the future (task state is 'pending').

* **Task type** and **Status label**. Each task type has its own icon. For example a person icon identifies the task as a 'manual task'. The status label below it indicates the current execution state of the task. 

* **Task title** and **assignee**: The task title and the name of the person or team that is responsible for it.

* **Task actions**: The actions on the task that are currently relevant and that you have permission to perform.

	* **Complete**: Complete the task and advance to the next task in the release. You can optionally add a comment when completing a task.
	* **Skip**: Skip the task and advance to the next task in the release. This action indicates that the work has not been done. You are required to enter a comment when skipping a task.
	* **Fail**: Put the human task in 'failed' state and notify the release owner. This action halts the release process. You can use it to signal that something is wrong and you don't know how to resolve it. You are required to enter a comment when failing a task.
	* **Abort**: Stops the automated task, put it in 'failed' state and notify the release owner. This action halts the release process. You are required to enter a comment when aborting a task.
	* **Assign to me**: Assigns the task to you.
	* **View in Release**: Opens the release flow for the task.

* **Number of comments**: The number of comments that have been added to the task.

* **Phase**: The phase the task is in.

* **Started**, **Planned**, or **Scheduled**: If the task has already started, its start date appears. If the task is scheduled to start at a specific future date, that date appears. "Planned" indicates that the task is waiting for a previous task to complete, and "Scheduled" indicates that it is waiting for its start date. See the task [lifecycle](/xl-release/concept/task-life-cycle.html) for more information.

* **Due**: If a due date is set on the task, it appears.

* **Flag**: You can flag a task to indicate that progress was not as smooth as intended and that timely completion may be at risk.

	* Orange flag: Indicates that attention is needed
	* Red flag: Indicates timely completion is at risk
	
	When setting a flag, you can also set a status text. In the task overview, the status text appears below the task details with the color of the flag.

To see more information about a task, click it.

## Filtering tasks

You can use filtering and search options to query the tasks in the system.

Click **Filter options** to toggle the options:

* **Active tasks**: Show tasks that are currently active. When deselected, you can also see tasks that are coming up (task state 'planned').
* **Assigned to me**: Show tasks that are assigned directly to you.
* **Assigned to my teams**: Show tasks that are assigned to the teams that you are in.
* **Assigned to others**: Show tasks that are signed to people other than you and teams that you are not in.
* **Not assigned**: Show tasks that are not assigned to anybody.

**Tip:** Select *Not assigned* and *Active tasks* to see all tasks that are active, but that no one is looking at.

To filter on task title, use the **Filter by title** box. Enter part of a task title, release title, task owner, or teams.

To filter on dates, use the **From** and **To** options.
