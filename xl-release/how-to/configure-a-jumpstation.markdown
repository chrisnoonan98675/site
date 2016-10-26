---
title: Configure an SSH jumpstation or HTTP proxy
categories:
- xl-release
subject:
- Remoting plugin
tags:
- remoting
- connectivity
- ssh
- jumpstation
- proxy
since:
- XL Release 5.0.0
---

When XL Release cannot reach a remote host directly, but that host can be reached via one or more SSH tunnels or HTTP proxies, you can configure these as follows:

1. Log in to XL Release as a user with the *Admin* permission.
1. Select **Settings** > **Task configurations** from the top menu.

    **Note:** Prior to XL Release 6.0.0, go to **Settings** > **Configuration**.

1. Depending on the kind of jumpstation you want to set up:
    1. Under **Remote Script: SSH jumpstation** click **Add SSH jumpstation** -or-
    1. Under **Remote Script: HTTP proxy** click **Add HTTP proxy**
1. Enter a name for the jumpstation and provide the connection details.

SSH jumpstations can also be reached via other jumpstations for even more complex network setups, but cycles are not allowed.

When XL Release needs a connection to a remote host and determines that it needs to connect through a jumpstation, it will first open a connection to that jumpstation and then set up an SSH tunnel ("local port forward") or proxied connection to the remote host.

**Note:** Jumpstations are configured globally in XL Release, not per-release.
