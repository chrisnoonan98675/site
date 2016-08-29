---
title: Types of tasks in XL Release
---

XL Release includes many types of [tasks](/xl-release/how-to/working-with-tasks.html). Some task types represent actions taken by a person, while others are automated and provide integration with other tools.

**Tip:** In addition to the built-in task types, you can create your own [custom task types](/xl-release/how-to/create-custom-task-types.html).

## Human task types

In human tasks, a person performs an action and indicates when it is done.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Gate](/xl-release/how-to/create-a-gate-task.html) | Contains conditions that must be fulfilled before the release can continue | Red |
| [Manual](/xl-release/how-to/create-a-manual-task.html) | Represents a step in a template or release that must be completed by a person | Gray |
| [User Input](/xl-release/how-to/create-a-user-input-task.html) | Allows users to provide values for release variables | Gray |

## Automated task types

In automated tasks, the XL Release execution engine performs an automated script.

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Create Release](/xl-release/how-to/create-a-create-release-task.html) | Creates and starts a release based on a template | Green |
| [Jenkins](/xl-release/how-to/create-a-jenkins-task.html) | Runs a Jenkins job | Blue |
| [JIRA](/xl-release/how-to/jira-plugin.html) | Query and/or update issues on an Atlassian JIRA server | Blue |
| [Notification](/xl-release/how-to/create-a-notification-task.html) | Automatically sends an email when the task becomes active | Gray |
| [Remote Script](/xl-release/how-to/remote-script-plugin.html) | Executes commands on remote hosts | Blue |
| [Script](/xl-release/how-to/create-a-script-task.html) | Executes a Python script on the XL Release server | Gray |
| [Webhook](/xl-release/how-to/create-a-webhook-task.html) | Sends an HTTP query and parses the response (XML or JSON)| Blue |
| [XL Deploy](/xl-release/how-to/create-an-xl-deploy-task.html) | Tells [XL Deploy](https://docs.xebialabs.com/xl-deploy/) to deploy an application to an environment | Green |

## Container task types

You use container tasks to group other types of tasks. The container task types are:

{:.table .table-striped}
| Task type | Description | Color in the release flow editor |
| --------- | ----------- | -------------------------------- |
| [Parallel Group](/xl-release/how-to/create-a-parallel-group.html) | A container for tasks that are executed simultaneously | None |
| [Sequential Group](/xl-release/how-to/create-a-sequential-group.html) | A container for tasks that are executed in sequence |  None |

## Gate tasks

A Gate task contains conditions that must be fulfilled before the release can continue. There are two types of conditions: simple checkboxes and dependencies on other releases.

![Gate Details](../images/gate-details.png)

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Gate tasks have a red border.

### Checkbox conditions

When you open a Gate task, you will see the checkbox conditions on the task. If you have the [*Edit Task*](/xl-release/how-to/configure-permissions-for-a-release.html) release permission, you can add a checkbox by clicking **Add condition**. To remove a condition, click the cross icon.

Checkboxes must be completed by users who are involved in the release. When a Gate task is active, it can only be completed after all of its conditions are met; that is, after all checkboxes have been ticked. The Gate task will not automatically complete when the conditions are met; the task assignee must mark it as complete.

### Dependencies

The Gate task also shows dependencies on other releases. A Gate task can wait on other releases, on the level of release, phase, or task. In the example above, there is a dependency on the "Deploy version to QA" task in the "QA" phase of the "Back office services 3.2" release.

Click **Add dependency** to create a new dependency or click an existing dependency to edit it.

![Dependency Editor](../images/dependency-editor.png)

Use the dependency editor to select the conditions for the dependency:

* The release the Gate will wait on (only current releases appear)
* The release phase the Gate will wait on (optional)
* The release task the Gate will wait on (optional)

When the Gate contains dependencies and no conditions, it completes _automatically_ when all dependent releases, phases, or tasks are complete.

When a dependent task or release fails, the Gate does not fail. It waits until the release is restarted and the task is completed or skipped. A Gate fails if a release it depends on is aborted.

#### Using variables in dependencies

In XL Release 5.0.0 and later, you can use variables instead of direct release dependencies by clicking ![Switch to variable](/images/button_switch_to_variable.png) and selecting a variable of type *text*. XL Release will search for a release, phase, or task with the ID stored in the variable. If the ID is valid, then the variable dependency will be replaced with a normal dependency and the release will proceed as usual. If the ID is not valid, does not exist, or is empty, then the Gate task will fail.

You can use this feature in combination with the [Create Release task](/xl-release/how-to/create-a-create-release-task.html) to start a release and require the initial release to wait at the Gate until the new release is compete.

## Manual tasks

A Manual task represents a step in a template or release that must be completed by a person. It is the most basic type of task in XL Release.

Like other task types, you can assign Manual tasks to a single user or to a [release team](/xl-release/how-to/configure-teams-for-a-release.html).

![Manual Task Details](../images/manual-task-details.png)

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Manual tasks have a gray border.

## User Input tasks

A User Input task is a manual task that allows users to provide values for release variables. These values can then be used in other tasks in the release.

![User input task details](../images/user-input-details.png)

Users with the **Task Edit** permission on a release can add [variables](/xl-release/how-to/create-release-variables.html) to a task.

To add variables to a User Input task, click **Edit variable list**. You can select the variables that have been defined on the release. You can reorder the list by dragging and dropping the selected variables.

![Add variables to a user input task](../images/user-input-variables.png)

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), User Input tasks have a gray border.

