---
title: Cleaning up the work directory
categories:
- xl-deploy
subject:
- Administration
tags:
- work directory
- work
- system administration
---

This topic describes what the work directory is and how to clean it up.

## What for is the work directory used?
The work directory is a special directory used by XL Deploy to temporarily store data. XL Deploy has to process large amounts of data that we do not want to keep in memory. For example if XL Deploy needs to process a large binary artifact of a couple of gigabytes, it woulds simply not fit in memory. Examples of things that are stored in the work directory are:

* When a deployment task runs, all files required for the deployment are temporarily stored here. Most common reason for this is placeholder replacement.
* Temporary storage of files that are being uploaded when creating CI's.
* Temporary storage of deployment packages that are being imported or exported
* Plus many other things.

## Why does the work directory grow so big?
When the work directory grows big, it does not always directly mean there is a bug. There can be various reason for the work directory to grow:

* **There are many unarchived tasks.** What is often a reason for a big work directory is that users do not archive their tasks properly. When the deployment is finished, the task needs to be archived for the work directory to be deleted. Archiving a task can be done via the GUI by closing it. Make sure to use the task manager in the GUI to have an overview of all unarchived tasks in the system. Make sure to log in as an administrator and list all tasks and not just the tasks of the current user.
* **The active tasks deal with big artifacts.** When deploying a big artifact, multiple copies of this file can be stored in the work directory.
* **Big artifacts are being created, imported or exported.** This can also be a reason for a temporary increase of the size of the work directory

So keep this in mind before concluding there is a problem. If you think the work directory is bigger than it should be, please use the instructions below to clean it up.

## How can I clean up the work directory?

The files in the work directory are most likely in use when XL Deploy server is running. Before cleaning up this folder you should always shutdown the XL Deploy server. Most files in the work directory can be deleted without breaking anything, but there is one big exception.

If any kind of task (like an deployment task) is not finished when stopping the server, the task will be recovered when the server starts up again. After recovering the task needs access to its previously created files in the work directory. So before stopping the server and cleaning up the work directory, always make sure no tasks are running anymore, and all tasks are properly archived (meaning closed).

## How can I configure where XL Deploy stores the work directory?

By default XL Deploy will use the `work` directory in the server installation directory instead of the OS Specific temp directory for two reasons:

* Security. Read access to this folder must be limited because it can contain sensitive information
* The OS specific temp folders are typically too small to fit all files needed by XL Deploy. Hence the disk space requirements in the administration manual.

If you want to store the files somewhere else for any reason, it is possible to change the work directory. To do this, create a file called `hotfix-context.xml` in the `conf` folder containing the following configuration:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:context="http://www.springframework.org/schema/context" xmlns:security="http://www.springframework.org/schema/security"
      xmlns:util="http://www.springframework.org/schema/util"
      xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

      <bean id="baseWorkDir" class="java.lang.String">
        <constructor-arg value="/tmp/work" />
      </bean>

    </beans>

If you now restart your XL Deploy server, it will use the folder `/tmp/work` as the work directory.
