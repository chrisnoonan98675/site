---
title: How to find identifiers for use in the REST API
categories:
- xl-release
subject:
- API
tags:
- api
- script
- release
- template
- phase
- task
weight: 512
---

To use the XL Release REST API, you need to know the unique identifier of templates, releases, phases and tasks. This topic explains where you can find them.

## Releases and templates

Both releases and templates have an ID of the form `Applications/ReleaseXXXXXXX`, where `XXXXXXX` is a seven-digit number. The fastest way to find it is to open it in the browser and look at the URL in the location bar.

![URL for template](../images/template-release-id.png)

Just take the last part of the URL and put `Applications` in front of it. In this case, the identifier of the template is `Applications/Release29994650`.

**Note:** All IDs start with `Applications/` for technical reasons (XL Release uses the same database as [XL Deploy](/xl-deploy), which limits the ID prefixes that can be used).

Now that you have the ID, you can use it in a REST call. For example, to get the contents of a release, do:

    GET http://localhost:5516/api/v1/releases/Applications/Release9324610

## Phases and tasks from the release response

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

## Getting IDs by title

It is also possible to get a release, phase, or task by querying it by its title. Note that titles are not unique in XL Release. Releases may have duplicate names, as well as tasks in the same phase. So "by title" queries will always return a list of possibilities.

This is an example that finds all tasks that have the title "Configure email address and mail server" in the release with ID `Applications/Release9324610`:

    http://localhost:5516/api/v1/tasks/byTitle?taskTitle=Configure%20email%20address%20and%20mail%20server&releaseId=Applications/Release9324610

For more information about these calls, refer to the REST API:

* [Releases by title](/xl-release/4.8.x/rest-api/#!/releases/searchReleasesByTitle)
* [Phases by title](/xl-release/4.8.x/rest-api/#!/phases/searchPhasesByTitle)
* [Tasks by title](/xl-release/4.8.x/rest-api/#!/tasks/searchTasksByTitle)
