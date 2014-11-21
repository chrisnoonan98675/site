---
title: Set up SSH in XL Deploy and on a target host
categories:
- xl-deploy
tags:
- remoting
- connectivity
- ssh
- overthere
---

To connect to a remote host using the SSH protocol, you must to install an SSH server on that remote host.

## Unix remote host

For Unix platforms, [OpenSSH](http://www.openssh.com/) is recommended. It is included in all Linux distributions and most other Unix-based systems.

## Microsoft Windows remote host 

For Microsoft Windows platforms, XL Deploy supports these SSH servers:

* OpenSSH on [Cygwin](http://www.cygwin.com/). [Copssh](http://www.itefix.no/i2/copssh) is recommended as a convenient packaging of OpenSSH and Cygwin. It is a free source download, but since 22 November 2011, the binary installers are a paid solution.
* [WinSSHD](http://www.bitvise.com/winsshd) is a commercial SSH server.

**Note:** The SFTP, SCP, SUDO and INTERACTIVE_SUDO connection types are only available for Unix hosts. To use SSH with z/OS hosts, use the SFTP connection type. To use SSH with Windows hosts, choose the SFTP_CYGWIN or the SFTP_WINSSHD connection type.

## SFTP connection type

To use the SFTP connection type, ensure that SFTP is enabled in the SSH server. This is enabled by default in most SSH servers.

## SFTP_CYGWIN connection type

To use the SFTP_CYGWIN connection type, install [Copssh](http://www.itefix.no/i2/copssh) on the Windows host. In the Copssh control panel:

1. Add the users that XL Deploy should connect as
1. Select **Linux shell and Sftp** from the **shell** list
1. Select **Password authentication** and/or **Public key authentication**, depending on the authentication method you want to use

**Note:** XL Deploy will take care of the translation from Windows style paths (such as `C:\Program Files\IBM\WebSphere\AppServer`) to Cygwin-style paths (such as `/cygdrive/C/Program Files/IBM/WebSphere/AppServer`), so that your code can use Windows-style paths.

## SFTP_WINSSHD connection type

To use the SFTP_WINSSHD connection type, install WinSSHD on the Windows host. In the Easy WinSSHD Settings control panel:

1. Add the users that XL Deploy should connect as
1. Select **Login allowed**
1. Select **Allow full access in the Virtual filesystem layout**

    Alternatively, you can select **Allow login to any Windows account** to allow access to all Windows accounts.

**Note:** XL Deploy will take care of the translation from Windows style paths (such as `C:\Program Files\IBM\WebSphere\AppServer`) to WinSSHD-style paths (such as `/C/Program Files/IBM/WebSphere/AppServer`), so that your code can use Windows-style paths.

## SUDO and INTERACTIVE_SUDO connection types

To use the SUDO connection type, the `/etc/sudoers` configuration must be set up so that the user configured with the **username** property can execute the commands below as the user configured with the **sudoUsername** property. The arguments passed to these commands depend on the exact usage of the XL Deploy connection. Check the INFO messages on the `com.xebialabs.overthere.ssh.SshConnection` category to see the commands that are executed.

* `chmod`
* `cp`
* `ls`
* `mkdir`
* `mv`
* `rm`
* `rmdir`
* `tar`
* Any other command that might be invoked by a middleware plugin; for example, `wsadmin.sh` or `wlst.sh`

You must configure these commands with the `NOPASSWD` setting in the `/etc/sudoers` file. Otherwise, you must use the INTERACTIVE_SUDO connection type.

When the INTERACTIVE_SUDO connection type is used, every line of the output will be matched against the regular expression configured with the `sudoPasswordPromptRegex` property. If a match is found, the value of the password property is sent.
