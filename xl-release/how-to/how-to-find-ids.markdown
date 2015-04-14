---
title: How to find identifiers for use in the REST API
categories:
- xl-release
subject: Release
tags:
- api
---

To use the REST API successfully, you need to know the unique identifier of templates, releases, phases and tasks. This article explains where you can find them.

## Releases & Templates

Both releases and templates have an ID of the form `Applications/ReleaseXXX`, where XXX is a 7 digit number. The fastest way to find it is to open it in the browser and take a look at the URL in the location bar. 

![URL for template](images/template-release-id.png)

Just take the last pat of the URL, and put `Applications` in front of it. In this case the identifier of the template is `Applications/Release29994650`.

**Note:** All IDs start with `Applications/`. The reason for this is, well... technical. [1]

Now that you have the ID, you can use it in a REST call. For example, to get the contents of a release, do:

    GET http://localhost:5516/api/v1/releases/Applications/Release9324610


## Phases and Tasks from the Release response

The IDs of phases and tasks can be found in the response of the above REST call that gets the contents of a release or template.

Inspect the contents of the Release JSON object. The release ID (which we already know) is in the `id` field. All phases in the release are stored as a list in the `phases` property. In turn, the phases contain their tasks in the `tasks` property. 

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

Here's an example of using the task ID in a REST call:

    GET http://localhost:5516/api/v1/tasks/Applications/Release9324610/Phase6441318/Task2674539

## Getting IDs by title

It is also possible to get a release, phase or task by querying it by its title. Note that titles are not unique in XL Release. Releases may have duplicate names, as well as tasks in the same phase for example. So the 'by title' queries will always return a list of possibilities.

Here's an example to find all the tasks that have the title "Configure email address and mail server" in the release with ID `Applications/Release9324610`.

    http://localhost:5516/api/v1/tasks/byTitle?taskTitle=Configure%20email%20address%20and%20mail%20server&releaseId=Applications/Release9324610

For more information about these calls, refer to the REST API:
 
 * [Releases by title](/xl-release/4.5.x/rest-api/#!/releases/searchReleasesByTitle)
 * [Phases by title](/xl-release/4.5.x/rest-api/#!/phases/searchPhasesByTitle)
 * [Tasks by title](/xl-release/4.5.x/rest-api/#!/tasks/searchTasksByTitle)

---
[1] XL Release uses the same database as XL Deploy. In the XL Deploy database, each identifier _must_ start with one of the following words: Applications, Environments, Infrastructure or Configuration. In XL Release we picked 'Applications' for releases and templates.