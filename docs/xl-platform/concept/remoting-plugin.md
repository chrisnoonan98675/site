---
title: Introduction to remoting functionality
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
weight: 335
---

XL Deploy and XL Release both have remoting functionality that enables them to manipulate files and execute commands on remote hosts. This functionality is provided by a plugin called the Remoting plugin, which uses the open-source Java framework [Overthere](https://github.com/xebialabs/overthere).

## Features

The remoting feature in XL Deploy and XL Release:

* Supports SSH for connectivity to Unix, Microsoft Windows, and z/OS hosts
* Supports CIFS, Telnet, and WinRM for connectivity to Windows hosts
* Allow SSH jumpstations and HTTP proxies to be used to access hosts to which a direct network connection is not possible; CIFS, Telnet, and WinRM can be tunneled through an SSH jumpstation as well
* Implements all connection methods internally, so no external dependencies are required; an exception is the WINRM_NATIVE connection type, which uses the Windows `winrs` command, but you can use a `winrs` proxy for this connection type when XL Deploy or XL Release runs on a Unix host

## Host types

The remoting feature supports the following protocols for accessing target hosts:

{:.table .table-striped}
| Host type | Description |
| --------| ----------|
| localhost | Connects to the local host (the host that runs XL Deploy or XL Release). |
| SSH | Connects to a Unix, Microsoft Windows, or z/OS host using the [SSH protocol](http://en.wikipedia.org/wiki/Secure_Shell). For Windows, OpenSSH on [Cygwin](http://www.cygwin.com) (such as [Copssh](https://www.itefix.no/i2/copssh)) and [WinSSHD](http://www.bitvise.com/winsshd) are supported. |
| CIFS | Connects to a Windows host using the [CIFS protocol](http://en.wikipedia.org/wiki/Server_Message_Block) for file manipulation and [WinRM](http://en.wikipedia.org/wiki/WS-Management) or [Telnet](http://en.wikipedia.org/wiki/Telnet) for process execution. This protocol is not supported for Unix or z/OS hosts. |
| SMB | Connects to a Windows host using the [SMB protocol](http://en.wikipedia.org/wiki/Server_Message_Block) file manipulation and WinRM or Telnet for process execution. This protocol is not supported for Unix or z/OS hosts. This protocol is available in XL Deploy 5.5.6 and later. It is not available in XL Release. |

For detailed information about host CI types, refer to the [Remoting plugin reference for XL Deploy](/xl-deploy/latest/remotingPluginManual.html).

## Connection types

For SSH hosts, CIFS hosts, and SMB hosts, you can select a connection type that determines how XL Deploy or XL Release connects to the host.

### SSH connection types

The following connection types are available for SSH hosts:

{:.table .table-striped}
| Connection type | Description |
| --------------| ----------|
| SFTP | Uses [SFTP](http://en.wikipedia.org/wiki/SSH_File_Transfer_Protocol) to transfer files to a Unix host or a z/OS host. Requires the SFTP subsystem to be enabled, which is the default for most SSH servers. This is the only connection type available for z/OS hosts. |
| SFTP_CYGWIN | Uses SFTP to transfer files to a Windows host running OpenSSH on Cygwin. This connection type can only be used for Windows hosts. |
| SFTP_WINSSHD | Uses SFTP to transfer files to a Windows host running WinSSHD. This connection type can only be used for Windows hosts. |
| SCP | Uses SCP to transfer files to a Unix host. The is the fastest connection type available for Unix hosts. |
| SUDO | Like the SCP connection type, but uses the [`sudo`](http://en.wikipedia.org/wiki/Sudo) command to execute commands and to copy files from and to their actual locations. Requires all commands to be executed to have been configured with `NOPASSWD` in the `/etc/sudoers` configuration file. If this connection type is selected, the `sudoUsername` property should be set to specify the user that _does_ have the necessary permissions. |
| INTERACTIVE_SUDO | Like the SUDO connection type, but does not require the `NOPASSWD` option to have been configured for all commands. It enables detection of the password prompt that is shown by the `sudo` command when the login user (`username`) tries to execute a command as the privileged user (`sudoUsername`) when that command has not been configured with `NOPASSWD`, and causes the password of `username` to be sent in reply.<br /><br />**Note**: Because the password of the login user (`username`) is needed to answer this prompt, this connection type is incompatible with the `privateKeyFile` property that can be used to authenticate with a private key file. |

For detailed information about connection types, refer to the [Remoting plugin reference for XL Deploy](/xl-deploy/latest/remotingPluginManual.html).

### CIFS connection types

The following connection types are available for CIFS and SMB hosts:

{:.table .table-striped}
| Connection type | Description |
| --------------| ----------|
| WINRM_INTERNAL | Uses <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/aa384426(v=vs.85).aspx">WinRM</a> over HTTP(S) to execute remote commands (a Java implementation of WinRM that is internal to XL Deploy and XL Release is used). The `port` property specifies the Telnet port to which to connect. The default value is 5985 for HTTP and 5986 for HTTPS. |
| WINRM_NATIVE | Like WINRM_INTERNAL, but uses the native Windows implementation of WinRM (the `winrs` command). If the XL Deploy or XL Release server is not running on a Windows host, a `winrs` proxy must be configured. |
| TELNET | Uses Telnet to execute remote commands. The `port` property specifies the Telnet port to which to connect. The default value is 23. |

## Exposing additional Overthere properties

Most of the Overthere connection properties defined in the [Overthere documentation](https://github.com/xebialabs/overthere/blob/master/README.md) are available as regular properties or as hidden properties on the `overthere.SshHost`, `overthere.CifsHost`, and `overthere.SmbHost` configuration item (CI) types. If you need access to additional properties, you can create a type modification in the `SERVER_HOME/ext/synthetic.xml` file like this:

    <type-modification type="overthere.SshHost">
        <property name="listFilesCommand" hidden="true" default="/bin/ls -a1 {0}" />
        <property name="getFileInfoCommand" hidden="true" default="/bin/ls -ld {0}" />
    </type-modification>
