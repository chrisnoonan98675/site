---
title: Troubleshoot an SSH connection
categories:
- xl-platform
- xl-deploy
- xl-release
subject:
- Remoting
tags:
- connectivity
- remoting
- ssh
- overthere
weight: 346
---

The [remoting functionality](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release uses the [Overthere framework](https://github.com/xebialabs/overthere) to manipulate files and execute commands on remote hosts. SSH is supported for connectivity to Unix, Microsoft Windows, and z/OS hosts. These are configuration errors that can occur when using XL Deploy or XL Release with the SSH protocol.

#### Cannot start a process on an SSH server because the server disconnects immediately

If the terminal type requested using the `allocatePty` property or the `allocateDefaultPty` property is not recognized by the SSH server, the connection will be dropped. Specifically, the `dummy` terminal type configured by `allocateDefaultPty` property, will cause OpenSSH on AIX and WinSSHD to drop the connection. Try a safe terminal type such as `vt220` instead.

To verify the behavior of your SSH server with respect to PTY allocation, you can manually execute the `ssh` command with the `-T` (disable PTY allocation) or `-t` (force PTY allocation) flags.

#### Connecting to AIX over SSH returns timeout error

When connecting over SSH to an [IBM AIX](http://www-03.ibm.com/systems/power/software/aix/) system, you may see a `ConnectionException: Timeout expired` error. To prevent this, set the `allocatePty` default to an empty value (null). If you do not want to change the default for all configuration items (CIs) of the `overthere.SshHost` type, [create a custom CI type](/xl-deploy/how-to/define-a-new-ci-type.html) to use for connections to AIX. For example:

{% highlight xml %}
<type type="overthere.AixSshHost" extends="overthere.SshHost">
    <property name="allocatePty" kind="string" hidden="false" required="false" default="" category="Advanced" />
</type>
{% endhighlight %}

#### Command executed using SUDO or INTERACTIVE_SUDO fails with the message `sudo: sorry, you must have a tty to run sudo`

The `sudo` command requires a `tty` to run. Set the `allocatePty` property or the `allocateDefaultPty` property to ask the SSH server allocate a PTY.

#### Command executed using SUDO or INTERACTIVE_SUDO appears to hang

This may be caused by the `sudo` command waiting for the user to enter his password to confirm his identity. There are several ways to solve this:

* Use the [`NOPASSWD`](http://www.gratisoft.us/sudo/sudoers.man.html#nopasswd_and_passwd) tag in your `/etc/sudoers` file, or
* Use the INTERACTIVE_SUDO connection type instead of the SUDO connection type

If you are already using the INTERACTIVE_SUDO connection type and you still get this error, please verify that you have correctly configured the `sudoPasswordPromptRegex` property. If you cannot determine the proper value for the `sudoPasswordPromptRegex` property, set the log level for the `com.xebialabs.overthere.ssh.SshInteractiveSudoPasswordHandlingStream` category to `TRACE` and examine the output.
