---
title: Start or shut down XL Deploy
---

## Start XL Deploy

To start the XL Deploy server, open a command prompt or terminal, go to the `XL_DEPLOY_SERVER_HOME/bin` directory, and execute the appropriate command:

{:.table .table-striped}
| Operating system | XL Deploy version | Command |
| ---------------- | ----------------- | ------- |
| Microsoft Windows | XL Deploy 4.5.x or earlier | `server.cmd` |
| Microsoft Windows | XL Deploy 5.0.0 or later | `run.cmd` |
| Unix-based systems | XL Deploy 4.5.x or earlier | `server.sh` |
| Unix-based systems | XL Deploy 5.0.0 or later | `run.sh` |

## Server options

Start the server with the `-help` flag to see the options it supports. They are:

{:.table .table-striped}
| Option | Description |
| ------ | ----------- |
| `-force-upgrades` | Forces the execution of upgrades at XL Deploy startup. This option is supported in XL Deploy 4.5.x and later. |
| `-recovery` | Attempts to recover a corrupted repository.
| `-repository-keystore-password VAL` | Specifies the password that XL Deploy should use to access the repository keystore. Alternatively, you can specify the password in the `deployit.conf` file with the `repository.keystore.password` key. If you do not specify the password and the keystore requires one, XL Deploy will prompt you for it. |
| `-reinitialize` | Reinitialize the repository. This option is only available for use with the `-setup` option, and it is only supported when XL Deploy is using a filesystem repository. It cannot be used when you have configured XL Deploy to run against a database. |
| `-setup` | Runs the XL Deploy setup wizard. |
| `-setup-defaults VAL` | Specifies a file that contains default values for configuration properties in the setup wizard. |

Any options you want to give the XL Deploy server when it starts can be specified in the `XL_DEPLOY_SERVER_OPTS` environment variable.

**Tip:** For information about the `-setup-defaults` option, refer to [Install XL Deploy](/xl-deploy/how-to/install-xl-deploy.html#automatically-install-xl-deploy-with-default-values).

## Start XL Deploy in the background

To run the XL Deploy server as a background process:

* In XL Deploy 4.5.0 and earlier:
    * On Unix, use `nohup bin/server.sh &`
    * On Windows, run XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html)
* In XL Deploy 5.0.0 and later:
    * On Unix, use `nohup bin/run.sh &`
    * On Windows, run XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html)

If you have installed XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html), you must ensure that the XL Deploy server is configured so that it can start without user interaction. For example, the server should not [require a password](/xl-deploy/how-to/install-xl-deploy.html#step-10-provide-a-password-for-the-encryption-key) for the encryption key that protects passwords in the repository. Alternatively, you can store the password in the `XL_DEPLOY_SERVER_HOME/conf/deployit.conf file` as follows:

    repository.keystore.password=MY_PASSWORD

## Shut down XL Deploy

If you have administrative permissions, you can shut down the XL Deploy server using the command-line interface (CLI) command:

    deployit.shutdown()

You can also stop the XL Deploy server using a REST API call. This is an example of a command to generate such a call (replace `admin:admin` with your own credentials):

    curl -X POST --basic -u admin:admin http://admin:admin@localhost:4516/deployit/server/shutdown

This requires the external `curl` command, available for both Unix and Microsoft Windows.

**Note:** If you modify any file in the `XL_DEPLOY_SERVER_HOME/conf` directory, or modify the `XL_DEPLOY_SERVER_HOME/ext/synthetic.xml` or `XL_DEPLOY_SERVER_HOME/ext/xl-rules.xml` file, then you must restart the XL Deploy server for the changes to take effect. For `xl-rules.xml`, you can change the default behavior as described in [Define a rule](/xl-deploy/how-to/how-to-define-rules.html#scanning-for-rules).

### Unclean shutdown

If the server is not shut down cleanly, the next start-up may be slow because XL Deploy will need to rebuild indexes.

#### Lock files left by unclean shutdown

If the server is not shut down cleanly, the following lock files may be left on the server:

* `XL_DEPLOY_SERVER_HOME/repository/.lock` (ensure that XL Deploy is not running before removing this file)
* `XL_DEPLOY_SERVER_HOME/repository/index/write.lock`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/default/write.lock`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/security/write.lock` (server start-up will be slower after this file is removed because the indexes must be rebuilt)
* `XL_DEPLOY_SERVER_HOME/repository/version/db/db.lck`
* `XL_DEPLOY_SERVER_HOME/repository/version/db/dbex.lck`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/default/db/db.lck`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/default/db/dbex.lck`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/security/db/db.lck`
* `XL_DEPLOY_SERVER_HOME/repository/workspaces/security/db/dbex.lck`
