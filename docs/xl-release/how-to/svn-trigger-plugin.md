---
title: Using the XL Release SVN trigger
categories:
xl-release
subject:
Triggers
tags:
svn
trigger
since:
XL Release 4.5.0
---

The XL Release SVN trigger periodically polls a Subversion repository and triggers a release when it detects a new commit.

## Features

* Periodically polls a Subversion (SVN) repository
* Triggers a release when it detects a new commit

## Set up an SVN repository

To set up an SVN repository:

1. In XL Release, go to **Settings** > **Shared configuration** and click **Add Repository** under **Svn: Repository**.

    **Note:** Prior to XL Release 6.0.0, go to **Settings** > **Configuration**.

2. In the **Title** box, enter the name of the repository.
3. In the **URL** box, enter the address where the server is reachable.
4. In the **Username** and **Password** boxes, enter the server log-in user ID and password.
5. Click **Save** to save the repository.

## Add an SVN trigger to a template

To create an SVN trigger:

1. Add a trigger to the template, as described in [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).
2. In the **Svn Repository** box, enter the SVN repository to poll.
3. In the **Branch** box, optionally enter the branch that the trigger should watch; this will be concatenated to the SVN repository URL.
4. In the **Username** and **Password** boxes, enter the log-in user ID and password to use to connect to the repository (leave them blank if authentication is not required).
5. Finish saving the trigger, as described in [Create a release trigger](/xl-release/how-to/create-a-release-trigger.html).

## Output properties

The output of the SVN trigger is a **Commit Id**, which corresponds to the ID of the new commit.
