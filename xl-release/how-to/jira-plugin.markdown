---
title: Using the XL Release JIRA plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- jira
- task
---

The XL Release JIRA plugin allows XL Release to interact with a JIRA server. It includes the following task types:

* **Jira: Create Issue**: Create a new JIRA issue
* **Jira: Query**: Query JIRA using [JQL](https://confluence.atlassian.com/jira/advanced-searching-179442050.html)
* **Jira: Update Issue**: Update a single JIRA issue to change its status or post a comment
* **Jira: Update Issues**: Update multiple JIRA issues
* **Jira: Update Issues by Query**: Update JIRA issues based on a query

## Features

* Create a new JIRA issue
* Query JIRA for issues
* Update one or more existing JIRA issues
* Update JIRA issues based on a query

## Set up a JIRA server

To set up a JIRA server:

1. In XL Release, go to **Settings** > **Configuration** and click **Add Repository** under **Jira: Server**.
2. In the **Title** box, enter the name of the JIRA server. This name will appear in JIRA tasks.
3. In the **URL** box, enter the URL of the JIRA server.
4. In the **Username** and **Password** boxes, enter the server log-in user ID and password.
5. Click **Save** to save the server.

    ![JIRA server setup](../images/jira-configuration-details.png)

## Create issue

Create an issue in JIRA.

The following properties are available in the **Jira: Create issue** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Project**: The JIRA project name
* **Title**: The issue title
* **Description**: The issue description
* **Issue Type**: The issue type (for example, bug or improvement); refer to the [JIRA issue type documentation](https://confluence.atlassian.com/display/AOD/What+is+an+Issue#WhatisanIssue-IssueType) for a complete list

The output of the task is `issueId`, the ID of the created JIRA issue.

![JIRA Create Issue task](../images/jira-create-issue-task-details.png)

## Query

Executes a JQL query on the JIRA server to retrieve a list of issues with their descriptions.

The following properties are available in the **Jira: Query** task type:

* **Query**: A [JQL](https://confluence.atlassian.com/jira/advanced-searching-179442050.html) query that finds the issues that will be updated in this task

The output of the task is `issues`, a key-value map consisting of the issue ID and summary (title) of the issues that were found. When stored in a variable, you can use this data later in the **Update Issues** task.

![JIRA Query task](../images/jira-query-issues-task-details.png)

## Update issue

Updates a single issue in JIRA.

The following properties are available in the **Jira: Update issue** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Issue Id**: The ID of the issue to update
* **New Status**: The new status of the issue; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issue

![JIRA Update Issue task](../images/jira-update-issue-task-details.png)

## Update issues

Updates multiple issues in JIRA.

The following properties are available in the **Jira: Update issues** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Issues**: A key-value map consisting of the issue ID and the summary (title) of each issue (you can optionally update the summaries in JIRA). Note that the **Query** task produces a key-value map as output, so you can link these two tasks together.
* **New Status**: The new status of the issues; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issue
* **Update Summaries**: A checkbox indicating whether or not to change the issue summaries in JIRA

![JIRA Update Issues task](../images/jira-update-issues-task-details.png)

## Update issues by query

Updates issues found by a JQL query.

The following properties are available in the **Jira: Update issues by query** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Query**: A [JQL](https://confluence.atlassian.com/jira/advanced-searching-179442050.html) query that finds the issues that will be updated in this task
* **New Status**: The new status of the issues; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issues

The output of the task is `issues`, a key-value map consisting of the issue ID and summary (title) of all updated issues.

![JIRA Update Issues by Query task](../images/jira-update-issues-by-query-task-details.png)
