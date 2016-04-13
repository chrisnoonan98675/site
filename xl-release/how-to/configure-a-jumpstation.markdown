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
---

When XL Release cannot reach a remote host directly, but that host can be reached by setting up one (or more) SSH tunnels, configure an SSH Jumpstation as follows:

1. Log in as someone having Admin permissions
1. On the top bar menu, go to Settings > Configuration
1. Under 'Remote Script: SSH jumpstation' click 'Add SSH jumpstation'

On the input form you can now provide connection details for this jumpstation. Also a title (symbolic name) needs to be set that will be used throughout XL Release to refer to this jumpstation. Jumpstations can also be reached via other jumpstations for even more complex network setups, but cycles are not allowed.

When XL Release needs a connection to a remote host and determines that it needs to connect through a jumpstation, it will first open a connection to that jumpstation and then set up an SSH tunnel ("local port forward") to the remote host.

Please note that jumpstations are configured XL Release-wide, not per-release.
