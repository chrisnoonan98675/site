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

When you [start XL Release](/xl-release/how-to/start-xl-release.html) by executing the `<XL_RELEASE_SERVER_HOME>/bin/server.sh` or `<XL_RELEASE_SERVER_HOME>\bin\server.cmd` command, the XL Release server is started in the foreground. To run the server as a background process on a Unix system, use:

    nohup bin/server.sh &

On a Windows system, you must configure XL Release to run as a [service](http://support.xebialabs.com/entries/20022143-install-deployit-as-a-windows-service).
