---
title: Connect through an SSH jumpstation
categories:
- xl-deploy
subject:
- Remoting plugin
tags:
- remoting
- connectivity
- ssh
---

When XL Deploy cannot reach a remote host directly, but that host can be reached by setting up one (or more) SSH tunnels, configure one (or more) `overthere.SshJumpstation` CIs as follows:

1. Create an `overthere.SshJumpStation` CI that represents a host to which XL Deploy can connect directly.
1. For each remote host that cannot be reached directly by XL Deploy, create an `overthere.Host` as usual, but set the jumpstation property to refer to the `overthere.SshJumpStation` CI created in step 1.

When XL Deploy creates a connection to the remote host and determines that it needs to connect through a jumpstation, and will first open a connection to that SSH jumpstation and then setup a SSH tunnel (also known as a [local port forward](https://en.wikipedia.org/wiki/Port_forwarding#Local_port_forwarding)) to the remote host.

**Note:** SSH jumpstations can also refer to other SSH jumpstations or to HTTP proxies for even more complex network setups, but cycles are not allowed.
