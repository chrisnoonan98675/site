---
title: Execute tasks from the XL Deploy CLI
categories:
- xl-deploy
subject:
- Command-line interface
tags:
- cli
- script
- task
---

XL Deploy can perform many deployments at the same time. Each of these deployments is called a _task_. Users can ask XL Deploy to start a task, stop a task or cancel a task.

Once a task is completed or canceled, it is moved to the _task archive_. This is where XL Deploy stores its task history. You can query it for tasks and examine the tasks steps and logs or export the task archive to an XML file.

Active tasks are stored in the XL Deploy _task registry_ which is periodically backed up to a file. If the XL Deploy Server is stopped abruptly, the tasks in the registry are persisted and can be continued when the server is restarted.

## Starting, stopping and canceling

Whenever you start a deployment or undeployment in XL Deploy, the XL Deploy CLI returns a `TaskInfo` object. This object describes the steps XL Deploy will take to execute your request, but it doesn't yet start execution. Instead, XL Deploy creates a task for the request and returns its id as the `id` field in the `TaskInfo` object. Using the task id, you can start, stop or cancel the task.

There are two ways to start a task in the XL Deploy CLI: `startTaskAndWait` and `startTask`. In the deployment example above we used `startTaskAndWait`. This method starts the tasks and waits for it to complete. The `startTask` method starts the task in XL Deploy and returns immediately. The task is run in the background in this case. Both methods can also be used to restart a failed task.

If a task is running and you want to to stop it, use the `stopTask` method. This attempts to interrupt the currently running task.

The `cancelTask` method is used to cancel a task. That is, abandon execution of the task and move it to the archive.

When a task has finished running, you can use the `archive` method to archive it. 

## Scheduling tasks

A task can be scheduled by calling the `tasks.schedule(taskId, dateTime)` method. First argument is the `taskId`. Second argument is the date and time.

Make sure you import the `DateTime` class:

    from org.joda.time import DateTime

You can create a `DateTime` object representing the current local time by calling `DateTime()`. For example you can schedule a task 2 hours in advance by calling:

	deployit> tasks.schedule(taskId, DateTime().plusHours(2))

You can also specify a concrete date. For example 31 December 2014, at 23:34 should be passed like: `DateTime(2014, 12, 31, 23, 34)`

Notice that the examples shown here will use the local timezone of the user. The server will always return date and time in UTC.

## Listing tasks

Before you can work with any of the tasks, you'll need to list them:

	deployit> depl = deployment.prepareInitial(package.id, environment.id)
    deployit> depl = deployment.generateAllDeployeds(depl)
    deployit> taskId = deployment.deploy(depl).id
    deployit> print deployit.listUnfinishedTasks()

This retrieves and shows a list of unfinished tasks that are assigned to the current user. The method returns an array of `TaskInfo` objects which you can actually print:

	deployit> for t in deployit.listUnfinishedTasks(): print "Task id " + t.id + " is assigned to user " + t.user

If you have `admin` permission, you can also list all tasks in XL Deploy:

	deployit> print deployit.listAllUnfinishedTasks()
    deployit> deployit.cancelTask(taskId)

## Assigning tasks

Tasks in XL Deploy are assigned to the user that started them. This means that they will appear in the XL Deploy GUI whenever this user logs out and back in again.

XL Deploy also supports reassigning tasks. If you have `task#assign` permission, you are allowed to assign a task currently assigned to you to another principal. If you have the `admin` permission, you can assign any task in the system to another principal.

This is how you assign a task in the CLI:

	# Import package
	deployit> package = repository.read('Applications/demo-application/1.0')

	# Load environment
	deployit> environment = repository.read('Environments/DiscoveredEnv')

	# Start deployment
	deployit> deploymentRef = deployment.prepareInitial(package.id, environment.id)
	deployit> deploymentRef = deployment.generateAllDeployeds(deploymentRef)
	deployit> taskID = deployment.deploy(deploymentRef).id

	deployit> deployit.assignTask(taskID, 'admin')
		
	# perform some operations on the task
		
	deployit> deployit.cancelTask(taskID)

**Note**: XL Deploy does not validate the principal you enter as the recipient of the task.

## Retrieving archived tasks from the repository

The `repository` object has facilities to retrieve an overview of all archived tasks, or a number of archived tasks within a specified date range.

To get all archived tasks, the command to use is:

	deployit> archivedTasks = repository.getArchivedTaskList()

This call will give you a list of `TaskWithBlock` object wrappers, on each of which you may call `get_step_blocks` that retrieves all step blocks from the task. So, to obtain some step block from a specific task, execute e.g.:

	deployit> first_step_block = archivedTasks[0].get_step_blocks()[0]
	
To obtain a certain step from the step block, do e.g.:
	
	deployit> first_step = first_step_block.getSteps()[0]

Next to all tasks, one may also just fetch all tasks within a given date range executing the following command:

	deployit> repository.getArchivedTasksList('01/01/2010', '01/01/2011')

Both date parameters in the method signature should be specified in the following format **mm/dd/yyyy**, with **m** a
month digit, **d** a day digit and **y** a year digit. The above method call will work exactly analogous to the `getArchivedTaskList()` method call, except that the resulting tasks all have been started on or after the given start date, but before or on the given end date.

### Exporting archived tasks from the repository to a local XML file

It's also possible to store the contents of the task repository to a local XML file. In order to store the complete task repository to a local XML file, use the following command:

	deployit> repository.exportArchivedTasks('/tmp/task-export.xml')

Note that you can use forward slashes in the path, even on Windows systems. 

It is also possible to export a number of tasks in a certain date range from the task repository to a local XML file using the following command:

	deployit> repository.exportArchivedTasks('/tmp/task-export.xml', '01/01/2010', '01/01/2011')
