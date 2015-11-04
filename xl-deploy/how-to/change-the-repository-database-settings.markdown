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

If you store the XL Deploy repository [in a database](/xl-deploy/how-to/configure-the-xl-deploy-repository.html#using-a-database), you may occasionally need to change settings such as the database username or password; for example, to test a new release against a non-production database.

To do so, you must update three files with the new settings:

* `<XLDEPLOY_SERVER_HOME>/conf/jackrabbit-repository.xml`
* `<XLDEPLOY_REPOSITORY_HOME>/workspaces/default/workspace.xml`
* `<XLDEPLOY_REPOSITORY_HOME>/workspaces/security/workspace.xml`

Note that if there are additional `workspace.xml` files in the repository directory, you must also update the settings in those files.
