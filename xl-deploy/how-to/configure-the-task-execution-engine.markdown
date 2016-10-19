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

In XL Deploy 6.0.0 and later, you can tune the XL Deploy task execution engine with the following settings in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `task.shutdown-timeout` | Time to wait for the task engine to shut down. | 1 minute |
| `task.max-active-tasks` | Maximum number of simultaneous running tasks allowed in the system. If this number is reached, the tasks will appear as `QUEUED` in the Task Monitor. Each `QUEUED` task will automatically start after a running task completes. | 100 |
| `task.recovery-dir` | Name of the directory in `XL_DEPLOY_SERVER_HOME` where task recovery files are stored. | `work` |
| `task.step.retry-delay` | Time to wait before rerunning a step that returned a `RETRY` exit code. | 5 seconds |

 You can configure the thread pool that is available for step execution in `XL_DEPLOY_SERVER_HOME/conf/system.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `task.step-dispatcher.thread-pool-executor.core-pool-size-min` | The minimum core thread pool size. | 4 |
| `task.step-dispatcher.thread-pool-executor.core-pool-size-max` | The maximum core thread pool size. | 10 |
| `task.step-dispatcher.thread-pool-executor.max-pool-size-min` | The minimum max pool size. | 8 |
| `task.step-dispatcher.thread-pool-executor.max-pool-size-max` | The maximum value for the max pool size. | 64 |
| `task.step-dispatcher.thread-pool-executor.task-queue-size` | The size of the queue used by the thread pool executor. This value define how quickly the pool size will grow when there are more thread requests than there are threads.  | 100 |

**Important:** Threads are shared by all running tasks in the system; they are not created per deployment.

### Task execution example

To understand how these values impact task execution, consider a simple example. Assume there is an application that contains six deployables, all of type `cmd.Command`. Each one is configured with a command to sleep for 15 seconds.

In `XL_DEPLOY_SERVER_HOME/conf/system.conf`, set the ThreadPoolExecutor core thread pool size to `2`:

    task.step-dispatcher.thread-pool-executor.core-pool-size-min=2
    task.step-dispatcher.thread-pool-executor.core-pool-size-max=2

Leave the default values for all other settings. Restart the XL Deploy server so the settings take effect.

After the server starts, set up a deployment of the application to an environment. In the **Deployment Properties**, set the orchestrator to `parallel-by-deployed`. This ensures that the deployment steps will be executed in parallel. Your deployment will look like:

![Sample deployment plan with parallel-by-deployed orchestrator](images/tuning/deployment-plan.png)

Click **Execute** to start the execution. Because the core pool size is `2`, only two threads will be created and used for step execution. The XL Deploy execution engine will start executing two steps and the rest of the steps will be in a queued state:

![Deployment with limited core pool size](images/tuning/execution-first-two-tasks.png)

After the two executing steps are done, the next two steps will be picked for execution. So, two steps are executed at a time.

Although the thread pool executor can create up to 64 threads, it takes the `task.step-dispatcher.thread-pool-executor.task-queue-size` setting into account when adjusting the thread pool size. The executor automatically adjusts the thread pool size according to the bounds set by the `core-pool-size` and `max-pool-size` settings. When a new step is submitted for execution and there are fewer threads than the `core-pool-size` value, the executor will create a new thread for the step execution. When you reach the `core-pool-size` limit, a new thread will only be created when the queue is full. The size of the queue is governed by the `task-queue-size` property, which is 100 by default. The example above did not reach this number, as there were only six steps to execute, so the executor did not create any new threads.

To see the `max-pool-size` setting in action, set `task-queue-size` to `2`:

    task.step-dispatcher.thread-pool-executor.core-pool-size-min=2
    task.step-dispatcher.thread-pool-executor.core-pool-size-max=2
    task.step-dispatcher.thread-pool-executor.task-queue-size=2

Restart the XL Deploy server so the settings take effect, and perform the same deployment again. You will notice that four steps will execute in parallel and two will be queued. As discussed above, the thread pool executor only creates new threads when the number of requests waiting for threads is more than `task-queue-size` and the `max-pool-size` limits are not reached. After creating two more threads, the queue size will be `2` again, so two steps will remain in the queued state.

![Deployment with limited max pool size](images/tuning/execution-with-task-queue-size.png)

**Important:** This simple example assumes that there is only one task running in the system at a time. Keep in mind that threads are shared among all tasks in the system; they are not created per deployment.

To learn more about the thread pool executor, refer to [the Java documentation for ThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html).

## Tuning the task execution engine prior to XL Deploy 5.0.0

Prior to XL Deploy 5.0.0, you could configure the following advanced threading settings in `XL_DEPLOY_SERVER_HOME/conf/deployit.conf`:

{:.table .table-striped}
| Setting | Description | Default |
| ------- | ----------- | ------- |
| `taskThreadPool.corePoolSize` | Minimum number of threads allocated to execute tasks (setting has no effect in XL Deploy 5.5.x) | 10 |
| `taskThreadPool.maxPoolSize` | Maximum number of threads allocated to execute tasks (setting has no effect in XL Deploy 5.5.x) | 2147483647 |
| `taskThreadPool.keepAliveSeconds` | Number of seconds a task thread is kept alive before destroying it if the number of allocated threads exceeds | |
| `taskThreadPool.queueCapacity` | Capacity of the queue that holds tasks to be executed if no threads are available | 2147483647 |

You must restart the XL Deploy server after changing these settings.
