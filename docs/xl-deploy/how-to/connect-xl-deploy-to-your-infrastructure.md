---
title: Connect XL Deploy to your infrastructure
subject:
Remoting
categories:
xl-deploy
tags:
connectivity
middleware
host
ssh
winrm
cifs
smb
weight: 336
---

After you have installed [XL Deploy](/xl-deploy/how-to/install-xl-deploy.html) and logged in for the first time, use these instructions to connect XL Deploy to the host on which your middleware is running. Follow the instructions for the host's operating system and the connection protocol that you want XL Deploy to use:

* [Unix and SSH](#connect-to-a-unix-host-using-ssh)
* [Windows and WinRM](#connect-to-a-windows-host-using-winrm)

If you would like to use SSH on Windows through WinSSHD or OpenSSH, refer to [Set up SSH](/xl-platform/how-to/set-up-ssh.html).

## Connect to a Unix host using SSH

To connect to a Unix host using SSH:

1. In the Explorer, click ![Context menu](/images/menu_three_dots.png) next to **Infrastructure** and select **New** > **overthere** > **SshHost**. A new tab appears.
2. In the **Name** box, enter a name for the host.
3. Select **UNIX** from the **Operating system** list.
4. Select the **Connection Type**:
    * Select **SCP** if the user that will connect to the host has privileges to manipulate files and execute commands.
    * Select **SU** if the user that will connect to the host can use `su` to log in as one user and execute commands as a different user.
    * Select **SUDO** or **INTERACTIVE_SUDO** if the user that will connect to the host can use `sudo` to execute commands as a different user. Refer to [Set up SSH](/xl-platform/how-to/set-up-ssh.html) if you don't know which connection type to choose.
5. In the **Address** box, enter the IP address of the host.
6. In the **Port** box, enter the port on which XL Deploy should connect to the host (default is 22).
7. In the **Username** box, enter the user name that XL Deploy should use when connecting to the host.
8. In the **Password** box, enter the user's password.
9. If you chose the connection type SU, SUDO, or INTERACTIVE_SUDO, go to the **Advanced** section and enter the user name and password (in the case of SU) that XL Deploy should use.
10. Click **Save**.

## Connect to a Windows host using WinRM

To check if WinRM is installed on the host, follow <a href="http://technet.microsoft.com/en-us/library/ff520073(WS.10).aspx" target="_blank">the appropriate instructions</a> for the host's version of Windows. If it is not installed, follow [these instructions](/xl-platform/how-to/using-cifs-smb-winrm-and-telnet.html) to install it, then follow the steps below to connect XL Deploy to the host.

To connect to a Windows host using WinRM:

1. In the Explorer, click ![Context menu](/images/menu_three_dots.png) next to **Infrastructure** and select **New** > **overthere** > **SmbHost**. A new tab appears.

    **Tip:** SMB is supported in XL Deploy 5.5.6, 6.0.0, and later. When using earlier versions of XL Deploy, you can use the CIFS protocol instead (`overthere.CifsHost`).

2. In the **Name** box, enter a name for the host.
3. Select **WINDOWS** from the **Operating system** list.
4. Select the **Connection Type**:
    * If the computer where you installed XL Deploy does not run Windows, select **WINRM_INTERNAL**.
    * If the computer where you installed XL Deploy runs Windows, select **WINRM_NATIVE**.

    **Note:** The WINRM_NATIVE option requires [Winrs](http://technet.microsoft.com/en-us/library/hh875630.aspx) to be installed on the computer where you installed XL Deploy. This is only supported for Windows 7, Windows 8, Windows Server 2008 R2, and Windows Server 2012.

5. In the **Address** box, enter the IP address of the host.
6. In the **Port** box, optionally enter the port on which Telnet or WinRM runs.

      **Note:** You can change the port on which the CIFS or SMB server runs in the **CIFS** or **SMB** section (defaults to 445).

7. In the **Username** box, enter the user name that XL Deploy should use when connecting to the host.
8. In the **Password** box, enter the user's password.

      **Tip:** For information about the permissions that the user must have, see the documentation on [WinRM connections](/xl-platform/how-to/using-cifs-smb-winrm-and-telnet.html).

10. Click **Save**..

## Verify the connection

After you configure the host, verify that XL Deploy can connect to it:

1. Under **Infrastructure**, click ![Context menu](/images/menu_three_dots.png) next to the host and select **Check connection**. A new tab appears with the steps that XL Deploy will execute to check the connection.
2. Click **Execute**. XL Deploy verifies that it can transfer files to the host and execute commands on it.

If the connection check succeeds, the state of the steps will be **DONE**.

If the connection check fails, refer to tips for troubleshooting [SSH](/xl-platform/how-to/troubleshoot-an-ssh-connection.html) and [WinRM](troubleshoot-a-winrm-connection.html) connections.
