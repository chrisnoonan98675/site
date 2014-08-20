---
layout: default
title: Choosing a connection type
tags: connectivity ssh infrastructure overthere
categories: connectivity
---

### Which host type and connection type to choose? ###

To determine what kind of host to create and what connection type to choose, please follow these guidelines.

* Is the remote host the XL Deploy server itself?
	* Yes -> Create an **overthere.LocalHost** CI. Done.
* Is the remote host a Unix host?
	* Yes -> Create an **overthere.SshHost** CI, set the **os** to `UNIX`, and answer the questions below:
	* Can you connect to the target system with the user that has the privileges to manipulate the files and execute the commands required?
		* Yes -> Use the **SCP** connection type. Done.
	* Do you need to log in as one user and then use `sudo` (or a similar command like `sx` but not `su`) to execute commands and manipulate files as a different user?
		* Yes -> Answer the questions below:
		* Are the commands you need configured with `NOPASSWD` in the `/etc/sudoers` configuration file? In other words, are you not prompted for a password when executing `sudo COMMAND`?
			* Yes -> Use the **SUDO** connection type. Done.
			* No -> Use the **INTERACTIVE_SUDO** connection type. Done.
	* Do you need to log in as one user and then use `su` to execute commands and manipulate files as a different user?
		* Yes -> Use the **SU** connection type. Done.
* Is the remote host a Windows host?
	* Yes -> Answer the questions below.
	* Have you configured WinRM on the remote host?
		* Yes -> Create an **overthere.CifsHost** CI and answer the questions below:
			* Is the XL Deploy server running on a Windows host?
				* Yes -> Use the **WINRM_NATIVE** connection type. Done.
				* No -> Use the **WINRM_INTERNAL** connection type. Done.
	* Have you installed WinSSHD on the remote host?
		* Yes -> Create an **overthere.SshHost** CI, set the **os** to `WINDOWS`, and use the **SFTP_WINSSHD** connection type. Done.
	* Have you installed OpenSSH (e.g. Cygwin or Copssh) on the remote host?
		* Yes -> Create an **overthere.SshHost** CI, set the **os** to `WINDOWS`, and use the **SFTP_CYGWIN** connection type. Done.
	* Have you configured Telnet on the remote host?
		* Yes -> Create an **overthere.CifsHost** CI and use the **TELNET** connection type. Done.
	* If you have not yet configured either WinRM, SSH or Telnet, please configure WinRM and start from the top.
* Is the remote host a z/OS host?
	* Yes -> Create an **overthere.SshHost** CI, set the **os** to `ZOS`, and use the **SFTP** connection type. Done.

{% include breadcrumbs.html %}