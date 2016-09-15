---
title: Configure the task execution engine
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
- configuration
- deployment
---

In XL Deploy, deployment tasks are executed by the task execution engine. Based on your deployment task, the task execution engine generates a deployment plan that contains steps that XL Deploy will cary out to deploy an application.

## Tuning the task execution engine in XL Deploy 6.0.0 and later

In XL Deploy 6.0.0 and later, you can tune the XL Deploy task execution engine with the following settings in `<XLDEPLOY_SERVER_HOME>/conf/system.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `task.shutdown-timeout` | Time to wait for the task engine to shut down. | 1 minute |
| `task.max-active-tasks` | Maximum number of simultaneous running tasks allowed in the system. If this number is reached, the tasks will appear as `QUEUED` in the Task Monitor. Each `QUEUED` task will automatically start after a running task completes. | 100 |
| `task.recovery-dir` | Name of the directory in `<XLDEPLOY_SERVER_HOME>` where task recovery files are stored. | work |
| `task.step.retry-delay` | Time to wait before rerunning a step that returned a `RETRY` exit code. | 5 seconds |

 You can configure the thread pool that is available for step execution in `<XLDEPLOY_SERVER_HOME>/conf/system.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `task.StepExecutorDispatcher.thread-pool-executor.core-pool-size-min` | Minimum number of threads allocated for step execution | 10 |
| `task.StepExecutorDispatcher.thread-pool-executor.core-pool-size-max` | Maximum number of threads allocated for step execution | 10 |

You must restart the XL Deploy server after changing these settings.

## Tuning the task execution engine prior to XL Deploy 6.0.0

Prior to XL Deploy 6.0.0, you could configure the following advanced threading settings in `<XLDEPLOY_SERVER_HOME>/conf/deployit.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `taskThreadPool.corePoolSize` | Minimum number of threads allocated to execute tasks (setting has no effect in XL Deploy 5.5.x) | 10 |
| `taskThreadPool.maxPoolSize` | Maximum number of threads allocated to execute tasks (setting has no effect in XL Deploy 5.5.x) | 2147483647 |
| `taskThreadPool.keepAliveSeconds` | Number of seconds a task thread is kept alive before destroying it if the number of allocated threads exceeds | |
| `taskThreadPool.queueCapacity` | Capacity of the queue that holds tasks to be executed if no threads are available | 2147483647 |

You must restart the XL Deploy server after changing these settings.
