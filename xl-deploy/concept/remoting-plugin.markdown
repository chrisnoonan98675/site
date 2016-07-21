---
title: Introduction to the XL Deploy Remoting plugin
subject:
- Remoting
categories:
- xl-deploy
tags:
- plugin
- connectivity
- remoting
weight: 335
---

The Remoting plugin allows XL Deploy to manipulate files and execute commands on remote hosts. It does so by using the Overthere framework. Overthere is a Java library to manipulate files and execute processes on remote hosts. See the [Overthere Github repository](https://github.com/xebialabs/overthere) for more information.

## Features

* SSH for connectivity to Unix, Microsoft Windows, and z/OS hosts
* CIFS, Telnet, and WinRM for connectivity to Windows hosts
* Allow SSH jumpstations and HTTP proxies to be used to access hosts to which a direct network connection is not possible (in addition to SSH, CIFS, Telnet and WinRM can be tunneled through an SSH jumpstation)
* All connection methods are implemented internally in XL Deploy itself, so no external dependencies are required (an exception is the `WINRM_NATIVE` connection type, which uses the Windows `winrs` command, but you can use a `winrs` proxy for this connection type when XL Deploy runs on a Unix host)

## Requirements

### Remoting plugin 3.9.x

* Deployit 3.5+
* Other XL Deploy plugins: None

### Remoting plugin 4.0.x

* XL Deploy 4.0+
* Other XL Deploy plugins: None

### Remoting plugin 4.5.x

* XL Deploy 4.0+
* Other XL Deploy plugins: None

## Host types

The Remoting plugin has the following three CI types that define the protocol that is used to access the target host:

{:.table .table-striped}
| Host type | Description |
| --------- | ----------- |
| `overthere.LocalHost` | Connects to the local host (the host that runs XL Deploy). |
| `overthere.SshHost` | Connects to a Unix, Windows, or z/OS host using the [SSH protocol](http://en.wikipedia.org/wiki/Secure_Shell). For Windows, OpenSSH on [Cygwin](http://www.cygwin.com) (such as [Copssh](https://www.itefix.no/i2/copssh)) and [WinSSHD](http://www.bitvise.com/winsshd) are supported. |
| `overthere.CifsHost` | Connects to a Windows host using the [CIFS protocol](http://en.wikipedia.org/wiki/Server_Message_Block), also known as SMB, for file manipulation and, depending on the settings, using either [WinRM](http://en.wikipedia.org/wiki/WS-Management) or [Telnet](http://en.wikipedia.org/wiki/Telnet) for process execution. This protocol is not supported for Unix or z/OS hosts. |

## Connection types

The `overthere.SshHost` and `overthere.CifsHost` types each have a `connectionType` property that defines more precisely how to connect to the remote host.

### SSH connection types

The `connectionType` property of an `overthere.SshHost` defines how files are transferred and how commands are executed on the remote host. Possible values are:

{:.table .table-striped}
| `connectionType` | Description |
| ---------------- | ----------- |
| `SFTP` | Uses [SFTP](http://en.wikipedia.org/wiki/SSH_File_Transfer_Protocol) to transfer files to a Unix host or a z/OS host. Requires the SFTP subsystem to be enabled, which is the default for most SSH servers. This is the only connection type available for z/OS hosts. |
| `SFTP_CYGWIN` | Uses SFTP to transfer files to a Windows host running OpenSSH on Cygwin. This connection type can only be used for Windows hosts. |
| `SFTP_WINSSHD` | Uses SFTP to transfer files to a Windows host running WinSSHD. This connection type can only be used for Windows hosts. |
| `SCP` | Uses SCP to transfer files to a Unix host. The is the fastest connection type available for Unix hosts. |
| `SUDO` | Like the `SCP` connection type, but uses the [sudo](http://en.wikipedia.org/wiki/Sudo) command to execute commands and to copy files from and to their actual locations. Requires all commands to be executed to have been configured with `NOPASSWD` in the `/etc/sudoers` configuration file. If this connection type is selected, the `sudoUsername` property should be set to specify the user that _does_ have the necessary permissions. |
| `INTERACTIVE_SUDO` | Like the `SUDO` connection type, but does not require the `NOPASSWD` option to have been configured for all commands. It enables detection of the password prompt that is shown by the `sudo` command when the login user (`username`) tries to execute a commands as the privileged user (`sudoUsername`) when that command has not been configured with `NOPASSWD`, and causes the password of `username` to be sent in reply. |

**Note**: Because the password of the login user (`username`) is needed to answer this prompt, this connection type is incompatible with the `privateKeyFile` property that can be used to authenticate with a private key file.

### CIFS connection types (includes WinRM and Telnet)

The `connectionType` property of an `overthere.CifsHost` defines how commands are executed on the remote host. Files are always transferred using CIFS. Possibles values are:

{:.table .table-striped}
| `connectionType` | Description |
| ---------------- | ----------- |
| `WINRM_INTERNAL` | Uses WinRM over HTTP(S) to execute remote commands. The `port` property specifies the Telnet port to connect to. The default value is `5985` for HTTP and `5986` for HTTPS. A Java implementation of WinRM internal to XL Deploy is used. |
| `WINRM_NATIVE` | Like `WINRM_INTERNAL` but uses the native Windows implementation of WinRM (the `winrs` command). If the XL Deploy server is not running on a Windows host, a `winrs` proxy must be configured. |
| `TELNET` | Uses Telnet to execute remote commands. The `port` property specifies the Telnet port to connect to. The default value is `23`. |

All CIFS connection types can only be used for Windows hosts.
