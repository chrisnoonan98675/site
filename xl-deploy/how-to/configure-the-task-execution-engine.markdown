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
| `task.step-dispatcher.thread-pool-executor.core-pool-size-min` | Sets the minimum core thread pool size | 4 |
| `task.step-dispatcher.thread-pool-executor.core-pool-size-max` | Sets the maximum core thread pool size | 10 |
| `task.step-dispatcher.thread-pool-executor.max-pool-size-min` | Sets the minimum max pool size | 8 |
| `task.step-dispatcher.thread-pool-executor.max-pool-size-max` | Sets the maximum value for max pool size | 64 |
| `task.step-dispatcher.thread-pool-executor.task-queue-size` | Sets size of the queue used by thread pool executor. This value define how quick the pool size will grow when there are more thread requests than threads.  | 100 |

To understand how these values impact task execution let's take a simple example. Assume there is a deployment task with 6 deployables. These deployables are of type `cmd.Command`. The command they use is sleep 15 seconds.
In your `<XLDEPLOY_SERVER_HOME>/conf/system.conf` use these values for thread pool:

```
task.step-dispatcher.thread-pool-executor.core-pool-size-min=2
task.step-dispatcher.thread-pool-executor.core-pool-size-max=2
```

In the configuration shown above, we have defined that the ThreadPoolExecutor core pool size is 2. As we have not changed other configuration values so default values will be used. You must restart the XL Deploy server after changing these settings.

Let's deploy this application to a environment. In the `Deployment Properties` choose the orchestrator to be `parallel-by-deployed`. This is required so that steps are executed in parallel. Your deployment will look like as shown below.

![](images/tuning/deployment-plan.png)

Press the `Execute` button to start the execution. As we have defined core pool size as 2 so only two threads will created and used for step execution. XL Deploy execution engine will start executing two steps and rest of the steps will be in queued state as shown below.

![](images/tuning/execution-first-two-tasks.png)

Once two executing steps are done, next two steps will be picked for execution. So, two steps are executed at a time.

Some of you might have noticed that we have specified max size of the ThreadPoolExecutor. So, you might think why ThreadPoolExecutor didn't created more threads as max value could go up to 64. The reason for it is the configuration property `task.step-dispatcher.thread-pool-executor.task-queue-size`. ThreadPoolExecutor automatically adjust the thread pool size according to the bounds set by `core-pool-size` and `max-pool-size` configuration properties. When a new step is submitted for execution and there are fewer than `core-pool-size` threads running, executor will create a new thread for the step execution. When you hit the limit of core-pool-size, a new thread will only be created when queue is full. The size of queue is governed by `task-queue-size` property. The default value of `task-queue-size` is 100. In the task execution we performed above we didn't reached this number as we only had 6 steps to execute. So, no new thread was created.

To see `max-pool-size` in action, let's set `task-queue-size` to a lower value 2. Update the `<XLDEPLOY_SERVER_HOME>/conf/system.conf` to use following values.

```
task.step-dispatcher.thread-pool-executor.core-pool-size-min=2
task.step-dispatcher.thread-pool-executor.core-pool-size-max=2
task.step-dispatcher.thread-pool-executor.task-queue-size=2
```

You must restart the XL Deploy server after changing these settings. When you perform the deployment again, this time you will notice that four steps will be executing in parallel and two will be queued. As discussed above, ThreadPoolExecutor only creates new threads when number of requests waiting for threads are more than `task-queue-size` and `max-pool-size` limits are not reached. After creating two more threads, queue size will again be 2 so two steps will remain in the queued state.

![](images/tuning/execution-with-task-queue-size.png)

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
