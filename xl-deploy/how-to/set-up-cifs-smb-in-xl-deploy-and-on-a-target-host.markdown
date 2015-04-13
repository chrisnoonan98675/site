---
title: Set up CIFS/SMB in XL Deploy and on a target host
subject:
- Remoting plugin
categories:
- xl-deploy
tags:
- remoting
- connectivity
- cifs
- smb
- overthere
---

XL Deploy can use Telnet or WinRM to execute commands on the middleware hosts, but it requires the [CIFS](http://en.wikipedia.org/wiki/Server_Message_Block) protocol (also known as SMB) to transfer files to the middleware host.

To connect to a remote host using CIFS, ensure the host is reachable on port 445.

If you will be connecting as an administrative user, ensure that the administrative shares are configured. Otherwise, ensure that the user you will be using to connect has access to shares that correspond to the directory you want to access and that the `pathShareMappings` property is configured accordingly.