## Create Release tasks

A Create Release task is an automatic task that creates and starts a release based on a configured template.

![Create release task details](../images/create-release-task-details.png)

The options for the Create Release task are:

{:.table .table-striped}
| Option                       | Description                                                                   |
| ---------------------------- | ----------------------------------------------------------------------------- |
| Release title                | The title to use for the newly created release                                |
| Template                     | The template from which to create a release                                   |
| Start release                | If checked release should also be started after it is created by this task    |
| Variables                    | Variables from the template that must be filled in (if applicable)            |
| Created release ID           | Output property bound to the variable that contains ID of the created release |

**Note:** To create a release, the release that contains the Create Release task must be configured with an automated tasks user who has the [*Create Release* permission](/xl-release/how-to/configure-permissions.html) on the template that is specified in the Create Release task. For information about configuring the automated tasks user, refer to [Configure release properties](/xl-release/how-to/configure-release-properties.html).

When the new release is created, XL Release stores its ID in the Create Release task's **Created release ID** output property. If you configure this property to be a variable, then you can use the variable in other tasks. For example, you can use the variable in a [Gate task](/xl-release/how-to/create-a-gate-task.html) so the initial release will wait at the Gate until the new release is complete.

If you do not set **Created release ID** to a variable, then the release will proceed normally, but you will not be able to refer to the created release in other tasks.

The example above shows a task that will create and start a new release based on the "Front office services release template" template. The new release's title will be "Release front office services 2.1", and the value of the `${version}` template variable will be set to "2.1". When the "Release front office services 2.1" release is created, XL Release will store its unique ID in the `${frontOfficeServiceReleaseId}` variable.

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Create Release tasks have a green border.

## Jenkins tasks

The Jenkins task allows you to run a Jenkins job that is triggered when the task becomes active.

The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail.

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Server | The Jenkins server to which XL Release connects. You can configure Jenkins servers in **Settings** > **Configuration**. |
| Username | Optional user name to use when connecting to the Jenkins server. Use this property to override the user that was configured on the Jenkins server. |
| Password | Optional password to use when connecting to the Jenkins server. Use this property to override the password that was configured on the Jenkins server. |
| Job Name | The name of the job that will be triggered. This job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add a `job` segment between each folder. For example, for a job that is located at `Applications/web/my portal`, use `Applications/job/web/job/my portal`. |
| Job Parameters | If the Jenkins job expects parameters, you can provide them, one parameter per line. The names and values of the parameters are separated by the first `=` character. |

The output properties of the task are **Build Number** and the **Build Status**. They can be stored in a variable of choice; in the example above, they are stored in the `${buildNumber}` and `${buildStatus}` release variables.

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Jenkins tasks have a blue border.

### Change the poll interval (XL Release 4.8.0 and later)

While the task is running, it polls the Jenkins server every five seconds. You can change this behavior by editing the `conf\deployit-default.properties` file in the XL Release installation directory.

Locate the following lines:

    # Frequency of job progress requests, in seconds
    #jenkins.Server.pollInterval=5

Remove the `#` sign before `jenkins.Server.pollInterval` and edit the value. For example, to set the poll interval to 30 seconds, use:

    # Frequency of job progress requests, in seconds
    jenkins.Server.pollInterval=30

After saving `conf\deployit-default.properties`, restart the XL Release server for the changes to take effect.

## Jenkins tasks

## Notification tasks

The Notification task type allows you to write emails that are sent automatically when a task becomes active. This is an automated task, so it will complete by itself (or fail if the email could not be sent) and XL Release will advance to the subsequent task.

![Notification Task Details](../images/notification-task-details.png)

