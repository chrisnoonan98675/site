---
title: Create an XL Deploy task
categories:
- xl-release
subject:
- Task
tags:
- task
- xl deploy
---

The XL Deploy task provides integration with [XL Deploy](/xl-deploy), XebiaLabs' Application Release Automation solution.

It is an automated task that tells XL Deploy to deploy a certain application to an environment. Both application and environment must be configured in XL Deploy. The task gives live updates of the deployment process and completes automatically when the deployment finishes.

![XL Deploy Task Details](../images/deployit-task-details.png)

The options for the XL Deploy task are:

* **Server**: XL Deploy Server that XL Release connects to. You can configure XL Deploy servers in the **Settings** section under **[XL Deploy Servers](/xl-release/how-to/configure-xl-deploy-servers-in-xl-release.html)**.
* **Deployment package**: Version of the application to be deployed. Auto-complete is supported in this field. Start typing and XL Release will retrieve the list of applications that are present in the XL Deploy server.
* **Environment**: Environment to deploy to. Auto-complete is also supported in this field.
* **Username**: Username to use when connecting to the XL Deploy server.
* **Password**: Password to use when connecting to the XL Deploy server.

You can also use [variables](/xl-release/concept/variables-in-xl-release.html) in the **Deployment package** and **Environment** fields. This allows you to reuse application version and environment across tasks in XL Release. For example, when using variables you can mention the name of the application and the environment you deploy to in a notification task.
