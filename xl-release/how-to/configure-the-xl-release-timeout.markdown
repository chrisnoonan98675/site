---
title: Configure the XL Release timeout
categories:
- xl-release
subject:
- System administration
tags:
- system administration
- security
- user management
---

In `<XL_RELEASE_SERVER_HOME>/conf/xl-release-server.conf`, you can set `client.session.timeout.minutes` to the number of minutes it takes before the user is disconnected when he is not using the XL Release GUI. The default value of '0' means that no time-out is configured. This configuration is only applicable during one browser session. If user closes the browser then the user will have to login again.
