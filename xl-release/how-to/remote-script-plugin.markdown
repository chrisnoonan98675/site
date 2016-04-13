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

The XL Release Remote Script plugin allows XL Release to execute commands on remote hosts. It does so by using the [Overthere](https://github.com/xebialabs/overthere) framework, Java library for manipulating files and executing processes on remote hosts.

The Remote Script plugin includes four task types:

* **Remote Script: Unix**: Execute a shell script on a Unix host via SSH
* **Remote Script: Windows**: Execute a batch script on a Microsoft Windows host via WinRM
* **Remote Script: Windows (SSH)**: Execute a shell script on a Microsoft Windows host via SFTP
* **Remote Script: z/OS**: Execute a shell script on z/OS

## Features

* Execute shell scripts on Unix, z/OS or Microsoft Windows (via Cygwin or WinSSHD) hosts
* Execute batch scripts on Microsoft Windows hosts

## Common properties

The following properties are common to all Remote Script task types:

#### Remote script execution details
* **Script**: The shell script to execute on the remote host (required)
* **Remote Path**: The path on the remote host where the script should be executed (required)
* **Temporary Directory Path**: Where to store temp files. This directory will be removed on connection close

#### Remote host connection
* **Jumpstation**: Which of the configured jumpstations to use, if any. See [Configuring an SSH Jumpstation](configure-a-jumpstation.html) for details.
* **Address** and **Port**: Address and SSH port (or Telnet or WinRM port for Windows) of the remote host (required)
* **Connection type**: The SSH connection type to use:
    - For **UNIX** this can be one of: SCP, SFTP, SU, SUDO, INTERACTIVE_SUDO, SFTP_CYGWIN, SFTP_WINSSHD.
    - For **Windows** this can be one of: TELNET, WINRM_INTERNAL, WINRM_NATIVE
    - For **Windows (SSH)** this can be one of:  SFTP_CYGWIN, SFTP_WINSSHD
    - For **z/OS** this option is not present, and SFTP will be used (unless overridden in `deployit-defaults.properties`, setting `remoteScript.Zos.connectionType`)

#### Command output
The following output properties are available:

* **Output**: The variable in which the standard output will be stored
* **Err**: The variable in which the error output will be stored


## Unix remote script

The following properties are available in the **Remote Script: Unix**, **Remote Script: Windows (SSH)** and **Remote Script: z/OS** task types:

#### Credentials
* **Username**: The Unix host user log-in ID (required)
* **Password**: The Unix host user password -or-
* **Private key**: The private key to use (verbatim) for authentication -or-
* **Private key file**, and **Passphrase**: The file containing a private key, and an (optional) passphrase for the key it contains

#### Privilege Elevation
* **SUDO username**: Access files and commands after `sudo`'ing to this user
* **SU username** and **SU password**: Access files and commands after `su`'ing to this user
* **Sudo**: (UNIX only; deprecated) Checking this box effectively means setting SUDO username to root. This option is provided only for backwards compatibility. It is deprecated since 5.0.0 and will be removed in future.

## Windows remote script

The following properties are available in the **Remote Script: Windows** task type:

#### Credentials
* **Username** and **Password**: The Windows host user log-in ID and password
* **Allow credential delegation**: If checked, allow the user's credentials to be used to access e.g. remote shares. (Only on WINRM_NATIVE connection type)

#### Connectivity
* **CIFS Port**: The port on which the CIFS (SMB) server runs
* **Windows path to Windows share mappings**: Mapping from Windows paths to Windows share names, e.g. C:\IBM\WebSphere -> WebSphereShare
* **Timeout**: The WinRM timeout in [XML schema duration format](http://www.w3.org/TR/xmlschema-2/#isoformats); the default value is `PT60.000S`
* **Enable HTTPS for WinRM**: Check this if the remote Windows host supports encypted connections
* **Winrs proxy**: Where to run the `winrs` command (only on WINRM_NATIVE connection type)


## Unleashing the full power of Overthere
XL Release makes use of the powerful Overthere library, which comes with a multitude of connection options. To keep things manageable, XL Release exposes only the most common ones in the UI. For the full set, please refer to the [Overthere library documentation](https://github.com/xebialabs/overthere/blob/master/README.md) Default values of non-exposed options can be tweaked in `conf/deployit-defaults.properties`.
 
If you need to use a different setting per task for a particular option, you can create a type modification in your `synthetic.xml` for any of these task types where you add the desired Overthere connection option as a property on the task. These task properties will automatically be used as connection options for Overthere if the name matches. E.g., to make the connection timeout configurable on each task, you'd add this in your `synthetic.xml`:


     <type-modification type="remoteScript.WindowsSsh">
         <property name="connectionTimeoutMillis" category="input" kind="integer" default="120000"
                   description="Number of milliseconds Overthere waits for a connection to a remote host to be established"/>
     </type-modification>

