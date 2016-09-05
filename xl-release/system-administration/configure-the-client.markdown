---
title: Configure the XL Release client
---

## Configure the polling interval

You can configure the polling interval to automatically refresh XL Release screens when the status of tasks changes. Configure this in the **Polling** section of **Settings** > **General settings**.

The General settings page is only available to users who have the *Admin* [global permission](/xl-release/how-to/configure-permissions.html).

![Reports Settings](/xl-release/images/polling-settings.png)

## Configure the XL Release timeout

In XL Release, you can configure the time that a user can remain logged in without using the GUI. After this timeout, the user will be logged out.

The timeout only applies during one browser sessions; if the user closes the browser, XL Release automatically logs the user out.

To configure the timeout:

1. Open `<XL_RELEASE_SERVER_HOME>/conf/xl-release-server.conf`.
1. Locate the `client.session.timeout.minutes` setting.
1. Change it to the number of minutes XL Release should wait before logging the user out.

    The default value of `0` means that no timeout is configured.

1. Save the file.
1. Restart the XL Release server.
