---
title: Create a Jenkins task
categories:
- xl-release
subject:
- Task types
tags:
- task
- jenkins
---

The Jenkins task allows you to run a Jenkins job that is triggered when the task becomes active.

The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail.

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| Server | The Jenkins server to which XL Release connects. You can configure Jenkins servers in **Settings** > **Task configurations** (**Settings** > **Configuration** prior to XL Deploy 6.0.0). |
| Username | Optional user name to use when connecting to the Jenkins server. Use this property to override the user that was configured on the Jenkins server. |
| Password | Optional password to use when connecting to the Jenkins server. Use this property to override the password that was configured on the Jenkins server. |
| Job Name | The name of the job that will be triggered. This job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add a `job` segment between each folder. For example, for a job that is located at `Applications/web/my portal`, use `Applications/job/web/job/my portal`. |
| Job Parameters | If the Jenkins job expects parameters, you can provide them, one parameter per line. The names and values of the parameters are separated by the first `=` character. |

The output properties of the task are **Build Number** and the **Build Status**. They can be stored in a variable of choice; in the example above, they are stored in the `${buildNumber}` and `${buildStatus}` release variables.

In the [release flow editor](/xl-release/how-to/using-the-release-flow-editor.html), Jenkins tasks have a blue border.

## Change the poll interval (XL Release 4.8.0 and later)

While the task is running, it polls the Jenkins server every five seconds. You can change this behavior by editing the `conf\deployit-default.properties` file in the XL Release installation directory.

Locate the following lines:

    # Frequency of job progress requests, in seconds
    #jenkins.Server.pollInterval=5

Remove the `#` sign before `jenkins.Server.pollInterval` and edit the value. For example, to set the poll interval to 30 seconds, use:

    # Frequency of job progress requests, in seconds
    jenkins.Server.pollInterval=30

After saving `conf\deployit-default.properties`, restart the XL Release server for the changes to take effect.
