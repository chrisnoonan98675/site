---
title: Set up Kerberos for WINRM_INTERNAL
subject:
- Remoting
categories:
- xl-deploy
tags:
- connectivity
- remoting
- winrm
- kerberos
- overthere
weight: 344
---

If you are going to use Microsoft Windows domain accounts to access remote hosts with the WINRM_INTERNAL connection type, you must configure Kerberos.

## Configure Kerberos for XL Deploy

Using Kerberos authentication requires that you follow the [Kerberos Requirements for Java](http://docs.oracle.com/javase/6/docs/technotes/guides/security/jgss/tutorials/KerberosReq.html) on the host that runs the XL Deploy server.

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

### Configure Kerberos for the XL Deploy satellite module

If you are using the [XL Deploy satellite module](/xl-deploy/concept/getting-started-with-the-satellite-module.html) with Kerberos, follow the instructions above to create a `krb5.conf` (Unix) or `krb5.ini` (Microsoft Windows) file on the satellite.

If you want to store the file in the default location for the satellite's operating system, no additional changes are required.

If you want to store the file in a different location, add the following line to the `run.sh` or `run.cmd` script on the satellite (replace the path with the file location):

    -Djava.security.krb5.conf=/path/to/krb5.conf

For more information about configuring satellites, refer to [Install and configure a satellite server](/xl-deploy/how-to/install-and-configure-a-satellite-server.html).

## Generating the Kerberos configuration file

It's not always easy to determine the right Windows domain name and the hostnames of all domain controllers. You can generate the configuration by copying the PowerShell script [generate-krb5-conf.ps1](sample-scripts/generate-krb5-conf.ps1) to a Windows machine in the target domain and then running it with the following command:

    powershell -f generate-krb5-conf.ps1

## Kerberos configuration for the remote host

By default, XL Deploy will request access to a Kerberos <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms677949(v=vs.85).aspx">service principal name</a> of the form `WSMAN/HOST`, for which an SPN should be configured automatically when you configure WinRM for a remote host.

If that was not configured correctly, e.g. if you have overridden the default SPN for which a ticket is requested through the `winrmKerberosAddPortToSpn` or the `winrmKerberosUseHttpSpn` properties, you will have configure the service principal names manually.

This can be achieved by invoking the <a href="http://technet.microsoft.com/en-us/library/cc731241(v=ws.10).aspx">setspn</a> command, as an Administrator, on any host in the domain, as follows:

    setspn -A <em>PROTOCOL</em>/<em>ADDRESS</em>:<em>PORT</em> <em>WINDOWS-HOST</em>

Where:

* `PROTOCOL` is `WSMAN` (default) or `HTTP` (if `winrmKerberosUseHttpSpn` has been set to `true`)
* `ADDRESS` is the address used to connect to the remote host
* `PORT` (optional) is the port used to connect to the remote host (usually 5985 or 5986, only necessary if `winrmKerberosAddPortToSpn` has been set to `true`)
* `WINDOWS-HOST` is the short Windows hostname of the remote host

Some other useful commands:

* List all service principal names configured for the domain: `setspn -Q */*`
* List all service principal names configured for a specific host in the domain: `setspn -L _WINDOWS-HOST_`
