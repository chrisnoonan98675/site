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

The XL Release JIRA plugin allows XL Release to interact with a JIRA server. It includes two task types:

* **Jira: Create issue**: Create a new JIRA issue
* **Jira: Update issue**: Update a JIRA issue to change its status or post a comment

## Features

* Create a new JIRA issue
* Update an existing JIRA iusse

## Set up a JIRA server

To set up a JIRA server:

1. In XL Release, go to **Settings** > **Configuration** and click **Add Repository** under **Jira: Server**.
2. In the **Title** box, enter the name of the JIRA server. This name will appear in JIRA tasks.
3. In the **URL** box, enter the URL of the JIRA server.
4. In the **Username** and **Password** boxes, enter the server log-in user ID and password.
5. Click **Save** to save the server.

    ![JIRA server](../images/jira-configuration-details.png)

## Create issue

The following properties are available in the **Jira: Create issue** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Project**: The JIRA project name
* **Title**: The issue title
* **Description**: The issue description
* **Issue Type**: The issue type (for example, bug or improvement); refer to the [JIRA issue type documentation](https://confluence.atlassian.com/display/AOD/What+is+an+Issue#WhatisanIssue-IssueType) for a complete list

The output of the task is `issueId`, the ID of the created JIRA issue.

![JIRA task](../images/jira-create-issue-task-details.png)


## Query

The following properties are available in the **Jira: Query** task type:

* **Query**: A JQL query that finds the issues that will be updated in this task

The output of the task is `issues`, a key-value map containing all found issues

![JIRA task](../images/jira-query-issues-task-details.png)

## Update issue

The following properties are available in the **Jira: Update issue** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Issue Id**: The ID of the issue to update
* **New Status**: The new status of the issue; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issue

![JIRA task](../images/jira-update-issue-task-details.png)

## Update issues

The following properties are available in the **Jira: Update issues** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Issues**: A key-value map consisting of the issue ID and the summary(title) of the issue. Optionally this summary can be updated in Jira
* **New Status**: The new status of the issue; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issue
* **updateSummaries**: A checkbox indicating whether or not to change the issue summaries in Jira

![JIRA task](../images/jira-update-issues-task-details.png)

## Update issues by query

The following properties are available in the **Jira: Update issues by query** task type:

* **Server**: The JIRA server that will be used
* **Username** and **Password**: The server log-in user ID and password; if set, these will override the credentials defined in the JIRA server configuration
* **Query**: A JQL query that finds the issues that will be updated in this task
* **New Status**: The new status of the issue; refer to the [JIRA Workflow documentation](https://confluence.atlassian.com/display/JIRA/What+is+Workflow) for a complete list
* **Comment**: The text of a comment to add to the issue

The output of the task is `issues`, a key-value map containing all updated issues

![JIRA task](../images/jira-update-issues-by-query-task-details.png)
