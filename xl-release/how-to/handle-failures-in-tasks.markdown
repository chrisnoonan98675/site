---
title: Automatically handle failures in XL Release tasks
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

These options provide general support for both fully and partially automated release scenarios. However, in some scenarios, you may want to execute certain actions if a task fails; for example, cleaning up resources or notifying a certain team. This topic will discuss two approaches:

* [Handling failure in the task](#handling-failure-in-the-task)
* [Handing failure in a conditional block, based on the task status/output](#handling-failure-in-a-conditional-block)

The examples shown here are based on a [sample template](https://gist.github.com/xlcommunity/a289a5a766fde13c1614/raw/c407e554521f32d91c8f879cd70bbfb080bed409/handling-task-failure-examples.xlr) that you can download and import. Note that this requires adding a [custom task type](/xl-release/how-to/create-custom-task-types.html), which you can do by [copying the type definition to the `ext` directory](/xl-release/how-to/create-custom-task-types.html#defining-a-custom-task) or by downloading and installing [this community plugin](https://gist.github.com/xlcommunity/a289a5a766fde13c1614/raw/c407e554521f32d91c8f879cd70bbfb080bed409/xlr-acme-webhook-2016-02-20.jar).

**Tip:** An alternative approach is to implement a custom failure handling phase; refer to [Implement a custom release failure handler](/xl-release/how-to/implement-a-custom-failure-handler.html) for more information.

## Handling failure in the task

The simplest and most effective way to handle failure in a task is to include the appropriate error-handling logic in the task implementation itself. For example:

![Sample Script task with cleanup](../images/automatically-handle-failures/handle-failures-script-task-clean-up.png)

This approach allows you to retry the task multiple times without accumulating a backlog of items that need to be cleaned up later. For example, if a task tries to create a user and then create a ticket, you could add a failure handler around the "create ticket" part so you can delete the user if the ticket was not created successfully. That is, instead of this logic:

    newUser = createUser()
    ticket = createTicket(newUser)

You can use this logic:

    newUser = createUser()
    try:
      ticket = createTicket(newUser)
    except:
      deleteUser(newUser)
      sys.exit(1)

You can use this approach with [Remote Script tasks](/xl-release/how-to/remote-script-plugin.html), [Script tasks](/xl-release/how-to/create-a-script-task.html), and [custom tasks](/xl-release/how-to/create-custom-task-types.html). For example, here is a Remote Script task:

![Sample Remote Script task with cleanup](../images/automatically-handle-failures/handle-failures-remote-task-clean-up.png)

## Handling failure in a conditional block

In cases where the task implementer does not necessarily know which actions need to be taken when a task fails, you can add a group immediately following the task with a condition that ensures that it only executes if the task *does not* succeed. For example:

![Group that handles failure](../images/automatically-handle-failures/handle-failures-conditional-block.png)

In this case, the task must:

* Complete its executing without triggering a task failure. That is, a Remote Script task must exit with a non-zero exit code, and a Script task must not throw an exception.

* Set the value of a variable to an appropriate value for the conditional group to determine whether it should execute. In some cases, this may happen by default; for example, there might be a standard output or error. But for HTTP requests, for example, you may want to expose the response code for this purpose.

### Sample Script task

This is an example of a Script task that succeeds and [sets a variable](/xl-release/how-to/create-a-script-task.html) with the response code of the HTTP request it executes.

![Sample Script task that sets a variable](../images/automatically-handle-failures/handle-failures-script-task-set-variable.png)

The associated group then checks the variable in its [precondition](/xl-release/how-to/set-a-precondition-on-a-task.html).

![Group with a precondition](../images/automatically-handle-failures/handle-failures-script-task-set-variable-precondition.png)

If you want the release to fail after the cleanup operation is done, add a task that fails to the end of the conditional group.

![Task that will cause the release to fail](../images/automatically-handle-failures/handle-failures-script-task-set-variable-add-precondition.png)

### Sample Remote Script Task

For Remote Script tasks, you can usually determine whether a task succeeded by looking at the standard output or standard error, which are available as variables.

For remote tasks, it's often possible to determine whether a task did not succeed by looking at the standard out or standard error, which are already available as variables. Note that the remote script is configured to always return exit code 0, so the XL Release task will not fail:

![](../images/automatically-handle-failures/handle-failures-remote-task-no-fail-01.png)

![](../images/automatically-handle-failures/handle-failures-remote-task-no-fail-02.png)

Note that in this example, the remote script is configured to always return exit code 0, so the XL Release task will not fail.

### Sample custom Webhook task

This approach can be used with other task types, with some customizations. For example, this `sythetic.xml` code extends the [Webhook task type](/xl-release/how-to/create-a-webhook-task.html) with a type that always succeeds and provides the HTTP response code as an output variable that can be used in subsequent tasks.

{% highlight xml %}
<type type="acme.JsonWebhook" extends="webhook.JsonWebhook">
    ...
    <property name="statusCode" kind="integer" category="output" required="false"
      description="The HTTP status code of the response" />
    <property name="alwaysSucceed" kind="boolean" category="input"
      required="false" default="false" description="If checked, this task will
        succeed irrespective of the HTTP response code. The 'statusCode' output
        property can be checked by subsequent tasks to determine whether the call
        was actually successful." />
</type>
{% endhighlight %}

This also requires minor changes to the implementation:

![Updated implementation](../images/automatically-handle-failures/handle-failures-remote-task-update.png)

Then, you can use the modified Webhook task with a group that checks the status code to determine whether the task executed successfully.

![](../images/automatically-handle-failures/handle-failures-webhook-task-no-fail-01.png)

![](../images/automatically-handle-failures/handle-failures-webhook-task-no-fail-02.png)

The [type definition](https://gist.github.com/xlcommunity/a289a5a766fde13c1614#file-synthetic-snippet-xml) and [task implementation](https://gist.github.com/xlcommunity/a289a5a766fde13c1614#file-jsonwebhook-py) are available as part of the [Gist](https://gist.github.com/xlcommunity/a289a5a766fde13c1614) mentioned above.

**Important:** The code provided in the [Gist](https://gist.github.com/xlcommunity/a289a5a766fde13c1614) is sample code that is not officially supported by XebiaLabs. If you have questions, please contact the [XebiaLabs support team](https://support.xebialabs.com).
