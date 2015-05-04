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

The Jenkins task lets you configure Jenkins jobs that are triggered when a task becomes active.

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

* **Server**: The Jenkins Server that XL Release connects to. You can configure Jenkins servers in the **Settings** section under **Configuration**.
* **Username**: Username to use when connecting to the Jenkins server.
* **Password**: Password to use when connecting to the Jenkins server.
* **Job Name**: The name of the job that will be triggered. It must be configured on the Jenkins server. If the job is located inside one or more Jenkins folders, you need to add 'job' segment between each folder. E.g. for a job which is located at `Applications/web/my portal` you need to fill in: `Applications/job/web/job/my portal`.
* **Job Parameters**: If the Jenkins job expects parameters, you can provide them one by line.

The task will complete when the job completes successfully on the Jenkins server; otherwise, the task will fail. The build number and build status are stored in the ${buildNumber} and ${buildStatus} release variable.
