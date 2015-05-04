---
title: Introduction to the XL Release JIRA plugin
categories:
- xl-release
subject:
- JIRA plugin
tags:
- plugin
- jira
---

The Jira plugin is an XL Release plugin that allows XL Release to interact with a Jira Server.

The Jira plugin comes with two task types:

* **Create issue:** Create a new Jira issue.
* **Update issue:** Update a Jira issue to change its status or to post a comment.

## Create issue

![Jira task](../images/jira-create-issue-task-details.png)

Input Properties:

* `Server` is the [Jira Server](#server-configuration) that will be used.
* `Username` is the Jira username. This property and the `Password` properties are optional, if set they override the credentials defined on the Server Configuration.
* `Password` is the Jira password.
* `Project` is the Jira project name.
* `Title` is the issue title.
* `Description` is the issue description.
* `Issue Type` is the issue type (Bug, Improvement, Feature...) See [Jira Issue Types documentation](https://confluence.atlassian.com/display/AOD/What+is+an+Issue#WhatisanIssue-IssueType)

Output Properties:

* `issueId` is the ID of the created issue.

## Update issue

![Jira task](../images/jira-update-issue-task-details.png)

Input Properties:

* `Server` is the [Jira Server](#server-configuration) that will be used.
* `Username` is the Jira username. This property and the `Password` properties are optional, if set they override the credentials defined on the Server Configuration.
* `Password` is the Jira password.
* `Issue Id` is the ID of the issue to update.
* `New Status` is the new status of the issue. See [Jira Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow).
* `Comment` is the comment text that will be added when updating the issue.

## Server configuration

Jira servers are defined globally in the **Configuration** page.

![Jira server](../images/jira-configuration-details.png)

A Jira Server has the following properties:

* `Title` is the name of the Jira Server. This name will be shown in the Jira Tasks.
* `Url` is the Jira Server URL.
* `Username` is the username used to connect to this server.
* `Password` is the username used to connect to this server.
