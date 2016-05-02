---
title: Using the XL Release Remote Script plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- script
- task
- connectivity
---

The XL Release Remote Script plugin allows XL Release to execute commands on remote hosts. It does so by using the [Overthere](https://github.com/xebialabs/overthere) framework, a Java library for manipulating files and executing processes on remote hosts.

The Remote Script plugin includes four task types:

* **Remote Script: Unix**: Execute a shell script on a Unix host via SSH
* **Remote Script: Windows**: Execute a batch script on a Microsoft Windows host via WinRM
* **Remote Script: Windows (SSH)**: Execute a shell script on a Microsoft Windows host via SFTP
* **Remote Script: z/OS**: Execute a shell script on z/OS

## Features

* Execute shell scripts on Unix, z/OS, or Microsoft Windows (via Cygwin or WinSSHD) hosts
* Execute batch scripts on Microsoft Windows hosts. Note: All scripts will be interpreted as batch files; PowerShell is currently not supported.


## Common properties

The following properties are common to all Remote Script task types.

#### Remote script execution details

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| Script | The shell script to execute on the remote host (required) |
| Remote Path | The path on the remote host where the script should be executed (required) |
| Temporary Directory Path | Where to store temporary files; this directory will be removed on connection close |

#### Remote host connection

<table class="table table-striped">
<thead>
<tr>
<th>Property</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td>Jumpstation</td>
<td>The <a href="/xl-release/how-to/configure-a-jumpstation.html">SHH jumpstation or HTTP proxy</a> to use</td>
</tr>
<tr>
<td>Address and Port</td>
<td>Address and SSH port (or Telnet or WinRM port for Windows) of the remote host (required)</td>
</tr>
<tr>
<td>Connection type</td>
<td>The SSH connection type to use:<br /><br />
<ul>
<li>UNIX: SCP, SFTP, SU, SUDO, INTERACTIVE_SUDO, SFTP_CYGWIN, or SFTP_WINSSHD</li>
<li>Windows: TELNET, WINRM_INTERNAL, or WINRM_NATIVE</li>
<li>Windows (SSH): SFTP_CYGWIN, or SFTP_WINSSHD</li>
<li>z/OS: This option is not present, and SFTP will be used (unless overridden in the <code>remoteScript.Zos.connectionType</code> setting in the <code>deployit-defaults.properties</code> file)</li>
</ul>
<b>Tip:</b> The easiest way to connect to a Windows host is to use the WINRM_INTERNAL connection type. This requires that the XL Release server is also running on Windows.

</td>
</tr>
</tbody>
</table>


#### Command output

The following output properties are available:

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| Output | The variable in which the standard output will be stored |
| Err | The variable in which the error output will be stored |

## Unix remote script

The following properties are available in the **Remote Script: Unix**, **Remote Script: Windows (SSH)**, and **Remote Script: z/OS** task types.

#### Credentials

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| Username | The Unix host user log-in ID (required) |
| Password | The Unix host user password |
| Private key | The private key to use (verbatim) for authentication |
| Private key file and Passphrase | The file containing a private key, and an optional passphrase for the key it contains |

You can use a password, a private key, or a private key file and passphrase.

#### Privilege Elevation

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| SUDO username | Access files and commands after `sudo`'ing to this user |
| SU username and SU password | Access files and commands after `su`'ing to this user |
| Sudo (UNIX only; deprecated) | Checking this box effectively means setting the SUDO username to `root`. This option is provided only for backward compatibility. It is deprecated since XL Release 5.0.0 and will be removed in future. |

## Windows remote script

The following properties are available in the **Remote Script: Windows** task type.

#### Credentials

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| Username and Password | The Windows host user log-in ID and password |
| Allow credential delegation | If checked, allow the user's credentials to be used to access, for example, remote shares (only on WINRM_NATIVE connection type) |

#### Connectivity

{:.table .table-striped}
| Property | Description |
| -------- | ----------- |
| CIFS Port | The port on which the CIFS (SMB) server runs |
| Windows path to Windows share mappings | Mapping from Windows paths to Windows share names; for example, `C:\IBM\WebSphere` &#8594; `WebSphereShare` |
| Timeout | The WinRM timeout in [XML schema duration format](http://www.w3.org/TR/xmlschema-2/#isoformats); the default value is `PT60.000S` |
| Enable HTTPS for WinRM | Check this if the remote Windows host supports encrypted connections |

## Using advanced Overthere functionality

XL Release uses the Overthere framework, which includes several connection options. While the most common options are available in the XL Release GUI, you can use other options as described in the [Overthere documentation](https://github.com/xebialabs/overthere/blob/master/README.md).

You can change the default values of options that are not exposed in the GUI in the `XLRELEASE_HOME/conf/deployit-defaults.properties` file.

If you need to use a different setting for a particular option per task, you can create a type modification in the `XLRELEASE_HOME/conf/synthetic.xml` file for the task types. In the modification, you can add the desired Overthere connection option as a property on the task. These task properties will automatically be used as connection options for Overthere if the name matches.

For example, to make the connection timeout configurable on each task, add this to `synthetic.xml`:

     <type-modification type="remoteScript.WindowsSsh">
         <property name="connectionTimeoutMillis" category="input" kind="integer" default="120000"
                   description="Number of milliseconds Overthere waits for a connection to a remote host to be established"/>
     </type-modification>
