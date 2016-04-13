---
title: Configure an SSH jumpstation
categories:
- xl-release
subject:
- Remoting plugin
tags:
- remoting
- connectivity
- ssh
- jumpstation
since:
- XL Release 5.0.0
---

When XL Release cannot reach a remote host directly, but that host can be reached by setting up one or more SSH tunnels, configure an SSH jumpstation as follows:

1. Log in to XL Release as a user with *Admin* permission.
1. Select **Settings** > **Configuration** from the top menu.
1. Under **Remote Script: SSH jumpstation** click **Add SSH jumpstation**.
1. Enter a name for the jumpstation and provide the connection details. 

Jumpstations can also be reached via other jumpstations for even more complex network setups, but cycles are not allowed.

When XL Release needs a connection to a remote host and determines that it needs to connect through a jumpstation, it will first open a connection to that jumpstation and then set up an SSH tunnel ("local port forward") to the remote host.

**Note:** Jumpstations are configured globally in XL Release, not per-release.
