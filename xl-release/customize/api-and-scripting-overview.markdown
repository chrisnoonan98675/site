---
title: XL Release API and scripting overview
since: XL Release 4.5.0
---

There are various ways to automate functionality in XL Release.

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

## Automation through the REST API

The principal way to control XL Release from the outside is through the REST API. This exposes XL Release's core functionality over HTTP, allowing shell scripts or third-party systems to interact with XL Release. Data is interchanged in the JSON format. With the API, you can start releases, complete tasks, add comments to tasks, and much more.

To get started, refer to [Create a new release via the XL Release REST API using cURL ](/xl-release/how-to/create-a-new-release-via-rest-api-using-curl.html). You can also browse the [XL Release REST API documentation](/xl-release/latest/rest-api/) to see what functionality can be used.

### How to find template and release IDs

To use the XL Release REST API, you need to know the unique identifier of templates, releases, phases and tasks. This topic explains where you can find them.

Both releases and templates have an ID of the form `Applications/ReleaseXXXXXXX`, where `XXXXXXX` is a seven-digit number. The fastest way to find it is to open it in the browser and look at the URL in the location bar.

![URL for template](/xl-release/images/template-release-id.png)

Just take the last part of the URL and put `Applications` in front of it. In this case, the identifier of the template is `Applications/Release29994650`.

**Note:** All IDs start with `Applications/` for technical reasons (XL Release uses the same database as [XL Deploy](/xl-deploy), which limits the ID prefixes that can be used).

Now that you have the ID, you can use it in a REST call. For example, to get the contents of a release, do:

    GET http://localhost:5516/api/v1/releases/Applications/Release9324610

#### Phases and tasks from the release response

The IDs of phases and tasks can be found in the response of the REST call that gets the contents of a release or template.

Inspect the contents of the release JSON object. The release ID (which we already know) is in the `id` field. All phases in the release are stored as a list in the `phases` property. In turn, the phases contain their tasks in the `tasks` property.

Here's an example:

    {
        "id": "Applications/Release9324610",
        "type": "xlrelease.Release",
        "title": "Configure XL Release",
        ...
        "phases": [
            {
                "id": "Applications/Release9324610/Phase2437552",
                "type": "xlrelease.Phase",
                "title": "Setup mail server",
                ...
                "tasks": [
                    {
                        "id": "Applications/Release9324610/Phase2437552/Task3066132",
                        "type": "xlrelease.Task",
                        "title": ""Configure email address and mail server",

As you can see, IDs are hierarchical. Note that the ID of the task in this example is `Applications/Release9324610/Phase2437552/Task3066132`, not just `Task3066132`.

This is an example of using the task ID in a REST call:

    GET http://localhost:5516/api/v1/tasks/Applications/Release9324610/Phase6441318/Task2674539

#### Getting IDs by title

It is also possible to get a release, phase, or task by querying it by its title. Note that titles are not unique in XL Release. Releases may have duplicate names, as well as tasks in the same phase. So "by title" queries will always return a list of possibilities.

This is an example that finds all tasks that have the title "Configure email address and mail server" in the release with ID `Applications/Release9324610`:

    http://localhost:5516/api/v1/tasks/byTitle?taskTitle=Configure%20email%20address%20and%20mail%20server&releaseId=Applications/Release9324610

For more information about these calls, refer to the REST API:

* [Releases by title](/xl-release/4.8.x/rest-api/#!/releases/searchReleasesByTitle)
* [Phases by title](/xl-release/4.8.x/rest-api/#!/phases/searchPhasesByTitle)
* [Tasks by title](/xl-release/4.8.x/rest-api/#!/tasks/searchTasksByTitle)

## Jython helper functions

In addition to the [Jython API](/jython-docs/#!/xl-release/5.0.x/), the following helper functions are available in [Script tasks](/xl-release/how-to/create-a-script-task.html) and Python scripts for plugin tasks.

{:.table .table-striped}
| Function | Description | Parameters | Returns |
| -------- | ----------- | ---------- | ------- |
| `getCurrentTask()` | Returns the current task | None | A Task object |
| `getCurrentPhase()` | Returns the current phase | None | A Phase object |
| `getCurrentRelease()` | Returns the current release | None | A Release object |
| `getTasksByTitle(taskTitle, phaseTitle = None, releaseId = None)` | Finds tasks by title | &#149; `taskTitle`: The task title to search<br />&#149; `phaseTitle`: The phase title to search tasks on (optional; will search tasks in the whole release if not provided)<br />&#149; `releaseId`: The release ID to search tasks on (optional; will search tasks in the current release if not provided) | An array of Task objects |
| `getPhasesByTitle(phaseTitle, releaseId = None)` | Finds phases by title | &#149; `phaseTitle`: The phase title to search<br />&#149; `releaseId`: The release ID to search phases on (optional; will search phases in the current release if not provided) | An array of Phase objects |
| `getReleasesByTitle(releaseTitle)` | Finds releases by title | `releaseTitle`: The release title to search | An array of Release objects |
