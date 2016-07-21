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
weight: 245
---

XL Deploy can perform many deployments at the same time. Each of these deployments is called a _task_. Users can ask XL Deploy to start a task, stop a task or cancel a task. After a task is completed or canceled, it is moved to the _task archive_. This is where XL Deploy stores its task history. You can query it for tasks and examine the tasks steps and logs or export the task archive to an XML file.

Active tasks are stored in the XL Deploy _task registry_ which is periodically backed up to a file. If the XL Deploy Server is stopped abruptly, the tasks in the registry are persisted and can be continued when the server is restarted.

## Starting, stopping, and canceling

Whenever you [start a deployment or undeployment](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html#sample-cli-scripts) in XL Deploy, the [command-line interface (CLI)](/xl-deploy/concept/getting-started-with-the-xl-deploy-cli.html) returns a `TaskInfo` object. This object describes the steps XL Deploy will take to execute your request, but it doesn't yet start execution. Instead, XL Deploy creates a task for the request and returns its id as the `id` field in the `TaskInfo` object. Using the task ID, you can start, stop, or cancel the task.

There are two ways to start a task in the XL Deploy CLI: `startTaskAndWait` and `startTask`. The `startTaskAndWait` method starts the task and waits for it to complete, while `startTask` starts the task and returns immediately (in this case, the task runs in the background). Both methods can be used to restart a failed task.

If a task is running and you want to to stop it, use the `stopTask` method. This attempts to interrupt the currently running task. The `cancelTask` method is used to cancel a task; that is, abandon execution of the task and move it to the archive.

When a task has finished running, you can use the `archive` method to archive it.

## Scheduling tasks

A task can be scheduled by calling the `task2.schedule(String taskId, DateTime dateTime)` method. The first argument is the task ID and the second argument is the date and time.

Ensure that you import the `DateTime` class:

    from org.joda.time import DateTime

You can create a `DateTime` object representing the current local time by calling `DateTime()`. For example, to prepare the deployment of the KidsStore 1.0.0 application to the TEST01 environment and schedule it for two hours in the future, execute:

    # Load package
    package = repository.read('Applications/Sample Apps/KidsStore/1.0.0')

    # Load environment
    environment = repository.read('Environments/Testing/TEST01')

    # Start deployment
    deploymentRef = deployment.prepareInitial(package.id, environment.id)
    depl = deployment.prepareAutoDeployeds(deploymentRef)
    task = deployment.createDeployTask(depl)
    task = task2.schedule(task.id, DateTime().plusHours(2))

You can also specify a concrete date. For example, 31 December 2014 at 23:34 would be specified as `DateTime(2014, 12, 31, 23, 34)`.

Note that the examples shown here will use the local time zone of the user. The server will always return the date and time in UTC.

## Assigning tasks

Tasks in XL Deploy are assigned to the user that started them. This means that they will appear in the XL Deploy GUI whenever this user logs out and back in again.

XL Deploy also supports reassigning tasks. If you have `task#assign` permission, you are allowed to assign a task currently assigned to you to another principal. If you have the `admin` permission, you can assign any task in the system to another principal.

This is how you assign a task in the CLI:

	# Import package
    package = repository.read('Applications/Sample Apps/KidsStore/1.0.0')

	# Load environment
    environment = repository.read('Environments/Testing/TEST01')

	# Start deployment
    deploymentRef = deployment.prepareInitial(package.id, environment.id)
    depl = deployment.prepareAutoDeployeds(deploymentRef)
    task = deployment.createDeployTask(depl)

    # Reassign deployment task
    deployit.assignTask(task.id, 'john')

**Note**: XL Deploy does not validate the principal you enter as the recipient of the task.

## Retrieving archived tasks from the repository

The `repository` object can retrieve an overview of all archived tasks or a number of archived tasks within a specified date range. To get all archived tasks, execute:

    archivedTasks = repository.getArchivedTaskList()

This call returns a list of `TaskWithBlock` object wrappers, on each of which you can call `get_step_blocks` that retrieves all step blocks from the task. So, to obtain some step block from a specific task, execute:

    first_step_block = archivedTasks[0].get_step_blocks()[0]

To obtain a certain step from the step block, execute:

    first_step = first_step_block.getSteps()[0]

You can also fetch the tasks within a given date range:

    repository.getArchivedTasksList('01/01/2010', '01/01/2011')

The start and end date parameters in the method signature should be specified in `mm/dd/yyyy` format.

### Exporting archived tasks from the repository to a local XML file

You can export the contents of the task repository to a local XML file:

    repository.exportArchivedTasks('/tmp/task-export.xml')

**Tip:** You can use forward slashes (`/`) in the path, even on Microsoft Windows systems.

You can also export the tasks within a given date range:

    repository.exportArchivedTasks('/tmp/task-export.xml', '01/01/2010', '01/01/2011')

## Using the stepPath parameter

The `TaskBlockService` allows you to retrieve information about a step in a deployment plan with the API call [`GET /tasks/v2/{taskid}/step/{stepPath}`](/xl-deploy/5.5.x/rest-api/com.xebialabs.deployit.engine.api.TaskBlockService.html#/tasks/v2/{taskid}/step/{stepPath}:GET). In a CLI script, this can be called as:

    task2.step(String taskId, String stepPath)

The `stepPath` parameter has three parts: the root, the number of the task block, and the number of the step in the block. In XL Deploy 4.0.x and 4.5.x, the root is always `0`. In XL Deploy 5.0.x and later, the root is always `0_0`. The parts of the `stepPath` are separated by underscores.

In the following XL Deploy 4.5.x example, the deployment task has one block. The `stepPath` for the first step is `0_1`, while the `stepPath` for the second step is `0_2`. The API call would be:

    task2.step("1582b3b8-096c-48ba-a1bd-85aed09b0ec9", "0_1")

![Deployment plan with no step blocks](images/plan-with-no-step-blocks.png)

In the following XL Deploy 5.0.x example, the deployment task has two blocks, and each block has one step. The `stepPath` for the first step is `0_1_1`, while the `stepPath` for the second step is `0_2_1`. The API calls would be:

    task2.step("4b672090-366e-4b37-b631-9f170f175610", "0_1_1")
    task2.step("4b672090-366e-4b37-b631-9f170f175610", "0_2_1")   

![Deployment plan with no step blocks](images/plan-with-two-step-blocks.png)
