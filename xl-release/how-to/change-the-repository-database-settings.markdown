---
title: Change the repository database settings
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- database
- repository
- security
- password
weight: 494
---

If you store the XL Release repository [in a database](/xl-release/how-to/configure-the-xl-release-repository-in-a-database.html), you may occasionally need to move the database or change settings such as the database username or password (for example, to test a new release against a non-production database).

To do so, you must *manually* update the following files with the new settings:

* `XL_RELEASE_SERVER_HOME/conf/jackrabbit-repository.xml`
* `XLRELEASE_REPOSITORY_HOME/workspaces/default/workspace.xml`
* `XLRELEASE_REPOSITORY_HOME/workspaces/security/workspace.xml`

**Important:** If there are additional `workspace.xml` files in the repository directory, you must also update the settings in those files.
