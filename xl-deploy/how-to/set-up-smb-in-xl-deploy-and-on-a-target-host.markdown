---
title: Set up SMB in XL Deploy and on a target host
subject:
- Remoting
categories:
- xl-deploy
tags:
- remoting
- connectivity
- cifs
- smb
- overthere
weight: 340
---

XL Deploy can use Telnet or WinRM to execute commands on the middleware hosts, but it requires the [CIFS|https://technet.microsoft.com/en-us/library/cc939973.aspx] or [SMB](http://en.wikipedia.org/wiki/Server_Message_Block) protocol to transfer files to the middleware host.

**Note:** SMB is supported in XL Deploy 5.5.6, 6.0.0, and later.

To connect to a remote host using CIFS or SMB, ensure the host is reachable on port 445.

If you will be connecting as an administrative user, ensure that the administrative shares are configured. Otherwise, ensure that the user you will be using to connect has access to shares that correspond to the directory you want to access and that the `pathShareMappings` property is configured accordingly.
