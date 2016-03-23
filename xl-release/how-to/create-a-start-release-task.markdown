---
title: Create a start release task
categories:
- xl-release
subject:
- Task types
tags:
- task
- start release
- sub-release
- subrelease
---

A **start release** task is an automatic task which starts a release from a configured template.

![Start release task details](../images/start-release-task-details.png)

In this example when the task executes, it will create and start a new release from template "_Front office services release template_", with title "_Release front office services 2.1_" and value "_2.1_" of the variable "_version_". When the release is created, it will be automatically added as a dependency of gate "_Wait for front office services 2.1 to be released_", so that execution of the parent release will stop until the sub-release is finished. 

As you can see on the screenshot, when configuring this task you have to select following required properties:

* _Template_ - the template from which to start a release.
* _New release title_ - the title given to the newly created release.
* _Variables_ - you have to fill in template variables, depending on which template you chose.

Additionally you can set the _Wait at Gate_ property to a gate within the same release. When the new release is started from the Start Release task, a dependency to it will be added to the selected gate. In this way you can start a sub-release from your main release, and wait for that sub-release to complete using the gate in your main release. If you don't specify any gates, then your main release will proceed normally while the sub-release runs.
