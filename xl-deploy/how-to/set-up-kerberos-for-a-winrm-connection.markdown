---
title: Set up Kerberos for WINRM_INTERNAL
tags:
- connectivity
- remoting
- winrm
- kerberos
- overthere
---

If you are going to use Windows domain accounts to access the remote host with the WINRM_INTERNAL connection type, you must configure Kerberos.

## Kerberos configuration for XL Deploy

In addition to the setup described in [the WINRM section](#cifs_host_setup_winrm), using Kerberos authentication requires that you follow the [Kerberos Requirements for Java](http://docs.oracle.com/javase/6/docs/technotes/guides/security/jgss/tutorials/KerberosReq.html) on the host that runs the XL Deploy server.

Create a file called `krb5.conf` (Unix) or `krb5.ini` (Windows) with at least the following content: 

    [realms]
    EXAMPLE.COM = {
        kdc = KDC.EXAMPLE.COM
    }

Replace the values with the name of your domain/realm and the hostname of your domain controller (multiple entries can be added to allow the XL Deploy server host to connect to multiple domains) and place the file in the default location for your operating system:

* Linux: `/etc/krb5.conf`
* Solaris: `/etc/krb5/krb5.conf`
* Windows: `C:\Windows\krb5.ini`

Alternatively, place the file somewhere else and edit the `server.sh` or `server.cmd` startup script and add the following Java system property to the command line: `-Djava.security.krb5.conf=/path/to/krb5.conf`. Replace the path with the location of the file you just created. 

See [the Kerberos V5 System Administrator's Guide at MIT](http://web.mit.edu/kerberos/krb5-1.10/krb5-1.10.6/doc/krb5-admin.html#krb5_002econf) for more information on the `krb5.conf` format.

## Kerberos configuration for the remote host

By default, XL Deploy will request access to a Kerberos <a href="http://msdn.microsoft.com/en-us/library/windows/desktop/ms677949(v=vs.85).aspx">service principal name</a> of the form `WSMAN/HOST`, for which an SPN should be configured automatically when you [configure WinRM for a remote host](#cifs_host_setup_winrm).

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