The options for the notification task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| To | A list of email addresses where the message will be sent |
| Subject | The subject of the message |
| Body | The message body, in plain text |

Click a field to edit it. You can use [variables](/xl-release/concept/variables-in-xl-release.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Notification tasks have a gray border.

## Script tasks

A Script task contains a Python script that is executed on the XL Release server. This is an automated task that completes automatically when the script finishes successfully.

![Script Task Details](../images/script-task-details.png)

Type or paste a Python script in the **Script** field of the Script task details. XL Release 4.7.0 and later supports Jython 2.7. Jython is the Java implementation of Python. This means that you have access to standard Python as well as the Java libraries included in Java 7.

You can access and modify [release variables](/xl-release/concept/variables-in-xl-release.html) in your scripts using the dictionary named `releaseVariables`. This sample script shows how to access and modify a variable:

{% highlight python %}
print(releaseVariables['xldeployPackage'])
releaseVariables['xldeployPackage'] = 'XL Release'
{% endhighlight %}

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Script tasks have a gray border.

### Security and Script tasks

When a Script task becomes active, the script is executed in a sandbox environment on the XL Release server. This means that the script has very restricted permissions. By default, access to the file system and network are not allowed.

To remove these restrictions, add a `script.policy` file to the `XL_RELEASE_HOME/conf` directory. This is a standard [Java Security Policy file](http://docs.oracle.com/javase/7/docs/technotes/guides/security/PolicyFiles.html) that contains the permissions that a script should have. You must restart the XL Release server after creating or changing the `XL_RELEASE_HOME/conf/script.policy` file.

### Sample scripts

This sample script shows how to download a file from a web site and save it locally:

{% highlight python %}
import httplib
url = 'www.xebialabs.com'
file = '/tmp/xebialabs.html'
xl = httplib.HTTPConnection(url)
xl.request('GET', '/')
response = xl.getresponse()
myFile = open(file, 'w')
myFile.write(response.read())
myFile.close()
print "Save %s to %s" % (url, file)
{% endhighlight %}

This example allows access to the network using Python `httplib` and read/write access to the `/tmp` directory on the XL Release server:

{% highlight python %}
grant {
    // Network access
    permission  java.lang.RuntimePermission "accessClassInPackage.sun.nio.ch";
    permission  java.net.SocketPermission "*", "connect, resolve";

    // File access
    permission java.io.FilePermission "/tmp/*", "read, write";
    permission java.util.PropertyPermission "line.separator", "read";
};
{% endhighlight %}

For an extended example, refer to [Creating XL Release Tasks Dynamically Using Jython API](http://blog.xebialabs.com/2015/08/11/creating-xl-release-tasks-dynamically-using-jython-api/).

## Webhook tasks

Automatic tasks often need to interact with an external system through a REST interface. You could use a script task to send an HTTP query and parse the response; but to make this easier, XL Release provides the Webhook task type.

To configure a Webhook, you specify the URL to call and the details of the request (HTTP method, request body, authentication). The task will perform the query, parse the response, and optionally extract results and them it in release variables.

Webhooks are available in XML and JSON, depending on the format of the HTTP response. Each has its own way of extracting a result from the response:

* **XML webhook**: Provide an [XPath](https://en.wikipedia.org/wiki/XPath) expression to select the value.
* **JSON webhooks**: Use a [JSON path](http://goessner.net/articles/JsonPath/).

This example is based on [HttpBin](http://httpbin.org/), a free service to test REST clients. It uses the `/ip` endpoint, which returns the IP of the caller in a JSON response with the following structure:

    {
      "origin": "xxx.xxx.xxx.xxx"
    }

This is the configuration of the task in XL release:

![Webhook details](../images/webhook-details.png)

After the task is complete, the `origin` field is extracted from the response and stored in the `${xlreleaseIP}` release variable, where it can be used by other tasks.

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Webhook tasks have a blue border.

## XL Deploy tasks

The XL Deploy task provides integration with [XL Deploy](/xl-deploy), XebiaLabs' Application Release Automation solution. It is an automated task that tells XL Deploy to deploy a certain application to an environment. Both the application and environment must be configured in XL Deploy. The task gives live updates of the deployment process and completes automatically when the deployment succeeds.

**Note:** If the deployment fails, it is automatically rolled back.

![XL Deploy Task Details](../images/deployit-task-details.png)

The options for the XL Deploy task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Server | XL Deploy server to which XL Release connects. You can configure XL Deploy servers in **Settings** > **[XL Deploy Servers](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html)**. |
| Deployment package | Version of the application to be deployed. Start typing and XL Release will retrieve the list of applications from the XL Deploy server. |
| Environment | Environment to which to deploy. Start typing and XL Release will retrieve the list of environments from the XL Deploy server. |
| Username | User name to use when connecting to the XL Deploy server. |
| Password | Password to use when connecting to the XL Deploy server. |

You can also use [variables](/xl-release/concept/variables-in-xl-release.html) in the **Deployment package** and **Environment** fields. This allows you to reuse application version and environment across tasks in XL Release. For example, when using variables, you can mention the name of the application and the environment to which you deployed in a [Notification task](/xl-release/how-to/create-a-notification-task.html).

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), XL Deploy tasks have a green border.

## Group tasks

### Parallel Groups

A Parallel Group is a container for tasks that are executed simultaneously.

**Tip:** To group tasks that should be executed in sequence, use the [Sequential Group](/xl-release/how-to/create-a-sequential-group.html) task type (available in XL Release 5.0.0 and later).

To add a Parallel Group to a template or release:

1. Select [**Release Flow Editor**](/xl-release/how-to/using-the-release-flow-editor.html) from the **Show** menu.
1. [Add a task to a phase](/xl-release/how-to/add-a-task-to-a-phase.html), selecting the Parallel Group type.
1. Add tasks to the group by clicking **Add task** in the group or by dragging existing tasks into the group.

![Parallel Group](../images/parallel-group.png)

In this example, the XL Deploy tasks and the "Divide test cases" task will start simultaneously. The "Deploy to ACC" group will not finish until all of its subtasks are complete. Then, XL Release will continue with the next task, "Notify QA installation".

#### Adjust Parallel Groups in the planner

By default, all tasks in a Parallel Group start when the group starts. The [planner](/xl-release/how-to/using-the-xl-release-planner.html) allows you to do planning on a detailed level and explicitly express dependencies between tasks.

This is an example of a Parallel Group with three tasks. Task 1 already has a due date.

![Parallel Group: Task 1](../images/planner-parallel-dependency-1.png)

Connect Task 1 to Task 2 by dragging an arrow from the right edge of Task 1 to the left edge of Task 2.

![Parallel Group: Connect to Task 2](../images/planner-parallel-dependency-2.png)

As a result, Task 2 will start at the due date of Task 1.

![Parallel Group: Task 1 and 2 connected](../images/planner-parallel-dependency-3.png)

Task 3 is not connected and because it is inside a Parallel Group, it will start at the same time as Task 1 (when the Parallel Group starts).

### Sequential Groups

A Sequential Group is a container for tasks that are executed in sequence. A Sequential Group works in the same way as a series of tasks in a phase; however, it provides a useful way to group related tasks within a phase. For example, you could configure a precondition on the group so that all of its subtasks will be skipped under certain conditions. You can also collapse Sequential Groups to make the release flow easier to read.

**Tip:** To group tasks that should be executed simultaneously, use the [Parallel Group](/xl-release/how-to/create-a-parallel-group.html) task type.

To add a Sequential Group to a template or release:

1. Select [**Release Flow Editor**](/xl-release/how-to/using-the-release-flow-editor.html) from the **Show** menu.
1. [Add a task to a phase](/xl-release/how-to/add-a-task-to-a-phase.html), selecting the Sequential Group type.
1. Add tasks to the group by clicking **Add task** in the group or by dragging existing tasks into the group. Drag tasks within the group to adjust their execution order.

![Sequential group](../images/sequential-group.png)

In this example, the "Prepare release notes" task has already started; when it finishes, then the "QA prerequisites" task will start. The "Manual checks" group will finish with its last subtask, "Check JIRA version".

## Custom task types

You can also define your own [custom task types](custom-task-types.html).

## Change a task's type

You can change the [type of a task](/xl-release/concept/types-of-tasks-in-xl-release.html) in:

* A template
* A planned release that has not started yet
* An active release, if the task that you want to change has not started yet

To change the type of a task in a template, you need the **Edit Template** permission on the template. To change the type of a task in a release, you need the **Edit Task** permission on the release.

To change a task's type:

1. Open the template or release in the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html).
1. Click ![Task action menu](/images/menu_three_dots.png) on the task you want to change.
1. In the menu, hover over **Change type**.
1. Select the desired task type.

    XL Release will copy the values of properties that are shared between the task's previous type and the type that you selected.

Note that:

* You cannot change the type of an active, completed, failed, or skipped task.
* You can change a task to a Parallel or Sequential Group, but you cannot change a Parallel or Sequential Group to a different type of task.
