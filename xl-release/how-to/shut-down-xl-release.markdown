---
title: Shut down XL Release
categories:
- xl-release
subject:
- System administration
tags:
- system administration
---

You can shut down the XL Release server using a REST API call. The following is an example of a command to generate such a call (replace `admin:admin` with your own credentials):

    curl -X POST --basic -u admin:admin
        http://admin:admin@localhost:5516/server/shutdown

This requires the external `curl` command, which is available for both Unix and Windows.

You can also shut down the server using the command-line interface.
