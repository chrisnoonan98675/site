---
title: Implement a custom release failure handler
categories:
- xl-release
subject:
- Tasks
tags:
- release
- task
- customization
- failure
since:
- XL Release 4.8.0
---

By default, when a task in a release fails, [the release stops](/xl-release/concept/release-life-cycle.html) so you can retry the task, skip the task, or add new tasks to deal with the situation. Alternatively, if the [Abort on failure](/xl-release/how-to/configure-release-properties.html) option is selected, the release immediately aborts if a failure occurs. This is useful for Continuous Integration/Continuous Delivery environments in which a new code commit will fix the problem and start a new release.

However, in some situations, other failure handling modes are useful. For example, instead of aborting the release, you might want to skip to a "failure handling block" that will run cleanup tasks and send notification emails. You can create this type of failure handler using two of XL Release's extension points: an [event listener](https://support.xebialabs.com/hc/en-us/community/posts/203782905-How-To-Create-an-event-listener-for-XL-Release-to-implement-a-post-save-hook-) (implemented in Java) and a [custom endpoint](/xl-release/how-to/declare-custom-rest-endpoints.html) (implemented in [Jython](/jython-docs/#!/xl-release/4.8.x/)).

**Tip:** For alternative approaches to handling task failures, refer to [Automatically handle failures in XL Release tasks](/xl-release/how-to/handle-failures-in-tasks.html).

## How the custom failure handler works

At a high level:

1. The event listener watches for "CI updated" events in which the status a CI of type `xlrelease.Release` is set to FAILED. When this status change occurs in a particular release for the first time, the listener calls the custom endpoint.
1. The custom endpoint sets the `releaseFailed` variable on the release and then skips all failed and remaining tasks, except those in a phase called "onFailure".

