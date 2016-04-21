---
title: Start XL Release in the background
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- installation
- setup
---

When you [start XL Release](/xl-release/how-to/start-xl-release.html) by executing the `<XL_RELEASE_SERVER_HOME>/bin/run.sh` or `<XL_RELEASE_SERVER_HOME>\bin\run.cmd` command, the XL Release server is started in the foreground. To run the server as a background process on a Unix system, use:

    nohup bin/run.sh &

You can also [Install XL Release as a service](/xl-release/how-to/install-xl-release-as-a-service.html).
