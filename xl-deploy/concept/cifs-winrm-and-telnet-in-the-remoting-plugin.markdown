---
title: CIFS, WinRM, and Telnet in the Remoting plugin
categories: 
- xl-deploy
subject:
- Bundled plugins
tags:
- plugin
- connectivity
- remoting
- cifs
- winrm
- telnet
---

The CIFS protocol implementation of XL Deploy uses [CIFS](http://en.wikipedia.org/wiki/Server_Message_Block) (also known as SMB) for file manipulation and either [WinRM](http://en.wikipedia.org/wiki/WS-Management) or [Telnet](http://en.wikipedia.org/wiki/Telnet) for process execution.

The built-in file sharing capabilities of Microsoft Windows are based on CIFS and are therefore available and enabled by default, so you should not need to install new software. However, you might need to enable and configure some services.

WinRM is available on Windows Server 2008 and up. XL Deploy supports basic authentication for local accounts and Kerberos authentication for domain accounts. [Overthere](https://github.com/xebialabs/overthere) has a built-in WinRM library that can be used from all operating systems by setting the **Connection Type** property on the `overthere.CifsHost` CI to WINRM_INTERNAL.

When connecting from a host that runs Windows, or when using a `winrs` proxy that runs Windows, the native WinRM capabilities of Windows (that is, the `winrs` command) can be used by setting the **Connection Type** property to WINRM_NATIVE.

A Telnet Server is available on all Windows Server versions, although it might not be enabled.

## Using administrative shares with CIFS

By default, XL Deploy will access [administrative shares](http://en.wikipedia.org/wiki/Administrative_share) on the remote host. These shares are only accessible for users that have administrator privileges on the remote host.

You can use Windows-style paths such as `C:\Program Files\IBM\WebSphere\AppServer`; XL Deploy will translate these to SMB URLs that use administrative shares.

For example, if you provide the path `C:\IBM\WebSphere\profiles\Dmgr01` on an `overthere.CifsHost` CI called `windows1`, XL Deploy will translate it to the SMB path `\\windows1\C$\IBM\WebSphere\profiles\Dmgr01` (which is equivalent to the [JCIFS](https://jcifs.samba.org/) URL `smb://windows1/C$/IBM/WebSphere/profiles/Dmgr01`). However, an administrative share such as `C$` can only be accessed by users with administrator privileges.

If you want to access the remote host with an account that does *not* have administrator privileges, you can specify other shares in the **Windows path to Windows share mappings** (`pathShareMapping`) property on the **CIFS** tab of the `overthere.CifsHost` CI.

For example, if you map the `WebSphere` share to `C:\IBM\WebSphere`, XL Deploy will translate the SMB path `C:\IBM\WebSphere\profiles\Dmgr01` on host `windows1` to `\\windows1\WebSphere\profiles\Dmgr01` (instead of `\\windows1\C$\IBM\WebSphere\profiles\Dmgr01`).

If you are using a plugin that will copy a file to a temporary directory (such as the [File plugin](/xl-deploy/concept/file-plugin.html) or [Generic plugin](/xl-deploy/concept/generic-plugin.html)), you should either:

* Add the directory to the mapping
* Set the hidden property `copyDirectlyToTargetPath` to "true" (in this case, the file will be copied directory to the target directory)

Note that the user identified by the **Username** (`username`) property on the **Common** tab must have access to the shares that you specify, as well as to the underlying directories and files.
