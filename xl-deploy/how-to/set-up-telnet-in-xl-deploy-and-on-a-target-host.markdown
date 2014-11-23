---
title: Set up Telnet in XL Deploy and on a target host
subject:
- Remoting plugin
categories:
- xl-deploy
tags:
- remoting
- connectivity
- telnet
- overthere
---

To use the TELNET connection type, enable and configure the Telnet Server on the remote host:

1. (Optional) If the Telnet Server is not already installed on the remote host, add it using the Add Features Wizard in the Server Manager console.
1. (Optional) If the remote host is running Windows Server 2003 SP1 or an x64-based version of Windows Server 2003, install the Telnet server according to [these instructions from the Microsoft Support site](http://support.microsoft.com/kb/899260).
1. Enable the Telnet Server Service on the remote host according to <a href="http://technet.microsoft.com/en-us/library/cc732046(WS.10).aspx">these instructions on the Microsoft Technet site</a>.
1. After you have started the Telnet Server, open a command prompt as the Administrator user on the remote host and enter the command tlntadmn config mode=stream to enable stream mode.

When the Telnet server is enabled any user that is in the Administrators group or that is in the TelnetClients group and that has the Allow logon locally privilege can log in using Telnet. See the Microsoft Technet to learn [how to grant a user or group the right to logon locally on Windows Server 2008 R2](http://technet.microsoft.com/en-us/library/ee957044(WS.10).aspx).

## Domain accounts

For the TELNET connection type, domain accounts must be specified using the old-style domain syntax, e.g `DOMAIN\USER`.
