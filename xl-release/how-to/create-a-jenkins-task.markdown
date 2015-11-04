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

The Jenkins task allows you to configure Jenkins jobs that are triggered when a task becomes active.

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

* **Server**: The Jenkins server that XL Release connects to. You can configure Jenkins servers in **Settings** > **Configuration**.
* **Username**: Username to use when connecting to the Jenkins server.
* **Password**: Password to use when connecting to the Jenkins server.
* **Job Name**: The name of the job that will be triggered; this job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add `job` segment between each folder. For example, for a job that is located at `Applications/web/my portal`, use `Applications/job/web/job/my portal`.
* **Job Parameters**: If the Jenkins job expects parameters, you can provide them, one parameter per line.

The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail. The build number and build status are stored in the `${buildNumber}` and `${buildStatus}` release variables.
