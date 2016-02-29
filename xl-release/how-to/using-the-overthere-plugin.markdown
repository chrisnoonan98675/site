---
title: Advanced Remote Scripting using the Overthere plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- remote script
- windows
- unix
---

## Introduction

Out of the box, XL Release ships with two task types that can be used to un scripts on a another host: **Remote Script: Unix** and **Remote Script: Windows**.

Fine as they are, these scripts do not cover all use cases to connect to a remote host. For example, not all SSH or WinRM connection options are exposed.

In short what we need is a Remote Script task that exposes all functionality of [Overthere](https://github.com/xebialabs/overthere), XebiaLabs' acclaimed remote scripting library that underpins the agentless technology of XL Deploy.

Fortunately, the [xlr-overthere-plugin](https://github.com/xebialabs/xlr-overthere-plugin) just does that! This how-to explains how to install and use the plugin to take advantage of remote scripting using the Overthere library.

This article shows you how to install and configure the plugin, so you cna ue it to run remote scripts on any host. As an example we will create a script that checks the status of a remote server.

## Installing the Overthere plugin

### Requirements
 * XL Release 4.8.1 or higher

### Installation steps
1. Download the latest `xlr-overthere-plugin` jar from [Github](https://github.com/xebialabs/xlr-overthere-plugin/releases) 
2. Copy the jar file into the `plugins` folder of your XL Release server.
3. Restart the server.

The installation was successful if you now see the **Script (advanced)** task under the **Remote Script** category when adding a new task.

![Remote Script: Script (advanced)](../images/xlr-overthere-plugin/add-task.png)

## Configuration

To use the new **Script** task, we first need to configure the host to connect to. This is different form the old **Windows** and **Unix** tasks where you could simply enter a host address. Also note that the **Script** task is universal: it can be used for Unix hosts, Windows hosts and even Z/OS hosts.

**Hosts** are defined on the **Settings** > **Configuration** screen. You need the **admin** role to add servers on this page. 

Hosts are platform specific. This table shows you which host type to choose given the operating system.

| Connecting to...        | Use                          |
|-------------------------|------------------------------|
| Linux                   | **SSH host** with OS=Unix    |
| Windows                 | **Cifs Host**                |
| Windows with SSH server | **SSH host** with OS=Windows |
| Z/OS                    | **SSH Host** with OS=ZOS     |

### Unix

To connect to a Unix server, find the **RemoteScript: Ssh host** heading and click the **Add Ssh host** link.

You will now be presented with a wealth of options to configure access to the machine.

![SSH host configuraiton](../images/xlr-overthere-plugin/ssh-configuration.png)

First of all, you can configure which machine to connect to and as which user. 

If a simple username / password connection over SSH won't cut it, all sorts of other options are supported

* Private key file authentication
* Connection methods: SFTP, SCP, SUDO, etc.
* Jumpstations (tunneling)
* [Any option exposed by the Overthere library](https://github.com/xebialabs/overthere#common-connection-options)

![SSH host configuration](../images/xlr-overthere-plugin/ssh-configuration-advanced.png)

Please note that the configuration of this server is available to all templates and releases in XL Release. To protect credentials from leaking, it is good practice to define the connections here, but leave the username and its credentials empty. These can be configured on the task level.


### Windows

To connect to a Windows server, find the **RemoteScript: Cifs host** heading and click the **Add Cifs host** link. ('Cifs' is the name of the protocol used to connect to a Windows machine, better known as 'Smb').

This screen is similar to the SSH host configuration.

One thing to note here is that to connect to a Windows machine, by far the easiest way to get the configuration right is to select **Connection type: WINRM_INTERNAL**. Note that this only works if the XL Release server itself is running on a Windows host.

All scripts on a Windows host will be interpreted as batch files. PowerShell is currently not supported. 

## Example task

With the host definition in place, let's run a little example. Create a template with one Phase and add a **Script** task. Create an SSH host for a machine you can connect to and set it on the task. You can now specify the command to execute on the remote host in the **Script** property of the task.

Here's an example:

![Task configuration](../images/xlr-overthere-plugin/task-definition.png)

The command `df` will be executed on the **XLR production** machine. The user that connects to it is `labrat`. The password for `labrat` is defined on the task, but could also be a release variable of type password.

We add a **Gate task** in the flow so the release won't terminate immediately.

![Task configuration](../images/xlr-overthere-plugin/release-flow.png)

When running the task, the output of the script is captured both in the output property **Output** of the task and on is printed in the comment section of the task:

![Task configuration](../images/xlr-overthere-plugin/task-output.png)





