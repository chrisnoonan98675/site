---
title: XL Release API and scripting overview
categories:
- xl-release
subject:
- API
tags:
- api
- script
since:
- XL Release 4.5.0
---

There are various ways to automate functionality in XL Release.

## REST API

The principal way to control XL Release from the outside is through the REST API. This exposes XL Release's core functionality over HTTP, allowing shell scripts or third-party systems to interact with XL Release. Data is interchanged in the JSON format. With the API, you can start releases, complete tasks, add comments to tasks, and much more.

To get started, refer to [Create a new release via the XL Release REST API using cURL ](/xl-release/how-to/create-a-new-release-via-rest-api-using-curl.html). You can also browse the [XL Release REST API documentation](/xl-release/latest/rest-api/) to see what functionality can be used.

## Automation within XL Release

There are several ways to interact with third-party systems and automate tasks through scripting in the XL Release user interface.

### Webhook

The easiest way to interact with a third-party system is to use the [Webhook task type](/xl-release/how-to/create-a-webhook-task.html). It allows you to connect to any system that has an HTTP endpoint; for example, SOAP or REST API.

You can send a request and parse the result into an XL Release variable for use in subsequent tasks. No scripting is needed.

### Remote shell scripting

The [Remote Script task type](/xl-release/concept/introduction-to-the-xl-release-remote-script-plugin.html) allows you to execute a shell script on a remote host. Both Unix and Windows are supported.

### Script tasks

The [Script task type](/xl-release/how-to/create-a-script-task.html) allows you to provide a script that is executed when the task becomes active. XL Release users with sufficient permission can write a script on the template or release level.

The scripting language used is [Jython](http://www.jython.org/), which is a [Python](https://www.python.org/) dialect that runs on the Java VM. XL Release 4.6.x and earlier uses Jython 2.5, while XL Release 4.7.0 and later uses Jython 2.7. Because it runs on a Java VM, the complete API of Java 7 can also be used.

Script tasks have access to the XL Release through the [XL Release Jython API](/jython-docs/#!/xl-release/4.8.x/). This API is equivalent to the REST API.

### Plugin tasks

You can use the task plugin mechanism to create [custom task types](/xl-release/how-to/create-custom-task-types-in-xl-release.html). These tasks will appear in the **Add task** menu in the XL Release user interface. This is convenient for reusable bits of functionality, such as "Create JIRA ticket" or "Start Jenkins Job".

In fact, the JIRA and Jenkins task types, as well as most other standard tasks in XL Release, are plugin tasks.

Like Script tasks, plugin tasks are written in Jython and have access to the XL Release API. Plugins are installed on the XL Release server.
