---
title: Shut down XL Deploy
categories:
- xl-deploy
subject:
- System administration
tags:
- system administration
---

If you have administrative permissions, you can shut down the XL Deploy server using the command-line interface (CLI) command:

    deployit.shutdown()

You can also stop the XL Deploy server using a REST API call. This is an example of a command to generate such a call (replace `admin:admin` with your own credentials):

    curl -X POST --basic -u admin:admin
        http://admin:admin@localhost:4516/deployit/server/shutdown

This requires the external `curl` command, available for both Unix and Windows.

**Note:** If you modify any file in the `conf` directory, `ext/synthetic.xml` or `ext/xl-rules`, then you must restart the XL Deploy server for the changes to take effect. For `ext/xl-rules`, you can change the default behavior by enabling automatic reloading in `conf/planner.conf` file.

## Unclean shutdown

If the server is not shut down cleanly, the next start-up may be slow because XL Deploy will need to rebuild indexes.

### Lock files left by unclean shutdown

If the server is not shut down cleanly, the following lock files may be left on the server:

* `XLDEPLOY_HOME/repository/.lock` (ensure that XL Deploy is not running before removing this file)
* `XLDEPLOY_HOME/repository/index/write.lock`
* `XLDEPLOY_HOME/repository/workspaces/default/write.lock`
* `XLDEPLOY_HOME/repository/workspaces/security/write.lock` (server start-up will be slower after this file is removed because the indexes must be rebuilt)
* `XLDEPLOY_HOME/repository/version/db/db.lck`
* `XLDEPLOY_HOME/repository/version/db/dbex.lck`
* `XLDEPLOY_HOME/repository/workspaces/default/db/db.lck`
* `XLDEPLOY_HOME/repository/workspaces/default/db/dbex.lck`
* `XLDEPLOY_HOME/repository/workspaces/security/db/db.lck`
* `XLDEPLOY_HOME/repository/workspaces/security/db/dbex.lck`
