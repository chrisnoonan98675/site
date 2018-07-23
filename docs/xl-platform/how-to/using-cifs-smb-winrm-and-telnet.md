---
title: Using CIFS, SMB, WinRM, and Telnet
subject:
Remoting
categories:
xl-platform
xl-deploy
xl-release
tags:
connectivity
remoting
cifs
smb
winrm
telnet
overthere
weight: 338
---

The [remoting functionality](/xl-platform/concept/remoting-plugin.html) for XL Deploy and XL Release supports the [CIFS and SMB protocols](http://en.wikipedia.org/wiki/Server_Message_Block) for file manipulation and [WinRM](http://en.wikipedia.org/wiki/WS-Management) and [Telnet](http://en.wikipedia.org/wiki/Telnet) for process execution.

Microsoft Windows' built-in file sharing capabilities are based on CIFS and are therefore available and enabled by default, so you should not need to install new software on a target CIFS or SMB host. However, you might need to enable and configure some services.

To connect to a remote host using CIFS or SMB, ensure the host is reachable on port 445.

<a href="http://msdn.microsoft.com/en-us/library/aa384426(v=vs.85).aspx">WinRM</a> is available on Windows Server 2008 and later. The Remoting plugin supports basic authentication for local accounts and Kerberos authentication for domain accounts. Overthere has a built-in WinRM library that can be used from all operating systems by setting the connection type on a CIFS host (CI type `overthere.CifsHost`) to WINRM_INTERNAL. When connecting from a host that runs Windows, or when using a `winrs` proxy that runs Windows, the native WinRM capabilities of Windows (that is, the `winrs` command) can be used by setting the connection type to WINRM_NATIVE.

A Telnet Server is available on all Windows Server versions, although it might not be enabled.

## Set up WinRM

To use the WINRM_INTERNAL or the WINRM_NATIVE connection type, set up <a href="http://msdn.microsoft.com/en-us/library/aa384426(v=vs.85).aspx">Microsoft Windows Remote Management (WinRM)</a> on the remote host:

1. If the remote host is running Windows Server 2003 SP1 or SP2, or Windows XP SP2, install the [WS-Management v.1.1 package](http://support.microsoft.com/default.aspx?scid=kb;EN-US;936059&wa=wsignin1.0).

1. If the remote host is running Windows Server 2003 R2, go to **Control Panel** > **Add/Remove System Components** and add WinRM under **Management and Monitoring Tools**. Then, install the [WS-Management v.1.1 package](http://support.microsoft.com/default.aspx?scid=kb;EN-US;936059&wa=wsignin1.0) to upgrade the WinRM installation.

1. If the remote host is running Windows Vista or Windows 7, the **Windows Remote Management (WS-Management)** service is not started by default. Start the service and change its startup type to **Automatic (Delayed Start)** before proceeding.

1. On the remote host, open a command prompt (not a PowerShell prompt) using the **Run as Administrator** option and paste in the following lines.

   When using the WINRM_INTERNAL connection type:

		winrm quickconfig
		y
		winrm set winrm/config/service/Auth @{Basic="true"}
		winrm set winrm/config/service @{AllowUnencrypted="true"}
		winrm set winrm/config/winrs @{MaxMemoryPerShellMB="1024"}
		winrm set winrm/config/client @{TrustedHosts="*"}

   When using the WINRM_NATIVE connection type:

		winrm quickconfig
		y
		winrm set winrm/config/service/Auth @{Basic="true"}
		winrm set winrm/config/winrs @{MaxMemoryPerShellMB="1024"}

   Or keep reading for more detailed instructions.

1. Run the quick config of WinRM to start the Windows Remote Management service, configure an HTTP listener, and create exceptions in the Windows Firewall for the Windows Remote Management service:

		winrm quickconfig

	**Note:** The Windows Firewall must be running to run this command. See [Microsoft Knowledge Base article #2004640](http://support.microsoft.com/kb/2004640).

1. (Optional) By default, basic authentication is disabled in WinRM. Enable it if you are going to use local accounts to access the remote host:

		winrm set winrm/config/service/Auth @{Basic="true"}

1. (Optional) By default, Kerberos authentication is enabled in WinRM. Disable it if you are **not** going to use domain accounts to access the remote host:

		winrm set winrm/config/service/Auth @{Kerberos="false"}

	**Note:** Do not disable **Negotiate authentication**, as the `winrm` command itself uses it to configure the WinRM subsystem.

1. (Only required for WINRM_INTERNAL or when the property `winrsUnencrypted` is set to true) Configure WinRM to allow unencrypted SOAP messages:

		winrm set winrm/config/service @{AllowUnencrypted="true"}

1. Configure WinRM to provide enough memory to the commands that you are going to run (that is, 1024 MB):

		winrm set winrm/config/winrs @{MaxMemoryPerShellMB="1024"}

	**Note:** This is not supported by WinRM 3.0, which is included with the Windows Management Framework 3.0. This update [has been temporarily removed from Windows Update](http://blogs.msdn.com/b/powershell/archive/2012/12/20/windows-management-framework-3-0-compatibility-update.aspx) because of numerous incompatibility issues with other Microsoft products. However, if you have already installed WMF 3.0 and cannot downgrade, [Microsoft Knowledge Base article #2842230](http://support.microsoft.com/kb/2842230) describes a hotfix that can be installed to re-enable the `MaxMemoryPerShellMB` setting.

1. Configure WinRM to accept connections from trusted hosts:

		winrm set winrm/config/client @{TrustedHosts="*"}

	**Note:** This is not always required and the command as given will accept connections from all hosts.  This command can be refined by specifying hostnames. For example:

		winrm set winrm/config/client @{TrustedHosts="host1,host2..."}


1. To use the WINRM_INTERNAL or WINRM_NATIVE connection type with HTTPS (that is, with `winrmEnableHttps` set to true), follow these steps:

	(Optional) Create a self-signed certificate for the remote host by installing `selfssl.exe` from [the IIS 6 resource kit](http://www.microsoft.com/download/en/details.aspx?displaylang=en&id=17275) and running the command below or by following the instructions [in this blog post by Hans Olav](http://www.hansolav.net/blog/SelfsignedSSLCertificatesOnIIS7AndCommonNames.aspx):

		C:\Program Files\IIS Resources\SelfSSL>selfssl.exe /T /N:cn=HOSTNAME /V:3650
		Microsoft (R) SelfSSL Version 1.0
		Copyright (C) 2003 Microsoft Corporation. All rights reserved.

		Do you want to replace the SSL settings for site 1 (Y/N)?Y
		The self signed certificate was successfully assigned to site 1.

	Open a PowerShell window and enter the following command to find the thumbprint for the certificate for the remote host:

		PS C:\Windows\system32> Get-childItem cert:\LocalMachine\Root\ | Select-String -pattern HOSTNAME

		[Subject]
		  CN=HOSTNAME

		[Issuer]
		  CN=HOSTNAME

		[Serial Number]
		  527E7AF9142D96AD49A10469A264E766

		[Not Before]
		  5/23/2011 10:23:33 AM

		[Not After]
		  5/20/2021 10:23:33 AM

		[Thumbprint]
		  5C36B638BC31F505EF7F693D9A60C01551DD486F

	Create an HTTPS WinRM listener for the remote host with the thumbprint you've just found:

		winrm create winrm/config/Listener?Address=*+Transport=HTTPS @{Hostname="HOSTNAME"; CertificateThumbprint="THUMBPRINT"}

    If you are using the WINRM_INTERNAL connection type, add the self-signed certificate to the JVM trusted keystore as described in [this blog post](http://blog.nerdability.com/2013/01/tech-how-to-fix-sslpeerunverifiedexcept.html).

For more information about WinRM, refer to <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/aa384426(v=vs.85).aspx">the online documentation at Microsoft's DevCenter</a>.

### Domain accounts

For the WINRM_INTERNAL connection type, domain accounts must be specified using the new-style domain syntax; that is, `USER@FULL.DOMAIN`. For the WINRM_NATIVE connection type, domain accounts can be specified using either the new-style (`USER@FULL.DOMAIN`) or old-style (`DOMAIN\USER`) domain syntax. For both connection types, local accounts must be specified without an at-sign (`@`) or a backslash (`\`).

### Password limitations

Due to a limitation of the `winrs` command, passwords containing a single quotation mark (`'`) or a double quotation mark (`"`) cannot be used with the WINRM_NATIVE connection type.

### Set up Kerberos for WinRM

If you are going to use Microsoft Windows domain accounts to access remote hosts with the WINRM_INTERNAL connection type, you must configure Kerberos. Using Kerberos authentication requires that you follow the [Kerberos Requirements for Java](http://docs.oracle.com/javase/6/docs/technotes/guides/security/jgss/tutorials/KerberosReq.html) on the host that runs the XL Deploy or XL Release server.

1. Create a file called `krb5.conf` (Unix) or `krb5.ini` (Windows) with at least the following content:

        [libdefaults]
            default_realm = EXAMPLE.COM

        [realms]
            EXAMPLE.COM = {
                kdc = KDC.EXAMPLE.COM
            }

2. Replace the values with the name of your domain/realm and the hostname of your domain controller. You can add multiple entries to allow the XL Deploy server host to connect to multiple domains.
3. Choose whether to:
    * Store the file in the default location for your operating system
    * Store the file in another location (requires additional changes in the file)

The default locations are:

* Linux: `/etc/krb5.conf`
* Solaris: `/etc/krb5/krb5.conf`
* Windows: `C:\Windows\krb5.ini`

If you want to store the file in a different location and you are using XL Deploy 5.0.0 or later, add the following line to `XL_DEPLOY_SERVER_HOME/conf/xld-wrapper.conf` (replace the path with the file location):

    wrapper.java.additional.5=-Djava.security.krb5.conf=/path/to/krb5.conf

If you want to store the file in a different location and you are using XL Deploy 4.5.x or earlier, add the following line to the `server.sh` or `server.cmd` script (replace the path with the file location):

    -Djava.security.krb5.conf=/path/to/krb5.conf

See [the Kerberos V5 System Administrator's Guide at MIT](http://web.mit.edu/kerberos/krb5-1.10/krb5-1.10.6/doc/krb5-admin.html#krb5_002econf) for more information about the `krb5.conf` format.

#### Set up Kerberos for XL Deploy satellite

If you are using the [XL Deploy satellite module](/xl-deploy/concept/getting-started-with-the-satellite-module.html) with Kerberos, follow the instructions above to create a `krb5.conf` (Unix) or `krb5.ini` (Microsoft Windows) file on the satellite.

If you want to store the file in the default location for the satellite's operating system, no additional changes are required.

If you want to store the file in a different location, add the following line to the `run.sh` or `run.cmd` script on the satellite (replace the path with the file location):

    -Djava.security.krb5.conf=/path/to/krb5.conf

For more information about configuring satellites, refer to [Install and configure a satellite server](/xl-deploy/how-to/install-and-configure-a-satellite-server.html).

#### Generate the Kerberos configuration file

It's not always easy to determine the right Windows domain name and the hostnames of all domain controllers. You can generate the configuration by copying the PowerShell script [generate-krb5-conf.ps1](/xl-deploy/how-to/sample-scripts/generate-krb5-conf.ps1) to a Windows machine in the target domain and then running it with the following command:

    powershell -f generate-krb5-conf.ps1

#### Configuration for the remote host

By default, XL Deploy or XL Release will request access to a Kerberos <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms677949(v=vs.85).aspx">service principal name</a> of the form `WSMAN/HOST`, for which an SPN should be configured automatically when you configure WinRM for a remote host.

If that was not configured correctly — for example, if you have overridden the default SPN for which a ticket is requested through the `winrmKerberosAddPortToSpn` or the `winrmKerberosUseHttpSpn` properties — you will have configure the service principal names manually. This can be achieved by invoking the <a href="http://technet.microsoft.com/en-us/library/cc731241(v=ws.10).aspx">setspn</a> command, as an Administrator, on any host in the domain, as follows:

    setspn -A <em>PROTOCOL</em>/<em>ADDRESS</em>:<em>PORT</em> <em>WINDOWS-HOST</em>

Where:

* `PROTOCOL` is `WSMAN` (default) or `HTTP` (if `winrmKerberosUseHttpSpn` has been set to "true")
* `ADDRESS` is the address used to connect to the remote host
* `PORT` (optional) is the port used to connect to the remote host (usually 5985 or 5986, only necessary if `winrmKerberosAddPortToSpn` has been set to "true")
* `WINDOWS-HOST` is the short Windows hostname of the remote host

### Enable multi-hop support (CredSSP) for WinRM

To enable <a href="http://msdn.microsoft.com/en-us/library/ee309365(v=vs.85).aspx">multi-hop support</a> (also known as CredSSP) for WinRM when using the WINRM_NATIVE connection type, follow the steps below.

**Note:** CredSSP is not supported by the WINRM_INTERNAL connection type.

#### Configure the server

On the XL Deploy, XL Deploy satellite, and/or XL Release server:

1. Execute the following command:

        winrm set winrm/config/client/auth @{CredSSP="true"}

1. Open the Group Policy Editor (`gpedit.msc`).
1. Go to **Computer Configuration** > **Administrative Templates** > **System** > **Credentials Delegation**.
1. Edit **Allow Delegating Fresh Credentials**.
1. Select **Enabled**.
1. Add the SPNs for target servers to the list; for example, `WSMAN/hostname.domain`.

   **Tip:** Wildcards are allowed; for example, `WSMAN/*`.

1. Click **Apply**.

If the server is not in the same Windows domain as at least one of the target machines, you must also:

1. Edit **Allow Delegating Fresh Credentials with NTLM-only Server Authentication**.
1. Select **Enabled**.
1. Add the SPNs for target servers to the list; for example, `WSMAN/hostname.domain`.
1. Click **Apply**.

#### Configure each target machine

On each target machine, execute the following command:

    winrm set winrm/config/service/auth @{CredSSP="true"}

#### Configure the host CIs

For each target machine, set the **Allow credential delegation** (`winrsAllowDelegate`) property on the host configuration item (`overthere.Host`) in XL Deploy or XL Release to "true".

### Useful WinRM commands

These commands are useful when you are using WinRM to execute commands and manipulate files on a remote host.

Quickly configure WinRM with HTTPS:

    winrm quickconfig -transport:https

View the complete WinRM configuration:

    winrm get winrm/config

View the listeners that have been configured:

    winrm enumerate winrm/config/listener

Create an HTTP listener:

    winrm create winrm/config/listener?Address=*+Transport=HTTP` (also done by `winrm quickconfig`)

Allow all hosts to connect to the WinRM listener:

    winrm set winrm/config/client @{TrustedHosts="*"}

Allow a fixed set of hosts to connect to the WinRM listener:

    winrm set winrm/config/client @{TrustedHosts="host1,host2..."}

List all service principal names configured for the domain (when using Kerberos):

    setspn -Q */*

List all service principal names configured for a specific host in the domain (when using Kerberos):

    setspn -L _WINDOWS-HOST_


## Set up Telnet

To use the TELNET connection type, enable and configure the Telnet Server on the remote host:

1. (Optional) If the Telnet Server is not already installed on the remote host, add it using the Add Features Wizard in the Server Manager console.
1. (Optional) If the remote host is running Windows Server 2003 SP1 or an x64-based version of Windows Server 2003, install the Telnet server according to [these instructions from the Microsoft Support site](http://support.microsoft.com/kb/899260).
1. Enable the Telnet Server Service on the remote host according to <a href="http://technet.microsoft.com/en-us/library/cc732046(WS.10).aspx">these instructions on the Microsoft Technet site</a>.
1. After you have started the Telnet Server, open a command prompt as the Administrator user on the remote host and enter the command `tlntadmn config mode=stream` to enable stream mode.

When the Telnet server is enabled any user that is in the Administrators group or that is in the TelnetClients group and that has the Allow logon locally privilege can log in using Telnet. See the Microsoft Technet to learn [how to grant a user or group the right to logon locally on Windows Server 2008 R2](http://technet.microsoft.com/en-us/library/ee957044(WS.10).aspx).

### Domain accounts

For the TELNET connection type, domain accounts must be specified using the old-style domain syntax, e.g `DOMAIN\USER`.

## Using administrative shares with CIFS

By default, XL Deploy and XL Release will access [administrative shares](http://en.wikipedia.org/wiki/Administrative_share) on the remote host. These shares are only accessible for users that have administrator privileges on the remote host.

You can use Windows-style paths such as `C:\Program Files\IBM\WebSphere\AppServer`; these will be translated to SMB URLs that use administrative shares. For example, if you provide the path `C:\IBM\WebSphere\profiles\Dmgr01` on a CIFS host (`overthere.CifsHost`) called _windows1_, it will be translated to the SMB path `\\windows1\C$\IBM\WebSphere\profiles\Dmgr01`, which is equivalent to the [JCIFS](https://jcifs.samba.org/) URL `smb://windows1/C$/IBM/WebSphere/profiles/Dmgr01`. However, an administrative share such as `C$` can only be accessed by users with administrator privileges.

If you want to access the remote host with an account that does *not* have administrator privileges, you can specify other shares in the **Windows path to Windows share mappings** (`pathShareMapping`) property on the CIFS host (`overthere.CifsHost`). For example, if you map the `WebSphere` share to `C:\IBM\WebSphere`, it will  be translated to the SMB path `C:\IBM\WebSphere\profiles\Dmgr01` on host _windows1_ to `\\windows1\WebSphere\profiles\Dmgr01` (instead of `\\windows1\C$\IBM\WebSphere\profiles\Dmgr01`).

If you are using a plugin that will copy a file to a temporary directory (such as the XL Deploy [File plugin](/xl-deploy/concept/file-plugin.html) or [Generic plugin](/xl-deploy/concept/generic-plugin.html)), you should either:

* Add the directory to the mapping
* Set the `copyDirectlyToTargetPath` hidden property on the CIFS host to "true" (in this case, the file will be copied directory to the target directory)

**Note:** The user identified by the user name (`username`) property on the CIFS host must have access to the shares that you specify, as well as to the underlying directories and files.
