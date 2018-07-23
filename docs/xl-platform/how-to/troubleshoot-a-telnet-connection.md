---
title: Troubleshoot a Telnet connection
categories:
xl-platform
xl-deploy
xl-release
subject:
Remoting
tags:
connectivity
remoting
telnet
overthere
weight: 348
---

The [remoting functionality](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release uses the [Overthere framework](https://github.com/xebialabs/overthere) to manipulate files and execute commands on remote hosts. CIFS, Telnet, and WinRM are supported for connectivity to Microsoft Windows hosts. These are configuration errors that can occur when using XL Deploy or XL Release with Telnet.

#### Telnet connection fails with the message `VT100/ANSI escape sequence found in output stream. Please configure the Windows Telnet server to use stream mode (tlntadmn config mode=stream).`

The Telnet service has been configured to be in "Console" mode. Did you configure it as described in [Using CIFS, SMB, WinRM, and Telnet](/xl-platform/how-to/using-cifs-smb-winrm-and-telnet.html#set-up-telnet)?
