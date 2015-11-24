---
title: Change the repository database settings
categories:
- xl-deploy
subject:
- Repository
tags:
- system administration
- database
- repository
- security
- password
---

If you store the XL Deploy repository [in a database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database), you may occasionally need to move the database or change settings such as the database username or password (for example, to test a new release against a non-production database).

To do so, you must *manually* update the following files with the new settings:

* `<XLDEPLOY_SERVER_HOME>/conf/jackrabbit-repository.xml`
* `<XLDEPLOY_REPOSITORY_HOME>/workspaces/default/workspace.xml`
* `<XLDEPLOY_REPOSITORY_HOME>/workspaces/security/workspace.xml`

**Important:** If there are additional `workspace.xml` files in the repository directory, you must also update the settings in those files.
