---
title: Task failure handler
categories:
- xl-release
subject:
- Tasks
tags:
- task
weight: 480
since:
- XL Release 8.1.0
---

As of XL Release 8.1.0 a new property was added on the task level called **Handling failure**. You can use this to execute a `Jython` script when the task fails its execution or just to skip the task.

![Task attachments](/xl-release/images/task-failure-handler.png)

* **Skip task**: If the task fails, it is automatically skipped.

* **Define additional action**: This option allows you to write your own Jython script to be executed if the task is going to fail.
**Caution**: If you do not modify the task status in your script, the task fails due to a wrong outcome. The modification of the task status (such as skip task, retry task, or other outcomes) should be the last line of your script.

## Example of scripts

These are examples of how you can use this feature.

### Retry a task a number of times

If your release process contains a third party dependency that is error prone and the task must be retried for a few times before you can conclude that something is broken:

* Create a release variable of type `Number`, named `attempt`, and set default value to `0`.
* Set the operation to **Define additional action**.
* Set the following script:

{% highlight python %}

from com.xebialabs.xlrelease.api.v1.forms import Comment
if (releaseVariables['attempt'] < 3):
  releaseVariables['attempt'] = releaseVariables['attempt'] + 1
  taskApi.retryTask(getCurrentTask().getId(), Comment("retrying again..."))
else:
  taskApi.skipTask(getCurrentTask().getId(), Comment("Skipped task from error handling"))

{% endhighlight %}

### Set a variable to be used on a precondition

Depending on the status of one of your tasks, you can execute the succeeding task or group of tasks by setting the value of a variable and using it in a [precondition](/xl-release/how-to/set-a-precondition-on-a-task.html):

* Create an `executeTask` variable of type `Boolean` with the default value set to `True` (click on checkbox).
* Set the operation to **Define additional action**.
* Set the following script:

{% highlight python %}

from com.xebialabs.xlrelease.api.v1.forms import Comment
releaseVariables['executeTask'] = False
taskApi.skipTask(getCurrentTask().getId(), Comment("Skipped task from error handling"))

{% endhighlight %}

* Set the precondition on a task or task group: `${executeTask}`.

### Skip a phase in advance

You can skip a complete phase if an error occurred in previous phases:

* Set the operation to **Define additional action**.
* Set the following script:

{% highlight python %}

from com.xebialabs.xlrelease.api.v1.forms import Comment
phase = phaseApi.searchPhasesByTitle("next phase", getCurrentRelease().getId())[0]
for task in phase.tasks:
  taskApi.skipTask(task.getId(), Comment("skipped from error handling") )
taskApi.skipTask(getCurrentTask().getId(), Comment("Skipped task from error handling"))

{% endhighlight %}

## Disabling the feature

If you want to disable this feature for a specific type of task, add the following line to `conf/deployit-defaults.properties`:

    xlrelease.GateTask.failureHandlerEnabled=false

This snippet sample disables **Handling failures** for the `Gate Task`.
