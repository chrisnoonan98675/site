---
title: Advanced remote scripting using the Overthere plugin
categories:
- xl-release
subject:
- Task types
tags:
- plugin
- remote script
- windows
- unix
since:
- XL Release 4.8.1
---

Out of the box, XL Release has two task types that you can use to run scripts on other hosts: [**Remote Script: Unix** and **Remote Script: Windows**](/xl-release/how-to/remote-script-plugin.html). While these task types are useful, they do not cover all use cases; for example, they do not support all SSH and WinRM connection options.

The [XL Release Overthere plugin](https://github.com/xebialabs/xlr-overthere-plugin) exposes all of the functionality of [Overthere](https://github.com/xebialabs/overthere), XebiaLabs' acclaimed remote scripting library that supports the agentless technology of [XL Deploy](/xl-deploy/index.html). It adds a script task type that you can use for Unix, Microsoft Windows, and z/OS hosts.

This topic explains how to install and configure the plugin, so you can use it to run remote scripts on any host. The example in this topic will create a script that checks the status of a remote server.

## Install the Overthere plugin

### Requirements

 * XL Release 4.8.1 or later

### Installation steps

1. Download the latest `xlr-overthere-plugin` JAR file from [GitHub](https://github.com/xebialabs/xlr-overthere-plugin/releases) .
2. Copy the JAR file to the `plugins` folder of your XL Release server.
3. Restart the server.

To verify that the installation succeeded, add a new task and look for the **Script (advanced)** type in the **Remote Script** category.

![Remote Script: Script (advanced)](../images/xlr-overthere-plugin/add-task.png)

## Configure a host

To use the **Script (advanced)** task, you must configure a host to which it will connect. This is different from the Remote Script: Unix and Remote Script: Windows tasks, in which you can simply enter a host address.

You define hosts in **Settings** > **Configuration**. This requires the *admin* role.

Hosts are platform-specific. To choose a host type:

{:.table .table-striped}
| Connecting to...        | Use                      |
|-------------------------|--------------------------|
| Linux                   | SSH host with OS=Unix    |
| Windows                 | CIFS host                |
| Windows with SSH server | SSH host with OS=Windows |
| Z/OS                    | SSH host with OS=ZOS     |

**Note:** The hosts that you configure here are available to all templates and releases in XL Release. To protect their credentials, it is recommended that you define the connections here, but leave the username and credentials empty. You can then enter the credentials on the task level.

### Configure a Unix host

To configure a Unix host:

1. From the top navigation menu, select **Settings** > **Configuration**.
1. Under **RemoteScript: Ssh host**, click **Add Ssh host**.
1. In the **Title** box, enter a name for the host.
1. Enter the connection information in the **Address**, **Port**, **Username**, and **Password** boxes.

    ![SSH host configuration](../images/xlr-overthere-plugin/ssh-configuration.png)

#### Advanced options

If a simple username/password connection over SSH is not sufficient, you can use additional connection options:
    
* Private key file authentication
* Connection methods such as SFTP, SCP, and SUDO
* Jumpstations (tunneling)
* [Any option exposed by the Overthere library](https://github.com/xebialabs/overthere#common-connection-options)  

![SSH host configuration](../images/xlr-overthere-plugin/ssh-configuration-advanced.png)

### Configure a Microsoft Windows host

To configure a Microsoft Windows host:

1. From the top navigation menu, select **Settings** > **Configuration**.
1. Under **RemoteScript: Cifs host**, click **Add Cifs host** ([CIFS](https://en.wikipedia.org/wiki/Server_Message_Block), also known as SMB, is the protocol used to connect to a Windows machine).
1. In the **Title** box, enter a name for the host.
1. Enter the connection information. The options are similar to the options for a Unix host.

**Tip:** The easiest way to connect to a Windows host is to use the WINRM_INTERNAL connection type. This requires that the XL Release server is also running on Windows.

All scripts on a Windows host will be interpreted as batch files. PowerShell is currently not supported.

## Sample advanced script task

As an example of an advanced script task:

1. Create an SSH host for a Unix host called *XLR production*.
1. Create a [release template](/xl-release/how-to/create-a-release-template.html) with one phase. Add a **Script (advanced)** task to the phase. For example:

    ![Task configuration](../images/xlr-overthere-plugin/task-definition.png)
    
    The `df` command will be executed on the *XLR production* machine. The user that connects to it is *labrat*. The password for *labrat* is defined on the task, but it could also be set to a [release variable](/xl-release/how-to/create-release-variables.html) of type *password*.

1. Add a [gate task](/xl-release/how-to/create-a-gate-task.html) to the end of the phase so the release won't terminate immediately.

    ![Task configuration](../images/xlr-overthere-plugin/release-flow.png)

1. Start a release from the template. When XL Release runs the script task, it captures the output of the script in the **Output** property of the task. You can see it under **Comments**:

    ![Task configuration](../images/xlr-overthere-plugin/task-output.png)
