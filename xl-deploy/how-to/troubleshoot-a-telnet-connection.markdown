---
title: Troubleshoot a Telnet connection
categories:
- xl-deploy
subject:
- Remoting
tags:
- connectivity
- remoting
- telnet
- overthere
weight: 348
---

These are configuration errors that can occur when using XL Deploy with Telnet.

## Telnet connection fails with the message `VT100/ANSI escape sequence found in output stream. Please configure the Windows Telnet server to use stream mode (tlntadmn config mode=stream).`

The Telnet service has been configured to be in "Console" mode. Did you configure it as described in [the section on Telnet setup](#cifs_host_setup_telnet)?
