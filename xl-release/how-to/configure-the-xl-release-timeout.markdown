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

In XL Release, you can configure the time that a user can remain logged in without using the GUI. After this timeout, the user will be logged out.

The timeout only applies during one browser sessions; if the user closes the browser, XL Release automatically logs the user out.

To configure the timeout:

1. Open `<XL_RELEASE_SERVER_HOME>/conf/xl-release-server.conf`.
1. Locate the `client.session.timeout.minutes` setting.
1. Change it to the number of minutes XL Release should wait before logging the user out.

    The default value of `0` means that no timeout is configured.

1. Save the file.
1. Restart the XL Release server.
