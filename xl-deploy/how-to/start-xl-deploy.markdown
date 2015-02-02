---
title: Start XL Deploy
categories:
- xl-deploy
subject:
- Getting started
tags:
- system administration
- getting started
---

To start XL Deploy, open a command line and go to the `XLDEPLOY_SERVER_HOME` directory. Start the XL Deploy server with the command:

* In XL Deploy 4.5.0 and earlier:
    * On Unix: `bin/server.sh`
    * On Windows: `bin/run.cmd`
* In XL Deploy 5.0.0 and later:
    * On Unix: `bin/run.sh`
    * On Windows: `bin/run.cmd`

Start the server with the `-h` flag to see the options it supports.

The command line options are:

* `-repository-keystore-password VAL` tells XL Deploy which password to use to access the repository keystore. As an alternative, this password can be specified in the `deployit.conf` configuration file using `repository.keystore.password` as a key. If not specified and the repository keystore does require a password, XL Deploy will prompt you for it.
* `-reinitialize` tells XL Deploy to reinitialize the repository. Used only in conjunction with `-setup`.

    **Note:** This flag only works if XL Deploy is running on the filesystem repository, not when you've configured XL Deploy to run against a database.

* `-setup` runs the XL Deploy Setup Wizard.
* `-setup-defaults VAL` specifies a file that contains default values for configuration properties set in the Setup Wizard.

### Server options

Any options you want to give the XL Deploy server when it starts can be specified in the `XLDEPLOY\_SERVER\_OPTS` environment variable.

### Starting XL Deploy in the background

By running the `run.sh` or `run.cmd` command, the XL Deploy server is started in the foreground. To run the server as a background process:

* In XL Deploy 4.5.0 and earlier:
    * On Unix, use `nohup bin/server.sh &`
    * On Windows, run XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy-as-a-service.html)
* In XL Deploy 4.5.0 and earlier:
    * On Unix, use `nohup bin/run.sh &`
    * On Windows, run XL Deploy [as a service](/xl-deploy/how-to/install-xl-deploy.html#install-the-server-as-a-daemon-or-service)
