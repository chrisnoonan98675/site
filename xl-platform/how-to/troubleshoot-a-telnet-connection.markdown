---
title: Troubleshoot a Telnet connection
categories:
- xl-platform
- xl-deploy
- xl-release
subject:
- Remoting
tags:
- connectivity
- remoting
- telnet
- overthere
weight: 348
---

The [Remoting plugin](/xl-platform/concept/remoting-plugin.html) allows XL Deploy and XL Release to manipulate files and execute commands on remote hosts. It does so by using the [Overthere](https://github.com/xebialabs/overthere) framework, which is a Java library to manipulate files and execute processes on remote hosts.

The Remoting plugin supports CIFS, Telnet, and WinRM for connectivity to Microsoft Windows hosts. These are configuration errors that can occur when using XL Deploy or XL Release with Telnet.

#### Telnet connection fails with the message `VT100/ANSI escape sequence found in output stream. Please configure the Windows Telnet server to use stream mode (tlntadmn config mode=stream).`

The Telnet service has been configured to be in "Console" mode. Did you configure it as described in [the section on Telnet setup](#cifs_host_setup_telnet)?
