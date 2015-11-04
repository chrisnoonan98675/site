---
title: Introduction to the XL Release JIRA plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- jira
- task
---

The JIRA plugin is an XL Release plugin that allows XL Release to interact with a JIRA Server.

The JIRA plugin comes with two task types:

* **Create issue:** Create a new JIRA issue.
* **Update issue:** Update a JIRA issue to change its status or to post a comment.

## Create issue

![JIRA task](../images/jira-create-issue-task-details.png)

Input Properties:

* `Server` is the [JIRA Server](#server-configuration) that will be used.
* `Username` is the JIRA username. This property and the `Password` properties are optional, if set they override the credentials defined on the Server Configuration.
* `Password` is the JIRA password.
* `Project` is the JIRA project name.
* `Title` is the issue title.
* `Description` is the issue description.
* `Issue Type` is the issue type (Bug, Improvement, Feature...) See [JIRA Issue Types documentation](https://confluence.atlassian.com/display/AOD/What+is+an+Issue#WhatisanIssue-IssueType)

Output Properties:

* `issueId` is the ID of the created issue.

## Update issue

![JIRA task](../images/jira-update-issue-task-details.png)

Input Properties:

* `Server` is the [JIRA Server](#server-configuration) that will be used.
* `Username` is the JIRA username. This property and the `Password` properties are optional, if set they override the credentials defined on the Server Configuration.
* `Password` is the JIRA password.
* `Issue Id` is the ID of the issue to update.
* `New Status` is the new status of the issue. See [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow).
* `Comment` is the comment text that will be added when updating the issue.

## Server configuration

JIRA servers are defined globally in the **Configuration** page.

![JIRA server](../images/jira-configuration-details.png)

A JIRA Server has the following properties:

* `Title` is the name of the JIRA Server. This name will be shown in the JIRA Tasks.
* `Url` is the JIRA Server URL.
* `Username` is the username used to connect to this server.
* `Password` is the username used to connect to this server.
