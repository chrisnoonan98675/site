---
title: Configure the task execution engine
categories:
- xl-deploy
subject:
- Installation
tags:
- system administration
- configuration
- deployment
since:
- XL Deploy 6.0.0
---

In XL Deploy, deployment tasks are executed by the task execution engine. Based on your deployment task, the task execution engine generates a deployment plan that contains steps that XL Deploy will cary out to deploy an application.

You can configure the XL Deploy task execution engine with the following settings in `<XLDEPLOY_HOME>/conf/system.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `task.shutdown-timeout` | Time to wait for the task engine to shut down. | 1 minute |
| `task.max-active-tasks` | Maximum number of simultaneous running tasks allowed in the system. If this number is reached, the tasks will appear as `QUEUED` in the Task Monitor. Each `QUEUED` task will automatically start after a running task completes. | 100 |
| `task.recovery-dir` | Name of the directory in `<XLDEPLOY_HOME>` where task recovery files are stored. | work |
| `task.step.retry-delay` | Time to wait before rerunning a step that returned a `RETRY` exit code. | 5 seconds |

 You can configure the thread pool that is available for step execution in `<XLDEPLOY_HOME>/conf/system.conf`:

{:.table .table-striped}
| Setting     | Description     | Default|
| ------------- | ------------- |-------------|
| `task.StepExecutorDispatcher.thread-pool-executor.core-pool-size-min` | Minimum number of threads allocated for step execution | 10 |
| `task.StepExecutorDispatcher.thread-pool-executor.core-pool-size-max` | Maximum number of threads allocated for step execution | 10 |


You must restart the server after changing these settings.
