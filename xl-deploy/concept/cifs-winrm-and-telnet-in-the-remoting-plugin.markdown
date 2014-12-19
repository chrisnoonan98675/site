---
title: CIFS, WinRM, and Telnet in the Remoting plugin
categories: 
- xl-deploy
subject:
- Remoting plugin
tags:
- plugin
- connectivity
- remoting
- cifs
- winrm
- telnet
---

The CIFS protocol implementation of XL Deploy uses the [CIFS protocol](http://en.wikipedia.org/wiki/Server_Message_Block), also known as SMB, for file manipulation and, depending on the settings, uses either [WinRM](http://en.wikipedia.org/wiki/WS-Management) or [Telnet](http://en.wikipedia.org/wiki/Telnet) for process execution. You will most likely not need to install new software although you might need to enable and configure some services:

* The built-in file sharing capabilities of Windows are based on CIFS and are therefore available and enabled by default.
* WinRM is available on Windows Server 2008 and up. XL Deploy supports basic authentication for local accounts and Kerberos authentication for domain accounts. Overthere has a built-in WinRM library that can be used from all operating systems by setting the `connectionType` property to WINRM_INTERNAL. When connecting from a host that runs Windows, or when using a `winrs` proxy that runs Windows, the native WinRM capabilities of Windows (that is, the `winrs` command) can be used by setting the `connectionType` property to WINRM_NATIVE.
* A Telnet Server is available on all Windows Server versions, although it might not be enabled.

## Administrative shares

By default, XL Deploy will access the [administrative shares](http://en.wikipedia.org/wiki/Administrative_share) on the remote host. These shares are only accessible for users that are part of the **Administrators** on the remote host.

If you want to access the remote host using a regular account, use the `pathShareMapping` property to configure the shares to use for the paths XL Deploy will be connecting to. Of course, the user configured with the `username` property should have access to those shares and the underlying directories and files.

**Note:** XL Deploy will take care of the translation from Windows paths, like `C:\Program Files\IBM\WebSphere\AppServer`, to SMB URLs that use the administrative shares, like `smb://username:password@hostname/C$/Program%20Files/IBM/WebSphere/AppServer` (which corresponds to the UNC path `\\hostname\C$\Program Files\IBM\WebSphere\AppServer`). Therefore, your code can use Windows-style paths.