The source code for the [event listener](https://gist.github.com/xlcommunity/93b63a414df15798fd2d#file-onreleasefailureeventlistener-java) and [custom endpoint](https://gist.github.com/xlcommunity/93b63a414df15798fd2d#file-execute-py) are available as [a Gist](https://gist.github.com/xlcommunity/93b63a414df15798fd2d). Note that:

* By default, the event listener is configured to call the endpoint at a path under `http://localhost:5516/`. If your XL Release server runs on a different port (for example), you must update the event listener configuration as needed and recompile it. A build project with instructions is available in the Gist.

* When a release fails, the event listener receives multiple "CI updated" events for that particular release in close succession. To avoid calling the endpoint more than once when a particular release fails, the listener includes a simple cache that remembers any failed releases it has already seen for 10 seconds.

* Because the endpoint that the listener calls will attempt to update the release, you cannot call it directly from the "CI updated" event that the listener is processing; this event must complete first so that the endpoint can make its changes. This is accomplished by invoking the endpoint from a simple, single-thread executor in the listener. The listener simply dispatches an "invoke the endpoint for this release" task to the executor and completes; the executor then invokes the endpoint.

* In the endpoint implementation, it is not possible to skip the remaining outstanding tasks and then skip the failed tasks, because the release is in a "stopped" state. Instead, it inserts a "holding" task immediately after the first failed tasks and then skips the failed tasks. This restarts the release, which immediately waits on the holding task, making it possible to skip all remaining planned tasks and, finally, the holding task itself.

    This is the relevant excerpt from the custom endpoint implementation:

      ...
      logger.debug('Adding "{}" variable', RELEASE_FAILED_VARIABLE_NAME)
      releaseFailedVar = new_boolean_var(RELEASE_FAILED_VARIABLE_NAME, True, False, 'Has this release failed?', 'Automatically set by onFailure handler')
      releaseApi.createVariable(release.getId(), releaseFailedVar)

      # add a manual placeholder task immediately after the first failure
      # so we can activate the release again
      logger.debug('Adding placeholder task')
      placeholderTaskId = add_placeholder_task(release, onFailureUsername)

      logger.debug('Skipping failed tasks')
      skipComment = Comment('Skipped by onFailure handler')
      for task in release.getAllTasks():
      if task.isFailed() and not IS_PARALLEL_GROUP(task):
          logger.trace('Skipping failed task {}', task.getId())
          taskApi.skipTask(task.getId(), skipComment)

      logger.debug('Skipping planned tasks')
      for phase in release.getPhases():
      if not IS_ON_FAILURE_PHASE(phase):
          for task in phase.getAllTasks():
              # leave the placeholder task running
              if (task.isPlanned() or task.isInProgress()) and not IS_PARALLEL_GROUP(task) and task.getId() != placeholderTaskId:
                  logger.trace('Skipping planned or in progress task {}', task.getId())
                  skip_task(task.getId(), onFailureUsername, skipComment)

      logger.debug('Skipping placeholder task')
      skip_task(placeholderTaskId, onFailureUsername, skipComment)

      responseMsg = "Successfully executed onFailure handler for release '%s'" % (releaseId)'
      ...

All tasks that the handler creates or skips are assigned to the handler's designated user. This makes it easy to track the number of tasks that the handler skips and allows you to strip these tasks from other reports and statistics.

## Using the failure handler in a release

To use the handler in a release, you add an "onFailure" phase (usually at the end of the release) and add any desired cleanup tasks to that phase.

![onFailure phase](../images/failure-handler/failure-handler-onFailure-phase.png)

It is recommended that you ensure that the cleanup tasks are not inadvertently executed if the release does not fail by adding them to a group in the onFailure phase that checks the `releaseFailed` variable in a precondition.

![onFailure precondition](../images/failure-handler/failure-handler-onFailure-phase-precondition.png)

## Configure the failure handler in XL Release

To add the failure handler to your XL Release installation:

1. Log in to XL Release and create an internal user called `onFailure_user`. This user only requires sufficient permissions to modify the releases on which it will be invoked.

    ![onFailure_user user](../images/failure-handler/failure-handler-user.png)

    ![onFailure_user role](../images/failure-handler/failure-handler-role.png)

    ![onFailure_user permission](../images/failure-handler/failure-handler-permission.png)

1. Stop the XL Release server.

1. Download [the JAR file](https://gist.github.com/xlcommunity/93b63a414df15798fd2d/raw/45fa39c66ed82da3f13ae884d633a0c7aaebd083/xlr-on-failure-handler-2016-02-17.jar) and copy it to `XLRELEASE_SERVER_HOME/plugins`. If your server is not running on `http://localhost:5516`, download the build project instead, modify the scheme, host, and port to match your XL Release server, and build the JAR following the instructions in the README.

        @DeployitEventListener
        public class OnReleaseFailureEventListener {
            ...
            private static final String ENDPOINT_SCHEME = "http";
            private static final String ENDPOINT_HOST = "localhost";
            private static final int ENDPOINT_PORT = 5516;
            ...

1. Add a [custom password property](/xl-release/how-to/changing-passwords-in-xl-release.html#configure-custom-passwords) `onFailureHandler.password` to `XLRELEASE_SERVER_HOME/conf/xl-release-server.conf`. Set its value to the password of the `onFailure_user` user that you created. This value will be securely encrypted when the server starts.

    ![onFailure password](../images/failure-handler/failure-handler-custom-password.png)

1. Start the XL Release server.

1. Add a [global variable](/xl-release/how-to/configure-global-variables.html) called `${global.onFailureHandlerPath}`. Set its value to the path of the custom endpoint. The default value in the endpoint definition is `/api/extension/on-failure`.

    ![onFailure variable](../images/failure-handler/failure-handler-variable.png)

Now, if you create a release with an "onFailure" phase, the handler will trigger if one of the tasks fails. The release will skip the failed and remaining tasks and go to the first task in the onFailure phase.

### Download the sample template

You can download [this sample template](https://gist.github.com/xlcommunity/93b63a414df15798fd2d/raw/5b89898382399c5ed78c36f192f9143ae5b4593d/sample-template.xlr) to try the handler.

![](../images/failure-handler/failure-handler-sample-template-01.png)

When you create a new release from this template, the indicated tasks should fail, upon which the handler will kick in and cause the release to "jump" to the conditional group in the last phase. The skipped tasks will all be assigned to `onFailure_user`.

In a release in which no tasks fail, the conditional group in the onFailure phase means that the tasks are simply ignored.

![](../images/failure-handler/failure-handler-sample-template-03.png)

## Logging for the event listener and endpoint

You can follow what the event listener and endpoint are doing by adding the following loggers to your XL Release logging configuration (note that the `com.xebialabs.platform.script.Logging` logger will capture log statements from other custom endpoints, too).

    ...
    <appender name="SCRIPT" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>log/script.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>log/script.%d{yyyy-MM-dd}.log</FileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%logger{36}] %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="com.xebialabs.platform.script.Logging" level="debug" additivity="false">
        <appender-ref ref="SCRIPT" />
    </logger>

    <logger name="ext.deployit.onfailurehandler.OnReleaseFailureEventListener" level="debug" additivity="false">
        <appender-ref ref="SCRIPT" />
    </logger>
    ...

This is an example of the log output from running the sample template mentioned above:

    2016...22.688 DEBUG [e.d.o.OnReleaseFailureEventListener] Submitting runnable to invoke onFailure handler for release 'A...
    2016...22.688 DEBUG [e.d.o.OnReleaseFailureEventListener] About to execute callback to GET http://localhost:5516/api/ext...
    2016...22.730 DEBUG [e.d.o.OnReleaseFailureEventListener] Release 'Applications/Release1413030' already seen. Doing nothing
    2016...22.836 INFO  [c.xebialabs.platform.script.Logging] Invoking onFailure handler for releaseId Applications/Release1...
    2016...22.937 DEBUG [c.xebialabs.platform.script.Logging] Adding "releaseFailed" variable
    2016...23.023 DEBUG [e.d.o.OnReleaseFailureEventListener] Release 'Applications/Release1413030' already seen. Doing nothing
    2016...23.038 DEBUG [c.xebialabs.platform.script.Logging] Adding placeholder task
    2016...23.313 DEBUG [c.xebialabs.platform.script.Logging] Skipping failed tasks
    2016...23.585 DEBUG [c.xebialabs.platform.script.Logging] Skipping planned tasks
    2016...25.265 DEBUG [c.xebialabs.platform.script.Logging] Skipping placeholder task
    2016...25.640 INFO  [e.d.o.OnReleaseFailureEventListener] Response line from request: HTTP/1.1 200 OK
    2016...25.640 DEBUG [e.d.o.OnReleaseFailureEventListener] Response body: {
      "entity": {
        "message": "Successfully executed onFailure handler for release 'Applications/Release1413030'"
      },
      "stdout": "",
      "stderr": "",
      "exception": null
    }

**Important:** The code provided in the Gist is sample code that is not officially supported by XebiaLabs. If you have questions, please contact the [XebiaLabs support team](https://support.xebialabs.com).
