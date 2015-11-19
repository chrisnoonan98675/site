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

### Configuration

![Jenkins task details](../images/jenkins-task-details.png)

The options for the Jenkins task are:

* **Server**: The Jenkins server that XL Release connects to. You can configure Jenkins servers in **Settings** > **Configuration**.
* **Username** _(optional)_: Username to use when connecting to the Jenkins server. You only need to use this property when you want to override the user that has been configured on the Jenkins Server. 
* **Password** _(optional)_: Password to use when connecting to the Jenkins server. You only need to use this property when you want to  override the password that has been configured on the Jenkins Server. 
* **Job Name**: The name of the job that will be triggered. This job must be configured on the Jenkins server. If the job is located in one or more Jenkins folders, add `job` segment between each folder. For example, for a job that is located at `Applications/web/my portal`, use `Applications/job/web/job/my portal`.
* **Job Parameters**: If the Jenkins job expects parameters, you can provide them, one parameter per line. The names and values of the parameters are separated by the first `=` character.

The output properties of the task are **Build Number** and the **Build Status**. They can be stored in a variable of choice; in the above example they are stored in the `${buildNumber}` and `${buildStatus}` release variables.

### Tweaking the poll interval (XLR 4.8 and higher)

While the task is running, it polls the Jenkins server every five seconds. You can change this behavior by editing the file `conf\deployit-default.properties` in the XL Release installation directory.

Find the following lines

    # Frequency of job progress requests, in seconds
    #jenkins.Server.pollInterval=5
 
 Remove the `#` sign before `jenkins.Server.pollInterval` end edit the value. For example, to set the poll interval to 30 seconds, use

    # Frequency of job progress requests, in seconds
    jenkins.Server.pollInterval=30
	
After saving `conf\deployit-default.properties`, restart the XL Release server for the changes to take effect.
