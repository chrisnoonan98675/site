---
title: How to find identifiers for use in the REST API
categories:
xl-release
subject:
Public API
tags:
api
script
release
template
phase
task
weight: 512
---

To use the XL Release REST API, you need to know the unique identifier of templates, releases, phases and tasks. This topic explains where you can find them.

## Releases and templates

Both releases and templates have an ID of the form `Applications/ReleaseXXXXXXX`, where `XXXXXXX` is a seven-digit number (all IDs start with `Applications/` for technical reasons). If you are using XL Release 6.0.0 or later and you have organized templates and releases in [folders](/xl-release/how-to/manage-templates-and-releases-using-folders.html), their IDs will be of the form `Applications/FolderXXXXXXXXX-FolderXXXXXXXXX-ReleaseXXXXXXX`.

The fastest way to find the ID of a template or release is to open it in a browser and look at the URL in the location bar. Take the last part of the URL and prefix it with `Applications`. If the template or release is in a folder, you must also replace the hyphens (`-`) with forward slashes (`/`).

In these examples, the IDs are `Applications/Release2994650` and `Applications/Folder608123241/Folder283360226/Release482440157`.

![URL for template](../images/template-release-id.png)

![URL with folders](../images/template-folders-release-id.png)

### Using the ID in a REST call

After you have the ID, you can use it in a REST call. For example, to get the contents of a release, do:

    GET http://localhost:5516/api/v1/releases/Applications/Release9324610
    GET http://localhost:5516/api/v1/releases/Applications/Folder608123241/Folder283360226/Release482440157

## Phases and tasks from the release response

The IDs of phases and tasks can be found in the response of the REST call that gets the contents of a release or template.

Inspect the contents of the release JSON object. The release ID (which you already know) is in the `id` field. All phases in the release are stored as a list in the `phases` property. In turn, the phases contain their tasks in the `tasks` property.

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

This is an example that uses the task ID in a REST call:

    GET http://localhost:5516/api/v1/tasks/Applications/Release9324610/Phase6441318/Task2674539

## Getting IDs by title

It is also possible to get a release, phase, or task by querying it by its title. Note that titles are not unique in XL Release. Releases may have duplicate names, as well as tasks in the same phase. So "by title" queries will always return a list of possibilities.

This is an example that finds all tasks that have the title "Configure email address and mail server" in the release with ID `Applications/Release9324610`:

    http://localhost:5516/api/v1/tasks/byTitle?taskTitle=Configure%20email%20address%20and%20mail%20server&releaseId=Applications/Release9324610

For more information about these calls, refer to the REST API:

* [Releases by title](/xl-release/6.0.x/rest-api/#!/releases/searchReleasesByTitle)
* [Phases by title](/xl-release/6.0.x/rest-api/#!/phases/searchPhasesByTitle)
* [Tasks by title](/xl-release/6.0.x/rest-api/#!/tasks/searchTasksByTitle)
