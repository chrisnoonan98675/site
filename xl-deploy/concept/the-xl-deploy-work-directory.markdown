---
title: The XL Deploy work directory
categories:
- xl-deploy
subject:
- System administration
tags:
- work directory
- task
- system administration
- maintenance
- cleanup
---

The `<XLDEPLOY_HOME>/work` directory is a special directory that XL Deploy uses to temporarily store data that cannot be kept in memory. For example, if XL Deploy needs to process a binary artifact that is several gigabytes in size, the file would simply not fit in memory.

Examples of items that are temporarily stored in the work directory are:

* All files required for deployment when a deployment task runs
* Files that are being uploaded when configuration items (CIs) are created
* Deployment packages that are being imported or exported

## Location of the work directory

The `work` directory is located in the XL Deploy server installation directory (`<XLDEPLOY_HOME>`). XL Deploy uses this directory instead of an operating system-specific temporary directory because:

* Read access to the work directory must be limited because it may contain sensitive information
* Operating system-specific temporary directories are typically not large enough to contain all of the files that XL Deploy needs (for more information about disk space, refer to [Requirements for installing XL Deploy](/xl-deploy/concept/requirements-for-installing-xl-deploy.html#determining-hard-disk-space-requirements))

## Why is my work directory growing?

The work directory can grow for several reasons:

* There are many unarchived tasks. After a deployment finishes, you should archive the deployment task so XL Deploy can remove the task from the work directory. To archive a deployment task after is complete, click **Close** on the deployment screen.

    **Tip:** To check for unarchived tasks (including those owned by other users), log in to XL Deploy as an administrator, open the Task Monitor, and select **All Tasks**.

* The active tasks work with large artifacts. When deploying a large artifact, multiple copies of the artifact may be stored in the work directory.

* Large artifacts are being created, imported, or exported. This can also cause a temporary increase in the size of the work directory.

To prevent the work directory from growing, it is recommended that you always archive completed deployment tasks and avoid leaving incomplete tasks open.

## How can I clean up the work directory?

When the XL Deploy server is running, files in the work directory may be in use. In addition, if a task is not finished before you stop the XL Deploy server, XL Deploy will recover the task when the server is restarted. After recovery, the task needs access to the files that it previously created in the work directory.

Therefore, before cleaning up the work directory, verify that all running tasks are finished and archived. To do so, log in to XL Deploy as an administrator, open the Task Monitor, and select **All Tasks**.

After you have verified that there are no running tasks, you can [shut down the XL Deploy server](/xl-deploy/how-to/shut-down-xl-deploy.html) and safely delete the files in the work directory.

## How can I change the location of the work directory?

In XL Deploy 5.0.0 and later, you cannot change the location of the work directory. However, you can change the location where XL Deploy stores `.task` files, which are normally stored in the work directory. To do so, change the `recovery-dir` setting in `<XLDEPLOY_HOME>/conf/system.conf`. After saving the file, restart the XL Deploy server.

In XL Deploy 4.5.x and earlier, you can change the work directory. To do so, create a file in the `conf` directory called `hotfix-context.xml` and add the following content, replacing `/tmp/work` with the desired work directory location:

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

After saving the file, restart the XL Deploy server.
