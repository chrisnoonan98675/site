---
title: Using SSH
subject:
- Remoting
categories:
- xl-deploy
- xl-release
tags:
- remoting
- connectivity
- ssh
- overthere
weight: 339
---

The [Remoting plugin](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release uses the [Overthere framework](https://github.com/xebialabs/overthere) to manipulate files and execute commands on remote hosts. The Remoting plugin supports the [SSH protocol](http://en.wikipedia.org/wiki/Secure_Shell) for connecting to a Unix, Microsoft Windows, or z/OS host. To connect to a remote host using the SSH protocol, you must to install an SSH server on that remote host.

## Unix remote host

For Unix platforms, [OpenSSH](http://www.openssh.com/) is recommended. It is included in all Linux distributions and most other Unix-based systems.

## Microsoft Windows remote host

For Microsoft Windows platforms, XL Deploy supports these SSH servers:

* OpenSSH on [Cygwin](http://www.cygwin.com/). [Copssh](http://www.itefix.no/i2/copssh) is recommended as a convenient packaging of OpenSSH and Cygwin. It is a free source download, but since 22 November 2011, the binary installers are a paid solution.
* [WinSSHD](http://www.bitvise.com/winsshd) is a commercial SSH server.

**Note:** The SFTP, SCP, SUDO, and INTERACTIVE_SUDO connection types are only available for Unix hosts. To use SSH with z/OS hosts, use the SFTP connection type. To use SSH with Windows hosts, use the SFTP_CYGWIN or the SFTP_WINSSHD connection type.

## SSH compatibility

The Remoting plugin uses the [`sshj`](https://github.com/shikhar/sshj) library for SSH and supports all algorithms and formats that are supported by that library:

* Ciphers: `aes{128,192,256}-{cbc,ctr}`, `blowfish-cbc`, `3des-cbc`
* Key Exchange methods: `diffie-hellman-group1-sha1`, `diffie-hellman-group14-sha1`
* Signature formats: `ssh-rsa`, `ssh-dss`
* MAC algorithms: `hmac-md5`, `hmac-md5-96`, `hmac-sha1`, `hmac-sha1-96`
* Compression algorithms: `zlib` and `zlib@openssh.com` (delayed `zlib`)
* Private Key file formats: `pkcs8` encoded (the format used by [OpenSSH](http://www.openssh.com/))

## SFTP connection type

To use the SFTP connection type, ensure that SFTP is enabled in the SSH server. This is enabled by default in most SSH servers.

## SFTP_CYGWIN connection type

To use the SFTP_CYGWIN connection type, install [Copssh](http://www.itefix.no/i2/copssh) on the Windows host. In the Copssh control panel:

1. Add the users that XL Deploy or XL Release should connect as.
1. Select **Linux shell and Sftp** from the **shell** list.
1. Select **Password authentication** and/or **Public key authentication**, depending on the authentication method you want to use.

**Note:** Windows-style paths such as `C:\Program Files\IBM\WebSphere\AppServer` will be translated to Cygwin-style paths such as `/cygdrive/C/Program Files/IBM/WebSphere/AppServer`, so that you can use Windows-style paths in XL Deploy or XL Release.

## SFTP_WINSSHD connection type

To use the SFTP_WINSSHD connection type, install WinSSHD on the Windows host. In the Easy WinSSHD Settings control panel:

1. Add the users that XL Deploy or XL Release should connect as.
1. Select **Login allowed**.
1. Select **Allow full access in the Virtual filesystem layout**. Alternatively, you can select **Allow login to any Windows account** to allow access to all Windows accounts.

**Note:** Windows-style paths such as `C:\Program Files\IBM\WebSphere\AppServer` will be translated to WinSSHD-style paths such as `/C/Program Files/IBM/WebSphere/AppServer`, so that you can use Windows-style paths in XL Deploy or XL Release.

## SUDO and INTERACTIVE_SUDO connection types

To use the SUDO connection type, set up the `/etc/sudoers` configuration so that the user who is identified by the `username` property on the SSH host CI can execute the commands below as the user who is identified by the `sudoUsername` property on the CI. The arguments passed to these commands depend on the exact usage of the XL Deploy or XL Release connection.

* `chmod`
* `cp`
* `ls`
* `mkdir`
* `mv`
* `rm`
* `rmdir`
* `tar`
* Any other command that might be invoked by a middleware plugin; for example, `wsadmin.sh` for WebSphere or `wlst.sh` for WebLogic

You must configure these commands with the `NOPASSWD` setting in the `/etc/sudoers` file. Otherwise, you must use the INTERACTIVE_SUDO connection type.

When the INTERACTIVE_SUDO connection type is used, every line of the output will be matched against the regular expression configured with the `sudoPasswordPromptRegex` property on the SSH host CI. If a match is found, the value of the password property is sent.

**Tip:** In XL Deploy, you can check the INFO messages on the `com.xebialabs.overthere.ssh.SshConnection` category to see the commands that are executed.

## Connect XL Deploy through an SSH jumpstation or HTTP proxy

When XL Deploy cannot reach a remote host directly, but that host can be reached by setting up one or more SSH tunnels, configure one or more SSH jumpstations (`overthere.SshJumpstation` CI type) as follows:

1. Create an `overthere.SshJumpStation` CI that represents a host to which XL Deploy can connect directly.
1. For each remote host that cannot be reached directly by XL Deploy, create an `overthere.Host` CI and set the jumpstation property to refer to the `overthere.SshJumpStation` CI created in step 1.

When XL Deploy creates a connection to the remote host and determines that it needs to connect through a jumpstation, and will first open a connection to that SSH jumpstation and then setup a SSH tunnel (also known as a [local port forward](https://en.wikipedia.org/wiki/Port_forwarding#Local_port_forwarding)) to the remote host.

Note that:

* SSH jumpstations can refer to other SSH jumpstations or to HTTP proxies for even more complex network setups, but cycles are not allowed.
* Because XL Deploy cannot transfer files through a jumpstation, the *Check connection* control task will fail when it attempts to verify file transfer.

## Connect XL Release through a jumpstation or HTTP proxy

When XL Release cannot reach a remote host directly, but that host can be reached via one or more SSH tunnels or HTTP proxies, you can configure these as follows:

1. Log in to XL Release as a user with the *Admin* permission.
1. Select **Settings** > **Shared configuration** from the top menu.

    **Note:** Prior to XL Release 6.0.0, go to **Settings** > **Configuration**.

1. Depending on the kind of jumpstation you want to set up:
    1. Under **Remote Script: SSH jumpstation** click **Add SSH jumpstation** -or-
    1. Under **Remote Script: HTTP proxy** click **Add HTTP proxy**
1. Enter a name for the jumpstation and provide the connection details.

SSH jumpstations can also be reached via other jumpstations for even more complex network setups, but cycles are not allowed.

When XL Deploy creates a connection to the remote host and determines that it needs to connect through a jumpstation, and will first open a connection to that SSH jumpstation and then setup a SSH tunnel (also known as a [local port forward](https://en.wikipedia.org/wiki/Port_forwarding#Local_port_forwarding)) to the remote host.

**Note:** Jumpstations are configured globally in XL Release, not per-release.
