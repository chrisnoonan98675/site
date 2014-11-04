---
title: Troubleshooting SSH host unknown response code
categories:
- xl-deploy
tags:
- connectivity
---

In XL Deploy, after adding an [`overthere.SshHost`](http://docs.xebialabs.com/releases/latest/xl-deploy/remotingPluginManual.html#overtheresshhost), you may find that the `checkConnection` control task returns:

    net.schmizz.sshj.xfer.scp.SCPException: Received unknown response code

This is caused by a log-in script that prints to `stdout`. To resolve this error, either remove all `echo` statements or prefix all `echo` statements in your log-in script with `[ -n $"PS1" ] &&`. For example, change:

    echo "welcome back, $USER"

To:

    [ -n $"PS1" ] && echo "welcome back, $USER"
