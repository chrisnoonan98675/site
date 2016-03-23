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

As you can see on the screenshot, when configuring this task you have to select following required properties:

* _Template_ - the template from which to start a release.
* _New release title_ - the title given to the newly created release.
* _Variables_ - you have to fill in template variables, depending on which template you chose.

Additionally you can set the _Wait at Gate_ property to a gate within the same release. When the new release is started from the Start Release task, a dependency to it will be added to the selected gate. In this way you can start a sub-release from your main release, and wait for that sub-release to complete using the gate in your main release. If you don't specify any gates, then your main release will proceed normally while the sub-release runs.
