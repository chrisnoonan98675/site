---
title: Add comments to a task
categories:
- xl-release
subject:
- Tasks
tags:
- task
- comment
- script
---

In the XL Release GUI, you can add comments to any [type of task](/xl-release/concept/types-of-tasks-in-xl-release.html) in a template or release by opening the task and clicking **Add comment**. 

You can also use the [REST API](/xl-release/latest/rest-api/index.html) and [Jython API](/jython-docs/#!/xl-release/4.8.x/) to automatically add comments to tasks; for an example, refer to [Using the XL Release API in scripts](/xl-release/how-to/using-the-xl-release-api-in-scripts.html).

The **Comments** heading in a task shows the number of comments on the task.

## Comment size limit

To prevent performance issues, comments are limited to 32,768 characters. If you manually add a comment that exceeds the limit, XL Release will truncate the comment. If a [Script task](/xl-release/how-to/create-a-script-task.html) tries to add a comment that exceeds the limit, XL Release will truncate the comment and attach the full output of the script to the task.

You can change the comment size limit for each task type in the `<XLRELEASE_HOME>/conf/deployit-defaults.properties` file. For example:

    #xlrelease.Task.maxCommentSize=32768

To change the limit, delete the number sign (`#`) at the beginning of the relevant line, change the limit as desired, then save the file and restart the XL Release server.
