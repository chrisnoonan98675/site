---
title: Additional Jython helper functions
categories:
xl-release
subject:
Public API
tags:
plugin
script
jython
customization
api
weight: 514
---

In addition to the [Jython API](/xl-release/latest/jython-api/index.html), the following helper functions are available in [Jython Script tasks](/xl-release/how-to/create-a-jython-script-task.html) and Python scripts for plugin tasks.

### getCurrentTask()

Returns the current task.

**Returns:** a Task object

### getCurrentPhase()

Returns the current phase.

**Returns:** a Phase object

### getCurrentRelease()

Returns the current release.

**Returns:** a Release object

### getTasksByTitle(taskTitle, phaseTitle = None, releaseId = None)

Finds tasks by title.

**taskTitle:** The task title to search.

**phaseTitle:** The phase title to search tasks on (optional: will search tasks in the whole release if not provided).

**releaseId:** The release id to search tasks on (optional: will search tasks in the current release if not provided).

**Returns:** An array of Task objects

### getPhasesByTitle(phaseTitle, releaseId = None)

Finds phases by title.

**phaseTitle:** The phase title to search.

**releaseId:** The release id to search phases on (optional: will search phases in the current release if not provided).

**Returns:** An array of Phase objects.

### getReleasesByTitle(releaseTitle)

Finds releases by title.

**releaseTitle:** The release title to search

**Returns:** An array of Release objects
