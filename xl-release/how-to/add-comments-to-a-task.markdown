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

You can add comments to any [type of task](/xl-release/concept/types-of-tasks-in-xl-release.html) in a template or release by clicking **Add comment**. You can also use the [REST API](/xl-release/latest/rest-api/index.html) and [Jython API](/jython-docs/#!/xl-release/4.8.x/) automatically add comments to tasks; for an example, refer to [Using the XL Release API in scripts](/xl-release/how-to/using-the-xl-release-api-in-scripts.html).

The **Comments** heading in a task shows the number of comments on the task.

## Comment size limit

To prevent performance issues, comments are limited to 32,768. If a [script task](/xl-release/how-to/create-a-script-task.html) tries to add a comment that exceeds the limit, the comment will be truncated and the full output of the script will be added as an attachment on the task.

To change the limit, open `<XLRELEASE_HOME>/conf/deployit-defaults.properties` and uncomment and edit the following line:

    xlrelease.BaseScriptTask.maxCommentSize=32768
