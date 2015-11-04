---
title: Useful WinRM commands
subject:
- Remoting plugin
categories:
- xl-deploy
tags:
- connectivity
- remoting
- winrm
---

These commands are useful when you are using <a href="http://msdn.microsoft.com/en-us/library/aa384426(v=vs.85).aspx">Microsoft Windows Remote Management (WinRM)</a> to execute commands and manipulate files on a remote host.

Quickly configure WinRM with HTTPS:

    winrm quickconfig -transport:https

View the complete WinRM configuration:

    winrm get winrm/config

View the listeners that have been configured:

    winrm enumerate winrm/config/listener

Create an HTTP listener:

    winrm create winrm/config/listener?Address=*+Transport=HTTP` (also done by `winrm quickconfig`)

Allow all hosts to connect to the WinRM listener:

    winrm set winrm/config/client @{TrustedHosts="*"}

Allow a fixed set of hosts to connect to the WinRM listener:

    winrm set winrm/config/client @{TrustedHosts="host1,host2..."}

For more information about WinRM, refer to <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/aa384426(v=vs.85).aspx">the online documentation at Microsoft's DevCenter</a>. 
