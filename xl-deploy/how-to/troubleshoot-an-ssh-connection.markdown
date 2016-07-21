---
title: Troubleshoot an SSH connection
categories:
- xl-deploy
subject:
- Remoting
tags:
- connectivity
- remoting
- ssh
- overthere
weight: 346
---

These are configuration errors that can occur when using XL Deploy with SSH.

## Cannot start a process on an SSH server because the server disconnects immediately

If the terminal type requested using the `allocatePty` property or the `allocateDefaultPty` property is not recognized by the SSH server, the connection will be dropped. Specifically, the `dummy` terminal type configured by `allocateDefaultPty` property, will cause OpenSSH on AIX and WinSSHD to drop the connection. Try a safe terminal type such as `vt220` instead.

To verify the behavior of your SSH server with respect to pty allocation, you can manually execute the `ssh` command with the `-T` (disable pty allocation) or `-t` (force pty allocation) flags.

## Command executed using SUDO or INTERACTIVE_SUDO fails with the message `sudo: sorry, you must have a tty to run sudo`

The `sudo` command requires a `tty` to run. Set the `allocatePty` property or the `allocateDefaultPty` property to ask the SSH server allocate a pty.

## Command executed using SUDO or INTERACTIVE_SUDO appears to hang

This may be caused by the `sudo` command waiting for the user to enter his password to confirm his identity. There are several ways to solve this:

* Use the [`NOPASSWD`](http://www.gratisoft.us/sudo/sudoers.man.html#nopasswd_and_passwd) tag in your `/etc/sudoers` file, or
* Use the INTERACTIVE_SUDO connection type instead of the SUDO connection type

If you are already using the INTERACTIVE_SUDO connection type and you still get this error, please verify that you have correctly configured the `sudoPasswordPromptRegex` property. If you have trouble determining the proper value for the `sudoPasswordPromptRegex` property, set the log level for the `com.xebialabs.overthere.ssh.SshInteractiveSudoPasswordHandlingStream` category to `TRACE` and examine the output.
