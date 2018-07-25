---
title: Choose a host type and connection type
subject:
Remoting
categories:
xl-platform
xl-deploy
xl-release
tags:
connectivity
remoting
overthere
weight: 337
---

The [remoting functionality](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release uses the [Overthere framework](https://github.com/xebialabs/overthere) to manipulate files and execute commands on remote hosts. To determine which type of Overthere host to create and which connection type to use, follow these guidelines.

## Is the remote host the XL Deploy or XL Release server itself?

* Yes &#8594; Create a local host (`overthere.LocalHost`) configuration item (CI). Done.

## Is the remote host a Unix host?

* Yes &#8594; Create an SSH host (`overthere.SshHost`) CI, set the `os` property to UNIX, and answer the questions below:
	* Can you connect to the target system with the user that has the privileges to manipulate the files and execute the commands required?
		* Yes &#8594; Use the SCP connection type. Done.
	* Do you need to log in as one user and then use `sudo` (or a similar command like `sx`, but not `su`) to execute commands and manipulate files as a different user?
		* Yes &#8594; Answer the questions below:
			* Are the commands you need configured with `NOPASSWD` in the `/etc/sudoers` configuration file? In other words, are you not prompted for a password when executing `sudo COMMAND`?
				* Yes &#8594; Use the SUDO connection type. Done.
				* No &#8594; Use the INTERACTIVE_SUDO connection type. Done.
	* Do you need to log in as one user and then use `su` to execute commands and manipulate files as a different user?
		* Yes &#8594; Use the SU connection type. Done.

## Is the remote host a Microsoft Windows host?

* Yes &#8594; Answer the questions below.
	* Have you configured WinRM on the remote host?
		* Yes &#8594; Create an SMB host (`overthere.SmbHost`) CI and answer the questions below:
			* Is the XL Deploy or XL Release server running on a Windows host?
				* Yes &#8594; Use the WINRM_NATIVE connection type. Done.
				* No &#8594; Use the WINRM_INTERNAL connection type. Done.
	* Have you installed WinSSHD on the remote host?
		* Yes &#8594; Create an SSH host (`overthere.SshHost`) CI, set the `os` property to WINDOWS, and use the SFTP_WINSSHD connection type. Done.
	* Have you installed OpenSSH (that is, Cygwin or Copssh) on the remote host?
		* Yes &#8594; Create an SSH host (`overthere.SshHost`) CI, set the `os` property to WINDOWS, and use the SFTP_CYGWIN connection type. Done.
	* Have you configured Telnet on the remote host?
		* Yes &#8594; Create an SMB host (`overthere.SmbHost`) CI and use the TELNET connection type. Done.
	* If you have not yet configured WinRM, SSH, or Telnet, please configure WinRM and start from the top.

**Note:** The SMB protocol is supported in XL Deploy 5.5.6 and later. In earlier versions of XL Deploy, you can use a CIFS host instead (`overthere.CifsHost`). SMB is not supported in XL Release.

## Is the remote host a z/OS host?

* Yes &#8594; Create an SSH (`overthere.SshHost`) CI, set the `os` property to ZOS, and use the SFTP connection type. Done.
