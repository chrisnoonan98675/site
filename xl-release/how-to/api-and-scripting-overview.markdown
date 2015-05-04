---
title: XL Release API and scripting overview
categories:
- xl-release
subject:
- XL Release API
tags:
- api
- script
since:
- 4.5.0
---

There are various ways to automate functionality in XL Release.

* REST API
* WebHook
* Remote shell scripting 
* Script tasks in releases
* Reusable plugin tasks

## REST API

The principal way to control XL Release from the outside is through the REST API.

The public REST API of XL Release exposes its core functionality over HTTP, allowing shell scripts or third party systems to interact with XL Release. Data is interchanged in the JSON format.

With the API, you can start releases, complete tasks, add comments to tasks and much more.

To get started, take a look on how to [create a new release using the REST API](/xl-release/how-to/create-a-new-release-via-rest-api-using-curl.html).

Browse the [XL Release REST API documentation](/xl-release/latest/rest-api/) to see what functionality can be used.

## Automation within XL Release

From within the XL Release application, there are various ways to interact with third-party systems and automate tasks through scripting.

## Webhook

The easiest way to interact with a third party system is to use the **Webhook task** in XL Release.

This task allows you to connect to any system that has an HTTP endpoint, for example SOAP or REST API. 

You can send a request and parse the result into an XL Release variable for use in subsequent tasks. No scripting is needed.

See the documentation for the [WebHook task](/xl-release/how-to/create-a-webhook-task.html) for more information.  

## Remote shell scripting

The **Remote Script** task in XL Release allows you to execute a shell script on a remote host. Both Unix and Windows are supported.

See [Remote Script Plugin Manual](/xl-release/concept/introduction-to-the-xl-release-remote-script-plugin.html) for more information.

## Script tasks

**Script tasks** are a task type in XL Release that contain, as the name implies, a script that is executed when the task becomes active. 

XL Release users with sufficient access privileges can write the script on the template or release level.

The scripting language used is [Jython](http://www.jython.org/), which is a [Python](https://www.python.org/) dialect that runs on the Java VM. It is compatible with Python 2.5. Since it runs on a Java VM, the complete API of Java 7 can also be used.

Script tasks have access to the XL Release through the [XL Release Python API](/jython-docs/#!/xl-release/4.6.x/). This API is equivalent with the REST API.

See the [Script Task](/xl-release/how-to/create-a-script-task.html) documentation for more information.

## Plugin tasks

You can create custom tasks types using the task plugin mechanism. These tasks will show up in the "Add Task" menu in XL Release. This is convenient for reusable bits of functionality, like "Create JIRA ticket" or "Start Jenkins Job". 

In fact, the JIRA and Jenkins tasks and most other standard tasks in XL Release are plugin tasks.

As Script tasks, Plugin tasks are written in Jython and have access to the XL Release API. Plugins are installed on the XL Release server.
